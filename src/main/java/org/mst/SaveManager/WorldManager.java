package org.mst.SaveManager;

import org.jnbt.CompoundTag;
import org.jnbt.IntArrayTag;
import org.jnbt.ListTag;
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
    public void setPlayerFrom(SaveManager other){
        NBTTree otherPlayerData = other.getPlayerData();
        NBTTree thisPlayerData = getPlayerData();
        PlayerManager otherPlayerManager = new PlayerManager(otherPlayerData); 
        PlayerManager thisPlayerManager = new PlayerManager(thisPlayerData);
        thisPlayerManager.setPlayerFrom(otherPlayerManager);

        NBTTree playerData = thisPlayerManager.getPlayerData();
        playerData.renameRoot("Player");
        data.replaceTagData(new String[]{"Data", "Player"}, playerData.getRoot());
    }

    @Override
    public NBTTree getPlayerData(){
        CompoundTag playerDataRoot = (CompoundTag) data.findTag(new String[]{"Data", "Player"});
        return new NBTTree(playerDataRoot);
    }

    @Override
    public ListTag getPlayerPos(){
        return (ListTag) data.findTag(new String[]{"Data", "Player", "Pos"});
    }

}
