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

.adminStateBox {
	border-radius: 5px;
}

.myBtnClear:hover, .myBtnArticle:hover{
	color: tomato;
	font-weight: 700;
}
</style>

<script type="text/javascript">
$(window).on("load",function() {
	$("#brCode option[value=${brType}]").prop('selected', true);
});

$(function() {
	$("body").on("click",".myBtnArticle", function() {
		var vNum = $(this).closest("tr").attr("data-bbsNum");
		var catNum = $(this).closest("tr").attr("data-catNum");
		
		query = "page=1&vNum="+vNum;
		
		var url = "${pageContext.request.contextPath}";
		
		if (catNum === 1 || catNum === '1') {
			url += "/village/qna/article";
		} else if (catNum === 2 || catNum === '2') {
			url += "/village/eat/article";
		} else if (catNum === 3 || catNum === '3') {
			url += "/village/help/article";
		} else if (catNum === 4 || catNum === '4') {
			url += "/village/with/article";
		} else if (catNum === 5 || catNum === '5') {
			url += "/village/news/article";
		} else if (catNum === 6 || catNum === '6') {
			url += "/village/lost/article";
		} else if (catNum === 7 || catNum === '7') {
			url += "/village/ad/article";
		} else {
			url += "/village/forone/article";
		}
		
		url += "?"+query;
		
		location.href = url;
	});
});

$(function() {
	$("body").on("change","#brCode", function() {
		var brType = $("#brCode option:selected").val();
		var page = "${page}";
		if (page<1) page = 1;
		var query = "page="+page+"&brType="+brType;
		
		var url = "${pageContext.request.contextPath}/admin/village/list";
		url += "?"+query;
		
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".myBtnRefresh", function() {
		var query = "page=1&brType=all";
		
		var url = "${pageContext.request.contextPath}/admin/village/list";
		url += "?"+query;
		
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".myBtnClear", function() {
		var brType = $(this).closest("tr").attr("data-brType");
		var reportNum = $(this).closest("tr").attr("data-reportNum");
		var selBrType = $("#brCode option:selected").val();
		
		var query = "brType="+brType+"&reportNum="+reportNum
			+"&page=${page}&selBrType="+selBrType;

		var url = "${pageContext.request.contextPath}/admin/village/clear";
		url += "?"+query;
		
		location.href = url;
	});
});
</script>

<main>
	<h1>Admin Page</h1>
	
	<div class="body-container">
	    <div class="body-title">
			<h2><i class="icofont-interface"></i> 동네커뮤니티 신고관리 </h2>
	    </div>
	    
	    <div class="body-main">
	    	<div class="row md-5">
	    		<div class="form-check">
	    			<div class="row">
			    		<div class="col-auto me-auto">
			    			<select title="게시글 댓글 여부" id="brCode" name="brCode" class="adminStateBox">
			    				<option value="all">전체</option>
			    				<option value="bbs">게시글</option>
			    				<option value="reply">댓글</option>
			    			</select>
			    		</div>	
			    		<div class="col-auto text-right">
			    			<button type="button" class="btn btn-outline-primary myBtnRefresh">
			    				<i class="icofont-ui-rotation"></i>
			    			</button>
			    		</div>
	    			</div>
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
								data-bbsNum="${dto.vnum}"
								data-catNum="${dto.catNum}"
								data-brType="${dto.brType}"
								data-reportNum="${dto.reportNum}">
								<td>${dto.reasonName}</td>
								<td class="myBtnArticle" title="${dto.fullSubject}">${dto.subject}</td>
								<td>${dto.rcontent}
									<c:if test="${dto.rcontent == null or dto.rcontent == ''}">
										(상세내역 미작성)
									</c:if>
								</td>
								<td>${dto.regDate}</td>
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