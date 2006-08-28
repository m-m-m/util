/* $Id: UISlideBarIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIReadOrientationIF;

/**
 * This is the interface for a slide-bar. It is an interactive widget used to
 * specify an value by sliding a thumb along a scale.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UISlideBarIF<E> extends UIListWidgetIF<E>, UIReadOrientationIF {

    /** the type of this object */
    String TYPE = "SlideBar";

}
