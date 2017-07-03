<div class="row" style="height: 15%; margin:0;">
    <header id="ContentPlaceHolder1_UserCMSHeader1_header" class="header">

        <div class="headerInner">
            <div class="container">
                <h1 id="logo">
                    <a href="">
                        <img title="Swachh, Swachhata Augmentation through Corporate Helping Hands"
                             alt="Swachh, Swachhata Augmentation through Corporate Helping Hands"
                             src="<c:url value='/static/Images/logo.png' />" height="66" width="360">
                    </a>
                </h1>
                <div class="moudLogo">
                    <a href="http://moud.gov.in/" target="_blank">
                        <img title="Ministry of Urban Development" alt="Ministry of Urban Development"
                             src="<c:url value='/static/Images/moud-logo.png' />"
                             height="67" width="166">
                    </a>
                </div>
                <div class="loginRow">
                    <%--<a href="" class="button sprite registrBtn">Registration</a>--%>
                    <sec:authorize access="isAuthenticated()">
                        <th width="100"></th>
                        <a href="/logout" class="button sprite loginBtn">Logout</a>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        <th width="100"></th>
                        <a href="/login" class="button sprite loginBtn">Login</a>
                    </sec:authorize>
                </div>
                <div id="mainNav" class="mainNavigation">
                    <%--<nav class="menuPart">--%>
                    <%--<a href="/home" class="toggleMenu menu-button">--%>
                    <%--<span class="mobileMenu">Home</span>--%>
                    <%--<span class="iconBar"></span>--%>
                    <%--</a>--%>
                    <%--</nav>--%>
                    <nav class="menuPart">
                        <a href="#" class="toggleMenu menu-button">
                            <span class="mobileMenu">Menu</span>
                            <span class="iconBar"></span>
                        </a>
                        <div id="ContentPlaceHolder1_UserCMSHeader1_UserMenuHorizontal1_divMenu" class="menuPart cf">
                            <ul id="nav" class="hide">
                                <li class="MenuLi MenuLiFirst MenuLi1"><a class="menuFirstNode" href="/home">Home</a>
                                </li>
                                <li class="MenuLi MenuLi2"><a class="menuFirstNode" href="/admin-dashboard">Admin
                                    Dashboard</a>
                                </li>
                                <li class="MenuLi MenuLi2"><a class="menuFirstNode" href="/marker-display">Map
                                    Marker</a>
                                </li>
                                <li class="MenuLi MenuLi2"><a class="menuFirstNode" href="/report">Report</a>
                                </li>
                                <li class="MenuLi MenuLi2"><a class="menuFirstNode" href="/users-list">Users</a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <%--<nav class="menuPart">--%>
                    <%--<ul class="nav navbar-nav" style="font-size: large">--%>
                    <%--<li class="active">--%>
                    <%--<a href="/home" class=" menu-button">Home</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                    <%--<a href="/marker-display" class="toggleMenu menu-button">Map Marker</a>--%>
                    <%--</li>--%>
                    <%--</ul>--%>
                    <%--</nav>--%>

                </div>
            </div>
        </div>
    </header>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>

<script>
    $(document).ready(function () {
        $(".toggleMenu").on("click", function () {
            $(this).toggleClass("active");
            $("ul#nav").toggleClass("show");
        })
    });
</script>