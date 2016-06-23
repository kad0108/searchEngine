<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ include file="/jsp/base/taglib.jsp" %> --%>



<div id="paginationTableDiv">

<%@ include file="/paginationTable.jsp" %>

</div>
<script> 


// var pageAddtionalData = {}
$(document).on("click","#firstPage",  function(){
    sendRequestPage(1);
})
$(document).on("click","#lastPage",  function(){

    sendRequestPage($(this).attr("totalPageNum"));
})

$(document).on("click","[requestPageNum]",  function(){

    sendRequestPage($(this).attr("requestPageNum"));
})

var paginationURL='page';
function sendRequestPage(currentPageNum) {
	var data = {"currentPageNum": currentPageNum , "isAjaxTransmission":true, "key" : key };
	
	if(typeof(pageAddtionalData)!="undefined")
		data = $.extend({}, data, pageAddtionalData);
    $.ajax({
        url: paginationURL,
        type: 'post',
        dataType: 'json',
        data: data,
        success: _requestPageCallback,
        error: requesterror
      });	
}

function _requestPageCallback(data) {
// 	alert("CAO::::");
	$("#paginationTableDiv").html(data.paginationHtml);
	
	
	$(document).find("#TableDiv").html(data.search_table);
// 	$(document).find("#paginationTableDiv").html(data.paginationHtml);
// 	alert(data.paginationHtml);

// 	requestPageCallback(data);
}

function requesterror(data){
	alert("error");
}
</script>





