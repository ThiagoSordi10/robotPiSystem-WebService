<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="html" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html:header styles="materialize.css" scripts="materialize.js"
	title="${empresa.nome}"></html:header>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/raspberry/lista" var="listaRasp" />
<c:set value="${contextPath }/estacao/lista" var="listaEstacao" />
<c:set value="${contextPath }/user/lista" var="listaUser" />

<div class="row center">
	<a href="${listaRasp}/${empresa.id}">
		<div class="col s12 m4 l4  waves-effect waves-light">
			<div class="card-panel hoverable z-depth-1 center" style="background-color: #a7a7af; 
			color:black;">
				<i class="large material-icons ">list </i>
				<div class="truncate">Lista de raspberries</div>
			</div>
		</div>
	</a>  <a href="${listaEstacao}/${empresa.id}">
		<div class="col s12 m4 l4 waves-effect waves-light">
			<div class="card-panel hoverable z-depth-1 center" style="background-color: #a7a7af; 
			color:black;">
				<i class="large material-icons ">list</i>
				<div class="truncate">Lista de estações</div>
			</div>
		</div>
	</a> <a href="${listaUser}/${empresa.id}">
		<div class="col s12 m4 l4 waves-effect waves-light">
			<div class="card-panel hoverable z-depth-1 center" style="background-color: #a7a7af; 
			color:black;">
				<i class="large material-icons ">list</i>
				<div class="truncate">Lista de usuários</div>
			</div>
		</div>
	</a>
</div>


<html:footer></html:footer>