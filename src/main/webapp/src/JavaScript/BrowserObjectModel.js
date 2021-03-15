/**
 * window对象：
 *          window: 客户端JavaScript中的顶层对象，每当<body>或者<frameset>标签出现时，window对象就会被自动创建
 *          navigator: 包含客户端有关的浏览器的信息
 *          screen: 包含客户端显示屏的消息
 *          history: 包含浏览器窗口访问过的URL信息
 *          location：包含当前网页文档的URL信息
 *          document：包含整个HTML文档，可被用来访问文档内容，及其所有页面信息
 * */

var a = "window.a";
function f() {
    alert(a);
}
// alert(window.a);
// window.f();


/**
 * window对象定义了三个人机交互的接口方法，方便测试。
 *      alert()：简单的提示对话框。
 *      confirm()：简单的提示对话框，但可以选择确认与取消
 *      prompt():弹出提示对话框，可以接受用户输入的信息
 * */
// var user = prompt("请输入你的用户名：");
// if (!!user) {
//     var ok = confirm("你输入的姓名为：\n" + user + "\n请确认。");
//     if (ok) {
//         alert("欢迎您\n" + user);
//     }
//     else {
//         user = prompt("请输入您的用户名：X2");
//         alert("欢迎您\n" + user);
//     }
// }
// else {
//     user = prompt("请输入您的用户名：X2");
// }


/**
 * 函数功能：使用location对象，获取URL查询字符串参数值的通用函数
 *          该函数能够抽取每个参数和参数值，并以名／值对的形式存储在对象中返回
 * */
function queryString() {
    var q = location.search.substring(1);       //删除问号
    var a = q.split("&");
    var o = {};
    for (var i = 0; i < a.length; i++) {
        var n = a[i].indexOf("=");
        if (n == -1) continue;
        var v1 = a[i].substring(0, n);
        var v2 = a[i].substring(n + 1);
        o[v1] = unescape(v2);
    }
    return o;
}