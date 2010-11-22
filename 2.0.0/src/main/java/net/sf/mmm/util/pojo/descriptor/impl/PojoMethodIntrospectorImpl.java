/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;

import net.sf.mmm.util.collection.base.AbstractIterator;
import net.sf.mmm.util.pojo.descriptor.base.PojoMethodIntrospector;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the implementation of the {@link PojoMethodIntrospector} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoMethodIntrospectorImpl extends AbstractPojoIntrospector implements
    PojoMethodIntrospector {

  /**
   * The constructor.
   */
  public PojoMethodIntrospectorImpl() {

    super();
  }

  /**
   * The constructor. Configures and {@link #initialize() initializes} the
   * component.
   * 
   * @param visibility is the {@link #getVisibility() visibility}.
   * @param acceptStatic is the {@link #isAcceptStatic() accept-static} flag.
   */
  public PojoMethodIntrospectorImpl(VisibilityModifier visibility, boolean acceptStatic) {

    super(visibility, acceptStatic);
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<Method> findMethods(Class<?> pojoType) {

    getInitializationState().requireInitilized();
    return new PojoMethodIterator(pojoType);
  }

  /**
   * This inner class iterates the methods of a given pojo-type according to the
   * {@link PojoMethodIntrospectorImpl#getVisibility() visibility}.
   */
  public class PojoMethodIterator extends AbstractIterator<Method> {

    /** the current class */
    private Class<?> currentClass;

    /** the methods */
    private Method[] methods;

    /** the current index of {@link #methods} */
    private int index;

    /**
     * The constructor.
     * 
     * @param pojoClass is the class for which the property-accessors should be
     *        iterated.
     */
    protected PojoMethodIterator(Class<?> pojoClass) {

      super();
      this.index = 0;
      if (getVisibility() == VisibilityModifier.PUBLIC) {
        this.currentClass = null;
        this.methods = pojoClass.getMethods();
      } else {
        this.currentClass = pojoClass;
        this.methods = pojoClass.getDeclaredMethods();
      }
      findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Method findNext() {

      Method next = null;
      while (this.index < this.methods.length) {
        Method method = this.methods[this.index++];
        int modifiers = method.getModifiers();
        if (isAcceptStatic() || !Modifier.isStatic(modifiers)) {
          VisibilityModifier methodVisibility = VisibilityModifier.valueOf(modifiers);
          if (getVisibility().getOrder() <= methodVisibility.getOrder()) {
            next = method;
            break;
          }
        }
      }
      if ((next == null) && (this.currentClass != null)) {
        this.currentClass = this.currentClass.getSuperclass();
        if (this.currentClass != null) {
          this.methods = this.currentClass.getDeclaredMethods();
          this.index = 0;
          next = findNext();
        }
      }
      return next;
    }

  }

}
