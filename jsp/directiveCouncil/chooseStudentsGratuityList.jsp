<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="ServidorApresentacao.Action.sop.utils.SessionConstants" %>

<h2><bean:message key="label.gratuity.listStudents"/></h2>
<span class="error"><html:errors/></span>

<html:form action="/studentsGratuityList" >
<logic:notPresent name="showNextSelects">
	<html:hidden property="method" value="prepareChooseDegree"/>
	<html:hidden property="page" value="1"/>
</logic:notPresent>
<logic:present name="showNextSelects">
	<html:hidden property="method" value="studentsGratuityList"/>
	<html:hidden property="executionYear"/>
	<html:hidden property="page" value="2"/>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center" class="infoselected">
				<bean:define id="executionYearLabel"><%=pageContext.findAttribute("executionYear")%></bean:define>
				<b><bean:message key="label.executionYear" />:</b>&nbsp;<bean:write name="executionYearLabel" /><br />
			</td>
		</tr>
	</table>
	<br />	
</logic:present>
<table>
		<logic:notPresent name="showNextSelects">
		<tr>
			<td>
				<bean:message key="label.executionYear"/>
			</td>
			<td>
				<html:select property="executionYear" onchange="document.studentsGratuityListForm.method.value='prepareChooseDegree';document.studentsGratuityListForm.submit();">
					<html:option value="" key="label.select">
						<bean:message key="label.select"/>
					</html:option>
					<html:optionsCollection name="executionYears"/>
				</html:select>
			</td>
		</tr>		
		</logic:notPresent>
		<logic:present name="showNextSelects">			
			<tr>
				<td>
					<bean:message key="label.qualification.degree"/>
				</td>
				<td>
					<html:select property="degree">
						<html:option value="" key="label.select">
							<bean:message key="label.select"/>
						</html:option>
						<html:option value="all" key="label.all">
							<bean:message key="label.all"/>
						</html:option>
						<html:optionsCollection name="<%=SessionConstants.DEGREES%>"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="label.specializationArea"/>
				</td>
				<td>
					<html:select property="specialization">
						<html:option value="" key="label.select">
							<bean:message key="label.select"/>
						</html:option>
						<html:option value="all" key="label.all">
							<bean:message key="label.feminine.all"/>
						</html:option>
						<html:optionsCollection name="specializations"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="label.situation"/>
				</td>
				<td>
					<html:select property="situation">
						<html:option value="">
							<bean:message key="label.select"/>
						</html:option>						
						<html:option value="all">
							<bean:message key="label.feminine.all"/>
						</html:option>
						<logic:iterate id="gratuitySituation" name="situations">
							<bean:define id="gratuitySituationName" name="gratuitySituation" property="name"/>
							<bean:define id="gratuitySituationNameKEY" value="<%= "label.gratuitySituationType." + gratuitySituationName.toString() %>"/>						
							<html:option value="<%= gratuitySituationName.toString() %>" >
								<bean:message name="gratuitySituationNameKEY"/>
							</html:option>
						</logic:iterate>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>	
					<br />
					<html:submit styleClass="inputbutton">
						<bean:message key="button.list"/>
					</html:submit>				
				</td>
			</tr>
		</logic:present>
</table>	
</html:form>	