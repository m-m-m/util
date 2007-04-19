/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the core NLS itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleNlsCore extends AbstractResourceBundle {

  /** to be used with resource bundle */
  public static final String ERR_RESOURCE_MISSING = "The required resource \"{0}\" is missing!";

  /** to be used with resource bundle */
  public static final String TEST = "This is a multi-line \n value!";

  /**
   * The constructor.
   */
  public NlsBundleNlsCore() {

    super();
  }

}
