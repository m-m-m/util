/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.text.api.Justification;
import net.sf.mmm.util.text.api.JustificationBuilder;

/**
 * This is the implementation of {@link JustificationBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
@Singleton
@Named(JustificationBuilder.CDI_NAME)
public class JustificationBuilderImpl extends AbstractComponent implements JustificationBuilder {

  /** @see #getInstance() */
  private static JustificationBuilder instance;

  /**
   * The constructor.
   */
  public JustificationBuilderImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link JustificationBuilder}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please read {@link net.sf.mmm.util.component.api.Ioc#GET_INSTANCE} before using.
   * 
   * @return the singleton instance.
   */
  public static JustificationBuilder getInstance() {

    if (instance == null) {
      synchronized (JustificationBuilderImpl.class) {
        if (instance == null) {
          instance = new JustificationBuilderImpl();
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Justification build(String format) {

    return new JustificationImpl(format);
  }

}
