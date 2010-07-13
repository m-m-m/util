/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliArgumentMissingException;
import net.sf.mmm.util.cli.api.CliException;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliOptionIncompatibleModesException;
import net.sf.mmm.util.cli.api.CliOptionMissingException;
import net.sf.mmm.util.cli.api.CliOptionMissingValueException;
import net.sf.mmm.util.cli.api.CliOptionUndefinedException;
import net.sf.mmm.util.cli.api.CliOutputSettings;
import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.api.TextColumnInfo;
import net.sf.mmm.util.text.api.TextTableInfo;

/**
 * This is the abstract base-implementation of the {@link CliParser} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractCliParser extends AbstractLoggable implements CliParser {

  /**
   * The {@link Pattern} for a mix of multiple short-options. E.g. "-vpa"
   * instead of "-v", "-p" and "-a".
   */
  private static final Pattern PATTERN_MIXED_SHORT_OPTIONS = Pattern.compile("-([a-zA-Z0-9]+)");

  /** @see #getCliState() */
  private final CliState cliState;

  /** @see #getConfiguration() */
  private final CliParserConfiguration configuration;

  /** The {@link CliValueMap}. */
  private final CliValueMap valueMap;

  /** @see #getState() */
  private final Object state;

  /**
   * The constructor.
   * 
   * @param state is the {@link #getState() state}.
   * @param cliState is the {@link CliState}.
   * @param configuration is the {@link #getConfiguration() configuration} with
   *        the required components.
   */
  public AbstractCliParser(Object state, CliState cliState, CliParserConfiguration configuration) {

    super();
    this.state = state;
    this.cliState = cliState;
    this.configuration = configuration;
    this.valueMap = new CliValueMap(configuration);
    for (CliOptionContainer option : this.cliState.getOptions()) {
      checkOption(option);
    }
    initialize();
  }

  /**
   * This method checks a {@link CliOption} for correctness.
   * 
   * @param optionContainer is the {@link CliOptionContainer}.
   */
  protected void checkOption(CliOptionContainer optionContainer) {

    CliOption option = optionContainer.getOption();
    String mode = option.mode();
    if (this.cliState.getMode(mode) == null) {
      handleUndefinedMode(optionContainer);
    }

    checkOptionName(option.name(), optionContainer);
    for (String alias : option.aliases()) {
      checkOptionName(alias, optionContainer);
    }
  }

  /**
   * This method checks the given <code>name</code> of the given
   * {@link CliOptionContainer option}.
   * 
   * @param name is the {@link CliOption#name() name} or
   *        {@link CliOption#aliases() alias}.
   * @param optionContainer is the {@link CliOptionContainer}.
   */
  protected void checkOptionName(String name, CliOptionContainer optionContainer) {

  }

  /**
   * This method handles a {@link CliOption} with an
   * {@link CliState#getMode(String) undefined} {@link CliOption#mode()}.
   * 
   * @param option is the {@link CliOptionContainer}.
   */
  protected void handleUndefinedMode(CliOptionContainer option) {

    CliStyle style = this.cliState.getCliStyle();
    CliStyleHandling handling = style.modeUndefined();
    if (handling != CliStyleHandling.OK) {
      ObjectNotFoundException exception = new ObjectNotFoundException(CliMode.class, option
          .getOption().mode());
      handling.handle(getLogger(), exception);
    }
  }

  /**
   * This method gets the {@link CliState}.
   * 
   * @return the {@link CliState}.
   */
  protected CliState getCliState() {

    return this.cliState;
  }

  /**
   * This method gets the instance of the state where the command-line arguments
   * should be applied to. The class of this object should be properly
   * annotated.
   * 
   * @see net.sf.mmm.util.cli.api.CliClass
   * 
   * @return the state.
   */
  public Object getState() {

    return this.state;
  }

  /**
   * This method parses the value of a {@link CliOption}.
   * 
   * @param parserState is the {@link CliParserState}.
   * @param option is the command-line parameter that triggered the given
   *        <code>optionContainer</code>.
   * @param optionContainer is the {@link CliOptionContainer} for the current
   *        option that has already been detected.
   * @param parameterConsumer is the {@link CliParameterConsumer}.
   */
  protected void parseOption(CliParserState parserState, String option,
      CliOptionContainer optionContainer, CliParameterConsumer parameterConsumer) {

    CliValueContainer valueContainer = this.valueMap.getOrCreate(optionContainer);
    PojoPropertyAccessorOneArg setter = optionContainer.getSetter();
    Class<?> propertyClass = setter.getPropertyClass();
    String argument;
    if (optionContainer.isTrigger()) {
      argument = StringUtil.TRUE;
    } else if (Boolean.class.equals(propertyClass)) {
      String lookahead = parameterConsumer.getCurrent();
      Boolean value = this.configuration.getStringUtil().parseBoolean(lookahead);
      if (value == null) {
        // option (e.g. "--trigger") is not followed by "true" or "false"
        CliStyleHandling handling = this.cliState.getCliStyle().optionMissingBooleanValue();
        if (handling != CliStyleHandling.OK) {
          handling.handle(getLogger(), new CliOptionMissingValueException(option));
        }
        argument = StringUtil.TRUE;
      } else {
        // getNext() == lookahead
        argument = parameterConsumer.getNext();
      }
    } else {
      if (!parameterConsumer.hasNext()) {
        throw new CliOptionMissingValueException(option);
      }
      argument = parameterConsumer.getNext();
    }
    valueContainer.setValue(argument, optionContainer, this.cliState.getCliStyle(),
        this.configuration, getLogger());
  }

  /**
   * This method parses the value of a {@link CliOption}.
   * 
   * @param parserState is the {@link CliParserState}.
   * @param argument is the commandline parameter.
   * @param argumentContainer is the {@link CliArgumentContainer} for the
   *        current argument.
   * @param parameterConsumer is the {@link CliParameterConsumer}.
   */
  protected void parseArgument(CliParserState parserState, String argument,
      CliArgumentContainer argumentContainer, CliParameterConsumer parameterConsumer) {

    CliValueContainer valueContainer = this.valueMap.getOrCreate(argumentContainer);
    valueContainer.setValue(argument, argumentContainer, this.cliState.getCliStyle(),
        this.configuration, getLogger());
    if (!valueContainer.isArrayMapOrCollection()) {
      parserState.argumentIndex++;
    }
  }

  /**
   * {@inheritDoc}
   */
  public CliModeObject parseParameters(String... parameters) throws CliException {

    // if ((parameters == null) || (parameters.length == 0)) {
    // throw new CliParameterListEmptyException();
    // }
    CliParameterConsumer parameterConsumer = new CliParameterConsumer(parameters);
    CliParserState parserState = new CliParserState();
    while (parameterConsumer.hasNext()) {
      String arg = parameterConsumer.getNext();
      parseParameter(arg, parserState, parameterConsumer);
    }
    if (parserState.currentMode == null) {
      // just in case no argument was given at all...
      parserState.currentMode = this.cliState.getMode(CliMode.MODE_DEFAULT);
      if (parserState.currentMode == null) {
        parserState.currentMode = new CliModeContainer(CliMode.MODE_DEFAULT);
      }
    }
    checkRequiredParameters(parserState);
    this.valueMap.assign(getState());
    return parserState.currentMode;
  }

  /**
   * This method checks that all {@link CliOption#required() required}
   * {@link CliOption options} are present if they are triggered by the
   * {@link CliParserState#getCurrentMode() current mode}.
   * 
   * @param parserState is the current {@link CliParserState}.
   */
  protected void checkRequiredParameters(CliParserState parserState) {

    CliModeObject mode = parserState.currentMode;
    // check all required options if active of not for current mode...
    for (CliOptionContainer option : this.cliState.getOptions(mode)) {
      CliOption cliOption = option.getOption();
      if (cliOption.required() && (this.valueMap.get(option) == null)) {
        throw new CliOptionMissingException(cliOption.name(), mode.getTitle());
      }
    }
    // check all required arguments if active or not for current mode...
    for (CliArgumentContainer argument : this.cliState.getArguments(mode)) {
      CliArgument cliArgument = argument.getArgument();
      if (cliArgument.required() && (this.valueMap.get(argument) == null)) {
        throw new CliArgumentMissingException(cliArgument.name(), mode.getTitle());
      }
    }
  }

  /**
   * This method parses a single command-line argument.
   * 
   * @param argument is the command-line argument.
   * @param parserState is the {@link CliParserState}.
   * @param parameterConsumer is the {@link CliParameterConsumer}.
   */
  protected void parseParameter(String argument, CliParserState parserState,
      CliParameterConsumer parameterConsumer) {

    if (!parserState.isOptionsComplete()) {
      CliOptionContainer optionContainer = this.cliState.getOption(argument);
      if (optionContainer != null) {
        // mode handling...
        String modeId = optionContainer.getOption().mode();
        CliModeObject newMode = this.cliState.getMode(modeId);
        if (newMode == null) {
          // should never happen!
          newMode = new CliModeContainer(modeId);
        }
        if (parserState.currentMode == null) {
          parserState.setCurrentMode(argument, newMode);
        } else if (!modeId.equals(parserState.currentMode.getId())) {
          // mode already detected, but mode of current option differs...
          if (newMode.getExtendedModes().contains(parserState.currentMode)) {
            // new mode extends current mode
            parserState.setCurrentMode(argument, newMode);
          } else if (!parserState.currentMode.getExtendedModes().contains(newMode)) {
            // current mode does NOT extend new mode and vice versa
            // --> incompatible modes
            throw new CliOptionIncompatibleModesException(parserState.modeOption, argument);
          }
        }

        parseOption(parserState, argument, optionContainer, parameterConsumer);
      } else {
        // no option found for argument...
        if (END_OPTIONS.equals(argument)) {
          parserState.setOptionsComplete();
        } else {
          CliStyleHandling handling = this.cliState.getCliStyle().optionMixedShortForm();
          if (handling != CliStyleHandling.EXCEPTION) {
            Matcher matcher = PATTERN_MIXED_SHORT_OPTIONS.matcher(argument);
            if (matcher.matches()) {
              // "-vlp" --> "-v", "-l", "-p"
              if (handling == CliStyleHandling.WARNING) {
                getLogger().warn("Mixed options (" + argument + ") should be avoided.");
              } else {
                assert (handling == CliStyleHandling.OK);
              }
              String multiOptions = matcher.group(1);
              for (int i = 0; i < multiOptions.length(); i++) {
                String subArgument = PREFIX_SHORT_OPTION + multiOptions.charAt(i);
                parseParameter(subArgument, parserState, CliParameterConsumer.EMPTY_INSTANCE);
              }
              return;
            }
          }
          if (argument.startsWith(PREFIX_SHORT_OPTION)) {
            throw new CliOptionUndefinedException(argument);
          }
          // seems to be the start of arguments
          parserState.setOptionsComplete();
        }
      }
    }
    if (parserState.isOptionsComplete()) {
      List<CliArgumentContainer> argumentList = this.cliState.getArguments(parserState.currentMode);
      int argumentIndex = parserState.getArgumentIndex();
      if (argumentIndex >= argumentList.size()) {
        // TODO: illegal argument, defined exception
        throw new NlsIllegalArgumentException(argument);
      } else {
        parseArgument(parserState, argument, argumentList.get(argumentIndex), parameterConsumer);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void printHelp(Appendable target) {

    printHelp(target, new CliOutputSettings());
  }

  /**
   * {@inheritDoc}
   */
  public void printHelp(Appendable appendable, CliOutputSettings settings) {

    NlsMessageFactory nlsMessageFactory = this.configuration.getNlsMessageFactory();

    CliHelpWriter writer = new CliHelpWriter(appendable, settings, this.configuration,
        this.cliState, this.state);

    Map<String, Object> nlsArguments = writer.getArguments();
    // TODO: NLS
    nlsArguments.put(NlsObject.KEY_OPTION, "[<option>*]");

    writer.printText(NlsBundleUtilCore.MSG_CLI_USAGE);

    String mainUsage = this.cliState.getCliClass().usage();
    if (mainUsage.length() > 0) {
      writer.printText(mainUsage);
    }
    Map<CliOption, CliOptionHelpInfo> option2HelpMap = new HashMap<CliOption, AbstractCliParser.CliOptionHelpInfo>();
    for (String modeId : this.cliState.getModeIds()) {
      writer.println();
      CliModeObject mode = this.cliState.getMode(modeId);
      CliMode cliMode = mode.getMode();
      if ((cliMode == null) || (!cliMode.isAbstract())) {
        NlsMessage modeMessage = nlsMessageFactory.create(mode.getTitle());
        nlsArguments.put(NlsObject.KEY_MODE, modeMessage);
        writer.printText(NlsBundleUtilCore.MSG_CLI_MODE_USAGE);
        StringBuilder parameters = new StringBuilder();
        Collection<CliOptionContainer> modeOptions = this.cliState.getOptions(mode);
        int maxOptionColumnWidth = 0;
        for (CliOptionContainer option : modeOptions) {
          CliOption cliOption = option.getOption();
          if (parameters.length() > 0) {
            parameters.append(" ");
          }
          if (!cliOption.required()) {
            parameters.append("[");
          }
          parameters.append(cliOption.name());
          if (!option.getSetter().getPropertyClass().equals(boolean.class)) {
            parameters.append(" ");
            parameters.append(cliOption.operand());
          }
          if (!cliOption.required()) {
            parameters.append("]");
          }
          CliOptionHelpInfo helpInfo = option2HelpMap.get(cliOption);
          if (helpInfo == null) {
            helpInfo = new CliOptionHelpInfo(option, this.configuration, settings);
            option2HelpMap.put(cliOption, helpInfo);
          }
          if (helpInfo.length > maxOptionColumnWidth) {
            maxOptionColumnWidth = helpInfo.length;
          }
        }
        List<CliArgumentContainer> argumentList = this.cliState.getArguments(mode);
        int maxArgumentColumnWidth = 0;
        List<CliArgumentHelpInfo> argumentHelpList;
        if (!argumentList.isEmpty()) {
          argumentHelpList = new ArrayList<AbstractCliParser.CliArgumentHelpInfo>(
              argumentList.size());
          boolean requiredArgument = true;
          CliArgument cliArgument = null;
          for (CliArgumentContainer argumentContainer : argumentList) {
            cliArgument = argumentContainer.getArgument();
            CliArgumentHelpInfo argumentHelpInfo = new CliArgumentHelpInfo(argumentContainer,
                this.configuration, settings);
            int argLength = argumentHelpInfo.name.length();
            if (argLength > maxArgumentColumnWidth) {
              maxArgumentColumnWidth = argLength;
            }
            parameters.append(" ");
            if (!cliArgument.required() && requiredArgument) {
              parameters.append("[");
              requiredArgument = false;
            }
            parameters.append("<");
            parameters.append(argumentHelpInfo.name);
            if (argumentContainer.isArrayMapOrCollection()) {
              parameters.append("...");
            }
            parameters.append(">");
            argumentHelpList.add(argumentHelpInfo);
          }
          if ((cliArgument != null) && !cliArgument.required()) {
            parameters.append("]");
          }
        } else {
          argumentHelpList = Collections.emptyList();
        }

        nlsArguments.put(NlsObject.KEY_OPTION, parameters);
        writer.printText(NlsBundleUtilCore.MSG_CLI_USAGE);
        writer.println();
        String optionUsage = "";
        if (cliMode != null) {
          optionUsage = cliMode.usage();
        }
        if (optionUsage.length() > 0) {
          writer.printText(optionUsage);
        }
        writer.printOptions(modeOptions, option2HelpMap, maxOptionColumnWidth);
        writer.printArguments(argumentHelpList, maxArgumentColumnWidth);
      }
      if (appendable instanceof Flushable) {
        try {
          ((Flushable) appendable).flush();
        } catch (Exception e) {
          throw new RuntimeIoException(e, IoMode.FLUSH);
        }
      }
    }
    nlsArguments.remove(NlsObject.KEY_MODE);
  }

  /**
   * @return the {@link CliParserConfiguration configuration} for this parser.
   */
  protected CliParserConfiguration getConfiguration() {

    return this.configuration;
  }

  /**
   * This inner class holds the state of the
   * {@link AbstractCliParser#parseParameters(String...) argument parsing}.
   */
  protected static class CliParserState {

    /** @see #getCurrentMode() */
    private CliModeObject currentMode;

    /** @see #getModeOption() */
    private String modeOption;

    /** @see #getArgumentIndex() */
    private int argumentIndex;

    /**
     * The constructor.
     */
    public CliParserState() {

      super();
      this.currentMode = null;
      this.modeOption = null;
      this.argumentIndex = -1;
    }

    /**
     * This method gets the current mode that was detected so far.
     * 
     * @return the currentMode or <code>null</code> if no mode has been
     *         detected, yet.
     */
    public CliModeObject getCurrentMode() {

      return this.currentMode;
    }

    /**
     * The command-line argument for the option, that activated the
     * {@link #getCurrentMode() current mode}.
     * 
     * @return the modeOption
     */
    public String getModeOption() {

      return this.modeOption;
    }

    /**
     * This method sets {@link #getCurrentMode() current mode} and
     * {@link #getModeOption() mode-option}.
     * 
     * @param option is the {@link #getModeOption() mode-option}.
     * @param mode is the {@link #getCurrentMode() current mode}.
     */
    public void setCurrentMode(String option, CliModeObject mode) {

      this.modeOption = option;
      this.currentMode = mode;
    }

    /**
     * This method determines if the {@link CliOption options} are completed and
     * further command-line parameters have to be
     * {@link net.sf.mmm.util.cli.api.CliArgument arguments}.
     * 
     * @see AbstractCliParser#END_OPTIONS
     * 
     * @return the optionsComplete
     */
    public boolean isOptionsComplete() {

      return (this.argumentIndex >= 0);
    }

    /**
     * This method gets the index of the
     * {@link CliState#getArguments(CliModeObject) current argument}.
     * 
     * @return the argumentIndex
     */
    public int getArgumentIndex() {

      return this.argumentIndex;
    }

    /**
     * This method sets the {@link #isOptionsComplete() options-complete flag}
     * to <code>true</code>.
     */
    public void setOptionsComplete() {

      this.argumentIndex = 0;
    }

  }

  /**
   * This inner class holds the help information for a single {@link CliOption}.
   */
  protected static class CliOptionHelpInfo {

    /** The actual option. */
    private final CliOptionContainer option;

    /** The syntax of the option. */
    private final String syntax;

    /**
     * {@link NlsMessage#getLocalizedMessage(java.util.Locale) Localized
     * message} for {@link CliOption#operand() operand}.
     */
    private final String operand;

    /** The maximum length of all syntax lines of this info. */
    private final int length;

    /** The current index in the buffer for {@link #syntax}. */
    private int lineIndex;

    /** The current maximum line index. */
    private int lineLength;

    /**
     * The constructor.
     * 
     * @param option is the {@link CliOptionContainer}.
     * @param configuration is the {@link CliParserConfiguration}.
     * @param settings are the {@link CliOutputSettings}.
     */
    public CliOptionHelpInfo(CliOptionContainer option, CliParserConfiguration configuration,
        CliOutputSettings settings) {

      super();
      this.option = option;
      StringBuilder syntaxBuilder = new StringBuilder();
      int maxLength = settings.getWidth() / 2 - 1;
      Locale locale = settings.getLocale();
      CliOption cliOption = this.option.getOption();
      NlsMessage operandMessage = configuration.getNlsMessageFactory().create(cliOption.operand());
      this.operand = operandMessage.getLocalizedMessage(locale,
          configuration.getNlsTemplateResolver());
      syntaxBuilder.append(cliOption.name());
      this.lineLength = syntaxBuilder.length();
      // this.lineIndex = this.lineLength;
      String[] aliases = cliOption.aliases();
      for (int i = 0; i < aliases.length; i++) {
        String prefix;
        if (i == 0) {
          prefix = " (";
        } else {
          prefix = " ";
        }
        String alias = prefix + aliases[i];
        if (i == (aliases.length - 1)) {
          alias = alias + ")";
        }
        append(syntaxBuilder, alias, maxLength, settings);
      }
      if (!option.isTrigger()) {
        append(syntaxBuilder, " " + this.operand, maxLength, settings);
      }
      // length of last line...
      int len = syntaxBuilder.length() - this.lineIndex;
      if (len > this.lineLength) {
        this.lineLength = len;
      }
      this.length = this.lineLength;
      this.syntax = syntaxBuilder.toString();
    }

    /**
     * This method appends a single option to the syntax. It automatically wraps
     * and updates {@link #lineLength} and {@link #lineIndex}.
     * 
     * @param syntaxBuilder is the {@link StringBuilder buffer} used to build
     *        the {@link #getSyntax() syntax}.
     * @param text is the text to append.
     * @param maxLength is the maximum length allowed for a single line.
     * @param settings are the {@link CliOutputSettings}.
     */
    private void append(StringBuilder syntaxBuilder, String text, int maxLength,
        CliOutputSettings settings) {

      syntaxBuilder.append(text);

      // int len = syntaxBuilder.length() - this.lineIndex;
      // int newLen = len + text.length();
      // if (newLen < maxLength) {
      // syntaxBuilder.append(text);
      // } else {
      // if (len > this.lineLength) {
      // this.lineLength = len;
      // }
      // syntaxBuilder.append(settings.getLineSeparator());
      // this.lineIndex = syntaxBuilder.length();
      // syntaxBuilder.append("  ");
      // syntaxBuilder.append(text);
      // }
    }

    /**
     * @return the length
     */
    public int getLength() {

      return this.length;
    }

    /**
     * @return the syntax
     */
    public String getSyntax() {

      return this.syntax;
    }

    /**
     * @return the operand
     */
    public String getOperand() {

      return this.operand;
    }

  }

  /**
   * This inner class holds the help information for a single
   * {@link CliArgument}.
   */
  public static class CliArgumentHelpInfo {

    /** @see #getArgument() */
    private final CliArgumentContainer argument;

    /** @see #getName() */
    private final String name;

    /**
     * The constructor.
     * 
     * @param argument is the {@link CliArgumentContainer}.
     * @param configuration is the {@link CliParserConfiguration}.
     * @param settings are the {@link CliOutputSettings}.
     */
    public CliArgumentHelpInfo(CliArgumentContainer argument, CliParserConfiguration configuration,
        CliOutputSettings settings) {

      super();
      this.argument = argument;
      NlsMessage message = configuration.getNlsMessageFactory().create(
          argument.getArgument().name());
      this.name = message.getLocalizedMessage(settings.getLocale(),
          configuration.getNlsTemplateResolver());
    }

    /**
     * This method gets the {@link CliArgumentContainer}.
     * 
     * @return the {@link CliArgumentContainer}.
     */
    public CliArgumentContainer getArgument() {

      return this.argument;
    }

    /**
     * This method gets the name of the {@link CliArgument argument}.
     * 
     * @return the {@link NlsMessage#getLocalizedMessage(java.util.Locale)
     *         localized message} for the {@link CliArgument#name()
     *         argument-name}.
     */
    public String getName() {

      return this.name;
    }
  }

  /**
   * This inner class is a helper to simplify writing the help-usage.
   */
  public static class CliHelpWriter {

    /** The {@link Appendable} where to write help to. */
    private final Appendable appendable;

    /** The {@link TextTableInfo}. */
    private final TextTableInfo tableInfo;

    /** The {@link TextColumnInfo} for parameters (e.g. "--help (-h)"). */
    private final TextColumnInfo parameterColumnInfo;

    /** The {@link TextColumnInfo} for the main column. */
    private final TextColumnInfo mainColumnInfo;

    /** The {@link CliParserConfiguration}. */
    private final CliParserConfiguration configuration;

    /** The NLS-arguments. */
    private final Map<String, Object> arguments;

    /** The {@link AbstractCliParser#getState() state-object}. */
    private final Object state;

    /**
     * The constructor.
     * 
     * @param appendable is the {@link Appendable} where to write help to.
     * @param settings is the {@link CliOutputSettings}.
     * @param configuration is the {@link CliParserConfiguration}.
     * @param cliState is the {@link CliState}.
     * @param state is the {@link AbstractCliParser#getState() state-object}.
     */
    public CliHelpWriter(Appendable appendable, CliOutputSettings settings,
        CliParserConfiguration configuration, CliState cliState, Object state) {

      super();
      this.appendable = appendable;
      this.state = state;

      String lineSeparator = settings.getLineSeparator();
      this.tableInfo = new TextTableInfo();
      this.tableInfo.setWidth(settings.getWidth());
      this.tableInfo.setLineSeparator(lineSeparator);

      this.parameterColumnInfo = new TextColumnInfo();
      // Locale = US?
      this.parameterColumnInfo.setLocale(settings.getLocale());
      this.parameterColumnInfo.setIndent("  ");

      this.mainColumnInfo = new TextColumnInfo();
      this.parameterColumnInfo.setLocale(settings.getLocale());
      this.parameterColumnInfo.setIndent("  ");
      this.parameterColumnInfo.setBorderRight("  ");

      this.arguments = new HashMap<String, Object>();
      this.arguments.put("mainClass", cliState.getName());
      this.arguments.put("optionCount", Integer.valueOf(cliState.getOptions().size()));
      this.arguments.put("argumentCount", Integer.valueOf(cliState.getArguments().size()));

      this.configuration = configuration;
    }

    /**
     * @return the arguments
     */
    public Map<String, Object> getArguments() {

      return this.arguments;
    }

    /**
     * This method performs localization of the given NLS-text (see
     * {@link net.sf.mmm.util.nls.base.AbstractResourceBundle}) and performs
     * {@link LineWrapper line-wrapping} while writing it to the
     * {@link Appendable} for help-usage output.
     * 
     * @param nlsText is the internationalized text to print.
     */
    public void printText(String nlsText) {

      LineWrapper lineWrapper = this.configuration.getLineWrapper();
      NlsMessage message = this.configuration.getNlsMessageFactory()
          .create(nlsText, this.arguments);
      String text = message.getLocalizedMessage(this.mainColumnInfo.getLocale());
      lineWrapper.wrap(this.appendable, this.tableInfo, text, this.mainColumnInfo);
    }

    /**
     * This method prints the help for the {@link CliArgument arguments} given
     * by <code>argumentList</code>. It prints them with localized usage texts
     * in a two column-layout via {@link LineWrapper}.
     * 
     * @param argumentList is the {@link List} with the according
     *        {@link CliArgumentHelpInfo help infos}.
     * @param maxArgumentColumnWidth is the maximum width of the argument-name
     *        column.
     */
    public void printArguments(List<CliArgumentHelpInfo> argumentList, int maxArgumentColumnWidth) {

      if (!argumentList.isEmpty()) {
        printText(NlsBundleUtilCore.MSG_CLI_ARGUMENTS);
        this.parameterColumnInfo.setWidth(maxArgumentColumnWidth);
        LineWrapper lineWrapper = this.configuration.getLineWrapper();

        for (CliArgumentHelpInfo helpInfo : argumentList) {
          CliArgumentContainer argumentContainer = helpInfo.argument;
          CliArgument cliArgument = argumentContainer.getArgument();
          Object defaultValue = null;
          if (argumentContainer.getGetter() != null) {
            defaultValue = argumentContainer.getGetter().invoke(this.state);
          }
          this.arguments.put(NlsObject.KEY_DEFAULT, defaultValue);
          NlsMessage usageMessage = this.configuration.getNlsMessageFactory().create(
              cliArgument.usage(), this.arguments);
          String usageText = usageMessage.getLocalizedMessage(this.mainColumnInfo.getLocale());

          lineWrapper.wrap(this.appendable, this.tableInfo, helpInfo.name,
              this.parameterColumnInfo, usageText, this.mainColumnInfo);
        }
      }
    }

    /**
     * This method prints the help for the {@link CliOption options} given by
     * <code>modeOptions</code>. It prints them with localized usage texts in a
     * two column-layout via {@link LineWrapper}.
     * 
     * @param modeOptions is the {@link Collection} with the options to print.
     * @param option2HelpMap is the {@link Map} with the according
     *        {@link CliOptionHelpInfo help infos}.
     * @param maxOptionColumnWidth is the maximum width of the option-syntax
     *        column.
     */
    public void printOptions(Collection<CliOptionContainer> modeOptions,
        Map<CliOption, CliOptionHelpInfo> option2HelpMap, int maxOptionColumnWidth) {

      this.parameterColumnInfo.setWidth(maxOptionColumnWidth);
      println();
      printOptions(modeOptions, option2HelpMap, true);
      printOptions(modeOptions, option2HelpMap, false);
    }

    /**
     * This method is like {@link #printOptions(Collection, Map, int)} but only
     * prints required or additional options.
     * 
     * @param modeOptions is the {@link Collection} with the options to print.
     * @param option2HelpMap is the {@link Map} with the according
     *        {@link CliOptionHelpInfo help infos}.
     * @param required - <code>true</code> if required options should be
     *        printed, <code>false</code> if additional options should be
     *        printed.
     */
    private void printOptions(Collection<CliOptionContainer> modeOptions,
        Map<CliOption, CliOptionHelpInfo> option2HelpMap, boolean required) {

      LineWrapper lineWrapper = this.configuration.getLineWrapper();
      // required options
      boolean firstOption = true;
      for (CliOptionContainer option : modeOptions) {
        CliOption cliOption = option.getOption();
        if (cliOption.required() == required) {
          if (firstOption) {
            String nlsText;
            if (required) {
              nlsText = NlsBundleUtilCore.MSG_CLI_REQUIRED_OPTIONS;
            } else {
              nlsText = NlsBundleUtilCore.MSG_CLI_ADDITIONAL_OPTIONS;
            }
            printText(nlsText);
            firstOption = false;
          }
          CliOptionHelpInfo helpInfo = option2HelpMap.get(cliOption);
          this.arguments.put(NlsObject.KEY_OPERAND, helpInfo.operand);
          Object defaultValue = null;
          if (option.getGetter() != null) {
            defaultValue = option.getGetter().invoke(this.state);
          }
          this.arguments.put(NlsObject.KEY_DEFAULT, defaultValue);
          NlsMessage usageMessage = this.configuration.getNlsMessageFactory().create(
              cliOption.usage(), this.arguments);
          String usageText = usageMessage.getLocalizedMessage(this.mainColumnInfo.getLocale());

          lineWrapper.wrap(this.appendable, this.tableInfo, helpInfo.syntax,
              this.parameterColumnInfo, usageText, this.mainColumnInfo);
        }
      }
      if (!firstOption) {
        println();
      }
    }

    /**
     * Prints a newline (terminates current line).
     */
    public void println() {

      try {
        this.appendable.append(this.tableInfo.getLineSeparator());
      } catch (IOException e) {
        throw new RuntimeIoException(e, IoMode.WRITE);
      }
    }

  }

}
