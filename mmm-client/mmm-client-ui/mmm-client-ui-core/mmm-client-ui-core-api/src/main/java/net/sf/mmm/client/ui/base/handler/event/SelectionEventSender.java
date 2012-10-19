/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.handler.event;

import net.sf.mmm.client.ui.api.attribute.AttributeReadHandlerObserver;
import net.sf.mmm.client.ui.api.attribute.AttributeReadSelectedValue;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;

/**
 * This is the implementation of {@link AbstractEventSender} for {@link UiHandlerEventSelection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link UiFeatureSelectedValue#getSelectedValues() selected
 *        values}.
 */
public class SelectionEventSender<VALUE> extends
    AbstractEventSender<UiHandlerEventSelection<VALUE>, UiFeatureSelectedValue<VALUE>> implements
    UiHandlerEventSelection<VALUE> {

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   * @param observerSource is the {@link AttributeReadHandlerObserver provider} of a potential
   *        {@link net.sf.mmm.client.ui.api.handler.UiHandlerObserver}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public SelectionEventSender(UiFeatureSelectedValue<VALUE> source, AttributeReadHandlerObserver observerSource) {

    super((Class) UiHandlerEventSelection.class, source, observerSource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onSelection(AttributeReadSelectedValue<VALUE> source, boolean programmatic) {

    before();
    for (UiHandlerEventSelection<VALUE> handler : getHandlers()) {
      handler.onSelection(source, programmatic);
    }
    after();
  }

}
