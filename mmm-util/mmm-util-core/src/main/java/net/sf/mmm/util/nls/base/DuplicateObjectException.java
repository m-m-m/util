/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * An {@link DuplicateObjectException} is thrown if an object was rejected
 * because it is a duplicate. This typically happens if objects are registered
 * (e.g. in a {@link java.util.Map}) and two objects should be associated with
 * the same key.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DuplicateObjectException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -1754790439556739544L;

  /**
   * The constructor.
   * 
   * @param object is the object that was rejected because it is a duplicate.
   */
  public DuplicateObjectException(Object object) {

    super(NlsBundleUtilCore.ERR_DUPLICATE_OBJECT, object);
  }

  /**
   * The constructor.
   * 
   * @param object is the object that was rejected because it is a duplicate.
   * @param key is the key the object could NOT be associated with because it
   *        already leads to another object.
   */
  public DuplicateObjectException(Object object, Object key) {

    super(NlsBundleUtilCore.ERR_DUPLICATE_OBJECT_WITH_KEY, object, key);
  }

}
