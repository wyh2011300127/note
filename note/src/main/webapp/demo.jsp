<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'hello.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/RSA/Barrett.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/RSA/BigInt.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/RSA/RSA.js"></script>

<script type="text/javascript">
	//测试AOP XML开发
	function aop_xml_btn() {
		setMaxDigits(130);
		var key = new RSAKeyPair("10001", "", "b080fdbdfb0e464a9ec5ec345d31f231a350dbaa9884bdd6b0f20069c21ccd21c110d582509904a8e6cbf68fbe94973f65d7eae19877d3915b5a55700579060be2c6f5fe5657f79c2240a4cc48581050e60925cb79060d4d36a3ea00b1379a7ead63eb9361031d685919fa6f34fcf5bbfd58b74bea83d835a997098e44e90e2f");
		var param = "ABCDETFY";
		param = encryptedString(key, param);
		window.location.href = "${pageContext.request.contextPath}/aopXml.do?param=" + param;
	}
</script>
</head>

<body>
	This is my JSP page.
	<br>
	<input type="button" onclick="aop_xml_btn();">
	测试AOP XML按钮
	</input>

	<br>
	<a href="${pageContext.request.contextPath}/aopAnno.do">测试AOP注解按钮</a>

</body>
</html>
