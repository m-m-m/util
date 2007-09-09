/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * This is the exception thrown if a required value was not set.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueNotSetException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = -8722582228766326020L;

  /**
   * The constructor.
   * 
   * @param valueName is the name of the required value that is not set.
   */
  public ValueNotSetException(String valueName) {

    super(NlsBundleUtilCore.ERR_VALUE_NOT_SET, valueName);
  }

}
