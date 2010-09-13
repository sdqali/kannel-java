package org.kannel.conf;

import java.net.URL;

/**
 * smsc configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SmscConfiguration
    extends Configuration
{

    public SmscConfiguration()
    {
	super("smsc");
    }

    private static final String[] SMSC_PROP_ORDER = { "smsc","smsc-id","smsc-admin-id","throughput","denied-smsc-id","allowed-smsc-id","preferred-smsc-id","allowed-prefix","denied-prefix","preferred-prefix","unified-prefix","alt-charset","alt-dcs","our-host","log-file","log-level","reconnect-delay","reroute","reroute-smsc-id","reroute-receiver","reroute-dlr","allowed-smsc-id-regex","denied-smsc-id-regex","preferred-smsc-id-regex","allowed-prefix-regex","denied-prefix-regex","preferred-prefix-regex","max-sms-octets","meta-data" };

    public String[] getPropertyOrder() {
	//	return arrayCat(super.getPropertyOrder(), SMSC_PROP_ORDER);
	return SMSC_PROP_ORDER;
    }

    private static final String[] SMSC_MANDATORY_PROPS = { "smsc" };

    public String[] getMandatoryProps() {
	//	return arrayCat(super.getPropertyOrder(), SMSC_MANDATORY_PROPS);
	return SMSC_MANDATORY_PROPS;
    }

    /**
     * smsc
     * Identifies the SMS center type. See below for a complete list. 
     */
    private String smsc;
    public String getSmsc() { return this.smsc; }
    public void setSmsc(String smsc) { this.smsc = smsc; }

    /**
     * smsc-id
     * An optional name or id for the smsc. Any string is acceptable, but semicolon ';' 
     * may cause problems, so avoid it and any other special non-alphabet characters. This 
     * 'id' is written into log files and can be used to route SMS messages, and to specify 
     * the used SMS-service. Several SMSCs can have the same id. The name is case-insensitive. 
     * Note that if SMS Center connection has an assigned SMSC ID, it does NOT automatically 
     * mean that messages with identical SMSC ID are routed to it; instead configuration 
     * variables denied-smsc-id, allowed-smsc-id and preferred-smsc-id is used for that. 
     * If you want to use Delivery Reports, you must define this. 
     */
    private String smscId;
    public String getSmscId() { return this.smscId; }
    public void setSmscId(String smscId) { this.smscId = smscId; }

    /**
     * smsc-admin-id
     * An optional id for the smsc to be used on administrative commands. This allows commands 
     * targeted to individual binds even if they share the same smsc-id (for load balancing 
     * scenarios, for example). Any string is acceptable, but semicolon ';' may cause problems, 
     * so avoid it and any other special non-alphabet characters. Several SMSCs can have 
     * the same admin-id, though it's not recommended. The name is case-insensitive. 
     */
    private String smscAdminId;
    public String getSmscAdminId() { return this.smscAdminId; }
    public void setSmscAdminId(String smscAdminId) { this.smscAdminId = smscAdminId; }

    /**
     * throughput
     * If SMSC requires that Kannel limits the number of messages per second, use this 
     * variable. This is considered as active throttling. (optional) 
     */
    private Integer throughput;
    public Integer getThroughput() { return this.throughput; }
    public void setThroughput(Integer throughput) { this.throughput = throughput; }

    /**
     * denied-smsc-id
     * SMS messages with SMSC ID equal to any of the IDs in this list are never routed 
     * to this SMSC. Multiple entries are separated with semicolons (';') 
     */
    private String[] deniedSmscId;
    public String[] getDeniedSmscId() { return this.deniedSmscId; }
    public void setDeniedSmscId(String[] deniedSmscId) { this.deniedSmscId = deniedSmscId; }

    /**
     * allowed-smsc-id
     * This list is opposite to previous: only SMS messages with SMSC ID in this list are 
     * ever routed to this SMSC. Multiple entries are separated with semicolons (';') 
     */
    private String[] allowedSmscId;
    public String[] getAllowedSmscId() { return this.allowedSmscId; }
    public void setAllowedSmscId(String[] allowedSmscId) { this.allowedSmscId = allowedSmscId; }

    /**
     * preferred-smsc-id
     * SMS messages with SMSC ID from this list are sent to this SMSC instead than to SMSC 
     * without that ID as preferred. Multiple entries are separated with semicolons (';') 
     */
    private String[] preferredSmscId;
    public String[] getPreferredSmscId() { return this.preferredSmscId; }
    public void setPreferredSmscId(String[] preferredSmscId) { this.preferredSmscId = preferredSmscId; }

    /**
     * allowed-prefix
     * A list of phone number prefixes which are accepted to be sent through this SMSC. 
     * Multiple entries are separated with semicolon (';'). For example, "040;050" prevents 
     * sending of any SMS message with prefix of 040 or 050 through this SMSC. If denied-prefix 
     * is unset, only these numbers are allowed. If set, number are allowed if present 
     * in allowed or not in denied list. 
     */
    private String[] allowedPrefix;
    public String[] getAllowedPrefix() { return this.allowedPrefix; }
    public void setAllowedPrefix(String[] allowedPrefix) { this.allowedPrefix = allowedPrefix; }

    /**
     * denied-prefix
     * A list of phone number prefixes which are NOT accepted to be sent through this SMSC. 
     */
    private String[] deniedPrefix;
    public String[] getDeniedPrefix() { return this.deniedPrefix; }
    public void setDeniedPrefix(String[] deniedPrefix) { this.deniedPrefix = deniedPrefix; }

    /**
     * preferred-prefix
     * As denied-prefix, but SMS messages with receiver starting with any of these prefixes 
     * are preferably sent through this SMSC. In a case of multiple preferences, one is 
     * selected at random (also if there are preferences, SMSC is selected randomly) 
     */
    private String[] preferredPrefix;
    public String[] getPreferredPrefix() { return this.preferredPrefix; }
    public void setPreferredPrefix(String[] preferredPrefix) { this.preferredPrefix = preferredPrefix; }

    /**
     * unified-prefix
     * String to unify received phone numbers, for SMSC routing and to ensure that SMS 
     * centers can handle them properly. This is applied to 'sender' number when receiving 
     * SMS messages from SMS Center and for 'receiver' number when receiving messages from 
     * smsbox (either sendsms message or reply to original message). Format is that first 
     * comes the unified prefix, then all prefixes which are replaced by the unified prefix, 
     * separated with comma (','). For example, for Finland an unified-prefix "+358,00358,0;+,00" 
     * should do the trick. If there are several unified prefixes, separate their rules 
     * with semicolon (';'), like "+35850,050;+35840,040". Note that prefix routing is 
     * next to useless now that there are SMSC ID entries. To remove prefixes, use like 
     * "-,+35850,050;-,+35840,040". 
     */
    private String[] unifiedPrefix;
    public String[] getUnifiedPrefix() { return this.unifiedPrefix; }
    public void setUnifiedPrefix(String[] unifiedPrefix) { this.unifiedPrefix = unifiedPrefix; }

    /**
     * alt-charset
     * As some SMS Centers do not follow the standards in character coding, an alt-charset 
     * character conversion is presented. This directive acts different for specific SMSC 
     * types. Please see your SMSC module type you want to use for more details. 
     */
    private String altCharset;
    public String getAltCharset() { return this.altCharset; }
    public void setAltCharset(String altCharset) { this.altCharset = altCharset; }

    /**
     * alt-dcs
     * Optional data coding scheme value usage. Sets the 'alt-dcs' value for the sendsms 
     * HTTP interface to a fixed value for each SMS sent via this SMSC connection. If equals 
     * to yes, uses FX. If equals to no (default), uses 0X. 
     */
    private Boolean altDcs;
    public Boolean getAltDcs() { return this.altDcs; }
    public void setAltDcs(Boolean altDcs) { this.altDcs = altDcs; }

    /**
     * our-host
     * Optional hostname or IP address in which to bind the connection in our end. TCP/IP 
     * connection only. 
     */
    private String ourHost;
    public String getOurHost() { return this.ourHost; }
    public void setOurHost(String ourHost) { this.ourHost = ourHost; }

    /**
     * log-file
     * A file in which to write a log of the given smsc output. Hence this allows to log 
     * smsc specific entries to a separate file. 
     */
    private String logFile;
    public String getLogFile() { return this.logFile; }
    public void setLogFile(String logFile) { this.logFile = logFile; }

    /**
     * log-level
     * Minimum level of log-file events logged. 0 is for 'debug', 1 'info', 2 'warning, 
     * 3 'error' and 4 'panic' (see Command Line Options) 
     */
    private Integer logLevel;
    public Integer getLogLevel() { return this.logLevel; }
    public void setLogLevel(Integer logLevel) { this.logLevel = logLevel; }

    /**
     * reconnect-delay
     * Number of seconds to wait between single re-connection attempts. Hence all SMSC 
     * modules should use this value for their re-connect behavior. (Defaults to '10' seconds). 
     */
    private Integer reconnectDelay;
    public Integer getReconnectDelay() { return this.reconnectDelay; }
    public void setReconnectDelay(Integer reconnectDelay) { this.reconnectDelay = reconnectDelay; }

    /**
     * reroute
     * If set for a smsc group, all inbound messages coming from this smsc connection are 
     * passed internally to the outbound routing functions. Hence this messages is not 
     * delivered to any connected box for processing. It is passed to the bearerbox routing 
     * as if it would have originated from an externally connected smsbox. (Defaults to 
     * 'no'). 
     */
    private Boolean reroute;
    public Boolean getReroute() { return this.reroute; }
    public void setReroute(Boolean reroute) { this.reroute = reroute; }

    /**
     * reroute-smsc-id
     * Similar to 'reroute'. Defines the explicit smsc-id the MO message should be passed 
     * to for MT traffic. Hence all messages coming from the the configuration group smsc 
     * are passed to the outbound queue of the specified smsc-id. This allows direct proxying 
     * of messages between 2 smsc connections without injecting them to the general routing 
     * procedure in bearerbox. 
     */
    private String rerouteSmscId;
    public String getRerouteSmscId() { return this.rerouteSmscId; }
    public void setRerouteSmscId(String rerouteSmscId) { this.rerouteSmscId = rerouteSmscId; }

    /**
     * reroute-receiver
     * Similar to 'reroute'. Defines the explicit smsc-id routes for specific receiver 
     * numbers of messages that are coming from this smsc connection. Format is that first 
     * comes the smsc-id to route the message to, then all receiver numbers that should 
     * be routed, separated with comma (','). For example, route receivers 111 and 222 
     * to smsc-id A and 333 and 444 to smsc-id B would look like: "A,111,222; B,333,444". 
     */
    private String rerouteReceiver;
    public String getRerouteReceiver() { return this.rerouteReceiver; }
    public void setRerouteReceiver(String rerouteReceiver) { this.rerouteReceiver = rerouteReceiver; }

    /**
     * reroute-dlr
     * Indicate whether DLR's should be re-routed too, if one of above reroute rules are 
     * enabled. Please note, that SMSC-Module should support DLR sending. At time of writing 
     * none of SMSC-Module supports DLR sending. 
     */
    private Boolean rerouteDlr;
    public Boolean getRerouteDlr() { return this.rerouteDlr; }
    public void setRerouteDlr(Boolean rerouteDlr) { this.rerouteDlr = rerouteDlr; }

    /**
     * allowed-smsc-id-regex
     * SMS messages with SMSC ID equal to any of the IDs in this set of SMSC IDs are always 
     * routed to this SMSC. See section on Regular Expressions for details. 
     */
    private String allowedSmscIdRegex;
    public String getAllowedSmscIdRegex() { return this.allowedSmscIdRegex; }
    public void setAllowedSmscIdRegex(String allowedSmscIdRegex) { this.allowedSmscIdRegex = allowedSmscIdRegex; }

    /**
     * denied-smsc-id-regex
     * SMS messages with SMSC ID equal to any of the IDs in this set of SMSC IDs are never 
     * routed to this SMSC. See section on Regular Expressions for details. 
     */
    private String deniedSmscIdRegex;
    public String getDeniedSmscIdRegex() { return this.deniedSmscIdRegex; }
    public void setDeniedSmscIdRegex(String deniedSmscIdRegex) { this.deniedSmscIdRegex = deniedSmscIdRegex; }

    /**
     * preferred-smsc-id-regex
     * SMS messages with SMSC ID in this set of SMSC IDs are sent to this SMSC as preferred. 
     * See section on Regular Expressions for details. 
     */
    private String preferredSmscIdRegex;
    public String getPreferredSmscIdRegex() { return this.preferredSmscIdRegex; }
    public void setPreferredSmscIdRegex(String preferredSmscIdRegex) { this.preferredSmscIdRegex = preferredSmscIdRegex; }

    /**
     * allowed-prefix-regex
     * A set of phone number prefixes which are accepted to be sent through this SMSC. 
     * See section on Regular Expressions for details. 
     */
    private String allowedPrefixRegex;
    public String getAllowedPrefixRegex() { return this.allowedPrefixRegex; }
    public void setAllowedPrefixRegex(String allowedPrefixRegex) { this.allowedPrefixRegex = allowedPrefixRegex; }

    /**
     * denied-prefix-regex
     * A set of phone number prefixes which may not be sent through this SMSC. See section 
     * on Regular Expressions for details. 
     */
    private String deniedPrefixRegex;
    public String getDeniedPrefixRegex() { return this.deniedPrefixRegex; }
    public void setDeniedPrefixRegex(String deniedPrefixRegex) { this.deniedPrefixRegex = deniedPrefixRegex; }

    /**
     * preferred-prefix-regex
     * As denied-prefix-regex, but SMS messages with receiver matching any of these prefixes 
     * are preferably sent through this SMSC. In a case of multiple preferences, one is 
     * selected at random. See section on Regular Expressions for details. 
     */
    private String preferredPrefixRegex;
    public String getPreferredPrefixRegex() { return this.preferredPrefixRegex; }
    public void setPreferredPrefixRegex(String preferredPrefixRegex) { this.preferredPrefixRegex = preferredPrefixRegex; }

    /**
     * max-sms-octets
     * Maximum allowed octets for this SMSC. If this maximum exceeds Kannel will split 
     * SMS into multiparts. Default: 140 
     */
    private Integer maxSmsOctets;
    public Integer getMaxSmsOctets() { return this.maxSmsOctets; }
    public void setMaxSmsOctets(Integer maxSmsOctets) { this.maxSmsOctets = maxSmsOctets; }

    /**
     * meta-data
     * url-encoded string with properly formatted meta-data pair(s).. See Meta Data for 
     * an explanation about meta-data configuration and encoding. 
     */
    private String metaData;
    public String getMetaData() { return this.metaData; }
    public void setMetaData(String metaData) { this.metaData = metaData; }

}
