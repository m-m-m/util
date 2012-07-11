/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.handler.event;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;

/**
 * This is the implementation of {@link AbstractEventSender} for {@link UiHandlerEventValueChange}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed {@link UiFeatureValue#getValue() value}.
 */
public class ChangeEventSender<VALUE> extends AbstractEventSender<UiHandlerEventValueChange<VALUE>, UiFeatureValue<VALUE>>
    implements UiHandlerEventValueChange<VALUE> {

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ChangeEventSender(UiFeatureValue<VALUE> source) {

    super((Class) UiHandlerEventValueChange.class, source);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onValueChange(UiFeatureValue<VALUE> source, boolean programmatic) {

    before();
    for (UiHandlerEventValueChange<VALUE> handler : getHandlers()) {
      handler.onValueChange(source, programmatic);
    }
    after();
  }

}
