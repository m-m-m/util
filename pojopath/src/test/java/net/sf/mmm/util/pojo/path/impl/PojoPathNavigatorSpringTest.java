/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.pojo.path.api.PojoPathNavigator;
import net.sf.mmm.util.pojo.path.api.PojoPathNavigatorTest;
import net.sf.mmm.util.pojo.path.impl.spring.UtilPojoPathSpringConfig;

/**
 * This is the test-case for {@link PojoPathNavigator} using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilPojoPathSpringConfig.class })
public class PojoPathNavigatorSpringTest extends PojoPathNavigatorTest {

  @Inject
  private PojoPathNavigator pojoPathNavigator;

  @Override
  protected PojoPathNavigator createNavigator() {

    return this.pojoPathNavigator;
  }

}
