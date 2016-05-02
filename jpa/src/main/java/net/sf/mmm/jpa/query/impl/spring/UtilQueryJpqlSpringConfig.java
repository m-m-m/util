/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.jpa.query.impl.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.jpa.query.api.statenent.jpql.JpqlStatementFactory;
import net.sf.mmm.jpa.query.impl.statement.jpql.JpqlStatementFactoryImpl;
import net.sf.mmm.util.bean.impl.spring.UtilBeanSpringConfig;

/**
 * This is the Spring {@link Configuration} for {@link net.sf.mmm.util.pojo}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Configuration
@Import(UtilBeanSpringConfig.class)
@SuppressWarnings("javadoc")
public class UtilQueryJpqlSpringConfig {

  @Bean
  public JpqlStatementFactory statementFactory() {

    return new JpqlStatementFactoryImpl();
  }

}
