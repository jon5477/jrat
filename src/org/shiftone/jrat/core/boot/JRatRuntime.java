package org.shiftone.jrat.core.boot;

import org.shiftone.jrat.core.boot.config.Configuration;
import org.shiftone.jrat.core.boot.config.ConfigurationParser;
import org.shiftone.jrat.core.boot.config.Settings;
import org.shiftone.jrat.core.JRatException;
import org.shiftone.jrat.util.io.ResourceUtil;
import org.shiftone.jrat.util.io.IOUtil;
import org.shiftone.jrat.util.log.Logger;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * @author Jeff Drost
 */
public class JRatRuntime {
    
    private static final Logger LOG = Logger.getLogger(JRatRuntime.class);
    private static final String DEFAULT = "org/shiftone/jrat/core/boot/default.xml";
    public static final JRatRuntime INSTANCE = new JRatRuntime();
    private Configuration configuration;


    public JRatRuntime() {

        File file = new File("jrat.xml");

        configuration = ConfigurationParser.parse(getConfigurationStream(file));

    }

    private InputStream getConfigurationStream(File file) {
        
        if (!file.exists()) {
            copyDefaultFile(file);
        }
        LOG.info("Loading JRat Configuration : " + file.getAbsolutePath() + "...");
        LOG.info("File was last modified " + new Date(file.lastModified()));
        return IOUtil.openInputStream(file);
    }


    private void copyDefaultFile(File file) {
        LOG.info("initializing configuration file with default...");
        try {
            InputStream defaultStream = ResourceUtil.loadResourceAsStream(DEFAULT);
            OutputStream outputStream = new FileOutputStream(file);
            IOUtil.copy(defaultStream, outputStream);
        } catch (Exception e) {
            throw new JRatException("unable to initialize : " + file.getAbsolutePath(), e);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

     public Settings getSettings() {
        return getConfiguration().getSettings();
    }
}
