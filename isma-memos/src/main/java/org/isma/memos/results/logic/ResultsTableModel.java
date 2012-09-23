package org.isma.memos.results.logic;

import org.isma.core.utils.JoinLabeleable;
import org.isma.guitoolkit.error.ErrorHandler;
import org.isma.memos.model.Memo;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ResultsTableModel extends DefaultTableModel {
    public static final String TITLE_COLUMN_NAME = "Title";
    public static final String TAGS_COLUMN_NAME = "Tags";
    public static final String ATTACH_COLUMN_NAME = "Attachments";
    private static final String[] COLUMN_NAMES = new String[]{TITLE_COLUMN_NAME, TAGS_COLUMN_NAME, ATTACH_COLUMN_NAME};
    private List<Memo> memos;


    public ResultsTableModel(List<Memo> memos) {
        super(convertToDoubleArray(memos), COLUMN_NAMES);
        this.memos = memos;
    }


    private static String[][] convertToDoubleArray(List<Memo> pMemos) {
        String[][] strings = new String[pMemos.size()][COLUMN_NAMES.length];
        int rowIdx = 0;
        for (Memo memo : pMemos) {
            String tagsLabel = JoinLabeleable.join(memo.getTags());
            String title = memo.getTitle();
            strings[rowIdx][0] = title;
            strings[rowIdx][1] = tagsLabel;
            strings[rowIdx][2] = Integer.toString(memo.getAttachments().size());
            rowIdx++;
        }
        return strings;
    }


    @Override
    public Memo getValueAt(int row, int column) {
        try {

            if (row >= 0) {//if ctrl + click on table, row value is -1 (??)
                return memos.get(row);
            }
        }
        catch (Exception e) {
            ErrorHandler.handleException(e);
        }
        return null;
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
