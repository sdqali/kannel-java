package org.kannel.protocol.packets;

import org.kannel.protocol.exceptions.*;
import org.kannel.protocol.tools.*;

/**
 *  Kannel's protocol acknowledge message.
 *
 *@author     Oscar Medina Duarte
 *@created    March 31, 2005
 */
public class AckMessage extends BasicPacket implements BasicKannelProtocolMessage {
	
	public static final int ACK_STAT_SUCCESS = 0;
	public static final int ACK_STAT_FAILED = 1;
	public static final int ACK_STAT_FAILED_TMP = 2;
	public static final int ACK_STAT_BUFFERED = 3;
	
	private byte[] message = null;
	
	private KInteger nack = null;
	private KTime time = null;
	private KUUID uuid = null;
	
	
	public AckMessage(int nack){
		int len = 0;
		super.type = new KInteger(super.ACK_PKT);
		this.nack = new KInteger(nack);
		len += 4;
		this.time = new KTime();
		len += 4;
		this.uuid = new KUUID();
		len += this.uuid.getLength().getIntValue() + 4;
		super.length = new KInteger(len);
	}
	
	public AckMessage(int nack, int time, String uuid){
		int len = 0;
		super.type = new KInteger(super.ACK_PKT);
		this.nack = new KInteger(nack);
		len += 4;
		this.time = new KTime(time);
		len += 4;
		this.uuid = new KUUID(uuid);
		len += this.uuid.getLength().getIntValue() + 4;
		super.length = new KInteger(len);
	}
	
	public AckMessage(byte[] data) throws PacketParseException{
		this.setMessage(data);
	}
	
	public AckMessage(int nack, KUUID kuuid){
		int len = 0;
		super.type = new KInteger(super.ACK_PKT);
		this.nack = new KInteger(nack);
		len += 4;
		this.time = new KTime();
		len += 4;
		this.uuid = kuuid;
		len += this.uuid.getLength().getIntValue() + 4;
		super.length = new KInteger(len);
	}
	
	public AckMessage(KUUID kuuid){
		int len = 0;
		super.type = new KInteger(super.ACK_PKT);
		this.nack = new KInteger(this.ACK_STAT_SUCCESS);
		len += 4;
		this.time = new KTime();
		len += 4;
		this.uuid = kuuid;
		len += this.uuid.getLength().getIntValue() + 4;
		super.length = new KInteger(len);
	}
	
	public AckMessage(int nack, SMSPacketMessage smsMesage){
		int len = 0;
		super.type = new KInteger(super.ACK_PKT);
		len += 4;
		this.nack = new KInteger(nack);
		len += 4;
		this.time = new KTime();
		len += 4;
		this.uuid = smsMesage.getUuid();
		len += this.uuid.getLength().getIntValue() + 4;
		super.length = new KInteger(len);
	}
	
	public byte[] getMessage(){
		
		if(this.message == null){
			this.message = DataTypesTools.byteCat(super.length.getBytes(), super.type.getBytes());
			this.message = DataTypesTools.byteCat(this.message, this.nack.getBytes());
			this.message = DataTypesTools.byteCat(this.message, this.time.getBytes());
			this.message = DataTypesTools.byteCat(this.message, this.uuid.getBytes());
			
		}
		return this.message;
	}

	public void setMessage(byte[] data) throws PacketParseException{
		KInteger swap = null;
		int index = 0;
		try{
			if (data.length < 8) {
				//System.out.println("ppe: 1");
				throw new PacketParseException();
			}
	
			swap = new KInteger(data[0], data[1], data[2], data[3]);
			if ((swap.getIntValue()+4) == data.length) {
				super.length = swap;
				swap = null;
			} else {
				//System.out.println("ppe: 2");
				throw new PacketParseException();
			}
	
			swap = new KInteger(data[4], data[5], data[6], data[7]);
			if (swap.getIntValue() == 0x03) {
				super.type = swap;
				swap = null;
			} else {
				//System.out.println("ppe: 3");
				throw new PacketParseException();
			}
			index = 8;
			
			//////  Y parsear el resto ////////
			this.nack = DataTypesTools.parseKIntFromByteArray(data, index);
			index += 4;
			this.time = DataTypesTools.parseKTimeFromByteArray(data, index);
			index += 4;
			this.uuid = DataTypesTools.parseKUUIDFromByteArray(data, index);
			index += this.uuid.getLength().getIntValue() + 4;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new PacketParseException();
		} catch (PacketParseException e){
			throw e;
		}
		///////////////////////////////////
	}
	
	/**
	 *  Returns a Hex Editor style representation of the message
	 *
	 *@return    String representation in Hex editor style
	 */
	public String hexDump() {
		return DataTypesTools.hexDump(this.getMessage());

	}

	
	public void setNack(KInteger nack) {
		this.nack = nack;
	}
	public void setTime(KTime time) {
		this.time = time;
	}
	public void setUuid(KUUID uuid) {
		this.uuid = uuid;
	}
	public KInteger getNack() {
		return nack;
	}
	public KTime getTime() {
		return time;
	}
	public KUUID getUuid() {
		return uuid;
	}
}

