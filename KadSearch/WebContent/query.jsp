<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %><%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript" src="plugs/jquery-2.1.4.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="plugs/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="plugs/search.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>kad</title>
</head>
<body>
	<div class="top" id="main-content">
		<div>
			<form class="form-inline kform col-md-12" id="form">
				<img src="logo.png" width="100" height="50">
				<input id="key" type="text" style="width:520px;height:35px">
				<button type="button" id="search" class="btn btn-default">Ksou一下</button>
			
			</form>
		</div>
		
	</div>
	
	<div id="TableDiv">
		<%@ include file="queryTable.jsp" %>
	</div>
	

	


	<%@ include file="/pagination.jsp" %>

	

	<script type="text/javascript">
	var key;
	$(document).on("click", "#search", function() {
		key = $("#key").val();
// 		alert(key);
		$.ajax({
			url : 'search',
			type : 'post',
			dataType : 'json',
			data : {"key" : key,},
			success : searchCallback
		});
	})
	function searchCallback(data){
// 		alert(data.qrList);
		$(document).find("#TableDiv").html(data.search_table);
		$(document).find("#paginationTableDiv").html(data.paginationHtml);
		$("#result").html("Ksou为您找到约 " + data.hits + " 条搜索结果 ，  用时约 " + data.time + " s");
// 		alert(data.paginationHtml);
	}
	</script>
</body>
</html>