package org.kannel.protocol.packets;

import org.kannel.protocol.exceptions.*;
import org.kannel.protocol.tools.*;

/**
 *  Kannel's protocol WDP packet message.
 *
 *@author     Oscar Medina Duarte
 *@created    March 31, 2005
 */
public class WDPDatagramPacketMessage extends BasicPacket implements BasicKannelProtocolMessage {
	
	private byte[] message = null;
	
	public WDPDatagramPacketMessage(){}
	
	public WDPDatagramPacketMessage(int command, String boxc_id){}
	
	public WDPDatagramPacketMessage(byte[] data) throws PacketParseException{
		this.setMessage(data);
	}
	
	public byte[] getMessage(){
		
		if(this.message == null){
			this.message = DataTypesTools.byteCat(super.length.getBytes(), super.type.getBytes());
			//this.message = DataTypesTools.byteCat(this.message, {0});
		}
		
		return this.message;
	}

	public void setMessage(byte[] data) throws PacketParseException{
		KInteger swap = null;
		int index = 0;
		
		if (data.length < 8) {
			throw new PacketParseException();
		}

		swap = new KInteger(data[0], data[1], data[2], data[3]);
		if ((swap.getIntValue()+4) == data.length) {
			super.length = swap;
			swap = null;
		} else {
			throw new PacketParseException();
		}
		
		swap = new KInteger(data[4], data[5], data[6], data[7]);
		if (swap.getIntValue() == 0x02) {
			super.type = swap;
			swap = null;
		} else {
			throw new PacketParseException();
		}
		index = 8;
		
		//////  Y parsear el resto ////////
		
		///////////////////////////////////
	}
}

