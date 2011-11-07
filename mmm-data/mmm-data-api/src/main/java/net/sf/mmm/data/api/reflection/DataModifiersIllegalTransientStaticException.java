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
 * transient} and
 * {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isStatic() static}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataModifiersIllegalTransientStaticException extends DataModifiersIllegalException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3486496308842395610L;

  /**
   * The constructor.
   */
  public DataModifiersIllegalTransientStaticException() {

    super(NlsBundleDataApi.ERR_MODIFIERS_TRANSIENT_STATIC);
  }

}
