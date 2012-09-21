package org.isma.memos.attachments;

import org.isma.core.ApplicationContext;
import org.isma.core.logic.LogicGlassedActionListener;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.model.Attachment;
import org.isma.memos.results.manager.MemoManager;

import java.io.File;

import static java.awt.Desktop.getDesktop;

public class OpenAttachmentAction extends LogicGlassedActionListener {
    private MemoManager memoManager;
    private Attachment attachment;
    private MemoManagerConfiguration configuration;


    public OpenAttachmentAction(ApplicationContext<MemoManagerConfiguration> context,
                                MemoManager memoManager,
                                Attachment attachment) {
        super(context);
        configuration = context.getConfiguration();
        this.memoManager = memoManager;
        this.attachment = attachment;
    }


    @Override
    protected void doAction() throws Exception {
        File file;
        if (attachment.isNew()) {
            file = new File(attachment.getName());
        }
        else {
            file = memoManager.loadAttachment(attachment, configuration.getAttachmentTmpDirectory());
        }
        getDesktop().open(file);
    }
}