package org.isma.memos.model;

import org.isma.utils.Labeleable;

import java.util.List;

public class Memo extends AbstractPersistantBean implements Labeleable {
    private String title;
    private List<Tag> tagList;
    private List<Attachment> attachmentList;
    private String content;


    public Memo(String title, String content, List<Tag> tagList, List<Attachment> attachmentList) {
        this(null, title, content, tagList, attachmentList);
    }


    public Memo(Integer id, String title, String content, List<Tag> tagList, List<Attachment> attachmentList) {
        this.id = id;
        this.title = title;
        this.tagList = tagList;
        this.content = content;
        this.attachmentList = attachmentList;
    }


    public String getLabel() {
        return getTitle();
    }


    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }


    public List<Tag> getTagList() {
        return tagList;
    }


    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }


    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }


    public void setContent(String content) {
        this.content = content;
    }
}
