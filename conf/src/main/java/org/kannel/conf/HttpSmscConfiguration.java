package org.kannel.conf;

import java.net.URL;

/**
 * http smsc configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class HttpSmscConfiguration
    extends SmscConfiguration
{

    public HttpSmscConfiguration()
    {
	super();
    }

    private static final String[] HTTP_SMSC_PROP_ORDER = { "system-type","send-url","no-sender","no-coding","no-sep","port","use-ssl","connect-allow-ip","smsc-username","smsc-password","status-success-regex","status-permfail-regex","status-tempfail-regex","generic-foreign-id-regex","generic-param-username","generic-param-password","generic-param-from","generic-param-to","generic-param-text","generic-param-udh","generic-param-service","generic-param-account","generic-param-binfo","generic-param-dlr-mask","generic-param-dlr-url","generic-param-dlr-mid","generic-param-flash","generic-param-mclass","generic-param-mwi","generic-param-coding","generic-param-validity","generic-param-deferred","generic-param-foreign-id","generic-message-sent","generic-status-sent","generic-status-error" };

    public String[] getPropertyOrder() {
	return arrayCat(super.getPropertyOrder(), HTTP_SMSC_PROP_ORDER);
    }

    private static final String[] HTTP_SMSC_MANDATORY_PROPS = { "system-type","send-url","port" };
    
    public String[] getMandatoryProps() {
	return arrayCat(super.getPropertyOrder(), HTTP_SMSC_MANDATORY_PROPS);
    }

    /**
     * system-type
     * Type of HTTP connection. Currently supported are: 'kannel', 'brunet', 'xidris', 
     * 'wapme', 'clickatell' and 'generic'. 
     */
    private String systemType;
    public String getSystemType() { return this.systemType; }
    public void setSystemType(String systemType) { this.systemType = systemType; }

    /**
     * send-url
     * Location to send MT messages. This URL is expanded by used system, if need to. 
     */
    private URL sendUrl;
    public URL getSendUrl() { return this.sendUrl; }
    public void setSendUrl(URL sendUrl) { this.sendUrl = sendUrl; }

    /**
     * no-sender
     * Do not add variable sender to the send-url. 
     */
    private Boolean noSender;
    public Boolean getNoSender() { return this.noSender; }
    public void setNoSender(Boolean noSender) { this.noSender = noSender; }

    /**
     * no-coding
     * Do not add variable coding to the send-url. 
     */
    private Boolean noCoding;
    public Boolean getNoCoding() { return this.noCoding; }
    public void setNoCoding(Boolean noCoding) { this.noCoding = noCoding; }

    /**
     * no-sep
     * Represent udh and text as a numeric string containing the hex-dump. For instance, 
     * text=%2b123 is represented as text=2b313233. 
     */
    private Boolean noSep;
    public Boolean getNoSep() { return this.noSep; }
    public void setNoSep(Boolean noSep) { this.noSep = noSep; }

    /**
     * port
     * Port number in which Kannel listens to (MO) messages from other gateway 
     */
    private Integer port;
    public Integer getPort() { return this.port; }
    public void setPort(Integer port) { this.port = port; }

    /**
     * use-ssl
     * Defines whether listen port should use SSL. 
     */
    private Boolean useSsl;
    public Boolean getUseSsl() { return this.useSsl; }
    public void setUseSsl(Boolean useSsl) { this.useSsl = useSsl; }

    /**
     * connect-allow-ip
     * IPs allowed to use this interface. If not set, "127.0.0.1" (localhost) is the only 
     * host allowed to connect. 
     */
    private String[] connectAllowIp;
    public String[] getConnectAllowIp() { return this.connectAllowIp; }
    public void setConnectAllowIp(String[] connectAllowIp) { this.connectAllowIp = connectAllowIp; }

    /**
     * smsc-username
     * Username associated to connection, if needed. Kannel requires this, and it is the 
     * same as send-sms username at other end. 
     */
    private String smscUsername;
    public String getSmscUsername() { return this.smscUsername; }
    public void setSmscUsername(String smscUsername) { this.smscUsername = smscUsername; }

    /**
     * smsc-password
     * Password for username, if needed. 
     */
    private String smscPassword;
    public String getSmscPassword() { return this.smscPassword; }
    public void setSmscPassword(String smscPassword) { this.smscPassword = smscPassword; }

    /**
     * status-success-regex
     * Regular expression to match against HTTP response body content, indicating a successful 
     * submission. 
     */
    private String statusSuccessRegex;
    public String getStatusSuccessRegex() { return this.statusSuccessRegex; }
    public void setStatusSuccessRegex(String statusSuccessRegex) { this.statusSuccessRegex = statusSuccessRegex; }

    /**
     * status-permfail-regex
     * Regular expression to match against HTTP response body content, indicating a permanent 
     * failure. 
     */
    private String statusPermfailRegex;
    public String getStatusPermfailRegex() { return this.statusPermfailRegex; }
    public void setStatusPermfailRegex(String statusPermfailRegex) { this.statusPermfailRegex = statusPermfailRegex; }

    /**
     * status-tempfail-regex
     * Regular expression to match against HTTP response body content, indicating a temporary 
     * failure. 
     */
    private String statusTempfailRegex;
    public String getStatusTempfailRegex() { return this.statusTempfailRegex; }
    public void setStatusTempfailRegex(String statusTempfailRegex) { this.statusTempfailRegex = statusTempfailRegex; }

    /**
     * generic-foreign-id-regex
     * Regular expression to match against HTTP response body content to get the foreign 
     * message id in case of successful submission. 
     */
    private String genericForeignIdRegex;
    public String getGenericForeignIdRegex() { return this.genericForeignIdRegex; }
    public void setGenericForeignIdRegex(String genericForeignIdRegex) { this.genericForeignIdRegex = genericForeignIdRegex; }

    /**
     * generic-param-username
     * Overrides the default parameter for the 'username' field used on incoming requests. 
     */
    private String genericParamUsername;
    public String getGenericParamUsername() { return this.genericParamUsername; }
    public void setGenericParamUsername(String genericParamUsername) { this.genericParamUsername = genericParamUsername; }

    /**
     * generic-param-password
     * Overrides the default parameter for the 'password' field used on incoming requests. 
     */
    private String genericParamPassword;
    public String getGenericParamPassword() { return this.genericParamPassword; }
    public void setGenericParamPassword(String genericParamPassword) { this.genericParamPassword = genericParamPassword; }

    /**
     * generic-param-from
     * Overrides the default parameter for the 'from' field used on incoming requests. 
     */
    private String genericParamFrom;
    public String getGenericParamFrom() { return this.genericParamFrom; }
    public void setGenericParamFrom(String genericParamFrom) { this.genericParamFrom = genericParamFrom; }

    /**
     * generic-param-to
     * Overrides the default parameter for the 'to' field used on incoming requests. 
     */
    private String genericParamTo;
    public String getGenericParamTo() { return this.genericParamTo; }
    public void setGenericParamTo(String genericParamTo) { this.genericParamTo = genericParamTo; }

    /**
     * generic-param-text
     * Overrides the default parameter for the 'text' field used on incoming requests. 
     */
    private String genericParamText;
    public String getGenericParamText() { return this.genericParamText; }
    public void setGenericParamText(String genericParamText) { this.genericParamText = genericParamText; }

    /**
     * generic-param-udh
     * Overrides the default parameter for the 'udh' field used on incoming requests. 
     */
    private String genericParamUdh;
    public String getGenericParamUdh() { return this.genericParamUdh; }
    public void setGenericParamUdh(String genericParamUdh) { this.genericParamUdh = genericParamUdh; }

    /**
     * generic-param-service
     * Overrides the default parameter for the 'service' field used on incoming requests. 
     */
    private String genericParamService;
    public String getGenericParamService() { return this.genericParamService; }
    public void setGenericParamService(String genericParamService) { this.genericParamService = genericParamService; }

    /**
     * generic-param-account
     * Overrides the default parameter for the 'account' field used on incoming requests. 
     */
    private String genericParamAccount;
    public String getGenericParamAccount() { return this.genericParamAccount; }
    public void setGenericParamAccount(String genericParamAccount) { this.genericParamAccount = genericParamAccount; }

    /**
     * generic-param-binfo
     * Overrides the default parameter for the 'binfo' field used on incoming requests. 
     */
    private String genericParamBinfo;
    public String getGenericParamBinfo() { return this.genericParamBinfo; }
    public void setGenericParamBinfo(String genericParamBinfo) { this.genericParamBinfo = genericParamBinfo; }

    /**
     * generic-param-dlr-mask
     * Overrides the default parameter for the 'dlr-mask' field used on incoming requests. 
     */
    private String genericParamDlrMask;
    public String getGenericParamDlrMask() { return this.genericParamDlrMask; }
    public void setGenericParamDlrMask(String genericParamDlrMask) { this.genericParamDlrMask = genericParamDlrMask; }

    /**
     * generic-param-dlr-url
     * Overrides the default parameter for the 'dlr-url' field used on incoming requests. 
     */
    private String genericParamDlrUrl;
    public String getGenericParamDlrUrl() { return this.genericParamDlrUrl; }
    public void setGenericParamDlrUrl(String genericParamDlrUrl) { this.genericParamDlrUrl = genericParamDlrUrl; }

    /**
     * generic-param-dlr-mid
     * Overrides the default parameter for the 'dlr-mid' field used on incoming requests. 
     */
    private String genericParamDlrMid;
    public String getGenericParamDlrMid() { return this.genericParamDlrMid; }
    public void setGenericParamDlrMid(String genericParamDlrMid) { this.genericParamDlrMid = genericParamDlrMid; }

    /**
     * generic-param-flash
     * Overrides the default parameter for the 'flash' field used on incoming requests. 
     */
    private String genericParamFlash;
    public String getGenericParamFlash() { return this.genericParamFlash; }
    public void setGenericParamFlash(String genericParamFlash) { this.genericParamFlash = genericParamFlash; }

    /**
     * generic-param-mclass
     * Overrides the default parameter for the 'mclass' field used on incoming requests. 
     */
    private String genericParamMclass;
    public String getGenericParamMclass() { return this.genericParamMclass; }
    public void setGenericParamMclass(String genericParamMclass) { this.genericParamMclass = genericParamMclass; }

    /**
     * generic-param-mwi
     * Overrides the default parameter for the 'mwi' field used on incoming requests. 
     */
    private String genericParamMwi;
    public String getGenericParamMwi() { return this.genericParamMwi; }
    public void setGenericParamMwi(String genericParamMwi) { this.genericParamMwi = genericParamMwi; }

    /**
     * generic-param-coding
     * Overrides the default parameter for the 'coding' field used on incoming requests. 
     */
    private String genericParamCoding;
    public String getGenericParamCoding() { return this.genericParamCoding; }
    public void setGenericParamCoding(String genericParamCoding) { this.genericParamCoding = genericParamCoding; }

    /**
     * generic-param-validity
     * Overrides the default parameter for the 'validity' field used on incoming requests. 
     */
    private String genericParamValidity;
    public String getGenericParamValidity() { return this.genericParamValidity; }
    public void setGenericParamValidity(String genericParamValidity) { this.genericParamValidity = genericParamValidity; }

    /**
     * generic-param-deferred
     * Overrides the default parameter for the 'deferred' field used on incoming requests. 
     */
    private String genericParamDeferred;
    public String getGenericParamDeferred() { return this.genericParamDeferred; }
    public void setGenericParamDeferred(String genericParamDeferred) { this.genericParamDeferred = genericParamDeferred; }

    /**
     * generic-param-foreign-id
     * Overrides the default parameter for the 'foreign-id' field used on incoming requests. 
     */
    private String genericParamForeignId;
    public String getGenericParamForeignId() { return this.genericParamForeignId; }
    public void setGenericParamForeignId(String genericParamForeignId) { this.genericParamForeignId = genericParamForeignId; }

    /**
     * generic-message-sent
     * It allows you to set the text returned when a succesful request is made. If not 
     * set, defaults to 'Sent.'. Note that you can use all sms service escapes here, see 
     * Parameters (Escape Codes) for details. 
     */
    private String genericMessageSent;
    public String getGenericMessageSent() { return this.genericMessageSent; }
    public void setGenericMessageSent(String genericMessageSent) { this.genericMessageSent = genericMessageSent; }

    /**
     * generic-status-sent
     * Overrides the HTTP status code returned when a successful request is made. If not 
     * set, defaults to 202 (HTTP_ACCEPTED). 
     */
    private String genericStatusSent;
    public String getGenericStatusSent() { return this.genericStatusSent; }
    public void setGenericStatusSent(String genericStatusSent) { this.genericStatusSent = genericStatusSent; }

    /**
     * generic-status-error
     * Overrides the HTTP status code returned when a request is rejected for any reason. 
     * If not set, defaults to 202 (HTTP_ACCEPTED). 
     */
    private String genericStatusError;
    public String getGenericStatusError() { return this.genericStatusError; }
    public void setGenericStatusError(String genericStatusError) { this.genericStatusError = genericStatusError; }

}
