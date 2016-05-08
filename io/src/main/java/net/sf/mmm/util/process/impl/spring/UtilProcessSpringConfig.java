/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.io.impl.spring.UtilIoSpringConfig;
import net.sf.mmm.util.process.api.ProcessUtil;
import net.sf.mmm.util.process.base.ProcessUtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.process}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import(UtilIoSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilProcessSpringConfig {

  @Bean
  public ProcessUtil processUtil() {

    return new ProcessUtilImpl();
  }

}
