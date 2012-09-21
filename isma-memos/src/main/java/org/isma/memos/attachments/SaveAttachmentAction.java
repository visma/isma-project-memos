package org.isma.memos.attachments;

import org.apache.commons.io.FileUtils;
import org.isma.core.ApplicationContext;
import org.isma.core.logic.LogicGlassedActionListener;
import org.isma.guitoolkit.DirectoryChooserListener;

import javax.swing.*;
import java.io.File;

public class SaveAttachmentAction extends LogicGlassedActionListener {
    private File originalFile;


    public SaveAttachmentAction(ApplicationContext context, File originalFile) {
        super(context);
        this.originalFile = originalFile;
    }


    @Override
    protected void doAction() throws Exception {
        DirectoryChooserListener directoryChooserListener = new DirectoryChooserListener(context.getMainFrame(), null);
        directoryChooserListener.actionPerformed(null);
        File directoryLocation = directoryChooserListener.getFile();
        if (directoryLocation == null) {
            return;
        }
        FileUtils.copyFileToDirectory(originalFile, directoryLocation);
        //TODO utiliser FILE_SEPARATOR
        String message = String.format("%s copied to %s",
                                       originalFile.getName(),
                                       directoryLocation.getAbsolutePath() + "\\");
        String title = "Attachment saved";
        JOptionPane.showMessageDialog(context.getMainFrame(), message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
