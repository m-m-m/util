/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

import net.sf.mmm.data.api.DataSelectionTree;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassCachingStrategy;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for an {@link net.sf.mmm.data.api.entity.DataEntity} that represents the genre of an
 * {@link DataAudio audio track}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataAudioGenre.CLASS_ID, title = DataAudioGenre.CLASS_TITLE, //
isFinal = BooleanEnum.TRUE, cachingStrategy = DataClassCachingStrategy.FULLY_MUTABLE)
public interface DataAudioGenre extends DataSelectionTree<DataAudioGenre> {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_AUDIOGENRE;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataSelectionGenericTree";

  /**
   * This method gets the ID of the best matching ID3 genre. The official standard ID3v1 introduced the genre
   * IDs from <code>0</code> to <code>79</code>. The genre IDs from <code>80</code> to <code>147</code> have
   * been added by Winamp and became more or less defacto standard. However, these genre lists are incomplete
   * and also contain entries that are mixtures of genres with other attributes (like
   * "Christian Gangsta Rap").<br/>
   * In mmm the {@link DataAudioGenre genres} are organized as tree that allows to add fine grained custom
   * genres derived from official genres. Further, other aspects (like if an {@link DataAudio audio track} is
   * instrumental or religious) can be expressed by additional attributes in addition to the genre.<br/>
   * The following table shows the list of these ID3 genres.
   * 
   * <table border="1">
   * <tr>
   * <th>ID</th>
   * <th>Genre</th>
   * </tr>
   * <tr>
   * <td>000</td>
   * <td>Blues</td>
   * </tr>
   * <tr>
   * <td>001</td>
   * <td>Classic Rock</td>
   * </tr>
   * <tr>
   * <td>002</td>
   * <td>Country</td>
   * </tr>
   * <tr>
   * <td>003</td>
   * <td>Dance</td>
   * </tr>
   * <tr>
   * <td>004</td>
   * <td>Disco</td>
   * </tr>
   * <tr>
   * <td>005</td>
   * <td>Funk</td>
   * </tr>
   * <tr>
   * <td>006</td>
   * <td>Grunge</td>
   * </tr>
   * <tr>
   * <td>007</td>
   * <td>Hip-Hop</td>
   * </tr>
   * <tr>
   * <td>008</td>
   * <td>Jazz</td>
   * </tr>
   * <tr>
   * <td>009</td>
   * <td>Metal</td>
   * </tr>
   * <tr>
   * <td>010</td>
   * <td>New Age</td>
   * </tr>
   * <tr>
   * <td>011</td>
   * <td>Oldies</td>
   * </tr>
   * <tr>
   * <td>012</td>
   * <td>Other</td>
   * </tr>
   * <tr>
   * <td>013</td>
   * <td>Pop</td>
   * </tr>
   * <tr>
   * <td>014</td>
   * <td>Rythm and Blues</td>
   * </tr>
   * <tr>
   * <td>015</td>
   * <td>Rap</td>
   * </tr>
   * <tr>
   * <td>016</td>
   * <td>Raggae</td>
   * </tr>
   * <tr>
   * <td>017</td>
   * <td>Rock</td>
   * </tr>
   * <tr>
   * <td>018</td>
   * <td>Techno</td>
   * </tr>
   * <tr>
   * <td>019</td>
   * <td>Industrial</td>
   * </tr>
   * <tr>
   * <td>020</td>
   * <td>Alternative</td>
   * </tr>
   * <tr>
   * <td>021</td>
   * <td>Ska</td>
   * </tr>
   * <tr>
   * <td>022</td>
   * <td>Death Metal</td>
   * </tr>
   * <tr>
   * <td>023</td>
   * <td>Pranks</td>
   * </tr>
   * <tr>
   * <td>024</td>
   * <td>Soundtrack</td>
   * </tr>
   * <tr>
   * <td>025</td>
   * <td>Euro-Techno</td>
   * </tr>
   * <tr>
   * <td>026</td>
   * <td>Ambient</td>
   * </tr>
   * <tr>
   * <td>027</td>
   * <td>Trip-Hop</td>
   * </tr>
   * <tr>
   * <td>028</td>
   * <td>Vocal</td>
   * </tr>
   * <tr>
   * <td>029</td>
   * <td>Jazz&amp;Funk</td>
   * </tr>
   * <tr>
   * <td>030</td>
   * <td>Fusion</td>
   * </tr>
   * <tr>
   * <td>031</td>
   * <td>Trance</td>
   * </tr>
   * <tr>
   * <td>032</td>
   * <td>Classical</td>
   * </tr>
   * <tr>
   * <td>033</td>
   * <td>Instrumental</td>
   * </tr>
   * <tr>
   * <td>034</td>
   * <td>Acid</td>
   * </tr>
   * <tr>
   * <td>035</td>
   * <td>House</td>
   * </tr>
   * <tr>
   * <td>036</td>
   * <td>Game</td>
   * </tr>
   * <tr>
   * <td>037</td>
   * <td>Sound Clip</td>
   * </tr>
   * <tr>
   * <td>038</td>
   * <td>Gospel</td>
   * </tr>
   * <tr>
   * <td>039</td>
   * <td>Noise</td>
   * </tr>
   * <tr>
   * <td>040</td>
   * <td>Alternative Rock</td>
   * </tr>
   * <tr>
   * <td>041</td>
   * <td>Bass</td>
   * </tr>
   * <tr>
   * <td>042</td>
   * <td>Soul</td>
   * </tr>
   * <tr>
   * <td>043</td>
   * <td>Punk</td>
   * </tr>
   * <tr>
   * <td>044</td>
   * <td>Space</td>
   * </tr>
   * <tr>
   * <td>045</td>
   * <td>Mediative</td>
   * </tr>
   * <tr>
   * <td>046</td>
   * <td>Instrumental Pop</td>
   * </tr>
   * <tr>
   * <td>047</td>
   * <td>Instrumental Rock</td>
   * </tr>
   * <tr>
   * <td>048</td>
   * <td>Ethnic</td>
   * </tr>
   * <tr>
   * <td>049</td>
   * <td>Gothic</td>
   * </tr>
   * <tr>
   * <td>050</td>
   * <td>Darkwave</td>
   * </tr>
   * <tr>
   * <td>051</td>
   * <td>Techno-Industrial</td>
   * </tr>
   * <tr>
   * <td>052</td>
   * <td>Electronic</td>
   * </tr>
   * <tr>
   * <td>053</td>
   * <td>Pop-Folk</td>
   * </tr>
   * <tr>
   * <td>054</td>
   * <td>Eurodance</td>
   * </tr>
   * <tr>
   * <td>055</td>
   * <td>Dream</td>
   * </tr>
   * <tr>
   * <td>056</td>
   * <td>Southern Rock</td>
   * </tr>
   * <tr>
   * <td>057</td>
   * <td>Comedy</td>
   * </tr>
   * <tr>
   * <td>058</td>
   * <td>Cult</td>
   * </tr>
   * <tr>
   * <td>059</td>
   * <td>Gangsta</td>
   * </tr>
   * <tr>
   * <td>060</td>
   * <td>Top 40</td>
   * </tr>
   * <tr>
   * <td>061</td>
   * <td>Christian Rap</td>
   * </tr>
   * <tr>
   * <td>062</td>
   * <td>Pop/Funk</td>
   * </tr>
   * <tr>
   * <td>063</td>
   * <td>Jungle</td>
   * </tr>
   * <tr>
   * <td>064</td>
   * <td>Native US</td>
   * </tr>
   * <tr>
   * <td>065</td>
   * <td>Cabaret</td>
   * </tr>
   * <tr>
   * <td>066</td>
   * <td>New Wave</td>
   * </tr>
   * <tr>
   * <td>067</td>
   * <td>Psychedelic</td>
   * </tr>
   * <tr>
   * <td>068</td>
   * <td>Rave</td>
   * </tr>
   * <tr>
   * <td>069</td>
   * <td>Showtunes</td>
   * </tr>
   * <tr>
   * <td>070</td>
   * <td>Trailer</td>
   * </tr>
   * <tr>
   * <td>071</td>
   * <td>Lo-Fi</td>
   * </tr>
   * <tr>
   * <td>072</td>
   * <td>Tribal</td>
   * </tr>
   * <tr>
   * <td>073</td>
   * <td>Acid Punk</td>
   * </tr>
   * <tr>
   * <td>074</td>
   * <td>Acid Jazz</td>
   * </tr>
   * <tr>
   * <td>075</td>
   * <td>Polka</td>
   * </tr>
   * <tr>
   * <td>076</td>
   * <td>Retro</td>
   * </tr>
   * <tr>
   * <td>077</td>
   * <td>Musical</td>
   * </tr>
   * <tr>
   * <td>078</td>
   * <td>Rock &amp; Roll</td>
   * </tr>
   * <tr>
   * <td>079</td>
   * <td>Hard Rock</td>
   * </tr>
   * <tr>
   * <td>080</td>
   * <td>Folk</td>
   * </tr>
   * <tr>
   * <td>081</td>
   * <td>Folk-Rock</td>
   * </tr>
   * <tr>
   * <td>082</td>
   * <td>National Folk</td>
   * </tr>
   * <tr>
   * <td>083</td>
   * <td>Swing</td>
   * </tr>
   * <tr>
   * <td>084</td>
   * <td>Fast Fusion</td>
   * </tr>
   * <tr>
   * <td>085</td>
   * <td>Bebop</td>
   * </tr>
   * <tr>
   * <td>086</td>
   * <td>Latin</td>
   * </tr>
   * <tr>
   * <td>087</td>
   * <td>Revival</td>
   * </tr>
   * <tr>
   * <td>088</td>
   * <td>Celtic</td>
   * </tr>
   * <tr>
   * <td>089</td>
   * <td>Bluegrass</td>
   * </tr>
   * <tr>
   * <td>090</td>
   * <td>Avantgarde</td>
   * </tr>
   * <tr>
   * <td>091</td>
   * <td>Gothic Rock</td>
   * </tr>
   * <tr>
   * <td>092</td>
   * <td>Progressive Rock</td>
   * </tr>
   * <tr>
   * <td>093</td>
   * <td>Psychedelic Rock</td>
   * </tr>
   * <tr>
   * <td>094</td>
   * <td>Symphonic Rock</td>
   * </tr>
   * <tr>
   * <td>095</td>
   * <td>Slow Rock</td>
   * </tr>
   * <tr>
   * <td>096</td>
   * <td>Big Band</td>
   * </tr>
   * <tr>
   * <td>097</td>
   * <td>Chorus</td>
   * </tr>
   * <tr>
   * <td>098</td>
   * <td>Easy Listening</td>
   * </tr>
   * <tr>
   * <td>099</td>
   * <td>Acoustic</td>
   * </tr>
   * <tr>
   * <td>100</td>
   * <td>Humor</td>
   * </tr>
   * <tr>
   * <td>101</td>
   * <td>Speech</td>
   * </tr>
   * <tr>
   * <td>102</td>
   * <td>Chanson</td>
   * </tr>
   * <tr>
   * <td>103</td>
   * <td>Opera</td>
   * </tr>
   * <tr>
   * <td>104</td>
   * <td>Chamber Music</td>
   * </tr>
   * <tr>
   * <td>105</td>
   * <td>Sonata</td>
   * </tr>
   * <tr>
   * <td>106</td>
   * <td>Symphony</td>
   * </tr>
   * <tr>
   * <td>107</td>
   * <td>Booty Bass</td>
   * </tr>
   * <tr>
   * <td>108</td>
   * <td>Primus</td>
   * </tr>
   * <tr>
   * <td>109</td>
   * <td>Porn Groove</td>
   * </tr>
   * <tr>
   * <td>110</td>
   * <td>Satire</td>
   * </tr>
   * <tr>
   * <td>111</td>
   * <td>Slow Jam</td>
   * </tr>
   * <tr>
   * <td>112</td>
   * <td>Club</td>
   * </tr>
   * <tr>
   * <td>113</td>
   * <td>Tango</td>
   * </tr>
   * <tr>
   * <td>114</td>
   * <td>Samba</td>
   * </tr>
   * <tr>
   * <td>115</td>
   * <td>Folklore</td>
   * </tr>
   * <tr>
   * <td>116</td>
   * <td>Ballad</td>
   * </tr>
   * <tr>
   * <td>117</td>
   * <td>Power Ballad</td>
   * </tr>
   * <tr>
   * <td>118</td>
   * <td>Rhythmic Soul</td>
   * </tr>
   * <tr>
   * <td>119</td>
   * <td>Freestyle</td>
   * </tr>
   * <tr>
   * <td>120</td>
   * <td>Duet</td>
   * </tr>
   * <tr>
   * <td>121</td>
   * <td>Punk Rock</td>
   * </tr>
   * <tr>
   * <td>122</td>
   * <td>Drum Solo</td>
   * </tr>
   * <tr>
   * <td>123</td>
   * <td>A Capella</td>
   * </tr>
   * <tr>
   * <td>124</td>
   * <td>Euro-House</td>
   * </tr>
   * <tr>
   * <td>125</td>
   * <td>Dance Hall</td>
   * </tr>
   * <tr>
   * <td>126</td>
   * <td>Goa</td>
   * </tr>
   * <tr>
   * <td>127</td>
   * <td>Drum &amp; Bass</td>
   * </tr>
   * <tr>
   * <td>128</td>
   * <td>Club-House</td>
   * </tr>
   * <tr>
   * <td>129</td>
   * <td>Hardcore</td>
   * </tr>
   * <tr>
   * <td>130</td>
   * <td>Terror</td>
   * </tr>
   * <tr>
   * <td>131</td>
   * <td>Indie</td>
   * </tr>
   * <tr>
   * <td>132</td>
   * <td>BritPop</td>
   * </tr>
   * <tr>
   * <td>133</td>
   * <td>Negerpunk</td>
   * </tr>
   * <tr>
   * <td>134</td>
   * <td>Polsk Punk</td>
   * </tr>
   * <tr>
   * <td>135</td>
   * <td>Beat</td>
   * </tr>
   * <tr>
   * <td>136</td>
   * <td>Christian Gangsta Rap</td>
   * </tr>
   * <tr>
   * <td>137</td>
   * <td>Heavy Metal</td>
   * </tr>
   * <tr>
   * <td>138</td>
   * <td>Black Metal</td>
   * </tr>
   * <tr>
   * <td>139</td>
   * <td>Crossover</td>
   * </tr>
   * <tr>
   * <td>140</td>
   * <td>Contemporary Christian</td>
   * </tr>
   * <tr>
   * <td>141</td>
   * <td>Christian Rock</td>
   * </tr>
   * <tr>
   * <td>142</td>
   * <td>Merengue</td>
   * </tr>
   * <tr>
   * <td>143</td>
   * <td>Salsa</td>
   * </tr>
   * <tr>
   * <td>144</td>
   * <td>Thrash Metal</td>
   * </tr>
   * <tr>
   * <td>145</td>
   * <td>Anime</td>
   * </tr>
   * <tr>
   * <td>146</td>
   * <td>Jpop</td>
   * </tr>
   * <tr>
   * <td>147</td>
   * <td>Synthpop</td>
   * </tr>
   * </table>
   * 
   * @return the best matching ID3 genre ID or <code>null</code> if no such genre ID can be associated (e.g.
   *         for the genre root node).
   */
  Integer getId3Genre();

  /**
   * This method sets the {@link #getId3Genre() CDDB genre ID} of this genre.
   * 
   * @param cddbGenreId is the {@link #getId3Genre() CDDB genre ID} to set.
   */
  void setId3Genre(Integer cddbGenreId);

}
