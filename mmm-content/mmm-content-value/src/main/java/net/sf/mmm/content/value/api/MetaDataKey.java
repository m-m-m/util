/* $Id$ */
package net.sf.mmm.content.value.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a key used to uniquely identify a value in a
 * {@link net.sf.mmm.content.value.api.MutableMetaDataSetIF}.<br>
 * An instance of this class is called qualified meta-data key and is a tuple of
 * a namespace and an unqualified key. <br>
 * Because a meta-data key is nothing but a tuple of two strings the API package
 * contains a regular class rather than an interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class MetaDataKey {

  /**
   * The string that separates the namespace from the key in the string
   * representation of a qualified meta-data key. A qualified key is of the
   * form &lt;NAMESPACE&gt;::&lt;KEY&gt;.
   */
  public static final String SEPARATOR = "::";

  /**
   * The pattern that specifies the syntax of a qualified key as string.
   * 
   * @see MetaDataKey#toString()
   */
  public static final Pattern PATTERN = Pattern.compile("(.*)" + SEPARATOR
      + "([a-zA-Z][a-zA-Z0-9-_.]*)");

  /**
   * The default namespace of the project. MUST ONLY be used for project
   * internal meta-data (no thridparty plugins, etc.).
   */
  public static final String NAMESPACE_PROJECT = "m-m-m.sf.net/metadata";

  /** the namespace */
  private final String namespace;

  /** the unqualified key */
  private final String key;

  /**
   * The constructor.
   * 
   * @param vendorNamespace
   *        is the namespace as described in
   *        {@link MetaDataKey#getNamespace()}.
   * @param unqualifiedKey
   *        is the unqualified key as described in
   *        {@link MetaDataKey#getKey()}
   * @throws MetaDataKeyException
   *         if the qualified key given as namespace/key pair does not
   *         represent a qualified key as specified by
   *         MetaDataSet.MetaDataKey#PATTERN.
   */
  public MetaDataKey(String vendorNamespace, String unqualifiedKey) throws MetaDataKeyException {

    super();
    this.namespace = vendorNamespace;
    this.key = unqualifiedKey;
    if (!PATTERN.matcher(toString()).matches()) {
      throw new MetaDataKeyException(toString());
    }
  }

  /**
   * The constructor.
   * 
   * @param qualifiedKeyAsString
   *        is the qualified key as string as returned by
   *        {@link MetaDataKey#toString()}.
   * @throws MetaDataKeyException
   *         if the given key does not represent a qualified key as specified
   *         by MetaDataSet.MetaDataKey#PATTERN.
   */
  public MetaDataKey(String qualifiedKeyAsString) throws MetaDataKeyException {

    super();
    Matcher matcher = PATTERN.matcher(qualifiedKeyAsString);
    if (matcher.matches()) {
      this.namespace = matcher.group(1);
      this.key = matcher.group(2);
    } else {
      throw new MetaDataKeyException(qualifiedKeyAsString);
    }
  }

  /**
   * This method gets the unqualified key. It should be a short english term
   * describing the meaning of the associated meta-data value. It MUST be
   * unqiue in combination with the namespace. The key MUST NOT contain the
   * string {@link MetaDataKey#SEPARATOR}.
   * 
   * @return the unqualified key.
   */
  public String getKey() {

    return this.key;
  }

  /**
   * This method gets the namespace. <br>
   * Namespaces are used to avoid conflicts with keys from different vendors.
   * Therefore each vendor should have its own unique namespace. Like in
   * XML-namespaces or in JAVA-package-names it is recomendet to use things as
   * internet domains as namespace. The namespace MUST NOT contain the string
   * {@link MetaDataKey#SEPARATOR}.
   * 
   * @return the namespace.
   */
  public String getNamespace() {

    return this.namespace;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {

    if ((obj != null) && (obj.getClass() == MetaDataKey.class)) {
      return (toString().equals(((MetaDataKey) obj).toString()));
    }
    return false;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    return toString().hashCode();
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return this.namespace + SEPARATOR + this.key;
  }

}
