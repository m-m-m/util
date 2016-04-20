/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.property.api.factory.PropertyFactoryManager;
import net.sf.mmm.util.property.impl.factory.PropertyFactoryManagerImpl;
import net.sf.mmm.util.reflect.impl.spring.ReflectSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.property}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Configuration
@ComponentScan(basePackageClasses = PropertyFactoryManagerImpl.class)
@Import(ReflectSpringConfig.class)
public class PropertySpringConfig {

  /**
   * @return the {@link PropertyFactoryManager}.
   */
  @Bean
  public PropertyFactoryManager propertyFactoryManager() {

    return new PropertyFactoryManagerImpl();
  }

}
