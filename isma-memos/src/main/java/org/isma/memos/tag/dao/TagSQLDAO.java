package org.isma.memos.tag.dao;

import org.isma.memos.db.AbstractSQLDAO;
import org.isma.memos.db.AbstractSQLHandler;
import org.isma.memos.model.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TagSQLDAO extends AbstractSQLDAO implements ITagDAO {
    private TagDTOFactory tagDTOFactory = new TagDTOFactory();


    public TagSQLDAO(AbstractSQLHandler sqlHandler) {
        super(sqlHandler);
    }


    public Tag loadAllTags() throws Exception {
        List<TagDTO> tagDTOs = new ArrayList<TagDTO>();
        PreparedStatement pst = getConnection().prepareStatement("SELECT ID, ID_PARENT, NAME FROM TAG order by ID");
        pst.clearParameters();
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            int idParent = rs.getInt("ID_PARENT");
            String name = rs.getString("NAME");
            tagDTOs.add(new TagDTO(id, idParent == 0 ? null : idParent, name));
        }
        return tagDTOFactory.createRootTag(tagDTOs);
    }


    public void saveTag(List<Tag> newTags) throws Exception {
        for (Tag tag : newTags) {
            saveTag(tag);
        }
        commit();
    }


    public void deleteTag(List<Tag> toDeleteTags) throws Exception {
        for (Tag tag : toDeleteTags) {
            deleteTag(tag);
        }
        commit();
    }


    private void deleteTag(Tag tag) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM TAG where ID= ?");
        stmt.setInt(1, tag.getId());
        stmt.executeUpdate();
        tag.setId(0);
    }


    private void saveTag(Tag tag) throws Exception {
        PreparedStatement stmt;
        if (tag.isNew()) {
            stmt = getConnection().prepareStatement("INSERT INTO TAG (NAME, ID_PARENT) values (?, ?)",
                                                    Statement.RETURN_GENERATED_KEYS);
        }
        else {
            stmt = getConnection().prepareStatement("UPDATE TAG SET NAME = ?, ID_PARENT = ? where ID = ?",
                                                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(3, tag.getId());
        }
        stmt.setString(1, tag.getLabel());
        stmt.setInt(2, tag.getParent().getId());
        stmt.executeUpdate();
        if (tag.isNew()) {
            final ResultSet generatedKeys = stmt.getGeneratedKeys();
            generatedKeys.next();
            tag.setId(generatedKeys.getInt(1));
        }
    }
}
