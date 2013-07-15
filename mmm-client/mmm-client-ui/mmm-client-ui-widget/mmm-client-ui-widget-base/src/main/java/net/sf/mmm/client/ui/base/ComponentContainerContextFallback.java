/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import net.sf.mmm.client.ui.api.UiConfiguration;
import net.sf.mmm.client.ui.api.UiDispatcher;
import net.sf.mmm.client.ui.api.UiDisplay;
import net.sf.mmm.client.ui.api.UiPopupHelper;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.base.aria.role.RoleFactory;
import net.sf.mmm.client.ui.base.binding.UiDataBindingFactory;
import net.sf.mmm.util.component.api.ComponentContainer;
import net.sf.mmm.util.component.api.ResourceAmbiguousException;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderFactory;

/**
 * This is a fake implementation of {@link ComponentContainer} used as fallback for
 * {@link AbstractUiContext#getContainer()} if no real {@link net.sf.mmm.util.component.api.Ioc} is available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ComponentContainerContextFallback extends AbstractLoggableComponent implements ComponentContainer {

  /** The {@link AbstractUiContext} instance. */
  private final AbstractUiContext context;

  /**
   * The constructor.
   * 
   * @param context is the {@link AbstractUiContext}.
   */
  public ComponentContainerContextFallback(AbstractUiContext context) {

    this.context = context;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <COMPONENT_API> COMPONENT_API get(Class<COMPONENT_API> apiClass) throws ResourceAmbiguousException,
      ResourceMissingException {

    Object result;
    if (apiClass == UiWidgetFactory.class) {
      result = this.context.getWidgetFactory();
    } else if (apiClass == UiDispatcher.class) {
      result = this.context.getDispatcher();
    } else if (apiClass == UiDisplay.class) {
      result = this.context.getDisplay();
    } else if (apiClass == UiPopupHelper.class) {
      result = this.context.getPopupHelper();
    } else if (apiClass == RoleFactory.class) {
      result = this.context.getRoleFactory();
    } else if (apiClass == UiConfiguration.class) {
      result = this.context.getConfiguration();
    } else if (apiClass == UiDataBindingFactory.class) {
      result = this.context.getDataBindingFactory();
    } else if (apiClass == PojoDescriptorBuilderFactory.class) {
      result = AbstractPojoDescriptorBuilderFactory.getInstance();
      if (result == null) {
        throw new ObjectNotFoundException(PojoDescriptorBuilderFactory.class);
      }
    } else {
      throw new ResourceMissingException(apiClass.getName());
    }
    return (COMPONENT_API) result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <COMPONENT_API> COMPONENT_API get(Class<COMPONENT_API> apiClass, String componentId)
      throws ResourceMissingException {

    return get(apiClass);
  }

}
