/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.impl.BeanFactoryImpl;
import net.sf.mmm.util.property.api.factory.PropertyFactoryManager;
import net.sf.mmm.util.property.impl.spring.PropertySpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Configuration
@Import(PropertySpringConfig.class)
public class BeanSpringConfig {

  /**
   * @return the {@link PropertyFactoryManager}.
   */
  @Bean
  public BeanFactory beanFactory() {

    return new BeanFactoryImpl();
  }

}
