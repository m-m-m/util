/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
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
   * @param contentObject is the object to write.
   * @param xmlWriter is where to write the XML to. The writer will NOT be
   *        {@link XMLStreamWriter#close() closed}.
   * @throws XMLStreamException if the <code>xmlWriter</code> caused an error.
   */
  private void writeObject(ContentReflectionObject contentObject, XMLStreamWriter xmlWriter)
      throws XMLStreamException {

    ContentId id = contentObject.getContentId();
    if (id != null) {
      String idString;
      if (id instanceof ContentId) {
        ContentId contentId = id;
        idString = Integer.toString(contentId.getObjectId());
      } else {
        idString = id.toString();
      }
      xmlWriter.writeAttribute(ContentObject.FIELD_NAME_ID, idString);
    }
    xmlWriter.writeAttribute(ContentObject.FIELD_NAME_TITLE, contentObject.getTitle());
    if (contentObject.getDeletedFlag()) {
      xmlWriter.writeAttribute(ContentObject.FIELD_NAME_DELETED, StringUtil.TRUE);
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
  public void writeField(ContentField contentField, XMLStreamWriter xmlWriter)
      throws XMLStreamException {

    xmlWriter.writeStartElement(ContentField.CLASS_NAME);
    writeObject(contentField, xmlWriter);
    // TODO:
    String type = contentField.getFieldType().toString();
    xmlWriter.writeAttribute(ContentField.FIELD_NAME_FIELD_TYPE, type);
    ContentFieldModifiers modifiers = contentField.getContentModifiers();
    // if (modifiers.isSystem()) {
    // xmlWriter.writeAttribute(Modifiers.XML_ATR_ROOT_SYSTEM, StringUtil.TRUE);
    // }
    if (modifiers.isFinal()) {
      xmlWriter.writeAttribute(ContentModifiers.XML_ATR_MODIFIERS_FINAL, StringUtil.TRUE);
    }
    if (modifiers.isReadOnly()) {
      xmlWriter.writeAttribute(ContentFieldModifiers.XML_ATR_ROOT_READ_ONLY, StringUtil.TRUE);
    }
    if (!modifiers.isStatic()) {
      xmlWriter.writeAttribute(ContentFieldModifiers.XML_ATR_ROOT_STATIC, StringUtil.FALSE);
    }
    if (!modifiers.isTransient()) {
      xmlWriter.writeAttribute(ContentFieldModifiers.XML_ATR_ROOT_TRANSIENT, StringUtil.FALSE);
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
  public void writeClass(ContentClass contentClass, XMLStreamWriter xmlWriter)
      throws XMLStreamException {

    xmlWriter.writeStartElement(ContentClass.CLASS_NAME);
    writeObject(contentClass, xmlWriter);
    ContentClassModifiers modifiers = contentClass.getContentModifiers();
    if (modifiers.isSystem()) {
      xmlWriter.writeAttribute(ContentModifiers.XML_ATR_MODIFIERS_SYSTEM, StringUtil.TRUE);
    }
    if (modifiers.isFinal()) {
      xmlWriter.writeAttribute(ContentModifiers.XML_ATR_MODIFIERS_FINAL, StringUtil.TRUE);
    }
    if (modifiers.isAbstract()) {
      xmlWriter.writeAttribute(ContentClassModifiers.XML_ATR_MODIFIERS_ABSTRACT, StringUtil.TRUE);
    }
    if (!modifiers.isExtendable() && !modifiers.isFinal()) {
      xmlWriter
          .writeAttribute(ContentClassModifiers.XML_ATR_MODIFIERS_EXTENDABLE, StringUtil.FALSE);
    }
    for (ContentField field : contentClass.getDeclaredFields()) {
      writeField(field, xmlWriter);
    }
    for (ContentClass subClass : contentClass.getSubClasses()) {
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
  public void writeModel(ContentClass rootClass, XMLStreamWriter xmlWriter)
      throws XMLStreamException {

    xmlWriter.writeStartElement(ContentClass.XML_TAG_CONTENT_MODEL);
    writeClass(rootClass, xmlWriter);
    xmlWriter.writeEndElement();
  }

}
