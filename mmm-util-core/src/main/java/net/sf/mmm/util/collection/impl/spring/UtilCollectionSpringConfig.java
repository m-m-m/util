/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.collection.impl.CollectionFactoryManagerImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.reflect}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Configuration
@SuppressWarnings("javadoc")
public class UtilCollectionSpringConfig {

  @Bean
  public CollectionFactoryManager collectionFactoryManager() {

    return new CollectionFactoryManagerImpl();
  }

}
