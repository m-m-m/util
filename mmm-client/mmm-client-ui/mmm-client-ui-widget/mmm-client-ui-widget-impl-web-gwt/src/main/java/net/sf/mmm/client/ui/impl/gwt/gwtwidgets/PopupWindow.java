/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteResizable;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.IconConstants;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;
import net.sf.mmm.util.lang.api.Direction;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
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
public class PopupWindow extends PopupPanel implements AttributeWriteResizable {

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

  /**
   * The constructor.
   */
  public PopupWindow() {

    this(false);
  }

  /**
   * The constructor.
   * 
   * @param autoHide - see {@link #setAutoHideEnabled(boolean)}. sddgfds gdsds gdsg dsg dsgds dsg dsg dsg
   *        dsvgdsg dsg dsgds gdsg dsg dsg dsg dsg dsg dsg dsg dsg dsg
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
    this.resizable = true;
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

    this.titleBar = new HorizontalFlowPanel();
    this.titleBar.addStyleName(CssStyles.WINDOW_TITLE_BAR);
    this.titleBar.add(this.title);
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
    this.borderEast.setVisible(resizable);
    this.borderNorth.setVisible(resizable);
    this.borderNorthEast.setVisible(resizable);
    this.borderNorthWest.setVisible(resizable);
    this.borderSouth.setVisible(resizable);
    this.borderSouthEast.setVisible(resizable);
    this.borderSouthWest.setVisible(resizable);
    this.borderWest.setVisible(resizable);
    this.resizable = resizable;
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
      if (keyCode == KeyCodes.KEY_ESCAPE) {
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

    return JavaScriptUtil.getInstance().getFocusable(this.contentPanel.getElement(), true, true);
  }

  /**
   * @return the content panel where the content of the popup window should be added to.
   */
  public VerticalFlowPanel getContentPanel() {

    return this.contentPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void show() {

    super.show();
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      @Override
      public void execute() {

        getFirstFocusElement().focus();
      }
    });
  }

}
