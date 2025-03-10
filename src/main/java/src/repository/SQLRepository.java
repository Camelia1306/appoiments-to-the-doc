package repository;

import domain.Pacient;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

 public class SQLRepository  extends MemoryRepository<Pacient> implements AutoCloseable  {

        private static final String JDBC_URL =
                "jdbc:sqlite:src/pacienti";

        private Connection conn = null;

     public SQLRepository() {
         openConnection();
         createSchema();
      //   initTables();
         loadData();
     }

     private void loadData() {
         try {
             try (PreparedStatement statement = conn.prepareStatement("SELECT * from pacienti");
                  ResultSet rs = statement.executeQuery();) {
                 while (rs.next()) {
                     Pacient pacient = new Pacient(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"),
                             rs.getInt("varsta"));
                 }
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
     }

        private void openConnection() {
            try {
                SQLiteDataSource ds = new SQLiteDataSource();
                ds.setUrl(JDBC_URL);
                if (conn == null || conn.isClosed()){
                    conn = ds.getConnection();
                    conn.setAutoCommit(true);}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        void createSchema() {
            try {
                try (final Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS pacienti(id int primary key, nume varchar(50), prenume varchar(50), varsta int);");
                }
            } catch (SQLException e) {
                System.err.println("[ERROR] createSchema : " + e.getMessage());
            }
        }

        void initTables() {
            final String[] pacienti = new String[]{
                    "322|nume2|prenume2|43"
            };

            try {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO pacienti VALUES (? ,?, ?, ?)")) {
                    for (String s : pacienti) {
                        String[] tokens = s.split("[|]");
                        statement.setInt(1, Integer.parseInt(tokens[0]));
                        statement.setString(2, tokens[1]);
                        statement.setString(3, tokens[2]);
                        statement.setInt(4, Integer.parseInt(tokens[3]));
                        statement.executeUpdate();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void add(Pacient p) throws RepositoryExceptions {
         super.add(p);
            try {
                try (PreparedStatement statement = conn.prepareStatement("INSERT INTO pacienti VALUES (?,?, ?, ?)")) {
                    statement.setInt(1, p.getId());
                    statement.setString(2, p.getNume());
                    statement.setString(3, p.getPrenume());
                    statement.setInt(4, p.getVarsta());
                    statement.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void removePacient(int id) {
            try {
                try (PreparedStatement statement = conn.prepareStatement("DELETE FROM pacienti WHERE id=?")) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<Pacient> getAll() {
            ArrayList<Pacient> pacients = new ArrayList<>();

            try {
                try (PreparedStatement statement = conn.prepareStatement("SELECT * from pacienti"); ResultSet rs = statement.executeQuery();) {
                    while (rs.next()) {
                        Pacient p = new Pacient(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"),
                                rs.getInt("varsta"));
                        pacients.add(p);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return pacients;
        }

        public Pacient findById(int id)  throws RepositoryExceptions
        {
            try (var statement = conn.prepareStatement("SELECT * FROM pacienti WHERE id = (?)")) {
                statement.setInt(1, id);
                var resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    var pacient_id = resultSet.getInt(1);
                    var nume = resultSet.getString(2);
                    var prenume = resultSet.getString(3);
                    var varsta = resultSet.getInt(4);
                    return new Pacient(pacient_id, nume, prenume, varsta);
                }
            } catch (SQLException e) {
                throw new RepositoryExceptions("Eroare la stergerea artistului: " + e.getMessage());
            }
            throw new RepositoryExceptions("Element with id not found=" + id);
        }

     public void updatePacient(Pacient pacient) throws RepositoryExceptions {
         try {
             String sql = "UPDATE pacienti SET nume = ?, prenume = ?, varsta = ? WHERE id = ?";
             try (PreparedStatement statement = conn.prepareStatement(sql)) {
                 statement.setString(1, pacient.getNume());
                 statement.setString(2, pacient.getPrenume());
                 statement.setInt(3, pacient.getVarsta());
                 statement.setInt(4, pacient.getId());

                 int rowsUpdated = statement.executeUpdate();
                 if (rowsUpdated == 0) {
                     throw new RepositoryExceptions("No pacient found with ID = " + pacient.getId());
                 }
             }
         } catch (SQLException e) {
             throw new RepositoryExceptions("Error updating pacient: " + e.getMessage());
         }
     }


     @Override
     public void close() throws Exception {
         try {
             if (conn != null)
                 conn.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 }

