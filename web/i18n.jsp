<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Enumeration"%>
<%@page import="pt.ist.fenixWebFramework.servlets.filters.RequestReconstructor"%>

<%@page import="pt.utl.ist.fenix.tools.util.Pair"%><html:xhtml/>

<div id="version">
<!-- BLOCK_HAS_CONTEXT -->
<table>
	<tr>
		<td>
			<%
				final RequestReconstructor requestReconstructor = (RequestReconstructor) request.getAttribute("requestReconstructor");
			%>
			<!-- HAS_CONTEXT --><form method="post" action="<%= requestReconstructor.getUrlSwitch("pt_PT") %>">
			<%
				for (Pair entry : requestReconstructor.getAttributes()) {
    				String key = (String) entry.getKey();
    				String value = (String) entry.getValue();
    				%>
		    			<input alt="<%= key %>" type="hidden" name="<%= key %>" value="<%= (String) value %>"/>
    				<%
				}
			%>

				<input 
						type="image" src="<%= request.getContextPath() %>/images/flags/pt.gif"
						alt="<bean:message key="language.pt" bundle="IMAGE_RESOURCES" />"
						title="<bean:message key="language.pt" bundle="IMAGE_RESOURCES" />"
						value="PT"
						onclick="this.form.submit();"/>
			</form>
		</td>
		<td>
			<!-- HAS_CONTEXT --><form method="post" action="<%= requestReconstructor.getUrlSwitch("en_EN") %>">
			<%
				for (Pair entry : requestReconstructor.getAttributes()) {
    				String key = (String) entry.getKey();
    				String value = (String) entry.getValue();
    				%>
		    			<input alt="<%= key %>" type="hidden" name="<%= key %>" value="<%= (String) value %>"/>
    				<%
				}
			%>

				<input 
						type="image" src="<%= request.getContextPath() %>/images/flags/en.gif"
						alt="<bean:message key="language.pt" bundle="IMAGE_RESOURCES" />"
						title="<bean:message key="language.pt" bundle="IMAGE_RESOURCES" />"
						value="EN"
						onclick="this.form.submit();"/>
			</form>
		</td>
	</tr>
</table>
<!-- END_BLOCK_HAS_CONTEXT -->
</div>
