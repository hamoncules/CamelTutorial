package ca.yng.camel.file;

import ca.yng.camel.configuration.ConfigurationProperties;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileProcessorTest extends CamelTestSupport {

    private static final Logger logger = LogManager.getLogger(FileProcessorTest.class.getName());

    @Before
    public void buildUp()
    {
        logger.info("buildUp : Started Test");
    }

    @After
    public void tearDown()
    {
        logger.info("tearDown : Finished Test");
    }

    @Test
    public void checkFileRenamed() throws InterruptedException
    {
        logger.debug("checkFileRenamed : Started Test");
        CamelContext ctx = new DefaultCamelContext();
        Exchange ex = new DefaultExchange(ctx);
        String sourceFileName = "inputFile";
        logger.debug("checkFileRenamed : Set filename to " + sourceFileName);
        ex.getIn().setHeader(Exchange.FILE_NAME, sourceFileName);

        FileProcessor fp = new FileProcessor();

        logger.debug("checkFileRenamed : Call FileProcessor");
        try {
            fp.process(ex);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("checkFileRenamed : Call FileProcessor " + ex.toString());
        }

        String modifiedFileName = (String) ex.getIn().getHeader(Exchange.FILE_NAME, String.class);

        // Check to see if the data has been prepended to the filename
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String rDate = modifiedFileName.substring(0,19);
        Date date = null;
        boolean validDate = true;
        try {
            date = DateFor.parse(rDate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("checkFileRenamed : Validate Date " + ex.toString());
            validDate = false;
        }

        assertEquals(true,validDate);
        logger.debug("checkFileRenamed : Date is Valid");
        assertEquals(rDate + "-" + sourceFileName, modifiedFileName);
        logger.debug("checkFileRenamed : Filename is Valid");

    }
}
