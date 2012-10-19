/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getType() type} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadType {

  /**
   * This method returns the type of the object. This must be a TYPE constant defined in a sub-interface of
   * object. It is legal to use the == operator to compare the result with the TYPE constant of a
   * sub-interface. Further it is legal to cast this instance to a sub-interface if the result of this method
   * is the same as the TYPE constant of that sub-interface. <br>
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

}
