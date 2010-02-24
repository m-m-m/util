/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.cli.api.CliClass;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeCycleException;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliModes;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.component.api.InitState;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * A {@link CliClassContainer} determines and holds the class-specific
 * CLI-informations of a {@link #getStateClass() state-class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliClassContainer {

  /** @see #getStateClass() */
  private final Class<?> stateClass;

  /** @see #getStyle() */
  private final CliStyle style;

  /** @see #getUsage() */
  private final String usage;

  /** @see #getName() */
  private final String name;

  /** @see #getMode(String) */
  private final Map<String, CliModeContainer> id2ModeMap;

  /**
   * The constructor.
   * 
   * @param stateClass is the {@link #getStateClass() state-class}.
   */
  public CliClassContainer(Class<?> stateClass) {

    super();
    this.stateClass = stateClass;
    this.id2ModeMap = new HashMap<String, CliModeContainer>();
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
    if (cliName == null) {
      cliName = stateClass.getName();
    }
    this.name = cliName;
    this.usage = cliUsage;
    for (CliModeContainer modeContainer : this.id2ModeMap.values()) {
      initializeRecursive(modeContainer);
    }
  }

  /**
   * This method initializes the given {@link CliModeContainer}.
   * 
   * @param mode is the {@link CliModeContainer} to initialize.
   * @return a {@link CliModeCycle} if a cyclic dependency has been detected but
   *         is NOT yet complete or <code>null</code> if the initialization was
   *         successful.
   * @throws CliModeCycleException if a cyclic dependency was detected and
   *         completed.
   */
  private CliModeCycle initializeRecursive(CliModeContainer mode) throws CliModeCycleException {

    InitState initState = mode.getState();
    if (initState != InitState.INITIALIZED) {
      if (initState == InitState.INITIALIZING) {
        // cycle detected
        return new CliModeCycle(mode);
      } else {
        mode.setState(InitState.INITIALIZING);
      }
      for (String parentId : mode.getMode().parentIds()) {
        CliModeContainer parentContainer = this.id2ModeMap.get(parentId);
        if (parentContainer == null) {
          throw new ObjectNotFoundException(CliMode.class, parentId);
        }
        CliModeCycle cycle = initializeRecursive(parentContainer);
        if (cycle != null) {
          cycle.inverseCycle.add(mode);
          if (cycle.startNode == mode) {
            throw new CliModeCycleException(cycle);
          }
          return cycle;
        }
        mode.getExtendedModes().addAll(parentContainer.getExtendedModes());
      }
    }
    return null;
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
  protected void addMode(CliModeContainer mode) {

    CliModeObject old = this.id2ModeMap.put(mode.getMode().id(), mode);
    if (old != null) {
      throw new DuplicateObjectException(mode, mode.getMode().id());
    }
  }

  /**
   * This method gets the {@link Class} reflecting the
   * {@link AbstractCliParser#getState() state-object}.
   * 
   * @return the state-class.
   */
  public Class<?> getStateClass() {

    return this.stateClass;
  }

  /**
   * This method gets the {@link CliClass#style() style} configured for the
   * {@link #getStateClass() state-class}.
   * 
   * @return the {@link CliClass#style() style}.
   */
  public CliStyle getStyle() {

    return this.style;
  }

  /**
   * This method gets the {@link CliClass#name() name} configured for the
   * {@link #getStateClass() state-class}.
   * 
   * @return the {@link CliClass#name() name}.
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method gets the {@link CliClass#usage() usage} configured for the
   * {@link #getStateClass() state-class}.
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
   * This method gets the {@link Collection} with the {@link CliMode#id() IDs}
   * of all {@link #getMode(String) registered} {@link CliMode}s.
   * 
   * @return the mode-IDs.
   */
  public Collection<String> getModeIds() {

    return this.id2ModeMap.keySet();
  }

  /**
   * This inner class is used to detect a cyclic {@link CliMode#parentIds()
   * dependency} of {@link CliMode}s.
   */
  protected static class CliModeCycle {

    /**
     * The {@link List} of {@link CliModeContainer modes} that build a cycle in
     * reverse order.
     */
    private List<CliModeContainer> inverseCycle;

    /** The start node where the detected cycle begins. */
    private CliModeContainer startNode;

    /**
     * The constructor.
     * 
     * @param startNode is the {@link #startNode}.
     */
    public CliModeCycle(CliModeContainer startNode) {

      super();
      this.startNode = startNode;
      this.inverseCycle = new ArrayList<CliModeContainer>();
      this.inverseCycle.add(this.startNode);
    }

    /**
     * @return the inverseCycle
     */
    public List<CliModeContainer> getInverseCycle() {

      return this.inverseCycle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      StringBuilder sb = new StringBuilder();
      for (int i = this.inverseCycle.size() - 1; i >= 0; i--) {
        CliModeContainer container = this.inverseCycle.get(i);
        sb.append(container.getMode().id());
        if (i > 0) {
          sb.append("-->");
        }
      }
      return sb.toString();
    }
  }

}
