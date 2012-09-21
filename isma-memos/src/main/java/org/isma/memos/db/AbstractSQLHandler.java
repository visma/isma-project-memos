package org.isma.memos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public abstract class AbstractSQLHandler {
    private Connection connection;
    private ISQLConfiguration conf;


    protected AbstractSQLHandler(ISQLConfiguration configuration) {
        this.conf = configuration;
    }


    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = buildConnection();
        }
        return connection;
    }


    private Connection buildConnection() throws SQLException {
        System.out.printf("create connection(URL=%s, USER=%s, PASSWORD=%s)\n",
                          conf.getURL(),
                          conf.getUser(),
                          conf.getPassword());
        return DriverManager.getConnection(conf.getURL(), conf.getUser(), conf.getPassword());
    }


    public ISQLConfiguration getConf() {
        return conf;
    }


    public abstract void commit() throws SQLException;


    public void buildDriver() throws Exception {
        Class.forName(getConf().getDriverClassName()).newInstance();
    }
}
