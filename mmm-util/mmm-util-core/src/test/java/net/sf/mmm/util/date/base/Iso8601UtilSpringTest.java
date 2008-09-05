/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.date.api.Iso8601Util;

/**
 * This is the test-case for {@link Iso8601Util} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Iso8601UtilSpringTest extends Iso8601UtilTest {

  /**
   * {@inheritDoc}
   */
  @Override
  public Iso8601Util getIso8601Util() {

    return SpringContainerPool.getContainer("net/sf/mmm/util/date/beans-util-date.xml")
        .getComponent(Iso8601Util.class);
  }

}
