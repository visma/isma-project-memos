package org.isma.memos.results.dao;

import org.isma.memos.mock.MockXmlParser;
import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.model.Tag;
import org.jdom.Element;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MockMemoDAO implements IMemoDAO {
    private MockXmlParser parser = new MockXmlParser();
    private List<Memo> memos = new ArrayList<Memo>();


    public MockMemoDAO() throws Exception {
        loadAllMemos();
    }


    private void loadAllMemos() throws Exception {
        int index = 1;
        URL resource = getClass().getResource(getClass().getSimpleName() + "_" + index++ + ".xml");
        while (resource != null) {
            final Element rootElement = parser.loadXmlDocument(resource);
            memos.add(parser.convertToMemo(rootElement));
            resource = getClass().getResource(getClass().getSimpleName() + "_" + index++ + ".xml");
        }
    }


    private boolean contains(String requested, String request) {
        return requested.toLowerCase().contains(request.toLowerCase());
    }


    public Memo saveMemo(String title, String content, List<Tag> tags, List<Attachment> attachments) {
        throw new RuntimeException("not implemented");
    }


    public List<Memo> search(Tag rootTag, String paramTitle, String paramContent, List<Tag> tags) {
        List<Memo> foundList = new ArrayList<Memo>();
        for (Memo memo : memos) {
            if (paramTitle.length() > 0) {
                if (!contains(memo.getTitle(), paramTitle)) {
                    continue;
                }
            }
            if (paramContent.length() > 0) {
                if (!contains(memo.getContent(), paramContent)) {
                    continue;
                }
            }
            if (!tags.isEmpty()) {
                boolean paramTagsMatches = true;
                for (Tag paramTag : tags) {
                    final boolean contains = memo.getTags().contains(paramTag);
                    if (!contains) {
                        paramTagsMatches = false;
                        break;
                    }
                }
                if (!paramTagsMatches) {
                    continue;
                }
            }
            foundList.add(memo);
        }
        return foundList;
    }


    public File loadAttachement(Attachment attachment, File attachmentTmpDirectory) {
        throw new RuntimeException("not implemented");
    }


    public void deleteMemo(Integer idMemo) {
        throw new RuntimeException("not implemented");
    }
}
