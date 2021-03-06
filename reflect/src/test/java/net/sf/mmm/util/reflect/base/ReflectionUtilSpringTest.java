/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.impl.spring.UtilReflectSpringConfig;

/**
 * This is the test-case for {@link ReflectionUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilReflectSpringConfig.class })
public class ReflectionUtilSpringTest extends ReflectionUtilTest {

  @Inject
  private ReflectionUtil reflectionUtil;

  @Override
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

}
