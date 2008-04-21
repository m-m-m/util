/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;

import org.junit.Test;

import net.sf.mmm.util.BasicUtil;

/**
 * This is the test-case for {@link EncodingUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class EncodingUtilTest {

  private static final String[] UNSUPPORTED_ENCODINGS = { EncodingUtil.ENCODING_ISO_8859_10,
      EncodingUtil.ENCODING_ISO_8859_12, EncodingUtil.ENCODING_ISO_8859_14,
      EncodingUtil.ENCODING_ISO_8859_16 };

  protected EncodingUtil getEncodingUtil() {

    return EncodingUtil.getInstance();
  }

  protected void checkEncoding(String encoding) {

    Charset charset = Charset.forName(encoding);
    String canonicalName = charset.name();
    if (!encoding.equals(canonicalName)) {
      System.out.println(encoding + " --> " + canonicalName);
    }
    // assertEquals(encoding, canonicalName);
  }

  @Test
  public void testEncodings() throws Exception {

    EncodingUtil util = getEncodingUtil();

    for (Field field : util.getClass().getFields()) {

      int modifiers = field.getModifiers();
      if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
        String name = field.getName();
        if (name.startsWith("ENCODING_")) {
          String encoding = (String) field.get(null);
          if (!BasicUtil.getInstance().isInArray(encoding, UNSUPPORTED_ENCODINGS, true)) {
            checkEncoding(encoding);
          }
        }
      }
    }
  }

  public void checkUtfReader(DataProducer producer, String encoding) throws Exception {

    System.out.println(encoding);
    String nonUtfEncoding = encoding.toLowerCase();
    if (nonUtfEncoding.startsWith("utf") || nonUtfEncoding.endsWith("ascii")) {
      nonUtfEncoding = "invalid-encoding";
    }
    // produce data as byte-array using the given encoding
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Writer writer = new OutputStreamWriter(out, encoding);
    producer.produce(writer);
    writer.close();
    byte[] data = out.toByteArray();
    // produce data again as string for assertions
    StringWriter stringWriter = new StringWriter();
    producer.produce(stringWriter);
    String expectedData = stringWriter.toString();
    // test reading character per character
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    Reader reader = getEncodingUtil().createUtfDetectionReader(in, nonUtfEncoding);
    int len = expectedData.length();
    for (int i = 0; i < len; i++) {
      int c = reader.read();
      if (c != expectedData.charAt(i)) {
        System.out.println("'" + ((char) c) + "' (" + c + ") - '" + expectedData.charAt(i) + "' ("
            + ((int) expectedData.charAt(i)) + ")");
      }
      // assertEquals((int) expectedData.charAt(i), c);
    }
    int c = reader.read();
    assertEquals(-1, c);
    reader.close();

    // test using buffer
    in = new ByteArrayInputStream(data);
    reader = getEncodingUtil().createUtfDetectionReader(in, nonUtfEncoding);
    stringWriter = new StringWriter();
    StreamUtil.getInstance().transfer(reader, stringWriter, false);
    String dataString = stringWriter.toString();
    assertEquals(expectedData, dataString);
  }

  @Test
  public void testAscii() throws Exception {

    checkUtfReader(new AsciiProducer(), EncodingUtil.ENCODING_US_ASCII);
  }

  @Test
  public void testUtf8() throws Exception {

    checkUtfReader(new UnicodeProducer(), EncodingUtil.ENCODING_UTF_8);
  }

  @Test
  public void testLatin1() throws Exception {

    checkUtfReader(new Latin1Producer(), EncodingUtil.ENCODING_ISO_8859_1);
  }

  protected static interface DataProducer {

    void produce(Writer writer) throws IOException;
  }

  protected static class AsciiProducer implements DataProducer {

    public void produce(Writer writer) throws IOException {

      for (int i = 32; i < 127; i++) {
        writer.write(i);
      }
    }
  }

  protected static class Latin1Producer implements DataProducer {

    public void produce(Writer writer) throws IOException {

      for (int i = 32; i < 255; i++) {
        writer.write(i);
      }
    }
  }

  protected static class UnicodeProducer implements DataProducer {

    public void produce(Writer writer) throws IOException {

      for (int i = 32; i < (4 * 4096); i++) {
        writer.write(i);
      }
    }
  }

}
