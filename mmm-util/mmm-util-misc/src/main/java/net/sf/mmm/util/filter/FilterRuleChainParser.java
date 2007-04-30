/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class allows to parse a list of including and excluding regex
 * {@link Pattern}s and build an according {@link FilterRuleChain}. The rules
 * (include/exclude patterns) are proceeded in the order of their appearance in
 * the list.<br>
 * Here is an example of a configuration (rule list) parsed by this class:
 * 
 * <pre>
 * # skip files and directories with extensions '.bak' or '.sav'
 * -\.(bak|sav)$
 * # skip files and directories if filename starts with '.' or '~'
 * -.&#42;/[.~].*$
 * # accept all others
 * +.
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FilterRuleChainParser {

  /** @see #getAcceptChar() */
  private char acceptChar;

  /** @see #getDenyChar() */
  private char denyChar;

  /** @see #getCommentChar() */
  private char commentChar;

  /**
   * The constructor.
   */
  public FilterRuleChainParser() {

    super();
    this.acceptChar = '+';
    this.denyChar = '-';
    this.commentChar = '#';
  }

  /**
   * This method gets the character used to identify an accept pattern. The
   * default value is <code>'+'</code>.
   * 
   * @return the accept character
   */
  public char getAcceptChar() {

    return this.acceptChar;
  }

  /**
   * @param acceptChar
   *        the acceptChar to set
   */
  public void setAcceptChar(char acceptChar) {

    this.acceptChar = acceptChar;
  }

  /**
   * This method gets the character used to identify a deny pattern. The default
   * value is <code>'-'</code>.
   * 
   * @return the deny character
   */
  public char getDenyChar() {

    return this.denyChar;
  }

  /**
   * @param denyChar
   *        the denyChar to set
   */
  public void setDenyChar(char denyChar) {

    this.denyChar = denyChar;
  }

  /**
   * This method gets the character used to identify a comment. If a line starts
   * with this character it is ignored. The default value is <code>'#'</code>.
   * 
   * @return the comment character
   */
  public char getCommentChar() {

    return this.commentChar;
  }

  /**
   * @param commentChar
   *        the commentChar to set
   */
  public void setCommentChar(char commentChar) {

    this.commentChar = commentChar;
  }

  /**
   * This method parses the content of the given <code>reader</code> as
   * {@link FilterRuleChain} as described {@link FilterRuleChainParser above}.
   * 
   * @param reader
   *        is where to read from. It will be closed at the end of this method
   *        (on success and in an exceptional state).
   * @return the parsed configuration as filter-chain.
   * @throws IOException
   *         if an I/O error occurred while parsing.
   */
  public FilterRuleChain parse(Reader reader) throws IOException {

    return parse(new BufferedReader(reader));
  }

  /**
   * This method parses the content of the given <code>reader</code> as
   * {@link FilterRuleChain} as described {@link FilterRuleChainParser above}.
   * 
   * @param reader
   *        is where to read from. It will be closed at the end of this method
   *        (on success and in an exceptional state).
   * @return the parsed configuration as filter-chain.
   * @throws IOException
   *         if an I/O error occurred while parsing.
   */
  public FilterRuleChain parse(BufferedReader reader) throws IOException {

    try {
      int lineCount = 0;
      List<FilterRule> rules = new ArrayList<FilterRule>();
      String line = reader.readLine();
      while (line != null) {
        if (line.length() > 0) {
          lineCount++;
          char first = line.charAt(0);
          if ((first == this.acceptChar) || (first == this.denyChar)) {
            String regex = line.substring(1);
            Pattern pattern = Pattern.compile(regex);
            boolean accept = (first == this.acceptChar);
            FilterRule rule = new PatternFilterRule(pattern, accept);
            rules.add(rule);
          } else if (first == this.commentChar) {
            // ignore line
          } else {
            throw new IllegalArgumentException("Illegal start character '" + first + "' in line "
                + lineCount + "!");
          }
        }
        line = reader.readLine();
      }
      FilterRule[] ruleArray = rules.toArray(new FilterRule[rules.size()]);
      return new FilterRuleChain(ruleArray);
    } finally {
      reader.close();
    }
  }

}
