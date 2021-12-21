<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 쪽지 </h3>
		</div>
		
		<div class="body-main">
			<c:if test="${empty noteFriendList}">
				<div class="border bg-light mb-3 p-3 text-center">
					채팅이 없습니다.
				</div>
			</c:if>
			
			<c:if test="${not empty noteFriendList}">
				<h6>임시 목록 (테이블로 변경..)</h6>
				<hr>
				<c:forEach var="dto" items="${noteFriendList}">
					<div class="row mb-3">
						<div class="col-md-4">${dto.youNick}</div>
						<div class="col-md-5">${dto.noteContent}</div>
						<div class="col-md-3">${dto.timeMsg}</div>
					</div>
				</c:forEach>
			</c:if>
			
			<div class="row">
				<div class="col-md-12 text-right">
					<button class="btn btn-danger" type="button" id="addrBtn" onclick="location.href='${pageContext.request.contextPath}/mypage/main'">
						뒤로가기
					</button>
				</div>
			</div>
		</div>
	</div>
</div>