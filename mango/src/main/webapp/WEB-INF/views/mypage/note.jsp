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
				<div class="row mb-3 mx-3">
					<table class="table">
						<thead class="table-light">
							<tr>
								<th class="col-3">닉네임</th>
								<th class="col-5">내용</th>
								<th class="col-2">최근채팅시간</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="dto" items="${noteFriendList}">
								<tr>
									<td class="col-3">${dto.youNick}</td>
									<td class="col-5">
										<a href="${pageContext.request.contextPath}/mypage/notenote?youNick=${dto.youNick}">
											<c:if test="${dto.youId==dto.sendId}">
												<i class="icofont-inbox"></i>&nbsp;${dto.noteContent}
											</c:if>
											<c:if test="${dto.youId==dto.receiveId}">
												<i class="icofont-paper-plane"></i>&nbsp;${dto.noteContent}
											</c:if>
										</a>
									</td>
									<td class="col-2">${dto.timeMsg}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
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