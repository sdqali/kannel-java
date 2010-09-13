package org.kannel.protocol.jms;

import org.kannel.protocol.kbinds.KannelBinding;
import org.kannel.protocol.packets.SMSPacketMessage;
import org.kannel.protocol.packets.BasicKannelProtocolMessage;
import java.io.IOException;
import java.util.LinkedList;

/**
 *  This class reads packets from a JMS Queue and redirects them to a Kannel link
 *
 *@author     Oscar Medina Duarte
 *@created    April 6, 2005
 */
public class KjWritingThread extends Thread {

	private KannelBinding kbind = null;
	private JMSTransport jmsTransport = null;
	private AckCycleThread ackAdminThread = null;
	private LinkedList mesages = null;
	
	
	/**
	 *  Constructor for the KjWritingThread object
	 *
	 *@param  kbind  Description of the Parameter
	 */
	public KjWritingThread(KannelBinding kbind) {
		this.kbind = kbind;
		this.mesages = new LinkedList();
	}

	public KjWritingThread(KannelBinding kbind, JMSTransport jmsTransport) {
		this.kbind = kbind;
		this.addJMSTransport(jmsTransport);
		this.jmsTransport.addKjWritingThread(this);
		this.mesages = new LinkedList();
	}
	
	public void send(SMSPacketMessage pack){
		try{
			if(this.ackAdminThread != null){
				//System.out.println("set ack");
				this.ackAdminThread.waitAck(pack);
			}
			// System.out.println("write it");
			this.kbind.writeNext((BasicKannelProtocolMessage)pack);
		}catch(IOException e){
			System.out.println("sendind message failed : " + e);
		}
	}
	
	public void sendOnThread(SMSPacketMessage pack){
		this.mesages.add(pack);
	}
	
	public void rawWrite(byte[] pktMessage){
		try{
			this.kbind.rawWrite(pktMessage);
		}catch(IOException e){
			System.out.println("Raw writing failed : " + e);
		}
	}
	
	public void addJMSTransport(JMSTransport jmsTransport){
		this.jmsTransport = jmsTransport;
		this.jmsTransport.addKjWritingThread(this);
	}
	
	public void addAckCycleThread(AckCycleThread ackAdminThread){
		this.ackAdminThread = ackAdminThread;
	}
	
	/**
	 *  Main processing method for the KjWritingThread object
	 */
	public void run() {
		// Wait for packets on a JMSTranstalor
		
		// Write them to a Kannel Link when received
		System.out.println("Writing thread started.");
		SMSPacketMessage sms = null;
		while(true){
			
			if(this.mesages.size() > 0){
				
				System.out.println("sending");
				sms = (SMSPacketMessage)this.mesages.removeFirst();
				send(sms);
				//byte[] bytes = sms.getMessage();
				//rawWrite(sms.getMessage());
			
			}
		}
	}
}

