<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

table thead tr {
	text-align: center;
}

table tbody tr {
	text-align: center;
}

.twobtn {
	color: black;
	background: white;
	border-radius: 10px;
}
</style>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 공지사항 </h3>
		</div>
		
		<div class="body-main">
			<table class="table table-hover" style="width: 90%; margin: 0px auto;">
			  <thead>
			    <tr>
			      <th scope="col" colspan="3" style="text-align: left; padding-left: 20px;">제목</th>
			      <th scope="col" style="text-align: right; padding-right: 20px;">작성일</th>
			    </tr>
			  </thead>
			  
			  <tbody>
			  	<c:forEach var="dto" items="${list}">
				    <tr>
				      <td colspan="3" style="text-align: left; padding-left: 22px;">
				      	<a href="${articleUrl}&noticeNum=${dto.noticeNum}">${dto.nSubject }</a>
				      	<c:if test="${dto.gap < 1}">
							<span class="badge rounded-pill text-danger">New</span>
						</c:if>
				      </td>
				      <td style="text-align: right; padding-right: 22px;">${dto.nRegDate }</td>
				    </tr>
		    	</c:forEach>
			  </tbody>
			</table>
		</div>
		
		<div class="page-box" style="padding: 0px; margin: 0px auto;">
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
		</div>
		
		<!-- 부트 스트랩 페이징 css
		<div style="width: 29%; margin: 0 auto;">
			<nav aria-label="Page navigation example">
			  <ul class="pagination">
			    <li class="page-item">
			      <a class="page-link" href="#" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    <li class="page-item"><a class="page-link" href="#">1</a></li>
			    <li class="page-item"><a class="page-link" href="#">2</a></li>
			    <li class="page-item"><a class="page-link" href="#">3</a></li>
			    <li class="page-item">
			      <a class="page-link" href="#" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
		</div>
		 -->
		
		<div style="float: left;">
			<button class="twobtn" type="button" onclick="location.href='${pageContext.request.contextPath}/cscenter/csNotice'; ">새로고침</button>
		</div>
		
		<div style="float: right;">
			<c:if test="${sessionScope.member.membership > 50}">
				<button class="twobtn" type="button" onclick="location.href='${pageContext.request.contextPath}/cscenter/write'; ">글 올리기</button>
			</c:if>
		</div>

	</div>
</div>