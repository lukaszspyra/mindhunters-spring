<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title>Mindhunters</title>
    <link href="/css/style.css" rel="stylesheet">

    <#--Adds favicon-->
    <link rel="shortcut icon" href="/img/favicon.ico" >
    <link rel="icon" href="/img/favicon.ico">

    <script src="https://kit.fontawesome.com/84fd5d585b.js" crossorigin="anonymous"></script>

    <style>
        .row + .row {
            border-top: 0;
        }

        div.row {
            border: 1px solid;
            border-color: #ffc107;
        }

    </style>


</head>
<body id="page-top" style="background-color: #1b1e21">


<#include "navigation.ftlh">


<section style="background-color: #1b1e21">
    <div class="container" style="color: white; height: max-content;">

        <form method="post" action="/admin/users">
            <label>Set user as ADMIN by id: </label>
            <input id="userId" type="number" min="2" name="u">
            <button type="submit" name="Set as admin">Set as admin</button>
        </form>
        <br>
        <form method="post" action="/admin/users">
            <label>Set admin as USER by id: </label>
            <input id="userId" type="number" min="2" name="a">
            <button type="submit" name="Set as admin">Set as user</button>
        </form>
        <br>
        <div class="row justify-content-center">
            <h3>Users registered in database</h3>
        </div>
        <div class="row justify-content-center">
            <div class="col-md-3">
                <h4>Id</h4>
            </div>
            <div class="col-md-3">
                <h4>Name</h4>
            </div>
            <div class="col-md-3">
                <h4>Email</h4>
            </div>
            <div class="col-md-3">
                <h4>Role</h4>
            </div>

        </div>

        <#list users as user>
            <div class="row justify-content-center">
                <div class="col-md-3">
                    <div>${user.id}</div>
                </div>

                <div class="col-md-3">
                    <div>${user.name}</div>
                </div>

                <div class="col-md-3">
                    <div>${user.email}</div>
                </div>

                <div class="col-md-3">
                    <div>${user.role}</div>
                </div>
            </div>
        </#list>


    </div>
    </div>
</section>


<#include "footer.ftlh">


<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="/js/homePage.js"></script>

</body>
</html>