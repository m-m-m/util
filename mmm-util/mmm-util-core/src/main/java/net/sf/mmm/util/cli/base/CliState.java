/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliException;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is a container for the {@link #getState() state-object}. It
 * automatically reads the CLI-annotations from the {@link #getState() states}
 * {@link Object#getClass() class} during
 * {@link #CliState(Class, PojoDescriptorBuilderFactory) construction}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliState extends CliClassContainer {

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
   * @param descriptorBuilderFactory is the {@link PojoDescriptorBuilderFactory}
   *        used to introspect the {@link PojoPropertyDescriptor properties} of
   *        the {@link #getState() state} from the {@link Class#getFields()
   *        fields}.
   */
  public CliState(Class<?> stateClass, PojoDescriptorBuilderFactory descriptorBuilderFactory) {

    super(stateClass);
    this.name2OptionMap = new HashMap<String, CliOptionContainer>();
    this.argumentList = new ArrayList<CliArgumentContainer>();
    this.optionList = new ArrayList<CliOptionContainer>();
    int nullIndex = -1;
    boolean annotationFound = findPropertyAnnotations(descriptorBuilderFactory
        .createPrivateFieldDescriptorBuilder());
    if (!annotationFound) {
      annotationFound = findPropertyAnnotations(descriptorBuilderFactory
          .createPublicMethodDescriptorBuilder());
    }
    if (!annotationFound) {
      throw new CliException("TODO") {};
    }
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
    return annotationFound;
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
   * This method {@link #getOptions() registers} the given
   * {@link CliOptionContainer option}.
   * 
   * @param option is the {@link CliOptionContainer option} to register.
   */
  private void addOption(CliOptionContainer option) {

    CliOption cliOption = option.getOption();
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

}
