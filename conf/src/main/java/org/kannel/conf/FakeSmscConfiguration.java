package org.kannel.conf;

import java.net.URL;

/**
 * fake smsc configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class FakeSmscConfiguration
    extends SmscConfiguration
{

    public FakeSmscConfiguration()
    {
	super();
    }

    private static final String[] FAKE_SMSC_PROP_ORDER = { "host","port","connect-allow-ip" };

    public String[] getPropertyOrder() {
	return arrayCat(super.getPropertyOrder(), FAKE_SMSC_PROP_ORDER);
    }

    private static final String[] FAKE_SMSC_MANDATORY_PROPS = { "host","port" };

    public String[] getMandatoryProps() {
	return arrayCat(super.getPropertyOrder(), FAKE_SMSC_MANDATORY_PROPS);
    }

    /**
     * host
     * Machine that runs the SMSC. As IP (100.100.100.100) or hostname (their.machine.here) 
     */
    private String host;
    public String getHost() { return this.host; }
    public void setHost(String host) { this.host = host; }
    
    /**
     * port
     * Port number in smsc host machine 
     */
    private Integer port;
    public Integer getPort() { return this.port; }
    public void setPort(Integer port) { this.port = port; }
    
    /**
     * connect-allow-ip
     * If set, only connections from these IP addresses are accepted. 
     */
    private String[] connectAllowIp;
    public String[] getConnectAllowIp() { return this.connectAllowIp; }
    public void setConnectAllowIp(String[] connectAllowIp) { this.connectAllowIp = connectAllowIp; }
    
}
