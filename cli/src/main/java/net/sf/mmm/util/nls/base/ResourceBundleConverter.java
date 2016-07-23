/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import net.sf.mmm.util.cli.NlsBundleUtilCliRoot;
import net.sf.mmm.util.cli.api.CliClass;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.collection.base.SortedProperties;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsResourceLocator;

/**
 * This class can be used to create and update the localized bundles (properties) from an {@link NlsBundle} or
 * {@link AbstractResourceBundle}. If you do not explicitly specify the bundle to synchronize as commandline option
 * (using {@code --bundle}) this {@link ResourceBundleConverter} will automatically scan your classpath for
 * {@link NlsBundle}s and synchronize all of them.<br>
 * This is a main-program to execute via command-line. Simply call it with the parameter {@code --help} to get help.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@CliClass(usage = NlsBundleUtilCliRoot.MSG_BUNDLE_CONVERTER_USAGE)
@CliMode(id = CliMode.ID_DEFAULT, title = NlsBundleUtilCliRoot.INF_MAIN_MODE_DEFAULT, //
    usage = NlsBundleUtilCliRoot.MSG_BUNDLE_CONVERTER_USAGE_MODE_DEFAULT)
public class ResourceBundleConverter extends AbstractResourceBundleCli {

  /** The command-line option to set the {@link #getKeyPattern() key pattern}. */
  public static final String OPTION_KEY_PATTERN = "--key-pattern";

  /** The command-line option to set the {@link #getFormat()}. */
  public static final String OPTION_FORMAT = "--format";

  /** The command-line option to set the {@link #getFormat()}. */
  public static final String OPTION_PATH_EXPRESSION = "--path-expression";

  @CliOption(name = OPTION_LOCALE, aliases = "-l", operand = "LOCALE", //
      required = false, usage = NlsBundleUtilCliRoot.MSG_SYNCHRONIZER_USAGE_LOCALES)
  private String[] locales;

  @CliOption(name = OPTION_KEY_PATTERN, aliases = "-k", operand = "REGEX", //
      required = false, usage = NlsBundleUtilCliRoot.MSG_BUNDLE_CONVERTER_USAGE_KEY_PATTERN)
  private Pattern keyPattern;

  @CliOption(name = OPTION_FORMAT, aliases = "-f", operand = "FORMAT", //
      required = false, usage = NlsBundleUtilCliRoot.MSG_BUNDLE_CONVERTER_USAGE_FORMAT)
  private Format format;

  @CliOption(name = OPTION_PATH_EXPRESSION, aliases = "-x", operand = "EXP", //
      required = false, usage = NlsBundleUtilCliRoot.MSG_BUNDLE_CONVERTER_USAGE_PATH_EXPRESSION)
  private String pathExpression;

  private NlsResourceLocator resourceLocator;

  /**
   * The constructor.
   */
  public ResourceBundleConverter() {

    super();
    this.locales = null;
    this.format = Format.properties;
    this.pathExpression = "{packagePath}/{name}-{locale}";
  }

  /**
   * @return the resourceLocator
   */
  public NlsResourceLocator getResourceLocator() {

    if (this.resourceLocator == null) {
      DefaultNlsResourceLocator impl = new DefaultNlsResourceLocator();
      impl.initialize();
      this.resourceLocator = impl;
    }
    return this.resourceLocator;
  }

  /**
   * @return the optional {@link Pattern} the resource bundle keys have to match for being added to the converted file.
   */
  public Pattern getKeyPattern() {

    return this.keyPattern;
  }

  /**
   * @param pattern the new value of {@link #getKeyPattern()}.
   */
  public void setKeyPattern(Pattern pattern) {

    this.keyPattern = pattern;
  }

  /**
   * @return the {@link Format} to convert to.
   */
  public Format getFormat() {

    return this.format;
  }

  /**
   * @param format the new value of {@link #getFormat()}.
   */
  public void setFormat(Format format) {

    this.format = format;
  }

  /**
   * @return the expression for the path of the converted output file relative to {@link #getPath()}. Includes an
   *         optional path and the filename.
   */
  public String getPathExpression() {

    return this.pathExpression;
  }

  /**
   * @param pathExpression the new value of {@link #getPathExpression()}.
   */
  public void setPathExpression(String pathExpression) {

    this.pathExpression = pathExpression;
  }

  @Override
  public String[] getLocales() {

    return this.locales;
  }

  @Override
  public void setLocales(String[] locales) {

    this.locales = locales;
  }

  @Override
  protected List<String> getLocales(NlsBundleContainer bundle) {

    if (this.locales != null) {
      return Arrays.asList(this.locales);
    }
    final String bundleNamePath = bundle.getQualifiedNamePath() + "_";
    Filter<String> filter = new Filter<String>() {
      @Override
      public boolean accept(String value) {

        return value.startsWith(bundleNamePath);
      }
    };
    Set<String> bundleNames = getReflectionUtil().findResourceNames(bundle.getPackageName(), false, filter);
    List<String> localeList = new ArrayList<>(bundleNames.size());
    for (String bundleName : bundleNames) {
      String suffix = bundleName.substring(bundleNamePath.length());
      int dotIndex = suffix.indexOf('.');
      if (dotIndex > 0) {
        suffix = suffix.substring(0, dotIndex);
      }
      localeList.add(suffix);
    }
    return localeList;
  }

  @Override
  protected File getTargetFile(NlsBundleContainer bundle, String locale) {

    StringBuilder pathBuffer = new StringBuilder(getPath());
    pathBuffer.append('/');
    String relativePath = getPathExpressionResolved(bundle, locale);
    pathBuffer.append(relativePath);
    pathBuffer.append('.');
    pathBuffer.append(this.format);
    File file = new File(pathBuffer.toString());
    return file;
  }

  /**
   * @param bundle the {@link AbstractResourceBundleCli.NlsBundleContainer}.
   * @param locale the {@link Locale}.
   * @return the {@link #getPathExpression() path expression} with all variables resolved.
   */
  protected String getPathExpressionResolved(NlsBundleContainer bundle, String locale) {

    Map<String, String> variables = getVariableMap(bundle, locale);
    int length = this.pathExpression.length();
    StringBuilder buffer = new StringBuilder(length);
    int start = 0;
    boolean todo = true;
    while (todo) {
      int index = this.pathExpression.indexOf('{', start);
      if (index >= 0) {
        int end = this.pathExpression.indexOf('}', index);
        if (end < 0) {
          throw new IllegalArgumentException(this.pathExpression);
        }
        String variable = this.pathExpression.substring(index + 1, end);
        String value = variables.get(variable);
        if (value == null) {
          throw new ObjectNotFoundException(String.class, variable);
        }
        if ((value.length() == 0) && (index > 0)) {
          char c = this.pathExpression.charAt(index - 1);
          if ((c == '_') || (c == '-')) {
            index--;
          }
        }
        buffer.append(this.pathExpression.substring(start, index));
        buffer.append(value);
        start = end + 1;
      } else {
        buffer.append(this.pathExpression.substring(start, length));
        todo = false;
      }
    }
    return buffer.toString();
  }

  /**
   * @param bundle the {@link AbstractResourceBundleCli.NlsBundleContainer}.
   * @param locale the {@link Locale}.
   * @return a {@link Map} with the variables to {@link #getPathExpressionResolved(NlsBundleContainer, String) resolve}.
   */
  protected Map<String, String> getVariableMap(NlsBundleContainer bundle, String locale) {

    Map<String, String> variables = new HashMap<>();
    variables.put("packageName", bundle.getPackageName());
    variables.put("packagePath", bundle.getPackagePath());
    variables.put("name", bundle.getName());
    variables.put("locale", locale);
    return variables;
  }

  @Override
  protected void synchronize(NlsBundleContainer bundle, String localeString, File targetFile, String date)
      throws IOException {

    Locale locale = getResourceLocator().getLocale(localeString);
    String qualifiedName = bundle.getQualifiedName();
    ResourceBundle resourceBundle = ResourceBundle.getBundle(qualifiedName, locale,
        ResourceBundleControlUtf8WithNlsBundleSupport.INSTANCE);
    Properties properties = new SortedProperties();
    Map<String, String> bundleProperties = bundle.getProperties();
    for (String key : bundleProperties.keySet()) {
      if ((this.keyPattern == null) || (this.keyPattern.matcher(key).matches())) {
        String message = resourceBundle.getString(key);
        properties.setProperty(key, message);
      } else {
        getLogger().debug("Ignoring key {} in bundle {} as it does not match key pattern {}", key, qualifiedName,
            this.keyPattern);
      }
    }
    this.format.write(properties, targetFile, getEncoding(), "Generated " + date);
  }

  /**
   * This is the main method used to run this class as application.
   *
   * @param arguments are the command-line arguments.
   */
  public static void main(String[] arguments) {

    ResourceBundleConverter synchronizer = new ResourceBundleConverter();
    synchronizer.runAndExit(arguments);
  }

}
