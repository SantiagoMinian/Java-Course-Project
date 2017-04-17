function form(name, action){
    if(name == "new" && action == "open") $("#popupbox").css("display", "block");
    else if (name == "new" && action == "close") $("#popupbox").css("display", "none");
    else if (name == "modify" && action == "open") $("#popupbox").css("display", "block");
    else if (name == "modify" && action == "close") $("#popupbox").css("display", "none");
}