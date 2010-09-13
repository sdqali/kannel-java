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

	CoreConfiguration core = cf.getCoreConfiguration();
	System.out.println(core.getAdminPort());

	Collection<SmppSmscConfiguration> smpps = cf.getSmppSmscConfigurations();
	for (SmppSmscConfiguration smpp:smpps) {
	    System.out.println(smpp.getHost());
	    System.out.println(smpp.getEnquireLinkInterval());
	}

	cf.save(new File(argv[1]));

    }

}