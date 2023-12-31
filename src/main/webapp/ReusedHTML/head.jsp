<%@ page import="com.tsw.studentsupps.Model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Site Head Nav Bar</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
<%String sitePath= request.getContextPath();%>
<header class="mainHeader" >
    <div class="wrapperHeader">
        <a onclick="openNav()" class="menuIcon"><i class="fa fa-bars"></i></a>
        <div class="logo">
            <a href="<%=sitePath%>/" class="site-logo-link"></a>
        </div>
        <nav class="navBar" id="myNavBar">
            <div class="mobileHeader">
                <a href="<%=sitePath%>/" class="homeBtn" id="hmBtn"><i class="fa fa-home"></i></a>
                <a onclick="subCatClose()" class="goBack" id="goBck"><i class="fa fa-angle-left"></i></a>
                <a style="flex-grow: 1"></a>
                <a href="javascript:void(0)" class="closeBtn" onclick="closeNav()"><i class="fa fa-times"></i></a>
            </div>
            <div class="navListContainer">
                <ul class="navCategories" id="navCats">
                    <li>
                        <div class="navBarPrimaryCategory">
                            <a href="<%=sitePath%>/Shop" title="Shop All">Shop All</a>
                        </div>
                    </li>
                    <li>
                        <div class="navBarPrimaryCategory" onclick="subCatOpen(1)">
                            <a href="<%=sitePath%>/Shop?filter=Boosters" title="Boosters">Boosters</a>
                            <i class="fa fa-caret-down" style="margin-left: 5px"></i>
                        </div>
                        <ul class="dropdown-content" id="drpCnt1">
                            <li>
                                <div class="navBarSecondaryCategory">
                                    <a href="<%=sitePath%>/Shop?filter=Boosters">All Boosters</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="<%=sitePath%>/Shop?filter=BoosterBundles">Booster Bundles</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <div class="navBarPrimaryCategory" onclick="subCatOpen(2)">
                            <a href="<%=sitePath%>/Shop?filter=Shakers" title="Shakers">Shakers</a>
                            <i class="fa fa-caret-down" style="margin-left: 5px"></i>
                        </div>
                        <ul class="dropdown-content" id="drpCnt2">
                            <li>
                                <div class="navBarSecondaryCategory">
                                    <a href="<%=sitePath%>/Shop?filter=Shakers">All Shakers</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="<%=sitePath%>/Shop?filter=ShakerBundles">Shaker Bundles</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <div class="navBarPrimaryCategory" onclick="subCatOpen(3)">
                            <a href="<%=sitePath%>/Shop?filter=Merch" title="Merch">Merch</a>
                            <i class="fa fa-caret-down" style="margin-left: 5px"></i>
                        </div>
                        <ul class="dropdown-content" id="drpCnt3">
                            <li>
                                <div class="navBarSecondaryCategory">
                                    <a href="<%=sitePath%>/Shop?filter=Merch">All Merch</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="<%=sitePath%>/Shop?filter=MerchTShirts">T-Shirts</a>
                                </div>
                                <div class="navBarSecondaryCategory">
                                    <a href="<%=sitePath%>/Shop?filter=MerchBackpacks">Backpacks</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <ul class="actions">
            <%if(session.getAttribute("Utente")!=null)
            {%>
                <li>
                    <div class="userInfo">
                        <%Utente u= (Utente) session.getAttribute("Utente");%>
                        <a>Bentornato, <%=u.getNome()%></a>
                    </div>
                </li>
            <%}%>
            <li>
                <div class="account-btn">
                    <a<%if(session.getAttribute("Utente")==null) {%>
                            href="<%=sitePath%>/Account" <%
                        } else {%>
                            onclick="actionOpen('Account')"
                        <%}%>
                    >
                        <i class="fa fa-user-circle"></i>
                    </a>
                </div>
                <ul class="dropdown-content" id="drpCntAccount">
                    <li>
                        <span class="closeAction" id="closeAct" onclick="actionClose()">
                            <i class="fa fa-times"></i>
                        </span>
                        <h2>Account</h2>
                        <div class="navBarSecondaryCategory" onclick="location.href='<%=sitePath%>/Account/EditProfile'">
                            <a href="<%=sitePath%>/Account/EditProfile">
                                Modifica Profilo
                                <i class="fa fa-user" style="margin-left: 5px"></i>
                            </a>
                        </div>
                        <div class="navBarSecondaryCategory" onclick="location.href='<%=sitePath%>/Account/Orders'">
                            <a href="<%=sitePath%>/Account/Orders">
                                Ordini
                                <i class="fa fa-truck" style="margin-left: 5px"></i>
                            </a>
                        </div>
                        <%if(session.getAttribute("Utente")!=null && ((Utente) session.getAttribute("Utente")).isAdmin()) {%>
                            <div class="navBarSecondaryCategory" onclick="location.href='<%=sitePath%>/Admin/Products'">
                                <a href="<%=sitePath%>/Admin/Products">
                                    Gestisci i prodotti
                                    <i class="fa fa-cart-plus" style="margin-left: 5px"></i>
                                </a>
                            </div>
                            <div class="navBarSecondaryCategory" onclick="location.href='<%=sitePath%>/Admin/Discount'">
                                <a href="<%=sitePath%>/Admin/Discounts">
                                    Gestisci gli sconti
                                    <i class="fa fa-percent" style="margin-left: 5px"></i>
                                </a>
                            </div>
                            <div class="navBarSecondaryCategory" onclick="location.href='<%=sitePath%>/Admin/Users'">
                                <a href="<%=sitePath%>/Admin/Users">
                                    Gestisci gli utenti
                                    <i class="fa fa-users" style="margin-left: 5px"></i>
                                </a>
                            </div>
                            <div class="navBarSecondaryCategory" onclick="location.href='<%=sitePath%>/Admin/Orders'">
                                <a href="<%=sitePath%>/Admin/Orders">
                                    Gestisci gli ordini
                                    <i class="fa fa-users" style="margin-left: 5px"></i>
                                    <i class="fa fa-truck"></i>
                                </a>
                            </div>
                        <%}%>
                        <div class="navBarSecondaryCategory" onclick="location.href='<%=sitePath%>/Logout'">
                            <a href="<%=sitePath%>/Logout">
                                Esci
                                <i class="fa fa-sign-out" style="margin-left: 5px"></i>
                            </a>
                        </div>
                    </li>
                </ul>
            </li>
            <li>
                <div class="cart-btn">
                    <a href="<%=sitePath%>/Cart"><i class="fa fa-shopping-cart"></i></a>
                </div>
            </li>
            <li>
                <div class="search-container">
                    <form action="<%=sitePath%>/Search" id="search-form-all" style="margin-bottom: 0">
                        <label>
                            <input type="text" id="searchField-all" placeholder="Cerca..." name="search" class="searchField" autocomplete="off">
                        </label>
                        <button type="submit" value="Cerca" class="searchBtn"><i class="fa fa-search"></i></button>
                    </form>
                </div>
                <ul class="dropdown-content-search" style="display: block;">
                    <li id="searchResult"></li>
                </ul>
            </li>
        </ul>
    </div>
