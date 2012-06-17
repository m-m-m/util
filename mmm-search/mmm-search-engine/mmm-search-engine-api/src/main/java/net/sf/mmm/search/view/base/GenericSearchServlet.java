/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.base;

import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.impl.SpringContainer;

/**
 * This is the controller {@link javax.servlet.Servlet servlet} for the search.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GenericSearchServlet extends AbstractSearchServlet {

  /** The UID for serialization. */
  private static final long serialVersionUID = -3795814301240648103L;

  /**
   * A comma separated list of {@link Package}s containing components.
   * 
   * @see #getIocContainer()
   */
  private static final String PARAM_COMPONENT_PACKAGES = "component-packages";

  /** @see #getIocContainer() */
  private SpringContainer container;

  /**
   * The constructor.
   */
  public GenericSearchServlet() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IocContainer getIocContainer() {

    // will be called by init-method, so no concurrency problem...
    if (this.container == null) {
      getLogger().info("Starting spring...");
      String componentPackages = getServletConfig().getInitParameter(PARAM_COMPONENT_PACKAGES);
      if (componentPackages == null) {
        this.container = new SpringContainer();
      } else {
        String[] packages = componentPackages.split(",");
        this.container = new SpringContainer(packages);
      }
      getLogger().info("Spring started...");
    }
    return this.container;
  }
}
