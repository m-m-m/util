/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.mmm.util.collection.base.AbstractIterator;
import net.sf.mmm.util.pojo.descriptor.base.PojoMethodIntrospector;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the implementation of the {@link PojoMethodIntrospector} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoMethodIntrospectorImpl extends AbstractPojoIntrospector implements PojoMethodIntrospector {

  /**
   * The constructor.
   */
  public PojoMethodIntrospectorImpl() {

    super();
  }

  /**
   * The constructor. Configures and {@link #initialize() initializes} the component.
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
  @Override
  public Iterator<Method> findMethods(Class<?> pojoType) {

    getInitializationState().requireInitilized();
    return new PojoMethodIterator(pojoType);
  }

  /**
   * Recursively collects all {@link Class#getInterfaces() interfaces} starting from the given <code>type</code>.
   *
   * @param type is the {@link Class} for which all {@link Class#getInterfaces() interfaces} shall be collected.
   * @param interfaceSet is the {@link Set} where the {@link Class#getInterfaces() interfaces} will be
   *        {@link Set#add(Object) added}.
   * @param excludeSet is a {@link Set} with {@link Class}es (interfaces) to omit. May be <code>null</code> for none.
   */
  private static void collectInterfaces(Class<?> type, Set<Class<?>> interfaceSet, Set<Class<?>> excludeSet) {

    for (Class<?> interfaceClass : type.getInterfaces()) {
      if ((excludeSet == null) || (!excludeSet.contains(interfaceClass))) {
        interfaceSet.add(interfaceClass);
      }
      collectInterfaces(interfaceClass, interfaceSet, excludeSet);
    }
  }

  /**
   * This inner class iterates the methods of a given pojo-type according to the
   * {@link PojoMethodIntrospectorImpl#getVisibility() visibility}.
   */
  public class PojoMethodIterator extends AbstractIterator<Method> {

    /** the current class */
    private Iterator<Class<?>> interfaces;

    /** the methods */
    private Method[] methods;

    /** the current index of {@link #methods} */
    private int index;

    /**
     * The constructor.
     *
     * @param pojoClass is the class for which the property-accessors should be iterated.
     */
    protected PojoMethodIterator(Class<?> pojoClass) {

      super();
      this.index = 0;
      this.methods = pojoClass.getDeclaredMethods();
      if (Modifier.isAbstract(pojoClass.getModifiers())) {
        Set<Class<?>> superInterfaceSet = null;
        Class<?> superClass = pojoClass.getSuperclass();
        if (superClass != null) {
          superInterfaceSet = new HashSet<>();
          collectInterfaces(superClass, superInterfaceSet, null);
        }
        Set<Class<?>> interfaceSet = new HashSet<>();
        collectInterfaces(pojoClass, interfaceSet, superInterfaceSet);
        this.interfaces = interfaceSet.iterator();
      } else {
        this.interfaces = null;
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
      if ((next == null) && (this.interfaces != null)) {
        if (this.interfaces.hasNext()) {
          this.methods = this.interfaces.next().getDeclaredMethods();
          this.index = 0;
          next = findNext();
        }
      }
      return next;
    }

  }

}
