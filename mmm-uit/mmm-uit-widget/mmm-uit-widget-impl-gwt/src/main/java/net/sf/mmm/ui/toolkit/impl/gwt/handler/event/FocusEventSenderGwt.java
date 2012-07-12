/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.handler.event;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureFocus;
import net.sf.mmm.ui.toolkit.base.handler.event.FocusEventSender;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;

/**
 * This is the implementation of {@link FocusEventSender} for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class FocusEventSenderGwt extends FocusEventSender implements FocusHandler, BlurHandler {

  /** */
  private boolean programmatic;

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   */
  public FocusEventSenderGwt(UiFeatureFocus source) {

    super(source);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onBlur(BlurEvent event) {

    boolean prog = this.programmatic;
    this.programmatic = false;
    onFocusChange(getSource(), prog, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onFocus(FocusEvent event) {

    boolean prog = this.programmatic;
    this.programmatic = false;
    onFocusChange(getSource(), prog, false);
  }

  /**
   * This method sets the <em>programmatic</em> flag. This should be done immediately before
   * {@link com.google.gwt.user.client.ui.Focusable#setFocus(boolean)} is called so it gets possible to
   * distinguish the programmatic change if the event is received.
   */
  public void setProgrammatic() {

    this.programmatic = true;
  }
}
