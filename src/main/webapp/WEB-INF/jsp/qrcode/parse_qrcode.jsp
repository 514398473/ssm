<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>解析二维码</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/webuploader/webuploader.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/webuploader/webuploader.js"></script>
</head>
<body>
	<h3>请上传二维码图片:</h3>
	<div id="uploader" class="wu-example">
		<!--用来存放文件信息-->
		<div id="info"></div>
		<div class="btns">
			<div id="picker">选择文件</div>
		</div>
	</div>
	<div id="append"></div>
</body>
<script type="text/javascript">
	var uploader = WebUploader.create({
			// swf文件路径
			swf : "${pageContext.request.contextPath }/swf/webuploader/Uploader.swf",
			// 文件接收服务端。
			server : '${pageContext.request.contextPath }/QRCode/parseQRCode.do',
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : {
				id : "#picker",
				multiple: false
			},
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			resize : false,
			// 自动上传
			auto : true,
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
			$("#append").append("<h3>"+ file.name + "的内容是:" +response.data+"</h3>");
		}
	});
</script>
</html>