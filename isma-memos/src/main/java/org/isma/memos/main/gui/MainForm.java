package org.isma.memos.main.gui;

import javax.swing.*;

public class MainForm {
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JPanel bottomPanel;


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public JPanel getSearchPanel() {
        //TODO un ces 4, mettre ça dans un endroit plus propre que le getter
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        return searchPanel;
    }


    public JPanel getBottomPanel() {
        //TODO un ces 4, mettre ça dans un endroit plus propre que le getter
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        return bottomPanel;
    }
}
