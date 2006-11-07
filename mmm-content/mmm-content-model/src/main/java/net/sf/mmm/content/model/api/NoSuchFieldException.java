/* $ Id: $ */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.NlsResourceBundle;

/**
 * This exception is thrown if a
 * {@link net.sf.mmm.content.model.api.ContentField field} was requested that
 * does not exist (in the according
 * {@link net.sf.mmm.content.model.api.ContentClass content-class}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NoSuchFieldException extends ContentModelException {

  /** uid for serialization */
  private static final long serialVersionUID = 8593724225377132253L;

  /**
   * The constructor.
   * 
   * @param fieldName
   *        is the {@link net.sf.mmm.content.api.ContentObject#getName() name}
   *        of the missing {@link ContentField field}.
   * @param declaringClass
   *        is the {@link ContentClass content-class} where the missing field
   *        was expected.
   */
  public NoSuchFieldException(String fieldName, ContentClass declaringClass) {

    super(NlsResourceBundle.ERR_NO_SUCH_FIELD, fieldName, declaringClass);
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.content.api.ContentObject#getName() name} of the
   * {@link ContentField field} that was not found.
   * 
   * @return the name of the missing field.
   */
  public String getFieldName() {

    return (String) getNlsMessage().getArgument(0);
  }

  /**
   * This method gets the {@link ContentClass content-class} where the missing
   * field was expected.
   * 
   * @return the class where the missing field was expected.
   */
  public ContentClass getContentClass() {

    return (ContentClass) getNlsMessage().getArgument(1);
  }

}
