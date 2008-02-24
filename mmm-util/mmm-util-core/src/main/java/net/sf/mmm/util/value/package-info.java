/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains utilities for generic handling of values.
 * <h2>Value Handling</h2>
 * When reading values from sources like configuration data or user input you 
 * always need to handle failure situations. The value can be <code>null</code> 
 * and often has to be converted to a specific type (e.g. from 
 * {@link java.lang.String} to {@link java.lang.Integer}) but may have an 
 * invalid format. If you write this sort of code again and again you get tired 
 * and your error-handling gets bad and worse.
 * If the user has tons of configurations to edit and the application starts 
 * with a {@link java.lang.NullPointerException} the user can NOT know which 
 * configuration-file and what value is wrong.<br>
 * This package provides an extendable {@link net.sf.mmm.util.value.ValueConverter} 
 * that makes your job a lot simpler and helps you to have precise 
 * exception-messages in situations of an error. Further you have 
 * {@link net.sf.mmm.util.nls NLS} build in.<br>
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
 * Here is the same thing when using {@link net.sf.mmm.util.value.ValueConverter}:
 * <pre>
 * String value = getValueFromSomewhere();
 * {@link net.sf.mmm.util.value.ValueConverter} converter = {@link net.sf.mmm.util.value.ValueConverter#getInstance()};
 * int valueAsInt = converter.{@link net.sf.mmm.util.value.ValueConverter#convertValue(String, Object, 
 * Number, Number) convertValue}(value, "somewhere", 0, 123456789);
 * </pre>
 */
package net.sf.mmm.util.value;

