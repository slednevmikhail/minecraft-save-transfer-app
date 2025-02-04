package org.mst.SaveManager;

import org.jnbt.IntArrayTag;
import org.mst.SaveManager.Utils.NBTTree;

import java.io.IOException;

public class PlayerManager extends SaveManager {

    public PlayerManager(String file_path) throws IOException {
        super(file_path);
    }
    @Override
    public IntArrayTag getPlayerId(){
        return
                ( (IntArrayTag) data.findTag(new String[] {"UUID"}));
    }

    @Override
    public NBTTree getPlayerData(){
        return data;
    }

    @Override
    public void setPlayerFrom(SaveManager sm){
        NBTTree otherPlayerData = sm.getPlayerData();
        IntArrayTag thisPlayerUUID = getPlayerId();
        otherPlayerData.replaceTagData(new String[] {"UUID"}, thisPlayerUUID);
        data = otherPlayerData;
    }
}
