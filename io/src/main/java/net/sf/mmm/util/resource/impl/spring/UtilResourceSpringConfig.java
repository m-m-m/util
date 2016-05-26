/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.file.impl.spring.UtilFileSpringConfig;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.spi.DataResourceProvider;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.resource.base.FileResource;
import net.sf.mmm.util.resource.base.UrlResource;
import net.sf.mmm.util.resource.impl.BrowsableResourceFactoryImpl;
import net.sf.mmm.util.resource.impl.spi.ClasspathResourceProvider;
import net.sf.mmm.util.resource.impl.spi.FileResourceProvider;
import net.sf.mmm.util.resource.impl.spi.UrlResourceProvider;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.resource}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import(UtilFileSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilResourceSpringConfig extends UtilResourceSpringConfigBase {

  @Bean
  public BrowsableResourceFactory browsableResourceFactory() {

    return new BrowsableResourceFactoryImpl();
  }

  @Bean
  public DataResourceProvider<ClasspathResource> classpathResourceProvider() {

    return new ClasspathResourceProvider();
  }

  @Bean
  public DataResourceProvider<FileResource> fileResourceProvider() {

    return new FileResourceProvider();
  }

  @Bean
  public DataResourceProvider<UrlResource> urlResourceProvider() {

    return new UrlResourceProvider();
  }

}
