<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="html" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html:header scripts="dispositivo/deletar_dispositivo.js"
	styles="lista.css" title="Dispositivos">
</html:header>

<c:if test="${info != null}">
	<script>
		Materialize.toast('${info}', 4000)
	</script>
</c:if>

<c:choose>
	<c:when test="${dispositivos.size() > 0}">
		<ul class="collection">
			<c:forEach items="${dispositivos}" var="dispositivo">
				<html:dispositivo dispositivo="${dispositivo}" />
			</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<h4 class="center">Nenhum dispositivo cadastrado.</h4>
	</c:otherwise>
</c:choose>

<html:footer></html:footer>