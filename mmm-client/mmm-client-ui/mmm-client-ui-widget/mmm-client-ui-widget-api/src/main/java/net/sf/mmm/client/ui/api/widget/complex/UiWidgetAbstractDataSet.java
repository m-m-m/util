/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteEditable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSummary;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteTitleVisible;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;

/**
 * This is the abstract interface for a {@link UiWidgetRegular regular widget} that is used to present complex
 * data sets. There are the following variants of this widget:
 * <ul>
 * <li>{@link UiWidgetAbstractListTable}</li>
 * <li>{@link UiWidgetTree}</li>
 * <li>{@link UiWidgetTreeTable}</li>
 * </ul>
 * The common aspects of a data set widget (one of the above widgets) are:
 * <ul>
 * <li>They contain a (potentially large) number of items.</li>
 * <li>{@link #setSelectionMode(net.sf.mmm.client.ui.api.common.SelectionMode) One or multiple} of these items
 * can be selected {@link #setSelectedValues(java.util.Collection) programmatically} or by the end-user.</li>
 * <li>The items can be edited. By default this happens inline (directly inside the widget) but it also
 * possible to write a custom editor popup.</li>
 * <li>It has a {@link #getSummary() summary} that is not visible but is honored by assistive technology
 * (screen-readers) for {@link net.sf.mmm.util.lang.api.concern.Accessibility}.</li>
 * <li>It has a {@link #getTitle() title} that describes the content of the table briefly. It should always be
 * {@link #setTitle(String) set} for {@link net.sf.mmm.util.lang.api.concern.Accessibility} even if you do not
 * want to {@link #isTitleVisible() show} it.</li>
 * </ul>
 *
 * @param <ITEM> is the generic type of a row in the list.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAbstractDataSet<ITEM> extends UiWidgetRegular, UiFeatureSelectedValue<ITEM>,
    AttributeWriteSummary, AttributeWriteStringTitle, AttributeWriteTitleVisible, AttributeWriteEditable {

  /**
   * {@inheritDoc}
   *
   * The title may also be called caption (technically in HTML).
   */
  @Override
  String getTitle();

  /**
   * {@inheritDoc}
   *
   * @see #setTitleVisible(boolean)
   */
  @Override
  void setTitle(String title);

}
