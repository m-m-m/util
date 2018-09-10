/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.mmm.util.xml.api.DomUtil;
import net.sf.mmm.util.xml.api.XmlUtil;
import net.sf.mmm.util.xml.base.DomUtilImpl;
import net.sf.mmm.util.xml.base.XmlUtilImpl;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.xml}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@SuppressWarnings("javadoc")
public class UtilXmlSpringConfigBase {

  @Bean
  public DomUtil domUtil() {

    return new DomUtilImpl();
  }

  @Bean
  public XmlUtil xmlUtil() {

    return new XmlUtilImpl();
  }

}
