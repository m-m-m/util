/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.Event;
import com.google.gwt.xhr.client.XMLHttpRequest;

/**
 * This class holds a collection of utility functions for GWT (Google Web Toolkit) using JSNI (JavaScript
 * Native Interface).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JavaScriptUtil {

  /** @see #getInstance() */
  private static JavaScriptUtil instance = GWT.create(JavaScriptUtil.class);

  /** @see #getFonts() */
  private String[] fonts;

  /**
   * The constructor.
   */
  public JavaScriptUtil() {

    super();
  }

  /**
   * @return the singleton instance of {@link JavaScriptUtil}.
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

  /**
   * @return the {@link List} of fonts available in the browser.
   */
  public List<String> getAvailableFonts() {

    if (this.fonts == null) {
      JsArrayString jsArray = getFonts();
      this.fonts = new String[jsArray.length()];
      for (int i = 0; i < this.fonts.length; i++) {
        this.fonts[i] = jsArray.get(i);
      }
    }
    return Arrays.asList(this.fonts);
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

  /**
   * This method sets the custom validation message of the given input {@link Element}.
   * The Browser needs to support HTML5 for this feature.
   *
   * @param inputElement is the input {@link Element} that has been validated.
   * @param message - the empty string to mark as valid, the validation failure message otherwise.
   * @return <code>true</code> if the browser supports custom validity and it has been set, <code>false</code>
   *         otherwise.
   */
  public native boolean setCustomValidity(Element inputElement, String message) /*-{
    if (inputElement.setCustomValidity) {
      inputElement.setCustomValidity(message);
      return true;
    }
    return false;
  }-*/;

  /**
   * Creates an {@link InputElement} of a custom {@link InputElement#getType() type}. Only used while GWT does NOT
   * directly provide a way to create HTML5 input elements.
   *
   * @param type is the requested {@link InputElement#getType() type} (e.g. "range", "date", etc.).
   * @return the requested {@link InputElement} of the given <code>type</code>.
   */
  public native InputElement createInputElement(String type) /*-{
    var result = $doc.createElement("INPUT");
    result.type = type;
    return result;
  }-*/;

  /**
   * This method sets the {@link InputElement#getType() type} of an {@link InputElement}.
   *
   * @param inputElement is the input {@link InputElement}.
   * @param type is the new {@link InputElement#getType() type} to set.
   */
  private native void setInputElementType(InputElement inputElement, String type) /*-{
    inputElement.type = type;
  }-*/;

  /**
   * This method creates a {@link Object#clone() clone} of the given <code>template</code>.
   *
   * @param template is the object to clone.
   * @return the cloned object.
   */
  public native Object clone(Object template) /*-{
    var clone = {};

    // create unique object hash code
    @com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(clone);

    for (var i in template) {
      if(!(i in clone)) {
          clone[i] = template[i];
      }
    }
    return clone;
  }-*/;

  /**
   * This method creates a {@link Class#newInstance() new instance} of the given <code>template</code>.
   *
   * @param template is the object create a new instance of.
   * @return the new instance.
   */
  public native Object newInstance(Object template) /*-{
    var clone = {};

    // create unique object hash code
    @com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(clone);

    for (var i in template) {
      if(!(i in clone)) {
          clone[i] = template[i];
      }
    }
    return clone;
  }-*/;

  /**
   * This method gets the response of the given {@link XMLHttpRequest} as {@link JavaScriptBlob}.
   *
   * @param xhr is the {@link XMLHttpRequest} that {@link XMLHttpRequest#getStatus() has already been} loaded.
   * @return the response as a {@link JavaScriptBlob}.
   */
  public native JavaScriptBlob getResponseAsBlob(XMLHttpRequest xhr) /*-{
    BlobBuilder = window.MozBlobBuilder || window.WebKitBlobBuilder || window.BlobBuilder;
    var bb = new BlobBuilder();
    bb.append(xhr.response);
    return bb.getBlob();
  }-*/;

  /**
   * This method gets the {@link JavaScriptFileList} for a given {@link Event}. E.g. if files are dragged onto some
   * element in the browser or in case of an {@link Event} (e.g. of {@value Event#ONCHANGE}) of a file input you can
   * receive the {@link JavaScriptFileList} here.
   *
   * @param event is the {@link Event}.
   * @return the {@link JavaScriptFileList} or <code>null</code> if no files are available.
   */
  public native JavaScriptFileList getFileList(Event event) /*-{
    if (event.dataTransfer && event.dataTransfer.files) {
      // drag and drop suppoer
      return event.dataTransfer.files;
    } else if (event.target && event.target.files) {
      // file upload item <input type="file">
      return event.target.files;
    } else {
      return null;
    }
  }-*/;


  /**
   * This method creates the URL pointing to a {@link JavaScriptBlob}. Such URL is starting with the "blob:" scheme and
   * is only valid within the current JS context.
   *
   * @param blob is the {@link JavaScriptBlob}.
   * @return the new instance.
   */
  public native String getBlobUrl(JavaScriptBlob blob) /*-{
    return $wnd.URL.createObjectURL(blob);
  }-*/;

  /**
   * This method opens a given {@link JavaScriptBlob}. The browser will show a regular popup to open the file or save it
   * to the local disc. Depending on its mimetype the browser might already open the file immediately in a new window.
   *
   * @param blob is the {@link JavaScriptBlob}.
   */
  public native void openBlob(JavaScriptBlob blob) /*-{
    window.open($wnd.URL.createObjectURL(blob), '_blank');
  }-*/;

  /**
   * This method gets the {@link JavaScriptSelection} of the browser {@link com.google.gwt.user.client.Window}.
   *
   * @return the corresponding {@link JavaScriptSelection}.
   */
  public native JavaScriptSelection getSelection() /*-{
    if ($wnd.getSelection) {
      return $wnd.getSelection();
    } else if ($wnd.document.getSelection) {
      return $wnd.document.getSelection();
     } else {
      return $wnd.document.selection.createRange().text;
    }
  }-*/;

  /**
   * This method gets the {@link JavaScriptSelection} of the given <code>&lt;iframe&gt;</code> {@link Element}.
   * The given <code>iframe</code> needs to be loaded when calling this method. Use {@link #onLoadFrame(Element, Runnable)}
   * to ensure.
   *
   * @param iframe is the <code>&lt;iframe&gt;</code> {@link Element}.
   * @return the corresponding {@link JavaScriptSelection}.
   */
  public native JavaScriptSelection getSelection(Element iframe) /*-{
    if (iframe.contentWindow) {
      if (iframe.contentWindow.getSelection) {
        return iframe.contentWindow.getSelection();
      } else if (iframe.contentWindow.document.getSelection) {
        return iframe.contentWindow.document.getSelection();
      } else {
        return iframe.contentWindow.document.selection;
      }
    }
    if (window.getSelection) {
      return window.getSelection();
    } else if (document.selection) {
      return document.selection.createRange();
    }
    return null;
  }-*/;

  /**
   * Determines if the given {@link Element} is focusable or tab-able.
   *
   * @param element is the {@link Element} to check.
   * @param tabable - <code>true</code> if the check should only consider tab-able {@link Element}s (so if tabindex is
   *        negative, the result is <code>false</code>), <code>false</code> otherwise.
   * @return <code>true</code> if focusable/tab-able, <code>false</code> otherwise.
   */
  public native boolean isFocusable(Element element, boolean tabable) /*-{
    if (element.style.display == 'none') {
      return false;
    }
    var tabindex = element.getAttribute('tabindex');
    if (tabable && /^-[1-9]\d*$/.test(tabindex)) {
      return false;
    }
    var tag = element.nodeName.toLowerCase();
    if (/input|select|textarea|button|object/.test(tag)) {
      if (element.disabled) {
        return false;
      }
      return true;
    } else if (tag == 'a') {
      return true;
    }
    return false;
  }-*/;

  /**
   * @return the {@link Element} that is currently focused or <code>null</code> if focus is outside the current document.
   */
  public native Element getFocusedElement() /*-{
    return $doc.activeElement;
  }-*/;

  /**
   * Get the first/last focusable or tab-able (child) {@link Element}.
   *
   * @param element is the {@link Element} to scan recursively.
   * @param tabable - <code>true</code> if the check should only consider tab-able {@link Element}s (so if tabindex is
   *        negative, the result is <code>false</code>), <code>false</code> otherwise.
   * @param last - <code>true</code> if the last focusable/tab-able element shall be returned, <code>false</code> for the last focusable/tab-able element.
   * @return the first/last focusable or tab-able {@link Element} or <code>null</code> if none exists.
   */
  public native Element getFocusable(Element element, boolean tabable, boolean last) /*-{

    if (element.style.display == 'none') {
      return null;
    }
    if (this.@net.sf.mmm.util.gwt.api.JavaScriptUtil::isFocusable(Lcom/google/gwt/dom/client/Element;Z)(element, tabable)) {
      return element;
    }
    if (last) {
      var child = element.lastChild;
      while (child) {
        if (child.nodeType == 1) {
          var result = this.@net.sf.mmm.util.gwt.api.JavaScriptUtil::getFocusable(Lcom/google/gwt/dom/client/Element;ZZ)(child, tabable, last);
          if (result) {
            return result;
          }
        }
        child = child.previousSibling;
      }
    } else {
      var child = element.firstChild;
      while (child) {
        if (child.nodeType == 1) {
          var result = this.@net.sf.mmm.util.gwt.api.JavaScriptUtil::getFocusable(Lcom/google/gwt/dom/client/Element;ZZ)(child, tabable, last);
          if (result) {
            return result;
          }
        }
        child = child.nextSibling;
      }
    }
    return null;
  }-*/;

  /**
   * This method gets the {@link Document} of a given <code>&lt;iframe&gt;</code> {@link Element}.
   * The given <code>iframe</code> needs to be loaded when calling this method. Use {@link #onLoadFrame(Element, Runnable)}
   * to ensure.
   *
   * @param iframe is the <code>&lt;iframe&gt;</code> {@link Element}.
   * @return the corresponding {@link Document}.
   */
  public native Document getFrameDocument(Element iframe) /*-{
    if (iframe.contentDocument) {
      return iframe.contentDocument;
    }
    return iframe.contentWindow.document;
  }-*/;

  /**
   * This method {@link Runnable#run() executes} the given <code>callback</code> when the given <code>iframe</code> is
   * loaded.
   *
   * @param iframe is the <code>&lt;iframe&gt;</code> {@link Element}.
   * @param callback is the callback to {@link Runnable#run() execute}.
   */
  public native void onLoadFrame(Element iframe, Runnable callback) /*-{
    var onloadFunction = function(e) {
      callback.@java.lang.Runnable::run()();
    };
    if ((iframe.contentDocument) || (iframe.contentWindow)) {
      onloadFunction();
      return;
    }
    if (iframe.addEventListener) {
      iframe.addEventListener('load', onloadFunction, false);
    } else if (iframe.attachEvent) {
      iframe.attachEvent('onload', onloadFunction);
    } else {
      iframe.onload = onloadFunction;
    }
  }-*/;

  /**
   * @return an array with the sorted names of the fonts installed.
   */
  private native JsArrayString getFonts() /*-{
    var fontSize = "80px ";
    var fontStyles = ['monospace', 'serif'];
    var defaultWidths = [];
    //var fontStyle = "sans-serif";
    var canvas = document.createElement("canvas");
    var context2d = canvas.getContext("2d");
    var text = "abcdefghijkllmnopqrstuvwxyz0123456789";
    for (var styleIndex = 0; styleIndex < fontStyles.length; styleIndex++) {
      context2d.font = fontSize + fontStyles[styleIndex];
      defaultWidths[styleIndex] = context2d.measureText(text).width;
    }

    var fontList = [];
    // fonts shall be in alphabetic order
    var allFontNames = ['Arial', 'Baskerville', 'Calibri', 'Charter', 'Clean', 'Comic Sans MS', 'Copperplate', 'Courier',
         'Fixed', 'Futura', 'Gadget', 'Georgia', 'Helvetica', 'Herculanum', 'Impact', 'Lucida', 'Lucida Console', 'Marlett',
         'Osaka', 'Palatino', 'Papyrus', 'Sand', 'Symbol', 'Tahoma', 'Times', 'Trebuchet', 'Utopia', 'Verdana'];
    for (var fontIndex = 0; fontIndex < allFontNames.length; fontIndex++) {
      var font = allFontNames[fontIndex];
      for (var styleIndex = 0; styleIndex < fontStyles.length; styleIndex++) {
        context2d.font = fontSize + "'" + font + "'," + fontStyles[styleIndex];
        if (context2d.measureText(text).width != defaultWidths[styleIndex]) {
          fontList[fontList.length] = font;
          break;
        }
      }
    }
    delete canvas;
    return fontList;
  }-*/;

  //formatter:on

}
