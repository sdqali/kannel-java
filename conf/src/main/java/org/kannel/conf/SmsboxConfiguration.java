package org.kannel.conf;

import java.net.URL;

/**
 * smsbox configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SmsboxConfiguration
    extends Configuration
{

    public SmsboxConfiguration()
    {
	super("smsbox");
    }

    private static final String[] PROP_ORDER = { "bearerbox-host","bearerbox-port","bearerbox-port-ssl","smsbox-id","sendsms-port","sendsms-port-ssl","sendsms-url","sendota-url","immediate-sendsms-reply","sendsms-chars","global-sender","log-file","log-level","access-log","white-list","black-list","reply-couldnotfetch","reply-couldnotrepresent","reply-requestfailed","reply-emptymessage","mo-recode","http-request-retry","http-queue-delay","white-list-regex","black-list-regex","max-pending-requests","http-timeout" };

    public String[] getPropertyOrder() {
	return PROP_ORDER;
    }

    private static final String[] MANDATORY_PROPS = { "bearerbox-host" };

    public String[] getMandatoryProps() {
	return MANDATORY_PROPS;
    }

    /**
     * bearerbox-host
     *  The machine in which the bearerbox is. 
     */
    private String bearerboxHost;
    public String getBearerboxHost() { return this.bearerboxHost; }
    public void setBearerboxHost(String bearerboxHost) { this.bearerboxHost = bearerboxHost; }

    /**
     * bearerbox-port
     *  This is the port number to which smsbox will connect bearerbox. If not given smsbox-port 
     * from core group used. 
     */
    private Integer bearerboxPort;
    public Integer getBearerboxPort() { return this.bearerboxPort; }
    public void setBearerboxPort(Integer bearerboxPort) { this.bearerboxPort = bearerboxPort; }

    /**
     * bearerbox-port-ssl
     *  If set to true, the smsbox connection will be SSL-enabled. Your smsbox will connect 
     * using SSL to the bearerbox then. This is used to secure communication between bearerbox 
     * and smsboxes in case they are in separate networks operated and the TCP communication 
     * is not secured on a lower network layer. If not given smsbox-port-ssl from core 
     * group used. 
     */
    private Boolean bearerboxPortSsl;
    public Boolean getBearerboxPortSsl() { return this.bearerboxPortSsl; }
    public void setBearerboxPortSsl(Boolean bearerboxPortSsl) { this.bearerboxPortSsl = bearerboxPortSsl; }

    /**
     * smsbox-id
     *  Optional smsbox instance identifier. This is used to identify an smsbox connected 
     * to an bearerbox for the purpose of having smsbox specific routing inside bearerbox. 
     * So if you you own boxes that do pass messages into bearerbox for delivery you may 
     * want that answers to those are routed back to your specific smsbox instance, i.e. 
     * SMPP or EMI proxying boxes. 
     */
    private String smsboxId;
    public String getSmsboxId() { return this.smsboxId; }
    public void setSmsboxId(String smsboxId) { this.smsboxId = smsboxId; }

    /**
     * sendsms-port
     * The port in which any sendsms HTTP requests are done. As with other ports in Kannel, 
     * can be set as anything desired. 
     */
    private Integer sendsmsPort;
    public Integer getSendsmsPort() { return this.sendsmsPort; }
    public void setSendsmsPort(Integer sendsmsPort) { this.sendsmsPort = sendsmsPort; }

    /**
     * sendsms-port-ssl
     *   If set to true, the sendsms HTTP interface will use a SSL-enabled HTTP server 
     * with the specified ssl-server-cert-file and ssl-server-key-file from the core group. 
     * Defaults to "no". 
     */
    private Boolean sendsmsPortSsl;
    public Boolean getSendsmsPortSsl() { return this.sendsmsPortSsl; }
    public void setSendsmsPortSsl(Boolean sendsmsPortSsl) { this.sendsmsPortSsl = sendsmsPortSsl; }

    /**
     * sendsms-url
     *  URL locating the sendsms service. Defaults to /cgi-bin/sendsms. 
     */
    private URL sendsmsUrl;
    public URL getSendsmsUrl() { return this.sendsmsUrl; }
    public void setSendsmsUrl(URL sendsmsUrl) { this.sendsmsUrl = sendsmsUrl; }

    /**
     * sendota-url
     *  URL locating the sendota service. Defaults to /cgi-bin/sendota. 
     */
    private URL sendotaUrl;
    public URL getSendotaUrl() { return this.sendotaUrl; }
    public void setSendotaUrl(URL sendotaUrl) { this.sendotaUrl = sendotaUrl; }

    /**
     * immediate-sendsms-reply
     * This is a backward compatibility flag: when set, Kannel will immediately answer 
     * to any sendsms requests, without knowing if the bearerbox will ever accept the message. 
     * If set to false (default), smsbox will not reply to HTTP request until the bearerbox 
     * has received the message. 
     */
    private Boolean immediateSendsmsReply;
    public Boolean getImmediateSendsmsReply() { return this.immediateSendsmsReply; }
    public void setImmediateSendsmsReply(Boolean immediateSendsmsReply) { this.immediateSendsmsReply = immediateSendsmsReply; }

    /**
     * sendsms-chars
     *  Only these characters are allowed in 'to' field when send-SMS service is requested 
     * via HTTP. Naturally, you should allow at least 0123456789. The space character (' 
     * ') has special meaning: it is used to separate multiple phone numbers from each 
     * other in multi-send. To disable this feature, do not have it as an accepted character. 
     * If this variable is not set, the default set "0123456789 +-" is used. 
     */
    private String sendsmsChars;
    public String getSendsmsChars() { return this.sendsmsChars; }
    public void setSendsmsChars(String sendsmsChars) { this.sendsmsChars = sendsmsChars; }

    /**
     * global-sender
     *  If set, all sendsms originators are set as these before proceeding. Note that in 
     * a case of most SMS centers you cannot set the sender number, but it is automatically 
     * set as the number of SMSC 
     */
    private String globalSender;
    public String getGlobalSender() { return this.globalSender; }
    public void setGlobalSender(String globalSender) { this.globalSender = globalSender; }

    /**
     * log-file
     *  As with the bearerbox 'core' group. Access-log is used to store information about 
     * MO and send-sms requests. Can be named same as the 'main' access-log (in 'core' 
     * group). 
     */
    private String logFile;
    public String getLogFile() { return this.logFile; }
    public void setLogFile(String logFile) { this.logFile = logFile; }

    /**
     * log-level
     *  
     */
    private Integer logLevel;
    public Integer getLogLevel() { return this.logLevel; }
    public void setLogLevel(Integer logLevel) { this.logLevel = logLevel; }

    /**
     * access-log
     *  
     */
    private String accessLog;
    public String getAccessLog() { return this.accessLog; }
    public void setAccessLog(String accessLog) { this.accessLog = accessLog; }

    /**
     * white-list
     * Load a list of accepted destinations of SMS messages. If a destination of an SMS 
     * message is not in this list, any message received from the HTTP interface is rejected. 
     * See notes of phone number format from numhash.h header file. 
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
     * reply-couldnotfetch
     * If set, replaces the SMS message sent back to user when Kannel could not fetch content. 
     * Defaults to Could not fetch content, sorry.. 
     */
    private String replyCouldnotfetch;
    public String getReplyCouldnotfetch() { return this.replyCouldnotfetch; }
    public void setReplyCouldnotfetch(String replyCouldnotfetch) { this.replyCouldnotfetch = replyCouldnotfetch; }

    /**
     * reply-couldnotrepresent
     * If set, replaces the SMS message sent back when Kannel could not represent the result 
     * as a SMS message. Defaults to Result could not be represented as an SMS message.. 
     */
    private String replyCouldnotrepresent;
    public String getReplyCouldnotrepresent() { return this.replyCouldnotrepresent; }
    public void setReplyCouldnotrepresent(String replyCouldnotrepresent) { this.replyCouldnotrepresent = replyCouldnotrepresent; }

    /**
     * reply-requestfailed
     * If set, replaces the SMS message sent back when Kannel could not contact http service. 
     * Defaults to Request Failed. 
     */
    private String replyRequestfailed;
    public String getReplyRequestfailed() { return this.replyRequestfailed; }
    public void setReplyRequestfailed(String replyRequestfailed) { this.replyRequestfailed = replyRequestfailed; }

    /**
     * reply-emptymessage
     * If set, replaces the SMS message sent back when message is empty. Set to "" to enable 
     * empty messages. Defaults to <Empty reply from service provider>. 
     */
    private String replyEmptymessage;
    public String getReplyEmptymessage() { return this.replyEmptymessage; }
    public void setReplyEmptymessage(String replyEmptymessage) { this.replyEmptymessage = replyEmptymessage; }

    /**
     * mo-recode
     * If enabled, Kannel will try to convert received messages with UCS-2 charset to WINDOWS-1252 
     * or to UTF-8, simplifying external servers jobs. If Kannel is able to recode message, 
     * it will also change coding to 7 bits and charset to windows-1252 or to utf-8. 
     */
    private Boolean moRecode;
    public Boolean getMoRecode() { return this.moRecode; }
    public void setMoRecode(Boolean moRecode) { this.moRecode = moRecode; }

    /**
     * http-request-retry
     * If set, specifies how many retries should be performed for failing HTTP requests 
     * of sms-services. Defaults to 0, which means no retries should be performed and hence 
     * no HTTP request queuing is done. 
     */
    private Integer httpRequestRetry;
    public Integer getHttpRequestRetry() { return this.httpRequestRetry; }
    public void setHttpRequestRetry(Integer httpRequestRetry) { this.httpRequestRetry = httpRequestRetry; }

    /**
     * http-queue-delay
     * If set, specifies how many seconds should pass within the HTTP queuing thread for 
     * retrying a failed HTTP request. Defaults to 10 sec. and is only obeyed if http-request-retry 
     * is set to a non-zero value. 
     */
    private Integer httpQueueDelay;
    public Integer getHttpQueueDelay() { return this.httpQueueDelay; }
    public void setHttpQueueDelay(Integer httpQueueDelay) { this.httpQueueDelay = httpQueueDelay; }

    /**
     * white-list-regex
     * Defines the set of accepted destinations of SMS messages. If a destination of an 
     * SMS message is not in this set, any message received from the HTTP interface is 
     * rejected. See section on Regular Expressions for details. 
     */
    private String whiteListRegex;
    public String getWhiteListRegex() { return this.whiteListRegex; }
    public void setWhiteListRegex(String whiteListRegex) { this.whiteListRegex = whiteListRegex; }

    /**
     * black-list-regex
     * As white-list-regex, but SMS messages to numbers within in this set are automatically 
     * discarded. See section on Regular Expressions for details. 
     */
    private String blackListRegex;
    public String getBlackListRegex() { return this.blackListRegex; }
    public void setBlackListRegex(String blackListRegex) { this.blackListRegex = blackListRegex; }

    /**
     * max-pending-requests
     *   Maximum number of pending MO or DLR messages that are handled in parallel. (Default: 
     * 512) 
     */
    private Integer maxPendingRequests;
    public Integer getMaxPendingRequests() { return this.maxPendingRequests; }
    public void setMaxPendingRequests(Integer maxPendingRequests) { this.maxPendingRequests = maxPendingRequests; }

    /**
     * http-timeout
     *   Sets socket timeout in seconds for outgoing client http connections. Optional. 
     * Defaults to 240 seconds. 
     */
    private Integer httpTimeout;
    public Integer getHttpTimeout() { return this.httpTimeout; }
    public void setHttpTimeout(Integer httpTimeout) { this.httpTimeout = httpTimeout; }

}
