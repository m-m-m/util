/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.Modifiers;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.resource.DataResource;
import net.sf.mmm.util.value.ValueUtil;
import net.sf.mmm.util.xml.StaxUtil;
import net.sf.mmm.util.xml.XIncludeStreamReader;
import net.sf.mmm.value.api.ValueException;

/**
 * This is an implementation of the {@link ContentClassLoader} interface that
 * allows to load (de-serialize) {@link ContentClass}es from XML using StAX.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassLoaderStAX extends AbstractContentClassLoader {

  /**
   * The constructor.
   * 
   * @param contentModelService is the
   *        {@link #getContentModelService() content-model service}.
   */
  public ContentClassLoaderStAX(AbstractMutableContentModelService contentModelService) {

    super(contentModelService);
  }

  /**
   * This method parses the attribute with the given
   * <code>localAttributeName</code> from the given <code>xmlReader</code>
   * as given by <code>type</code>.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param xmlReader is where to read the XML from.
   * @param localAttributeName is the local name of the requested attribute.
   * @param type is the type the requested attribute should be converted to.
   * @return the requested attribute as the given <code>type</code>.
   * @throws ValueException if the attribute is NOT defined or its value can NOT
   *         be converted to <code>type</code>.
   */
  protected <V> V parseAttribute(XMLStreamReader xmlReader, String localAttributeName, Class<V> type)
      throws ValueException {

    String value = xmlReader.getAttributeValue(null, localAttributeName);
    return ValueUtil.convertValue(localAttributeName, value, type);
  }

  /**
   * This method parses the attribute with the given
   * <code>localAttributeName</code> from the given <code>xmlReader</code>
   * as given by <code>type</code>.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param xmlReader is where to read the XML from.
   * @param localAttributeName is the local name of the requested attribute.
   * @param type is the type the requested attribute should be converted to.
   * @param defaultValue is the default value returned if the requested
   *        attribute is NOT defined. It may be <code>null</code>.
   * @return the requested attribute as the given <code>type</code>.
   * @throws ValueException if the attribute value can NOT be converted to
   *         <code>type</code>.
   */
  protected <V> V parseAttribute(XMLStreamReader xmlReader, String localAttributeName,
      Class<V> type, V defaultValue) throws ValueException {

    String value = xmlReader.getAttributeValue(null, localAttributeName);
    return ValueUtil.convertValue(localAttributeName, value, type, defaultValue);
  }

  /**
   * This method parses the type given by <code>typeString</code>.
   * 
   * @param typeSpecification is the type given as string (e.g.
   *        <code>String</code> or <code>List&lt;ContentObject&gt;</code>).
   * @return the parsed type.
   */
  protected Type parseFieldType(String typeSpecification) {

    try {
      return ReflectionUtil.toType(typeSpecification, getContentModelService());
    } catch (Exception e) {
      throw new ContentModelException("Illegal Type '" + typeSpecification + "'!", e);
    }
  }

  /**
   * This method reads the ID from the given <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the ID of the class or field.
   */
  protected SmartId parseId(XMLStreamReader xmlReader) {

    boolean isClass = ContentClass.XML_TAG_CLASS.equals(xmlReader.getLocalName());
    // parse ID
    int id = parseAttribute(xmlReader, ContentObject.FIELD_NAME_ID, Integer.class).intValue();
    SmartId uid;
    SmartIdManager idManager = getContentModelService().getIdManager();
    if (isClass) {
      uid = idManager.getClassId(id);
    } else {
      uid = idManager.getFieldId(id);
    }
    return uid;
  }

  /**
   * This method reads the {@link ContentObject#getName() name} from the given
   * <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the name of the class or field.
   */
  protected String parseName(XMLStreamReader xmlReader) {

    return parseAttribute(xmlReader, ContentObject.FIELD_NAME_NAME, String.class);
  }

  /**
   * This method reads the {@link ContentObject#isDeleted() deleted flag} from
   * the given <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the deleted-flag of the class or field.
   */
  protected boolean parseDeletedFlag(XMLStreamReader xmlReader) {

    return parseAttribute(xmlReader, ContentObject.FIELD_NAME_DELETED, Boolean.class, Boolean.FALSE)
        .booleanValue();
  }

  /**
   * This method loads the basic fields of a {@link ContentClass} or
   * {@link ContentField}.
   * 
   * @param xmlReader is where to read the XML from.
   * @param object is the class or field.
   * @throws ValueException if a value was illegal.
   * @throws XMLStreamException if the XML was illegal.
   */
  protected void loadBasicFields(XMLStreamReader xmlReader, AbstractContentReflectionObject object)
      throws ValueException, XMLStreamException {

    // parse ID
    SmartId uid = parseId(xmlReader);
    object.setId(uid);
    // parse name
    String name = parseName(xmlReader);
    object.setName(name);
    // parse deleted flag
    boolean deleted = parseDeletedFlag(xmlReader);
    object.setDeleted(deleted);
  }

  /**
   * This method reads a class-hierarchy from the given <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * <ul>
   * <li>The order of the elements in the XML is important to this method.
   * Field elements have to occur after Class elements on the same level in the
   * XML tree.</li>
   * <li>The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized classes and fields is NOT set by this method so it may be
   * <code>null</code> if NOT initialized via the
   * {@link net.sf.mmm.content.model.api.ContentModelService model-service}.</li>
   * </ul>
   * 
   * @param xmlReader is where to read the XML from.
   * @param superClass is the super-class or <code>null</code> when loading
   *        the root-class.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public AbstractContentClass loadClassRecursive(XMLStreamReader xmlReader,
      AbstractContentClass superClass) throws ValueException, XMLStreamException {

    assert (xmlReader.isStartElement());
    assert (ContentClass.XML_TAG_CLASS.equals(xmlReader.getLocalName()));
    // parse class attributes...
    SmartId id = parseId(xmlReader);
    String name = parseName(xmlReader);
    boolean deleted = parseDeletedFlag(xmlReader);
    // parse modifier
    boolean isFinal = parseAttribute(xmlReader, Modifiers.XML_ATR_ROOT_FINAL, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isAbstract = parseAttribute(xmlReader, ClassModifiers.XML_ATR_ROOT_ABSTRACT,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isExtendable = parseAttribute(xmlReader, ClassModifiers.XML_ATR_ROOT_EXTENDABLE,
        Boolean.class, Boolean.valueOf(!isFinal)).booleanValue();
    boolean isSystem = parseAttribute(xmlReader, Modifiers.XML_ATR_ROOT_SYSTEM, Boolean.class,
        Boolean.FALSE).booleanValue();
    ClassModifiers modifiers = ClassModifiersImpl.getInstance(isSystem, isFinal, isAbstract,
        isExtendable);
    AbstractContentClass contentClass = getContentModelService().createOrUpdateClass(id, name,
        superClass, modifiers, deleted, null);
    // parse XML-child elements
    int xmlEvent = xmlReader.nextTag();
    while (XMLStreamConstants.START_ELEMENT == xmlEvent) {
      String tagName = xmlReader.getLocalName();
      if (ContentClass.XML_TAG_CLASS.equals(tagName)) {
        // class
        // AbstractContentClass childClass =
        loadClassRecursive(xmlReader, contentClass);
        // childClass.setSuperClass(contentClass);
        // contentClass.addSubClass(childClass);
      } else if (ContentField.XML_TAG_FIELD.equals(tagName)) {
        // field
        loadField(xmlReader, contentClass);
        // contentClass.addField(contentField);
      } else {
        parseClassChildElement(xmlReader, contentClass);
      }
      xmlEvent = xmlReader.nextTag();
    }
    return contentClass;
  }

  /**
   * This method parses the XML at the point where an unknown child-element of a
   * Class element was hit. The method has to consume this element including all
   * its children and point to the end of the unknown element.
   * 
   * @param xmlReader is where to read the XML from.
   * @param superClass is the class that owns the unknown child-element.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  protected void parseClassChildElement(XMLStreamReader xmlReader, AbstractContentClass superClass)
      throws XMLStreamException {

    StaxUtil.skipOpenElement(xmlReader);
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
  public AbstractContentField loadField(XMLStreamReader xmlReader,
      AbstractContentClass declaringClass) throws ValueException, XMLStreamException {

    assert (xmlReader.isStartElement());
    assert (ContentField.XML_TAG_FIELD.equals(xmlReader.getLocalName()));
    SmartId id = parseId(xmlReader);
    String name = parseName(xmlReader);
    boolean deleted = parseDeletedFlag(xmlReader);
    // parse modifier
    boolean isFinal = parseAttribute(xmlReader, Modifiers.XML_ATR_ROOT_FINAL, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isReadOnly = parseAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_READ_ONLY,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isStatic = parseAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_STATIC, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isSystem = declaringClass.getModifiers().isSystem();
    boolean isTransient = parseAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_TRANSIENT,
        Boolean.class, Boolean.FALSE).booleanValue();
    FieldModifiers modifiers = FieldModifiersImpl.getInstance(isSystem, isFinal, isReadOnly,
        isStatic, isTransient);
    // parse type
    String typeSpecification = parseAttribute(xmlReader, ContentField.XML_ATR_FIELD_TYPE,
        String.class);
    Type fieldType = parseFieldType(typeSpecification);
    AbstractContentField contentField = getContentModelService().createOrUpdateField(id, name,
        declaringClass, modifiers, fieldType, typeSpecification, deleted);
    xmlReader.nextTag();
    if (xmlReader.isStartElement()) {
      StaxUtil.skipOpenElement(xmlReader);
    }
    return contentField;
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
  public AbstractContentClass loadModel(XMLStreamReader xmlReader) throws ValueException,
      XMLStreamException {

    // TODO: how should this work:
    // 1. load complete class tree without modifying the model
    // and add the tree or update existing tree after validation as
    // "transaction".
    // 2. while loading the classes all changes directly effect the
    // content-model. If an error occurs while reloading there is no
    // "transaction" and maybe only parts of the model are loaded.
    assert (xmlReader.isStartElement());
    if (!ContentClass.XML_TAG_CONTENT_MODEL.equals(xmlReader.getLocalName())) {
      // TODO:
      throw new IllegalArgumentException("wrong tag for content-model: " + xmlReader.getLocalName());
    }
    AbstractContentClass rootClass = null;
    while (XMLStreamConstants.START_ELEMENT == xmlReader.nextTag()) {
      if (ContentClass.XML_TAG_CLASS.equals(xmlReader.getLocalName())) {
        if (rootClass != null) {
          // TODO:
          throw new IllegalArgumentException("Multiple root-classes in contentmodel!");
        }
        rootClass = loadClassRecursive(xmlReader, null);
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
   * 
   * TODO: javadoc
   * 
   * @param contentClass
   */
  protected void initializeModel(ContentClass contentClass) {

    for (ContentField contentField : contentClass.getDeclaredFields()) {
      // TODO: only parse if changed
      String typeSpecification = contentField.getFieldTypeSpecification();
      Type type = parseFieldType(typeSpecification);
      ((AbstractContentField) contentField).setFieldTypeAndClass(type);
    }
    for (ContentClass subClass : contentClass.getSubClasses()) {
      initializeModel(subClass);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void loadClasses(DataResource resource) throws IOException, ContentModelException {

    XMLInputFactory factory = XMLInputFactory.newInstance();
    XMLStreamReader xmlReader = null;
    try {
      xmlReader = new XIncludeStreamReader(factory, resource);
      xmlReader.nextTag();
      ContentClass rootClass = loadModel(xmlReader);
      initializeModel(rootClass);
    } catch (XMLStreamException e) {
      throw new ContentModelException("Failed to parse content-model from " + resource.getPath(), e);
    } finally {
      try {
        if (xmlReader != null) {
          xmlReader.close();
        }
      } catch (XMLStreamException e) {
        throw new ContentModelException("Failed to parse content-model from " + resource.getPath(),
            e);
      }
    }
  }

}
