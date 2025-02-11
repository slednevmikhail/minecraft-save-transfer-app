package org.mst;

import org.mst.SaveManager.PlayerManager;
import org.mst.SaveManager.Utils.NBTController;
import org.mst.SaveManager.WorldManager;
import org.mstapp.mstgui.Controller.file_types;

import java.io.IOException;
import java.time.Instant;

public class SaveTransfer {



    /**
     * Replaces the {@code Player} tag inside the world NBT file with data from the player's file,
     * effectively transferring the player's save into the world file.
     * <p>
     * As a result, a new world save file is automatically created at: <br>
     *  {@code outputRootPath}/Output/{@link Instant#now()}/level.dat.
     * </p>
     *
     * The absolute path to the world save file (e.g., {@code D:\level.dat}).
     * The absolute path to the player save file (e.g., {@code D:\3928349ddf9ds9s9ddf.dat}).
     * @return The path to the output file.
     * @throws IOException If an error occurs during file processing.
     */
    public static String transfer(String to, file_types to_type, String from, file_types from_type, String backupName) throws IOException {
        String outputRootPath = System.getProperty("user.dir");
        if (to_type == file_types.WORLD) {
            WorldManager to_wm = new WorldManager(to);
            if (from_type == file_types.WORLD) {
                WorldManager from_wm = new WorldManager(from);
                to_wm.setPlayerFrom(from_wm);
            }
            else {
                PlayerManager from_pm = new PlayerManager(from);
                to_wm.setPlayerFrom(from_pm);
            }
            NBTController.backup(to_wm.getFileName(), to_wm.getData(), outputRootPath, backupName);
            return NBTController.save(to_wm.getFile_path(), to_wm.getData());
        }
        else {
            PlayerManager to_pm = new PlayerManager(to);
            if (from_type == file_types.WORLD) {
                WorldManager from_wm = new WorldManager(from);
                to_pm.setPlayerFrom(from_wm);
            }
            else {
                PlayerManager from_pm = new PlayerManager(from);
                to_pm.setPlayerFrom(from_pm);
            }

            NBTController.backup(to_pm.getFileName(), to_pm.getData(), outputRootPath, backupName, "playerdata");
            return NBTController.save(to_pm.getFile_path(), to_pm.getData());
        }
    }
}