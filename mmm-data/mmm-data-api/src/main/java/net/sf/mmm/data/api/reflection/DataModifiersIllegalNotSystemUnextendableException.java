/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.NlsBundleDataApi;

/**
 * This is the exception thrown if some
 * {@link net.sf.mmm.data.api.reflection.DataClassModifiers} should be created
 * that are NOT
 * {@link net.sf.mmm.data.api.reflection.DataClassModifiers#isSystem() system},
 * NOT {@link net.sf.mmm.data.api.reflection.DataClassModifiers#isFinal() final}
 * and NOT
 * {@link net.sf.mmm.data.api.reflection.DataClassModifiers#isExtendable()
 * extendable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataModifiersIllegalNotSystemUnextendableException extends
    DataModifiersIllegalException {

  /** UID for serialization. */
  private static final long serialVersionUID = -8402419119130916967L;

  /**
   * The constructor.
   */
  public DataModifiersIllegalNotSystemUnextendableException() {

    super(NlsBundleDataApi.ERR_MODIFIERS_USER_UNEXTENDABLE);
  }

}
