<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html:xhtml/>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/enum.tld" prefix="enum" %>

<logic:present role="RESEARCHER">	
	<div style="float: right;">
		 <jsp:include page="../../../i18n.jsp"/>
	</div>
	<script type="text/javascript">
	hideButtons();
	</script>
</logic:present>
