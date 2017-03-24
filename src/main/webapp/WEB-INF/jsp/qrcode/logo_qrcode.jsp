<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建带logo的二维码</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/webuploader/webuploader.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/webuploader/webuploader.js"></script>
</head>
<body>
	<h3>请上传二维码的logo图片:</h3>
	<div id="uploader" class="wu-example">
		<!--用来存放文件信息-->
		<div id="info"></div>
		<div class="btns">
			<div id="picker">选择文件</div>
			扫描二维码跳转的链接:<input type="text" name="url" id="url"><br/>
			附加文字:<input type="text" name="description" id="description">
			<button id="submitBtn">点击提交</button>
		</div>
	</div>
	<!-- 生成的二维码图片 -->
	<img id="qrcode"/>
</body>
<script type="text/javascript">
	var uploader = WebUploader.create({
			// swf文件路径
			swf : "${pageContext.request.contextPath }/swf/webuploader/Uploader.swf",
			// 文件接收服务端。
			server : '${pageContext.request.contextPath }/QRCode/createLogoQRCode.do',
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : '#picker',
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			resize : false,
			// 自动上传
			auto : false,
			//是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开
			disableGlobalDnd : true,
			//截屏图片粘贴上传
			paste : "#uploader",
			accept: {// 只允许选择图片文件格式
				title: 'Images',
				extensions: 'gif,jpg,jpeg,bmp,png',
				mimeTypes: 'image/!*'
			}
		});
	
	uploader.on('uploadSuccess', function(file, response) {
		if (response.success == true) {
			$("#qrcode").attr("src","data:image/png;base64," + response.data);
		}
	});
	$('#submitBtn').on('click',function(){
		var description = $('#description').val();
		var url = $('#url').val();
		if(url == null || url == ''){
			alert("url不能为空");
			return;
		}
		if(description == null || description == ''){
			alert("description不能为空");
			return;
		}
		uploader.option('formData',{
			description:$('#description').val(),
			url:$('#url').val()
		});
		//添加完需要与图片一起上传的参数之后,就可以手动触发uploader的上传事件了.
		uploader.upload();
	})
</script>
</html>