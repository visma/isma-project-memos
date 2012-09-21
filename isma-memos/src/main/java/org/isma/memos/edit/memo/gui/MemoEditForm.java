package org.isma.memos.edit.memo.gui;

import javax.swing.*;

public class MemoEditForm {
    private JPanel mainPanel;
    private JTextField titleTextField;
    private JTextArea contentTextArea;
    private JButton saveButton;
    private JTree tagTree;
    private JButton attachButton;
    private JPanel attachmentPanel;


    public JTextField getTitleTextField() {
        return titleTextField;
    }


    public JTextArea getContentTextArea() {
        return contentTextArea;
    }


    public JButton getSaveButton() {
        return saveButton;
    }


    public JTree getTagTree() {
        return tagTree;
    }


    public JPanel getAttachmentPanel() {
        return attachmentPanel;
    }


    public JButton getAttachButton() {
        return attachButton;
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
