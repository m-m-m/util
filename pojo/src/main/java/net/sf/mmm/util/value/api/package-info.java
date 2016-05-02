/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for generic handling of values.
 * <a name="documentation"></a><h2>Value-Util API</h2>
 * When reading values from sources like configuration data or user input you
 * always need to handle failure situations. The value can be <code>null</code>
 * and often has to be converted to a specific type (e.g. from
 * {@link java.lang.String} to {@link java.lang.Integer}) but may have an
 * invalid format. If you write this sort of code again and again you get tired
 * and your error-handling gets bad and worse.
 * If the user has tons of configurations to edit and the application starts
 * with a {@link java.lang.NullPointerException} the user can NOT know which
 * configuration-file and what value is wrong. <br>
 * This package provides the {@link net.sf.mmm.util.value.api.GenericValueConverter}
 * that makes your job a lot simpler and helps you to have precise
 * exception-messages in situations of an error. Further you have
 * NLS (see {@link net.sf.mmm.util.nls.api.NlsMessage}) build in. <br>
 * Here is a little example of custom value handling:
 * <pre>
 * String value = getValueFromSomewhere();
 * if (value == null) {
 *   throw new IllegalArgumentException("The value from somewhere is NOT defined!");
 * }
 * int valueAsInt;
 * try {
 *   valueAsInt = Integer.valueOf(value);
 * } catch (NumberFormatException e) {
 *   throw new IllegalArgumentException("The value '" + value + "' from somewhere is no integer!", e);
 * }
 * if (valueAsInt < 0) {
 *   throw new IllegalArgumentException("The value '" + value + "' from somewhere must NOT be negative!");
 * }
 * if (valueAsInt > 123456789) {
 *   throw new IllegalArgumentException("The value '" + value + "' from somewhere must be less than '123456789'!");
 * }
 * </pre>
 * Here is the same thing when using {@link net.sf.mmm.util.value.api.GenericValueConverter}:
 * <pre>
 * String value = getValueFromSomewhere();
 * {@link net.sf.mmm.util.value.api.GenericValueConverter} converter = {@link net.sf.mmm.util.value.base.StringValueConverterImpl#getInstance()};
 * int valueAsInt = converter.{@link net.sf.mmm.util.value.api.GenericValueConverter#convertValue(Object, Object, Number, Number)
 * convertValue}(value, "somewhere", 0, 123456789);
 * </pre>
 * <br>
 * Even more powerful is the {@link net.sf.mmm.util.value.api.ComposedValueConverter}
 * that allows conversions from and to arbitrary types, properly treats generics
 * and is easily extendable via {@link net.sf.mmm.util.value.api.ValueConverter}s
 * as plugins.
 */
package net.sf.mmm.util.value.api;

