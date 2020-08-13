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
    <link href="../../css/addDrinkForm.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/84fd5d585b.js" crossorigin="anonymous"></script>

</head>
<body id="page-top justify-content-center " style="background-color: #1b1e21;">

<!--Navigation-->

<#include "navigation.ftlh">

<form class="was-validated  " action="/drink-management" method="post" id="abc" enctype="multipart/form-data">

    <div class="container-fluid form-elements " style="width: 800px">


        <div class="col-12 mb-3">

            <label>Input recipe data: </label>

            <input name="name" type="text" class="form-control integerInput md-3 " id="field" placeholder="Insert name"
                   required minlength="3">
            <div class="invalid-feedback alert alert-danger col-5">Input min. 3 chars</div>

        </div>

        <div class="row justify-content-between" style="margin: 0px">

            <div class="form-group md-3 col-5 " >
                <select name="status" class="custom-select" required>
                    <option value="">Alcohol status</option>
                    <option value="Alcoholic">Alcoholic</option>
                    <option value="Non-alcoholic">Non-alcoholic</option>
                    <option value="Optional">Optional</option>
                </select>
                <div class="invalid-feedback alert alert-danger">Select alcohol status</div>
            </div>

            <div class="form-group md-3 col-5 ">
                <select name="category" class="custom-select" required>
                    <option value="">Category</option>
                    <#list categories as category>
                        <option value="${category.name}">${category.name}</option>
                    </#list>
                </select>
                <div class="invalid-feedback alert alert-danger">Select category</div>
            </div>

        </div>


        <div class="form-group col-12">
            <label for="exampleFormControlTextarea1">Input recipe:</label>
            <textarea name="recipe" class="form-control" id="exampleFormControlTextarea1" rows="5" required
                      minlength="3"></textarea>
            <div class="invalid-feedback alert alert-danger col-5">Input min. 5 chars</div>
        </div>


        <div class=" add-group  justify-content-between" >

            <div class=" sidebar-list" >
                <div class="row justify-content-between " id="ingrMeasure" style="margin: 0px">

                    <div class=" md-3 col-5 " >
                        <input type="text" required maxlength="20" minlength="2" name="ingredient" value="" placeholder="Ingredient..." class="form-control " aria-required="true"/>
                        <div class="invalid-feedback alert alert-danger">Input ingredient</div>
                    </div>

                    <div class=" md-3 col-5">
                        <input type="text" required maxlength="20" minlength="2" name="measure" value="" placeholder="Measure..." class="form-control "/>
                        <div class="invalid-feedback alert alert-danger">Input measure</div>
                    </div>

                    <a href="javascript:void(0);" class="add_button " id="remove_blue" title="Add field">
                        <svg class="bi bi-plus-square-fill" id="remove_blue" width="1em" height="1em"
                             viewBox="0 0 16 16"
                             fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4a.5.5 0 0 0-1 0v3.5H4a.5.5 0 0 0 0 1h3.5V12a.5.5 0 0 0 1 0V8.5H12a.5.5 0 0 0 0-1H8.5V4z"/>
                        </svg>
                    </a>
                    <br>
                </div>

            </div>

        </div>


        <div class="custom-file col-6 justify-content-between" style="padding-left: 15px; padding-right: 15px; margin: 0px">
            <input type="file" name="image" style="margin: auto"/>
        </div>


        <div class=" my-3 col-5 justify-content-between">
            <button type="button" class="btn btn-primary" name="save" value="save">
                Save drink
            </button>
        </div>


    </div>
</form>


<!--Footer-->

<#include "footer.ftlh">


<link href="https://fonts.googleapis.com/css2?family=Cinzel&display=swap" rel="stylesheet">


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>

<!-- Bootstrap core JS-->

<!--<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>-->
<!--&lt;!&ndash; Third party plugin JS&ndash;&gt;-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<#--
<script src="../../js/addDrinkForm.js"></script>
-->
<!--&lt;!&ndash;&lt;!&ndash; Core theme JS&ndash;&gt;&ndash;&gt;-->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script src="../../js/addDrinkForm.js"></script>
<#--
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
-->
</body>
</html>