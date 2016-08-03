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
import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.lang.api.Visitor;
import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsFormatterPlugin;
import net.sf.mmm.util.nls.api.NlsMessageFormatter;
import net.sf.mmm.util.nls.api.NlsMessageFormatterFactory;
import net.sf.mmm.util.nls.api.NlsResourceLocator;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoice;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoice.Choice;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoice.Segment;
import net.sf.mmm.util.text.api.Justification;

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

  /** The command-line option to set the {@link #getFormat()}. */
  public static final String OPTION_ARGUMENT = "--argument";

  /** The command-line option to set the {@link #getEvalChoice()}. */
  public static final String OPTION_EVAL_CHOICE = "--eval-choice";

  /** The default for option {@link #getArgument() argument}. */
  private static final String DEFAULT_ARGUMENT = "{key,type,style{justification}}";

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

  @CliOption(name = OPTION_ARGUMENT, aliases = "-a", operand = "EXP", //
      required = false, usage = NlsBundleUtilCliRoot.MSG_BUNDLE_CONVERTER_USAGE_ARGUMENT)
  private String argument = DEFAULT_ARGUMENT;

  @CliOption(name = OPTION_EVAL_CHOICE, aliases = "-c", operand = "EVAL", //
      required = false, usage = NlsBundleUtilCliRoot.MSG_BUNDLE_CONVERTER_USAGE_EVAL_CHOICE)
  private ChoiceEvaluation evalChoice;

  private NlsResourceLocator resourceLocator;

  private NlsMessageFormatterFactory messageFormatterFactory;

  /**
   * The constructor.
   */
  public ResourceBundleConverter() {

    super();
    this.locales = null;
    this.format = Format.properties;
    this.pathExpression = "{packagePath}/{name}-{locale}";
    setPath("target/nls");
  }

  /**
   * @return the resourceLocator
   */
  public NlsResourceLocator getResourceLocator() {

    if (this.resourceLocator == null) {
      IocContainer iocContainer = getIocContainer();
      if (iocContainer != null) {
        this.resourceLocator = iocContainer.get(NlsResourceLocator.class);
      } else {
        DefaultNlsResourceLocator impl = new DefaultNlsResourceLocator();
        impl.initialize();
        this.resourceLocator = impl;
      }
    }
    return this.resourceLocator;
  }

  /**
   * @return the messageFormatterFactory
   */
  public NlsMessageFormatterFactory getMessageFormatterFactory() {

    if (this.messageFormatterFactory == null) {
      IocContainer iocContainer = getIocContainer();
      if (iocContainer != null) {
        this.messageFormatterFactory = iocContainer.get(NlsMessageFormatterFactory.class);
      } else {
        this.messageFormatterFactory = AbstractNlsDependencies.getInstance().getMessageFormatterFactory();
      }
    }
    return this.messageFormatterFactory;
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

  /**
   * @return the expression/pattern used to convert {@link net.sf.mmm.util.nls.api.NlsArgument}s.
   */
  public String getArgument() {

    return this.argument;
  }

  /**
   * @param argument the new value of {@link #getArgument()}.
   */
  public void setArgument(String argument) {

    this.argument = argument;
  }

  /**
   * @return the {@link ChoiceEvaluation} or {@code null} to leave unmodified.
   */
  public ChoiceEvaluation getEvalChoice() {

    return this.evalChoice;
  }

  /**
   * @param evalChoice the new value of {@link #getEvalChoice()}.
   */
  public void setEvalChoice(ChoiceEvaluation evalChoice) {

    this.evalChoice = evalChoice;
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
  protected List<String> getLocales(NlsBundleDescriptor bundle) {

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
  protected File getTargetFile(NlsBundleDescriptor bundle, String locale) {

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
   * @param bundle the {@link NlsBundleDescriptor}.
   * @param locale the {@link Locale}.
   * @return the {@link #getPathExpression() path expression} with all variables resolved.
   */
  protected String getPathExpressionResolved(NlsBundleDescriptor bundle, String locale) {

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
   * @param bundle the {@link NlsBundleDescriptor}.
   * @param locale the {@link Locale}.
   * @return a {@link Map} with the variables to {@link #getPathExpressionResolved(NlsBundleDescriptor, String)
   *         resolve}.
   */
  protected Map<String, String> getVariableMap(NlsBundleDescriptor bundle, String locale) {

    Map<String, String> variables = new HashMap<>();
    variables.put("packageName", bundle.getPackageName());
    variables.put("packagePath", bundle.getPackagePath());
    variables.put("name", bundle.getName());
    variables.put("locale", locale);
    return variables;
  }

  @Override
  protected int runDefaultMode() throws Exception {

    if ((this.evalChoice != null) && (this.argument == null)) {
      this.argument = DEFAULT_ARGUMENT;
    }
    return super.runDefaultMode();
  }

  @Override
  protected void synchronize(NlsBundleDescriptor bundle, String localeString, File targetFile, String date)
      throws IOException {

    Locale locale = getResourceLocator().getLocale(localeString);
    String qualifiedName = bundle.getQualifiedName();
    ResourceBundle resourceBundle = getBundleHelper().getResourceBundle(qualifiedName, locale);
    Properties properties = new SortedProperties();
    for (String key : bundle.getMessages().keySet()) {
      if ((this.keyPattern == null) || (this.keyPattern.matcher(key).matches())) {
        String message = resourceBundle.getString(key);
        message = convertMessage(message, key, bundle, localeString);
        properties.setProperty(key, message);
      } else {
        getLogger().debug("Ignoring key {} in bundle {} as it does not match key pattern {}", key, qualifiedName,
            this.keyPattern);
      }
    }
    this.format.write(properties, targetFile, getEncoding(), "Generated " + date);
  }

  private String convertMessage(String message, String key, NlsBundleDescriptor bundle, String locale) {

    try {
      final StringBuilder buffer = new StringBuilder(message.length());
      final NlsMessageDescriptor messageDescriptor = bundle.getMessage(key);
      final List<String> argumentList = new ArrayList<>();

      NlsMessageFormatter formatter = getMessageFormatterFactory().create(message);
      Visitor<String> textVisitor = new Visitor<String>() {
        @Override
        public void visit(String text) {

          buffer.append(text);
        }
      };
      Visitor<NlsArgument> argumentVisitor = new Visitor<NlsArgument>() {

        @Override
        public void visit(NlsArgument nlsArgument) {

          convertArgument(nlsArgument, buffer, messageDescriptor, argumentList);
        }
      };
      formatter.visit(textVisitor, argumentVisitor);
      String result = buffer.toString();
      return result;
    } catch (Exception e) {
      getLogger().warn("Malformed NLS message {}.{} for locale {}.", bundle.getQualifiedName(), key, locale, e);
      return message;
    }
  }

  private void convertArgument(NlsArgument nlsArgument, StringBuilder buffer,
      NlsMessageDescriptor messageDescriptor, List<String> argumentList) {

    NlsFormatterPlugin<?> formatter = nlsArgument.getFormatter();
    if ((this.evalChoice != null) && (formatter instanceof NlsFormatterChoice)) {
      convertArgumentChoice((NlsFormatterChoice) formatter, buffer, messageDescriptor, argumentList);
    } else {
      String result = this.argument;
      String key = nlsArgument.getKey();
      result = result.replace("key", key);
      result = convertArgumentFormatter(formatter, result);
      result = convertArgumentJustification(nlsArgument.getJustification(), result);
      result = convertArgumentIndex(messageDescriptor, argumentList, result, key);
      buffer.append(result);
    }
  }

  private void convertArgumentChoice(NlsFormatterChoice formatter, StringBuilder buffer,
      NlsMessageDescriptor messageDescriptor, List<String> argumentList) {

    Choice choice = extracted(formatter);
    if (choice == null) {
      return;
    }
    for (Segment segment : choice.getSegments()) {
      buffer.append(segment.getLiteral());
      NlsArgument nlsArgument = segment.getArgument();
      if (nlsArgument != null) {
        convertArgument(nlsArgument, buffer, messageDescriptor, argumentList);
      }
    }
  }

  private Choice extracted(NlsFormatterChoice formatter) {

    for (Choice choice : formatter.getChoices()) {
      switch (this.evalChoice) {
        case asElse:
          if (choice.isElse()) {
            return choice;
          }
          break;
        case asNull:
          if (choice.getCondition().accept(null)) {
            return choice;
          }
          break;
        default:
          throw new IllegalCaseException(ChoiceEvaluation.class, this.evalChoice);
      }
    }
    return null;
  }

  private String convertArgumentIndex(NlsMessageDescriptor messageDescriptor, List<String> argumentList,
      String result, String key) {

    int fallbackIndex = argumentList.indexOf(key);
    if (fallbackIndex == -1) {
      fallbackIndex = argumentList.size();
      argumentList.add(key);
    }
    int index = fallbackIndex;
    if (messageDescriptor != null) {
      NlsArgumentDescriptor argumentDescriptor = messageDescriptor.getArgument(key);
      if (argumentDescriptor != null) {
        index = argumentDescriptor.getIndex();
      }
    }
    return result.replace("index", Integer.toString(index));
  }

  private String convertArgumentFormatter(NlsFormatterPlugin<?> formatter, String arg) {

    String result = arg;
    result = convertSegment(result, "type", formatter.getType());
    result = convertSegment(result, "style", formatter.getStyle());
    return result;
  }

  private String convertSegment(String result, String segment, String type) {

    String t = type;
    if (type == null) {
      t = "";
    } else {
      t = "," + type;
    }
    return result.replace("," + segment, t);
  }

  private String convertArgumentJustification(Justification justification, String result) {

    String justify = "";
    if (justification != null) {
      justify = "{" + justification.toString() + "}";
    }
    return result.replace("{justification}", justify);
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
