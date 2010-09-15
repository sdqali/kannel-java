package org.kannel.admin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Implementation of KannelAdmin using Kannel's HTTP administration interface
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class HttpKannelAdmin
    implements KannelAdmin
{

    /**
     * @param baseUrl The base URL of the Kannel HTTP administration interface
     * @param password The admin-password 
     */    
    public HttpKannelAdmin(URL baseUrl, String password) {
	this.baseUrl = baseUrl;
	this.password = password;
    }
	
    private URL baseUrl;
    private String password;	

    private URL getUrl(String method, boolean requiresPassword) throws Exception
    {
	if (requiresPassword) method = addPassword(method);
	return this.baseUrl.toURI().resolve(method).toURL();
    }

    private String addPassword(String method)
    {
	StringBuffer o = new StringBuffer(method);
	if (!method.contains("?")) o.append("?").append("password=").append(this.password);
	else o.append("&").append("password=").append(this.password);
	return o.toString();
    }

    /**
     * status
     * Get the current status of the gateway in a text version. Tells the current state
     * (see above) and total number of messages relied and queuing in the system right now.
     * Also lists the total number of smsbox and wapbox connections. No password required,
     * unless status-password set, in which case either that or main admin password must be
     * supplied.
     * @return A Status containing conveniences to access the XML.
     */
    public Status getStatus() throws AdminException
    {
	try {
 	    InputStream is = getUrl("status.xml", true).openStream();
 	    Status s = Status.parse(is);
 	    is.close();
	    return s;
	} catch (Exception e) {
	    throw new AdminException("Error executing status", e);
	}
    }

    /**
     * store-status
     * Get the current content of the store queue of the gateway in a text version. No
     * password required, unless status-password set, in which case either that or main
     * admin password must be supplied.
     * @return A StoreStatus containing conveniences to access the XML.
     */
    public StoreStatus getStoreStatus() throws AdminException
    {
	try {
	    InputStream is = getUrl("store-status.xml", true).openStream();
	    StoreStatus s = StoreStatus.parse(is);
	    is.close();
	    return s;
	} catch (Exception e) {
	    throw new AdminException("Error executing store-status", e);
	}
    }

    /**
     * suspend
     * Set Kannel state as 'suspended' (see above). Password required.
     */
    public void suspend() throws AdminException
    {
	try {
	    String response = getContent(getUrl("suspend", true));
	    if (response.contains("Already susptended")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing suspend", e);
	}
    }

    /**
     * isolate
     * Set Kannel state as 'isolated' (see above). Password required.
     */
    public void isolate() throws AdminException
    {
	try {
	    String response = getContent(getUrl("isolate", true));
	    if (response.contains("Already isolated")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing isolate", e);
	}
    }

    /**
     * resume
     * Set Kannel state as 'running' if it is suspended or isolated. Password required.
     */
    public void resume() throws AdminException
    {
	try {
	    String response = getContent(getUrl("resume", true));
	    if (response.contains("Already running")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing resume", e);
	}
    }

    /**
     * shutdown
     * Bring down the gateway, by setting state to 'shutdown'. After a shutdown is initiated,
     * there is no other chance to resume normal operation. However, 'status' command still
     * works. Password required. If shutdown is sent for a second time, the gateway is forced
     * down, even if it has still messages in queue.
     */
    public void shutdown() throws AdminException
    {
	try {
	    String response = getContent(getUrl("shutdown", true));
	} catch (Exception e) {
	    throw new AdminException("Error executing shutdown", e);
	}
    }

    /**
     * flush-dlr
     * If Kannel state is 'suspended' this will flush all queued DLR messages in the current
     * storage space. Password required.
     */
    public void flushDLR() throws AdminException
    {
	try {
	    String response = getContent(getUrl("flush-dlr", true));
	    if (response.contains("before trying to flush DLR queue")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing flush-dlr", e);
	}
    }

    /**
     * start-smsc
     * Re-start a single SMSC link. Password required. Additionally the smsc parameter must
     * be given to identify which smsc-admin-id should be re-started. The configuration
     * for the SMSC link is re-read from disk before the action is performed.
     * @param smsc The smsc-id
     */
    public void startSMSC(String smsc) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("start-smsc");
	    query.append("?").append("smsc=").append(smsc);
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("Could not re-start smsc-id") ||
		response.contains("SMSC id not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing start-smsc", e);
	}
    }

    /**
     * stop-smsc
     * Shutdown a single SMSC link. Password required. Additionally the smsc parameter must
     * be given (see above).
     * @param smsc The smsc-id
     */
    public void stopSMSC(String smsc) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("stop-smsc");
	    query.append("?").append("smsc=").append(smsc);
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("Could not shut down smsc-id") ||
		response.contains("SMSC id not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing stop-smsc", e);
	}
    }

    /**
     * add-smsc
     * Adds an SMSC link previously removed or created after the service was started. Password
     * required. Additionally the smsc parameter must be given (see above).
     * @param smsc The smsc-id
     */
    public void addSMSC(String smsc) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("add-smsc");
	    query.append("?").append("smsc=").append(smsc);
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("Could not add smsc-id") ||
		response.contains("SMSC id not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing add-smsc", e);
	}
    }

    /**
     * remove-smsc
     * Removes an existing SMSC link. Password required. Additionally the smsc parameter
     * must be given (see above). If you want a permanent removal, you should also remove
     * the entry from the configuration file or it will be recreated after a service restart.
     * @param smsc The smsc-id
     */
    public void removeSMSC(String smsc) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("remove-smsc");
	    query.append("?").append("smsc=").append(smsc);
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("Could not remove smsc-id") ||
		response.contains("SMSC id not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing remove-smsc", e);
	}
    }

    /**
     * restart
     * Re-start whole bearerbox, hence all SMSC links. Password required. Beware that you
     * loose the smsbox connections in such a case.
     */
    public void restart() throws AdminException
    {
	try {
	    String response = getContent(getUrl("restart", true));
	    if (response.contains("Trying harder to restart")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing restart", e);
	}
    }

    /**
     * loglevel
     * Set Kannel log-level of log-files while running. This allows you to change the current
     * log-level of the log-files on the fly.
     * @param logLevel The new log level to set
     */
    public void logLevel(int logLevel) throws AdminException
    {
	try {
	    StringBuffer query = new StringBuffer("log-level");
	    query.append("?").append("level=").append(Integer.toString(logLevel));
	    String response = getContent(getUrl(query.toString(), true));
	    if (response.contains("New level not given")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing log-level", e);
	}
    }    

    /**
     * reload-lists
     * Re-loads the 'white-list' and 'black-list' URLs provided in the core group. This
     * allows Kannel to keep running while the remote lists change and signal bearerbox
     * to re-load them on the fly. 
     */
    public void reloadLists() throws AdminException
    {
	try {
	    String response = getContent(getUrl("reload-lists", true));
	    if (response.contains("Could not re-load lists")) throw new AdminException(response.trim());
	} catch (Exception e) {
	    throw new AdminException("Error executing reload-lists", e);
	}
    }

    private static String getContent(URL u) throws IOException
    {
	// InputStream is = u.openStream();
	// Need to set HTTP accept header to text only. Java's default includes HTML, which
	// causes Kannel to auto-detect and send HTML.
	URLConnection uc = u.openConnection();
	uc.setRequestProperty("Accept", "text/plain");
	uc.connect();
	InputStream is = uc.getInputStream();
	String response = parseStreamToString(is, -1);
	is.close();
	return response;
    }

    private static String parseStreamToString(InputStream is, int maxSize) throws IOException
    {
	BufferedReader bf = new BufferedReader(new InputStreamReader(is));
	StringBuffer o = new StringBuffer();
	String line = null;
	while ((line = bf.readLine()) != null && ((maxSize < 0) || (o.length() < maxSize))) {
	    o.append(line + "\n");
	}
	bf.close();
	return o.toString();
    }

    /**
     * A command line client for testing.
     * usage: HttpKannelAdmin <host> <password> <method> <opt:param>
     */
    public static void main(String[] argv)
    {
	org.apache.log4j.BasicConfigurator.configure();
	if (argv.length < 3) {
	    System.err.println("usage: HttpKannelAdmin <host> <password> <method> <opt:param>");
	    System.exit(1);
	}
	try {
	    KannelAdmin admin = new HttpKannelAdmin(new URL(argv[0]), argv[1]);
	    String method = argv[2];
	    String param = null;
	    if (argv.length == 4) param = argv[3];
	    if (method.equals("status")) System.out.println(admin.getStatus().getGateway().xmlText());
	    if (method.equals("store-status")) System.out.println(admin.getStoreStatus());
	    if (method.equals("suspend")) admin.suspend();
	    if (method.equals("isolate")) admin.isolate();
	    if (method.equals("resume")) admin.resume();
	    if (method.equals("shutdown")) admin.shutdown();
	    if (method.equals("flush-dlr")) admin.flushDLR();
	    if (method.equals("start-smsc")) admin.startSMSC(param);
	    if (method.equals("stop-smsc")) admin.stopSMSC(param);
	    if (method.equals("add-smsc")) admin.addSMSC(param);
	    if (method.equals("remove-smsc")) admin.removeSMSC(param);
	    if (method.equals("restart")) admin.restart();
	    if (method.equals("log-level")) admin.logLevel(Integer.parseInt(param));
	    if (method.equals("reload-lists")) admin.reloadLists();
	    System.exit(0);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
