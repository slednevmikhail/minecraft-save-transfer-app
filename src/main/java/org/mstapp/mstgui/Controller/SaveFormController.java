package org.mstapp.mstgui.Controller;

import org.mst.SaveManager.PlayerManager;
import org.mst.SaveManager.SaveManager;
import org.mst.SaveManager.WorldManager;

import java.io.IOException;
import java.util.Objects;
/**
 * Controller for ("Choose file") panes.
 * @implNote Controls elements with fx:id = "ContentPane"
 */
public class SaveFormController {
    /**
     * Path to NBT file
     */
    private SaveManager saveManager;

    private String filePath;
    public String getFilePath(){
        return filePath == null ? null : filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    /**
     * Type of file based on the current active value of two radio buttons in GUI <br>
     * @implNote There are two main file types, the Player.dat and Level.dat, both have different structure
     * and need to be handled differently
     * @see file_types#WORLD
     * @see file_types#PLAYER
     */
    private file_types fileType;

    /**
     * Player ID in the .dat file. <br>
     *
     * @implSpec Can be initialized only after {@code fileType} and {@code filePath} are given. <br>
     * @implNote After any change of {@code fileType} or {@code filePath}, {@code this.updateUUID} should be called
     */
    private String UUID = null;
    SaveFormController(file_types defaultType) {
        filePath = null;
        fileType = defaultType;
    }

    public file_types getFileType() {
        return fileType;
    }

    public void setFileType(boolean typeToggle) {
        fileType = typeToggle ? file_types.WORLD : file_types.PLAYER;
    }

    public void switchType(){
        switch (fileType) {
            case WORLD -> fileType = file_types.PLAYER;
            case PLAYER -> fileType = file_types.WORLD;
        }
    }

    public String getUUID() {
        return UUID;
    }

    private void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public void updateUUID() throws IOException {
        if (Objects.isNull(filePath)){
            return;
        }
        switch (fileType){
            case file_types.PLAYER -> saveManager = new PlayerManager(filePath);
            case file_types.WORLD -> saveManager = new WorldManager(filePath);
        }
        try{
            setUUID(saveManager.getPlayerIdString());
        } catch (Exception e){
            setUUID(null);
        }
        System.out.println(UUID);
    }
}
