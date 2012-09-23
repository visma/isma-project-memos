package org.isma.memos.results.dao;

import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.model.Tag;

import java.io.File;
import java.util.List;

public interface IMemoDAO {

    public List<Memo> search(Tag rootTag, String title, String content, List<Tag> tags) throws Exception;


    public Memo saveMemo(String title, String content, List<Tag> tags, List<Attachment> attachments) throws Exception;


    public File loadAttachement(Attachment attachment, File attachmentTmpDirectory) throws Exception;


    public void deleteMemo(Integer idMemo) throws Exception;
}
