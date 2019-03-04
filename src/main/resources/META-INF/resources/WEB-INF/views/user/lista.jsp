<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="html" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/user/form" var='form' />

<html:header scripts="user/deletar_user.js"
	styles="lista.css" title="Usuários">
</html:header>

<c:if test="${info != null}">
	<script>
		Materialize.toast('${info}', 4000)
	</script>
</c:if>

<c:choose>
	<c:when test="${users.size() > 0}">
		<ul class="collection">
			<c:forEach items="${users}" var="user">
				<html:avatar user="${user}" />
			</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<h4 class="center">Nenhum usuario cadastrado.</h4>
	</c:otherwise>
</c:choose>

<div class="fixed-action-btn horizontal"
	style="bottom: 45px; right: 40px;">
	<a href="${form }"
		class="btn-floating btn-large red waves-effect waves-light"> <i
		class="large material-icons">add</i>
	</a>
</div>

<html:footer></html:footer>