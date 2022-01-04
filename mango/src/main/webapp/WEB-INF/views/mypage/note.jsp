<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.delete-one-msg:hover {
	color: red;
}

.content-wrapper {
	background-color: #FFFFFF;
}

.myCount {
    font-size: .8em;
    width: 1.5em;
    border-radius: 3em;
    padding: .1em  .2em;
    line-height: 1.25em;
    border: none;
    display: inline-block;
    text-align: center;
    color:white;
    background-color:red;
    font-weight: 600;
}
</style>

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
				alert("요청 처리가 실패했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

$(function() {
	$("body").on("click",".delete-all-msg", function() {
		if (!confirm("모든 쪽지를 삭제하시겠습니까?")) {
			return false;
		}
		
		var meId = '${sessionScope.member.userId}';
		var url = "${pageContext.request.contextPath}/mypage/deleteEntireMyNote";
		var query = "meId="+meId;
		
		console.log(url+"?"+query);
		
		var fn = function(data) {
			var state = data.state;
			if (state==="true") {
				location.href = "${pageContext.request.contextPath}/mypage/notenote?youNick=${youNick}";
				location.reload();
			} else if (state === "unAuthorizedAccess") {
				alert("채팅 삭제가 정상적으로 이루어지지 않았습니다. [오류코드 01]");
			} else {
				alert("채팅 삭제가 정상적으로 이루어지지 않았습니다. [오류코드 99]");
			}
		}
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

$(function() {
	$("body").on("click",".delete-one-msg", function() {
		var youNick = $(this).attr("data-youNick");
		var confirmMsg = youNick+' 님과의 쪽지를 삭제하시겠습니까?';
		
		if (!confirm(confirmMsg)) {
			return false;
		}
		
		var meId = '${sessionScope.member.userId}';
		var youId = $(this).attr("data-youId");
		
		var url = "${pageContext.request.contextPath}/mypage/deleteNoteUser";
		var query = "youId="+youId+"&meId="+meId;
		
		console.log(url+"?"+query);
		
		var fn = function(data) {
			var state = data.state;
			if (state==="true") {
				location.href = "${pageContext.request.contextPath}/mypage/notenote?youNick=${youNick}";
				location.reload();
			} else if (state === "unAuthorizedAccess") {
				alert("채팅 삭제가 정상적으로 이루어지지 않았습니다. [오류코드 01]");
			} else {
				alert("채팅 삭제가 정상적으로 이루어지지 않았습니다. [오류코드 99]");
			}
		}
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

$(function() {
	$("body").on("click",".btnSend", function() {
		var content = $(this).closest("form").find("textarea").val().trim();
		var sendId = '${sessionScope.member.userId}';
		var targetNickName = $(this).closest(".row").find("input").val().trim();
		
		if (!content) {
			$(this).closest("form").find("textarea").focus();
			return false;
		}
		if (!targetNickName) {
			$(this).closest("form").find("input").focus();
			return false;
		}
		
		var origNick = targetNickName;
		targetNickName = encodeURIComponent(targetNickName);
		content = encodeURIComponent(content);
		
		var url = "${pageContext.request.contextPath}/mypage/sendNoteAtMain";
		var query = "sendId="+sendId+"&targetNickName="+targetNickName+"&content="+content;
		
		console.log(url+"?"+query);
		
		var fn = function(data) {
			$(this).closest("form").find("textarea").val("");
			
			var state = data.state;
			if (state==="true") {
				location.href = "${pageContext.request.contextPath}/mypage/note";
				location.reload();
			} else if (state==="unAuthorizedAccess") {
				alert("비정상적인 접근입니다.");
			} else if (state==="nickNameNotFound") {
				alert(origNick+" 님을 찾을 수 없습니다.");
			} else if (state==="selfSendErr") {
				alert("자기 자신에게는 보낼 수 없습니다.");
			} else if (state==="blockReceiverException") {
				alert("차단한 유저에게는 보낼 수 없습니다.");
			} else {
				alert("전송이 정상적으로 이루어지지 않았습니다.");
			}
		}
		
		ajaxFun(url, "post", query, "json", fn);
	});
});
</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">
			<div class="section-header">
				<div class="row">
					<div class="col-auto me-auto">
						<h3> 쪽지 </h3>
					</div>
					<div class="col-auto">
						<button type="button" title="새로고침" class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/note'"><i class="icofont-refresh"></i></button>
						&nbsp;
						<button type="button" title="뒤로가기" class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/main'"><i class="icofont-arrow-left"></i></button>
						&nbsp;
						<button type="button" title="전체삭제" class="btn btn-outline-danger delete-all-msg"><i class="icofont-bin"></i></button>
					</div>
				</div>
			</div>
			
			<div class="container">
				<div class="row mb-5 px-5">
					<form name="sendForm" method="post" class="form-floating">
						<textarea class="form-control" name="content" style="height: 120px; resize: none;"></textarea>
						<br>
						<div class="row">
							<div class="col-md-6">
								<input type="text" name="targetNickName" class="bosTF" placeholder="닉네임">
							</div>
							<div class="col-md-6 text-right">
								<button title="전송" class="btn btn-primary btnSend" type="button" id="sendBtn">
									<i class="icofont-paper-plane"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				
				<c:if test="${empty noteFriendList}">
					<div class="border bg-light mb-3 p-3 text-center">
						쪽지가 없습니다.
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
									<th class="col-1">&nbsp;</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dto" items="${noteFriendList}">
									<tr>
										<td class="col-3">${dto.youNick}</td>
										<td class="col-5">
											<a href="${pageContext.request.contextPath}/mypage/notenote?youNick=${dto.youNick}&gomain=true">
												<c:if test="${dto.youId==dto.sendId}">
													<i class="icofont-inbox"></i>&nbsp;${dto.noteContent}
												</c:if>
												<c:if test="${dto.youId==dto.receiveId}">
													<i class="icofont-paper-plane"></i>&nbsp;${dto.noteContent}
												</c:if>
												<c:if test="${dto.notReadCount>0 and dto.notReadCount<100}">
													<span class="ms-3 myCount">${dto.notReadCount}</span>
												</c:if>
												<c:if test="${dto.notReadCount>100}">
													<span class="ms-3 myCount">99</span>
												</c:if>
											</a>
										</td>
										<td class="col-2">${dto.timeMsg}</td>
										<td class="col-1 delete-one-msg text-center" 
											data-youNick='${dto.youNick}'
											data-youId='${dto.youId}'>
											<i class="icofont-close"></i>
										</td>
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
</div>