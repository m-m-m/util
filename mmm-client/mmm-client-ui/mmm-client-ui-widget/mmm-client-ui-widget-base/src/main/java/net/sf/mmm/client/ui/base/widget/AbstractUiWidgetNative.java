/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteAriaRole;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteFlagAdvanced;
import net.sf.mmm.client.ui.api.common.FlagModifier;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.event.UiEventMode;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetAbstractComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.base.aria.role.AbstractRole;
import net.sf.mmm.client.ui.base.aria.role.RoleFactory;
import net.sf.mmm.client.ui.base.attribute.AbstractFlagAdvanced;
import net.sf.mmm.client.ui.base.widget.adapter.AbstractUiWidgetAdapter;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectDisposedException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ReadOnlyException;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base implementation of {@link UiWidget} or more precisely
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetNative}. It is independent of any native toolkit via
 * {@link UiWidgetAdapter} that is {@link #getWidgetAdapter() lazily created}.<br/>
 * If you want to create an implementation of all the {@link UiWidget}s for a native UI toolkit, you are
 * strongly encouraged to extend from this class and its subclasses (all classes named
 * <code>AbstractUiWidget*</code>).
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Use {@link Void} for no value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetNative<ADAPTER extends UiWidgetAdapter, VALUE> extends AbstractUiWidget<VALUE>
    implements UiWidgetAbstractComposite, AttributeWriteAriaRole {

  /** @see #setIdPrefix(String) */
  private static String idPrefix = "mmm";

  /** @see #createUniqueId() */
  private static int idCounter;

  /** @see #getWidgetAdapter() */
  private ADAPTER widgetAdapter;

  /** @see #getParent() */
  private UiWidgetComposite<?> parent;

  /** @see #getId() */
  private String id;

  /** @see #isVisible() */
  private boolean visible;

  /** @see #getVisibleFlag() */
  private VisibleFlag visibleFlag;

  /** @see #isEnabled() */
  private boolean enabled;

  /** @see #isDisposed() */
  private boolean disposed;

  /** @see #getTooltip() */
  private String tooltip;

  /** @see #getStyles() */
  private String styles;

  /** @see #getWidth() */
  private Length width;

  /** @see #getHeight() */
  private Length height;

  /** @see #getAriaRole() */
  private AbstractRole ariaRole;

  /** @see #getMode() */
  private UiMode mode;

  /** @see #getModeFixed() */
  private UiMode modeFixed;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetNative(UiContext context, ADAPTER widgetAdapter) {

    super(context);
    this.widgetAdapter = widgetAdapter;
    this.visible = true;
    this.enabled = true;
    this.styles = "";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean hasWidgetAdapter() {

    return (this.widgetAdapter != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final ADAPTER getWidgetAdapter() {

    if (this.widgetAdapter == null) {
      if (this.disposed) {
        throw new ObjectDisposedException(this);
      }
      this.widgetAdapter = createWidgetAdapter();
      ((AbstractUiWidgetAdapter<?>) this.widgetAdapter).setUiWidget(this);
      initializeWidgetAdapter(this.widgetAdapter);

      if (getAriaRole() != null) {
        this.ariaRole.setDelegate(this.widgetAdapter);
      }
    }
    return this.widgetAdapter;
  }

  /**
   * This method gives access to {@link #getWidgetAdapter()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is only for internal purposes when implementing {@link UiWidget}s. It shall never be used by
   * regular users (what also applies for all classes in this <code>base</code> packages).
   * 
   * @param <A> is the generic type of {@link #getWidgetAdapter()}.
   * @param widget is the widget.
   * @return the {@link #getWidgetAdapter() widget adapter} of the given <code>widget</code>.
   */
  public static final <A extends UiWidgetAdapter> A getWidgetAdapter(AbstractUiWidgetNative<A, ?> widget) {

    return widget.getWidgetAdapter();
  }

  /**
   * This method is called from {@link #getWidgetAdapter()} to initialize the {@link UiWidgetAdapter}. All
   * attributes of this widget need to be set in the {@link UiWidgetAdapter}.
   * 
   * @param adapter is the {@link UiWidgetAdapter} to initialize.
   */
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    // for ariaRole see getWidgetAdapter()
    if (this.mode != null) {
      getContext().getModeChanger().changeMode(this, this.mode);
    }
    adapter.setVisible(isVisible(), true);
    if (!this.enabled) {
      adapter.setEnabled(this.enabled);
    }
    if (this.parent != null) {
      adapter.setParent(this.parent);
    }
    adapter.setId(getId());
    if (this.tooltip != null) {
      adapter.setTooltip(this.tooltip);
    }
    if (!this.styles.isEmpty()) {
      adapter.setStyles(this.styles);
    }
    if (this.width != null) {
      adapter.setWidth(this.width);
    }
    if (this.height != null) {
      adapter.setHeight(this.height);
    }
    if (hasEventSender()) {
      adapter.setEventSender(this, getEventSender());
    }
  }

  /**
   * This method creates the {@link #getWidgetAdapter() widget adapter}.<br/>
   * This design is done to give more flexibility by overriding and also for lazy initialization of the
   * widget.
   * 
   * @return a new instance of the {@link #getWidgetAdapter() underlying widget}.
   */
  protected abstract ADAPTER createWidgetAdapter();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String newId) {

    if (getBasicUtil().isEqual(this.id, newId)) {
      return;
    }
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setId(newId);
    }
    this.id = newId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    if (this.id == null) {
      setId(createUniqueId());
    }
    return this.id;
  }

  /**
   * This method sets the static prefix used for generated IDs.
   * 
   * @param idPrefix is the idPrefix to set
   */
  // TODO hohwille statics are evil. Better use component "WidgetIdGenerator" via UiContext
  public static void setIdPrefix(String idPrefix) {

    AbstractUiWidgetNative.idPrefix = idPrefix;
  }

  /**
   * @return a new unique value for {@link #getId()}.
   */
  protected String createUniqueId() {

    return idPrefix + idCounter++;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setFocused() {

    int childCount = getChildCount();
    // set focus to the first focusable field/widget...
    for (int childIndex = 0; childIndex < childCount; childIndex++) {
      AbstractUiWidget<?> child = (AbstractUiWidget<?>) getChild(childIndex);
      if (child.setFocused()) {
        return true;
      }
    }
    // not supported by default...
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetComposite<?> getParent() {

    return this.parent;
  }

  /**
   * This method sets the {@link #getParent() parent}.
   * 
   * @param parent is the new {@link #getParent() parent}.
   */
  @Override
  protected void setParent(UiWidgetComposite<?> parent) {

    this.parent = parent;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setParent(parent);
    }
  }

  /**
   * This method removes this widget from its {@link #getParent() parent}. The {@link #getParent() parent} is
   * set to <code>null</code> and the native widget is removed from its parent.
   */
  @Override
  protected void removeFromParent() {

    if (this.widgetAdapter != null) {
      this.widgetAdapter.removeFromParent();
    }
    this.parent = null;
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
   * {@inheritDoc}
   */
  @Override
  public final UiMode getMode() {

    return this.mode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setMode(UiMode mode) {

    super.setMode(mode);
    // #78: set focus into first editable field/widget when switching to edit mode
    if (mode.isEditable()) {
      setFocused();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setMode(UiMode newMode, boolean programmatic) {

    if (this.mode == newMode) {
      // mode not changed, nothing to do...
      return;
    }
    if (this.modeFixed != null) {
      // fixed mode prevents changing the mode...
      return;
    }
    doSetMode(newMode);
    getContext().getModeChanger().changeMode(this, newMode);
    setModeRecursive(newMode, programmatic);
    this.mode = newMode;
    fireEvent(new UiEventMode(this, programmatic));
  }

  /**
   * This method is called from {@link #setMode(UiMode)} if the {@link UiMode} actually changed.
   * 
   * @param uiMode is the new {@link UiMode}.
   */
  protected void doSetMode(UiMode uiMode) {

    // nothing by default...
  }

  /**
   * This method is called from {@link #setMode(UiMode)} to recursively change the {@link UiMode}.
   * 
   * @param newMode is the new {@link UiMode}.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   */
  void setModeRecursive(UiMode newMode, boolean programmatic) {

    int childCount = getChildCount();
    for (int i = 0; i < childCount; i++) {
      UiWidget child = getChild(i);
      ((AbstractUiWidget<?>) child).setMode(newMode, programmatic);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiMode getModeFixed() {

    return this.modeFixed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModeFixed(UiMode modeFixed) {

    if (this.modeFixed == modeFixed) {
      return;
    }
    this.modeFixed = null;
    setMode(modeFixed);
    this.modeFixed = modeFixed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    return this.disposed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    if ((!this.disposed) && (this.widgetAdapter != null)) {
      this.widgetAdapter.dispose();
      this.widgetAdapter = null;
    }
    this.disposed = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setVisible(boolean visible) {

    setVisible(visible, true);
  }

  /**
   * @see #setVisible(boolean)
   * 
   * @param visibility - see {@link #setVisible(boolean)}.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   */
  public void setVisible(boolean visibility, boolean programmatic) {

    if (this.visible != visibility) {
      boolean oldVisibility = this.visible;
      if (this.visibleFlag != null) {
        oldVisibility = this.visibleFlag.getFlag();
      }
      this.visible = visibility;
      boolean newVisible = visibility;
      if (this.visibleFlag != null) {
        // this would actually not be necessary but we do not want to assume implementation knowledge here...
        newVisible = this.visibleFlag.getFlag();
      }
      if (oldVisibility != newVisible) {
        if (this.widgetAdapter != null) {
          this.widgetAdapter.setVisible(newVisible, programmatic);
        }
        visibilityChanged(newVisible, programmatic);
      }
    }
  }

  /**
   * Called from {@link #setVisible(boolean)} is the visibility has actually changed.
   * 
   * @param visibility is the new {@link #isVisible() visibility}.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   */
  protected void visibilityChanged(boolean visibility, boolean programmatic) {

    // nothing by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    if (this.visibleFlag != null) {
      return this.visibleFlag.getFlag();
    } else {
      return this.visible;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AttributeWriteFlagAdvanced getVisibleFlag() {

    if (this.visibleFlag == null) {
      this.visibleFlag = new VisibleFlag();
    }
    return this.visibleFlag;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isVisibleRecursive() {

    if (!isVisible()) {
      return false;
    }
    if ((this.parent == null) || !this.parent.isVisible()) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    if (getBasicUtil().isEqual(this.tooltip, tooltip)) {
      return;
    }
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setTooltip(tooltip);
    }
    this.tooltip = tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTooltip() {

    return this.tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    if (this.enabled != enabled) {
      if (this.widgetAdapter != null) {
        this.widgetAdapter.setEnabled(enabled);
      }
      this.enabled = enabled;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {

    if (!this.enabled) {
      return false;
    }
    // if ((this.parent == null) || !this.parent.isEnabled()) {
    // return false;
    // }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getWidth() {

    if ((this.width == null) && (this.widgetAdapter != null)) {
      return this.widgetAdapter.getWidth();
    }
    if (this.width == null) {
      return Length.ZERO;
    }
    return this.width;
  }

  /**
   * @return the height
   */
  @Override
  public Length getHeight() {

    if ((this.height == null) && (this.widgetAdapter != null)) {
      return this.widgetAdapter.getHeight();
    }
    if (this.height == null) {
      return Length.ZERO;
    }
    return this.height;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    this.width = convertWidth(width);
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setWidth(this.width);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    this.height = convertHeight(height);
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setWidth(this.height);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(Length newWidth, Length newHeight) {

    this.width = convertWidth(newWidth);
    this.height = convertHeight(newHeight);
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setSize(this.width, this.height);
    }
  }

  /**
   * Converts the {@link Length} given as width.<br/>
   * Just returns the given {@link Length} by default. Override to change (e.g. if you need to convert to
   * {@link net.sf.mmm.client.ui.api.common.SizeUnit#PIXEL pixels}). Global conversion should be done in
   * {@link #getWidgetAdapter() widget adapter} instead.
   * 
   * @param newWidth is the width to convert.
   * @return the converted {@link Length} value.
   */
  protected Length convertWidth(Length newWidth) {

    return newWidth;
  }

  /**
   * Converts the {@link Length} given as height.<br/>
   * Just returns the given {@link Length} by default. Override to change (e.g. if you need to convert to
   * {@link net.sf.mmm.client.ui.api.common.SizeUnit#PIXEL pixels}). Global conversion should be done in
   * {@link #getWidgetAdapter() widget adapter} instead.
   * 
   * @param newHeight is the height to convert.
   * @return the converted {@link Length} value.
   */
  protected Length convertHeight(Length newHeight) {

    return newHeight;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Role getAriaRole() {

    if (this.ariaRole == null) {
      Class<? extends Role> ariaRoleFixedType = getAriaRoleFixedType();
      if (ariaRoleFixedType != null) {
        setAriaRole(ariaRoleFixedType);
      }
    }
    return this.ariaRole;
  }

  /**
   * @return the {@link Class} reflecting the {@link Role} that is fixed for this widget or <code>null</code>
   *         if the role can be changed dynamically.
   */
  protected Class<? extends Role> getAriaRoleFixedType() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <ROLE extends Role> ROLE setAriaRole(Class<ROLE> roleType) {

    Class<? extends Role> ariaRoleFixedType = getAriaRoleFixedType();
    if (ariaRoleFixedType != null) {
      if (this.ariaRole != null) {
        throw new ReadOnlyException(this, "ARIA role");
      }
      if (roleType != ariaRoleFixedType) {
        throw new ObjectMismatchException(roleType, ariaRoleFixedType);
      }
    }
    NlsNullPointerException.checkNotNull("roleInterface", roleType);
    if (this.ariaRole != null) {
      if (roleType.equals(this.ariaRole.getRoleInterface())) {
        return (ROLE) this.ariaRole;
      }
    }
    RoleFactory roleFactory = getContext().getRoleFactory();
    ROLE role = roleFactory.createRole(roleType);
    this.ariaRole = (AbstractRole) role;
    if (this.widgetAdapter != null) {
      this.ariaRole.setDelegate(this.widgetAdapter);
    }
    return role;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyles() {

    return this.styles;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    NlsNullPointerException.checkNotNull("styles", styles);
    this.styles = styles;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setStyles(this.styles);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrimaryStyle() {

    if (this.styles.isEmpty()) {
      return null;
    } else {
      int index = this.styles.indexOf(' ');
      if (index >= 0) {
        return this.styles.substring(0, index);
      } else {
        return this.styles;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPrimaryStyle(String primaryStyle) {

    NlsNullPointerException.checkNotNull("primaryStyle", primaryStyle);
    assert (primaryStyle.matches(STYLE_PATTERN_SINGLE)) : primaryStyle;
    if (this.styles.isEmpty()) {
      this.styles = primaryStyle;
    } else {
      int startIndex = getIndexOfStyle(primaryStyle);
      if (startIndex == 0) {
        // it is already the primary style - nothing to do...
        return;
      }
      StringBuilder buffer = new StringBuilder(primaryStyle);
      if (startIndex > 0) {
        // the primaryStyle is an existing style but NOT the primary style. We have to move it to the start.
        int endIndex = startIndex + primaryStyle.length();
        if (endIndex < this.styles.length()) {
          // also remove separating space after style-name
          endIndex++;
        }
        buffer.append(' ');
        buffer.append(this.styles.substring(0, startIndex));
        buffer.append(this.styles.substring(endIndex));
      } else {
        // the primaryStyle is a new style - we have to replace the current primary style.
        int index = this.styles.indexOf(' ');
        if (index >= 0) {
          buffer.append(' ');
          buffer.append(this.styles.substring(index + 1));
        } else {
          // nothing to do...
        }
      }
      this.styles = buffer.toString();
    }
    if (this.widgetAdapter != null) {
      if (this.widgetAdapter.isStyleDeltaSupported()) {
        this.widgetAdapter.setPrimaryStyle(primaryStyle);
      } else {
        this.widgetAdapter.setStyles(this.styles);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean hasStyle(String style) {

    NlsNullPointerException.checkNotNull("style", style);
    return (getIndexOfStyle(style) >= 0);
  }

  /**
   * @see #hasStyle(String)
   * 
   * @param style is the {@link #hasStyle(String) style to check}.
   * @return the start-index of the given <code>style</code> in {@link #getStyles() styles} or <code>-1</code>
   *         if NOT present.
   */
  private int getIndexOfStyle(String style) {

    return getIndexOfStyle(this.styles, style);
  }

  /**
   * @see #getIndexOfStyle(String)
   * 
   * @param allStyles is the {@link String} with all current styles separated with whitespaces.
   * @param style is the single style to check.
   * @return the start-index of the given <code>style</code> in <code>allStyles</code> or <code>-1</code> if
   *         NOT present.
   */
  public static int getIndexOfStyle(String allStyles, String style) {

    int length = style.length();
    int index = allStyles.indexOf(style);
    while (index >= 0) {
      boolean validStart = true;
      int end = index + length;
      if (index > 0) {
        char c = allStyles.charAt(index - 1);
        if (c != ' ') {
          validStart = false;
        }
      }
      if (validStart) {
        if ((end == allStyles.length()) || (allStyles.charAt(end) == ' ')) {
          return index;
        }
      }
      index = allStyles.indexOf(style, end);
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean addStyle(String style) {

    NlsNullPointerException.checkNotNull("style", style);
    assert (style.matches(STYLE_PATTERN_SINGLE));
    if (!hasStyle(style)) {
      if (this.styles == null) {
        this.styles = style;
      } else {
        this.styles = this.styles + " " + style;
      }
      if (this.widgetAdapter != null) {
        if (this.widgetAdapter.isStyleDeltaSupported()) {
          this.widgetAdapter.addStyle(style);
        } else {
          this.widgetAdapter.setStyles(this.styles);
        }
      }
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeStyle(String style) {

    NlsNullPointerException.checkNotNull("style", style);
    assert (!style.isEmpty());
    assert (!style.contains(" "));
    int startIndex = getIndexOfStyle(style);
    if (startIndex >= 0) {
      int endIndex = startIndex + style.length();
      if (endIndex < this.styles.length()) {
        // also remove separating space after style-name
        endIndex++;
      }
      this.styles = this.styles.substring(0, startIndex) + this.styles.substring(endIndex);
      if (this.widgetAdapter != null) {
        if (this.widgetAdapter.isStyleDeltaSupported()) {
          this.widgetAdapter.removeStyle(style);
        } else {
          this.widgetAdapter.setStyles(this.styles);
        }
      }
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isModifiedRecursive() {

    int childCount = getChildCount();
    for (int i = 0; i < childCount; i++) {
      UiWidget child = getChild(i);
      if (child.isModified()) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void clearMessages() {

    super.clearMessages();
    int childCount = getChildCount();
    for (int i = 0; i < childCount; i++) {
      UiWidget child = getChild(i);
      if (child.isModified()) {
        child.clearMessages();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void clearValidity() {

    super.clearValidity();
    int childCount = getChildCount();
    for (int i = 0; i < childCount; i++) {
      AbstractUiWidget<?> child = (AbstractUiWidget<?>) getChild(i);
      child.clearValidity();
    }
  }

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * It performs the {@link #validate(ValidationState) validation} of this widget by delegating to
   * {@link #validateRecursive(ValidationState)}. It may be overridden to collect potential validation
   * failures and attach them to this widget. You should not forget the super-call in such case.
   */
  @Override
  protected void doValidate(ValidationState state, VALUE value) {

    super.doValidate(state, value);
    validateRecursive(state);
  }

  /**
   * This method performs the recursive validation of potential children of this widget excluding the
   * validation of this widget itself. A legal implementation of a composite widget needs to call
   * {@link #validate(ValidationState)} on all child widgets.
   * 
   * @param state is the {@link ValidationState}.
   */
  void validateRecursive(ValidationState state) {

    int size = getChildCount();
    for (int i = 0; i < size; i++) {
      AbstractUiWidget<?> child = (AbstractUiWidget<?>) getChild(i);
      Boolean valid = child.getDataBinding().getValidity();
      if (valid == null) {
        // boolean childValid = child.validate(state);
        child.getValueDirect(null, state);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidget getChild(int index) throws IndexOutOfBoundsException {

    throw new IndexOutOfBoundsException(String.valueOf(index));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildIndex(UiWidget child) {

    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildCount() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidget getChild(String childId) {

    NlsNullPointerException.checkNotNull("childId", childId);
    int size = getChildCount();
    for (int i = 0; i < size; i++) {
      UiWidget child = getChild(i);
      if ((child != null) && (childId.equals(child.getId()))) {
        return child;
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(VALUE value, boolean forUser) {

    // default implementation only relevant for subclasses binding VOID for <VALUE>
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE doGetValue(VALUE template, ValidationState state) throws RuntimeException {

    // default implementation only relevant for subclasses binding VOID for <VALUE>
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(getClass().getSimpleName());
    if (this.id != null) {
      buffer.append("[");
      buffer.append(this.id);
      buffer.append("]");
    }
    if (!this.visible) {
      buffer.append("[hidden]");
    }
    if (!this.enabled) {
      buffer.append("[disabled]");
    }
    return buffer.toString();
  }

  /**
   * This inner class is the implementation of the visible flag.
   */
  private class VisibleFlag extends AbstractFlagAdvanced {

    /**
     * The constructor.
     */
    public VisibleFlag() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getFlag() {

      if (!AbstractUiWidgetNative.this.visible) {
        return false;
      }
      return super.getFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getFlag(FlagModifier modifier) {

      if (modifier == null) {
        return AbstractUiWidgetNative.this.visible;
      }
      return super.getFlag(modifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFlag(boolean flag, FlagModifier modifier) {

      boolean oldFlag = getFlag();
      if (modifier == null) {
        AbstractUiWidgetNative.this.visible = flag;
      }
      super.setFlag(flag, modifier);
      boolean newFlag = getFlag();
      if (oldFlag != newFlag) {
        if (AbstractUiWidgetNative.this.widgetAdapter != null) {
          AbstractUiWidgetNative.this.widgetAdapter.setVisible(newFlag, true);
        }
        visibilityChanged(newFlag, true);
      }
    }
  }

}
