package org.kannel.runtime;

import java.io.File;
import java.util.List;

/**
 * Abstract class for all Box implementations.
 *
 * @author Garth Patil <garthpatil@gmail.com>
 */
public abstract class Box
{

    public abstract void start() throws Exception;
    public abstract void stop() throws Exception;

    protected Process process;

    protected List<String> addArgs(List<String> args) 
    {
	if (verbosity > -1) args.add("-v "+verbosity);
	if (debugPlaces != null) args.add("-D "+debugPlaces);
	if (logFileName != null) args.add("-F "+logFileName);
	if (logVerbosityLevel > -1) args.add("-V "+logVerbosityLevel);
	if (tryHttp) args.add("-H");
	if (user != null) args.add("-u "+user);
	if (pidFileName != null) args.add("-p "+pidFileName);
	if (daemonize) args.add("-d");
	if (parachute) args.add("-P");
	return args;
    }

    protected boolean checkPid()
    {
	try {
	    String pidString = Utils.getFileContent(pidFileName);
	    if (pidString != null && !pidString.trim().equals("")) {
		Process p = Runtime.getRuntime().exec("kill -0 " + pidString);
		if (p.exitValue() == 0) return true;
	    }
	} catch (Exception e) {}
	return false;
    }
    
    protected void killPid()
    {
	try {
	    String pidString = Utils.getFileContent(pidFileName);
	    if (pidString != null && !pidString.trim().equals("")) {
		Process p = Runtime.getRuntime().exec("kill -9 " + pidString);
	    }
	    File pidFile = new File(pidFileName);
	    pidFile.delete();
	} catch (Exception e) {}
    }

    /**
     * Executable path.
     */
    protected String executable;
    public String getExecutable() { return this.executable; }
    public void setExecutable(String executable) { this.executable = executable; }

    /**
     * Path to Kannel configuration file.
     */
    protected String configFileName;
    public String getConfigFileName() { return this.configFileName; }
    public void setConfigFileName(String configFileName) { this.configFileName = configFileName; }

    /**
     * -v <level> Set verbosity level for stdout (screen) logging. Default is 0, which means
     * 'debug'. 1 is 'info, 2 'warning', 3 'error' and 4 'panic'
     * --verbosity <level>
     */
    protected int verbosity = -1;
    public int getVerbosity() { return this.verbosity; }
    public void setVerbosity(int verbosity) { this.verbosity = verbosity; }
    
    /**
     * -D <places> Set debug-places for 'debug' level output.
     * --debug <places>
     */
    protected String debugPlaces;
    public String getDebugPlaces() { return this.debugPlaces; }
    public void setDebugPlaces(String debugPlaces) { this.debugPlaces = debugPlaces; }
    
    /**
     * -F <file-name> Log to file named file-name, too. Does not overrun or affect any log-file
     * defined in configuration file.
     * --logfile <file-name>
     */
    protected String logFileName;
    public String getLogFileName() { return this.logFileName; }
    public void setLogFileName(String logFileName) { this.logFileName = logFileName; }

    /**
     * -V <level> Set verbosity level for that extra log-file (default 0, which means 'debug').
     * Does not affect verbosity level of the log-file defined in configuration file, not
     * verbosity level of the stdout output.
     * --fileverbosity <level>
     */
    protected int logVerbosityLevel = -1;
    public int getLogVerbosityLevel() { return this.logVerbosityLevel; }
    public void setLogVerbosityLevel(int logVerbosityLevel) { this.logVerbosityLevel = logVerbosityLevel; }

    /**
     * -H Only try to open HTTP sendsms interface; if it fails, only warn about that, do
     * not exit. (smsbox only)
     * --tryhttp
     */
    protected boolean tryHttp;
    public boolean getTryHttp() { return this.tryHttp; }
    public void setTryHttp(boolean tryHttp) { this.tryHttp = tryHttp; }
    
    /**
     * -u <username> Change process user-id to the given.
     * --user <username>
     */
    protected String user;
    public String getUser() { return this.user; }
    public void setUser(String user) { this.user = user; }
    
    /**
     * -p <filename> Write process PID to the given file.
     * --pid-file <filename>
     */
    protected String pidFileName;
    public String getPidFileName() { return this.pidFileName; }
    public void setPidFileName(String pidFileName) { this.pidFileName = pidFileName; }
    
    /**
     * -d Start process as daemon (detached from a current shell session). Note: Process
     * will change CWD (Current working directory) to /, therefore you should ensure that
     * all paths to binary/config/config-includes are absolute instead of relative.
     * --daemonize
     */
    protected boolean daemonize;
    public boolean getDaemonize() { return this.daemonize; }
    public void setDaemonize(boolean daemonize) { this.daemonize = daemonize; }

    /**
     * -P Start watcher process. This process watch a child process and if child process
     * crashed will restart them automatically.
     * --parachute
     */
    protected boolean parachute;
    public boolean getParachute() { return this.parachute; }
    public void setParachute(boolean parachute) { this.parachute = parachute; }

}