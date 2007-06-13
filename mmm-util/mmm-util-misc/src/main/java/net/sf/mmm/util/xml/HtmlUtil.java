/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * This utility class contains methods that help to deal with markup such as
 * XML, SGML, HTML, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class HtmlUtil {

  /** @see #resolveEntity(String) */
  private static final Map<String, Character> ENTITY_MAP;

  static {
    ENTITY_MAP = new HashMap<String, Character>(256);
    // XML
    ENTITY_MAP.put("quot", Character.valueOf((char) 34));
    ENTITY_MAP.put("amp", Character.valueOf((char) 38));
    ENTITY_MAP.put("lt", Character.valueOf((char) 60));
    ENTITY_MAP.put("gt", Character.valueOf((char) 62));
    // HTML starts here
    ENTITY_MAP.put("apos", Character.valueOf((char) 39));
    // ISO-8859-1
    ENTITY_MAP.put("nbsp", Character.valueOf((char) 160));
    ENTITY_MAP.put("iexcl", Character.valueOf((char) 161));
    ENTITY_MAP.put("cent", Character.valueOf((char) 162));
    ENTITY_MAP.put("pound", Character.valueOf((char) 163));
    ENTITY_MAP.put("curren", Character.valueOf((char) 164));
    ENTITY_MAP.put("yen", Character.valueOf((char) 165));
    ENTITY_MAP.put("brvbar", Character.valueOf((char) 166));
    ENTITY_MAP.put("sect", Character.valueOf((char) 167));
    ENTITY_MAP.put("uml", Character.valueOf((char) 168));
    ENTITY_MAP.put("copy", Character.valueOf((char) 169));
    ENTITY_MAP.put("ordf", Character.valueOf((char) 170));
    ENTITY_MAP.put("laquo", Character.valueOf((char) 171));
    ENTITY_MAP.put("not", Character.valueOf((char) 172));
    ENTITY_MAP.put("shy", Character.valueOf((char) 173));
    ENTITY_MAP.put("reg", Character.valueOf((char) 174));
    ENTITY_MAP.put("macr", Character.valueOf((char) 175));
    ENTITY_MAP.put("deg", Character.valueOf((char) 176));
    ENTITY_MAP.put("plusmn", Character.valueOf((char) 177));
    ENTITY_MAP.put("sup2", Character.valueOf((char) 178));
    ENTITY_MAP.put("sup3", Character.valueOf((char) 179));
    ENTITY_MAP.put("acute", Character.valueOf((char) 180));
    ENTITY_MAP.put("micro", Character.valueOf((char) 181));
    ENTITY_MAP.put("para", Character.valueOf((char) 182));
    ENTITY_MAP.put("middot", Character.valueOf((char) 183));
    ENTITY_MAP.put("cedil", Character.valueOf((char) 184));
    ENTITY_MAP.put("sup1", Character.valueOf((char) 185));
    ENTITY_MAP.put("ordm", Character.valueOf((char) 186));
    ENTITY_MAP.put("raquo", Character.valueOf((char) 187));
    ENTITY_MAP.put("frac14", Character.valueOf((char) 188));
    ENTITY_MAP.put("frac12", Character.valueOf((char) 189));
    ENTITY_MAP.put("frac34", Character.valueOf((char) 190));
    ENTITY_MAP.put("iquest", Character.valueOf((char) 191));
    ENTITY_MAP.put("Agrave", Character.valueOf((char) 192));
    ENTITY_MAP.put("Aacute", Character.valueOf((char) 193));
    ENTITY_MAP.put("Acirc", Character.valueOf((char) 194));
    ENTITY_MAP.put("Atilde", Character.valueOf((char) 195));
    ENTITY_MAP.put("Auml", Character.valueOf((char) 196));
    ENTITY_MAP.put("Aring", Character.valueOf((char) 197));
    ENTITY_MAP.put("AElig", Character.valueOf((char) 198));
    ENTITY_MAP.put("Ccedil", Character.valueOf((char) 199));
    ENTITY_MAP.put("Egrave", Character.valueOf((char) 200));
    ENTITY_MAP.put("Eacute", Character.valueOf((char) 201));
    ENTITY_MAP.put("Ecirc", Character.valueOf((char) 202));
    ENTITY_MAP.put("Euml", Character.valueOf((char) 203));
    ENTITY_MAP.put("Igrave", Character.valueOf((char) 204));
    ENTITY_MAP.put("Iacute", Character.valueOf((char) 205));
    ENTITY_MAP.put("Icirc", Character.valueOf((char) 206));
    ENTITY_MAP.put("Iuml", Character.valueOf((char) 207));
    ENTITY_MAP.put("ETH", Character.valueOf((char) 208));
    ENTITY_MAP.put("Ntilde", Character.valueOf((char) 209));
    ENTITY_MAP.put("Ograve", Character.valueOf((char) 210));
    ENTITY_MAP.put("Oacute", Character.valueOf((char) 211));
    ENTITY_MAP.put("Ocirc", Character.valueOf((char) 212));
    ENTITY_MAP.put("Otilde", Character.valueOf((char) 213));
    ENTITY_MAP.put("Ouml", Character.valueOf((char) 214));
    ENTITY_MAP.put("times", Character.valueOf((char) 215));
    ENTITY_MAP.put("Oslash", Character.valueOf((char) 216));
    ENTITY_MAP.put("Ugrave", Character.valueOf((char) 217));
    ENTITY_MAP.put("Uacute", Character.valueOf((char) 218));
    ENTITY_MAP.put("Ucirc", Character.valueOf((char) 219));
    ENTITY_MAP.put("Uuml", Character.valueOf((char) 220));
    ENTITY_MAP.put("Yacute", Character.valueOf((char) 221));
    ENTITY_MAP.put("THORN", Character.valueOf((char) 222));
    ENTITY_MAP.put("szlig", Character.valueOf((char) 223));
    ENTITY_MAP.put("agrave", Character.valueOf((char) 224));
    ENTITY_MAP.put("aacute", Character.valueOf((char) 225));
    ENTITY_MAP.put("acirc", Character.valueOf((char) 226));
    ENTITY_MAP.put("atilde", Character.valueOf((char) 227));
    ENTITY_MAP.put("auml", Character.valueOf((char) 228));
    ENTITY_MAP.put("aring", Character.valueOf((char) 229));
    ENTITY_MAP.put("aelig", Character.valueOf((char) 230));
    ENTITY_MAP.put("ccedil", Character.valueOf((char) 231));
    ENTITY_MAP.put("egrave", Character.valueOf((char) 232));
    ENTITY_MAP.put("eacute", Character.valueOf((char) 233));
    ENTITY_MAP.put("ecirc", Character.valueOf((char) 234));
    ENTITY_MAP.put("euml", Character.valueOf((char) 235));
    ENTITY_MAP.put("igrave", Character.valueOf((char) 236));
    ENTITY_MAP.put("iacute", Character.valueOf((char) 237));
    ENTITY_MAP.put("icirc", Character.valueOf((char) 238));
    ENTITY_MAP.put("iuml", Character.valueOf((char) 239));
    ENTITY_MAP.put("eth", Character.valueOf((char) 240));
    ENTITY_MAP.put("ntilde", Character.valueOf((char) 241));
    ENTITY_MAP.put("ograve", Character.valueOf((char) 242));
    ENTITY_MAP.put("oacute", Character.valueOf((char) 243));
    ENTITY_MAP.put("ocirc", Character.valueOf((char) 244));
    ENTITY_MAP.put("otilde", Character.valueOf((char) 245));
    ENTITY_MAP.put("ouml", Character.valueOf((char) 246));
    ENTITY_MAP.put("divide", Character.valueOf((char) 247));
    ENTITY_MAP.put("oslash", Character.valueOf((char) 248));
    ENTITY_MAP.put("ugrave", Character.valueOf((char) 249));
    ENTITY_MAP.put("uacute", Character.valueOf((char) 250));
    ENTITY_MAP.put("ucirc", Character.valueOf((char) 251));
    ENTITY_MAP.put("uuml", Character.valueOf((char) 252));
    ENTITY_MAP.put("yacute", Character.valueOf((char) 253));
    ENTITY_MAP.put("thorn", Character.valueOf((char) 254));
    ENTITY_MAP.put("yuml", Character.valueOf((char) 255));
    // HTML 4.0
    ENTITY_MAP.put("fnof", Character.valueOf((char) 402));
    ENTITY_MAP.put("Alpha", Character.valueOf((char) 913));
    ENTITY_MAP.put("Beta", Character.valueOf((char) 914));
    ENTITY_MAP.put("Gamma", Character.valueOf((char) 915));
    ENTITY_MAP.put("Delta", Character.valueOf((char) 916));
    ENTITY_MAP.put("Epsilon", Character.valueOf((char) 917));
    ENTITY_MAP.put("Zeta", Character.valueOf((char) 918));
    ENTITY_MAP.put("Eta", Character.valueOf((char) 919));
    ENTITY_MAP.put("Theta", Character.valueOf((char) 920));
    ENTITY_MAP.put("Iota", Character.valueOf((char) 921));
    ENTITY_MAP.put("Kappa", Character.valueOf((char) 922));
    ENTITY_MAP.put("Lambda", Character.valueOf((char) 923));
    ENTITY_MAP.put("Mu", Character.valueOf((char) 924));
    ENTITY_MAP.put("Nu", Character.valueOf((char) 925));
    ENTITY_MAP.put("Xi", Character.valueOf((char) 926));
    ENTITY_MAP.put("Omicron", Character.valueOf((char) 927));
    ENTITY_MAP.put("Pi", Character.valueOf((char) 928));
    ENTITY_MAP.put("Rho", Character.valueOf((char) 929));
    ENTITY_MAP.put("Sigma", Character.valueOf((char) 931));
    ENTITY_MAP.put("Tau", Character.valueOf((char) 932));
    ENTITY_MAP.put("Upsilon", Character.valueOf((char) 933));
    ENTITY_MAP.put("Phi", Character.valueOf((char) 934));
    ENTITY_MAP.put("Chi", Character.valueOf((char) 935));
    ENTITY_MAP.put("Psi", Character.valueOf((char) 936));
    ENTITY_MAP.put("Omega", Character.valueOf((char) 937));
    ENTITY_MAP.put("alpha", Character.valueOf((char) 945));
    ENTITY_MAP.put("beta", Character.valueOf((char) 946));
    ENTITY_MAP.put("gamma", Character.valueOf((char) 947));
    ENTITY_MAP.put("delta", Character.valueOf((char) 948));
    ENTITY_MAP.put("epsilon", Character.valueOf((char) 949));
    ENTITY_MAP.put("zeta", Character.valueOf((char) 950));
    ENTITY_MAP.put("eta", Character.valueOf((char) 951));
    ENTITY_MAP.put("theta", Character.valueOf((char) 952));
    ENTITY_MAP.put("iota", Character.valueOf((char) 953));
    ENTITY_MAP.put("kappa", Character.valueOf((char) 954));
    ENTITY_MAP.put("lambda", Character.valueOf((char) 955));
    ENTITY_MAP.put("mu", Character.valueOf((char) 956));
    ENTITY_MAP.put("nu", Character.valueOf((char) 957));
    ENTITY_MAP.put("xi", Character.valueOf((char) 958));
    ENTITY_MAP.put("omicron", Character.valueOf((char) 959));
    ENTITY_MAP.put("pi", Character.valueOf((char) 960));
    ENTITY_MAP.put("rho", Character.valueOf((char) 961));
    ENTITY_MAP.put("sigmaf", Character.valueOf((char) 962));
    ENTITY_MAP.put("sigma", Character.valueOf((char) 963));
    ENTITY_MAP.put("tau", Character.valueOf((char) 964));
    ENTITY_MAP.put("upsilon", Character.valueOf((char) 965));
    ENTITY_MAP.put("phi", Character.valueOf((char) 966));
    ENTITY_MAP.put("chi", Character.valueOf((char) 967));
    ENTITY_MAP.put("psi", Character.valueOf((char) 968));
    ENTITY_MAP.put("omega", Character.valueOf((char) 969));
    ENTITY_MAP.put("thetasym", Character.valueOf((char) 977));
    ENTITY_MAP.put("upsih", Character.valueOf((char) 978));
    ENTITY_MAP.put("piv", Character.valueOf((char) 982));
    ENTITY_MAP.put("bull", Character.valueOf((char) 8226));
    ENTITY_MAP.put("hellip", Character.valueOf((char) 8230));
    ENTITY_MAP.put("prime", Character.valueOf((char) 8242));
    ENTITY_MAP.put("Prime", Character.valueOf((char) 8243));
    ENTITY_MAP.put("oline", Character.valueOf((char) 8254));
    ENTITY_MAP.put("frasl", Character.valueOf((char) 8260));
    ENTITY_MAP.put("weierp", Character.valueOf((char) 8472));
    ENTITY_MAP.put("image", Character.valueOf((char) 8465));
    ENTITY_MAP.put("real", Character.valueOf((char) 8476));
    ENTITY_MAP.put("trade", Character.valueOf((char) 8482));
    ENTITY_MAP.put("alefsym", Character.valueOf((char) 8501));
    ENTITY_MAP.put("larr", Character.valueOf((char) 8592));
    ENTITY_MAP.put("uarr", Character.valueOf((char) 8593));
    ENTITY_MAP.put("rarr", Character.valueOf((char) 8594));
    ENTITY_MAP.put("darr", Character.valueOf((char) 8595));
    ENTITY_MAP.put("harr", Character.valueOf((char) 8596));
    ENTITY_MAP.put("crarr", Character.valueOf((char) 8629));
    ENTITY_MAP.put("lArr", Character.valueOf((char) 8656));
    ENTITY_MAP.put("uArr", Character.valueOf((char) 8657));
    ENTITY_MAP.put("rArr", Character.valueOf((char) 8658));
    ENTITY_MAP.put("dArr", Character.valueOf((char) 8659));
    ENTITY_MAP.put("hArr", Character.valueOf((char) 8660));
    ENTITY_MAP.put("forall", Character.valueOf((char) 8704));
    ENTITY_MAP.put("part", Character.valueOf((char) 8706));
    ENTITY_MAP.put("exist", Character.valueOf((char) 8707));
    ENTITY_MAP.put("empty", Character.valueOf((char) 8709));
    ENTITY_MAP.put("nabla", Character.valueOf((char) 8711));
    ENTITY_MAP.put("isin", Character.valueOf((char) 8712));
    ENTITY_MAP.put("notin", Character.valueOf((char) 8713));
    ENTITY_MAP.put("ni", Character.valueOf((char) 8715));
    ENTITY_MAP.put("prod", Character.valueOf((char) 8719));
    ENTITY_MAP.put("sum", Character.valueOf((char) 8721));
    ENTITY_MAP.put("minus", Character.valueOf((char) 8722));
    ENTITY_MAP.put("lowast", Character.valueOf((char) 8727));
    ENTITY_MAP.put("radic", Character.valueOf((char) 8730));
    ENTITY_MAP.put("prop", Character.valueOf((char) 8733));
    ENTITY_MAP.put("infin", Character.valueOf((char) 8734));
    ENTITY_MAP.put("ang", Character.valueOf((char) 8736));
    ENTITY_MAP.put("and", Character.valueOf((char) 8743));
    ENTITY_MAP.put("or", Character.valueOf((char) 8744));
    ENTITY_MAP.put("cap", Character.valueOf((char) 8745));
    ENTITY_MAP.put("cup", Character.valueOf((char) 8746));
    ENTITY_MAP.put("int", Character.valueOf((char) 8747));
    ENTITY_MAP.put("there4", Character.valueOf((char) 8756));
    ENTITY_MAP.put("sim", Character.valueOf((char) 8764));
    ENTITY_MAP.put("cong", Character.valueOf((char) 8773));
    ENTITY_MAP.put("asymp", Character.valueOf((char) 8776));
    ENTITY_MAP.put("ne", Character.valueOf((char) 8800));
    ENTITY_MAP.put("equiv", Character.valueOf((char) 8801));
    ENTITY_MAP.put("le", Character.valueOf((char) 8804));
    ENTITY_MAP.put("ge", Character.valueOf((char) 8805));
    ENTITY_MAP.put("sub", Character.valueOf((char) 8834));
    ENTITY_MAP.put("sup", Character.valueOf((char) 8835));
    ENTITY_MAP.put("sube", Character.valueOf((char) 8838));
    ENTITY_MAP.put("supe", Character.valueOf((char) 8839));
    ENTITY_MAP.put("oplus", Character.valueOf((char) 8853));
    ENTITY_MAP.put("otimes", Character.valueOf((char) 8855));
    ENTITY_MAP.put("perp", Character.valueOf((char) 8869));
    ENTITY_MAP.put("sdot", Character.valueOf((char) 8901));
    ENTITY_MAP.put("lceil", Character.valueOf((char) 8968));
    ENTITY_MAP.put("rceil", Character.valueOf((char) 8969));
    ENTITY_MAP.put("lfloor", Character.valueOf((char) 8970));
    ENTITY_MAP.put("rfloor", Character.valueOf((char) 8971));
    ENTITY_MAP.put("lang", Character.valueOf((char) 9001));
    ENTITY_MAP.put("rang", Character.valueOf((char) 9002));
    ENTITY_MAP.put("loz", Character.valueOf((char) 9674));
    ENTITY_MAP.put("spades", Character.valueOf((char) 9824));
    ENTITY_MAP.put("clubs", Character.valueOf((char) 9827));
    ENTITY_MAP.put("hearts", Character.valueOf((char) 9829));
    ENTITY_MAP.put("diams", Character.valueOf((char) 9830));
    ENTITY_MAP.put("OElig", Character.valueOf((char) 338));
    ENTITY_MAP.put("oelig", Character.valueOf((char) 339));
    ENTITY_MAP.put("Scaron", Character.valueOf((char) 352));
    ENTITY_MAP.put("scaron", Character.valueOf((char) 353));
    ENTITY_MAP.put("Yuml", Character.valueOf((char) 376));
    ENTITY_MAP.put("circ", Character.valueOf((char) 710));
    ENTITY_MAP.put("tilde", Character.valueOf((char) 732));
    ENTITY_MAP.put("ensp", Character.valueOf((char) 8194));
    ENTITY_MAP.put("emsp", Character.valueOf((char) 8195));
    ENTITY_MAP.put("thinsp", Character.valueOf((char) 8201));
    ENTITY_MAP.put("zwnj", Character.valueOf((char) 8204));
    ENTITY_MAP.put("zwj", Character.valueOf((char) 8205));
    ENTITY_MAP.put("lrm", Character.valueOf((char) 8206));
    ENTITY_MAP.put("rlm", Character.valueOf((char) 8207));
    ENTITY_MAP.put("ndash", Character.valueOf((char) 8211));
    ENTITY_MAP.put("mdash", Character.valueOf((char) 8212));
    ENTITY_MAP.put("lsquo", Character.valueOf((char) 8216));
    ENTITY_MAP.put("rsquo", Character.valueOf((char) 8217));
    ENTITY_MAP.put("sbquo", Character.valueOf((char) 8218));
    ENTITY_MAP.put("ldquo", Character.valueOf((char) 8220));
    ENTITY_MAP.put("rdquo", Character.valueOf((char) 8221));
    ENTITY_MAP.put("bdquo", Character.valueOf((char) 8222));
    ENTITY_MAP.put("dagger", Character.valueOf((char) 8224));
    ENTITY_MAP.put("Dagger", Character.valueOf((char) 8225));
    ENTITY_MAP.put("permil", Character.valueOf((char) 8240));
    ENTITY_MAP.put("lsaquo", Character.valueOf((char) 8249));
    ENTITY_MAP.put("rsaquo", Character.valueOf((char) 8250));
    ENTITY_MAP.put("euro", Character.valueOf((char) 8364));
  }

  /**
   * The constructor.
   */
  private HtmlUtil() {

  }

  /**
   * This method resolves an HTML entity given by <code>entityName</code>.
   * 
   * @param entityName is the bare name of the entity (e.g. "amp" or "uuml").
   *        Please note that entity-names are case-sensitive.
   * @return the value of the entity or <code>null</code> if no entity exists
   *         for the given <code>entityName</code>.
   */
  public static Character resolveEntity(String entityName) {

    return ENTITY_MAP.get(entityName);
  }

  /**
   * This method extracts the plain text from the given
   * <code>htmlFragment</code> and appends it to the given <code>buffer</code>.
   * This includes removing tags, un-escaping entities and parsing CDATA
   * sections. Unlike DOM parsers this method is completely fault tolerant, fast
   * and uses a minimum amount of memory.<br>
   * <b>ATTENTION:</b><br>
   * Be aware that the caller is responsible for reading the HTML with the
   * proper encoding (according to Content-Type from HTTP header and/or META
   * tag).
   * 
   * @param htmlFragment is the HTML fragment to parse.
   * @param buffer is the buffer where the plain text will be appended to.
   * @param parserState is the state to continue on a subsequent call for
   *        multiple <code>htmlFragment</code>s of the same HTML-document or
   *        <code>null</code> for a fresh start.
   * @return the state at the end of <code>htmlFragment</code>. You can pass
   *         this as <code>parserState</code> argument on subsequent call to
   *         continue parsing.
   */
  public static ParserState extractPlainText(String htmlFragment, StringBuffer buffer,
      ParserState parserState) {

    ParserState state = new ParserState(parserState);
    int len = htmlFragment.length();
    for (int i = 0; i < len; i++) {
      char c = htmlFragment.charAt(i);
      if (state.tagStartIndex > 0) {
        // in tag...
        int tagLen = i - state.tagStartIndex;
        if (tagLen == 2) {
          // check for BR tag
          String substring = htmlFragment.substring(state.tagStartIndex, i);
          if (substring.toLowerCase().equals("br")) {
            if ((c == ' ') || (c == '/') || (c == '>')) {
              // BR tag detected...
              buffer.append('\n');
            }
          }
        } else if (tagLen == 8) {
          // check for CDATA section
          // "![CDATA[".length() == 8
          String substring = htmlFragment.substring(state.tagStartIndex, i);
          if ("![CDATA[".equals(substring)) {
            // start of CDATA section detected...
            state.cdataCloseCount = 3;
          }
        }
      }
      // main proceeding
      if (state.cdataCloseCount > 0) {
        // in CDATA section...
        if ((c == ']') && (state.cdataCloseCount > 1)) {
          state.cdataCloseCount--;
        } else if ((c == '>') && (state.cdataCloseCount == 1)) {
          state.cdataCloseCount = 0;
        } else if (state.cdataCloseCount == 3) {
          buffer.append(c);
        } else {
          buffer.append(']');
          if (state.cdataCloseCount == 1) {
            buffer.append(']');
          }
          state.cdataCloseCount = 3;
        }
      } else if (c == '<') {
        // start of tag detected...
        // '<' must be escaped outside CDATA sections, even in attributes...
        state.tagStartIndex = i + 1;
      } else if (c == '>') {
        // end of tag detected if not in attribute...
        if (state.inAttribute == 0) {
          state.tagStartIndex = 0;
        }
      } else if (state.tagStartIndex == 0) {
        // in regular text...
        if (c == '&') {
          // un-escaping of entities...
          int end = i + 10;
          if (end > len) {
            end = len;
          }
          boolean semicolonFound = false;
          int lookaheadIndex;
          for (lookaheadIndex = i + 1; lookaheadIndex < end; lookaheadIndex++) {
            if (htmlFragment.charAt(lookaheadIndex) == ';') {
              semicolonFound = true;
              break;
            }
          }
          if (semicolonFound) {
            if (htmlFragment.charAt(i + 1) == '#') {
              String codepoint = htmlFragment.substring(i + 2, lookaheadIndex);
              try {
                int code = Integer.parseInt(codepoint);
                c = (char) code;
              } catch (NumberFormatException e) {
                c = (char) 65533;
              }
              buffer.append(c);
            } else {
              String entityName = htmlFragment.substring(i + 1, lookaheadIndex);
              Character value = HtmlUtil.resolveEntity(entityName);
              if (value != null) {
                c = value.charValue();
              } else {
                c = (char) 65533;
              }
              buffer.append(c);
            }
            i = lookaheadIndex;
          } else {
            buffer.append(c);
          }
        } else {
          buffer.append(c);
        }
      } else if ((c == '"') || (c == '\'')) {
        // quotation inside tag...
        if (state.inAttribute == 0) {
          // start of attribute value...
          state.inAttribute = c;
        } else if (state.inAttribute == c) {
          // end of attribute value...
          state.inAttribute = 0;
        }
      }
    }
    return state;
  }

  /**
   * This inner class contains the state of an HTML
   * {@link HtmlUtil#extractPlainText(String, StringBuffer, net.sf.mmm.util.xml.HtmlUtil.ParserState) parsing}
   * process.
   */
  public static class ParserState {

    /** index of first char after '<' in string or 0 if not in tag. */
    private int tagStartIndex;

    /** the opening quotation char if inside attribute, else 0. */
    private char inAttribute;

    /**
     * set to 3 at start of CDATA section, count down for closing "]]>" but
     * reset to 3 on mismatch.
     */
    private int cdataCloseCount;

    /**
     * The constructor.
     */
    public ParserState() {

      super();
      this.tagStartIndex = 0;
      this.inAttribute = 0;
      this.cdataCloseCount = 0;
    }

    /**
     * The constructor.
     * 
     * @param other is the state to continue with.
     */
    public ParserState(ParserState other) {

      this();
      if (other != null) {
        if (other.tagStartIndex != 0) {
          this.tagStartIndex = -1;
        } else {
          this.tagStartIndex = 0;
        }
        this.inAttribute = other.inAttribute;
        this.cdataCloseCount = other.cdataCloseCount;
      }
    }

  }

}
