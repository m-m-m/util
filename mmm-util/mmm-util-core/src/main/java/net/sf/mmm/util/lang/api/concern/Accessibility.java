/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.concern;

/**
 * This interface is used to document the concept and meaning of <em>accessibility</em> (of a user-interface).
 * We provide advanced support especially via {@link #WAI_ARIA} within this project and via this marker
 * interface you can find the related spots. However, descriptive information such as meaningful alt-texts
 * have to be defined by the developers of actual UIs and can not be generated automatically by the framework.
 * Please support people with disabilities by enriching your application with semantical information - we
 * already do our best to make this easy for you. Have a look at the known subinterfaces and implementing
 * classes to get an overview of our supporting code.
 *
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract interface Accessibility {

  /**
   * The Web Acessibility initiative (WAI) has created a standardized framework called <a
   * href="http://www.w3.org/WAI/intro/aria.php">WAI-ARIA</a> that aims to support accessibility for rich
   * Internet applications (ARIA).<br/>
   * The main concept is to add semantical information to UI elements via specialized attributes. The most
   * important of these attributes is the role attribute. While this is already handled inside our framework
   * there are some descriptive informations required that have to be set by the UI developer.
   */
  String WAI_ARIA = "http://www.w3.org/WAI/intro/aria.php";

}
