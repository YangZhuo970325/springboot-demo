<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FreeMarker 实验</title>
</head>
<body>
<table border="1" cellspacing="0">
    <tr>
        <td>姓名</td>
        <td>年龄</td>
        <td>Email</td>
    </tr>
    <#list userList as user>
        <tr>
            <td>${user.name}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
        </tr>
    </#list>
</table>
</body>
</html>