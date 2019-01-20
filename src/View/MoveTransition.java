package View;

import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MoveTransition extends Transition {
    private ImageView imageView;
    private double startX;
    private double startY;
    private double differenceX;
    private double differenceY;

    public MoveTransition(ImageView imageView,
                          double startX, double startY, double endX, double endY, double durationMillis) {
        this.imageView = imageView;
        this.startX = startX;
        this.startY = startY;
        differenceX = endX-startX;
        differenceY= endY-startY;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(durationMillis));
    }

    @Override
    protected void interpolate(double frac) {
        imageView.relocate(frac* differenceX +startX, frac*differenceY+startY);
    }
}
