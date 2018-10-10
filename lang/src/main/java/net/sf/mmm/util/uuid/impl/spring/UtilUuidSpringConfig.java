/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.uuid.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.mmm.util.uuid.api.UuidAccess;
import net.sf.mmm.util.uuid.api.UuidFactory;
import net.sf.mmm.util.uuid.base.RandomUuidFactory;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.uuid}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@SuppressWarnings("javadoc")
public class UtilUuidSpringConfig {

  @Bean
  public UuidFactory uuidFactory() {

    RandomUuidFactory uuidFactory = new RandomUuidFactory();
    UuidAccess.setFactory(uuidFactory);
    return uuidFactory;
  }

}
