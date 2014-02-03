/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * This is the interface for {@link SafeHtmlTemplates} used for injection/XSS safe HTML markup.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface HtmlTemplates extends SafeHtmlTemplates {

  /** The singleton instance. */
  HtmlTemplates INSTANCE = GWT.create(HtmlTemplates.class);

  /**
   * @param iconName is the name of the icon.
   * @return the HTML markup to render the icon.
   */
  @Template("<span class=\"{0}\"></span>")
  SafeHtml iconMarkup(String iconName);

}
