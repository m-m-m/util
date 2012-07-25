/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

/**
 * This class holds a collection of utility functions for GWT (Google Web Toolkit) using JSNI (JavaScript
 * Native Interface).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JavaScriptUtil {

  /** @see #getInstance() */
  private static JavaScriptUtil instance = new JavaScriptUtil();

  /**
   * The constructor.
   */
  public JavaScriptUtil() {

    super();
  }

  /**
   * @return the iNSTANCE
   */
  public static JavaScriptUtil getInstance() {

    return instance;
  }

  /**
   * This method allows to replace the default implementation returned by {@link #getInstance()}.
   * 
   * @param customInstance is the new {@link JavaScriptUtil} instance.
   */
  protected static void setInstance(JavaScriptUtil customInstance) {

    instance = customInstance;
  }

  //formatter:off

  /**
   * This method gets the width of the current screen/display also called the horizontal resolution.
   *
   * @return the screen width in pixels.
   */
  public native int getScreenWidth() /*-{
    return $wnd.screen.width;
  }-*/;

  /**
   * This method gets the height of the current screen/display also called the vertical resolution.
   *
   * @return the screen height in pixels.
   */
  public native int getScreenHeight() /*-{
    return $wnd.screen.height;
  }-*/;

  /**
   * This method gets the pixel position of the browser window's left border on the x-axis (horizontal).
   *
   * @return the browsers X position.
   */
  public native int getBrowserPositionX() /*-{
    if ($wnd.screenLeft) {
      return $wnd.screenLeft;
    } else if ($wnd.screenX) {
      return $wnd.screenX;
    }
    return 0;
  }-*/;


  /**
   * This method gets the pixel position of the browser window's top border on the y-axis (vertical).
   *
   * @return the browsers Y position.
   */
  public native int getBrowserPositionY() /*-{
    if ($wnd.screenTop) {
      return $wnd.screenTop;
    } else if ($wnd.screenY) {
      return $wnd.screenY;
    }
    return 0;
  }-*/;

  /**
   * This method gets the width of the browser window in pixel (horizontal size).
   *
   * @return the browsers width.
   */
  public native int getBrowserWidth() /*-{
    if ($wnd.outerwidth) {
      return $wnd.outerwidth;
    } else if ($doc.documentElement.offsetWidth) {
      return $doc.documentElement.offsetWidth + 18;
    }
  }-*/;

  /**
   * This method gets the height of the browser window in pixel (vertical size).
   *
   * @return the browsers height.
   */
  public native int getBrowserHeight() /*-{
    if ($wnd.outerheight) {
      return $wnd.outerheight;
    } else if ($doc.documentElement.offsetHeight) {
      return $doc.documentElement.offsetHeight + 110;
    }
    return 0;
  }-*/;

  //formatter:on

}
