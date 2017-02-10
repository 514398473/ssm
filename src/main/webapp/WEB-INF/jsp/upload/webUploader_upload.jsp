<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>webUploader文件上传</title>
<style type="text/css">
	#area{
		width: 200px;
		height: 100px;
		border-color: red;
		border-style: dashed;
	}
</style>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/webuploader/webuploader.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/webuploader/webuploader.js"></script>
</head>
<body>
	<div id="uploader" class="wu-example">
		<div id="area">拖拽上传区域</div>
		<!--用来存放文件信息-->
		<div id="info"></div>
		<div class="btns">
			<div id="picker">选择文件</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//获取到了文件的标记
	var fileMd5;
	var ext;
	//监听分块上传过程中的三个时间点
	WebUploader.Uploader.register({
		"before-send-file":"beforeSendFile",
		"before-send":"beforeSend",
		"after-send-file":"afterSendFile"
	},{
		//时间点1：:所有分块进行上传之前调用此函数
		beforeSendFile:function(file){
			var deferred = WebUploader.Deferred();
		
			//1.计算文件的唯一标记，用于断点续传和秒传
			(new WebUploader.Uploader()).md5File(file,0,5*1024*1024)
					.progress(function(percentage){
						$("#"+file.id).find(".state").text("正在获取文件信息...");
					})
					.then(function(val){
						fileMd5 = val;
						ext = file.ext;
						$("#"+file.id).find(".state").text("成功获取文件信息");
						//只有文件信息获取成功，才进行下一步操作
						deferred.resolve();
					});
			
			//2.请求后台是否保存过该文件，如果存在，则跳过该文件，实现秒传功能
			return deferred.promise();
		},
		//时间点2：如果有分块上传，则 每个分块上传之前调用此函数
		beforeSend:function(block){
			var deferred = WebUploader.Deferred();
			//alert(fileMd5);
			$.ajax(
				{
				type:"POST",
				url:"${pageContext.request.contextPath}/upload/checkChunk?action=checkChunk",
				data:{
					//文件唯一标记
					fileMd5:fileMd5,
					//当前分块下标
					chunk:block.chunk,
					//当前分块大小
					chunkSize:block.end-block.start
				},
				dataType:"json",
				success:function(response){
					if(response.ifExist){
						//分块存在，跳过该分块
						deferred.reject();
					}else{
						//分块不存在或者不完整，重新发送该分块内容
						deferred.resolve();
					}
				}
				}
			);
			this.owner.options.formData.fileMd5 = fileMd5;
			
			return deferred.promise();
			//1.请求后台是否保存过当前分块，如果存在，则跳过该分块文件，实现断点续传功能
		},
		//时间点3：所有分块上传成功之后调用此函数
		afterSendFile:function(){
			//1.如果分块上传，则通过后台合并所有分块文件
			$.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/upload/checkChunk?action=mergeChunks",
				data:{
					fileMd5:fileMd5,
					ext:ext
				},
				success:function(response){
					
				}
			});
		}
	});

	var uploader = WebUploader.create({
		// swf文件路径
		swf : "${pageContext.request.contextPath }/swf/webuploader/Uploader.swf",
		// 文件接收服务端。
		server : '${pageContext.request.contextPath }/upload/chunkUpload.do',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : '#picker',
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		resize : false,
		// 自动上传
		auto:true,
		// 开启拖拽上传
		dnd : '#area',
		//是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开
		disableGlobalDnd :true,
		//截屏图片粘贴上传
		paste: "#uploader",
		//开启分片上传
		chunked: true,
		//块文件大小
		chunkSize:5*1024*1024,
		//开启几个并发线程（默认3个）
	//	threads:1000,
	});
	
	// 显示文件的信息
	uploader.on('fileQueued', function(file) {
		$("#info").append('<div id="' + file.id + '" class="item">'+ '<img/><span class="progress"></span>&emsp;<span class="info">'+ file.name + '</span>' + '<span class="state"></span><span class="ext"></span></div>');
			//制作缩略图
			uploader.makeThumb( file, function( error, src ) {
				if ( error ) {
					$("#"+file.id).find("img").replaceWith('预览错误');
				} else {
					$("#"+file.id).find("img").attr('src',src);
				}
			});
	});
	
	// 文件上传的进度
	uploader.on('uploadProgress', function(file,percentage ) {
		$("#"+file.id).find("span.progress").text(Math.floor(percentage * 100) +"%"); 
	});
</script>
</html>