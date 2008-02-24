/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import java.util.HashMap;
import java.util.Properties;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultPojoPathContext extends PojoPathContextBean {

  /**
   * The constructor.
   */
  public DefaultPojoPathContext() {

    super();
    setCache(new HashMap<String, Object>());
    setProperties(new Properties());
  }
}
