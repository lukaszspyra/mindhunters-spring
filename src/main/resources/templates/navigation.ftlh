<nav class="navbar navbar-expand-lg" id="mainNav" style="background-color: #1b1e21">
    <div class="container-fluid">
        <a href="/" class="navbar-brand ">
            <img  src="/img/logomindhunters.jpg" alt="logo">
        </a>

        <div class="navbar-toggler  text-warning" type="button"
             data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive"
             aria-expanded="false" aria-label="Toggle navigation" style="border-color:#fed136">
            <a> Menu
                <svg class="bi bi-justify" width="1.5em" height="1.4em" viewBox="0 0 16 16" fill="currentColor"
                     xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M2 12.5a.5.5 0 01.5-.5h11a.5.5 0 010 1h-11a.5.5 0 01-.5-.5zm0-3a.5.5 0 01.5-.5h11a.5.5 0
                          010 1h-11a.5.5 0 01-.5-.5zm0-3a.5.5 0 01.5-.5h11a.5.5 0 010 1h-11a.5.5 0 01-.5-.5zm0-3a.5.5
                          0 01.5-.5h11a.5.5 0 010 1h-11a.5.5 0 01-.5-.5z" clip-rule="evenodd"/>
                </svg>
            </a>
        </div>

        <#if role??>
            <#if role = "ADMIN" || role = "SUPER_ADMIN">

                <div class="btn-group">
                    <button type="button" class="btn dropdown-toggle btn-outline-warning btn-background"
                            data-toggle="dropdown" aria-haspopup="true"
                            aria-expanded="false">

                        Admin panel
                    </button>
                    <div class="dropdown-menu dropdown-menu-right text-warning" style="text-align: center;
                    background-color:#1b1e21">


                        <li><a class="text-warning" href="/admin/users">User Management</a></li>
                        <div class="dropdown-divider"></div>

                        <li><a class="text-warning" href="/admin/statistics">Statistics</a></li>
                        <div class="dropdown-divider"></div>

                        <li><a class="text-warning" href="/admin/to-approve-list">Drinks to approve</a></li>
                        <div class="dropdown-divider"></div>

                        <li><a class="text-warning" href="/admin/page">Back up drinks upload</a></li>


                    </div>
                </div>

            </#if>
        </#if>


        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav text-uppercase ml-auto">


                <li class="nav-item"><a class="nav-link js-scroll-trigger text-warning">
                        << Welcome ${name ! "Stranger"} >></a>
                </li>
                <li class="nav-item"><a class="nav-link js-scroll-trigger text-warning" href="/">Home</a></li>
                <li class="nav-item"><a class="nav-link js-scroll-trigger text-warning" href="/list">Recipes</a>
                </li>
                <li class="nav-item"><a class="nav-link js-scroll-trigger text-warning" href="/search">Search</a>
                </li>

                <#if role??>
                    <#if role = "USER" || role = "ADMIN" || role = "SUPER_ADMIN">
                        <li class="nav-item"><a class="nav-link js-scroll-trigger text-warning" href="/favourites">Favourites</a>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger text-warning" href="/drink-management/create">Drink creator</a>
                        </li>
                    </#if>
                </#if>


                <div>
                    <li class="nav-item">
                    </li>
                </div>


                <div class="btn btn-outline-warning btn-background">

                    <#if role??>
                        <#if role = "USER" || role = "ADMIN" || role = "SUPER_ADMIN">
                            <li class="nav-item"><a class="nav-link js-scroll-trigger text-warning"
                                                    href="/oauth_logout">Logout</a>
                        <#else>
                            <li class="nav-item"><a class="nav-link js-scroll-trigger text-warning"
                                                    href="/oauth_login">Login</a>
                            </li>
                        </#if>

                        </li>
                    </#if>
                </div>

            </ul>
        </div>
    </div>
</nav>