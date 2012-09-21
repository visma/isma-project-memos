package org.isma.memos.edit.tags.logic;

import org.isma.memos.edit.tags.gui.TagTreeNode;
import org.isma.memos.model.Tag;

public class TagTreeNodeBuilder {

    public TagTreeNode buildRootTagNode(Tag rootTag) {
        TagTreeNode rootNode = new TagTreeNode(rootTag);
        for (Tag tag : rootTag.getChildList()) {
            if (tag.hasChildren()) {
                rootNode.addChild(buildRootTagNode(tag));
            }
            else {
                rootNode.addChild(new TagTreeNode(tag));
            }
        }
        return rootNode;
    }
}
