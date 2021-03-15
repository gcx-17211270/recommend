//使用Ajax进行通信
function login() {
    var xmlHttp = createXMLHTTPObject();
    var url = "login";
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4) {
            //监听状态变化
            alert(document.getElementById("name").value);
            alert(xmlHttp.responseText);
            loginSucceed();
        }
    };
    xmlHttp.open("POST", url);
    xmlHttp.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded ");
    var name = document.getElementById("name").value;
    var pass = document.getElementById("pass").value;
    xmlHttp.send("name=" + name + "&pass=" + pass);
}

function loginSucceed() {

}

