package org.isma.memos.tag;

import org.isma.guitoolkit.components.checkboxtree.CheckBoxNodeEditor;
import org.isma.guitoolkit.components.checkboxtree.CheckBoxNodeTreeModel;
import org.isma.guitoolkit.components.checkboxtree.CheckableNode;
import org.isma.memos.model.Tag;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;

public class TagTreeBuilder {
    public void rebuild(JTree tree, Tag rootTag) {
        CheckableNode rootNode = TagCheckableNodeBuilder.convertIntoCheckableNode(rootTag);
        CheckBoxNodeTreeModel model = new CheckBoxNodeTreeModel(rootNode, tree);
        tree.setModel(model);
        tree.setRootVisible(true);
        tree.setCellRenderer(new TagCheckBoxNodeRenderer());
        tree.setCellEditor(new CheckBoxNodeEditor(null));
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(SINGLE_TREE_SELECTION);
    }


    public List<Tag> buildSelectedTags(JTree tagTree) throws Exception {
        TreeModel model = tagTree.getModel();
        List<Tag> selectedTags = new ArrayList<Tag>();
        for (CheckableNode checkableNode : ((CheckBoxNodeTreeModel)model).getSelectedNodes()) {
            selectedTags.add((Tag) checkableNode.getBeanToDisplay());
        }
        return selectedTags;
    }


    public void selectTagOnTree(JTree tagTree, List<Tag> selectedTags) throws Exception {
        TreeModel model = tagTree.getModel();
        for (CheckableNode checkableNode : ((CheckBoxNodeTreeModel)model).getAllNodes()) {
            Tag tag = (Tag)checkableNode.getBeanToDisplay();
            boolean contains = selectedTags.contains(tag);
            checkableNode.setSelected(contains);
        }
    }
}
