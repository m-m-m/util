/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.lang.reflect.Type;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.content.NlsBundleContentModel;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.Modifiers;
import net.sf.mmm.content.model.api.MutableContentModelService;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.model.base.FieldModifiersImpl;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.nls.impl.ResourceMissingException;
import net.sf.mmm.util.event.ChangeEvent;
import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueService;

/**
 * This is the basic implementation of the {@link MutableContentModelService}
 * interface. It does NOT persist the model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractMutableContentModelService extends AbstractBaseContentModelService
    implements MutableContentModelService {

  /** descendant name of attribute <code>id</code> */
  private static final String CFG_ATR_ID = Configuration.NAME_PREFIX_ATTRIBUTE
      + ContentObject.XML_ATR_ROOT_ID;

  /** descendant name of attribute <code>name</code> */
  private static final String CFG_ATR_NAME = Configuration.NAME_PREFIX_ATTRIBUTE
      + ContentObject.XML_ATR_ROOT_NAME;

  /** descendant name of attribute <code>system</code> */
  private static final String CFG_ATR_SYSTEM = Configuration.NAME_PREFIX_ATTRIBUTE
      + Modifiers.XML_ATR_ROOT_SYSTEM;

  /** descendant name of attribute <code>final</code> */
  private static final String CFG_ATR_FINAL = Configuration.NAME_PREFIX_ATTRIBUTE
      + Modifiers.XML_ATR_ROOT_FINAL;

  /** descendant name of attribute <code>abstract</code> */
  private static final String CFG_ATR_ABSTRACT = Configuration.NAME_PREFIX_ATTRIBUTE
      + ClassModifiers.XML_ATR_ROOT_ABSTRACT;

  /** descendant name of attribute <code>extendable</code> */
  private static final String CFG_ATR_EXTENDABLE = Configuration.NAME_PREFIX_ATTRIBUTE
      + ClassModifiers.XML_ATR_ROOT_EXTENDABLE;

  /** descendant name of attribute <code>read-only</code> */
  private static final String CFG_ATR_READ_ONLY = Configuration.NAME_PREFIX_ATTRIBUTE
      + FieldModifiers.XML_ATR_ROOT_READ_ONLY;

  /** descendant name of attribute <code>static</code> */
  private static final String CFG_ATR_STATIC = Configuration.NAME_PREFIX_ATTRIBUTE
      + FieldModifiers.XML_ATR_ROOT_STATIC;

  /** descendant name of attribute <code>transient</code> */
  private static final String CFG_ATR_TRANSIENT = Configuration.NAME_PREFIX_ATTRIBUTE
      + FieldModifiers.XML_ATR_ROOT_TRANSIENT;

  /** descendant name of attribute <code>transient</code> */
  private static final String CFG_ATR_TYPE = Configuration.NAME_PREFIX_ATTRIBUTE
      + ContentField.XML_ATR_ROOT_TYPE;

  /** @see #setDeleted(ContentReflectionObject, boolean) */
  private ValueService valueService;

  /** @see #isEditable() */
  private boolean editable;

  /**
   * The constructor.
   */
  public AbstractMutableContentModelService() {

    super();
    this.editable = true;
    this.valueService = null;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return this.editable;
  }

  /**
   * This method sets the {@link #isEditable() editable-flag} of this service.
   * 
   * @param isEditable
   *        the editable to set
   */
  public void setEditable(boolean isEditable) {

    this.editable = isEditable;
  }

  /**
   * This method injects the value-service required by this implementation.
   * 
   * @param service
   *        the value-service to set
   */
  @Resource
  public void setValueService(ValueService service) {

    this.valueService = service;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   * 
   * @throws Exception
   *         if initialization failed.
   */
  @PostConstruct
  public void initialize() throws Exception {

    if (this.valueService == null) {
      throw new ResourceMissingException("value-service");
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass createClass(ContentClass superClass, String name,
      ClassModifiers modifiers) throws ContentModelException {

    if (modifiers.isSystem()) {
      throw new ContentModelException(NlsBundleContentModel.ERR_CLASS_SYSTEM, superClass);
    }
    if (!superClass.getModifiers().isExtendable()) {
      throw new ContentModelException(NlsBundleContentModel.ERR_CLASS_NOT_EXTENDABLE, superClass);
    }
    AbstractContentClass newClass = doCreateClass(superClass, name, modifiers);
    addClass(newClass);
    ((AbstractContentClass) superClass).addSubClass(newClass);
    ContentModelEvent event = new ContentModelEvent(newClass, ChangeEvent.Type.ADD);
    fireEvent(event);
    return newClass;
  }

  /**
   * This method creates a new ID for a custom {@link ContentClass}.
   * 
   * @see IdImpl#MINIMUM_CUSTOM_CLASS_ID
   * 
   * @return the created ID.
   */
  protected abstract IdImpl createClassId();

  /**
   * This method creates a new ID for a custom {@link ContentField}.
   * 
   * @see IdImpl#MINIMUM_CUSTOM_FIELD_ID
   * 
   * @return the created ID.
   */
  protected abstract IdImpl createFieldId();

  /**
   * Creates and new class and persists it.
   * 
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#createClass(net.sf.mmm.content.model.api.ContentClass,
   *      java.lang.String, net.sf.mmm.content.model.api.ClassModifiers)
   * 
   * @param superClass
   *        is the {@link ContentClass#getSuperClass() super-class} of the class
   *        to create.
   * @param name
   *        is the {@link net.sf.mmm.content.api.ContentObject#getName() name}
   *        of the class to create.
   * @param modifiers
   *        are the {@link ContentClass#getModifiers() modifiers} for the class
   *        to create.
   * @return the created class.
   * @throws ContentModelException
   *         if the class could not be created.
   */
  protected AbstractContentClass doCreateClass(ContentClass superClass, String name,
      ClassModifiers modifiers) throws ContentModelException {

    IdImpl id = createClassId();
    ContentClassImpl newClass = new ContentClassImpl(id, name, superClass, modifiers);
    // persist here
    return newClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#createField(net.sf.mmm.content.model.api.ContentClass,
   *      java.lang.String, java.lang.Class,
   *      net.sf.mmm.content.model.api.FieldModifiers)
   */
  public ContentField createField(ContentClass declaringClass, String name, Class type,
      FieldModifiers modifiers) throws ContentModelException {

    // validation
    ContentField field = declaringClass.getField(name);
    if (field != null) {
      if (field.getModifiers().isFinal()) {
        throw new ContentModelException("Can not extend final field: " + field + "!");
      }
      if (!field.getFieldClass().isAssignableFrom(type)) {
        throw new ContentModelException("Can not extend " + field + " with incompatible type "
            + type.getName() + "!");
      }
    }
    if (modifiers.isSystem()) {
      throw new ContentModelException("User can not create system field: " + name + "!");
    }
    AbstractContentField newField = doCreateField(declaringClass, name, type, modifiers);
    ((ContentClassImpl) declaringClass).addField(newField);
    fireEvent(new ContentModelEvent(newField, ChangeEvent.Type.ADD));
    return newField;
  }

  /**
   * Creates and new class and persists it.
   * 
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#createField(net.sf.mmm.content.model.api.ContentClass,
   *      java.lang.String, java.lang.Class,
   *      net.sf.mmm.content.model.api.FieldModifiers)
   * 
   * @param declaringClass
   *        is the {@link ContentField#getDeclaringClass() class} where the new
   *        field will be added.
   * @param name
   *        is the {@link net.sf.mmm.content.api.ContentObject#getName() name}
   *        of the field to create.
   * @param type
   *        is the {@link ContentField#getFieldType() type} of the values that
   *        can be stored in the field to create.
   * @param modifiers
   *        are the {@link ContentField#getModifiers() modifiers} for the field
   *        to create. They must NOT be
   *        {@link FieldModifiers#isTransient() transient} (@see
   *        #createField(ContentClass, String, Class, FieldModifiersImpl,
   *        Term)).
   * @return the created field.
   * @throws ContentModelException
   *         if the field could not be created.
   */
  protected AbstractContentField doCreateField(ContentClass declaringClass, String name,
      Class type, FieldModifiers modifiers) throws ContentModelException {

    // creation
    IdImpl id = createFieldId();
    AbstractContentField newField = new ContentFieldImpl(id, name, declaringClass, type, modifiers);
    // persist here
    return newField;
  }

  /**
   * {@inheritDoc}
   */
  public void setDeleted(ContentReflectionObject classOrField, boolean newDeletedFlag)
      throws ContentModelException {

    if (classOrField.getModifiers().isSystem()) {
      throw new ContentModelException(NlsBundleContentModel.ERR_DELETE_SYSTEM, classOrField);
    }
    ((AbstractContentObject) classOrField).setDeleted(newDeletedFlag);
    // persist here...

    // Update or Delete?
    if (classOrField.isClass()) {
      fireEvent(new ContentModelEvent((ContentClass) classOrField, ChangeEvent.Type.UPDATE));
    } else {
      fireEvent(new ContentModelEvent((ContentField) classOrField, ChangeEvent.Type.UPDATE));
    }
  }

  /**
   * This method imports a class (recursively including its fields and
   * sub-classes) from the given <code>configuration</code>.<br>
   * If the class(es) to import already exist, they are updated accordingly.
   * Please note that changing IDs, names or modifiers of existing classes is
   * NOT supported.
   * 
   * @param configuration
   *        is the configuration of the class to import.
   * @param superClass
   *        is the {@link ContentClass#getSuperClass() super-class} of the class
   *        to import or <code>null</code> for ROOT-class.
   * @return the imported class.
   * @throws ContentModelException
   *         if the import failed. This will stop the import at the point where
   *         the error occurred. This may result in partial completion of the
   *         import.
   */
  protected AbstractContentClass importClass(Configuration configuration,
      AbstractContentClass superClass) throws ContentModelException {

    // what is our strategy: import as much as possible and log errors or
    // verify as much as possible and only import on successful verification?
    String name = configuration.getDescendant(CFG_ATR_NAME).getValue().getString();

    Integer classId = configuration.getDescendant(CFG_ATR_ID).getValue().getInteger(null);

    boolean isSystem = configuration.getDescendant(CFG_ATR_SYSTEM).getValue().getBoolean(false);
    boolean isFinal = configuration.getDescendant(CFG_ATR_FINAL).getValue().getBoolean(false);
    boolean isAbstract = configuration.getDescendant(CFG_ATR_ABSTRACT).getValue().getBoolean(false);
    boolean isExtendable = configuration.getDescendant(CFG_ATR_EXTENDABLE).getValue().getBoolean(
        !isFinal);
    ClassModifiers modifiers = ClassModifiersImpl.getInstance(isSystem, isFinal, isAbstract,
        isExtendable);

    if ((classId == null) || (classId.intValue() >= IdImpl.MINIMUM_CUSTOM_CLASS_ID)) {
      // do NOT allow to create system classes in user-space
      if (isSystem) {
        throw new ContentModelException(NlsBundleContentModel.ERR_CLASS_SYSTEM, name);
      }
    }

    AbstractContentClass existingClass = getClassOrNull(name);
    AbstractContentClass newClass;
    if (existingClass != null) {
      if (classId != null) {
        if (classId.intValue() != existingClass.getId().getClassId()) {
          throw new ContentModelException("ID can NOT be changed!");
        }
      }
      if (!modifiers.equals(existingClass.getModifiers())) {
        throw new ContentModelException("TODO: changing modifiers NOT implemented!");
      }
      if ((superClass != null) && (!superClass.equals(existingClass.getSuperClass()))) {
        throw new ContentModelException("TODO: changing class hierarchy NOT implemented!");
      }
      newClass = existingClass;
    } else {
      IdImpl id;
      if (classId == null) {
        id = createClassId();
      } else {
        id = new IdImpl(classId.intValue());
      }

      newClass = new ContentClassImpl(id, name, superClass, modifiers);
      addClass(newClass);
    }

    // recurse on sub-classes
    for (Configuration subClassConfig : configuration.getDescendants(ContentClass.CLASS_NAME)) {
      // TODO
      AbstractContentClass subClass = importClass(subClassConfig, newClass);
      if (!newClass.getSubClasses().contains(subClass)) {
        newClass.addSubClass(subClass);
      }
    }

    for (Configuration subClassConfig : configuration.getDescendants(ContentField.CLASS_NAME)) {
      AbstractContentField field = importField(subClassConfig, newClass);
      if (newClass.getField(field.getName()) != field) {
        // TODO
        newClass.addField(field);
      }
    }

    return newClass;
  }

  /**
   * This method imports a field from the given <code>configuration</code>.<br>
   * 
   * @param config
   * @param declaringClass
   * @return
   * @throws ContentModelException
   */
  protected AbstractContentField importField(Configuration config,
      AbstractContentClass declaringClass) throws ContentModelException {

    String name = config.getDescendant(CFG_ATR_NAME).getValue().getString();

    Integer fieldId = config.getDescendant(CFG_ATR_ID).getValue().getInteger(null);

    boolean isSystem = declaringClass.getModifiers().isSystem();
    boolean isFinal = config.getDescendant(CFG_ATR_FINAL).getValue().getBoolean(false);
    boolean isReadOnly = config.getDescendant(CFG_ATR_READ_ONLY).getValue().getBoolean(false);
    boolean isStatic = config.getDescendant(CFG_ATR_STATIC).getValue().getBoolean(false);
    boolean isTransient = config.getDescendant(CFG_ATR_TRANSIENT).getValue().getBoolean(false);
    FieldModifiers modifiers = FieldModifiersImpl.getInstance(isSystem, isFinal, isReadOnly,
        isStatic, isTransient);

    String typeName = config.getDescendant(CFG_ATR_TYPE).getValue().getString();
    ValueManager<?> valueManager = this.valueService.getManager(typeName);
    Type fieldType;
    if (valueManager == null) {
      try {
        fieldType = Class.forName(typeName);
      } catch (ClassNotFoundException e) {
        // TODO: NLS
        throw new ContentModelException(e, "Value-type \"{0}\" can NOT be resolved!", typeName);
      }
    } else {
      fieldType = valueManager.getValueType();
    }

    if ((fieldId == null) || (fieldId.intValue() >= IdImpl.MINIMUM_CUSTOM_FIELD_ID)) {
      // do NOT allow to create system classes in user-space
      if (isSystem) {
        // TODO: NLS
        throw new ContentModelException("System-Field \"{0}\" can NOT be created in user-space!",
            name);
      }
    }

    AbstractContentField existingField = declaringClass.getDeclaredField(name);
    AbstractContentField newField;
    if (existingField != null) {
      if (fieldId != null) {
        if (fieldId.intValue() != existingField.getId().getClassId()) {
          // TODO: NLS
          throw new IllegalStateException("ID can NOT be changed!");
        }
      }
      if (!modifiers.equals(existingField.getModifiers())) {
        throw new IllegalStateException("TODO: changing modifiers NOT implemented!");
      }
      newField = existingField;
    } else {
      IdImpl id;
      if (fieldId == null) {
        id = createClassId();
      } else {
        id = new IdImpl(IdImpl.OID_FIELD, fieldId.intValue());
      }

      newField = new ContentFieldImpl(id, name, declaringClass, fieldType, modifiers);
    }

    return newField;
  }

}
