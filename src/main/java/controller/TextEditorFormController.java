package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;

public class TextEditorFormController {
    public HTMLEditor txtEditor;
    public MenuItem mnClose;
    public MenuItem mnuNew;
    public MenuItem mnuOpen;
    public MenuItem mnuSave;
    public MenuItem mnuPrint;
    public MenuItem mnuCut;
    public MenuItem mnuCopy;
    public MenuItem mnuPaste;
    public MenuItem mnuSelectAll;
    public MenuItem mnuAbout;

    private File file1;

    public void initialize() {
        mnuAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                URL resource = this.getClass().getResource("/view/AboutForm.fxml");
                try {
                    Parent load = FXMLLoader.load(resource);
                    Scene scene = new Scene(load);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        mnuNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtEditor.setHtmlText("");
            }
        });
        mnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit(); // exit from javafx run time env
            }
        });

        mnuOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fc = new FileChooser();
                File file = new File("/home/nipunija/Desktop");
                fc.setInitialDirectory(file);
                file1 = fc.showOpenDialog(txtEditor.getScene().getWindow());
                try {
                    FileInputStream fis = new FileInputStream(file1);
                    BufferedInputStream bfis = new BufferedInputStream(fis);

                    char[] chars = new char[(int) file1.length()];
                    for (int i = 0; i < file1.length(); i++) {
                        chars[i]= (char) bfis.read();
                    }
                    txtEditor.setHtmlText(String.valueOf(chars));
                    bfis.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        mnuSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String htmlText = txtEditor.getHtmlText();
                System.out.println(htmlText);
                char[] chars = htmlText.toCharArray();
                byte[] bytes = new byte[chars.length];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i]= (byte) chars[i];
                }

                try {
                    FileOutputStream fos = new FileOutputStream(file1);
                    fos.write(bytes);

                    fos.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        mnuPrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (Printer.defaultPrinterProperty()==null){
                    new Alert(Alert.AlertType.ERROR,"no default printer").show();
                    return;
                }
                PrinterJob printerJob = PrinterJob.createPrinterJob();

                if (printerJob!=null){
                    printerJob.showPageSetupDialog(txtEditor.getScene().getWindow());
                    boolean b = printerJob.printPage(txtEditor);
                    if (b){
                        printerJob.endJob();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"failed to print").show();
                    }

                }else {
                    new Alert(Alert.AlertType.ERROR,"didn't create a print job").show();
                }

            }
        });

    }
}
