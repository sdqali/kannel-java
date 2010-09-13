package org.kannel.protocol.packets;

import org.kannel.protocol.exceptions.*;
import org.kannel.protocol.tools.*;

/**
 *  Kannel's protocol admin message.
 *
 *@author     Oscar Medina Duarte
 *@created    March 31, 2005
 */
public class AdminKMessage extends BasicPacket implements BasicKannelProtocolMessage {

	/**
	 *  Shutdown command
	 */
	public final static int CMD_SHUTDOWN = 0;
	/**
	 *  Suspend  command
	 */
	public final static int CMD_SUSPEND = 1;
	/**
	 *  Resume  command
	 */
	public final static int CMD_RESUME = 2;
	/**
	 *  Identify  command
	 */
	public final static int CMD_IDENTIFY = 3;
	/**
	 *  Restart  command
	 */
	public final static int CMD_RESTART = 4;

	private byte[] message = null;

	private KInteger adm_command = null;
	private KString boxc_id = null;


	/**
	 *  Constructor for the AdminKMessage object
	 */
	public AdminKMessage() { }


	/**
	 *  Constructor for the AdminKMessage object
	 *
	 *@param  command  Command
	 *@param  boxc_id  Name of the box
	 */
	public AdminKMessage(int command, String boxc_id) {

		super.type = new KInteger(super.ADMIN_PKT);
		this.adm_command = new KInteger(command);
		this.boxc_id = new KString(boxc_id);

		super.length = new KInteger(4 + 4 + 4 + this.boxc_id.getLength().getIntValue());
	}


	/**
	 *  Constructor for the AdminKMessage object
	 *
	 *@param  data                      byte array containing an Admin packet as it was received over a tcp link
	 *@exception  PacketParseException  Exception thrown when parsing fails
	 */
	public AdminKMessage(byte[] data) throws PacketParseException {
		this.setMessage(data);
	}


	/**
	 *  Gets the message bytes of the AdminKMessage object<br>
	 *  <p>
	 *
	 *  This is the actual message package to be sent over a tco link.</p>
	 *
	 *@return    The message value
	 */
	public byte[] getMessage() {

		if (this.message == null) {
			this.message = DataTypesTools.byteCat(super.length.getBytes(), super.type.getBytes());
			this.message = DataTypesTools.byteCat(this.message, this.adm_command.getBytes());
			this.message = DataTypesTools.byteCat(this.message, this.boxc_id.getBytes());
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
		int index = 0;

		if (data.length < 8) {
			throw new PacketParseException();
		}

		swap = new KInteger(data[0], data[1], data[2], data[3]);
		if ((swap.getIntValue() + 4) == data.length) {
			super.length = swap;
			swap = null;
		} else {
			throw new PacketParseException();
		}

		swap = new KInteger(data[4], data[5], data[6], data[7]);
		if (swap.getIntValue() == 0x01) {
			super.type = swap;
			swap = null;
		} else {
			System.out.println("--3");
			throw new PacketParseException();
		}
		index = 8;

		//////  Y parsear el resto ////////
		this.adm_command = DataTypesTools.parseKIntFromByteArray(data, index);
		index += 4;
		System.out.println(this.adm_command);
		this.boxc_id = DataTypesTools.parseKStringFromByteArray(data, index);
		index += this.boxc_id.getLength().getIntValue() + 4;
		///////////////////////////////////
	}


	/**
	 *  Sets the adm_command attribute of the AdminKMessage object
	 *
	 *@param  adm_command  The new adm_command value
	 */
	public void setAdm_command(KInteger adm_command) {
		this.adm_command = adm_command;
	}


	/**
	 *  Sets the boxc_id attribute of the AdminKMessage object
	 *
	 *@param  boxc_id  The new boxc_id value
	 */
	public void setBoxc_id(KString boxc_id) {
		this.boxc_id = boxc_id;
	}


	/**
	 *  Gets the adm_command attribute of the AdminKMessage object
	 *
	 *@return    The adm_command value
	 */
	public KInteger getAdm_command() {
		return adm_command;
	}


	/**
	 *  Gets the boxc_id attribute of the AdminKMessage object
	 *
	 *@return    The boxc_id value
	 */
	public KString getBoxc_id() {
		return boxc_id;
	}


	/**
	 *  Class testing
	 *
	 *@param  args           Description of the Parameter
	 *@exception  Exception  Exception thrown when
	 */
	public static void main(String args[]) throws Exception {

		byte[] data = {0, 0, 0, 21, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 9, 116, 101, 115, 116, 101, 111, 95, 48, 48};
		AdminKMessage amsg = new AdminKMessage(data);
		byte[] arr = amsg.getMessage();
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.println("\n" + DataTypesTools.byteToHex(arr));
		
	}

}

