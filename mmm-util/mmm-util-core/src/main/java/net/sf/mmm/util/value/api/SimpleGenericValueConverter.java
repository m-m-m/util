/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a {@link SimpleValueConverter} that is generic and can convert from
 * {@link Object} to {@link Object}.
 * 
 * @see ComposedValueConverter
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@ComponentSpecification
public interface SimpleGenericValueConverter extends SimpleValueConverter<Object, Object> {

  // nothing to add...

}
