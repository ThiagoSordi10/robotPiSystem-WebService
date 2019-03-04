<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="html" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value='${contextPath }/empresa' var="actionUrl" />

<html:header title="Cadastro"></html:header>

<form:form action="${actionUrl}" commandName="empresa" class="col s12">
	<c:if test="${empresa.id != null }">
		<form:input type="hidden" path="id" value="${empresa.id }"></form:input>
	</c:if>
	<div class="row">
		<html:input-metade label="Nome" name="nome" />
		<html:input-metade label="CEP" name="cep" />
	</div>

	<form:button class="btn waves-effect waves-light">Cadastrar</form:button>
</form:form>
</div>



<html:footer></html:footer>