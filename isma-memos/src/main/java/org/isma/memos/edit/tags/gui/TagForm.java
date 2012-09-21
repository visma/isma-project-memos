package org.isma.memos.edit.tags.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class TagForm implements IForm {
    private JPanel mainPanel;
    private JScrollPane tagScrollPane;
    private JTree tagEditionTree;


    public JTree getTagEditionTree() {
        return tagEditionTree;
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
