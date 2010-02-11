/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.cli.api.CliException;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeMixException;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliOptionIllegalNameOrAliasException;
import net.sf.mmm.util.cli.api.CliOutputSettings;
import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.value.api.GenericValueConverter;

/**
 * This is the abstract base-implementation of the {@link CliParser} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
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
   * The prefix for {@link CliOption#name() name} or {@link CliOption#aliases()
   * alias} of a short option (e.g. "-h").
   */
  private static final String PREFIX_SHORT_OPTION = "-";

  /**
   * The prefix for {@link CliOption#name() name} or {@link CliOption#aliases()
   * alias} of a long option (e.g. "--help").
   */
  private static final String PREFIX_LONG_OPTION = "--";

  /**
   * The {@link String#length() length} of the {@link CliOption#name() name} or
   * {@link CliOption#aliases() alias} of a short option.
   */
  private static final int LENGTH_SHORT_OPTION = 2;

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
      filter = CharFilter.ASCII_UPPER_CASE_LETTER_FILTER;
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

  protected void parseOption(CliModeObject mode, CliOptionContainer optionContainer,
      CliArgumentConsumer argumentConsumer) {

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
          throw new CliException("TODO") {};
        }
        value = Boolean.TRUE;
      }
      argumentConsumer.getNext();
    } else {
      if (!argumentConsumer.hasNext()) {
        throw new CliException("TODO") {};
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

    CliModeObject currentMode = null;
    String modeOption = null;
    CliArgumentConsumer argumentConsumer = new CliArgumentConsumer(arguments);
    boolean optionsComplete = false;
    while (argumentConsumer.hasNext()) {
      String arg = argumentConsumer.getNext();
      CliOptionContainer optionContainer = this.cliState.getOption(arg);
      if (optionContainer != null) {
        if (optionsComplete) {
          throw new CliException("TODO") {};
        }
        String modeId = optionContainer.getOption().mode();
        CliModeObject newMode = this.cliState.getMode(modeId);
        if (newMode == null) {
          newMode = new CliModeContainer(modeId);
        }
        if (currentMode == null) {
          currentMode = newMode;
          modeOption = arg;
        } else if (!modeId.equals(currentMode.getId())) {
          if (newMode.getExtendedModes().contains(currentMode)) {
            // new mode extends current mode
            currentMode = newMode;
            modeOption = arg;
          } else if (!currentMode.getExtendedModes().contains(newMode)) {
            // current mode does NOT extend new mode and vice versa
            // --> incompatible modes
            throw new CliModeMixException(modeOption, arg);
          }
        }
        parseOption(currentMode, optionContainer, argumentConsumer);
      } else {
        if (PREFIX_LONG_OPTION.equals(arg)) {
          optionsComplete = true;
        } else {
          // options over? arguments start?
        }
      }
    }
    return null;
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

    String name = this.cliState.getName();
    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put("mainClass", this.cliState.getName());
    NlsMessage usageMessage = this.nlsMessageFactory.create(NlsBundleUtilCore.MSG_MAIN_USAGE,
        arguments);
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
}
