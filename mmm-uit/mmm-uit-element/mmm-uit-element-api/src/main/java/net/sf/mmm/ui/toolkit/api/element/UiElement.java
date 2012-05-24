/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.element;

import net.sf.mmm.ui.toolkit.api.adapter.UiAdapter;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteMode;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStyles;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible;
import net.sf.mmm.ui.toolkit.api.common.EnabledState;
import net.sf.mmm.ui.toolkit.api.common.UiMode;
import net.sf.mmm.ui.toolkit.api.common.VisibleState;
import net.sf.mmm.ui.toolkit.api.element.composite.UiElementComposite;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is the abstract base class for an element of the UI (user-interface). Such element can be a Button,
 * ComboBox, Accordion, VerticalPanel, Menu, Window, etc.<br/>
 * <br/>
 * A {@link UiElement} has a {@link #getValue() value} that is displayed to the end-user and may be edited.
 * This concept shall also be used for composite elements that compose atomic elements to build entire data
 * forms.<br/>
 * Therefore according subclasses of {@link UiElement} shall be extended for your custom code to build a
 * particular UI. {@link UiElement}s that have no value (e.g. a Button or a Menu) indicate this by using
 * {@link Void} for the generic &lt;VALUE&gt;.<br/>
 * <br/>
 * Further a {@link UiElement} internally has an {@link #getAdapter() adapter} that is used to abstract from
 * the underlying native UI toolkit. The adapter is lazily created when needed on
 * {@link #initialize(UiConfiguration) initialization}. The initialization has to be done only once for each
 * top-level {@link UiElement} (root {@link #getParent() node}). If a new {@link UiElement} is attached to a
 * {@link #getParent() parent} that has already been {@link #initialize(UiConfiguration) initialized}, the
 * sub-tree of new {@link UiElement}s will also be {@link #initialize(UiConfiguration) initialized}
 * automatically. This approach allows to switch the underlying widgets without rewriting the entire UI code.
 * Additionally this approach allows simple testing of the UI without the complex environment of a native UI
 * toolkit such as GWT.<br/>
 * <br/>
 * A {@link UiElement} also has a {@link #getMode() mode}. This allows to
 * {@link #setMode(net.sf.mmm.ui.toolkit.api.common.UiMode) switch} the mode from {@link UiMode#VIEW view} to
 * {@link UiMode#EDIT edit} mode.<br/>
 * <br/>
 * The philosophy of the {@link UiElement}s is to build abstract base classes for the technical foundation of
 * this UI-Toolkit. Based on this foundation logical {@link UiElement}-classes are derived that extend the
 * technical classes e.g. for elements to display and edit custom {@link net.sf.mmm.util.lang.api.Datatype}s.
 * We strongly encourage you to follow this paradigm. You shall create custom
 * {@link net.sf.mmm.util.lang.api.Datatype}s specific for the needs and requirements of your application and
 * data-model. Next, you should create according {@link UiElement}s that specify how these
 * {@link net.sf.mmm.util.lang.api.Datatype}s are shown and edited by end-users. On the next level create
 * {@link UiElement}s to view and edit your entire business-objects. Design them following the idioms of
 * <em>devide and conquer</em> as well as the principle of <em>resuse</em>. This is a major key for
 * maintenance of complex UIs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Use {@link Void} for {@link UiElement}
 *        s that do not carry a {@link #getValue() value}.
 * @param <ADAPTER> is the generic type of the underlying {@link #getAdapter()}.
 */
public abstract class UiElement<VALUE, ADAPTER extends UiAdapter<?>> implements AttributeReadHtmlId,
    AttributeWriteVisible, AttributeWriteTooltip, AttributeWriteEnabled, AttributeWriteStyles, AttributeWriteMode,
    AttributeWriteValue<VALUE> {

  /** @see #getValue() */
  private VALUE value;

  /** @see #getAdapter() */
  private ADAPTER adapter;

  /** @see #getId() */
  private final String id;

  /** @see #getTooltip() */
  private String tooltip;

  /** @see #getVisibleState() */
  private VisibleState visibleState;

  /** @see #getEnabledState() */
  private EnabledState enabledState;

  /** @see #getMode() */
  private UiMode mode;

  /** @see #getStyles() */
  private String styles;

  /**
   * The constructor.
   * 
   * @param id is the {@link #getId() ID}.
   */
  public UiElement(String id) {

    super();
    this.id = id;
    this.visibleState = VisibleState.VISIBLE;
    this.enabledState = EnabledState.ENABLED;
    this.mode = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {

    return this.id;
  }

  /**
   * @return the {@link EnabledState} of this element.
   */
  protected final EnabledState getEnabledState() {

    if ((this.adapter == null) && (this.enabledState.isEnabled())) {
      return EnabledState.BLOCKED;
    }
    return this.enabledState;
  }

  /**
   * {@inheritDoc}
   */
  public final boolean isEnabled() {

    // TODO is disabled if parent is disabled like in isVisible()?
    return getEnabledState().isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public final void setEnabled(boolean enabled) {

    EnabledState newEnabledState = this.enabledState.setEnabled(enabled);
    if (this.enabledState != newEnabledState) {
      if (this.enabledState.isEnabled() != newEnabledState.isEnabled()) {
        doSetVisible(enabled);
      }
      this.enabledState = newEnabledState;
    }
  }

  /**
   * @return the {@link VisibleState} of this element.
   */
  protected final VisibleState getVisibleState() {

    if ((this.adapter == null) && (this.visibleState.isVisible())) {
      return VisibleState.BLOCKED;
    }
    return this.visibleState;
  }

  /**
   * {@inheritDoc}
   */
  public final boolean isVisible() {

    if (!this.visibleState.isVisible()) {
      return false;
    }
    if (this.adapter == null) {
      return false;
    }
    UiElementComposite<?, ?> parent = getParent();
    if (parent != null) {
      return parent.isVisible();
    } else {
      return true;
    }
  }

  /**
   * {@inheritDoc}
   */
  public final void setVisible(boolean visible) {

    VisibleState newVisibleState = this.visibleState.setVisible(visible);
    if (this.visibleState != newVisibleState) {
      if (this.visibleState.isVisible() != newVisibleState.isVisible()) {
        doSetVisible(visible);
      }
      this.visibleState = newVisibleState;
    }
  }

  /**
   * This method is called from a virtual {@link #getParent() parent} if this element shall be blocked or
   * unblocked.
   * 
   * @param blocked - <code>true</code> if the element shall be {@link VisibleState#BLOCKED blocked},
   *        <code>false</code> otherwise (to unblock).
   */
  protected final void setVisibleBlocked(boolean blocked) {

    VisibleState newVisibleState = this.visibleState.setBlocked(blocked);
    if (this.visibleState != newVisibleState) {
      if (this.visibleState.isVisible() != newVisibleState.isVisible()) {
        doSetVisible(newVisibleState.isVisible());
      }
      this.visibleState = newVisibleState;
    }
  }

  /**
   * @param visible - see {@link #setVisible(boolean)}.
   */
  private void doSetVisible(boolean visible) {

    if (this.adapter != null) {
      this.adapter.setVisible(visible);
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiMode getMode() {

    return this.mode;
  }

  /**
   * {@inheritDoc}
   */
  public final void setMode(UiMode mode) {

    if (this.mode != mode) {
      doSetMode(mode);
      this.mode = mode;
    }
  }

  /**
   * This method is called from {@link #setMode(UiMode)} if the mode has actually changed.
   * 
   * @param newMode is the new {@link UiMode}.
   */
  protected void doSetMode(UiMode newMode) {

    if (newMode == UiMode.VIEW) {
      doSetMode(false);
    } else if (newMode == UiMode.EDIT) {
      doSetMode(true);
    } else {
      // invoke logic from UiConfiguration?
    }
  }

  /**
   * This method is called from {@link #doSetMode(UiMode)} for the regular modes {@link UiMode#VIEW} or
   * {@link UiMode#EDIT}.
   * 
   * @param editMode - <code>true</code> for {@link UiMode#EDIT} and <code>false</code> for
   *        {@link UiMode#VIEW}.
   */
  protected void doSetMode(boolean editMode) {

    // TODO
  }

  /**
   * {@inheritDoc}
   */
  public final String getTooltip() {

    return this.tooltip;
  }

  /**
   * {@inheritDoc}
   */
  public final void setTooltip(String tooltip) {

    if ((this.adapter != null) && (!this.tooltip.equals(tooltip))) {
      this.adapter.setTooltip(tooltip);
    }
    this.tooltip = tooltip;
  }

  /**
   * {@inheritDoc}
   */
  public final String getStyles() {

    return this.styles;
  }

  /**
   * {@inheritDoc}
   */
  public final boolean hasStyle(String style) {

    return (indexOfStyle(style) >= 0);
  }

  /**
   * @see #hasStyle(String)
   * 
   * @param style is the {@link #hasStyle(String) style to check}.
   * @return the start-index of the given <code>style</code> in {@link #getStyles() styles} or <code>-1</code>
   *         if NOT present.
   */
  private int indexOfStyle(String style) {

    if (this.styles != null) {
      int length = style.length();
      int index = this.styles.indexOf(style);
      while (index >= 0) {
        boolean validStart = true;
        int end = index + length;
        if (index > 0) {
          index--;
          char c = this.styles.charAt(index);
          if (c != ' ') {
            validStart = false;
          }
        }
        if (validStart) {
          if (end == this.styles.length()) {
            return index;
          }
          if (this.styles.charAt(end) == ' ') {
            return index;
          }
        }
        index = this.styles.indexOf(style, end);
      }
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  public final void setStyles(String styles) {

    assert (styles != null);
    this.styles = styles.trim();
    // normalize?
    updateStyles();
  }

  /**
   * {@inheritDoc}
   */
  public final void addStyle(String style) {

    assert (style != null);
    assert (!style.isEmpty());
    assert (!style.contains(" "));
    if (!hasStyle(style)) {
      if (this.styles == null) {
        this.styles = style;
      } else {
        this.styles = this.styles + " " + style;
      }
      updateStyles();
    }
  }

  /**
   * This method updates the styles in the view.
   */
  protected final void updateStyles() {

    if (this.adapter != null) {
      // this.view.setStyles(this.styles);
    }
  }

  /**
   * {@inheritDoc}
   */
  public final boolean removeStyle(String style) {

    assert (!style.isEmpty());
    assert (!style.contains(" "));
    if (this.styles != null) {
      int startIndex = indexOfStyle(style);
      if (startIndex >= 0) {
        int endIndex = startIndex + style.length();
        if (startIndex > 0) {
          endIndex++;
        }
        this.styles = this.styles.substring(0, startIndex) + this.styles.substring(endIndex);
        updateStyles();
        return true;
      }
    }
    return false;
  }

  /**
   * This method determines if this element has no value.<br/>
   * <b>NOTE:</b><br/>
   * This method can be used in combination with {@link #getValue()} to determine if the user entered invalid
   * data.
   * 
   * @return <code>true</code> if this element is entirely empty, <code>false</code> otherwise (if this
   *         element has a {@link #getValue() value} or the user entered invalid data).
   */
  public boolean isEmpty() {

    // TODO
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @return the current value of this object or <code>null</code> if this element {@link #isEmpty() is empty}
   *         or contains invalid data.
   */
  public final VALUE getValue() {

    return getValueLastSet();
  }

  /**
   * This method gets the current value contained in this element (as entered by the user).
   * 
   * @return the current value.
   * @throws RuntimeException if the user entered invalid data (or an internal exception occurred).
   */
  protected VALUE getValueInternal() throws RuntimeException {

    return getValueLastSet();
  }

  /**
   * This method gets the internal value that has been stored by the last call of {@link #setValue(Object)}.
   * 
   * @see #getValue()
   * 
   * @return the internal value.
   */
  protected final VALUE getValueLastSet() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  public final void setValue(VALUE value) {

    setValueInternal(value);
    this.value = value;
  }

  /**
   * This method is invoked from {@link #setValue(Object)} to update the value in the {@link #getAdapter()
   * view}.
   * 
   * @param newValue is the value to set. May be <code>null</code> to clear the element.
   */
  protected void setValueInternal(VALUE newValue) {

    if (this.adapter != null) {
      // this.adapter.setValue(this.value);
    }
  }

  /**
   * @return the parent element of this element.
   */
  public abstract UiElementComposite<?, ?> getParent();

  /**
   * This method gets the {@link Class} reflecting the underlying type of {@link UiAdapter}. It is required to
   * create the adapter for {@link #initializeAdapter(UiAdapter) initialization}.
   * 
   * @return the {@link #getAdapter() adapter} {@link Class}.
   */
  protected abstract Class<ADAPTER> getAdapterClass();

  /**
   * This method gets the underlying {@link UiAdapter}.
   * 
   * @return the {@link UiAdapter} or <code>null</code> if NOT yet created.
   */
  protected final ADAPTER getAdapter() {

    return this.adapter;
  }

  /**
   * This method initializes this {@link UiElement}. A tree of {@link UiElement}s has to be initialized once
   * from its top-level node in order to be displayed. During the initialization the {@link #getAdapter()
   * adapters} and their underlying native widgets are created.
   * 
   * @param configuration is the {@link UiConfiguration}.
   */
  protected final void initialize(UiConfiguration<?> configuration) {

    if (this.adapter != null) {
      // already initialized...
      return;
    }
    this.adapter = configuration.getAdapterFactory().create(getAdapterClass());
    initializeAdapter(this.adapter);
  }

  /**
   * This method initializes the {@link #getAdapter() view}.
   * 
   * @param widgetAdapter is the {@link UiAdapter} to initialize.
   */
  protected void initializeAdapter(ADAPTER widgetAdapter) {

    widgetAdapter.setId(this.id);
    widgetAdapter.setTooltip(this.tooltip);
  }

}
