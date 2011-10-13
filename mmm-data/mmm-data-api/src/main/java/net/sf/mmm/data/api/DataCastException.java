/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.NlsBundleDataApi;

/**
 * This is the exception thrown if a cast failed in the context of
 * {@link net.sf.mmm.data.api.DataObject content}.
 * 
 * @see net.sf.mmm.data.api.DataObject#getValue(String, Class)
 * @see net.sf.mmm.data.api.DataObject#setValue(String, Object)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataCastException extends DataException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4116558423565287160L;

  /**
   * The constructor.
   * 
   * @param source is the type that should be casted.
   * @param destination is the type the <code>source</code> should be casted to.
   */
  public DataCastException(Class<?> source, Class<?> destination) {

    this(source.getName(), destination.getName());
  }

  /**
   * The constructor.
   * 
   * @param source is the type that should be casted.
   * @param destination is the type the <code>source</code> should be casted to.
   */
  public DataCastException(String source, String destination) {

    super(NlsBundleDataApi.ERR_CAST, toMap(KEY_SOURCE, source, KEY_TARGET_TYPE, destination));
  }

  /**
   * The constructor.
   * 
   * @param source is the type that should be casted.
   * @param destination is the type the <code>source</code> should be casted to.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public DataCastException(Class<?> source, Class<?> destination, Throwable nested) {

    this(source.getName(), destination.getName(), nested);
  }

  /**
   * The constructor.
   * 
   * @param source is the type that should be casted.
   * @param destination is the type the <code>source</code> should be casted to.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public DataCastException(String source, String destination, Throwable nested) {

    super(nested, NlsBundleDataApi.ERR_CAST, toMap(KEY_SOURCE, source, KEY_TARGET_TYPE,
        destination));
  }

}
