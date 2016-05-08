/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.process.api.ProcessUtil;
import net.sf.mmm.util.process.impl.spring.UtilProcessSpringConfig;

/**
 * This is the test-case for {@link ProcessUtilImpl} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilProcessSpringConfig.class })
public class ProcessUtilSpringTest extends ProcessUtilTest {

  @Inject
  private ProcessUtil processUtil;

  @Override
  public ProcessUtil getProcessUtil() {

    return this.processUtil;
  }

}
