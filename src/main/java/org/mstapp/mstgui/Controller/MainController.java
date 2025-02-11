package org.mstapp.mstgui.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.mst.SaveTransfer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class MainController {
    // ------------- Main file panel
    // ------------- Radio box with two main type toggle buttons (file type: world -> player)
    public ToggleGroup mainTypeBox;

    public Button chooseMainFile;
    public Label mainIdText;
    public Pane mainPane;
    public ToggleGroup targetTypeBox;
    public ProgressBar transferProgress;

    public Button chooseTargetFile;
    public Label TargetIdText;


    public TextField mainPathField;
    public TextField savePathField;
    public TextField targetPathField;

    public Button showFolderButton;
    public Button transferButton;
    public RadioButton type_target_player;
    public RadioButton type_target_world;
    public RadioButton type_main_player;
    public RadioButton type_main_world;

    // --------[ File View UI ] ---------------------------------------
    private File chooseFile(String title, SaveFormController saveController){
        FileChooser mainFileChooser = new FileChooser();
        String filePath = saveController.getFilePath();
        if (filePath != null) {
            mainFileChooser.setInitialDirectory(getInitialDir(filePath));
        }
        else {
            mainFileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        mainFileChooser.setTitle(title);
        mainFileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Dat files", "*.dat")
        );
        return mainFileChooser.showOpenDialog(null);
    }

    private File getInitialDir(String filePath){
        StringBuilder builder = new StringBuilder(filePath);
        int lastIndex = builder.lastIndexOf("\\");
        builder.delete(lastIndex + 1, builder.length());
        return new File(builder.toString());
    }

    private File chooseFolder(String title, SaveFormController saveController){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (saveController.getFilePath() != null) {
            directoryChooser.setInitialDirectory(new File(saveController.getFilePath()));
        }
        else {
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        directoryChooser.setInitialDirectory(new File(saveController.getFilePath()));
        directoryChooser.setTitle(title);
        return directoryChooser.showDialog(null);
    }
    // ----------------------------------------------------------------

    // ---------[ MAIN Control pane event holder ]---------------------
    private final SaveFormController MainSaveController = new SaveFormController(file_types.WORLD);

    public void mainTypeSwitch(ActionEvent actionEvent) throws IOException {
        MainSaveController.switchType();
        switch (MainSaveController.getFileType()){
            case WORLD: {
                type_main_player.setTextFill(Color.rgb(255, 210, 161));
                type_main_world.setTextFill(Color.WHITE);
                break;
            }
            case PLAYER: {
                type_main_world.setTextFill(Color.rgb(255, 210, 161));
                type_main_player.setTextFill(Color.WHITE);
                break;
            }
        }
        System.out.println("{MainFile - " + MainSaveController.getFileType() + "}");
        UUIDUpdate(MainSaveController, mainIdText);
        controlTransferButton();
    }


    public void mainChooseClick(ActionEvent actionEvent) throws IOException {
        File file = chooseFile("Select Main File", MainSaveController);
        if (Objects.isNull(file)){
            return;
        }
        String file_path = file.getAbsolutePath();
        MainSaveController.setFilePath(file_path);
        mainPathField.setText(file_path);
        UUIDUpdate(MainSaveController, mainIdText);
        controlTransferButton();
    }
    //-----------------------------------------------------------------

    //---------[ TARGET Control pane event holder ]--------------------
    private final SaveFormController TargetSaveController = new SaveFormController(file_types.WORLD);

    public void targetTypeSwitch(ActionEvent actionEvent) throws IOException {
        TargetSaveController.switchType();
        switch (TargetSaveController.getFileType()){
            case WORLD: {
                type_target_player.setTextFill(Color.rgb(255, 210, 161));
                type_target_world.setTextFill(Color.WHITE);
                break;
            }
            case PLAYER: {
                type_target_world.setTextFill(Color.rgb(255, 210, 161));
                type_target_player.setTextFill(Color.WHITE);
                break;
            }
        }
        System.out.println("{TargetFile - " + TargetSaveController.getFileType() + "}");
        UUIDUpdate(TargetSaveController, TargetIdText);
        controlTransferButton();
    }

    public void targetChooseClick(ActionEvent actionEvent) throws IOException {
        File file = chooseFile("Select Target File", TargetSaveController);
        if (Objects.isNull(file)){
            return;
        }
        String file_path = file.getAbsolutePath();
        TargetSaveController.setFilePath(file_path);
        targetPathField.setText(file_path);
        UUIDUpdate(TargetSaveController, TargetIdText);
        controlTransferButton();
    }

    //---------- [UUID Updater]---------------------------------------
    private void UUIDUpdate(SaveFormController SaveController, Label label) throws IOException {
        SaveController.updateUUID();
        if (!Objects.isNull(SaveController.getUUID())) {
            label.setText("Player UUID: " + SaveController.getUUID());
            label.setTextFill(Color.LIGHTGREEN);
        }
        else{
            label.setTextFill(Color.RED);
            label.setText("ERROR!");
        }
    }

    //-----------------------------------------------------------------

    //-------------[Save Control pane]--------------------------------
    public void onEditSaveButtonClick(ActionEvent actionEvent) throws IOException {
    }
    //----------------------------------------------------------------

    public void transferButtonClick(ActionEvent actionEvent) throws IOException, InterruptedException {
        mainPane.setDisable(true);
        transferProgress.setDisable(false);
        transferProgress.setProgress(0.0);
        transfer();
        mainPane.setDisable(false);
        showFolderButton.setDisable(false);
    }

    private void transfer() throws IOException, InterruptedException {
        String MainFilePath = MainSaveController.getFilePath();
        String TargetFilePath = TargetSaveController.getFilePath();
        if (Objects.isNull(MainFilePath) || Objects.isNull(TargetFilePath)){
            mainPane.setDisable(false);
            return;
        }
        SaveTransfer.transfer
                (MainFilePath, MainSaveController.getFileType(),
                        TargetFilePath,TargetSaveController.getFileType(), savePathField.getText());

        moveProgressBar();
        moveProgressBar();
        moveProgressBar();
        moveProgressBar();
        transferButton.setDisable(true);
    }

    private void controlTransferButton(){
        transferButton.setDisable(MainSaveController.getUUID() == null);
    }

    private void moveProgressBar() throws InterruptedException {
        double progress = transferProgress.getProgress();
        transferProgress.setProgress(progress + 0.25);
    }

    public void showOutputClick(ActionEvent actionEvent) throws IOException{

        Desktop.getDesktop().open(new File(System.getProperty("user.dir") + File.separator + "Output"));
    }

    public void OnGithubButtonClick(ActionEvent actionEvent) throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI("https://github.com/slednevmikhail"));
    }



}