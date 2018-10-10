/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.cli.api.CliClass;
import net.sf.mmm.util.cli.api.CliContainerStyle;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliModes;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.component.base.InitializationState;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * A {@link CliClassContainer} determines and holds the class-specific CLI-informations of a {@link #getStateClass()
 * state-class}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliClassContainer {

  private static final Logger LOG = LoggerFactory.getLogger(CliClassContainer.class);

  private final Class<?> stateClass;

  private final CliStyle cliStyle;

  private final CliClass cliClass;

  private final Map<String, CliModeContainer> id2ModeMap;

  private final String name;

  /**
   * The constructor.
   *
   * @param stateClass is the {@link #getStateClass() state-class}.
   */
  public CliClassContainer(Class<?> stateClass) {

    super();
    this.stateClass = stateClass;
    this.id2ModeMap = new HashMap<>();
    CliStyle cliStyleAnnotation = null;
    CliClass cliClassAnnotation = null;
    Class<?> currentClass = stateClass;
    while (currentClass != null) {
      if (cliStyleAnnotation == null) {
        cliStyleAnnotation = currentClass.getAnnotation(CliStyle.class);
      }
      if (cliClassAnnotation == null) {
        cliClassAnnotation = currentClass.getAnnotation(CliClass.class);
      }
      currentClass = currentClass.getSuperclass();
    }

    if (cliStyleAnnotation == null) {
      cliStyleAnnotation = CliDefaultAnnotations.CLI_STYLE;
    }
    if (cliStyleAnnotation.containerStyle() == CliContainerStyle.DEFAULT) {
      throw new NlsIllegalArgumentException(CliContainerStyle.DEFAULT, "@" + CliStyle.class.getSimpleName() + ".containerStyle()");
    }
    this.cliStyle = cliStyleAnnotation;
    if (cliClassAnnotation == null) {
      cliClassAnnotation = CliDefaultAnnotations.CLI_CLASS;
    }
    this.cliClass = cliClassAnnotation;
    if (this.cliClass.name().length() == 0) {
      this.name = this.stateClass.getName();
    } else {
      this.name = this.cliClass.name();
    }

    currentClass = stateClass;
    while (currentClass != null) {
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
    for (CliModeContainer modeContainer : this.id2ModeMap.values()) {
      List<String> cycle = initializeModeRecursive(modeContainer);
      if (cycle != null) {
        StringBuilder sb = new StringBuilder();
        for (int i = cycle.size() - 1; i >= 0; i--) {
          sb.append(cycle.get(i));
          if (i > 0) {
            sb.append("-->");
          }
        }
        throw new IllegalStateException("Cyclic dependency of CLI modes: " + sb.toString());
      }
    }
  }

  /**
   * This method initializes the given {@link CliModeContainer}.
   *
   * @param mode is the {@link CliModeContainer} to initialize.
   * @return a {@link List} of {@link CliModeContainer#getId() CLI mode IDs} (in reverse order) of a cyclic dependency
   *         that was detected, or {@code null} if the initialization was successful.
   */
  protected List<String> initializeModeRecursive(CliModeContainer mode) {

    InitializationState state = mode.getState();
    if (!state.isInitialized()) {
      if (state.isInitializing()) {
        // cycle detected
        List<String> cycle = new ArrayList<>();
        cycle.add(mode.getId());
        return cycle;
      } else {
        state.setInitializing();
      }
      for (String parentId : mode.getMode().parentIds()) {
        CliModeContainer parentContainer = this.id2ModeMap.get(parentId);
        if (parentContainer == null) {
          throw new ObjectNotFoundException(CliMode.class, parentId);
        }
        List<String> cycle = initializeModeRecursive(parentContainer);
        if (cycle != null) {
          cycle.add(mode.getId());
          return cycle;
        }
        mode.getExtendedModes().addAll(parentContainer.getExtendedModes());
      }
      state.setInitialized();
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

    CliModeObject old = this.id2ModeMap.put(mode.getId(), mode);
    if (old != null) {
      CliStyleHandling handling = this.cliStyle.modeDuplicated();
      DuplicateObjectException exception = new DuplicateObjectException(mode, mode.getMode().id());
      if (handling != CliStyleHandling.OK) {
        handling.handle(LOG, exception);
      }
    }
  }

  /**
   * This method gets the {@link Class} reflecting the {@link AbstractCliParser#getState() state-object}.
   *
   * @return the state-class.
   */
  public Class<?> getStateClass() {

    return this.stateClass;
  }

  /**
   * This method gets the {@link CliStyle style} configured for the {@link #getStateClass() state-class}. If no such
   * annotation is present, a default instance is returned.
   *
   * @return the {@link CliStyle}.
   */
  public CliStyle getCliStyle() {

    return this.cliStyle;
  }

  /**
   * This method gets the {@link CliClass} configured for the {@link #getStateClass() state-class}. If no such
   * annotation is present, a default instance is returned.
   *
   * @return the {@link CliClass}.
   */
  public CliClass getCliClass() {

    return this.cliClass;
  }

  /**
   * This method gets the {@link CliModeContainer mode} associated with the given {@link CliMode#id() ID}.
   *
   * @param id the {@link CliMode#id() ID} of the requested {@link CliMode}.
   * @return the requested {@link CliMode} or {@code null} if no such {@link CliMode} exists.
   */
  public CliModeObject getMode(String id) {

    return this.id2ModeMap.get(id);
  }

  /**
   * This method gets the {@link Collection} with the {@link CliMode#id() IDs} of all {@link #getMode(String)
   * registered} {@link CliMode}s.
   *
   * @return the mode-IDs.
   */
  public Collection<String> getModeIds() {

    return this.id2ModeMap.keySet();
  }

  /**
   * This method gets the {@link CliClass#name() name} configured for the {@link #getStateClass() state-class} .
   *
   * @return the annotated {@link CliClass#name() name} or the {@link Class#getName() class-name} of the
   *         {@link #getStateClass() state-class} if NOT configured.
   */
  public String getName() {

    return this.name;
  }

  /**
   * This inner class is a dummy for getting default instances of CLI annotations.
   */
  @CliClass
  @CliStyle
  private static final class CliDefaultAnnotations {

    /** The default instance of {@link CliClass}. */
    private static final CliClass CLI_CLASS = CliDefaultAnnotations.class.getAnnotation(CliClass.class);

    /** The default instance of {@link CliStyle}. */
    private static final CliStyle CLI_STYLE = CliDefaultAnnotations.class.getAnnotation(CliStyle.class);

    /**
     * The forbidden constructor.
     */
    private CliDefaultAnnotations() {

      throw new IllegalStateException();
    }
  }

}
