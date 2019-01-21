package View.Graphic;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Options
{
    private Group group = new Group();
    private Scene scene = new Scene(group, Menu.WIDTH, Menu.HEIGHT);
    private Stage stage;
    private Menu menu;

    public Scene getScene()
    {
        stage.setFullScreen(menu.isFullScreen());
        return scene;
    }

    public Options(Stage stage , Menu menu)
    {
        this.stage = stage;
        this.menu = menu;
        try
        {
            Image background = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\background.png")
                    , Menu.WIDTH, Menu.HEIGHT, false, true);
            ImageView backgroundView = new ImageView(background);
            backgroundView.setY(0);
            backgroundView.setX(0);

            Rectangle rectangle = new Rectangle(0,0,Menu.WIDTH,Menu.HEIGHT);
            rectangle.setFill(Color.rgb(54,16,0));
            rectangle.setOpacity(0.7);

            Image menuBackground = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\menuBackground.png")
                    , 550, Menu.HEIGHT, false, true);
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setY(0);
            menuBackgroundView.setX(Menu.WIDTH - 550);
            group.getChildren().addAll(backgroundView,rectangle,menuBackgroundView);

            insertSoundOption();
            insertMusicOption();
            insertFullScreenOption();
            insertBack();
        }
        catch ( Exception e ){}
    }

    private void insertBack()
    {
        try
        {
            Image back = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\backButton.png")
                    , 84, 79, false, true);
            ImageView backView = new ImageView(back);
            backView.setY(0);
            backView.setY(Menu.HEIGHT - 150);
            backView.setX(Menu.WIDTH - 500);
            group.getChildren().addAll(backView);
            backView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    stage.setScene(menu.getScene());
                }
            });
        }
        catch ( Exception e ){}
    }

    private void insertSoundOption()
    {
        Label sound = new Label("Sound On/Off : ");
        sound.setLayoutY(Menu.HEIGHT / 4);
        sound.setLayoutX(Menu.WIDTH - 400);
        sound.setTextFill(Color.rgb(54,16,0));
        sound.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,25));
        try
        {
            Image soundIconMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\muteSoundButton.png")
                    , 102, 98, false, true);
            Image soundIconUnMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\soundButton.png")
                    , 102, 98, false, true);
            ImageView soundIconView;
            if( menu.isSoundMuted() )
                soundIconView = new ImageView(soundIconMute);
            else
                soundIconView = new ImageView(soundIconUnMute);
            soundIconView.setY(Menu.HEIGHT / 4);
            soundIconView.setX(Menu.WIDTH - 198);
            soundIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    //TODO really mute sound!
                    if( menu.isSoundMuted() )
                    {
                        menu.setMuteSound(false);
                        soundIconView.setImage(soundIconUnMute);
                    }
                    else
                    {
                        menu.setMuteSound(true);
                        soundIconView.setImage(soundIconMute);
                    }
                }
            });
            group.getChildren().addAll(sound,soundIconView);
        }
        catch ( Exception e ){}
    }

    private void insertMusicOption()
    {
        Label music = new Label("Music On/Off : ");
        music.setLayoutY(Menu.HEIGHT / 2);
        music.setLayoutX(Menu.WIDTH - 400);
        music.setTextFill(Color.rgb(54,16,0));
        music.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,25));
        try
        {
            Image musicIconMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\musicMuteButton.png")
                    , 102, 98, false, true);
            Image musicIconUnMute = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\musicButton.png")
                    , 102, 98, false, true);
            ImageView musicIconView;
            if( menu.isMusicMuted() )
                musicIconView = new ImageView(musicIconMute);
            else
                musicIconView = new ImageView(musicIconUnMute);
            musicIconView.setY(Menu.HEIGHT / 2);
            musicIconView.setX(Menu.WIDTH - 198);
            musicIconView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( menu.isMusicMuted() )
                    {
                        musicIconView.setImage(musicIconUnMute);
                        menu.getMediaPlayer().play();
                        menu.setMuteMusic(false);
                    }
                    else
                    {
                        musicIconView.setImage(musicIconMute);
                        menu.getMediaPlayer().stop();
                        menu.setMuteMusic(true);
                    }
                }
            });
            group.getChildren().addAll(music,musicIconView);
        }
        catch ( Exception e ){}
    }

    private void insertFullScreenOption()
    {
        //TODO fullScreen still has problem!
        Label fullScreen = new Label("Full Screen On/Off : ");
        fullScreen.setLayoutY(Menu.HEIGHT * 3 / 4);
        fullScreen.setLayoutX(Menu.WIDTH - 400);
        fullScreen.setTextFill(Color.rgb(54,16,0));
        fullScreen.setFont(Font.font("Segoe Print", FontWeight.BOLD, FontPosture.REGULAR,25));
        try
        {
            Image uncheckedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxFalse.png")
                    , 25, 25, false, true);
            Image checkedCheckBox = new Image(new FileInputStream("src\\Resources\\Graphic\\Game UI\\checkBoxTrue.png")
                    , 25, 25, false, true);
            ImageView fullScreenView;
            if( menu.isFullScreen() )
                fullScreenView = new ImageView(checkedCheckBox);
            else
                fullScreenView = new ImageView(uncheckedCheckBox);
            fullScreenView.setY(Menu.HEIGHT * 3 / 4 + 10);
            fullScreenView.setX(Menu.WIDTH - 135);
            fullScreenView.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    if( menu.isFullScreen() )
                    {
                        fullScreenView.setImage(uncheckedCheckBox);
                        stage.setFullScreen(false);
                        menu.setFullScreen(false);
                    }
                    else
                    {
                        fullScreenView.setImage(checkedCheckBox);
                        stage.setFullScreen(true);
                        menu.setFullScreen(true);
                    }
                }
            });
            group.getChildren().addAll(fullScreen,fullScreenView);
        }
        catch ( Exception e ){}
    }

}
