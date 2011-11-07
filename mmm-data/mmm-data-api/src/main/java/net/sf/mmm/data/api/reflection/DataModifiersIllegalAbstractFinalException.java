/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.NlsBundleDataApi;

/**
 * This is the exception thrown if some
 * {@link net.sf.mmm.data.api.reflection.DataClassModifiers} should be created
 * that are both
 * {@link net.sf.mmm.data.api.reflection.DataClassModifiers#isAbstract()
 * abstract} and
 * {@link net.sf.mmm.data.api.reflection.DataClassModifiers#isFinal() final}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataModifiersIllegalAbstractFinalException extends DataModifiersIllegalException {

  /** UID for serialization. */
  private static final long serialVersionUID = -2497368313237159022L;

  /**
   * The constructor.
   */
  public DataModifiersIllegalAbstractFinalException() {

    super(NlsBundleDataApi.ERR_MODIFIERS_ABSTRACT_FINAL);
  }

}
