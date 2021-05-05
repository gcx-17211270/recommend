//使用Ajax进行通信
function login() {
    var xmlHttp = createXMLHTTPObject();
    var url = "login";
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4) {
            //监听状态变化
            if (xmlHttp.responseText.trim() == null) {
                return;
            }
            else if("ok" == xmlHttp.responseText.trim()){
                window.location.href='static/html/index.html';
            }
            else if("false" == xmlHttp.responseText.trim()){
                alert("请重新输入正确的密码");
            }
        }
    };
    xmlHttp.open("POST", url);
    xmlHttp.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded ");
    var name = document.getElementById("name").value;
    var pass = document.getElementById("pass").value;
    xmlHttp.send("name=" + name + "&pass=" + pass);
}

