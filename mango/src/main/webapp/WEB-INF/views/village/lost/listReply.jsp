<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class='reply-info'>
	<span class='reply-count'>댓글 ${replyCount}개</span>
	<span>[목록, ${pageNo}/${total_page} 페이지]</span>
</div>

<table class='table table-borderless'>
	<c:forEach var="vo" items="${listReply}">
		<tr class='border bg-light'>
			<td width='50%'>
				<span class="bold">
				<a href="${pageContext.request.contextPath}/mypage/yourpage?userNickName=${vo.userNickName}">${vo.userNickName}</a>
				</span>
			</td>
			<td width='50%' align='right'>
				<span class="text-muted">${vo.vrRegDate}</span> |
				<c:choose>
					<c:when test="${sessionScope.member.userId==vo.userId || sessionScope.member.membership > 50 }">
						<span class='deleteReply' data-vreplyNum='${vo.vreplyNum}' data-pageNo='${pageNo}'>삭제</span>
					</c:when>
					<c:otherwise>
						<span class='notifyReply' data-vreplyNum='${vo.vreplyNum}'>신고</span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan='2' valign='top'>${vo.vrContent}</td>
		</tr>

		<tr>
			<td>
				<button type='button' class='btn btn-light btnReplyAnswerLayout' data-vreplyNum='${vo.vreplyNum}'>답글 <span id="answerCount${vo.vreplyNum}">${vo.answerCount}</span></button>
			</td>
			<td align='right'>
				<button type='button' class='btn btn-light btnSendReplyLike' data-vreplyNum='${vo.vreplyNum}' data-replyLike='1' title="좋아요"><i class="bi bi-hand-thumbs-up"></i> <span>${vo.likeCount}</span></button>
				<button type='button' class='btn btn-light btnSendReplyLike' data-vreplyNum='${vo.vreplyNum}' data-replyLike='0' title="싫어요"><i class="bi bi-hand-thumbs-down"></i> <span>${vo.disLikeCount}</span></button>	        
			</td>
		</tr>
	
	    <tr class='reply-answer' style="display:none;">
	        <td colspan='2' class="px-3">
	        	<div class="p-2 border">
		            <div id='listReplyAnswer${vo.vreplyNum}' class='p-2'></div>
		            <div class="row px-2">
		                <div class='col'><textarea class='form-control'></textarea></div>
		            </div>
		             <div class='row p-2'>
		             	<div class="col text-end">
		                	<button type='button' class='btn btn-light btnSendReplyAnswer' data-vreplyNum='${vo.vreplyNum}'>답글 등록</button>
		                </div>
		            </div>
				</div>
			</td>
	    </tr>
	</c:forEach>
</table>

				
<div class="page-box">
	${paging}
</div>							