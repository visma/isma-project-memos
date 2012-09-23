package org.isma.memos.tag;

import org.isma.guitoolkit.components.checkboxtree.CheckableNode;
import org.isma.memos.model.Tag;

public class TagCheckableNodeBuilder {

    private TagCheckableNodeBuilder() {
    }


    public static CheckableNode convertIntoCheckableNode(Tag tag) {
        CheckableNode tagNode = new CheckableNode(tag);
        for (Tag subTag : tag.getChildren()) {
            tagNode.addChild(convertIntoCheckableNode(subTag));
        }
        return tagNode;
    }
}
