package org.isma.memos.preview.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class PreviewForm implements IForm {
    private JPanel mainPanel;
    private JTextArea contentTextArea;
    private JTextField titleTextField;
    private JTextField tagsTextField;
    private JPanel attachmentPanel;


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public JTextArea getContentTextArea() {
        return contentTextArea;
    }


    public JTextField getTitleTextField() {
        return titleTextField;
    }


    public JTextField getTagsTextField() {
        return tagsTextField;
    }



    public JPanel getAttachmentPanel() {
        return attachmentPanel;
    }
}
