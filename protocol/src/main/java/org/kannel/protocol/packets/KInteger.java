package org.kannel.protocol.packets;

import org.kannel.protocol.tools.DataTypesTools;
/**
 *  4 bytes Integer container
 *
 *@author     Oscar Medina Duarte
 *@created    March 30, 2005
 */
public class KInteger {

	public final static int MAXVALUE = 0xFFFFFFFF;

	/**
	 *  Value for this object
	 */
	public byte[] value = {0, 0, 0, 0};


	/**
	 *  Constructor for the KInteger object that takes a int as initial value
	 *
	 *@param  value  Value of this Object
	 */
	public KInteger(int value) {
		setValue(value);
	}


	/**
	 *  Constructor for the KInteger object that takes a byte array as initial
	 *  value
	 *
	 *@param  value  Byte array to taken as Integer in this Object, MSB is index 3,
	 *      LSB is index 0.
	 */
	public KInteger(byte[] value) {
		setValue(value);
	}


	/**
	 *  Constructor for the KInteger object that takes 4 bytes to form the initial
	 *  value.
	 *
	 *@param  msb     Most significant byte
	 *@param  third   Third least significant byte
	 *@param  second  Second least significant byte
	 *@param  lsb     Least significant byte
	 */
	public KInteger(byte msb, byte third, byte second, byte lsb) {
		setValue(msb, third, second, lsb);
	}


	/**
	 *  Sets the value attribute of the KInteger object
	 *
	 *@param  value  The new value value
	 */
	public void setValue(byte[] value) {
		setValue(value[0],
				value[1],
				value[2],
				value[3]);
	}

	/**
	 *  Sets the value attribute of the KInteger object
	 *
	 *@param  msb     Most significant byte
	 *@param  third   Third least significant byte
	 *@param  second  Second least significant byte
	 *@param  lsb     Least significant byte
	 */
	public void setValue(byte msb, byte third, byte second, byte lsb) {
				
		this.value[0] = msb;
		this.value[1] = third;
		this.value[2] = second;
		this.value[3] = lsb;
	}


	/**
	 *  Sets the value attribute of the KInteger object it truncates the extra
	 *  bytes
	 *
	 *@param  value  The new value value
	 */
	public void setValue(int value) {
		byte msb = 0;
		byte lsb = 0;
		byte third = 0;
		byte second = 0;
		msb = (byte) (value >> 24);

		third = (byte) ((value << 8) >> 24);
		second = (byte) ((value << 16) >> 24);
		lsb = (byte) ((value << 24) >> 24);

		// System.out.println( msb + " " + third + " " + second + " " + lsb);

		setValue(msb, third, second, lsb);
	}


	/**
	 *  Gets the value attribute of the KInteger object
	 *
	 *@return    The value value
	 */
	public byte[] getValue() {
		return value;
	}


	/**
	 *  Gets the integer value of the KInteger object
	 *
	 *@return    The intValue value
	 */
	public int getIntValue() {

		return DataTypesTools.signed2unsignedShort(this.value[3]) +
				DataTypesTools.signed2unsignedShort(this.value[2]) * 256 +
				DataTypesTools.signed2unsignedShort(this.value[1]) * (int) Math.pow(256, 2) +
				DataTypesTools.signed2unsignedShort(this.value[0]) * (int) Math.pow(256, 3);
	}


	/**
	 *  Gets the bytes from the value.
	 *
	 *@return    The bytes value
	 */
	public byte[] getBytes() {
		return getValue();
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(this.getIntValue()).append(" = ")
				.append(this.value[0]).append(" ")
				.append(this.value[1]).append(" ")
				.append(this.value[2]).append(" ")
				.append(this.value[3]);
		return sb.toString();
	}


	/**
	 *  Class testing
	 *
	 *@param  args  Description of the Parameter 
	 */
	public static void main(String args[]) {
		KInteger el_int = new KInteger(-1411510265);

		System.out.println("Rearmandolo: " + el_int.getIntValue());

	}
}


