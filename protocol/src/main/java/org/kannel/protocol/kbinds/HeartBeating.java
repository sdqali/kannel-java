package org.kannel.protocol.kbinds;
import java.io.IOException;

import org.kannel.protocol.packets.HeartBeatKMessage;

/**
 *  Heart Beating Operations
 *
 *@author     Oscar Medina Duarte
 *@created    April 6, 2005
 */
public class HeartBeating implements Runnable {

	private long rate = 0;
	private KannelBinding kbind = null;
	private HeartBeatKMessage heartbeatMessage = null;
	private boolean running = false;


	/**
	 *  Constructor for the HeartBeating object
	 *
	 *@param  kbind  This is where to send Heart beatings
	 *@param  rate   rate frecuency in milliseconds
	 */
	public HeartBeating(KannelBinding kbind, long rate) {
		this.rate = rate;
		this.kbind = kbind;
	}


	/**
	 *  Run method
	 */
	public void run() {
		if (rate != 0) {
			Thread current = Thread.currentThread();
			if (this.heartbeatMessage == null) {
				this.heartbeatMessage = new HeartBeatKMessage();
			}
			this.running = true;

			try {
				while (this.running) {

					current.sleep(rate);
					kbind.writeNext(this.heartbeatMessage);
					
					

				}
			} catch (IOException e) {

			} catch (InterruptedException e) {

			}
		}
	}


	/**
	 *  Tell this heart beat to stop
	 */
	public void stop() {
		this.running = false;
	}

}

