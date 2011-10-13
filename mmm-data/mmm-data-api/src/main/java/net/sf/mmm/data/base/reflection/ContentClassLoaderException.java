/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import net.sf.mmm.data.NlsBundleDataApi;
import net.sf.mmm.data.api.reflection.DataClassLoader;
import net.sf.mmm.data.api.reflection.DataReflectionException;

/**
 * This exception is thrown by the {@link DataClassLoader} if something went
 * wrong.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentClassLoaderException extends DataReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = 8680994381092858484L;

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param context is
   */
  public ContentClassLoaderException(Throwable nested, String context) {

    super(nested, NlsBundleDataApi.ERR_LOAD_CLASS, context);
  }

}
