package org.isma.memos.db;

import java.sql.SQLException;
import java.sql.Statement;

public class HSQLHandler extends AbstractSQLHandler {

    public HSQLHandler(AbstractSQLConfiguration configuration) {
        super(configuration);
    }


    @Override
    public void commit() throws SQLException {
        //TODO a virer car le shutdown sert que pour une base mémoire je crois...
//        System.out.println("shutdown HSQL");
//        Statement statement = getConnection().createStatement();
//        statement.executeUpdate("SHUTDOWN");
//        statement.close();
    }

    @Override
    public void end() throws SQLException {
        Statement st = getConnection().createStatement();
        st.getConnection().commit();
        st.execute("SHUTDOWN COMPACT");
        getConnection().close();
    }
}
