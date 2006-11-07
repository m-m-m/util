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
   * This is the same as {@link Modifiers#isFinal() final}. But additionally a
   * {@link Modifiers#isSystem() system} class that is NOT
   * {@link Modifiers#isFinal() final} can be unextendable (this method will
   * return <code>false</code> then). Such class will usually have
   * {@link ContentClass#getSubClasses() sub-classes} (that are all system
   * classes) but may not allow the user to directly extend that class. In that
   * case this method returns <code>true</code> even though
   * {@link Modifiers#isFinal()} will return <code>false</code>.
   * 
   * @return <code>true</code> if the class can be extended by the user,
   *         <code>false</code> otherwise.
   */
  boolean isExtendable();

}
