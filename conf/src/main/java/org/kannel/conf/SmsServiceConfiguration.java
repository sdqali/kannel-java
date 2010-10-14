package org.kannel.conf;

import java.net.URL;

/**
 * sms-service configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SmsServiceConfiguration
    extends Configuration
{

    public SmsServiceConfiguration()
    {
	super("sms-service");
    }

    private static final String[] PROP_ORDER = { "keyword","keyword-regex","aliases","name","get-url","post-url","post-xml","file","text","exec","accepted-smsc","accepted-account","allowed-prefix","denied-prefix","allowed-receiver-prefix","denied-receiver-prefix","catch-all","send-sender","strip-keyword","faked-sender","max-messages","accept-x-kannel-headers","assume-plain-text","concatenation","split-chars","split-suffix","omit-empty","header","footer","prefix","suffix","white-list","black-list","accepted-smsc-regex","accepted-account-regex","allowed-prefix-regex","denied-prefix-regex","allowed-receiver-prefix-regex","denied-receiver-prefix-regex","white-list-regex","black-list-regex" };

    public String[] getPropertyOrder() {
	return PROP_ORDER;
    }

    private static final String[] MANDATORY_PROPS = { "keyword" };

    public String[] getMandatoryProps() {
	return MANDATORY_PROPS;
    }

    /**
     * keyword
     *  Services are identified by the first word in the SMS. Each `%s' in the URL corresponds 
     * to one word in the SMS message. Words are separated with spaces. A keyword is matched 
     * only if the number of words in the SMS message is the same as the number of `%s' 
     * fields in the URL. This allows you to configure the gateway to use different URLs 
     * for the same keyword depending on the number of words the SMS message contains. 
     * The keyword is case insensitive, which means you don't have to use aliases to handle 
     * different cased versions of your keyword. 
     */
    private String keyword;
    public String getKeyword() { return this.keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    /**
     * keyword-regex
     *  This field may be used to enable service-selection based on a regular expression. 
     * You can define either keyword or keyword-regex in configuration, but not both in 
     * the same sms-service. keyword-regex is case sensitive. See section on Regular Expressions 
     * for details. 
     */
    private String keywordRegex;
    public String getKeywordRegex() { return this.keywordRegex; }
    public void setKeywordRegex(String keywordRegex) { this.keywordRegex = keywordRegex; }

    /**
     * aliases
     * If the service has aliases, they are listed as a list with each entry separated 
     * with a semicolon (';'). Aliases are case insensitive just like keyword. 
     */
    private String[] aliases;
    public String[] getAliases() { return this.aliases; }
    public void setAliases(String[] aliases) { this.aliases = aliases; }

    /**
     * name
     * Optional name to identify the service in logs. If unset, keyword is used. 
     */
    private String name;
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    /**
     * get-url
     * Requested URL. The url can include a list of parameters, which are parsed before 
     * the url is fetched. See below for these parameters. Also works with plain 'url' 
     */
    private URL getUrl;
    public URL getGetUrl() { return this.getUrl; }
    public void setGetUrl(URL getUrl) { this.getUrl = getUrl; }

    /**
     * post-url
     * Requested URL. As above, but request is done as POST, not GET. Always matches the 
     * keyword, regardless of pattern matching. See notes on POST other where. 
     */
    private URL postUrl;
    public URL getPostUrl() { return this.postUrl; }
    public void setPostUrl(URL postUrl) { this.postUrl = postUrl; }

    /**
     * post-xml
     * Requested URL. As above, but request is done as XML POST. Always matches the keyword, 
     * regardless of pattern matching. See notes on POST other where and XML Post 
     */
    private URL postXml;
    public URL getPostXml() { return this.postXml; }
    public void setPostXml(URL postXml) { this.postXml = postXml; }

    /**
     * file
     * File read from a local disc. Use this variable only if no url is set. All escape 
     * codes (parameters) in url are supported in filename. The last character of the file 
     * (usually linefeed) is removed. 
     */
    private String file;
    public String getFile() { return this.file; }
    public void setFile(String file) { this.file = file; }

    /**
     * text
     * Predefined text answer. Only if there is neither url nor file set. Escape codes 
     * (parameters) are usable here, too. 
     */
    private String text;
    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }

    /**
     * exec
     * Executes the given shell command as the current UID of the running smsbox user and 
     * returns the output to stdout as reply. Escape codes (parameters) are usable here, 
     * too. BEWARE: You may harm your system if you use this sms-service type without serious 
     * caution! Make sure anyone who is allowed to use these kind of services is checked 
     * using white/black-list mechanisms for security reasons. 
     */
    private String exec;
    public String getExec() { return this.exec; }
    public void setExec(String exec) { this.exec = exec; }

    /**
     * accepted-smsc
     * Accept ONLY SMS messages arriving from SMSC with matching ID. [a] Separate multiple 
     * entries with ';'. For example, if accepted-smsc is "RL;SON", accept messages which 
     * originate from SMSC with ID set as 'RL' or 'SON' 
     */
    private String[] acceptedSmsc;
    public String[] getAcceptedSmsc() { return this.acceptedSmsc; }
    public void setAcceptedSmsc(String[] acceptedSmsc) { this.acceptedSmsc = acceptedSmsc; }

    /**
     * accepted-account
     * Accept ONLY SMS messages arriving with a matching ACCOUNT field. [b] Separate multiple 
     * entries with ';'. For example, if accepted-account is "FOO;BAR", accept messages 
     * which originate with the ACCOUNT field set as 'FOO' or 'BAR' 
     */
    private String[] acceptedAccount;
    public String[] getAcceptedAccount() { return this.acceptedAccount; }
    public void setAcceptedAccount(String[] acceptedAccount) { this.acceptedAccount = acceptedAccount; }

    /**
     * allowed-prefix
     * A list of phone number prefixes of the sender number which are accepted to be received 
     * by this service. [c] Multiple entries are separated with semicolon (';'). For example, 
     * "91;93" selects this service for these prefixes. If denied-prefix is unset, only 
     * this numbers are allowed. If denied is set, number are allowed if present in allowed 
     * or not in denied list. 
     */
    private String[] allowedPrefix;
    public String[] getAllowedPrefix() { return this.allowedPrefix; }
    public void setAllowedPrefix(String[] allowedPrefix) { this.allowedPrefix = allowedPrefix; }

    /**
     * denied-prefix
     * A list of phone number prefixes of the sender number which are NOT accepted to be 
     * sent through this SMSC. 
     */
    private String[] deniedPrefix;
    public String[] getDeniedPrefix() { return this.deniedPrefix; }
    public void setDeniedPrefix(String[] deniedPrefix) { this.deniedPrefix = deniedPrefix; }

    /**
     * allowed-receiver-prefix
     * A list of phone number prefixes of the receiver number which are accepted to be 
     * received by this service. This may be used to allow only inbound SMS to certain 
     * shortcut numbers to be allowed to this service. 
     */
    private String[] allowedReceiverPrefix;
    public String[] getAllowedReceiverPrefix() { return this.allowedReceiverPrefix; }
    public void setAllowedReceiverPrefix(String[] allowedReceiverPrefix) { this.allowedReceiverPrefix = allowedReceiverPrefix; }

    /**
     * denied-receiver-prefix
     * A list of phone number prefixes of the receiver number which are NOT accepted to 
     * be sent through this SMSC. 
     */
    private String[] deniedReceiverPrefix;
    public String[] getDeniedReceiverPrefix() { return this.deniedReceiverPrefix; }
    public void setDeniedReceiverPrefix(String[] deniedReceiverPrefix) { this.deniedReceiverPrefix = deniedReceiverPrefix; }

    /**
     * catch-all
     * Catch keyword regardless of '%s' parameters in pattern. 
     */
    private Boolean catchAll;
    public Boolean getCatchAll() { return this.catchAll; }
    public void setCatchAll(Boolean catchAll) { this.catchAll = catchAll; }

    /**
     * send-sender
     * Used only with POST. If set to true, number of the handset is set, otherwise not. 
     */
    private Boolean sendSender;
    public Boolean getSendSender() { return this.sendSender; }
    public void setSendSender(Boolean sendSender) { this.sendSender = sendSender; }

    /**
     * strip-keyword
     * Used only with POST. Remove matched keyword from message text before sending it 
     * onward. 
     */
    private Boolean stripKeyword;
    public Boolean getStripKeyword() { return this.stripKeyword; }
    public void setStripKeyword(Boolean stripKeyword) { this.stripKeyword = stripKeyword; }

    /**
     * faked-sender
     * This number is set as sender. Most SMS centers ignore this, and use their fixed 
     * number instead. This option overrides all other sender setting methods. 
     */
    private String fakedSender;
    public String getFakedSender() { return this.fakedSender; }
    public void setFakedSender(String fakedSender) { this.fakedSender = fakedSender; }

    /**
     * max-messages
     * If the message to be sent is longer than maximum length of an SMS it will be split 
     * into several parts. max-messages lets you specify a maximum number of individual 
     * SMS messages that can be used. If max-messages is set to 0, no reply is sent, except 
     * for error messages. 
     */
    private Integer maxMessages;
    public Integer getMaxMessages() { return this.maxMessages; }
    public void setMaxMessages(Integer maxMessages) { this.maxMessages = maxMessages; }

    /**
     * accept-x-kannel-headers
     * Request reply can include special X-Kannel headers but these are only accepted if 
     * this variable is set to true. See Extended headers. 
     */
    private Boolean acceptXKannelHeaders;
    public Boolean getAcceptXKannelHeaders() { return this.acceptXKannelHeaders; }
    public void setAcceptXKannelHeaders(Boolean acceptXKannelHeaders) { this.acceptXKannelHeaders = acceptXKannelHeaders; }

    /**
     * assume-plain-text
     * If client does not set Content-Type for reply, it is normally application/octet-stream 
     * which is then handled as data in Kannel. This can be forced to be plain/text to 
     * allow backward compatibility, when data was not expected. 
     */
    private Boolean assumePlainText;
    public Boolean getAssumePlainText() { return this.assumePlainText; }
    public void setAssumePlainText(Boolean assumePlainText) { this.assumePlainText = assumePlainText; }

    /**
     * concatenation
     * Long messages can be sent as independent SMS messages with concatenation = false 
     * or as concatenated messages with concatenation = true. Concatenated messages are 
     * reassembled into one long message by the receiving device. 
     */
    private Boolean concatenation;
    public Boolean getConcatenation() { return this.concatenation; }
    public void setConcatenation(Boolean concatenation) { this.concatenation = concatenation; }

    /**
     * split-chars
     * Allowed characters to split the message into several messages. So, with "#!" the 
     * message is split from last '#' or '!', which is included in the previous part. 
     */
    private String splitChars;
    public String getSplitChars() { return this.splitChars; }
    public void setSplitChars(String splitChars) { this.splitChars = splitChars; }

    /**
     * split-suffix
     * If the message is split into several ones, this string is appended to each message 
     * except the last one. 
     */
    private String splitSuffix;
    public String getSplitSuffix() { return this.splitSuffix; }
    public void setSplitSuffix(String splitSuffix) { this.splitSuffix = splitSuffix; }

    /**
     * omit-empty
     * Normally, Kannel sends a warning to the user if there was an empty reply from the 
     * service provider. If omit-empty is set to 'true', Kannel will send nothing at all 
     * in such a case. 
     */
    private Boolean omitEmpty;
    public Boolean getOmitEmpty() { return this.omitEmpty; }
    public void setOmitEmpty(Boolean omitEmpty) { this.omitEmpty = omitEmpty; }

    /**
     * header
     * If specified, this string is automatically added to each SMS sent with this service. 
     * If the message is split, it is added to each part. 
     */
    private String header;
    public String getHeader() { return this.header; }
    public void setHeader(String header) { this.header = header; }

    /**
     * footer
     * As header, but not inserted into head but appended to end. 
     */
    private String footer;
    public String getFooter() { return this.footer; }
    public void setFooter(String footer) { this.footer = footer; }

    /**
     * prefix
     * Stuff in answer that is cut away, only things between prefix and suffix is left. 
     * Not case sensitive. Matches the first prefix and then the first suffix. These are 
     * only used for url type services, and only if both are specified. 
     */
    private String prefix;
    public String getPrefix() { return this.prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    /**
     * suffix
     *  
     */
    private String suffix;
    public String getSuffix() { return this.suffix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }

    /**
     * white-list
     * Load a list of accepted senders of SMS messages. If a sender of an SMS message is 
     * not in this list, any message received from the SMSC is rejected, unless a black-list 
     * service is defined. See notes of phone number format from numhash.h header file. 
     */
    private URL whiteList;
    public URL getWhiteList() { return this.whiteList; }
    public void setWhiteList(URL whiteList) { this.whiteList = whiteList; }

    /**
     * black-list
     * As white-list, but SMS messages from these numbers are automatically discarded 
     */
    private URL blackList;
    public URL getBlackList() { return this.blackList; }
    public void setBlackList(URL blackList) { this.blackList = blackList; }

    /**
     * accepted-smsc-regex
     * Accept only SMS messages arriving from SMSCs with a matching ID. [d] See section 
     * on Regular Expressions for details. 
     */
    private String acceptedSmscRegex;
    public String getAcceptedSmscRegex() { return this.acceptedSmscRegex; }
    public void setAcceptedSmscRegex(String acceptedSmscRegex) { this.acceptedSmscRegex = acceptedSmscRegex; }

    /**
     * accepted-account-regex
     * Accept ONLY SMS messages arriving with a matching ACCOUNT field. [e] See section 
     * on Regular Expressions for details. 
     */
    private String acceptedAccountRegex;
    public String getAcceptedAccountRegex() { return this.acceptedAccountRegex; }
    public void setAcceptedAccountRegex(String acceptedAccountRegex) { this.acceptedAccountRegex = acceptedAccountRegex; }

    /**
     * allowed-prefix-regex
     * A set of phone number prefixes of sender-numbers accepted by this service. [f] See 
     * section on Regular Expressions for details. 
     */
    private String allowedPrefixRegex;
    public String getAllowedPrefixRegex() { return this.allowedPrefixRegex; }
    public void setAllowedPrefixRegex(String allowedPrefixRegex) { this.allowedPrefixRegex = allowedPrefixRegex; }

    /**
     * denied-prefix-regex
     * A set of phone number prefixes of sender-numbers which may not use this service. 
     * See section on Regular Expressions for details. 
     */
    private String deniedPrefixRegex;
    public String getDeniedPrefixRegex() { return this.deniedPrefixRegex; }
    public void setDeniedPrefixRegex(String deniedPrefixRegex) { this.deniedPrefixRegex = deniedPrefixRegex; }

    /**
     * allowed-receiver-prefix-regex
     * A set of phone number prefixes of receiver-numbers which may receive data sent by 
     * this service. This can be used to allow only inbound SMS to certain shortcut numbers 
     * to be allowed to this service. See section on Regular Expressions for details. 
     */
    private String allowedReceiverPrefixRegex;
    public String getAllowedReceiverPrefixRegex() { return this.allowedReceiverPrefixRegex; }
    public void setAllowedReceiverPrefixRegex(String allowedReceiverPrefixRegex) { this.allowedReceiverPrefixRegex = allowedReceiverPrefixRegex; }

    /**
     * denied-receiver-prefix-regex
     * A set of phone number prefixes of receiver-numbers which may not receive data sent 
     * by this service. See section on Regular Expressions for details. 
     */
    private String deniedReceiverPrefixRegex;
    public String getDeniedReceiverPrefixRegex() { return this.deniedReceiverPrefixRegex; }
    public void setDeniedReceiverPrefixRegex(String deniedReceiverPrefixRegex) { this.deniedReceiverPrefixRegex = deniedReceiverPrefixRegex; }

    /**
     * white-list-regex
     * Defines a set of accepted senders of SMS messages. If a sender of an SMS message 
     * is not in this list, the message is rejected. See section on Regular Expressions 
     * for details. 
     */
    private String whiteListRegex;
    public String getWhiteListRegex() { return this.whiteListRegex; }
    public void setWhiteListRegex(String whiteListRegex) { this.whiteListRegex = whiteListRegex; }

    /**
     * black-list-regex
     * As white-list-regex, but SMS messages from these numbers are automatically discarded. 
     * See section on Regular Expressions for details. 
     */
    private String blackListRegex;
    public String getBlackListRegex() { return this.blackListRegex; }
    public void setBlackListRegex(String blackListRegex) { this.blackListRegex = blackListRegex; }

}
