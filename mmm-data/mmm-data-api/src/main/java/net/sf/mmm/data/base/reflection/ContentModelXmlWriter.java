/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionObject;
import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This class allows to serialize the content-model to XML using StAX.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentModelXmlWriter {

  /**
   * The constructor.
   */
  public ContentModelXmlWriter() {

    super();
  }

  /**
   * This method writes the general XML attributes of the given
   * <code>contentObject</code> to the <code>xmlWriter</code>.
   * 
   * @param contentClassOrField is the object to write.
   * @param xmlWriter is where to write the XML to. The writer will NOT be
   *        {@link XMLStreamWriter#close() closed}.
   * @throws XMLStreamException if the <code>xmlWriter</code> caused an error.
   */
  private void writeReflectionObject(DataReflectionObject<? extends DataObject> contentClassOrField,
      XMLStreamWriter xmlWriter) throws XMLStreamException {

    Long id = contentClassOrField.getId();
    if (id != null) {
      xmlWriter.writeAttribute(DataObject.FIELD_NAME_ID, id.toString());
    }
    xmlWriter.writeAttribute(DataObject.FIELD_NAME_TITLE, contentClassOrField.getTitle());
    if (contentClassOrField.getDeletedFlag()) {
      xmlWriter.writeAttribute(DataReflectionObject.FIELD_NAME_DELETEDFLAG, StringUtil.TRUE);
    }
  }

  /**
   * This method writes the XML of the given <code>contentField</code> to the
   * <code>xmlWriter</code>.
   * 
   * @param contentField is the field to write.
   * @param xmlWriter is where to write the XML to. The writer will NOT be
   *        {@link XMLStreamWriter#close() closed}.
   * @throws XMLStreamException if the <code>xmlWriter</code> caused an error.
   */
  public void writeField(DataField<? extends DataObject, ?> contentField,
      XMLStreamWriter xmlWriter) throws XMLStreamException {

    xmlWriter.writeStartElement(DataField.CLASS_NAME);
    writeReflectionObject(contentField, xmlWriter);
    String type = contentField.getFieldType().toString();
    xmlWriter.writeAttribute(DataField.FIELD_NAME_FIELD_TYPE, type);
    DataFieldModifiers modifiers = contentField.getContentModifiers();
    // if (modifiers.isSystem()) {
    // xmlWriter.writeAttribute(Modifiers.XML_ATR_ROOT_SYSTEM, StringUtil.TRUE);
    // }
    if (modifiers.isFinal()) {
      xmlWriter.writeAttribute(DataModifiers.XML_ATR_FINAL, StringUtil.TRUE);
    }
    if (modifiers.isReadOnly()) {
      xmlWriter.writeAttribute(DataFieldModifiers.XML_ATR_READ_ONLY, StringUtil.TRUE);
    }
    if (!modifiers.isStatic()) {
      xmlWriter.writeAttribute(DataFieldModifiers.XML_ATR_STATIC, StringUtil.FALSE);
    }
    if (!modifiers.isTransient()) {
      xmlWriter.writeAttribute(DataFieldModifiers.XML_ATR_TRANSIENT, StringUtil.FALSE);
    }
    xmlWriter.writeEndElement();
  }

  /**
   * This method writes the XML of the given <code>contentClass</code> including
   * all its {@link DataClass#getDeclaredFields() fields} and its
   * {@link DataClass#getSubClasses() sub-classes} (recursive) to the
   * <code>xmlWriter</code>.
   * 
   * @param contentClass is the class to write.
   * @param xmlWriter is where to write the XML to. The writer will NOT be
   *        {@link XMLStreamWriter#close() closed}.
   * @throws XMLStreamException if the <code>xmlWriter</code> caused an error.
   */
  public void writeClass(DataClass<? extends DataObject> contentClass,
      XMLStreamWriter xmlWriter) throws XMLStreamException {

    xmlWriter.writeStartElement(DataClass.CLASS_NAME);
    writeReflectionObject(contentClass, xmlWriter);
    DataClassModifiers modifiers = contentClass.getContentModifiers();
    if (modifiers.isSystem()) {
      xmlWriter.writeAttribute(DataModifiers.XML_ATR_SYSTEM, StringUtil.TRUE);
      if (modifiers.isExtendable() && !modifiers.isFinal()) {
        xmlWriter.writeAttribute(DataClassModifiers.XML_ATR_EXTENDABLE,
            StringUtil.TRUE);
      }
    }
    if (modifiers.isFinal()) {
      xmlWriter.writeAttribute(DataModifiers.XML_ATR_FINAL, StringUtil.TRUE);
    }
    if (modifiers.isAbstract()) {
      xmlWriter.writeAttribute(DataClassModifiers.XML_ATR_ABSTRACT, StringUtil.TRUE);
    }
    for (DataField<? extends DataObject, ?> field : contentClass.getDeclaredFields()) {
      writeField(field, xmlWriter);
    }
    for (DataClass<? extends DataObject> subClass : contentClass.getSubClasses()) {
      writeClass(subClass, xmlWriter);
    }
    xmlWriter.writeEndElement();
  }

  /**
   * This method writes the XML of the content-model with the given
   * <code>rootClass</code> to the <code>xmlWriter</code>.
   * 
   * @param rootClass is the root-class of the content-model.
   * @param xmlWriter is where to write the XML to. The writer will NOT be
   *        {@link XMLStreamWriter#close() closed}.
   * @throws XMLStreamException if the <code>xmlWriter</code> caused an error.
   */
  public void writeModel(DataClass<? extends DataObject> rootClass, XMLStreamWriter xmlWriter)
      throws XMLStreamException {

    xmlWriter.writeStartElement(DataClass.XML_TAG_CONTENT_MODEL);
    writeClass(rootClass, xmlWriter);
    xmlWriter.writeEndElement();
  }

}
