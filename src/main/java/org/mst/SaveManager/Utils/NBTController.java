package org.mst.SaveManager.Utils;

import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class NBTController {


    public static CompoundTag readNBT(String file_path) throws IOException {
        File save_file = new File(file_path);
        NBTInputStream is = new NBTInputStream(Files.newInputStream(save_file.toPath()));
        CompoundTag result = (CompoundTag) is.readTag();
        is.close();
        return result;
    }


    private static void write(File file, CompoundTag tag) throws IOException {
        FileOutputStream os = new FileOutputStream(file);
        NBTOutputStream nbt_os = new NBTOutputStream(os);
        nbt_os.writeTag(tag);
        nbt_os.close();
    }

    public static void write(String file_path, CompoundTag tag) throws IOException {
        File output_file = new File(file_path);
        write(output_file, tag);
    }

    private static void write(File file, NBTTree data) throws IOException {
        write(file, data.getRoot());
    }

    private static void write(String file_path, NBTTree data) throws IOException {
        write(file_path, data.getRoot());
    }

    public static String backup(String filename, NBTTree data, String rootPath, String backupName) throws IOException {
        String path = generatePath(rootPath, backupName);
        File output_file = createBackupFile(path, filename);
        write(output_file, data);
        return output_file.getAbsolutePath();
    }

    public static String backup(String filename, NBTTree data, String rootPath, String backupName, String addFolder) throws IOException {
        String path = generatePath(rootPath, backupName) + File.separator + addFolder;
        File output_file = createBackupFile(path, filename);
        write(output_file, data);
        return output_file.getAbsolutePath();
    }


    private static File createBackupFile(String path, String filename) throws IOException {
        boolean makeDirs = new File(path).mkdirs();
        String file_path = path + File.separator + filename;
        if (!makeDirs) {
            throw new IOException("Creating dir error!");
        }
        try{
            File file = new File(file_path);
            if (file.createNewFile()){
                System.out.println("Создан файл: " + file_path);
            }
            else{
                System.out.println("File already exists: " + file_path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(file_path);
    }

    private static String generatePath(String rootPath, String name){
        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        String dir_name = now.toString().replace(':', '_');
        System.out.println(rootPath + "\\Output\\" + dir_name);
        if (!name.isEmpty()){
            return rootPath + "\\Output\\" + name + "_" + dir_name;
        }
        return rootPath + "\\Output\\" + dir_name;

    }

    public static String save(String file_path, NBTTree data) throws IOException {
        write(file_path, data);
        return file_path;
    }

}
