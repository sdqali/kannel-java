package org.kannel.protocol.tools;

import java.util.Random;
import org.kannel.protocol.exceptions.*;
import org.kannel.protocol.packets.KInteger;
import org.kannel.protocol.packets.KString;
import org.kannel.protocol.packets.KTime;
import org.kannel.protocol.packets.KUUID;

/**
 *  Class containing method tools to handle byte arrays, numbers, etcetera...
 *
 *@author     Oscar Medina Duarte
 *@created    March 31, 2005
 */
public class DataTypesTools {

	/**
	 *  Converts a signed byte number into a unsigned Short
	 *
	 *@param  signed  Signed byte
	 *@return         Unsigned Short
	 */
	public static short signed2unsignedShort(byte signed) {

		//System.out.println((signed < 0 ? (256 - Math.abs(signed)) : signed) + " = " + signed);
		return (short) (signed < 0 ? (256 - Math.abs(signed)) : signed);
	}


	/**
	 *  Appends contents of src to dst and returns a third byte[]
	 *
	 *@param  dst  Destination byte array
	 *@param  src  Source Byte array
	 *@return      dst + src byte array.
	 */
	public static byte[] append(byte[] dst, byte[] src) {

		byte[] data = new byte[(dst == null ? 0 : dst.length) + src.length];

		for (int i = 0; i < dst.length; i++) {
			data[i] = dst[i];
		}

		for (int i = dst.length; i < data.length; i++) {
			data[i] = src[i - dst.length];
		}

		return data;
	}


	/**
	 *  Extracts bytes from start index to end index of source byte array
	 *
	 *@param  start  Starting position
	 *@param  end    Ending position
	 *@param  src    Source byte array
	 *@return        Sub byute array containig bytes start to end from source byte
	 *      array
	 */
	public static byte[] byteSubstring(int start, int end, byte[] src) {
		byte[] dst = new byte[end - start];

		for (int i = 0; i < dst.length; i++) {
			dst[i] = src[i + start];
		}
		return dst;
	}


	/**
	 *  Concatenates two byte arays
	 *
	 *@param  src  Source byte array
	 *@param  dst  Destination byte array
	 *@return      Concatenated byte arrays
	 */
	public static byte[] byteCat(byte[] src, byte[] dst) {

		if (dst == null) {
			return src;
		} else if (src == null) {
			return dst;
		} else {
			byte[] fin = new byte[src.length + dst.length];
			for (int i = 0; i < src.length; i++) {
				fin[i] = src[i];
			}

			for (int i = src.length; i < fin.length; i++) {
				fin[i] = dst[i - src.length];
			}
			return fin;
		}
	}


	/**
	 *  Converts a byte to its corresponding String in Hex representation
	 *
	 *@param  num  Byte number
	 *@return      Hex representation Sring from num
	 */
	public static String byteToHex(byte num) {
		StringBuffer sb = new StringBuffer();
		String snum = Integer.toHexString((num < 0 ? num + 256 : num));
		snum.trim();
		if (snum.length() < 2) {
			sb.append("0");
		}

		sb.append(snum.toUpperCase());
		return sb.toString();
	}


