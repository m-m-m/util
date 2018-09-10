/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.pojo.path.api.PojoPathFunctionManager;
import net.sf.mmm.util.pojo.path.api.PojoPathNavigator;
import net.sf.mmm.util.pojo.path.impl.PojoPathFunctionManagerImpl;
import net.sf.mmm.util.pojo.path.impl.PojoPathNavigatorImpl;
import net.sf.mmm.util.value.impl.spring.UtilValueSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.pojo}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilValueSpringConfig.class })
@ComponentScan({ "net.sf.mmm.util.pojo.path.impl.function" })
@SuppressWarnings("javadoc")
public class UtilPojoPathSpringConfig {

  @Bean
  public PojoPathNavigator pojoPathNavigator() {

    return new PojoPathNavigatorImpl();
  }

  @Bean
  public PojoPathFunctionManager pojoPathFunctionManager() {

    return new PojoPathFunctionManagerImpl();
  }

}
