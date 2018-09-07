/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.math.impl.spring.UtilMathSpringConfig;
import net.sf.mmm.util.validation.base.jsr303.BeanValidationProcessor;
import net.sf.mmm.util.validation.base.jsr303.BeanValidationProcessorImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.validation}.
 *
 * @author hohwille
 * @since 7.4.0
 */
@Configuration
@Import({ UtilMathSpringConfig.class })
@ComponentScan("net.sf.mmm.util.validation.base.jsr303.constraints")
@SuppressWarnings("javadoc")
public class UtilValidationSpringConfig {

  @Bean
  public BeanValidationProcessor beanValidationProcessor() {

    return new BeanValidationProcessorImpl();
  }

}
