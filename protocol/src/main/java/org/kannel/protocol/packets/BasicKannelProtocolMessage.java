package org.kannel.protocol.packets;

/**
 *  All kannel's messages classes, should implement this interface.
 *
 *@author     Oscar Medina Duarte
 *@created    March 31, 2005
 */
public interface BasicKannelProtocolMessage {

	/**
	 *  Gets the full message as an array of bytes as it would be sent over a TCP
	 *  link.
	 *
	 *@return    The message value
	 */
	public byte[] getMessage();



	/**
	 *  Sets the message data to a message class as it would have been received
	 *  from a TCP link.Sets the message attribute of the
	 *  BasicKannelProtocolMessage object
	 *
	 *@param  data  The new message value
	 */
	public void setMessage(byte[] data) throws Exception;

}

