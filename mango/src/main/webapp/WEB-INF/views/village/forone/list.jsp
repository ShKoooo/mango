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

.page-link {
	color : #f69730;
}

a:hover {
	text-decoration : none;
}

.container {
	width: 1170px;
}

.img-viewer {
	width: 228px;
	height: 228px;
	padding: 0;
	background-image: url("${pageContext.request.contextPath}/resources/images/nothumb.png");
	position: relative;
	z-index: 9999;
	background-repeat : no-repeat;
	background-size : cover;
}


</style>

<script type="text/javascript">
function searchList() {
	var f = document.searchVillageForm;
	f.submit();
}

</script>

<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2>For ONE</h2>
				<span>혼자서도 잘 노는 방법!</span>
			</div>
		</div> <!-- row -->
	<div class="projects-holder">
		<div class="col-12">
			<div class="box-content">
			<div class="row">
			 	<c:forEach var="dto" items="${list}" varStatus="status">
			 		<div class="col-md-4 col-lg-3 p-1 mb-3 item">
			 			<div class="row mb-3">
				 			<a href="${articleUrl}&vNum=${dto.vNum}" title="${dto.subject}">
				 				<img class="img-viewer img-fluid img-thumbnail w-100" src="${dto.thumbnail}">
				 			</a>			 				
			 			</div>
			 			<div class="row mb-3">
			 				<a href="${articleUrl}&vNum=${dto.vNum}"  style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">${dto.subject}</a>
			 			</div>
					</div>
			 	</c:forEach>
			 </div>
				
				<div class="page-box">
					${dataCount == 0? "등록된 게시물이 없습니다." : paging}
				</div>
				
				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="window.location.reload()">새로고침</button>
					</div>
					
					<div class="col-6 text-center">
						<form class="row" name="searchVillageForm" action="${pageContext.request.contextPath}/village/forone/list" method="post"
							style="width: 600px;">
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
						<c:if test = "${sessionScope.member.membership > 50}">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/forone/write';">글쓰기</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>	
</div>