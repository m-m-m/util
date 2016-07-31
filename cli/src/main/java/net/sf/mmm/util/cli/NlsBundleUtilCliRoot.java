/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface NlsBundleUtilCliRoot extends NlsBundle {

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String INF_MAIN_MODE_HELP = "help";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String INF_MAIN_MODE_VERSION = "version";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String INF_MAIN_MODE_DEFAULT = "default";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String MSG_MAIN_OPTION_HELP_USAGE = "Print this help.";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String MSG_MAIN_MODE_HELP_USAGE = "Print help about this program.";

  /** @see net.sf.mmm.util.cli.api.AbstractVersionedMain */
  String MSG_MAIN_OPTION_VERSION_USAGE = "Print the program-version.";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String MSG_MAIN_MODE_VERSION_USAGE = "Print the version of this program.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE = "Create and/or update resource-bundle property-files.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_MODE_DEFAULT = "Create and/or "
      + "update resource-bundle property-files from <bundle-class> for the given "
      + "locales (including the root locale). Example:\n\n"
      + "{mainClass} --bundle-class foo.bar.NlsBundleMyExample --locale de de_DE en en_US en_GB fr zh ja_JP zh_TW\n\n"
      + "For each locale a property-file foo/bar/NlsBundleMyExample_<locale>.properties will be created or updated "
      + "in the base-path. In each property-file all properties defined in <bundle-class> will be added with a "
      + "TODO-marker and the original text as value. If the property-file already exists, all existing properties "
      + "will remain unchanged and comments will be kept.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleConverter */
  String MSG_BUNDLE_CONVERTER_USAGE = "Converts resource-bundle property-files to different formats.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleConverter */
  String MSG_BUNDLE_CONVERTER_USAGE_MODE_DEFAULT = "Convert resource-bundle property-files from <bundle-class> for "
      + "all available or the explicitly given locales. Example:\n\n"
      + "{mainClass} --bundle-class foo.bar.NlsBundleMyExample \n\n"
      + "For each locale where a localization exists in property-file foo/bar/NlsBundleMyExample_<locale>.properties "
      + "an according converted file is created in <path>. Already existing files will be overridden.";

  /** @see #messageBundleConverterUsageKeyPattern(Object, Object) */
  String MSG_BUNDLE_CONVERTER_USAGE_KEY_PATTERN = "Use {operand} as regular expression to filter the resource "
      + "bundle keys (e.g. \"label.*\").";

  /** @see #messageBundleConverterUsageFormat(Object, Object) */
  String MSG_BUNDLE_CONVERTER_USAGE_FORMAT = "Write the converted output in the specified format {operand} (Default is \"{default}\").";

  /** @see #messageBundleConverterUsagePathExpression(Object, Object) */
  String MSG_BUNDLE_CONVERTER_USAGE_PATH_EXPRESSION = "Use the expression {operand} for the path of the converted output "
      + "file including filename relative to the output path (Default is \"{default}\").";

  /** @see #messageBundleConverterUsageArgument(Object, Object) */
  String MSG_BUNDLE_CONVERTER_USAGE_ARGUMENT = "Convert the arguments of the messages to the format {operand} (e.g. \"\\{{key}}\" "
      + "or \"\\{index,format}\"). Can be used to convert the syntax for other NLS frameworks such as angular-translate "
      + "(Default is \"{default}\").";

  /** @see #messageBundleConverterUsageEvalChoice(Object, Object) */
  String MSG_BUNDLE_CONVERTER_USAGE_EVAL_CHOICE = "Simplify and resolve CHOICE format expressions using {operand}.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_LOCALES = "The list of locales "
      + "to process. Each locale has to be in the form \"ll[_CC[_vv]]\" where "
      + "\"ll\" is the lowercase ISO 639 code, CC is the uppercase ISO 3166 "
      + "2-letter code and vv is an arbitrary variant. Examples are \"de\", " + "\"en_US\" or \"th_TH_TH\".";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_ENCODING = "Read and write "
      + "property-files using the specified encoding {operand} (Default is {default}).";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_PATH = "Write property-files "
      + "to the base-path {operand} (Default is \"{default}\").";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_DATE_PATTERN = "Use the specified "
      + "date pattern {operand} for writing synchronization date to property-files (Default is \"{default}\").";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_BUNDLE_CLASS = "The explicit "
      + "list of bundle-classes for which the property-files should be created or updated. "
      + "It has to be the fully qualified name of a subclass of AbstractResourceBundle. "
      + "For all given locales the according property-file is created or updated. "
      + "If this option is omitted the bundle-classes are resolved from all instances of "
      + NlsTemplateResolver.CLASSPATH_NLS_BUNDLE + " on your classpath.";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_USAGE = "Usage: {mainClass} {option}";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_MODE_USAGE = "Mode {mode}:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_REQUIRED_OPTIONS = "Required options:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_ADDITIONAL_OPTIONS = "Additional options:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_ARGUMENTS = "Arguments:";

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_MAIN_MODE_HELP)
  NlsMessage infoMainModeHelp();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_MAIN_MODE_VERSION)
  NlsMessage infoMainModeVersion();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_MAIN_MODE_DEFAULT)
  NlsMessage infoMainModeDefault();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_MAIN_OPTION_HELP_USAGE)
  NlsMessage messageMainOptionHelpUsage();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_MAIN_MODE_HELP_USAGE)
  NlsMessage messageMainModeHelpUsage();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractVersionedMain
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_MAIN_OPTION_VERSION_USAGE)
  NlsMessage messageMainOptionVersionUsage();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_MAIN_MODE_VERSION_USAGE)
  NlsMessage messageMainModeVersionUsage();

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE)
  NlsMessage messageSynchronizerUsage();

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   *
   * @param mainClass is the {@link Class} with the main-method.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_MODE_DEFAULT)
  NlsMessage messageSynchronizerUsageModeDefault(@Named("mainClass") Object mainClass);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_LOCALES)
  NlsMessage messageSynchronizerUsageLocales();

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   *
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_ENCODING)
  NlsMessage messageSynchronizerUsageEncoding(@Named("operand") Object operand,
      @Named("defaultValue") Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   *
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_PATH)
  NlsMessage messageSynchronizerUsagePath(@Named("operand") Object operand,
      @Named("defaultValue") Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   *
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_DATE_PATTERN)
  NlsMessage messageSynchronizerUsageDatePattern(@Named("operand") Object operand,
      @Named("defaultValue") Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_BUNDLE_CLASS)
  NlsMessage messageSynchronizerUsageBundleClass();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_USAGE)
  NlsMessage messageCliUsage();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_MODE_USAGE)
  NlsMessage messageCliModeUsage();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_REQUIRED_OPTIONS)
  NlsMessage messageCliRequiredOptions();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_ADDITIONAL_OPTIONS)
  NlsMessage messageCliAdditionalOptions();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_ARGUMENTS)
  NlsMessage messageCliArguments();

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionDuplicateException
   *
   * @param option is the duplicated option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate option \"{option}\"!")
  NlsMessage errorCliOptionDuplicate(@Named("option") String option);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionUndefinedException
   *
   * @param option is the undefined option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Undefined option \"{option}\"!")
  NlsMessage errorCliOptionUndefined(@Named("option") String option);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionMissingValueException
   *
   * @param option is the option with missing value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The option \"{option}\" must be followed by a value!")
  NlsMessage errorCliOptionMissingValue(@Named("option") String option);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionMissingException
   *
   * @param option is the missing but required option.
   * @param mode is the current mode.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The option \"{option}\" is required for mode \"{mode}\"!")
  NlsMessage errorCliOptionMissing(@Named("option") String option, @Named("mode") String mode);

  /**
   * @see net.sf.mmm.util.cli.api.CliArgumentMissingException
   *
   * @param argument is the missing but required argument.
   * @param mode is the current mode.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The argument \"{argument}\" is required for mode \"{mode}\"!")
  NlsMessage errorCliArgumentMissing(@Named("argument") String argument, @Named("mode") String mode);

  /**
   * @see net.sf.mmm.util.cli.api.CliArgumentReferenceMissingException
   *
   * @param reference is the missing reference.
   * @param argument is the argument containing the reference.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The argument \"{argument}\" referenced by \"{reference}\" is not defined!")
  NlsMessage errorCliArgumentReferenceMissing(@Named("reference") Object reference,
      @Named("argument") Object argument);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionMisplacedException
   *
   * @param option is the misplaced option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The option \"{option}\" is misplaced and can not be given after the start of the arguments!")
  NlsMessage errorCliOptionMisplaced(@Named("option") String option);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionAndArgumentAnnotationException
   *
   * @param property is the name of the annotated property.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The property \"{property}\" can not be annotated both with @CliOption and @CliArgument!")
  NlsMessage errorCliOptionAndArgumentAnnotation(@Named("property") String property);

  /**
   * @see net.sf.mmm.util.cli.api.CliParameterListEmptyException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("No parameter given! You have to supply at least one commandline parameter.")
  NlsMessage errorCliParameterListEmpty();

  /**
   * @see net.sf.mmm.util.cli.api.CliClassNoPropertyException
   *
   * @param type is the CLI {@link Class}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The CLI class \"{type}\" "
      + "is illegal because it has no property annotated with @CliOption or @CliArgument!")
  NlsMessage errorCliClassNoProperty(@Named("type") Object type);

  /**
   * @see net.sf.mmm.util.cli.api.CliModeUndefinedException
   *
   * @param mode is the undefined mode.
   * @param value is the referencing object.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The mode \"{mode}\" used by \"{value}\" is undefined! You have to declare it via @CliMode or "
      + "change @CliStyle.modeUndefined() to something else than EXCEPTION.")
  NlsMessage errorCliModeUndefined(@Named("mode") String mode, @Named("value") Object value);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionIllegalNameOrAliasException
   *
   * @param option is the actual option.
   * @param name is the illegal name or alias.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The name or alias \"{name}\" of \"{option}\" is illegal!")
  NlsMessage errorCliOptionNameIllegal(@Named("option") Object option, @Named("name") Object name);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionIncompatibleModesException
   *
   * @param option1 is the first incompatible option.
   * @param option2 is the second incompatible option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The options \"{option1}\" and \"{option2}\" have incompatible modes and can not be mixed!")
  NlsMessage errorCliOptionIncompatibleModes(@Named("option1") Object option1, @Named("option2") Object option2);

  /**
   * @see net.sf.mmm.util.cli.api.CliParserExcepiton
   *
   * @param type is the type of the invalid state.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The class \"{type}\" is invalid as command-line interface state-object!")
  NlsMessage errorCliParser(@Named("type") Object type);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleConverter
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_BUNDLE_CONVERTER_USAGE)
  NlsMessage messageBundleConverterUsage();

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleConverter
   *
   * @param mainClass is the {@link Class} with the main-method.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_BUNDLE_CONVERTER_USAGE_MODE_DEFAULT)
  NlsMessage messageBundleConverterUsageModeDefault(@Named("mainClass") Object mainClass);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleConverter#getKeyPattern()
   *
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_BUNDLE_CONVERTER_USAGE_KEY_PATTERN)
  NlsMessage messageBundleConverterUsageKeyPattern(@Named("operand") Object operand,
      @Named("defaultValue") Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleConverter#getFormat()
   *
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_BUNDLE_CONVERTER_USAGE_FORMAT)
  NlsMessage messageBundleConverterUsageFormat(@Named("operand") Object operand,
      @Named("defaultValue") Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleConverter#getPathExpression()
   *
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_BUNDLE_CONVERTER_USAGE_PATH_EXPRESSION)
  NlsMessage messageBundleConverterUsagePathExpression(@Named("operand") Object operand,
      @Named("defaultValue") Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleConverter#getArgument()
   *
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_BUNDLE_CONVERTER_USAGE_ARGUMENT)
  NlsMessage messageBundleConverterUsageArgument(@Named("operand") Object operand,
      @Named("defaultValue") Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleConverter#getEvalChoice()
   *
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_BUNDLE_CONVERTER_USAGE_EVAL_CHOICE)
  NlsMessage messageBundleConverterUsageEvalChoice(@Named("operand") Object operand,
      @Named("defaultValue") Object defaultValue);

}
