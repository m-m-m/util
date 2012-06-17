/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

import java.io.InputStream;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.content.parser.base.AbstractContentParserGeneric;
import net.sf.mmm.util.context.api.MutableGenericContext;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} used as fallback if no
 * specific parser is available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
@Singleton
public class ContentParserGeneric extends AbstractContentParserGeneric {

  /**
   * The constructor.
   */
  public ContentParserGeneric() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, ContentParserOptions options,
      MutableGenericContext context) throws Exception {

    // TODO: re-implement GNU command "strings"...
    context.setVariable(VARIABLE_NAME_TEXT, "");
  }

}
