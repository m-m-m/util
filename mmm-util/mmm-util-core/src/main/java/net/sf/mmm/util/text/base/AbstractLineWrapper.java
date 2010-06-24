/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.api.TextColumn;
import net.sf.mmm.util.text.api.TextColumnInfo;
import net.sf.mmm.util.text.api.TextTableInfo;

/**
 * This is the abstract base-implementation of the {@link LineWrapper}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractLineWrapper extends AbstractLoggable implements LineWrapper {

  /**
   * The constructor.
   */
  public AbstractLineWrapper() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public int wrap(Appendable appendable, TextTableInfo tableInfo, String columnText,
      TextColumnInfo columnInfo) {

    return wrap(appendable, tableInfo, new TextColumn[] { new TextColumn(columnText, columnInfo) });
  }

  /**
   * {@inheritDoc}
   */
  public int wrap(Appendable appendable, TextTableInfo tableInfo, String column1Text,
      TextColumnInfo column1Info, String column2Text, TextColumnInfo column2Info) {

    return wrap(appendable, tableInfo, new TextColumn[] { new TextColumn(column1Text, column1Info),
        new TextColumn(column2Text, column2Info) });
  }

  /**
   * {@inheritDoc}
   */
  // CHECKSTYLE:OFF (more than 7 parameters required)
  public int wrap(Appendable appendable, TextTableInfo tableInfo, String column1Text,
      TextColumnInfo column1Info, String column2Text, TextColumnInfo column2Info,
      String column3Text, TextColumnInfo column3Info) {

    return wrap(appendable, tableInfo, new TextColumn[] { new TextColumn(column1Text, column1Info),
        new TextColumn(column2Text, column2Info), new TextColumn(column3Text, column3Info) });
  }
  // CHECKSTYLE:ON
}
