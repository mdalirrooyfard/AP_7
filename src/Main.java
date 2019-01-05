import Controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
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
        Label label = new Label("Farm Frenzy!");
        label.setFont(new Font(40));
        label.relocate(300, 50);
        Button player = new Button("New Player");
        player.relocate(20, 450);
        player.setFont(new Font(15));
        Button start = new Button("Start");
        start.relocate(160, 450);
        start.setFont(new Font(15));
        Button options = new Button("Options");
        options.relocate(20, 500);
        options.setFont(new Font(15));
        Button exit = new Button("Exit");
        exit.relocate(160, 500);
        exit.setFont(new Font(15));
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
        Image image = new Image(new FileInputStream("C:\\Users\\mahsa\\Desktop\\farm_frenzi_project\\src\\Images\\cartoonFarm.jpeg")
                , 800, 600, false, true);
        ImageView imageView = new ImageView(image);
        imageView.setY(0);
        imageView.setX(0);
        Scene menu = new Scene(menuRoot, 800, 600);
        menuRoot.getChildren().addAll(imageView, label, player, start, options, exit);
        primaryStage.setScene(menu);
        primaryStage.setTitle("Farm Frenzy");
        primaryStage.show();
    }
}
