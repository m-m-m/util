/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.handler.event;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick;
import net.sf.mmm.ui.toolkit.base.handler.event.ClickEventSender;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * This is the implementation of {@link ClickEventSender} for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ClickEventSenderGwt extends ClickEventSender implements ClickHandler {

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   */
  public ClickEventSenderGwt(UiFeatureClick source) {

    super(source);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    onClick(null, false);
  }

}
