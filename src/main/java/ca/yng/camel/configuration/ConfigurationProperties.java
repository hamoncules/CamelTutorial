package ca.yng.camel.configuration;


import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * @author Crunchify.com
 *
 */

public class ConfigurationProperties {


    private static  Configurations configs = new Configurations();
    private static Configuration config;

    public static Configuration getConfiguration()
    {

        try {
            config = configs.properties(new File("src/main/resources/config.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }

}