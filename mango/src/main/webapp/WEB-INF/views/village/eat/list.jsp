<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.section-header {
	margin-top : 60px;
	margin-bottom: 60px;
}

.page-item.active .page-link {
	background-color : #f69730;
	border-color : #f69730;
}

a:hover {
	text-decoration : none;
}
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">

<script type="text/javascript">
function searchList() {
	var f = document.searchForm;
	f.submit();
}

$(function() {
	
	$("#selectArea").change( function() {
		// 주소 바꿨을 때 좌표 영역에 값 세팅
		var maLat = $("#selectArea option:selected").attr("data-maLat");
		var maLon = $("#selectArea option:selected").attr("data-maLon");
		
		var f = document.searchForm;
		f.maLat.value = maLat;
		f.maLon.value = maLon;
		f.areaNum.value = $(this).val();
		searchList()
	});
	
});
</script>

<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2>동네 맛집</h2>
				<span>Village EAT</span>
			</div>
		</div> <!-- row -->
	<div class="projects-holder">
		<div class="col-12">
			<div class="box-content">
				<c:if test="${! empty sessionScope.member}">
					<div style="width: 120px; float: right;">
						<select name="areaNum" id="selectArea" class="form-select">
							<c:forEach var="vo" items="${listMemberAddr}">
								<option value="${vo.areaNum}" data-maLat='${vo.maLat}' data-maLon='${vo.maLon}' ${areaNum == vo.areaNum ? "selected='selected'" :"" }>${vo.area3}</option>
							</c:forEach>
							
							<c:if test="${empty listMemberAddr}">
								<option value="${pageContext.request.contextPath}/member/address">동네 설정</option>
							</c:if>
						</select>
					</div>
					<div>
					</div>
				</c:if>
				<table class="table table-hover board-list">
					<thead>
						<tr style="text-align: center;">
							<th class="bw-60">번호</th>
							<th class="bw-auto">제목</th>
							<th class="bw-100">작성자</th>
							<th class="bw-100">작성일</th>
							<th class="bw-70">조회수</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}">
							<tr style="text-align: center">
								<td>${dto.listNum}</td>
								<td style="text-align: left;">
									<a href="${articleUrl}&vNum=${dto.vNum}&areaNum=${areaNum}" class="text-reset">${dto.subject}</a>
									<c:if test="${dto.replyCount!=0}">(${dto.replyCount})</c:if>
								</td>
								<td> ${dto.userNickName} </td>
								<td> ${dto.reg_date} </td>
								<td> ${dto.hitCount} </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="page-box">
					${dataCount == 0? "등록된 게시물이 없습니다." : paging}
				</div>
				
				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="window.location.reload()">새로고침</button>
					</div>
					
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/village/eat/list" method="post">
							<div class="col-auto p-1">
								<select name="condition" class="form-select">
									<option value="all" ${condition=="all"?":selected='selected'":""}>제목+내용</option>
									<option value="userNickName" ${condition=="userNickName"?"selected='selected'":""}>작성자</option>
									<option value="subject" ${condition=="subject"?"selected='selected'":""}>제목</option>
									<option value="content" ${condition=="content"?"selected='selected'":""}>내용</option>
								</select>
							</div>
							<div class="col-8 p-1">
								<input type="text" name="keyword" value="${keyword}" class="form-control">
								<input type="hidden" name="maLat" value="0">
								<input type="hidden" name="maLon" value="0">
								<input type="hidden" name="areaNum" value="0">
							</div>
							<div class="col-auto p-1">
								<button type="button" class="btn btn-light" onclick="searchList()"><i class="bi bi-search"></i></button>
							</div>
						</form>
					</div>
					
					<div class="col text-end">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/eat/write';">글쓰기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>	
</div>