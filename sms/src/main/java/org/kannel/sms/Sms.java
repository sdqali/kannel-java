package org.kannel.sms;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * An SMS message. This combines fields used in both sending and receiving SMS messages.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class Sms
    extends Msg
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
     * compress
     * Optional. Sets the Compression bit in DCS Field.
     */
    private Integer compress;
    public Integer getCompress() { return this.compress; }
    public void setCompress(Integer compress) { this.compress = compress; }
    
    /**
     * validity
     * Optional. If given, Kannel will inform SMS Center that it should only try to send
     * the message for this many minutes. If the destination mobile is off other situation
     * that it cannot receive the sms, the smsc discards the message. Note: you must have
     * your Kannel box time synchronized with the SMS Center.
     */
    private Integer validity;
    public Integer getValidity() { return this.validity; }
    public void setValidity(Integer validity) { this.validity = validity; }

    /**
     * deferred
     * Optional. If given, the SMS center will postpone the message to be delivered at
     * now plus this many minutes. Note: you must have your Kannel box time synchronized
     * with the SMS Center.
     */
    private Integer deferred;
    public Integer getDeferred() { return this.deferred; }
    public void setDeferred(Integer deferred) { this.deferred = deferred; }

    /**
     * dlr-mask
     * Optional. Request for delivery reports with the state of the sent message. The value
     * is a bit mask composed of: 1: Delivered to phone, 2: Non-Delivered to Phone, 4: Queued
     * on SMSC, 8: Delivered to SMSC, 16: Non-Delivered to SMSC. Must set dlr-url on sendsms-user
     * group or use the dlr-url CGI variable.
     */
    private Integer dlrMask;
    public Integer getDlrMask() { return this.dlrMask; }
    public void setDlrMask(Integer dlrMask) { this.dlrMask = dlrMask; }

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
    private Byte pid;
    public Byte getPid() { return this.pid; }
    public void setPid(Byte pid) { this.pid = pid; }

    /**
     * alt-dcs
     * Optional. If unset, Kannel uses the alt-dcs defined on smsc configuration, or 0X
     * per default. If equals to 1, uses FX. If equals to 0, force 0X.
     */
    private Integer altDcs;
    public Integer getAltDcs() { return this.altDcs; }
    public void setAltDcs(Integer altDcs) { this.altDcs = altDcs; }

    /**
     * rpi
     * Optional. Sets the Return Path Indicator (RPI) value. (See ETSI Documentation).
     */
    private Integer rpi;
    public Integer getRpi() { return this.rpi; }
    public void setRpi(Integer rpi) { this.rpi = rpi; }

    /**
     * priority
     * Optional. Sets the Priority value (range 0-3 is allowed). 
     */
    private Integer priority;
    public Integer getPriority() { return this.priority; }
    public void setPriority(Integer priority) { this.priority = priority; }    

    public static Sms buildFromTemplate(UrlTemplate u, Map m)
    {
	return u.parseSms(m);
    }

    public static Sms buildFromHeaders(Map m)
    {
	Sms sms = new Sms();
	Set<Map.Entry> entries = m.entrySet();
	for (Map.Entry entry:entries) {
	    String k = (String)entry.getKey();
	    String v = (String)entry.getValue();
	    if (k.equals("X-Kannel-Username")) sms.setUsername(v);
	    if (k.equals("X-Kannel-Password")) sms.setPassword(v);
	    if (k.equals("X-Kannel-From")) sms.setFrom(v);
	    if (k.equals("X-Kannel-To")) sms.setTo(v);
	    if (k.equals("X-Kannel-UDH")) sms.setUdh(v.getBytes());
	    if (k.equals("X-Kannel-SMSC")) sms.setSmsc(v);
	    if (k.equals("X-Kannel-MClass")) sms.setMclass(Integer.parseInt(v));
	    if (k.equals("X-Kannel-MWI")) sms.setMwi(Integer.parseInt(v));
	    if (k.equals("X-Kannel-Compress")) sms.setCompress(Integer.parseInt(v));
	    if (k.equals("X-Kannel-Coding")) sms.setCoding(Integer.parseInt(v));
	    if (k.equals("X-Kannel-Validity")) sms.setValidity(Integer.parseInt(v));
	    if (k.equals("X-Kannel-Deferred")) sms.setDeferred(Integer.parseInt(v));
	    if (k.equals("X-Kannel-DLR-Mask")) sms.setDlrMask(Integer.parseInt(v));
	    if (k.equals("X-Kannel-DLR-Url")) {
		try { 
		    sms.setDlrUrl(new URL(v));
		} catch (Exception e) {}
	    }
	    if (k.equals("X-Kannel-PID")) sms.setPid(Byte.parseByte(v));
	    if (k.equals("X-Kannel-Alt-DCS")) sms.setAltDcs(Integer.parseInt(v));
	    if (k.equals("X-Kannel-RPI")) sms.setRpi(Integer.parseInt(v));
	    if (k.equals("X-Kannel-Account")) sms.setAccount(v);
	    if (k.equals("X-Kannel-BInfo")) sms.setBinfo(v);
	    if (k.equals("X-Kannel-Priority")) sms.setPriority(Integer.parseInt(v));
	}
	return sms;
    }

}