package org.mst.SaveManager.Utils;

import org.jnbt.CompoundTag;
import org.jnbt.Tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NBTTree {
    private CompoundTag root;
    public NBTTree() {}
    public NBTTree(CompoundTag tag){
        this.root = tag;
    }

    public CompoundTag getRoot() {
        return root;
    }

    private void setRoot(CompoundTag root) {
        this.root = root;
    }

    /**
     * Replaces the {@link Tag} located at the specified {@code path} inside this {@link NBTTree}.
     * <p>
     * This method navigates through the tree structure, finds the target tag, and updates its data.
     * The path should be an array of keys leading to the target tag inside the NBT structure.
     * Type of the {@code newtag} should be the same as the replaced ones.
     * </p>
     *
     * @param path The hierarchical path to the target tag.
     * @param newtag The new tag that will replace the old one at the specified path.
     * @throws ClassCastException if the path leads to a non-CompoundTag element.
     */
    public void replaceTagData(String[] path, Tag newtag){
        Stack<Tag> pathStack = new Stack<>(); // "" "data" "uuid" -> "" : {"data" : {}}
        pathStack.push(new CompoundTag("", root.getValue())); // adding root node
        CompoundTag currentNode = this.root;
        for (int i = 0; i < path.length - 1; i++) {
            currentNode = (CompoundTag) currentNode.getValue().get(path[i]);
            pathStack.push(currentNode);
        }
        pathStack.push(newtag);
        setRoot(updateTagTree(pathStack));
    }


     /**
     * Updates the {@link CompoundTag} tree using the stack of {@link CompoundTag} children.
     * <p>
      *    This method is specifically designed to work with {@link #replaceTagData(String[], Tag)}.
     *     This method navigates from a specific node to the root of the {@link CompoundTag},
     *     merging it with its parent nodes. Thus, it updates the entire branch with the new element,
     *     which is the first element in the {@link Stack<Tag>} path.
     * </p>
     *
     * @param path full path to the node, every element of the Stack should contain a full node, with all of its child. <br>
      *             The first element contains the root
     * @return The updated {@link CompoundTag} with {@code !NO ROOT!}
     */
    private static CompoundTag updateTagTree(Stack<Tag> path){
        while (path.size() > 1){
            var child = path.pop();
            CompoundTag parent = (CompoundTag) path.pop();
            CompoundTag updatedParentTag = NBTTree.connectTags(parent, child);
            path.push(updatedParentTag);
        }
        return (CompoundTag) path.peek();
    }

    /**
     * Merges the target {@link Tag} into the parent {@link NBTTree}
     * <p>
     * Target replaces any of the parent's child with the same name
     * </p>
     * @param target target {@link Tag} to merge into this {@link CompoundTag}
     * @return new {@link CompoundTag}
     *
     */
    private CompoundTag appendChild(Tag target){
        CompoundTag parent = this.root;
        Map<String, Tag> parentValue = new HashMap<>(Map.copyOf(parent.getValue()));
        parentValue.put(target.getName(), target);
        return new CompoundTag(parent.getName(), parentValue);
    }

    /**
     * Returns the specific tag located at the path of this {@link NBTTree}
     * @param path path of the target tag
     * @return {@link Tag} tag, located at the {@code path}
     */
    public Tag findTag(String[] path){
        CompoundTag result = this.root;// {}
        for (int i = 0; i < path.length - 1; i++){
            result = (CompoundTag) result.getValue().get(path[i]);
        }
        return result.getValue().get(path[path.length - 1]);
    }

    /**
     * This is the static implementation of the {@link NBTTree} mergeWith method.
     * <p>
     *     Casts the {@link CompoundTag} parent to the new {@link NBTTree} and implements the {@link #appendChild(Tag)} method
     * </p>
     * @param parent {@link CompoundTag} parent
     * @param child {@link Tag} child
     * @return new {@link CompoundTag}
     */
    private static CompoundTag connectTags(CompoundTag parent, Tag child){
        NBTTree parentWrapper = new NBTTree(parent);
        return parentWrapper.appendChild(child);
    }
    /**
     * This is the static implementation of <br> {@link #replaceTagData(String[], Tag)} <br>
     * Casts {@link CompoundTag origin} to the new {@link NBTTree}, and then uses <br>
     * {@link #replaceTagData(String[], Tag)}
     * <p>
     * {@link #replaceTagData(String[], Tag)} description: <br>
     * Replaces the {@link Tag} located at the specified {@code path} inside this {@link NBTTree}.
     * </p>
     * <p>
     * This method navigates through the tree structure, finds the target tag, and updates its data.
     * The path should be an array of keys leading to the target tag inside the NBT structure.
     * Type of the {@code newtag} should be the same as the replaced ones.
     * </p>
     *
     * @param path The hierarchical path to the target tag.
     * @param newtag The new tag that will replace the old one at the specified path.
     * @return The updated {@link CompoundTag} after the replacement.
     * @throws ClassCastException if the path leads to a non-CompoundTag element.
     */
    public static CompoundTag replaceTagData(CompoundTag origin, String[] path, Tag newtag){
        NBTTree targetWrapper = new NBTTree(origin);
        targetWrapper.replaceTagData(path, newtag);
        return targetWrapper.getRoot();
    }

    public void renameRoot(String newName){
        setRoot( new CompoundTag(newName, root.getValue()) );
    }

    public static NBTTree copyOf(NBTTree tree){
        CompoundTag newRoot = new CompoundTag(tree.getRoot().getName(), tree.getRoot().getValue());
        return new NBTTree(newRoot);
    }

    public static NBTTree fromPath(String path) throws IOException {
        return new NBTTree(NBTController.readNBT(path));
    }

    public void readFromPath(String path) throws IOException {
        root = NBTController.readNBT(path);
    }

}
