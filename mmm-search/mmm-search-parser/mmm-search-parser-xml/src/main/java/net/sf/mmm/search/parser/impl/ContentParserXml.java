/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.util.Properties;

import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for XML
 * documents (content with the mimetype "text/xml").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserXml implements ContentParser {

  /**
   * The constructor. 
   */
  public ContentParserXml() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Properties parse(InputStream inputStream, long filesize) throws Exception {

    Properties properties = new Properties();
    StringBuffer text = new StringBuffer();
    int dataByte = inputStream.read();
    while (dataByte == ' ') {
      dataByte = inputStream.read();
    }
    boolean inTag = false;
    if (dataByte == '<') {
      inTag = true;
      // check if xml header
      dataByte = inputStream.read();
      if (dataByte == '?') {
        byte[] miniBuffer = new byte[4];
        int byteCount = inputStream.read(miniBuffer);
        if (byteCount == 4) {
          String miniString = new String(miniBuffer);
          if (miniString.equals("xml ")) {
            dataByte = inputStream.read();
            while (dataByte == ' ') {
              dataByte = inputStream.read();
            }
            // search for encoding
            while (dataByte != 'e') {
              // maybe standalone or version attribute...
              // read to next space
              while (dataByte != ' ') {
                // this may potentially throw an IOException if EOF is reached
                // but in this case we cannot extract useful data anyways...
                dataByte = inputStream.read();
              }
              while (dataByte == ' ') {
                dataByte = inputStream.read();
              }
              dataByte = inputStream.read();
            }
            // so we found the xml-header attribute starting with 'e'
            miniBuffer = new byte[8];
            byteCount = inputStream.read(miniBuffer);
            if (byteCount == 8) {
              miniString = new String(miniBuffer);
              if (miniString.equals("ncoding=")) {
                
              }
            }            
          }
        }
      }
    }
    properties.setProperty(PROPERTY_KEY_TEXT, text.toString());
    return properties;
  }

}
