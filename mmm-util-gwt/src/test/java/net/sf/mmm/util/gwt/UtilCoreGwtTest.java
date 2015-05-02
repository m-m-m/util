/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt;

import net.sf.mmm.util.lang.api.GenericBean;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorBuilderFactoryLimited;

import org.slf4j.LoggerFactory;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * This is the test-case to test GWT compatibility of <code>mmm-util-core</code>.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class UtilCoreGwtTest extends GWTTestCase {

  /**
   * The constructor.
   *
   */
  public UtilCoreGwtTest() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getModuleName() {

    return "net.sf.mmm.util.gwt.UtilCoreGwtTest";
  }

  public void testLogging() {

    LoggerFactory.getLogger(getClass()).info("Log message from GWT code via SLF4J");
  }

  public void testPojoPropertyIntrospector() {

    PojoDescriptorBuilderFactory result = new PojoDescriptorBuilderFactoryLimited();
    assertSame(result, AbstractPojoDescriptorBuilderFactory.getInstance());
    PojoDescriptorBuilder descriptorBuilder = result.createPublicMethodDescriptorBuilder();
    PojoDescriptor<GwtTestPojo> descriptor = descriptorBuilder.getDescriptor(GwtTestPojo.class);
    PojoPropertyDescriptor propertyDescriptor = descriptor.getPropertyDescriptor(GwtTestPojo.PROPERTY_GENERICBEAN);
    PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
    assertEquals(GenericBean.class, getter.getPropertyType().getRetrievalClass());
  }

}
