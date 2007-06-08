<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@page import="net.sf.mmm.util.StringUtil"
%><%@ page import="net.sf.mmm.search.engine.api.SearchResultPage"
%><%@ page import="net.sf.mmm.search.engine.api.SearchHit"
%><%@ page import="net.sf.mmm.search.view.SearchViewConfiguration"
%><%@ page import="net.sf.mmm.search.view.SearchViewContext"%><%
  // get parameters as attributes (already validated and prepared by the servlet)
  SearchViewContext searchContext = SearchViewContext.get(request);
  SearchResultPage searchPage = searchContext.getResultPage();
  SearchViewConfiguration conf = searchContext.getConfiguration();
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
  document.search.<%= SearchViewContext.PARAM_PAGE %>.value = pageNum;
  document.search.submit();
}
  </script>
</head>
<%
  String paramExpert = request.getParameter("expert");
  if (paramExpert == null) {
    paramExpert = "";
  }
  String expertSelected = "";
  if (paramExpert.length() > 0) {
    expertSelected = " checked=\"true\"";
  }
  String htmlQuery = StringUtil.escapeXml(searchContext.getQuery(), true);
%>
<body onload="updateExpert()">
<div id="body">
<a name="top"/>
<div id="logo">
  <img src="images/logo.png" alt="Logo"/>
</div>
<div id="searchhead">
  <form method="get" action="search" name="search" target="_self">
    Query: 
    <input type="text" size="60" maxlength="2048" name="<%= SearchViewContext.PARAM_QUERY %>" value="<%= htmlQuery %>"/>
    <input type="hidden" name="<%= SearchViewContext.PARAM_PAGE %>" value="<%= searchContext.getPageNumber() %>"/>  
    <input type="submit" value="Search" onclick="showPage(0)">
    <input type="checkbox" name="expert" <%= expertSelected %> onclick="updateExpert()">Advanced</Input>
    <div id="searchadvanced">
      Source: 
      <select name="<%= SearchViewContext.PARAM_SOURCE %>" size="1">
        <%
          for (String source: conf.getSourceNames()) {
            String sourceKey = conf.getSourceByName(source);
            String sourceSelected = "";
            if (sourceKey.equals(searchContext.getSource())) {
              sourceSelected = "selected=\"true\"";
            }
            %>
        <option value="<%= sourceKey%>"<%= sourceSelected %>><%= source %></option>            
            <%
          }          
        %>
      </select>
      Filetype:
      <select name="<%= SearchViewContext.PARAM_TYPE %>" size="1">
        <%
          for (String type: conf.getTypeNames()) {
            String typeKey = conf.getTypeByName(type);
            String typeSelected = "";
            if (typeKey.equals(searchContext.getType())) {
              typeSelected = " selected=\"true\"";
            }
            %>
        <option value="<%= typeKey%>"<%= typeSelected %>><%= type %></option>            
            <%
          }          
        %>
      </select>
      Author: <input type="text" maxlength="512" name="<%= SearchViewContext.PARAM_AUTHOR %>" value="<%= StringUtil.escapeXml(searchContext.getAuthor(), true) %>"/><br/>
    </div>
  </form>
</div>
<% 
  if (searchPage == null) {
    Exception error = searchContext.getException();
    if (error != null) {
      %>
<div id="hitlisttop"> 
An error has occured during search.
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
<li>Ensure that all terms are spelled corretly.</li>
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
<a href="#bottom"><img src="images/icons/arrow-bottom-on.gif" alt="End of page"/></a>
<%@include file="jinc/search-paging.jinc" %>
</div>
      <%
      // show the hits
      int hitCount = searchPage.getPageHitCount();
      for (int hitIndex = 0; hitIndex < hitCount; hitIndex++) {
        SearchHit hit = searchPage.getPageHit(hitIndex);
        String baseUrl = conf.getUrlPrefixBySource(hit.getSource());
        String url = baseUrl + hit.getUri();
        String title = SearchViewContext.getEntryTitle(hit);
        String styleClass;
        if ((hitIndex % 2) == 0) {
          styleClass = "even";
        } else {
          styleClass = "odd";
        }
        %>
<div id="hit" class="<%= styleClass %>">
<a href="details?id=<%= hit.getEntryId() %>"><img src="images/stars<%= hit.getScore(5) %>.gif"/></a> 
<a href="<%= url %>"><img src="images/icons/<%= conf.getIconName(hit.getType())%>"/> <%= title %></a><br/>
<%= hit.getHighlightedText() %><br/>
<a href="<%= url %>"><span class="url"><%= url %></span></a>
</div>
        <%
      }
      %>
<div id="hitlistbottom">
<a name="bottom"/><a href="#top"><img src="images/icons/arrow-top-on.gif" alt="Top of page"/></a> <%@include file="jinc/search-paging.jinc" %>
</div>
      <%
    }
    %>
    <%
  }
%>
</div>
<div id="footer">
&copy;2007 The m-m-m Team
</div>
</body>
</html>