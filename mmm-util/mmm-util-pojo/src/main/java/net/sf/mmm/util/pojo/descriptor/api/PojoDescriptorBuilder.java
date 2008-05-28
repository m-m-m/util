/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the interface used to {@link #getDescriptor(Class) get} (or create)
 * the {@link PojoDescriptor descriptor} for a given
 * {@link net.sf.mmm.util.pojo.api.Pojo}.<br>
 * This functionality is an advanced alternative to
 * {@link java.beans.Introspector} or <a
 * href="http://commons.apache.org/beanutils">commons-beanutils</a>.
 * 
 * @see PojoDescriptor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoDescriptorBuilder {

  /**
   * This method gets (or creates) the {@link PojoDescriptor descriptor} for the
   * given <code>pojoClass</code>.
   * 
   * @param <POJO> is the templated type of the <code>pojoType</code>.
   * 
   * @param pojoClass is the {@link Class} reflecting the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to introspect.
   * @return the descriptor used to get information about the properties of the
   *         given <code>pojoClass</code>.
   */
  <POJO> PojoDescriptor<POJO> getDescriptor(Class<POJO> pojoClass);

  /**
   * This method gets (or creates) the {@link PojoDescriptor descriptor} for the
   * given <code>pojoType</code>.
   * 
   * @see #getDescriptor(GenericType)
   * 
   * @param pojoType is the {@link Type} reflecting the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to introspect.
   * @return the descriptor used to get information about the properties of the
   *         given <code>pojoType</code>.
   */
  PojoDescriptor<?> getDescriptor(Type pojoType);

  /**
   * This method gets (or creates) the {@link PojoDescriptor descriptor} for the
   * given <code>pojoType</code>.
   * 
   * @param <POJO> is the templated type of the <code>pojoType</code>.
   * 
   * @param pojoType is the {@link GenericType} reflecting the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to introspect.
   * @return the descriptor used to get information about the properties of the
   *         given <code>pojoType</code>.
   */
  <POJO> PojoDescriptor<POJO> getDescriptor(GenericType<POJO> pojoType);

}
