package org.kannel.protocol.jms;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.kannel.protocol.jms.exceptions.NotEnoughArgumentsException;
import org.kannel.protocol.exceptions.NotEnoughPropertiesException;
import org.kannel.protocol.exceptions.WrongPropertieException;
import org.kannel.protocol.kbinds.KannelBinding;

public class KannelJMSGateway extends Thread{

	public static final String props_inBound = "kjGateway.inBound";
	public static final String props_outBound = "kjGateway.outBound";
	public static final String props_JMSTransportClass = "kjGateway.JMSTransportClass";
	
	private boolean inBoundThreadOn = true;
	private boolean outBoundThreadOn = true;
	
	private Properties properties = null;
	private KannelBinding kannelBind = null;
	private KjWritingThread outBoundThread = null;
	private KjReadingThread inBoundThread = null;
	private JMSTransport jmsTransport = null;
	private AckCycleThread ackAdminThread = null;
	private long ackTTL = 0;
	private long ackFrecuency = 0;
	
	KannelJMSGateway(){}
	
	KannelJMSGateway(String args[]) throws NotEnoughArgumentsException,
							FileNotFoundException,
							IOException,
							ClassNotFoundException,
							InstantiationException,
							IllegalAccessException {
		parseArgs(args);
	}
	
	private void parseArgs(String[] args) throws NotEnoughArgumentsException,
							FileNotFoundException,
							IOException,
							ClassNotFoundException,
							InstantiationException,
							IllegalAccessException {
		if (args.length < 1){
			throw new NotEnoughArgumentsException();
		}else{
			this.properties = new Properties();
			this.properties.load(new FileInputStream(
					new File(args[0])));
					
			String swap = this.properties.getProperty(this.props_inBound);
			if (swap.equalsIgnoreCase("true")){
				this.inBoundThreadOn = true;
			}else{
				this.inBoundThreadOn = false;
			} 
			
			swap = this.properties.getProperty(this.props_outBound);
			if (swap.equalsIgnoreCase("true")){
				this.outBoundThreadOn = true;
			}else{
				this.outBoundThreadOn = false;
			}
			
			// default JMSTransport
			swap = this.properties.getProperty(this.props_JMSTransportClass);
			//System.out.println(swap);
			this.jmsTransport = (JMSTransport)Class.forName(swap).newInstance();
			
			// ack ttl
			swap = this.properties.getProperty(AckCycleThread.prop_waitForAckTTL);
			ackTTL = Long.parseLong(swap);
			
			// ack frecuency
			swap = this.properties.getProperty(AckCycleThread.prop_acknowledgementCycleRate);
			ackFrecuency = Long.parseLong(swap);
			
		}
	}
	
	
	public void terminate(){
	
	}
	
	public void run() {
		try{
			
			if(this.jmsTransport == null){
				// if its null trow exception
				// log and stop
				System.out.println("No hay JMS Translator");
			}else{
				this.jmsTransport.start(this.properties);
			}
			
			
			if(this.kannelBind == null){
				this.kannelBind = new KannelBinding(this.properties);
			}else if(!this.kannelBind.isConnected()){
				this.kannelBind.connect();
			}
			// Start reading thread
			if (this.inBoundThreadOn){
				if (this.inBoundThread == null){
					this.inBoundThread = new KjReadingThread(this.kannelBind, this.jmsTransport);
				}
				
				//this.inBoundThread.start();
				
			}
			
			// Start writing thread
			if (this.outBoundThreadOn){
				if (this.outBoundThread == null){
					this.outBoundThread = new KjWritingThread(this.kannelBind, this.jmsTransport);
				}
				
				//this.outBoundThread.start();
				
			}
			
			if (this.ackAdminThread == null){
				this.ackAdminThread = new AckCycleThread(this.outBoundThread, ackFrecuency);
				this.ackAdminThread.setAckTTL(ackTTL);
			}
			this.ackAdminThread.start();
			
			if (this.outBoundThreadOn){
				//this.outBoundThread.start();	
			}
			if (this.inBoundThreadOn){
				this.inBoundThread.start();
			}
			
			// And add acknowledger to in and out binded threads
			this.inBoundThread.addAckCycleThread(this.ackAdminThread);
			this.outBoundThread.addAckCycleThread(this.ackAdminThread);
			
			
		}catch(NotEnoughPropertiesException e){
			// Logging facilities
		}catch(WrongPropertieException e){
			// Logging facilities
		}catch(IOException e){
			// Logging facilities
		}
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public void setKannelBind(KannelBinding kannelBind) {
		this.kannelBind = kannelBind;
	}
	
	public void setOutBoundThread(KjWritingThread outBoundThread) {
		this.outBoundThread = outBoundThread;
	}
	public void setInBoundThread(KjReadingThread inBoundThread) {
		this.inBoundThread = inBoundThread;
	}
	public void setInBoundThreadOn(boolean inBoundThreadOn) {
		this.inBoundThreadOn = inBoundThreadOn;
	}
	public void setOutBoundThreadOn(boolean outBoundThreadOn) {
		this.outBoundThreadOn = outBoundThreadOn;
	}
	public boolean getInBoundThreadOn() {
		return inBoundThreadOn;
	}
	public boolean getOutBoundThreadOn() {
		return outBoundThreadOn;
	}

	public KjWritingThread getOutBoundThread() {
		return outBoundThread;
	}
	public KjReadingThread getInBoundThread() {
		return inBoundThread;
	}
	
	public Properties getProperties() {
		return properties;
	}
	public KannelBinding getKannelBind() {
		return kannelBind;
	}

	
	public static void main(String args[]) throws Exception{
		System.out.println("Kannel - JMS Gateway v0.1\n by: Oscar Medina Duarte\n moscar[at]gmail.com");
		KannelJMSGateway kjmsGateway = new KannelJMSGateway(args);
		kjmsGateway.start();
	}
}
