/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.common.UiMode;

/**
 * This is the interface used to handle the change of a {@link UiMode}. In case you need to extend
 * {@link UiMode} with custom mode(s) you can also
 * {@link AbstractUiWidgetFactory#setModeChanger(UiModeChanger) replace} the {@link UiModeChangerImpl default
 * implementation} of this interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiModeChanger {

  /**
   * This method is called if the {@link UiMode} of a {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget} is to
   * be changed.
   * 
   * @param widget is the {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget} where to change the {@link UiMode}
   *        to <code>newMode</code>.
   * @param newMode is the new {@link UiMode}.
   */
  void changeMode(AbstractUiWidget<?> widget, UiMode newMode);

}
