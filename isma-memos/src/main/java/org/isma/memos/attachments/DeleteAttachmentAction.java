package org.isma.memos.attachments;

import org.isma.core.ApplicationContext;
import org.isma.core.logic.LogicGlassedActionListener;
import org.isma.guitoolkit.components.HyperLink;

import javax.swing.*;
import java.io.File;
import java.util.List;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

public class DeleteAttachmentAction extends LogicGlassedActionListener {
    private JPanel attachmentPanel;
    private HyperLink link;
    private List<File> attachmentFiles;
    private File file;


    public DeleteAttachmentAction(ApplicationContext context,
                                  JPanel attachmentPanel,
                                  HyperLink link,
                                  List<File> attachmentFiles,
                                  File file) {
        super(context);
        this.attachmentPanel = attachmentPanel;
        this.link = link;
        this.attachmentFiles = attachmentFiles;
        this.file = file;
    }


    @Override
    protected void doAction() throws Exception {
        String confirmMessage = "Are you sure to delete " + file.getName() + " ?";
        String confirmTitle = "Delete attachment";
        if (JOptionPane.YES_OPTION == showConfirmDialog(attachmentPanel,
                                                        confirmMessage,
                                                        confirmTitle,
                                                        YES_NO_OPTION)) {
            attachmentFiles.remove(file);
            attachmentPanel.remove(link);
            attachmentPanel.revalidate();
        }
    }
}
