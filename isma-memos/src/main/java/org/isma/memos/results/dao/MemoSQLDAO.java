package org.isma.memos.results.dao;

import org.apache.commons.lang.StringUtils;
import org.isma.memos.db.AbstractSQLDAO;
import org.isma.memos.db.AbstractSQLHandler;
import org.isma.memos.model.Attachment;
import org.isma.memos.model.Memo;
import org.isma.memos.model.Tag;
import org.isma.utils.Labeleable.LabeleableComparator;
import org.isma.utils.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MemoSQLDAO extends AbstractSQLDAO implements IMemoDAO {

    public MemoSQLDAO(AbstractSQLHandler sqlHandler) {
        super(sqlHandler);
    }


    private String joinIdTags(List<Tag> searchedTagList) {
        String[] ids = new String[searchedTagList.size()];
        int index = 0;
        for (Tag tag : searchedTagList) {
            ids[index++] = Integer.toString(tag.getId());
        }
        return StringUtils.join(ids, ", ");
    }


    private String buildSearchTitleClause(String searchedTitle) {
        if (searchedTitle.trim().length() < 1) {
            return "";
        }
        return " LOWER(M.TITLE) like '%" + searchedTitle.toLowerCase() + "%' ";
    }


    private String buildSearchTagsClause(List<Tag> searchedTagList) {
        if (searchedTagList.isEmpty()) {
            return "";
        }
        return searchedTagList.size() + " = (select count(1) from MEMO_TAGS where ID_MEMO = M.ID and ID_TAG in ("
               + joinIdTags(searchedTagList) + ")) ";
    }


    private String buildSearchContentClause(String searchedContent) {
        final String[] words = StringUtils.split(searchedContent);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String word : words) {
            if (i++ != 0) {
                sb.append(" and ");
            }
            sb.append(" LOWER(M.CONTENT) like '%").append(word).append("%' ");
        }
        return sb.toString();
    }


    private String buildSearchWhereClause(String searchedTitle, String searchedContent, List<Tag> searchedTagList) {
        String titleClause = buildSearchTitleClause(searchedTitle);
        String contentClause = buildSearchContentClause(searchedContent);
        String tagClause = buildSearchTagsClause(searchedTagList);
        String whereClause = titleClause;
        if (whereClause.length() > 0 && contentClause.length() > 0) {
            whereClause = whereClause + " and " + contentClause;
        }
        else {
            whereClause = whereClause + contentClause;
        }
        if (whereClause.length() > 0 && tagClause.length() > 0) {
            whereClause = whereClause + " and " + tagClause;
        }
        else {
            whereClause = whereClause + tagClause;
        }
        return whereClause.length() > 0 ? " where " + whereClause : "";
    }


    //TODO afficher les resultats si les tags fils correspondent aux criteres title/content
    public List<Memo> search(Tag rootTag, String searchedTitle, String searchedContent, List<Tag> searchedTagList)
          throws Exception {
        Set<Memo> memoSet = new TreeSet<Memo>(new LabeleableComparator());

        //Pas possible de faire de distinct avec le clob
        String sql = "SELECT M.ID, M.TITLE, M.CONTENT FROM MEMO M left join MEMO_TAGS MT on M.ID = MT.ID_MEMO"
                     + buildSearchWhereClause(searchedTitle, searchedContent, searchedTagList)
                     + "  ORDER BY M.TITLE";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int idMemo = rs.getInt("ID");
            String title = rs.getString("TITLE");
            List<Tag> selectedTagList = getTagListForMemo(rootTag, idMemo);
            List<Attachment> attachmentList = getAttachmentListForMemo(idMemo);
            final String content = clobToString(rs.getClob("CONTENT"));
            Memo memo = new Memo(idMemo, title, content, selectedTagList, attachmentList);
            memoSet.add(memo);
        }
        return new ArrayList<Memo>(memoSet);
    }


    public Memo saveMemo(String title, String content, List<Tag> tagList, List<Attachment> attachmentList)
          throws Exception {
        int idMemo = saveMemo(title, content);
        saveTagListForMemo(idMemo, tagList);
        saveAttachmentListForMemo(idMemo, attachmentList);
        commit();
        return new Memo(idMemo, title, content, tagList, attachmentList);
    }


    private List<Tag> getTagListForMemo(Tag rootTag, int idMemo) throws SQLException {
        PreparedStatement pst = getConnection().prepareStatement(
              "SELECT T.ID, T.ID_PARENT, T.NAME FROM MEMO_TAGS MT inner join TAG T on T.ID = MT.ID_TAG where MT.ID_MEMO = ?");
        pst.setInt(1, idMemo);
        ResultSet rs = pst.executeQuery();
        List<Tag> memoTagList = new ArrayList<Tag>();
        while (rs.next()) {
            int id = rs.getInt("ID");
            Tag memoTag = rootTag.getChild(id);
            if (memoTag == null) {
                throw new RuntimeException("memo tag not found !");
            }
            memoTagList.add(memoTag);
        }
        return memoTagList;
    }


    private List<Attachment> getAttachmentListForMemo(int idMemo) throws SQLException {
        List<Attachment> beanList = new ArrayList<Attachment>();
        PreparedStatement pst = getConnection().prepareStatement(
              "SELECT A.ID, A.NAME FROM ATTACHMENT A where A.ID_MEMO = ?");
        pst.setInt(1, idMemo);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("NAME");
            beanList.add(new Attachment(id == 0 ? null : id, idMemo, name));
        }
        return beanList;
    }


    private void saveTagListForMemo(int idMemo, List<Tag> tagList) throws SQLException {
        for (Tag tag : tagList) {
            saveTagForMemo(idMemo, tag);
        }
    }


    private void saveAttachmentListForMemo(int idMemo, List<Attachment> attachmentList) throws Exception {
        for (Attachment attachment : attachmentList) {
            attachment.setIdMemo(idMemo);
            saveAttachmentForMemo(attachment);
        }
    }


    private void saveAttachmentForMemo(Attachment attachment) throws Exception {
        File file = new File(attachment.getName());

        PreparedStatement stmt = getConnection().prepareStatement(
              "INSERT INTO Attachment (id_memo, name, content) values (?, ?, ?)",
              Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, attachment.getIdMemo());
        stmt.setString(2, file.getName());

        stmt.setBinaryStream(3, new FileInputStream(file), file.length());

        stmt.executeUpdate();

        final ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        attachment.setId(generatedKeys.getInt(1));
        attachment.setName(file.getName());
    }


    private void saveTagForMemo(int idMemo, Tag tag) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(
              "INSERT INTO MEMO_TAGS (id_memo, id_tag) values (?, ?)",
              Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, idMemo);
        stmt.setInt(2, tag.getId());
        stmt.executeUpdate();
    }


    private int saveMemo(String title, String content) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO MEMO (title, content) values (?, ?)",
                                                                  Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, title);
        final Clob clob = getConnection().createClob();
        clob.setString(1, content);
        stmt.setClob(2, clob);
        stmt.executeUpdate();

        final ResultSet generatedKeys = stmt.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }


    public File loadAttachement(Attachment attachment, File attachmentTmpDirectory) throws Exception {
        //Pas possible de faire de distinct avec le clob
        String sql = "SELECT NAME, CONTENT FROM ATTACHMENT WHERE ID = ?";
        //System.out.println(sql);
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, attachment.getId());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String name = rs.getString("NAME");
        InputStream is = rs.getBlob("CONTENT").getBinaryStream();

        File tmp = new File(attachmentTmpDirectory, name);
        FileUtils.writeToFile(tmp.getAbsolutePath(), is);
        return tmp;
    }


    public void deleteMemo(Integer idMemo) throws Exception {
        PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM ATTACHMENT WHERE ID_MEMO = ?");
        stmt.setInt(1, idMemo);
        stmt.executeUpdate();

        stmt = getConnection().prepareStatement("DELETE FROM MEMO_TAGS WHERE ID_MEMO = ?");
        stmt.setInt(1, idMemo);
        stmt.executeUpdate();

        stmt = getConnection().prepareStatement("DELETE FROM MEMO WHERE ID = ?");
        stmt.setInt(1, idMemo);
        stmt.executeUpdate();

        commit();
    }
}
