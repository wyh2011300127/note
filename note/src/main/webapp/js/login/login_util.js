$(function() {
	$("#login_btn").click(login);
});
function login() {
	//验证码
	var yzm = $("#yzm").val();
	if (yzm == "") {
		$("#yzm_error_msg").html("请输入验证码");
		return;
	}
	//用户名
	var name = $("#user_name").val();
	//密码
	var pwd = $("#pwd").val();
	if (name == "") {
		$("#user_name_error_msg").html("请输入用户名");
		return;
	}
	if (pwd == "") {
		$("#pwd_error_msg").html("请输入密码");
		return;
	}
	key = new RSAKeyPair("10001","","aecf16369722e43f11324d704cf96437e27aff706456d17bf66f6f079403217b07e8817964e4342176974918d7941cf753cada1a5d1164f1c9856a21b1da8ab41957d0666ab6ca11f360a6d53ef41b880ee41be8736aa2a3c2aedc6713b5da3b0c349e3ee6e94e3fceaa84f2d921886aa6078fe258982bdc5da04f2a5b8e9321");
	name=encryptedString();


}