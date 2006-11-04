/* $Id$ */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.api.ContentObjectIF;

/**
 * This interface declares the api of a content field. Such object describes the
 * structure of a field of a {@link net.sf.mmm.content.model.api.ContentClassIF}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentFieldIF extends ContentReflectionObjectIF {

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentFieldIF field}
   * {@link #getId() ID} for generic access via {@link #getFieldValue(String)}.
   */
  String NAME_ID = "id";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentFieldIF field}
   * {@link #getName() name} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String NAME_NAME = "name";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentFieldIF field}
   * {@link #getParentFolder() parentFolder} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String NAME_PARENT_FOLDER = "parentFolder";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentFieldIF field}
   * {@link #getPath() path} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String NAME_PATH = "path";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentFieldIF field}
   * {@link #getContentClass() class} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String NAME_CLASS = "class";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentFieldIF field}
   * {@link #getMetaData() metadata} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String NAME_METADATA = "metadata";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentFieldIF field}
   * {@link #getFieldType() type} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String NAME_FIELD_TYPE = "type";

  /**
   * This method gets the content-class that declares this field. This does NOT
   * mean that the field is
   * {@link #getInitiallyDefiningClass() initially defined} by that class. It
   * can also be that the declaring class inherits the field from a
   * {@link ContentClassIF#getSuperClass() super-class} but overrides it (if
   * supported by the {@link ContentModelServiceIF content-model}) in order to
   * declare it more specific meaning that the type of the field is a subtype of
   * the field that is overriden or the validator is more restrictive.<br>
   * 
   * @return the class that declares this field.
   */
  ContentClassIF getDeclaringClass();

  /**
   * This method gets the content-class that initially defined by this field.
   * This means that the returned content-class does not inherit this field and
   * its parent class (if not root) has no field with the same
   * {@link ContentObjectIF#getName() name}.
   * 
   * @return the class that initially defines this field.
   */
  ContentClassIF getInitiallyDefiningClass();

  /**
   * This method gets the super-field of this field if this field is extended. A
   * field defined in a class can be extended in a sub-class. The extended field
   * may have a more specific type.
   * 
   * @return the field extended by this field or <code>null</code> if the
   *         field is NOT inherited.
   */
  ContentFieldIF getSuperField();

  /**
   * This method gets the value type of the field. Only values of this type can
   * be stored in this field.
   * 
   * @return the type of this field.
   */
  Class getFieldType();

  /**
   * @see net.sf.mmm.content.model.api.ContentReflectionObjectIF#getModifiers()
   * 
   */
  FieldModifiersIF getModifiers();

  /**
   * This method validates if the given argument is an acceptable value for this
   * field.
   * 
   * @see net.sf.mmm.content.validator.api.ValueValidatorIF#validate(Object)
   * 
   * @param value
   *        is the value to check. It may be <code>null</code> but its not
   *        allowed to throw a {@link NullPointerException} for this reason.
   * @return the result of the validation.
   */
  // ValidationResultIF validate(Object value);
  /**
   * This method dynamically determines the value of the field. It is only
   * applicable for {@link FieldModifiersIF#isTransient() transient} fields.
   * 
   * @param object
   *        is the content-object for that the value of the current transient
   *        field should be calculated.
   * @return the calculated value of the current transient field for the given
   *         content-object. It will be <code>null</code> if the field is not
   *         transient.
   * @throws CalculationException
   *         if the calculation fails.
   * @throws PermissionDeniedException
   *         if the calculation performed an operation you (the current user) is
   *         not permitted.
   */
  // Object calculate(ContentObjectIF object) throws CalculationException,
  // PermissionDeniedException;
}
