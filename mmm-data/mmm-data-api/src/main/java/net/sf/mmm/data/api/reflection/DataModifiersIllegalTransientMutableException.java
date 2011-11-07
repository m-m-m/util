/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.NlsBundleDataApi;

/**
 * This is the exception thrown if some
 * {@link net.sf.mmm.data.api.reflection.DataFieldModifiers} should be created
 * that are both
 * {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isTransient()
 * transient} and mutable (NOT
 * {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isReadOnly()
 * read-only}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataModifiersIllegalTransientMutableException extends DataModifiersIllegalException {

  /** UID for serialization. */
  private static final long serialVersionUID = 9096374723351446743L;

  /**
   * The constructor.
   */
  public DataModifiersIllegalTransientMutableException() {

    super(NlsBundleDataApi.ERR_MODIFIERS_TRANSIENT_MUTABLE);
  }

}
