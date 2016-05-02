/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface used to {@link #build(String) build} a {@link Justification} from a {@link String}. It
 * therefore expects the following format:<br>
 * {@code «filler»«alignment»«width»[«mode»]}<br>
 * The segments have the following meaning:
 * <table border="1">
 * <tr>
 * <th>segment</th>
 * <th>pattern</th>
 * <th>meaning</th>
 * </tr>
 * <tr>
 * <td>{@code «filler»}</td>
 * <td>{@code .}</td>
 * <td>character used to fill up with</td>
 * </tr>
 * <tr>
 * <td>{@code «alignment»}</td>
 * <td>{@code [+-~]}</td>
 * <td>align to the right(+), left(-) or centered(~)</td>
 * </tr>
 * <tr>
 * <td>{@code «with»}</td>
 * <td>{@code [0-9]+}</td>
 * <td>if the length of the string to {@link Justification#justify(CharSequence, Appendable) justify} is less than this
 * width, the string will be expanded using the filler according to the alignment.</td>
 * </tr>
 * <tr>
 * <td>{@code «mode»}</td>
 * <td>{@code [|]}</td>
 * <td>if the mode is truncate(|) then the string will be truncated if its length is greater than «with» so the result
 * will always have the length of «with». Please note that truncate can remove valuable information or cause wrong
 * results (e.g. "10000" with a justification of " +3|" will result in "100").</td>
 * </tr>
 * </table>
 *
 * Examples:
 * <table border="1">
 * <tr>
 * <th>value</th>
 * <th>justification</th>
 * <th>result</th>
 * </tr>
 * <tr>
 * <td>{@code 42}</td>
 * <td>{@code 0+4}</td>
 * <td>{@code 0042}</td>
 * </tr>
 * <tr>
 * <td>{@code 42}</td>
 * <td>{@code .-4}</td>
 * <td>{@code 42..}</td>
 * </tr>
 * <tr>
 * <td>{@code 42}</td>
 * <td>{@code _~11}</td>
 * <td>{@code ____42_____}</td>
 * </tr>
 * <tr>
 * <td>{@code Hello World}</td>
 * <td>{@code _+5}</td>
 * <td>{@code Hello World}</td>
 * </tr>
 * <tr>
 * <td>{@code Hello World}</td>
 * <td>{@code _+5|}</td>
 * <td>{@code Hello}</td>
 * </tr>
 * </table>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
@ComponentSpecification
public interface JustificationBuilder {

  /**
   * This method parses the given {@code format} as {@link Justification}.
   *
   * @param format is the format specified {@link JustificationBuilder above}.
   * @return the parsed {@link Justification}
   */
  Justification build(String format);

}
