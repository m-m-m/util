/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * A {@link UnknownCollectionInterfaceException} is thrown if a {@link java.util.Collection}-interface was
 * given that is unknown or no {@link java.util.Collection}.
 *
 * @see CollectionReflectionUtilImpl#create(Class)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class UnknownCollectionInterfaceException extends RuntimeException implements AttributeReadMessageCode {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "UnknwonCollection";

  /**
   * The constructor.
   *
   * @param collectionInterface is the {@link Class} reflecting the potential {@link java.util.Collection}
   *        -interface.
   */
  public UnknownCollectionInterfaceException(Class<?> collectionInterface) {

    super("Unknown collection interface '" + collectionInterface + "'!");
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
