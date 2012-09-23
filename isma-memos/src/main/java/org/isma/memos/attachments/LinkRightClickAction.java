package org.isma.memos.attachments;

import org.isma.core.ApplicationContext;
import org.isma.guitoolkit.components.HyperLink;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.model.Attachment;
import org.isma.memos.results.manager.MemoManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class LinkRightClickAction implements ActionListener {
    private HyperLink link;
    private File file;
    private boolean readonly;
    private ApplicationContext<MemoManagerConfiguration> context;
    private JPanel attachmentPanel;
    private List<File> files;


    private LinkRightClickAction(ApplicationContext<MemoManagerConfiguration> context,
                                 JPanel attachmentPanel,
                                 List<File> files,
                                 HyperLink link,
                                 File file,
                                 boolean readonly) {
        this.context = context;
        this.attachmentPanel = attachmentPanel;
        this.files = files;
        this.link = link;
        this.file = file;
        this.readonly = readonly;
    }


    public LinkRightClickAction(ApplicationContext<MemoManagerConfiguration> context,
                                JPanel attachmentPanel,
                                List<File> files,
                                HyperLink link,
                                File file) {

        this(context, attachmentPanel, files, link, file, false);
    }


    public LinkRightClickAction(ApplicationContext<MemoManagerConfiguration> context,
                                MemoManager memoManager,
                                JPanel attachmentPanel,
                                HyperLink link,
                                Attachment attachment) throws Exception {

        this(context, attachmentPanel, null, link, null, true);
        File tmpDir = context.getConfiguration().getAttachmentTmpDirectory();
        this.file = memoManager.loadAttachment(attachment, tmpDir);
    }


    public void actionPerformed(ActionEvent e) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new SaveAttachmentAction(context, file));
        menu.add(saveItem);
        if (!readonly) {
            JMenuItem deleteItem = new JMenuItem("Delete");
            deleteItem.addActionListener(new DeleteAttachmentAction(context, attachmentPanel, link, files, file));
            menu.add(deleteItem);
        }
        menu.show(link, link.getX(), link.getY());
    }
}