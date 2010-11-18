<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@ page import="net.sf.mmm.util.xml.api.XmlUtil"
%><%@ page import="net.sf.mmm.search.api.config.SearchSource"
%><%@ page import="net.sf.mmm.search.engine.api.config.SearchEntryType"
%><%@ page import="net.sf.mmm.search.engine.api.SearchResultPage"
%><%@ page import="net.sf.mmm.search.engine.api.SearchHit"
%><%@ page import="net.sf.mmm.search.engine.api.config.SearchEngineConfiguration"
%><%@ page import="net.sf.mmm.search.view.api.SearchViewLogic"
%><%@ page import="net.sf.mmm.search.view.api.SearchViewRequestParameters"
%><%@ page import="net.sf.mmm.search.view.api.SearchViewContextAccess"
%><%@ page import="net.sf.mmm.search.view.api.SearchViewContext"%><%
  // get parameters as attributes (already validated and prepared by the servlet)
  SearchViewContext searchContext = SearchViewContextAccess.getContext(request);
  SearchResultPage searchPage = searchContext.getResultPage();
  SearchViewRequestParameters parameters = searchContext.getRequestParameters();
  SearchViewLogic logic = searchContext.getLogic();
  SearchEngineConfiguration configuration = logic.getConfiguration();
  XmlUtil xmlUtil = logic.getXmlUtil();
%><html>
<head>
  <title>Search</title>
  <meta name="description" content="Search result page"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" href="css/site.css" type="text/css"/>
  <script type="text/javascript">
function getById(id) {
  var element;
  if (document.getElementById) {
    // offical javascript
    element = document.getElementById(id);
  } else if (document.all) {
    // IE 6-
    element = document.all[id];
  } else if (document.layers) {
    // old netscape
    element = document.layers[id];
  }
  return element;
}
function toggleDiv(divId) {
  var divElement = getById(divId);
  if (divElement) {
    divElement.style.display = divElement.style.display ? "" : "block";
  }
}
function setDivVisible(divId, isVisible) {
  var divElement = getById(divId);
  if (divElement) {
    // dont ask me why "block" sets visible and "" makes invisble ???
    if (isVisible) {
      divElement.style.display = "block";
    } else {
      divElement.style.display = "";
    }
  }
}
function updateExpert() {
  setDivVisible('searchadvanced', document.search.expert.checked);
}
function showPage(pageNum) {
  document.search.<%= SearchViewRequestParameters.PARAMETER_PAGE %>.value = pageNum;
  document.search.submit();
}
  </script>
</head><%
  String paramExpert = request.getParameter("expert");
  if (paramExpert == null) {
    paramExpert = "";
  }
  String expertSelected = "";
  if (paramExpert.length() > 0) {
    expertSelected = " checked=\"true\"";
  }
  String htmlQuery = xmlUtil.escapeXml(parameters.getQuery(), true);
%>
<body onload="updateExpert()">
<%@include file="jinc/header.jinc" %>
<div id="searchhead">
  <form method="get" action="search" name="search" target="_self">
    Query: 
    <input type="text" size="60" maxlength="2048" name="<%= SearchViewRequestParameters.PARAMETER_QUERY %>" value="<%= htmlQuery %>"/>
    <input type="hidden" name="<%= SearchViewRequestParameters.PARAMETER_PAGE %>" value="<%= parameters.getPageNumber() %>"/>  
    <input type="hidden" name="<%= SearchViewRequestParameters.PARAMETER_TOTAL_HIT_COUNT %>" value="<%= parameters.getTotalHitCount() %>"/>  
    <input type="submit" value="Search" onclick="showPage(0)"/>
    <input type="checkbox" name="expert" <%= expertSelected %> onclick="updateExpert()"/>Advanced
    <div id="searchadvanced">
      Source: 
      <select name="<%= SearchViewRequestParameters.PARAMETER_SOURCE %>" size="1"><%
  for (SearchSource source: logic.getSourceViews()) {
    String sourceKey = source.getId();
    String sourceSelected = "";
    if (sourceKey.equals(parameters.getSource())) {
      sourceSelected = "selected=\"true\"";
    }
        %>
        <option value="<%= sourceKey%>"<%= sourceSelected %>><%=source.getTitle()%></option><%
  }
