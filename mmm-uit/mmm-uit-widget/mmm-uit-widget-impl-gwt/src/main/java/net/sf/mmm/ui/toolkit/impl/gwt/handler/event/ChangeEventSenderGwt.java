/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.handler.event;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.base.handler.event.ChangeEventSender;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

/**
 * This is the implementation of {@link ChangeEventSender} for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed {@link AttributeReadValue#getValue() value}.
 */
public class ChangeEventSenderGwt<VALUE> extends ChangeEventSender<VALUE> implements ChangeHandler {

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   */
  public ChangeEventSenderGwt(UiFeatureValue<VALUE> source) {

    super(source);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onChange(ChangeEvent event) {

    onChange(getSource(), false);
  }

}
