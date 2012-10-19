/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getHelpText() help text} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadHelpText {

  /**
   * This method gets the <em>help text</em> of this object. This text is used to provide dynamic online-help
   * information about this object. This is an optional attribute. If no help text is provided, it falls back
   * to the tooltip.
   * 
   * @return the help text or <code>null</code> if NOT set.
   */
  String getHelpText();

}
