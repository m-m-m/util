/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.dialog;

/**
 * This is the interface for a dialog of the client application. It displays itself in the page of the main
 * application window. It is a singleton instance that is addressed via a {@link DialogPlace}. A
 * {@link Dialog} should NOT be mixed with a {@link PopupDialog}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Dialog extends AbstractDialog {

  /**
   * This method determines the type of this {@link Dialog}. There are predefined <code>TYPE_*</code>
   * constants for common types in {@link DialogConstants}. However, you can simply create additional custom
   * types for your needs.
   * 
   * @see DialogConstants#TYPE_MAIN
   * @see DialogConstants#TYPE_HEADER
   * @see DialogConstants#TYPE_FOOTER
   * @see DialogConstants#TYPE_NAVIGATION
   * @see DialogConstants#TYPE_SIDE
   * @see DialogConstants#TYPE_ROOT
   * 
   * @return the type of this {@link Dialog}.
   */
  String getType();

}
