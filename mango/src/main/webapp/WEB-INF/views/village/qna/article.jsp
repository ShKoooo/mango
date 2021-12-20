<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">

.ck.ck-editor__main>.ck-editor__editable:not(.ck-focused) { 
	border: none;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/ckeditor5/ckeditor.js"></script>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId||sessionScope.member.membership>50}">
	function deleteBoard() {
		if(confirm("게시글을 삭제하시겠습니까?")) {
			var query = "vNum=${dto.vNum}&${query}";
			var url = "${pageContext.request.contextPath}/village/qna/delete?" + query;
			location.href = url;
		}
	}
</c:if>
</script>

<script type="text/javascript">
function login() {
	location.href="${pageContext.request.contextPath}/member/login";
}

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}


// 게시글 공감
$(function() {
	$(".btnSendBoardLike").click(function() {
		var $i = $(this).find("i");
		var userLiked = $i.hasClass("bi-hand-thumbs-up-fill");
		var msg = userLiked ? "게시글 공감을 취소하시겠습니까 ? " : "게시글에 공감하십니까 ? ";
		
		if(! confirm(msg)) {
			return false;
		}
		
		var url="${pageContext.request.contextPath}/village/qna/insertBoardLike";
		var vNum="${dto.vNum}";
		var query = "vNum="+vNum+"&userLiked="+userLiked;
		
		var fn = function(data) {
			var state = data.state;
			if(state === "true") {
				if( userLiked ) {
					$i.removeClass("bi-hand-thumbs-up-fill").addClass("bi-hand-thumbs-up");
				} else {
					$i.removeClass("bi-hand-thumbs-up").addClass("bi-hand-thumbs-up-fill");
				}
				
				var count = data.boardLikeCount;
				$("#boardLikeCount").text(count);
			} else if(state==="liked") {
				alert("게시글 공감은 한 번만 가능합니다.");
			} else if(state==="false") {
				alert("게시글 공감 여부 처리에 실패했습니다.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});
</script>

<script type="text/javascript">
function login () {
	location.href="${pageContext.request.contextPath}/member/login";
}
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패했습니다.");
				return false;
			}
			
			console.log(jqXHR.responseText);
		}
	});
}

</script>

<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2> 동네 질문 </h2>
				<span>Village QNA</span>
			</div>
		</div> <!-- row -->
	<div class="projects-holder">
		<div class="col-12">
			<div class="box-content">
			
				<div class="body-main">
				
					<table class="table mb-0">
						<thead>
							<tr>
								<td colspan="2" align="center">
									${dto.subject}
								</td>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td width="50%">
									이름 : ${dto.userNickName}
								</td>
								<td align="right">
									${dto.reg_date} | 조회 ${dto.hitCount}
								</td>							
							</tr>
							
							<tr>
								<td colspan="2" valign="top" height="200" style="border-bottom: none; padding-top: 5px;">
									<div class="editor">${dto.content}</div>
								</td>
							</tr>
							
							<tr>
								<td colspan="2" class="text-center p-3">
									<button type="button" class="btn btn-outline-secondary btnSendBoardLike" title="좋아요"><i class="bi ${userBoardLiked ? 'bi-hand-thumbs-up-fill':'bi-hand-thumbs-up'}"></i>&nbsp;&nbsp;<span id="boardLikeCount">${dto.boardLikeCount}</span></button>
								</td>
							</tr>
						
							<tr>
								<td colspan="2">
									이전글 : 
									<c:if test="${not empty preReadDto}">
										<a href="${pageContext.request.contextPath}/village/qna/article?${query}&vNum=${preReadDto.vNum}">${preReadDto.subject}</a>
									</c:if>
								</td>
							</tr>
							
							<tr>
								<td colspan="2">
									다음글 : 
									<c:if test="${not empty nextReadDto}">
										<a href="${pageContext.request.contextPath}/village/qna/article?${query}&vNum=${nextReadDto.vNum}">${nextReadDto.subject}</a>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
					
					<table class="table table-borderless mb-2">
						<tr>
							<td width="50%">
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId}">
										<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/qna/update?vNum=${dto.vNum}&page=${page}';">수정</button>
									</c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.membership>50}">
										<button type="button" class="btn btn-light" onclick="deleteBoard();"> 삭제 </button>
									</c:when>
								</c:choose>
							</td>
							<td class="text-end">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/qna/list?${query}';">리스트</button>
							</td>
						</tr>
					</table>
				
					<div class="reply">
						<form name="replyForm" method="post">
							<div class='form-header'>
								<span class="bold">댓글</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가주세요. </span>
							</div>
							
							<table class="table table-borderless reply-form">
								<tr>
									<td>
										<textarea class='form-control' name="content"></textarea>
									</td>
								</tr>
								<tr>
									<td align='right'>
										<button type='button' class='btn btn-light btnSendReply'>댓글 등록</button>
									</td>
								</tr>
							</table>
						</form>
						
						<div id="listReply"></div>
					</div>
				</div>
			
			</div>
		</div>
	</div>
	</div>



</div>

<script type="text/javascript">
ClassicEditor
	.create( document.querySelector( '.editor'), {
	})
	.then( editor => {
		window.editor = editor;
		editor.isReadOnly = true;
		editor.ui.view.top.remove( editor.ui.view.stickyPanel );
	});

</script>