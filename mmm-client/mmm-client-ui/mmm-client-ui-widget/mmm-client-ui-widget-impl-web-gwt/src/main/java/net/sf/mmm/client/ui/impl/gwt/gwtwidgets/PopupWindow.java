/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import java.awt.Event;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.IconConstants;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
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
public class PopupWindow extends PopupPanel {

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

  /** The center panel. */
  private final HorizontalFlowPanel centerPanel;

  /** The top left border in the titlebar of the window */
  private FlowPanel topLeft;

  /** The top right border in the titlebar of the window */
  private FlowPanel topRight;

  /** The left border of the window */
  private FlowPanel left;

  /** The right border of the window */
  private FlowPanel right;

  /** The center panel. */
  private final HorizontalFlowPanel bottomPanel;

  /** The bottom border of the window */
  private final FlowPanel bottom;

  /** The bottom left corner of the window */
  private final FlowPanel bottomLeft;

  /** The bottom right corner of the window */
  private final FlowPanel bottomRight;

  /** The left offset of the document body. */
  private int clientLeftOffset;

  /** The top offset of the document body. */
  private int clientTopOffset;

  /** The total width of the browser window. */
  private int windowWidth;

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

    this.topLeft = new FlowPanel();
    this.topLeft.setStyleName(CssStyles.WINDOW_LEFT);
    this.topRight = new FlowPanel();
    this.topRight.setStyleName(CssStyles.WINDOW_RIGHT);
    this.titleBar = new HorizontalFlowPanel();
    this.titleBar.addStyleName(CssStyles.WINDOW_TITLE_BAR);
    this.titleBar.add(this.topLeft);
    this.titleBar.add(this.title);
    this.titleBar.add(this.closeButton);
    this.titleBar.add(this.topRight);

    this.contentPanel = new VerticalFlowPanel();
    this.contentPanel.addStyleName(CssStyles.WINDOW_CONTENT);

    this.left = new FlowPanel();
    this.left.setStyleName(CssStyles.WINDOW_LEFT);
    this.right = new FlowPanel();
    this.right.setStyleName(CssStyles.WINDOW_RIGHT);

    this.centerPanel = new HorizontalFlowPanel();
    this.centerPanel.add(this.left);
    this.centerPanel.add(this.contentPanel);
    this.centerPanel.add(this.right);

    this.bottom = new FlowPanel();
    this.bottom.setStyleName(CssStyles.WINDOW_BOTTOM);
    this.bottomLeft = new FlowPanel();
    this.bottomLeft.setStyleName(CssStyles.WINDOW_BOTTOM_LEFT);
    this.bottomRight = new FlowPanel();
    this.bottomRight.setStyleName(CssStyles.WINDOW_BOTTOM_RIGHT);
    this.bottomPanel = new HorizontalFlowPanel();
    this.bottomPanel.add(this.bottomLeft);
    this.bottomPanel.add(this.bottom);
    this.bottomPanel.add(this.bottomRight);

    this.mainPanel = new VerticalFlowPanel();
    this.mainPanel.add(this.titleBar);
    this.mainPanel.add(this.centerPanel);
    this.mainPanel.add(this.bottomPanel);
    super.add(this.mainPanel);

    // setStyleName(getContainerElement(), "popupContent");
    this.clientLeftOffset = Document.get().getBodyOffsetLeft();
    this.clientTopOffset = Document.get().getBodyOffsetTop();

    this.windowWidth = Window.getClientWidth();

    addMouseHandler(this.title, MouseAction.MOVE);
    addMouseHandler(this.topRight, MouseAction.RESIZE_HORIZONTAL);
    addMouseHandler(this.right, MouseAction.RESIZE_HORIZONTAL);
    addMouseHandler(this.bottom, MouseAction.RESIZE_VERTICAL);
    addMouseHandler(this.bottomRight, MouseAction.RESIZE_BOTH);
  }

  /**
   * Adds a {@link MouseHandler} to the given {@link Widget} based on the given {@link MouseAction}.
   * 
   * @param widget is the {@link Widget} where to add the {@link MouseHandler} to.
   * @param action is the {@link MouseAction} to handle.
   */
  private void addMouseHandler(Widget widget, MouseAction action) {

    MouseHandler handler = new MouseHandler(action, widget.getElement());
    widget.sinkEvents(Event.MOUSE_DOWN | Event.MOUSE_UP | Event.MOUSE_MOVE);
    widget.addDomHandler(handler, MouseDownEvent.getType());
    widget.addDomHandler(handler, MouseUpEvent.getType());
    widget.addDomHandler(handler, MouseMoveEvent.getType());
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
   * @return the content panel where the content of the popup window should be added to.
   */
  public VerticalFlowPanel getContentPanel() {

    return this.contentPanel;
  }

  /**
   * This inner class is the handler for mouse events to move an resize the window.
   */
  private class MouseHandler implements MouseDownHandler, MouseUpHandler, MouseMoveHandler {

    /** The current {@link MouseAction} while dragging or <code>null</code>. */
    private final MouseAction mouseAction;

    /** The {@link Element} to capture and where this handler is attached. */
    private final Element element;

    /** <code>true</code> while dragging the mouse, <code>false</code> otherwise. */
    private boolean dragging;

    /** The initial x position while dragging the mouse. */
    private int startX;

    /** The initial y position while dragging the mouse. */
    private int startY;

    private int width;

    private int height;

    /**
     * The constructor.
     * 
     * @param mouseAction is the {@link MouseAction} to handle.
     * @param element is the {@link Element} to capture.
     */
    public MouseHandler(MouseAction mouseAction, Element element) {

      super();
      this.mouseAction = mouseAction;
      this.element = element;
    }

    @Override
    public void onMouseDown(MouseDownEvent event) {

      if (DOM.getCaptureElement() == null) {
        DOM.setCapture(this.element);
        this.startX = event.getX();
        this.startY = event.getY();
        this.width = getOffsetWidth();
        this.height = getOffsetHeight();
        this.dragging = true;
      }
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {

      if (this.dragging) {
        int x = event.getX();
        int y = event.getY();
        int deltaX = x - this.startX;
        int deltaY = y - this.startY;

        if (this.mouseAction == MouseAction.MOVE) {
          int absoluteLeft = getAbsoluteLeft();
          int absoluteTop = getAbsoluteTop();
          int newX = absoluteLeft + deltaX;
          int newY = absoluteTop + deltaY;

          // if the mouse is off the screen to the left, right, or top, don't
          // move the dialog box. This would let users lose dialog boxes, which
          // would be bad for modal popups.
          if (newX < 0 || newX >= PopupWindow.this.windowWidth || newY < 0) {
            return;
          }
          setPopupPosition(newX, newY);
        } else {
          if (this.mouseAction == MouseAction.RESIZE_HORIZONTAL) {
            deltaY = 0;
          } else if (this.mouseAction == MouseAction.RESIZE_VERTICAL) {
            deltaX = 0;
          }
          setPixelSize(this.width + deltaX, this.height + deltaY);
        }
      }
    }

    @Override
    public void onMouseUp(MouseUpEvent event) {

      this.dragging = false;
      DOM.releaseCapture(((Widget) event.getSource()).getElement());
    }
  }

  public enum MouseAction {

    MOVE,

    RESIZE_HORIZONTAL,

    RESIZE_VERTICAL,

    RESIZE_BOTH,
  }

}