</header>

<script>
    const contextPath= "<%=request.getContextPath()%>";

    let searchFieldHead= document.getElementById("searchField-all");
    let searchResults= document.getElementsByClassName("dropdown-content-search")[0];

    searchFieldHead.addEventListener("input", function() {
        if(window.matchMedia("(min-width: 1024px)").matches) {
            $.ajax({
                type: 'POST',
                url: contextPath + '/Search',
                dataType: "json",
                async: true,
                data: {
                    search: searchFieldHead.value
                },
                success: function(data) {
                    searchResults.style.display="block";
                    let searchResult= ('#searchResult');
                    $(searchResult).empty();

                    for (let i= 0; i<data.length; i++) {
                        let newDiv= document.createElement("div");
                        newDiv.className= "navBarSecondaryCategory";
                        newDiv.style.cursor= "pointer";
                        newDiv.style.display= "flex";
                        newDiv.style.alignItems= "center";
                        newDiv.style.gap="25px";

                        let escapedName= data[i].nome;
                        escapedName= escapedName.replaceAll(/%(?![0-9a-fA-F]{2})/g, "%25");
                        escapedName= escapedName.replaceAll("+", "%2B");

                        newDiv.setAttribute("onclick", "location.href='"+contextPath+"/Shop/Prodotto?prodName="+escapedName+"'");
                        newDiv.innerHTML=`
                            <figure class="imageWrapper">
                                <picture>
                                    <img style="max-width: 50px" src="`+contextPath+`/ProductImages/`+data[i].nome+`.png"
                                            class="imgProdErr" alt="`+data[i].nome+`">
                                </picture>
                            </figure>
                            <a href="`+contextPath+`/Shop/Prodotto?prodName=`+escapedName+`">`+data[i].nome+`</a>`

                        $(searchResult).append(newDiv);
                    }

                    let imgProds= document.querySelectorAll('.imgProdErr');
                    imgProds.forEach(img=>{
                        img.addEventListener('error', ()=>{
                            img.src="<%=request.getContextPath()%>/images/img_notfound.png";
                            img.alt="Immagine non trovata";
                            img.title="Immagine non trovata";
                        })
                    })
                }
            });
        }
    })

    function openNav() {
        let el= document.getElementById("myNavBar");
        if (el.className === "navBar")
            el.className = "navBar navBar--open";
    }
    function closeNav() {
        let el= document.getElementById("myNavBar");
        if (el.className === "navBar navBar--open")
            el.className = "navBar";
    }

    function subCatOpen(drpCntNumber) {
        let nav= document.getElementById("navCats");
        let drpCnt= document.getElementById("drpCnt"+drpCntNumber);
        if (nav.className === "navCategories")
            nav.className = "navCategories navCategories--sub";
        if (drpCnt.className === "dropdown-content")
            drpCnt.className = "dropdown-content dropdown-content--active";

        let hmBtn= document.getElementById("hmBtn");
        if (hmBtn.className === "homeBtn")
            hmBtn.className = "homeBtn homeBtn--remove";
        let goBck= document.getElementById("goBck");
        if (goBck.className === "goBack")
            goBck.className = "goBack goBack--appear";
    }
    function subCatClose() {
        let nav= document.getElementById("navCats");
        let drpCntArr= document.getElementsByClassName("dropdown-content");
        if (nav.className === "navCategories navCategories--sub")
            nav.className = "navCategories";
        for (let drpCnt of drpCntArr) {
            if (drpCnt.className === "dropdown-content dropdown-content--active")
                drpCnt.className = "dropdown-content";
        }

        let hmBtn= document.getElementById("hmBtn");
        if (hmBtn.className === "homeBtn homeBtn--remove")
            hmBtn.className = "homeBtn";
        let goBck= document.getElementById("goBck");
        if (goBck.className === "goBack goBack--appear")
            goBck.className = "goBack";
    }

    function actionOpen(drpName) {
        let drpCnt= document.getElementById("drpCnt"+drpName);
        if (drpCnt.className === "dropdown-content")
            drpCnt.className = "dropdown-content dropdown-content--active";
        else
            drpCnt.className = "dropdown-content";

        let closeAct= document.getElementById("closeAct");
        if (closeAct.className === "closeAction")
            closeAct.className = "closeAction closeAction--appear";
        else
            closeAct.className = "closeAction";
    }
    function actionClose() {
        let drpCntArr= document.getElementsByClassName("dropdown-content");

        for (let drpCnt of drpCntArr) {
            if (drpCnt.className === "dropdown-content dropdown-content--active")
                drpCnt.className = "dropdown-content";
        }

        let closeAct= document.getElementById("closeAct");
        if (closeAct.className === "closeAction closeAction--appear")
            closeAct.className = "closeAction";
    }


    window.addEventListener("scroll", function() {
        let body=  document.getElementsByTagName("body")[0];
        let head=  document.getElementsByClassName("mainHeader")[0];
        if (window.scrollY!==0){
            if (window.innerWidth>=1024){
                body.style.paddingTop="80px";
            }
            else{
                body.style.paddingTop="62px";
            }
            head.style.position="fixed";
            head.style.top="0px";
            head.style.left="0px";
            head.style.width="100%";
        } else{
            body.style.removeProperty("padding-top");
            head.style.removeProperty("position");
            head.style.removeProperty("top");
            head.style.removeProperty("left");
            head.style.removeProperty("width");
        }

    });

    $('#drpCntAccount, .account-btn, #myNavBar, .menuIcon, #searchField-all, .dropdown-content-search').click(function(e) {
        e.stopPropagation();
    })

    $(function(){
        $(document).click(function(){
            actionClose();
            subCatClose();
            closeNav();

            searchResults.style.display="none";
        });
    });
</script>
</body>
</html>
