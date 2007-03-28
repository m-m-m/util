/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.validator.base;

import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;
import net.sf.mmm.value.validator.api.ValueValidator;

/**
 * This is the abstract base implementation of the value validator interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValueValidator implements ValueValidator {

  /**
   * The constructor.
   */
  public AbstractValueValidator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void toXml(XmlWriter xmlWriter) throws XmlException {

    xmlWriter.writeStartElement(XML_TAG_VALIDATOR);
    xmlWriter.writeAttribute(XML_ATR_VALIDATOR_TYPE, getClass().toString());
    xmlWriter.writeEndElement(XML_TAG_VALIDATOR);
  }

}
