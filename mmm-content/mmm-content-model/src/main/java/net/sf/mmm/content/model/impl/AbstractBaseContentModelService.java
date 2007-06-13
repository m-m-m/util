/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.lang.reflect.Type;
import java.util.List;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.Modifiers;
import net.sf.mmm.content.model.api.MutableContentModelService;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.model.base.AbstractContentModelService;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.model.base.FieldModifiersImpl;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.util.collection.SizedIterable;
import net.sf.mmm.value.validator.api.ValueValidator;

/**
 * This is the basic implementation of the {@link MutableContentModelService}
 * interface. It does NOT persist the model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractBaseContentModelService extends AbstractContentModelService {

  /** @see #getRootClass() */
  private final ContentClassImpl rootClass;

  /** @see ContentClass#getClass() */
  private final ContentClassImpl classClass;

  /** @see ContentField#getClass() */
  private final ContentClassImpl fieldClass;

  /**
   * The constructor.
   */
  public AbstractBaseContentModelService() {

    super();
    // create root class
    this.rootClass = new ContentClassImpl(IdImpl.ID_CLASS_ROOT, ContentObject.CLASS_NAME, null,
        ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE);
    int fid = 0;
    // add fields
    this.rootClass.addField(fid++, ContentObject.FIELD_NAME_ID, Id.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    // name can only be modified via repository.rename
    this.rootClass.addField(fid++, ContentObject.FIELD_NAME_NAME, String.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    this.rootClass.addField(fid++, ContentObject.FIELD_NAME_CONTENT_CLASS, ContentClass.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);

    // create reflection-object class
    ContentClassImpl classReflection = new ContentClassImpl(IdImpl.ID_CLASS_REFELCTION,
        ContentReflectionObject.CLASS_NAME, this.rootClass,
        ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE);
    // add fields
    classReflection.addField(fid++, ContentReflectionObject.FIELD_NAME_MODIFIERS, Modifiers.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    this.rootClass.addSubClass(classReflection);

    // create class class
    this.classClass = new ContentClassImpl(IdImpl.ID_CLASS_CLASS, ContentClass.CLASS_NAME,
        classReflection, ClassModifiersImpl.SYSTEM_FINAL);
    // add fields
    this.classClass.addField(fid++, ContentReflectionObject.FIELD_NAME_MODIFIERS,
        ClassModifiers.class, FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    this.classClass.addField(fid++, ContentClass.FIELD_NAME_SUPER_CLASS, ContentClass.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    // TODO: create Type List<? extends ContentClass>
    this.classClass.addField(fid++, ContentClass.FIELD_NAME_SUB_CLASSES, List.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    // TODO: ...
    this.classClass.addField(fid++, ContentClass.FIELD_NAME_FIELDS, SizedIterable.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    // TODO: ...
    this.classClass.addField(fid++, ContentClass.FIELD_NAME_DECLARED_FIELDS, SizedIterable.class,
        FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    classReflection.addSubClass(this.classClass);

    // create field class
    this.fieldClass = new ContentClassImpl(IdImpl.ID_CLASS_FIELD, ContentField.CLASS_NAME,
        classReflection, ClassModifiersImpl.SYSTEM_FINAL);
    // add fields
    this.fieldClass.addField(fid++, ContentReflectionObject.FIELD_NAME_MODIFIERS,
        FieldModifiers.class, FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    classReflection.addSubClass(this.fieldClass);

    addClassRecursive(this.rootClass);
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getRootClass() {

    return this.rootClass;
  }

  /**
   * This is the implementation of the {@link ContentClass} interface.
   */
  public class ContentClassImpl extends AbstractContentClass {

    /** UID for serialization. */
    private static final long serialVersionUID = -1985821250541285370L;

    /**
     * The constructor.
     * 
     * @param classId is the {@link #getId() ID} of the class.
     * @param className is the {@link #getName() name} of the class.
     * @param parentClass is the {@link #getSuperClass() super-class} of the
     *        class or <code>null</code> for creating the root-class.
     * @param classModifiers are the {@link #getModifiers() modifiers}.
     */
    public ContentClassImpl(IdImpl classId, String className, ContentClass parentClass,
        ClassModifiers classModifiers) {

      super(classId, className, parentClass, classModifiers);
    }

    /**
     * {@inheritDoc}
     */
    public ContentClass getContentClass() {

      return AbstractBaseContentModelService.this.classClass;
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

      ContentFieldImpl field = new ContentFieldImpl(new IdImpl(IdImpl.OID_FIELD, id), name, this,
          type, modifiers);
      addField(field);
      return field;
    }

  }

  /**
   * This is the implementation of the {@link ContentField} interface.
   */
  public class ContentFieldImpl extends AbstractContentField {

    /** UID for serialization. */
    private static final long serialVersionUID = -6504064074715878761L;

    /**
     * The constructor.
     * 
     * @see AbstractContentField#AbstractContentField(IdImpl, String,
     *      ContentClass, Type, FieldModifiers, ValueValidator)
     */
    public ContentFieldImpl(IdImpl fieldId, String fieldName, ContentClass declaringContentClass,
        Type fieldType, FieldModifiers fieldModifiers, ValueValidator validator) {

      super(fieldId, fieldName, declaringContentClass, fieldType, fieldModifiers, validator);
    }

    /**
     * The constructor.
     * 
     * @see AbstractContentField#AbstractContentField(IdImpl, String,
     *      ContentClass, Type, FieldModifiers)
     */
    public ContentFieldImpl(IdImpl fieldId, String fieldName, ContentClass declaringContentClass,
        Type fieldType, FieldModifiers fieldModifiers) {

      super(fieldId, fieldName, declaringContentClass, fieldType, fieldModifiers);
    }

    /**
     * {@inheritDoc}
     */
    public ContentClass getContentClass() {

      return AbstractBaseContentModelService.this.fieldClass;
    }
  }
}
