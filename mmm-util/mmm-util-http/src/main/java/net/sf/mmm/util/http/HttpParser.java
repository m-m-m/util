/* $Id$ */
package net.sf.mmm.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import net.sf.mmm.util.CharFilter;
import net.sf.mmm.util.StringParser;

/**
 * This is a utility class used to parse {@link HttpMessage HTTP-messages}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class HttpParser {

  /**
   * The constructor
   */
  private HttpParser() {

    super();
  }

  /**
   * This method parses a single line of an HTTP header from the given
   * <code>stream</code>. It (re)uses the given <code>buffer</code> to
   * cache bytes while reading. To convert the read bytes to a string the given
   * <code>charset</code> is used.
   * 
   * @param stream
   *        is the input stream to read from.
   * @param buffer
   *        is where the bytes can be cached.
   * @param charset
   *        is used to convert the bytes to a string.
   * @return the parsed string or <code>null</code> if the <code>stream</code>
   *         is already at EOF.
   * @throws IOException
   *         if the operation failes with an I/O problem.
   */
  private static String parseLine(InputStream stream, byte[] buffer, Charset charset)
      throws IOException {

    StringBuffer stringBuffer = null;
    int c = stream.read();
    if (c < 0) {
      // EOF
      return null;
    }
    int bufferIndex = 0;
    while (c >= 0) {
      if (c == '\n') {
        break;
      } else if (c != '\r') {
        buffer[bufferIndex++] = (byte) c;
        if (bufferIndex == buffer.length) {
          String part = new String(buffer, 0, bufferIndex, charset);
          if (stringBuffer != null) {
            stringBuffer = new StringBuffer(part);
          } else {
            stringBuffer.append(part);
          }
          bufferIndex = 0;
        }
      }
      c = stream.read();
    }
    String s = new String(buffer, 0, bufferIndex, charset);
    if (stringBuffer != null) {
      stringBuffer.append(s);
      s = stringBuffer.toString();
    }
    return s;
  }

  /**
   * This method parses the HTTP header properties from the given
   * <code>stream</code>. This method should be called after the first line
   * has been read and an HTTP-version greator or equal to "1.0" has been
   * detected.
   * 
   * @param stream
   *        is the stream to read from.
   * @param message
   *        is the HTTP-message where to
   *        {@link HttpMessage#setHeaderProperty(String, String) set} the parsed
   *        properties.
   * @param charset
   *        is the charset used to parse the properties.
   * @param buffer
   *        is a buffer used to cache bytes.
   * @throws IOException
   *         if the operation failes with an I/O problem.
   */
  private static void parseProperties(InputStream stream, HttpMessage message, Charset charset,
      byte[] buffer) throws IOException {

    String line = parseLine(stream, buffer, charset);
    String currentProperty = null;
    while ((line != null) && (line.length() > 0)) {
      char c = line.charAt(0);
      if ((c == ' ') || (c == '\t')) {
        // LWS
        if (currentProperty != null) {
          message.appendHeaderProperty(currentProperty, line);
        }
      } else {
        StringParser parser = new StringParser(line);
        String property = parser.readWhile(TOKEN_FILTER);
        c = parser.forceNext();
        if (c == ':') {
          c = parser.forceNext();
          parser.readWhile(CharFilter.WHITESPACE_FILTER);
          currentProperty = property;
          String value = parser.read(Integer.MAX_VALUE);
          message.appendHeaderProperty(property, value);
        }
      }
      line = parseLine(stream, buffer, charset);
    }
  }

  /**
   * This method parses the HTTP-header from the given <code>stream</code> and
   * applies all information to the given <code>request</code>. After this
   * method has been called, the given <code>stream</code> is pointing to the
   * beginning of the HTTP-body (or EOF if empty).<br>
   * 
   * @param stream
   *        is the input-stream to read the header from. Only the header is read
   *        so the stream is NOT closed by this method.
   * @param request
   *        is where to apply the parsed information to. Simply supply a new
   *        instance.
   * @param charset
   *        is the charset used to convert the read bytes to strings. (TODO:
   *        default to ASCII?)
   * @throws IOException
   *         if the operation failes with an I/O problem.
   */
  public static void parseRequest(InputStream stream, HttpRequest request, Charset charset)
      throws IOException {

    byte[] buffer = new byte[256];
    String line = parseLine(stream, buffer, charset);
    StringParser parser = new StringParser(line);
    String method = parser.readUntil(' ', false);
    if (method == null) {
      throw new IllegalStateException("Illegal HTTP header!");
    }
    request.setMethod(method.toUpperCase());
    String uri = parser.readUntil(' ', true);
    if (uri == null) {
      throw new IllegalStateException("Illegal HTTP header!");
    }
    request.setUri(uri);
    if (parser.hasNext()) {
      boolean compliant = parser.expect(HttpMessage.VERSION_PREFIX, true);
      if (!compliant) {
        throw new IllegalStateException("Illegal HTTP header!");
      }
      String version = parser.read(Integer.MAX_VALUE);
      request.setVersion(version);
      parseProperties(stream, request, charset, buffer);
    } else {
      request.setVersion(HttpMessage.VERSION_0_9);
    }
  }

  /** char-filter for a token as defined by the HTTP specification */
  private static final CharFilter TOKEN_FILTER = new CharFilter() {

    /**
     * @see net.sf.mmm.util.CharFilter#accept(char)
     */
    public boolean accept(char c) {

      if ((c < 31) || (c == 127)) {
        // no CTL
        return false;
      }
      if ((c == '(') || (c == ')') || (c == '>') || (c == '>') || (c == '@') || (c == ',')
          || (c == ';') || (c == ':') || (c == '\\') || (c == '"') || (c == '/') || (c == '[')
          || (c == ']') || (c == '?') || (c == '=') || (c == '{') || (c == '}') || (c == ' ')) {
        // no tspecials (HT already covered by CTL)
        return false;
      }
      return true;
    }

  };

}