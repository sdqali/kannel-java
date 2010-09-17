package org.kannel.protocol.gateway;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.kannel.protocol.exceptions.NotEnoughPropertiesException;
import org.kannel.protocol.exceptions.WrongPropertieException;
import org.kannel.protocol.kbinds.KannelBinding;

/**
 * Base class for creating Kannel gateways.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class KannelGateway
    extends Thread
{

    public static final String props_inBound = "kjGateway.inBound";
    public static final String props_outBound = "kjGateway.outBound";
    
    protected boolean inBoundThreadOn = true;
    protected boolean outBoundThreadOn = true;
    
    protected Properties properties = null;
    protected KannelBinding kannelBind = null;
    protected KjWritingThread outBoundThread = null;
    protected KjReadingThread inBoundThread = null;
    protected AckCycleThread ackAdminThread = null;
    protected long ackTTL = 0;
    protected long ackFrecuency = 0;
    
    public KannelGateway() {}
    
    public KannelGateway(String args[]) throws FileNotFoundException,
					       IOException,
					       ClassNotFoundException,
					       InstantiationException,
					       IllegalAccessException
    {
	parseArgs(args);
    }
    
    private void parseArgs(String[] args) throws FileNotFoundException,
						 IOException,
						 ClassNotFoundException,
						 InstantiationException,
						 IllegalAccessException
    {
	if (args.length < 1){
	    throw new IllegalArgumentException();
	}else{
	    this.properties = new Properties();
	    this.properties.load(new FileInputStream(new File(args[0])));
	    
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
	    
	    // ack ttl
	    swap = this.properties.getProperty(AckCycleThread.prop_waitForAckTTL);
	    ackTTL = Long.parseLong(swap);
			
	    // ack frecuency
	    swap = this.properties.getProperty(AckCycleThread.prop_acknowledgementCycleRate);
	    ackFrecuency = Long.parseLong(swap);
	    
	}
    }
    
    
    public void terminate()
    {
	
    }
    
    public void run()
    {
	try{
	    if (this.kannelBind == null) {
		this.kannelBind = new KannelBinding(this.properties);
	    } else if (!this.kannelBind.isConnected()) {
		this.kannelBind.connect();
	    }

	    if (this.ackAdminThread == null){
		this.ackAdminThread = new AckCycleThread(this.outBoundThread, ackFrecuency);
		this.ackAdminThread.setAckTTL(ackTTL);
	    }
	    this.ackAdminThread.start();

	    // Start reading thread
	    if (this.inBoundThreadOn){
		this.inBoundThread.addAckCycleThread(this.ackAdminThread);
		this.inBoundThread.start();
	    }
	    
	    // Start writing thread
	    if (this.outBoundThreadOn){
                this.outBoundThread.addAckCycleThread(this.ackAdminThread);
		this.outBoundThread.start();
	    }
	    
	} catch(NotEnoughPropertiesException e) {
	    // 
	} catch(WrongPropertieException e) {
	    // 
	} catch(IOException e) {
	    // 
	}
    }
	
    public Properties getProperties() { return properties; }
    public void setProperties(Properties properties) { this.properties = properties; }

    public KannelBinding getKannelBind() { return kannelBind; }
    public void setKannelBind(KannelBinding kannelBind) { this.kannelBind = kannelBind; }
    
    public KjWritingThread getOutBoundThread() { return outBoundThread; }
    public void setOutBoundThread(KjWritingThread outBoundThread) { this.outBoundThread = outBoundThread; }

    public boolean getOutBoundThreadOn() { return outBoundThreadOn; }
    public void setOutBoundThreadOn(boolean outBoundThreadOn) { this.outBoundThreadOn = outBoundThreadOn; }

    public KjReadingThread getInBoundThread() {	return inBoundThread; }
    public void setInBoundThread(KjReadingThread inBoundThread) { this.inBoundThread = inBoundThread; }

    public boolean getInBoundThreadOn() { return inBoundThreadOn; }
    public void setInBoundThreadOn(boolean inBoundThreadOn) { this.inBoundThreadOn = inBoundThreadOn; }

}
