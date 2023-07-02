<%@ page import="java.util.List" %>
<%@ page import="com.tsw.studentsupps.Model.Categoria" %>
<%@ page import="com.tsw.studentsupps.Model.CategoriaDAO" %>
<%@ page import="com.tsw.studentsupps.Model.Prodotto" %>
<%@ page import="com.tsw.studentsupps.Model.ProdottocategoriaDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Aggiungi Prodotto | StudentSupps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/siteStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/formContainer.css">
</head>
<body>
    <jsp:include page="/ReusedHTML/head.jsp"/>

    <%Prodotto p= (Prodotto) request.getAttribute("prodToEdit");%>
    <main class="formContainer">
        <h1 class="formContainer-title">Aggiungi i parametri del prodotto</h1>
        <div class="formContainer-wrapper">
            <section class="formContainer-section">
                <c:if test="${requestScope.addProductStatus == 'nameTaken'}">
                    <p style="color: red">Nome Prodotto già usato!</p>
                </c:if>
                <form action="<%=request.getContextPath()%>/Admin/EditProduct" method="post" enctype="multipart/form-data">
                    <div class="form-field">
                        <label class="form-field-label" for="prodToEditId">Id</label>
                        <input class="form-field-input" id="prodToEditId" name="id" type="text" value="<%=p.getId()%>" readonly>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="imageAdd">Immagine</label>
                        <input class="form-field-inputFile" id="imageAdd" name="image" type="file" accept=".png">
                        <div class="form-field-comment">
                            Accetta .png. Non selezionandola verrà solo rinominata l'immagine già presente.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="nameAdd">Nome</label>
                        <input class="form-field-input" id="nameAdd" name="name" type="text" maxlength="50" autocomplete="off" value="<%=p.getNome()%>" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 50 caratteri. Accetta lettere, numeri, spazi, punti e trattini medi.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="descrAdd">Descrizione</label>
                        <input class="form-field-input" id="descrAdd" name="description" type="text" maxlength="250" value="<%=p.getDescrizione()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 2 caratteri. Massimo 250 caratteri.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="priceAdd">Prezzo</label>
                        <input class="form-field-input" id="priceAdd" name="price" type="number" min="0" max="10000000" step="0.01" value="<%=p.getPrezzo()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 10000000.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="ivaAdd">IVA</label>
                        <input class="form-field-input" id="ivaAdd" name="iva" type="number" min="0" max="100" value="<%=p.getIVA()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 100.
                        </div>
                    </div>
                    <div class="form-field">
                        <label class="form-field-label" for="quantityAdd">Quantità</label>
                        <input class="form-field-input" id="quantityAdd" name="quantity" type="number" min="0" max="10000000" value="<%=p.getQuantita()%>" autocomplete="off" required>
                        <div class="form-field-comment">
                            Minimo 0. Massimo 10000000.
                        </div>
                    </div>
                    <div class="form-field">
                        <div class="form-field-multipleCheckbox" onclick="openCheckboxs('Categorie')">
                            Categorie
                            <i class="fa fa-caret-down" ></i>
                        </div>
                        <ul class="form-field-nav-checkbox" style="height:0px" id="navCheckboxCategorie">
                            <%List<Categoria> catList= CategoriaDAO.doRetrieveAll();
                            for(Categoria cat: catList) {%>
                                <li class="form-field-checkbox">
                                    <input class="form-field-input" style="height: auto; width: auto;" id="category<%=cat.getNome()%>"
                                           name="categories" type="checkbox" value="<%=cat.getId()%>"
                                            <%if(ProdottocategoriaDAO.doExists(p.getId(), cat.getId())) {%> checked <%}%>>
                                    <label class="form-field-label" style="display: inline; margin-left: 5px;" for="category<%=cat.getNome()%>"><%=cat.getNome()%></label>
                                </li>
                            <%}%>
                        </ul>
                    </div>

                    <button class="form-submitButton" onclick="return (confermaParametri())" type="submit">Aggiorna</button>
                </form>
            </section>
        </div>
    </main>

    <script>
        function confermaParametri() {
            let name= document.getElementById('nameAdd').value;
            let descr= document.getElementById('descrAdd').value;

            const nameRGX= /^[\w\-. ]{2,50}$/;
            const descrRGX= /^.{2,250}$/;

            if(!nameRGX.test(name)){
                alert("Nome non valido!");
                return false;
            }
            if(!descrRGX.test(descr)){
                alert("Descrizione non valida!");
                return false;
            }

            return true;
        }

        function openCheckboxs(x){
            let navlink=document.getElementById("navCheckbox"+x);
            if (navlink.style.height==="0px")
                navlink.style.height="auto";
            else
                navlink.style.height="0px";
        }
    </script>

    <jsp:include page="/ReusedHTML/tail.jsp"/>
</body>
</html>