package org.mst.SaveManager;

import org.jnbt.IntArrayTag;
import org.jnbt.ListTag;
import org.mst.SaveManager.Utils.NBTTree;

import java.io.File;
import java.io.IOException;

public abstract class SaveManager {
    protected String file_path;
    protected NBTTree data;

    public SaveManager(String file_path) throws IOException {
        data = NBTTree.fromPath(file_path);
        this.file_path = file_path;
    }
    public SaveManager(NBTTree root){
        data = NBTTree.copyOf(root);
    }

    public NBTTree getData(){
        return data;
    }

    public String getFile_path(){
        return file_path;
    }


    public void setPlayerFrom(SaveManager sm){
    }

    public ListTag getPlayerPos(){
        return null;
    }


    public String getPlayerIdString(){
        return getPlayerId().toString().substring(23);
    }
    public IntArrayTag getPlayerId(){
        return null;
    }

    public NBTTree getPlayerData(){
        return null;
    }

    public String getFileName(){
        File file = new File(file_path);
        return file.getName();
    }


}
