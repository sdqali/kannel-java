package org.kannel.protocol.packets;
import org.kannel.protocol.exceptions.*;

import org.kannel.protocol.tools.*;

/**
 *  Kannel's protocol heart beat message.
 *
 *@author     Oscar Medina Duarte
 *@created    March 31, 2005
 */
public class HeartBeatKMessage extends BasicPacket implements BasicKannelProtocolMessage {

	private KInteger load = null;
	private byte[] message = null;


	/**
	 *  Constructor for the HeartBeatKMessage object
	 */
	public HeartBeatKMessage() {
		super.length = new KInteger(0x08);
		super.type = new KInteger(super.HEARTBEAT_PKT);
		this.load = new KInteger(0);
	}


	/**
	 *  Constructor for the HeartBeatKMessage object
	 *
	 *@param  load  Load field value.
	 */
	public HeartBeatKMessage(int load) {
		super.length = new KInteger(0x08);
		super.type = new KInteger(super.HEARTBEAT_PKT);
		this.load = new KInteger(load);
	}


	/**
	 *  Constructor for the HeartBeatKMessage object
	 *
	 *@param  data  Byte array containing data of this type.
	 */
	public HeartBeatKMessage(byte[] data) throws PacketParseException{
		this.setMessage(data);
	}


	/**
	 *  Gets the message bytes of the HeartBeatKMessage object<br>
	 *  <p>
	 *
	 *  This is the actual message package to be sent over a tco link.</p>
	 *
	 *@return    The message value
	 */
	public byte[] getMessage() {
		if (this.message == null) {
			this.message = DataTypesTools.byteCat(super.length.getBytes(),
					DataTypesTools.byteCat(super.type.getBytes(),
					this.load.getBytes()));
		}
		return this.message;
	}


	/**
	 *  Sets the message attribute of the HeartBeatKMessage object <p>
	 *
	 *  This is the way to parse a packet received from a tcp link.</p>
	 *
	 *@param  data                      The new message value
	 *@exception  PacketParseException  Exception thrown when parsing fails
	 */
	public void setMessage(byte[] data) throws PacketParseException {
		KInteger swap = null;

		if (data.length != 12) {
			throw new PacketParseException();
		}

		swap = new KInteger(data[0], data[1], data[2], data[3]);
		if (swap.getIntValue() == 0x08) {
			super.length = swap;
			swap = null;
		} else {
			throw new PacketParseException();
		}

		swap = new KInteger(data[4], data[5], data[6], data[7]);
		if (swap.getIntValue() == 0x00) {
			super.type = swap;
			swap = null;
		} else {
			throw new PacketParseException();
		}

		this.load = new KInteger(data[8], data[9], data[10], data[11]);
	}


	/**
	 *  Sets the load field of the HeartBeatKMessage object
	 *
	 *@param  load  The new load field value
	 */
	public void setLoad(KInteger load) {
		this.load = load;
	}


	/**
	 *  Gets the load field attribute of the HeartBeatKMessage object
	 *
	 *@return    The load field value
	 */
	public KInteger getLoad() {
		return load;
	}
	
	public static void main(String args[]){
		HeartBeatKMessage hbmsg = new HeartBeatKMessage(0xABCDEF00);
		byte[] arr = hbmsg.getMessage();
		System.out.println(DataTypesTools.byteToHex(arr));
	}

}

