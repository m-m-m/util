/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.lang.impl.spring.UtilLangSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.file}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import(UtilLangSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilFileSpringConfig {

  @Bean
  public FileUtil fileUtil() {

    return new FileUtilImpl();
  }

}
