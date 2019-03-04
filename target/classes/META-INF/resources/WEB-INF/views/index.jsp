<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="html" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html:header styles="materialize.css" scripts="materialize.js"
	title="piBot"></html:header>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/user" var="lista" />
<c:set value="${contextPath }/firebase" var="configuracao_firebase" />
<c:set value="${contextPath }/user/form" var="cadastro" />
<c:set value="${contextPath }/empresa/form" var="cadastroEmpresa" />
<c:set value="${contextPath }/empresa" var="listaEmpresa" />
<div>Icons made by 
<a href="http://www.freepik.com" title="Freepik">Freepik</a> from 
<a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by 
<a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY
</a>
</div>
<div class="row center">
	<a href="${cadastro}">
		<div class="col s12 m4 l4  waves-effect waves-light">
			<div class="card-panel hoverable z-depth-1 center">
				<i class="large material-icons ">perm_identity </i>
				<div class="truncate">Cadastro de users</div>
			</div>
		</div>
	</a> <a href="${configuracao_firebase}">
		<div class="col s12 m4 l4  waves-effect waves-light ">
			<div class="card-panel hoverable z-depth-1 center ">
				<i class="large material-icons ">settings </i>
				<div class="truncate">Configuração do Firebase</div>
			</div>
		</div>
	</a> <a href="${lista}">
		<div class="col s12 m4 l4 waves-effect waves-light">
			<div class="card-panel hoverable z-depth-1 center">
				<i class="large material-icons ">list</i>
				<div class="truncate">Lista de usuários</div>
			</div>
		</div>
	</a> <a href="${listaEmpresa}">
		<div class="col s12 m4 l4 waves-effect waves-light">
			<div class="card-panel hoverable z-depth-1 center">
				<i class="large material-icons ">list</i>
				<div class="truncate">Lista de empresas</div>
			</div>
		</div>
	</a> <a href="${cadastroEmpresa}">
		<div class="col s12 m4 l4 waves-effect waves-light">
			<div class="card-panel hoverable z-depth-1 center">
				<i class="large material-icons ">perm_identity</i>
				<div class="truncate">Cadastro de empresa</div>
			</div>
		</div>
	</a>
</div>


<html:footer></html:footer>