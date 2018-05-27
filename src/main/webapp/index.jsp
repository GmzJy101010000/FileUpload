<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>文件上传</title>
<style type="text/css">
#Bar{width:400px;height:19px;background:#FFFFFF;border:1px solid #000000;padding:1px}
#item{width:50%;height:100%;background:#FF0000;}
</style>
 <script type="text/javascript" src="http://libs.baidu.com/jquery/1.9.1/jquery.js"></script>
</head>
<body>
<iframe name=upload_iframe width=0 height=0></iframe>
<div align="center">
<form action="UploadServlet" method=post enctype="multipart/form-data"  target="upload_iframe" onsubmit="showStatus();">

<div>
<div >请选择文件:</div>
<input type="file" name="file1"></input>
</div>
<input id="btnSubmit" type="submit" value="上传"></input>
</form>
</div>

<div id="status"  style="display:none">
上传进度条：
<div id="Bar"><div id="item"></div></div>
<div id="info"></div>
</div>
<script type="text/javascript">
var $j = jQuery.noConflict();

var finished=true;
 function $(obj){
	return document.getElementById(obj);
} 
function showStatus(){
	finished=false;
	$("status").style.display="block";
	$("item").style.width='1%';
	$("btnSubmit").disabled=true;
	setTimeout("requestStatusd()",300);
}

function requestStatusd(){
	if(finished) {
		$("btnSubmit").disabled=false;
		//$("status").style.display="none";
		return;
		}
	
	var xhr=createRequest();
	xhr.open("GET","/fileupload/UploadServlet",false);
	xhr.onreadystatechange=function(){callback(xhr);}
	xhr.send(null);
	setTimeout("requestStatusd()",300);
	
}
function createRequest(){
	
	if(window.XMLHttpRequest){
		return new XMLHttpRequest();
	}
}
function callback(xhr){
	
	if(xhr.readyState==4){
		if(xhr.status!=200){
			alert("error");
			return;
		}
		var value=xhr.responseText.split("||");
		$("item").style.width=''+value[0]+'%';
		$j("#info").html('<div><ul><li>上传速度：'+value[3]+'</li><li>已用时间：'+value[4]+'</li><li>剩余时间：'+value[6]+'</li><ul></div>');
		
		if(value[1]==value[2]){
			finished =true;
		}
	}
}

</script>
</body>
</html>
