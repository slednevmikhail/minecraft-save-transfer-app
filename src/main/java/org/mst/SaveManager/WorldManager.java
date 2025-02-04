package org.mst.SaveManager;

import org.jnbt.CompoundTag;
import org.jnbt.IntArrayTag;
import org.mst.SaveManager.Utils.NBTTree;

import java.io.IOException;

public class WorldManager extends SaveManager {

    public WorldManager(String file_path) throws IOException {
        super(file_path);
    }

    @Override
    public IntArrayTag getPlayerId(){
        return  ((IntArrayTag) data.findTag(new String[]{"Data", "Player", "UUID"}));
    }

    @Override
    public void setPlayerFrom(SaveManager sm){
        NBTTree playerData = sm.getData();
        playerData.renameRoot("Player");
        data.replaceTagData(new String[]{"Data", "Player"}, playerData.getRoot());
    }

    @Override
    public NBTTree getPlayerData(){
        CompoundTag playerDataRoot = (CompoundTag) data.findTag(new String[]{"Data", "Player"});
        NBTTree playerData = new NBTTree(playerDataRoot);
        playerData.renameRoot("");
        return playerData;
    }

}
