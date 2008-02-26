/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo;

import net.sf.mmm.util.nls.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the POJO support.
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

  /** @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_FOUND = "Property \"{0}\" not found in \"{1}\"!";

  /** @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_ACCESSABLE = "Property \"{0}\" not "
      + "accessible for \"{2}\" in \"{1}\"!";

  /** @see net.sf.mmm.util.reflect.pojo.path.api.PojoPathSegmentIsNullException */
  public static final String ERR_PATH_SEGMENT_IS_NULL = "Failed to evaluate the "
      + "POJO-path \"{0}\" for object \"{1}\" because the segment at \"{2}\" is null!";

  /** @see net.sf.mmm.util.reflect.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_ILLEGAL_PATH = "Illegal POJO-path \"{0}\"!";

  /** @see net.sf.mmm.util.reflect.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_FUNCTION_UNDEFINED = "Undefined function \"{0}\"!";

}
