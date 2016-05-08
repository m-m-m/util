/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.concurrent.impl.spring.UtilConcurrentSpringConfig;
import net.sf.mmm.util.io.api.EncodingUtil;
import net.sf.mmm.util.io.api.StreamUtil;
import net.sf.mmm.util.io.base.EncodingUtilImpl;
import net.sf.mmm.util.io.base.StreamUtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.io}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import(UtilConcurrentSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilIoSpringConfig {

  @Bean
  public EncodingUtil encodingUtil() {

    return new EncodingUtilImpl();
  }

  @Bean
  public StreamUtil streamUtil() {

    return new StreamUtilImpl();
  }

}
