/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.lang.reflect.Type;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionObject;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.data.api.reflection.DataClassLoader} interface that allows
 * to load (de-serialize) {@link DataClass}es from XML using StAX.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataClassLoaderStAX extends DataClassLoaderNative {

  /**
   * The constructor.
   * 
   * @param contentModelService is the {@link #getDataReflectionService()
   *        content-model service}.
   */
  public DataClassLoaderStAX(AbstractMutableDataReflectionService contentModelService) {

    super(contentModelService);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void parseConfiguration(XMLStreamReader xmlReader, Context context)
      throws XMLStreamException {

    String tagName = xmlReader.getLocalName();
    if (DataReflectionService.XML_TAG_CLASS.equals(tagName)) {
      loadClassRecursive(xmlReader, context);
    }
    super.parseConfiguration(xmlReader, context);
  }

  /**
   * This method parses the type given by <code>typeString</code>.
   * 
   * @param typeSpecification is the type given as string (e.g.
   *        <code>String</code> or <code>List&lt;ContentObject&gt;</code>).
   * @param classResolver is the resolver used to resolve the classes.
   * @return the parsed type.
   */
  protected GenericType<?> parseFieldType(String typeSpecification, ClassResolver classResolver) {

    Type type = getReflectionUtil().toType(typeSpecification, classResolver);
    return getReflectionUtil().createGenericType(type);
  }

  /**
   * This method reads the ID from the given <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the ID of the class or field.
   */
  protected Long parseId(XMLStreamReader xmlReader) {

    return getStaxUtil().parseAttribute(xmlReader, null, DataObjectView.FIELD_NAME_ID, Long.class);
  }

  /**
   * This method reads the {@link DataObjectView#getTitle() name} from the given
   * <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the name of the class or field.
   */
  protected String parseTitle(XMLStreamReader xmlReader) {

    return getStaxUtil().parseAttribute(xmlReader, null, DataObjectView.FIELD_NAME_TITLE, String.class);
  }

  /**
   * This method reads the {@link DataReflectionObject#getDeletedFlag() deleted
   * flag} from the given <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the deleted-flag of the class or field.
   */
  protected boolean parseDeletedFlag(XMLStreamReader xmlReader) {

    return getStaxUtil().parseAttribute(xmlReader, null,
        DataReflectionObject.FIELD_NAME_DELETEDFLAG, Boolean.class, Boolean.FALSE).booleanValue();
  }

  /**
   * This method reads a class-hierarchy from the given <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * <ul>
   * <li>The order of the elements in the XML is important to this method. Field
   * elements have to occur after Class elements on the same level in the XML
   * tree.</li>
   * <li>The {@link DataClass} of the de-serialized classes and fields is NOT
   * set by this method so it may be <code>null</code> if NOT initialized via
   * the {@link net.sf.mmm.data.api.reflection.DataReflectionService
   * model-service}.</li>
   * </ul>
   * 
   * @param xmlReader is where to read the XML from.
   * @param context is where to add the configured classes to.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public AbstractDataClass<? extends DataObjectView> loadClassRecursive(XMLStreamReader xmlReader,
      Context context) throws ValueException, XMLStreamException {

    assert (xmlReader.isStartElement());
    assert (DataClass.XML_TAG_CLASS.equals(xmlReader.getLocalName()));
    // parse class attributes...
    Long id = parseId(xmlReader);
    String title = parseTitle(xmlReader);
    boolean deleted = parseDeletedFlag(xmlReader);
    // parse modifier
    boolean isSystem = getStaxUtil().parseAttribute(xmlReader, null, DataModifiers.XML_ATR_SYSTEM,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isFinal = getStaxUtil().parseAttribute(xmlReader, null, DataModifiers.XML_ATR_FINAL,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isAbstract = getStaxUtil().parseAttribute(xmlReader, null,
        DataClassModifiers.XML_ATR_ABSTRACT, Boolean.class, Boolean.FALSE).booleanValue();
    // default value for extendable is...
    boolean isExtendable = !(isFinal || (isSystem && isAbstract));
    // configured value is therefore...
    isExtendable = getStaxUtil().parseAttribute(xmlReader, null,
        DataClassModifiers.XML_ATR_EXTENDABLE, Boolean.class, Boolean.valueOf(isExtendable))
        .booleanValue();
    DataClassModifiers modifiers = DataClassModifiersBean.getInstance(isSystem, isFinal,
        isAbstract, isExtendable);

    AbstractDataClass<? extends DataObjectView> dataClass = context.getDataClass(id.longValue());
    if (dataClass == null) {
      dataClass = getDataReflectionService().createDataClass(id, title, null, modifiers, null,
          deleted);
    } else {
      if (!title.equals(dataClass.getTitle())) {
        throw new ObjectMismatchException(title, dataClass.getTitle(), dataClass,
            DataObjectView.FIELD_NAME_TITLE);
      }
      // TODO ...
    }

    String javaClassName = xmlReader.getAttributeValue(null, "javaClass");
    if (javaClassName != null) {
      Class javaClass = getClassResolver().resolveClass(javaClassName);
      Class<?> oldClass = dataClass.getJavaClass();
      if ((oldClass != null) && (!oldClass.equals(javaClass))) {
        throw new ObjectMismatchException(javaClass, oldClass, dataClass,
            DataClass.FIELD_NAME_JAVA_CLASS);
      }
      dataClass.setJavaClass(javaClass);
    }

    // parse XML-child elements
    int xmlEvent = xmlReader.nextTag();
    while (XMLStreamConstants.START_ELEMENT == xmlEvent) {
      String tagName = xmlReader.getLocalName();
      if (DataClass.XML_TAG_CLASS.equals(tagName)) {
        AbstractDataClass subClass = loadClassRecursive(xmlReader, context);
        subClass.setSuperClass(dataClass);
        dataClass.addSubClass(subClass);
      } else if (DataField.XML_TAG_FIELD.equals(tagName)) {
        // field
        AbstractDataField contentField = loadField(xmlReader, dataClass);
        dataClass.addDeclaredField(contentField);
      } else {
        parseClassChildElement(xmlReader, dataClass);
      }
      xmlEvent = xmlReader.nextTag();
    }
    return dataClass;
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
  protected void parseClassChildElement(XMLStreamReader xmlReader,
      AbstractDataClass<? extends DataObjectView> superClass) throws XMLStreamException {

    getStaxUtil().skipOpenElement(xmlReader);
  }

  /**
   * This method reads a {@link DataField} from the given <code>xmlReader</code>
   * .<br>
   * <b>ATTENTION:</b><br>
   * The {@link DataClass} of the de-serialized field is NOT set by this method
   * so it may be <code>null</code> if NOT initialized.
   * 
   * @param xmlReader is where to read the XML from.
   * @param declaringClass is the class declaring this field.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public AbstractDataField<? extends DataObjectView, ?> loadField(XMLStreamReader xmlReader,
      AbstractDataClass<? extends DataObjectView> declaringClass) throws ValueException,
      XMLStreamException {

    assert (xmlReader.isStartElement());
    assert (DataField.XML_TAG_FIELD.equals(xmlReader.getLocalName()));
    Long id = parseId(xmlReader);
    String name = parseTitle(xmlReader);
    boolean deleted = parseDeletedFlag(xmlReader);
    // parse modifier
    boolean isFinal = getStaxUtil().parseAttribute(xmlReader, null, DataModifiers.XML_ATR_FINAL,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isReadOnly = getStaxUtil().parseAttribute(xmlReader, null,
        DataFieldModifiers.XML_ATR_READ_ONLY, Boolean.class, Boolean.FALSE).booleanValue();
    boolean isStatic = getStaxUtil().parseAttribute(xmlReader, null,
        DataFieldModifiers.XML_ATR_STATIC, Boolean.class, Boolean.FALSE).booleanValue();
    boolean isSystem = declaringClass.getModifiers().isSystem();
    boolean isTransient = getStaxUtil().parseAttribute(xmlReader, null,
        DataFieldModifiers.XML_ATR_TRANSIENT, Boolean.class, Boolean.FALSE).booleanValue();
    boolean isInheritedFromParent = getStaxUtil().parseAttribute(xmlReader, null,
        DataFieldModifiers.XML_ATR_INHERITED_FROM_PARENT, Boolean.class, Boolean.FALSE)
        .booleanValue();
    DataFieldModifiers modifiers = DataFieldModifiersBean.getInstance(isSystem, isFinal,
        isReadOnly, isStatic, isTransient, isInheritedFromParent);
    // parse type
    String typeSpecification = getStaxUtil().parseAttribute(xmlReader, null,
        DataField.XML_ATR_FIELD_TYPE, String.class);
    GenericType<?> fieldType = parseFieldType(typeSpecification, getClassResolver());
    // fieldType = parseFieldType(typeSpecification, getClassResolver());
    AbstractDataField<? extends DataObjectView, ?> contentField = getDataReflectionService()
        .createDataField(id, name, declaringClass, fieldType, modifiers, deleted);
    // AbstractContentField contentField =
    // getContentModelService().createOrUpdateField(id, name,
    // declaringClass, modifiers, fieldType, typeSpecification, deleted);
    xmlReader.nextTag();
    if (xmlReader.isStartElement()) {
      getStaxUtil().skipOpenElement(xmlReader);
    }
    return contentField;
  }

  /**
   * 
   * TODO: javadoc
   * 
   * @param dataClass
   * @param resolver
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected void initializeModel(AbstractDataClass<? extends DataObjectView> dataClass,
      ClassResolver resolver) {

    for (AbstractDataField contentField : dataClass.getDeclaredFields()) {
      String typeSpecification = contentField.getFieldTypeSpecification();
      GenericType<?> type = parseFieldType(typeSpecification, resolver);
      contentField.setFieldType(type);
    }
    for (AbstractDataClass<? extends DataObjectView> subClass : dataClass.getSubClasses()) {
      initializeModel(subClass, resolver);
    }
  }

}
