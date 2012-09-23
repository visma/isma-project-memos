package org.isma.memos.model;

import org.isma.utils.Labeleable;

import java.util.List;

public class Memo extends AbstractPersistantBean implements Labeleable {
    private String title;
    private List<Tag> tags;
    private List<Attachment> attachments;
    private String content;


    public Memo(String title, String content, List<Tag> tags, List<Attachment> attachments) {
        this(null, title, content, tags, attachments);
    }


    public Memo(Integer id, String title, String content, List<Tag> tags, List<Attachment> attachments) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.content = content;
        this.attachments = attachments;
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


    public List<Tag> getTags() {
        return tags;
    }


    public List<Attachment> getAttachments() {
        return attachments;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }


    public void setContent(String content) {
        this.content = content;
    }
}
