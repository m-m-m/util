/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import net.sf.mmm.util.exception.api.ExceptionUtil;
import net.sf.mmm.util.exception.api.GlobalExceptionHandler;
import net.sf.mmm.util.exception.base.ExceptionUtilImpl;
import net.sf.mmm.util.exception.base.GlobalExceptionHandlerLoggingImpl;
import net.sf.mmm.util.lang.api.EnvironmentDetector;
import net.sf.mmm.util.lang.base.EnvironmentDetectorSpringProfileImpl;
import net.sf.mmm.util.lang.impl.spring.UtilLangSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.transferobject}.
 *
 * @author hohwille
 * @since 7.4.0
 */
@Configuration
@Import(UtilLangSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilExceptionSpringConfig {

  @Bean
  public ExceptionUtil transferObjectUtil() {

    return new ExceptionUtilImpl();
  }

  @Bean
  public GlobalExceptionHandler globalExceptionHandler() {

    return new GlobalExceptionHandlerLoggingImpl();
  }

  @Bean
  @Lazy
  public EnvironmentDetector environmentDetector() {

    return new EnvironmentDetectorSpringProfileImpl();
  }

}
