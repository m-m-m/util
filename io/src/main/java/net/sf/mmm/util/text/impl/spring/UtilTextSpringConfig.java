/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.nls.impl.spring.UtilNlsSpringConfig;
import net.sf.mmm.util.text.api.HyphenatorBuilder;
import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.base.DefaultLineWrapper;
import net.sf.mmm.util.text.base.HyphenatorBuilderImpl;
import net.sf.mmm.util.value.impl.spring.UtilValueSpringConfig;
import net.sf.mmm.util.xml.impl.spring.UtilXmlSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.text}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilNlsSpringConfig.class, UtilXmlSpringConfig.class, UtilValueSpringConfig.class })
@SuppressWarnings("javadoc")
public class UtilTextSpringConfig extends UtilTextSpringConfigBase {

  @Bean
  public HyphenatorBuilder hyphenatorBuilder() {

    return new HyphenatorBuilderImpl();
  }

  @Bean
  public LineWrapper lineWrapper() {

    return new DefaultLineWrapper();
  }

}
