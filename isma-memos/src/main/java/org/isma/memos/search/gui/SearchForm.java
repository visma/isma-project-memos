package org.isma.memos.search.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class SearchForm implements IForm {
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JTextField titleSearchTextField;
    private JTextField contentSearchTextField;
    private JButton searchButton;
    private JTree tagTree;


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public JPanel getSearchPanel() {
        return searchPanel;
    }


    public JTextField getTitleSearchTextField() {
        return titleSearchTextField;
    }


    public JTextField getContentSearchTextField() {
        return contentSearchTextField;
    }


    public JTree getTagTree() {
        return tagTree;
    }


    public JButton getSearchButton() {
        return searchButton;
    }
}
