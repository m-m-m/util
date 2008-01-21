/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.NlsBundleContentCore;
import net.sf.mmm.content.model.api.ContentModelException;

/**
 * This exception is thrown by the {@link ContentClassLoader} if something went
 * wrong.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassLoaderException extends ContentModelException {

  /** UID for serialization. */
  private static final long serialVersionUID = 8680994381092858484L;

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param context is
   */
  public ContentClassLoaderException(Throwable nested, String context) {

    super(nested, NlsBundleContentCore.ERR_LOAD_CLASS, context);
  }

}
