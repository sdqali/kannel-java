package org.kannel.protocol.packets;

import java.util.Random;
import java.util.UUID;
    
/**
 * Contains a Universal Unique IDentifier 
 *
 * @author     Oscar Medina Duarte
 * @author     Garth Patil <garthpatil@gmail.com>
 * @created    April 4, 2005
 */
public class KUUID
    extends KString
{

    private UUID uuid = null;

    /**
     *  Constructor for the KUUID object
     *
     *@param  value  String representation of a UUID
     */
    public KUUID(String value) {
	super(value);
    }

    /**
     *  Constructor for the KUUID object
     *
     *@param  value  Byte array of a string representation of the UUID
     */
    public KUUID(byte[] value) {
	super(value);
    }

    /**
     *  Constructor for the KUUID object
     */
    public KUUID() {
	this.uuid = UUID.randomUUID();
	super.value = this.uuid.toString().getBytes();
	super.length = new KInteger(super.value.length);
    }

    /**
     *  Testing method
     *
     *@param  args  Description of the Parameter
     */
    public static void main(String args[]) {
	KUUID uuid = new KUUID();
	System.out.println(uuid);
    }

}

