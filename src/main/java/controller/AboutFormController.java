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

        FadeTransition ft = new FadeTransition(Duration.millis(1500), ctrAbout);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.playFromStart();


    }
}
