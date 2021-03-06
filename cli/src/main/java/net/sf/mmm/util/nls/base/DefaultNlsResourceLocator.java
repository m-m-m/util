/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Locale;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.nls.api.NlsResourceLocator;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This class is used to {@link #findResource(String, String, Locale) find} a {@link Locale localized}
 * {@link DataResource resource}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DefaultNlsResourceLocator extends AbstractComponent implements NlsResourceLocator {

  /**
   * The constructor.
   */
  public DefaultNlsResourceLocator() {

    super();
  }

  @Override
  public Locale getLocale(String locale) {

    int start = 0;
    int end = locale.indexOf(SEPARATOR, 0);
    if (end < 0) {
      if (locale.indexOf('-') > 0) {
        return Locale.forLanguageTag(locale);
      } else {
        return new Locale(locale);
      }
    }
    int length = locale.length();
    String language = locale.substring(start, end);
    String country = "";
    String variant = "";
    start = end + 1;
    if (start < length) {
      end = locale.indexOf(SEPARATOR, start);
      if (end == -1) {
        end = length;
      }
      country = locale.substring(start, end);
      start = end + 1;
      if (start < length) {
        variant = locale.substring(start);
      }
    }
    return new Locale(language, country, variant);
  }

  @Override
  public Locale getLocaleForInfix(String localeInfix) {

    if ((localeInfix != null) && (localeInfix.startsWith(Character.toString(SEPARATOR)))) {
      return getLocale(localeInfix.substring(1));
    }
    return AbstractNlsMessage.LOCALE_ROOT;
  }

  @Override
  public String[] getLocaleInfixes(Locale locale) {

    String[] infixes;
    int length = 1;
    StringBuilder infix = new StringBuilder();
    String infixLang = null;
    String language = locale.getLanguage();
    infix.append(SEPARATOR);
    if ((language != null) && (language.length() == 2)) {
      infix.append(language);
      infixLang = infix.toString();
      length++;
    }
    String infixCountry = null;
    String country = locale.getCountry();
    infix.append(SEPARATOR);
    if ((country != null) && (country.length() == 2)) {
      infix.append(country);
      infixCountry = infix.toString();
      length++;
    }
    String infixVariant = null;
    String variant = locale.getVariant();
    infix.append(SEPARATOR);
    if ((variant != null) && (variant.length() > 0)) {
      infix.append(variant);
      infixVariant = infix.toString();
      length++;
    }
    infixes = new String[length];
    int i = 0;
    if (infixVariant != null) {
      infixes[i] = infixVariant;
      i++;
    }
    if (infixCountry != null) {
      infixes[i] = infixCountry;
      i++;
    }
    if (infixLang != null) {
      infixes[i] = infixLang;
      i++;
    }
    infixes[i] = "";
    return infixes;
  }

  @Override
  public DataResource findResource(Class<?> type, String extension, Locale locale) {

    DataResource resource = null;
    String[] infixes = getLocaleInfixes(locale);
    for (int i = 0; i < infixes.length; i++) {
      String suffix = infixes[i] + extension;
      resource = new ClasspathResource(type, suffix, true);
      if (resource.isAvailable()) {
        return resource;
      }
    }
    String path;
    if (resource != null) {
      path = resource.getPath();
    } else {
      // should never happen...
      path = new ClasspathResource(type, extension, true).getPath();
    }
    throw new ResourceNotAvailableException(path);
  }

  @Override
  public DataResource findResource(String pathAndBasicName, String extension, Locale locale) {

    String[] infixes = getLocaleInfixes(locale);
    StringBuilder path = new StringBuilder();
    path.append(pathAndBasicName);
    for (int i = 0; i < infixes.length; i++) {
      path.setLength(pathAndBasicName.length());
      path.append(infixes[i]);
      path.append(extension);
      DataResource resource = new ClasspathResource(path.toString());
      if (resource.isAvailable()) {
        return resource;
      }
    }
    throw new ResourceNotAvailableException(path.toString());
  }
}
