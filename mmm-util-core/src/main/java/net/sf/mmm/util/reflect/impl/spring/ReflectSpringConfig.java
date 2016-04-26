/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.collection.impl.spring.CollectionSpringConfig;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.reflect}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Configuration
@Import(CollectionSpringConfig.class)
@SuppressWarnings("javadoc")
public class ReflectSpringConfig {

  @Bean
  public ReflectionUtil reflectionUtil() {

    return new ReflectionUtilImpl();
  }

  @Bean
  public CollectionReflectionUtil collectionReflectionUtil() {

    return new CollectionReflectionUtilImpl();
  }

}
