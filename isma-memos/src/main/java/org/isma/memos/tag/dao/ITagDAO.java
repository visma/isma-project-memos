package org.isma.memos.tag.dao;

import org.isma.memos.model.Tag;

import java.util.List;
public interface ITagDAO {
    Tag loadAllTags() throws Exception;


    void saveTag(List<Tag> newTags) throws Exception;


    void deleteTag(List<Tag> toDeleteTags) throws Exception;
}
