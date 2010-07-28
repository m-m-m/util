/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;

import net.sf.mmm.search.indexer.base.config.SearchIndexerConfigurationBean;
import net.sf.mmm.util.cli.api.AbstractMain;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.file.api.FileUtil;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is the main program for the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer search-indexer}. It allows
 * to do complex indexing of files from arbitrary sources.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class AdvancedSearchIndexer extends AbstractMain {

  /** The default configuration XML-file. */
  private static final String DEFAULT_CONFIGURATION_FILE = "~/.mmm/search.xml";

  /** The default spring configuration. */
  private static final String DEFAULT_SPRING_CONFIGURATION = "net/sf/mmm/search/indexer/impl/beans-search-indexer.xml";

  /** The optional filename of the configuration XML-file. */
  @CliOption(name = "--config", aliases = "-c", required = false, operand = "FILE", usage = "The XML-configuration file. The default is {default}.")
  private String configurationFilename;

  /** The optional context-path for the spring-context. */
  @CliOption(name = "--spring", aliases = "-s", required = false, operand = "FILE", usage = "The optional spring context-path. The default is {default}.")
  private String springConfig;

  /** The spring context. */
  private ClassPathXmlApplicationContext springContext;

  /**
   * The constructor.
   */
  public AdvancedSearchIndexer() {

    super();
    this.springContext = null;
    this.configurationFilename = DEFAULT_CONFIGURATION_FILE;
    this.springConfig = DEFAULT_SPRING_CONFIGURATION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int run(CliModeObject mode) throws Exception {

    ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(
        this.springConfig);
    try {
      FileUtil fileUtil = (FileUtil) this.springContext.getBean(FileUtil.class.getName(),
          FileUtil.class);
      String configFilename = fileUtil.normalizePath(this.configurationFilename);
      File configFile = new File(configFilename);
      if (!configFile.isFile()) {
        throw new FileNotFoundException(configFilename);
      }
      InputStream inStream = new FileInputStream(configFile);
      try {
        JAXBContext jaxbContext = JAXBContext.newInstance(SearchIndexerConfigurationBean.class);
        SearchIndexerConfigurationBean configuration = (SearchIndexerConfigurationBean) jaxbContext
            .createUnmarshaller().unmarshal(inStream);

        // SearchIndexManager
      } finally {
        inStream.close();
      }
    } finally {
      this.springContext.stop();
      this.springContext.close();
    }
    return 0;
  }

  /**
   * This is the main-method of this program.
   * 
   * @param args are the commandline-arguments.
   */
  public static void main(String[] args) {

    int exitCode = new AdvancedSearchIndexer().run(args);
    // CHECKSTYLE:OFF (main method)
    System.exit(exitCode);
    // CHECKSTYLE:ON
  }

}
