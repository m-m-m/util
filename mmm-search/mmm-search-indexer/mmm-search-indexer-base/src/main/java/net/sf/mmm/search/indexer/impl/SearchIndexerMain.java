/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import net.sf.mmm.search.indexer.NlsBundleSearchIndexerApi;
import net.sf.mmm.search.indexer.base.AbstractSearchIndexerMain;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.impl.SpringContainer;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is the main program that triggers the
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer search-indexer}
 * according to a given
 * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration
 * configuration}. It uses springframework as {@link IocContainer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchIndexerMain extends AbstractSearchIndexerMain {

  /** The optional context-path for the spring-context. */
  @CliOption(name = NlsBundleSearchIndexerApi.INT_INDEXER_MAIN_OPTION_NAME_SPRING_XML, //
  required = false, operand = "FILE", //
  usage = NlsBundleSearchIndexerApi.MSG_MAIN_OPTION_USAGE_SPRING_XML)
  private String springConfig;

  /** The optional context-path for the spring-context. */
  @CliOption(name = "--spring-packages", required = false, operand = "PACKAGES", //
  usage = NlsBundleSearchIndexerApi.MSG_MAIN_OPTION_USAGE_SPRING_PACKAGES)
  private String[] springPackages;

  /** The {@link IocContainer}. */
  private IocContainer container;

  /**
   * The constructor.
   */
  public SearchIndexerMain() {

    super();
    this.container = null;
    this.springConfig = null;
    this.springPackages = new String[] { "net.sf.mmm" };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IocContainer getIocContainer() {

    if (this.container == null) {
      getLogger().info("Starting spring context...");
      ConfigurableApplicationContext springContext;
      if (this.springConfig == null) {
        springContext = new AnnotationConfigApplicationContext(this.springPackages);
      } else {
        springContext = new ClassPathXmlApplicationContext(this.springConfig);
      }
      this.container = new SpringContainer(springContext);
      getLogger().info("Spring context started...");
    }
    return this.container;
  }

  /**
   * This is the main-method of this program.
   * 
   * @param args are the commandline-arguments.
   */
  public static void main(String[] args) {

    int exitCode = new SearchIndexerMain().run(args);
    // CHECKSTYLE:OFF (main method)
    System.exit(exitCode);
    // CHECKSTYLE:ON
  }

}
