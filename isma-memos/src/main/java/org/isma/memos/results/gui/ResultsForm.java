package org.isma.memos.results.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class ResultsForm implements IForm {
    private JPanel mainPanel;
    private JTable resultsTable;


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public JTable getResultsTable() {
        return resultsTable;
    }
}
