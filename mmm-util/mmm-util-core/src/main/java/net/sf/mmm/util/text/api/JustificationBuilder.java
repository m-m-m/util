/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

/**
 * This is the interface used to {@link #build(String) build} a
 * {@link Justification} from a {@link String}. It therefore expects the
 * following format:<br>
 * <code>&lt;filler&gt;&lt;alignment&gt;&lt;width&gt;[&lt;mode&gt;]</code><br>
 * The segments have the following meaning:
 * <table border="1">
 * <tr>
 * <th>segment</th>
 * <th>pattern</th>
 * <th>meaning</th>
 * </tr>
 * <tr>
 * <td><code>&lt;filler&gt;</code></td>
 * <td><code>.</code></td>
 * <td>character used to fill up with</td>
 * </tr>
 * <tr>
 * <td><code>&lt;alignment&gt;</code></td>
 * <td><code>[+-~]</code></td>
 * <td>align to the right(+), left(-) or centered(~)</td>
 * </tr>
 * <tr>
 * <td><code>&lt;with&gt;</code></td>
 * <td><code>[0-9]+</code></td>
 * <td>if the length of the string to
 * {@link Justification#justify(CharSequence, Appendable) justify} is less than
 * this width, the string will be expanded using the filler according to the
 * alignment.</td>
 * </tr>
 * <tr>
 * <td><code>&lt;mode&gt;</code></td>
 * <td><code>[|]</code></td>
 * <td>if the mode is truncate(|) then the string will be truncated if its
 * length is greater than &lt;with&gt; so the result will always have the length
 * of &lt;with&gt;. Please note that truncate can remove valuable information or
 * cause wrong results (e.g. "10000" with a justification of " +3|" will result
 * in "100").</td>
 * </tr>
 * </table>
 * Examples:
 * <table border="1">
 * <tr>
 * <th>value</th>
 * <th>justification</th>
 * <th>result</th>
 * </tr>
 * <tr>
 * <td><code>42</code></td>
 * <td><code>0+4</code></td>
 * <td><code>0042</code></td>
 * </tr>
 * <tr>
 * <td><code>42</code></td>
 * <td><code>.-4</code></td>
 * <td><code>42..</code></td>
 * </tr>
 * <tr>
 * <td><code>42</code></td>
 * <td><code>_~11</code></td>
 * <td><code>____42_____</code></td>
 * </tr>
 * <tr>
 * <td><code>Hello World</code></td>
 * <td><code>_+5</code></td>
 * <td><code>Hello World</code></td>
 * </tr>
 * <tr>
 * <td><code>Hello World</code></td>
 * <td><code>_+5|</code></td>
 * <td><code>Hello</code></td>
 * </tr>
 * </table>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
public interface JustificationBuilder {

  /**
   * This method parses the given <code>format</code> as {@link Justification}.
   * 
   * @param format is the format specified {@link JustificationBuilder above}.
   * @return the parsed {@link Justification}
   */
  Justification build(String format);

}
