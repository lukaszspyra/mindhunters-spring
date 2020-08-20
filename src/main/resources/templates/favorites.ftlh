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
    <link href="css/drink_list.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/84fd5d585b.js" crossorigin="anonymous"></script>

</head>
<body id="page-top" style="background-color: #1b1e21;">

<!--Navigation-->
<#include "navigation.ftlh">

<!--List-->
<nav aria-label="LIST" style="cursor: pointer;">

    <div class="container-fluid">
        <div class="row mx-2 my-2 justify-content-center">

            <!--categoriesSidebar-->
            <#if categories??>
                <#include "categoriesSidebar.ftlh">
            </#if>

            <!--LIST-->
            <div class="col-12 col-md-8 col-lg-8 col-xl-8">
                <ul class="list-unstyled  justify-content-center  ml-auto space_list  " id="ID03">
                    <#list drinkList as drink>

                        <li class="media ">
                            <div class="container ml-0 pl-0 mr-0 pr-0 ">
                                <div class="row list-element">
                                    <div class="col-md-auto d-flex justify-content-center " style="min-width: 205px">
                                        <div class="relative" id="ID04">
                                            <img src="${drink.image}"
                                                 class=" thumbnail_list justify-content-center" alt="...">
                                            <#if name??>
                                                <i class="icon-lock-star icons <#if favourites!?seq_contains(drink.id)>color_toggle_on </#if> "
                                                   title="Add/delete from favourites">
                                                    <svg id="star-fill" class="bi bi-star-fill  " width="1em"
                                                         height="1em"
                                                         viewBox="0 0 16 16"
                                                         xmlns="http://www.w3.org/2000/svg">
                                                        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                                    </svg>
                                                </i>
                                                <p hidden>${drink.id}</p>
                                            </#if>
                                        </div>
                                    </div>
                                    <div class="media-body  line-clamp col-sm-8 ml-0 pl-1 mr-0 pr-0"

                                         style="color: #ffc107" data-link="${"/single-view?drink=" + drink.id}">

                                        <div class="row col-sm d-flex mt-4 mt-md-0 ">
                                            <h5 class="col-md-6 mx-2 my-2  font-weight-bold text-center text-md-left">
                                                ${drink.drinkName}
                                            </h5>

                                            <h5 class="col-md-5 mx-2 my-2   text-center text-md-right" style="
                                                    padding-right: 0px;
                                                    margin-right: 0px;">
                                                ${drink.categoryView.name}
                                            </h5>

                                        </div>
                                        <div class="line-clamp mx-2 my-2 mt-4 mr-4 d-none d-md-block">
                                            ${drink.recipe}
                                        </div>
                                        <div class="line-clamp mx-2 my-2 mt-4 mr-4 ml-4 text-center text-md-right"
                                                <#if drink.alcoholStatus == "Alcoholic">
                                                    style="color: #dc3545"
                                                </#if>
                                        >
                                            ${drink.alcoholStatus}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </#list>

                    <#if !(drinkList?has_content) >
                        <#include "emptyList.ftlh">
                    </#if>
                </ul>
            </div>
        </div>
    </div>

    <!--Pagination-->

    <#include "pagination.ftlh">

</nav>

<!--Footer-->

<#include "footer.ftlh">
<link href="https://fonts.googleapis.com/css2?family=Cinzel&display=swap" rel="stylesheet">


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>

<!-- Bootstrap core JS-->

<script src="js/receipeList.js"></script>

</body>
</html>