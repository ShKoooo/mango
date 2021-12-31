<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach var="vo" items="${listReplyAnswer}">
	<div class='border-bottom mb-2'>
		<div class='row py-1'>
			<div class='col-6'><span class="bold">
			<a href="${pageContext.request.contextPath}/mypage/yourpage?userNickName=${vo.userNickName}">&nbsp;<b>${vo.userNickName}</b></a>
			</span></div>
			<div class='col text-end'>
				<span class="text-muted">${vo.vrRegDate}</span> |
				<c:choose>
					<c:when test="${sessionScope.member.userId==vo.userId || sessionScope.member.membership>50}">
						<span class='deleteReplyAnswer' data-vreplyNum='${vo.vreplyNum}' data-vrAnswer='${vo.vrAnswer}'>삭제</span>
					</c:when>
					<c:otherwise>
						<span class='notifyReply' data-vreplyNum='${vo.vreplyNum}'>신고</span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class='row px-2 pb-2'>
			<div class='col'>${vo.vrContent}</div>
		</div>
	</div>
</c:forEach>