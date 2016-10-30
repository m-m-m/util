/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.mmm.util.text.api.JustificationBuilder;
import net.sf.mmm.util.text.api.Singularizer;
import net.sf.mmm.util.text.api.UnicodeUtil;
import net.sf.mmm.util.text.base.EnglishSingularizer;
import net.sf.mmm.util.text.base.JustificationBuilderImpl;
import net.sf.mmm.util.text.base.UnicodeUtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.text}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@SuppressWarnings("javadoc")
public class UtilTextSpringConfigBase {

  @Bean
  public JustificationBuilder justificationBuilder() {

    return new JustificationBuilderImpl();
  }

  @Bean
  public UnicodeUtil unicodeUtil() {

    return new UnicodeUtilImpl();
  }

  @Bean
  public Singularizer singularizer() {

    return EnglishSingularizer.INSTANCE;
  }

}
