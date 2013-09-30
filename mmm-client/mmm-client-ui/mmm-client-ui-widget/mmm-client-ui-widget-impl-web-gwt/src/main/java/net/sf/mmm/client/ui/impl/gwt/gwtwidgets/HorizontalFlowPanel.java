/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.api.common.CssStyles;

import com.google.gwt.user.client.ui.FlowPanel;

/**
 * This is a {@link com.google.gwt.user.client.ui.Panel} with a horizontal layout for its children.<br/>
 * <b>ATTENTION:</b><br/>
 * The actual layout needs to be enforced by a CSS rule for the style {@link CssStyles#HORIZONTAL_PANEL}.
 * Therefore, it is much more lightweight than a {@link com.google.gwt.user.client.ui.HorizontalPanel} that
 * (mis-)uses an HTML table to enforce the layout.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class HorizontalFlowPanel extends FlowPanel {

  /**
   * The constructor.
   */
  public HorizontalFlowPanel() {

    super();
    setStyleName(CssStyles.HORIZONTAL_PANEL);
  }

}
