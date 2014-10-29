/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.feature.UiFeatureValueAndValidation;

/**
 * This is the interface for a {@link UiWidgetRegular regular widget} that has a {@link #getValue() value}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetWithValue<VALUE> extends UiWidgetAbstractWithValue<VALUE>, UiFeatureValueAndValidation<VALUE>,
    UiWidgetRegular {

  // nothing to add...

  /**
   * {@inheritDoc}
   * 
   * <br>
   * <b>IMPORTANT:</b><br>
   * To prevent problems with undesired modifications of values there is a general <em>copy on get</em>
   * strategy. This way you can {@link #setValue(Object) set values} and allow them to be modified in the UI
   * without side-effects on the original value. This is especially important for composite custom widgets
   * (e.g. {@link net.sf.mmm.client.ui.base.widget.custom.pattern.UiWidgetCustomEditor editors}) to allow the
   * user to modify business entities and modifications can still be cancelled and changed will be reverted.
   * All objects that are (potentially) modified by the widgets will be copied. Therefore it is even safe to
   * modify the result of this method in most cases. However,
   * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree tree widgets} build an excuse and use
   * a <em>copy on set/retrieve</em>
   * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeModelMutable strategy}.
   */
  @Override
  VALUE getValue();

}
