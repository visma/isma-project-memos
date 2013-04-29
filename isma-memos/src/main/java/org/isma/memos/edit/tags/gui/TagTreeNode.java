package org.isma.memos.edit.tags.gui;

import org.isma.memos.model.Tag;

import javax.swing.tree.DefaultMutableTreeNode;

public class TagTreeNode extends DefaultMutableTreeNode {

    public TagTreeNode(Tag tag) {
        super(tag);
    }


    public void addChild(TagTreeNode treeTreeNode) {
        insert(treeTreeNode, getChildCount());
    }


    public Tag getTag() {
        return (Tag)getUserObject();
    }

}
