package org.isma.memos.edit.memo.logic;

import org.isma.core.ApplicationContext;
import org.isma.core.logic.AbstractLogic;
import org.isma.core.logic.LogicGlassedActionListener;
import org.isma.guitoolkit.components.HyperLink;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.attachments.LinkRightClickAction;
import org.isma.memos.attachments.OpenAttachmentAction;
import org.isma.memos.edit.memo.AttachmentChooserListener;
import org.isma.memos.edit.memo.gui.MemoEditForm;
import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.model.Tag;
import org.isma.memos.results.manager.MemoManager;
import org.isma.memos.tag.TagTreeBuilder;
import org.isma.memos.tag.manager.TagManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class MemoEditLogic extends AbstractLogic<MemoManagerConfiguration> {
    private MemoManager memoManager;
    private TagManager tagManager;
    private TagTreeBuilder tagTreeBuilder = new TagTreeBuilder();

    private MemoEditForm form = new MemoEditForm();

    private Memo memo;
    private List<File> attachmentFiles;


    public MemoEditLogic(ApplicationContext<MemoManagerConfiguration> context,
                         MemoManager memoManager,
                         TagManager tagManager,
                         Memo memo) throws Exception {
        super(context);
        this.memoManager = memoManager;
        this.tagManager = tagManager;
        this.memo = memo;
        initTree();
        addListeners();
        loadForm();
    }


    private void loadForm() throws Exception {
        if (memo != null) {
            form.getTitleTextField().setText(memo.getTitle());
            form.getContentTextArea().setText(memo.getContent());
            tagTreeBuilder.selectTagOnTree(form.getTagTree(), memo.getTags());

            form.getAttachmentPanel().removeAll();
            form.getAttachmentPanel().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            for (Attachment attachment : memo.getAttachments()) {
                addExistingLinkIntoAttachmentPanel(attachment);
            }
            form.getAttachmentPanel().revalidate();
        }
    }


    private void addExistingLinkIntoAttachmentPanel(Attachment attachment) throws Exception {
        HyperLink link = new HyperLink(attachment.getName(), "memoedit");
        File attachmentFile = memoManager.loadAttachment(attachment,
                                                         context.getConfiguration().getAttachmentTmpDirectory());
        attachmentFiles.add(attachmentFile);

        link.addLeftActionListener(new OpenAttachmentAction(context, memoManager, attachment));
//        link.addRightActionListener(new DeleteAttachmentAction(context, form.getAttachmentPanel(),
//                                                               link,
//                                                               attachmentFileList,
//                                                               attachmentFile));
        link.addRightActionListener(new LinkRightClickAction(context,
                                                             form.getAttachmentPanel(),
                attachmentFiles,
                                                             link,
                                                             attachmentFile));
        form.getAttachmentPanel().add(link);
        form.getAttachmentPanel().revalidate();
    }


    private void initTree() throws Exception {
        Tag root = tagManager.getRootTag();
        tagTreeBuilder.rebuild(form.getTagTree(), root);
    }


    private void addListeners() {
        form.getSaveButton().addActionListener(new LogicGlassedActionListener(context) {
            @Override
            protected void doAction() throws Exception {
                context.getMainFrame().getGlassPane().setVisible(true);
                save();
                context.getMainFrame().getGlassPane().setVisible(false);

                SwingUtilities.getWindowAncestor(form.getMainPanel()).setVisible(false);
                showMessageDialog(form.getMainPanel(), "Memo saved", "Memo saved", INFORMATION_MESSAGE);
            }
        });
        attachmentFiles = new ArrayList<File>();
        form.getAttachButton().addActionListener(new AttachmentChooserListener(context,
                                                                               memoManager,
                                                                               form.getMainPanel(),
                                                                               form.getAttachmentPanel(),
                attachmentFiles,
                                                                               memo));
    }


    private void save() throws Exception {
        final List<Tag> tags = tagTreeBuilder.buildSelectedTags(form.getTagTree());
        List<Attachment> attachments = convertToAttachment(attachmentFiles);
        Memo memoSaved = memoManager.save(memo,
                                          form.getTitleTextField().getText(),
                                          form.getContentTextArea().getText(),
                                          tags,
                                          attachments);
        boolean isNewMemo = memo == null;
        if (!isNewMemo) {
            memo.setId(memoSaved.getId());
            memo.setTitle(memoSaved.getTitle());
            memo.setContent(memoSaved.getContent());
            memo.setTags(memoSaved.getTags());
            memo.setAttachments(memoSaved.getAttachments());
        }
    }


    private List<Attachment> convertToAttachment(List<File> files) {
        List<Attachment> attachments = new ArrayList<Attachment>();
        for (File file : files) {
            attachments.add(new Attachment(null, null, file.getAbsolutePath()));
        }
        return attachments;
    }


    public JPanel getGui() {
        return form.getMainPanel();
    }
}
