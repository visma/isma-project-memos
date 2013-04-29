package org.isma.memos.edit.tags.gui;

import org.isma.memos.gui.MemoIconEnum;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class TagEditingTreeCellRenderer implements TreeCellRenderer {
    private static final Color FOREGROUND = Color.BLACK;
    private static final Color BACKGROUND = Color.WHITE;


    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        TagTreeNode node = (TagTreeNode)value;
        String text = node.getTag().getLabel();
        final JLabel label = buildLabel(text, getIcon());
        if (selected) {
            label.setForeground(BACKGROUND);
            label.setBackground(FOREGROUND);
            label.setOpaque(true);
        }
        else {
            label.setForeground(FOREGROUND);
            label.setBackground(BACKGROUND);
        }
        return label;
    }


    private ImageIcon getIcon() {
        return MemoIconEnum.TAG_ICON.getImageIcon();
    }


    private JLabel buildLabel(String text, Icon icon) {
        return new JLabel(text, icon, JLabel.HORIZONTAL);
    }
}
