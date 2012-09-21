package org.isma.memos.mock;

import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.model.Tag;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MockXmlParser {

    public Element loadXmlDocument(URL resource) throws Exception {
        Document document;
        SAXBuilder sxb = new SAXBuilder();
        document = sxb.build(new File(resource.toURI()));
        return document.getRootElement();
    }


    public Tag convertToTag(Element tagElement) {
        if (!"tag".equals(tagElement.getName())) {
            throw new RuntimeException("ml tag '<tag>' expected, actual <" + tagElement.getName() + ">");
        }
        Tag tag = new Tag(tagElement.getAttribute("label").getValue(), -1);
        List tagChildList = tagElement.getChildren("tag");
        for (Object tagChild : tagChildList) {
            Element tagChildElement = (Element)tagChild;
            Tag child = convertToTag(tagChildElement);
            tag.addChild(child);
        }
        return tag;
    }


    public Memo convertToMemo(Element memoElement) {
        if (!"memo".equals(memoElement.getName())) {
            throw new RuntimeException("xml tag '<memo>' expected, actual <" + memoElement.getName() + ">");
        }
        final String title = memoElement.getAttribute("title").getValue();
        final Element contentElement = memoElement.getChild("content");
        final Element tagElement = memoElement.getChild("tag");
        final String content = contentElement.getContent(0).getValue();
        final Tag tag = convertToTag(tagElement);
        final List<Tag> tagList = extractChildren(tag);
        return new Memo(title, content, tagList, new ArrayList<Attachment>());
    }


    private List<Tag> extractChildren(Tag tag) {
        List<Tag> tagList = new ArrayList<Tag>();
        if (tag.isLeaf()) {
            tagList.add(tag);
            return tagList;
        }
        for (Tag child : tag.getChildList()) {
            tagList.addAll(extractChildren(child));
        }
        return tagList;
    }
}
