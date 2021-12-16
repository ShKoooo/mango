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

<script type="text/javascript">
function searchList() {
	var f = document.searchForm;
	f.submit();
}
</script>

<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2>동네 질문</h2>
				<span>Village QNA</span>
			</div>
		</div> <!-- row -->
	<div class="projects-holder">
		<div class="col-12">
			<div class="box-content">
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
									<a href="${articleUrl}&num=${dto.vNum}" class="text-reset">${dto.subject}</a>
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
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/qna/list';">새로고침</button>
					</div>
					
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/village/qna/list" method="post">
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
							</div>
							<div class="col-auto p-1">
								<button type="button" class="btn btn-light" onclick="searchList()"><i class="bi bi-search"></i></button>
							</div>
						</form>
					</div>
					
					<div class="col text-end">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/qna/write';">글쓰기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>	
</div>