/* $ Id: $ */
package net.sf.mmm.content.model.api;

/**
 * This is the interface for the modifiers of a
 * {@link net.sf.mmm.content.model.api.ContentField content-field}.<br>
 * ATTENTION: Be aware that a {@link ContentField field} in this context is
 * different from a <code>field</code> in the JAVA language. Better think of
 * it as a bean property. If the field is {@link #isFinal() final}, the getter
 * (and setter) is final. A {@link #isReadOnly() read-only} field has no setter
 * (can NOT be modified). Therefore a {@link #isStatic() static} and
 * {@link #isFinal() final} field is NOT a constant and can be modified if it is
 * NOT {@link #isReadOnly() read-only}.
 * 
 * @see net.sf.mmm.content.model.api.ContentField#getModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface FieldModifiers extends Modifiers {

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
   * {@link net.sf.mmm.content.api.ContentObject#setFieldValue(String, Object) setFieldValue}).
   * 
   * @return <code>true</code> if the field is immutable.
   */
  boolean isReadOnly();

  /**
   * This method determines if the field is transient. A transient field is not
   * stored by the persistence but its value is dynamically calculated by a
   * given expression term.
   * 
   * @return <code>true</code> if this field is transient.
   */
  boolean isTransient();

  /**
   * This method determines if the field is static. A static field is a field of
   * the {@link ContentClass content-class} instead of the
   * {@link net.sf.mmm.content.api.ContentObject instance}.<br>
   * Be aware that a {@link #isStatic() static} and {@link #isFinal() final}
   * field can be modified if it is NOT {@link #isReadOnly() read-only} as
   * described {@link FieldModifiers here}.<br>
   * From all instances of the {@link ContentClass content-class} declaring the
   * field, the value of the field can be
   * {@link net.sf.mmm.content.api.ContentObject#getFieldValue(String) read}
   * what will always result in the same value. Writing the value via the
   * instance is NOT allowed and must be done via the declaring
   * {@link ContentClass content-class}.<br>
   * A {@link #isStatic() static} field can NOT be
   * {@link #isTransient() transient}. It can be persisted without additional
   * costs. If the according field should be {@link #isTransient() transient} it
   * can be declared NOT {@link #isStatic() static}.
   * 
   * @return <code>true</code> if this field is static.
   */
  boolean isStatic();

}
