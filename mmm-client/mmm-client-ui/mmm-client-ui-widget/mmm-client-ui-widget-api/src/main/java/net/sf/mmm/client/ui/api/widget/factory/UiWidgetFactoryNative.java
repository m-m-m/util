/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.factory;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a generic factory used to {@link #create(Class) create} {@link UiWidget}s.
 * 
 * @see UiWidgetFactory
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface UiWidgetFactoryNative extends AbstractUiWidgetFactoryGeneric {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.client.ui.api.widget.UiWidgetFactoryNative";

}
