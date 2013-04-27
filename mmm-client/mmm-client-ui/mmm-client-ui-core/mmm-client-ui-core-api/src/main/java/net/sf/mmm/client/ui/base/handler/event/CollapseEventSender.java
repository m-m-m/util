/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.handler.event;

import net.sf.mmm.client.ui.api.attribute.AttributeReadHandlerObserver;
import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse;

/**
 * This is the implementation of {@link AbstractEventSender} for {@link UiHandlerEventCollapse}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CollapseEventSender extends AbstractEventSender<UiHandlerEventCollapse, UiFeatureCollapse> implements
    UiHandlerEventCollapse {

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   * @param observerSource is the {@link AttributeReadHandlerObserver provider} of a potential
   *        {@link net.sf.mmm.client.ui.api.handler.UiHandlerObserver}.
   */
  public CollapseEventSender(UiFeatureCollapse source, AttributeReadHandlerObserver observerSource) {

    super(UiHandlerEventCollapse.class, source, observerSource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCollapseOrExpand(UiFeatureCollapse source, boolean collapse, boolean programmatic) {

    before();
    for (UiHandlerEventCollapse handler : getHandlers()) {
      handler.onCollapseOrExpand(source, collapse, programmatic);
    }
    after();
  }

}
