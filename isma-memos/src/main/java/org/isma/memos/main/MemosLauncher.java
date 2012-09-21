package org.isma.memos.main;

import org.isma.core.main.AbstractApplicationLauncher;
import org.isma.guitoolkit.SimpleGlassPane;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.db.HSQLConfiguration;
import org.isma.memos.db.HSQLHandler;
import org.isma.memos.edit.tags.logic.TagEventListener;
import org.isma.memos.main.dao.InitSQLDAO;
import org.isma.memos.main.logic.MainMemosLogic;
import org.isma.memos.main.logic.menus.actions.MemoEditionAction;
import org.isma.memos.main.logic.menus.actions.TagEditionAction;
import org.isma.memos.main.logic.menus.actions.TagEventListenerProvider;
import org.isma.memos.main.manager.InitManager;
import org.isma.memos.results.dao.MemoSQLDAO;
import org.isma.memos.results.manager.MemoManager;
import org.isma.memos.tag.dao.TagSQLDAO;
import org.isma.memos.tag.manager.TagManager;

import javax.swing.*;

public class MemosLauncher extends AbstractApplicationLauncher<MemoManagerConfiguration, MainMemosLogic>
      implements TagEventListenerProvider {

    protected MemosLauncher() throws Exception {
    }


    public TagEventListener getTagEventListener() {
        return getMainLogic();
    }


    @Override
    protected void configureMainFrame(JFrame mainFrame) {
        mainFrame.setGlassPane(new SimpleGlassPane());
    }


    @Override
    protected void registerImplementations() {
        picoContainer.registerComponentInstance(this);
        registerDAOs();
        registerManagers();
        registerActions();
    }


    protected void registerActions() {
        picoContainer.registerComponentImplementation(TagEditionAction.class);
        picoContainer.registerComponentImplementation(MemoEditionAction.class);
    }


    protected void registerManagers() {
        picoContainer.registerComponentImplementation(InitManager.class);
        picoContainer.registerComponentImplementation(TagManager.class);
        picoContainer.registerComponentImplementation(MemoManager.class);
    }


    protected void registerDAOs() {
        picoContainer.registerComponentImplementation(HSQLHandler.class);
        picoContainer.registerComponentImplementation(HSQLConfiguration.class);

        picoContainer.registerComponentImplementation(TagSQLDAO.class);
        picoContainer.registerComponentImplementation(MemoSQLDAO.class);
        picoContainer.registerComponentImplementation(InitSQLDAO.class);
    }


    @Override
    protected MemoManagerConfiguration buildConfiguration() throws Exception {
        return new MemoManagerConfiguration();
    }


    @Override
    protected MainMemosLogic buildMainLogic(JFrame frame) throws Exception {
        return new MainMemosLogic(this,
                                  getGlobalComponent(InitManager.class),
                                  getGlobalComponent(TagManager.class),
                                  getGlobalComponent(MemoManager.class));
    }


    public static void main(String[] args) throws Exception {
        new MemosLauncher().launch();
    }
}
