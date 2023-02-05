<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	</head>
	<body>
	<div class="container-fluid ">
			<nav class="navbar navbar-expand-lg" style="background-color: #D9F0DE">
	  			<div class="container-fluid">
	    			<a href="http://localhost:8080"><img src="images/adopta_patitas.png" width="80" height="80" class="d-inline-block align-top" alt="Logo"></a>
	    		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      		<span class="navbar-toggler-icon"></span>
	    		</button>
	    		<div class="collapse navbar-collapse" id="navbarSupportedContent">
	      			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
	        		<li class="nav-item">
	          		<a class="nav-link" href="/blog">blog</a>
	        		</li>
	        		<li class="nav-item">
	          		<a class="nav-link" href="/apadrina">Apadrina</a>
	        		</li>
	        		<li class="nav-item dropdown">
	          		<a class="nav-link" href="/adopta">Adopta</a>
	        		</li>
	       	 		<li class="nav-item">
	          		<a class="nav-link" href="/tienda">Articulos</a>
	       	 		</li>
	      			</ul>
	    		</div>
	    		<div>
					<form action="/logout" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" value="Cerrar Sesión" class="btn btn-dark"/>
					</form>
				</div>
	  		</div>
			</nav>
		</div>
	
		<div class="container">
			<div class="row mt-4">
				<div class="col-8">
					<img src="/img/${pet.image}" class="img-fluid mx-auto d-block" style="height: 400px; width: 380px"/>								
					<table class="table table-bordered mt-5">
						<thead>
							<tr>
								<th>Nombre</th>
								<th>Edad</th>
								<th>Sexo</th>
								<th>N° Vacunas</th>
								<th>Desparasitad@</th>
								<th>Microchip</th>
							</tr>
						</thead>
								<tr>
									<td>${pet.name}</td>
									<td>${pet.age}</td>
									<td>${pet.sexo}</td>
									<td>${pet.vaccine}</td>
									<td>${pet.deworming}</td>
									<td>${pet.chip}</td>
								</tr>	
						</tbody>
					</table>
				</div>
				<div class="col-4 mt-2">
					<h1>Editar Mascota</h1>
					<form:form action="/update/pet" method="POST" modelAttribute="ObjectPet" enctype="multipart/form-data">
						<input type="hidden" name="_method" value="PUT">
						<form:hidden path="id" value="${pet.getId()}" />
						<div class="form-group">
							<form:label path="name">Nombre:</form:label>
							<form:input path="name" class="form-control" value="${pet.getName()}" readonly="true"/>
							<form:errors path="name" class="text-danger"/>
						</div>
						
						<div class="form-group">
							<form:label path="age">Edad:</form:label>
							<form:input path="age" class="form-control" value="${pet.getAge()}"/>
							<form:errors path="age" class="text-danger"/>
						</div>
						
						<div class="form-group">
							<form:label path="sexo">Sexo (Macho / Hembra):</form:label>
							<form:input path="sexo" class="form-control" value="${pet.getSexo()}" readonly="true"/>
							<form:errors path="sexo" class="text-danger"/>
						</div>				
						
						<div class="form-group">
							<form:label path="vaccine">Vacunas:</form:label>
							<form:input path="vaccine" class="form-control" value="${pet.getVaccine()}"/>
							<form:errors path="vaccine" class="text-danger"/>
						</div>
						
						<div class="form-group">
							<form:label path="deworming">Desparacitado:</form:label>
							<form:select path="deworming" class="form-control">
								<c:forEach items="${options}" var="option">
									<c:choose>
										<c:when test="${option.equals(pet.deworming)}">
											<option selected value="${option}">${option}</option>
										</c:when>
										<c:otherwise>
											<option value="${option}">${option}</option>
										</c:otherwise>
									</c:choose>				   
								</c:forEach>
							</form:select>						
						</div>
						
						<div class="form-group">
							<form:label path="chip">Chip:</form:label>
							<form:select path="chip" class="form-control">
								<c:forEach items="${options}" var="option">
									<c:choose>
										<c:when test="${option.equals(pet.chip)}">
											<option selected value="${option}">${option}</option>
										</c:when>
										<c:otherwise>
											<option value="${option}">${option}</option>
										</c:otherwise>
									</c:choose>				   
								</c:forEach>
							</form:select>	
						</div>
						<div class="form-group">
							<label>Agrega una imagen</label>
							<input type="file" name="imagen" class="form-control" value="${pet.image}"/>
						</div>
						
						<!--  <div class="form-group">
							<label>Agrega una imagen</label>
							<input type="file" name="imagen" class="form-control"/>
						</div>
						-->		
						<form:hidden path="creator_pet" value="${user.id}"/>
						<input type="submit" value="Actualizar Información" class="btn btn-info mt-4"/>												
					</form:form>
				</div>
			</div>
		</div>
	</body>
</html>