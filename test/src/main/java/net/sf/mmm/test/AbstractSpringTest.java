/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * This is the abstract base class for tests using spring infrastructure. Tests derived from this class should specify
 * their configuration using {@link org.springframework.test.context.ContextConfiguration}. E.g.:
 *
 * <pre>
 * {@literal @}{@link org.springframework.test.context.ContextConfiguration}(locations = { "classpath:net/sf/mmm/util/beans-util-core.xml" })
 * </pre>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ WebContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
public abstract class AbstractSpringTest extends Assert {

  /** Spring configuration location for mmm-util-core. */
  public static final String SPRING_CONFIG_UTIL_CORE = "classpath:net/sf/mmm/util/beans-util-core.xml";

  /**
   * The constructor.
   */
  public AbstractSpringTest() {

    super();
  }

}
