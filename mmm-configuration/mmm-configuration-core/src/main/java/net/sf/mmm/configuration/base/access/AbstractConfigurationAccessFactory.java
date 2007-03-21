/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactory;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.context.api.Context;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the abstract base implementation of the
 * {@link ConfigurationAccessFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationAccessFactory implements ConfigurationAccessFactory {

  /** the file accessors */
  private AbstractConfigurationAccess[] accessors;

  /**
   * The constructor.
   */
  public AbstractConfigurationAccessFactory() {

    super();
    this.accessors = null;
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactory#configure(java.lang.String,
   *      net.sf.mmm.context.api.Context,
   *      net.sf.mmm.configuration.api.Configuration)
   */
  public final void configure(String prefix, Context context, Configuration include)
      throws ConfigurationException, ValueException {

    String href = include.getDescendant(ConfigurationDocument.NAME_INCLUDE_HREF).getValue()
        .getString();
    String parentKey = prefix + CONTEXT_VARIABLE_SUFFIX_PARENT;
    ConfigurationAccess parentAccess = context.getValue(parentKey).getValue(
        ConfigurationAccess.class, null);
    // TODO: only store root URL access specific and relative href
    // globally???

    // TODO: base is 'http://foo.com/bar/test.xml'
    // 'foo/some.xml' -> 'http://foo.com/bar/foo/some.xml'
    // '/foo/some.xml' -> 'http://foo.com/foo/some.xml'
    // 'http://bar.com/foo/test.xml' -> 'http://bar.com/foo/test.xml'
    // same for 'C:\bar\text.xml' and '\foo\test.xml' -> 'C:\foo\test.xml'

    // This means there is a top-root 'http://foo.com' or 'C:\'
    // and additionally a PWD 'http://foo.com/bar' or 'C:\bar'
    this.accessors = configure(prefix, context, include, parentAccess, href);
    for (int i = 0; i < this.accessors.length; i++) {
      this.accessors[i].setContextPrefix(prefix);
    }
  }

  /**
   * @see #configure(java.lang.String, net.sf.mmm.context.api.Context,
   *      net.sf.mmm.configuration.api.Configuration)
   * @see #getAccessors()
   * 
   * @param prefix
   *        is the prefix for the {@link Context#getValue(String) "variables"}.
   * @param context
   *        is the context (potentially) containing the required configuration.
   * @param include
   *        is the
   *        {@link net.sf.mmm.configuration.api.ConfigurationDocument#NAME_INCLUDE include}
   *        configuration.
   * @param parent
   *        is the parent access the given <code>href</code> may be relative
   *        to. It should only be interpreted if the given <code>href</code>
   *        is relative and may potentially be <code>null</code> in other
   *        cases.
   * @param href
   *        is the value of the {@link ConfigurationDocument#NAME_INCLUDE_HREF}
   *        attribute. It can be relative or absolute.
   * @return the {@link #getAccessors() accessors}.
   * @throws ConfigurationException
   */
  public abstract AbstractConfigurationAccess[] configure(String prefix, Context context,
      Configuration include, ConfigurationAccess parent, String href) throws ConfigurationException;

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactory#getAccessors()
   */
  public final ConfigurationAccess[] getAccessors() {

    return this.accessors;
  }

}
