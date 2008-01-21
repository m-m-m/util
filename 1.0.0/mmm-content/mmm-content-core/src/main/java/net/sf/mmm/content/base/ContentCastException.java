/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.base;

import net.sf.mmm.content.NlsBundleContentCore;
import net.sf.mmm.content.api.ContentException;

/**
 * This is the exception thrown if a cast failed in the context of
 * {@link net.sf.mmm.content.api.ContentObject content}.
 * 
 * @see net.sf.mmm.content.api.ContentObject#getValue(String, Class)
 * @see net.sf.mmm.content.api.ContentObject#setValue(String, Object)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentCastException extends ContentException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4116558423565287160L;

  /**
   * The constructor.
   * 
   * @param source is the type that should be casted.
   * @param destination is the type the <code>source</code> should be casted
   *        to.
   */
  public ContentCastException(Class source, Class destination) {

    this(source.getName(), destination.getName());
  }

  /**
   * The constructor.
   * 
   * @param source is the type that should be casted.
   * @param destination is the type the <code>source</code> should be casted
   *        to.
   */
  public ContentCastException(String source, String destination) {

    super(NlsBundleContentCore.ERR_CAST, source, destination);
  }

  /**
   * The constructor.
   * 
   * @param source is the type that should be casted.
   * @param destination is the type the <code>source</code> should be casted
   *        to.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public ContentCastException(Class source, Class destination, Throwable nested) {

    this(source.getName(), destination.getName(), nested);
  }

  /**
   * The constructor.
   * 
   * @param source is the type that should be casted.
   * @param destination is the type the <code>source</code> should be casted
   *        to.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public ContentCastException(String source, String destination, Throwable nested) {

    super(nested, NlsBundleContentCore.ERR_CAST, source, destination);
  }

}
