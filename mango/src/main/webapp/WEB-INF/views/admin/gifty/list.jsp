<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/icon/bootstrap-icons.css" type="text/css">

<style type="text/css">
.body-container {
	margin-left : 20px;
	max-width: 1000px;
}

.myBtnClear:hover, .myBtnArticle:hover{
	color: tomato;
	font-weight: 700;
}
</style>

<script type="text/javascript">

$(function() {
	$("body").on("click",".myBtnArticle", function() {
		var gNum = $(this).closest("tr").attr("data-bbsNum");
		var query = "gNum="+gNum+"&page=1";
		
		var url = "${pageContext.request.contextPath}/gifty/article";
		url += "?" + query;
		
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".myBtnRefresh", function() {
		var url = "${pageContext.request.contextPath}/admin/gifty/list";
		
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".myBtnClear", function() {
		var reportNum = $(this).closest("tr").attr("data-reportNum");
		var query = "reportNum="+reportNum+"&page=${page}";
		
		var url = "${pageContext.request.contextPath}/admin/gifty/clear";
		
		url += "?"+query;
		
		location.href = url;
	});
});

</script>

<main>
	<h1>Admin Page</h1>
	
	<div class="body-container">
	    <div class="body-title">
			<h2><i class="icofont-interface"></i> 기프티콘 신고관리 </h2>
	    </div>
	    
	    <div class="body-main">
	    	<div class="row md-5">
	    		<div class="col text-right">
	    			<button type="button" class="btn btn-outline-primary myBtnRefresh">
			    		<i class="icofont-ui-rotation"></i>
			    	</button>
	    		</div>
	    	</div>
	    	
	    	<div class="row mb-3">
	    		미처리 신고건수: 총 ${dataCount} 건
	    		
	    		<table class="table">
	    			<thead class="table-light">
						<tr>
							<th class="col-2">신고사유</th>							
							<th class="col-3">제목</th>
							<th class="col-4">상세</th>
							<th class="col-2">신고일자</th>
							<th class="col-1">&nbsp;</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach varStatus="status" var="dto" items="${list}">
							<tr
								data-bbsNum="${dto.num}"
								data-catNum="${dto.catNum}"
								data-reportNum="${dto.reportNum}">
								<td>${dto.reasonName}</td>
								<td class="myBtnArticle" title="${dto.fullSubject}">${dto.subject}</td>
								<td>${dto.reportContent}
									<c:if test="${dto.reportContent == null or dto.reportContent == ''}">
										(상세내역 미작성)
									</c:if>
								</td>
								<td>${dto.reportRegDate}</td>
								<td class="myBtnClear">처리&nbsp;<i class="icofont-ui-check"></i></td>
							</tr>
						</c:forEach>
					</tbody>
	    		</table>
	    		
	    		<c:if test="${empty list}">
					접수된 신고내역이 없습니다.
				</c:if>
				<c:if test="${not empty list}">
					${paging}
				</c:if>
	    	</div>
	    </div>
	</div>
	
</main>