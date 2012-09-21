package org.isma.memos.tag.dao;

import org.isma.memos.model.Tag;

import java.util.List;
public interface ITagDAO {
    public Tag loadAllTags() throws Exception;


    public void saveTag(List<Tag> newTagList) throws Exception;


    public void deleteTag(List<Tag> toDeleteTagList) throws Exception;
}
