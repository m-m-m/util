/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.impl.spring.UtilPojoSpringConfig;

/**
 * This is the test-case for {@link PojoFactory} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilPojoSpringConfig.class })
public class PojoFactorySpringTest extends PojoFactoryTest {

  @Inject
  private PojoFactory pojoFactory;

  @Override
  protected PojoFactory getPojoFactory() {

    return this.pojoFactory;
  }

}
