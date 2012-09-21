package org.isma.memos.db;

import java.io.InputStream;
import java.util.Properties;
public abstract class AbstractSQLConfiguration implements ISQLConfiguration {
    final protected Properties props;


    protected AbstractSQLConfiguration() throws Exception {
        String filename = getClass().getSimpleName() + ".properties";
        InputStream stream = Class.forName(getClass().getName()).getResourceAsStream(filename);
        props = new Properties();
        props.load(stream);
    }


    public String getURL() {
        return props.getProperty("database.jdbc.url");
    }


    public String getUser() {
        return props.getProperty("database.user");
    }


    public String getPassword() {
        return props.getProperty("database.password");
    }


    public String getDriverClassName() {
        return props.getProperty("database.jdbc.driver.classname");
    }
}
