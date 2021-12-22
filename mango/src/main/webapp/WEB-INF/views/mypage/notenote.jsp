<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 640px;
}

.mybox-mango {
	background-color: #ffebb5;
	border-radius: 20px;
	max-width: 400px;
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
</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<div class="row">
				<div class="col-auto me-auto">
					<h3><i class="bi bi-app"></i> ${youNick} 님과의 쪽지 </h3>
				</div>
				<div class="col-auto">
					<button type="button" class="btn btn-outline-primary"><i class="icofont-refresh"></i></button>
					&nbsp;
					<button type="button" class="btn btn-outline-danger"><i class="icofont-bin"></i></button>
				</div>
			</div>
		</div>
		
		<div class="body-main">
			<div class="row mb-5 px-5">
				<form name="sendForm" method="post" class="form-floating">
					<textarea class="form-control" name="content" style="height: 120px; resize: none;"></textarea>
					<br>
					<div class="row">
						<div class="col-md-6">
							<button class="btn btn-primary btnSend" type="button" id="sendBtn">
								전송
							</button>
						</div>
						<div class="col-md-6 text-right">
							<button class="btn btn-danger" type="button" id="gobackBtn" onclick="location.href='${pageContext.request.contextPath}/mypage/note'">
								뒤로가기
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
									<div class="col-auto p-3 mybox-mango">
										<p class="text-right">
											${dto.noteRegDate}&nbsp;(${dto.timeMsg})
										</p>
										<hr>
										${dto.noteContent}
										<p class="text-right mb-0">
											<i class="icofont-close"></i>
										</p>
									</div>
									<c:if test="${empty dto.noteReadDate}">
										<p class="text-right mb-0">읽지않음</p>
									</c:if>
								</c:if>
								<c:if test="${dto.receiveId == sessionScope.member.userId}">
									<div class="col-auto me-auto p-3 mybox-mango">
										<p class="text-right">
											${dto.noteRegDate}&nbsp;(${dto.timeMsg})
										</p>
										<hr>
										${dto.noteContent}
										<p class="text-right mb-0">
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