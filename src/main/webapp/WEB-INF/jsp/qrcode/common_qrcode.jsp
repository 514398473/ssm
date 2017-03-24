<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery/jquery.js"></script>
<title>创建普通二维码</title>
<script type="text/javascript">
	$(function(){
		$("#stu").click(function(){
			var content = $("#content").val();
			if(null == content || '' == content){
				alert("地址不能为空!");
				return;
			}
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/QRCode/createCommonQRCode",
				data:{
					content:content
				},
				dataType:"json",
				success:function(response){
					if (response.success == true) {
						$("#qrcode").attr("src","data:image/png;base64," + response.data);
					}
				}
			});
		});
	});
</script>
</head>
<body>
	请填写生成的链接地址:<input type="text" id="content" />
	<input type="button" id="stu" value="提交"/>
	<!-- 生成的二维码图片 -->
	<img id="qrcode"/>
</body>
</html>