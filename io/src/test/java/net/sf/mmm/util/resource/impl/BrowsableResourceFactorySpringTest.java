/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.impl.spring.UtilResourceSpringConfig;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilResourceSpringConfig.class })
public class BrowsableResourceFactorySpringTest extends BrowsableResourceFactoryTest {

  @Inject
  private BrowsableResourceFactory browsableResourceFactory;

  @Override
  public BrowsableResourceFactory getBrowsableResourceFactory() {

    return this.browsableResourceFactory;
  }

}
