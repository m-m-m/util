/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.value.api.GenericValueConverter;
import net.sf.mmm.util.value.impl.DefaultComposedValueConverter;

/**
 * The default implementation of the
 * {@link net.sf.mmm.util.cli.api.CliParserBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DefaultCliParserBuilder extends AbstractCliParserBuilder {

  private ReflectionUtil reflectionUtil;

  private StringUtil stringUtil;

  private GenericValueConverter<Object> converter;

  private NlsMessageFactory nlsMessageFactory;

  /**
   * The constructor.
   * 
   */
  public DefaultCliParserBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.nlsMessageFactory == null) {
      this.nlsMessageFactory = NlsAccess.getFactory();
    }
    if (this.converter == null) {
      DefaultComposedValueConverter valueConverter = new DefaultComposedValueConverter();
      valueConverter.initialize();
      this.converter = valueConverter;
    }
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected CliParser buildInternal(Object state, CliState cliState) {

    return new DefaultCliParser(state, cliState, this.stringUtil, this.converter,
        this.nlsMessageFactory);
  }

}
