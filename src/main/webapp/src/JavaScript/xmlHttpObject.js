//文件名：xmlHttpObject.js
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
    var xmlHttp = false;
    for (var i = 0; i < XMLHttpFactories.length; i++) {
        //尝试调用匿名函数，成功则返回实例对象，否则进行下一个
        try {
            xmlHttp = XMLHttpFactories[i]();
        }
        catch(e) {
            continue;
        }
        break;
    }
    return xmlHttp;
}
