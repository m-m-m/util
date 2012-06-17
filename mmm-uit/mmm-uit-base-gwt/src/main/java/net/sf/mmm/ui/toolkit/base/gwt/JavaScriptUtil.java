/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.gwt;

/**
 * This class contains all JSNI methods for the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class JavaScriptUtil {

  /**
   * Construction forbidden.
   */
  private JavaScriptUtil() {

    super();
  }
  
  /**
   * This method sets the size of the browser window.
   * 
   * @param width is the width in pixels.
   * @param height is the height in pixels.
   */
  public static native void setSizeOfBrowserWindow(int width, int height) /*-{
    $wnd.resizeTo(width, height);
  }-*/;

  /**
   * This method gets the width of the browser window (outerWidth).
   * 
   * @return the width.
   */
  public static native int getWidthOfBrowserWindow() /*-{
    return $wnd.outerWidth;
  }-*/;

  /**
   * This method gets the height of the browser window (outerHeight).
   * 
   * @return the height.
   */
  public static native int getHeightOfBrowserWindow() /*-{
    return $wnd.outerHeight;
  }-*/;

  /**
   * This method gets the width of the browser window (innerWidth).
   * 
   * @return the width.
   */
  public static native int getWidthOfBrowserViewport() /*-{
    return $wnd.innerWidth;
  }-*/;

  /**
   * This method gets the height of the browsers viewport (innerHeight).
   * 
   * @return the height.
   */
  public static native int getHeightOfBrowserViewport() /*-{
    return $wnd.innerHeight;
  }-*/;

  /**
   * This method gets the horizontal scroll position of the browsers viewport 
   * (pageXOffset).
   * 
   * @return the horizontal scroll position.
   */
  public static native int getXOfBrowserScrollposition() /*-{
    return $wnd.pageXOffset;
  }-*/;
  
  /**
   * This method gets the vertical scroll position of the browsers viewport 
   * (pageYOffset).
   * 
   * @return the vertical scroll position.
   */
  public static native int getYOfBrowserScrollposition() /*-{
    return $wnd.pageYOffset;
  }-*/;

  /**
   * This method gets the X (horizontal) position of the browser window 
   * (screenX).
   * 
   * @return the horizontal scroll position.
   */
  public static native int getXOfBrowserWindow() /*-{
    return $wnd.screenX;
  }-*/;
  
  /**
   * This method gets the Y (vertical) position of the browser window 
   * (screenY).
   * 
   * @return the vertical scroll position.
   */
  public static native int getYOfBrowserWindow() /*-{
    return $wnd.screenY;
  }-*/;

  /**
   * This method gets the width of the screen (screen.width).
   * 
   * @return the screen width.
   */
  public static native int getWidthOfScreen() /*-{
    return screen.width;
  }-*/;

  /**
   * This method gets the height of the screen (screen.height).
   * 
   * @return the screen height.
   */
  public static native int getHeightOfScreen() /*-{
    return screen.height;
  }-*/;
  
}
