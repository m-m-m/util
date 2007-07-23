/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.Type;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.content.value.impl.ClassId;
import net.sf.mmm.content.value.impl.FieldId;
import net.sf.mmm.util.value.ValueUtil;
import net.sf.mmm.value.api.ValueException;

/**
 * This class allows to de-serialize the content-model from XML using StAX.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentModelXmlReader {

  /**
   * The constructor.
   */
  public AbstractContentModelXmlReader() {

    super();
  }

  protected <V> V readAttribute(XMLStreamReader xmlReader, String localAttributeName, Class<V> type)
      throws ValueException {

    String value = xmlReader.getAttributeValue(null, localAttributeName);
    return ValueUtil.convertValue(localAttributeName, value, type);
  }

  protected <V> V readAttribute(XMLStreamReader xmlReader, String localAttributeName, Class<V> type,
      V defaultValue) throws ValueException {

    String value = xmlReader.getAttributeValue(null, localAttributeName);
    return ValueUtil.convertValue(localAttributeName, value, type, defaultValue);
  }

  protected Type parseFieldType(String typeString) {

    // TODO
    try {
      return Class.forName(typeString);
    } catch (ClassNotFoundException e) {
      // TODO
      // throw new WrongValueTypeException(typeString,
      // ContentField.XML_ATR_ROOT_TYPE,
      // Type.class);
      return null;
    }
  }

  protected void readBasicFields(XMLStreamReader xmlReader, AbstractContentReflectionObject object)
      throws ValueException, XMLStreamException {

    // parse ID
    int id = readAttribute(xmlReader, ContentObject.FIELD_NAME_ID, Integer.class).intValue();
    SmartId uid;
    // TODO: get id manager
    SmartIdManager idManager = null;
    if (idManager != null) {
      if (object.isClass()) {
        uid = idManager.getClassId(id);
      } else {
        uid = idManager.getFieldId(id);
      }      
    }
    if (object.isClass()) {
      uid = ClassId.valueOf(id);
    } else {
      uid = FieldId.valueOf(id);
    }
    object.setId(uid);
    // parse name
    String name = readAttribute(xmlReader, ContentObject.FIELD_NAME_NAME, String.class);
    object.setName(name);
    // parse deleted flag
    boolean deleted = readAttribute(xmlReader, ContentObject.FIELD_NAME_DELETED, Boolean.class,
        Boolean.FALSE).booleanValue();
    object.setDeletedFlag(deleted);
  }

}
