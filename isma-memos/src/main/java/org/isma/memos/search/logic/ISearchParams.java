package org.isma.memos.search.logic;

import org.isma.memos.model.Tag;

import java.util.List;

public interface ISearchParams {
    public String getTitle();


    public String getContent();


    public List<Tag> getTags() throws Exception;
}
