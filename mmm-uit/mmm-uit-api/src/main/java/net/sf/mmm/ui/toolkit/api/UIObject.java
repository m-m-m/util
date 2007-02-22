/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api;

/**
 * This is the interface for a UI object. Any object other than the UI factory
 * and the UI service should extend/implement this interface. <br>
 * An instance of this interface may be casted to a sub-interface of this API
 * (net.sf.mmm.ui.api.*) according to the result of the
 * {@link UIObject#getType()}method.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIObject {

  /**
   * This method gets the UI factory that created this component.
   * 
   * @return the ui instance.
   */
  UIFactory getFactory();

  /**
   * This method returns the type of the component. This must be a TYPE
   * constant defined in a sub-interface. It is legal to use the == operator
   * to compare the result with the TYPE constant of a sub-interface. Further
   * it is legal to cast this instance to a sub-interface if the result of
   * this method is the same as the TYPE constant of that sub-interface. <br>
   * An example usage of this method may be:
   * 
   * <pre>
   * UIObject uiObject = ...;
   * if (uiObject.getType() == UIButton.TYPE) {
   *   UIButton button = (UIButton) uiObject;
   *   ...
   * }
   * </pre>
   * 
   * @return the type of this component.
   */
  String getType();

  /**
   * This method determines if this component is a window.
   * 
   * @return <code>true</code> if this component is a
   *         {@link net.sf.mmm.ui.toolkit.api.window.UIWindow window},
   *         <code>false</code> otherwise.
   */
  boolean isWindow();

  /**
   * This method determines if this component is a widget.
   * 
   * @return <code>true</code> if this component is a
   *         {@link net.sf.mmm.ui.toolkit.api.widget.UIWidget widget},
   *         <code>false</code> otherwise.
   */
  boolean isWidget();

  /**
   * This method gets the identifier of this object. It is used to associate
   * additional information (e.g. CSS styles) with the object. The identifier
   * should therefore be unique.
   * 
   * @return the name of this object.
   */
  String getId();

  /**
   * This method set the identifier of this object. Use this method to give
   * the object a meanfull identifier after creation.
   * 
   * @see #getId()
   * 
   * @param newId
   *        is the new identifier for the object.
   */
  void setId(String newId);

}
