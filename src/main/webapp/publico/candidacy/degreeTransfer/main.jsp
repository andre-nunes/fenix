<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<html:xhtml/>

<!--LANGUAGE SWITCHER -->
<div id="version">
	<img class="activeflag" src="Candidato%20%20Licenciatura%20_%20IST_files/icon_pt.gif" alt="Português">
	<a href="http://www.ist.utl.pt/en/htm/profile/pstudent/lic/"><img src="Candidato%20%20Licenciatura%20_%20IST_files/icon_en.gif" alt="English" border="0"></a>
</div>
<!--END LANGUAGE SWITCHER -->
<bean:define id="mappingPath" name="mappingPath"/>
<bean:define id="fullPath"><%= request.getContextPath() + "/publico" + mappingPath + ".do" %></bean:define>

<div class="breadcumbs">
	<a href="#"><%=net.sourceforge.fenixedu.domain.organizationalStructure.Unit.getInstitutionAcronym()%></a> &gt;
	<a href="#">Candidato</a> &gt;
	<a href="#">Candidaturas</a> &gt;
	<a href='<%= fullPath + "?method=candidacyIntro" %>'>Licenciaturas</a> &gt;
	Transferências
</div>


<div id="contextual_nav">
<h2 class="brown">Nesta página</h2>
	<ul>
    	<li><a href="#1">Critérios de selecção</a></li>
    	<li><a href="#2">Vagas</a></li>
    	<li><a href="#3">Propinas</a></li>
    	<li><a href="#4">Prazos</a></li>
		<li><a href="#5">FAQs</a></li>
    	<li><a href="<%= fullPath + "?method=preparePreCreationOfCandidacy" %>"><b>Submeter Candidatura</b></a></li>
    	<li><a href="<%= fullPath + "?method=prepareAutheticationCandidacyRequest" %>"><b>Consultar Candidatura</b></a></li>
   </ul>
</div>


<h1>Candidatura: Transferências</h1>


<p>O regime de ingresso por transferência destina-se aos estudantes que estando ou
tendo estado inscritos no 1º ciclo de um curso de Licenciatura ou de um Mestrado
Integrado pretendam inscrever-se no mesmo curso mas numa instituição diferente
daquela em que praticaram a última inscrição. A última inscrição pode ter ocorrido
numa escola nacional ou estrangeira.</p>


<h2 id="1">Critérios de selecção</h2>
<p>Os candidatos a Transferências serão seriados pela coordenação do curso a que se candidatam tendo em conta os seguintes critérios:</p>
<ul>
	<li>Afinidade entre o curso que possuem e o curso a que se candidatam;</li>
	<li>Natureza do grau que possuem;</li>
	<li>Sucesso escolar no curso que frequentam/frequentaram.</li>
</ul>


<h2 id="2">Vagas</h2>

<table class="tab_lay" width="99%" cellspacing="0" summary="Informações sobre vagas por licenciatura para candidatos titulares de Curso Médio e Superior ">
	<tr>
		<th>Cursos</th>
		<th>Campus</th>
		<th style="text-align: center;">Transfer&ecirc;ncias</th>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/leamb?locale=pt">Licenciatura em Engenharia do Ambiente</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>	
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/lean?locale=pt">Licenciatura em Engenharia e Arquitectura Naval</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/lee?locale=pt">Licenciatura em Engenharia Electr&oacute;nica</a></td>
		<td>Taguspark</td>
		<td class="center">1</td>
	</tr>
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/legm?locale=pt">Licenciatura em Engenharia Geol&oacute;gica e de Minas</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/legi?locale=pt">Licenciatura em Engenharia e Gest&atilde;o Industrial</a></td>
		<td>Taguspark</td>
		<td class="center">1</td>
	</tr>
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/leic-a?locale=pt">Licenciatura em Engenharia Inform&aacute;tica e de Computadores</a></td>
		<td>Alameda</td>
		<td class="center">2</td>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/leic-t?locale=pt">Licenciatura em Engenharia Inform&aacute;tica e de Computadores</a></td>
		<td>Taguspark</td>
		<td class="center">1</td>
	</tr>	
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/lemat?locale=pt">Licenciatura em Engenharia de Materiais</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/lerc?locale=pt">Licenciatura em Engenharia de Redes de Comunica&ccedil;&otilde;es</a></td>
		<td>Taguspark</td>
		<td class="center">1</td>
	</tr>
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/lmac?locale=pt">Licenciatura em Matem&aacute;tica Aplicada e Computa&ccedil;&atilde;o</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/ma?locale=pt">Mestrado em Arquitectura</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/meaer?locale=pt">Mestrado em Engenharia Aeroespacial</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>	
	<tr>
		<td><a href="http://fenix.ist.utl.pt/mebiol?locale=pt">Mestrado em Engenharia Biol&oacute;gica</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/mebiom?locale=pt">Mestrado em Engenharia Biom&eacute;dica</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/mec?locale=pt">Mestrado em Engenharia Civil</a></td>
		<td>Alameda</td>
		<td class="center">2</td>
	</tr>
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/meec?locale=pt">Mestrado em Engenharia Electrot&eacute;cnica e de Computadores</a></td>
		<td>Alameda</td>
		<td class="center">3</td>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/meft?locale=pt">Mestrado em Engenharia F&iacute;sica Tecnol&oacute;gica</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
	<tr class="bluecell">
		<td><a href="http://fenix.ist.utl.pt/memec?locale=pt">Mestrado em Engenharia Mec&acirc;nica</a></td>
		<td>Alameda</td>
		<td class="center">2</td>
	</tr>
	<tr>
		<td><a href="http://fenix.ist.utl.pt/meq?locale=pt">Mestrado em Engenharia Qu&iacute;mica</a></td>
		<td>Alameda</td>
		<td class="center">1</td>
	</tr>
</table>




<h2 id="3">Propinas</h2>
<p>Para o ano lectivo de 2008/2009 a propina para as Licenciaturas de 1º ciclo e 1º ciclo de Mestrados Integrados foi fixada no valor de 972,14 &euro;.</p>


<h2 id="4">Prazos</h2>

<table class="tab_simpler" summary="Prazos para candidatos em mudanÃ§a ou transferÃªncia de curso" cellspacing="0">
	<tr>
		<td class="align_r"><span class="marker">1 a 29 de Agosto de 2008</span></td>
		<td><a href="#">Apresentação de candidaturas</a></td>
	</tr>
	<tr>
		<td class="align_r"><span class="marker">16 de Setembro de 2008</span></td>
		<td>Afixação dos editais de colocação</td>
	</tr>

	<tr>
		<td class="align_r"><span class="marker">16 a 23 de Setembro de 2008</span></td>
		<td>Matrícula e inscrição</td>
	</tr>
	<tr>
		<td class="align_r"><span class="marker">16 a 23 de Setembro de 2008</span></td>
		<td>Reclamação sobre as colocações</td>

	</tr>
	<tr>
		<td class="align_r"><span class="marker">16 de Outubro de 2008</span></td>
		<td>Decisão sobre as reclamações</td>
	</tr>
	<tr>
		<td class="align_r"><span class="marker">23 de Outubro de 2008</span></td>
		<td>Matrícula para reclamações atendidas</td>
	</tr>
</table>



<h2 id="5">FAQ's</h2>
<p>Em falta.</p>
