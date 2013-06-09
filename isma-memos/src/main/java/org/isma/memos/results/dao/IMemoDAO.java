package org.isma.memos.results.dao;

import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.model.Tag;

import java.io.File;
import java.util.List;

public interface IMemoDAO {
    List<Memo> selectAll(Tag rootTag) throws Exception;

    List<Memo> search(Tag rootTag, String title, String content, List<Tag> tags) throws Exception;


    Memo saveMemo(String title, String content, List<Tag> tags, List<Attachment> attachments) throws Exception;


    File loadAttachement(Attachment attachment, File attachmentTmpDirectory) throws Exception;


    void deleteMemo(Integer idMemo) throws Exception;
}
