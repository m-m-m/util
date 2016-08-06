/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This {@link Enum} defines the available formats of the resource bundle output file to convert to.
 *
 * @see ResourceBundleConverter
 *
 * @author hohwille
 * @since 7.3.0
 */
public enum Format {

  /** Format as {@link Properties}. */
  properties() {
    @Override
    String getNewline() {

      return "\\n";
    }
  },

  /** Format as {@code JSON}. */
  json() {
    @Override
    String getNewline() {

      return "<br>";
    }
  };

  abstract String getNewline();

  void write(Properties props, File file, String encoding, String comment) {

    if (props.isEmpty()) {
      return;
    }
    try (FileOutputStream out = new FileOutputStream(file);
        Writer writer = new OutputStreamWriter(out, encoding)) {
      try {
        writeStart(writer, comment);
        boolean first = true;
        for (Object k : props.keySet()) {
          String key = (String) k;
          String value = props.getProperty(key);
          writeProperty(writer, key, value, first);
          first = false;
        }
        writeEnd(writer);
      } catch (IOException e) {
        throw new RuntimeIoException(e, IoMode.WRITE);
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.OPEN);
    }
  }

  private void writeProperty(Writer writer, String key, String message, boolean first) throws IOException {

    String value = message.replace("\r", "").replace("\n", getNewline());
    switch (this) {
      case properties:
        writer.write(key);
        writer.write('=');
        writer.write(value);
        writer.write('\n');
        break;
      case json:
        if (!first) {
          writer.write(",\n");
        }
        writer.write('"');
        writer.write(key);
        writer.write("\": \"");
        writer.write(value.replace("\\", "\\\\").replace("\"", "\\\""));
        writer.write('"');
        break;
      default:
        break;
    }
  }

  private void writeStart(Writer writer, String comment) throws IOException {

    switch (this) {
      case properties:
        if ((comment != null) && !comment.isEmpty()) {
          writer.write("# ");
          writer.write(comment);
          writer.write('\n');
        }
        break;
      case json:
        writer.write("{\n");
        break;
      default:
        break;
    }
  }

  private void writeEnd(Writer writer) throws IOException {

    switch (this) {
      case json:
        writer.write("\n}");
        break;
      default:
        break;
    }
  }
}
