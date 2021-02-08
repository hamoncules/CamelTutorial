package ca.yng.camel.file;

import ca.yng.camel.configuration.ConfigurationProperties;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.configuration2.Configuration;

public class FileRouter extends RouteBuilder {

    Configuration config = ConfigurationProperties.getConfiguration();

//    private final String fileName = new String(config.getProperty("ca.yng.camel.file.fileName").toString() + (new Date().getTime()));
    private final String sourceDir = new String(config.getProperty("ca.yng.camel.file.srcDir").toString());
    private final String destinationDir = new String(config.getProperty("ca.yng.camel.file.dstDir").toString());

    @Override
    public void configure() throws Exception {
        //from("file://" + sourceDir + "?delete=true").process(new FileProcessor()).to("file://" + destinationDir);
        from("file://" + sourceDir + "?delete=true").to("file://" + destinationDir);
    }

}