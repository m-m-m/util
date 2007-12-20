/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

/**
 * This class holds the internationalized messages for <code>util-misc</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleUtilCore extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.value.WrongValueTypeException */
  public static final String ERR_VALUE_WRONG_TYPE = "The value \"{0}\" with the "
      + "type \"{1}\" can NOT be converted to the requested type \"{2}\"!";

  /** @see net.sf.mmm.util.value.WrongValueTypeException */
  public static final String ERR_VALUE_WRONG_TYPE_SOURCE = "The value \"{0}\" "
      + "from \"{3}\" with the type \"{1}\" can NOT be converted to the requested type \"{2}\"!";

  /** @see net.sf.mmm.util.value.ValueNotSetException */
  public static final String ERR_VALUE_NOT_SET = "The value \"{0}\" is not set!";

  /** @see net.sf.mmm.util.value.ValueParseStringException */
  public static final String ERR_PARSE_STRING = "Failed to parse \"{0}\" as \"{2}\"-value[{1}]!";

  /** @see net.sf.mmm.util.value.ValueOutOfRangeException */
  public static final String ERR_VALUE_OUT_OF_RANGE = "The value \"{0}\" ({1}) "
      + "is not in the expected range of \"[{2}-{3}]\"!";

  /** @see net.sf.mmm.util.component.ResourceMissingException */
  public static final String ERR_RESOURCE_MISSING = "The required resource \"{0}\" is missing!";

  /** @see net.sf.mmm.util.component.AlreadyInitializedException */
  public static final String ERR_ALREADY_INITIALIZED = "The object is already initialized!";

  /** @see net.sf.mmm.util.component.NotInitializedException */
  public static final String ERR_NOT_INITIALIZED = "The object is NOT initialized!";

  /** @see NlsIllegalArgumentException */
  public static final String ERR_ILLEGAL_ARGUMENT = "The given argument \"{0}\" is illegal!";

  /** @see net.sf.mmm.util.date.IllegalDateFormatException */
  public static final String ERR_ILLEGAL_DATA_FORMAT = "Illegal date-format \"{0}\"!";

}
