/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This interface gives {@link #setValue(Object) advanced} read and write access to the {@link #getValue()
 * value} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface AttributeWriteValueAdvanced<VALUE> extends AttributeReadValueAdvanced<VALUE>,
    AttributeWriteValue<VALUE> {

  /**
   * {@inheritDoc}
   * 
   * It will also set the {@link AttributeReadModified#isModified() modified} flag to <code>false</code>.
   */
  @Override
  void setValue(VALUE value);

  /**
   * This method is like {@link #setValue(Object)} but will NOT set the {@link #getOriginalValue() original
   * value} and will set the {@link AttributeReadModified#isModified() modified} to <code>true</code> (instead
   * of <code>false</code>).
   * 
   * @param value is the {@link #getValue() value} to set.
   */
  void setValueForUser(VALUE value);

  /**
   * This method resets this widget. It is a convenience method for the following code.<br/>
   * 
   * <pre>{@link #setValue(Object) setValue}({@link #getOriginalValue()})</pre>
   */
  void resetValue();

}
