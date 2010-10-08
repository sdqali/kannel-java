package org.kannel.protocol.kbinds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.kannel.protocol.exceptions.NotEnoughPropertiesException;
import org.kannel.protocol.exceptions.PacketParseException;
import org.kannel.protocol.exceptions.WrongPropertieException;
import org.kannel.protocol.packets.BasicKannelProtocolMessage;
import org.kannel.protocol.packets.BasicPacket;
import org.kannel.protocol.packets.SMSPacketMessage;

/**
 * An extension to KannelBinding that uses BlockingQueues for reading a writing, and
 * exposes those queues to the user.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class KannelBindingQueue
    extends KannelBinding
{

    public KannelBindingQueue(Properties conf) throws NotEnoughPropertiesException,
						      WrongPropertieException,
						      IOException
    {
	//	super(conf);
	init(conf);
	readQueue = new LinkedBlockingQueue<BasicPacket>();
	writeQueue = new LinkedBlockingQueue<BasicKannelProtocolMessage>();
	if (this.connectedAtStart) {
	    connect();
	}
    }

    private ReadThread reader;
    private WriteThread writer;
    private boolean running;

    private LinkedBlockingQueue<BasicPacket> readQueue;
    private LinkedBlockingQueue<BasicKannelProtocolMessage> writeQueue;

    public BlockingQueue<BasicPacket> getReadQueue() { return this.readQueue; }

    public BasicPacket read() throws Exception { return getReadQueue().take(); }

    public BlockingQueue<BasicKannelProtocolMessage> getWriteQueue() { return this.writeQueue; }

    public void write(BasicKannelProtocolMessage bkpMessage) throws Exception { getWriteQueue().put(bkpMessage); }

    public void connect() throws IOException
    {
	running = true;
	reader = new ReadThread();
	reader.start();
	writer = new WriteThread();
	writer.start();
	super.connect();
    }
    
    public void disconnect() throws IOException {
	running = false;
	super.disconnect();
    }

    /**
     * Test main method
     */
    public static void main(String argv[]) throws Exception
    {
	Properties props = new Properties();
	props.load(new FileInputStream(new File(argv[0])));
	KannelBindingQueue kbndg = new KannelBindingQueue(props);
	SMSPacketMessage sms = new SMSPacketMessage("44636", "6508145269", "", "test message");
	kbndg.write(sms);
	//kbndg.writeNext(sms);
    }

    private class ReadThread extends Thread
    {
	public void run()
	{
	    while (running) {
		try {
		    // TODO: Make readNext timeout.
		    BasicPacket p = readNext();
		    readQueue.put(p);
		} catch (IOException ioe) {
		    //
		} catch (PacketParseException ppe) {
		    //
		} catch (InterruptedException ie) {
		    //
		}
	    }
	    System.out.println("Exiting ReadThread");
	}
    }

    private class WriteThread extends Thread
    {
	public void run()
	{
	    while (running) {
		try {
		    BasicKannelProtocolMessage m = writeQueue.poll(1, TimeUnit.SECONDS);
		    if (m != null) writeNext(m);
		} catch (IOException ioe) {
		    //
		} catch (InterruptedException ie) {
		    //
		}
	    }
	    System.out.println("Exiting WriteThread");
	}
    }

}