/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.api.common.ButtonContainer;

import com.google.gwt.aria.client.Roles;
import com.google.gwt.aria.client.ToolbarRole;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a {@link com.google.gwt.user.client.ui.Widget} representing a <em>toolbar</em>. It renders a horizontal bar
 * that is supposed to contain buttons with icons. You can directly {@link #add(com.google.gwt.user.client.ui.Widget)
 * add} buttons. To put buttons into a {@link ButtonGroup} you may also use {@link #startGroup()} and
 * {@link #endGroup()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Toolbar extends FlowPanel implements ButtonContainer, HasAllFocusHandlers, FocusHandler, KeyUpHandler {

  /** @see #add(Widget) */
  private final List<ButtonBase> buttonList;

  /** @see #add(Widget) */
  private ButtonBase focusButton;

  /** The current {@link ButtonGroup} or <code>null</code>. */
  private ButtonGroup currentButtonGroup;

  /**
   * The constructor.
   */
  public Toolbar() {

    super();
    this.buttonList = new ArrayList<ButtonBase>();
    setStylePrimaryName("Toolbar");
    ToolbarRole toolbarRole = Roles.getToolbarRole();
    Element element = getElement();
    toolbarRole.set(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HandlerRegistration addBlurHandler(BlurHandler handler) {

    return addDomHandler(handler, BlurEvent.getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HandlerRegistration addFocusHandler(FocusHandler handler) {

    return addDomHandler(handler, FocusEvent.getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onFocus(FocusEvent event) {

    ButtonBase button = (ButtonBase) event.getSource();
    updateFocus(button);
  }

  /**
   * Updates the focus to the given button.
   * 
   * @param button is the button to focus.
   */
  private void updateFocus(ButtonBase button) {

    if (this.focusButton != null) {
      this.focusButton.setTabIndex(-1);
    }
    setFocusButton(button);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onKeyUp(KeyUpEvent event) {

    if (event.isLeftArrow()) {
      int size = this.buttonList.size();
      if (size <= 1) {
        return;
      }
      for (int i = size - 1; i >= 0; i--) {
        ButtonBase button = this.buttonList.get(i);
        if (button == this.focusButton) {
          i--;
          if (i < 0) {
            i = size - 1;
          }
          button = this.buttonList.get(i);
          updateFocus(button);
          button.setFocus(true);
          break;
        }
      }
    } else if (event.isRightArrow()) {
      int size = this.buttonList.size();
      if (size <= 1) {
        return;
      }
      for (int i = 0; i < size; i++) {
        ButtonBase button = this.buttonList.get(i);
        if (button == this.focusButton) {
          i++;
          if (i >= size) {
            i = 0;
          }
          button = this.buttonList.get(i);
          updateFocus(button);
          button.setFocus(true);
          break;
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(Widget widget) {

    if (widget instanceof ButtonBase) {
      ButtonBase button = (ButtonBase) widget;
      if (this.focusButton == null) {
        setFocusButton(button);
      } else {
        button.setTabIndex(HtmlConstants.TAB_INDEX_DISABLE);
      }
      button.addFocusHandler(this);
      button.addKeyUpHandler(this);
      this.buttonList.add(button);
    }
    if (this.currentButtonGroup != null) {
      this.currentButtonGroup.add(widget);
    } else {
      super.add(widget);
    }
  }

  /**
   * 
   * @param button is the {@link ButtonBase button} to set a active descendant and receiver of the focus (via tab).
   */
  private void setFocusButton(ButtonBase button) {

    button.setTabIndex(0);
    this.focusButton = button;
    // Element element = button.getElement();
    // Roles.getToolbarRole().setAriaActivedescendantProperty(getElement(), Id.of(element));
  }

  /**
   * {@inheritDoc}
   * 
   * A button-group is realized by {@link ButtonGroup}.
   */
  @Override
  public void startGroup() throws IllegalStateException {

    if (this.currentButtonGroup != null) {
      throw new IllegalStateException();
    }
    this.currentButtonGroup = new ButtonGroup();
    super.add(this.currentButtonGroup);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean endGroup() {

    boolean result = (this.currentButtonGroup != null);
    this.currentButtonGroup = null;
    return result;
  }

}
