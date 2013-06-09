package org.isma.memos.main.logic.menus;

import org.isma.core.ApplicationContext;
import org.isma.core.actions.QuitAction;
import org.isma.core.components.AbstractApplicationMenuBar;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.db.AbstractSQLHandler;
import org.isma.memos.main.logic.menus.actions.MemoEditionAction;
import org.isma.memos.main.logic.menus.actions.TagEditionAction;
import org.isma.utils.io.FileUtils;

import java.awt.event.ActionEvent;

public class MemosMenuBar extends AbstractApplicationMenuBar<MemoManagerConfiguration> {
    public MemosMenuBar(ApplicationContext<MemoManagerConfiguration> context) {
        super(context);
    }


    @Override
    protected void fillFileMenu() {
        MemoEditionAction memoEditionAction = context.getGlobalComponent(MemoEditionAction.class);
        TagEditionAction tagEditionAction = context.getGlobalComponent(TagEditionAction.class);
        fileMenu.add(menuBuilder.buildMenuItem(memoEditionAction));
        fileMenu.add(menuBuilder.buildMenuItem(tagEditionAction));
    }


    @Override
    protected QuitAction buildQuitAction() {
        return new QuitAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("quit");
                    //Pour �tre sur de closer la connexion et de d�gager les fichiers tempo
                    context.getGlobalComponent(AbstractSQLHandler.class).end();
                    //Clean des pi�ces jointes t�l�charg�es
                    FileUtils.forceDeleteIfExists(context.getConfiguration().getAttachmentTmpDirectory());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                MemosMenuBar.super.buildQuitAction().actionPerformed(e);
            }
        };
    }
}
