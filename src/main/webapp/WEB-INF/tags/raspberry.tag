<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="raspberry" required="true"
	type="br.com.thiago.robotPi.model.Raspberry"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/img/person.png" var="foto" />
<c:set value="${contextPath }/raspberry" var="edit" />

<li class="collection-item avatar"><a href="/raspberry/${raspberry.id}"><img
		src="${foto}" alt="" class="circle"> <span class="title">${raspberry.nome}</span>
		<p>Drone</p> </a> <i id="lixeira_deleta_raspberry"
	class="secondary-content material-icons waves-effect waves-light"
	onclick="deleta_raspberry('${raspberry.id}')">delete</i></li>
