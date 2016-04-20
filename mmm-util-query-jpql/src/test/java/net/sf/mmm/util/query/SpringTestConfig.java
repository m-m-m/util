/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.sf.mmm.util.bean.impl.spring.BeanSpringConfig;
import net.sf.mmm.util.query.api.statenent.jpql.JpqlStatementFactory;
import net.sf.mmm.util.query.base.statement.jpql.UnquotedJpqlDialect;
import net.sf.mmm.util.query.impl.statement.jpql.JpqlStatementFactoryImpl;

/**
 * Spring {@link Configuration} for the tests.
 *
 * @author hohwille
 */
@Configuration
@Import(BeanSpringConfig.class)
@EntityScan
@SpringBootApplication
public class SpringTestConfig {

  @Bean
  public JpqlStatementFactory statementFactory() {

    return new JpqlStatementFactoryImpl(new UnquotedJpqlDialect());
  }

}
