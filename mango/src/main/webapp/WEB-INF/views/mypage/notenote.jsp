<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 640px;
}

.mybox-sender-color {
	background-color: #FFE08C;
}

.mybox-receiver-color {
	background-color: #CEF279;
}

.mybox-mango {
	border-radius: 20px;
	max-width: 400px;
}

.delete-one-msg:hover {
	color: red;
}

.content-wrapper {
	background-color: #FFFFFF;
}
</style>

<script type="text/javascript">
// background-color: #ffebb5;
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
	$("body").on("click",".btnSend", function() {
		var content = $(this).closest("form").find("textarea").val().trim();
		var sendId = '${sessionScope.member.userId}';
		var receiveId = '${youId}';
		
		if (!content) {
			$(this).closest("form").find("textarea").focus();
			return false;
		}
		content = encodeURIComponent(content);
		
		var url = "${pageContext.request.contextPath}/mypage/sendNote";
		var query = "sendId="+sendId+"&receiveId="+receiveId+"&content="+content;
		
		var fn = function(data) {
			$(this).closest("form").find("textarea").val("");
			
			var state = data.state;
			if (state==="true") {
				location.href = "${pageContext.request.contextPath}/mypage/notenote?youNick=${youNick}";
				location.reload();
			} else {
				alert(data.message);
			}
		}
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

$(function() {
	$("body").on("click",".delete-one-msg", function() {
		if (!confirm("메시지를 삭제하겠습니까?")) {
			return false;
		}
		
		var noteNum = $(this).attr('data-noteNum');
		var sendId = $(this).attr('data-sender');
		var receiveId = $(this).attr('data-receiver');
		var meId = '${sessionScope.member.userId}';
		
		var url = "${pageContext.request.contextPath}/mypage/deleteNoteMsg";
		var query = "noteNumStr="+noteNum+"&meId="+meId+"&sendId="+sendId+"&receiveId="+receiveId;
		
		var fn = function(data) {
			var state = data.state;
			if (state==="true") {
				location.href = "${pageContext.request.contextPath}/mypage/notenote?youNick=${youNick}";
				location.reload();
			} else if (state === "unAuthorizedAccess") {
				alert("채팅 삭제가 정상적으로 이루어지지 않았습니다. [오류코드 01]");
			} else if (state === "numberFormatException") {
				alert("채팅 삭제가 정상적으로 이루어지지 않았습니다. [오류코드 02]");
			} else {
				alert("채팅 삭제가 정상적으로 이루어지지 않았습니다. [오류코드 99]");
			}
		}
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

$(function() {
	$("body").on("click",".delete-user-msg", function() {
		var confirmMsg = "${youNick} 님과의 모든 메시지를 삭제하시겠습니까?";
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
</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">	
			<div class="section-header">
				<div class="row">
					<div class="col-auto me-auto">
						<h3> ${youNick} 님과의 쪽지 </h3>
					</div>
					<div class="col-auto">
						<button type="button" title="새로고침" class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/notenote?youNick=${youNick}'"><i class="icofont-refresh"></i></button>
						&nbsp;
						<c:if test="${gomain=='true'}">
							<button type="button" title="뒤로가기" class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/note'"><i class="icofont-arrow-left"></i></button>
							&nbsp;
						</c:if>
						<button type="button" title="전체삭제" class="btn btn-outline-danger delete-user-msg" data-youId="${youId}"><i class="icofont-bin"></i></button>
					</div>
				</div>
			</div>
			
			<div class="container">
				<div class="row mb-5 px-5">
					<form name="sendForm" method="post" class="form-floating">
						<textarea class="form-control" name="content" style="height: 120px; resize: none;"></textarea>
						<br>
						<div class="row">
							<div class="col text-right">
								<button title="전송" class="btn btn-primary btnSend text-center" type="button" id="sendBtn">
									<i class="icofont-paper-plane"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
			
				<div class="row ms-3">
					<c:if test="${empty list}">
						<div class="border bg-light mb-3 p-3 text-center">
							쪽지가 없습니다. ${youNick} 님께 쪽지를 보내보세요!
						</div>
					</c:if>
					
					<c:if test="${not empty list}">
						<c:forEach var="dto" items="${list}">
							<div class="row mb-2">
								<div class="row mb-3">
									<c:if test="${dto.sendId == sessionScope.member.userId}">
										<div class="col-auto me-auto">&nbsp;</div>
										<div class="col-auto p-3 mybox-mango mybox-sender-color">
											<p class="text-right">
												${dto.noteRegDate}&nbsp;(${dto.timeMsg})
											</p>
											<hr>
											${dto.noteContent}
											<p class="text-right mb-0 delete-one-msg"
												data-notenum="${dto.noteNum}"
												data-sender="${dto.sendId}"
												data-receiver="${dto.receiveId}">
												<i class="icofont-close"></i>
											</p>
										</div>
										<c:if test="${empty dto.noteReadDate}">
											<p class="text-right mb-0">읽지않음</p>
										</c:if>
									</c:if>
									<c:if test="${dto.receiveId == sessionScope.member.userId}">
										<div class="col-auto me-auto p-3 mybox-mango mybox-receiver-color">
											<p class="text-right">
												${dto.noteRegDate}&nbsp;(${dto.timeMsg})
											</p>
											<hr>
											${dto.noteContent}
											<p class="text-right mb-0 delete-one-msg"
												data-notenum="${dto.noteNum}"
												data-sender="${dto.sendId}"
												data-receiver="${dto.receiveId}">
												<i class="icofont-close"></i>
											</p>
										</div>
										<div class="col-auto">&nbsp;</div>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
			
		
		
	</div>
</div>