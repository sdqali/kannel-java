package org.kannel.sms;

import java.net.URL;
import java.nio.charset.Charset;

public class Sms
{

    /**
     * username (or user)
     * Username or account name. Must be username of the one 'sendsms-user' group in the
     * Kannel configuration, or results in 'Authorization failed' reply.
     */
    private String username;
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    
    /**
     * password (or pass)
     * Password associated with given username. Must match corresponding field in the 'sendsms-user'
     * group of the Kannel configuration, or 'Authorization failed' is returned.
     */
    private String password;
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * from
     * Phone number of the sender. This field is usually overridden by the SMS Center, or
     * it can be overridden by faked-sender variable in the sendsms-user group. If this
     * variable is not set, smsbox global-sender is used.
     */
    private String from;
    public String getFrom() { return this.from; }
    public void setFrom(String from) { this.from = from; }

    /**
     * to
     * Phone number of the receiver. To send to multiple receivers, separate each entry
     * with space (' ', '+' url-encoded) - but note that this can be deactivated via sendsms-chars
     * in the 'smsbox' group.
     */
    private String to;
    public String getTo() { return this.to; }
    public void setTo(String to) { this.to = to; }
    
    /**
     * text
     * Contents of the message, URL encoded as necessary. The content can be more than
     * 160 characters, but then sendsms-user group must have max-messages set more than 1.
     */
    private String text;
    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }
    
    /**
     * charset
     * Charset of text message. Used to convert to a format suitable for 7 bits or to UCS-2.
     * Defaults to UTF-8 if coding is 7 bits and UTF-16BE if coding is UCS-2.
     */
    private Charset charset;
    public Charset getCharset() { return this.charset; }
    public void setCharset(Charset charset) { this.charset = charset; }
    
    /**
     * udh
     * Optional User Data Header (UDH) part of the message. Must be URL encoded.
     */
    private byte[] udh;
    public byte[] getUdh() { return this.udh; }
    public void setUdh(byte[] udh) { this.udh = udh; }

    /**
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
     * mclass
     * Optional. Sets the Message Class in DCS field. Accepts values between 0 and 3, for
     * Message Class 0 to 3, A value of 0 sends the message directly to display, 1 sends
     * to mobile, 2 to SIM and 3 to SIM toolkit.
     */
    private int mclass;
    public int getMclass() { return this.mclass; }
    public void setMclass(int mclass) { this.mclass = mclass; }
    
    /**
     * mwi
     * Optional. Sets Message Waiting Indicator bits in DCS field. If given, the message
     * will be encoded as a Message Waiting Indicator. The accepted values are 0,1,2 and
     * 3 for activating the voice, fax, email and other indicator, or 4,5,6,7 for deactivating,
     * respectively. This option excludes the flash option. [a]
     */
    private int mwi;
    public int getMwi() { return this.mwi; }
    public void setMwi(int mwi) { this.mwi = mwi; }

    /**
     * compress
     * Optional. Sets the Compression bit in DCS Field.
     */
    private int compress;
    public int getCompress() { return this.compress; }
    public void setCompress(int compress) { this.compress = compress; }
    
    /**
     * coding
     * Optional. Sets the coding scheme bits in DCS field. Accepts values 0 to 2, for 7bit,
     * 8bit or UCS-2. If unset, defaults to 7 bits unless a udh is defined, which sets coding
     * to 8bits.
     */
    private int coding;
    public int getCoding() { return this.coding; }
    public void setCoding(int coding) { this.coding = coding; }

    /**
     * validity
     * Optional. If given, Kannel will inform SMS Center that it should only try to send
     * the message for this many minutes. If the destination mobile is off other situation
     * that it cannot receive the sms, the smsc discards the message. Note: you must have
     * your Kannel box time synchronized with the SMS Center.
     */
    private int validity;
    public int getValidity() { return this.validity; }
    public void setValidity(int validity) { this.validity = validity; }

    /**
     * deferred
     * Optional. If given, the SMS center will postpone the message to be delivered at
     * now plus this many minutes. Note: you must have your Kannel box time synchronized
     * with the SMS Center.
     */
    private int deferred;
    public int getDeferred() { return this.deferred; }
    public void setDeferred(int deferred) { this.deferred = deferred; }

    /**
     * dlr-mask
     * Optional. Request for delivery reports with the state of the sent message. The value
     * is a bit mask composed of: 1: Delivered to phone, 2: Non-Delivered to Phone, 4: Queued
     * on SMSC, 8: Delivered to SMSC, 16: Non-Delivered to SMSC. Must set dlr-url on sendsms-user
     * group or use the dlr-url CGI variable.
     */
    private int dlrMask;
    public int getDlrMask() { return this.dlrMask; }
    public void setDlrMask(int dlrMask) { this.dlrMask = dlrMask; }

    /**
     * dlr-url
     * Optional. If dlr-mask is given, this is the url to be fetched. (Must be url-encoded)
     */
    private URL dlrUrl;
    public URL getDlrUrl() { return this.dlrUrl; }
    public void setDlrUrl(URL dlrUrl) { this.dlrUrl = dlrUrl; }

    /**
     * pid
     * Optional. Sets the PID value. (See ETSI Documentation). Ex: SIM Toolkit messages
     * would use something like &pid=127&coding=1&alt-dcs=1&mclass=3
     */
    private byte pid;
    public byte getPid() { return this.pid; }
    public void setPid(byte pid) { this.pid = pid; }

    /**
     * alt-dcs
     * Optional. If unset, Kannel uses the alt-dcs defined on smsc configuration, or 0X
     * per default. If equals to 1, uses FX. If equals to 0, force 0X.
     */
    private int altDcs;
    public int getAltDcs() { return this.altDcs; }
    public void setAltDcs(int altDcs) { this.altDcs = altDcs; }

    /**
     * rpi
     * Optional. Sets the Return Path Indicator (RPI) value. (See ETSI Documentation).
     */
    private int rpi;
    public int getRpi() { return this.rpi; }
    public void setRpi(int rpi) { this.rpi = rpi; }

    /**
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
     * priority
     * Optional. Sets the Priority value (range 0-3 is allowed). 
    */
    private int priority;
    public int getPriority() { return this.priority; }
    public void setPriority(int priority) { this.priority = priority; }    

}