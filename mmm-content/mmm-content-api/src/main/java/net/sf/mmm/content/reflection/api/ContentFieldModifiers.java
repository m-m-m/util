/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api;

/**
 * This is the interface for the modifiers of a
 * {@link net.sf.mmm.content.reflection.api.ContentField content-field}.<br>
 * ATTENTION: Be aware that a {@link ContentField field} in this context is
 * different from a <code>field</code> in the JAVA language. Better think of it
 * as a bean property. If the field is {@link #isFinal() final}, the getter (and
 * setter) is final. A {@link #isReadOnly() read-only} field has no setter (can
 * NOT be modified). Therefore a {@link #isStatic() static} and
 * {@link #isFinal() final} field is NOT a constant and can be modified if it is
 * NOT {@link #isReadOnly() read-only}.
 * 
 * @see net.sf.mmm.content.reflection.api.ContentField#getContentModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentFieldModifiers extends ContentModifiers {

  /**
   * the name of the root tag.
   */
  String XML_TAG_ROOT = "fieldModifiers";

  /**
   * the attribute for the {@link #isStatic() static-flag}.
   */
  String XML_ATR_ROOT_STATIC = "static";

  /**
   * the attribute for the {@link #isReadOnly() read-only flag}.
   */
  String XML_ATR_ROOT_READ_ONLY = "read-only";

  /**
   * the attribute for the {@link #isTransient() transient-flag}.
   */
  String XML_ATR_ROOT_TRANSIENT = "transient";

  /**
   * This method determines if the field is read-only. A read-only field can NOT
   * be written (at least NOT via
   * {@link ContentField#setFieldValue(Object, Object)}).
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
   * the {@link ContentClass content-class} instead of the
   * {@link net.sf.mmm.content.api.ContentObject instance}.<br>
   * Be aware that a {@link #isStatic() static} and {@link #isFinal() final}
   * field can be modified if it is NOT {@link #isReadOnly() read-only} as
   * described {@link ContentFieldModifiers here}.<br>
   * From all instances of the {@link ContentClass content-class} declaring the
   * field, the value of the field can be
   * {@link net.sf.mmm.content.reflection.api.ContentField#getFieldValue(Object)
   * read} and (if not {@link #isReadOnly() read-only} be
   * {@link net.sf.mmm.content.reflection.api.ContentField#setFieldValue(Object, Object)
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
   * This method determines if the {@link ContentField} will inherit a value
   * from {@link net.sf.mmm.content.api.ContentObject#getParent() parent} if not
   * set.<br/>
   * If a {@link net.sf.mmm.content.api.ContentObject} <code>O</code> is an
   * instance of some {@link ContentClass} <code>C</code> that has a
   * {@link ContentField} <code>f</code> where this modifier is
   * <code>true</code> and the internal value of that that field (
   * <code>O.f</code>) is not set (has a value of <code>null</code>) then the
   * value will be recursively inherited from the
   * {@link net.sf.mmm.content.api.ContentObject#getParent() parent} (
   * <code>O.parent.f</code>). Therefore the
   * {@link net.sf.mmm.content.api.ContentObject#getParent() parent} needs to be
   * of the same type ({@link ContentClass}) (<code>O.parent</code> instanceof
   * <code>C</code>).<br/>
   * <b>ATTENTION:</b><br>
   * In a statically typed {@link net.sf.mmm.content.reflection.api content-model}
   * this feature has to be manually implemented in the getter method. In a
   * dynamically typed model the feature will be available automatically.
   * 
   * @return <code>true</code> if inherited from parent, <code>false</code>
   *         otherwise.
   */
  boolean isInheritedFromParent();

}
