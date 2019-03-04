<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="estacao" required="true"
	type="br.com.thiago.robotPi.model.Estacao"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set value="${contextPath }/img/person.png" var="foto" />
<c:set value="${contextPath }/estacao" var="edit" />

<li class="collection-item avatar">
	<div class="row">
		<div class="col s5">
			<a href="/estacao/${estacao.id}">
				<img src="${foto}" alt="" class="circle"> <span class="title">${estacao.nome}</span>
				<p>${estacao.id}</p> 
			</a> 
			<i id="lixeira_deleta_estacao" class="secondary-content material-icons waves-effect waves-light"
			onclick="deleta_estacao('${estacao.id}')">delete</i>
		</div>
		<div class="col s6">
			<a class="btn waves-effect waves-light" style="margin-top: 0%;float:right;" 
			href="https://chart.googleapis.com/chart?cht=qr&chs=450x450&chl=${estacao.id}" >
			Baixar estação ${estacao.nome}</a>
		</div>
</li>