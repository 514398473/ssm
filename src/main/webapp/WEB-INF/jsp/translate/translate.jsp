<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>百度在线翻译</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery/jquery.js"></script>
</head>
<body>
请输入需要翻译的文字:<textarea id="input" cols="50" rows="10"></textarea>
<input type="button" value="翻译" onclick="translateInfo()"/><br/>
<textarea id="result" cols="50" rows="10"></textarea>
<script type="text/javascript">
function translateInfo(){
	var input = $("#input").val();
	if (input !== null && input !== undefined && input !== '') {
		$.ajax({ 
		    url:'${pageContext.request.contextPath}/translate/translate', 
		    type:'post', 
		    data:{"info":input}, 
		    dataType:'text', 
		    success:function(data){ 
		    	$("#result").val(data); 
		    },
		    error:function(XMLHttpRequest, textStatus, errorThrown){
		    	console.log(XMLHttpRequest);
		    	console.log(textStatus);
		    	console.log(errorThrown);
		    }
		});
	}
}
</script>
</body>
</html>