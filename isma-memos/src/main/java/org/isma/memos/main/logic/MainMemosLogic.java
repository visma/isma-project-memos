package org.isma.memos.main.logic;

import org.isma.core.ApplicationContext;
import org.isma.core.components.AbstractApplicationMenuBar;
import org.isma.core.logic.IMenuBarLogic;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.edit.tags.logic.TagEventListener;
import org.isma.memos.main.gui.MainForm;
import org.isma.memos.main.logic.menus.MemosMenuBar;
import org.isma.memos.main.logic.menus.actions.MemoEditionAction;
import org.isma.memos.main.manager.InitManager;
import org.isma.memos.preview.logic.PreviewLogic;
import org.isma.memos.results.logic.ResultsLogic;
import org.isma.memos.results.manager.MemoManager;
import org.isma.memos.search.logic.SearchLogic;
import org.isma.memos.tag.manager.TagManager;

import javax.swing.*;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class MainMemosLogic implements IMenuBarLogic, TagEventListener {

    private MainForm form = new MainForm();
    private SearchLogic searchLogic;
    private ResultsLogic resultsLogic;
    private PreviewLogic previewLogic;
    private MemosMenuBar menuBar;


    public void onTagChangeEvent(boolean existingTagsModified) throws Exception {
        searchLogic.rebuildTagTree();
        if (existingTagsModified && !resultsLogic.isCleared()) {
            showMessageDialog(form.getMainPanel(),
                    "Possible obsolete tags : clearing results",
                    "Message",
                    INFORMATION_MESSAGE);
            resultsLogic.clearResults();
            previewLogic.clearPreview();
        }
    }


    public MainMemosLogic(ApplicationContext<MemoManagerConfiguration> context,
                          InitManager initManager,
                          TagManager tagManager,
                          MemoManager memoManager)
            throws Exception {
        initManager.init();
        //
        //List<Memo> memos = memoManager.selectAll(tagManager.getRootTag());
        //System.out.println("nb memos : " + memos.size());
        //
        menuBar = new MemosMenuBar(context);
        menuBar.build();
        context.getMainFrame().setJMenuBar(menuBar);

        MemoEditionAction memoEditionAction = new MemoEditionAction(context, memoManager, tagManager);
        previewLogic = new PreviewLogic(context, memoManager);
        resultsLogic = new ResultsLogic(context, tagManager, memoManager, previewLogic, memoEditionAction);
        searchLogic = new SearchLogic(context, tagManager, resultsLogic, previewLogic);
        resultsLogic.setSearchParams(searchLogic);
        previewLogic.setResults(resultsLogic);
        initForm();
    }


    private void initForm() {
        form.getSearchPanel().add(searchLogic.getGui());
        form.getBottomPanel().add(buildBottomPanel());
    }


    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        JPanel resultPanel = resultsLogic.getGui();
        JPanel previewPanel = previewLogic.getGui();

        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(resultPanel);
        splitPane.setRightComponent(previewPanel);
        panel.add(splitPane);
        return panel;
    }


    public JPanel getGui() {
        return form.getMainPanel();
    }


    public AbstractApplicationMenuBar getMenuBar() {
        return menuBar;
    }
}
