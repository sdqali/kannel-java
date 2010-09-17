package org.kannel.protocol.gateway.jms;

import org.kannel.protocol.packets.SMSPacketMessage;
import java.io.Serializable;

public class SimpleMessage implements Serializable{
	
	private String sender = "";
	private String receiver = "";
	private String udhData = "";
	private String msgData = "";
	
	public SimpleMessage(){}
	
	public SimpleMessage(String sender, String receiver, String udhData, String msgData){
		setSender(sender);
		setReceiver(receiver);
		setUdhData(udhData);
		setMsgData(msgData);
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public void setUdhData(String udhData) {
		this.udhData = udhData;
	}
	public void setMsgData(String msgData) {
		this.msgData = msgData;
	}
	public String getSender() {
		return sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public String getUdhData() {
		return udhData;
	}
	public String getMsgData() {
		return msgData;
	}
	
	
}
