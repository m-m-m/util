<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@page import="java.util.Iterator"
%><%@page import="net.sf.mmm.util.nls.api.NlsRuntimeException"
%><%@page import="net.sf.mmm.util.xml.api.XmlUtil"
%><%@page import="net.sf.mmm.search.api.SearchEntry"
%><%@page import="net.sf.mmm.search.api.config.SearchSource"
%><%@page import="net.sf.mmm.search.api.config.SearchFieldConfiguration"
%><%@page import="net.sf.mmm.search.engine.api.config.SearchEngineConfiguration"
%><%@page import="net.sf.mmm.search.view.api.SearchViewLogic"
%><%@page import="net.sf.mmm.search.view.api.SearchViewContextAccess"
%><%@page import="net.sf.mmm.search.view.api.SearchViewContext"%><%
  // get parameters as attributes (already validated and prepared by the servlet)
  SearchViewContext searchContext = SearchViewContextAccess.getContext(request);
  SearchViewLogic logic = searchContext.getLogic();
  SearchEngineConfiguration configuration = searchContext.getLogic().getConfiguration();
  SearchEntry entry = searchContext.getEntry();
  XmlUtil xmlUtil = logic.getXmlUtil();
%><html>
<head>
  <title>Details for your search-result</title>
  <meta name="description" content="Details for your search-result"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" href="css/site.css" type="text/css"/>
</head>
<body>
<%@include file="jinc/header.jinc" %>
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
<br/><%
    NlsRuntimeException error = searchContext.getException();
    if (error == null) {
%>
The parameter for the search-result is missing. Maybe this page was not called
from the <a href="search">search-page</a>.<%
    } else {
%>
<pre>
<%
error.printStackTrace(new java.io.PrintWriter(out));
%>
</pre><%
    }
%>
</div>
<div id="hitlistbottom">
An error has occurred while getting the details of your search-result.
</div><%
  } else {
    SearchSource source = configuration.getSource(entry.getSource());
    String urlPrefix = "";
    if (source != null) {
      urlPrefix = source.getUrlPrefix();
    }
    String url = urlPrefix + entry.getUri();
    String title = logic.getDisplayTitle(entry);
%>
<div id="hitlisttop">
Details for <a href="<%= url%>"><strong><%=title%></strong></a>.
</div>
<div id="hit">
<table>
  <thead>
    <tr>
      <th>Field</th>
      <th>Value</th>
    </tr>
  </thead>
  <tbody><%
    Iterator<String> fieldIterator = entry.getFieldNames();
    int flipFlop = 0;
    while (fieldIterator.hasNext()) {
      String name = fieldIterator.next();
      if (!name.equals(SearchEntry.FIELD_TEXT)) {
        SearchFieldConfiguration fieldConfiguration = configuration.getFields().getFieldConfiguration(name);
        boolean hidden = false;
        if (fieldConfiguration != null) {
          hidden = fieldConfiguration.isHidden();
        }
        String styleClass = (flipFlop == 0) ? "even" : "odd";
%>
    <tr class="<%= styleClass%>">
      <td><%=name%></td>
      <td><%=xmlUtil.escapeXml(entry.getFieldAsString(name), false)%></td>
    </tr><%
        flipFlop = (flipFlop + 1) % 2;
      }
    }  
    String styleClass = (flipFlop == 0) ? "even" : "odd";
%>
    <tr class="<%= styleClass%>">
      <td><%=SearchEntry.FIELD_TEXT%></td>
      <td><pre><%=xmlUtil.escapeXml(entry.getFieldAsString(SearchEntry.FIELD_TEXT), false)%></pre></td>
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
<%@include file="jinc/footer.jinc" %>
</body>
</html>