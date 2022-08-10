package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.IndexRange;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Arrays;

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
    public TextArea txtNote;

    private File file1;
    private String selectedText;

    private String selectednotchangetext;

    final Clipboard scb = Clipboard.getSystemClipboard();
    public void initialize() {
        txtNote.selectionProperty().addListener(new ChangeListener<IndexRange>() {
            @Override
            public void changed(ObservableValue<? extends IndexRange> observableValue, IndexRange indexRange, IndexRange t1) {
                selectedText = txtNote.getText(t1.getStart(), t1.getEnd());
            }
        });
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
                txtNote.setText("");
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
                File file = new File(System.getProperty("user.home"),"Desktop");
                fc.setInitialDirectory(file);
                file1 = fc.showOpenDialog(txtEditor.getScene().getWindow());
                if (file1==null)return;
                String[] split = file1.getName().split("\\.");

                try {
                    FileInputStream fis = new FileInputStream(file1);
                    BufferedInputStream bfis = new BufferedInputStream(fis);

                    char[] chars = new char[(int) file1.length()];
                    for (int i = 0; i < file1.length(); i++) {
                        if (split[1].equalsIgnoreCase("dep9")){
                            int converted = bfis.read() ^ 0b1111_1111;
                            chars[i]=(char)converted;
                        }else{
                            chars[i]= (char) bfis.read();
                        }

                    }
                    txtNote.setText(String.valueOf(chars));
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

                String htmlText = txtNote.getText();
                char[] chars = htmlText.toCharArray();
                byte[] bytes = new byte[chars.length];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i]= (byte) chars[i];
                }

                try {
                    if (file1==null)return;
                    String name = file1.getName();

                    String[] split = name.split("\\.");
                    String parent = file1.getParent();
                    File file = new File(parent,split[0]+".dep9");
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedOutputStream bfos = new BufferedOutputStream(fos);
                    byte[] buffer=new byte[bytes.length];
                    for (int k = 0; k < bytes.length; k++) {
                        buffer[k] = (byte) (bytes[k] ^ 0b1111_1111);
                    }
                    bfos.write(buffer);
                    bfos.close();
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
                    boolean b = printerJob.printPage(txtNote);
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
        mnuCopy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(selectedText);
                scb.setContent(clipboardContent);
                selectednotchangetext=selectedText;

            }
        });
        mnuPaste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int start = txtNote.getSelection().getStart();
                int end = txtNote.getSelection().getEnd();
                txtNote.replaceText(start,end,selectednotchangetext);
            }
        });
        mnuCut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(selectedText);
                scb.setContent(clipboardContent);
                selectednotchangetext=selectedText;
                int start1 = txtNote.getSelection().getStart();
                int end1 = txtNote.getSelection().getEnd();
                txtNote.replaceText(start1,end1,"");

            }
        });

    }
}
