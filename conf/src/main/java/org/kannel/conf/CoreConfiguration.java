package org.kannel.conf;

import java.net.URL;

/**
 * core configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class CoreConfiguration
    extends Configuration
{

    public CoreConfiguration()
    {
	super("core");
    }
    
    private static final String[] PROP_ORDER = { "admin-port","admin-port-ssl","admin-password","status-password","admin-deny-ip","admin-allow-ip","smsbox-port","smsbox-port-ssl","wapbox-port","wapbox-port-ssl","box-deny-ip","box-allow-ip","udp-deny-ip","udp-allow-ip","wdp-interface-name","log-file","log-level","access-log","access-log-clean","access-log-format","unified-prefix","white-list","black-list","store-type","store-location","store-dump-freq","http-proxy-host","http-proxy-port","http-proxy-exceptions","http-proxy-exceptions-regex","http-proxy-username","http-proxy-password","ssl-client-certkey-file","ssl-server-cert-file","ssl-server-key-file","ssl-trusted-ca-file","dlr-storage","sms-incoming-queue-limit","sms-outgoing-queue-limit","white-list-regex","black-list-regex","smsbox-max-pending","sms-resend-freq","sms-resend-retry","sms-combine-concatenated-mo","sms-combine-concatenated-mo-timeout","http-timeout" };

    public String[] getPropertyOrder() {
	return PROP_ORDER;
    }

    private static final String[] MANDATORY_PROPS = { "admin-port","admin-password" };

    public String[] getMandatoryProps() {
	return MANDATORY_PROPS;
    }

    /**
     * admin-port
     * The port number in which the bearerbox listens to HTTP administration commands. 
     * It is NOT the same as the HTTP port of the local HTTP server, just invent any port, 
     * but it must be over 1023 unless you are running Kannel as a root process (not recommended) 
     */
    private Integer adminPort;
    public Integer getAdminPort() { return this.adminPort; }
    public void setAdminPort(Integer adminPort) { this.adminPort = adminPort; }

    /**
     * admin-port-ssl
     * If set to true a SSL-enabled administration HTTP server will be used instead of 
     * the default insecure plain HTTP server. To access the administration pages you will 
     * have to use a HTTP client that is capable of talking to such a server. Use the "https://" 
     * scheme to access the secured HTTP server. Defaults to "no". 
     */
    private Boolean adminPortSsl;
    public Boolean getAdminPortSsl() { return this.adminPortSsl; }
    public void setAdminPortSsl(Boolean adminPortSsl) { this.adminPortSsl = adminPortSsl; }

    /**
     * admin-password
     * Password for HTTP administration commands (see below) 
     */
    private String adminPassword;
    public String getAdminPassword() { return this.adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }

    /**
     * status-password
     * Password to request Kannel status. If not set, no password is required, and if set, 
     * either this or admin-password can be used 
     */
    private String statusPassword;
    public String getStatusPassword() { return this.statusPassword; }
    public void setStatusPassword(String statusPassword) { this.statusPassword = statusPassword; }

    /**
     * admin-deny-ip
     * These lists can be used to prevent connection from given IP addresses. Each list 
     * can have several addresses, separated with semicolons (';'). An asterisk ('*') can 
     * be used as a wild-card in a place of any ONE number, so *.*.*.* matches any IP. 
     */
    private String[] adminDenyIp;
    public String[] getAdminDenyIp() { return this.adminDenyIp; }
    public void setAdminDenyIp(String[] adminDenyIp) { this.adminDenyIp = adminDenyIp; }

    /**
     * admin-allow-ip
     * These lists can be used to prevent connection from given IP addresses. Each list 
     * can have several addresses, separated with semicolons (';'). An asterisk ('*') can 
     * be used as a wild-card in a place of any ONE number, so *.*.*.* matches any IP. 
     */
    private String[] adminAllowIp;
    public String[] getAdminAllowIp() { return this.adminAllowIp; }
    public void setAdminAllowIp(String[] adminAllowIp) { this.adminAllowIp = adminAllowIp; }

    /**
     * smsbox-port
     * This is the port number to which the smsboxes, if any, connect. As with admin-port, 
     * this can be anything you want. Must be set if you want to handle any SMS traffic. 
     */
    private Integer smsboxPort;
    public Integer getSmsboxPort() { return this.smsboxPort; }
    public void setSmsboxPort(Integer smsboxPort) { this.smsboxPort = smsboxPort; }

    /**
     * smsbox-port-ssl
     * If set to true, the smsbox connection module will be SSL-enabled. Your smsboxes 
     * will have to connect using SSL to the bearerbox then. This is used to secure communication 
     * between bearerbox and smsboxes in case they are in separate networks operated and 
     * the TCP communication is not secured on a lower network layer. Defaults to "no". 
     */
    private Boolean smsboxPortSsl;
    public Boolean getSmsboxPortSsl() { return this.smsboxPortSsl; }
    public void setSmsboxPortSsl(Boolean smsboxPortSsl) { this.smsboxPortSsl = smsboxPortSsl; }

    /**
     * wapbox-port
     * Like smsbox-port, but for wapbox-connections. If not set, Kannel cannot handle WAP 
     * traffic 
     */
    private Integer wapboxPort;
    public Integer getWapboxPort() { return this.wapboxPort; }
    public void setWapboxPort(Integer wapboxPort) { this.wapboxPort = wapboxPort; }

    /**
     * wapbox-port-ssl
     * If set to true, the wapbox connection module will be SSL-enabled. Your wapboxes 
     * will have to connect using SSL to the bearerbox then. This is used to secure communication 
     * between bearerbox and wapboxes in case they are in separate networks operated and 
     * the TCP communication is not secured on a lower network layer. Defaults to "no". 
     */
    private Boolean wapboxPortSsl;
    public Boolean getWapboxPortSsl() { return this.wapboxPortSsl; }
    public void setWapboxPortSsl(Boolean wapboxPortSsl) { this.wapboxPortSsl = wapboxPortSsl; }

    /**
     * box-deny-ip
     * These lists can be used to prevent box connections from given IP addresses. Each 
     * list can have several addresses, separated with semicolons (';'). An asterisk ('*') 
     * can be used as a wild-card in place of any ONE number, so *.*.*.* matches any IP. 
     */
    private String[] boxDenyIp;
    public String[] getBoxDenyIp() { return this.boxDenyIp; }
    public void setBoxDenyIp(String[] boxDenyIp) { this.boxDenyIp = boxDenyIp; }

    /**
     * box-allow-ip
     * These lists can be used to prevent box connections from given IP addresses. Each 
     * list can have several addresses, separated with semicolons (';'). An asterisk ('*') 
     * can be used as a wild-card in place of any ONE number, so *.*.*.* matches any IP. 
     */
    private String[] boxAllowIp;
    public String[] getBoxAllowIp() { return this.boxAllowIp; }
    public void setBoxAllowIp(String[] boxAllowIp) { this.boxAllowIp = boxAllowIp; }

    /**
     * udp-deny-ip
     * These lists can be used to prevent UDP packets from given IP addresses, thus preventing 
     * unwanted use of the WAP gateway. Used the same way as box-deny-ip and box-allow-ip. 
     */
    private String[] udpDenyIp;
    public String[] getUdpDenyIp() { return this.udpDenyIp; }
    public void setUdpDenyIp(String[] udpDenyIp) { this.udpDenyIp = udpDenyIp; }

    /**
     * udp-allow-ip
     * These lists can be used to prevent UDP packets from given IP addresses, thus preventing 
     * unwanted use of the WAP gateway. Used the same way as box-deny-ip and box-allow-ip. 
     */
    private String[] udpAllowIp;
    public String[] getUdpAllowIp() { return this.udpAllowIp; }
    public void setUdpAllowIp(String[] udpAllowIp) { this.udpAllowIp = udpAllowIp; }

    /**
     * wdp-interface-name
     * If this is set, Kannel listens to WAP UDP packets incoming to ports 9200-9208, bound 
     * to given IP. If no specific IP is needed, use just an asterisk ('*'). If UDP messages 
     * are listened to, wapbox-port variable MUST be set. 
     */
    private String wdpInterfaceName;
    public String getWdpInterfaceName() { return this.wdpInterfaceName; }
    public void setWdpInterfaceName(String wdpInterfaceName) { this.wdpInterfaceName = wdpInterfaceName; }

    /**
     * log-file
     * A file in which to write a log. This in addition to stdout and any log file defined 
     * in command line. Log-file in 'core' group is only used by the bearerbox. 
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
     * access-log
     * A file in which information about received/sent SMS messages is stored. Access-log 
     * in 'core' group is only used by the bearerbox. 
     */
    private String accessLog;
    public String getAccessLog() { return this.accessLog; }
    public void setAccessLog(String accessLog) { this.accessLog = accessLog; }

    /**
     * access-log-clean
     * Indicates if access-log will contain standard 'markers', which means the 'Log begins', 
     * 'Log ends' markers and the prefixed timestamp. This config directive should be set 
     * to 'true' if a custom logging format is desired without a prefixed default timestamp. 
     */
    private Boolean accessLogClean;
    public Boolean getAccessLogClean() { return this.accessLogClean; }
    public void setAccessLogClean(Boolean accessLogClean) { this.accessLogClean = accessLogClean; }

    /**
     * access-log-format
     * String defining a custom log file line format. May use escape codes as defined later 
     * on to substitute values of the messages into the log entry. If no custom log format 
     * is used the standard format will be: "%t %l [SMSC:%i] [SVC:%n] [ACT:%A] [BINF:%B] 
     * [FID:%F] [META:%D] [from:%p] [to:%P] [flags:%m:%c:%M:%C:%d] [msg:%L:%b] [udh:%U:%u]" 
     */
    private String accessLogFormat;
    public String getAccessLogFormat() { return this.accessLogFormat; }
    public void setAccessLogFormat(String accessLogFormat) { this.accessLogFormat = accessLogFormat; }

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
     * white-list
     * Load a list of accepted senders of SMS messages. If a sender of an SMS message is 
     * not in this list, any message received from the SMS Center is discarded. See notes 
     * of phone number format from numhash.h header file. NOTE: the system has only a precision 
     * of last 9 or 18 digits of phone numbers, so beware! 
     */
    private URL whiteList;
    public URL getWhiteList() { return this.whiteList; }
    public void setWhiteList(URL whiteList) { this.whiteList = whiteList; }

    /**
     * black-list
     * As white-list, but SMS messages to these numbers are automatically discarded 
     */
    private URL blackList;
    public URL getBlackList() { return this.blackList; }
    public void setBlackList(URL blackList) { this.blackList = blackList; }

    /**
     * store-type
     * Kannel can use store subsystem that means storing messages on hard disk until they 
     * are successfully handled. By using this subsystem, no SMS messages are lost in Kannel 
     * even by crash, but theoretically some messages can duplicate when system is taken 
     * down violently. This variable defines a type of backend used for store subsystem. 
     * Now two types are supported: a) file: writes store into one single file b) spool: 
     * writes store into spool directory (one file for each message) 
     */
    private String storeType;
    public String getStoreType() { return this.storeType; }
    public void setStoreType(String storeType) { this.storeType = storeType; }

    /**
     * store-location
     * Depends on store-type option used, it is ether file or spool directory. 
     */
    private String storeLocation;
    public String getStoreLocation() { return this.storeLocation; }
    public void setStoreLocation(String storeLocation) { this.storeLocation = storeLocation; }

    /**
     * store-dump-freq
     * Approximated frequency how often the memory dump of current pending messages are 
     * stored to store-file, providing something has happened. Defaults to 10 seconds if 
     * not set. 
     */
    private Integer storeDumpFreq;
    public Integer getStoreDumpFreq() { return this.storeDumpFreq; }
    public void setStoreDumpFreq(Integer storeDumpFreq) { this.storeDumpFreq = storeDumpFreq; }

    /**
     * http-proxy-host
     * Enable the use of an HTTP proxy for all HTTP requests. 
     */
    private String httpProxyHost;
    public String getHttpProxyHost() { return this.httpProxyHost; }
    public void setHttpProxyHost(String httpProxyHost) { this.httpProxyHost = httpProxyHost; }

    /**
     * http-proxy-port
     *  
     */
    private Integer httpProxyPort;
    public Integer getHttpProxyPort() { return this.httpProxyPort; }
    public void setHttpProxyPort(Integer httpProxyPort) { this.httpProxyPort = httpProxyPort; }

    /**
     * http-proxy-exceptions
     * A list of excluded hosts from being used via a proxy. Separate each entry with space. 
     */
    private String[] httpProxyExceptions;
    public String[] getHttpProxyExceptions() { return this.httpProxyExceptions; }
    public void setHttpProxyExceptions(String[] httpProxyExceptions) { this.httpProxyExceptions = httpProxyExceptions; }

    /**
     * http-proxy-exceptions-regex
     * Same as http-proxy-exceptions but match against UNIX regular expression. 
     */
    private String httpProxyExceptionsRegex;
    public String getHttpProxyExceptionsRegex() { return this.httpProxyExceptionsRegex; }
    public void setHttpProxyExceptionsRegex(String httpProxyExceptionsRegex) { this.httpProxyExceptionsRegex = httpProxyExceptionsRegex; }

    /**
     * http-proxy-username
     * Username for authenticating proxy use, for proxies that require this. 
     */
    private String httpProxyUsername;
    public String getHttpProxyUsername() { return this.httpProxyUsername; }
    public void setHttpProxyUsername(String httpProxyUsername) { this.httpProxyUsername = httpProxyUsername; }

    /**
     * http-proxy-password
     * Password for authenticating proxy use, for proxies that require this. 
     */
    private String[] httpProxyPassword;
    public String[] getHttpProxyPassword() { return this.httpProxyPassword; }
    public void setHttpProxyPassword(String[] httpProxyPassword) { this.httpProxyPassword = httpProxyPassword; }

    /**
     * ssl-client-certkey-file
     * A PEM encoded SSL certificate and private key file to be used with SSL client connections. 
     * This certificate is used for the HTTPS client side only, i.e. for SMS service requests 
     * to SSL-enabled HTTP servers. 
     */
    private String sslClientCertkeyFile;
    public String getSslClientCertkeyFile() { return this.sslClientCertkeyFile; }
    public void setSslClientCertkeyFile(String sslClientCertkeyFile) { this.sslClientCertkeyFile = sslClientCertkeyFile; }

    /**
     * ssl-server-cert-file
     * A PEM encoded SSL certificate file to be used with SSL server connections. This 
     * certificate is used for the HTTPS server side only, i.e. for the administration 
     * HTTP server and the HTTP interface to send SMS messages. 
     */
    private String sslServerCertFile;
    public String getSslServerCertFile() { return this.sslServerCertFile; }
    public void setSslServerCertFile(String sslServerCertFile) { this.sslServerCertFile = sslServerCertFile; }

    /**
     * ssl-server-key-file
     * A PEM encoded SSL private key file to be used with SSL server connections. This 
     * key is associated to the specified certificate and is used for the HTTPS server 
     * side only. 
     */
    private String sslServerKeyFile;
    public String getSslServerKeyFile() { return this.sslServerKeyFile; }
    public void setSslServerKeyFile(String sslServerKeyFile) { this.sslServerKeyFile = sslServerKeyFile; }

    /**
     * ssl-trusted-ca-file
     * This file contains the certificates Kannel is willing to trust when working as a 
     * HTTPS client. If this option is not set, certificates are not validated and those 
     * the identity of the server is not proven. 
     */
    private String sslTrustedCaFile;
    public String getSslTrustedCaFile() { return this.sslTrustedCaFile; }
    public void setSslTrustedCaFile(String sslTrustedCaFile) { this.sslTrustedCaFile = sslTrustedCaFile; }

    /**
     * dlr-storage
     * Defines the way DLRs are stored. If you have build-in external DLR storage support, 
     * i.e. using MySQL you may define here the alternative storage type like 'mysql'. 
     * Supported types are: internal, mysql, pgsql, sdb, mssql and oracle. By default this 
     * is set to 'internal'. 
     */
    private String dlrStorage;
    public String getDlrStorage() { return this.dlrStorage; }
    public void setDlrStorage(String dlrStorage) { this.dlrStorage = dlrStorage; }

    /**
     * sms-incoming-queue-limit
     * Set maximum size of incoming message queue. After number of messages has hit this 
     * value, Kannel began to discard them. Value 0 means giving strict priority to outgoing 
     * messages. -1, default, means that the queue of infinite length is accepted. (This 
     * works with any normal input, use this variable only when Kannel message queues grow 
     * very long). 
     */
    private Integer smsIncomingQueueLimit;
    public Integer getSmsIncomingQueueLimit() { return this.smsIncomingQueueLimit; }
    public void setSmsIncomingQueueLimit(Integer smsIncomingQueueLimit) { this.smsIncomingQueueLimit = smsIncomingQueueLimit; }

    /**
     * sms-outgoing-queue-limit
     * Set maximum size of outgoing message queue. After number of messages has hit this 
     * value, Kannel began to discard them. The default value, 1 million, works for most 
     * installations. 
     */
    private Integer smsOutgoingQueueLimit;
    public Integer getSmsOutgoingQueueLimit() { return this.smsOutgoingQueueLimit; }
    public void setSmsOutgoingQueueLimit(Integer smsOutgoingQueueLimit) { this.smsOutgoingQueueLimit = smsOutgoingQueueLimit; }

    /**
     * white-list-regex
     * A regular expression defining the set of accepted senders. See section on Regular 
     * Expressions for details. 
     */
    private String whiteListRegex;
    public String getWhiteListRegex() { return this.whiteListRegex; }
    public void setWhiteListRegex(String whiteListRegex) { this.whiteListRegex = whiteListRegex; }

    /**
     * black-list-regex
     * A regular expression defining the set of rejected senders. See section on Regular 
     * Expressions for details. 
     */
    private String blackListRegex;
    public String getBlackListRegex() { return this.blackListRegex; }
    public void setBlackListRegex(String blackListRegex) { this.blackListRegex = blackListRegex; }

    /**
     * smsbox-max-pending
     * Maximum number of pending messages on the line to smsbox compatible boxes. 
     */
    private Integer smsboxMaxPending;
    public Integer getSmsboxMaxPending() { return this.smsboxMaxPending; }
    public void setSmsboxMaxPending(Integer smsboxMaxPending) { this.smsboxMaxPending = smsboxMaxPending; }

    /**
     * sms-resend-freq
     * Frequency for the SMS resend thread in which temporarily failed or queued messages 
     * will be resent. Defaults to 60 seconds. 
     */
    private Integer smsResendFreq;
    public Integer getSmsResendFreq() { return this.smsResendFreq; }
    public void setSmsResendFreq(Integer smsResendFreq) { this.smsResendFreq = smsResendFreq; }

    /**
     * sms-resend-retry
     * Maximum retry attempts for the temporarily failed messages. Defaults to -1, means: 
     * unlimited. 
     */
    private Integer smsResendRetry;
    public Integer getSmsResendRetry() { return this.smsResendRetry; }
    public void setSmsResendRetry(Integer smsResendRetry) { this.smsResendRetry = smsResendRetry; }

    /**
     * sms-combine-concatenated-mo
     * Whether Kannel should attempt to combine concatenated MO SMS prior to passing them 
     * over to smsbox. Default is true 
     */
    private Boolean smsCombineConcatenatedMo;
    public Boolean getSmsCombineConcatenatedMo() { return this.smsCombineConcatenatedMo; }
    public void setSmsCombineConcatenatedMo(Boolean smsCombineConcatenatedMo) { this.smsCombineConcatenatedMo = smsCombineConcatenatedMo; }

    /**
     * sms-combine-concatenated-mo-timeout
     * How long to wait for all concatenated message parts to arrive before timeouting. 
     * Default 1800 seconds. 
     */
    private Integer smsCombineConcatenatedMoTimeout;
    public Integer getSmsCombineConcatenatedMoTimeout() { return this.smsCombineConcatenatedMoTimeout; }
    public void setSmsCombineConcatenatedMoTimeout(Integer smsCombineConcatenatedMoTimeout) { this.smsCombineConcatenatedMoTimeout = smsCombineConcatenatedMoTimeout; }

    /**
     * http-timeout
     * Sets socket timeout in seconds for outgoing client http connections. Optional. Defaults 
     * to 240 seconds. 
     */
    private Integer httpTimeout;
    public Integer getHttpTimeout() { return this.httpTimeout; }
    public void setHttpTimeout(Integer httpTimeout) { this.httpTimeout = httpTimeout; }

}
