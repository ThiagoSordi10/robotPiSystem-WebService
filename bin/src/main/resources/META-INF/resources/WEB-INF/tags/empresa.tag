<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="empresa" required="true"
	type="br.com.thiago.robotPi.model.Empresa"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/img/person.png" var="foto" />
<c:set value="${contextPath }/empresa" var="edit" />

<li class="collection-item avatar"><a href="/empresa/${empresa.id}"><img
		src="${foto}" alt="" class="circle"> <span class="title">${empresa.nome}</span>
		<p>${empresa.cep}</p> </a> <i id="lixeira_deleta_user"
	class="secondary-content material-icons waves-effect waves-light"
	onclick="deleta_user('${empresa.id}')">delete</i></li>
