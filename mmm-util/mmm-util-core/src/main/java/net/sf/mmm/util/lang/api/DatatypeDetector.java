/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface to {@link #isDatatype(Class) detect} if some type is a
 * {@link net.sf.mmm.util.lang.api.Datatype}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@ComponentSpecification
public interface DatatypeDetector {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.lang.api.DatatypeDetector";

  /**
   * This method determines if the given {@link Class} reflects an (immutable)
   * {@link net.sf.mmm.util.lang.api.Datatype}. It will return <code>true</code> if the given
   * <code>value</code> is a {@link String}, {@link Boolean}, {@link Character}, anything derived from
   * {@link Number}, an {@link Enum}, an instance of {@link net.sf.mmm.util.lang.api.Datatype}, a
   * {@link java.util.Date} (even though not immutable) or anything similar.
   *
   * @param type is the {@link Class} to check.
   * @return <code>true</code> if the given {@link Class} represents an (immutable)
   *         {@link net.sf.mmm.util.lang.api.Datatype}, <code>false</code> otherwise.
   */
  boolean isDatatype(Class<?> type);

}
