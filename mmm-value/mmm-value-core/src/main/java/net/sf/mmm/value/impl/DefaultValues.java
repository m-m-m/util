/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import net.sf.mmm.value.api.GenericValue;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultValues {

  /**
   * The constructor.
   */
  private DefaultValues() {

    super();
  }

  /**
   * The <code>0</code> value as integer.
   */
  public static final GenericValue INTEGER_ZERO = new ImmutableObjectValue(Integer.valueOf(0));

}
