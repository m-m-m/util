/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the interface for a factory used to create instances of {@link PojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@ComponentSpecification
public interface PojoDescriptorBuilderFactory {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory";

  /**
   * This method creates a {@link PojoDescriptorBuilder} that introspects public methods for building
   * {@link PojoPropertyDescriptor}s. All other methods and all fields will be ignored. This is the preferred
   * way to use following the java-bean philosophy.
   * 
   * @return the new {@link PojoDescriptorBuilder} instance.
   */
  PojoDescriptorBuilder createPublicMethodDescriptorBuilder();

  /**
   * This method creates a {@link PojoDescriptorBuilder} that introspects all non-static fields for building
   * {@link PojoPropertyDescriptor}s. All methods will be ignored.
   * 
   * @see #createPublicMethodDescriptorBuilder()
   * 
   * @return the new {@link PojoDescriptorBuilder} instance.
   */
  PojoDescriptorBuilder createPrivateFieldDescriptorBuilder();

  /**
   * This method creates a {@link PojoDescriptorBuilder} that introspects the methods and non-static fields
   * with the given {@link VisibilityModifier visibility} or a higher {@link VisibilityModifier#getOrder()
   * order}.
   * 
   * @see #createPublicMethodDescriptorBuilder()
   * 
   * @param methodVisibility is the minimum {@link VisibilityModifier visibility} of the methods to introspect
   *        or <code>null</code> to ignore all methods.
   * @param fieldVisibility is the minimum {@link VisibilityModifier visibility} of the fields to introspect
   *        or <code>null</code> to ignore all fields.
   * @return the new {@link PojoDescriptorBuilder} instance.
   */
  PojoDescriptorBuilder createDescriptorBuilder(VisibilityModifier methodVisibility, VisibilityModifier fieldVisibility);

}
