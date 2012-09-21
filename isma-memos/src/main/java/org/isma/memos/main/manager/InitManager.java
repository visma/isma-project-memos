package org.isma.memos.main.manager;

import org.isma.memos.main.dao.IInitDAO;

public class InitManager {
    private IInitDAO initDAO;


    public InitManager(IInitDAO initDAO) {
        this.initDAO = initDAO;
    }


    public void init() throws Exception {
        initDAO.init();
    }
}
