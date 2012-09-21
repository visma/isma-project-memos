package org.isma.memos.results.manager;

import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.model.Tag;
import org.isma.memos.results.dao.IMemoDAO;

import java.io.File;
import java.util.List;

public class MemoManager {
    private IMemoDAO dao;


    public MemoManager(IMemoDAO dao) {
        this.dao = dao;
    }


    public List<Memo> search(Tag rootTag, String searchedTitle, String searchContent, List<Tag> searchedTagList)
          throws Exception {
        return dao.search(rootTag, searchedTitle, searchContent, searchedTagList);
    }


    public Memo save(Memo memo, String title, String content, List<Tag> tagList, List<Attachment> attachmentList)
          throws Exception {
        if (memo != null) {
            dao.deleteMemo(memo.getId());
        }
        return dao.saveMemo(title, content, tagList, attachmentList);
    }


    public File loadAttachment(Attachment attachment, File attachmentTmpDirectory) throws Exception {
        return dao.loadAttachement(attachment, attachmentTmpDirectory);
    }


    public void deleteMemo(Memo memo) throws Exception {
        dao.deleteMemo(memo.getId());
    }
}
