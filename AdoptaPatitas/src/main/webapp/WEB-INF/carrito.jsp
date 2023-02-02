<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
    
<!DOCTYPE html>
<html lang="es" xmlns:th="http://www.thymeleaf.org">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Carrito AdoptaPatita</title>


<!-- Bootstrap core CSS -->
<link th:href="@{/vendor/bootstrap/css/bootstrap.min.css}"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link th:href="@{/css/heroic-features.css}" rel="stylesheet">

</head>

<body>

	<!-- Navigation -->
	<div th:include="usuario/template_usuario.html::header" th:if="${sesion==null}">	
	</div>
	
	<div th:include="usuario/template_usuario.html::header-user" th:unless="${sesion==null}"> </div>

	<!-- Page Content -->
	<div class="container">
		<!-- Page Heading/Breadcrumbs -->
		<h1 class="mt-4 mb-3">
			Spring eCommerce <small>Carrito</small>
		</h1>

		<ol class="breadcrumb">
			<li class="breadcrumb-item"><a th:href="@{/}">Home</a></li>
			<li class="breadcrumb-item active">Carrito</li>
		</ol>

		<!-- Blog Post -->
		<div class="card mb-4">
			<div class="card-body">
				<div class="row">
					<div class="col-lg-9">
						<table class="table">
							<thead>
								<tr>
									<th scope="col">Producto</th>
									<th scope="col">Precio</th>
									<th scope="col">Cantidad</th>
									<th scope="col">Total</th>
									<th scope="col">Acci�n</th>
								</tr>
							</thead>
							<tbody>
								<tr th:each="dorden:${cart}">
									<td th:text="${dorden.nombre}"></td>
									<td th:text="${dorden.precio}"></td>
									<td th:text="${dorden.cantidad}"></td>
									<td th:text="${dorden.total}"></td>
									<td><a th:href="@{/delete/cart/{id}   (id=${dorden.producto.id}) }" class="btn btn-danger">Quitar</a></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="col-lg-3">
						<h2 class="card-title">SUBTOTAL</h2>

						<ul class="list-group">
							<li class="list-group-item"><h5 th:text="${orden.total}"></h5></li>
							<a th:href="@{/order}" class="btn btn-dark">Ver Orden</a>
						</ul>
					</div>


				</div>
			</div>
		</div>
	</div>
	<!-- /.container -->

	<!-- Footer -->
	<div th:include="usuario/template_usuario.html::footer"></div>

	<!-- Bootstrap core JavaScript -->
	<script th:src="@{/vendor/jquery/jquery.min.js}"></script>
	<script th:src="@{/vendor/bootstrap/js/bootstrap.bundle.min.js}"></script>

</body>

</html>