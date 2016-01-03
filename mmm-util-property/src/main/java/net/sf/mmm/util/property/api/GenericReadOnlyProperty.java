/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.property.ReadOnlyProperty;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.lang.api.attribute.AttributeReadName;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the interface for a generic property.
 *
 * @author hohwille
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @since 7.1.0
 */
public interface GenericReadOnlyProperty<VALUE>
    extends ReadOnlyProperty<VALUE>, AttributeReadValue<VALUE>, AttributeReadName {

  /**
   * @return the type of the property.
   */
  GenericType<VALUE> getType();

  @Override
  Bean getBean();

}
