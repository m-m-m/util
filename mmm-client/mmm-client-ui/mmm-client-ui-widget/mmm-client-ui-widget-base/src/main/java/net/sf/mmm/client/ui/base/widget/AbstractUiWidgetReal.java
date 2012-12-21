/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteAriaRole;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.AbstractUiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.aria.role.AbstractRole;
import net.sf.mmm.client.ui.base.aria.role.RoleFactory;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectDisposedException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base implementation of {@link UiWidget} or more precisely
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetReal}. It is independent of any native toolkit via
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
public abstract class AbstractUiWidgetReal<ADAPTER extends UiWidgetAdapter<?>, VALUE> extends AbstractUiWidget<VALUE>
    implements AbstractUiWidgetComposite, AttributeWriteAriaRole {

  /** @see #getWidgetAdapter() */
  private ADAPTER widgetAdapter;

  /** @see #getParent() */
  private UiWidgetComposite<?> parent;

  /** @see #getId() */
  private String id;

  /** @see #isVisible() */
  private boolean visible;

  /** @see #isEnabled() */
  private boolean enabled;

  /** @see #isDisposed() */
  private boolean disposed;

  /** @see #getTooltip() */
  private String tooltip;

  /** @see #getStyles() */
  private String styles;

  /** @see #getWidth() */
  private String width;

  /** @see #getHeight() */
  private String height;

  /** @see #getAriaRole() */
  private AbstractRole ariaRole;

  /** @see #getMode() */
  private UiMode mode;

  /** @see #getModeFixed() */
  private UiMode modeFixed;

  /** @see #setIdPrefix(String) */
  private static String idPrefix = "mmm";

  /** @see #createUniqueId() */
  private static int idCounter;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetReal(AbstractUiContext context) {

    super(context);
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
      this.widgetAdapter.setConfiguration(getContext().getConfiguration());
      initializeWidgetAdapter(this.widgetAdapter);
      if (this.ariaRole != null) {
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
  public static final <A extends UiWidgetAdapter<?>> A getWidgetAdapter(AbstractUiWidgetReal<A, ?> widget) {

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
    adapter.setVisible(this.visible);
    adapter.setEnabled(this.enabled);
    if (this.parent != null) {
      adapter.setParent(this.parent);
    }
    if (this.id != null) {
      adapter.setId(this.id);
    }
    if (this.tooltip != null) {
      adapter.setTooltip(this.tooltip);
    }
    if (!this.styles.isEmpty()) {
      adapter.setStyles(this.styles);
    }
    if (this.width != null) {
      if (this.height != null) {
        adapter.setSize(this.width, this.height);
      } else {
        adapter.setWidth(this.width);
      }
    } else if (this.height != null) {
      adapter.setHeight(this.height);
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

    if (((this.id == null) && (newId != null)) || (!this.id.equals(newId))) {
      if (this.widgetAdapter != null) {
        this.widgetAdapter.setId(newId);
      }
      this.id = newId;
    }
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
   * @param idPrefix is the idPrefix to set
   */
  public static void setIdPrefix(String idPrefix) {

    AbstractUiWidgetReal.idPrefix = idPrefix;
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

    this.parent = null;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.removeFromParent();
    }
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

    if (this.mode == mode) {
      // mode not changed, nothing to do...
      return;
    }
    if (this.modeFixed != null) {
      // fixed mode prevents changing the mode...
      return;
    }
    getContext().getModeChanger().changeMode(this, mode);
    this.mode = mode;
    setModeRecursive(mode);
  }

  /**
   * This method is called from {@link #setMode(UiMode)} to recursively change the {@link UiMode}.
   * 
   * @param newMode is the new {@link UiMode}.
   */
  void setModeRecursive(UiMode newMode) {

    int childCount = getChildCount();
    for (int i = 0; i < childCount; i++) {
      UiWidget child = getChild(i);
      child.setMode(this.mode);
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
  public final void setModeFixed(UiMode modeFixed) {

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
  public void setVisible(boolean visible) {

    if (this.visible != visible) {
      if (this.widgetAdapter != null) {
        this.widgetAdapter.setVisible(visible);
      }
      this.visible = visible;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    return this.visible;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isVisibleRecursive() {

    if (!this.visible) {
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

    if (hasWidgetAdapter()) {
      if (((this.tooltip == null) && (tooltip != null)) || (!this.tooltip.equals(tooltip))) {
        getWidgetAdapter().setTooltip(tooltip);
      }
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
    if ((this.parent == null) || !this.parent.isEnabled()) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getWidth() {

    if ((this.width == null) && (this.widgetAdapter != null)) {
      return this.widgetAdapter.getWidth();
    }
    return this.width;
  }

  /**
   * @return the height
   */
  @Override
  public String getHeight() {

    if ((this.height == null) && (this.widgetAdapter != null)) {
      return this.widgetAdapter.getHeight();
    }
    return this.height;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(String width) {

    this.width = width;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setWidth(width);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(String height) {

    this.height = height;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setWidth(height);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(String newWidth, String newHeight) {

    this.width = newWidth;
    this.height = newHeight;
    if (this.widgetAdapter != null) {
      this.widgetAdapter.setSize(newWidth, newHeight);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSizeInPixel(int newWidth, int newHeight) {

    setSize(newWidth + "px", newHeight + "px");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int widthInPixel) {

    setWidth(widthInPixel + "px");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int heightInPixel) {

    setHeight(heightInPixel + "px");
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
        // TODO hohwille create ObjectImmutableException()
        throw new IllegalStateException(ariaRoleFixedType.getSimpleName());
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
    updateStyles();
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

    if ((primaryStyle == null) || (primaryStyle.isEmpty())) {
      String currentPrimaryStyle = getPrimaryStyle();
      if (currentPrimaryStyle != null) {
        removeStyle(currentPrimaryStyle);
      }
      return;
    }
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
    updateStyles();
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

    int length = style.length();
    int index = this.styles.indexOf(style);
    while (index >= 0) {
      boolean validStart = true;
      int end = index + length;
      if (index > 0) {
        char c = this.styles.charAt(index - 1);
        if (c != ' ') {
          validStart = false;
        }
      }
      if (validStart) {
        if ((end == this.styles.length()) || (this.styles.charAt(end) == ' ')) {
          return index;
        }
      }
      index = this.styles.indexOf(style, end);
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addStyle(String style) {

    NlsNullPointerException.checkNotNull("style", style);
    assert (style.matches(STYLE_PATTERN_SINGLE));
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
      updateStyles();
      return true;
    }
    return false;
  }

  /**
   * This method updates the styles in the view.
   */
  protected final void updateStyles() {

    if (hasWidgetAdapter()) {
      this.widgetAdapter.setStyles(this.styles);
    }
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
   * 
   * <br/>
   * It performs the {@link #validate(ValidationState) validation} of this widget by delegating to
   * {@link #validateLocal(ValidationState)} and {@link #validateRecursive(ValidationState)}. It may be
   * overridden to collect potential validation failures and attach them to this widget. You should not forget
   * the super-call in such case.
   */
  @Override
  protected void doValidate(ValidationState state) {

    super.doValidate(state);
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
      UiWidget child = getChild(i);
      child.validate(state);
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
  protected void doSetValue(VALUE value) {

    // default implementation only relevant for subclasses binding VOID for <VALUE>
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE doGetValue() throws RuntimeException {

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

}
