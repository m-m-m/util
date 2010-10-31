/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;

import net.sf.mmm.util.collection.base.AbstractIterator;
import net.sf.mmm.util.pojo.descriptor.base.PojoFieldIntrospector;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.base.PojoMethodIntrospector}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoFieldIntrospectorImpl extends AbstractPojoIntrospector implements
    PojoFieldIntrospector {

  /**
   * The constructor.
   */
  public PojoFieldIntrospectorImpl() {

    super();
    // for fields the default visibility is private
    setVisibility(VisibilityModifier.PRIVATE);
  }

  /**
   * The constructor. Configures and {@link #initialize() initializes} the
   * component.
   * 
   * @param visibility is the {@link #getVisibility() visibility}.
   * @param acceptStatic is the {@link #isAcceptStatic() accept-static} flag.
   */
  public PojoFieldIntrospectorImpl(VisibilityModifier visibility, boolean acceptStatic) {

    super(visibility, acceptStatic);
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<Field> findFields(Class<?> pojoType) {

    getInitializationState().requireInitilized();
    return new PojoFieldIterator(pojoType);
  }

  /**
   * This inner class iterates the fields of a given pojo-type according to the
   * {@link PojoFieldIntrospectorImpl#getVisibility() visibility}.
   */
  public class PojoFieldIterator extends AbstractIterator<Field> {

    /** the current class */
    private Class<?> currentClass;

    /** the fields. */
    private Field[] fields;

    /** the current index of {@link #fields}. */
    private int index;

    /**
     * The constructor.
     * 
     * @param pojoClass is the class for which the property-accessors should be
     *        iterated.
     */
    protected PojoFieldIterator(Class<?> pojoClass) {

      super();
      this.index = 0;
      if (getVisibility() == VisibilityModifier.PUBLIC) {
        this.currentClass = null;
        this.fields = pojoClass.getFields();
      } else {
        this.currentClass = pojoClass;
        this.fields = pojoClass.getDeclaredFields();
      }
      findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Field findNext() {

      while (this.index < this.fields.length) {
        Field field = this.fields[this.index++];
        int modifiers = field.getModifiers();
        if (isAcceptStatic() || !Modifier.isStatic(modifiers)) {
          VisibilityModifier fieldVisibility = VisibilityModifier.valueOf(modifiers);
          if (getVisibility().getOrder() <= fieldVisibility.getOrder()) {
            return field;
          }
        }
      }
      if (this.currentClass != null) {
        this.currentClass = this.currentClass.getSuperclass();
        if (this.currentClass != null) {
          this.fields = this.currentClass.getDeclaredFields();
          this.index = 0;
          return findNext();
        }
      }
      return null;
    }

  }

}
