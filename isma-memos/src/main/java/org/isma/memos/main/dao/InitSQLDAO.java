package org.isma.memos.main.dao;

import org.isma.memos.db.AbstractSQLDAO;
import org.isma.memos.db.AbstractSQLHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InitSQLDAO extends AbstractSQLDAO implements IInitDAO {

    public InitSQLDAO(AbstractSQLHandler sqlHandler) throws Exception {
        super(sqlHandler);
        sqlHandler.buildDriver();
    }


    public void init() throws Exception {
        List<String> tables = loadTables();
        createTablesIfNotExists(tables);
        commit();

    }


    private List<String> loadTables() throws SQLException {
        List<String> tables = new ArrayList<String>();
        PreparedStatement pst = getConnection().prepareStatement(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE = 'TABLE'");
        pst.clearParameters();
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            tables.add(rs.getString(1).toUpperCase());
        }
        return tables;
    }


    private void createTablesIfNotExists(List<String> tables) throws Exception {
        System.out.println("creating tables (if not already exists)...");
        if (!tables.contains("TAG")) {
            createTableTag();
        }
        if (!tables.contains("MEMO")) {
            createTableMemo();
        }
        if (!tables.contains("ATTACHMENT")) {
            createTableAttachment();
        }
        if (!tables.contains("MEMO_TAGS")) {
            createTableMemoTags();
        }
    }


    private void createTableAttachment() throws SQLException {
        System.out.println("creating table attachment...");
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate(
                "create table attachment ("
                        + "id int GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY, "
                        + "id_memo int, "
                        + "name varchar(255), "
                        + "content blob, "
                        + "FOREIGN KEY(id_memo) REFERENCES memo (id))");
    }


    private void createTableMemoTags() throws SQLException {
        System.out.println("creating table memo_tags...");
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate(
                "create table memo_tags ("
                        + "id_memo int, "
                        + "id_tag int, "
                        + "FOREIGN KEY(id_memo) REFERENCES memo (id), "
                        + "FOREIGN KEY(id_tag) REFERENCES tag (id))");
    }


    private void createTableMemo() throws SQLException {
        System.out.println("creating table memo...");
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate(
                "create table memo ("
                        + "id int GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY, "
                        + "content longvarchar, "
                        + "title varchar(255))");
    }


    private void createTableTag() throws Exception {
        System.out.println("creating table tag...");
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate(
                "create table tag ("
                        + "id int GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY, "
                        + "id_parent int, "
                        + "name varchar(50),"
                        + "FOREIGN KEY(id_parent) REFERENCES tag (id))");
        populateTags();
    }


    private void populateTags() throws Exception {
        PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO TAG (name) values (?)");
        stmt.setString(1, "root");
        stmt.executeUpdate();
        commit();
    }
}
