package ca.yng.camel.file;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.RoutesBuilder;
import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import ca.yng.camel.configuration.ConfigurationProperties;

public class FileRouterTest extends CamelTestSupport {

    private static final Logger logger = LogManager.getLogger(FileRouterTest.class.getName());

    Configuration config = ConfigurationProperties.getConfiguration();

    private final String fileName = new String(config.getProperty("ca.yng.camel.file.fileName").toString() + (new Date().getTime()));
    private final String sourceDir = new String(config.getProperty("ca.yng.camel.file.srcDir").toString());
    private final String destinationDir = new String(config.getProperty("ca.yng.camel.file.dstDir").toString());

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        logger.debug("createRouteBuilder : Creating source file");
        this.createFile();
        logger.debug("createRouteBuilder : Source file created");
        return new FileRouter();
    }

    @Before
    public void buildUp()
    {
        logger.info("buildUp : Started Test");
    }

    @Test
    public void checkFileWasCopied() throws InterruptedException
    {
        int wait = 5000;

        logger.debug("checkFileWasCopied : Wait for " + wait + " milliseconds for the process to complete to check file validity");
        Thread.sleep(wait);
        File file = new File(destinationDir + fileName);
        logger.debug("createRouteBuilder : Run file existence check");
        assertEquals(true,file.exists());
    }

    @After
    public void tearDown() throws Exception {
        logger.debug("tearDown : Deleting file from dst folder");
        File file = new File(destinationDir + fileName);
        file.delete();
        logger.debug("tearDown : File deleting from dst folder");
        logger.info("tearDown : Finished Test");
    }

    public void createFile() throws IOException {
        logger.debug("createFile : Creating source dir");
        File srcDir = new File(sourceDir);
        if (!srcDir.exists()){
            srcDir.mkdirs();
        }
        logger.debug("createFile : Source dir created");
        logger.debug("createFile : Creating destination dir");
        File dstDir = new File(destinationDir);
        if (!dstDir.exists()){
            dstDir.mkdirs();
        }
        logger.debug("createFile : Destination dir created");
        logger.debug("createFile : Creating source file");
        File f = new File(sourceDir + fileName);

        f.createNewFile();
        logger.debug("createFile : Source file created");

    }
}
