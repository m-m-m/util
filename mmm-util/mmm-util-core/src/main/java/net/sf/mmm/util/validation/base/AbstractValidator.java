/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.sql.Date;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the abstract base class all {@link ValueValidator} implementations should extend.
 * 
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractValidator<V> implements ValueValidator<V> {

  /**
   * The constructor.
   */
  public AbstractValidator() {

    super();
  }

  /**
   * This is the default implementation to retrieve the {@link ValidationFailure#getCode() code} of this
   * {@link ValueValidator}.<br/>
   * <b>ATTENTION:</b><br/>
   * This default implementation returns the {@link Class#getSimpleName() classname} of the actual
   * {@link ValueValidator} implementation. This strategy is chosen for simplicity when implementing a new
   * validator. To ensure stable codes override this method and return a string constant. This shall at least
   * be done when the name of the class is changed.
   * 
   * @return the {@link ValidationFailure#getCode() code}.
   */
  protected String getCode() {

    return getClass().getSimpleName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final ValidationFailure validate(V value) {

    return validate(value, null);
  }

  /**
   * This method determines if this {@link ValueValidator} is <em>dynamic</em>. Here dynamic means that the
   * validation of the same input may not always return the same validation result (e.g. it holds references
   * to instances that have dynamic impact on the validation).
   * 
   * @return <code>true</code> if this {@link ValueValidator} is dynamic, <code>false</code> otherwise.
   */
  public boolean isDynamic() {

    return false;
  }

  /**
   * This method {@link StringBuilder#append(String) appends} the source-code statement to create a new
   * instance of this validator with the same behavior. This is required to be GWT compatible. For complex
   * validators that hold references to instances that have dynamic impact on the validation, nothing shall be
   * appended.<br/>
   * E.g. this method may return:
   * 
   * <pre>
   * new com.mycompany.MyValidator(5, 6.2, ">");
   * </pre>
   * 
   * @see #appendSourceCodeConstructorArguments(StringBuilder)
   * 
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(String) append} the the
   *        source-code.
   */
  public void appendSourceCodeCreationStatement(StringBuilder buffer) {

    if (isDynamic()) {
      return;
    }
    buffer.append("new ");
    buffer.append(getClass().getName());
    buffer.append("(");
    appendSourceCodeConstructorArguments(buffer);
    buffer.append(")");
  }

  /**
   * This method is called from {@link #appendSourceCodeCreationStatement(StringBuilder)} to append the
   * constructor arguments. The default implementation does nothing and assumes that there is a non-arg
   * constructor and no parameters. If you have constructor arguments you need to override this method. Here
   * is an example to do so:
   * 
   * <pre>
   * protected appendSourceCodeConstructorArguments(StringBuilder buffer) {
   *   buffer.append(this.min);
   *   buffer.append(", ");
   *   buffer.append(this.variance);
   *   buffer.append(", \"");
   *   buffer.append(this.operation);
   *   buffer.append("\"");
   * }
   * </pre>
   * 
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(String) append} the
   *        arguments.
   */
  protected void appendSourceCodeConstructorArguments(StringBuilder buffer) {

    // nothing by default...
  }

  /**
   * Appends the given constructor <code>argument</code> to the given <code>buffer</code>.
   * 
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(String) append}.
   * @param argument is the argument to append.
   */
  @SuppressWarnings("deprecation")
  protected void appendSourceCodeConstructorArgument(StringBuilder buffer, Object argument) {

    Class<?> argumentClass = argument.getClass();
    if (argumentClass.isEnum()) {
      buffer.append(argumentClass.getName());
      buffer.append('.');
      buffer.append(((Enum<?>) argument).name());
      return;
    }
    buffer.append("new ");
    buffer.append(argumentClass.getName());
    buffer.append('(');
    if (argumentClass == Date.class) {
      // new java.util.Date(year, month, date, hrs, min, sec);
      Date date = (Date) argument;
      buffer.append(date.getYear());
      buffer.append(',');
      buffer.append(date.getMonth());
      buffer.append(',');
      buffer.append(date.getDate());
      buffer.append(',');
      buffer.append(date.getHours());
      buffer.append(',');
      buffer.append(date.getMinutes());
      buffer.append(',');
      buffer.append(date.getSeconds());
    } else {
      buffer.append(argument.toString());
    }
    buffer.append(')');
  }

  /**
   * @return <code>true</code> if this is a validator for mandatory fields (that will not accept
   *         <code>null</code> or empty values), <code>false</code> otherwise.
   */
  public boolean isMandatory() {

    return false;
  }

}
