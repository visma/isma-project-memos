package org.isma.memos.tag.manager;

import org.isma.memos.model.Tag;
import org.isma.memos.tag.TagCache;
import org.isma.memos.tag.dao.ITagDAO;

import java.util.ArrayList;
import java.util.List;

public class TagManager {
    private ITagDAO dao;
    private TagCache cache;


    public TagManager(ITagDAO dao) {
        this.dao = dao;
        this.cache = new TagCache(dao);
    }


    public Tag getRootTag() throws Exception {
        return cache.get();
    }


    public void saveTag(Tag tag) throws Exception {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(tag);
        dao.saveTag(tags);
    }


    public void deleteTag(Tag tag) throws Exception {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(tag);
        dao.deleteTag(tags);
    }
}
