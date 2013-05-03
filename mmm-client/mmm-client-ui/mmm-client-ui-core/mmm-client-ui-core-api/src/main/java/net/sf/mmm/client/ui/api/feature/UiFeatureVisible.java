/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.client.ui.api.attribute.AttributeReadVisibleRecursive;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteVisible;

/**
 * This is the interface for the {@link UiFeature} of an object that can be {@link #isVisible() visible} or
 * hidden. Besides a simple flag the visibility here is an advanced feature. You may ignore the advanced
 * features such as {@link #addVisibleFunction(net.sf.mmm.client.ui.api.attribute.AttributeReadVisible)} but
 * in every bigger client application there are additional circumstances like authorization or other states
 * that have influence on the visibility.<br/>
 * <b>ATTENTION:</b><br/>
 * If this object also supports the method
 * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteMode#setMode(net.sf.mmm.client.ui.api.common.UiMode)}
 * , then that method implementation has to trigger {@link #updateVisibilityLocal()} if the
 * {@link net.sf.mmm.client.ui.api.common.UiMode} has actually been changed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @deprecated use {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteVisibleAdvanced} instead.
 */
@Deprecated
public interface UiFeatureVisible extends UiFeatureVisibleFunctions, AttributeWriteVisible,
    AttributeReadVisibleRecursive {

  /**
   * This method updates the {@link #isVisibleAggregated() aggregated visibility}. In advance to
   * {@link #updateVisibilityLocal()} this method is a recursive operation.
   */
  void updateVisibility();

  /**
   * This method updates the visibility according to potential
   * {@link #addVisibleFunction(net.sf.mmm.client.ui.api.attribute.AttributeReadVisible) visible-functions}.
   * It calculates the {@link #isVisibleAggregated() aggregated visibility} and updated the UI accordingly.
   */
  void updateVisibilityLocal();

  /**
   * This method returns the the current aggregated visibility as calculated on the last call of
   * {@link #updateVisibilityLocal()}. This is the actual visibility of this UI node independent of its
   * potential parent (see {@link #isVisibleRecursive()}). It is only <code>true</code> if
   * {@link #isVisible()} of this object AND all visible-functions return <code>true</code>.
   * 
   * @return the aggregated visibility as calculated on the last call of {@link #updateVisibilityLocal()}.
   */
  boolean isVisibleAggregated();

}
