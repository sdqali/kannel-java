package org.kannel.protocol.packets;

/**
 *  The basic type of packet, it contains lenght and type for a kannel protocol message.
 *  This class is used by extending it.
 *
 *@author     Oscar Medina Duarte
 *@created    March 30, 2005
 */
public class BasicPacket {
	/**
	 *  4 byte integer, all bits set to 1, it is used to indicate that a field is
	 *  not in use.
	 */
	public final static KInteger NODATA = new KInteger((byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF);

	/**
	 *  Heart beat type of packet constant
	 */
	public final static byte HEARTBEAT_PKT = 0x00;
	/**
	 *  Admin type of packet constant
	 */
	public final static byte ADMIN_PKT = 0x01;
	/**
	 *  Short Message Service type of packet constant
	 */
	public final static byte SMS_PKT = 0x02;
	/**
	 *  Acknowledgement type of packet constant
	 */
	public final static byte ACK_PKT = 0x03;
	/**
	 *  Wireless Datagram Packet type of packet constant
	 */
	public final static byte WDP_PKT = 0x04;

	/**
	 *  Length of the packet
	 */
	public KInteger length;
	/**
	 *  Type of the packet
	 */
	public KInteger type;

}

