<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="estacao" required="true"
	type="br.com.thiago.robotPi.model.Estacao"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/img/person.png" var="foto" />
<c:set value="${contextPath }/estacao" var="edit" />

<li class="collection-item avatar"><a href="/estacao/${estacao.id}"><img
		src="${foto}" alt="" class="circle"> <span class="title">${estacao.nome}</span>
		<p>${estacao.id}</p> </a> <i id="lixeira_deleta_estacao"
	class="secondary-content material-icons waves-effect waves-light"
	onclick="deleta_estacao('${estacao.id}')">delete</i></li>
