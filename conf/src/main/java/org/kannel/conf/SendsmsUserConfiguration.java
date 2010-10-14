package org.kannel.conf;

import java.net.URL;

/**
 * sendsms-user configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SendsmsUserConfiguration
    extends Configuration
{

    public SendsmsUserConfiguration()
    {
	super("sendsms-user");
    }

    private static final String[] PROP_ORDER = { "username","password","name","user-deny-ip","user-allow-IP","forced-smsc","default-smsc","default-sender","faked-sender","max-messages","concatenation","split-chars","split-suffix","omit-empty","header","footer","allowed-prefix","denied-prefix","white-list","black-list","dlr-url","allowed-prefix-regex","denied-prefix-regex","white-list-regex","black-list-regex" };

    public String[] getPropertyOrder() {
	return PROP_ORDER;
    }

    private static final String[] MANDATORY_PROPS = { "username","password" };

    public String[] getMandatoryProps() {
	return MANDATORY_PROPS;
    }

    /**
     * username
     *  Name for the user/account. 
     */
    private String username;
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    /**
     * password
     *  Password for the user (see HTTP interface, below) 
     */
    private String password;
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * name
     * As in 'sms-service' groups. 
     */
    private String name;
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    /**
     * user-deny-ip
     *     As other deny/allow IP lists, but for this user (i.e. this user is not allowed 
     * to do the SMS push HTTP request from other IPs than allowed ones). If not set, there 
     * is no limitations. 
     */
    private String[] userDenyIp;
    public String[] getUserDenyIp() { return this.userDenyIp; }
    public void setUserDenyIp(String[] userDenyIp) { this.userDenyIp = userDenyIp; }

    /**
     * user-allow-IP
     *  
     */
    private String[] userAllowIp;
    public String[] getUserAllowIp() { return this.userAllowIp; }
    public void setUserAllowIp(String[] userAllowIp) { this.userAllowIp = userAllowIp; }

    /**
     * forced-smsc
     * Force SMSC ID as a 'string' (linked to SMS routing, see 'smsc' groups) 
     */
    private String forcedSmsc;
    public String getForcedSmsc() { return this.forcedSmsc; }
    public void setForcedSmsc(String forcedSmsc) { this.forcedSmsc = forcedSmsc; }

    /**
     * default-smsc
     * If no SMSC ID is given with the send-sms request (see below), use this one. No idea 
     * to use with forced-smsc. 
     */
    private String defaultSmsc;
    public String getDefaultSmsc() { return this.defaultSmsc; }
    public void setDefaultSmsc(String defaultSmsc) { this.defaultSmsc = defaultSmsc; }

    /**
     * default-sender
     * This number is set as sender if not set by from get/post parameter 
     */
    private String defaultSender;
    public String getDefaultSender() { return this.defaultSender; }
    public void setDefaultSender(String defaultSender) { this.defaultSender = defaultSender; }

    /**
     * faked-sender
     * As in 'sms-service' groups 
     */
    private String fakedSender;
    public String getFakedSender() { return this.fakedSender; }
    public void setFakedSender(String fakedSender) { this.fakedSender = fakedSender; }

    /**
     * max-messages
     *  
     */
    private Integer maxMessages;
    public Integer getMaxMessages() { return this.maxMessages; }
    public void setMaxMessages(Integer maxMessages) { this.maxMessages = maxMessages; }

    /**
     * concatenation
     *  
     */
    private Boolean concatenation;
    public Boolean getConcatenation() { return this.concatenation; }
    public void setConcatenation(Boolean concatenation) { this.concatenation = concatenation; }

    /**
     * split-chars
     *  
     */
    private String splitChars;
    public String getSplitChars() { return this.splitChars; }
    public void setSplitChars(String splitChars) { this.splitChars = splitChars; }

    /**
     * split-suffix
     *  
     */
    private String splitSuffix;
    public String getSplitSuffix() { return this.splitSuffix; }
    public void setSplitSuffix(String splitSuffix) { this.splitSuffix = splitSuffix; }

    /**
     * omit-empty
     *  
     */
    private Boolean omitEmpty;
    public Boolean getOmitEmpty() { return this.omitEmpty; }
    public void setOmitEmpty(Boolean omitEmpty) { this.omitEmpty = omitEmpty; }

    /**
     * header
     *  
     */
    private String header;
    public String getHeader() { return this.header; }
    public void setHeader(String header) { this.header = header; }

    /**
     * footer
     *  
     */
    private String footer;
    public String getFooter() { return this.footer; }
    public void setFooter(String footer) { this.footer = footer; }

    /**
     * allowed-prefix
     *      A list of phone number prefixes which are accepted to be sent using this username. 
     * Multiple entries are separated with semicolon (';'). For example, "040;050" prevents 
     * sending of any SMS message with prefix of 040 or 050 through this SMSC. If denied-prefix 
     * is unset, only this numbers are allowed. If set, number are allowed if present in 
     * allowed or not in denied list. 
     */
    private String[] allowedPrefix;
    public String[] getAllowedPrefix() { return this.allowedPrefix; }
    public void setAllowedPrefix(String[] allowedPrefix) { this.allowedPrefix = allowedPrefix; }

    /**
     * denied-prefix
     *      A list of phone number prefixes which are NOT accepted to be sent using this 
     * username. 
     */
    private String[] deniedPrefix;
    public String[] getDeniedPrefix() { return this.deniedPrefix; }
    public void setDeniedPrefix(String[] deniedPrefix) { this.deniedPrefix = deniedPrefix; }

    /**
     * white-list
     *      Load a list of accepted destinations of SMS messages. If a destination of an 
     * SMS message is not in this list, any message received from the HTTP interface is 
     * rejected. See notes of phone number format from numhash.h header file. 
     */
    private URL whiteList;
    public URL getWhiteList() { return this.whiteList; }
    public void setWhiteList(URL whiteList) { this.whiteList = whiteList; }

    /**
     * black-list
     *      As white-list, but SMS messages from these numbers are automatically rejected. 
     */
    private URL blackList;
    public URL getBlackList() { return this.blackList; }
    public void setBlackList(URL blackList) { this.blackList = blackList; }

    /**
     * dlr-url
     *      URL to be fetched if a dlr-mask CGI parameter is present. 
     */
    private URL dlrUrl;
    public URL getDlrUrl() { return this.dlrUrl; }
    public void setDlrUrl(URL dlrUrl) { this.dlrUrl = dlrUrl; }

    /**
     * allowed-prefix-regex
     *      A set of phone numbers which are accepted to be sent using this username. See 
     * section on Regular Expressions for details. 
     */
    private String allowedPrefixRegex;
    public String getAllowedPrefixRegex() { return this.allowedPrefixRegex; }
    public void setAllowedPrefixRegex(String allowedPrefixRegex) { this.allowedPrefixRegex = allowedPrefixRegex; }

    /**
     * denied-prefix-regex
     *      A set of phone numbers which may not send using this username. See section 
     * on Regular Expressions for details. 
     */
    private String deniedPrefixRegex;
    public String getDeniedPrefixRegex() { return this.deniedPrefixRegex; }
    public void setDeniedPrefixRegex(String deniedPrefixRegex) { this.deniedPrefixRegex = deniedPrefixRegex; }

    /**
     * white-list-regex
     *      Defines a set of accepted destinations of SMS messages. If a destination of 
     * an SMS message is not in this list, any message received from the HTTP interface 
     * is rejected. See section on Regular Expressions for details. 
     */
    private String whiteListRegex;
    public String getWhiteListRegex() { return this.whiteListRegex; }
    public void setWhiteListRegex(String whiteListRegex) { this.whiteListRegex = whiteListRegex; }

    /**
     * black-list-regex
     *      As white-list-regex, but SMS messages originating from a number matching the 
     * pattern are discarded. See section on Regular Expressions for details. 
     */
    private String blackListRegex;
    public String getBlackListRegex() { return this.blackListRegex; }
    public void setBlackListRegex(String blackListRegex) { this.blackListRegex = blackListRegex; }

}
