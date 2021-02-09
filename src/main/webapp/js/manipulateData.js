function addNewTariff() {
    $.ajax({
        type: "GET",
        url: "/admin/addNewTariff",
        error: function () {
            alert("Some problems with adding tariffs :( Please try later");
        }
    })
}

function deleteTariff() {
}

function addNewOption() {

}