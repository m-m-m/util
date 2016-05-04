/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * This utility class provides various functions for GWT specific functions. <br>
 * <b>NOTE:</b><br>
 * All functions related to JavaScript (JSNI) can be found in {@link JavaScriptUtil}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GwtUtil {

  private static final GwtUtil INSTANCE = GWT.create(GwtUtil.class);

  /**
   * The constructor.
   */
  protected GwtUtil() {

    super();
  }

  /**
   * @see net.sf.mmm.util.component.api.Cdi#GET_INSTANCE
   *
   * @return the singleton instance of {@link GwtUtil}.
   */
  public static GwtUtil getInstance() {

    return INSTANCE;
  }

  /**
   * Shorthand for {@link #getId(Element) getId(widget.getElement())}.
   *
   * @param widget is the {@link Widget} to get the ID from.
   * @return the (unique) ID of the {@link Widget}. Created if not set.
   */
  public String getId(Widget widget) {

    Element element = widget.getElement();
    return getId(element);
  }

  /**
   * This method gets the ID of the given {@link Element}. If the {@link Element} {@link Element#getId() has no ID} set,
   * this method will {@link DOM#createUniqueId() create a unique ID} and {@link Element#setId(String) assign} it to the
   * given {@link Element}.
   *
   * @param element is the {@link Element} for which the ID is requested.
   * @return the (unique) ID of the {@link Element}. Created if not set.
   */
  public String getId(Element element) {

    String id = element.getId();
    if ((id == null) || (id.length() == 0)) {
      id = DOM.createUniqueId();
      element.setId(id);
    }
    return id;
  }

}
