/* $ Id: $ */
package net.sf.mmm.content.model.api;

/**
 * This is the interface for the modifiers of a
 * {@link net.sf.mmm.content.model.api.ContentClass content-class}.
 * 
 * @see net.sf.mmm.content.model.api.ContentClass#getModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ClassModifiers extends Modifiers {

  /**
   * the name of the root tag.
   */
  String XML_TAG_ROOT = "classModifiers";

  /**
   * the attribute for the {@link #isAbstract() abstract-flag}.
   */
  String XML_ATR_ROOT_ABSTRACT = "abstract";

  /**
   * the attribute for the {@link #isExtendable() extendable-flag}.
   */
  String XML_ATR_ROOT_EXTENDABLE = "extendable";

  /**
   * This method determines if the class is abstract and no
   * {@link net.sf.mmm.content.api.ContentObject instance} can be created
   * directly from this class.
   * 
   * @return <code>true</code> if the class is abstract.
   */
  boolean isAbstract();

  /**
   * This method determines if the class can be
   * {@link ContentModelWriteAccess#createClass(ContentClass, String, ClassModifiers) extendet}
   * by the user.<br>
   * A {@link Modifiers#isFinal() final} class is NOT extendable. In advance a
   * {@link Modifiers#isSystem() system} class that is NOT
   * {@link Modifiers#isFinal() final} can have
   * {@link ContentClass#getSubClasses() sub-classes} but may NOT be extendable.
   * These {@link ContentClass#getSubClasses() sub-classes} will also be
   * {@link Modifiers#isSystem() system} classes. In that case this method
   * returns <code>false</code> even though {@link #isFinal()} will return
   * <code>false</code>.
   * 
   * @return <code>true</code> if the class can be extended by the user,
   *         <code>false</code> otherwise.
   */
  boolean isExtendable();

}
