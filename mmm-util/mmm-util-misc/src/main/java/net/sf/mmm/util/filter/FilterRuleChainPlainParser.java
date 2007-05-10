/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allows to parse a list of including and excluding regex
 * {@link PatternFilterRule}s and build an according {@link FilterRuleChain}.
 * The rules (include/exclude patterns) are proceeded in the order of their
 * appearance in the list.<br>
 * Here is an example of a configuration (rule list) parsed by this class:
 * 
 * <pre>
 * # This file contains a filter-chain. Such chain is build of a list of 
 * # filter-rules. Each rule starts with a character that determines if an 
 * # inclusion (+) or an exclusion (-) is defined. This character must be followed
 * # by a regex pattern. To make it easier for you a simple pre-processing is 
 * # performed on the pattern string before it is compiled as 
 * # java.util.regex.Pattern:
 * # If the pattern does NOT start with "^" or ".*" the prefix ".*" is 
 * # automatically added. If the pattern does NOT end with "$" or ".*" the suffix
 * # ".*" is automatically appended.
 * # The pre-processing should safe you some typing and make it a little easier for
 * # users of the GNU command "grep".
 * #
 * # The filter-rules in the chain are processed in the order of their occurrence.
 * # The first rule that matches makes the decision according to the first 
 * # character (+/-).
 * # A list starting with the '#' character indicates a comment and is therefore 
 * # ignored. The same applies for empty lines.
 *  
 * # 1. rule says that all strings that start with "/doc/" will be accepted:
 * +^/doc/
 *  
 * # 2. rule says that all strings that end ($) with ".pdf" ignoring the case (?i)  
 * # of the characters will be rejected:
 * -(?i)\.pdf$
 * 
 * # 3. rule says that all string that start with "/data/" will be accepted:
 * +^/data/
 * 
 * # 4. rule says that all string that end ($) with ".xml" or ".xsl" ignoring the 
 * # case (?i) of the characters will be rejected:
 * -(?i)\.(xml|xsl)$
 * 
 * # 5. rule says that everything else is accepted
 * +.*
 * </pre>
 * 
 * @see FilterRuleChainXmlParser
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FilterRuleChainPlainParser {

  /** @see #getAcceptChar() */
  private char acceptChar;

  /** @see #getDenyChar() */
  private char denyChar;

  /** @see #getCommentChar() */
  private char commentChar;

  /**
   * The constructor.
   */
  public FilterRuleChainPlainParser() {

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
   * {@link FilterRuleChain} as described
   * {@link FilterRuleChainPlainParser above}.
   * 
   * @param reader
   *        is where to read from. It will be closed at the end of this method
   *        (on success and in an exceptional state).
   * @param defaultResult
   *        is the {@link FilterRuleChain#getDefaultResult() default-result} of
   *        the chain.
   * @return the parsed configuration as filter-chain.
   * @throws IOException
   *         if an I/O error occurred while parsing.
   */
  public FilterRuleChain parse(Reader reader, boolean defaultResult) throws IOException {

    return parse(new BufferedReader(reader), defaultResult);
  }

  /**
   * This method parses the content of the given <code>reader</code> as
   * {@link FilterRuleChain} as described
   * {@link FilterRuleChainPlainParser above}.
   * 
   * @param reader
   *        is where to read from. It will be closed at the end of this method
   *        (on success and in an exceptional state).
   * @param defaultResult
   *        is the {@link FilterRuleChain#getDefaultResult() default-result} of
   *        the chain.
   * @return the parsed configuration as filter-chain.
   * @throws IOException
   *         if an I/O error occurred while parsing.
   */
  public FilterRuleChain parse(BufferedReader reader, boolean defaultResult) throws IOException {

    try {
      int lineCount = 0;
      List<FilterRule> rules = new ArrayList<FilterRule>();
      String line = reader.readLine();
      while (line != null) {
        line = line.trim();
        if (line.length() > 0) {
          lineCount++;
          char first = line.charAt(0);
          if ((first == this.acceptChar) || (first == this.denyChar)) {
            String regex = line.substring(1);
            boolean accept = (first == this.acceptChar);
            FilterRule rule = new PatternFilterRule(regex, accept);
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
      return new FilterRuleChain(defaultResult, ruleArray);
    } finally {
      reader.close();
    }
  }

}
