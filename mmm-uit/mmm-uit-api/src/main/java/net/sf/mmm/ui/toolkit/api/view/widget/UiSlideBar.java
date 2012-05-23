/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadOrientation;

/**
 * This is the interface for a slide-bar. It is an interactive widget used to
 * specify an value by sliding a thumb along a scale.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiSlideBar<E> extends UiListWidget<E>, AttributeReadOrientation {

  /** the type of this object */
  String TYPE = "SlideBar";

}
