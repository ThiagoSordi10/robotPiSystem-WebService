<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="user" required="true"
	type="br.com.thiago.robotPi.model.User"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/img/person.png" var="foto" />
<c:set value="${contextPath }/user" var="edit" />

<li class="collection-item avatar">
	<div class="row">
		<div class="col s6">
			<a href="/user/${user.id}">
				<img src="${foto}" alt="" class="circle"> <span class="title">${user.nome}</span>
				<p>${user.telefone }</p> 
				<c:if test="${user.desativado == 1}">
					<p>Usuário desativado</p>
				</c:if>
			</a> 
		</div>
		<div class="col s6">
			<c:if test="${user.desativado == 1}">
				<a onclick="ativa_user('${user.id}')" class="btn waves-effect waves-light" 
				style="margin-top: 0%;float:right;" >Ativar</a>
			</c:if>
			 <c:if test="${user.desativado == 0}">
					<a onclick="desativa_user('${user.id}')" class="btn waves-effect waves-light" 
				style="margin-top: 0%;float:right;"
				value="Desativar">Desativar</a>
			</c:if>
		</div>
	</div>
</li>
