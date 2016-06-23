<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript" src="plugs/jquery-2.1.4.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="plugs/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="plugs/search.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kad</title>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px;
	color: #000;
}
-->
</style>
</head>
<body>
	<div class="container" style="text-align:center">
		<div class="top"><img src="logo.png" width="300" height="150"></div>
		
		<form class="form-inline" id="form" action="searchShow">
			<input id="key" type="text" style="width:520px;height:35px">
			<div style="padding-top:18px">
				<button type="submit" id="search" class="btn btn-default">Ksou一下</button>
			</div>
			<div class="top">CopyRight© 2015 Kad.</div>
		</form>
	</div>
	
	
	
	
<!-- 	<script type='text/javascript' src="/js/admin/repertory.js"></script> -->
	<script type="text/javascript">
// 	var key;
// 	$(document).on("click", "#search", function() {
// 		key = $("#key").val();
// // 		alert(key);
// 		$.ajax({
// 			url : 'searchShow',
// 			type : 'post',
// 			dataType : 'json',
// 			data : {"key" : key,},
// 			success : searchCallback
// 		});
// 	})
// 	function searchCallback(data){
// 		alert(data.key);
// 	}
	</script>
</body>
</html>