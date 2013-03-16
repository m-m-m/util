<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet>
<!-- This is an xsl transformation to fix problems of MS Visio SVG export.
  Step 1: Open VSD in Visio and save as SVG
  Step 2: Edit the SVG with a text editor and add this to the root tag (svg):
          xmlns:xlink="http://www.w3.org/1999/xlink"
  Step 3: Call this XSLT to process the outcome of Step 2.
  TODO: UML arrows such as generalization are still broken...
 -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:svg="http://www.w3.org/2000/svg"
xmlns="http://www.w3.org/2000/svg" exclude-result-prefixes="svg">
<xsl:output method="xml" indent="no"/>

<!--
  Remove useless titles that otherwise prevent xlink titles as hovers/tooltips such as:
  <title content="structured text">Page element.34</title>
 -->
<xsl:template match="svg:title">
</xsl:template>

<!--
  Realign text that is not centered to the left (whatever Visio is doing here)
 -->
<xsl:template match="svg:text[@x &lt; 40.0]">
<xsl:element name="text" xmlns="http://www.w3.org/2000/svg">
<xsl:copy-of select="@*"/>
<xsl:attribute name="x">4</xsl:attribute>
<xsl:copy-of select="node()"/>
</xsl:element>
</xsl:template>

<xsl:template match="svg:marker">
<xsl:element name="marker" xmlns="http://www.w3.org/2000/svg">
<xsl:copy-of select="@*"/>
<xsl:attribute name="overflow">visible</xsl:attribute>
<xsl:copy-of select="node()"/>
</xsl:element>
</xsl:template>

<xsl:template match="@*|node()">
<xsl:copy>
<xsl:apply-templates select="@*|node()"/>
</xsl:copy>
</xsl:template>

</xsl:stylesheet>