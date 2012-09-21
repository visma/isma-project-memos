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
    private List<Memo> memoList;


    public ResultsTableModel(List<Memo> memoList) {
        super(convertToDoubleArray(memoList), COLUMN_NAMES);
        this.memoList = memoList;
    }


    private static String[][] convertToDoubleArray(List<Memo> memoList) {
        String[][] strings = new String[memoList.size()][COLUMN_NAMES.length];
        int rowIdx = 0;
        for (Memo memo : memoList) {
            String tagsLabel = JoinLabeleable.join(memo.getTagList());
            String title = memo.getTitle();
            strings[rowIdx][0] = title;
            strings[rowIdx][1] = tagsLabel;
            strings[rowIdx][2] = Integer.toString(memo.getAttachmentList().size());
            rowIdx++;
        }
        return strings;
    }


    @Override
    public Memo getValueAt(int row, int column) {
        try {

            if (row >= 0) {//if ctrl + click on table, row value is -1 (??)
                return memoList.get(row);
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
