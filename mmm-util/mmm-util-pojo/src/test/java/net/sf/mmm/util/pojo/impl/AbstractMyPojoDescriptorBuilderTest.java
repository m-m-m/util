/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.util.List;

import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.impl.dummy.MyPojo;

/**
 * This is the abstract test-case for
 * {@link net.sf.mmm.util.pojo.api.PojoDescriptorBuilder} implementations using
 * {@link MyPojo}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AbstractMyPojoDescriptorBuilderTest extends AbstractPojoDescriptorBuilderTest {

  protected void checkPojo(PojoDescriptor<MyPojo> pojoDescriptor, MyPojo pojoInstance)
      throws Exception {

    // test property "name"
    checkProperty(pojoDescriptor, "name", String.class, String.class);
    String name = "Emma";
    pojoDescriptor.setProperty(pojoInstance, "name", name);
    String retrievedName = (String) pojoDescriptor.getProperty(pojoInstance, "name");
    assertEquals(name, retrievedName);
    // test property "port"
    // checkProperty(pojoDescriptor, "port", Integer.class, int.class);
    Integer port = Integer.valueOf(4242);
    assertNull(pojoDescriptor.getProperty(pojoInstance, "port"));
    pojoDescriptor.setProperty(pojoInstance, "port", port);
    Integer retrievedPort = (Integer) pojoDescriptor.getProperty(pojoInstance, "port");
    assertEquals(port, retrievedPort);
    // test property "flag"
    boolean flag = true;
    assertNull(pojoDescriptor.getProperty(pojoInstance, "flag"));
    pojoDescriptor.setProperty(pojoInstance, "flag", Boolean.valueOf(flag));
    assertEquals(Boolean.valueOf(flag), pojoDescriptor.getProperty(pojoInstance, "flag"));
    // test property "items"/"item"
    checkProperty(pojoDescriptor, "items", List.class, List.class);
  }

}
