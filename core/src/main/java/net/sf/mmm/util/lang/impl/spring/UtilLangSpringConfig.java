/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.lang.api.BasicUtil;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.lang.api.EnumProvider;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.api.SystemUtil;
import net.sf.mmm.util.lang.base.BasicUtilImpl;
import net.sf.mmm.util.lang.base.DatatypeDetectorImpl;
import net.sf.mmm.util.lang.base.SimpleEnumProvider;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.lang.base.SystemUtilImpl;
import net.sf.mmm.util.lang.base.datatype.descriptor.DatatypeDescriptorManagerImpl;
import net.sf.mmm.util.reflect.impl.spring.UtilReflectSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.lang}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Configuration
@Import(UtilReflectSpringConfig.class)
@ComponentScan("net.sf.mmm.util.lang.base.datatype.descriptor")
@SuppressWarnings("javadoc")
public class UtilLangSpringConfig {

  @Bean
  public BasicUtil basicUtil() {

    return new BasicUtilImpl();
  }

  @Bean
  public StringUtil stringUtil() {

    return new StringUtilImpl();
  }

  @Bean
  public SystemUtil systemUtil() {

    return new SystemUtilImpl();
  }

  @Bean
  public DatatypeDetector datatypeDetector() {

    return new DatatypeDetectorImpl();
  }

  @Bean
  public DatatypeDescriptorManager datatypeDescriptorManager() {

    return new DatatypeDescriptorManagerImpl();
  }

  @Bean
  public EnumProvider enumProvider() {

    return new SimpleEnumProvider();
  }

}
