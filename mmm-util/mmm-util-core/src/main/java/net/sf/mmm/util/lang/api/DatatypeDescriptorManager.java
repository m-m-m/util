/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a manager of {@link DatatypeDescriptor}s for all supported {@link Datatype}s.
 * Instead of writing adapters for all kinds of mapping technologies (hibernate, jackson, jaxb, etc.) you can
 * simple create central support via {@link DatatypeDescriptorManager} and use the generic adapters for
 * according mapping technologies provided by this project (see
 * {@link net.sf.mmm.util.lang.base.datatype.adapter}). If you use {@link SimpleDatatype} there is even
 * nothing left to do as your {@link Datatype} is supported out of the box. For composed {@link Datatype}s you
 * can implement your own {@link DatatypeDescriptor} and plugin it into the {@link DatatypeDescriptorManager}
 * via {@link net.sf.mmm.util.component.api.Cdi}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
@ComponentSpecification
public interface DatatypeDescriptorManager {

  /**
   * Gets the {@link DatatypeDescriptor} for the specified <code>datatype</code>.
   *
   * @param <T> is the generic type of the {@link Datatype}.
   * @param datatype is the {@link Class} reflecting the {@link Datatype}.
   * @return the corresponding {@link DatatypeDescriptor}.
   * @throws IllegalArgumentException is the given {@link Class} is not a valid {@link Datatype}.
   */
  <T> DatatypeDescriptor<T> getDatatypeDescriptor(Class<T> datatype) throws IllegalArgumentException;

}
