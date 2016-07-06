/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.json.api.JsonUtil;
import net.sf.mmm.util.json.base.JsonUtilImpl;
import net.sf.mmm.util.reflect.impl.spring.UtilReflectSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.value}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilReflectSpringConfig.class })
@ComponentScan("net.sf.mmm.util.value.impl")
@SuppressWarnings("javadoc")
public class UtilJsonSpringConfig {

  @Bean
  public JsonUtil stringValueConverter() {

    return new JsonUtilImpl();
  }

}
