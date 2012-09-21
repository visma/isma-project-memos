package org.isma.memos.results.actions;

import org.isma.core.ApplicationContext;
import org.isma.core.logic.LogicGlassedActionListener;
import org.isma.memos.model.Memo;
import org.isma.memos.results.manager.MemoManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DeleteAction extends LogicGlassedActionListener {
    private MemoManager memoManager;
    private Memo memo;
    private JTable resultsTable;
    private int row;


    public DeleteAction(ApplicationContext context, MemoManager memoManager, Memo memo, JTable resultsTable, int row) {
        super(context);
        this.memoManager = memoManager;
        this.memo = memo;
        this.resultsTable = resultsTable;
        this.row = row;
    }


    @Override
    protected void doAction() throws Exception {
        memoManager.deleteMemo(memo);
        ((DefaultTableModel)resultsTable.getModel()).removeRow(row);
    }
}
