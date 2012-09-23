package org.isma.memos.model;

import org.isma.utils.Labeleable;

import java.util.ArrayList;
import java.util.List;

public class Tag extends AbstractPersistantBean implements Labeleable {
    private String label;
    private Tag parent;
    private final List<Tag> children = new ArrayList<Tag>();


    public Tag(String label, Integer id) {
        this.label = label;
        this.id = id;
    }


    public Tag(String label) {
        this.label = label;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void addChild(Tag child) {
        children.add(child);
        child.parent = this;
    }


    public void removeChild(Tag child) {
        children.remove(child);
        child.parent = null;
    }


    public String getLabel() {
        return label;
    }


    public Tag getParent() {
        return parent;
    }


    public List<Tag> getChildren() {
        return children;
    }


    public boolean hasChildren() {
        return !children.isEmpty();
    }


    public boolean isRoot() {
        return parent == null;
    }


    public boolean isLeaf() {
        return children.isEmpty();
    }


    public Tag[] getPath() {
        List<Tag> paths = new ArrayList<Tag>();
        Tag currentTag = this;
        paths.add(this);
        while (currentTag.getParent() != null) {
            currentTag = currentTag.getParent();
            paths.add(0, currentTag);
        }
        return paths.toArray(new Tag[]{});
    }

    public Tag getChild(int idTag) {
        if (getId().intValue() == idTag) {
            return this;
        }
        for (Tag child : children) {
            Tag found = child.getChild(idTag);
            if (found != null) {
                return found;
            }
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Tag tag = (Tag)o;

        if (label != null ? !label.equals(tag.label) : tag.label != null) {
            return false;
        }
        if (parent != null ? !parent.equals(tag.parent) : tag.parent != null) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }


    public void setLabel(String label) {
        this.label = label;
    }
}
