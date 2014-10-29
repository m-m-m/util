/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import java.util.HashMap;
import java.util.Map;

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
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
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

  /** @see #get(Class) */
  private final Map<Class<?>, Object> componentMap;

  /**
   * The constructor.
   *
   * @param context is the {@link AbstractUiContext}.
   */
  public ComponentContainerContextFallback(AbstractUiContext context) {

    super();
    this.context = context;
    this.componentMap = new HashMap<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialized() {

    this.componentMap.put(UiWidgetFactory.class, this.context.getWidgetFactory());
    this.componentMap.put(UiDispatcher.class, this.context.getDispatcher());
    this.componentMap.put(UiDisplay.class, this.context.getDisplay());
    this.componentMap.put(UiPopupHelper.class, this.context.getPopupHelper());
    this.componentMap.put(RoleFactory.class, this.context.getRoleFactory());
    this.componentMap.put(UiConfiguration.class, this.context.getConfiguration());

    this.componentMap.put(UiDataBindingFactory.class, this.context.getDataBindingFactory());
    PojoDescriptorBuilderFactory result = AbstractPojoDescriptorBuilderFactory.getInstance();
    if (result == null) {
      throw new ObjectNotFoundException(PojoDescriptorBuilderFactory.class);
    }
    this.componentMap.put(PojoDescriptorBuilderFactory.class, result);
    super.doInitialized();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <COMPONENT_API> COMPONENT_API get(Class<COMPONENT_API> apiClass) throws ResourceAmbiguousException,
      ResourceMissingException {

    Object result = this.componentMap.get(apiClass);
    if (result == null) {
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

  /**
   * Puts the given <code>componentInstance</code> associated with the given <code>apiClass</code> into this
   * container. <br>
   * <b>ATTENTION:</b><br>
   * This class and especially this method should only be used for initialization of limited environments.
   * Otherwise please prefer proper usage of {@link net.sf.mmm.util.component.api.Cdi}.
   *
   * @param <COMPONENT_API> is the generic type of the <code>apiClass</code>.
   * @param apiClass is the class reflecting the API of component. This should be an interface.
   * @param componentInstance is the instance of the component to register.
   */
  public <COMPONENT_API> void put(Class<COMPONENT_API> apiClass, COMPONENT_API componentInstance) {

    this.componentMap.put(apiClass, componentInstance);
  }

}
