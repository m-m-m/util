/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import net.sf.mmm.util.value.ValueParseException;
import net.sf.mmm.value.NlsBundleValueMain;

/**
 * This exception is thrown if the parsing of a value as string fails.
 * 
 * @see net.sf.mmm.value.api.ValueManager#fromString(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueParseXmlException extends ValueParseException {

  /** uid for serialization */
  private static final long serialVersionUID = -5461017585248675363L;

  /**
   * The constructor.
   * 
   * @param valueType is the type of value the given string should be parsed as.
   * @param stringToParse is the string that could not be parsed
   */
  public ValueParseXmlException(Class valueType, String stringToParse) {

    this(valueType, stringToParse, null);
  }

  /**
   * The constructor.
   * 
   * @param valueType
   * @param stringToParse
   * @param nested
   */
  public ValueParseXmlException(Class valueType, String stringToParse, Throwable nested) {

    super(nested, NlsBundleValueMain.ERR_PARSE_STRING, valueType, stringToParse);
  }

  /**
   * This method gets the expected value type of the given string to parse.
   * 
   * @return the value type.
   */
  public Class getValueType() {

    return (Class) getNlsMessage().getArgument(0);
  }

  /**
   * This method gets the string that could not be parsed and is the reason of
   * this exception.
   * 
   * @return the malformed value string.
   */
  public String getStringToParse() {

    return (String) getNlsMessage().getArgument(1);
  }

}
