package org.isma.memos.model;

public abstract class AbstractPersistantBean {
    protected Integer id;


    public Integer getId() {
        return id;
    }


    public boolean isNew() {
        return id == null || id == 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractPersistantBean)) {
            return false;
        }

        AbstractPersistantBean bean = (AbstractPersistantBean)o;

        if (id != null ? !id.equals(bean.id) : bean.id != null) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
