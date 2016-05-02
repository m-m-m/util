/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.lang.impl.spring.UtilLangSpringConfig;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;
import net.sf.mmm.util.transferobject.base.TransferObjectUtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.transferobject}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Configuration
@Import(UtilLangSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilTransferobjectSpringConfig {

  @Bean
  public TransferObjectUtil transferObjectUtil() {

    return new TransferObjectUtilImpl();
  }

}
