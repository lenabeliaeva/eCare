function showAllTariffs() {
    $("#source").empty();
    $.ajax({
        type: "GET",
        url: "/tariffs",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            $("#source")
                .append(
                    '<div class="col-sm-12 prop rounded center ">' +
                    '<div class="col-sm-12">' +
                    '<div class="dt-buttons btn-group">' +
                    "<table id='table' class=\"table table-hover\">"
                );
            $("#table")
                .append(
                    "<tr>" +
                    "<td>Name</td>" +
                    "<td>Price</td>" +
                    "</tr>"
                );
            for (var i = 0; i < response.length; i++) {
                $("#table")
                    .append(
                        "<tr>" +
                        "<td>" + response[i].name + "</td>" +
                        "<td>" + response[i].price + "</td>" +

                        +"</tr>"
                    );
            }
            $("#table")
                .append("</table>");
            $("#source")
                .append(
                    "<input type='submit' class=\"btn btn-outline-primary\" onclick='addNewTariff()' value='Add new tariff'/>"
                );
        },
        error: function () {
            alert("Some problems with getting tariffs :( Please try later");
        }
    });
}

function showAllOptions() {
    $("#os").empty();
    $.ajax({
        type: "GET",
        url: "/options",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            $("#os")
                .append(
                    '<div class="col-sm-12 prop rounded center ">' +
                    '<div class="col-sm-12">' +
                    '<div class="dt-buttons btn-group">' +
                    "<table id='table' class=\"table table-hover\">"
                );
            $("#table")
                .append(
                    "<tr>" +
                    "<td>Name</td>" +
                    "<td>Price</td>" +
                    "<td>Connection cost</td>" +
                    "</tr>"
                );
            for (var i = 0; i < response.length; i++) {
                $("#table")
                    .append(
                        "<tr>" +
                        "<td>" + response[i].name + "</td>" +
                        "<td>" + response[i].price + "</td>" +
                        "<td>" + response[i].connectionCost + "</td>"
                        + "</tr>"
                    );
            }
            $("#table")
                .append("</table>");
        },
        error: function () {
            alert("Some problems with getting options :( Please try later ");
        }
    })
}
