/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.mmm.util.event.api.EventBus;
import net.sf.mmm.util.event.impl.EventBusImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.event}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@SuppressWarnings("javadoc")
public class UtilEventSpringConfig {

  @Bean
  public EventBus eventBus() {

    return new EventBusImpl();
  }

}
