package org.isma.memos.edit.tags.logic;

import org.isma.guitoolkit.DragAndDropGlassPane;
import org.isma.guitoolkit.error.ErrorHandler;
import org.isma.memos.edit.tags.gui.TagTreeNode;
import org.isma.memos.model.Tag;
import org.isma.memos.tag.manager.TagManager;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Enumeration;

class TagTreeTransfertHandler extends TransferHandler {
    private TagManager tagManager;
    private TagEventListener tagEventListener;
    private JPanel mainPanel;
    private Component originalGlassPane;


    TagTreeTransfertHandler(TagManager tagManager, TagEventListener tagEventListener, JPanel mainPanel) {
        this.tagManager = tagManager;
        this.tagEventListener = tagEventListener;
        this.mainPanel = mainPanel;
    }


    @Override
    public Icon getVisualRepresentation(Transferable t) {
        //TODO ça sert à quoi ?
        return null;
    }


    @Override
    public int getSourceActions(JComponent component) {
        return MOVE;
    }


    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
        mainPanel.getRootPane().setGlassPane(originalGlassPane);
        mainPanel.getRootPane().getGlassPane().setVisible(false);
    }


    @Override
    protected Transferable createTransferable(JComponent component) {
        originalGlassPane = mainPanel.getRootPane().getGlassPane();
        mainPanel.getRootPane().setGlassPane(new DragAndDropGlassPane());
        mainPanel.getRootPane().getGlassPane().setVisible(true);
        try {
            String tagId = exportTagId(component);
            return new StringSelection(tagId);
        }
        catch (Exception e) {
            mainPanel.getRootPane().setGlassPane(originalGlassPane);
            mainPanel.getRootPane().getGlassPane().setVisible(false);
        }
        return null;
    }


    @Override
    public boolean canImport(JComponent component, DataFlavor[] flavors) {
        for (DataFlavor f : flavors) {
            if (DataFlavor.stringFlavor.equals(f)) {
                return true;
            }
        }
        throw new RuntimeException("cant import");
    }


    @Override
    public boolean importData(JComponent component, Transferable t) {
        try {
            JTree tree = (JTree)component;
            if (canImport(tree, t.getTransferDataFlavors())) {
                String idTag = (String)t.getTransferData(DataFlavor.stringFlavor);
                TagTreeNode rootNode = (TagTreeNode)tree.getModel().getRoot();
                TagTreeNode sourceNode = getTagTreeNode(rootNode, Integer.parseInt(idTag));
                return moveTag(tree, rootNode, sourceNode);
            }
        }
        catch (Exception e) {
            ErrorHandler.handleException(e);
        }
        return false;
    }


    private TagTreeNode getTagTreeNode(TagTreeNode node, int idTag) {
        if (node.getTag().getId() == idTag) {
            return node;
        }
        Enumeration children = node.children();
        while (children.hasMoreElements()) {
            TagTreeNode subNode = getTagTreeNode((TagTreeNode)children.nextElement(), idTag);
            if (subNode != null) {
                return subNode;
            }
        }
        return null;
    }


    public String exportTagId(JComponent component) {
        JTree tree = (JTree)component;
        Object lastPathComponent = tree.getSelectionPath().getLastPathComponent();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)lastPathComponent;
        return Integer.toString(((Tag)node.getUserObject()).getId());
    }


    public boolean moveTag(JTree tree, TagTreeNode rootNode, TagTreeNode sourceNode) throws Exception {
        if (tree.getSelectionPath() == null){
            return false;
        }
        TagTreeNode targetNode = (TagTreeNode)tree.getSelectionPath().getLastPathComponent();

        checkPossibleMove(sourceNode, rootNode, targetNode);

        //Update Bean
        sourceNode.getTag().getParent().removeChild(sourceNode.getTag());
        targetNode.getTag().addChild(sourceNode.getTag());

        //Update Tree
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        model.removeNodeFromParent(sourceNode);
        model.insertNodeInto(sourceNode, targetNode, 0);

        //Save
        tagManager.saveTag(sourceNode.getTag());
        tagEventListener.onTagChangeEvent(true);
        return true;
    }


    private void checkPossibleMove(TagTreeNode sourceNode, TagTreeNode rootNode, TagTreeNode targetNode) {
        Enumeration ancestorEnumeration = targetNode.pathFromAncestorEnumeration(rootNode);
        while (ancestorEnumeration.hasMoreElements()) {
            TagTreeNode ancestorNode = (TagTreeNode)ancestorEnumeration.nextElement();
            if (ancestorNode.equals(sourceNode)) {
                String error = String.format("Cannot import %s because %s is part of the path from %s to %s",
                                             sourceNode.getTag().getLabel(),
                                             ancestorNode.getTag().getLabel(),
                                             targetNode.getTag().getLabel(),
                                             rootNode.getTag().getLabel());
                throw new RuntimeException(error);
            }
        }
    }
}
