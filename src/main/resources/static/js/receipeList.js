$(document).ready(function () {
    $('#ID03').on('click', '#FAVOURITES', function () {
        let drinkId = $(this).text().trim().toString();
        let url = new URL(window.location);

        let newUrl = url.origin + '/list?page=1';

            $.post(newUrl, {drinkId: drinkId});

            $(this).children('i').toggleClass("color_toggle_on");
    });
    window.location.reload();
});

$(document).ready(function () {
    $("[data-link]").click(function () {
            window.location.href = $(this).attr("data-link");
    });
});



$(document).ready(function () {

    let content = null;

    $('#ID01').on('click', '*', function () {
        content = $(this).children('p').text().trim();

        let url = new URL(window.location);

        var searchParams = url.searchParams;

        searchParams.sort();

        searchParams.set("page", "1");

        if (!searchParams.getAll("category").includes(content)) {

            searchParams.append("category", content);

            window.location = url;

        } else {

            let searchByCategory = searchParams.getAll("category");
            let searchByAlcoholStatus = searchParams.getAll("alcoholStatus");

            searchByCategory.splice(searchByCategory.indexOf(content), 1);

            if (searchByCategory.length > 0 && searchByAlcoholStatus.length > 0) {

                var newUrl = "&alcoholStatus=" + searchByAlcoholStatus.join("&alcoholStatus=")
                    + "&category=" + searchByCategory.join("&category=");

            } else if (searchByCategory.length > 0) {

                newUrl = "&category=" + searchByCategory.join("&category=");

            } else if (searchByAlcoholStatus.length > 0) {

                newUrl = "&alcoholStatus=" + searchByAlcoholStatus.join("&alcoholStatus=");

            } else {

                newUrl = "";
            }

            window.location = '/list?page=1&' + newUrl;

        }
    });

})

$(document).ready(function () {


    let content = null;

    $('#ID02').on('click', '*', function () {
        content = $(this).text().trim();

        let url = new URL(window.location);

        var searchParams = url.searchParams;

        searchParams.set("page", "1");

        if (!searchParams.getAll("alcoholStatus").includes(content)) {

            searchParams.append("alcoholStatus", content);

            window.location = url;

        } else {

            let searchByCategory = searchParams.getAll("category");

            let searchByAlcoholStatus = searchParams.getAll("alcoholStatus");

            searchByAlcoholStatus.splice(searchByAlcoholStatus.indexOf(content), 1);

            if (searchByCategory.length > 0 && searchByAlcoholStatus.length > 0) {

                var newUrl = "&alcoholStatus=" + searchByAlcoholStatus.join("&alcoholStatus=")
                    + "&category=" + searchByCategory.join("&category=");

            } else if (searchByCategory.length > 0) {

                newUrl = "&category=" + searchByCategory.join("&category=");

            } else if (searchByAlcoholStatus.length > 0) {

                newUrl = "&alcoholStatus=" + searchByAlcoholStatus.join("&alcoholStatus=");

            } else {

                newUrl = "";

            }

            window.location = '/list?page=1' + newUrl;

        }

    });

})


async function nextPage() {
    let url = new URL(window.location); // or construct from window.location
    let params = new URLSearchParams(url.search.slice(1));
    let pageNumber = parseInt(params.get('page'));
    let newPageNumber = pageNumber + 1;
    params.set('page', (newPageNumber).toString());
    let newURL = url.origin + url.pathname + '?'+ params.toString();
    location.replace(newURL);
}

async function previousPage() {
    let url = new URL(window.location); // or construct from window.location
    let params = new URLSearchParams(url.search.slice(1));
    let pageNumber = parseInt(params.get('page'));
    let newPageNumber = pageNumber - 1;
    if (newPageNumber > 0) {
        params.set('page', (newPageNumber).toString());
        let newURL = url.origin + url.pathname + '?' + params.toString();
        location.replace(newURL);
    }


}


