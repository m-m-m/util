/* $Id$ */
package net.sf.mmm.value.impl;

/**
 * This is a simple,
 * {@link net.sf.mmm.value.base.AbstractGenericValue#isAddDefaults() immutable}
 * implementation of the {@link net.sf.mmm.value.api.GenericValue} interface
 * that uses an object as internal value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ImmutableObjectValue extends ObjectValue {

  /** UID for serialization */
  private static final long serialVersionUID = 5962310960812151729L;

  /**
   * The constructor.
   * 
   * @param internalValue
   */
  public ImmutableObjectValue(Object internalValue) {

    super(internalValue);
  }

  /**
   * @see net.sf.mmm.value.impl.ObjectValue#isEditable()
   */
  @Override
  public boolean isEditable() {

    return false;
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#isAddDefaults()
   */
  @Override
  public boolean isAddDefaults() {

    return false;
  }

  /**
   * @see net.sf.mmm.value.impl.ObjectValue#setPlainValue(java.lang.Object)
   */
  @Override
  protected void setPlainValue(Object newValue) {

  // just to get sure...
  }

}
