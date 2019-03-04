<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="user" required="true"
	type="br.com.thiago.robotPi.model.User"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/img/person.png" var="foto" />
<c:set value="${contextPath }/user" var="edit" />

<li class="collection-item avatar"><a href="/user/${user.id}"><img
		src="${foto}" alt="" class="circle"> <span class="title">${user.nome}</span>
		<p>${user.telefone }</p> </a> <i id="lixeira_deleta_user"
	class="secondary-content material-icons waves-effect waves-light"
	onclick="deleta_user('${user.id}')">delete</i> </li>
