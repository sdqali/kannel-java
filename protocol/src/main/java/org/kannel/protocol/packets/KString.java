package org.kannel.protocol.packets;

import org.kannel.protocol.tools.*;
import java.io.UnsupportedEncodingException;

/**
 *  Contains a byte array String with a 4 bytes length field.
 *
 *@author     Oscar Medina Duarte
 *@created    March 31, 2005
 */
public class KString {

	public KInteger length = null;
	public byte[] value = null;


	protected KString(){}
	
	/**
	 *  Constructor for the KString object
	 *
	 *@param  value  String contained in a String field
	 */
	public KString(String value) {
		if (value.length()==0){
			this.value = null;
			// this.length = new KInteger(0xFFFFFFFF);
			this.length = new KInteger(0);
		}else{
			this.value = value.getBytes();
			this.length = new KInteger(this.value.length);
		}
	}


	/**
	 *  Constructor for the KString object
	 *
	 *@param  value                             String contained in a String field
	 *@param  charsetName                       Charset Name to use for converting String to bytes
	 *@exception  UnsupportedEncodingException  Description of the Exception
	 */
	public KString(String value, String charsetName) throws UnsupportedEncodingException {
		if (value.length()==0){
			this.value = null;
			//this.length = new KInteger(0xFFFFFFFF);
			this.length = new KInteger(0);
		}else{
			this.value = value.getBytes(charsetName);
			this.length = new KInteger(this.value.length);
		}
	}


	/**
	 *  Constructor for the KString object
	 *
	 *@param  value  Byte array contained in a String field
	 */
	public KString(byte[] value) {
		if (value.length == 0){
			this.value = null;
			//this.length = new KInteger(0xFFFFFFFF);
			this.length = new KInteger(0);
		}else{
			this.value = value;
			this.length = new KInteger(this.value.length);
		}
	}


	/**
	 *  Gets the bytes attribute of the KString object
	 *  <p>With the form: &lt;length&gt;&lt;StringBytes&gt;  </p>
	 *
	 *@return    The bytes value
	 */
	public byte[] getBytes() {
		if ((this.value == null) || (this.value.length < 1)){
			byte[] NULL = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};
			return NULL;
		}else{
			return DataTypesTools.byteCat(this.length.getBytes(), this.value);
		}
	}
	
	public String getString(){
		if ((this.value == null) || (this.value.length < 1)){
			byte[] NULL = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};
			return new String(NULL);
		}else{
			return new String(this.value);
		}
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.length.toString());
		sb.append(" | ");
		if (this.value != null){
			sb.append(new String(this.value));
		}else{
			sb.append("<NIL>");
		}
		
		return sb.toString();
	}
	
	/**
	 *  Gets the lenght attribute of the KString object
	 *
	 *@return    Number of bytes, not including the 4 bytes length field
	 */
	public KInteger getLength() {
		return this.length;
	}
}

