<!doctype html>
<html lang="en">
<head>
    <#-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <#-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="/css/styleSingleViewSearch.css" rel="stylesheet">

    <#--Adds favicon-->
    <link rel="shortcut icon" href="/img/favicon.ico" >
    <link rel="icon" href="/img/favicon.ico">

    <#-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">

    <title>Drink</title>

</head>
<body id="page-top" style="background-color: #1b1e21">

<#include "navigation.ftlh">

<section style="background-color: #1b1e21">
    <div class="container" style="color: white; height: max-content;">

        <#if !errorMessage??>

            <div class="container" style="color: white">
                <br><br><br>
                <div class="row justify-content-center" id="ID03">
                    <div class="col-md-3">
                        <h4>${drink.drinkName}</h4>
                        <#if name??>
                            <i class="icon-lock-star icons <#if favourites!?seq_contains(drink.id)>color_toggle_on </#if> "
                               title="Add/delete from favourites" id="FAVOURITES" style="position: static">
                                <svg id="star-fill" class="bi bi-star-fill  " width="1em" height="1em"
                                     viewBox="0 0 16 16"
                                     xmlns="http://www.w3.org/2000/svg">
                                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                </svg>
                            </i>
                            |
                            <i class="icon-lock" title="Delete recipe" id="DELETE">
                                <svg class="bi bi-trash" width="1em" height="1em" viewBox="0 0 16 16"
                                     fill="currentColor"
                                     xmlns="http://www.w3.org/2000/svg">
                                    <path d="M5.5 5.5A.5.5 0 016 6v6a.5.5 0 01-1 0V6a.5.5 0 01.5-.5zm2.5 0a.5.5 0 01.5.5v6a.5.5 0 01-1 0V6a.5.5 0 01.5-.5zm3 .5a.5.5 0 00-1 0v6a.5.5 0 001 0V6z"/>
                                    <path fill-rule="evenodd"
                                          d="M14.5 3a1 1 0 01-1 1H13v9a2 2 0 01-2 2H5a2 2 0 01-2-2V4h-.5a1 1 0 01-1-1V2a1 1 0 011-1H6a1 1 0 011-1h2a1 1 0 011 1h3.5a1 1 0 011 1v1zM4.118 4L4 4.059V13a1 1 0 001 1h6a1 1 0 001-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"
                                          clip-rule="evenodd"/>
                                </svg>
                            </i>

                            |
                            <i class="icon-lock" title="Edit recipe" id="EDIT">
                                <svg class="bi bi-pen" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor"
                                     xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd"
                                          d="M5.707 13.707a1 1 0 01-.39.242l-3 1a1 1 0 01-1.266-1.265l1-3a1 1 0 01.242-.391L10.086 2.5a2 2 0 012.828 0l.586.586a2 2 0 010 2.828l-7.793 7.793zM3 11l7.793-7.793a1 1 0 011.414 0l.586.586a1 1 0 010 1.414L5 13l-3 1 1-3z"
                                          clip-rule="evenodd"/>
                                    <path fill-rule="evenodd"
                                          d="M9.854 2.56a.5.5 0 00-.708 0L5.854 5.855a.5.5 0 01-.708-.708L8.44 1.854a1.5 1.5 0 012.122 0l.293.292a.5.5 0 01-.707.708l-.293-.293z"
                                          clip-rule="evenodd"/>
                                    <path d="M13.293 1.207a1 1 0 011.414 0l.03.03a1 1 0 01.03 1.383L13.5 4 12 2.5l1.293-1.293z"/>
                                </svg>
                            </i>
                            <br>
                        </#if>

                        <p hidden>${drink.id}</p>
                        <br>
                        <#if drink.drinkId??>
                            <div>ID number: ${drink.drinkId}</div>
                        </#if>

                        <div>Category: ${drink.categoryView.name}</div>

                        <#if drink.alcoholStatus == "Alcoholic" || drink.alcoholStatus == "Optional alcohol">
                            <#assign color="red">
                        <#else>
                            <#assign color="white">
                        </#if>
                        <div style="color:${color}">${drink.alcoholStatus}</div>
                        <br>

                        Rate this recipe!
                        <br>
                        <div id="rateYo"></div>

                    </div>
                    <div class="col-md-3">
                        <#if drink.image!?trim?has_content>
                            <img src="${drink.image}" style="
                        border-radius: 10px;
  padding: 5px; width: 70%;" alt="">
                        <#else>
                            <img src="img/drinkimage-not-avail.jpg" style="
                        border-radius: 10px;
  padding: 5px; width: 70%;" alt="">
                        </#if>

                    </div>
                </div>

                <div class="row justify-content-center">
                    <div class="col-md-3">
                        <br><br><br>
                        <div><h5>Ingredients and measure:<br><br></h5></div>

                        <#list drink.drinkIngredientViews>
                            <#items as drinkIngredientView>

                                <div> ${drinkIngredientView.name} : ${drinkIngredientView.measure}</div>

                            </#items>
                        </#list>

                    </div>
                    <div class="col-md-3">
                        <br><br><br>
                        <div><h5>Instructions:<br><br></h5></div>
                        <div>${drink.recipe}</div>
                    </div>
                </div>

                <br><br>
                <div class="row-copy" style="font-size: 12px">

                    <i> Modified: ${drink.date}</i>

                </div>

            </div>

        <#else>
            <div class="container ml-0 pl-0 mr-0 pr-0 ">
                <div class="row justify-content-center">
                    <div class="col-md-3" style="font-size: 12px; text-align: center">
                        <br><br><br>
                        <h3 style="color: #ff0000">${errorMessage}</h3>
                        <br><br><br>
                        <br><br><br>
                        <br><br><br>
                        <br><br><br>
                        <br><br><br>
                    </div>
                </div>

            </div>

        </#if>

    </div>
</section>

<#include "footer.ftlh">

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="https://kit.fontawesome.com/84fd5d585b.js" crossorigin="anonymous"></script>
<script src="/js/singleDrinkView.js"></script>

<#-- RateYo rating plugin -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>

<script>

    <#-- Make sure that the dom is ready -->
    $(function () {

        $("#rateYo").rateYo({
            rating: ${rating ! "0"},
            ratedFill: "#FFD119",
            starWidth: "18px",
            halfStar: true,
            onSet: function (rating, rateYoInstance) {
                $.ajax({
                    type: 'POST',
                    url: '/api/rate/drinks/${drink.id}/rating/' + rating,
                    success: function () {
                        alert("Thank you for your vote. Your rating is: " + rating);
                        location.reload();
                    },
                    statusCode: {
                        403: function () {
                            alert("You can vote only once!");
                            location.reload();
                        }
                    }
                });

            }

        });

    });

</script>

</body>
</html>