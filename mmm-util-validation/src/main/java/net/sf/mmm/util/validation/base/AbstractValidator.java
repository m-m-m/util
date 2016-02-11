/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
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

  /** {@link #getProperty(TypedProperty) Property} for {@link #isMandatory() mandatory flag}. */
  public static final TypedProperty<Boolean> PROPERTY_MANDATORY = new TypedProperty<>(Boolean.class, "mandatory");

  /** {@link #getProperty(TypedProperty) Property} for minimum value (<code>null</code> if unbounded). */
  @SuppressWarnings("rawtypes")
  public static final TypedProperty<Comparable> PROPERTY_MINIMUM = new TypedProperty<>(Comparable.class, "min");

  /** {@link #getProperty(TypedProperty) Property} for maximum value (<code>null</code> if unbounded). */
  @SuppressWarnings("rawtypes")
  public static final TypedProperty<Comparable> PROPERTY_MAXIMUM = new TypedProperty<>(Comparable.class, "max");

  /**
   * The constructor.
   */
  public AbstractValidator() {

    super();
  }

  /**
   * This is the default implementation to retrieve the {@link ValidationFailure#getCode() code} of this
   * {@link ValueValidator}. <br>
   * <b>ATTENTION:</b><br>
   * This default implementation returns the {@link Class#getSimpleName() classname} of the actual
   * {@link ValueValidator} implementation. This strategy is chosen for simplicity when implementing a new validator. To
   * ensure stable codes override this method and return a string constant. This shall at least be done when the name of
   * the class is changed.
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
   * This method determines if this {@link ValueValidator} is <em>dynamic</em>. Here dynamic means that the validation
   * of the same input may not always return the same validation result (e.g. it holds references to instances that have
   * dynamic impact on the validation).
   *
   * @return <code>true</code> if this {@link ValueValidator} is dynamic, <code>false</code> otherwise.
   */
  public boolean isDynamic() {

    return false;
  }

  /**
   * @return <code>true</code> if this is a validator for mandatory fields (that will not accept <code>null</code> or
   *         empty values), <code>false</code> otherwise.
   */
  public final boolean isMandatory() {

    Boolean mandatory = getProperty(PROPERTY_MANDATORY);
    if (Boolean.TRUE.equals(mandatory)) {
      return true;
    }
    return false;
  }

  /**
   * This is a convenience method delegating to {@link net.sf.mmm.util.nls.api.NlsBundleFactory#createBundle(Class)}.
   *
   * @param <BUNDLE> is the generic type of the requested {@link NlsBundle}.
   * @param bundleInterface the interface of the requested {@link NlsBundle}. Has to be a sub-interface of
   *        {@link NlsBundle} with according methods.
   * @return an instance of the requested {@link NlsBundle} interface.
   * @since 3.1.0
   */
  protected <BUNDLE extends NlsBundle> BUNDLE createBundle(Class<BUNDLE> bundleInterface) {

    return NlsAccess.getBundleFactory().createBundle(bundleInterface);
  }

  /**
   * Gets the value of a property.
   *
   * @param
   *        <P>
   *        the generic type of the requested property.
   * @param property the {@link TypedProperty}.
   * @return the requested value or <code>null</code> if undefined.
   * @since 7.1.0
   */
  public <P> P getProperty(TypedProperty<P> property) {

    return null;
  }

  /**
   * @param validator the {@link AbstractValidator validator} to check.
   * @return {@code true} if the given {@link AbstractValidator validator} is {@link #equals(Object) equal} to this
   *         validator or one of its descendents, {@code false} otherwise.
   */
  public boolean contains(AbstractValidator<?> validator) {

    return equals(validator);
  }

  /**
   * @param validator the {@link AbstractValidator validator} to append.
   * @return a new {@link ComposedValidator} instance composing the current validator ({@code this}) with the given
   *         validator.
   */
  public ComposedValidator<V> append(AbstractValidator<? super V> validator) {

    return new ComposedValidator<>(this, validator);
  }

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();

}
