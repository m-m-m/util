/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link ReadOnlyException} is thrown if the modification of something failed
 * because it is read-only. Here something can be the property of a java object,
 * an attribute in a persistent store, a file, etc.<br/>
 * <b>ATTENTION:</b><br>
 * Please design your APIs in a way to prevent such exception where ever
 * possible. However for generic access to objects that can be mutable or
 * read-only this exception is the right choice.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public class ReadOnlyException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4118721668441129491L;

  /**
   * The constructor.
   * 
   * @param object is the object that is read-only and can not be modified.
   */
  public ReadOnlyException(Object object) {

    super(NlsBundleUtilCore.ERR_READ_ONLY, toMap(KEY_OBJECT, object));
  }

  /**
   * The constructor.
   * 
   * @param object is the object that is read-only and can not be modified.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public ReadOnlyException(Object object, Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_READ_ONLY, toMap(KEY_OBJECT, object));
  }

}
