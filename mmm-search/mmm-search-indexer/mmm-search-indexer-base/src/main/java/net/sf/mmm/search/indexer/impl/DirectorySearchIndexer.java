/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.io.FileNotFoundException;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.mmm.search.indexer.api.SearchIndexManager;
import net.sf.mmm.util.cli.AbstractMain;
import net.sf.mmm.util.file.api.FileUtil;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DirectorySearchIndexer extends AbstractMain {

  /** The default configuration XML-file. */
  private static final String DEFAULT_CONFIG_FILE = "~/.mmm/search.xml";

  /** The default spring configuration. */
  private static final String DEFAULT_SPRING_CONFIG = "net/sf/mmm/search/indexer/impl/beans-search-indexer.xml";

  /** The optional filename of the configuration XML-file. */
  @Option(name = "--config", aliases = "-c", required = false, metaVar = "FILE", usage = "The XML-configuration file. Default is "
      + DEFAULT_CONFIG_FILE + ".")
  private String configurationFilename;

  /** The optional context-path for the spring-context. */
  @Option(name = "--spring", aliases = "-s", required = false, metaVar = "FILE", usage = "The optional spring context-path.")
  private String springConfig;

  /** The spring context. */
  private ClassPathXmlApplicationContext springContext;

  /**
   * The constructor.
   */
  public DirectorySearchIndexer() {

    super();
    this.springContext = null;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws FileNotFoundException
   */
  @Override
  protected int run() throws CmdLineException, FileNotFoundException {

    if (this.configurationFilename == null) {
      this.configurationFilename = DEFAULT_CONFIG_FILE;
    }
    if (this.springConfig == null) {
      this.springConfig = DEFAULT_SPRING_CONFIG;
    }
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(this.springConfig);
    try {
      FileUtil fileUtil = (FileUtil) context.getBean(FileUtil.class.getName(), FileUtil.class);
      String configFilename = fileUtil.normalizePath(this.configurationFilename);
      File configFile = new File(configFilename);
      if (!configFile.isFile()) {
        throw new FileNotFoundException(configFilename);
      }
      SearchIndexManager
    } finally {
      context.stop();
      context.close();
    }
    return 0;
  }

  /**
   * This is the main-method of this program.
   * 
   * @param args are the commandline-arguments.
   */
  public static void main(String[] args) {

    int exitCode = new DirectorySearchIndexer().run(args);
    // CHECKSTYLE:OFF
    System.exit(exitCode);
    // CHECKSTYLE:ON
  }

}
