package org.isma.memos.tag.dao;

public class TagDTO {
    private int id;
    private Integer idParent;
    private String name;


    public TagDTO(int id, Integer idParent, String name) {
        this.id = id;
        this.idParent = idParent;
        this.name = name;
    }


    public int getId() {
        return id;
    }


    public Integer getIdParent() {
        return idParent;
    }


    public String getName() {
        return name;
    }
}
