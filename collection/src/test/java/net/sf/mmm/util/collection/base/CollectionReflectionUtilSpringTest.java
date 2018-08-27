/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.collection.impl.spring.UtilCollectionSpringConfig;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;

/**
 * This is the test-case for {@link CollectionReflectionUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilCollectionSpringConfig.class })
public class CollectionReflectionUtilSpringTest extends CollectionReflectionUtilTest {

  @Inject
  private CollectionReflectionUtil collectionReflectionUtil;

  @Override
  public CollectionReflectionUtil getCollectionReflectionUtil() {

    return this.collectionReflectionUtil;
  }

}
