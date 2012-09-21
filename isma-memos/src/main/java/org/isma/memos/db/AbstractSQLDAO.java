package org.isma.memos.db;

import java.io.BufferedReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractSQLDAO {
    private AbstractSQLHandler sqlHandler;


    protected AbstractSQLDAO(AbstractSQLHandler sqlHandler) {
        this.sqlHandler = sqlHandler;
    }


    protected Connection getConnection() throws SQLException {
        return sqlHandler.getConnection();
    }


    protected String clobToString(Clob clob) throws Exception {
        final BufferedReader br = new BufferedReader(clob.getCharacterStream());
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            if (sb.length() == 0) {
                sb.append(line);
            }
            else {
                sb.append("\n").append(line);
            }
        }
        return sb.toString();
    }


    protected void commit() throws SQLException {
        sqlHandler.commit();
    }
}
