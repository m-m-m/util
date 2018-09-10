/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.impl;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.collection.impl.spring.UtilCollectionSpringConfig;

/**
 * This is the test-case for {@link CollectionFactoryManager} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilCollectionSpringConfig.class })
public class CollectionFactoryManagerImplSpringTest extends CollectionFactoryManagerImplTest {

  @Inject
  private CollectionFactoryManager collectionFactoryManager;

  @Override
  protected CollectionFactoryManager getCollectionFactoryManager() {

    return this.collectionFactoryManager;
  }

}
