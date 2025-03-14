package com.example.first_try_of_fx;

import domain.IdGenerator;
import domain.Pacient;
import domain.Programare;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import repository.RepositoryExceptions;
import repository.SQLRepository;
import repository.SQLRepositoryProgramari;
import service.ProgramareService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController {
    public TableView tablePacienti;
    public ListView<Pacient> listPacienti;
    public Label labelId;
    public TextField textFieldId;
    public Label labelname;
    public TextField textFieldName;
    public Label labelsurname;
    public TextField textFieldSurname;
    public Label labelAge;
    public TextField textFieldAge;
    public Button buttonAdd;
    public Button buttonDeleteProgramare;
    @FXML
    private Label welcomeText;

    public TableView<Programare> tableProgramari;
    public ListView<Programare> listProgramari;
    public TextField textFieldProgramareId;
    public TextField textFieldPacientId;
    public TextField textFieldData;
    public TextField textFieldScop;

    private ObservableList<Programare> dataProgramari = FXCollections.observableList(new ArrayList<>());
    private SQLRepositoryProgramari repositoryProgramari;


    private ObservableList<Pacient> dataPacienti = FXCollections.observableArrayList(new ArrayList<>());
    private SQLRepository repository;

    public void init() {
        listPacienti.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pacient>() {
            @Override
            public void changed(ObservableValue<? extends Pacient> observableValue, Pacient pacient, Pacient p1) {
                ObservableList<Pacient> changes = listPacienti.getSelectionModel().getSelectedItems();
                Pacient selectedPacient = changes.get(0);

                textFieldId.setText(Integer.toString(selectedPacient.getId()));
                textFieldName.setText(selectedPacient.getNume());
                textFieldSurname.setText(selectedPacient.getPrenume());
                textFieldAge.setText(Integer.toString(selectedPacient.getVarsta()));
            }
        });

        listProgramari.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Programare>() {
            @Override
            public void changed(ObservableValue<? extends Programare> observableValue, Programare oldProgramare, Programare newProgramare) {
                if (newProgramare != null) {
                    textFieldProgramareId.setText(Integer.toString(newProgramare.getId()));
                    textFieldPacientId.setText(
                            newProgramare.getPacient().getId() + "; " +
                                    newProgramare.getPacient().getNume() + "; " +
                                    newProgramare.getPacient().getPrenume() + "; " +
                                    newProgramare.getPacient().getVarsta()
                    );                    textFieldData.setText(newProgramare.getData().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    textFieldScop.setText(newProgramare.getScop());
                }
            }
        });

        TableColumn<Pacient, Integer> columnId = new TableColumn<>("Pacient Id");
        columnId.setCellValueFactory(square -> new SimpleIntegerProperty(square.getValue().getId()).asObject());

        TableColumn<Pacient, String> columnNume = new TableColumn<>("Nume");
        columnNume.setCellValueFactory(square -> new SimpleStringProperty(square.getValue().getNume()));

        TableColumn<Pacient, String> columnSurname = new TableColumn<>("Prenume");
        columnSurname.setCellValueFactory(square -> new SimpleStringProperty(square.getValue().getPrenume()));

        TableColumn<Pacient, Integer> columnVarsta = new TableColumn<>("Varsta");
        columnVarsta.setCellValueFactory(square -> new SimpleIntegerProperty(square.getValue().getVarsta()).asObject());

        tablePacienti.getColumns().add(columnId);
        tablePacienti.getColumns().add(columnNume);
        tablePacienti.getColumns().add(columnSurname);
        tablePacienti.getColumns().add(columnVarsta);


        TableColumn<Programare, Integer> columnProgramareId = new TableColumn<>("Programare ID");
        columnProgramareId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getId()).asObject());

        TableColumn<Programare, Integer> columnPacientId = new TableColumn<>("Pacient ID");
        columnPacientId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getPacient().getId()).asObject());

        TableColumn<Programare, String> columnData = new TableColumn<>("Data");
        columnData.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getData().toString()));

        TableColumn<Programare, String> columnScop = new TableColumn<>("Scop");
        columnScop.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getScop()));

        tableProgramari.getColumns().addAll(columnProgramareId, columnPacientId, columnData, columnScop);
        listProgramari.setItems(dataProgramari);
        tableProgramari.setItems(dataProgramari);

        HBox.setHgrow(tablePacienti, Priority.ALWAYS);
        VBox.setVgrow(tablePacienti, Priority.ALWAYS);
        HBox.setHgrow(tableProgramari, Priority.ALWAYS);
        VBox.setVgrow(tableProgramari, Priority.ALWAYS);
        HBox.setHgrow(textAreaResults, Priority.ALWAYS);
        VBox.setVgrow(textAreaResults, Priority.ALWAYS);

    }

    public void setRepository(SQLRepository repository) {
        this.repository = repository;
        listPacienti.setItems(dataPacienti);
        tablePacienti.setItems(dataPacienti);

        dataPacienti.addAll(repository.getAll());

    }

    public void setRepositoryProgramari(SQLRepositoryProgramari repositoryProgramari) {
        this.repositoryProgramari = repositoryProgramari;
        listProgramari.setItems(dataProgramari);
        tableProgramari.setItems(dataProgramari);

        dataProgramari.addAll(repositoryProgramari.getAll());
    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        try {
            var pacientId = Integer.parseInt(textFieldId.getText());
            var pacientName = textFieldName.getText();
            var pacientSurname = textFieldSurname.getText();
            var pacientVarsta = Integer.parseInt(textFieldAge.getText());
            Pacient pacient = new Pacient(pacientId, pacientName, pacientSurname, pacientVarsta);
            repository.add(pacient);
            dataPacienti.add(pacient);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } catch (RepositoryExceptions e) {
            throw new RuntimeException(e);
        } finally {
            textFieldId.clear();
            textFieldName.clear();
            textFieldAge.clear();
            textFieldSurname.clear();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header for simplicity
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void updatePacient(ActionEvent actionEvent) {
        // Get selected patient from TableView
        Pacient selectedPacient = (Pacient) tablePacienti.getSelectionModel().getSelectedItem();

        if (selectedPacient == null) {
            showAlert("No Selection", "Please select a pacient to update.");
            return;
        }

        // Retrieve updated values from input fields
        String newNume = textFieldName.getText();
        String newPrenume = textFieldSurname.getText();
        int newVarsta;

        try {
            newVarsta = Integer.parseInt(textFieldAge.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number for age.");
            return;
        }

        // Update the object with new values
        selectedPacient.setNume(newNume);
        selectedPacient.setPrenume(newPrenume);
        selectedPacient.setVarsta(newVarsta);

        try {
            // Call repository to update in DB
            repository.updatePacient(selectedPacient);

            // Refresh table to show updated values
            tablePacienti.refresh();
            listPacienti.refresh();
        } catch (RepositoryExceptions e) {
            showAlert("Update Failed", e.getMessage()   );
        }
    }


    public void onAddProgramareButtonClick(ActionEvent actionEvent) {
        try {
            int programareId = Integer.parseInt(textFieldProgramareId.getText().trim());
            String pacientData = textFieldPacientId.getText().trim(); // Stringul cu datele pacientului
            String scop = textFieldScop.getText().trim();

            // Parsam data și ora
            String dateText = textFieldData.getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dataProgramare = LocalDateTime.parse(dateText, formatter);

            // Prelucram stringul pacientului
            String[] pacientParts = pacientData.split(";");
            if (pacientParts.length != 4) {
                throw new IllegalArgumentException("Format invalid pentru pacient! Trebuie să fie:id;nume;prenume;varsta");
            }
            int pacientid = Integer.parseInt(pacientParts[0].trim());
            String pacientNume = pacientParts[1].trim();
            String pacientPrenume = pacientParts[2].trim();
            int pacientVarsta = Integer.parseInt(pacientParts[3].trim());
            Pacient pacient = new Pacient(pacientid, pacientNume, pacientPrenume, pacientVarsta);

            Programare programare = new Programare(programareId, pacient, dataProgramare, scop);

            // Adaugam in repository si lista locala
            repositoryProgramari.add(programare);
            dataProgramari.add(programare);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Eroare: " + e.getMessage()).show();
        }
    }



    private ProgramareService programareService;

    public void setProgramareService(ProgramareService programareService) {
        this.programareService = programareService;
    }

    public void handleNumarProgramariPerPacient(ActionEvent actionEvent) {
        Map<Pacient, Long> statistica = programareService.numarProgramariPerPacient(new ArrayList<>(dataProgramari));
        StringBuilder rezultat = new StringBuilder("Număr programări per pacient:\n");
        statistica.forEach((pacient, numarProgramari) ->
                rezultat.append(pacient.getNume()).append(" ").append(pacient.getPrenume())
                        .append(": ").append(numarProgramari).append(" programări\n")
        );
        textAreaResults.setText(rezultat.toString());
    }

    public void handleZileDeLaUltimaProgramare(ActionEvent actionEvent) {
        Map<Pacient, Long> zile = programareService.zileDeLaUltimaProgramare(new ArrayList<>(dataProgramari));
        StringBuilder rezultat = new StringBuilder("Zile de la ultima programare:\n");
        zile.forEach((pacient, zileTrecute) ->
                rezultat.append(pacient.getNume()).append(" ").append(pacient.getPrenume())
                        .append(": ").append(zileTrecute).append(" zile\n")
        );
        textAreaResults.setText(rezultat.toString());
    }

    public void handleCeleMaiAglomerateLuni(ActionEvent actionEvent) {
        Map.Entry<Month, Long> lunaAglomerata = programareService.celeMaiAglomerateLuni(new ArrayList<>(dataProgramari));

        if (lunaAglomerata != null) {
            String rezultat = "Cea mai aglomerată lună este: "
                    + lunaAglomerata.getKey() + " cu "
                    + lunaAglomerata.getValue() + " programări.\n";
            textAreaResults.setText(rezultat);
        } else {
            textAreaResults.setText("Nu există programări.");
        }
    }


    public void handleNumarProgramariPerLuna(ActionEvent actionEvent) {
        Map<Month, Long> statistica = programareService.numarProgramariPerLuna(new ArrayList<>(dataProgramari));
        StringBuilder rezultat = new StringBuilder("Număr programări per lună:\n");
        statistica.forEach((luna, numarProgramari) ->
                rezultat.append(luna).append(": ").append(numarProgramari).append(" programări\n")
        );
        textAreaResults.setText(rezultat.toString());
    }


    @FXML
    private TextArea textAreaResults;


    public void onDeleteButtonClick(ActionEvent actionEvent) {
        try {
            var pacientId = Integer.parseInt(textFieldId.getText());
            var pacientName = textFieldName.getText();
            var pacientSurname = textFieldSurname.getText();
            var pacientVarsta = Integer.parseInt(textFieldAge.getText());
            Pacient pacient = new Pacient(pacientId, pacientName, pacientSurname, pacientVarsta);
            repository.removePacient(pacientId);
            dataPacienti.remove(pacient);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            textFieldId.clear();
            textFieldName.clear();
            textFieldAge.clear();
            textFieldSurname.clear();
        }
    }

    public void onDeleteButtonClickProgramare(ActionEvent actionEvent) {
        try {
            int programareId = Integer.parseInt(textFieldProgramareId.getText().trim());
            String pacientData = textFieldPacientId.getText().trim(); // Stringul cu datele pacientului
            String scop = textFieldScop.getText().trim();

            // Parsam data și ora
            String dateText = textFieldData.getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dataProgramare = LocalDateTime.parse(dateText, formatter);

            // Prelucram stringul pacientului
            String[] pacientParts = pacientData.split(";");
            if (pacientParts.length != 4) {
                throw new IllegalArgumentException("Format invalid pentru pacient! Trebuie să fie:id;nume;prenume;varsta");
            }
            int pacientid = Integer.parseInt(pacientParts[0].trim());
            String pacientNume = pacientParts[1].trim();
            String pacientPrenume = pacientParts[2].trim();
            int pacientVarsta = Integer.parseInt(pacientParts[3].trim());
            Pacient pacient = new Pacient(pacientid, pacientNume, pacientPrenume, pacientVarsta);

            Programare programare = new Programare(programareId, pacient, dataProgramare, scop);

            // Adaugam in repository si lista locala
            repositoryProgramari.removeProgramare(programareId);
            dataProgramari.remove(programare);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Eroare: " + e.getMessage()).show();
        }
        finally {
            textFieldProgramareId.clear();
            textFieldPacientId.clear();
            textFieldScop.clear();
            textFieldData.clear();
        }
    }

}

