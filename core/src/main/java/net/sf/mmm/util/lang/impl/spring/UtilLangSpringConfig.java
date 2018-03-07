/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.lang.api.EnvironmentDetector;
import net.sf.mmm.util.lang.base.DatatypeDetectorImpl;
import net.sf.mmm.util.lang.base.EnvironmentDetectorSpringProfileImpl;
import net.sf.mmm.util.nls.impl.spring.UtilNlsSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.lang}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilNlsSpringConfig.class, UtilLangSpringConfigBase.class })
@ComponentScan("net.sf.mmm.util.lang.base.datatype.descriptor")
@SuppressWarnings("javadoc")
public class UtilLangSpringConfig {

  @Bean
  public DatatypeDetector datatypeDetector() {

    return new DatatypeDetectorImpl();
  }

  @Bean
  public EnvironmentDetector environmentDetector() {

    return new EnvironmentDetectorSpringProfileImpl();
  }

}
