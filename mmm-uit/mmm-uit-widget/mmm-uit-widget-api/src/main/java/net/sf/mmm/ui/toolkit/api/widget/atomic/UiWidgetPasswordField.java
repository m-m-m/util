/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

/**
 * This is the interface for a {@link UiWidgetInputField input field widget} representing a password field.
 * Such field is like {@link UiWidgetTextField} but allows to enter secret data in a way the the value entered
 * is not visible on the screen (e.g. by echoing a standard symbol like * instead of the actual character that
 * was typed). The field also prevents to get the value via copy and paste.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetPasswordField extends UiWidgetInputField<String> {

  // nothing to add...

}
