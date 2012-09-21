package org.isma.memos.edit.memo;

import org.isma.core.ApplicationContext;
import org.isma.guitoolkit.components.HyperLink;
import org.isma.guitoolkit.components.TestableFileChooser;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.attachments.LinkRightClickAction;
import org.isma.memos.attachments.OpenAttachmentAction;
import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.results.manager.MemoManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class AttachmentChooserListener implements ActionListener {
    private ApplicationContext<MemoManagerConfiguration> context;
    private MemoManager memoManager;
    private Component parentComponent;
    private JPanel attachmentPanel;
    private List<File> fileList;
    private JFileChooser fileChooser = new TestableFileChooser();
    private Memo memo;


    public AttachmentChooserListener(ApplicationContext<MemoManagerConfiguration> context,
                                     MemoManager memoManager,
                                     Component parentComponent,
                                     JPanel attachmentPanel,
                                     List<File> fileList, Memo memo) {
        this.context = context;
        this.memoManager = memoManager;
        this.parentComponent = parentComponent;
        this.attachmentPanel = attachmentPanel;
        this.fileList = fileList;
        this.memo = memo;
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }


    public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooser.showOpenDialog(parentComponent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileList.add(file);
            attachmentPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            Attachment newAttachment = new Attachment(null, memo == null ? null : memo.getId(), file.getAbsolutePath());
            HyperLink link = new HyperLink(file.getName());
            link.addLeftActionListener(new OpenAttachmentAction(context, memoManager, newAttachment));
            link.addRightActionListener(new LinkRightClickAction(context,
                                                                 attachmentPanel,
                                                                 fileList,
                                                                 link,
                                                                 file));
            attachmentPanel.add(link);
            attachmentPanel.revalidate();
        }
    }
}
