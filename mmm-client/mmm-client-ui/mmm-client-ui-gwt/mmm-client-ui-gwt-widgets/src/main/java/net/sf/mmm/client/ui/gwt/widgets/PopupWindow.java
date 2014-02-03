/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteClosable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteMaximizable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteMaximized;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteMovable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteResizable;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.IconConstants;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;
import net.sf.mmm.util.lang.api.Direction;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is a {@link com.google.gwt.user.client.ui.Widget} that represents a <em>popup</em>. It extends
 * {@link PopupPanel} with additional features such as being resizable. It is an alternative to
 * {@link com.google.gwt.user.client.ui.DialogBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PopupWindow extends PopupPanel implements AttributeWriteResizable, AttributeWriteMovable,
    AttributeWriteClosable, AttributeWriteMaximized, AttributeWriteMaximizable {

  /** The main panel. */
  private final VerticalFlowPanel mainPanel;

  /** The content panel. */
  private final VerticalFlowPanel contentPanel;

  /** The title panel. */
  private final HorizontalFlowPanel titleBar;

  /** The title label. */
  private final InlineLabel title;

  /** The button to close the window. */
  private final Button closeButton;

  /** The button to {@link #setMaximized(boolean) (un)maximize} the window. */
  private final SimpleToggleButton maximizeButton;

  /** @see #getButtonPanel() */
  private final ButtonPanel buttonPanel;

  // /** The footer panel. */
  // private final HorizontalFlowPanel footerBar;

  /** The left border of the window */
  private FlowPanel borderWest;

  /** The right border of the window */
  private FlowPanel borderEast;

  /** The bottom border of the window */
  private final FlowPanel borderSouth;

  /** The bottom left corner of the window */
  private final FlowPanel borderSouthWest;

  /** The bottom right corner of the window */
  private final FlowPanel borderSouthEast;

  /** The top border of the window */
  private final FlowPanel borderNorth;

  /** The top left corner of the window */
  private final FlowPanel borderNorthWest;

  /** The top right corner of the window */
  private final FlowPanel borderNorthEast;

  /** @see #isResizable() */
  private boolean resizable;

  /** @see #isMovable() */
  private boolean movable;

  /** @see #isClosable() */
  private boolean closable;

  /** @see #isMaximizable() */
  private boolean maximizable;

  /** @see #isMaximized() */
  private boolean maximized;

  /**
   * The saved X-{@link #setPopupPosition(int, int) position} to restore after e.g.
   * {@link #setMaximized(boolean)}.
   */
  private int savedX;

  /**
   * The saved Y-{@link #setPopupPosition(int, int) position} to restore after e.g.
   * {@link #setMaximized(boolean)}.
   */
  private int savedY;

  /** The saved {@link #getOffsetWidth() width} to restore after e.g. {@link #setMaximized(boolean)}. */
  private int savedWidth;

  /** The saved {@link #getOffsetHeight() height} to restore after e.g. {@link #setMaximized(boolean)}. */
  private int savedHeight;

  /**
   * The {@link Element} outside the {@link PopupWindow} that had the focus before the {@link PopupWindow} was
   * {@link #show() shown}.
   */
  private Element focusedElement;

  /** @see #getGlassPanel() */
  private Element glassPanel;

  /**
   * The constructor.
   */
  public PopupWindow() {

    this(false);
  }

  /**
   * The constructor.
   * 
   * @param autoHide - see {@link #setAutoHideEnabled(boolean)}.
   */
  public PopupWindow(boolean autoHide) {

    this(autoHide, true);
  }

  /**
   * The constructor.
   * 
   * @param autoHide - see {@link #setAutoHideEnabled(boolean)}.
   * @param modal - see {@link #setModal(boolean)}.
   */
  public PopupWindow(boolean autoHide, boolean modal) {

    super(autoHide, modal);
    if (modal) {
      setGlassStyleName(CssStyles.GLASS_PANEL);
      setGlassEnabled(true);
      getGlassElement().getStyle().setPosition(Position.FIXED);
    }
    this.resizable = true;
    this.maximized = false;
    this.maximizable = true;
    setStyleName(CssStyles.WINDOW);

    this.title = new InlineLabel();
    this.title.setStyleName(CssStyles.WINDOW_TITLE);

    SafeHtml iconMarkup = HtmlTemplates.INSTANCE.iconMarkup(IconConstants.CLOSE);
    this.closeButton = new Button(iconMarkup);
    this.closeButton.setStyleName(CssStyles.BUTTON);
    ClickHandler closeHandler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        hide();
      }
    };
    this.closeButton.addClickHandler(closeHandler);
    iconMarkup = HtmlTemplates.INSTANCE.iconMarkup(IconConstants.MAXIMIZE);
    this.maximizeButton = new SimpleToggleButton();
    this.maximizeButton.setHTML(iconMarkup);
    this.maximizeButton.setStyleName(CssStyles.BUTTON);
    this.maximizeButton.addStyleName(CssStyles.TOGGLE_BUTTON);
    ClickHandler maximizeHandler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        setMaximized(!PopupWindow.this.maximized);
      }
    };
    this.maximizeButton.addClickHandler(maximizeHandler);

    this.titleBar = new HorizontalFlowPanel();
    this.titleBar.addStyleName(CssStyles.WINDOW_TITLE_BAR);
    this.titleBar.addStyleName(CssStyles.MOVABLE);
    this.movable = true;
    this.titleBar.add(this.title);
    this.titleBar.add(this.maximizeButton);
    this.titleBar.add(this.closeButton);

    this.contentPanel = new VerticalFlowPanel();
    this.contentPanel.addStyleName(CssStyles.WINDOW_CONTENT);

    // borders for resizing...
    this.borderWest = new FlowPanel();
    this.borderWest.setStyleName(CssStyles.BORDER_WEST);
    this.borderEast = new FlowPanel();
    this.borderEast.setStyleName(CssStyles.BORDER_EAST);
    this.borderSouth = new FlowPanel();
    this.borderSouth.setStyleName(CssStyles.BORDER_SOUTH);
    this.borderSouthWest = new FlowPanel();
    this.borderSouthWest.setStyleName(CssStyles.BORDER_SOUTH_WEST);
    this.borderSouthEast = new FlowPanel();
    this.borderSouthEast.setStyleName(CssStyles.BORDER_SOUTH_EAST);
    this.borderNorth = new FlowPanel();
    this.borderNorth.setStyleName(CssStyles.BORDER_NORTH);
    this.borderNorthWest = new FlowPanel();
    this.borderNorthWest.setStyleName(CssStyles.BORDER_NORTH_WEST);
    this.borderNorthEast = new FlowPanel();
    this.borderNorthEast.setStyleName(CssStyles.BORDER_NORTH_EAST);

    this.mainPanel = new VerticalFlowPanel();
    this.mainPanel.add(this.titleBar);
    this.mainPanel.add(this.contentPanel);
    this.buttonPanel = new ButtonPanel();
    this.mainPanel.add(this.buttonPanel);
    // this.footerBar = new HorizontalFlowPanel();
    // this.footerBar.addStyleName(CssStyles.WINDOW_FOOTER_BAR);
    // this.mainPanel.add(this.footerBar);
    this.mainPanel.add(this.borderWest);
    this.mainPanel.add(this.borderEast);
    this.mainPanel.add(this.borderSouth);
    this.mainPanel.add(this.borderSouthWest);
    this.mainPanel.add(this.borderSouthEast);
    this.mainPanel.add(this.borderNorth);
    this.mainPanel.add(this.borderNorthWest);
    this.mainPanel.add(this.borderNorthEast);
    super.add(this.mainPanel);

    // setStyleName(getContainerElement(), "popupContent");

    addMouseHandler(this.title, null);
    addMouseHandler(this.borderEast, Direction.EAST);
    addMouseHandler(this.borderSouth, Direction.SOUTH);
    addMouseHandler(this.borderSouthEast, Direction.SOUTH_EAST);
    addMouseHandler(this.borderSouthWest, Direction.SOUTH_WEST);
    addMouseHandler(this.borderWest, Direction.WEST);
    addMouseHandler(this.borderNorth, Direction.NORTH);
    addMouseHandler(this.borderNorthEast, Direction.NORTH_EAST);
    addMouseHandler(this.borderNorthWest, Direction.NORTH_WEST);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    return this.resizable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    if (this.resizable == resizable) {
      return;
    }
    if (!this.maximized) {
      doSetResizable(resizable);
    }
    this.resizable = resizable;
  }

  /**
   * @param newResizable - see {@link #setResizable(boolean)}.
   */
  private void doSetResizable(boolean newResizable) {

    this.borderEast.setVisible(newResizable);
    this.borderNorth.setVisible(newResizable);
    this.borderNorthEast.setVisible(newResizable);
    this.borderNorthWest.setVisible(newResizable);
    this.borderSouth.setVisible(newResizable);
    this.borderSouthEast.setVisible(newResizable);
    this.borderSouthWest.setVisible(newResizable);
    this.borderWest.setVisible(newResizable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {

    return this.movable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMovable(boolean movable) {

    if (this.movable == movable) {
      return;
    }
    if (movable) {
      if (!this.maximized) {
        this.titleBar.addStyleName(CssStyles.MOVABLE);
      }
    } else {
      this.titleBar.removeStyleName(CssStyles.MOVABLE);
    }
    this.movable = movable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosable() {

    return this.closable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClosable(boolean closable) {

    if (this.closable == closable) {
      return;
    }
    this.closable = closable;
    this.closeButton.setVisible(closable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximized() {

    return this.maximized;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximized(boolean maximized) {

    if (this.maximized == maximized) {
      return;
    }
    if (maximized) {
      this.savedX = getAbsoluteLeft();
      this.savedY = getAbsoluteTop();
      this.savedWidth = getOffsetWidth();
      this.savedHeight = getOffsetHeight();
      setPopupPosition(Window.getScrollLeft(), Window.getScrollTop());
      setPixelSize(Window.getClientWidth(), Window.getClientHeight());
      if (this.movable) {
        this.titleBar.removeStyleName(CssStyles.MOVABLE);
      }
      if (this.resizable) {
        doSetResizable(false);
      }
    } else {
      setPopupPosition(this.savedX, this.savedY);
      setPixelSize(this.savedWidth, this.savedHeight);
      if (this.movable) {
        this.titleBar.addStyleName(CssStyles.MOVABLE);
      }
      if (this.resizable) {
        doSetResizable(true);
      }
    }
    this.maximized = maximized;
    this.maximizeButton.setValue(Boolean.valueOf(maximized), false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximizable() {

    return this.maximizable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximizable(boolean maximizable) {

    if (this.maximizable == maximizable) {
      return;
    }
    this.maximizeButton.setVisible(maximizable);
    this.maximizable = maximizable;
  }

  /**
   * Adds a {@link PopupMouseHandler} to the given {@link Widget} based on the given {@link Direction}.
   * 
   * @param widget is the {@link Widget} where to add the {@link PopupMouseHandler} to.
   * @param resizeDirection is the resize {@link Direction} or <code>null</code> for move.
   */
  private void addMouseHandler(Widget widget, Direction resizeDirection) {

    PopupMouseHandler handler = new PopupMouseHandler(this, resizeDirection);
    widget.sinkEvents(Event.ONMOUSEDOWN);
    widget.addDomHandler(handler, MouseDownEvent.getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(Widget w) {

    this.contentPanel.add(w);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(IsWidget child) {

    this.contentPanel.add(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(IsWidget child) {

    return this.contentPanel.remove(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(Widget w) {

    return this.contentPanel.remove(w);
  }

  /**
   * @param titleText is the text to be displayed in the title bar of the popup window.
   */
  public void setTitleText(String titleText) {

    this.title.setText(titleText);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onPreviewNativeEvent(NativePreviewEvent event) {

    int eventType = event.getTypeInt();
    if (eventType == Event.ONKEYPRESS) {
      NativeEvent nativeEvent = event.getNativeEvent();
      int keyCode = nativeEvent.getKeyCode();
      if ((keyCode == KeyCodes.KEY_ESCAPE) && (this.closable)) {
        hide();
      }
      if (keyCode == KeyCodes.KEY_TAB) {
        if (nativeEvent.getShiftKey()) {
          EventTarget target = nativeEvent.getEventTarget();
          if (Element.is(target)) {
            Element targetElement = Element.as(target);
            if ((targetElement == getFirstFocusElement()) || !getElement().isOrHasChild(targetElement)) {
              event.cancel();
              getLastFocusElement().focus();
            }
          }
        } else {
          EventTarget target = nativeEvent.getEventTarget();
          if (Element.is(target)) {
            Element targetElement = Element.as(target);
            if ((targetElement == getLastFocusElement()) || !getElement().isOrHasChild(targetElement)) {
              event.cancel();
              getFirstFocusElement().focus();
            }
          }
        }
      }
    }
  }

  /**
   * @return the first {@link Element} of this popup that is tab-able to get the focus.
   */
  private Element getFirstFocusElement() {

    return JavaScriptUtil.getInstance().getFocusable(getElement(), true, false);
  }

  /**
   * @return the last {@link Element} of this popup that is tab-able to get the focus.
   */
  private Element getLastFocusElement() {

    return JavaScriptUtil.getInstance().getFocusable(getElement(), true, true);
  }

  /**
   * @return the main panel of this {@link PopupWindow}. This panel contains the borders, the title-bar and
   *         the {@link #getContentPanel() content-panel}.
   */
  VerticalFlowPanel getMainPanel() {

    return this.mainPanel;
  }

  /**
   * @return the {@link ButtonPanel} located at the bottom of the {@link PopupWindow} where buttons shall be
   *         added.
   */
  public ButtonPanel getButtonPanel() {

    return this.buttonPanel;
  }

  /**
   * @return the content panel where the content of the popup window should be added to.
   */
  public VerticalFlowPanel getContentPanel() {

    return this.contentPanel;
  }

  /**
   * @return the glassPanel
   */
  protected Element getGlassPanel() {

    if (this.glassPanel == null) {
      this.glassPanel = Document.get().createDivElement();
      this.glassPanel.setClassName(CssStyles.GLASS_PANEL);
    }
    return this.glassPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void show() {

    if (isShowing()) {
      return;
    }
    super.show();
    // if (isModal()) {
    // Document.get().getBody().appendChild(getGlassPanel());
    // }

    this.focusedElement = JavaScriptUtil.getInstance().getFocusedElement();
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      @Override
      public void execute() {

        Element focusable = JavaScriptUtil.getInstance().getFocusable(PopupWindow.this.contentPanel.getElement(), true,
            false);
        if (focusable == null) {
          focusable = getFirstFocusElement();
        }
        if (focusable != null) {
          focusable.focus();
        }
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hide() {

    if (!isShowing()) {
      return;
    }
    super.hide();
    // if (isModal()) {
    // Document.get().getBody().removeChild(getGlassPanel());
    // }
    if (this.focusedElement != null) {
      this.focusedElement.focus();
      this.focusedElement = null;
    }
  }

}
