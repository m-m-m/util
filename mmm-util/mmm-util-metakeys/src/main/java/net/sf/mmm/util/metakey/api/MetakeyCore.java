/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.metakey.api;

/**
 * This interface is a collection of constants defining keys for
 * metadata-properties. <br>
 * This is the root that all such interfaces inherit from. It defines the common
 * properties and is inspired by but NOT identical to the <a
 * href="http://dublincore.org">DCMI</a>. <br>
 * All properties defined by this interface and its sub-interfaces by default
 * have values of the type {@link String}. Exceptions will be documented
 * explicitly in the javadoc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetakeyCore {

  /**
   * The official title of the content. <br>
   * <table border="1">
   * <tr>
   * <td>DCMI Name:</td>
   * <td>title</td>
   * </tr>
   * <tr>
   * <td>DCMI Definition:</td>
   * <td>A name given to the resource.</td>
   * </tr>
   * <tr>
   * <td>DCMI URI:</td>
   * <td>http://purl.org/dc/terms/title</td>
   * </tr>
   * </table>
   */
  String TITLE = "title";

  /**
   * The subtitle of the content. It informs about a specific detail of the
   * content but is NOT part of the official {@link #TITLE}. <br>
   * Examples: "Live in Budapest", "Digitally Remastered", "Limited Edition"
   */
  String SUBTITLE = "subtitle";

  /**
   * A short description (abstract) of the content. <br>
   * <table border="1">
   * <tr>
   * <td>DCMI Name:</td>
   * <td>description</td>
   * </tr>
   * <tr>
   * <td>DCMI Definition:</td>
   * <td>An account of the resource.</td>
   * </tr>
   * <tr>
   * <td>DCMI URI:</td>
   * <td>http://purl.org/dc/terms/description</td>
   * </tr>
   * </table>
   */
  String DESCRIPTION = "description";

  /**
   * The object(s) who created the content, typically the name of a person (but
   * may also be the name of a company or a software that generated the
   * content). Multiple objects are separated by commas. (e.g.
   * "Mel Brooks, Daphne Zuniga, Bill Pullman, Rick Moranis")
   * 
   * <table border="1">
   * <tr>
   * <td>DCMI Name:</td>
   * <td>creator</td>
   * </tr>
   * <tr>
   * <td>DCMI Definition:</td>
   * <td>An entity primarily responsible for making the resource.</td>
   * </tr>
   * <tr>
   * <td>DCMI URI:</td>
   * <td>http://purl.org/dc/terms/creator</td>
   * </tr>
   * </table>
   */
  String CREATOR = "creator";

  /**
   * The object(s) that published the content, typically the name of a person.
   * 
   * <table border="1">
   * <tr>
   * <td>DCMI Name:</td>
   * <td>publisher</td>
   * </tr>
   * <tr>
   * <td>DCMI Definition:</td>
   * <td>An entity responsible for making the resource available.</td>
   * </tr>
   * <tr>
   * <td>DCMI URI:</td>
   * <td>http://purl.org/dc/terms/publisher</td>
   * </tr>
   * </table>
   */
  String PUBLISHER = "publisher";

  /**
   * The object(s) that contributed for the content, typically the name of a
   * person.
   * <table border="1">
   * <tr>
   * <td>DCMI Name:</td>
   * <td>contributor</td>
   * </tr>
   * <tr>
   * <td>DCMI Definition:</td>
   * <td>An entity responsible for making contributions to the resource.</td>
   * </tr>
   * <tr>
   * <td>DCMI URI:</td>
   * <td>http://purl.org/dc/terms/contributor</td>
   * </tr>
   * </table>
   */
  String CONTRIBUTOR = "contributor";

  /**
   * The (main) language of the content according to <a
   * href="http://www.ietf.org/rfc/rfc4646.txt">RFC-4646</a>. Typically a <a
   * href="http://www.mathguide.de/info/tools/languagecode.html">two letter code
   * according to ISO-639-1</a> that may be optionally followed by hyphen and <a
   * href="http://www.mathguide.de/projekt/doku/landcode.html">two-letter code
   * according to ISO-3166</a>. The case of the letters does NOT matter. However
   * a common practice is to use lower-case for language-codes and upper-case
   * for country-codes. <br>
   * Examples are "en" or "fr-CA". <br>
   * Will NOT be set if NOT applicable (e.g. for an symbolic image or
   * instrumental music). <br>
   * URI: http://purl.org/dc/elements/1.1/language
   * <table border="1">
   * <tr>
   * <td>DCMI Name:</td>
   * <td>language</td>
   * </tr>
   * <tr>
   * <td>DCMI Definition:</td>
   * <td>A language of the resource.</td>
   * </tr>
   * <tr>
   * <td>DCMI URI:</td>
   * <td>http://purl.org/dc/terms/language</td>
   * </tr>
   * </table>
   */
  String LANGUAGE = "language";

  /**
   * The keywords (tags) that categorize this content. Subject?
   */
  String KEYWORDS = "keywords";

  /**
   * The date of creation of this content. {@link java.util.Date}-object if
   * possible; in case of string-representation in the form "YYYY[-mm-DD]".
   */
  String CREATION_DATE = "creation-date";

  /**
   * The source where this content origins from.
   */
  String SOURCE = "source";

  /**
   * The material or physical carrier of the resource. <br>
   * URI: http://purl.org/dc/terms/medium
   */
  String MEDIUM = "medium";

  /**
   * The size of the file containing the content. <br>
   * <b>ATTENTION:</b><br>
   * This property does NOT actually belong to the metadata of the content
   * itself. It is directly related to the file representing the content.
   * However there may be different forms of representation for the content.
   */
  String FILE_SIZE = "file-size";

  /**
   * The type of the file containing the content. This will typically be the <a
   * href="http://www.iana.org/assignments/media-types/">mimetype</a>. <br>
   * <b>ATTENTION:</b><br>
   * This property does NOT actually belong to the metadata of the content
   * itself. It is directly related to the file representing the content.
   * However there may be different forms of representation for the content.
   */
  String FILE_TYPE = "file-type";

  /**
   * <b>ATTENTION:</b><br>
   * This property is too generic. Please use {@link #FILE_SIZE} or
   * {@link MetakeyAudioVideo#DURATION} instead.
   * <table border="1">
   * <tr>
   * <td>DCMI Name:</td>
   * <td>extent</td>
   * </tr>
   * <tr>
   * <td>DCMI Definition:</td>
   * <td>The size or duration of the resource.</td>
   * </tr>
   * <tr>
   * <td>DCMI URI:</td>
   * <td>http://purl.org/dc/terms/extent</td>
   * </tr>
   * </table>
   */
  String EXTENT = "extent";

  /**
   * <b>ATTENTION:</b><br>
   * This property is too generic. Please use {@link #FILE_TYPE} instead.
   * <table border="1">
   * <tr>
   * <td>DCMI Name:</td>
   * <td>format</td>
   * </tr>
   * <tr>
   * <td>DCMI Definition:</td>
   * <td>The file format, physical medium, or dimensions of the resource.</td>
   * </tr>
   * <tr>
   * <td>DCMI URI:</td>
   * <td>http://purl.org/dc/terms/format</td>
   * </tr>
   * </table>
   */
  String FORMAT = "format";

}
