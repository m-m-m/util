package java.util;

//import com.google.gwt.i18n.client.LocaleInfo;

public final class Locale {
  private final String variant;
  private final String country;
  private final String language;
  public Locale(String language) {
    this(language, "", "");
  }
  public Locale(String language, String country) {
    this(language, country, "");
  }
  public Locale(String language, String country, String variant) {
    this.language = language;
    this.country = country;
    this.variant = variant;
  }
  public static Locale getDefault() {
    return null;
//    String localeName = LocaleInfo.getCurrentLocale().getLocaleName();
//    if ((localeName == null) || (localeName.length() == 0) || localeName.equals("default")) {
//      return new Locale("");
//    }
//    String language = localeName;
//    String country = "";
//    String variant = "";
//    int underscore = language.indexOf('_');
//    if (underscore > 0) {
//      country = localeName.substring(underscore + 1);
//      language = localeName.substring(0, underscore);
//      underscore = country.indexOf('_');
//      if (underscore > 0) {
//        variant = country.substring(underscore + 1);
//        country = country.substring(0, underscore);
//      }
//    }
//    return new Locale(language, country, variant);
  }
  public String getCountry() {
    return country;
  }
  public String getLanguage() {
    return language;
  }
  public String getVariant() {
    return variant;
  }  
}