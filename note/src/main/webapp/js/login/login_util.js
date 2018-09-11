$(function() {
	$("#login_btn").click(login);
});
function login() {
	var userName = $("#user_name").val();
	var pwd = $("#pwd").val();
	if (userName == "") {
		$("#user_name_error_msg").html("请输入用户名");
		return;
	} else {
		$("#user_name_error_msg").html("");
	}
	if (pwd == "") {
		$("#pwd_error_msg").html("请输入密码");
		return;
	} else {
		$("#pwd_error_msg").html("");
	}
	var yzm = $("#yzm").val();
	if (yzm == "") {
		$("#yzm_error_msg").html("请输入验证码");
		return;
	} else {
		$("#yzm_error_msg").html("");
	}
	if (pwd.length < 6 || pwd.length > 18) {
		$("#pwd_error_msg").html("密码长度为6-18个字符");
		return;
	}
	setMaxDigits(130);
	key = new RSAKeyPair("10001", "", "aecf16369722e43f11324d704cf96437e27aff706456d17bf66f6f079403217b07e8817964e4342176974918d7941cf753cada1a5d1164f1c9856a21b1da8ab41957d0666ab6ca11f360a6d53ef41b880ee41be8736aa2a3c2aedc6713b5da3b0c349e3ee6e94e3fceaa84f2d921886aa6078fe258982bdc5da04f2a5b8e9321");
	alert(userName);
	return;
	$.ajax({
		url : "${pageContext.request.contextPath}/login.do",
		type : "POST",
		data : {
			"userName" : encryptedString(key, userName),
			"pwd" : encryptedString(key, pwd)
		},
		dataType : "JSON",
		success : function(data) {},
		error : function() {}
	});

}