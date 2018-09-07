/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

/**
 * This is a sub-interface of {@link GenericValueConverter} for the most common value type {@link String}. It
 * is typically used for dealing with values (e.g. when reading configurations).
 *
 * @see ComposedValueConverter
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface StringValueConverter extends GenericValueConverter<String> {

}
