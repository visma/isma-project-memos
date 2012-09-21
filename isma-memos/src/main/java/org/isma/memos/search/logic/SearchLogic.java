package org.isma.memos.search.logic;

import org.isma.core.ApplicationContext;
import org.isma.core.logic.AbstractLogic;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.model.Tag;
import org.isma.memos.search.gui.SearchForm;
import org.isma.memos.tag.TagTreeBuilder;
import org.isma.memos.tag.manager.TagManager;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchLogic extends AbstractLogic<MemoManagerConfiguration> implements ISearchParams {
    private SearchForm form = new SearchForm();
    private TagManager tagManager;
    private TagTreeBuilder tagTreeBuilder = new TagTreeBuilder();


    public SearchLogic(ApplicationContext<MemoManagerConfiguration> context,
                       TagManager tagManager,
                       ActionListener... searchListeners) throws Exception {
        super(context);
        this.tagManager = tagManager;
        initForm();
        addListeners(searchListeners);
    }


    private void addListeners(ActionListener... searchListeners) {
        for (ActionListener searchListener : searchListeners) {
            form.getSearchButton().addActionListener(searchListener);
        }
    }


    private void initForm() throws Exception {
        rebuildTagTree();
    }


    public void rebuildTagTree() throws Exception {
        Tag root = tagManager.getRootTag();
        JTree tree = form.getTagTree();
        tagTreeBuilder.rebuild(tree, root);
    }


    public JPanel getGui() {
        return form.getMainPanel();
    }


    public String getTitle() {
        return form.getTitleSearchTextField().getText();
    }


    public String getContent() {
        return form.getContentSearchTextField().getText();
    }


    public List<Tag> getTagList() throws Exception {
        return tagTreeBuilder.buildSelectedTagList(form.getTagTree());
    }
}
