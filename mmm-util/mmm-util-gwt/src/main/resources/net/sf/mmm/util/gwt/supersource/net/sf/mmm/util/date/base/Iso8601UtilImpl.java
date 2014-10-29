/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.date.api.Iso8601Util;

/**
 * This is the implementation of the {@link net.sf.mmm.util.date.api.Iso8601Util} interface. It does NOT use
 * {@link java.text.SimpleDateFormat}. All methods of this class are fast and thread-safe. <br>
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public final class Iso8601UtilImpl extends Iso8601UtilLimitedImpl implements Iso8601Util {

  /**
   * This is the singleton instance of this {@link Iso8601UtilImpl}. Instead of declaring the methods static,
   * we declare this static instance what gives the same way of access while still allowing a design for
   * extension by inheriting from this class.
   */
  private static Iso8601UtilImpl instance;

  /**
   * The constructor.
   */
  public Iso8601UtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link Iso8601UtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Ioc#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static Iso8601UtilImpl getInstance() {

    if (instance == null) {
      synchronized (Iso8601UtilImpl.class) {
        if (instance == null) {
          instance = new Iso8601UtilImpl();
        }
      }
    }
    return instance;
  }

}
