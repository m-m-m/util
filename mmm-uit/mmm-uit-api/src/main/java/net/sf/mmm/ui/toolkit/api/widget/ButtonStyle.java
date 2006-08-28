/* $Id: ButtonStyle.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.widget;

/**
 * This enum contains the available values for the style of a
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIButtonIF button}. Everyting said
 * about "buttons" also applies about
 * {@link net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF items}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum ButtonStyle {

    /**
     * This is the default style. The according button will be stateless and can
     * be selected by the user and automatically gets unselected.
     */
    DEFAULT,

    /**
     * This is the style for a checkbox-button. Such button has a
     * selection-status typically visualized by a box that contains a checkmark
     * or not. If the button is selected, its selection-status will be inverted.
     * The initial selection-status is deselected (NOT selected).
     */
    CHECK,

    /**
     * This is the style for a toggle-button. The behaviour is the same as
     * {@link #CHECK} except for the visualization. A toggle-button is viewed
     * like a {@link #DEFAULT default} button, but if it is selected, its border
     * will appear inverted so the button looks as it is pushed inside the GUI.
     * After the button is selected again, it will be deselected and looks
     * normal again.<br>
     * ATTENTION: A {@link net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF} with
     * this style is unspecified.
     */
    TOGGLE,

    /**
     * This is the style for a radio-button. The according button is
     * automatically grouped with all other buttons of this style in the same
     * {@link net.sf.mmm.ui.toolkit.api.composite.UIPanelIF panel} or
     * {@link net.sf.mmm.ui.toolkit.api.menu.UIMenuIF menu}. Like for
     * {@link #CHECK} and {@link #TOGGLE} buttons of this style have a
     * selection-status. This is typically visualized by a circle or square that
     * is eigther filled or not. The difference in behaviour is that the button
     * gets not deselected if it is selected again but if another button of the
     * same group is selected.
     */
    RADIO;

}
