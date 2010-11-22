/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;

/**
 * A {@link NlsResourceLocator} is used to
 * {@link #findResource(String, String, Locale) find} a {@link Locale localized}
 * {@link DataResource resource}.<br>
 * The lookup algorithm is analog to the one used by
 * {@link java.util.ResourceBundle}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface NlsResourceLocator {

  /** The separator used for {@link #getLocaleInfixes(Locale) locale-infixes}. */
  char SEPARATOR = '_';

  /**
   * This method determines the infix-strings for localization lookup ordered
   * from most specific to least specific (empty string representing
   * {@link Locale#ROOT}). Each infix is defined as:
   * 
   * <pre>[_&lt;{@link Locale#getLanguage() language}&gt;[_&lt;{@link Locale#getCountry() country}&gt;[_&lt;{@link Locale#getVariant() variant}&gt;]]]</pre>
   * 
   * Please note that if a segment is empty but a following segment is present,
   * multiple underscores ('_') will occur.<br>
   * Examples:
   * <table border="1">
   * <tr>
   * <th>locale</th>
   * <th>result</th>
   * </tr>
   * <tr>
   * <td>{@link Locale#GERMANY}</td>
   * <td><code>{"_de_DE", "_de", ""}</code></td>
   * </tr>
   * <tr>
   * <td>
   * <code>new {@link Locale#Locale(String, String) Locale}("", "CM")</code></td>
   * <td><code>{"__CM", ""}</code></td>
   * </tr>
   * <tr>
   * <td>
   * <code>new {@link Locale#Locale(String, String, String) Locale}("", "", "variant")</code>
   * </td>
   * <td><code>{"___variant", ""}</code></td>
   * </tr>
   * </table>
   * 
   * @param locale is the {@link Locale}.
   * @return the localization-infixes ordered from most specific to least
   *         specific. The returned array will always contain the empty string
   *         as last entry.
   */
  String[] getLocaleInfixes(Locale locale);

  /**
   * This method gets the {@link Locale} for the given
   * {@link #getLocaleInfixes(Locale) locale-infix}.
   * 
   * @param localeInfix is the locale-infix (e.g. "en_US").
   * @return the Locale for the given <code>localeInfix</code>.
   *         {@link Locale#ROOT} if the given <code>localeInfix</code> is
   *         invalid.
   */
  Locale getLocaleForInfix(String localeInfix);

  /**
   * Like {@link #findResource(String, String, Locale)} but using a
   * {@link Class} as <code>pathAndBasicName</code>.
   * 
   * @param type is the {@link Class} identifying the path of the resource
   *        including the filename without locale-part (<code>infix</code>) or
   *        <code>extension</code>.
   * @param extension is the final suffix of the requested {@link DataResource}.
   *        Typically ".properties" or ".xml".
   * @param locale is the locale for which the {@link DataResource resource} is
   *        requested.
   * @return the most specific {@link DataResource resource} for the given
   *         {@link Locale}.
   */
  DataResource findResource(Class<?> type, String extension, Locale locale);

  /**
   * This method finds a localized {@link DataResource resource}. It returns the
   * most specific resource for the given path:
   * 
   * <pre>&lt;pathAndBasicName&gt;&lt;infix&gt;&lt;extension&gt;</pre>
   * 
   * with
   * 
   * <pre>&lt;infix&gt; = [_&lt;locale.getLanguage()&gt;[_&lt;locale.getCountry()&gt;[_&lt;locale.getVariant()&gt;]]]</pre>
   * 
   * If a locale-specific part is NOT defined, the according part of the path is
   * omitted. E.g. for {@link Locale#GERMANY} the following values are tried for
   * <code>&lt;infix&gt;</code>:<br>
   * <code>"de_DE", "de", ""</code>
   * 
   * @param pathAndBasicName is the path of the resource including the filename
   *        without locale-part or <code>extension</code>. Folders (or packages)
   *        need to be separated with '/' and NOT with '.'. E.g.
   *        "net/sf/mmm/util/nls/text/Hypenator".
   * @param extension is the suffix of the requested {@link DataResource}.
   *        Typically ".properties" or ".xml". May be the empty string.
   * @param locale is the locale for which the {@link DataResource resource} is
   *        requested.
   * @return the most specific {@link DataResource resource} for the given
   *         {@link Locale}.
   * @throws ResourceNotAvailableException if no such {@link DataResource
   *         resource} including the {@link Locale#ROOT un-localized} one is
   *         {@link DataResource#isAvailable() available}.
   */
  DataResource findResource(String pathAndBasicName, String extension, Locale locale)
      throws ResourceNotAvailableException;

}
