package org.isma.memos.tag;

import org.isma.memos.model.Tag;
import org.isma.memos.tag.dao.ITagDAO;

public class TagCache {
    private ITagDAO dao;
    private Tag rootTag;


    public TagCache(ITagDAO dao) {
        this.dao = dao;
    }


    public Tag get() throws Exception {
        if (rootTag == null) {
            rootTag = dao.loadAllTags();
        }
        return rootTag;
    }
}
