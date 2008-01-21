/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value;

import net.sf.mmm.util.nls.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the configuration
 * subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleValueCore extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleValueCore() {

    super();
  }

  /**
   * exception message if configuration value has wrong type.
   * 
   * @see net.sf.mmm.util.value.WrongValueTypeException
   */
  public static final String ERR_VALUE_WRONG_TYPE = "The value \"{0}\" of the type \"{1}\" can NOT be converted to the requested type \"{2}\"!";

  /**
   * exception message if configuration value has wrong type.
   * 
   * @see net.sf.mmm.util.value.ValueOutOfRangeException
   */
  public static final String ERR_VALUE_OUT_OF_RANGE = "The configuration \"{0}\" has the value \"{1}\" which is not in the expected range of \"[{2}-{3}]\"!";

  /**
   * exception message if required value is NOT set.
   * 
   * @see net.sf.mmm.util.value.ValueNotSetException
   */
  public static final String ERR_VALUE_NOT_SET = "The requested value \"{0}\" is not set!";

  /**
   * exception message if a node was tried to be changed that is NOT
   * {@link net.sf.mmm.value.api.MutableGenericValue#isEditable() editable}.
   * 
   * @see net.sf.mmm.value.api.ValueNotEditableException
   */
  public static final String ERR_NODE_NOT_EDITABLE = "The value \"{0}\" can not be changed!";

  /**
   * exception message if
   * {@link net.sf.mmm.value.api.GenericValue#getJavaClassInstance(Class) instantiation}
   * failed.
   * 
   * @see net.sf.mmm.value.api.ValueInstanciationException
   */
  public static final String ERR_INSTANTIATION_FAILED = "The instantiation of \"{0}\" failed!";

}
