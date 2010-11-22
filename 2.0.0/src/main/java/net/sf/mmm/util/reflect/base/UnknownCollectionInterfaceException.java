/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * A {@link UnknownCollectionInterfaceException} is thrown if a
 * {@link java.util.Collection}-interface was given that is unknown or no
 * {@link java.util.Collection}.
 * 
 * @see CollectionReflectionUtilImpl#create(Class)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class UnknownCollectionInterfaceException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1094581788015698338L;

  /**
   * The constructor.
   * 
   * @param collectionInterface is the {@link Class} reflecting the potential
   *        {@link java.util.Collection}-interface.
   */
  public UnknownCollectionInterfaceException(Class<?> collectionInterface) {

    super(NlsBundleUtilCore.ERR_UNKNOWN_COLLECTION_INTERFACE, toMap(KEY_TYPE, collectionInterface));
  }

}
