package org.isma.memos.model;

import org.isma.utils.Labeleable;
public class Attachment extends AbstractPersistantBean implements Labeleable {
    private Integer idMemo;
    private String name;


    public Attachment(Integer id, Integer idMemo, String name) {
        this.id = id;
        this.idMemo = idMemo;
        this.name = name;
    }


    public Integer getIdMemo() {
        return idMemo;
    }


    public String getName() {
        return name;
    }


    public String getLabel() {
        return getName();
    }


    public void setIdMemo(Integer idMemo) {
        this.idMemo = idMemo;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }
}
