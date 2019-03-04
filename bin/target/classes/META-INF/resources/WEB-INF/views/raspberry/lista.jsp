<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="html" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/raspberry/form" var='form' />

<html:header scripts="estacao/deletar_raspberry.js"
	styles="lista.css" title="Raspberries">
</html:header>

<c:if test="${info != null}">
	<script>
		Materialize.toast('${info}', 4000)
	</script>
</c:if>

<c:choose>
	<c:when test="${raspberries.size() > 0}">
		<ul class="collection">
			<c:forEach items="${raspberries}" var="raspberry">
				<html:raspberry raspberry="${raspberry}" />
			</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<h4 class="center">Nenhum Raspberry cadastrado.</h4>
	</c:otherwise>
</c:choose>

<div class="fixed-action-btn horizontal"
	style="bottom: 45px; right: 40px;">
	<a href="${form}/${empresa.id}"
		class="btn-floating btn-large red waves-effect waves-light"> <i
		class="large material-icons">add</i>
	</a>
</div>

<html:footer></html:footer>