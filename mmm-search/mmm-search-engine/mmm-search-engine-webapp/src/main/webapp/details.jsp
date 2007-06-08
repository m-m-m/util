<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@ page import="java.util.Iterator"
%><%@ page import="net.sf.mmm.util.StringUtil"
%><%@ page import="net.sf.mmm.search.api.SearchEntry"
%><%@ page import="net.sf.mmm.search.view.SearchViewConfiguration"
%><%@ page import="net.sf.mmm.search.view.SearchViewContext"%><%
  // get parameters as attributes (already validated and prepared by the servlet)
  SearchViewContext searchContext = SearchViewContext.get(request);
  SearchViewConfiguration conf = searchContext.getConfiguration();
  SearchEntry entry = searchContext.getEntry();
%><html>
<head>
  <title>Details for your search-result</title>
  <meta name="description" content="Details for your search-result"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" href="css/site.css" type="text/css"/>
</head>
<body>
<div id="body">
<a name="top"/>
<div id="logo">
  <img src="images/logo" alt="Logo"/>
</div>
<div id="searchhead">
  Back to the <a href="search">search</a>.
</div>
<%
  if (entry == null) {
    %>
<div id="hitlisttop">
An error has occurred while getting the details of your search-result.
</div>
<div id="hit">
Error while getting the details:<br/>
<br/>
    <%
    Exception error = searchContext.getException();
    if (error == null) {
      %>
The parameter for the search-result is missing. Maybe this page was not called
from the <a href="search">search-page</a>.
      <%
    } else {      
      %>
<pre>
<% error.printStackTrace(new java.io.PrintWriter(out)); %>
</pre>
      <%
    }
    %>
</div>
<div id="hitlistbottom">
An error has occurred while getting the details of your search-result.
</div>
    <%
  } else {
    String url = conf.getUrlPrefixBySource(entry.getSource()) + entry.getUri();
    String title = SearchViewContext.getEntryTitle(entry);
    %>
<div id="hitlisttop">
Details for <a href="<%= url%>"><strong><%= title %></strong></a>.
</div>
<div id="hit">
<table>
  <thead>
    <tr>
      <th>Field</th>
      <th>Value</th>
    </tr>
  </thead>
  <tbody>
    <%
  Iterator<String> fieldIterator = entry.getPropertyNames();
  int flipFlop = 0;
  while (fieldIterator.hasNext()) {
    String name = fieldIterator.next();
    if (!name.equals(SearchEntry.PROPERTY_TEXT)) {
      String styleClass = (flipFlop == 0) ? "even" : "odd";
      %>
    <tr class="<%= styleClass%>">
      <td><%= name %></td>
      <td><%= StringUtil.escapeXml(entry.getProperty(name), false) %></td>
    </tr>
      <%
      flipFlop = (flipFlop + 1) % 2;
    }
  }  
  String styleClass = (flipFlop == 0) ? "even" : "odd";
    %>
    <tr class="<%= styleClass%>">
      <td><%= SearchEntry.PROPERTY_TEXT %></td>
      <td><pre><%= StringUtil.escapeXml(entry.getProperty(SearchEntry.PROPERTY_TEXT), false) %></pre></td>
    </tr>
  </tbody>
</table>
</div>
<div id="hitlistbottom">
Details for <a href="<%= url%>"><strong><%= title %></strong></a>.
</div>
    <%
  }
%>
</div>
<div id="footer">
&copy;2007 The m-m-m Team
</body>
</html>