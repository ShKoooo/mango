<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.section-header {
	margin-top : 60px;
	margin-bottom: 60px;
}

.ck.ck-editor__main>.ck-editor__editable:not(.ck-focused) { 
	border: none;
}

textarea.form-control {
    width: 100%;
    height: 100px;
    resize: none;
}
  
.form-control {
	font-size: 12px;
}
.page-item.active .page-link {
	background-color : #f69730;
	border-color : #f69730;
}

.page-link {
	color : #f69730;
}
.btn-primary {
	background-color : #f69730;
	border-color : #f69730;
}
.container {
	width: 1170px;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/ckeditor5/ckeditor.js"></script>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId||sessionScope.member.membership>50}">
	function deleteBoard() {
		if(confirm("게시글을 삭제하시겠습니까?")) {
			var query = "vNum=${dto.vNum}&${query}";
			var url = "${pageContext.request.contextPath}/village/eat/delete?" + query;
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

$(function() {
	$(".btnSendBoardLike").click(function() {
		var $i = $(this).find("i");
		var userLiked = $i.hasClass("bi-hand-thumbs-up-fill");
		var msg = userLiked ? "게시글 공감을 취소하시겠습니까?" : "게시글에 공감하십니까?";
		
		if(! confirm(msg)) {
			return false;
		}
		
		var url="${pageContext.request.contextPath}/village/eat/insertBoardLike";
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
			} else if (state==="liked") {
				alert("게시글 공감은 한 번만 가능합니다.");
			} else if (state==="false") {
				alert("게시글 공감 여부처리를 실패했습니다.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

// 페이징 처리
$(function() {
	listPage(1);
});

function listPage(page) {
	var url = "${pageContext.request.contextPath}/village/eat/listReply";
	var query = "vNum=${dto.vNum}&pageNo="+page;
	var selector = "#listReply";
	
	var fn = function(data) {
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}

// 댓글 등록
$(function() {
	$(".btnSendReply").click(function() {
		var vNum = "${dto.vNum}";
		var $tb = $(this).closest("table");
		var vrContent = $tb.find("textarea").val().trim();
		if(! vrContent) {
			$tb.find("textarea").focus();
			return false;
		}
		vrContent = encodeURIComponent(vrContent);
		
		var url = "${pageContext.request.contextPath}/village/eat/insertReply";
		var query = "vNum=" + vNum + "&vrContent="+vrContent+"&vrAnswer=0";
		
		var fn = function(data) {
			$tb.find("textarea").val("");
			
			var state = data.state;
			if(state === "true") {
				listPage(1);
			} else if(state === "false") {
				alert("댓글을 추가하지 못했습니다.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

// 댓글 삭제
$(function() {
	$("body").on("click", ".deleteReply", function() {
		if(! confirm("게시물을 삭제하시겠습니까?")) {
			return false;
		}
		
		var vreplyNum = $(this).attr("data-vreplyNum");
		var page = $(this).attr("data-pageNo");
		
		var url = "${pageContext.request.contextPath}/village/eat/deleteReply";
		var query = "vreplyNum="+vreplyNum;
		
		var fn = function(data) {
			listPage(page);
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

// 댓글 좋아요 싫어요
$(function() {
	// 댓글 좋아요, 싫어요 등록하기
	$("body").on("click", ".btnSendReplyLike", function() {
		var vreplyNum = $(this).attr("data-vreplyNum");
		var replyLike = $(this).attr("data-replyLike");
		var $btn = $(this);
		
		var msg = "게시물이 마음에 들지 않으십니까?";
		if(replyLike === "1") {
			msg = "게시물에 공감하십니까?";
		}
		
		if(! confirm(msg)) {
			return false;
		}
		
		var url = "${pageContext.request.contextPath}/village/eat/insertReplyLike";
		var query = "vreplyNum="+vreplyNum+"&replyLike="+replyLike;
		
		var fn = function(data) {
			var state = data.state;
			if(state === "true") {
				var likeCount = data.likeCount;
				var disLikeCount = data.disLikeCount;
				
				$btn.parent("td").children().eq(0).find("span").html(likeCount);
				$btn.parent("td").children().eq(1).find("span").html(disLikeCount);
			} else if(state === "liked") {
				alert("게시물 공감은 한번만 가능합니다.");
			} else {
				alert("게시물 공감 여부 처리가 실패했습니다.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

// 댓글별 답글 리스트
function listReplyAnswer(vrAnswer) {
	var url ="${pageContext.request.contextPath}/village/eat/listReplyAnswer";
	var query = "vrAnswer="+vrAnswer;
	var selector="#listReplyAnswer" + vrAnswer;
	
	var fn = function(data) {
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}

// 댓글별 답글 개수
function countReplyAnswer(vrAnswer) {
	var url="${pageContext.request.contextPath}/village/eat/countReplyAnswer";
	var query = "vrAnswer="+vrAnswer;
	
	var fn = function(data) {
		var count = data.count;
		var selector = "#answerCount"+vrAnswer;
		$(selector).html(count);
	};
	
	ajaxFun(url, "post", query, "json", fn);
}

// 댓글의 답글 버튼
$(function() {
	$("body").on("click", ".btnReplyAnswerLayout", function() {
		var $trReplyAnswer = $(this).closest("tr").next();
		
		var isVisible = $trReplyAnswer.is(':visible');
		var vreplyNum = $(this).attr("data-vreplyNum");
		
		if(isVisible) {
			$trReplyAnswer.hide();
		} else {
			$trReplyAnswer.show();
			
			// 답글리스트
			listReplyAnswer(vreplyNum);
			
			// 답글 개수
			countReplyAnswer(vreplyNum);
		}
	});
});

// 댓글별 답글 등록
$(function() {
	$("body").on("click", ".btnSendReplyAnswer", function() {
		var vNum = "${dto.vNum}";
		var vreplyNum = $(this).attr("data-vreplyNum");
		var $td = $(this).closest("td");
		
		var vrContent = $td.find("textarea").val().trim();
		if(! vrContent) {
			$td.find("textarea").focus();
			return false;
		}
		vrContent = encodeURIComponent(vrContent);
		
		var url = "${pageContext.request.contextPath}/village/eat/insertReply";
		var query = "vNum="+vNum+"&vrContent="+vrContent+"&vrAnswer="+vreplyNum;
		
		var fn = function(data) {
			$td.find("textarea").val("");
			
			var state = data.state;
			if(state === "true") {
				listReplyAnswer(vreplyNum);
				countReplyAnswer(vreplyNum);
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

// 댓글별 답글 삭제
$(function() {
	$("body").on("click", ".deleteReplyAnswer", function() {
		if(! confirm("게시글을 삭제하시겠습니까?")) {
			return false;
		}
		
		var vreplyNum = $(this).attr("data-vreplyNum");
		var vrAnswer = $(this).attr("data-vrAnswer");
		
		var url = "${pageContext.request.contextPath}/village/eat/deleteReply";
		var query = "vreplyNum="+vreplyNum;
		
		var fn = function(data) {
			listReplyAnswer(vrAnswer);
			countReplyAnswer(vrAnswer);
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});


// 신고 게시글 신고
function sendReport() {
	if(confirm("한 번 신고한 상품/게시물은 신고를 철회할 수 없습니다.\n정말 신고하시겠습니까?")) {
		var f = document.vReportForm;
		var query = "page=${page}&vNum=${dto.vNum}"
		
		$('input:radio[name=vrReasonNum]').is(':checked');
		if(! $('input:radio[name=vrReasonNum]').is(':checked') ) {
			alert ("신고사유를 선택하세요.")
			return;
		}
		
		f.action = "${pageContext.request.contextPath}/village/eat/report?" + query;
		f.submit();
		
		alert("신고가 접수되었습니다.");
	}
}

$(document).ready(function(){
	// 신고 모달의 close를 눌렀을 시, 내용 초기화
	$("#closeBtn").click(function(){
		$("#vReportF").each(function(){
			this.reset();
		});
	});
});

</script>

<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2> 동네 맛집 </h2>
				<span>Village EAT</span>
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
								작성자 : <a href="${pageContext.request.contextPath}/mypage/yourpage?userNickName=${dto.userNickName}"> <b> ${dto.userNickName} </b> </a>
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
									<button type="button" class="btn btn-outline-secondary btnSendBoardLike" title="좋아요"><i class="bi ${userBoardLiked ? 'bi-hand-thumbs-up-fill':'bi-hand-thumbs-up' }"></i>&nbsp;&nbsp;<span id="boardLikeCount">${dto.boardLikeCount}</span></button>
								</td>
							</tr>
						
						</tbody>
					</table>
					
					<table class="table table-borderless mb-2">
						<tr>
							<td width="50%">
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId}">
										<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/eat/update?vNum=${dto.vNum}&page=${page}';">수정</button>
									</c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.membership>50}">
										<button type="button" class="btn btn-light" onclick="deleteBoard();"> 삭제 </button>
									</c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${sessionScope.member.userId!=dto.userId}">
										<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#reportbbs" data-bs-whatever="@mdo">신고</button>
									</c:when>
								</c:choose>
							</td>
							<td class="text-end">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/eat/list';">리스트</button>
							</td>
						</tr>
					</table>
					
					<!-- Modal -->
					<div class="modal fade" id="reportbbs" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLabel">신고하기</h5>
					        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					      </div>
					      <form name="vReportForm" id="vReportF" method="post">
					      <div class="modal-body">
							 	<div class="mb-3">
						      		<label for="recipient-name" class="col-form-label">신고 항목</label><br>
						        	
						        		<c:forEach var="vo" items="${listVreport}">
						        			<input type="radio" name="vrReasonNum" value="${vo.vrReasonNum}">${vo.vrReasonName}<br>
						        		</c:forEach>
						     	</div>
						      	<div class="mb-3">
									<label for="message-text" class="col-form-label">자세한 사유</label>
										<textarea class="form-control" id="message-text" name="vbbsRepContent"></textarea>
							    </div>
						  </div>
					      <div class="modal-footer">
					        <button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					        <button type="button" class="btn btn-primary" onclick="sendReport();">전송</button>
					      </div>
					      </form>
					  </div>
					</div>
					</div>
					
					
					<div class="reply">
						<form name="replyForm" method="post">
							<table class="table table-borderless reply-form">
								<tr>
									<td>
										<textarea class='form-control' name="content" placeholder="타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가주세요."></textarea>
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

<div class="modal fade" id="reportReply" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">신고하기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <form name="vrReportForm" id="vrReportF" method="post">
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
        <button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        <button type="button" class="btn btn-primary" onclick="sendReplyReport();">전송</button>
        <input type="hidden" name="vreplyNum" id = "myVReplyNum" value="0">
        <input type="hidden" name="vNum" value="${dto.vNum}">
        <input type="hidden" name="page" value="${page}">
      </div>
      </form>
    </div>
  </div>
</div>
					

<script type="text/javascript">
$(function(){
	$("body").on("click", ".notifyReply", function(){
		var vreplyNum = $(this).attr("data-vreplyNum");
		$("#myVReplyNum").val(vreplyNum);
		
		
		$("#reportReply").modal("show");
		
	});
});


function sendReplyReport() {
	if(confirm("한 번 신고한 댓글은 신고를 철회할 수 없습니다.\n정말 신고하시겠습니까?")) {
		var f = document.vrReportForm;
		
		$('input:radio[name=vrrReasonNum]').is(':checked');
		if(! $('input:radio[name=vrrReasonNum]').is(':checked') ) {
			alert ("신고사유를 선택하세요.")
			return;
		}
		
		f.action = "${pageContext.request.contextPath}/village/eat/reportReply";
		
		
		f.submit();
		
		alert("신고가 접수되었습니다.");
	}
}

$(document).ready(function(){
	// 신고 모달의 close를 눌렀을 시, 내용 초기화
	$("#closeBtn").click(function(){
		$("#vrReportF").each(function(){
			this.reset();
		});
	});
});
</script>

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