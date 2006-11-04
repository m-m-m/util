/* $Id$ */
package net.sf.mmm.content.model.api;

/**
 * This interface gives write access to the content model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentModelWriteAccessIF {

  /**
   * This method creates a sub-class of the given class.
   * 
   * @param superClass
   *        is the {@link ContentClassIF#getSuperClass() super-class} of the
   *        class to create.
   * @param name
   *        is the {@link net.sf.mmm.content.api.ContentObjectIF#getName() name}
   *        of the class to create.
   * @param modifiers
   *        are the {@link ContentClassIF#getModifiers() modifiers} for the
   *        class to create.
   * @return the created class.
   * @throws ContentModelException
   *         if the class could not be created. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given class is final</li>
   *         <li>the given id already identifies another class</li>
   *         <li>both abstract and final flag are set</li>
   *         </ul>
   */
  ContentClassIF createClass(ContentClassIF superClass, String name, ClassModifiersIF modifiers)
      throws ContentModelException;

  /**
   * This method creates a field in the given class with the specified name.
   * 
   * @param declaringClass
   *        is the {@link ContentFieldIF#getDeclaringClass() class} where the
   *        new field will be added.
   * @param name
   *        is the {@link net.sf.mmm.content.api.ContentObjectIF#getName() name}
   *        of the field to create.
   * @param type
   *        is the {@link ContentFieldIF#getFieldType() type} of the values that
   *        can be stored in the field to create.
   * @param modifiers
   *        are the {@link ContentFieldIF#getModifiers() modifiers} for the
   *        field to create. They must NOT be
   *        {@link FieldModifiersIF#isTransient() transient} (@see
   *        #createField(ContentClassIF, String, Class, FieldModifiersIF,
   *        TermIF)).
   * @return the created field.
   * @throws ContentModelException
   *         if the field could not be created. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name
   *         </li>
   *         </ul>
   */
  ContentFieldIF createField(ContentClassIF declaringClass, String name, Class type,
      FieldModifiersIF modifiers) throws ContentModelException;

  /**
   * This method creates a {@link FieldModifiersIF#isTransient() transient}
   * field in the given class with the specified name.
   * 
   * @param declaringClass
   *        is the {@link ContentFieldIF#getDeclaringClass() class} where the
   *        new field will be added.
   * @param name
   *        is the {@link net.sf.mmm.content.ContentObjectIF#getName() name} of
   *        the field to create.
   * @param type
   *        is the {@link ContentFieldIF#getFieldType() type} of the values that
   *        can be stored in the field to create.
   * @param modifiers
   *        are the {@link ContentFieldIF#getModifiers() modifiers} for the
   *        field to create. They must be
   *        {@link FieldModifiersIF#isTransient() transient} (@see
   *        #createField(ContentClassIF, String, Class, FieldModifiersIF)).
   * @param term
   *        is the term used for dynamic
   *        {@link ContentFieldIF#calculate(net.sf.mmm.content.ContentObjectIF) calculation}
   *        of this {@link FieldModifiersIF#isTransient() transient} field.
   * @return the created field.
   * @throws ContentModelException
   *         if the field could not be created. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name
   *         </li>
   *         </ul>
   */
  // ContentFieldIF createField(ContentClassIF declaringClass, String name,
  // Class type,
  // FieldModifiersIF modifiers, TermIF term) throws ContentModelException;
  /**
   * This method sets the status of the deleted flag of the given class or
   * field.
   * 
   * @param classOrField
   *        is the class or field to mark as deleted.
   * @param newDeletedFlag -
   *        if <code>true</code> the class will be marked as delted, else it
   *        will be undeleted.
   * @throws ContentModelException
   *         if the deleted flag could not be modified. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given class or field has the system flag set</li>
   *         </ul>
   */
  void setDeleted(ContentReflectionObjectIF classOrField, boolean newDeletedFlag)
      throws ContentModelException;

}
