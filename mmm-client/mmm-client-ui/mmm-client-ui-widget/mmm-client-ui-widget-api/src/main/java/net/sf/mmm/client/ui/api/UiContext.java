/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api;

import net.sf.mmm.client.ui.api.widget.UiConfiguration;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactoryDatatype;
import net.sf.mmm.client.ui.base.aria.role.RoleFactory;
import net.sf.mmm.util.component.api.ComponentContainer;

/**
 * This is the interface for the context of the UI. In various places you can use CDI (Context and Dependency
 * Injection) to get access to arbitrary components. Even though this is still a recommended pattern also for
 * clients there are some limitations (lack of features on platforms like web). Further, clients have smaller
 * components created with <code>new</code> for simplicity. Injecting {@link javax.inject.Provider}s for this
 * would make the programming much more complex. In such cases you need to pass required components manually.
 * This context is a container to make this easy and maintainable.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiContext {

  /**
   * This method gets the {@link UiDisplay} e.g. to read the current screen resolution.
   * 
   * @return the {@link UiDisplay}.
   */
  UiDisplay getDisplay();

  /**
   * @return the {@link UiDispatcher}.
   */
  UiDispatcher getDispatcher();

  /**
   * @return the {@link UiWidgetFactory}.
   */
  UiWidgetFactory<?> getWidgetFactory();

  /**
   * @return the instance of {@link ComponentContainer}.
   */
  ComponentContainer getContainer();

  /**
   * @return the {@link UiWidgetFactoryDatatype}.
   */
  UiWidgetFactoryDatatype getWidgetFactoryDatatype();

  /**
   * This method gets the {@link UiConfiguration} for this factory. It is intended to be read by
   * {@link net.sf.mmm.client.ui.api.widget.UiWidget} implementations to configure their look and feel.
   * 
   * @return the {@link UiConfiguration}.
   */
  UiConfiguration getConfiguration();

  /**
   * @return the instance of {@link RoleFactory}.
   */
  RoleFactory getRoleFactory();

}
