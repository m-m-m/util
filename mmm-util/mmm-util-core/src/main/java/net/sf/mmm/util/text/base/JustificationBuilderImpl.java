/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.text.api.Justification;
import net.sf.mmm.util.text.api.JustificationBuilder;

/**
 * This is the implementation of the {@link JustificationBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
@Singleton
@Named
public class JustificationBuilderImpl extends AbstractLoggable implements JustificationBuilder {

  /** @see #getInstance() */
  private static JustificationBuilder instance;

  /**
   * The constructor.
   */
  public JustificationBuilderImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this
   * {@link JustificationBuilder}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
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
  public Justification build(String format) {

    return new JustificationImpl(format);
  }

}
