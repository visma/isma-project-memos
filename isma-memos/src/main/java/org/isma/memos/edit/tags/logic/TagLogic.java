package org.isma.memos.edit.tags.logic;

import org.isma.core.ApplicationContext;
import org.isma.core.logic.AbstractLogic;
import org.isma.guitoolkit.error.ErrorHandler;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.edit.tags.gui.TagEditingTreeCellRenderer;
import org.isma.memos.edit.tags.gui.TagForm;
import org.isma.memos.edit.tags.gui.TagTreeNode;
import org.isma.memos.model.Tag;
import org.isma.memos.tag.manager.TagManager;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.*;

public class TagLogic extends AbstractLogic<MemoManagerConfiguration> {
    private TagManager manager;
    private TagEventListener tagEventListener;
    private TagTreeNodeBuilder treeTreeNodeBuilder = new TagTreeNodeBuilder();
    private TagForm form = new TagForm();

    public enum SaveMode {
        ADD,
        UPDATE,
        DELETE
    }
    ;


    public TagLogic(ApplicationContext<MemoManagerConfiguration> context,
                    TagManager manager,
                    TagEventListener tagEventListener) throws Exception {
        super(context);
        this.manager = manager;
        this.tagEventListener = tagEventListener;
        initForm();
        addListeners();
    }


    private void addListeners() {

        form.getTagEditionTree().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    List<Integer> authorizedKeys = new ArrayList<Integer>();
                    authorizedKeys.add(KeyEvent.VK_INSERT);
                    authorizedKeys.add(KeyEvent.VK_DELETE);
                    authorizedKeys.add(KeyEvent.VK_F2);
                    if (!authorizedKeys.contains(e.getKeyCode())) {
                        return;
                    }
                    TreePath[] selectionPaths = form.getTagEditionTree().getSelectionModel().getSelectionPaths();
                    if (selectionPaths == null) {
                        throw new IllegalArgumentException("No selected node");
                    }
                    TagTreeNode node = (TagTreeNode)selectionPaths[selectionPaths.length - 1].getLastPathComponent();
                    if (e.getKeyCode() == KeyEvent.VK_INSERT) {
                        add(node);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                        delete(node);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_F2) {
                        rename(node);
                    }
                }
                catch (Exception ex) {
                    ErrorHandler.handleException(ex);
                }
            }
        });
        form.getTagEditionTree().setDragEnabled(true);
        form.getTagEditionTree().setTransferHandler(new TagTreeTransfertHandler(manager, tagEventListener, form.getMainPanel()));
    }


    private void save(Tag tag, SaveMode saveMode) throws Exception {
        boolean isTagChanged = false;
        if (saveMode == SaveMode.ADD || saveMode == SaveMode.UPDATE) {
            isTagChanged = saveMode == SaveMode.UPDATE;
            manager.saveTag(tag);
        }
        else if (saveMode == SaveMode.DELETE) {
            manager.deleteTag(tag);
        }
        tagEventListener.onTagChangeEvent(isTagChanged);
    }


    private void add(TagTreeNode parentNode) throws Exception {
        String childName = JOptionPane.showInputDialog("Enter child tag name");
        if (childName == null) {
            //Null == Cancel input
            return;
        }
        if (childName.length() < 1) {
            throw new Exception("No name defined");
        }
        Tag child = new Tag(childName);
        Tag parentTag = (Tag)parentNode.getUserObject();
        parentTag.addChild(child);

        save(child, SaveMode.ADD);
        addChildNode(parentNode, child);
    }


    private void rename(TagTreeNode node) throws Exception {
        String newName = JOptionPane.showInputDialog("Enter new name", node.getTag().getLabel());
        if (newName == null) {
            //Null == Cancel input
            return;
        }
        if (newName.length() < 1) {
            throw new Exception("No name defined");
        }
        Tag tag = (Tag)node.getUserObject();
        tag.setLabel(newName);

        save(tag, SaveMode.UPDATE);
        renameNode(node);
    }


    private void delete(TagTreeNode node) throws Exception {
        String confirmRemovingMessage = "Are you sure to delete " + node.getTag().getLabel() + " ?";
        final String confirmRemovingTitle = "Delete Tag";
        if (YES_OPTION == showConfirmDialog(form.getMainPanel(),
                                            confirmRemovingMessage,
                                            confirmRemovingTitle,
                                            YES_NO_OPTION)) {
            Tag tag = (Tag)node.getUserObject();
            Tag parent = tag.getParent();
            parent.removeChild(tag);

            save(tag, SaveMode.DELETE);
            removeNode(node);
        }
    }


    private void removeNode(TagTreeNode node) {
        DefaultTreeModel model = (DefaultTreeModel)form.getTagEditionTree().getModel();
        model.removeNodeFromParent(node);
    }


    private void renameNode(TagTreeNode node) {
        DefaultTreeModel model = (DefaultTreeModel)form.getTagEditionTree().getModel();
        model.nodeChanged(node);
    }


    private void addChildNode(TagTreeNode parentNode, Tag childTag) {
        TagTreeNode childNode = new TagTreeNode(childTag);
        DefaultTreeModel model = (DefaultTreeModel)form.getTagEditionTree().getModel();
        model.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
    }


    private void initForm() throws Exception {
        Tag rootTag = manager.getRootTag();
        TagTreeNode rootTagTreeNode = treeTreeNodeBuilder.buildRootTagNode(rootTag);
        form.getTagEditionTree().setModel(new DefaultTreeModel(rootTagTreeNode));
        form.getTagEditionTree().getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        form.getTagEditionTree().setCellRenderer(new TagEditingTreeCellRenderer());
        form.getTagEditionTree().setRootVisible(true);
        form.getTagEditionTree().setShowsRootHandles(true);
    }


    public JPanel getGui() {
        return form.getMainPanel();
    }
}
