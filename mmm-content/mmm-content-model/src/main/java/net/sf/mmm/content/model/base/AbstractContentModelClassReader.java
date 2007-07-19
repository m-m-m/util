/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;

import javax.annotation.Generated;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.content.model.base.FieldModifiersImpl;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.content.value.impl.ClassId;
import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.impl.FieldPojoDescriptorBuilder;
import net.sf.mmm.util.pojo.impl.PublicMethodPojoDescriptorBuilder;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentModelClassReader {

  /** @see #readClass(Class) */
  private PojoDescriptorBuilder methodDescriptorBuilder = new PublicMethodPojoDescriptorBuilder();

  /** @see #readClass(Class) */
  private PojoDescriptorBuilder fieldDescriptorBuilder = new FieldPojoDescriptorBuilder();

  /**
   * The constructor.
   */
  public AbstractContentModelClassReader() {

    super();
    this.methodDescriptorBuilder = new PublicMethodPojoDescriptorBuilder();
    this.fieldDescriptorBuilder = new FieldPojoDescriptorBuilder();
  }

  protected String getClassName(Class<?> type) {

    // TODO: check that field is NOT accidently inherited
    String name = ReflectionUtil.getStaticFieldOrNull(type, "CLASS_NAME", String.class, true);
    if (name == null) {
      name = type.getSimpleName();
    }
    return name;
  }

  protected int getClassId(Class<?> type) {

    // TODO: check that field is NOT accidently inherited
    Number id = ReflectionUtil.getStaticFieldOrNull(type, "CLASS_ID", Number.class, true);
    if (id == null) {
      // TODO!
    }
    if (id.doubleValue() != id.intValue()) {
      throw new IllegalArgumentException("Id (" + id.doubleValue() + ") must be an integer value!");
    }
    return id.intValue();
  }

  protected boolean isClassFinal(Class<?> type) {

    return Modifier.isFinal(type.getModifiers());
  }

  protected boolean isClassAbstract(Class<?> type) {

    return Modifier.isAbstract(type.getModifiers());
  }

  protected boolean isClassSystem(Class<?> type) {

    // boolean isSystem = !((type instanceof ContentDocument) && (type !=
    // ContentDocument.class));
    return false;
  }

  protected boolean isClassExtendable(Class<?> type) {

    // TODO: unextendable system classes...
    return !isClassFinal(type);
  }

  protected boolean isClassDeleted(Class<?> type) {

    return type.isAnnotationPresent(Deprecated.class);
  }

  protected boolean isFieldDeleted(PojoPropertyDescriptor methodPropertyDescriptor) {

    AccessibleObject getter = methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.READ)
        .getAccessibleObject();
    return getter.isAnnotationPresent(Deprecated.class);
  }

  protected boolean isFieldFinal(PojoPropertyDescriptor methodPropertyDescriptor) {

    int modifiers = methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.READ)
        .getModifiers();
    return Modifier.isFinal(modifiers);
  }

  protected boolean isFieldReadOnly(PojoPropertyDescriptor methodPropertyDescriptor) {

    PojoPropertyAccessor accessor = methodPropertyDescriptor
        .getAccessor(PojoPropertyAccessMode.WRITE);
    return (accessor == null);
  }

  protected boolean isFieldStatic(PojoPropertyDescriptor methodPropertyDescriptor) {

    int modifiers = methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.READ)
        .getModifiers();
    return Modifier.isStatic(modifiers);
  }

  protected boolean isFieldTransient(PojoPropertyDescriptor methodPropertyDescriptor) {

    int modifiers = methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.READ)
        .getModifiers();
    // TODO: this does NOT make sense! we need to get the modifiers of the
    // field!
    return Modifier.isTransient(modifiers);
  }

  protected abstract AbstractContentClass createClass(Class<? extends ContentObject> type);

  protected abstract AbstractContentField createField(
      PojoPropertyDescriptor methodPropertyDescriptor);

  public AbstractContentClass readClass(Class<? extends ContentObject> type) {

    // TODO: first read all classes and build hierarchy and the read fields in
    // second run!

    AbstractContentClass contentClass = createClass(type);
    contentClass.setName(getClassName(type));
    contentClass.setDeletedFlag(isClassDeleted(type));
    ClassModifiers classModifiers = ClassModifiersImpl.getInstance(isClassSystem(type),
        isClassFinal(type), isClassAbstract(type), isClassExtendable(type));
    contentClass.setModifiers(classModifiers);
    SmartId classId;
    // TODO:
    SmartIdManager idManager = null;
    int id = getClassId(type);
    // classId = idManager.getClassId(id);
    classId = ClassId.valueOf(id);
    contentClass.setId(classId);
    PojoDescriptor<? extends ContentObject> methodPojoDescriptor = this.methodDescriptorBuilder
        .getDescriptor(type);
    PojoDescriptor<? extends ContentObject> fieldPojoDescriptor = this.fieldDescriptorBuilder
        .getDescriptor(type);
    for (PojoPropertyDescriptor methodPropertyDescriptor : methodPojoDescriptor
        .getPropertyDescriptors()) {
      PojoPropertyAccessor accessor = methodPropertyDescriptor
          .getAccessor(PojoPropertyAccessMode.READ);
      if (accessor != null) {
        // the accessor might be inherited from an interface...
        // we rather need to check that the parent class does not have this
        // field
        // with the same accessible object...
        if (accessor.getDeclaringClass() == type) {
          AbstractContentField contentField = createField(methodPropertyDescriptor);
          contentField.setName(accessor.getName());
          contentField.setDeletedFlag(isFieldDeleted(methodPropertyDescriptor));
          contentField.setDeclaringClass(contentClass);
          // TODO: read-only: what if getter is present but setter is only
          // available in subclass? -> override field in subclass as NOT
          // read-only! --> also check setters?
          // TODO: transient: only noteable in implementing class, declare as
          // "abstract" field above?
          FieldModifiers fieldModifiers = FieldModifiersImpl.getInstance(classModifiers.isSystem(),
              isFieldFinal(methodPropertyDescriptor), isFieldReadOnly(methodPropertyDescriptor),
              isFieldStatic(methodPropertyDescriptor), isFieldTransient(methodPropertyDescriptor));
          contentField.setModifiers(fieldModifiers);
          PojoPropertyDescriptor fieldPropertyDescriptor = fieldPojoDescriptor
              .getPropertyDescriptor(methodPropertyDescriptor.getName());
          if (fieldPropertyDescriptor != null) {

          }
        }
      }
    }
    return contentClass;
  }

}
