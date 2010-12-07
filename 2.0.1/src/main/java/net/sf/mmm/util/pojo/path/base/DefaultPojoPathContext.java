/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import java.util.HashMap;
import java.util.Properties;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.pojo.path.api.PojoPathContext} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class DefaultPojoPathContext extends PojoPathContextBean {

  /**
   * The constructor.
   */
  public DefaultPojoPathContext() {

    super();
    setCache(new HashMap<Object, Object>());
    setProperties(new Properties());
  }
}
