/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

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
import net.sf.mmm.content.model.base.AbstractContentModelService;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.model.base.FieldModifiersImpl;
import net.sf.mmm.content.validator.api.ValueValidator;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.util.event.ChangeEvent;

/**
 * This is the basic implementation of the {@link MutableContentModelService}
 * interface. It does NOT persist the model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractMutableContentModelService extends AbstractContentModelService implements
    MutableContentModelService {

  /** @see #getRootClass() */
  private final ContentClassImpl rootClass;

  /** @see ContentClass#getClass() */
  private final ContentClassImpl classClass;

  /** @see ContentField#getClass() */
  private final ContentClassImpl fieldClass;

  /**
   * The constructor.
   */
  public AbstractMutableContentModelService() {

    super();
    // create root class
    this.rootClass = new ContentClassImpl(IdImpl.ID_CLASS_ROOT, ContentObject.CLASS_NAME, null,
        ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE);
    addClass(this.rootClass);
    // add fields
    this.rootClass.addField(0, ContentObject.FIELD_NAME_ID, Id.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    // name can only be modified via repository.rename
    this.rootClass.addField(1, ContentObject.FIELD_NAME_NAME, String.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    this.rootClass.addField(2, ContentObject.FIELD_NAME_CLASS, ContentClass.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);

    // create reflection-object class
    ContentClassImpl classReflection = new ContentClassImpl(IdImpl.ID_CLASS_REFELCTION,
        ContentReflectionObject.CLASS_NAME, this.rootClass,
        ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE);
    // add fields
    classReflection.addField(3, ContentReflectionObject.FIELD_NAME_MODIFIERS, Modifiers.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    this.rootClass.addSubClass(classReflection);

    // create class class
    this.classClass = new ContentClassImpl(IdImpl.ID_CLASS_CLASS, ContentClass.CLASS_NAME,
        classReflection, ClassModifiersImpl.SYSTEM_FINAL);
    // add fields
    this.classClass.addField(4, ContentReflectionObject.FIELD_NAME_MODIFIERS, ClassModifiers.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    classReflection.addSubClass(this.classClass);

    // create field class
    this.fieldClass = new ContentClassImpl(IdImpl.ID_CLASS_FIELD, ContentField.CLASS_NAME,
        classReflection, ClassModifiersImpl.SYSTEM_FINAL);
    // add fields
    this.fieldClass.addField(5, ContentReflectionObject.FIELD_NAME_MODIFIERS, FieldModifiers.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    classReflection.addSubClass(this.fieldClass);

  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelReadAccess#getRootClass()
   */
  public ContentClass getRootClass() {

    return this.rootClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#createClass(net.sf.mmm.content.model.api.ContentClass,
   *      java.lang.String, net.sf.mmm.content.model.api.ClassModifiers)
   */
  public AbstractContentClass createClass(ContentClass superClass, String name, ClassModifiers modifiers)
      throws ContentModelException {

    if ((!superClass.getModifiers().isExtendable()) && (!modifiers.isSystem())) {
      throw new ContentModelException(NlsBundleContentModel.ERR_CLASS_NOT_EXTENDABLE, superClass);
    }
    AbstractContentClass newClass = doCreateClass(superClass, name, modifiers);
    ((AbstractContentClass) superClass).addSubClass(newClass);
    addClass(newClass);
    ContentModelEvent event = new ContentModelEvent(newClass, ChangeEvent.Type.ADD);
    fireEvent(event);
    return newClass;
  }

  protected abstract Id createClassId();
  
  protected abstract Id createFieldId();
  
  /**
   * Creates and persists the class.
   * 
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#createClass(net.sf.mmm.content.model.api.ContentClass,
   *      java.lang.String, net.sf.mmm.content.model.api.ClassModifiers)
   */
  protected AbstractContentClass doCreateClass(ContentClass superClass, String name,
      ClassModifiers modifiers) throws ContentModelException {

    Id id = createClassId();
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
      if (!field.getFieldType().isAssignableFrom(type)) {
        throw new ContentModelException("Can not extend " + field + " with incompatible type "
            + type.getName() + "!");
      }
    }
    if (modifiers.isSystem()) {
      throw new ContentModelException("User can not create system field: " + name + "!");
    }
    ContentField newField = doCreateField(declaringClass, name, type, modifiers);
    ((ContentClassImpl) declaringClass).addField(newField);
    fireEvent(new ContentModelEvent(newField, ChangeEvent.Type.ADD));
    return newField;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#createField(net.sf.mmm.content.model.api.ContentClass,
   *      java.lang.String, java.lang.Class,
   *      net.sf.mmm.content.model.api.FieldModifiers)
   */
  protected ContentField doCreateField(ContentClass declaringClass, String name, Class type,
      FieldModifiers modifiers) throws ContentModelException {

    // creation
    Id id = createFieldId();
    ContentField newField = new ContentFieldImpl(id, name, declaringClass, type, modifiers);
    // persist here
    return newField;
  }
  
  /**
   * @see net.sf.mmm.content.model.api.ContentModelWriteAccess#setDeleted(net.sf.mmm.content.model.api.ContentReflectionObject,
   *      boolean)
   */
  public void setDeleted(ContentReflectionObject classOrField, boolean newDeletedFlag)
      throws ContentModelException {

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
   * This is the implementation of the {@link ContentClass} interface.
   */
  public class ContentClassImpl extends AbstractContentClass {

    /** UID for serialization */
    private static final long serialVersionUID = -1985821250541285370L;

    /**
     * The constructor
     * 
     * @param classId
     *        is the {@link #getId() ID} of the class.
     * @param className
     *        is the {@link #getName() name} of the class.
     * @param parentClass
     *        is the {@link #getSuperClass() super-class} of the class or
     *        <code>null</code> for creating the root-class.
     * @param classModifiers
     *        are the {@link #getModifiers() modifiers}.
     */
    public ContentClassImpl(Id classId, String className, ContentClass parentClass,
        ClassModifiers classModifiers) {

      super(classId, className, parentClass, classModifiers);
    }

    /**
     * @see net.sf.mmm.content.api.ContentObject#getContentClass()
     */
    public ContentClass getContentClass() {

      return AbstractMutableContentModelService.this.classClass;
    }

    /**
     * This method is internally used to create initial fields.
     * 
     * @param id
     * @param name
     * @param type
     * @param modifiers
     * @return the created field.
     */
    protected ContentField addField(int id, String name, Class type, FieldModifiers modifiers) {

      ContentField field = new ContentFieldImpl(new IdImpl(IdImpl.OID_FIELD, id), name, this, type,
          modifiers);
      addField(field);
      return field;
    }

  }

  /**
   * This is the implementation of the {@link ContentField} interface.
   */
  public class ContentFieldImpl extends AbstractContentField {

    /** UID for serialization */
    private static final long serialVersionUID = -6504064074715878761L;

    /**
     * The constructor
     * 
     * @see AbstractContentField#AbstractContentField(Id, String, ContentClass,
     *      Class, FieldModifiers, ValueValidator)
     */
    public ContentFieldImpl(Id fieldId, String fieldName, ContentClass declaringContentClass,
        Class fieldType, FieldModifiers fieldModifiers, ValueValidator validator) {

      super(fieldId, fieldName, declaringContentClass, fieldType, fieldModifiers, validator);
    }

    /**
     * The constructor
     * 
     * @see AbstractContentField#AbstractContentField(Id, String, ContentClass,
     *      Class, FieldModifiers)
     */
    public ContentFieldImpl(Id fieldId, String fieldName, ContentClass declaringContentClass,
        Class fieldType, FieldModifiers fieldModifiers) {

      super(fieldId, fieldName, declaringContentClass, fieldType, fieldModifiers);
    }

    /**
     * @see net.sf.mmm.content.api.ContentObject#getContentClass()
     */
    public ContentClass getContentClass() {

      return AbstractMutableContentModelService.this.fieldClass;
    }

  }
}
