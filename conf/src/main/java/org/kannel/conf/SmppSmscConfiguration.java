package org.kannel.conf;

import java.net.URL;

/**
 * smpp smsc configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class SmppSmscConfiguration
    extends SmscConfiguration
{

    public SmppSmscConfiguration()
    {
	super();
    }

    private static final String[] SMPP_SMSC_PROP_ORDER = { "host","port","use-ssl","transceiver-mode","receive-port","smsc-username","smsc-password","system-type","service-type","interface-version","address-range","my-number","enquire-link-interval","max-pending-submits","reconnect-delay","source-addr-ton","source-addr-npi","source-addr-autodetect","dest-addr-ton","dest-addr-npi","bind-addr-ton","bind-addr-npi","msg-id-type","alt-charset","alt-addr-charset","connection-timeout","wait-ack","wait-ack-expire","validityperiod" };

    public String[] getPropertyOrder() {
	return arrayCat(super.getPropertyOrder(), SMPP_SMSC_PROP_ORDER);
    }

    private static final String[] SMPP_SMSC_MANDATORY_PROPS = { "host","port","smsc-username","smsc-password","system-type","address-range" };

    public String[] getMandatoryProps() {
	return arrayCat(super.getPropertyOrder(), SMPP_SMSC_MANDATORY_PROPS);
    }

    /**
     * host
     *  Machine that runs SMSC. As IP (100.100.100.100) or hostname (their.machine.here) 
     */
    private String host;
    public String getHost() { return this.host; }
    public void setHost(String host) { this.host = host; }

    /**
     * port
     * The port number for the TRANSMITTER connection to the SMSC. May be the same as receive-port. 
     * Use value 0 to disable this I/O thread. 
     */
    private Integer port;
    public Integer getPort() { return this.port; }
    public void setPort(Integer port) { this.port = port; }

    /**
     * use-ssl
     * Defines whether we should try to bind with SSL enabled connection. 
     */
    private Boolean useSsl;
    public Boolean getUseSsl() { return this.useSsl; }
    public void setUseSsl(Boolean useSsl) { this.useSsl = useSsl; }

    /**
     * transceiver-mode
     * Attempt to use a TRANSCEIVER mode connection to the SM-SC. It uses the standard 
     * transmit 'port', there is no need to set 'receive-port'. This is a SMPP 3.4 only 
     * feature and will not work on an earlier SM-SC. This will try a bind_transceiver 
     * only and will not attempt to fall back to doing transmit and receive on the same 
     * connection. 
     */
    private Boolean transceiverMode;
    public Boolean getTransceiverMode() { return this.transceiverMode; }
    public void setTransceiverMode(Boolean transceiverMode) { this.transceiverMode = transceiverMode; }

    /**
     * receive-port
     * The port number for the RECEIVER connection to the SMSC. May be the same as port. 
     * Use value 0 to disable this I/O thread. 
     */
    private Integer receivePort;
    public Integer getReceivePort() { return this.receivePort; }
    public void setReceivePort(Integer receivePort) { this.receivePort = receivePort; }

    /**
     * smsc-username
     * The 'username' of the Messaging Entity connecting to the SM-SC. If the SM-SC operator 
     * reports that the "TELEPATH SYSTEM MANAGER TERMINAL" view "Control.Apps.View" value 
     * "Name:" is "SMPP_ZAPVMA_T" for the transmitter and "SMPP_ZAPVMA_R" for the receiver 
     * the smsc-username value is accordingly "SMPP_ZAP". Note that this used to be called 
     * system-id (the name in SMPP documentation) and has been changed to smsc-username 
     * to make all Kannel SMS center drivers use the same name. 
     */
    private String smscUsername;
    public String getSmscUsername() { return this.smscUsername; }
    public void setSmscUsername(String smscUsername) { this.smscUsername = smscUsername; }

    /**
     * smsc-password
     * The password matching the "smsc-username" your teleoperator provided you with. 
     */
    private String smscPassword;
    public String getSmscPassword() { return this.smscPassword; }
    public void setSmscPassword(String smscPassword) { this.smscPassword = smscPassword; }

    /**
     * system-type
     * Usually you can get away with "VMA" which stands for Voice Mail Activation. 
     */
    private String systemType;
    public String getSystemType() { return this.systemType; }
    public void setSystemType(String systemType) { this.systemType = systemType; }

    /**
     * service-type
     * Optional; if specified, sets the service type for the SMSC. If unset, the default 
     * service type is used. This may be used to influence SMS routing (for example). The 
     * SMSC operator may also refer to this as the "profile ID". The maximum length of 
     * the service type is 6, according to the SMPP specification v3.4. 
     */
    private String serviceType;
    public String getServiceType() { return this.serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    /**
     * interface-version
     * Change the "interface version" parameter sent from Kannel to a value other then 
     * 0x34 (for SMPP v3.4). the value entered here should be the hexadecimal representation 
     * of the interface version parameter. for example, the default (if not set) is "34" 
     * which stands for 0x34. for SMPP v3.3 set to "33". 
     */
    private Integer interfaceVersion;
    public Integer getInterfaceVersion() { return this.interfaceVersion; }
    public void setInterfaceVersion(Integer interfaceVersion) { this.interfaceVersion = interfaceVersion; }

    /**
     * address-range
     * According to the SMPP 3.4 spec this is supposed to affect which MS's can send messages 
     * to this account. Doesn't seem to work, though. 
     */
    private String addressRange;
    public String getAddressRange() { return this.addressRange; }
    public void setAddressRange(String addressRange) { this.addressRange = addressRange; }

    /**
     * my-number
     * Optional smsc short number. Should be set if smsc sends a different one. 
     */
    private Integer myNumber;
    public Integer getMyNumber() { return this.myNumber; }
    public void setMyNumber(Integer myNumber) { this.myNumber = myNumber; }

    /**
     * enquire-link-interval
     * Optional the time lapse allowed between operations after which an SMPP entity should 
     * interrogate whether it's peer still has an active session. The default is 30 seconds. 
     */
    private Integer enquireLinkInterval;
    public Integer getEnquireLinkInterval() { return this.enquireLinkInterval; }
    public void setEnquireLinkInterval(Integer enquireLinkInterval) { this.enquireLinkInterval = enquireLinkInterval; }

    /**
     * max-pending-submits
     * Optional the maximum number of outstanding (i.e. acknowledged) SMPP operations between 
     * an ESME and SMSC. This number is not specified explicitly in the SMPP Protocol Specification 
     * and will be governed by the SMPP implementation on the SMSC. As a guideline it is 
     * recommended that no more than 10 (default) SMPP messages are outstanding at any 
     * time. 
     */
    private Integer maxPendingSubmits;
    public Integer getMaxPendingSubmits() { return this.maxPendingSubmits; }
    public void setMaxPendingSubmits(Integer maxPendingSubmits) { this.maxPendingSubmits = maxPendingSubmits; }

    /**
     * reconnect-delay
     * Optional the time between attempts to connect an ESME to an SMSC having failed to 
     * connect initiating or during an SMPP session. The default is 10 seconds. 
     */
    private Integer reconnectDelay;
    public Integer getReconnectDelay() { return this.reconnectDelay; }
    public void setReconnectDelay(Integer reconnectDelay) { this.reconnectDelay = reconnectDelay; }

    /**
     * source-addr-ton
     * Optional, source address TON setting for the link. (Defaults to 0). 
     */
    private Integer sourceAddrTon;
    public Integer getSourceAddrTon() { return this.sourceAddrTon; }
    public void setSourceAddrTon(Integer sourceAddrTon) { this.sourceAddrTon = sourceAddrTon; }

    /**
     * source-addr-npi
     * Optional, source address NPI setting for the link. (Defaults to 1). 
     */
    private Integer sourceAddrNpi;
    public Integer getSourceAddrNpi() { return this.sourceAddrNpi; }
    public void setSourceAddrNpi(Integer sourceAddrNpi) { this.sourceAddrNpi = sourceAddrNpi; }

    /**
     * source-addr-autodetect
     * Optional, if defined tries to scan the source address and set TON and NPI settings 
     * accordingly. If you don't want to autodetect the source address, turn this off, 
     * by setting it to no. (Defaults to yes). 
     */
    private Boolean sourceAddrAutodetect;
    public Boolean getSourceAddrAutodetect() { return this.sourceAddrAutodetect; }
    public void setSourceAddrAutodetect(Boolean sourceAddrAutodetect) { this.sourceAddrAutodetect = sourceAddrAutodetect; }

    /**
     * dest-addr-ton
     * Optional, destination address TON setting for the link. (Defaults to 0). 
     */
    private Integer destAddrTon;
    public Integer getDestAddrTon() { return this.destAddrTon; }
    public void setDestAddrTon(Integer destAddrTon) { this.destAddrTon = destAddrTon; }

    /**
     * dest-addr-npi
     * Optional, destination address NPI setting for the link. (Defaults to 1). 
     */
    private Integer destAddrNpi;
    public Integer getDestAddrNpi() { return this.destAddrNpi; }
    public void setDestAddrNpi(Integer destAddrNpi) { this.destAddrNpi = destAddrNpi; }

    /**
     * bind-addr-ton
     * Optional, bind address TON setting for the link. (Defaults to 0). 
     */
    private Integer bindAddrTon;
    public Integer getBindAddrTon() { return this.bindAddrTon; }
    public void setBindAddrTon(Integer bindAddrTon) { this.bindAddrTon = bindAddrTon; }

    /**
     * bind-addr-npi
     * Optional, bind address NPI setting for the link. (Defaults to 0). 
     */
    private Integer bindAddrNpi;
    public Integer getBindAddrNpi() { return this.bindAddrNpi; }
    public void setBindAddrNpi(Integer bindAddrNpi) { this.bindAddrNpi = bindAddrNpi; }

    /**
     * msg-id-type
     * Optional, specifies which number base the SMSC is using for the message ID numbers 
     * in the corresponding submit_sm_resp and deliver_sm PDUs. This is required to make 
     * delivery reports (DLR) work on SMSC that behave differently. The number is a combined 
     * set of bit 1 and bit 2 that indicate as follows: bit 1: type for submit_sm_resp, 
     * bit 2: type for deliver_sm. If the bit is set then the value is in hex otherwise 
     * in decimal number base. Which means the following combinations are possible and 
     * valid: 0x00 deliver_sm decimal, submit_sm_resp decimal; 0x01 deliver_sm decimal, 
     * submit_sm_resp hex; 0x02 deliver_sm hex, submit_sm_resp decimal; 0x03 deliver_sm 
     * hex, submit_sm_resp hex. In accordance to the SMPP v3.4 specs the default will be 
     * a C string literal if no of the above values is explicitly indicated using the config 
     * directive. 
     */
    private Integer msgIdType;
    public Integer getMsgIdType() { return this.msgIdType; }
    public void setMsgIdType(Integer msgIdType) { this.msgIdType = msgIdType; }

    /**
     * alt-charset
     * Defines which character encoding is used for this specific smsc link. Uses iconv() 
     * routines to convert from and to that specific character set encoding. See your local 
     * iconv_open(3) manual page for the supported character encodings and the type strings 
     * that should be presented for this directive. 
     */
    // private String altCharset;
    // public String getAltCharset() { return this.altCharset; }
    // public void setAltCharset(String altCharset) { this.altCharset = altCharset; }

    /**
     * alt-addr-charset
     * Defines which character encoding is used for alphanumeric addresses. When set to 
     * GSM, addresses are converted into the GSM 03.38 charset (Since @ is translated into 
     * 0x00 which will break the SMPP PDU, @ replaced with ?). If set to another value, 
     * iconv() is used. (Defaults to windows-1252) 
     */
    private String altAddrCharset;
    public String getAltAddrCharset() { return this.altAddrCharset; }
    public void setAltAddrCharset(String altAddrCharset) { this.altAddrCharset = altAddrCharset; }

    /**
     * connection-timeout
     * This timer specifies the maximum time lapse allowed between transactions , after 
     * which period of inactivity, an SMPP driver may assume that the session is no longer 
     * active and does reconnect. Defaults to 300 seconds, to disable set it to 0. 
     */
    private Integer connectionTimeout;
    public Integer getConnectionTimeout() { return this.connectionTimeout; }
    public void setConnectionTimeout(Integer connectionTimeout) { this.connectionTimeout = connectionTimeout; }

    /**
     * wait-ack
     * A message is resent if the acknowledge from SMSC takes more than this time. Defaults 
     * to 60 seconds. 
     */
    private Integer waitAck;
    public Integer getWaitAck() { return this.waitAck; }
    public void setWaitAck(Integer waitAck) { this.waitAck = waitAck; }

    /**
     * wait-ack-expire
     * Defines what kind of action should be taken if the ack of a message expires. The 
     * options for this value are: 0x00 - disconnect/reconnect, (default) 0x01 - as is 
     * now, re-queue, but this could potentially result in the msg arriving twice 0x02 
     * - just carry on waiting (given that the wait-ack should never expire this is the 
     * must accurate) 
     */
    private Integer waitAckExpire;
    public Integer getWaitAckExpire() { return this.waitAckExpire; }
    public void setWaitAckExpire(Integer waitAckExpire) { this.waitAckExpire = waitAckExpire; }

    /**
     * validityperiod
     * How long the message will be valid, i.e., how long the SMSC will try try to send 
     * the message to the recipient. Defined in minutes. 
     */
    private Integer validityperiod;
    public Integer getValidityperiod() { return this.validityperiod; }
    public void setValidityperiod(Integer validityperiod) { this.validityperiod = validityperiod; }

}
