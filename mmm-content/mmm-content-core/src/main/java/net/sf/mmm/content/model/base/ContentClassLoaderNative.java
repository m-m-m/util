/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.impl.MethodPojoDescriptorBuilder;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is an extension of {@link ContentClassLoaderStAX} that also allows to
 * load {@link ContentClass}es from the native Java implementations of
 * entities.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassLoaderNative extends ContentClassLoaderStAX {

  /** */
  public static final String XML_TAG_CLASS_LIST = "Entities";
  
  /** @see #loadClass(Class, AbstractContentClass) */
  private PojoDescriptorBuilder methodDescriptorBuilder;

  /**
   * The constructor.
   * 
   * @param contentModelService
   */
  public ContentClassLoaderNative(AbstractMutableContentModelService contentModelService) {

    super(contentModelService);
    this.methodDescriptorBuilder = new MethodPojoDescriptorBuilder();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void parseClassChildElement(XMLStreamReader xmlReader, AbstractContentClass superClass) throws XMLStreamException {

    super.parseClassChildElement(xmlReader, superClass);
  }

  /**
   * This method determines the {@link ContentClass#getName() name} for the
   * according {@link ContentClass}.
   * 
   * @param type is the type representing the entity.
   * @return the name of the {@link ContentClass} (the entity).
   */
  protected String getClassName(Class<? extends ContentObject> type) {

    // TODO: check that field is NOT accidently inherited
    String name = ReflectionUtil.getStaticFieldOrNull(type, "CLASS_NAME", String.class, true);
    if (name == null) {
      name = type.getSimpleName();
    }
    return name;
  }

  /**
   * This method determines the {@link ContentClass#getId() id} of the according
   * {@link ContentClass}.
   * 
   * @param type is the type representing the entity.
   * @return the ID for the {@link ContentClass} or null if NOT declared.
   */
  protected SmartId getClassId(Class<? extends ContentObject> type) {

    // TODO: check that field is NOT accidently inherited
    Number id = ReflectionUtil.getStaticFieldOrNull(type, "CLASS_ID", Number.class, true);
    if (id == null) {
      return null;
    }
    int classId = id.intValue();
    if (id.doubleValue() != classId) {
      throw new IllegalArgumentException("Id (" + id.doubleValue() + ") must be an integer value!");
    }
    return getContentModelService().getIdManager().getClassId(classId);
  }

  /**
   * This method determines if the {@link ContentClass} for the given
   * <code>type</code> should be {@link ClassModifiers#isFinal() final}.
   * 
   * @param type is the type representing the entity.
   * @return the final flag for the {@link ContentClass}.
   */
  protected boolean isClassFinal(Class<? extends ContentObject> type) {

    return Modifier.isFinal(type.getModifiers());
  }

  /**
   * This method determines if the {@link ContentClass} for the given
   * <code>type</code> should be {@link ClassModifiers#isAbstract() abstract}.
   * 
   * @param type is the type representing the entity.
   * @return the abstract flag for the {@link ContentClass}.
   */
  protected boolean isClassAbstract(Class<? extends ContentObject> type) {

    return Modifier.isAbstract(type.getModifiers());
  }

  /**
   * This method determines if the {@link ContentClass} for the given
   * <code>type</code> should have the
   * {@link ClassModifiers#isSystem() system} flag.
   * 
   * @param type is the type representing the entity.
   * @return the system flag for the {@link ContentClass}.
   */
  protected boolean isClassSystem(Class<? extends ContentObject> type) {

    // boolean isSystem = !((type instanceof ContentDocument) && (type !=
    // ContentDocument.class));
    return false;
  }

  /**
   * This method determines if the {@link ContentClass} for the given
   * <code>type</code> should be
   * {@link ClassModifiers#isExtendable() extendable}.
   * 
   * @param type is the type representing the entity.
   * @return the extendable flag for the {@link ContentClass}.
   */
  protected boolean isClassExtendable(Class<? extends ContentObject> type) {

    // TODO: unextendable system classes...
    return !isClassFinal(type);
  }

  /**
   * This method determines if the {@link ContentClass} for the given
   * <code>type</code> should be
   * {@link ContentClass#isDeletedFlagSet() deleted} (=deprecated).
   * 
   * @param type is the type representing the entity.
   * @return the deleted flag for the {@link ContentClass}.
   */
  protected boolean isClassDeleted(Class<? extends ContentObject> type) {

    return type.isAnnotationPresent(Deprecated.class);
  }

  /**
   * This method determines if the {@link ContentField} for the given
   * <code>methodPropertyDescriptor</code> should be
   * {@link ContentField#isDeletedFlagSet() deleted} (=deprecated).
   * 
   * @param methodPropertyDescriptor is the descriptor holding the accessor
   *        methods of the field.
   * @return the deleted flag for the {@link ContentField}.
   */
  protected boolean isFieldDeleted(PojoPropertyDescriptor methodPropertyDescriptor) {

    AccessibleObject getter = methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.READ)
        .getAccessibleObject();
    return getter.isAnnotationPresent(Deprecated.class);
  }

  /**
   * This method determines if the {@link ContentField} for the given
   * <code>methodPropertyDescriptor</code> should be
   * {@link FieldModifiers#isFinal() final}.
   * 
   * @param methodPropertyDescriptor is the descriptor holding the accessor
   *        methods of the field.
   * @return the final flag for the {@link ContentField}.
   */
  protected boolean isFieldFinal(PojoPropertyDescriptor methodPropertyDescriptor) {

    int modifiers = methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.READ)
        .getModifiers();
    return Modifier.isFinal(modifiers);
  }

  /**
   * This method determines if the {@link ContentField} for the given
   * <code>methodPropertyDescriptor</code> should be
   * {@link FieldModifiers#isReadOnly() read-only}.
   * 
   * @param methodPropertyDescriptor is the descriptor holding the accessor
   *        methods of the field.
   * @return the final flag for the {@link ContentField}.
   */
  protected boolean isFieldReadOnly(PojoPropertyDescriptor methodPropertyDescriptor) {

    PojoPropertyAccessor accessor = methodPropertyDescriptor
        .getAccessor(PojoPropertyAccessMode.WRITE);
    if (accessor != null) {
      if (Modifier.isPublic(accessor.getModifiers())) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method determines if the {@link ContentField} for the given
   * <code>methodPropertyDescriptor</code> should be
   * {@link FieldModifiers#isTransient() transient}.
   * 
   * @param methodPropertyDescriptor is the descriptor holding the accessor
   *        methods of the field.
   * @return the transient flag for the {@link ContentField}.
   */
  protected boolean isFieldTransient(PojoPropertyDescriptor methodPropertyDescriptor) {

    PojoPropertyAccessor accessor = methodPropertyDescriptor
        .getAccessor(PojoPropertyAccessMode.READ);
    if (Modifier.isAbstract(accessor.getModifiers())) {
      // if the method is abstract, we do not expect the field to be transient!
      return false;
    }
    accessor = methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.WRITE);
    return (accessor == null);
  }

  /**
   * This method determines if the {@link ContentField} for the given
   * <code>methodPropertyDescriptor</code> should be
   * {@link FieldModifiers#isStatic() static}.
   * 
   * @param methodPropertyDescriptor is the descriptor holding the accessor
   *        methods of the field.
   * @return the static flag for the {@link ContentField}.
   */
  protected boolean isFieldStatic(PojoPropertyDescriptor methodPropertyDescriptor) {

    int modifiers = methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.READ)
        .getModifiers();
    return Modifier.isStatic(modifiers);
  }

  /**
   * This method creates the actual {@link ContentClass} instance choosing the
   * implementation. It does NOT initialize the {@link ContentClass} (except
   * maybe some hidden features that are implementation specific).
   * 
   * @param type is the type representing the entity.
   * @return the created instance of the according {@link ContentClass}.
   */
  protected AbstractContentClass createClass(Class<? extends ContentObject> type) {

    AbstractContentClass newClass = getContentModelService().newClass();
    newClass.setJavaClass(type);
    return newClass;
  }

  /**
   * This method creates the actual {@link ContentField} instance choosing the
   * implementation. It does NOT initialize the {@link ContentField} (except
   * maybe some hidden features that are implementation specific).
   * 
   * @param methodPropertyDescriptor is the descriptor holding the accessor
   *        methods of the field.
   * @return the created instance of the according {@link ContentField}.
   */
  protected AbstractContentField createField(PojoPropertyDescriptor methodPropertyDescriptor) {

    AbstractContentField newField = getContentModelService().newField();
    // TODO: add descriptor
    return newField;
  }

  /**
   * This method reads a {@link ContentClass} from the given <code>type</code>.
   * It acts recursive if the {@link ContentClass#getSuperClass() super-class}
   * has NOT already be read.
   * 
   * @param typeSet is the {@link Set} containing all types for which the
   *        content-class should be read via {@link #loadClasses(Set)}. Some of
   *        them may have already been read.
   * @param type is the current type to read.
   * @return the {@link ContentClass} representing the entity reflected by the
   *         given <code>type</code>.
   */
  protected AbstractContentClass loadClass(Set<Class<? extends ContentObject>> typeSet,
      Class<? extends ContentObject> type) {

    // maybe we already created the content-class before...
    SmartId classId = getClassId(type);
    AbstractContentClass result = getContentModelService().getClass(classId);
    if (result == null) {
      // okay the regular case, we need to create the class...
      // first we need to get or create the parent-class...
      AbstractContentClass parent = null;
      Class parentType = type.getSuperclass();
      // our main loop ascends the super-classes of the type
      do {
        // TODO: currently interfaces are not supported as content-class
        if (parentType == null) {
          // TODO: if no base/root class is given we will end up here!
          // we need to do an isAssignableFrom on ContentObject in main loop
          throw new IllegalStateException("Illegal entity type: " + type.getName());
        }
        // maybe we already have the parent in our map...
        classId = getClassId(parentType);
        if (classId != null) {
          parent = getContentModelService().getClass(classId);
          if (parent == null) {
            // or maybe the parentType is also a content-class to create...
            if (typeSet.contains(parentType)) {
              parent = loadClass(typeSet, parentType);
            }
          } else if (parentType != parent.getJavaClass()) {
            throw new DuplicateClassException(classId);
          }
        }
        parentType = parentType.getSuperclass();
      } while (parent == null);
      result = loadClass(type, parent);
      getContentModelService().addClass(result);
    } else if (result.getJavaClass() != type) {
      throw new DuplicateClassException(classId);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public void loadClasses(Set<Class<? extends ContentObject>> typeSet) {

    // Map<Class, ContentClass> classesMap = new HashMap<Class,
    // ContentClass>(typeSet.size());
    // if (superClass != null) {
    // classesMap.put(superClass.getImplementation(), superClass);
    // }
    for (Class<? extends ContentObject> type : typeSet) {
      loadClass(typeSet, type);
    }
  }

  /**
   * This method reads a {@link ContentClass} from the given <code>type</code>
   * reflecting an entity written in java.
   * 
   * @param type is the type representing the entity.
   * @param superClass is the {@link ContentClass#getSuperClass() super-class}
   *        of the {@link ContentClass} to read.
   * @return the according {@link ContentClass}.
   */
  public AbstractContentClass loadClass(Class<? extends ContentObject> type,
      AbstractContentClass superClass) {

    // TODO: first read all classes and build hierarchy and the read fields in
    // second run!

    AbstractContentClass contentClass = createClass(type);
    contentClass.setSuperClass(superClass);
    contentClass.setJavaClass(type);
    contentClass.setName(getClassName(type));
    contentClass.setDeleted(isClassDeleted(type));
    ClassModifiers classModifiers = ClassModifiersImpl.getInstance(isClassSystem(type),
        isClassFinal(type), isClassAbstract(type), isClassExtendable(type));
    contentClass.setModifiers(classModifiers);
    SmartId classId = getClassId(type);
    if (classId == null) {
      // TODO:
      throw new IllegalArgumentException("Entity " + type.getName() + " does NOT define CLASS_ID!");
    }
    contentClass.setId(classId);
    PojoDescriptor<? extends ContentObject> methodPojoDescriptor = this.methodDescriptorBuilder
        .getDescriptor(type);
    for (PojoPropertyDescriptor methodPropertyDescriptor : methodPojoDescriptor
        .getPropertyDescriptors()) {
      PojoPropertyAccessor accessor = methodPropertyDescriptor
          .getAccessor(PojoPropertyAccessMode.READ);
      if ((accessor != null) && (Modifier.isPublic(accessor.getModifiers()))) {
        ContentField superField = null;
        if (superClass != null) {
          superField = superClass.getField(accessor.getName());
        }
        FieldModifiers fieldModifiers = FieldModifiersImpl.getInstance(classModifiers.isSystem(),
            isFieldFinal(methodPropertyDescriptor), isFieldReadOnly(methodPropertyDescriptor),
            isFieldStatic(methodPropertyDescriptor), isFieldTransient(methodPropertyDescriptor));
        boolean isFieldDeleted = isFieldDeleted(methodPropertyDescriptor);
        Type fieldType = accessor.getPropertyType();
        boolean declareField = true;
        if (superField != null) {
          if (superField.getModifiers().equals(fieldModifiers)
              && (superField.isDeleted() == isFieldDeleted)
              && (fieldType.equals(superField.getFieldType()))) {
            declareField = false;
          }
        }
        if (declareField) {
          AbstractContentField contentField = createField(methodPropertyDescriptor);
          contentField.setName(accessor.getName());
          contentField.setFieldTypeAndClass(fieldType);
          contentField.setDeleted(isFieldDeleted);
          contentField.setDeclaringClass(contentClass);
          contentField.setModifiers(fieldModifiers);
          // TODO: How to get the ID of the field? Use annotation? Use counter
          // and persist the model?
        }
      }
    }
    return contentClass;
  }

}
