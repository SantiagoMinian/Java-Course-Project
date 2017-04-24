function form(name, action, id){
    if(name == "new" && action == "open") {
        $("#popupbox").css("display", "block");
        $("#cuitDNI").prop('disabled', false);
        $("#formalta")[0].reset();
        $("#formalta").on("submit", function(e) {
            e.preventDefault();
            e.returnValue = false;
            saveClient();
        });
    }
    else if (name == "new" && action == "close") $("#popupbox").css("display", "none");
    else if (name == "modify" && action == "open") {
        $("#popupbox").css("display", "block");
        $("#cuitDNI").prop('disabled', true);
        $("#formalta").on("submit", function(e) {
            e.preventDefault();
            e.returnValue = false;
            updateClient();
        });
        fillForm(id);
    }
    else if (name == "modify" && action == "close") $("#popupbox").css("display", "none");
}

function saveClient() {
    var client = getClientFromForm();
    $.ajax({
        url: "/clients/add",
        type: "post",
        data: JSON.stringify(client),
        success: function(data){
            location.reload();
        }
    });
}

function updateClient() {
    var client = getClientFromForm();
    $.ajax({
        url: "/clients/update",
        type: "put",
        data: JSON.stringify(client),
        success: function(data){
            location.reload();
        }
    });
}

function deleteClient(id) {
    $.ajax({
        url: "/clients/remove",
        type: "delete",
        data: JSON.stringify({ cuitDNI: id}),
        success: function(data){
            location.reload();
        }
    });
}

function fillForm(id) {
    $.ajax({
            url: "/client/" + id + "/json",
            type: "get",
            dataType: "json",
            success: function(data){
                $("#cuitDNI").val(data.cuitDNI),
                $("#name").val(data.name),
                $("#surname").val(data.surname),
                $("#legalName").val(data.legalName),
                $("#phone").val(data.phone),
                $("#longitude").val(data.longitude),
                $("#latitude").val(data.latitude),
                $("#address").val(data.address),
                $("#mail").val(data.mail),
                $("#type").val(data.type)
            }
    });
}

function getClientFromForm() {
    return client = {
                         cuitDNI: $("#cuitDNI").val(),
                         name: $("#name").val(),
                         surname: $("#surname").val(),
                         legalName: $("#legalName").val(),
                         phone: $("#phone").val(),
                         longitude: $("#longitude").val(),
                         latitude: $("#latitude").val(),
                         address: $("#address").val(),
                         mail: $("#mail").val(),
                         type: $("#type").val()
    }
}