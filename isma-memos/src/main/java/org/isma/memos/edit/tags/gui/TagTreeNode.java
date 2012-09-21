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

    /*public void print() {
        print(0);
    }


    private void print(int depth) {
        System.out.println(PrintUtils.getString(depth, getBean().getLabel()));
        Enumeration chilren = children();
        while (chilren.hasMoreElements()) {
            TagTreeNode child = (TagTreeNode)chilren.nextElement();
            child.print(depth + 1);
        }
    }*/
}
