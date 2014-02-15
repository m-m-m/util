/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import java.util.Set;

import javax.inject.Inject;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.base.ConjunctionFilter;
import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.lang.api.StringTokenizer;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.AnnotationFilter;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * This class implements a {@link PersistenceUnitPostProcessor} that locates {@link Entity entities} and
 * registers them to the persistence unit info.<br/>
 * <b>INFO:</b><br/>
 * This class only exists due to the wired specification of the JPA that defines auto-location of entities but
 * for some unknown reason not across multiple modules (jar-files). However, the latter case is desired for a
 * reasonable application with a modular design. Therefore this is a workaround to solve the problem.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public class EntityLocator extends AbstractLoggableComponent implements PersistenceUnitPostProcessor {

  /** @see #scanEntities(String) */
  private final Filter<Class<?>> entityFilter;

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /** @see #getPersistenceBasePackages() */
  private String persistenceBasePackages;

  /**
   * The constructor.
   */
  public EntityLocator() {

    super();
    this.entityFilter = new ConjunctionFilter<Class<?>>(Conjunction.OR, new AnnotationFilter(Entity.class),
        new AnnotationFilter(Embeddable.class), new AnnotationFilter(MappedSuperclass.class));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.persistenceBasePackages == null) {
      // fallback: scan all packages...
      this.persistenceBasePackages = "";
    }
  }

  /**
   * This method locates all {@link Entity entities} (including {@link Embeddable}s and
   * {@link MappedSuperclass}es) in the given package including its sub-packages.
   * 
   * @param packageName is the name of the {@link Package} to scan.
   * @return the set of entity classes.
   */
  protected Set<Class<?>> scanEntities(String packageName) {

    boolean includeSubPackages = true;
    Set<String> allClassNames = this.reflectionUtil.findClassNames(packageName, includeSubPackages);
    Set<Class<?>> entityClasses = this.reflectionUtil.loadClasses(allClassNames, this.entityFilter);
    return entityClasses;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {

    StringTokenizer tokenizer = new StringTokenizer(this.persistenceBasePackages, ',');
    for (String packageName : tokenizer) {
      Set<Class<?>> entityClasses = scanEntities(packageName);
      for (Class<?> entity : entityClasses) {
        pui.addManagedClassName(entity.getName());
      }
    }
  }

  /**
   * @return the instance of {@link ReflectionUtil}.
   */
  protected ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the {@link ReflectionUtil} to inject.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * @return the persistenceBasePackage
   */
  protected String getPersistenceBasePackages() {

    return this.persistenceBasePackages;
  }

  /**
   * @param persistenceBasePackages is the comma separated list of packages to scan for persistent entities.
   */
  public void setPersistenceBasePackages(String persistenceBasePackages) {

    this.persistenceBasePackages = persistenceBasePackages;
  }

}
