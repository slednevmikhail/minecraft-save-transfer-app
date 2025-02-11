package org.mst.SaveManager;

import org.jnbt.IntArrayTag;
import org.jnbt.ListTag;
import org.mst.SaveManager.Utils.NBTTree;

import java.io.IOException;

public class PlayerManager extends SaveManager {
    public String getBackupLocation() {
        return "playerdata\\";
    }
    public PlayerManager(String file_path) throws IOException {
        super(file_path);
    }

    public PlayerManager(NBTTree root){
        super(root);
    }

    @Override
    public IntArrayTag getPlayerId(){
        return
                ( (IntArrayTag) data.findTag(new String[] {"UUID"}));
    }

    @Override
    public ListTag getPlayerPos(){
        return (ListTag) data.findTag(new String[] {"Pos"});
    }

    @Override
    public NBTTree getPlayerData(){
        return NBTTree.copyOf(data);
    }

    @Override
    public void setPlayerFrom(SaveManager other){
        System.out.println(data.toString());
        NBTTree otherPlayerData = other.getPlayerData();
        ListTag thisPlayerPos = getPlayerPos();

        IntArrayTag thisPlayerUUID = getPlayerId();
        data = NBTTree.copyOf(otherPlayerData);

        data.replaceTagData(new String[] {"UUID"}, thisPlayerUUID);
        data.replaceTagData(new String[] {"Pos"}, thisPlayerPos);

        data.renameRoot("");
    }
}
