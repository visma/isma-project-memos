package org.isma.memos.edit.tags.logic;

public interface TagEventListener {
    public void onTagChangeEvent(boolean existingTagsModified) throws Exception;
}
