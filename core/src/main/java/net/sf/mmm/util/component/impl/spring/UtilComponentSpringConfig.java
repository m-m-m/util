/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.component.api.PeriodicRefresher;
import net.sf.mmm.util.component.impl.PeriodicRefresherImpl;
import net.sf.mmm.util.concurrent.impl.spring.UtilConcurrentSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.component}.
 *
 * @deprecated will be deleted in a future release.
 * @author hohwille
 * @since 7.1.0
 */
@Deprecated
@Configuration
@Import(UtilConcurrentSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilComponentSpringConfig {

  @Bean
  public PeriodicRefresher periodicRefresher() {

    return new PeriodicRefresherImpl();
  }
}
