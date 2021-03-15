
function loadUserData() {
    var xmlhttp = createXMLHTTPObject();
    var sUrl = "loadUserData";
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            document.getElementById("userData").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open('GET', sUrl);
    xmlhttp.send(null);

}