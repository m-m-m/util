/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.date.impl.spring.UtilDateSpringConfig;
import net.sf.mmm.util.nls.api.NlsBundleFactory;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsMessageFormatterFactory;
import net.sf.mmm.util.nls.api.NlsResourceBundleRequestor;
import net.sf.mmm.util.nls.api.NlsResourceLocator;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.DefaultNlsResourceLocator;
import net.sf.mmm.util.nls.base.NlsArgumentFormatter;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.nls.base.NlsFormatterMap;
import net.sf.mmm.util.nls.base.NlsMessageFactoryImpl;
import net.sf.mmm.util.nls.impl.ConfiguredNlsFormatterMap;
import net.sf.mmm.util.nls.impl.DefaultNlsResourceBundleRequestor;
import net.sf.mmm.util.nls.impl.DefaultNlsTemplateResolver;
import net.sf.mmm.util.nls.impl.NlsBundleFactoryImpl;
import net.sf.mmm.util.nls.impl.NlsDependenciesImpl;
import net.sf.mmm.util.nls.impl.NlsMessageFormatterFactoryImpl;
import net.sf.mmm.util.nls.impl.NlsResourceBundleLocator;
import net.sf.mmm.util.nls.impl.NlsResourceBundleLocatorImpl;
import net.sf.mmm.util.nls.impl.formatter.NlsArgumentFormatterImpl;
import net.sf.mmm.util.resource.impl.spring.UtilResourceSpringConfigBase;
import net.sf.mmm.util.text.impl.spring.UtilTextSpringConfigBase;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.value}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilDateSpringConfig.class, UtilTextSpringConfigBase.class, UtilResourceSpringConfigBase.class })
@ComponentScan("net.sf.mmm.util.nls.impl.formatter")
@SuppressWarnings("javadoc")
public class UtilNlsSpringConfig {

  @Bean
  public NlsMessageFactory nlsMessageFactory() {

    return new NlsMessageFactoryImpl();
  }

  @Bean
  public NlsBundleFactory nlsBundleFactory() {

    return new NlsBundleFactoryImpl();
  }

  @Bean
  public NlsDependencies nlsDependencies() {

    return new NlsDependenciesImpl();
  }

  @Bean
  public NlsTemplateResolver nlsTemplateResolver() {

    return new DefaultNlsTemplateResolver();
  }

  @Bean
  public NlsResourceBundleRequestor nlsResourceBundleRequestor() {

    return new DefaultNlsResourceBundleRequestor();
  }

  @Bean
  public NlsFormatterMap nlsFormatterMap() {

    return new ConfiguredNlsFormatterMap();
  }

  @Bean
  public NlsMessageFormatterFactory nlsMessageFormatterFactory() {

    return new NlsMessageFormatterFactoryImpl();
  }

  @Bean
  public NlsResourceBundleLocator nlsResourceBundleLocator() {

    return new NlsResourceBundleLocatorImpl();
  }

  @Bean
  public NlsResourceLocator nlsResourceLocator() {

    return new DefaultNlsResourceLocator();
  }

  @Bean
  public NlsArgumentFormatter nlsArgumentFormatter() {

    return new NlsArgumentFormatterImpl();
  }

}
