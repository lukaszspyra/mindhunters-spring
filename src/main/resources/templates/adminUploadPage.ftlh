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
    <script src="https://kit.fontawesome.com/84fd5d585b.js" crossorigin="anonymous"></script>
</head>
<body id="page-top" style="background-color: #1b1e21">

<#include "navigation.ftlh">

<div id="body part1-top" style="background-color: #1b1e21">
    <div class="row container-fluid add-padding-admin" id="part1-top">
        <div class="over-photo2">
                <span class="admin-btn-block vis-of-btn" id="admin-btn-block">

                    <a class="btn btn-background  text-uppercase  text-warning btn-admin"
                       id="btn-admin2" a href="/admin/upload-json-api">LOAD DB FROM API</a>
                    <br>

                </span>
        </div>
        <div class="container-fluid load-file-browse" id="over-photo3">
            <div class="load-file-div container-fluid" id="load-file-div">
                <form method="post" action="/admin/upload-json-file" enctype="multipart/form-data"
                      id="vis-of-btn">
                    <div class="txt-center">
                        <p class="text-warning">
                            Load DB of Drinks from file
                        </p>
                    </div>
                    <p class="text-warning p-padding">Select DrinkJson</p>
                    <div class="text-warning lf-div col-8" style="display: inline-block">
                        <label class="submit-btn" style="display: inline-block"></label><br>
                        <input type="file" id="jsonFile" name="jsonFile" multiple
                               class="btn btn-background text-uppercase text-warning size-input-design">
                    </div>
                    <p class="text-warning p-padding">Select CategoryJson</p>
                    <div class="text-warning lf-div col-8" style="display: inline-block">
                        <label class="submit-btn" style="display: inline-block"></label><br>
                        <input type="file" id="jsonCatFile" name="jsonCatFile" multiple
                               class="btn btn-background text-uppercase text-warning size-input-design">
                    </div>

                    <div class="lf-div col-3" style="display: inline-block">
                        <input class="submit-btn btn btn-background  text-uppercase  text-warning" type="submit">
                    </div>

                    <div class="txt-warning-center" id="load-warning">

                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<#include "footer.ftlh">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymneiw iem jak wsatawilem u mnie footer to mi namiesal z ustawieniemous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="/js/BootstrapValidation.js"></script>
<script src="/js/homePage.js"></script>
</body>
</html>