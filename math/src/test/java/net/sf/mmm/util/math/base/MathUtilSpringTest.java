/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.impl.spring.UtilMathSpringConfig;

/**
 * This is the test-case for {@link MathUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilMathSpringConfig.class })
public class MathUtilSpringTest extends MathUtilTest {

  @Inject
  private MathUtil mathUtil;

  @Override
  public MathUtil getMathUtil() {

    return this.mathUtil;
  }

}
