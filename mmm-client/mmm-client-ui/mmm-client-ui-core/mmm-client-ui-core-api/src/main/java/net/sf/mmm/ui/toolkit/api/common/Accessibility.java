/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.common;

/**
 * This interface is used to document the concept and meaning of <em>accessibility</em> of a user-interface.
 * Please support people with disabilities by enriching your application with semantical information like
 * {@link #WAI_ARIA} attributes and meaningful
 * {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeReadAltText alt-texts}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface Accessibility {

  /**
   * The Web Acessibility initiative (WAI) has created a standardized framework called <a
   * href="http://www.w3.org/WAI/intro/aria.php">WAI-ARIA</a> that aims to support accessibility for rich
   * Internet applications (ARIA).<br/>
   * The main concept is to add semantical information to UI elements via specialized attributes. The most
   * important of these attributes is the
   * {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeReadAriaRole#getAriaRole() role} attribute.
   */
  String WAI_ARIA = "http://www.w3.org/WAI/intro/aria.php";

}