%>
      </select>
      Filetype:
      <select name="<%= SearchViewRequestParameters.PARAMETER_TYPE %>" size="1"><%
  for (SearchEntryType type: logic.getEntryTypeViews()) {
    String typeKey = type.getId();
    String typeSelected = "";
    if (typeKey.equals(parameters.getType())) {
      typeSelected = " selected=\"true\"";
    }
%>
        <option value="<%= typeKey%>"<%= typeSelected %>><%=type.getTitle()%></option><%
  }
%>
      </select>
      Creator: <input type="text" maxlength="512" name="<%= SearchViewRequestParameters.PARAMETER_CREATOR %>" value="<%= xmlUtil.escapeXml(parameters.getCreator(), true) %>"/><br/>
      Latest Refresh: <%= logic.getLastRefreshDate() %>
    </div>
  </form>
</div>
<% 
  if (searchPage == null) {
    Exception error = searchContext.getException();
    if (error != null) {
      %>
<div id="hitlisttop"> 
An error has occurred during search.
</div>
<div id="hit">
Unfortunately your search has caused an error:<br/>
<br/>
<pre>
<% error.printStackTrace(new java.io.PrintWriter(out)); %>
</pre>
</div>
<div id="hitlistbottom">
An error has occured during search.
</div>
      <%    
    }
  } else {
    if (searchPage.getTotalHitCount() == 0) {
      %>
<div id="hitlisttop"> 
Your search for <strong><%= htmlQuery %></strong> produced no hit.
</div>
<div id="hit">
No hits where found for your query.<br/>
<br/>
Suggestions:
<ul>
<li>Ensure that all terms are spelled correctly.</li>
<li>Try other search terms.</li>
</ul>
</div>
<div id="hitlistbottom">
Sorry, no hits.
</div>
      <%
    } else {
      int hitStartIndex = searchPage.getPageIndex() * searchPage.getHitsPerPage();
      int hitStart = hitStartIndex + 1;
      int hitEnd = hitStartIndex + searchPage.getPageHitCount();
      int maxPage = searchPage.getPageCount();
      int currentPage = searchPage.getPageIndex() + 1;
      int pagingStart = searchPage.getPagingStartIndex(2);
      int pagingEnd = searchPage.getPagingEndIndex(2);
      %>
<div id="hitlisttop"> 
Result <strong><%= hitStart %></strong> to <strong><%= hitEnd %></strong> 
of <strong><%= searchPage.getTotalHitCount() %></strong> for your search
<strong><%= htmlQuery %></strong>.<br/>
<a href="#bottom"><img src="images/arrows/arrow-bottom-on.gif" alt="End of page"/></a>
<%@include file="jinc/search-paging.jinc" %>
</div>
      <%
      // show the hits
      int hitCount = searchPage.getPageHitCount();
      for (int hitIndex = 0; hitIndex < hitCount; hitIndex++) {
        SearchHit hit = searchPage.getPageHit(hitIndex);
        SearchSource source = configuration.getSource(hit.getSource());
        String baseUrl = "";
        if (source != null) {
          baseUrl = source.getUrlPrefix();
        }
        String url = baseUrl + hit.getUri();
        String title = logic.getDisplayTitle(hit);
        String styleClass;
        if ((hitIndex % 2) == 0) {
          styleClass = "even";
        } else {
          styleClass = "odd";
        }
        %>
<div id="hit" class="<%= styleClass %>">
<a href="details?id=<%= hit.getEntryId() %>"><img src="images/ranking/stars<%= hit.getScore(5) %>.gif"/></a> 
<a href="<%= url %>"><img src="images/filetypes/<%= logic.getEntryType(hit.getType()).getIcon() %>"/> <%= title %></a><br/>
<%= hit.getHighlightedText() %><br/>
<a href="<%= url %>"><span class="url"><%= url %></span></a>
</div>
        <%
      }
      %>
<div id="hitlistbottom">
<a name="bottom"></a><a href="#top"><img src="images/arrows/arrow-top-on.gif" alt="Top of page"/></a> <%@include file="jinc/search-paging.jinc" %>
</div>
      <%
    }
    %>
    <%
  }
%>
<%@include file="jinc/footer.jinc" %>
</body>
</html>