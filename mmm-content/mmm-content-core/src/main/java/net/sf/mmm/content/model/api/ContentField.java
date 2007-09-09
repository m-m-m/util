/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import java.lang.reflect.Type;
import java.util.List;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.value.validator.api.ValueValidator;

/**
 * This interface declares the api of a field in the content-model. Such object
 * describes the structure of a field of a
 * {@link net.sf.mmm.content.model.api.ContentClass}.<br>
 * <b>ATTENTION:</b><br>
 * Do NOT get confused in comparison with {@link java.lang.reflect.Field fields}
 * in the java language specification. Better think of a {@link ContentField} as
 * something like a java {@link java.lang.reflect.Method method} or even more
 * precise of a Java-Bean property.
 * 
 * @see FieldModifiers
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentField extends ContentReflectionObject {

  /** the name of the {@link #getContentClass() class} reflecting this type. */
  String CLASS_NAME = "ContentField";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 3;

  /** the xml tag for a {@link ContentField}. */
  String XML_TAG_FIELD = "Field";

  /** the attribute for the {@link #getFieldType() type}. */
  String XML_ATR_FIELD_TYPE = "type";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getFieldType() fieldType} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_FIELD_TYPE = "fieldType";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getFieldClass() fieldClass} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_FIELD_CLASS = "fieldClass";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getDeclaringClass() declaringClass} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_DECLARING_CLASS = "declaringClass";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getInitiallyDefiningClass() initiallyDefiningClass} for generic
   * access via {@link #getValue(String)}.
   */
  String FIELD_NAME_INITIALLY_DEFINING_CLASS = "initiallyDefiningClass";

  /**
   * {@inheritDoc}
   * 
   * @return the {@link #getDeclaringClass() declaring-class}.
   */
  ContentField getParent();

  /**
   * {@inheritDoc}
   * 
   * @return the sub-fields of this field.
   */
  public List<? extends ContentField> getChildren();
  
  /**
   * This method gets the content-class that declares this field. This does NOT
   * mean that the field is
   * {@link #getInitiallyDefiningClass() initially defined} by that class. It
   * can also be that the declaring class inherits the field from a
   * {@link ContentClass#getSuperClass() super-class} but overrides it (if
   * supported by the {@link ContentModelService content-model}) in order to
   * declare it more specific meaning that the type of the field is a subtype of
   * the field that is overridden or the validator is more restrictive.<br>
   * 
   * @return the class that declares this field.
   */
  ContentClass getDeclaringClass();

  /**
   * This method gets the content-class that initially defined by this field.
   * This means that the returned content-class does not inherit this field and
   * its parent class (if not root) has no field with the same
   * {@link #getName() name}.
   * 
   * @return the class that initially defines this field.
   */
  ContentClass getInitiallyDefiningClass();

  /**
   * This method gets the super-field of this field if this field is extended. A
   * field defined in a class can be extended in a sub-class. The extended field
   * may have a more specific type.
   * 
   * @return the field extended by this field or <code>null</code> if the
   *         field is NOT inherited.
   */
  ContentField getSuperField();

  /**
   * This method gets the specification of the fields type. This is the most
   * precise description of the fields type.
   * 
   * @see #getFieldType()
   * 
   * @return the type specification.
   */
  String getFieldTypeSpecification();

  /**
   * This method gets the value type of the field. Only values of this type can
   * be stored in this field. According to the content-model different
   * {@link ContentObject entities} may be realized by the same Java
   * {@link Class}.
   * 
   * @see #getFieldClass()
   * 
   * @return the type of this field.
   */
  Type getFieldType();

  /**
   * This method gets the {@link Class raw-type} of the
   * {@link #getFieldType() field-type}.
   * 
   * @return the raw-type of this field.
   */
  Class<?> getFieldClass();

  /**
   * {@inheritDoc}
   */
  FieldModifiers getModifiers();

  /**
   * This method gets the constraint a value of this field must fulfill in order
   * to be valid. <br>
   * The constraint will validate that the value has the
   * {@link #getFieldType() specified field-type}. A constraint may also verify
   * additional things. E.g. that the value
   * <ul>
   * <li>is NOT <code>null</code>.</li>
   * <li>does NOT exceed a specific length (e.g. for strings or lists).</li>
   * <li>is a {@link java.util.List list} that only contains entities of a
   * specific {@link #getContentClass() type}.</li>
   * </ul>
   * 
   * @see net.sf.mmm.value.validator.api.ValueValidator
   * 
   * @return the constraint.
   */
  ValueValidator getConstraint();

  /**
   * This method gets the accessor used to read or write the field. According to
   * the fields {@link #getModifiers() modifiers} the field may be
   * {@link FieldModifiers#isReadOnly() read-only}. Then the write access will
   * NOT be allowed and therefore fail.
   * 
   * @return the accessor.
   */
  ContentAccessor getAccessor();

}
