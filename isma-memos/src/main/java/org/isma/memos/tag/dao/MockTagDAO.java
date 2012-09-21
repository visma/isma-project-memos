package org.isma.memos.tag.dao;

import org.isma.memos.mock.MockXmlParser;
import org.isma.memos.model.Tag;
import org.jdom.Element;

import java.net.URL;
import java.util.List;

public class MockTagDAO implements ITagDAO {
    private MockXmlParser parser = new MockXmlParser();


    public Tag loadAllTags() throws Exception {
        final URL resource = getClass().getResource(getClass().getSimpleName() + ".xml");
        Element rootElement = parser.loadXmlDocument(resource);
        return parser.convertToTag(rootElement);
    }


    public void saveTag(List<Tag> newTagList) {
    }


    public void deleteTag(List<Tag> toDeleteTagList) {
        // Todo
    }
}
