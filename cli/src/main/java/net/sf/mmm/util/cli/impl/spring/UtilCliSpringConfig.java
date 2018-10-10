/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.cli.api.CliParserBuilder;
import net.sf.mmm.util.cli.impl.DefaultCliParserBuilder;
import net.sf.mmm.util.collection.impl.spring.UtilCollectionSpringConfig;
import net.sf.mmm.util.pojo.impl.spring.UtilPojoSpringConfig;
import net.sf.mmm.util.reflect.impl.spring.UtilReflectSpringConfig;
import net.sf.mmm.util.text.impl.spring.UtilTextSpringConfig;
import net.sf.mmm.util.value.impl.spring.UtilValueSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.cli}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Configuration
@Import({ UtilReflectSpringConfig.class, UtilPojoSpringConfig.class, UtilValueSpringConfig.class, UtilCollectionSpringConfig.class,
    UtilTextSpringConfig.class })
@SuppressWarnings("javadoc")
public class UtilCliSpringConfig {

  @Bean
  public CliParserBuilder cliParserBuilder() {

    return new DefaultCliParserBuilder();
  }

}
