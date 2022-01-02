<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.center {
	text-align: center;
}

.okBtn {
	width: 100%;
	height: 50px;
	
	border-radius: 10px;
	background: white;
	
	font-weight: bold;
	font-size: 20px;
}
</style>

<h3 style="font-size: 15px; padding-top: 10px;"><i class="icofont-double-right"></i> 문의 정보</h3>
<table class="table border mx-auto my-10">
	<tr>
		<td class="wp-15 text-right pe-7 bg center">문의 번호</td>
		<td class="wp-35 ps-5">${dto.inquiryNum}</td>
		<td class="wp-15 text-right pe-7 bg center">아이디</td>
		<td class="wp-35 ps-5">${dto.userId}</td>
	</tr>

	<tr>
		<td class="text-right pe-7 bg center">이메일</td>
		<td class="ps-5">${dto.inquiryEmail}</td>
		<td class="text-right pe-7 bg center">문의 날짜</td>
		<td class="ps-5">${dto.inquiryRegDate}</td>
	</tr>
	
	<tr>
		<td class="text-right pe-7 bg center">첨부파일</td>
		<td colspan="3" class="ps-5">
			<a href="${pageContext.request.contextPath}/admin/cscenter/download?inquiryNum=${dto.inquiryNum}" class="text-reset">${dto.saveFilename}</a>
		</td>
	</tr>
	
	<tr>
		<td class="text-right pe-7 bg center" style="height: 145px;">문의 내용</td>
		<td colspan="3" class="ps-5">
			${dto.inquiryContent}
		</td>
	</tr>
</table>

<form id="deteailedMemberForm" name="deteailedMemberForm" method="post" action="${pageContext.request.contextPath}/admin/cscenter/update">
	<div>
		<button type="submit" class="okBtn">${dto.state == '1' ? '미완료 처리' : '완료 처리'}</button>
		<input type="hidden" name="state" value="${dto.state=='0' ? '1' : '0' }">
		<input type="hidden" name="inquiryNum" value="${dto.inquiryNum}">
		<input type="hidden" name="userId" value="${dto.userId}">
	</div>
</form>
