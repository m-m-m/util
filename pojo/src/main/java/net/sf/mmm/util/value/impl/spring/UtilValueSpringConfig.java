/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.collection.impl.spring.UtilCollectionSpringConfig;
import net.sf.mmm.util.math.impl.spring.UtilMathSpringConfig;
import net.sf.mmm.util.pojo.impl.spring.UtilPojoSpringConfig;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.StringValueConverter;
import net.sf.mmm.util.value.base.StringValueConverterImpl;
import net.sf.mmm.util.value.impl.ComposedValueConverterImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.value}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilMathSpringConfig.class, UtilPojoSpringConfig.class, UtilCollectionSpringConfig.class })
@ComponentScan("net.sf.mmm.util.value.impl")
@SuppressWarnings("javadoc")
public class UtilValueSpringConfig {

  @Bean
  public StringValueConverter stringValueConverter() {

    return new StringValueConverterImpl();
  }

  @Bean
  public ComposedValueConverter composedValueConverter() {

    return new ComposedValueConverterImpl();
  }

}
