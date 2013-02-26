/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.NlsBundleDataApi;
import net.sf.mmm.data.api.DataObject;

/**
 * This exception is thrown if a {@link DataObject} could NOT be modified because it is
 * {@link net.sf.mmm.data.api.reflection.DataModifiers#isSystem() required by the system}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataSystemModifyException extends DataReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5661107156513140887L;

  /**
   * The constructor.
   * 
   * @param systemObject is the {@link DataObject} that could NOT be modified because it is a
   *        {@link net.sf.mmm.data.api.reflection.DataModifiers#isSystem() system}-object.
   */
  public DataSystemModifyException(DataObject systemObject) {

    super(NlsBundleDataApi.ERR_MODIFY_SYSTEM, toMap(KEY_OBJECT, systemObject));
  }
}
