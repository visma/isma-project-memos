package org.isma.memos.results.logic;

import org.isma.memos.model.Memo;

import java.awt.event.ActionListener;

public interface IMemoAction extends ActionListener {
    public void setMemo(Memo memo);
}
