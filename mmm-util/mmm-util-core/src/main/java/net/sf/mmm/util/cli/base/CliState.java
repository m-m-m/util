/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliClass;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliModes;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is a container for the {@link #getState() state-object}. It
 * automatically reads the CLI-annotations from the {@link #getState() states}
 * {@link Object#getClass() class} during
 * {@link #CliState(Object, PojoDescriptorBuilder) construction}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliState {

  /** @see #getState() */
  private final Object state;

  /** @see #getStyle() */
  private final CliStyle style;

  /** @see #getUsage() */
  private final String usage;

  /** @see #getName() */
  private final String name;

  /** @see #getMode(String) */
  private final Map<String, CliModeContainer> id2ModeMap;

  /** @see #getOption(String) */
  private final Map<String, CliOptionContainer> name2OptionMap;

  /** @see #getOptions() */
  private final List<CliOptionContainer> optionList;

  /** @see #getArguments() */
  private final List<CliArgumentContainer> argumentList;

  /**
   * The constructor.
   * 
   * @param state is the {@link #getState() state}.
   * @param descriptorBuilder is the {@link PojoDescriptorBuilder} used to
   *        introspect the properties of the {@link #getState() state}.
   */
  public CliState(Object state, PojoDescriptorBuilder descriptorBuilder) {

    super();
    this.state = state;
    this.id2ModeMap = new HashMap<String, CliModeContainer>();
    this.name2OptionMap = new HashMap<String, CliOptionContainer>();
    this.argumentList = new ArrayList<CliArgumentContainer>();
    this.optionList = new ArrayList<CliOptionContainer>();
    Class<?> stateClass = this.state.getClass();
    CliStyle cliStyle = null;
    String cliUsage = null;
    String cliName = null;
    Class<?> currentClass = stateClass;
    while (currentClass != null) {
      CliClass cliClass = currentClass.getAnnotation(CliClass.class);
      if (cliClass != null) {
        if ((cliStyle != null) && (cliClass.style() != CliStyle.INHERIT)) {
          cliStyle = cliClass.style();
        }
        if ((cliName != null) && (cliClass.name().length() > 0)) {
          cliName = cliClass.name();
        }
        if ((cliUsage != null) && (cliClass.usage().length() > 0)) {
          cliUsage = cliClass.usage();
        }
      }
      CliMode cliMode = currentClass.getAnnotation(CliMode.class);
      if (cliMode != null) {
        addMode(cliMode, currentClass);
      }
      CliModes cliModes = currentClass.getAnnotation(CliModes.class);
      if (cliModes != null) {
        for (CliMode mode : cliModes.value()) {
          addMode(mode, currentClass);
        }
      }
      currentClass = currentClass.getSuperclass();
    }
    if (cliStyle == null) {
      cliStyle = CliStyle.STRICT;
    }
    this.style = cliStyle;
    this.name = cliName;
    this.usage = cliUsage;
    PojoDescriptor<?> descriptor = descriptorBuilder.getDescriptor(stateClass);
    for (PojoPropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
      PojoPropertyAccessorOneArg setter = propertyDescriptor
          .getAccessor(PojoPropertyAccessorOneArgMode.SET);
      if (setter != null) {
        AccessibleObject accessible = setter.getAccessibleObject();
        CliOption option = accessible.getAnnotation(CliOption.class);
        if (option != null) {
          CliOptionContainer optionContainer = new CliOptionContainer(option, setter);
          addOption(optionContainer);
        }
        CliArgument argument = accessible.getAnnotation(CliArgument.class);
        if (argument != null) {
          if (option != null) {
            // TODO own exception
            throw new IllegalStateException("The property '" + propertyDescriptor.getName()
                + "' can not be annotated with both '" + CliOption.class.getSimpleName()
                + "' and '" + CliArgument.class.getSimpleName() + "'!");
          }
          CliArgumentContainer argumentContainer = new CliArgumentContainer(argument, setter);
          addArgument(argumentContainer);
        }
      }
    }
    int nullIndex = -1;
    for (int i = 0; i < this.argumentList.size(); i++) {
      CliArgumentContainer argument = this.argumentList.get(i);
      if (argument == null) {
        nullIndex = i;
      } else if (nullIndex > -1) {
        // TODO own exception
        throw new IllegalStateException(argument + " is illegal because index '" + nullIndex
            + "' is missing!");

      }
    }
  }

  /**
   * This method {@link #getArguments() registers} the given
   * {@link CliArgumentContainer argument}.
   * 
   * @param argument is the {@link CliArgumentContainer argument} to register.
   */
  private void addArgument(CliArgumentContainer argument) {

    int index = argument.getArgument().index();
    ValueOutOfRangeException.checkRange(Integer.valueOf(index), CliArgument.INDEX_MIN,
        CliArgument.INDEX_MAX, argument);
    // ensure capacity
    for (int i = this.argumentList.size(); i <= index; i++) {
      this.argumentList.add(null);
    }
    if (this.argumentList.get(index) != null) {
      throw new DuplicateObjectException(argument, Integer.valueOf(index));
    }
    this.argumentList.set(index, argument);
  }

  /**
   * This method adds the given {@link CliMode}.
   * 
   * @param mode is the {@link CliMode} to add.
   * @param annotatedClass is the Class where the {@link CliMode} was annotated.
   */
  private void addMode(CliMode mode, Class<?> annotatedClass) {

    CliModeContainer container = new CliModeContainer(mode, annotatedClass);
    addMode(container);
  }

  /**
   * This method adds the given {@link CliMode}.
   * 
   * @param mode is the {@link CliMode} to add.
   */
  private void addMode(CliModeContainer mode) {

    CliModeObject old = this.id2ModeMap.put(mode.getMode().id(), mode);
    if (old != null) {
      throw new DuplicateObjectException(mode, mode.getMode().id());
    }
  }

  /**
   * This method {@link #getOptions() registers} the given
   * {@link CliOptionContainer option}.
   * 
   * @param option is the {@link CliOptionContainer option} to register.
   */
  private void addOption(CliOptionContainer option) {

    CliOption cliOption = option.getOption();
    String mode = cliOption.mode();
    if (getMode(mode) == null) {
      // if (this.style == CliStyle.STRICT) {
      throw new ObjectNotFoundException(CliMode.class, mode);
      // }
    }
    addOption(cliOption.name(), option);
    for (String alias : cliOption.aliases()) {
      addOption(alias, option);
    }
    this.optionList.add(option);
  }

  /**
   * This method {@link #getOption(String) registers} the given
   * {@link CliOptionContainer option} with the given <code>name</code>.
   * 
   * @param nameOrAlias is the {@link CliOption#name()} or
   *        {@link CliOption#aliases() alias} of the option.
   * @param option is the {@link CliOptionContainer option} to register.
   */
  private void addOption(String nameOrAlias, CliOptionContainer option) {

    CliOptionContainer old = this.name2OptionMap.put(nameOrAlias, option);
    if (old != null) {
      throw new DuplicateObjectException(option, nameOrAlias);
    }
  }

  /**
   * This method gets the instance of the state where the command-line arguments
   * should be applied to.
   * 
   * @see CliClass
   * 
   * @return the state.
   */
  public Object getState() {

    return this.state;
  }

  /**
   * This method gets the {@link CliClass#style() style} configured for the
   * {@link #getState() state-object}.
   * 
   * @return the {@link CliClass#style() style}.
   */
  public CliStyle getStyle() {

    return this.style;
  }

  /**
   * This method gets the {@link CliClass#name() name} configured for the
   * {@link #getState() state-object}.
   * 
   * @return the {@link CliClass#name() name}.
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method gets the {@link CliClass#usage() usage} configured for the
   * {@link #getState() state-object}.
   * 
   * @return the {@link CliClass#usage() usage}.
   */
  public String getUsage() {

    return this.usage;
  }

  /**
   * This method gets the {@link CliModeContainer mode} associated with the
   * given {@link CliMode#id() ID}.
   * 
   * @param id the {@link CliMode#id() ID} of the requested {@link CliMode}.
   * @return the requested {@link CliMode} or <code>null</code> if no such
   *         {@link CliMode} exists.
   */
  public CliModeObject getMode(String id) {

    return this.id2ModeMap.get(id);
  }

  /**
   * This method gets the {@link CliOptionContainer option} associated with the
   * given <code>nameOrAlias</code>.
   * 
   * @param nameOrAlias is the {@link CliOption#name()} or
   *        {@link CliOption#aliases() alias} of the requested
   *        {@link CliOptionContainer option}.
   * @return the requested {@link CliOptionContainer option} or
   *         <code>null</code> if no such {@link CliOptionContainer option}
   *         exists.
   */
  public CliOptionContainer getOption(String nameOrAlias) {

    return this.name2OptionMap.get(nameOrAlias);
  }

  /**
   * This method gets the {@link List} of {@link CliOptionContainer CLI-options}
   * .
   * 
   * @return the options
   */
  public List<CliOptionContainer> getOptions() {

    return this.optionList;
  }

  /**
   * This method gets the {@link List} of {@link CliArgumentContainer
   * CLI-arguments}.
   * 
   * @return the arguments
   */
  public List<CliArgumentContainer> getArguments() {

    return this.argumentList;
  }

  /**
   * This method gets the {@link Collection} with the {@link CliMode#id() IDs}
   * of all {@link #getMode(String) registered} {@link CliMode}s.
   * 
   * @return the mode-IDs.
   */
  public Collection<String> getModeIds() {

    return this.id2ModeMap.keySet();
  }

}
