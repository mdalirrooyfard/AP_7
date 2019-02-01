package Network;

import Model.Constants;
import Model.Farm;
import Model.Player;
import View.Graphic.Menu;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

public class ClientGui extends Application {
    private Market market = null;
    private ClientSender clientSender;
    private Player player;
    private ScrollPane chatArea = new ScrollPane();
    private Pane area = new Pane();
    private Group root = new Group();
    private Scene scene;
    private boolean isOpen;
    private VBox leaderBoard;
    private Farm farm;
    private HashMap<String, Boolean> friendRequests = new HashMap<>();
    private int first = 0;
    private int y = 30;

    public ClientGui(ClientSender clientSender, Player player) {
        this.clientSender = clientSender;
        this.player = player;
        this.isOpen = false;
        area.layout();
        chatArea.setVvalue(1D);
        chatArea.setContent(area);
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
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
        primaryStage.setTitle(player.getName() + " in chatRoom");
        primaryStage.setResizable(false);
        TextField textField = new TextField();
        Image send = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\sendButton.png"));
        ImageView sendView = new ImageView(send);
        sendView.setFitWidth(70);
        sendView.setFitHeight(40);
        Image list = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\listButton.png"));
        ImageView listView = new ImageView(list);
        listView.setFitWidth(70);
        listView.setFitHeight(40);
        Image leaderBoard = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\leaderBoardButton.png"));
        ImageView leaderBoardView = new ImageView(leaderBoard);
        leaderBoardView.setFitWidth(70);
        leaderBoardView.setFitHeight(40);
        HBox hBox = new HBox(20, chatArea);
        HBox hBox1 = new HBox(20, textField, sendView, listView, leaderBoardView);
        VBox vBox = new VBox(20, hBox1, hBox);
        chatArea.setPrefHeight(560);
        chatArea.setPrefWidth(800);
        root.getChildren().add(vBox);
        scene = new Scene(root, 800, 600);
        sendView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String data = textField.getText();
                if (!data.equals("")) {
                    clientSender.sendMessage(player.getName() + ": " + data);
                }
                textField.setText("");
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clientSender.sendListRequest();
            }
        });
        leaderBoardView.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        first = 0;
        checkRequests();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void checkRequests(){
        try {
            Rectangle rectangle = new Rectangle(0, 0, 800, 600);
            rectangle.setFill(Color.rgb(54, 16, 0));
            rectangle.setOpacity(0.7);
            for (String s : friendRequests.keySet()) {
                if (!friendRequests.get(s) && !player.getFriends().contains(s)) {
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
                    Label request = new Label(s + " wants to be friend with you!");
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
            Label request = new Label(userName + " wants to be friend with you!");
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
        Label label = new Label();
        Rectangle rectangle;
        label.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 15));
        TranslateTransition translateTransition , translateTransition1;
        if(message.startsWith(player.getName())){
            String[] strings = message.split(":" , 2);
            message = strings[1].trim();
            rectangle = new Rectangle((message.length()+1)*8,25);
            label.setTranslateX(800 - (message.length()+1)*8);
            translateTransition = new TranslateTransition(Duration.millis(2000) , label);
            translateTransition.setToY(y);
            rectangle.setTranslateX(800 - (message.length()+1)*8);
            translateTransition1 = new TranslateTransition((Duration.millis(2000)) , rectangle);
            translateTransition1.setToY(y);
            label.setText(message);
        }
        else{
            label.setTranslateX(10);
            translateTransition = new TranslateTransition(Duration.millis(2000) , label);
            translateTransition.setToY(y);
            rectangle = new Rectangle((message.length()+1)*8,25);
            rectangle.setTranslateX(10);
            translateTransition1 = new TranslateTransition((Duration.millis(2000)) , rectangle);
            translateTransition1.setToY(y);
            label.setText(message);
        }
        rectangle.setFill(Color.SANDYBROWN);
        rectangle.setOpacity(0.8);
        translateTransition1.play();
        translateTransition.play();
        area.getChildren().addAll(rectangle , label);
        y += 35;
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
                    400, 200 + otherPlayer.getFriends().size() * 50, false, true);
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
            Label[] friends = new Label[otherPlayer.getFriends().size()];
            int i = 0;
            for (String friend : otherPlayer.getFriends()) {
                friends[i] = new Label("friend"+Integer.toString(i+1)+ ": "+ friend);
                friends[i].relocate(250, 350 + (i+1) * 60);
                friends[i].setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR, 20));
                friends[i].setTextFill(Color.rgb(54, 16, 0));
                i ++;
            }
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png"),
                    100, 39, false, true);
            ImageView okView = new ImageView(ok);
            okView.setX(500);
            okView.setY(400 + otherPlayer.getFriends().size() * 50);
            Image privateChat = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\privateChatButton.png"),
                    100, 39, false, true);
            ImageView privateChatView = new ImageView(privateChat);
            privateChatView.setX(350);
            privateChatView.setY(400 + otherPlayer.getFriends().size() * 50);

            Image friendRequest = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\friendRequest.png")
            , 100, 39, false, true);
            ImageView fRequestView = new ImageView(friendRequest);
            fRequestView.setX(220);
            fRequestView.setY(400 + otherPlayer.getFriends().size() * 50);

            fRequestView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!player.getFriends().contains(otherPlayer.getUserName()))
                        clientSender.sendFriendRequest(player.getUserName(), otherPlayer.getUserName());
                    else
                        errorForAleadyBeingFriend();
                }
            });

            privateChatView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    clientSender.sendPrivateChatRequest(otherPlayer.getUserName());
                    root.getChildren().removeAll(friends);
                    root.getChildren().removeAll(messageView, name, userName, level, money, okView, privateChatView, fRequestView);
                }
            });
            okView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    root.getChildren().removeAll(friends);
                    root.getChildren().removeAll(messageView, name, userName, level, money, okView, privateChatView, fRequestView);
                }
            });
            root.getChildren().addAll(messageView, name, userName, level, money, okView, privateChatView, fRequestView);
            root.getChildren().addAll(friends);
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

    private void errorForAleadyBeingFriend()
    {
        try
        {
            Rectangle rectangle = new Rectangle(0,0, Menu.WIDTH,Menu.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);
            Image error1 = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\error4.png")
                    , 800, 300, false, true);
            ImageView error1View = new ImageView(error1);
            error1View.setY(Menu.HEIGHT / 2 - 150);
            error1View.setX(Menu.WIDTH / 2 - 400);
            Image ok = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\okButton.png")
                    , 200, 79, false, true);
            ImageView okView = new ImageView(ok);
            okView.setY(Menu.HEIGHT / 2 + 150);
            okView.setX(Menu.WIDTH / 2 - 100);
            okView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    root.getChildren().removeAll(rectangle,error1View,okView);
                }
            });
            root.getChildren().addAll(rectangle,error1View,okView);
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }
}
