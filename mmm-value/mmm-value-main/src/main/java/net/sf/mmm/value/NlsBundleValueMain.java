/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value;

import net.sf.mmm.util.nls.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the value component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleValueMain extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleValueMain() {

    super();
  }

  /**
   * exception message for a value that could not be parsed.
   * 
   * @see net.sf.mmm.value.api.ValueParseStringException
   */
  public static final String ERR_PARSE_STRING = "Failed to parse \"{0}\" as \"{2}\"-value[{1}]!";

  /**
   * exception message for a value that could not be parsed.
   * 
   * @see net.sf.mmm.util.value.api.ValueParseException
   */
  public static final String ERR_PARSE_XML_START_TAG = "Failed to parse XML as \"{0}\"-value! Illegal start tag \"{1}\"!";

  /**
   * exception message for a value that could not be parsed.
   * 
   * @see net.sf.mmm.util.value.api.ValueParseException
   */
  public static final String ERR_PARSE_XML_NO_TYEP_ATTRIBUTE = "Failed to parse XML as \"{0}\"-value! Illegal start tag \"{1}\"!";

  /**
   * exception message if value manager is already registered.
   * 
   * @see net.sf.mmm.value.base.AbstractValueService
   */
  public static final String ERR_ALREADY_REGISTERED_CLASS = "Value manager for value class \"{0}\" is already registered!";

  /**
   * exception message if value manager is already registered.
   * 
   * @see net.sf.mmm.value.base.AbstractValueService
   */
  public static final String ERR_ALREADY_REGISTERED_NAME = "Value manager with name \"{0}\" is already registered!";

  /**
   * exception message if value type is not registered.
   * 
   * @see net.sf.mmm.value.base.AbstractValueService
   */
  public static final String ERR_NOT_REGISTERED = "The value \"{0}\" is not registered!";

  /**
   * exception message if value class caused security exception.
   * 
   * @see net.sf.mmm.value.impl.GenericValueManager
   */
  public static final String ERR_GENERIC_CLASS_SECURITY = "The value type \"{0}\" caused a security exception!";

  /**
   * exception message if value class is abstract.
   * 
   * @see net.sf.mmm.value.impl.GenericValueManager
   */
  public static final String ERR_GENERIC_CLASS_ABSTRACT = "The value type \"{0}\" is abstract!";

  /**
   * exception message if generic value class can not be created from string.
   * 
   * @see net.sf.mmm.value.impl.GenericValueManager
   */
  public static final String ERR_GENERIC_CLASS_STRING = "The value type \"{0}\" has no String constructor!";

  /**
   * exception message if generic value class can not be created from string.
   * 
   * @see net.sf.mmm.value.impl.GenericValueManager
   */
  public static final String ERR_GENERIC_CLASS_NOT_ACCESSIBLE = "The value type \"{0}\" is not accessible!";

  /**
   * exception message if generic value class can not be created from string.
   * 
   * @see net.sf.mmm.value.impl.GenericValueManager
   */
  public static final String MSG_MANAGER_TO_STRING = "Value manager for \"{0}\" [{1}]!";

}
