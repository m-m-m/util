/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.mmm.util.date.api.DurationUtil;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.DurationUtilImpl;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.concurrent}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@SuppressWarnings("javadoc")
public class UtilDateSpringConfig {

  @Bean
  public Iso8601Util iso8601Util() {

    return new Iso8601UtilImpl();
  }

  @Bean
  public DurationUtil durationUtil() {

    return new DurationUtilImpl();
  }

}
