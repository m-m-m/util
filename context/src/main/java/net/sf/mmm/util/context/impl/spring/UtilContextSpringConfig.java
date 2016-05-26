/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.impl.GenericContextFactoryImpl;
import net.sf.mmm.util.value.impl.spring.UtilValueSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.context}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import(UtilValueSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilContextSpringConfig {

  @Bean
  public GenericContextFactory genericContextFactory() {

    return new GenericContextFactoryImpl();
  }

}
