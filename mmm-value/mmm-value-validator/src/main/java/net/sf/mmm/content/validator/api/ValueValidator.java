/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.validator.api;

import net.sf.mmm.util.xml.api.XmlSerializable;

/**
 * This is the interface for a validator of values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ValueValidator extends XmlSerializable {

  /** the XML tag for a avalidator */
  String XML_TAG_VALIDATOR = "validator";

  /** the XML tag for a avalidator */
  String XML_ATR_VALIDATOR_TYPE = "name";

  /**
   * This method validates the given value. The implementation decides what is
   * valid and invalid.
   * 
   * @param value
   *        the value to validate. It may be <code>null</code> but its not
   *        allowed to throw a {@link NullPointerException} for this reason.
   * @return the result of the validation.
   */
  ValidationResult validate(Object value);

}
