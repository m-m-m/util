/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteCollapsed;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetAbstractButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.util.nls.api.NlsAccess;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * A {@link CollapseExpandButton} is a {@link Button} used to {@link #setCollapsed(boolean) collapse and
 * expand} a section that is typically located below it.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CollapseExpandButton extends Button implements AttributeWriteCollapsed {

  /** The {@link CollapseHandler} given at construction time. */
  private final CollapseHandler collapseHandler;

  /** @see #isCollapsed() */
  private boolean collapsed;

  /**
   * The constructor.
   * 
   * @param collapseHandler is the {@link CollapseHandler} notified about collapsing and expanding.
   */
  public CollapseExpandButton(CollapseHandler collapseHandler) {

    // visual appearance is configured via CSS, only logical tagging via styles here...
    super("<span></span>");
    this.collapseHandler = collapseHandler;
    setStylePrimaryName(UiWidgetAbstractButton.STYLE_BUTTON);
    addStyleName(UiWidgetCollapsableSection.STYLE_COLLAPSE_BUTTON);
    setTitle(getBundle().tooltipCollapse().getLocalizedMessage());
    ClickHandler handler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        setCollapsed(!CollapseExpandButton.this.collapsed, false);
      }
    };
    addClickHandler(handler);
    this.collapsed = false;
  }

  /**
   * @return the instance of {@link NlsBundleClientUiRoot}.
   */
  private NlsBundleClientUiRoot getBundle() {

    return NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCollapsed(boolean collapsed) {

    setCollapsed(collapsed, true);
  }

  /**
   * @param newCollapsed - see {@link #setCollapsed(boolean)}.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   */
  public void setCollapsed(boolean newCollapsed, boolean programmatic) {

    if (newCollapsed == this.collapsed) {
      return;
    }
    if (newCollapsed) {
      addStyleName(UiWidgetCollapsableSection.STYLE_COLLAPSED);
      setTitle(getBundle().tooltipExpand().getLocalizedMessage());
    } else {
      removeStyleName(UiWidgetCollapsableSection.STYLE_COLLAPSED);
      setTitle(getBundle().tooltipCollapse().getLocalizedMessage());
    }
    this.collapsed = newCollapsed;
    this.collapseHandler.onCollapse(newCollapsed, programmatic);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCollapsed() {

    return this.collapsed;
  }

  /**
   * Interface for a handler that gets notified about {@link #onCollapse(boolean, boolean) collapsing and
   * expanding}.
   */
  public interface CollapseHandler {

    /**
     * This method is called to notify about collapsing and expanding. It has to implement the logic that
     * actually shows and hides things accordingly.
     * 
     * @param collapse - <code>true</code> if switched to collapsed state, <code>false</code> if switched to
     *        expanded state.
     * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
     */
    void onCollapse(boolean collapse, boolean programmatic);
  }

}
