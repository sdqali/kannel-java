package org.kannel.conf;

import java.net.URL;

/**
 * loopback smsc configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class LoopbackSmscConfiguration
    extends SmscConfiguration
{

    public LoopbackSmscConfiguration()
    {
	super();
    }

    private static final String[] LOOPBACK_SMSC_PROP_ORDER = { "reroute-smsc-id" };

    public String[] getPropertyOrder() {
	return arrayCat(super.getPropertyOrder(), LOOPBACK_SMSC_PROP_ORDER);
    }

    private static final String[] LOOPBACK_SMSC_MANDATORY_PROPS = { "reroute-smsc-id" };
    
    public String[] getMandatoryProps() {
	return arrayCat(super.getPropertyOrder(), LOOPBACK_SMSC_MANDATORY_PROPS);
    }

    /**
     * reroute-smsc-id
     * Tag the rerouted MO with the given value for smsc-id instead of the canonical
     * smsc-id value of the smsc group itself. 
     */
    private String rerouteSmscId;
    public String getRerouteSmscId() { return this.rerouteSmscId; }
    public void setRerouteSmscId(String rerouteSmscId) { this.rerouteSmscId = rerouteSmscId; }

}