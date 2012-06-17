/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.DataSelectionTreeListView;
import net.sf.mmm.data.api.reflection.access.DataFieldAccessor;
import net.sf.mmm.util.lang.api.BooleanEnum;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This interface declares the api of a field in the content-model. Such object
 * describes the structure of a field of a
 * {@link net.sf.mmm.data.api.reflection.DataClass}.<br>
 * <b>ATTENTION:</b><br>
 * Do NOT get confused in comparison with {@link java.lang.reflect.Field fields}
 * in the java language specification. Better think of a {@link DataField} as
 * something like a Java-Bean property (pair of getter and setter
 * {@link java.lang.reflect.Method methods}).
 * 
 * @see DataClass
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @param <FIELD> is the generic type of the value reflected by this field.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataField.CLASS_ID, title = DataField.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataField<CLASS extends DataObjectView, FIELD> extends
    DataReflectionObject<CLASS>, DataFieldAccessor<CLASS, FIELD>,
    DataSelectionTreeListView<DataClass<? extends DataObjectView>> {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_FIELD;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() name} of the
   * {@link DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataField";

  /** the xml tag for a {@link DataField}. */
  String XML_TAG_FIELD = "Field";

  /** the attribute for the {@link #getFieldType() type}. */
  String XML_ATR_FIELD_TYPE = "type";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field}
   * {@link #getFieldType() fieldType} for generic access.
   */
  String FIELD_NAME_FIELD_TYPE = "fieldType";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field}
   * {@link #getDeclaringClass() declaringClass} for generic access.
   */
  String FIELD_NAME_DECLARING_CLASS = "declaringClass";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field}
   * {@link #getInitiallyDefiningClass() initiallyDefiningClass} for generic
   * access.
   */
  String FIELD_NAME_INITIALLY_DEFINING_CLASS = "initiallyDefiningClass";

  /**
   * {@inheritDoc}
   * 
   * @return the {@link #getDeclaringClass() declaring-class}.
   */
  DataClass<? extends DataObjectView> getParent();

  /**
   * This method gets the content-class that declares this field. This does NOT
   * mean that the field is {@link #getInitiallyDefiningClass() initially
   * defined} by that class. It can also be that the declaring class inherits
   * the field from a {@link DataClass#getSuperClass() super-class} but
   * overrides it (if supported by the {@link DataReflectionService
   * content-model} ) in order to declare it more specific meaning that the type
   * of the field is a sub-type of the field that is overridden or the validator
   * is more restrictive.<br>
   * 
   * This method will return the same result as {@link #getParent()} but is a
   * more explicit and stronger typed.
   * 
   * @return the class that declares this field.
   */
  DataClass<CLASS> getDeclaringClass();

  /**
   * This method gets the content-class that initially defined by this field.
   * This means that the returned content-class does not inherit this field and
   * its parent class (if not root) has no field with the same
   * {@link #getTitle() name}.
   * 
   * @return the class that initially defines this field.
   */
  DataClass<? extends DataObjectView> getInitiallyDefiningClass();

  // /**
  // * This method gets the direct sub-fields of this field. The sub-fields are
  // * the {@link DataField fields} that have this field as
  // * {@link #getSuperField() super-field}.
  // *
  // * @return the sub-fields of this field.
  // */
  // List<? extends DataField<? extends CLASS, ? extends FIELD>> getSubFields();

  /**
   * This method gets the super-field of this field if this field is extended. A
   * non-{@link DataFieldModifiers#isFinal() final} field defined in a class can
   * be extended in a sub-class. The extended field may have a more specific
   * type.
   * 
   * @return the field extended by this field or <code>null</code> if the field
   *         is NOT inherited.
   */
  DataField<? extends DataObjectView, ? super FIELD> getSuperField();

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
   * be stored in this field.
   * 
   * @see GenericType#getAssignmentClass()
   * 
   * @return the type of this field.
   */
  GenericType<FIELD> getFieldType();

  /**
   * {@inheritDoc}
   */
  DataFieldModifiers getModifiers();

  // TODO: JSR 303
  // /**
  // * This method gets the constraint a value of this field must fulfill in
  // order
  // * to be valid. <br>
  // * The constraint will validate that the value has the
  // * {@link #getFieldType() specified field-type}. A constraint may also
  // verify
  // * additional things. E.g. that the value
  // * <ul>
  // * <li>is NOT <code>null</code>.</li>
  // * <li>does NOT exceed a specific length (e.g. for strings or lists).</li>
  // * <li>is a {@link java.util.List list} that only contains entities of a
  // * specific {@link #getContentClass() type}.</li>
  // * </ul>
  // *
  // * @see net.sf.mmm.value.validator.api.ValueValidator
  // *
  // * @return the constraint.
  // */
  // ValueValidator getConstraint();

}
