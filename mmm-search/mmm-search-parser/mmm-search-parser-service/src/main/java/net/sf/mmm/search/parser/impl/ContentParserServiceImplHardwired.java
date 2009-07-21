/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import net.sf.mmm.search.parser.base.AbstractContentParser;

/**
 * This is a variant of {@link ContentParserServiceImpl} that can be used easier
 * without a IoC-framework such as spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserServiceImplHardwired extends ContentParserServiceImpl {

  /**
   * The constructor.
   * 
   */
  public ContentParserServiceImplHardwired() {

    super();
  }

  /**
   * @see #addParser(net.sf.mmm.search.parser.api.ContentParser, String...)
   * 
   * @param parser is the parser to register.
   */
  public void addParser(AbstractContentParser parser) {

    parser.initialize();
    super.addParser(parser, parser.getRegistryKeys());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();

    addParser(new ContentParserPdf());
    addParser(new ContentParserHtml());
    addParser(new ContentParserDoc());
    addParser(new ContentParserXls());
    addParser(new ContentParserPpt());
    addParser(new ContentParserXml());
    // even.txt files may contain markup (e.g. twiki data files).
    addParser(new ContentParserTextMarkupAware());
    addParser(new ContentParserText());
    addParser(new ContentParserJava());

  }
}
