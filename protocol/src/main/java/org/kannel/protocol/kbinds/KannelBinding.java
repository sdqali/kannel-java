package org.kannel.protocol.kbinds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import org.kannel.protocol.exceptions.NotEnoughPropertiesException;
import org.kannel.protocol.exceptions.PacketParseException;
import org.kannel.protocol.exceptions.WrongPropertieException;
import org.kannel.protocol.packets.AckMessage;
import org.kannel.protocol.packets.BasicKannelProtocolMessage;
import org.kannel.protocol.packets.BasicPacket;
import org.kannel.protocol.packets.KInteger;
import org.kannel.protocol.packets.SMSPacketMessage;
import org.kannel.protocol.tools.DataTypesTools;

/**
 * This class is used to maintain a link with a bearerbox, its purpuse is to
 * allow simple writing, reading and automated heart beating using a specified
 * rate.
 *
 * @author Oscar Medina Duarte
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class KannelBinding
{

    protected static final String prop_heartbeat_rate = "KannelBinding.heartbeating_rate";
    protected static final String prop_initialConnectedState = "KannelBinding.initialConnectedState";
    protected static final String prop_bearerbox_host = "KannelBinding.bearerbox_host";
    protected static final String prop_bearerbox_port = "KannelBinding.bearerbox_port";
    protected static final String prop_boxc_id = "KannelBinding.boxc_id";
    
    protected long heartBeatRate = 60 * 1000;
    protected boolean connectedAtStart = false;
    protected InetAddress bearerBox = null;
    protected int port = 0;
    protected String boxc_id = null;
    
    protected Thread heartBeaterThread = null;
    protected HeartBeating heartBeater = null;
    protected KSocket kannelSocket = null;
    protected Properties conf = null;
    protected InputStream inBoundStream = null;
    protected OutputStream outBoundStream = null;

    protected KannelBinding() {}

    /**
     *  Constructor for the KannelBinding object
     *
     * @param  conf                              Parameters to configure this
     *      class's behaviour.
     * @exception  NotEnoughPropertiesException  Exception thrown when an important
     *      property config is missing
     * @exception  WrongPropertieException       Exception thrown when a value for a
     *      property is inapropriate
     * @exception  IOException                   Exception thrown when connection to
     *      the bearer box fails
     */
    public KannelBinding(Properties conf) throws NotEnoughPropertiesException, WrongPropertieException, IOException {
	init(conf);
	if (this.connectedAtStart) {
	    connect();
	}
    }

    /**
     *  This method starts a conenction with a bearerbox, as specified in the
     *  properties file.
     *
     * @exception  IOException  Exception thrown when a connection error occurs
     */
    public void connect() throws IOException {
	this.kannelSocket = new KSocket(this.bearerBox,
					this.port,
					this.boxc_id);
	
	this.inBoundStream = this.kannelSocket.getInputStream();
	this.outBoundStream = this.kannelSocket.getOutputStream();
	
	startHeartBeating();
    }
    
    /**
     *  Stops a bearerbox connection.
     *
     * @exception  IOException  Exception thrown when an error occurs
     */
    public void disconnect() throws IOException {
	if (this.kannelSocket != null) {
	    synchronized (this.kannelSocket) {
		this.kannelSocket.close();
	    }
	}
	stopHeartBeating();
    }

    /**
     *  Starts the heart beating thread class that sends Heat Beats to the bearer
     *  in fixed slices of time as defined in the properties
     */
    public void startHeartBeating() {
	if (this.heartBeater == null) {
	    this.heartBeater = new HeartBeating(this, this.getHeartBeatRate());
	}
	if (this.heartBeaterThread == null) {
	    this.heartBeaterThread = new Thread(this.heartBeater);
	}
	if (!this.heartBeaterThread.isAlive()) {
	    this.heartBeaterThread.start();
	}
    }

    /**
     *  Stops the heart beating Thread
     */
    public void stopHeartBeating() {
	this.heartBeater.stop();
    }

    /**
     *  Sets the heartBeatRate attribute of the KannelBinding object
     *
     * @param  heartBeatRate  The new heartBeatRate value
     */
    public void setHeartBeatRate(long heartBeatRate) {
	this.heartBeatRate = heartBeatRate;
    }
    
    /**
     *  Gets the heartBeatRate attribute of the KannelBinding object
     *
     * @return    The heartBeatRate value
     */
    public long getHeartBeatRate() {
	return this.heartBeatRate;
    }

    /**
     *  Gets the connected attribute of the KannelBinding object
     *
     *@return    True if the link is up, false otherwise
     */
    public boolean isConnected() {
	return this.kannelSocket.isConnected();
    }
    
    /**
     * Sets up the properties variables from a properties file
     *
     * @param  conf                              Description of the Parameter
     * @exception  NotEnoughPropertiesException  Exception thrown when
     * @exception  WrongPropertieException       Exception thrown when
     */
    public void init(Properties conf) throws NotEnoughPropertiesException, WrongPropertieException {
	// This sets up the configurations
	String swap = conf.getProperty(this.prop_heartbeat_rate);
	try {
	    this.heartBeatRate = Integer.parseInt(swap);
	} catch (NumberFormatException e) {
	    throw new WrongPropertieException(this.prop_heartbeat_rate + " = " + swap);
	}
	
	swap = conf.getProperty(this.prop_initialConnectedState);
	this.connectedAtStart = swap.equalsIgnoreCase("connected");
	
	swap = conf.getProperty(this.prop_bearerbox_host);
	try {
	    this.bearerBox = InetAddress.getByName(swap);
	} catch (UnknownHostException e) {
	    throw new WrongPropertieException(this.prop_bearerbox_host + " = " + swap);
	}
	
	swap = conf.getProperty(this.prop_bearerbox_port);
	try {
	    this.port = Integer.parseInt(swap);
	} catch (NumberFormatException e) {
	    throw new WrongPropertieException(this.prop_bearerbox_port + " = " + swap);
	}
	this.boxc_id = conf.getProperty(this.prop_boxc_id);
    }

    public BasicPacket read() throws Exception
    {
	return readNext();
    }
    
    /**
     * Reads next packet from link
     *
     * @return                           BasicPacket object
     * @exception  IOException           Exception thrown when reading fails or
     *      there is not enough data to fill a field
     * @exception  PacketParseException  Exception thrown when parsing of a Packet
     *      fails
     */
    protected BasicPacket readNext() throws IOException, PacketParseException
    {
	BasicPacket bPckt = null;
	KInteger kLen = null;
	int len = 0;
	KInteger kType = null;
	int type = -1;
	byte[] bInt = new byte[4];
	byte[] swap = null;
	int read = 0;
	
	// leemos 4 bytes para la long KInteger
	//System.out.println("---");
	read = this.inBoundStream.read(bInt);
	
	//System.out.println("+++");
	
	if (read < bInt.length) {
	    throw new IOException("Not enough data to be read to fill a KInteger!");
	} else {
	    kLen = new KInteger(bInt);
	    len = kLen.getIntValue();
	    // System.out.println("len: " + len);
	    if (len < 4) {
		return null;
	    }
	}
	//System.out.println("i");
	read = 0;
	bInt[0] = bInt[1] = bInt[2] = bInt[3] = 0;
	
	// leemos 4 bytes para el tipo KInteger
	read = this.inBoundStream.read(bInt);
	if (read < bInt.length) {
	    throw new IOException("Not enough data to be read to fill a KInteger!");
	} else {
	    
	    kType = new KInteger(bInt);
	    type = kType.getIntValue();
	}
	read = 0;
	bInt = null;
	
	//System.out.println("f");
	// leemos len numerio de bytes a un byte array
	bInt = new byte[len + 4];
	swap = kLen.getBytes();
	for (int i = 0; i < 4; i++) {
	    bInt[read] = swap[i];
	    read++;
	}
	swap = kType.getBytes();
	for (int i = 0; i < 4; i++) {
	    bInt[read] = swap[i];
	    read++;
	}
	swap = null;
	
	read = this.inBoundStream.read(bInt, 8, len - 8 );
	
	this.inBoundStream.read();
	this.inBoundStream.read();
	this.inBoundStream.read();
	this.inBoundStream.read();
	
	System.out.println("in:\n" + DataTypesTools.hexDump(bInt));
	
	// Parseamos segun sea el Type
	// System.out.println("Leyendo... type: " + type);
	switch (type) {
	    
	case 2:
	    {
		bPckt = new SMSPacketMessage(bInt);
		//System.out.println("++ " + bPckt);
		break;
	    }case 3:
	    {
		bPckt = new AckMessage(bInt);
		break;
	    }
	default:
	    {
		
	    }
	}

	return bPckt;
    }

    public void write(BasicKannelProtocolMessage bkpMessage) throws Exception
    {
	writeNext(bkpMessage);
    }

    /**
     * Writes a packet to the link
     *
     * @param  bkpMessage       Packet to be sent
     * @exception  IOException  Exception thrown when writing fails
     */
    protected void writeNext(BasicKannelProtocolMessage bkpMessage) throws IOException {
	//synchronized (this.outBoundStream) {
	System.out.println("out:\n" + DataTypesTools.hexDump(bkpMessage.getMessage()));
	//System.out.print(".");
	this.outBoundStream.write(bkpMessage.getMessage());
	//this.outBoundStream.write((char) 10);
	this.outBoundStream.flush();
	
	/*BufferedOutputStream bos = new BufferedOutputStream(this.outBoundStream);
	  byte[] out = bkpMessage.getMessage();
	  bos.write(out, 0, out.length );
	  bos.flush();*/
	
	//}
    }
    
    public void rawWrite(byte[] pktMessage) throws IOException {
	//synchronized (this.outBoundStream) {
	// System.out.println("out:\n" + DataTypesTools.hexDump(bkpMessage.getMessage()));
	this.outBoundStream.write(pktMessage);
	//this.outBoundStream.write((char) 10);
	this.outBoundStream.flush();
	//}
    }

    /**
     * Testing method
     *
     * @param  args           Description of the Parameter
     * @exception  Exception  Exception thrown when
     */
    public static void main(String args[]) throws Exception {
	Properties props = new Properties();
	props.load(new FileInputStream(
				       new File(
						new String(System.getProperty("user.dir") +
							   System.getProperty("file.separator") +
							   "/properties.txt"))));
	
	KannelBinding kbndg = new KannelBinding(props);
	int i = 0;
	BasicPacket bPckt;
	while (i < 2) {
	    bPckt = kbndg.readNext();
	    if (bPckt != null) {
		//System.out.println(bPckt);
		i++;
		kbndg.writeNext(new AckMessage(0, (SMSPacketMessage) bPckt));
	    }
	}
    }

}

