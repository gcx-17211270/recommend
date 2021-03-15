/**
 * 函数功能：安全检测JavaScript的基本数据类型和内置对象
 * 参数：o表示检测的值
 * 返回值：返回字符串"undefined", "number", "boolean". "string", "function", "regexp", "array"
 *          "date", "error", "object"或"null"
 * */

function typeOf(o) {
    var _toString = Object.prototype.toString;
    //获取对象的toString()方法引用
    //列举基本数据类型和内置对象类型，可能进一步补充该数组的检测数据类型范围
    var _type = {
        "undefined":"undefined",
        "number":"number",
        "boolean":"boolean",
        "string":"string",
        "[object Function]":"function",
        "[object RegExp]":"regexp",
        "[object Array]":"array",
        "[object Date]":"date",
        "[object Error]":"error"
    }

    //如果o==null，typeof o会返回object
    return _type[typeof o] || _type[_toString.call(o)] || (o ? "object" : "null");
}