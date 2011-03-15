# kannel-java-conf #

Beans and loaders for the Kannel configuration file: <http://kannel.org/download/kannel-userguide-snapshot/userguide.html>. This package gives you a mechanism to programmatically read and write Kannel configuration files, and creates beans for each configuration type. Warning: not all configuration "group"s are supported at this time, and Kannel's "include" functionality doesn't work. Most of the configuration bean code is generated using a Groovy script in src/main/resources/genConfig.groovy using a cleaned up version of the Kannel documentation in src/main/resources/*.txt.

Usage:

    // Load a kannel.conf
    ConfigurationFile cf = new ConfigurationFile(new File("kannel.conf"));
    cf.load();

    // Change a parameter
    CoreConfiguration core = cf.getCoreConfiguration();
    core.setLogLevel(1);

    // Save the new configuration
    cf.save(new File("kannel_new.conf"));
