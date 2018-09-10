/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliArgumentReferenceMissingException;
import net.sf.mmm.util.cli.api.CliClassNoPropertyException;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliModeUndefinedException;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliOptionAndArgumentAnnotationException;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.collection.base.BasicDoubleLinkedNode;
import net.sf.mmm.util.collection.base.NodeCycle;
import net.sf.mmm.util.collection.base.NodeCycleException;
import net.sf.mmm.util.component.api.InitializationState;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.api.AnnotationUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.ValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorBuilderNone;
import net.sf.mmm.util.value.api.SimpleValueConverter;

/**
 * This is a container for the {@link #getStateClass() state-class}. It determines and holds the CLI-informations of
 * that {@link #getStateClass() state-class}. In advance to {@link CliClassContainer} it also handles the CLI specific
 * property annotations.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliState extends CliClassContainer {

  private static final Logger LOG = LoggerFactory.getLogger(CliState.class);

  private final ReflectionUtil reflectionUtil;

  private final AnnotationUtil annotationUtil;

  private final Map<String, CliOptionContainer> name2OptionMap;

  private final List<CliOptionContainer> optionList;

  private final List<CliArgumentContainer> arguments;

  private final ValidatorBuilder validatorBuilder;

  /**
   * The constructor.
   *
   * @param stateClass is the {@link #getStateClass() state-class}.
   * @param descriptorBuilderFactory is the {@link PojoDescriptorBuilderFactory} used to introspect the
   *        {@link PojoPropertyDescriptor properties} of the {@link #getStateClass() stateClass}.
   * @param logger is the {@link Logger} to use (e.g. for {@link CliStyleHandling}).
   * @param reflectionUtil is the {@link ReflectionUtil} instance to use.
   * @param annotationUtil is the {@link AnnotationUtil} instance to use.
   */
  public CliState(Class<?> stateClass, PojoDescriptorBuilderFactory descriptorBuilderFactory, ReflectionUtil reflectionUtil, AnnotationUtil annotationUtil) {

    super(stateClass);
    this.name2OptionMap = new HashMap<>();
    this.optionList = new ArrayList<>();
    this.arguments = new ArrayList<>();
    this.reflectionUtil = reflectionUtil;
    this.annotationUtil = annotationUtil;
    this.validatorBuilder = createValidatorBuilder();
    boolean fieldAnnotationFound = findPropertyAnnotations(descriptorBuilderFactory.createPrivateFieldDescriptorBuilder());
    boolean methodAnnotationFound = findPropertyAnnotations(descriptorBuilderFactory.createPublicMethodDescriptorBuilder());
    if (!fieldAnnotationFound && !methodAnnotationFound) {
      // is this really forbidden? Add handling to CliStyle?
      throw new CliClassNoPropertyException(stateClass);
    }
    initializeArguments();
  }

  /**
   * @return the {@link ValidatorBuilder} instance.
   */
  private ValidatorBuilder createValidatorBuilder() {

    ValidatorBuilder builder;
    try {
      builder = new net.sf.mmm.util.validation.base.ValidatorBuilderJsr303();
    } catch (Throwable e) {
      LOG.error("Failed to setup javax.validation - validation disabled", e);
      builder = new ValidatorBuilderNone();
    }
    return builder;
  }

  /**
   * @return the annotationUtil
   */
  protected AnnotationUtil getAnnotationUtil() {

    return this.annotationUtil;
  }

  /**
   * @return the reflectionUtil
   */
  protected ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * This method initializes the {@link #getArguments() arguments}. This means that the {@link #getArguments()
   * arguments} are ordered properly.
   *
   * @see CliArgument#addCloseTo()
   * @see CliArgument#addAfter()
   */
  protected void initializeArguments() {

    Map<String, BasicDoubleLinkedNode<CliArgumentContainer>> argumentMap = new HashMap<>();
    BasicDoubleLinkedNode<CliArgumentContainer> startHead = null;
    BasicDoubleLinkedNode<CliArgumentContainer> startTail = null;
    BasicDoubleLinkedNode<CliArgumentContainer> endHead = null;
    BasicDoubleLinkedNode<CliArgumentContainer> endTail = null;
    for (CliArgumentContainer argumentContainer : this.arguments) {
      String id = argumentContainer.getId();
      if ((CliArgument.ID_FIRST.equals(id)) || (CliArgument.ID_LAST.equals(id))) {
        throw new NlsIllegalArgumentException(id, argumentContainer.toString());
      }
      if (argumentMap.containsKey(id)) {
        throw new DuplicateObjectException(argumentContainer, id);
      }
      BasicDoubleLinkedNode<CliArgumentContainer> node = new BasicDoubleLinkedNode<>();
      node.setValue(argumentContainer);
      argumentMap.put(id, node);
      CliArgument cliArgument = argumentContainer.getArgument();
      boolean addAfter = cliArgument.addAfter();
      String nextTo = cliArgument.addCloseTo();
      if (CliArgument.ID_FIRST.equals(nextTo)) {
        if ((startHead == null) || (startTail == null)) {
          startHead = node;
          startTail = node;
        } else {
          if (addAfter) {
            startTail.insertAsNext(node);
            startTail = node;
          } else {
            startHead.insertAsPrevious(node);
            startHead = node;
          }
        }
        argumentContainer.setState(InitializationState.INITIALIZED);
      } else if (CliArgument.ID_LAST.equals(nextTo)) {
        if ((endTail == null) || (endHead == null)) {
          endTail = node;
          endHead = node;
        } else {
          if (addAfter) {
            endTail.insertAsNext(node);
            endTail = node;
          } else {
            endHead.insertAsPrevious(node);
            endHead = node;
          }
        }
        argumentContainer.setState(InitializationState.INITIALIZED);
      }
    }
    if ((startTail != null) && (endHead != null)) {
      // connect start and end of list...
      startTail.insertAsNext(endHead);
    }
    for (BasicDoubleLinkedNode<CliArgumentContainer> node : argumentMap.values()) {
      initializeArgumentRecursive(node, argumentMap);
    }
    // order arguments
    if (startHead != null) {
      this.arguments.clear();
      startHead.addToList(this.arguments);
    } else if (endHead != null) {
      this.arguments.clear();
      BasicDoubleLinkedNode<CliArgumentContainer> node = endHead;
      BasicDoubleLinkedNode<CliArgumentContainer> previous = node.getPrevious();
      while (previous != null) {
        node = previous;
        previous = node.getPrevious();
      }
      node.addToList(this.arguments);
    }
  }

  /**
   * This method initializes the {@link BasicDoubleLinkedNode node} {@link BasicDoubleLinkedNode#getValue() containing}
   * an {@link CliArgumentContainer} in order to determine the appropriate order of the {@link CliArgument}s.
   *
   * @param node is the node to initialize (link into the node-list).
   * @param argumentMap maps the {@link CliArgumentContainer#getId() id} to the according argument-node.
   * @return a {@link NodeCycle} if a cyclic dependency has been detected but is NOT yet complete or {@code null} if the
   *         initialization was successful.
   * @throws NodeCycleException if a cyclic dependency was detected and completed.
   */
  protected NodeCycle<CliArgumentContainer> initializeArgumentRecursive(BasicDoubleLinkedNode<CliArgumentContainer> node,
      Map<String, BasicDoubleLinkedNode<CliArgumentContainer>> argumentMap) throws NodeCycleException {

    CliArgumentContainer argumentContainer = node.getValue();
    if (argumentContainer.getState() != InitializationState.INITIALIZED) {
      if (argumentContainer.getState() == InitializationState.INITIALIZING) {
        // cycle detected
        return new NodeCycle<>(argumentContainer, CliArgumentFormatter.INSTANCE);
      } else {
        argumentContainer.setState(InitializationState.INITIALIZING);
      }
      CliArgument cliArgument = argumentContainer.getArgument();
      String nextTo = cliArgument.addCloseTo();
      assert (!CliArgument.ID_FIRST.equals(nextTo) && !CliArgument.ID_LAST.equals(nextTo));
      BasicDoubleLinkedNode<CliArgumentContainer> nextToNode = argumentMap.get(nextTo);
      if (nextToNode == null) {
        throw new CliArgumentReferenceMissingException(argumentContainer);
      }
      boolean addAfter = cliArgument.addAfter();
      NodeCycle<CliArgumentContainer> cycle = initializeArgumentRecursive(nextToNode, argumentMap);
      if (cycle != null) {
        cycle.getInverseCycle().add(argumentContainer);
        if (cycle.getStartNode() == argumentContainer) {
          throw new NodeCycleException(cycle);
        }
        return cycle;
      }
      if (addAfter) {
        nextToNode.insertAsNext(node);
      } else {
        nextToNode.insertAsPrevious(node);
      }
      argumentContainer.setState(InitializationState.INITIALIZED);
    }
    return null;
  }

  /**
   * This method finds the properties annotated with {@link CliOption} or {@link CliArgument}.
   *
   * @param descriptorBuilder is the {@link PojoDescriptorBuilder} to use (determines if fields or setters are used).
   * @return {@code true} if at least one annotated property has been found, {@code false} otherwise.
   */
  protected boolean findPropertyAnnotations(PojoDescriptorBuilder descriptorBuilder) {

    boolean annotationFound = false;
    PojoDescriptor<?> descriptor = descriptorBuilder.getDescriptor(getStateClass());
    for (PojoPropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
      PojoPropertyAccessorOneArg setter = propertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.SET);
      if (setter != null) {
        AccessibleObject accessible = setter.getAccessibleObject();
        CliOption option = accessible.getAnnotation(CliOption.class);
        CliArgument argument = accessible.getAnnotation(CliArgument.class);
        if ((option != null) && (argument != null)) {
          // property can not both be option and argument
          throw new CliOptionAndArgumentAnnotationException(propertyDescriptor.getName());
        } else if ((option != null) || (argument != null)) {
          annotationFound = true;
          PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
          ValueValidator<?> validator = this.validatorBuilder.newValidator(getStateClass(), propertyDescriptor.getName());
          if (option != null) {
            CliOptionContainer optionContainer = new CliOptionContainer(option, setter, getter, validator);
            addOption(optionContainer);
          } else {
            assert (argument != null);
            CliArgumentContainer argumentContainer = new CliArgumentContainer(argument, setter, getter, validator);
            addArgument(argumentContainer);
          }
        }
      }
    }
    return annotationFound;
  }

  /**
   * This method is like {@link #getMode(String)} but also {@link net.sf.mmm.util.cli.api.CliStyle#modeUndefined()
   * handles} the case that a {@link net.sf.mmm.util.cli.api.CliMode} may be undefined.
   *
   * @param id is the {@link net.sf.mmm.util.cli.api.CliMode#id() ID} of the requested
   *        {@link net.sf.mmm.util.cli.api.CliMode}.
   * @param annotationContainer is the {@link CliArgumentContainer} or {@link CliOptionContainer}.
   * @return the requested {@link CliModeObject}.
   */
  protected CliModeObject requireMode(String id, Object annotationContainer) {

    CliModeObject modeObject = getMode(id);
    if (modeObject == null) {
      CliStyleHandling handling = getCliStyle().modeUndefined();
      if (handling != CliStyleHandling.OK) {
        handling.handle(LOG, new CliModeUndefinedException(id, annotationContainer));
      }
      CliModeContainer modeContainer = new CliModeContainer(id);
      addMode(modeContainer);
      modeObject = modeContainer;
    }
    return modeObject;
  }

  /**
   * This method {@link #getArguments(CliModeObject) registers} the given {@link CliArgumentContainer argument}.
   *
   * @param argumentContainer is the {@link CliArgumentContainer argument} to register.
   */
  private void addArgument(CliArgumentContainer argumentContainer) {

    this.arguments.add(argumentContainer);
    String modeId = argumentContainer.getArgument().mode();
    requireMode(modeId, argumentContainer);
  }

  /**
   * This method {@link #getOptions() registers} the given {@link CliOptionContainer option}.
   *
   * @param optionContainer is the {@link CliOptionContainer option} to register.
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
   * This method {@link #getOption(String) registers} the given {@link CliOptionContainer option} with the given
   * {@code name}.
   *
   * @param nameOrAlias is the {@link CliOption#name()} or {@link CliOption#aliases() alias} of the option.
   * @param option is the {@link CliOptionContainer option} to register.
   */
  private void addOption(String nameOrAlias, CliOptionContainer option) {

    CliOptionContainer old = this.name2OptionMap.put(nameOrAlias, option);
    if (old != null) {
      throw new DuplicateObjectException(option, nameOrAlias);
    }
  }

  /**
   * This method gets the {@link CliOptionContainer option} associated with the given {@code nameOrAlias} .
   *
   * @param nameOrAlias is the {@link CliOption#name()} or {@link CliOption#aliases() alias} of the requested
   *        {@link CliOptionContainer option}.
   * @return the requested {@link CliOptionContainer option} or {@code null} if no such {@link CliOptionContainer
   *         option} exists.
   */
  public CliOptionContainer getOption(String nameOrAlias) {

    return this.name2OptionMap.get(nameOrAlias);
  }

  /**
   * This method gets the {@link List} of {@link CliOptionContainer CLI-options} .
   *
   * @return the options
   */
  public List<CliOptionContainer> getOptions() {

    return this.optionList;
  }

  /**
   * This method gets the {@link List} of all {@link CliArgumentContainer CLI-arguments}.
   *
   * @return the arguments
   */
  public Collection<CliArgumentContainer> getArguments() {

    return this.arguments;
  }

  /**
   * This method gets the {@link List} of {@link CliArgumentContainer CLI-arguments} for the given {@link CliModeObject
   * mode}.
   *
   * @param mode is the according {@link CliModeContainer mode}.
   * @return the arguments.
   */
  public List<CliArgumentContainer> getArguments(CliModeObject mode) {

    List<CliArgumentContainer> result = new ArrayList<>();
    for (CliArgumentContainer argumentContainer : this.arguments) {
      String argumentModeId = argumentContainer.getArgument().mode();
      CliModeObject argumentMode = getMode(argumentModeId);
      if ((argumentMode == mode) || argumentMode.isAncestorOf(mode)) {
        result.add(argumentContainer);
      }
    }
    return result;
  }

  // /**
  // * This method gets the {@link List} of {@link CliArgumentContainer
  // * CLI-arguments} for the given {@link CliModeObject mode}.
  // *
  // * @param mode is the according {@link CliModeContainer mode}.
  // * @return the arguments or {@code null} if no arguments are defined
  // for
  // * this mode.
  // */
  // protected List<CliArgumentContainer> getArgumentsRecursive(CliModeObject
  // mode) {
  //
  // List<CliArgumentContainer> modeArguments =
  // this.mode2argumentsMap.get(mode.getId());
  // if (modeArguments == null) {
  // CliMode cliMode = mode.getMode();
  // if (cliMode != null) {
  // for (String parentId : cliMode.parentIds()) {
  // CliModeObject parentMode = getMode(parentId);
  // modeArguments = getArgumentsRecursive(parentMode);
  // if (modeArguments != null) {
  // break;
  // }
  // }
  // }
  // }
  // return modeArguments;
  // }

  /**
   * This method gets the {@link CliOption options} for the given {@link CliModeObject mode}.
   *
   * @param mode is the {@link CliModeObject} for which the {@link CliOption options} are required.
   * @return a {@link Collection} with all {@link CliOption options} that are {@link CliOption#mode() compatible} with
   *         the given {@code mode}.
   */
  public Collection<CliOptionContainer> getOptions(CliModeObject mode) {

    String modeId = mode.getId();
    // create set of active modes...
    Set<String> modeIdSet = new HashSet<>();
    modeIdSet.add(modeId);
    Collection<CliModeContainer> extendedModes = ((CliModeContainer) mode).getExtendedModes();
    for (CliModeObject childMode : extendedModes) {
      modeIdSet.add(childMode.getId());
    }
    List<CliOptionContainer> result = new ArrayList<>();
    for (CliOptionContainer option : this.optionList) {
      if (modeIdSet.contains(option.getOption().mode())) {
        result.add(option);
      }
    }
    return result;
  }

  /**
   * This inner class converts a {@link CliArgumentContainer} to a {@link String}.
   */
  protected static final class CliArgumentFormatter implements SimpleValueConverter<CliArgumentContainer, String> {

    /** The singleton instance. */
    private static final CliArgumentFormatter INSTANCE = new CliArgumentFormatter();

    @Override
    @SuppressWarnings("all")
    public <T extends String> T convert(CliArgumentContainer value, Object valueSource, Class<T> targetClass) {

      return (T) value.getId();
    }
  }

}
