/**
 * 定义XMLHttpRequest对象
 * 采用一种高效的工厂模式把定义XMLHttpRequest对象功能进行封装，这样只要调用createXMLHttpObject()方法
 *      就可以返回一个XMLHttpRequest对象
 * */

//定义XMLHttpRequest对象
//参数：无
//返回值：XMLHttpRequest对象实例
function createXMLHTTPObject() {
    //兼容不同浏览器和版本的创造函数数组
    var XMLHttpFactories = [
        function() {return new XMLHttpRequest()},
        function() {return new ActiveXObject("Msxml2.XMLHTTP")},
        function() {return new ActiveXObject("Msxml3.XMLHTTP")},
        function() {return new ActiveXObject("MicroSoft.XMLHTTP")}
    ];
    var xmlhttp = false;
    for (var i = 0; i < XMLHttpFactories.length; i++) {
        //尝试调用匿名函数，成功则返回实例对象，否则进行下一个
        try {
            xmlhttp = XMLHttpFactories[i]();
        }
        catch(e) {
            continue;
        }
        break;
    }
    return xmlhttp;
}


//asp对Ajax的响应
function request(url) {
    var xmlhttp = createXMLHTTPObject();
    xmlhttp.open("GET", url, false);
    xmlhttp.send(null);
    document.writeln(xmlhttp.responseText);
}

// window.onload = function() {
//     var b = document.getElementById("1");
//     b.onclick = function () {
//         var url="res/server.asp?callback=functionName";
//         // alert("server.asp?callback=functionName");
//         //设置向服务器端发送请求的文件，以及传递的参数消息
//         request(url);
//     };
// }


//J2EE对Ajax的响应
function simple_ajax() {
    var xmlhttp = createXMLHTTPObject();
    var input = document.getElementById("1");
    var sUrl = "SimpleAjax?uname=London";
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            alert(xmlhttp.responseText);
        }
    };
    xmlhttp.open('POST', sUrl);
    xmlhttp.send(null);
}