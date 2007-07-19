/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.io.InputStream;
import java.lang.reflect.Type;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.Modifiers;
import net.sf.mmm.content.model.base.AbstractContentModelXmlReader;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.model.base.FieldModifiersImpl;
import net.sf.mmm.util.xml.StaxUtil;
import net.sf.mmm.value.api.ValueException;

/**
 * This class allows to de-serialize the content-model from XML using StAX.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentModelXmlReader extends AbstractContentModelXmlReader {

  /**
   * The constructor.
   */
  public ContentModelXmlReader() {

    super();
  }

  /**
   * This method reads a class-hierarchy from the given <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized classes and fields is NOT set by this method so it may be
   * <code>null</code> if NOT initialized.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public ContentClassImpl readModel(XMLStreamReader xmlReader) throws ValueException,
      XMLStreamException {

    assert (xmlReader.isStartElement());
    if (!ContentClass.XML_TAG_CONTENT_MODEL.equals(xmlReader.getLocalName())) {
      // TODO:
      throw new IllegalArgumentException("wrong tag for content-model: " + xmlReader.getLocalName());
    }
    ContentClassImpl rootClass = null;
    while (XMLStreamConstants.START_ELEMENT == xmlReader.nextTag()) {
      if (ContentClass.CLASS_NAME.equals(xmlReader.getLocalName())) {
        if (rootClass != null) {
          // TODO:
          throw new IllegalArgumentException("Multiple root-classes in contentmodel!");
        }
        rootClass = readClass(xmlReader);
      } else {
        StaxUtil.skipOpenElement(xmlReader);
      }
    }
    if (rootClass == null) {
      // TODO:
      throw new IllegalArgumentException("Missing root-class!");
    }
    return rootClass;
  }

  /**
   * This method reads a class-hierarchy from the given <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized classes and fields is NOT set by this method so it may be
   * <code>null</code> if NOT initialized.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public ContentClassImpl readClass(XMLStreamReader xmlReader) throws ValueException,
      XMLStreamException {

    assert (xmlReader.isStartElement());
    if (!ContentClass.CLASS_NAME.equals(xmlReader.getLocalName())) {
      // TODO:
      throw new IllegalArgumentException("wrong tag for class: " + xmlReader.getLocalName());
    }
    ContentClassImpl contentClass = new ContentClassImpl();
    // contentClass.setSuperClass(superClass);
    // superClass.addSubClass(contentClass);
    readBasicFields(xmlReader, contentClass);
    // parse modifier
    boolean isFinal = readAttribute(xmlReader, Modifiers.XML_ATR_ROOT_FINAL, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isAbstract = readAttribute(xmlReader, ClassModifiers.XML_ATR_ROOT_ABSTRACT,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isExtendable = readAttribute(xmlReader, ClassModifiers.XML_ATR_ROOT_EXTENDABLE,
        Boolean.class, Boolean.valueOf(!isFinal)).booleanValue();
    boolean isSystem = readAttribute(xmlReader, Modifiers.XML_ATR_ROOT_SYSTEM, Boolean.class,
        Boolean.FALSE).booleanValue();
    ClassModifiers modifiers = ClassModifiersImpl.getInstance(isSystem, isFinal, isAbstract,
        isExtendable);
    contentClass.setModifiers(modifiers);
    // parse XML-child elements
    int xmlEvent = xmlReader.nextTag();
    while (XMLStreamConstants.START_ELEMENT == xmlEvent) {
      String tagName = xmlReader.getLocalName();
      if (ContentClass.CLASS_NAME.equals(tagName)) {
        // class
        ContentClassImpl childClass = readClass(xmlReader);
        childClass.setSuperClass(contentClass);
        contentClass.addSubClass(childClass);
      } else if (ContentField.CLASS_NAME.equals(tagName)) {
        // field
        ContentFieldImpl contentField = readField(xmlReader, contentClass);
        contentClass.addField(contentField);
      }
      xmlEvent = xmlReader.nextTag();
    }
    return contentClass;
  }

  /**
   * This method reads a {@link ContentField} from the given
   * <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized field is NOT set by this method so it may be
   * <code>null</code> if NOT initialized.
   * 
   * @param xmlReader is where to read the XML from.
   * @param declaringClass is the class declaring this field.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public ContentFieldImpl readField(XMLStreamReader xmlReader, ContentClassImpl declaringClass)
      throws ValueException, XMLStreamException {

    ContentFieldImpl contentField = new ContentFieldImpl();
    readBasicFields(xmlReader, contentField);
    contentField.setDeclaringClass(declaringClass);
    // parse modifier
    boolean isFinal = readAttribute(xmlReader, Modifiers.XML_ATR_ROOT_FINAL, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isReadOnly = readAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_READ_ONLY,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isStatic = readAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_STATIC, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isSystem = declaringClass.getModifiers().isSystem();
    boolean isTransient = readAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_TRANSIENT,
        Boolean.class, Boolean.FALSE).booleanValue();
    FieldModifiers modifiers = FieldModifiersImpl.getInstance(isSystem, isFinal, isReadOnly,
        isStatic, isTransient);
    contentField.setModifiers(modifiers);
    // parse type
    String typeString = readAttribute(xmlReader, ContentField.XML_ATR_ROOT_TYPE, String.class);
    Type type = parseFieldType(typeString);
    contentField.setFieldTypeAndClass(type);

    xmlReader.nextTag();
    if (xmlReader.isStartElement()) {
      // TODO: parse until end...
    }
    return contentField;
  }

  /**
   * 
   * TODO: javadoc
   * @param args a
   * @throws Exception b
   */
  public static void main(String[] args) throws Exception {

    InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(
        "net/sf/mmm/content/model/ContentModel.xml");
    XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(in);
    xmlReader.nextTag();
    ContentModelXmlReader s = new ContentModelXmlReader();
    ContentClassImpl c = s.readModel(xmlReader);
    in.close();
    System.out.println(c);
  }

}
