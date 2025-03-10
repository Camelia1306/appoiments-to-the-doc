package repository;

import domain.Pacient;
import domain.Programare;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLRepositoryProgramari extends MemoryRepository<Programare> implements AutoCloseable {

    private static final String JDBC_URL = "jdbc:sqlite:src/programari";
    private Connection conn = null;

    public SQLRepositoryProgramari() {
        openConnection();
        createSchema();
        loadData();
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed()) {
                conn = ds.getConnection();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createSchema() {
        try (final Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS programari (
                    id INTEGER PRIMARY KEY,
                    pacient_id INTEGER,
                    pacient_nume VARCHAR(255),
                    pacient_prenume VARCHAR(255),
                    pacient_varsta INTEGER,
                    data DATETIME,
                    scop VARCHAR(255)
                );
            """);
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    private void loadData() {
        String sql = """
            SELECT id, pacient_id, pacient_nume, pacient_prenume, pacient_varsta, data, scop FROM programari;
        """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int pacientId = rs.getInt("pacient_id");
                String dataText = rs.getString("data");

                // Formatăm data corect
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime data = LocalDateTime.parse(dataText, formatter);

                String scop = rs.getString("scop");

                String pacientNume = rs.getString("pacient_nume");
                String pacientPrenume = rs.getString("pacient_prenume");
                int pacientVarsta = rs.getInt("pacient_varsta");

                Pacient pacient = new Pacient(pacientId, pacientNume, pacientPrenume, pacientVarsta);

                // Creăm obiectul Programare
                Programare programare = new Programare(id, pacient, data, scop);
                add(programare);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Programare programare) {
        String sql = """
            INSERT INTO programari (id, pacient_id, pacient_nume, pacient_prenume, pacient_varsta, data, scop) 
            VALUES (?, ?, ?, ?, ?, ?, ?);
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, programare.getId());
            stmt.setInt(2, programare.getPacient().getId());
            stmt.setString(3, programare.getPacient().getNume());
            stmt.setString(4, programare.getPacient().getPrenume());
            stmt.setInt(5, programare.getPacient().getVarsta());
            stmt.setTimestamp(6, Timestamp.valueOf(programare.getData()));
            stmt.setString(7, programare.getScop());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Programare> getAll() {
        List<Programare> programari = new ArrayList<>();
        String sql = """
            SELECT id, pacient_id, pacient_nume, pacient_prenume, pacient_varsta, data, scop FROM programari;
        """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int pacientId = rs.getInt("pacient_id");
                String pacientNume = rs.getString("pacient_nume");
                String pacientPrenume = rs.getString("pacient_prenume");
                int pacientVarsta = rs.getInt("pacient_varsta");
                String dataText = rs.getString("data");

                // Formatăm data corect
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime data = LocalDateTime.parse(dataText, formatter);

                String scop = rs.getString("scop");

                // Creăm obiectul Pacient
                Pacient pacient = new Pacient(pacientId, pacientNume, pacientPrenume, pacientVarsta);

                // Creăm obiectul Programare
                Programare programare = new Programare(id, pacient, data, scop);
                programari.add(programare);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programari;
    }

    public void removeProgramare(int id) {
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM programari WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
