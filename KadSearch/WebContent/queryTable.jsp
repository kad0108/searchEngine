<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
	<div class="panel panel-default" style="width:720px; margin-left:10px;">
  		<div class="panel-heading res" id="result">欢迎使用Ksou</div>
  		
	</div>
	<ul class="list-group" style="width:720px; margin-left:10px;">
		<s:iterator value="qrList" var="i" status="index">
			<li class="list-group-item">
				<p style="font-size:15px;">
					<a href="http://<s:property value='#i.url' />" target="_blank">
						<s:property value="#i.title" />
					</a>
				</p>
				<p style="font-size:14px;"><s:property value="#i.content" escape="false" /></p>
				<p>
					<a class="mya" href="http://<s:property value='#i.url' />" target="_blank">
						<s:property value='#i.url.substring(0,20)' />&nbsp;
					</a>
					<a class="mya2">&nbsp;<s:property value="#i.score" /></span>
				</p>
			</li>
		</s:iterator>
  	</ul>
  	
  	