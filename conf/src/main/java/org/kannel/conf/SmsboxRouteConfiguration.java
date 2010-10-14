package org.kannel.conf;

import java.net.URL;

/**
 * smsbox-route configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SmsboxRouteConfiguration
    extends Configuration
{

    public SmsboxRouteConfiguration()
    {
	super("smsbox-route");
    }
    
    private static final String[] PROP_ORDER = { "smsbox-id","smsc-id","shortcode" };

    public String[] getPropertyOrder() {
	return PROP_ORDER;
    }

    private static final String[] MANDATORY_PROPS = { "smsbox-id" };

    public String[] getMandatoryProps() {
	return MANDATORY_PROPS;
    }

    /**
     * smsbox-id
     * Defines for which smsbox instance the routing rules do apply. 
     */
    private String smsboxId;
    public String getSmsboxId() { return this.smsboxId; }
    public void setSmsboxId(String smsboxId) { this.smsboxId = smsboxId; }

    /**
     * smsc-id
     * If set, specifies from which smsc-ids all inbound messages should be routed to this 
     * smsbox instance. List contains smsc-ids separated by semicolon (";"). This rule 
     * may be used to pull any smsc specific message stream to an smsbox instance. If used 
     * in combination with config directive shortcode, then this is another matching criteria 
     * for the routing decission. 
     */
    private String[] smscId;
    public String[] getSmscId() { return this.smscId; }
    public void setSmscId(String[] smscId) { this.smscId = smscId; }

    /**
     * shortcode
     * If set, specifies which receiver numbers for inbound messages should be routed to 
     * this smsbox instance. List contains numbers separated by semicolon (";"). This rule 
     * may be used to pull receiver number specific message streams to an smsbox instance. 
     * If used in combination with config directive smsc-id, then only messages originating 
     * from the connections in the smsc-id are matched against the shortcode list. 
     */
    private String[] shortcode;
    public String[] getShortcode() { return this.shortcode; }
    public void setShortcode(String[] shortcode) { this.shortcode = shortcode; }

}
