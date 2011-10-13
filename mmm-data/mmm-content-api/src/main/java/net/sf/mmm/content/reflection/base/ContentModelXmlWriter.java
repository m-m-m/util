/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentClassModifiers;
import net.sf.mmm.content.reflection.api.ContentField;
import net.sf.mmm.content.reflection.api.ContentFieldModifiers;
import net.sf.mmm.content.reflection.api.ContentModifiers;
import net.sf.mmm.content.reflection.api.ContentReflectionObject;
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
  private void writeReflectionObject(ContentReflectionObject<? extends ContentObject> contentClassOrField,
      XMLStreamWriter xmlWriter) throws XMLStreamException {

    Long id = contentClassOrField.getId();
    if (id != null) {
      xmlWriter.writeAttribute(ContentObject.FIELD_NAME_ID, id.toString());
    }
    xmlWriter.writeAttribute(ContentObject.FIELD_NAME_TITLE, contentClassOrField.getTitle());
    if (contentClassOrField.getDeletedFlag()) {
      xmlWriter.writeAttribute(ContentReflectionObject.FIELD_NAME_DELETEDFLAG, StringUtil.TRUE);
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
  public void writeField(ContentField<? extends ContentObject, ?> contentField,
      XMLStreamWriter xmlWriter) throws XMLStreamException {

    xmlWriter.writeStartElement(ContentField.CLASS_NAME);
    writeReflectionObject(contentField, xmlWriter);
    String type = contentField.getFieldType().toString();
    xmlWriter.writeAttribute(ContentField.FIELD_NAME_FIELD_TYPE, type);
    ContentFieldModifiers modifiers = contentField.getContentModifiers();
    // if (modifiers.isSystem()) {
    // xmlWriter.writeAttribute(Modifiers.XML_ATR_ROOT_SYSTEM, StringUtil.TRUE);
    // }
    if (modifiers.isFinal()) {
      xmlWriter.writeAttribute(ContentModifiers.XML_ATR_FINAL, StringUtil.TRUE);
    }
    if (modifiers.isReadOnly()) {
      xmlWriter.writeAttribute(ContentFieldModifiers.XML_ATR_READ_ONLY, StringUtil.TRUE);
    }
    if (!modifiers.isStatic()) {
      xmlWriter.writeAttribute(ContentFieldModifiers.XML_ATR_STATIC, StringUtil.FALSE);
    }
    if (!modifiers.isTransient()) {
      xmlWriter.writeAttribute(ContentFieldModifiers.XML_ATR_TRANSIENT, StringUtil.FALSE);
    }
    xmlWriter.writeEndElement();
  }

  /**
   * This method writes the XML of the given <code>contentClass</code> including
   * all its {@link ContentClass#getDeclaredFields() fields} and its
   * {@link ContentClass#getSubClasses() sub-classes} (recursive) to the
   * <code>xmlWriter</code>.
   * 
   * @param contentClass is the class to write.
   * @param xmlWriter is where to write the XML to. The writer will NOT be
   *        {@link XMLStreamWriter#close() closed}.
   * @throws XMLStreamException if the <code>xmlWriter</code> caused an error.
   */
  public void writeClass(ContentClass<? extends ContentObject> contentClass,
      XMLStreamWriter xmlWriter) throws XMLStreamException {

    xmlWriter.writeStartElement(ContentClass.CLASS_NAME);
    writeReflectionObject(contentClass, xmlWriter);
    ContentClassModifiers modifiers = contentClass.getContentModifiers();
    if (modifiers.isSystem()) {
      xmlWriter.writeAttribute(ContentModifiers.XML_ATR_SYSTEM, StringUtil.TRUE);
      if (modifiers.isExtendable() && !modifiers.isFinal()) {
        xmlWriter.writeAttribute(ContentClassModifiers.XML_ATR_EXTENDABLE,
            StringUtil.TRUE);
      }
    }
    if (modifiers.isFinal()) {
      xmlWriter.writeAttribute(ContentModifiers.XML_ATR_FINAL, StringUtil.TRUE);
    }
    if (modifiers.isAbstract()) {
      xmlWriter.writeAttribute(ContentClassModifiers.XML_ATR_ABSTRACT, StringUtil.TRUE);
    }
    for (ContentField<? extends ContentObject, ?> field : contentClass.getDeclaredFields()) {
      writeField(field, xmlWriter);
    }
    for (ContentClass<? extends ContentObject> subClass : contentClass.getSubClasses()) {
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
  public void writeModel(ContentClass<? extends ContentObject> rootClass, XMLStreamWriter xmlWriter)
      throws XMLStreamException {

    xmlWriter.writeStartElement(ContentClass.XML_TAG_CONTENT_MODEL);
    writeClass(rootClass, xmlWriter);
    xmlWriter.writeEndElement();
  }

}
