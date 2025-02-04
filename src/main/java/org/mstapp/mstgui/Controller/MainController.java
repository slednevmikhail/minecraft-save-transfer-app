package org.mstapp.mstgui.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.mst.SaveTransfer;

import java.io.File;
import java.io.IOException;
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
    public String OutputPath=System.getProperty("user.dir");

    public Button chooseTargetFile;
    public Label TargetIdText;

    public TextField mainPathField;
    public TextField savePathField;
    public TextField targetPathField;

    public Button showFolderButton;
    public Button transferButton;
    // --------[ File View UI ] ---------------------------------------
    private File chooseFile(String title, SaveFormController saveController){
        FileChooser mainFileChooser = new FileChooser();
        if (saveController.getFilePath() != null) {
            mainFileChooser.setInitialDirectory(new File(saveController.getFilePath()));
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
    private SaveFormController MainSaveController = new SaveFormController(file_types.WORLD);

    public void mainTypeSwitch(ActionEvent actionEvent) throws IOException {
        MainSaveController.switchType();
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
    private SaveFormController TargetSaveController = new SaveFormController(file_types.PLAYER);

    public void targetTypeSwitch(ActionEvent actionEvent) throws IOException {
        TargetSaveController.switchType();
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
        }
        else{
            label.setText("ERROR!");
        }
    }

    //-----------------------------------------------------------------

    //-------------[Save Control pane]--------------------------------
    public void onEditSaveButtonClick(ActionEvent actionEvent) throws IOException {
        File dest = chooseFolder("Choose output path", MainSaveController);
        if (Objects.isNull(dest)){
            return;
        }
        OutputPath = dest.getAbsolutePath();
        savePathField.setText(OutputPath + "\\Output");
        showFolderButton.setDisable(true);
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
        SaveTransfer.transfer(MainFilePath, TargetFilePath, OutputPath);
        moveProgressBar();
        moveProgressBar();
        moveProgressBar();
        moveProgressBar();
        transferButton.setDisable(true);
    }

    private void controlTransferButton(){
        transferButton.setDisable(MainSaveController.getUUID() == null || TargetSaveController.getUUID() == null);
    }

    private void moveProgressBar() throws InterruptedException {
        double progress = transferProgress.getProgress();
        transferProgress.setProgress(progress + 0.25);
    }


    public void showFolderClick(ActionEvent actionEvent) throws IOException {
        openInExplorer(OutputPath);
    }
    private void openInExplorer(String path) throws IOException {
        Runtime.getRuntime().exec("explorer.exe /select, " + path + File.separator + "Output");
    }


}