/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.concurrent.impl.spring;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import net.sf.mmm.util.concurrent.base.CachedThreadPoolExecutor;
import net.sf.mmm.util.concurrent.base.SimpleExecutor;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.concurrent}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@SuppressWarnings("javadoc")
public class UtilConcurrentSpringConfig {

  @Profile("!server")
  @Bean
  public Executor simpleExecutor() {

    return new SimpleExecutor();
  }

  @Profile("server")
  @Bean
  public Executor threadPoolExecutor() {

    return new CachedThreadPoolExecutor();
  }

}
