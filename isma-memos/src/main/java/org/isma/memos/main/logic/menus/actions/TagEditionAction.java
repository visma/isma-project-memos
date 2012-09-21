package org.isma.memos.main.logic.menus.actions;

import org.isma.core.ApplicationContext;
import org.isma.core.actions.AbstractDialogAction;
import org.isma.guitoolkit.error.ErrorHandler;
import org.isma.memos.edit.tags.logic.TagEventListener;
import org.isma.memos.edit.tags.logic.TagLogic;
import org.isma.memos.gui.MemoIconEnum;
import org.isma.memos.tag.manager.TagManager;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class TagEditionAction extends AbstractDialogAction {
    private ApplicationContext context;
    private TagManager tagManager;


    public TagEditionAction(ApplicationContext context, TagManager tagManager) {
        super(context.getMainFrame(), "Tag Edition", MemoIconEnum.TAG_ICON.getImageIcon(), KeyEvent.VK_T);
        this.context = context;
        this.tagManager = tagManager;
    }


    public void actionPerformed(ActionEvent e) {
        try {
            TagEventListenerProvider launcher = (TagEventListenerProvider)context.getGlobalComponent(
                  TagEventListenerProvider.class);
            TagEventListener tagEventListener = launcher.getTagEventListener();
            displayDialog(new TagLogic(context, tagManager, tagEventListener).getGui(), getLabel(), getIcon());
        }
        catch (Exception e1) {
            ErrorHandler.handleException(e1);
        }
    }
}
