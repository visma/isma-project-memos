package org.isma.memos.results.logic;

import org.isma.core.utils.JoinLabeleable;
import org.isma.memos.gui.MemoIconEnum;
import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

import static org.isma.memos.results.logic.ResultsTableModel.*;
public class ResultsTableBuilder {

    public void build(JTable table, List<Memo> memoList) {
        table.setModel(new ResultsTableModel(memoList));
        table.getColumn(TITLE_COLUMN_NAME).setCellRenderer(new TitleTableCellRenderer());
        table.getColumn(TAGS_COLUMN_NAME).setCellRenderer(new TagsTableCellRenderer());

        table.getColumn(ATTACH_COLUMN_NAME).setCellRenderer(new AttachmentTableCellRenderer());
        table.getColumn(ATTACH_COLUMN_NAME).setWidth(30);

        final TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        table.getColumn(ATTACH_COLUMN_NAME).setHeaderRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int column) {
                final JLabel label = (JLabel)headerRenderer.getTableCellRendererComponent(table,
                                                                                          "",
                                                                                          isSelected,
                                                                                          hasFocus,
                                                                                          row,
                                                                                          column);
                label.setIcon(MemoIconEnum.ATTACHMENT_ICON.getImageIcon());
                return label;
            }
        });
    }


    private static class AttachmentTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            List<Attachment> attachmentList = ((Memo)value).getAttachmentList();
            String amount = attachmentList.isEmpty() ? "" : Integer.toString(attachmentList.size());
            return super.getTableCellRendererComponent(table,
                                                       amount,
                                                       isSelected,
                                                       hasFocus,
                                                       row,
                                                       column);
        }
    }

    private static class TitleTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            return super.getTableCellRendererComponent(table,
                                                       ((Memo)value).getTitle(),
                                                       isSelected,
                                                       hasFocus,
                                                       row,
                                                       column);
        }
    }

    private static class TagsTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            String tagLabel = JoinLabeleable.join(((Memo)value).getTagList());
            return super.getTableCellRendererComponent(table,
                                                       tagLabel,
                                                       isSelected,
                                                       hasFocus,
                                                       row,
                                                       column);
        }
    }
}
