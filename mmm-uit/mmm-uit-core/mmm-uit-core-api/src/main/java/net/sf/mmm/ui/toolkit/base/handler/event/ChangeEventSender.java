/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.handler.event;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventChange;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This is the implementation of {@link AbstractEventSender} for {@link UiHandlerEventChange}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed {@link AttributeReadValue#getValue() value}.
 */
public class ChangeEventSender<VALUE> extends AbstractEventSender<UiHandlerEventChange<VALUE>, UiFeatureValue<VALUE>>
    implements UiHandlerEventChange<VALUE> {

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ChangeEventSender(UiFeatureValue<VALUE> source) {

    super((Class) UiHandlerEventChange.class, source);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onChange(AttributeReadValue<VALUE> changedObject, boolean programmatic) {

    before();
    for (UiHandlerEventChange<VALUE> handler : getHandlers()) {
      handler.onChange(changedObject, programmatic);
    }
    after();
  }

}
