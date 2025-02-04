package org.mst;

import org.mst.SaveManager.PlayerManager;
import org.mst.SaveManager.SaveManager;
import org.mst.SaveManager.Utils.NBTController;
import org.mst.SaveManager.WorldManager;

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
    public static String transfer(String origin, String target, String outputRootPath) throws IOException {
        WorldManager wm = new WorldManager(origin);
        PlayerManager pm = new PlayerManager(target);
        wm.setPlayerFrom(pm);
        return NBTController.autosave("level.dat", wm.getData(), outputRootPath);
    }

    public static String transfer(SaveManager origin, SaveManager target, String outputRootPath) throws IOException {
        origin.setPlayerFrom(target);
        return NBTController.autosave(origin.getFileName(), origin.getData(), outputRootPath);
    }
}