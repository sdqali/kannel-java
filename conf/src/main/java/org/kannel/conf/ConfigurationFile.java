package org.kannel.conf;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Configuration file holds all configurations and does reading and
 * writing of the configuration file.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class ConfigurationFile
{

    private CoreConfiguration coreConfiguration;
    public CoreConfiguration getCoreConfiguration() { return this.coreConfiguration; }

    private Collection<SmppSmscConfiguration> smppSmscConfigurations;
    public Collection<SmppSmscConfiguration> getSmppSmscConfigurations() { return this.smppSmscConfigurations; }
    public void addSmppSmscConfiguration(SmppSmscConfiguration smppSmscConfiguration) {
	if (smppSmscConfigurations == null) {
	    smppSmscConfigurations = new HashSet<SmppSmscConfiguration>();
	}
	smppSmscConfigurations.add(smppSmscConfiguration);
    }

    // private WapboxConfiguration wapboxConfiguration;
    // private SmsboxConfiguration smsboxConfiguration;
    // private SmsboxRouteConfiguration smsboxRouteConfiguration;
    // private SmsServiceConfiguration smsServiceConfiguration;
    // private SendsmsUserConfiguration SendsmsUserConfiguration;
    // private OtaSettingConfiguration otaSettingConfiguration;
    // private OtaBookmarkConfiguration otaBookmarkConfiguration;
    // private PpgConfiguration ppgConfiguration;
    // private WapPushUserConfiguration wapPushUserConfiguration;
    // private RadiusAccountConfiguration radiusAccountConfiguration;
    // private SmppTlvConfiguration SmppTlvConfiguration;
    // private ModemsConfiguration modemsConfiguration;
    // private MysqlConnectionConfiguration mysqlConnectionConfiguration;
    // private OracleConnectionConfiguration oracleConnectionConfiguration;
    // private PgsqlConnectionConfiguration pgsqlConnectionConfiguration;
    // private MssqlConnectionConfiguration mssqlConnectionConfiguration;
    // private SdbConnectionConfiguration sdbConnectionConfiguration;
    // private DlrDbConfiguration dlrDbConfiguration;

    public ConfigurationFile(File file)
    {
     	this.file = file;
    }

    private File file;

    public synchronized void load() throws Exception
    {
	FileInputStream fis = new FileInputStream(file);
	DataInputStream in = new DataInputStream(fis);
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	String line;
	while ((line = br.readLine()) != null)   {
	    if (line == null || line.trim().equals("") || line.trim().startsWith("#")) {
		//
	    } else if (line.startsWith("group")) {
		int pos = line.indexOf("=");
		String group = line.substring(pos+1).trim();
		if (group.equals("core")) {
		    coreConfiguration = new CoreConfiguration();
		    coreConfiguration.readConfiguration(br);
		}
		if (group.equals("smsc")) {
		    SmppSmscConfiguration smppSmscConfiguration = new SmppSmscConfiguration();
		    smppSmscConfiguration.readConfiguration(br);
		    addSmppSmscConfiguration(smppSmscConfiguration);
		}
	    }		  
	}
	br.close();
	in.close();
	fis.close();
    }

    public void save() throws Exception
    {
	save(file);
    }

    public synchronized void save(File newfile) throws Exception
    {
	FileWriter fw = new FileWriter(newfile);
	if (coreConfiguration != null) {
	    coreConfiguration.writeConfiguration(new String[]{"core"}, fw);
	}
	if (smppSmscConfigurations != null) {
	    for (SmppSmscConfiguration smppSmscConfiguration:smppSmscConfigurations) {
		smppSmscConfiguration.writeConfiguration(new String[]{"smsc", "smpp"}, fw);
	    }
	}
	fw.close();
    }

}
