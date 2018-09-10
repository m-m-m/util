/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.base.MathUtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.math}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@SuppressWarnings("javadoc")
public class UtilMathSpringConfig {

  @Bean
  public MathUtil mathUtil() {

    return new MathUtilImpl();
  }

}
