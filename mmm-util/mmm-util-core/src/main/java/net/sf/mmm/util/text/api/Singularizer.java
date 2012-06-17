/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This is the interface for a {@link #transform(String) translation} of a term given in plural form to the
 * according singular form. The implementation will typically work for a specific {@link java.util.Locale}
 * (language).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface Singularizer extends Transformer<String> {

  /**
   * This method gets the singular form of the given term (some word) <code>pluralForm</code> that is
   * (potentially) in plural form. Additionally it preserves the case of the term.<br>
   * The following table illustrates some examples how an implementation for English language could behave:<br>
   * <table border="1">
   * <tr>
   * <th><code>term</code></th>
   * <th><code>toSingular(term)</code></th>
   * </tr>
   * <tr>
   * <td>children</td>
   * <td>child</td>
   * </tr>
   * <tr>
   * <td>classes</td>
   * <td>class</td>
   * </tr>
   * <tr>
   * <td>interfaces</td>
   * <td>interface</td>
   * </tr>
   * <tr>
   * <td>countries</td>
   * <td>country</td>
   * </tr>
   * <tr>
   * <td>women</td>
   * <td>woman</td>
   * </tr>
   * <tr>
   * <td>WOMEN</td>
   * <td>WOMAN</td>
   * </tr>
   * <tr>
   * <td>MailMen</td>
   * <td>MailMan</td>
   * </tr>
   * <tr>
   * <td>SubClasses</td>
   * <td>SubClass</td>
   * </tr>
   * </table>
   * 
   * @param plural is a term (potentially) in plural form.
   * @return the according singular form. This will be {@link Object#equals(Object) equal} to the given string
   *         <code>plural</code> if already singular or no singular form is known.
   */
  String transform(String plural);

}
