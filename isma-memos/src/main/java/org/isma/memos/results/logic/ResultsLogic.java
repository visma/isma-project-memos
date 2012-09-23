package org.isma.memos.results.logic;

import org.isma.core.ApplicationContext;
import org.isma.core.logic.AbstractLogic;
import org.isma.core.logic.LogicGlassedActionListener;
import org.isma.guitoolkit.error.ErrorHandler;
import org.isma.memos.MemoManagerConfiguration;
import org.isma.memos.model.Memo;
import org.isma.memos.results.actions.DeleteAction;
import org.isma.memos.results.gui.ResultsForm;
import org.isma.memos.results.manager.MemoManager;
import org.isma.memos.search.logic.ISearchParams;
import org.isma.memos.tag.manager.TagManager;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ResultsLogic extends AbstractLogic<MemoManagerConfiguration> implements ActionListener, IResults {
    private TagManager tagManager;
    private MemoManager memoManager;
    private ResultsForm form = new ResultsForm();
    private ISearchParams searchParams;
    private ListSelectionListener listSelectionListener;
    private IMemoAction memoEditAction;


    public ResultsLogic(ApplicationContext<MemoManagerConfiguration> context,
                        TagManager tagManager,
                        MemoManager memoManager,
                        ListSelectionListener listSelectionListener,
                        IMemoAction memoEditAction) {
        super(context);
        this.tagManager = tagManager;
        this.memoManager = memoManager;
        this.listSelectionListener = listSelectionListener;
        this.memoEditAction = memoEditAction;
        form.getResultsTable().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addListeners();
    }


    public void clearResults() {
        loadTable(new ArrayList<Memo>());
    }


    public boolean isCleared() {
        return form.getResultsTable().getRowCount() == 0;
    }


    private void addListeners() {
        form.getResultsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouveEvent) {
                if (mouveEvent.getButton() == MouseEvent.BUTTON1 && mouveEvent.getClickCount() == 2) {
                    int row = form.getResultsTable().rowAtPoint(mouveEvent.getPoint());
                    ResultsTableModel model = (ResultsTableModel)form.getResultsTable().getModel();
                    Memo memo = model.getValueAt(row, 0);
                    new MemoTableEditionAction(memo, row).actionPerformed(null);
                }
            }


            @Override
            public void mouseReleased(MouseEvent mouveEvent) {
                if (mouveEvent.getButton() == MouseEvent.BUTTON3) {
                    int row = form.getResultsTable().rowAtPoint(mouveEvent.getPoint());
                    ResultsTableModel model = (ResultsTableModel)form.getResultsTable().getModel();
                    Memo memo = model.getValueAt(row, 0);
                    JPopupMenu menu = new JPopupMenu();

                    JMenuItem modifyItem = new JMenuItem("Modify");
                    modifyItem.addActionListener(new MemoTableEditionAction(memo, row));
                    JMenuItem deleteItem = new JMenuItem("Delete");
                    deleteItem.addActionListener(new DeleteAction(context,
                                                                  memoManager,
                                                                  memo,
                                                                  form.getResultsTable(),
                                                                  row));

                    menu.add(modifyItem);
                    menu.add(deleteItem);
                    menu.show(form.getResultsTable(), mouveEvent.getX(), mouveEvent.getY());
                }
            }
        });
    }


    class MemoTableEditionAction implements ActionListener {
        private Memo memo;
        private int row;


        MemoTableEditionAction(Memo memo, int row) {
            this.memo = memo;
            this.row = row;
        }


        public void actionPerformed(ActionEvent e) {
            try {
                memoEditAction.setMemo(memo);
                memoEditAction.actionPerformed(e);
                //TODO à declencher de préférence seulement si le preview affiche le memo en edition
                listSelectionListener.valueChanged(null);
                ((DefaultTableModel)form.getResultsTable().getModel()).fireTableRowsUpdated(row, row);
            }
            catch (Exception ex) {
                ErrorHandler.handleException(ex);
            }
        }
    }


    private void removeSelectionListener() {
        final JTable table = form.getResultsTable();
        table.getSelectionModel().removeListSelectionListener(listSelectionListener);
        table.getSelectionModel().removeSelectionInterval(0, table.getRowCount() - 1);
    }


    private void addSelectionListener() {
        form.getResultsTable().getSelectionModel().addListSelectionListener(listSelectionListener);
    }


    public JPanel getGui() {
        return form.getMainPanel();
    }


    public void actionPerformed(ActionEvent e) {
        new LogicGlassedActionListener(context) {
            @Override
            protected void doAction() throws Exception {
                List<Memo> memoList = memoManager.search(tagManager.getRootTag(),
                                                         searchParams.getTitle(),
                                                         searchParams.getContent(),
                                                         searchParams.getTags());
                loadTable(memoList);
            }
        }.actionPerformed(e);
    }


    private void loadTable(List<Memo> memos) {
        removeSelectionListener();
        final JTable table = form.getResultsTable();
        new ResultsTableBuilder().build(table, memos);
        addSelectionListener();
    }


    public void setSearchParams(ISearchParams searchLogic) {
        this.searchParams = searchLogic;
    }


    public Memo getSelection() {
        int selectedRow = form.getResultsTable().getSelectedRow();
        return (Memo)form.getResultsTable().getModel().getValueAt(selectedRow, 0);
    }
}
