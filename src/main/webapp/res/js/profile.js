function saveClientInformation() {

    var emailInput = $("#email");
    var firstNameInput = $("#firstname");
    var lastNameInput = $("#lastname");
    var birthdayInput = $("#birthdate");
    var passportInput = $("#passport");
    var addressInput = $("#address");

    var sendData = {
        email : emailInput.val(),
        password : passwordInput.val(),
        lastname : lastNameInput.val(),
        firstname : firstNameInput.val(),
        birthday: birthdayInput.val(),
        passport : passportInput.val(),
        address : addressInput.val()
    };

    $.ajax({
        type: "PUT",
        url : '/profile',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(sendData),
        success: function (response) {
            window.location = "/"
        },
        error: function () {
            alert("Problems with saving your information, please try later");
        }
    });
}

function showClientInfo() {

    $.ajax({
        type: "GET",
        url : '/profile',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            $("#firstname").attr("value", response.firstname);
            $("#lastname").attr("value", response.lastname);
            $("#email").attr("value", response.email);
            $("#datepicker").attr("value", response.birthday);

        },
        error: function () {
            alert(this.error);
        }
    });
}