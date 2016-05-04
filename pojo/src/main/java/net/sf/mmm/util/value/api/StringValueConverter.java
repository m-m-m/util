/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is a sub-interface of {@link GenericValueConverter} for the most common value type {@link String}. It is
 * typically used for dealing with values (e.g. when reading configurations).
 *
 * @see ComposedValueConverter
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface StringValueConverter extends GenericValueConverter<String> {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.value.api.StringValueConverter";

}
