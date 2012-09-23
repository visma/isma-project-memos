package org.isma.memos.preview.logic;

import org.isma.core.ApplicationContext;
import org.isma.core.logic.AbstractLogic;
import org.isma.core.utils.JoinLabeleable;
import org.isma.guitoolkit.GuiRunnable;
import org.isma.guitoolkit.components.HyperLink;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.attachments.LinkRightClickAction;
import org.isma.memos.attachments.OpenAttachmentAction;
import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.preview.gui.PreviewForm;
import org.isma.memos.results.logic.IResults;
import org.isma.memos.results.manager.MemoManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreviewLogic extends AbstractLogic<MemoManagerConfiguration>
      implements ListSelectionListener, ActionListener {
    private MemoManager memoManager;
    private PreviewForm form = new PreviewForm();
    private IResults results;


    public PreviewLogic(ApplicationContext<MemoManagerConfiguration> context, MemoManager memoManager) {
        super(context);
        this.memoManager = memoManager;
    }


    public JPanel getGui() {
        return form.getMainPanel();
    }


    private synchronized void loadPreview(final Memo selection) throws Exception {
        if (selection == null) {
            return;
        }
        final JPanel attachmentPanel = form.getAttachmentPanel();
        form.getTitleTextField().setText(selection.getTitle());
        form.getContentTextArea().setText(selection.getContent());
        form.getTagsTextField().setText(JoinLabeleable.join(selection.getTags()));

        attachmentPanel.removeAll();
        //second revalidate() call is not enough to have a proper refresh
        attachmentPanel.revalidate();
        attachmentPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        for (Attachment attachment : selection.getAttachments()) {
            addAttachmentLink(attachmentPanel, attachment);
        }
        attachmentPanel.revalidate();
    }


    private void addAttachmentLink(JPanel attachmentPanel, Attachment attachment) throws Exception {
        HyperLink link = new HyperLink(attachment.getName());
        link.addLeftActionListener(new OpenAttachmentAction(context, memoManager, attachment));
        link.addRightActionListener(new LinkRightClickAction(context,
                                                             memoManager, attachmentPanel,
                                                             link,
                                                             attachment
        ));
        attachmentPanel.add(link);
    }


    public void clearPreview() {
        form.getTitleTextField().setText(null);
        form.getContentTextArea().setText(null);
        form.getTagsTextField().setText(null);
        form.getAttachmentPanel().removeAll();
        form.getAttachmentPanel().revalidate();
    }


    public void valueChanged(ListSelectionEvent e) {
        final Memo selection = results.getSelection();
        new GuiRunnable() {
            @Override
            protected void doRun() throws Exception {
                loadPreview(selection);
            }
        }.start();
    }


    public void actionPerformed(ActionEvent e) {
        clearPreview();
    }


    public void setResults(IResults results) {
        this.results = results;
    }
}
