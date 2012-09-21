package org.isma.memos.main.logic.menus.actions;

import org.isma.core.ApplicationContext;
import org.isma.core.actions.AbstractDialogAction;
import org.isma.guitoolkit.error.ErrorHandler;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.edit.memo.logic.MemoEditLogic;
import org.isma.memos.gui.MemoIconEnum;
import org.isma.memos.model.Memo;
import org.isma.memos.results.logic.IMemoAction;
import org.isma.memos.results.manager.MemoManager;
import org.isma.memos.tag.manager.TagManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MemoEditionAction extends AbstractDialogAction implements IMemoAction {
    static final String MENU_LABEL = "New Memo";
    private ApplicationContext<MemoManagerConfiguration> context;
    private MemoManager memoManager;
    private TagManager tagManager;
    protected Memo memo;


    public MemoEditionAction(ApplicationContext<MemoManagerConfiguration> context, MemoManager memoManager, TagManager tagManager) {
        super(context.getMainFrame(), MENU_LABEL, MemoIconEnum.NEW_ICON.getImageIcon(), KeyEvent.VK_N);
        this.context = context;
        this.memoManager = memoManager;
        this.tagManager = tagManager;
    }


    public void setMemo(Memo memo) {
        this.memo = memo;
    }


    public void actionPerformed(ActionEvent e) {
        try {
            displayDialog(new MemoEditLogic(context, memoManager, tagManager, memo).getGui(),
                          memo == null ? MENU_LABEL : "Edition Memo",
                          getIcon(),
                          new Dimension(800, 600));
        }
        catch (Exception e1) {
            ErrorHandler.handleException(e1);
        }
    }
}