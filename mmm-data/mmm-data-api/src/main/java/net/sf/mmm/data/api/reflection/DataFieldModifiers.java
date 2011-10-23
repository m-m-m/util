/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

/**
 * This is the interface for the modifiers of a
 * {@link net.sf.mmm.data.api.reflection.DataField content-field}.<br>
 * ATTENTION: Be aware that a {@link DataField field} in this context is
 * different from a <code>field</code> in the JAVA language. Better think of it
 * as a bean property. If the field is {@link #isFinal() final}, the getter (and
 * setter) is final. A {@link #isReadOnly() read-only} field has no setter (can
 * NOT be modified). Therefore a {@link #isStatic() static} and
 * {@link #isFinal() final} field is NOT a constant and can be modified if it is
 * NOT {@link #isReadOnly() read-only}.
 * 
 * @see net.sf.mmm.data.api.reflection.DataField#getModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataFieldModifiers extends DataModifiers {

  /**
   * the name of the root tag.
   */
  String XML_TAG_ROOT = "FieldModifiers";

  /**
   * the attribute for the {@link #isStatic() static-flag}.
   */
  String XML_ATR_STATIC = "static";

  /**
   * the attribute for the {@link #isReadOnly() read-only flag}.
   */
  String XML_ATR_READ_ONLY = "read-only";

  /**
   * the attribute for the {@link #isTransient() transient-flag}.
   */
  String XML_ATR_TRANSIENT = "transient";

  /**
   * The attribute for the {@link #isInheritedFromParent()
   * inherited-from-parent} flag.
   */
  String XML_ATR_INHERITED_FROM_PARENT = "inherited-from-parent";

  /**
   * This method determines if the field is read-only. A read-only field can NOT
   * be written (at least NOT via
   * {@link DataField#setFieldValue(Object, Object)}).
   * 
   * @return <code>true</code> if the field is immutable, <code>false</code>
   *         otherwise.
   */
  boolean isReadOnly();

  /**
   * This method determines if the field is transient. A transient field is not
   * stored by the persistence but its value is dynamically calculated by a
   * given expression term.<br>
   * A transient field is always {@link #isReadOnly() read-only} and never
   * {@link #isStatic() static}.
   * 
   * @return <code>true</code> if this field is transient, <code>false</code>
   *         otherwise.
   */
  boolean isTransient();

  /**
   * This method determines if the field is static. A static field is a field of
   * the {@link DataClass content-class} instead of the
   * {@link net.sf.mmm.data.api.DataObject instance}.<br>
   * Be aware that a {@link #isStatic() static} and {@link #isFinal() final}
   * field can be modified if it is NOT {@link #isReadOnly() read-only} as
   * described {@link DataFieldModifiers here}.<br>
   * From all instances of the {@link DataClass content-class} declaring the
   * field, the value of the field can be
   * {@link net.sf.mmm.data.api.reflection.DataField#getFieldValue(Object)
   * read} and (if not {@link #isReadOnly() read-only} be
   * {@link net.sf.mmm.data.api.reflection.DataField#setFieldValue(Object, Object)
   * written} while the instance can be <code>null</code>.<br>
   * A {@link #isStatic() static} field can NOT be {@link #isTransient()
   * transient}. It can be persisted without relevant costs. If the according
   * field should be {@link #isTransient() transient} it can be declared NOT
   * {@link #isStatic() static}.
   * 
   * @return <code>true</code> if this field is static, <code>false</code>
   *         otherwise.
   */
  boolean isStatic();

  /**
   * This method determines if the {@link DataField} will inherit a value
   * from {@link net.sf.mmm.data.api.DataObject#getParent() parent} if not
   * set.<br/>
   * If a {@link net.sf.mmm.data.api.DataObject} <code>O</code> is an
   * instance of some {@link DataClass} <code>C</code> that has a
   * {@link DataField} <code>f</code> where this modifier is
   * <code>true</code> and the internal value of that that field (
   * <code>O.f</code>) is not set (has a value of <code>null</code>) then the
   * value will be recursively inherited from the
   * {@link net.sf.mmm.data.api.DataObject#getParent() parent} (
   * <code>O.parent.f</code>). Therefore the
   * {@link net.sf.mmm.data.api.DataObject#getParent() parent} needs to be
   * of the same type ({@link DataClass}) (<code>O.parent</code> instanceof
   * <code>C</code>).<br/>
   * <b>ATTENTION:</b><br>
   * In a statically typed {@link net.sf.mmm.data.api.reflection
   * content-model} this feature has to be manually implemented in the getter
   * method. In a dynamically typed model the feature will be available
   * automatically.
   * 
   * @return <code>true</code> if inherited from parent, <code>false</code>
   *         otherwise.
   */
  boolean isInheritedFromParent();

}
