//J2EE对Ajax的响应（一个测试按钮）
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

function getMostRatings() {
    var xmlhttp = createXMLHTTPObject();
    var sUrl = "MostRatings";
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            // alert(xmlhttp.responseText);
            document.getElementById("Recommend-result").innerHTML = xmlhttp.responseText;
        }
    };
    xmlhttp.open('POST', sUrl);
    xmlhttp.send(null);

}

function recommend_for_user() {
    var xmlhttp = createXMLHTTPObject();
    if(!isNaN(document.getElementById("userId").value)) {
        var radio = document.getElementsByName("model");
        var tableName = "";
        for (i=0; i<radio.length; i++) {
            if (radio[i].checked) {
                tableName = radio[i].value;
            }
        }
        var sUrl = "recommend_for_user" + "?userId=" + document.getElementById("userId").value + "&tableName=" + tableName;
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4) {
                var s = xmlhttp.responseText;
                if (s.trim() + "" ==  "未查找到相关用户信息")
                    alert(xmlhttp.responseText);
                else {
                    var radio = document.getElementsByName("model");
                    var tableName = "";
                    for (i=0; i<radio.length; i++) {
                        if (radio[i].checked) {
                            tableName = radio[i].value;
                        }
                    }
                    //对输出结果的json数组处理
                    var jsonArray = JSON.parse(xmlhttp.responseText);
                    document.getElementById("name").innerHTML = "用户 " + jsonArray[1].id + "的推荐内容"
                                    + "(" + tableName + ")";
                    var htmlContent =
                        "<table style='border:3px solid gray;width: 100%;'>" +
                        "<tr style='border:1px solid gray; text-align:center;'>" +
                            "<th>recommendation</th><th>score</th>" +
                        "</tr>";
                    for (var i = 0; i < jsonArray.length; i++) {
                        htmlContent += "<tr style='border:1px solid gray;'>" +
                            "<td>"+jsonArray[i].recommendation + "</td>" +
                            "<td>" + jsonArray[i].score + "</td>"
                            "</tr>"
                    }
                    htmlContent += "</table>";
                    document.getElementById("Recommend-result").innerHTML = htmlContent;
                }
            }
        };
        xmlhttp.open('GET', sUrl);
        xmlhttp.send(null);
    }
    else {
        alert("请输入合法用户id值");
    }
}

function searchMovie() {
    alert(str);
    var str = document.getElementById("SearchText").value;
    //输入了电影名模糊查询
    if (isNaN(str)) {
        var sUrl = "SearchName?movieName=" + str;
    }
    //输入了电影ID
    else {
        var sUrl = "SearchId?movieId=" + str;
    }
    var xmlhttp = createXMLHTTPObject();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            //为了防止电影名中出现的&被误认为是分隔符
            var textArray = xmlhttp.responseText.trim().replaceAll("]&[", "]&&&[").split("&&&");
            if (textArray[0].trim() == "[]") {
                alert("未查找到相关信息");
            }
            else if (textArray.length == 3) {
                var htmlContent = "<p>电影基本信息：</p>";
                var jsonArray = JSON.parse(textArray[0]);
                htmlContent += "<table style='border:3px solid gray;width: 100%;text-align:center;'>" +
                    "<tr style='border:1px solid gray; '>" +
                    "<th>movieId</th><th>title</th><th>genres</th></tr>" +
                    "<tr><td>" + jsonArray[0].movieId + "</td><td>" + jsonArray[0].title + " </td><td>" + jsonArray[0].genres + " </td></tr>"+
                    "</table>";
                htmlContent += "<p>观看过的用户评分情况如下：</p>";
                jsonArray = JSON.parse(textArray[1]);
                htmlContent += "<table style='border:3px solid gray;width: 100%;text-align:center;'>" +
                    "<tr style='border:1px solid gray; text-align:center;'>" +
                    "<th>userId</th><th>movieId</th><th>rating</th><th>timestamp</th></tr>";
                for (var i = 0; i < jsonArray.length; i++) {
                    htmlContent += "<tr>" +
                        "<td>" + jsonArray[i].userId + "</td><td>" + jsonArray[i].movieId + "</td><td>" + jsonArray[i].rating + " </td><td>" + jsonArray[i].timestamp + " </td></tr>"
                }
                htmlContent += "</table>"

                htmlContent += "<p>观看过的用户标识Tag情况如下：</p>";
                jsonArray = JSON.parse(textArray[2]);
                htmlContent += "<table style='border:3px solid gray;width: 100%;text-align:center;'>" +
                    "<tr style='border:1px solid gray; text-align:center;'>" +
                    "<th>userId</th><th>movieId</th><th>tag</th><th>timestamp</th></tr>";
                for (var i = 0; i < jsonArray.length; i++) {
                    htmlContent += "<tr>"+
                        "<td>" + jsonArray[i].userId + "</td><td>" + jsonArray[i].movieId + "</td><td>" + jsonArray[i].tag + " </td><td>" + jsonArray[i].timestamp + " </td></tr>"
                }
                htmlContent += "</table>"
                document.getElementById("recommend-left-img").innerHTML = htmlContent;
            }
            else if (textArray.length == 1) {
                var htmlContent = "<p>电影基本信息：</p>";
                var jsonArray = JSON.parse(textArray[0]);
                htmlContent += "<table style='border:3px solid gray;width: 100%;text-align:center;'>" +
                    "<tr style='border:1px solid gray; text-align:center;'>" +
                    "<th>movieId</th><th>title</th><th>genres</th></tr>";
                for (var i = 0; i < jsonArray.length; i++) {
                    htmlContent += "<tr><td>" + jsonArray[i].movieId + "</td><td>" + jsonArray[i].title + " </td><td>" + jsonArray[i].genres + " </td></tr>";
                }
                htmlContent += "</table>";
                document.getElementById("recommend-left-img").innerHTML = htmlContent;
            }
            else{
                //&做分隔符在电影名中也可以找到，所以有时候会长度为2
                alert("未查找到相关信息");
            }
        }
    }
    xmlhttp.open('GET', sUrl);
    xmlhttp.send(null);
}

function getAlgorithmName() {
    var radio = document.getElementsByName("model");
    var tableName = "";
    for (i=0; i<radio.length; i++) {
        if (radio[i].checked) {
            tableName = radio[i].value;
        }
    }
    document.getElementById("AlgorithmName").innerText = tableName;
    sUrl = "getRecResult" + "?algoName=" + tableName;
    var xmlhttp = createXMLHTTPObject();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            var resultSet = xmlhttp.responseText.split('&');
            document.getElementById("AlgorithmPrecision").innerText = resultSet[0];
            document.getElementById("AlgorithmRecall").innerText = resultSet[1];
            document.getElementById("AlgorithmCoverage").innerText = resultSet[2];
            document.getElementById("AlgorithmPopularity").innerText = resultSet[3];
        }
    }
    xmlhttp.open('GET', sUrl);
    xmlhttp.send(null);
}