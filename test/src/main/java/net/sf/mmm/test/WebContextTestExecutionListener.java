/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * This is a {@link org.springframework.test.context.TestExecutionListener} that allows testing with spring-beans of the
 * scope <em>request</em> or <em>session</em>.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class WebContextTestExecutionListener extends AbstractTestExecutionListener {

  /**
   * The constructor.
   */
  public WebContextTestExecutionListener() {

    super();
  }

  @Override
  public void prepareTestInstance(TestContext testContext) throws Exception {

    if (testContext.getApplicationContext() instanceof GenericApplicationContext) {
      GenericApplicationContext context = (GenericApplicationContext) testContext.getApplicationContext();
      ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
      Scope requestScope = new SimpleThreadScope();
      beanFactory.registerScope("request", requestScope);
      Scope sessionScope = new SimpleThreadScope();
      beanFactory.registerScope("session", sessionScope);
    }
  }

}
