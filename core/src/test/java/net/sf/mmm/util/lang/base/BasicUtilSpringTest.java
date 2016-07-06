/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.lang.api.BasicUtil;
import net.sf.mmm.util.lang.impl.spring.UtilLangSpringConfig;

/**
 * This is the test-case for {@link BasicUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilLangSpringConfig.class })
public class BasicUtilSpringTest extends BasicUtilTest {

  @Inject
  private BasicUtil basicUtil;

  @Override
  public BasicUtil getBasicUtil() {

    return this.basicUtil;
  }

}
