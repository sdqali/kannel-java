package org.kannel.conf;

import java.io.File;
import java.util.Collection;

public class App
{

    public static void main(String[] argv) throws Exception
    {
	org.apache.log4j.BasicConfigurator.configure();

	ConfigurationFile cf = new ConfigurationFile(new File(argv[0]));
	cf.load();
	cf.save(new File(argv[1]));

	System.exit(0);
    }

}