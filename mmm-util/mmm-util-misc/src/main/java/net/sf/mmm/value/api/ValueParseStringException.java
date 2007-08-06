/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api;

import net.sf.mmm.util.NlsBundleUtilMisc;

/**
 * This exception is thrown if the parsing of a value as string fails.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueParseStringException extends ValueParseException {

  /** uid for serialization */
  private static final long serialVersionUID = -5461017585248675363L;

  /**
   * The constructor.
   * 
   * @param stringToParse is the string that could not be parsed.
   * @param valueType is the type the given string should be parsed as.
   * @param valueName is the alias name of <code>valueType</code>.
   */
  public ValueParseStringException(String stringToParse, Class valueType, String valueName) {

    super(NlsBundleUtilMisc.ERR_PARSE_STRING, stringToParse, valueType, valueName);
  }

  /**
   * The constructor.
   * 
   * @param stringToParse is the string that could not be parsed.
   * @param valueType is the type the given string should be parsed as.
   * @param valueName is the alias name of <code>valueType</code>.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public ValueParseStringException(String stringToParse, Class valueType, String valueName,
      Throwable nested) {

    super(nested, NlsBundleUtilMisc.ERR_PARSE_STRING, stringToParse, valueType, valueName);
  }

}
