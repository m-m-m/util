/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo;

import net.sf.mmm.nls.base.AbstractResourceBundle;
import net.sf.mmm.util.pojo.api.PojoPropertyNotFoundException;

/**
 * This class holds the internationalized messages for this subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundlePojo extends AbstractResourceBundle {

  /**
   * The constructor. 
   */
  public NlsBundlePojo() {

    super();
  }

  /** @see PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_FOUND = "Property \"{0}\" not found in \"{1}\"!";

  /** @see PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_ACCESSABLE = "Property \"{0}\" not accessible for \"{2}\" in \"{1}\"!";

}
