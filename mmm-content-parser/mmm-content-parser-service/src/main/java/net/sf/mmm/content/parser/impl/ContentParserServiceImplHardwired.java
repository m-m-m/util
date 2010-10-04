/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.content.parser.base.AbstractContentParser;
import net.sf.mmm.content.parser.impl.html.ContentParserHtml;
import net.sf.mmm.content.parser.impl.java.ContentParserJava;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOdb;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOdc;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOdf;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOdg;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOdi;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOdm;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOds;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOdt;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOtg;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOth;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOtp;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOts;
import net.sf.mmm.content.parser.impl.opendoc.ContentParserOtt;
import net.sf.mmm.content.parser.impl.pdf.ContentParserPdf;
import net.sf.mmm.content.parser.impl.poi.ContentParserDoc;
import net.sf.mmm.content.parser.impl.poi.ContentParserPpt;
import net.sf.mmm.content.parser.impl.poi.ContentParserXls;
import net.sf.mmm.content.parser.impl.text.ContentParserTextMarkupAware;
import net.sf.mmm.content.parser.impl.xml.ContentParserXml;

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
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    if (getGenericParser() == null) {
      setGenericParser(new ContentParserGenericImpl());
    }
    List<AbstractContentParser> parserList = new ArrayList<AbstractContentParser>();

    parserList.add(new ContentParserPdf());
    parserList.add(new ContentParserHtml());
    parserList.add(new ContentParserDoc());
    parserList.add(new ContentParserXls());
    parserList.add(new ContentParserPpt());

    parserList.add(new ContentParserOdb());
    parserList.add(new ContentParserOdc());
    parserList.add(new ContentParserOdf());
    parserList.add(new ContentParserOdg());
    parserList.add(new ContentParserOdi());
    parserList.add(new ContentParserOdm());
    parserList.add(new ContentParserOds());
    parserList.add(new ContentParserOdt());
    parserList.add(new ContentParserOtg());
    parserList.add(new ContentParserOth());
    parserList.add(new ContentParserOtp());
    parserList.add(new ContentParserOts());
    parserList.add(new ContentParserOtt());

    parserList.add(new ContentParserXml());
    parserList.add(new ContentParserTextMarkupAware());
    parserList.add(new ContentParserJava());
    for (AbstractContentParser abstractContentParser : parserList) {
      abstractContentParser.initialize();
    }
    setContentParsers(parserList);
    super.doInitialize();
  }
}
