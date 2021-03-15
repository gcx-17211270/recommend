

//asp对Ajax的响应
function request(url) {
    var xmlhttp = createXMLHTTPObject();
    xmlhttp.open("GET", url, false);
    xmlhttp.send(null);
    document.writeln(xmlhttp.responseText);
}

//J2EE对Ajax的响应
function simple_ajax() {
    var xmlhttp = createXMLHTTPObject();
    var input = document.getElementById("sayhello");
    var sUrl = "SimpleAjax?name=guys";
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            alert(xmlhttp.responseText);
        }
    };
    xmlhttp.open('GET', sUrl);
    xmlhttp.send(null);
}

function getRecommend1() {
    var xmlhttp = createXMLHTTPObject();
    var sUrl = "MostRatings";
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            // alert(xmlhttp.responseText);
            var strs = new Array();
            strs = xmlhttp.responseText.split("&");
            // for (var i = 0; i < strs.length; i++) {
            //     document.getElementById("Recommend1").innerHTML = strs[i];
            // }
            // document.getElementById("Recommend1").innerHTML = strs[0];
            document.getElementById("Recommend1").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open('POST', sUrl);
    xmlhttp.send(null);

}9