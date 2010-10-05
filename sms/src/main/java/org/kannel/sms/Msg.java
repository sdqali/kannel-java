package org.kannel.sms;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

/**
 * Abstract message. Used by Dlr and Sms.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public abstract class Msg
{

    /**
     * %k
     * the keyword in the SMS request (i.e., the first word in the SMS message)
     */
    private String keyword;
    public String getKeyword() { return this.keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    /**
     * %s
     * next word from the SMS message, starting with the second one (i.e., the first word,
     * the keyword, is not included); problematic characters for URLs are encoded (e.g.,
     * '+' becomes '%2B')
     */
    private String secondKeyword;
    public String getSecondKeyword() { return this.secondKeyword; }
    public void setSecondKeyword(String secondKeyword) { this.secondKeyword = secondKeyword; }

    /**
     * %S
     * same as %s, but '*' is converted to '~' (useful when user enters a URL) and URL
     * encoding isn't done (all others do URL encode)
     */
    private String secondKeywordConverted;
    public String getSecondKeywordConverted() { return this.secondKeywordConverted; }
    public void setSecondKeywordConverted(String secondKeywordConverted) { this.secondKeywordConverted = secondKeywordConverted; }

    /**
     * %r
     * words not yet used by %s; e.g., if the message is "FOO BAR FOOBAR BAZ", and the
     * has been one %s, %r will mean "FOOBAR BAZ"
     */
    private String query;
    public String getQuery() { return this.query; }
    public void setQuery(String query) { this.query = query; }

    /**
     * %a
     * all words of the SMS message, including the first one, with spaces squeezed to one
     *
     * text
     * Contents of the message, URL encoded as necessary. The content can be more than
     * 160 characters, but then sendsms-user group must have max-messages set more than 1.
     */
    private String text;
    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }

    /**
     * %b
     * the original SMS message, in a binary form
     */
    private byte[] binaryMessage;
    public byte[] getBinaryMessage() { return this.binaryMessage; }
    public void setBinaryMessage(byte[] binaryMessage) { this.binaryMessage = binaryMessage; }

    /**
     * %t
     * the time the message was sent, formatted as "YYYY-MM-DD HH:MM", e.g., "1999-09-21 14:18"
     */
    private Date timeSent;
    public Date getTimeSent() { return this.timeSent; }
    public void setTimeSent(Date timeSent) { this.timeSent = timeSent; }

    /**
     * %T
     * the time the message was sent, in UNIX epoch timestamp format
     */
    private long unixtimeSent;
    public long getUnixtimeSent() { return this.unixtimeSent; }
    public void setUnixtimeSent(long unixtimeSent) { this.unixtimeSent = unixtimeSent; }

    /**
     * %p
     * the phone number of the sender of the SMS message
     *
     * from
     * Phone number of the sender. This field is usually overridden by the SMS Center, or
     * it can be overridden by faked-sender variable in the sendsms-user group. If this
     * variable is not set, smsbox global-sender is used.
     */
    private String from;
    public String getFrom() { return this.from; }
    public void setFrom(String from) { this.from = from; }

    /**
     * %P
     * the phone number of the receiver of the SMS message
     *
     * to
     * Phone number of the receiver. To send to multiple receivers, separate each entry
     * with space (' ', '+' url-encoded) - but note that this can be deactivated via sendsms-chars
     * in the 'smsbox' group.
     */
    private String to;
    public String getTo() { return this.to; }
    public void setTo(String to) { this.to = to; }

    /**
     * %q
     * like %p, but a leading `00' is replaced with `+'
     */
    private String fromConverted;
    public String getFromConverted() { return this.fromConverted; }
    public void setFromConverted(String fromConverted) { this.fromConverted = fromConverted; }

    /**
     * %Q
     * like %P, but a leading `00' is replaced with `+'
     */
    private String toConverted;
    public String getToConverted() { return this.toConverted; }
    public void setToConverted(String toConverted) { this.toConverted = toConverted; }

    /**
     * %i
     * the smsc-id of the connection that received the message
     *
     * smsc
     * Optional virtual smsc-id from which the message is supposed to have arrived. This
     * is used for routing purposes, if any denied or preferred SMS centers are set up
     * in SMS center configuration. This variable can be overridden with a forced-smsc
     * configuration variable. Likewise, the default-smsc variable can be used to set the
     * SMSC if it is not set otherwise.
     */
    private String smsc;
    public String getSmsc() { return this.smsc; }
    public void setSmsc(String smsc) { this.smsc = smsc; }

    /**
     * %I
     * the SMS ID of the internal message structure
     */
    private String messageId;
    public String getMessageId() { return this.messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    /**
     * %n
     * the sendsms-user or sms-service name
     */
    private String service;
    public String getService() { return this.service; }
    public void setService(String service) { this.service = service; }

    /**
     * %c
     * message coding: 0 (default, 7 bits), 1 (8 bits) or 2 (Unicode)
     *
     * coding
     * Optional. Sets the coding scheme bits in DCS field. Accepts values 0 to 2, for 7bit,
     * 8bit or UCS-2. If unset, defaults to 7 bits unless a udh is defined, which sets coding
     * to 8bits.
     */
    private Integer coding;
    public Integer getCoding() { return this.coding; }
    public void setCoding(Integer coding) { this.coding = coding; }

    /**
     * %m
     * message class bits of DCS: 0 (directly to display, flash), 1 (to mobile), 2 (to
     * SIM) or 3 (to SIM toolkit).
     *
     * mclass
     * Optional. Sets the Message Class in DCS field. Accepts values between 0 and 3, for
     * Message Class 0 to 3, A value of 0 sends the message directly to display, 1 sends
     * to mobile, 2 to SIM and 3 to SIM toolkit.
     */
    private Integer mclass;
    public Integer getMclass() { return this.mclass; }
    public void setMclass(Integer mclass) { this.mclass = mclass; }
    
    /**
     * %M
     * mwi (message waiting indicator) bits of DCS: 0 (voice), 1, (fax), 2 (email) or 3
     * (other) for activation and 4, 5, 6, 7 for deactivation respectively.
     *
     * mwi
     * Optional. Sets Message Waiting Indicator bits in DCS field. If given, the message
     * will be encoded as a Message Waiting Indicator. The accepted values are 0,1,2 and
     * 3 for activating the voice, fax, email and other indicator, or 4,5,6,7 for deactivating,
     * respectively. This option excludes the flash option. [a]
     */
    private Integer mwi;
    public Integer getMwi() { return this.mwi; }
    public void setMwi(Integer mwi) { this.mwi = mwi; }

    /**
     * %C
     * message charset: for a "normal" message, it will be "GSM" (coding=0), "binary" (coding=1)
     * or "UTF-16BE" (coding=2). If the message was successfully recoded from Unicode,
     * it will be "WINDOWS-1252"
     *
     * charset
     * Charset of text message. Used to convert to a format suitable for 7 bits or to UCS-2.
     * Defaults to UTF-8 if coding is 7 bits and UTF-16BE if coding is UCS-2.
     */
    private Charset charset;
    public Charset getCharset() { return this.charset; }
    public void setCharset(Charset charset) { this.charset = charset; }

    /**
     * %u
     * udh of incoming message
     *
     * udh
     * Optional User Data Header (UDH) part of the message. Must be URL encoded.
     */
    private byte[] udh;
    public byte[] getUdh() { return this.udh; }
    public void setUdh(byte[] udh) { this.udh = udh; }

    /**
     * %B
     * billing identifier/information of incoming message. The value depends on the SMSC
     * module and the associated billing semantics of the specific SMSC providing the information.
     * For EMI2 the value is the XSer 0c field, for SMPP MO it is the service_type of the
     * deliver_sm PDU, and for SMPP DLR it is the DLR message err component. (Note: This
     * is used for proxying billing information to external applications. There is no semantics
     * associated while processing these.)
     *
     * binfo
     * Optional. Billing identifier/information proxy field used to pass arbitrary billing
     * transaction IDs or information to the specific SMSC modules. For EMI2 this is encapsulated
     * into the XSer 0c field, for SMPP this is encapsulated into the service_type of the
     * submit_sm PDU.
     */
    private String binfo;
    public String getBinfo() { return this.binfo; }
    public void setBinfo(String binfo) { this.binfo = binfo; }

    /**
     * %o
     * account identifier/information of incoming message. The value depends on the SMSC
     * module and has been introduced to allow the forwarding of an operator ID from aggregator
     * SMSCs to the application layer, hence the smsbox HTTP calling instance.
     *
     * account
     * Optional. Account name or number to carry forward for billing purposes. This field
     * is logged as ACT in the log file so it allows you to do some accounting on it if
     * your front end uses the same username for all services but wants to distinguish
     * them in the log. In the case of a HTTP SMSC type the account name is prepended with
     * the service-name (username) and a colon (:) and forwarded to the next instance of
     * Kannel. This allows hierarchical accounting.
     */
    private String account;
    public String getAccount() { return this.account; }
    public void setAccount(String account) { this.account = account; }

    /**
     * %O
     * DCS (Data coding schema) value.
     */
    private String dcs;
    public String getDcs() { return this.dcs; }
    public void setDcs(String dcs) { this.dcs = dcs; }

    /**
     * %f
     * Originating SMSC of incoming message. The value is set if the AT driver is used
     * to receive a SMS on a gsm modem. The value of %f will contain the number of the
     * SMSC sending the SMS to the SIM card. Other SMSC types than AT do not set this field
     * so it will be empty.
     */
    private String originSmsc;
    public String getOriginSmsc() { return this.originSmsc; }
    public void setOriginSmsc(String originSmsc) { this.originSmsc = originSmsc; }
  
}