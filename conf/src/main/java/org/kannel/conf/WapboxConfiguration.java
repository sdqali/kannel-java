package org.kannel.conf;

import java.net.URL;

/**
 * wapbox configuration
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public class WapboxConfiguration
    extends Configuration
{

    public WapboxConfiguration()
    {
	super("wapbox");
    }

    private static final String[] PROP_ORDER = { "bearerbox-host","timer-freq","http-interface-name","map-url","map-url-max","map-url-0","device-home","log-file","log-level","syslog-level","access-log","access-log-clean","access-log-time","smart-errors","wml-strict","http-timeout" };

    public String[] getPropertyOrder() {
	return PROP_ORDER;
    }

    private static final String[] MANDATORY_PROPS = { "bearerbox-host" };

    public String[] getMandatoryProps() {
	return MANDATORY_PROPS;
    }

    /**
     * bearerbox-host
     * The machine in which the bearerbox is. 
     */
    private String bearerboxHost;
    public String getBearerboxHost() { return this.bearerboxHost; }
    public void setBearerboxHost(String bearerboxHost) { this.bearerboxHost = bearerboxHost; }

    /**
     * timer-freq
     * The frequency of how often timers are checked out. Default is 1 
     */
    private Integer timerFreq;
    public Integer getTimerFreq() { return this.timerFreq; }
    public void setTimerFreq(Integer timerFreq) { this.timerFreq = timerFreq; }

    /**
     * http-interface-name
     * If this is set then Kannel do outgoing HTTP requests using this interface. 
     */
    private String httpInterfaceName;
    public String getHttpInterfaceName() { return this.httpInterfaceName; }
    public void setHttpInterfaceName(String httpInterfaceName) { this.httpInterfaceName = httpInterfaceName; }

    /**
     * map-url
     * The pair is separated with space. Adds a single mapping for the left side URL to 
     * the given destination. If you append an asterisk `*' to the left side URL, its prefix 
     * Is matched against the incoming URL. Whenever the prefix matches, the URL will be 
     * replaced completely by the right side. In addition, if if you append an asterisk 
     * to the right side URL, the part of the incoming URL coming after the prefix, will 
     * be appended to the right side URL. Thus, for a line: map-url = "http://source/* 
     * http://destination/*" and an incoming URL of "http://source/some/path", the result 
     * will be "http://destination/some/path" 
     */
    private String[] mapUrl;
    public String[] getMapUrl() { return this.mapUrl; }
    public void setMapUrl(String[] mapUrl) { this.mapUrl = mapUrl; }

    /**
     * map-url-max
     * If you need more than one mapping, set this to the highest number mapping you need. 
     * The default gives you 10 mappings, numbered from 0 to 9. Default: 9 
     */
    private Integer mapUrlMax;
    public Integer getMapUrlMax() { return this.mapUrlMax; }
    public void setMapUrlMax(Integer mapUrlMax) { this.mapUrlMax = mapUrlMax; }

    /**
     * map-url-0
     * Adds a mapping for the left side URL to the given destination URL. Repeat these 
     * lines, with 0 replaced by a number up to map-url-max, if you need several mappings. 
     */
    private String[] mapUrl0;
    public String[] getMapUrl0() { return this.mapUrl0; }
    public void setMapUrl0(String[] mapUrl0) { this.mapUrl0 = mapUrl0; }

    /**
     * device-home
     * Adds a mapping for the URL DEVICE:home (as sent by Phone.com browsers) to the given 
     * destination URL. There is no default mapping. NOTE: the mapping is added with both 
     * asterisks, as described above for the "map-url" setting. Thus, the above example 
     * line is equivalent to writing map-url = "DEVICE:home* http://some.where/*" 
     */
    private URL deviceHome;
    public URL getDeviceHome() { return this.deviceHome; }
    public void setDeviceHome(URL deviceHome) { this.deviceHome = deviceHome; }

    /**
     * log-file
     * As with bearerbox 'core' group. 
     */
    private String logFile;
    public String getLogFile() { return this.logFile; }
    public void setLogFile(String logFile) { this.logFile = logFile; }

    /**
     * log-level
     *  
     */
    private Integer logLevel;
    public Integer getLogLevel() { return this.logLevel; }
    public void setLogLevel(Integer logLevel) { this.logLevel = logLevel; }

    /**
     * syslog-level
     * Messages of this log level or higher will also be sent to syslog, the UNIX system 
     * log daemon. The wapbox logs under the 'daemon' category. The default is not to use 
     * syslog, and you can set that explicitly by setting syslog-level to 'none'. 
     */
    private Integer syslogLevel;
    public Integer getSyslogLevel() { return this.syslogLevel; }
    public void setSyslogLevel(Integer syslogLevel) { this.syslogLevel = syslogLevel; }

    /**
     * access-log
     * A file containing all requested URLs from clients while wapbox operation, including 
     * client IP, HTTP server User-Agent string, HTTP response code, size of reply. 
     */
    private String accessLog;
    public String getAccessLog() { return this.accessLog; }
    public void setAccessLog(String accessLog) { this.accessLog = accessLog; }

    /**
     * access-log-clean
     * Indicates if access-log will contain standard 'markers', which means the 'Log begins', 
     * 'Log ends' markers and the prefixed timestamp. This config directive should be set 
     * to 'true' if a custom logging format is desired without a prefixed default timestamp. 
     */
    private Boolean accessLogClean;
    public Boolean getAccessLogClean() { return this.accessLogClean; }
    public void setAccessLogClean(Boolean accessLogClean) { this.accessLogClean = accessLogClean; }

    /**
     * access-log-time
     * Determine which timezone we use for access logging. Use 'gmt' for GMT time, anything 
     * else will use local time. Default is local time. 
     */
    private String accessLogTime;
    public String getAccessLogTime() { return this.accessLogTime; }
    public void setAccessLogTime(String accessLogTime) { this.accessLogTime = accessLogTime; }

    /**
     * smart-errors
     * If set wapbox will return a valid WML deck describing the error that occurred while 
     * processing an WSP request. This may be used to have a smarter gateway and let the 
     * user know what happened actually. 
     */
    private Boolean smartErrors;
    public Boolean getSmartErrors() { return this.smartErrors; }
    public void setSmartErrors(Boolean smartErrors) { this.smartErrors = smartErrors; }

    /**
     * wml-strict
     * If set wapbox will use a strict policy in XML parsing the WML deck. If not set it 
     * will be more relaxing and let the XML parser recover from parsing errors. This has 
     * mainly impacts in how smart the WML deck and it's character set encoding can be 
     * adopted, even while you used an encoding that does not fit the XML preamble. BEWARE: 
     * This may be a vulnerability in your wapbox for large bogus WML input. Therefore 
     * this defaults to 'yes', strict parsing. is assumed. 
     */
    private Boolean wmlStrict;
    public Boolean getWmlStrict() { return this.wmlStrict; }
    public void setWmlStrict(Boolean wmlStrict) { this.wmlStrict = wmlStrict; }

    /**
     * http-timeout
     * Sets socket timeout in seconds for outgoing client http connections. Optional. Defaults 
     * to 240 seconds. 
     */
    private Integer httpTimeout;
    public Integer getHttpTimeout() { return this.httpTimeout; }
    public void setHttpTimeout(Integer httpTimeout) { this.httpTimeout = httpTimeout; }

}
