/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.base.feature.AbstractUiFeatureValueAndValidationWithValidators;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageLookup;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.base.ValidationFailureImpl;
import net.sf.mmm.util.value.api.PropertyAccessor;

import org.slf4j.Logger;

/**
 * This is a base implementation of {@link UiDataBinding}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiDataBinding<VALUE> extends AbstractUiFeatureValueAndValidationWithValidators<VALUE>
    implements UiDataBinding<VALUE> {

  /** @see #getWidget() */
  private final AbstractUiWidget<VALUE> widget;

  /** @see #getOriginalValue() */
  private VALUE originalValue;

  /** @see #getRecentValue() */
  private VALUE recentValue;

  /** @see #isModified() */
  private boolean modified;

  /** @see #getValidity() */
  private Boolean validity;

  /**
   * The constructor.
   *
   * @param widget is the {@link #getWidget() widget} to bind.
   */
  public AbstractUiDataBinding(AbstractUiWidget<VALUE> widget) {

    super();
    this.widget = widget;
    this.originalValue = null;
    this.recentValue = null;
  }

  /**
   * @return the {@link AbstractUiWidget} bound via this {@link UiDataBinding}.
   */
  protected final AbstractUiWidget<VALUE> getWidget() {

    return this.widget;
  }

  /**
   * @return a {@link Logger} instance.
   */
  @Override
  protected final Logger getLogger() {

    return this.widget.getContext().getLogger();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValue(VALUE newValue, boolean forUser) {

    this.widget.setModified(forUser);
    this.recentValue = newValue;
    if (!forUser) {
      this.originalValue = newValue;
    }
    VALUE v = newValue;
    if (v == null) {
      // Prevent NPE and simplify clearing fields...
      // if (getWidget().getContext().getContainer().get(DatatypeDetector.class).isDatatype(getWidget().getV))
      v = createNewValue();
    }
    // even if no validation is performed:
    // whenever a value is set, previous validation failures will be cleared
    AbstractUiWidget.AccessHelper.clearValidationFailure(this.widget);
    doSetValue(v, forUser);
  }

  /**
   * This method is called from {@link #setValue(Object, boolean)}. It has to be implemented with the custom
   * logic to set the value in the view. The implementation of this method has to correspond with the
   * implementation of {@link #doGetValue(Object, ValidationState)}.
   *
   * @see #doGetValue(Object, ValidationState)
   *
   * @param value is the value to set. Typically a composite object (e.g. java bean) so its attributes are set
   *        to fields (see <code>UiWidgetField</code>).
   * @param forUser <code>true</code> if called from {@link #setValueForUser(Object)}, <code>false</code> if
   *        set from {@link #setValue(Object)}.
   */
  protected void doSetValue(VALUE value, boolean forUser) {

    AbstractUiWidget.AccessHelper.doSetValue(this.widget, value, forUser);
  }

  /**
   * {@inheritDoc}
   */
  // @Override
  @Override
  public final VALUE getValueDirect(VALUE template, ValidationState state) throws RuntimeException {

    try {
      VALUE value = template;
      if (value == null) {
        if (this.originalValue != null) {
          value = createCopyOfValue(this.originalValue);
        }
        if (value == null) {
          value = createNewValue();
        }
      }
      value = doGetValue(value, state);
      return value;
    } catch (RuntimeException e) {
      if (state == null) {
        throw e;
      }
      handleGetValueError(e, state);
      return null;
    }
  }

  /**
   * This method creates a new instance of &lt;VALUE&gt; (see {@link #getValue()}). It is called from
   * {@link #getValueDirect(Object, ValidationState)} or {@link #setValue(Object)} in case the given value is
   * <code>null</code>.<br/>
   * <b>NOTE:</b><br/>
   * If &lt;VALUE&gt; is {@link Void} or a {@link net.sf.mmm.util.lang.api.Datatype} (immutable object), this
   * method should legally return <code>null</code>. This can also be suitable for objects that only delegate
   * their {@link #getValue() value} to something else. Further, to be GWT compatible you cannot create the
   * new instance via reflection. If you do not care about GWT, you can use reflection or better use it via
   * {@link net.sf.mmm.util.pojo.api.PojoFactory}.
   *
   * @return a new instance of &lt;VALUE&gt;.
   */
  protected abstract VALUE createNewValue();

  /**
   * This method will create a (deep-)copy of the given <code>value</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * If &lt;VALUE&gt; is immutable you can simply return <code>null</code>.
   *
   * @param value is the {@link #getValue() value} to copy. Will typically be {@link #getOriginalValue()}.
   *        Must NOT be modified in any way.
   * @return a copy of the value or <code>null</code> if NOT implemented or supported.
   */
  protected abstract VALUE createCopyOfValue(VALUE value);

  /**
   * This method is called from {@link #getValueOrException(Object)}. It has to be implemented with the custom
   * logic to get the value from the view. The implementation of this method has to correspond with the
   * implementation of {@link #doSetValue(Object, boolean)}. A typical implementation of this method for a
   * composite widget should look like this:
   *
   * @see #doSetValue(Object, boolean)
   *
   * @param template is the object where the data is filled in. May only be <code>null</code> if
   *        {@link #createNewValue()} does.
   * @param state is the {@link ValidationState}. May be <code>null</code> (if the validation is omitted).
   *        Should only be used to propagate to {@link #getValueDirect(Object, ValidationState)} of children.
   * @return the current value of this widget. May be <code>null</code> if empty. If &lt;VALUE&gt; is
   *         {@link String} the empty {@link String} has to be returned if no value has been entered. In case
   *         &lt;VALUE&gt; is a mutable object (java bean) and <code>template</code> is NOT <code>null</code>,
   *         this method is supposed to return <code>template</code> after the value(s) of this object have
   *         been assigned. If <code>template</code> is <code>null</code> this method has to create a new
   *         instance of &lt;VALUE&gt;. It is forbidden and an explicit bug-pattern to modify the
   *         {@link #getOriginalValue() original value}. This is required to support operations such as
   *         {@link #resetValue()}.
   */
  protected VALUE doGetValue(VALUE template, ValidationState state) {

    return AbstractUiWidget.AccessHelper.doGetValue(this.widget, template, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getOriginalValue() {

    return this.originalValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getRecentValue() {

    return this.recentValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isModified() {

    return this.modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModified(boolean modified) {

    this.modified = modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValidity() {

    // getLogger().debug("getValidity:" + this.widget);
    return this.validity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValidity(Boolean validity) {

    // getLogger().debug("setValidity(" + validity + ") :" + this.widget);
    this.validity = validity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setMandatory(boolean mandatory) {

    super.setMandatory(mandatory);
    AbstractUiWidget.AccessHelper.setMandatory(this.widget, mandatory);
  }

  /**
   * This method converts an exception from {@link #getValueOrException(Object)} to a
   * {@link ValidationFailure}.
   *
   * @param error is the exception.
   * @return the {@link ValidationFailure}.
   */
  @Override
  protected ValidationFailure createValidationFailure(Throwable error) {

    return new ValidationFailureImpl(error.getClass().getSimpleName(), getSource(), error.getLocalizedMessage());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    this.widget.addChangeHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    return this.widget.removeChangeHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> void bind(TypedProperty<P> property, UiWidgetWithValue<P> valueWidget) {

    throw new NlsUnsupportedOperationException("bind");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property) {

    return createAndBind(property, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property, String label) {

    throw new NlsUnsupportedOperationException("bind");
  }

  /**
   * Determines the label to use for the given <code>property</code> (including I18N, etc.).
   *
   * @see TypedProperty#getTitle()
   *
   * @param property is the {@link TypedProperty}.
   * @return the label to use for the property.
   */
  public String getLabel(TypedProperty<?> property) {

    String title = property.getTitle();
    NlsMessageLookup labelLookup = getWidget().getContext().getConfiguration().getLabelLookup();
    NlsMessage message = labelLookup.getMessage(title, null);
    if (message == null) {
      return title;
    }
    return message.getLocalizedMessage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PropertyAccessor<VALUE, ?> createPropertyAccessor(String property) {

    throw new NlsUnsupportedOperationException("createPropertyAccessor");
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <P> PropertyAccessor<VALUE, P> createPropertyAccessor(TypedProperty<P> property) {

    return (PropertyAccessor<VALUE, P>) createPropertyAccessor(property.getPojoPath());
  }

  /**
   * @return the source for validation failures.
   */
  @Override
  protected String getSource() {

    return this.widget.getSource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    // default implementation
    return getSource() + "[" + getClass().getSimpleName() + "]";
  }

}
