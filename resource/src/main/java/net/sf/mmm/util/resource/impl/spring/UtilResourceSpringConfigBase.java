/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.reflect.impl.spring.UtilReflectSpringConfig;
import net.sf.mmm.util.resource.api.ClasspathScanner;
import net.sf.mmm.util.resource.impl.ClasspathScannerImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.resource}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import(UtilReflectSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilResourceSpringConfigBase {

  @Bean
  public ClasspathScanner classpathScanner() {

    return new ClasspathScannerImpl();
  }

}
