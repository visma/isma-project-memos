package org.isma.memos.edit.tags.logic;

public interface TagEventListener {
    void onTagChangeEvent(boolean existingTagsModified) throws Exception;
}
