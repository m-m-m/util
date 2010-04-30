/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliException;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliOptionDuplicateException;
import net.sf.mmm.util.cli.api.CliOptionIllegalNameOrAliasException;
import net.sf.mmm.util.cli.api.CliOptionIncompatibleModesException;
import net.sf.mmm.util.cli.api.CliOptionMisplacedException;
import net.sf.mmm.util.cli.api.CliOptionMissingException;
import net.sf.mmm.util.cli.api.CliOptionMissingValueException;
import net.sf.mmm.util.cli.api.CliOutputSettings;
import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.nls.base.NlsWriter;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.value.api.GenericValueConverter;

/**
 * This is the abstract base-implementation of the {@link CliParser} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractCliParser extends AbstractLoggable implements CliParser {

  /**
   * The option prefix character.
   * 
   * @see #PREFIX_SHORT_OPTION
   * @see #PREFIX_LONG_OPTION
   */
  private static final char CHAR_OPTION = '-';

  /**
   * The {@link String#length() length} of the {@link CliOption#name() name} or
   * {@link CliOption#aliases() alias} of a short option.
   */
  private static final int LENGTH_SHORT_OPTION = 2;

  /**
   * The {@link Pattern} for a mix of multiple short-options. E.g. "-vpa"
   * instead of "-v", "-p" and "-a".
   */
  private static final Pattern PATTERN_MULTI_SHORT_OPTIONS = Pattern.compile("-([a-zA-Z0-9]+)");

  /**
   * The minimum {@link String#length() length} of the {@link CliOption#name()
   * name} or {@link CliOption#aliases() alias} of a long option.
   */
  private static final int MIN_LENGTH_LONG_OPTION = 4;

  /** @see #getCliState() */
  private final CliState cliState;

  /** The {@link StringUtil} instance to use. */
  private final StringUtil stringUtil;

  /** The {@link GenericValueConverter}. */
  private final GenericValueConverter<Object> converter;

  /** The {@link NlsMessageFactory}. */
  private final NlsMessageFactory nlsMessageFactory;

  /** @see #getState() */
  private final Object state;

  /**
   * The constructor.
   * 
   * @param state is the {@link #getState() state}.
   * @param cliState is the {@link CliState}.
   * @param stringUtil is the {@link StringUtil} instance.
   * @param converter is the {@link GenericValueConverter} instance. TODO
   * @param warning see TODO
   * @param nlsMessageFactory is the {@link NlsMessageFactory} used to create
   *        instances of {@link NlsMessage}.
   */
  public AbstractCliParser(Object state, CliState cliState, StringUtil stringUtil,
      GenericValueConverter<Object> converter, NlsMessageFactory nlsMessageFactory) {

    super();
    this.state = state;
    this.cliState = cliState;
    this.converter = converter;
    this.stringUtil = stringUtil;
    this.nlsMessageFactory = nlsMessageFactory;
    for (CliOptionContainer option : this.cliState.getOptions()) {
      checkOption(option);
    }
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

    CliStyle style = this.cliState.getStyle();
    if (style == CliStyle.LEGACY) {
      return;
    }
    boolean valid = false;
    CharFilter filter = CharFilter.LATIN_DIGIT_OR_LETTER_FILTER;
    if (style == CliStyle.STRICT) {
      filter = CharFilter.ASCII_LETTER_FILTER;
    }
    if (name.startsWith(PREFIX_SHORT_OPTION)) {
      if ((name.length() == LENGTH_SHORT_OPTION) && (filter.accept(name.charAt(1)))) {
        // short option, e.g. "-h"
        valid = true;
      } else if (name.startsWith(PREFIX_LONG_OPTION) && (name.length() >= MIN_LENGTH_LONG_OPTION)) {
        valid = true;
        char previous = CHAR_OPTION;
        for (int i = 2; i < name.length(); i++) {
          char c = name.charAt(i);
          if (c == CHAR_OPTION) {
            // allow options like "--foo-bar"
            if (previous == CHAR_OPTION) {
              // but NOT "--foo--bar" or "---foo"
              valid = false;
              break;
            } else if (i == (name.length() - 1)) {
              // and NOT "--foo-"
              valid = false;
              break;
            }
          } else if (!filter.accept(c)) {
            valid = false;
            break;
          }
          previous = c;
        }
      }
    }
    if (!valid) {
      CliOptionIllegalNameOrAliasException error = new CliOptionIllegalNameOrAliasException(name,
          optionContainer.getOption());
      if (style == CliStyle.STRICT) {
        throw error;
      } else {
        String message = error.getLocalizedMessage();
        if (style == CliStyle.TOLERANT) {
          getLogger().warn(message);
        } else {
          getLogger().debug(message);
        }
      }
    }
  }

  /**
   * This method handles a {@link CliOption} with an
   * {@link CliState#getMode(String) undefined} {@link CliOption#mode()}.
   * 
   * @param option is the {@link CliOptionContainer}.
   */
  protected void handleUndefinedMode(CliOptionContainer option) {

    ObjectNotFoundException error = new ObjectNotFoundException(CliMode.class, option.getOption()
        .mode());
    CliStyle style = this.cliState.getStyle();
    if (style == CliStyle.STRICT) {
      throw error;
    } else {
      String message = error.getLocalizedMessage();
      if (style == CliStyle.LEGACY) {
        getLogger().debug(message);
      } else {
        getLogger().warn(message);
      }
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
   * @param argumentConsumer is the {@link CliArgumentConsumer}.
   */
  protected void parseOption(CliParserState parserState, String option,
      CliOptionContainer optionContainer, CliArgumentConsumer argumentConsumer) {

    PojoPropertyAccessorOneArg setter = optionContainer.getSetter();
    Class<?> propertyClass = setter.getPropertyClass();
    // propertyClass =
    // this.reflectionUtil.getNonPrimitiveType(propertyClass);
    Object value;
    if (boolean.class.equals(propertyClass)) {
      // trigger
      value = Boolean.TRUE;
    } else if (Boolean.class.equals(propertyClass)) {
      String lookahead = argumentConsumer.getCurrent();
      value = this.stringUtil.parseBoolean(lookahead);
      if (value == null) {
        // option (e.g. "--trigger") is not followed by "true" or "false"
        if (this.cliState.getStyle() == CliStyle.STRICT) {
          throw new CliOptionMissingValueException(option);
        }
        value = Boolean.TRUE;
      } else {
        argumentConsumer.getNext();
      }
    } else {
      if (!argumentConsumer.hasNext()) {
        throw new CliOptionMissingValueException(option);
      }
      String argument = argumentConsumer.getNext();
      value = this.converter.convertValue(argument, null, propertyClass, setter.getPropertyType());
    }
    setter.invoke(this.state, value);
  }

  /**
   * {@inheritDoc}
   */
  public CliModeObject parseArguments(String... arguments) throws CliException {

    CliArgumentConsumer argumentConsumer = new CliArgumentConsumer(arguments);
    CliParserState parserState = new CliParserState();
    while (argumentConsumer.hasNext()) {
      String arg = argumentConsumer.getNext();
      parseArgument(arg, parserState, argumentConsumer);
    }
    checkRequiredOptions(parserState);
    return parserState.currentMode;
  }

  /**
   * This method checks that all {@link CliOption#required() required}
   * {@link CliOption options} are present if they are triggered by the
   * {@link CliParserState#getCurrentMode() current mode}.
   * 
   * @param parserState is the current {@link CliParserState}.
   */
  protected void checkRequiredOptions(CliParserState parserState) {

    CliModeObject mode = parserState.currentMode;
    // check all required options if active of not for current mode...
    for (CliOptionContainer option : this.cliState.getOptions(mode)) {
      CliOption cliOption = option.getOption();
      if (!parserState.optionSet.contains(cliOption) && cliOption.required()) {
        throw new CliOptionMissingException(cliOption.name(), mode.getId());
      }
    }
  }

  /**
   * This method parses a single command-line argument.
   * 
   * @param argument is the command-line argument.
   * @param parserState is the {@link CliParserState}.
   * @param argumentConsumer is the {@link CliArgumentConsumer}.
   */
  protected void parseArgument(String argument, CliParserState parserState,
      CliArgumentConsumer argumentConsumer) {

    if (!parserState.optionsComplete) {
      CliOptionContainer optionContainer = this.cliState.getOption(argument);
      if (optionContainer == null) {
        Matcher matcher = PATTERN_MULTI_SHORT_OPTIONS.matcher(argument);
        if (matcher.matches()) {
          // "-vlp" --> "-v", "-l", "-p"
          String multiOptions = matcher.group(1);
          String[] arguments = new String[multiOptions.length()];
          for (int i = 0; i < multiOptions.length(); i++) {
            arguments[i] = PREFIX_SHORT_OPTION + multiOptions.charAt(i);
          }
          CliArgumentConsumer subConsumer = new CliArgumentConsumer(arguments);
          while (subConsumer.hasNext()) {
            String subArg = subConsumer.getNext();
            parseArgument(subArg, parserState, argumentConsumer);
          }
        }
      }
      if (optionContainer != null) {
        if (parserState.optionsComplete) {
          throw new CliOptionMisplacedException(argument);
        }
        boolean newOption = parserState.optionSet.add(optionContainer.getOption());
        if (!newOption) {
          handleDuplicateOption(optionContainer);
        }
        String modeId = optionContainer.getOption().mode();
        CliModeObject newMode = this.cliState.getMode(modeId);
        if (newMode == null) {
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
        if (END_OPTIONS.equals(argument)) {
          parserState.setOptionsComplete();
        } else {
          // unknown option or start of arguments
          List<CliArgumentContainer> argumentList = this.cliState
              .getArguments(parserState.currentMode);
        }
      }
    } else {

    }
  }

  /**
   * This method is invoked if the same {@link CliOption option} has occurred
   * twice.
   * 
   * @param optionContainer is the {@link CliOptionContainer} representing the
   *        duplicated option.
   */
  private void handleDuplicateOption(CliOptionContainer optionContainer) {

    CliStyle style = getCliState().getStyle();
    if (style == CliStyle.STRICT) {
      throw new CliOptionDuplicateException(optionContainer.getOption().name());
    } else {
      String message = "Duplicate option: " + optionContainer.getOption().name();
      if (style == CliStyle.TOLERANT) {
        getLogger().warn(message);
      } else {
        getLogger().debug(message);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void printHelp(Appendable target) {

    printHelp(target, new CliOutputSettings());
  }

  //
  // protected void println(Appendable target, String message, Map<String,
  // Object> arguments,
  // CliOutputSettings settings) {
  //
  // try {
  // NlsMessage usageMessage =
  // this.nlsMessageFactory.create(NlsBundleUtilCore.MSG_MAIN_USAGE,
  // arguments);
  // target.append(usageMessage.getLocalizedMessage(settings.getLocale()));
  // target.append(settings.getLineSeparator());
  // } catch (IOException e) {
  // throw new RuntimeIoException(e, IoMode.WRITE);
  // }
  // }

  /**
   * {@inheritDoc}
   */
  public void printHelp(Appendable target, CliOutputSettings settings) {

    try {
      Map<String, Object> arguments = new HashMap<String, Object>();
      arguments.put("mainClass", this.cliState.getName());
      arguments.put("optionCount", Integer.valueOf(this.cliState.getOptions().size()));
      arguments.put("argumentCount", Integer.valueOf(this.cliState.getArguments().size()));
      arguments.put(NlsObject.KEY_OPTION, "[<option>*]");
      NlsWriter writer = new NlsWriter(target, arguments, settings.getLocale(), settings
          .getLineSeparator(), this.nlsMessageFactory, settings.getTemplateResolver());
      writer.println(NlsBundleUtilCore.MSG_MAIN_USAGE);
      // this.cliState.getOptions();
      writer.println(this.cliState.getUsage());
      Map<CliOption, CliOptionHelpInfo> option2HelpMap = new HashMap<CliOption, AbstractCliParser.CliOptionHelpInfo>();
      for (String modeId : this.cliState.getModeIds()) {
        writer.println();
        CliModeObject mode = this.cliState.getMode(modeId);
        CliMode cliMode = mode.getMode();
        if ((cliMode == null) || (!cliMode.isAbstract())) {
          NlsMessage modeMessage = this.nlsMessageFactory.create(mode.getMode().title());
          arguments.put(NlsObject.KEY_MODE, modeMessage);
          writer.println(NlsBundleUtilCore.MSG_MAIN_MODE_USAGE);
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
              helpInfo = new CliOptionHelpInfo(option, this.nlsMessageFactory, settings);
              option2HelpMap.put(cliOption, helpInfo);
            }
            int len = helpInfo.getLength();
            if (len > maxOptionColumnWidth) {
              maxOptionColumnWidth = len;
            }
          }
          this.cliState.getOptions(mode);
          arguments.put(NlsObject.KEY_OPTION, options);
          writer.println(NlsBundleUtilCore.MSG_MAIN_USAGE);
          String usage = "";
          if (cliMode != null) {
            usage = cliMode.usage();
          }
          if (usage.length() > 0) {
            writer.println(usage);
          }
          for (CliOptionContainer option : modeOptions) {
            CliOption cliOption = option.getOption();
            CliOptionHelpInfo helpInfo = option2HelpMap.get(cliOption);
            helpInfo.print(writer, maxOptionColumnWidth + 1);
          }
        }
      }
      arguments.remove(NlsObject.KEY_MODE);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  /**
   * @return the stringUtil
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * @return the nlsMessageFactory
   */
  protected NlsMessageFactory getNlsMessageFactory() {

    return this.nlsMessageFactory;
  }

  /**
   * This inner class holds the state of the
   * {@link AbstractCliParser#parseArguments(String...) argument parsing}.
   */
  protected static class CliParserState {

    /** @see #getCurrentMode() */
    private CliModeObject currentMode;

    /** @see #getModeOption() */
    private String modeOption;

    /** @see #getOptionSet() */
    private Set<CliOption> optionSet;

    /** @see #isOptionsComplete() */
    private boolean optionsComplete;

    /**
     * The constructor.
     */
    public CliParserState() {

      super();
      this.currentMode = null;
      this.modeOption = null;
      this.optionSet = new HashSet<CliOption>();
      this.optionsComplete = false;
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
     * The {@link Set} of activated {@link CliOption options}.
     * 
     * @return the optionSet, initially empty.
     */
    public Set<CliOption> getOptionSet() {

      return this.optionSet;
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

      return this.optionsComplete;
    }

    /**
     * This method sets the {@link #isOptionsComplete() options-complete flag}
     * to <code>true</code>.
     */
    public void setOptionsComplete() {

      this.optionsComplete = true;
    }

  }

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
     * @throws IOException if an I/O error occurred.
     */
    public void print(NlsWriter writer, int optionsColumnWith) throws IOException {

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
     * @throws IOException if an I/O error occurred.
     */
    private int printUsage(NlsWriter writer, int usageStart, int usageColumnWith)
        throws IOException {

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
