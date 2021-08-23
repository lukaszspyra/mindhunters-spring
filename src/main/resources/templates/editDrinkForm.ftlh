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
    <link href="/css/addDrinkForm.css" rel="stylesheet">

    <#--Adds favicon-->
    <link rel="shortcut icon" href="/img/favicon.ico" >
    <link rel="icon" href="/img/favicon.ico">

    <script src="https://kit.fontawesome.com/84fd5d585b.js" crossorigin="anonymous"></script>

</head>
<body id="page-top justify-content-center " style="background-color: #1b1e21;">

<!--Navigation-->

<#include "navigation.ftlh">
<div style="margin-bottom: 20px; margin-top: 20px">
    <#--Form-->
    <form class="was-validated" id="form_data">


        <div class="container-fluid form-elements " style="width: 800px">


            <div class="col-12 mb-3">

                <label>Input recipe data: </label>

                <input name="drinkName" type="text" class="form-control integerInput md-3 " id="field"
                       placeholder="Insert name"
                       required minlength="3" value="${(drink.drinkName)!}">
                <div class="invalid-feedback alert alert-danger col-5">Input min. 3 chars</div>

            </div>

            <#--ALCOHOL STATUS-->

            <div class="row justify-content-between" style="margin: 0px">

                <div class="form-group md-3 col-5 ">
                    <select name="alcoholStatus" class="custom-select" required>
                        <option value="${(drink.alcoholStatus)!}">${(drink.alcoholStatus)!}</option>
                        <option value="Alcoholic">Alcoholic</option>
                        <option value="Non-alcoholic">Non-alcoholic</option>
                        <option value="Optional">Optional</option>
                    </select>
                    <div class="invalid-feedback alert alert-danger">Select alcohol status</div>
                </div>

                <#--CATEGORY-->

                <div class="form-group md-3 col-5 ">
                    <select name="category[name]" class="custom-select" required>
                        <option value="${(drink.categoryView.name)!}">${(drink.categoryView.name)!}</option>
                        <#list categories as category>
                            <option value="${category.name}">${category.name}</option>
                        </#list>
                    </select>
                    <div class="invalid-feedback alert alert-danger">Select category</div>
                </div>

            </div>

            <#--RECIPE-->


            <div class="form-group col-12">
                <label for="exampleFormControlTextarea1">Input recipe:</label>
                <textarea name="recipe" class="form-control" id="exampleFormControlTextarea1" rows="5" required
                          minlength="3">${(drink.recipe)!}</textarea>
                <div class="invalid-feedback alert alert-danger col-5">Input min. 5 chars</div>
            </div>


            <#--INGREDIENTS-->


            <div class=" add-group  justify-content-between">
                <div class=" sidebar-list">

                    <p hidden id="initialIngredientsCount">${(drink.drinkIngredientViews)!?size}</p>

                    <#list (drink.drinkIngredientViews)!>
                        <#items as drinkIngredientView>
                            <div class="row justify-content-between " id="ingrMeasure" style="margin: 0px">


                                <div class=" md-3 col-5 ">
                                    <input type="text" required maxlength="20" minlength="2"
                                           name="drinkIngredients[${(drinkIngredientView?counter)-1}][ingredient[name]]"
                                           value="${drinkIngredientView.name}"
                                           placeholder="Ingredient..." class="form-control " aria-required="true"/>
                                    <div class="invalid-feedback alert alert-danger">Input ingredient</div>
                                </div>

                                <div class=" md-3 col-5 ">
                                    <input type="text" required maxlength="20" minlength="2"
                                           name="drinkIngredients[${(drinkIngredientView?counter)-1}][measure[quantity]]"
                                           value="${drinkIngredientView.measure}"
                                           placeholder="Measure..." class="form-control "/>
                                    <div class="invalid-feedback alert alert-danger">Input measure</div>
                                </div>

                                <a href="javascript:void(0);" class="add_button col-1 " id="remove_blue"
                                   title="Add field">
                                    <svg class="bi bi-plus-square-fill" id="remove_blue" width="1em" height="1em"
                                         viewBox="0 0 16 16"
                                         fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                        <path fill-rule="evenodd"
                                              d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4a.5.5 0 0 0-1 0v3.5H4a.5.5 0 0 0 0 1h3.5V12a.5.5 0 0 0 1 0V8.5H12a.5.5 0 0 0 0-1H8.5V4z"/>
                                    </svg>
                                </a>
                                <#if (drinkIngredientView?counter) != 1>
                                    <a id="remove_blue" href="javascript:void(0);" class="remove_button col-1">
                                        <svg class="bi bi-trash" width="1em" height="1em" viewBox="0 0 16 16"
                                             fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"></path>
                                            <path fill-rule="evenodd"
                                                  d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"></path>
                                        </svg>
                                    </a>

                                </#if>

                            </div>

                        </#items>
                    </#list>

                    <#if !(drink?has_content)>

                        <div class="row justify-content-between " id="ingrMeasure" style="margin: 0px">


                            <div class=" md-3 col-5 ">
                                <input type="text" required maxlength="20" minlength="2"
                                       name="drinkIngredients[1][ingredient[name]]" value=""
                                       placeholder="Ingredient..." class="form-control " aria-required="true"/>
                                <div class="invalid-feedback alert alert-danger">Input ingredient</div>
                            </div>

                            <div class=" md-3 col-5 ">
                                <input type="text" required maxlength="20" minlength="2"
                                       name="drinkIngredients[1][measure[quantity]]" value=""
                                       placeholder="Measure..." class="form-control "/>
                                <div class="invalid-feedback alert alert-danger">Input measure</div>
                            </div>

                            <a href="javascript:void(0);" class="add_button col-1 " id="remove_blue" title="Add field">
                                <svg class="bi bi-plus-square-fill" id="remove_blue" width="1em" height="1em"
                                     viewBox="0 0 16 16"
                                     fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd"
                                          d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4a.5.5 0 0 0-1 0v3.5H4a.5.5 0 0 0 0 1h3.5V12a.5.5 0 0 0 1 0V8.5H12a.5.5 0 0 0 0-1H8.5V4z"/>
                                </svg>
                            </a>


                            <br>
                        </div>

                    </#if>


                </div>


            </div>

            <#--        IMAGE-->

            <#--            <div class="custom-file col-6 justify-content-between"-->
            <#--                 style="padding-left: 15px; padding-right: 15px; margin: 0px">-->
            <#--                <input type="file" name="image" style="margin: auto"/>-->
            <#--            </div>-->

            <#if ((drink)?has_content)>

                <input hidden name="image" style="margin: auto" value="${(drink.image)!}"/>

            </#if>


            <#--BUTTON-->

            <div class=" my-3 col-5 justify-content-between">
                <button type="button" class="btn btn-primary" name="save" value="save" id="submit_but">
                    Save drink
                </button>
            </div>


        </div>


    </form>

<#--    <form name="uploader" class="was-validated  " id="image_form" onsubmit="return false">-->
<#--        <div class="custom-file col-6 justify-content-between"-->
<#--             style="padding-left: 15px; padding-right: 15px; margin: 0px">-->
<#--            <input type="file" name="image" style="margin: auto"/>-->
<#--        </div>-->
<#--        <div class=" my-3 col-5 justify-content-between">-->
<#--            <button type="submit" class="btn btn-primary" name="image" value="save" id="submit_image_but"-->
<#--                    action="/drink-management" method="post" enctype="multipart/form-data">-->
<#--                Save drink-->
<#--            </button>-->
<#--        </div>-->
<#--    </form>-->


</div>
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
<script src="js/addDrinkForm.js"></script>
-->
<!--&lt;!&ndash;&lt;!&ndash; Core theme JS&ndash;&gt;&ndash;&gt;-->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script src="/js/editDrink.js"></script>
<#--
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
-->
</body>
</html>