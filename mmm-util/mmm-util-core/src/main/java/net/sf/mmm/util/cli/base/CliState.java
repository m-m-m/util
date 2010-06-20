/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliClassNoPropertyException;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliModeUndefinedException;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliOptionAndArgumentAnnotationException;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;

import org.slf4j.Logger;

/**
 * This is a container for the {@link #getStateClass() state-class}. It
 * determines and holds the CLI-informations of that {@link #getStateClass()
 * state-class}. In advance to {@link CliClassContainer} it also handles the CLI
 * specific property annotations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliState extends CliClassContainer {

  /** @see #getOption(String) */
  private final Map<String, CliOptionContainer> name2OptionMap;

  /** @see #getOptions() */
  private final List<CliOptionContainer> optionList;

  /** @see #getArguments(CliModeObject) */
  private final Map<String, List<CliArgumentContainer>> mode2argumentsMap;

  /** @see #getArguments() */
  private final List<CliArgumentContainer> arguments;

  /** @see #getLogger() */
  private final Logger logger;

  /**
   * The constructor.
   * 
   * @param stateClass is the {@link #getStateClass() state-class}.
   * @param descriptorBuilderFactory is the {@link PojoDescriptorBuilderFactory}
   *        used to introspect the {@link PojoPropertyDescriptor properties} of
   *        the {@link #getStateClass() stateClass}.
   * @param logger is the {@link Logger} to use (e.g. for
   *        {@link CliStyleHandling}).
   */
  public CliState(Class<?> stateClass, PojoDescriptorBuilderFactory descriptorBuilderFactory,
      Logger logger) {

    super(stateClass);
    this.name2OptionMap = new HashMap<String, CliOptionContainer>();
    this.optionList = new ArrayList<CliOptionContainer>();
    this.mode2argumentsMap = new HashMap<String, List<CliArgumentContainer>>();
    this.arguments = new ArrayList<CliArgumentContainer>();
    this.logger = logger;
    int nullIndex = -1;
    boolean annotationFound = findPropertyAnnotations(descriptorBuilderFactory
        .createPrivateFieldDescriptorBuilder());
    if (!annotationFound) {
      annotationFound = findPropertyAnnotations(descriptorBuilderFactory
          .createPublicMethodDescriptorBuilder());
    }
    if (!annotationFound) {
      throw new CliClassNoPropertyException(stateClass);
    }
    for (List<CliArgumentContainer> argumentList : this.mode2argumentsMap.values()) {
      for (int i = 0; i < argumentList.size(); i++) {
        CliArgumentContainer argument = argumentList.get(i);
        if (argument == null) {
          nullIndex = i;
        } else if (nullIndex > -1) {
          // TODO own exception
          throw new IllegalStateException(argument + " is illegal because index '" + nullIndex
              + "' is missing!");

        }
      }
    }
  }

  /**
   * This method gets the {@link Logger}.
   * 
   * @return the {@link Logger}.
   */
  public Logger getLogger() {

    return this.logger;
  }

  /**
   * This method finds the properties annotated with {@link CliOption} or
   * {@link CliArgument}.
   * 
   * @param descriptorBuilder is the {@link PojoDescriptorBuilder} to use
   *        (determines if fields or setters are used).
   * @return <code>true</code> if at least one annotated property has been
   *         found, <code>false</code> otherwise.
   */
  protected boolean findPropertyAnnotations(PojoDescriptorBuilder descriptorBuilder) {

    boolean annotationFound = false;
    PojoDescriptor<?> descriptor = descriptorBuilder.getDescriptor(getStateClass());
    for (PojoPropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
      PojoPropertyAccessorOneArg setter = propertyDescriptor
          .getAccessor(PojoPropertyAccessorOneArgMode.SET);
      if (setter != null) {
        AccessibleObject accessible = setter.getAccessibleObject();
        CliOption option = accessible.getAnnotation(CliOption.class);
        if (option != null) {
          annotationFound = true;
          CliOptionContainer optionContainer = new CliOptionContainer(option, setter);
          addOption(optionContainer);
        }
        CliArgument argument = accessible.getAnnotation(CliArgument.class);
        if (argument != null) {
          annotationFound = true;
          if (option != null) {
            throw new CliOptionAndArgumentAnnotationException(propertyDescriptor.getName());
          }
          CliArgumentContainer argumentContainer = new CliArgumentContainer(argument, setter);
          addArgument(argumentContainer);
        }
      }
    }
    return annotationFound;
  }

  /**
   * This method is like {@link #getMode(String)} but also
   * {@link net.sf.mmm.util.cli.api.CliStyle#modeUndefined() handles} the case
   * that a {@link CliMode} may be undefined.
   * 
   * @param id is the {@link CliMode#id() ID} of the requested {@link CliMode}.
   * @param annotationContainer is the {@link CliArgumentContainer} or
   *        {@link CliOptionContainer}.
   * @return the requested {@link CliMode}.
   */
  protected CliModeObject requireMode(String id, Object annotationContainer) {

    CliModeObject modeObject = getMode(id);
    if (modeObject == null) {
      CliStyleHandling handling = getCliStyle().modeUndefined();
      if (handling != CliStyleHandling.OK) {
        handling.handle(getLogger(), new CliModeUndefinedException(id, annotationContainer));
      }
      CliModeContainer modeContainer = new CliModeContainer(id);
      addMode(modeContainer);
      modeObject = modeContainer;
    }
    return modeObject;
  }

  /**
   * This method {@link #getArguments(CliModeObject) registers} the given
   * {@link CliArgumentContainer argument}.
   * 
   * @param argumentContainer is the {@link CliArgumentContainer argument} to
   *        register.
   */
  private void addArgument(CliArgumentContainer argumentContainer) {

    CliArgument cliArgument = argumentContainer.getArgument();
    // int index = cliArgument.index();
    // ValueOutOfRangeException.checkRange(Integer.valueOf(index),
    // CliArgument.INDEX_MIN,
    // CliArgument.INDEX_MAX, argumentContainer);

    this.arguments.add(argumentContainer);
    String modeId = cliArgument.mode();
    requireMode(modeId, argumentContainer);
    List<CliArgumentContainer> argumentList = this.mode2argumentsMap.get(modeId);
    if (argumentList == null) {
      argumentList = new ArrayList<CliArgumentContainer>();
      this.mode2argumentsMap.put(modeId, argumentList);
    }
    // ensure capacity
    // for (int i = argumentList.size(); i <= index; i++) {
    // argumentList.add(null);
    // }
    // if (argumentList.get(index) != null) {
    // throw new DuplicateObjectException(argumentContainer,
    // Integer.valueOf(index));
    // }
    // argumentList.set(index, argumentContainer);
    // TODO
    argumentList.add(argumentContainer);
  }

  /**
   * This method {@link #getOptions() registers} the given
   * {@link CliOptionContainer option}.
   * 
   * @param optionContainer is the {@link CliOptionContainer option} to
   *        register.
   */
  private void addOption(CliOptionContainer optionContainer) {

    CliOption cliOption = optionContainer.getOption();
    addOption(cliOption.name(), optionContainer);
    for (String alias : cliOption.aliases()) {
      addOption(alias, optionContainer);
    }
    this.optionList.add(optionContainer);
    String modeId = optionContainer.getOption().mode();
    requireMode(modeId, optionContainer);
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
   * This method gets the {@link List} of all {@link CliArgumentContainer
   * CLI-arguments}.
   * 
   * @return the arguments
   */
  public Collection<CliArgumentContainer> getArguments() {

    return this.arguments;
  }

  /**
   * This method gets the {@link List} of {@link CliArgumentContainer
   * CLI-arguments} for the given {@link CliModeObject mode}.
   * 
   * @param mode is the according {@link CliModeContainer mode}.
   * @return the arguments
   */
  public List<CliArgumentContainer> getArguments(CliModeObject mode) {

    List<CliArgumentContainer> modeArguments = this.mode2argumentsMap.get(mode.getId());
    if (modeArguments == null) {
      CliMode cliMode = mode.getMode();
      if (cliMode != null) {
        for (String parentId : cliMode.parentIds()) {
          CliModeObject parentMode = getMode(parentId);
          modeArguments = getArguments(parentMode);
          if (modeArguments != null) {
            break;
          }
        }
      }
    }
    return modeArguments;
  }

  /**
   * This method gets the {@link CliOption options} for the given
   * {@link CliMode mode}.
   * 
   * @param mode is the {@link CliModeObject} for which the {@link CliOption
   *        options} are required.
   * @return a {@link Collection} with all {@link CliOption options} that are
   *         {@link CliOption#mode() compatible} with the given
   *         <code>mode</code>.
   */
  public Collection<CliOptionContainer> getOptions(CliModeObject mode) {

    String modeId = mode.getId();
    // create set of active modes...
    Set<String> modeIdSet = new HashSet<String>();
    modeIdSet.add(modeId);
    for (CliModeObject childMode : mode.getExtendedModes()) {
      modeIdSet.add(childMode.getId());
    }
    List<CliOptionContainer> result = new ArrayList<CliOptionContainer>();
    for (CliOptionContainer option : this.optionList) {
      if (modeIdSet.contains(option.getOption().mode())) {
        result.add(option);
      }
    }
    return result;
  }
}
