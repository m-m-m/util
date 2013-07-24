/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventCollapse;
import net.sf.mmm.client.ui.api.event.UiEventExpand;

/**
 * This is the {@link UiHandlerEvent} for the actions {@link #onCollapse(UiEventCollapse)} and
 * {@link #onCollapse(UiEventCollapse)}. <br/>
 * <b>ATTENTION:</b><br/>
 * Implementations of this method should only
 * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteVisible#setVisible(boolean) change the visibility}
 * of objects. Do not misuse this feature for totally different things as this will cause confusion and bad
 * usability.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// TODO hohwille We need Java8 support for GWT!
// public interface UiHandlerEventCollapse extends UiHandlerEvent {
public abstract class UiHandlerEventCollapse implements UiHandlerEvent {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onEvent(UiEvent event) {

    if (event.getType() == EventType.COLLAPSE) {
      onCollapse((UiEventCollapse) event);
    } else if (event.getType() == EventType.EXPAND) {
      onExpand((UiEventExpand) event);
    }
  }

  /**
   * This method is invoked if an {@link net.sf.mmm.client.ui.api.feature.UiFeatureCollapse collapsable
   * object} is to be {@link net.sf.mmm.client.ui.api.feature.UiFeatureCollapse#setCollapsed(boolean)
   * collapsed}.
   * 
   * @param event is the {@link UiEventCollapse collapse event}.
   */
  public abstract void onCollapse(UiEventCollapse event);

  /**
   * This method is invoked if an {@link net.sf.mmm.client.ui.api.feature.UiFeatureCollapse collapsable
   * object} is to be {@link net.sf.mmm.client.ui.api.feature.UiFeatureCollapse#setCollapsed(boolean)
   * expanded}.
   * 
   * @param event is the {@link UiEventExpand expand event}.
   */
  public abstract void onExpand(UiEventExpand event);

  // /**
  // * This method is invoked if an {@link UiFeatureCollapse collapsable object} is to be
  // * {@link UiFeatureCollapse#setCollapsed(boolean) collapsed or expanded}.<br/>
  // * <b>ATTENTION:</b><br/>
  // * Implementations of this method should only
  // * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteVisible#setVisible(boolean) change the
  // * visibility} of objects. Do not misuse this feature for totally different things as this will cause
  // * confusion and bad usability.
  // *
  // * @param source is the object that triggered the collapse or expand.
  // * @param collapse - <code>true</code> to collapse, <code>false</code> to expand.
  // * @param programmatic - <code>true</code> if the
  // * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteCollapsed#setCollapsed(boolean) change was
  // * triggered by the program}, <code>false</code> if performed by the end-user.
  // */
  // public abstract void onCollapseOrExpand(UiFeatureCollapse source, boolean collapse, boolean
  // programmatic);

}
