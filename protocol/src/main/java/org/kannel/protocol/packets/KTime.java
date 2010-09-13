package org.kannel.protocol.packets;

import org.kannel.protocol.tools.DataTypesTools;

/**
 *  Contains a time field
 *
 *@author     Oscar Medina Duarte
 *@created    April 4, 2005
 */
public class KTime extends KInteger {

	/**
	 *  Constructor for the KTime object
	 *
	 *@param  value  New time value
	 */
	public KTime(int value) {
		super(value);
	}


	/**
	 *  Constructor for the KTime object
	 *
	 *@param  value  Description of the Parameter
	 */
	public KTime(byte[] value) {
		super(value);
	}


	/**
	 *  Constructor for the KTime object that takes 4 bytes to form the initial
	 *  value.
	 *
	 *@param  msb     Most significant byte
	 *@param  third   Third least significant byte
	 *@param  second  Second least significant byte
	 *@param  lsb     Least significant byte
	 */
	public KTime(byte msb, byte third, byte second, byte lsb) {
		super(msb, third, second, lsb);
	}


	/**
	 *  Constructor for the KTime object
	 *  <p>It puts a default initial time.<br><b>(Not yet implemented)</b></p>
	 */
	public KTime() {
		super((int)(System.currentTimeMillis()/1000));
	}
}

