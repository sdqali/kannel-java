package org.kannel.conf;

import java.net.URL;

/**
 * dlr-db configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class DlrDbConfiguration
    extends Configuration
{

    public DlrDbConfiguration()
    {
	super("dlr-db");
    }

    private static final String[] PROP_ORDER = { "id","table","field-smsc","field-timestamp","field-destination","field-source","field-service","field-url","field-mask","field-status","field-boxc-id" };

    public String[] getPropertyOrder() {
	return PROP_ORDER;
    }

    private static final String[] MANDATORY_PROPS = { "id","table","field-smsc","field-timestamp","field-destination","field-source","field-service","field-url","field-mask","field-status","field-boxc-id" };

    public String[] getMandatoryProps() {
	return MANDATORY_PROPS;
    }

    /**
     * id
     * An id to identify which external connection should be used for DLR storage. Any 
     * string is acceptable, but semicolon ';' may cause problems, so avoid it and any 
     * other special non-alphabet characters. 
     */
    private String id;
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }

    /**
     * table
     * The name of the table that is used to store the DLR information. 
     */
    private String table;
    public String getTable() { return this.table; }
    public void setTable(String table) { this.table = table; }

    /**
     * field-smsc
     * The table field that is used for the smsc data. 
     */
    private String fieldSmsc;
    public String getFieldSmsc() { return this.fieldSmsc; }
    public void setFieldSmsc(String fieldSmsc) { this.fieldSmsc = fieldSmsc; }

    /**
     * field-timestamp
     * The table field that is used for the timestamp data. 
     */
    private String fieldTimestamp;
    public String getFieldTimestamp() { return this.fieldTimestamp; }
    public void setFieldTimestamp(String fieldTimestamp) { this.fieldTimestamp = fieldTimestamp; }

    /**
     * field-destination
     * The table field that is used for the destination number data. 
     */
    private String fieldDestination;
    public String getFieldDestination() { return this.fieldDestination; }
    public void setFieldDestination(String fieldDestination) { this.fieldDestination = fieldDestination; }

    /**
     * field-source
     * The table field that is used for the source number data. 
     */
    private String fieldSource;
    public String getFieldSource() { return this.fieldSource; }
    public void setFieldSource(String fieldSource) { this.fieldSource = fieldSource; }

    /**
     * field-service
     * The table field that is used for the service username data. 
     */
    private String fieldService;
    public String getFieldService() { return this.fieldService; }
    public void setFieldService(String fieldService) { this.fieldService = fieldService; }

    /**
     * field-url
     * The table field that is used for the DLR URL which is triggered when the DLR for 
     * this message arrives from the SMSC. 
     */
    private String fieldUrl;
    public String getFieldUrl() { return this.fieldUrl; }
    public void setFieldUrl(String fieldUrl) { this.fieldUrl = fieldUrl; }

    /**
     * field-mask
     * The table field that is used for the DLR mask that has been set for a message. 
     */
    private String fieldMask;
    public String getFieldMask() { return this.fieldMask; }
    public void setFieldMask(String fieldMask) { this.fieldMask = fieldMask; }

    /**
     * field-status
     * The table field that is used to reflect the status of the DLR for a specific message. 
     */
    private String fieldStatus;
    public String getFieldStatus() { return this.fieldStatus; }
    public void setFieldStatus(String fieldStatus) { this.fieldStatus = fieldStatus; }

    /**
     * field-boxc-id
     * The table field that is used to store the smsbox connection id that has passed the 
     * message for delivery. This is required in cases you want to guarantee that DLR messages 
     * are routed back to the same smsbox conn instance. This is done via the smsbox routing. 
     * If you don't use smsbox routing simply add this field to your database table and 
     * keep it empty. 
     */
    private String fieldBoxcId;
    public String getFieldBoxcId() { return this.fieldBoxcId; }
    public void setFieldBoxcId(String fieldBoxcId) { this.fieldBoxcId = fieldBoxcId; }

}
