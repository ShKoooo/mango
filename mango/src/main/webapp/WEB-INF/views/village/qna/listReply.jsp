<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
function sendReplyReport() {
	if(confirm("한 번 신고한 상품/게시물은 신고를 철회할 수 없습니다.\n정말 신고하시겠습니까?")) {
		var f = document.vrReportForm;
		
		$('input:radio[name=vrrReasonNum]').is(':checked');
		if(! $('input:radio[name=vrrReasonNum]').is(':checked') ) {
			alert ("신고사유를 선택하세요.")
			return;
		}
		
		f.action = "${pageContext.request.contextPath}/village/qna/reportReply"+query;
		f.submit();
		
		alert("신고가 접수되었습니다.");
	}
}
</script>

<div class='reply-info'>
	<span class='reply-count'>댓글 ${replyCount}개</span>
	<span>[목록, ${pageNo}/${total_page} 페이지]</span>
</div>

<table class='table table-borderless'>
	<c:forEach var="vo" items="${listReply}">
		<tr class='border bg-light'>
			<td width='50%'>
				<i class="bi bi-person-circle text-muted"></i> <span class="bold">${vo.userNickName}</span>
			</td>
			<td width='50%' align='right'>
				<span class="text-muted">${vo.vrRegDate}</span> |
				<c:choose>
					<c:when test="${sessionScope.member.userId==vo.userId || sessionScope.member.membership > 50 }">
						<span class='deleteReply' data-vreplyNum='${vo.vreplyNum}' data-pageNo='${pageNo}'>삭제</span>
					</c:when>
					<c:otherwise>
						<span class='notifyReply' data-bs-toggle="modal" data-bs-target="#reportReply" data-bs-whatever="@mdo">신고</span>
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

				<!-- Modal -->
					<div class="modal fade" id="reportReply" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLabel">신고하기</h5>
					        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					      </div>
					      <form name="vrReportForm" method="post">
					      <div class="modal-body">
							 	<div class="mb-3">
						      		<label for="recipient-name" class="col-form-label">신고 항목</label><br>
						        	
						        		<c:forEach var="rep" items="${listVRreport}">
						        			<input type="radio" name="vrrReasonNum" value="${rep.vrrReasonNum}">${rep.vrrReasonName}<br>
						        		</c:forEach>
						     	</div>
						      	<div class="mb-3">
									<label for="message-text" class="col-form-label">자세한 사유</label>
										<textarea class="form-control" id="message-text" name="vrReportContent"></textarea>
							    </div>
						  </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					        <button type="button" class="btn btn-primary" onclick="sendReplyReport();">전송</button>
					      </div>
					      </form>
					  </div>
					</div>
					</div>
					
<div class="page-box">
	${paging}
</div>							