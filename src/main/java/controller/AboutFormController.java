package controller;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class AboutFormController {

    public AnchorPane ctrAbout;

    public void initialize(){
        RotateTransition rt = new RotateTransition(Duration.millis(750), ctrAbout);
        rt.setFromAngle(0);
        rt.setToAngle(270);
        rt.playFromStart();
//
//        TranslateTransition tt = new TranslateTransition(Duration.millis(500), imgRose);
//        tt.setFromX(-500);
//        tt.setToX(50);
//        tt.playFromStart();
//
//        FadeTransition ft = new FadeTransition(Duration.millis(1500), ctrAbout);
//        ft.setFromValue(0);
//        ft.setToValue(1);
//        ft.playFromStart();
//
//        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), imgRose);
//        scaleTransition.setFromX(0);
//        scaleTransition.setFromY(0);
//        scaleTransition.setToX(1);
//        scaleTransition.setToY(1);
//        scaleTransition.playFromStart();
    }
}
