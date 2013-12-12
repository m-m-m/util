/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;


/**
 * This interface gives advanced read and write access to the {@link #isVisible() visibility} of an object.
 * 
 * @see #setVisible(boolean)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteVisibleAdvanced extends AttributeWriteVisible, AttributeReadVisibleRecursive {

  /**
   * This method gets advanced visibility support. It allows you to read and write the visibility for
   * different cross-cutting concerns.<br/>
   * <b>Motivation:</b><br/>
   * Typical UI toolkits provide visibility as a primitive <code>boolean</code> attribute or property.
   * However, this is leading to various problems if you have multiple aspects that influence the flag. E.g.
   * assume you have some dynamic effect that is showing or hiding a widget. Additionally the widget is in a
   * collapsable section, tab-panel, or the like that is also showing or hiding the widget. Further, there
   * might be an authorization in your application as a cross-cutting concern that hides widgets from the UI
   * according to the current roles and permissions of the user. If this is all based on a simple boolean flag
   * you will end up in a mess and with a buggy application. So the dynamic effect or even worse the
   * authorization will hide the widget but after the user collapses and then expands a panel it becomes
   * visible again. You do not want to deal with bugs like this.<br/>
   * <b>ATTENTION:</b><br/>
   * The returned instance may be lazily created. It will not be expensive but for a large UI this will sum
   * up. In a web or especially a mobile client memory might be low. So if you are recursively traversing the
   * UI widget tree you should only rely on {@link #isVisible()} for reading visibility.
   * 
   * @return the {@link AttributeWriteFlagAdvanced mutable flag} for advanced visibility support.
   */
  AttributeWriteFlagAdvanced getVisibleFlag();

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * This method is an efficient shortcut for
   * <code>{@link #getVisibleFlag()}.{@link AttributeReadFlagAdvanced#getFlag() getFlag()}</code>. If you want
   * to read the value that has been set via {@link #setVisible(boolean)} you would need to do
   * <code>{@link #getVisibleFlag()}.{@link AttributeReadFlagAdvanced#getFlag(net.sf.mmm.client.ui.api.common.FlagModifier) getFlag(null)}</code>
   * instead. See {@link #getVisibleFlag()} for more information.
   */
  @Override
  boolean isVisible();

  /**
   * This method shows or hides this object.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is NOT symmetric to {@link #isVisible()}. It is an efficient shortcut for
   * <code>{@link #getVisibleFlag()}.{@link AttributeWriteFlagAdvanced#setFlag(boolean, net.sf.mmm.client.ui.api.common.FlagModifier)
   * setFlag(visible, null)}</code>.
   * 
   * @see #isVisible()
   * 
   * @param visible is the new visibility status of this object. If <code>true</code>, the object will be
   *        shown (and raised), if false the object will be hidden (iconified).
   */
  @Override
  void setVisible(boolean visible);

  /**
   * This method shows or hides this object.
   * 
   * @see #isVisible()
   * 
   * @param visible is the new visibility status of this object. If <code>true</code>, the object will be
   *        shown (and raised), if false the object will be hidden (iconified).
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   */
  void setVisible(boolean visible, boolean programmatic);

}
