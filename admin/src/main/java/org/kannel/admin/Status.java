package org.kannel.admin;

import java.io.InputStream;
import org.kannel.admin.xml.Gateway;
import org.kannel.admin.xml.GatewayDocument;

/**
 * Status of kannel, parsed from status.xml admin query.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class Status
{

    public static Status parse(InputStream is) throws Exception
    {
	GatewayDocument doc = GatewayDocument.Factory.parse(is);
	Status s = new Status();
	s.setGateway(doc.getGateway());
	return s;
    }

    private Gateway gateway;
    private void setGateway(Gateway gateway) { this.gateway = gateway; }
    public Gateway getGateway() { return this.gateway; }

}