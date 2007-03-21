/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.base;

import java.util.Iterator;

import net.sf.mmm.content.value.api.MetaDataKey;
import net.sf.mmm.content.value.api.MutableMetaDataSet;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This is the abstract base implementation of the {@link MutableMetaDataSet}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractMetaDataSet implements MutableMetaDataSet {

  /**
   * The constructor.
   */
  public AbstractMetaDataSet() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void toXml(XmlWriter xmlWriter) throws XmlException {

    xmlWriter.writeStartElement(XML_TAG_META_DATA);
    Iterator<MetaDataKey> keyList = getKeys().iterator();
    while (keyList.hasNext()) {
      MetaDataKey key = keyList.next();
      String value = getMetaData(key);
      // the value should never be null, but just to get sure...
      if (value != null) {
        xmlWriter.writeStartElement(XML_TAG_VALUE);
        xmlWriter.writeAttribute(XML_ATR_VALUE_NAMESPACE, key.getNamespace());
        xmlWriter.writeAttribute(XML_ATR_VALUE_KEY, key.getKey());
        xmlWriter.writeCharacters(value);
        xmlWriter.writeEndElement(XML_TAG_VALUE);
      }
    }
    xmlWriter.writeEndElement(XML_TAG_META_DATA);
  }

}
