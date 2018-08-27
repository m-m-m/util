/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import java.io.StringReader;

import net.sf.mmm.util.scanner.api.CharStreamScanner;

/**
 * Test of {@link CharReaderScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CharReaderScannerTest extends AbstractCharStreamScannerTest {

  private static final CharReaderScanner SCANNER = new CharReaderScanner(1);

  @Override
  protected CharStreamScanner scanner(String string, boolean lookahead) {

    StringReader reader = new StringReader(string);
    if (lookahead) {
      return new CharReaderScanner(1024, reader);
    }
    SCANNER.setReader(reader);
    return SCANNER;
  }

}
