/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeReadEventObserver;
import net.sf.mmm.client.ui.api.attribute.AttributeReadFocused;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteModified;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteOnlyFocused;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventValueChange;
import net.sf.mmm.client.ui.api.handler.UiEventObserver;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetAbstractWithValue;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.feature.AbstractUiFeatureValueAndValidation;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.util.lang.api.BasicUtil;
import net.sf.mmm.util.lang.base.BasicUtilImpl;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.ValidatorNone;

import org.slf4j.Logger;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.client.ui.api.widget.UiWidget}. Below this
 * class there are two inheritance hierarchies <code>AbstractUiWidgetNative</code> and
 * {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom}. To avoid problems with the lack of
 * multi-inheritance in Java, we already implement {@link net.sf.mmm.client.ui.api.widget.UiWidgetWithValue}.
 * For subclasses that have no value {@link Void} is used for {@literal <VALUE>}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidget<VALUE> extends AbstractUiFeatureValueAndValidation<VALUE> implements
    UiWidgetAbstractWithValue<VALUE>, AttributeWriteModified, AttributeWriteOnlyFocused {

  /** @see #getContext() */
  private final AbstractUiContext context;

  /** @see #fireEvent(UiEvent) */
  private EventSender eventSender;

  /** @see #getDataBinding() */
  private UiDataBinding<VALUE> dataBinding;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidget(UiContext context) {

    super();
    this.context = (AbstractUiContext) context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Logger getLogger() {

    return this.context.getLogger();
  }

  /**
   * @return the instance of {@link BasicUtil}.
   */
  protected final BasicUtil getBasicUtil() {

    // TODO: retrieve via context
    return BasicUtilImpl.getInstance();
  }

  /**
   * @return the {@link UiDataBinding} for this widget.
   */
  protected UiDataBinding<VALUE> getDataBinding() {

    return getDataBinding(null);
  }

  /**
   * @see #getDataBinding()
   * 
   * @param example is an example value that may be used to determine the {@link #getValueClass() value class}
   *        if not available. May be <code>null</code>.
   * @return the data binding.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private UiDataBinding<VALUE> getDataBinding(VALUE example) {

    if (this.dataBinding == null) {
      Class<VALUE> valueType = getValueClass();
      if (valueType == null) {
        if (example != null) {
          valueType = (Class) example.getClass();
        }
      }
      this.dataBinding = getContext().getDataBindingFactory().createDataBinding(this, valueType);
    }
    return this.dataBinding;
  }

  /**
   * @return the {@link Class} reflecting {@literal <VALUE>}. May also be <code>null</code> for {@link Void}.
   */
  protected Class<VALUE> getValueClass() {

    // ATTENTION: This is only the default implementation for widgets without value.
    // It must be overridden for widgets with value...
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValueOrException(VALUE template) throws RuntimeException {

    // clearValidity();
    return super.getValueOrException(template);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getOriginalValue() {

    if (this.dataBinding == null) {
      return null;
    }
    return getDataBinding().getOriginalValue();
  }

  /**
   * @return the {@link UiDataBinding#getRecentValue() recent value}.
   */
  protected VALUE getRecentValue() {

    if (this.dataBinding == null) {
      return null;
    }
    return getDataBinding().getRecentValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValueDirect(VALUE template, ValidationState state) throws RuntimeException {

    int failureCount = 0;
    if (state != null) {
      failureCount = state.getFailureCount();
    }
    VALUE value = getDataBinding().getValueDirect(template, state);
    if (state != null) {
      doValidate(state, value);
      assert (state.getFailureCount() >= failureCount) : "failure count must not decrease.";
      boolean success = (state.getFailureCount() == failureCount);
      getDataBinding().setValidity(Boolean.valueOf(success));
      if (!success && (failureCount == 0)) {
        // on the first validation failure, set the focus into the according field/widget...
        setFocused();
      }
    }
    return value;
  }

  /**
   * This method is called from the {@link #getDataBinding() data-binding} triggered by API methods such as
   * {@link #getValue()}. In many cases the {@link #getDataBinding() data-binding} already performs the
   * required work to do. However, here is the place to implemented the custom logic to get the value from the
   * actual widget. The following cases have to be distinguished:
   * <ul>
   * <li>For {@link net.sf.mmm.client.ui.api.widget.UiWidgetNative native}
   * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField fields} this method has to get the actual
   * value from the underlying native widget (text-box, password-field, etc.).</li>
   * <li>For {@link net.sf.mmm.client.ui.base.widget.custom.field.UiWidgetCustomField custom fields} this
   * method will be responsible to convert from the delegates value (see
   * <code>UiWidgetCustom.getDelegate()</code>) to the proper value type. E.g. if you want to create a custom
   * widget to edit your own datatype such as <code>CustomerNumber</code> based on a
   * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField} you need to convert from {@link String}
   * to <code>CustomerNumber</code>.</li>
   * <li>For {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite custom composite widgets}
   * you should use {@link #getDataBinding()}.
   * {@link net.sf.mmm.client.ui.base.binding.UiDataBinding#createAndBind(net.sf.mmm.util.pojo.path.api.TypedProperty)}
   * that gives you high-level support and makes your live easy. Then there is no need to override this
   * method.</li>
   * <li>For other cases, especially if {@literal <VALUE>} is {@link Void}, there is nothing to do here.</li>
   * </ul>
   * The implementation of this method has to correspond with the implementation of
   * {@link #doSetValue(Object, boolean)} .
   * 
   * @see #doSetValue(Object, boolean)
   * 
   * @param template is the object where the data is filled in. May only be <code>null</code> if according to
   *        {@link #getDataBinding() data-binding} (e.g. if {@literal <VALUE>} is an (immutable)
   *        {@link net.sf.mmm.util.lang.api.Datatype}).
   * @param state is the {@link ValidationState}. May be <code>null</code> (if the validation is omitted).
   *        Should only be used to propagate to {@link #getValueDirect(Object, ValidationState)} of children.
   * @return the current value of this widget. May be <code>null</code> if empty. If {@literal <VALUE>} is
   *         {@link String} the empty {@link String} has to be returned if no value has been entered. In case
   *         {@literal <VALUE>} is a mutable object (java bean) the <code>template</code> is NOT
   *         <code>null</code> and is to be returned by this method after the value(s) of this widget have
   *         been assigned. For immutable {@link net.sf.mmm.util.lang.api.Datatype datatypes}
   *         <code>template</code> will be <code>null</code> and this method has to create a new instance of
   *         {@literal <VALUE>} based on the end-users input in the widget.
   */
  protected VALUE doGetValue(VALUE template, ValidationState state) {

    return template;
  }

  /**
   * This method is called from the {@link #getDataBinding() data-binding} triggered by API methods such as
   * {@link #setValue(Object, boolean)}. In many cases the {@link #getDataBinding() data-binding} already
   * performs the required work to do. However, here is the place to implemented the custom logic to get the
   * value from the actual widget. For details see {@link #doGetValue(Object, ValidationState)}.
   * 
   * @see #doGetValue(Object, ValidationState)
   * 
   * @param value is the value to set. Typically a composite object (e.g. java bean) so its attributes are set
   *        to fields (see <code>UiWidgetField</code>).
   * @param forUser <code>true</code> if called from {@link #setValueForUser(Object)}, <code>false</code> if
   *        set from {@link #setValue(Object)}.
   */
  protected void doSetValue(VALUE value, boolean forUser) {

    // nothing to do by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(VALUE newValue, boolean forUser) {

    getDataBinding(newValue).setValue(newValue, forUser);
    fireValueChange(true);
  }

  /**
   * Converts the given value to {@link String}.
   * 
   * @param value is the value to convert.
   * @return the {@link String} representation to display the given <code>value</code>.
   */
  protected String convertValueToString(VALUE value) {

    if (value == null) {
      return "";
    }
    return value.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeValidator(ValueValidator<? super VALUE> validator) {

    return getDataBinding().removeValidator(validator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMandatory() {

    return getDataBinding().isMandatory();
  }

  /**
   * This method is called whenever the {@link #isMandatory()} flag is updated. You may override it to update
   * the UI to reflect this change.
   * 
   * @param mandatory is the new value of {@link #isMandatory()}.
   */
  protected void setMandatory(boolean mandatory) {

    // nothing by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isModified() {

    if (getDataBinding().isModified()) {
      return true;
    }
    return isModifiedRecursive();
  }

  /**
   * @return <code>true</code> if a child or descendant of this object is {@link #isModified() modified},
   *         <code>false</code> otherwise.
   */
  protected boolean isModifiedRecursive() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModified(boolean modified) {

    getDataBinding().setModified(modified);
    if (!modified) {
      // setModifiedRecursive(false);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMode(UiMode mode) {

    setMode(mode, true);
  }

  /**
   * @see #setMode(UiMode)
   * 
   * @param mode is the new {@link UiMode} to set.
   * @param programmatic - see {@link UiEvent#isProgrammatic()}.
   */
  protected abstract void setMode(UiMode mode, boolean programmatic);

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearMessages() {

    clearMessagesLocal();
  }

  /**
   * This method clears the messages locally (the non-recursive part of {@link #clearMessages()}).
   */
  protected void clearMessagesLocal() {

    clearValidationFailure();
  }

  /**
   * This method clears the messages locally (the non-recursive part of {@link #clearMessages()}).
   */
  protected void clearValidationFailure() {

    getDataBinding().setValidity(null);
  }

  /**
   * {@inheritDoc}
   * 
   * It clears the {@link UiDataBinding#getValidity() validity} recursively.
   */
  @Override
  protected void clearValidity() {

    getDataBinding().setValidity(null);
  }

  /**
   * This method is called from {@link #validate(ValidationState)} and performs the actual validation of this
   * object. This method performs the recursive validation of potential children of this widget excluding the
   * validation of this widget itself. A legal implementation of a composite widget needs to call
   * {@link #validate(ValidationState)} on all child widgets.
   * 
   * @param state is the {@link ValidationState}. Never <code>null</code>.
   * @param value is the {@link #getValue() current value} of this object that has already be determined.
   */
  private void validateInteral(ValidationState state, VALUE value) {

    if (!isVisible()) {
      // return true;
      return;
    }
    if (!isEnabled()) {
      // return true;
      return;
    }
    // return
    doValidate(state, value);
  }

  /**
   * This method is called from {@link #validate(ValidationState)} and performs the actual validation of this
   * object. This method performs the recursive validation of potential children of this widget excluding the
   * validation of this widget itself. A legal implementation of a composite widget needs to call
   * {@link #validate(ValidationState)} on all child widgets.
   * 
   * @param state is the {@link ValidationState}. Never <code>null</code>.
   * @param value is the {@link #getValue() current value} of this object that has already be determined.
   */
  protected void doValidate(ValidationState state, VALUE value) {

    getDataBinding().doValidate(state, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addValidator(ValueValidator<? super VALUE> validator) {

    if (validator instanceof ValidatorNone) {
      return;
    }
    getDataBinding().addValidator(validator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final AbstractUiContext getContext() {

    return this.context;
  }

  /**
   * @return die Instanz von {@link AttributeReadEventObserver} oder <code>null</code> falls nicht verfügbar.
   */
  protected final AttributeReadEventObserver getObserverSource() {

    return this.context;
  }

  /**
   * @return the {@link UiWidgetFactory} via {@link #getContext()} for convenience.
   */
  public final UiWidgetFactory getFactory() {

    return this.context.getWidgetFactory();
  }

  /**
   * @return <code>true</code> if the {@link UiWidgetAdapter} has already been {@link #getWidgetAdapter()
   *         created}. Otherwise <code>false</code> (if {@link #getWidgetAdapter()} has never been called
   *         yet).
   */
  public abstract boolean hasWidgetAdapter();

  /**
   * This method gets or creates the {@link UiWidgetAdapter}.<br/>
   * <b>ATTENTION:</b><br/>
   * On the first call of this method, the {@link UiWidgetAdapter} is created. For the purpose of lazy
   * instantiation this should happen as late as possible. Use {@link #hasWidgetAdapter()} to prevent
   * unnecessary creation.
   * 
   * @return the {@link UiWidgetAdapter}.
   */
  protected abstract UiWidgetAdapter getWidgetAdapter();

  /**
   * This method gives access to {@link #getWidgetAdapter()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget.
   * @return the {@link #getWidgetAdapter() widget adapter} of the given <code>widget</code>.
   */
  public static final UiWidgetAdapter getWidgetAdapter(UiWidget widget) {

    return ((AbstractUiWidget<?>) widget).getWidgetAdapter();
  }

  /**
   * This method removes this widget from its {@link #getParent() parent}. The {@link #getParent() parent} is
   * set to <code>null</code> and the native widget is removed from its parent.
   */
  protected abstract void removeFromParent();

  /**
   * This method invokes {@link #removeFromParent()} on the given <code>widget</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users.
   * 
   * @param widget is the widget that should be removed from its {@link #getParent() parent}.
   */
  public static void removeFromParent(UiWidget widget) {

    ((AbstractUiWidget<?>) widget).removeFromParent();
  }

  /**
   * This method sets the {@link #getParent() parent}.
   * 
   * @param parent is the new {@link #getParent() parent}.
   */
  protected abstract void setParent(UiWidgetComposite<?> parent);

  /**
   * This method sets the {@link #getParent() parent} of the given <code>widget</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param widget is the widget where to set the {@link #getParent() parent}.
   * @param newParent is the new {@link #getParent() parent}.
   */
  public static void setParent(UiWidget widget, UiWidgetComposite<?> newParent) {

    ((AbstractUiWidget<?>) widget).setParent(newParent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPercent(double heightInPercent) {

    setHeight(Length.valueOfPercent(heightInPercent));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(double heightInPixel) {

    setHeight(Length.valueOfPixel(heightInPixel));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPercent(double widthInPercent) {

    setWidth(Length.valueOfPercent(widthInPercent));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(double widthInPixel) {

    setWidth(Length.valueOfPixel(widthInPixel));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(double widthAmount, double heightAmount, LengthUnit unit) {

    setSize(unit.newLength(widthAmount), unit.newLength(heightAmount));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(Length width, Length height) {

    setWidth(width);
    setHeight(height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getWidth() {

    return getLength(LengthProperty.WIDTH);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    setLength(LengthProperty.WIDTH, width);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getHeight() {

    return getLength(LengthProperty.HEIGHT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    setLength(LengthProperty.HEIGHT, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addEventHandler(UiHandlerEvent handler) {

    NlsNullPointerException.checkNotNull(UiHandlerEvent.class, handler);
    getEventSender().eventHandlers.add(handler);
  }

  /**
   * @return <code>true</code> if the {@link EventSender} has already been {@link #getEventSender() created}.
   *         Otherwise <code>false</code> (if {@link #getEventSender()} has never been called yet).
   */
  protected final boolean hasEventSender() {

    return (this.eventSender != null);
  }

  /**
   * This method gets the {@link EventSender}. It will be created on the first call.
   * 
   * @return the {@link EventSender}.
   */
  protected final EventSender getEventSender() {

    if (this.eventSender == null) {
      this.eventSender = new EventSender();
      if (hasWidgetAdapter()) {
        // TODO hohwille might be called twice for custom widget
        getWidgetAdapter().setEventSender(this, this.eventSender);
      }
    }
    return this.eventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeEventHandler(UiHandlerEvent handler) {

    if (this.eventSender == null) {
      return false;
    }
    return this.eventSender.eventHandlers.remove(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    return removeEventHandler(handler);
  }

  /**
   * Fires an event with the given parameters.
   * 
   * @param event is the {@link UiEvent}.
   */
  protected final void fireEvent(UiEvent event) {

    if (this.eventSender != null) {
      this.eventSender.onEvent(event);
    }
  }

  /**
   * Called if the value has changed internally. Implementation has to fire a
   * {@link net.sf.mmm.client.ui.api.event.EventType#VALUE_CHANGE value change} event.
   * 
   * @param programmatic - see {@link UiEvent#isProgrammatic()}, should typically be <code>true</code> here.
   */
  protected void fireValueChange(boolean programmatic) {

    fireEvent(new UiEventValueChange<VALUE>(this, programmatic));
  }

  /**
   * This inner class is an adapter for the events.
   */
  protected class EventSender implements UiHandlerEvent, AttributeReadFocused {

    /** @see #addEventHandler(UiHandlerEvent) */
    private final List<UiHandlerEvent> eventHandlers;

    /** @see #isFocused() */
    private boolean focused;

    /**
     * The constructor.
     */
    public EventSender() {

      super();
      this.eventHandlers = new LinkedList<UiHandlerEvent>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFocused() {

      return this.focused;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(UiEvent event) {

      if (event.getType() == EventType.FOCUS_GAIN) {
        this.focused = true;
      } else if (event.getType() == EventType.FOCUS_LOSS) {
        this.focused = false;
      }
      UiEventObserver eventObserver = null;
      AttributeReadEventObserver observerSource = getObserverSource();
      if (observerSource != null) {
        eventObserver = observerSource.getEventObserver();
      }
      if (eventObserver != null) {
        eventObserver.beforeHandler(event);
      }
      // getLogger().debug(event.toString());
      for (UiHandlerEvent handler : this.eventHandlers) {
        // TODO hohwille if a handler will remove itself in onEvent we might get
        // ConcurrentModificationException
        handler.onEvent(event);
      }
      if (eventObserver != null) {
        eventObserver.afterHandler(event);
      }
    }
  }

  /**
   * @return a {@link String} representation of this object that qualifies as source description that might be
   *         displayed to end-users (unlike {@link #toString()} what is for debugging only).
   */
  @Override
  public String getSource() {

    return getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName() + "@" + getId();
  }

  /**
   * This inner class gives access to methods not visible in the public API.<br/>
   * <b>ATTENTION:</b><br/>
   * It is reserved for internal usage and should therefore never be used by regular users. Otherwise
   * compatibility might break with any release update.
   */
  public static class AccessHelper {

    /**
     * @see AbstractUiWidget#setMandatory(boolean)
     * 
     * @param widget is the {@link AbstractUiWidget}.
     * @param mandatory - see {@link AbstractUiWidget#setMandatory(boolean)}.
     */
    public static void setMandatory(AbstractUiWidget<?> widget, boolean mandatory) {

      widget.setMandatory(mandatory);
    }

    /**
     * @see AbstractUiWidget#doSetValue(Object, boolean)
     * 
     * @param <VALUE> - see {@link AbstractUiWidget#doSetValue(Object, boolean)}.
     * @param widget is the {@link AbstractUiWidget}.
     * @param value - see {@link AbstractUiWidget#doSetValue(Object, boolean)}
     * @param forUser - see {@link AbstractUiWidget#doSetValue(Object, boolean)}
     */
    public static <VALUE> void doSetValue(AbstractUiWidget<VALUE> widget, VALUE value, boolean forUser) {

      widget.doSetValue(value, forUser);
    }

    /**
     * @see AbstractUiWidget#doGetValue(Object, ValidationState)
     * 
     * @param <VALUE> - see {@link AbstractUiWidget#doGetValue(Object, ValidationState)}.
     * @param widget is the {@link AbstractUiWidget}.
     * @param template - see {@link AbstractUiWidget#doGetValue(Object, ValidationState)}.
     * @param state - see {@link AbstractUiWidget#doGetValue(Object, ValidationState)}.
     * @return - see {@link AbstractUiWidget#doGetValue(Object, ValidationState)}.
     */
    public static <VALUE> VALUE doGetValue(AbstractUiWidget<VALUE> widget, VALUE template, ValidationState state) {

      return widget.doGetValue(template, state);
    }

    /**
     * @see AbstractUiWidget#getRecentValue()
     * 
     * @param <VALUE> - see {@link AbstractUiWidget#getRecentValue()}.
     * 
     * @param widget is the {@link AbstractUiWidget}.
     * @return - see {@link AbstractUiWidget#getRecentValue()}.
     */
    public static <VALUE> VALUE getRecentValue(AbstractUiWidget<VALUE> widget) {

      return widget.getRecentValue();
    }

    /**
     * @see AbstractUiWidget#doValidate(ValidationState, Object)
     * 
     * @param <VALUE> - see {@link AbstractUiWidget#doValidate(ValidationState, Object)}.
     * 
     * @param widget is the {@link AbstractUiWidget}.
     * @param state - see {@link AbstractUiWidget#doValidate(ValidationState, Object)}.
     * @param value - see {@link AbstractUiWidget#doValidate(ValidationState, Object)}.
     */
    public static <VALUE> void doValidate(AbstractUiWidget<VALUE> widget, ValidationState state, VALUE value) {

      widget.validateInteral(state, value);
    }

    /**
     * @see AbstractUiWidget#clearValidationFailure()
     * 
     * @param widget is the {@link AbstractUiWidget}.
     */
    public static void clearValidationFailure(AbstractUiWidget<?> widget) {

      widget.clearValidationFailure();
    }

    /**
     * @see AbstractUiWidget#clearValidity()
     * 
     * @param widget is the {@link AbstractUiWidget}.
     */
    public static void clearValidity(AbstractUiWidget<?> widget) {

      widget.clearValidity();
    }

    /**
     * @see AbstractUiWidget#convertValueToString(Object)
     * 
     * @param <VALUE> - see {@link AbstractUiWidget#convertValueToString(Object)}.
     * @param widget is the {@link AbstractUiWidget}.
     * @param value - see {@link AbstractUiWidget#convertValueToString(Object)}.
     * @return - see {@link AbstractUiWidget#convertValueToString(Object)}.
     */
    public static <VALUE> String convertValueToString(AbstractUiWidget<VALUE> widget, VALUE value) {

      return widget.convertValueToString(value);
    }
  }

}
