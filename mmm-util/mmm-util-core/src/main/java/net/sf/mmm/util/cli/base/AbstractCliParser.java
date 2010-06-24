/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.cli.api.CliArgument;
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
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.nls.base.NlsWriter;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;

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
    if (boolean.class.equals(propertyClass)) {
      // trigger
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
   * @param argumentContainer is the {@link CliArgumentContainer} for the
   *        current argument.
   * @param parameterConsumer is the {@link CliParameterConsumer}.
   */
  protected void parseArgument(CliParserState parserState, CliArgumentContainer argumentContainer,
      CliParameterConsumer parameterConsumer) {

    CliArgument cliArgument = argumentContainer.getArgument();

  }

  /**
   * {@inheritDoc}
   */
  public CliModeObject parseParameters(String... parameters) throws CliException {

    CliParameterConsumer parameterConsumer = new CliParameterConsumer(parameters);
    CliParserState parserState = new CliParserState();
    while (parameterConsumer.hasNext()) {
      String arg = parameterConsumer.getNext();
      parseParameter(arg, parserState, parameterConsumer);
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
      // if (!parserState.optionSet.contains(cliOption) && cliOption.required())
      // {
      if ((this.valueMap.get(option) == null) && cliOption.required()) {
        throw new CliOptionMissingException(cliOption.name(), mode.getId());
      }
    }
  }

  /**
   * This method parses a single command-line argument.
   * 
   * @param argument is the command-line argument.
   * @param parserState is the {@link CliParserState}.
   * @param argumentConsumer is the {@link CliParameterConsumer}.
   */
  protected void parseParameter(String argument, CliParserState parserState,
      CliParameterConsumer argumentConsumer) {

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

        parseOption(parserState, argument, optionContainer, argumentConsumer);
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
        // illegal argument
      } else {
        // parseArgument();
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
  public void printHelp(Appendable target, CliOutputSettings settings) {

    NlsMessageFactory nlsMessageFactory = this.configuration.getNlsMessageFactory();
    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put("mainClass", this.cliState.getName());
    arguments.put("optionCount", Integer.valueOf(this.cliState.getOptions().size()));
    arguments.put("argumentCount", Integer.valueOf(this.cliState.getArguments().size()));
    // TODO: NLS
    arguments.put(NlsObject.KEY_OPTION, "[<option>*]");
    NlsWriter writer = new NlsWriter(target, arguments, settings.getLocale(), settings
        .getLineSeparator(), nlsMessageFactory, settings.getTemplateResolver());
    writer.println(NlsBundleUtilCore.MSG_CLI_USAGE);
    // this.cliState.getOptions();
    String mainUsage = this.cliState.getCliClass().usage();
    if (mainUsage.length() > 0) {
      writer.println(mainUsage);
    }
    Map<CliOption, CliOptionHelpInfo> option2HelpMap = new HashMap<CliOption, AbstractCliParser.CliOptionHelpInfo>();
    for (String modeId : this.cliState.getModeIds()) {
      writer.println();
      CliModeObject mode = this.cliState.getMode(modeId);
      CliMode cliMode = mode.getMode();
      if ((cliMode == null) || (!cliMode.isAbstract())) {
        NlsMessage modeMessage = nlsMessageFactory.create(mode.getMode().title());
        arguments.put(NlsObject.KEY_MODE, modeMessage);
        writer.println(NlsBundleUtilCore.MSG_CLI_MODE_USAGE);
        StringBuilder options = new StringBuilder();
        Collection<CliOptionContainer> modeOptions = this.cliState.getOptions(mode);
        int maxOptionColumnWidth = 0;
        for (CliOptionContainer option : modeOptions) {
          CliOption cliOption = option.getOption();
          if (options.length() > 0) {
            options.append(" ");
          }
          if (!cliOption.required()) {
            options.append("[");
          }
          options.append(cliOption.name());
          if (!cliOption.required()) {
            options.append("]");
          }
          CliOptionHelpInfo helpInfo = option2HelpMap.get(cliOption);
          if (helpInfo == null) {
            helpInfo = new CliOptionHelpInfo(option, nlsMessageFactory, settings);
            option2HelpMap.put(cliOption, helpInfo);
          }
          int len = helpInfo.getLength();
          if (len > maxOptionColumnWidth) {
            maxOptionColumnWidth = len;
          }
        }
        this.cliState.getOptions(mode);
        arguments.put(NlsObject.KEY_OPTION, options);
        writer.println(NlsBundleUtilCore.MSG_CLI_USAGE);
        String optionUsage = "";
        if (cliMode != null) {
          optionUsage = cliMode.usage();
        }
        if (optionUsage.length() > 0) {
          writer.println(optionUsage);
        }
        // required options
        boolean firstOption = true;
        for (CliOptionContainer option : modeOptions) {
          CliOption cliOption = option.getOption();
          if (cliOption.required()) {
            if (firstOption) {
              writer.println(NlsBundleUtilCore.MSG_CLI_REQUIRED_OPTIONS);
              firstOption = false;
            }
            CliOptionHelpInfo helpInfo = option2HelpMap.get(cliOption);
            helpInfo.print(writer, maxOptionColumnWidth + 1);
          }
        }

        // additional options
        firstOption = true;
        for (CliOptionContainer option : modeOptions) {
          CliOption cliOption = option.getOption();
          if (!cliOption.required()) {
            if (firstOption) {
              writer.println(NlsBundleUtilCore.MSG_CLI_ADDITIONAL_OPTIONS);
              firstOption = false;
            }
            CliOptionHelpInfo helpInfo = option2HelpMap.get(cliOption);
            helpInfo.print(writer, maxOptionColumnWidth + 1);
          }
        }
      }
    }
    arguments.remove(NlsObject.KEY_MODE);
  }

  protected void printOptions(Collection<CliOptionContainer> modeOptions, NlsWriter writer,
      Map<CliOption, CliOptionHelpInfo> option2HelpMap, int maxOptionColumnWidth) {

    boolean firstOption = true;
    for (CliOptionContainer option : modeOptions) {
      CliOption cliOption = option.getOption();
      if (cliOption.required()) {
        if (firstOption) {
          writer.println(NlsBundleUtilCore.MSG_CLI_REQUIRED_OPTIONS);
          firstOption = false;
        }
        CliOptionHelpInfo helpInfo = option2HelpMap.get(cliOption);
        helpInfo.print(writer, maxOptionColumnWidth + 1);
      }
    }
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
     * further command-line parameters have to be {@link CliArgument arguments}.
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
   * TODO
   */
  protected static class CliOptionHelpInfo {

    /** The actual option. */
    private final CliOptionContainer option;

    /**
     * The syntax of the option. Typically one entry. If it does NOT fit in one
     * line, multiple entries.
     */
    private final List<String> syntaxLines;

    /** The {@link CliOutputSettings}. */
    private final CliOutputSettings settings;

    /**
     * {@link NlsMessage#getLocalizedMessage(java.util.Locale) Localized
     * message} for {@link CliOption#usage() usage}.
     */
    private final String usage;

    /** The maximum length of all syntax lines of this info. */
    private int length;

    /**
     * The constructor.
     * 
     * @param option is the {@link CliOptionContainer}.
     * @param nlsMessageFactory is the {@link NlsMessageFactory}.
     * @param settings are the {@link CliOutputSettings}.
     */
    public CliOptionHelpInfo(CliOptionContainer option, NlsMessageFactory nlsMessageFactory,
        CliOutputSettings settings) {

      super();
      this.option = option;
      this.settings = settings;
      this.syntaxLines = new ArrayList<String>();
      int maxLength = settings.getWidth() / 2 - 1;
      Locale locale = settings.getLocale();
      CliOption cliOption = this.option.getOption();
      NlsMessage operandMessage = nlsMessageFactory.create(cliOption.operand());
      String operand = operandMessage.getLocalizedMessage(locale);
      NlsMessage usageMessage = nlsMessageFactory.create(cliOption.usage(), NlsObject.KEY_OPERAND,
          operand);
      this.usage = usageMessage.getLocalizedMessage(locale);
      StringBuilder syntax = new StringBuilder();
      syntax.append(cliOption.name());
      this.length = syntax.length();
      String[] aliases = cliOption.aliases();
      if (aliases.length > 0) {
        String alias = " (" + aliases[0];
        if (aliases.length == 1) {
          alias = alias + ")";
        }
        syntax = append(syntax, alias, maxLength);
        for (int i = 1; i < aliases.length; i++) {
          alias = " " + aliases[i];
          if (i == (aliases.length - 1)) {
            alias = aliases + ")";
          }
          syntax = append(syntax, alias, maxLength);
        }
      }
      if (!boolean.class.equals(option.getSetter().getPropertyClass())) {
        syntax = append(syntax, " " + operand, maxLength);
      }
      int len = syntax.length();
      if (len > this.length) {
        this.length = len;
      }
      this.syntaxLines.add(syntax.toString());
    }

    /**
     * 
     * TODO: javadoc
     * 
     * @param syntax
     * @param text
     * @param maxLength
     * @return
     */
    private StringBuilder append(StringBuilder syntax, String text, int maxLength) {

      int len = syntax.length();
      int newLen = len + text.length();
      if (newLen < maxLength) {
        syntax.append(text);
        return syntax;
      } else {
        if (len > this.length) {
          this.length = len;
        }
        this.syntaxLines.add(syntax.toString());
        StringBuilder newSyntax = new StringBuilder();
        newSyntax.append("  ");
        newSyntax.append(text);
        return newSyntax;
      }
    }

    /**
     * @return the length
     */
    public int getLength() {

      return this.length;
    }

    /**
     * This method writes the help of this option to the given
     * <code>writer</code>.
     * 
     * @param writer is where to write the message to.
     * @param optionsColumnWith is the number of characters to use for the
     *        options syntax. The actual usage of the option is "indented" with
     *        this index (two column layout).
     */
    public void print(NlsWriter writer, int optionsColumnWith) {

      assert (this.syntaxLines.size() > 0);
      int usageColumnWith = this.settings.getWidth() - optionsColumnWith;
      int usageLength = this.usage.length();
      int usageStart = 0;
      // loop over syntax lines
      for (String syntax : this.syntaxLines) {
        writer.printRaw(syntax);
        int delta = optionsColumnWith - syntax.length();
        assert (delta > 0);
        for (int i = 0; i < delta; i++) {
          writer.printRaw(" ");
        }
        //
        if (usageStart < usageLength) {
          usageStart = printUsage(writer, usageStart, usageColumnWith);
        }
      }
      String indent = null;
      while (usageStart < usageLength) {
        if (indent == null) {
          StringBuilder buffer = new StringBuilder(usageColumnWith);
          for (int i = 0; i < usageColumnWith; i++) {
            buffer = buffer.append(' ');
          }
          indent = buffer.toString();
        }
        writer.printRaw(indent);
        usageStart = printUsage(writer, usageStart, usageColumnWith);
      }
    }

    /**
     * 
     * @param writer is where to write the message to.
     * @param usageStart is the current start-index in {@link #usage}.
     * @param usageColumnWith the number of characters for the usage column.
     * @return the new value for <code>usageStart</code>.
     */
    private int printUsage(NlsWriter writer, int usageStart, int usageColumnWith) {

      int start = usageStart;
      int usageLength = this.usage.length();
      // omit leading whitespaces...
      while (this.usage.charAt(start) == ' ') {
        start++;
      }
      int end = start + usageColumnWith;
      if (end >= usageLength) {
        end = usageLength;
      } else {
        // usage to long -> wrap at space if available
        int indexOfSpace = this.usage.lastIndexOf(' ', end);
        int indexOfHyphen = this.usage.lastIndexOf('-', end);
        if (indexOfHyphen > indexOfSpace) {
          indexOfSpace = indexOfHyphen;
        }
        if (indexOfSpace > start) {
          end = indexOfSpace;
        }
      }
      writer.printRaw(this.usage.subSequence(start, end));
      start = end;
      writer.println();
      return start;
    }
  }

}
