/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.collection.impl.spring.UtilCollectionSpringConfig;
import net.sf.mmm.util.nls.impl.spring.UtilNlsSpringConfig;
import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.api.PojoUtil;
import net.sf.mmm.util.pojo.base.GuessingPojoFactory;
import net.sf.mmm.util.pojo.base.PojoUtilImpl;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.base.ExtendedPojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorEnhancer;
import net.sf.mmm.util.pojo.descriptor.impl.DefaultPojoDescriptorEnhancer;
import net.sf.mmm.util.pojo.descriptor.impl.ExtendedPojoDescriptorDependenciesImpl;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.reflect.impl.spring.UtilReflectSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.pojo}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilReflectSpringConfig.class, UtilCollectionSpringConfig.class, UtilNlsSpringConfig.class })
@ComponentScan({ "net.sf.mmm.util.pojo.descriptor.impl.accessor" })
@SuppressWarnings("javadoc")
public class UtilPojoSpringConfig {

  @Bean
  public PojoUtil pojoUtil() {

    return new PojoUtilImpl();
  }

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
