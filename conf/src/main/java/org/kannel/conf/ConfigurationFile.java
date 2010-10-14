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

    private Collection<SmppSmscConfiguration> smppSmscConfigurations = new HashSet<SmppSmscConfiguration>();
    public Collection<SmppSmscConfiguration> getSmppSmscConfigurations() { return this.smppSmscConfigurations; }
    public void addSmppSmscConfiguration(SmppSmscConfiguration smppSmscConfiguration) {
	smppSmscConfigurations.add(smppSmscConfiguration);
    }

    private Collection<SmsboxConfiguration> smsboxConfigurations = new HashSet<SmsboxConfiguration>();
    public Collection<SmsboxConfiguration> getSmsboxConfigurations() { return this.smsboxConfigurations; }
    public void addSmsboxConfiguration(SmsboxConfiguration smsboxConfiguration) {
	smsboxConfigurations.add(smsboxConfiguration);
    }

    private Collection<SmsServiceConfiguration> smsServiceConfigurations = new HashSet<SmsServiceConfiguration>();
    public Collection<SmsServiceConfiguration> getSmsServiceConfigurations() { return this.smsServiceConfigurations; }
    public void addSmsServiceConfiguration(SmsServiceConfiguration smsServiceConfiguration) {
	smsServiceConfigurations.add(smsServiceConfiguration);
    }

    private Collection<SendsmsUserConfiguration> sendsmsUserConfigurations = new HashSet<SendsmsUserConfiguration>();
    public Collection<SendsmsUserConfiguration> getSendsmsUserConfigurations() { return this.sendsmsUserConfigurations; }
    public void addSendsmsUserConfiguration(SendsmsUserConfiguration sendsmsUserConfiguration) {
	sendsmsUserConfigurations.add(sendsmsUserConfiguration);
    }

    private Collection<SmsboxRouteConfiguration> smsboxRouteConfigurations = new HashSet<SmsboxRouteConfiguration>();
    public Collection<SmsboxRouteConfiguration> getSmsboxRouteConfigurations() { return this.smsboxRouteConfigurations; }
    public void addSmsboxRouteConfiguration(SmsboxRouteConfiguration smsboxRouteConfiguration) {
	smsboxRouteConfigurations.add(smsboxRouteConfiguration);
    }

    // private WapboxConfiguration wapboxConfiguration;
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
		if (group.equals("smsbox")) {
		    SmsboxConfiguration smsboxConfiguration = new SmsboxConfiguration();
		    smsboxConfiguration.readConfiguration(br);
		    addSmsboxConfiguration(smsboxConfiguration);
		}
		if (group.equals("sms-service")) {
		    SmsServiceConfiguration smsServiceConfiguration = new SmsServiceConfiguration();
		    smsServiceConfiguration.readConfiguration(br);
		    addSmsServiceConfiguration(smsServiceConfiguration);
		}
		if (group.equals("sendsms-user")) {
		    SendsmsUserConfiguration sendsmsUserConfiguration = new SendsmsUserConfiguration();
		    sendsmsUserConfiguration.readConfiguration(br);
		    addSendsmsUserConfiguration(sendsmsUserConfiguration);
		}
		if (group.equals("smsbox-route")) {
		    SmsboxRouteConfiguration smsboxRouteConfiguration = new SmsboxRouteConfiguration();
		    smsboxRouteConfiguration.readConfiguration(br);
		    addSmsboxRouteConfiguration(smsboxRouteConfiguration);
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
	writeConfigurations(smppSmscConfigurations, new String[]{"smsc", "smpp"}, fw);
 	writeConfigurations(smsboxConfigurations, new String[]{"smsbox", "#"}, fw);
 	writeConfigurations(smsServiceConfigurations, new String[]{"sms-service", "#"}, fw);
 	writeConfigurations(sendsmsUserConfigurations, new String[]{"sendsms-user", "#"}, fw);
 	writeConfigurations(smsboxRouteConfigurations, new String[]{"smsbox-route", "#"}, fw);
	// more
	fw.close();
    }

    private void writeConfigurations(Collection<? extends Configuration> configs, String[] comments, FileWriter fw)
	throws Exception
    {
	if (configs != null && !configs.isEmpty()) {
	    for (Configuration config:configs) {
		((Configuration)config).writeConfiguration(comments, fw);
	    }
	}
    }

}
