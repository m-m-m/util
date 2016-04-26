/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.collection.impl.spring.CollectionSpringConfig;
import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.base.GuessingPojoFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.base.ExtendedPojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorEnhancer;
import net.sf.mmm.util.pojo.descriptor.impl.DefaultPojoDescriptorEnhancer;
import net.sf.mmm.util.pojo.descriptor.impl.ExtendedPojoDescriptorDependenciesImpl;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.pojo}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Configuration
@Import(CollectionSpringConfig.class)
@SuppressWarnings("javadoc")
public class PojoSpringConfig {

  @Bean
  public PojoDescriptorBuilderFactory pojoDescriptorBuilderFactory() {

    return new PojoDescriptorBuilderFactoryImpl();
  }

  @Bean
  public PojoFactory pojoFactory() {

    return new GuessingPojoFactory();
  }

  @Bean
  public ExtendedPojoDescriptorDependencies extendedPojoDescriptorDependencies() {

    return new ExtendedPojoDescriptorDependenciesImpl();
  }

  @Bean
  public PojoDescriptorEnhancer pojoDescriptorEnhancer() {

    return new DefaultPojoDescriptorEnhancer();
  }

}
