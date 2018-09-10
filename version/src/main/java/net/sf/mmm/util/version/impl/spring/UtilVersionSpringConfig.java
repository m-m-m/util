/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.date.impl.spring.UtilDateSpringConfig;
import net.sf.mmm.util.version.api.VersionUtil;
import net.sf.mmm.util.version.impl.VersionUtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.version}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilDateSpringConfig.class })
@SuppressWarnings("javadoc")
public class UtilVersionSpringConfig {

  @Bean
  public VersionUtil cliParserBuilder() {

    return new VersionUtilImpl();
  }

}
