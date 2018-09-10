/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.impl.spring.UtilPojoSpringConfig;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the test-case for {@link PojoDescriptorBuilder} using combined introspection of private fields and
 * public methods via spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilPojoSpringConfig.class })
public class FieldAndPublicMethodPojoDescriptorBuilderSpringTest extends FieldAndPublicMethodPojoDescriptorBuilderTest {

  @Inject
  private PojoDescriptorBuilderFactory factory;

  @Override
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    return this.factory.createDescriptorBuilder(VisibilityModifier.PUBLIC, VisibilityModifier.PRIVATE);
  }
}
