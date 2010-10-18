package org.kannel.conf;

import java.io.BufferedReader;
import java.net.URL;
import java.util.Map;

/**
 * <db>-connection configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class DbConnectionConfiguration
    extends Configuration
{

    public DbConnectionConfiguration(String type)
    {
	super(type+"-smsc");
    }

    private static final String[] PROP_ORDER = { "id","host","server","username","password","database","tnsname","max-connections" };
    
    public String[] getPropertyOrder() {
	return PROP_ORDER;
    }

    private static final String[] MANDATORY_PROPS = { "id","host","username","password" };

    public String[] getMandatoryProps() {
	return MANDATORY_PROPS;
    }

    /**
     * id
     * An optional name or id to identify this connection for internal reference with other 
     * related configuration groups. Any string is acceptable, but semicolon ';' may cause 
     * problems, so avoid it and any other special non-alphabet characters. 
     */
    private String id;
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }

    /**
     * host
     * Hostname or IP of a server running a database to connect to. 
     */
    private String host;
    public String getHost() { return this.host; }
    public void setHost(String host) { this.host = host; }

    /**
     * server
     * Hostname or IP of a server running a database 
     */
    private String server;
    public String getServer() { return this.server; }
    public void setServer(String server) { this.server = server; }

    /**
     * username
     * User name for connecting to database. 
     */
    private String username;
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    /**
     * password
     * Password for connecting to database. 
     */
    private String password;
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * database
     * Name of database in database server to connect to. 
     */
    private String database;
    public String getDatabase() { return this.database; }
    public void setDatabase(String database) { this.database = database; }

    /**
     * tnsname
     * Name of oracle sqlnet configuration 
     */
    private String tnsname;
    public String getTnsname() { return this.tnsname; }
    public void setTnsname(String tnsname) { this.tnsname = tnsname; }

    /**
     * max-connections
     * How many connections should be opened to the given database. This is used for database 
     * pool. 
     */
    private Integer maxConnections;
    public Integer getMaxConnections() { return this.maxConnections; }
    public void setMaxConnections(Integer maxConnections) { this.maxConnections = maxConnections; }

}
