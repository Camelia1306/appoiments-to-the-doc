package com.example.first_try_of_fx;

import domain.IdGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.SQLRepository;
import repository.SQLRepositoryProgramari;
import service.ProgramareService;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SQLRepository repository = new SQLRepository();
        SQLRepositoryProgramari repositoryProgramari = new SQLRepositoryProgramari();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        HelloController controller = fxmlLoader.getController();
        controller.init();
        controller.setRepository(repository);
        controller.setRepositoryProgramari(repositoryProgramari);


        IdGenerator idGenerator = new IdGenerator("C:\\Users\\w\\IdeaProjects\\first_try_of_fx\\src\\main\\java\\src\\ID");

        ProgramareService programareService = new ProgramareService(repositoryProgramari, idGenerator);
        controller.setProgramareService(programareService);


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}