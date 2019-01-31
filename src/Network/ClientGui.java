package Network;

import Model.Constants;
import Model.Farm;
import Model.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

public class ClientGui extends Application {
    private ClientSender clientSender;
    private Player player;
    private TextArea chatArea = new TextArea();
    private Group root = new Group();
    private Scene scene;
    private boolean isOpen;
    private VBox leaderBoard;
    private Farm farm;
    private HashMap<String, Boolean> friendRequests = new HashMap<>();
    private int first = 0;


    public ClientGui(ClientSender clientSender, Player player) {
        this.clientSender = clientSender;
        this.player = player;
        this.isOpen = false;
    }

    public void addFriendRequest(String userName){
        friendRequests.put(userName, false);
    }

    public boolean getOpen() {
        return isOpen;
    }

    public Player getPlayer() {
        return player;
    }

    public ClientSender getClientSender() {
        return clientSender;
    }

    public void setFarm(Farm farm){
        this.farm = farm;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        isOpen = true;
        first = 0;
        checkRequests();
        primaryStage.setTitle(player.getName() + " in chatRoom");
        primaryStage.setResizable(false);
        TextField textField = new TextField();
        Button send = new Button("send");
        Button list = new Button("list");
        Button leaderBoard = new Button("Leader Board");
        HBox hBox = new HBox(20, chatArea);
        HBox hBox1 = new HBox(20, textField, send, list, leaderBoard);
        VBox vBox = new VBox(20, hBox1, hBox);
        chatArea.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
        chatArea.setWrapText(true);
        chatArea.setMinHeight(560);
        root.getChildren().add(vBox);
        scene = new Scene(root, 800, 600);
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String data = textField.getText();
                if (!data.equals("")) {
                    clientSender.sendMessage(player.getName() + ": " + data);
                }
                textField.setText("");
            }
        });
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clientSender.sendListRequest();
            }
        });
        leaderBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clientSender.sendLeaderBoardRequest();
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Group tempGroup = new Group();
                scene.setRoot(tempGroup);
                isOpen = false;
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void checkRequests(){
        try {
            Rectangle rectangle = new Rectangle(0, 0, 800, 600);
            rectangle.setFill(Color.rgb(54, 16, 0));
            rectangle.setOpacity(0.7);
            for (String s : friendRequests.keySet()) {
                if (!friendRequests.get(s)) {
                    first++;
                    if (first == 1) {
                        root.getChildren().add(rectangle);
                    }
                    Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                            400, 150, false, true);
                    ImageView messageView = new ImageView(message);
                    messageView.setX(200);
                    messageView.setY(200);
                    Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png"),
                            51, 45, false, true);
                    ImageView yesView = new ImageView(yes);
                    Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png"),
                            51, 45, false, true);
                    ImageView noView = new ImageView(no);
                    yesView.setX(500);
                    yesView.setY(340);
                    noView.setX(300);
                    noView.setY(340);
                    Label request = new Label(s + "wants to be friend with you!");
                    Label approve = new Label("Do you accept?");
                    request.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 15));
                    request.setTextFill(Color.rgb(54, 16, 0));
                    request.relocate(230, 230);
                    approve.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 15));
                    approve.setTextFill(Color.rgb(54, 16, 0));
                    approve.relocate(250, 270);

                    yesView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            player.addFriend(s);
                            clientSender.sendApproveRequest( player.getUserName(),s);
                            root.getChildren().removeAll(messageView, yesView, noView, request, approve);
                            if (first == 1)
                                root.getChildren().remove(rectangle);
                            friendRequests.replace(s, true);
                        }
                    });

                    noView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            root.getChildren().removeAll(messageView, yesView, noView, request, approve);
                            if (first == 1)
                                root.getChildren().remove(rectangle);
                            friendRequests.replace(s, true);
                        }
                    });
                    root.getChildren().addAll(messageView, yesView, noView, request, approve);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void checkNewFriendRequest(String userName){
        try{
            Rectangle rectangle = new Rectangle(0, 0, 800, 600);
            rectangle.setFill(Color.rgb(54, 16, 0));
            rectangle.setOpacity(0.7);
            Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                    400, 150, false, true);
            ImageView messageView = new ImageView(message);
            messageView.setX(200);
            messageView.setY(200);
            Image yes = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\YesButton.png"),
                    51, 45, false, true);
            ImageView yesView = new ImageView(yes);
            Image no = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\NoButton.png"),
                    51, 45, false, true);
            ImageView noView = new ImageView(no);
            yesView.setX(500);
            yesView.setY(340);
            noView.setX(300);
            noView.setY(340);
            Label request = new Label(userName + "wants to be friend with you!");
            Label approve = new Label("Do you accept?");
            request.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 15));
            request.setTextFill(Color.rgb(54, 16, 0));
            request.relocate(230, 230);
            approve.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 15));
            approve.setTextFill(Color.rgb(54, 16, 0));
            approve.relocate(250, 270);

            yesView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    player.addFriend(userName);
                    clientSender.sendApproveRequest(player.getUserName(), userName);
                    root.getChildren().removeAll(messageView, yesView, noView, request, approve);
                    if (first == 1)
                        root.getChildren().remove(rectangle);
                }
            });

            noView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().removeAll(messageView, yesView, noView, request, approve);
                    if (first == 1)
                        root.getChildren().remove(rectangle);
                }
            });
            root.getChildren().addAll(messageView, yesView, noView, request, approve);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void putInCharArea(String message) {
        chatArea.appendText(message + "\n");
    }

    public void playerJoinedMessage(String userName) {
        if (!userName.equals(player.getUserName()) && isOpen) {
            try {
                Rectangle rectangle = new Rectangle(0, 0, 800, 600);
                rectangle.setFill(Color.rgb(54, 16, 0));
                rectangle.setOpacity(0.7);
                Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                        400, 150, false, true);
                ImageView messageView = new ImageView(message);
                messageView.setX(200);
                messageView.setY(200);
                Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                        100, 39, false, true);
                ImageView okView = new ImageView(ok);
                okView.setY(350);
                okView.setX(450);
                Image profile = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\viewProfileButton.png"),
                        100, 39, false, true);
                ImageView profileView = new ImageView(profile);
                profileView.setY(350);
                profileView.setX(250);
                Label label = new Label(userName + " had joined the chatRoom!");
                label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 15));
                label.setTextFill(Color.rgb(54, 16, 0));
                label.relocate(235, 250);
                okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        root.getChildren().removeAll(rectangle, messageView, okView, profileView, label);
                    }
                });
                profileView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        clientSender.sendViewProfileRequest(userName);
                    }
                });

                root.getChildren().addAll(rectangle, messageView, okView, profileView, label);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public void showProfile(Player otherPlayer) {
        try {
            Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                    400, 200, false, true);
            ImageView messageView = new ImageView(message);
            messageView.setX(200);
            messageView.setY(200);
            Label name = new Label("Name : " + otherPlayer.getName());
            Label userName = new Label("Username : " + otherPlayer.getUserName());
            Label level = new Label("Level : " + Integer.toString(otherPlayer.getLastLevel()));
            Label money = new Label("Money : " + Integer.toString(otherPlayer.getMoney()));
            name.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            userName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            level.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            money.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            name.relocate(250, 230);
            userName.relocate(250, 270);
            level.relocate(250, 310);
            money.relocate(250, 350);
            name.setTextFill(Color.rgb(54, 16, 0));
            userName.setTextFill(Color.rgb(54, 16, 0));
            level.setTextFill(Color.rgb(54, 16, 0));
            money.setTextFill(Color.rgb(54, 16, 0));
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 39, false, true);
            ImageView okView = new ImageView(ok);
            okView.setX(500);
            okView.setY(370);
            Image privateChat = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\privateChatButton.png"),
                    100, 39, false, true);
            ImageView privateChatView = new ImageView(privateChat);
            privateChatView.setX(350);
            privateChatView.setY(370);
            privateChatView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    clientSender.sendPrivateChatRequest(otherPlayer.getUserName());
                    root.getChildren().removeAll(messageView, name, userName, level, money, okView, privateChatView);
                }
            });
            okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().removeAll(messageView, name, userName, level, money, okView, privateChatView);
                }
            });

            root.getChildren().addAll(messageView, name, userName, level, money, okView, privateChatView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showList(String list, int numberOfLines) {
        try {
            Rectangle rectangle = new Rectangle(0, 0, 800, 600);
            rectangle.setFill(Color.rgb(54, 16, 0));
            rectangle.setOpacity(0.7);
            Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                    400, (numberOfLines + 1) * 50, false, true);
            ImageView messageView = new ImageView(message);
            messageView.setX(200);
            messageView.setY(Constants.HEIGHT / 2 - numberOfLines * 25);
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 39, false, true);
            ImageView okView = new ImageView(ok);
            okView.setY(messageView.getY() + numberOfLines * 50 + 20);
            okView.setX(450);
            TextField someOne = new TextField();
            someOne.relocate(310, messageView.getY() + numberOfLines * 50 - 10);
            Image profile = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\viewProfileButton.png"),
                    100, 39, false, true);
            ImageView profileView = new ImageView(profile);
            profileView.setY(messageView.getY() + numberOfLines * 50 + 20);
            profileView.setX(335);
            Image sendWild = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\wildAnimalButton.png"),
                    100, 39, false, true);
            ImageView sendWildView = new ImageView(sendWild);
            sendWildView.setY(messageView.getY() + numberOfLines * 50 + 20);
            sendWildView.setX(220);

            profileView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!someOne.getText().equals("")){
                        if (list.contains(someOne.getText())){
                            clientSender.sendViewProfileRequest(someOne.getText());
                            someOne.setText("");
                        }
                        else{
                            notSuchUserNameError(someOne.getText());
                            someOne.setText("");
                        }
                    }
                }
            });

            sendWildView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!someOne.getText().equals("")){
                        if (list.contains(someOne.getText())){
                            clientSender.sendWildAnimal(someOne.getText());
                            someOne.setText("");
                        }
                        else{
                            notSuchUserNameError(someOne.getText());
                            someOne.setText("");
                        }
                    }
                }
            });
            Label names = new Label(list);
            names.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            names.setTextFill(Color.rgb(54, 16, 0));
            names.relocate(250, messageView.getY());
            okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().removeAll(rectangle, messageView, names, okView, someOne, profileView, sendWildView);
                }
            });
            root.getChildren().addAll(rectangle, messageView, names, okView, someOne, profileView, sendWildView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notSuchUserNameError(String userName){
        try {
            Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                    400, 150, false, true);
            ImageView messageView = new ImageView(message);
            messageView.setX(200);
            messageView.setY(200);
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 39, false, true);
            ImageView okView = new ImageView(ok);
            okView.setY(350);
            okView.setX(450);
            Label label = new Label("Username "+userName + " is not available now!");
            label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 15));
            label.setTextFill(Color.rgb(54, 16, 0));
            label.relocate(240, 250);
            okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().removeAll(messageView, okView, label);
                }
            });
            root.getChildren().addAll(messageView, okView, label);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void showLeaderBoard(Vector<Player> players, int numberOfPeople){
        try{
            Rectangle rectangle = new Rectangle(0, 0, 800, 600);
            rectangle.setFill(Color.rgb(54, 16, 0));
            rectangle.setOpacity(0.7);
            Image message = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\messageBox.png"),
                    500, (numberOfPeople + 1) * 50, false, true);
            ImageView messageView = new ImageView(message);
            messageView.setX(150);
            messageView.setY(Constants.HEIGHT / 2 - numberOfPeople * 45);
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 39, false, true);
            ImageView okView = new ImageView(ok);
            okView.setY(messageView.getY() + numberOfPeople * 50 + 50);
            okView.setX(450);
            leaderBoard = makeLeaderBoard(players, numberOfPeople);
            leaderBoard.relocate( 220,Constants.HEIGHT / 2 - numberOfPeople * 45);
            Image levelSort = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\levelSortButton.png"),
                    100, 39, false, true);
            ImageView levelSortView = new ImageView(levelSort);
            levelSortView.relocate(320,messageView.getY() + numberOfPeople * 50 + 50);

            Image moneySort = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\moneySortButton.png"),
                    100, 39, false, true);
            ImageView moneySortView = new ImageView(moneySort);
            moneySortView.relocate( 210,messageView.getY() + numberOfPeople * 50 + 50);

            okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().removeAll(rectangle, messageView, okView, leaderBoard, levelSortView, moneySortView);
                }
            });

            levelSortView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().remove(leaderBoard);
                    Collections.sort(players);
                    leaderBoard = makeLeaderBoard(players, numberOfPeople);
                    leaderBoard.relocate( 220,Constants.HEIGHT / 2 - numberOfPeople * 45);
                    root.getChildren().add(leaderBoard);
                }
            });

            moneySortView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().remove(leaderBoard);
                    players.sort(new Comparator<Player>() {
                        @Override
                        public int compare(Player o1, Player o2) {
                            return o1.getMoney() - o2.getMoney();
                        }
                    });
                    leaderBoard = makeLeaderBoard(players, numberOfPeople);
                    leaderBoard.relocate( 220,Constants.HEIGHT / 2 - numberOfPeople * 45);
                    root.getChildren().add(leaderBoard);
                }
            });
            root.getChildren().addAll(rectangle, messageView, okView, leaderBoard, levelSortView, moneySortView);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static VBox makeLeaderBoard(Vector<Player> players, int numberOfPeople){
        Label name = new Label("username");
        Label levelTitle = new Label("level");
        Label moneyTitle = new Label("money");
        name.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
        name.setTextFill(Color.rgb(54, 16, 0));
        levelTitle.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
        levelTitle.setTextFill(Color.rgb(54, 16, 0));
        moneyTitle.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
        moneyTitle.setTextFill(Color.rgb(54, 16, 0));
        HBox[] rows = new HBox[numberOfPeople + 1];
        rows[0] = new HBox(70, name, levelTitle, moneyTitle);
        for (int i = 0; i < numberOfPeople; i++) {
            Label userName = new Label(players.get(i).getUserName());
            Label level = new Label(Integer.toString(players.get(i).getLastLevel()));
            Label money = new Label(Integer.toString(players.get(i).getMoney()));
            userName.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            userName.setTextFill(Color.rgb(54, 16, 0));
            level.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            level.setTextFill(Color.rgb(54, 16, 0));
            money.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
            money.setTextFill(Color.rgb(54, 16, 0));
            rows[i + 1] = new HBox(100,userName, level, money);
        }
        VBox result = new VBox(rows);
        return result;
    }

    public void sendWildAnimal(){
        farm.sendExtraWildAnimal();
    }

}
