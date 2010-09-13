package org.kannel.protocol.jms;

import org.kannel.protocol.kbinds.KannelBinding;
import org.kannel.protocol.exceptions.PacketParseException;
import org.kannel.protocol.packets.AckMessage;
import org.kannel.protocol.packets.SMSPacketMessage;
import org.kannel.protocol.packets.BasicPacket;
import java.io.IOException;

/**
 *  This class reads packets from a kannel link and redirects them to a JMS Queue
 *
 *@author     Oscar Medina Duarte
 *@created    April 6, 2005
 */
public class KjReadingThread extends Thread {

	private KannelBinding kbind = null;
	private JMSTransport jmsTransport = null;
	private AckCycleThread ackAdminThread = null;
	private boolean halted = false;
	
	
	public void setKbind(KannelBinding kbind) {
		this.kbind = kbind;
	}
	public KannelBinding getKbind() {
		return kbind;
	}

	
	/**
	 *  Constructor for the KjReadingThread object
	 *
	 *@param  kbind  KannelBinding link
	 */
	public KjReadingThread(KannelBinding kbind) {
		this.kbind = kbind;
	}
	
	/**
	 *  Constructor for the KjReadingThread object
	 *
	 *@param  kbind  KannelBinding link
	 */
	public KjReadingThread(KannelBinding kbind, JMSTransport jmsTransport) {
		this.kbind = kbind;
		this.addJMSTransport(jmsTransport);
	}

	public void addJMSTransport(JMSTransport jmsTransport){
		this.jmsTransport = jmsTransport;
	}
	
	public void addAckCycleThread(AckCycleThread ackAdminThread){
		this.ackAdminThread = ackAdminThread;
	}
	
	public void halt(){
		halted = true;
	}

	public boolean halted(){
		return halted;
	}
	/**
	 *  Main processing method for the KjReadingThread object
	 */
	public void run() {
		// wait for packets on a Klink
		// if message is ack, write gotAck to ack thread
		// else if message of type sms
		// Then send them to a JMSTransport
		BasicPacket recPacket = null;
		int type = -1;
		System.out.println("Reading thread started.");
		try{
			while(!halted){
				
				try{
					//System.out.println("???");
					recPacket = kbind.readNext();
					//System.out.println("???2");
					
					if (recPacket != null){
						type = recPacket.type.getIntValue();
						
						if(type == BasicPacket.ACK_PKT){
							
							if(this.ackAdminThread != null){
								//System.out.println("ack received" + (AckMessage)recPacket);
								this.ackAdminThread.confirmAck((AckMessage)recPacket);
							}
							
						}else if(type == BasicPacket.ADMIN_PKT) {
						
						}else if(type == BasicPacket.HEARTBEAT_PKT) {
						
						}else if(type == BasicPacket.SMS_PKT) {
							
							this.jmsTransport.gotMOMessage((SMSPacketMessage)recPacket);
							
							
						}else if(type == BasicPacket.WDP_PKT) {
						
						}else{
							// NOP
						}
					}
				}catch(PacketParseException e){
					// Performance bottle neck
					// Logging
					System.out.println("Packet parse exception");
				}
				try{this.sleep(100);}catch(Exception e){e.printStackTrace();}
			}
		}catch(IOException e){
			// Logging
			System.out.println("Thread Error");
		}
	}
}

