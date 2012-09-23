package org.isma.memos.model;
import junit.framework.TestCase;

public class TagTest extends TestCase {

    public void testIsRoot() {
        Tag tag = new Tag("toto");
        Tag parent = new Tag("root");
        assertTrue(tag.isRoot());
        assertTrue(parent.isRoot());

        parent.addChild(tag);

        assertFalse(tag.isRoot());
        assertTrue(parent.isRoot());
    }


    public void testIsLeaf() {
        Tag tag = new Tag("toto");
        Tag parent = new Tag("parent");

        parent.addChild(tag);

        assertTrue(tag.isLeaf());
        assertFalse(parent.isLeaf());
    }


    public void testAddChild() throws Exception {
        Tag tag = new Tag("parent");

        assertEquals(0, tag.getChildren().size());
        assertFalse(tag.hasChildren());

        tag.addChild(new Tag("child1"));

        assertTrue(tag.hasChildren());

        tag.addChild(new Tag("child2"));

        assertEquals(2, tag.getChildren().size());
        assertEquals("child1", tag.getChildren().get(0).getLabel());
        assertEquals("child2", tag.getChildren().get(1).getLabel());
    }


    public void testPath() throws Exception {
        Tag root = new Tag("root");
        Tag nv1 = new Tag("nv1");
        Tag nv1b = new Tag("nv1b");
        Tag nv2 = new Tag("nv2");
        Tag nv2b = new Tag("nv2b");

        root.addChild(nv1);
        root.addChild(nv1b);

        nv1.addChild(nv2);
        nv1b.addChild(nv2b);

        final Tag[] path = nv2.getPath();
        final Tag[] pathB = nv2b.getPath();

        assertEquals(3, path.length);
        assertEquals(root, path[0]);
        assertEquals(nv1, path[1]);
        assertEquals(nv2, path[2]);

        assertEquals(3, pathB.length);
        assertEquals(root, pathB[0]);
        assertEquals(nv1b, pathB[1]);
        assertEquals(nv2b, pathB[2]);
    }


    public void testGetChild() throws Exception {
        Tag root = new Tag("root", 1);
        root.addChild(new Tag("root-child1", 2));
        root.addChild(new Tag("root-child2", 3));

        Tag rootChild1 = root.getChild(2);
        Tag rootChild2 = root.getChild(3);
        assertEquals("root-child1", rootChild1.getLabel());
        assertEquals("root-child2", rootChild2.getLabel());

        rootChild1.addChild(new Tag("root-child1-child1", 4));
        rootChild1.addChild(new Tag("root-child1-child2", 5));

        Tag rootChild1Child1 = root.getChild(4);
        Tag rootChild1Child2 = root.getChild(5);
        assertEquals("root-child1-child1", rootChild1Child1.getLabel());
        assertEquals("root-child1-child2", rootChild1Child2.getLabel());


    }
}
