<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/styles/login.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/RSA/Barrett.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/RSA/BigInt.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/RSA/RSA.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/login/login_util.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="global">
		<div class="log log_in" tabindex='-1' id='dl'>
			<dl>
				<dt>
					<div class='header'>
						<h3>登&nbsp;录</h3>
					</div>
				</dt>
				<dt></dt>
				<dt>
					<div class='letter'>
						用户名:&nbsp;
						<input type="text" name="" id="user_name" tabindex='1'
							style="ime-mode:disabled" />
						<span id="user_name_error_msg" style="font-size: 15px;color: red;"></span>
					</div>
				</dt>
				<dt>
					<div class='letter'>
						密&nbsp;&nbsp;&nbsp;码:&nbsp;
						<input type="password" name="" id="pwd" tabindex='2' />
						<span id="pwd_error_msg" style="font-size: 15px;color: red;"></span>
					</div>
				</dt>
				<dt>
					<div class='letter'>
						验证码:&nbsp;
						<input type="text" name="" id="yzm" tabindex='3'
							style="width:120px;ime-mode:disabled" />
						<img alt="点击刷新" title="点击刷新"
							style="margin-left:10px;margin-right:5px;margin-bottom:-8px;"
							src="${pageContext.request.contextPath}/verifyCode.do"
							onclick="var s=Math.random();this.src='${pageContext.request.contextPath}/verifyCode.do?1='+s;" />
						<span id="yzm_error_msg" style="font-size: 15px;color: red;"></span>
					</div>
				</dt>
				<dt>
					<div>
						<input type="button" name="" id="login_btn"
							value='&nbsp登&nbsp录&nbsp' tabindex='4' />
						<input type="button" name="" id="sig_in" value='&nbsp注&nbsp册&nbsp'
							tabindex='5' />
					</div>
				</dt>
			</dl>
		</div>
		<div class="sig sig_out" tabindex='-1' id='zc'
			style='visibility:hidden;'>
			<dl>
				<dt>
					<div class='header'>
						<h3>注&nbsp;册</h3>
					</div>
				</dt>
				<dt></dt>
				<dt>
					<div class='letter'>
						用户名:&nbsp;
						<input type="text" name="" id="regist_username" tabindex='5' />
						<div class='warning' id='warning_1'>
							<span>该用户名不可用</span>
						</div>
					</div>
				</dt>
				<dt>
					<div class='letter'>
						昵&nbsp;&nbsp;&nbsp;称:&nbsp;
						<input type="text" name="" id="nickname" tabindex='6' />
					</div>
				</dt>
				<dt>
					<div class='letter'>
						密&nbsp;&nbsp;&nbsp;码:&nbsp;
						<input type="password" name="" id="regist_password" tabindex='7' />
						<div class='warning' id='warning_2'>
							<span>密码长度过短</span>
						</div>
					</div>
				</dt>
				<dt>
					<div class='password'>
						&nbsp;&nbsp;&nbsp;确认密码:&nbsp;
						<input type="password" name="" id="final_password" tabindex='8' />
						<div class='warning' id='warning_3'>
							<span>密码输入不一致</span>
						</div>
					</div>
				</dt>
				<dt>
					<div>
						<input type="button" name="" id="regist_button"
							value='&nbsp注&nbsp册&nbsp' tabindex='9' />
						<input type="button" name="" id="back" value='&nbsp返&nbsp回&nbsp'
							tabindex='10' />
						<script type="text/javascript">
							function get(e) {
								return document.getElementById(e);
							}
							get('sig_in').onclick = function() {
								get('dl').className = 'log log_out';
								get('zc').className = 'sig sig_in';
							}
							get('back').onclick = function() {
								get('zc').className = 'sig sig_out';
								get('dl').className = 'log log_in';
							}
							window.onload = function() {
								var t = setTimeout("get('zc').style.visibility='visible'", 800);
								get('final_password').onblur = function() {
									var npassword = get('regist_password').value;
									var fpassword = get('final_password').value;
									if (npassword != fpassword) {
										get('warning_3').style.display = 'block';
									}
								}
								get('regist_password').onblur = function() {
									var npassword = get('regist_password').value.length;
									if (npassword < 6 && npassword > 0) {
										get('warning_2').style.display = 'block';
									}
								}
								get('regist_password').onfocus = function() {
									get('warning_2').style.display = 'none';
								}
								get('final_password').onfocus = function() {
									get('warning_3').style.display = 'none';
								}
							}
						</script>
					</div>
				</dt>
			</dl>
		</div>
	</div>
</body>
</html>
