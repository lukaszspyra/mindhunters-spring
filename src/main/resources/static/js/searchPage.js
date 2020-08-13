//add search fields
$(document).ready(function () {
    var maxField = 10; //Input fields increment limitation
    var addButton = $('.add_button'); //Add button selector
    var wrapper = $('.add-group'); //Input field wrapper
    var fieldHTML = '<div class="list-group-item sidebar-list"><input class="input-ingredientName" type="text"' +
        ' maxlength="20" minlength="2"' +
        ' name="ing"' +
        ' value=""' +
        ' placeholder="Add ingredient"/><a' +
        ' id="remove_blue" href="javascript:void(0);"' +
        ' class="remove_button"><svg class="bi bi-trash" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">\n' +
        '  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>\n' +
        '  <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>\n' +
        '</svg><br></a></div>'; //New input field html
    var x = 1; //Initial field counter is 1

    //Once add button is clicked
    $(addButton).click(function () {
        //Check maximum number of input fields
        if (x < maxField) {
            x++; //Increment field counter
            $(wrapper).append(fieldHTML); //Add field html
        }

        //autocomplete ingredients extra fields
        $('.input-ingredientName').keyup(function () {

            if (this.value.length < 2) return;
            var substring = $(this).val();
            $.ajax({
                url: '/api/search/ingredient/' + substring,
                type: 'GET',
                success: function (data) {

                    let result = data.map(r => r.name);
                    $('.input-ingredientName').autocomplete({
                        source: result
                    });
                }
            });
        });

    });

    //Once remove button is clicked
    $(wrapper).on('click', '.remove_button', function (e) {
        e.preventDefault();
        $(this).parent('div').remove(); //Remove field html
        x--; //Decrement field counter
    });
});


//pagination
async function nextPage() {
    let url = new URL(window.location); // or construct from window.location
    let params = new URLSearchParams(url.search.slice(1));
    let pageNumber = parseInt(params.get('page'));
    let newPageNumber = pageNumber + 1;
    params.set('page', (newPageNumber).toString());
    let newURL = url.origin + '/search?' + params.toString();
    location.replace(newURL);
}

async function previousPage() {
    let url = new URL(window.location); // or construct from window.location
    let params = new URLSearchParams(url.search.slice(1));
    let pageNumber = parseInt(params.get('page'));
    let newPageNumber = pageNumber - 1;
    if (newPageNumber > 0) {
        params.set('page', (newPageNumber).toString());
        let newURL = url.origin + '/search?' + params.toString();
        location.replace(newURL);
    }

}


//autocomplete drink names
$('#input-name').keyup(function () {

    if (this.value.length < 2) return;
    var substring = $(this).val();
    $.ajax({
        url: '/api/search/drink/' + substring,
        type: 'GET',
        success: function (data) {

            let result = data.map(r => r.drinkName);
            $('#input-name').autocomplete({
                source: result
            });
        }
    });
});


//autocomplete ingredients main field
$('.input-ingredientName').keyup(function () {

    if (this.value.length < 2) return;
    var substring = $(this).val();
    $.ajax({
        url: '/api/search/ingredient/' + substring,
        type: 'GET',
        success: function (data) {

            let result = data.map(r => r.name);
            $('.input-ingredientName').autocomplete({
                source: result
            });
        }
    });
});


//click on list to go to single drink
$(document).ready(function() {
    $("[data-link]").click(function() {
        window.location.href = $(this).attr("data-link");
        return false;
    });
});