	/**
	 *  Converts a byte array to its corresponding String in Hex representation
	 *
	 *@param  nums  Byte array
	 *@return       Hex representation Sring from nums
	 */
	public static String byteToHex(byte[] nums) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < nums.length; i++) {
			sb.append(byteToHex(nums[i]));
		}

		return sb.toString();
	}


	/**
	 *  Converts a byte to its corresponding String in Hex representation scaped
	 *  for usage as URL encoding
	 *
	 *@param  num  Byte number
	 *@return      URL Hex representation Sring from nums
	 */
	public static String byteToURLHex(byte num) {
		StringBuffer sb = new StringBuffer();
		String snum = Integer.toHexString((num < 0 ? num + 256 : num));
		snum.trim();

		if (snum.length() < 2) {
			sb.append("0");
		}

		sb.append(snum.toUpperCase());
		// System.out.println("oups !?? --- >  " + sb);
		return "%" + sb.toString();
	}


	/**
	 *  Converts a byte array to its corresponding String in Hex representation
	 *  scaped for usage as URL encoding
	 *
	 *@param  nums  Description of the Parameter
	 *@return       URL Hex representation Sring from nums
	 */
	public static String byteToURLHex(byte[] nums) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < nums.length; i++) {
			sb.append("%");
			sb.append(byteToHex(nums[i]));
		}

		return sb.toString();
	}


	/**
	 *  Returns a random number between 0 and 255
	 *
	 *@return    Random Byte
	 */
	public static byte randomByte() {
		return (byte) ((new Random()).nextInt(Byte.MAX_VALUE));
	}


	/**
	 *  Converts a single char value to its Hex equivalent into a byte
	 *
	 *@param  val  Char input value
	 *@return      Byte ascii hex
	 */
	private static byte charToHexValue(char val) {
		if ((val >= 'A') && (val <= 'F')) {
			return (byte) (10 + (val - 'A'));
		} else if ((val >= '0') && (val <= '9')) {
			return (byte) (val - '0');
		}
		return -1;
	}


	/**
	 *  Parses a string of Hex values into a byte array with the equivalent
	 *  numbers.<br>
	 *  For example: <br>
	 *  <p>
	 *
	 *  A sring like : "1110DEAB"<br>
	 *  Would be : byte[] return = {17, 16, 222, 171}</p>
	 *
	 *@param  data                         A string of the form: [0-9A-Fa-f]+
	 *@return                              Byte array containing the parsed data
	 *@exception  NotAnHexStringException  If data is not the form specified above.
	 */
	public static byte[] hexStringToBytes(String data) throws NotAnHexStringException {
		data.trim();
		data.toUpperCase();
		char[] cdata = data.toCharArray();
		byte[] bdata = new byte[cdata.length / 2];

		byte msb = 0;
		byte lsb = 0;
		int secIdx = 0;

		for (int i = 0; i < bdata.length; i++) {

			secIdx = 2 * i;
			msb = charToHexValue(cdata[secIdx]);
			lsb = charToHexValue(cdata[secIdx + 1]);

			if ((msb < 0) || (lsb < 0)) {
				throw new NotAnHexStringException();
			}

			bdata[i] = (byte) (lsb + (msb * (byte) 0x10));

		}
		return bdata;
	}


	/**
	 *  Takes bytes from array to map them into a KInteger
	 *
	 *@param  src       Source byte array
	 *@param  position  First Byte in array to map
	 *@return           Last parsed byte from src
	 */
	public static KInteger parseKIntFromByteArray(byte[] src, int position) {
		KInteger result = new KInteger(src[position], src[(++position)], src[(++position)], src[(++position)]);
		return result;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  src       Description of the Parameter
	 *@param  position  Description of the Parameter
	 *@return           Description of the Return Value
	 */
	public static KTime parseKTimeFromByteArray(byte[] src, int position) {
		KTime result = new KTime(src[position], src[(++position)], src[(++position)], src[(++position)]);
		return result;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  src       Description of the Parameter
	 *@param  position  Description of the Parameter
	 *@return           Description of the Return Value
	 */
	public static KUUID parseKUUIDFromByteArray(byte[] src, int position) {
		KInteger swap = new KInteger(src[position], src[(++position)], src[(++position)], src[(++position)]);
		position++;
		// if(swap.getIntValue() != 0x24){
		if (swap.getIntValue() < 0) {
		    System.out.println("swap < 0");
		    return new KUUID();
		} else {
			byte[] bSwap = new byte[swap.getIntValue()];
			
			//System.out.println("bSwap: ");
			for (int i = 0; i < bSwap.length; i++, position++) {
				bSwap[i] = src[position];
				//System.out.print((char)bSwap[i]);
			}
			//System.out.println("|");
			
			position++;
			swap = null;
			return new KUUID(bSwap);
		}
	}


	/**
	 *  Takes bytes from array to map them into a KString
	 *
	 *@param  src       Source byte array
	 *@param  position  First Byte in array to map
	 *@return           Last parsed byte from src
	 */
	public static KString parseKStringFromByteArray(byte[] src, int position) {
		KInteger swap = new KInteger(src[position], src[(++position)], src[(++position)], src[(++position)]);
		position++;
		// System.out.println(swap);
		if (swap.getIntValue() < 0) {
			return new KString("");
		} else {
			byte[] bSwap = new byte[swap.getIntValue()];
			for (int i = 0; i < bSwap.length; i++, position++) {
				bSwap[i] = src[position];
			}
			position++;
			swap = null;
			return new KString(bSwap);
		}
	}


	/**
	 *  Converts a byte array string into a hex editor representation
	 *
	 *@param  data  byte array
	 *@return       Hex editor representation
	 */
	public static String hexDump(byte[] data) {
		StringBuffer sb = new StringBuffer();
		byte[] hexData = DataTypesTools.byteToHex(data).getBytes();
		int j = 0;
		int last = 0;
		for (int i = 0; i < hexData.length; i++) {

			if (((i % 8) == 0) && (i != 0)) {
				sb.append(" ");
			}
			if (((i % 16) == 0) && (i != 0)) {
				sb.append(" ");
			}
			if (((i % 32) == 0) && (i != 0)) {
				sb.append("| ");
				last = j + 15;
				for (; j < data.length; j++) {
					if ((j % 8) == 0) sb.append((char)' ');
					sb.append((char) (((data[j] > 32) && (data[j] < 126)) ? data[j] : 0x2e));
					if (j == last) {
						j++;
						break;
					}
				}
				sb.append("\n");
			}
			sb.append((char) hexData[i]);
		}

		sb.append("  | ");
		last = j + 16;
		for (; j < data.length; j++) {
			sb.append((char) (((data[j] > 32) && (data[j] < 126)) ? data[j] : 0x2e));
			if (j == last) {
				break;
			}
		}
		sb.append("\n");

		return sb.toString();
	}
}

