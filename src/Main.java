import Controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    private Controller controller = new Controller();
    public static void main(String[] args) {
        launch(args);
        //Scanner scanner = new Scanner(System.in);
        //controller.getCommand(scanner);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group menuRoot = new Group();
        Scene menu = new Scene(menuRoot, 800, 600);
        primaryStage.setScene(menu);
        Label label = new Label("Farm Frenzy!");
        label.setFont(new Font(40));
        label.relocate(300, 50);
        menuRoot.getChildren().add(label);
        Button player = new Button("New Player");
        Button start = new Button("Start");
        Button options = new Button("Options");
        Button exit = new Button("Exit");
        menuRoot.getChildren().addAll(player, start, options, exit);
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("The game is closing...");
                alert.setContentText("Are you sure???");
                ButtonType b1 = new ButtonType("Yes");
                ButtonType b2 = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(b1, b2);
                alert.showAndWait().ifPresent(result -> {
                    if (result == b1)
                        primaryStage.close();
                });
            }
        });
        primaryStage.setTitle("Farm Frenzy");
        primaryStage.show();
    }
}
