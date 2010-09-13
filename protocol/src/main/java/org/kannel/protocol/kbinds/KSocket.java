package org.kannel.protocol.kbinds;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;

import java.net.Socket;
import java.net.UnknownHostException;
import org.kannel.protocol.packets.AdminKMessage;
import org.kannel.protocol.packets.SMSPacketMessage;
import org.kannel.protocol.tools.DataTypesTools;

/**
 *  Socket connected to a Kannel bearerbox (Typically)
 *
 *@author     Oscar Medina Duarte
 *@created    April 4, 2005
 */
public class KSocket extends Socket {

	private String boxc_id = null;
	private AdminKMessage admMessage = null;

	/**
	 *  Wrapper constructor for the <i>Socket</i> super class
	 */
	public KSocket() {
		super();
	}


	/**
	 *  Wrapper constructor for the <i>Socket</i> super class
	 *
	 *@param  address          Description of the Parameter
	 *@param  port             Description of the Parameter
	 *@exception  IOException  Exception thrown when
	 */
	public KSocket(InetAddress address, int port) throws IOException {
		super(address, port);

	}


	/**
	 *  Wrapper constructor for the <i>Socket</i> super class
	 *
	 *@param  address          Description of the Parameter
	 *@param  port             Description of the Parameter
	 *@param  localAddr        Description of the Parameter
	 *@param  localPort        Description of the Parameter
	 *@exception  IOException  Exception thrown when
	 */
	public KSocket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
		super(address, port, localAddr, localPort);

	}


	/**
	 *  Wrapper constructor for the <i>Socket</i> super class
	 *
	 *@param  host                      Description of the Parameter
	 *@param  port                      Description of the Parameter
	 *@exception  UnknownHostException  Exception thrown when
	 *@exception  IOException           Exception thrown when
	 */
	public KSocket(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
	}


	/**
	 *  Wrapper constructor for the <i>Socket</i> super class
	 *
	 *@param  host             Description of the Parameter
	 *@param  port             Description of the Parameter
	 *@param  localAddr        Description of the Parameter
	 *@param  localPort        Description of the Parameter
	 *@exception  IOException  Exception thrown when
	 */
	public KSocket(String host, int port, InetAddress localAddr, int localPort) throws IOException {
		super(host, port, localAddr, localPort);
	}


	/**
	 *  Constructor for the KSocket object
	 *  <p>This constructor sets the boxc_id attribute to be used by <i>void kconnect()</i></p>
	 *
	 *@param  boxc_id  Description of the Parameter
	 */
	public KSocket(String boxc_id) {
		super();
		this.boxc_id = boxc_id;
	}


	/**
	 *  Constructor for the KSocket object
	 *  <p>This constructor sets the boxc_id attribute to be used by <i>void kconnect()</i>
	 *  and sends AdminKMessage to Kannel box to initiate a session.</p>
	 *
	 *@param  address          Description of the Parameter
	 *@param  port             Description of the Parameter
	 *@param  boxc_id          Description of the Parameter
	 *@exception  IOException  Exception thrown when
	 */
	public KSocket(InetAddress address, int port, String boxc_id) throws IOException {
		super(address, port);
		this.boxc_id = boxc_id;
		this.kconnect(this.boxc_id);
	}


	/**
	 *  Constructor for the KSocket object
	 *  <p>This constructor sets the boxc_id attribute to be used by <i>void kconnect()</i>
	 *  and sends AdminKMessage to Kannel box to initiate a session.</p>
	 *
	 *@param  address          Description of the Parameter
	 *@param  port             Description of the Parameter
	 *@param  localAddr        Description of the Parameter
	 *@param  localPort        Description of the Parameter
	 *@param  boxc_id          Description of the Parameter
	 *@exception  IOException  Exception thrown when
	 */
	public KSocket(InetAddress address, int port, InetAddress localAddr, int localPort, String boxc_id) throws IOException {
		super(address, port, localAddr, localPort);
		this.boxc_id = boxc_id;
		this.kconnect(this.boxc_id);
	}


	/**
	 *  Constructor for the KSocket object
	 *  <p>This constructor sets the boxc_id attribute to be used by <i>void kconnect()</i>
	 *  and sends AdminKMessage to Kannel box to initiate a session.</p>
	 *
	 *@param  host                      Description of the Parameter
	 *@param  port                      Description of the Parameter
	 *@param  boxc_id                   Description of the Parameter
	 *@exception  UnknownHostException  Exception thrown when
	 *@exception  IOException           Exception thrown when
	 */
	public KSocket(String host, int port, String boxc_id) throws UnknownHostException, IOException {
		super(host, port);
		this.boxc_id = boxc_id;
		this.kconnect(this.boxc_id);
	}


	/**
	 *  Constructor for the KSocket object
	 *  <p>This constructor sets the boxc_id attribute to be used by <i>void kconnect()</i>
	 *  and sends AdminKMessage to Kannel box to initiate a session.</p>
	 *
	 *@param  host             Description of the Parameter
	 *@param  port             Description of the Parameter
	 *@param  localAddr        Description of the Parameter
	 *@param  localPort        Description of the Parameter
	 *@param  boxc_id          Description of the Parameter
	 *@exception  IOException  Exception thrown when
	 */
	public KSocket(String host, int port, InetAddress localAddr, int localPort, String boxc_id) throws IOException {
		super(host, port, localAddr, localPort);
		this.boxc_id = boxc_id;
		this.kconnect(this.boxc_id);
	}


	/**
	 *  Sets the boxc_id attribute of the KSocket object
	 *
	 *@param  boxc_id  The new boxc_id value
	 */
	public void setBoxc_id(String boxc_id) {
		this.boxc_id = boxc_id;
	}


	/**
	 *  Gets the boxc_id attribute of the KSocket object
	 *
	 *@return    The boxc_id value
	 */
	public String getBoxc_id() {
		return boxc_id;
	}


	/**
	 *  Initiates a session with a bearer box.
	 *
	 *@param  boxc_id          Description of the Parameter
	 *@exception  IOException  Exception thrown when
	 */
	public void kconnect(String boxc_id) throws IOException {
		if (this.admMessage == null){
			this.admMessage = new AdminKMessage(AdminKMessage.CMD_IDENTIFY, boxc_id);
		}
		OutputStream os = super.getOutputStream();
		byte[] bAdmMsg = admMessage.getMessage();
		System.out.println("out:\n" + DataTypesTools.hexDump(bAdmMsg));
		os.write(bAdmMsg);
		os.flush();
	}


	/**
	 *  Initiates a session with a bearer box.
	 *
	 *@exception  IOException  Exception thrown when
	 */
	public void kconnect() throws IOException {
		this.kconnect(this.boxc_id);
	}


	/**
	 *  Testing class
	 *
	 *@param  args           Description of the Parameter
	 *@exception  Exception  Exception thrown when
	 */
	public static void main(String args[]) throws Exception {
		KSocket ksckt = new KSocket("localhost", 6667, "");
		OutputStream os = ksckt.getOutputStream();
		SMSPacketMessage smsMsg = new SMSPacketMessage("de_mi", "pa_ti", "CosasRaras", "Y un mensaje de texto !");

		os.write(smsMsg.getMessage());
		os.flush();

		ksckt.close();
	}
}

