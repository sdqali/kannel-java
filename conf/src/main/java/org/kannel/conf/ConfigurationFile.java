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

    private Collection<SmscConfiguration> smscConfigurations = new HashSet<SmscConfiguration>();
    public Collection<SmscConfiguration> getSmscConfigurations() { return this.smscConfigurations; }
    public void addSmscConfiguration(SmscConfiguration smscConfiguration) {
	smscConfigurations.add(smscConfiguration);
    }

    private Collection<SmsboxConfiguration> smsboxConfigurations = new HashSet<SmsboxConfiguration>();
    public Collection<SmsboxConfiguration> getSmsboxConfigurations() { return this.smsboxConfigurations; }
    public void addSmsboxConfiguration(SmsboxConfiguration smsboxConfiguration) {
	smsboxConfigurations.add(smsboxConfiguration);
    }

    private Collection<WapboxConfiguration> wapboxConfigurations = new HashSet<WapboxConfiguration>();
    public Collection<WapboxConfiguration> getWapboxConfigurations() { return this.wapboxConfigurations; }
    public void addWapboxConfiguration(WapboxConfiguration wapboxConfiguration) {
	wapboxConfigurations.add(wapboxConfiguration);
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

    private Collection<DbConnectionConfiguration> dbConnectionConfigurations = new HashSet<DbConnectionConfiguration>();
    public Collection<DbConnectionConfiguration> getDbConnectionConfigurations() { return this.dbConnectionConfigurations; }
    public void addDbConnectionConfiguration(DbConnectionConfiguration dbConnectionConfiguration) {
	dbConnectionConfigurations.add(dbConnectionConfiguration);
    }

    private Collection<DlrDbConfiguration> dlrDbConfigurations = new HashSet<DlrDbConfiguration>();
    public Collection<DlrDbConfiguration> getDlrDbConfigurations() { return this.dlrDbConfigurations; }
    public void addDlrDbConfiguration(DlrDbConfiguration dlrDbConfiguration) {
	dlrDbConfigurations.add(dlrDbConfiguration);
    }

    // TODO: Missing types
    // private CimdSmscConfiguration cimdSmscConfiguration;
    // private GsmSmscConfiguration GsmSmscConfiguration;
    // private SemaSmscConfiguration semaSmscConfiguration;
    // private OtaSettingConfiguration otaSettingConfiguration;
    // private OtaBookmarkConfiguration otaBookmarkConfiguration;
    // private PpgConfiguration ppgConfiguration;
    // private WapPushUserConfiguration wapPushUserConfiguration;
    // private RadiusAccountConfiguration radiusAccountConfiguration;
    // private SmppTlvConfiguration SmppTlvConfiguration;
    // private ModemsConfiguration modemsConfiguration;

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
		// ignore comment or break
	    } else if (line.startsWith("group")) {
		int pos = line.indexOf("=");
		String group = line.substring(pos+1).trim();
		if (group.equals("core")) {
		    coreConfiguration = new CoreConfiguration();
		    coreConfiguration.readConfiguration(br);
		}
		if (group.equals("smsc")) {
		    addSmscConfiguration(SmscConfiguration.getConfigurationInstance(br));
		}
		if (group.equals("smsbox")) {
		    SmsboxConfiguration smsboxConfiguration = new SmsboxConfiguration();
		    smsboxConfiguration.readConfiguration(br);
		    addSmsboxConfiguration(smsboxConfiguration);
		}
		if (group.equals("wapbox")) {
		    WapboxConfiguration wapboxConfiguration = new WapboxConfiguration();
		    wapboxConfiguration.readConfiguration(br);
		    addWapboxConfiguration(wapboxConfiguration);
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
		if (group.equals("dlr-db")) {
		    DlrDbConfiguration dlrDbConfiguration = new DlrDbConfiguration();
		    dlrDbConfiguration.readConfiguration(br);
		    addDlrDbConfiguration(dlrDbConfiguration);
		}
		if (group.contains("-connection")) {
		    String[] a = group.split("-");
		    DbConnectionConfiguration dbConnectionConfiguration = new DbConnectionConfiguration(a[0]);
		    dbConnectionConfiguration.readConfiguration(br);
		    addDbConnectionConfiguration(dbConnectionConfiguration);
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
	writeConfigurations(smscConfigurations, new String[]{"smsc"}, fw);
 	writeConfigurations(smsboxConfigurations, new String[]{"smsbox"}, fw);
 	writeConfigurations(wapboxConfigurations, new String[]{"wapbox"}, fw);
 	writeConfigurations(smsServiceConfigurations, new String[]{"sms-service"}, fw);
 	writeConfigurations(sendsmsUserConfigurations, new String[]{"sendsms-user"}, fw);
 	writeConfigurations(smsboxRouteConfigurations, new String[]{"smsbox-route"}, fw);
 	writeConfigurations(dlrDbConfigurations, new String[]{"dlr-db"}, fw);
 	writeConfigurations(dbConnectionConfigurations, new String[]{"db-connection"}, fw);
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
