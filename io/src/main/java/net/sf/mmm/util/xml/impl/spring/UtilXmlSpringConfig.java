/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.resource.impl.spring.UtilResourceSpringConfig;
import net.sf.mmm.util.value.impl.spring.UtilValueSpringConfig;
import net.sf.mmm.util.xml.api.StaxUtil;
import net.sf.mmm.util.xml.base.StaxUtilImpl;

/**
 * This is the extended Spring {@link Configuration} for {@link net.sf.mmm.util.xml}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilValueSpringConfig.class, UtilResourceSpringConfig.class })
@SuppressWarnings("javadoc")
public class UtilXmlSpringConfig extends UtilXmlSpringConfigBase {

  @Bean
  public StaxUtil staxUtil() {

    return new StaxUtilImpl();
  }

}
