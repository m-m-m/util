/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api;

import java.util.Collection;

/**
 * This is the interface for the service that provides the UI toolkit. <br>
 * Please take notice that it is illegal to cast any interface of the UI toolkit
 * to an implementing class. The only casts allowed are described in the
 * {@link net.sf.mmm.ui.toolkit.api.UiObject UIObject} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIServiceRenamed {

  /**
   * This method gets the UI factory for the default display. Depending on the
   * implementation this may always return the same instance (native client) or
   * an individual instance per request (server side web client).
   * 
   * @return the default factory.
   */
  UIFactoryRenamed getFactory();

  /**
   * This method gets all available display objects.
   * 
   * @return the list of all available display objects.
   */
  Collection<UIDisplay> getDisplays();

}
