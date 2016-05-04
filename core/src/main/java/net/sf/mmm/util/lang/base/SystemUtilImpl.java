/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.SystemInformation;
import net.sf.mmm.util.lang.api.SystemUtil;

/**
 * This is the implementation of the {@link SystemUtil} interface.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class SystemUtilImpl extends AbstractLoggableComponent implements SystemUtil {

  private static SystemUtil instance;

  private final SystemInformation systemInformation;

  /**
   * The constructor.
   */
  public SystemUtilImpl() {

    super();
    this.systemInformation = new SystemInformationImpl();
  }

  /**
   * This method gets the singleton instance of this {@link SystemUtil}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static SystemUtil getInstance() {

    if (instance == null) {
      synchronized (SystemUtilImpl.class) {
        if (instance == null) {
          instance = new SystemUtilImpl();
        }
      }
    }
    return instance;
  }

  @Override
  public SystemInformation getSystemInformation() {

    return this.systemInformation;
  }

}
