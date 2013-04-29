package org.isma.memos.search.logic;

import org.isma.memos.model.Tag;

import java.util.List;

public interface ISearchParams {
    String getTitle();


    String getContent();


    List<Tag> getTags() throws Exception;
}
