
//add search fields
$(document).ready(function () {
    var maxField = 10; //Input fields increment limitation
    var addButton = $('.add_button'); //Add button selector
    var wrapper = $('.add-group'); //Input field wrapper
    var fieldHTML =
        '<div class="list-group-item sidebar-list" >' +
        '<div class="row justify-content-between " id="ingrMeasure" style="margin: 0px">' +
        '<input type="text" required maxlength="20" minlength="2" name="ingredient" value="" placeholder="Ingredient..." class="form-control md-3 col-5"/>'+
        '<input type="text" required maxlength="20" minlength="2" name="measure" value="" placeholder="Measure..." class="form-control md-3 col-5"/>' +
        '<a id="remove_blue" href="javascript:void(0);"' +
        ' class="remove_button">' +
        '<svg class="bi bi-trash" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">\n' +
        '<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>\n' +
        '<path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>\n' +
        '</svg><br></a></div>'; //New input field html

    var x = 1; //Initial field counter is 1

    //Once add button is clicked
    $(addButton).click(function () {
        //Check maximum number of input fields
        if (x < maxField) {
            x++; //Increment field counter
            $(wrapper).append(fieldHTML); //Add field html
        }
    });

    //Once remove button is clicked
    $(wrapper).on('click', '.remove_button', function (e) {
        e.preventDefault();
        $(this).parent('div').remove(); //Remove field html
        x--; //Decrement field counter
    });
});


$(document).ready(function () {


    $( "#my-form" ).submit(function( event ) {
        event.preventDefault();
    });



    $('#btn').click(function () {
        let first_number = 0;
        let second_number = 1;

        const size = $("#field").val();
        result = $("#field");
        let output = 'Ciąg Fibbonaciego dla ' + size + ' wyrazów:' + '<br><br> 0 <br> 1';

        $.post("", {value : size});

        if ( $.isNumeric(size) && size>0 ) {
            $("#panel").slideUp({duration: 400});

            for (i = 0; i < size - 2; i++) {
                var next_number = first_number + second_number;
                output = (output + ("<br>") + next_number);
                first_number = second_number;
                second_number = next_number;
            }
            $("#result").html(output);



            document.getElementById("fibonacci").innerHTML = output;
            // w.document.body.innerHTML = output;
            $('#exampleModal').modal('toggle');



        } else {
            document.getElementById("fibonacci").innerHTML = "Wprowadź odpowiednią liczbę";

            $("#panel").slideDown({duration: 400});

        }

    });

    return false;
});

