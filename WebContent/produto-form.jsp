<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <html>

        <head>
            <title>User Management Application</title>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        </head>

        <body>

            <header>
                <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
                    <div>
                        <a href="https://www.javaguides.net" class="navbar-brand"> Produto Management App </a>
                    </div>

                    <ul class="navbar-nav">
                        <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Produto</a></li>
                    </ul>
                </nav>
            </header>
            <br>
            <div class="container col-md-5">
                <div class="card">
                    <div class="card-body">
                        <c:if test="${produto != null}">
                            <form action="update" method="post">
                        </c:if>
                        <c:if test="${produto == null}">
                            <form action="insert" method="post">
                        </c:if>

                        <caption>
                            <h2>
                                <c:if test="${produto != null}">
                                    Edit Produto
                                </c:if>
                                <c:if test="${produto == null}">
                                    Add New Produto
                                </c:if>
                            </h2>
                        </caption>

                        <c:if test="${produto != null}">
                            <input type="hidden" name="id" value="<c:out value='${produto.id}' />" />
                        </c:if>

                        <fieldset class="form-group">
                            <label>Nome</label> <input type="text" value="<c:out value='${produto.nome}' />" class="form-control" name="nome" required="required">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Tipo</label> <input type="text" value="<c:out value='${produto.tipo}' />" class="form-control" name="tipo">
                        </fieldset>


                        <button type="submit" class="btn btn-success">Save</button>
                        </form>
                    </div>
                </div>
            </div>
        </body>

        </html>