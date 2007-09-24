/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.Type;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the interface for the factory to create
 * {@link net.sf.mmm.content.model.api.ContentReflectionObject reflection-objects}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentReflectionFactory {

  /**
   * This method creates a new content-class.
   * 
   * @param id is the {@link AbstractContentClass#getId() id}.
   * @param name is the {@link AbstractContentClass#getName() name}.
   * @return the created class.
   */
  AbstractContentClass createNewClass(SmartId id, String name);

  /**
   * This method creates a new content-class.
   * 
   * @param id is the {@link AbstractContentClass#getId() id}.
   * @param name is the {@link AbstractContentClass#getName() name}.
   * @param superClass is the
   *        {@link net.sf.mmm.content.model.api.ContentClass#getSuperClass() super-class}
   *        of the class. May be <code>null</code> if the root-class is to be
   *        created/updated.
   * @param modifiers are the
   *        {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   *        of the class.
   * @param javaClass is the associated
   *        {@link net.sf.mmm.content.model.api.ContentClass#getJavaClass() java-class}
   *        or <code>null</code>.
   * @param deleted is the
   *        {@link net.sf.mmm.content.model.api.ContentClass#isDeleted() deleted flag}
   *        of the class.
   * @return the created class.
   */
  AbstractContentClass createNewClass(SmartId id, String name, AbstractContentClass superClass,
      ClassModifiers modifiers, Class<? extends ContentObject> javaClass, boolean deleted);

  /**
   * This method creates a new content-field.
   * 
   * @param id is the {@link AbstractContentField#getId() id}.
   * @param name is the {@link AbstractContentField#getName() name}.
   * @return the created field.
   */
  AbstractContentField createNewField(SmartId id, String name);

  /**
   * This method creates a new content-field.
   * 
   * @param id is the {@link AbstractContentField#getId() id}.
   * @param name is the {@link AbstractContentField#getName() name}.
   * @param declaringClass is the {@link ContentField#getDeclaringClass() class}
   *        where the new field will be added.
   * @param type is the {@link ContentField#getFieldType() type} of the values
   *        that can be stored in the field to create.
   * @param modifiers are the {@link ContentField#getModifiers() modifiers} for
   *        the field to create. They must NOT be
   *        {@link FieldModifiers#isTransient() transient}.
   * @param deleted is the
   *        {@link net.sf.mmm.content.model.api.ContentField#isDeleted() deleted flag}
   *        of the field.
   * @return the created field.
   */
  AbstractContentField createNewField(SmartId id, String name, ContentClass declaringClass,
      Type type, FieldModifiers modifiers, boolean deleted);

}
