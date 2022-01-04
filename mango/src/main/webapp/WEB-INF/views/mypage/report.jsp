<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.myRepBox {
	border-radius : 5px;
}

.content-wrapper {
	background-color: #FFFFFF;
}
</style>

<script type="text/javascript">
$(function() {
	$("body").on("click",".myBtnReset", function() {
		$("#repReason").val('');
		$("#repContent").val('');
		$("#title-span").text('신고를 오용 또는 남용할 경우 사이트 이용에 제한이 있을 수 있습니다.');
	});
});

$(function() {
	$("body").on("click",".myBtnGoback", function() {
		var userNick = "${userNick}";
		userNick = encodeURIComponent(userNick);
		var query = "userNickName="+userNick;
		
		var url = "${pageContext.request.contextPath}/mypage/yourpage?"+query;
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".myReportSubmit", function() {
		var userNick = "${userNick}";
		var content = $(this).closest("form").find("textarea").val();
		var reasonNum = $("#repReason option:selected").val();
		var reasonName = $("#repReason option:selected").attr("data-reason");
		
		if (!reasonNum) {
			$("#title-span").html("<span style='color:tomato;'>신고 사유를 선택해 주세요.</span>");
			$("#repReason").focus();
			return false;
		}
		if (!content) {
			$("#title-span").html("<span style='color:tomato;'>내용을 작성해 주세요.</span>");
			$("textarea").focus();
			return false;
		}
		var confirmStr = "한번 접수된 신고는 철회할 수 없습니다.\n"+userNick+" 님을 "+reasonName+" (으)로 신고하시겠습니까?";
		if (!confirm(confirmStr)) {
			return false;
		}
		
		userNick = encodeURIComponent(userNick);
		content = encodeURIComponent(content);
		
		query = "userNick="+userNick+"&content="+content+"&reasonNum="+reasonNum;
		var url = "${pageContext.request.contextPath}/mypage/reportSubmit?"+query;
		
		console.log(url);
		location.href = url;
	});
});

</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">	
			<div class="section-header">
				<div class="row">
					<div class="col-auto me-auto">
						<h2>회원 신고 </h2><span id="title-span">신고를 오용하거나 남용할 경우 사이트 이용에 제한이 있을 수 있습니다.</span>
					</div>
					<div class="col-auto ms-6">
						<button type="button" class="btn btn-outline-primary myBtnReset me-2" title="새로고침">
							<i class="icofont-eraser"></i>
						</button>
						<button type="button" class="btn btn-outline-danger myBtnGoback" title="뒤로가기">
							<i class="icofont-arrow-left"></i>
						</button>
					</div>
				</div>
			</div>
			
			<div class="container">
				<div class="row mb-5 px-5">
					<form name="sendForm" method="post" class="form-floating">
						<div class="row mb-3">
							<div class="col-md-2">신고 닉네임</div>
							<div class="col-md-10" style="color: tomato; font-weight: 700;">${userNick}</div>
						</div>
						<div class="row mb-4">
							<div class="col-md-2">
								신고사유
							</div>
							<div class="col-md-10">
								<select id="repReason" class="myRepBox" name="repReason">
									<option value='' selected>신고사유 선택</option>
									<c:forEach var="dto" items="${reasonList}">
										<option value='${dto.rmReasonNum}' data-reason='${dto.rmReasonName}'>${dto.rmReasonName}</option>
									</c:forEach>					
								</select>
							</div>
						</div>
						<div class="row mb-4">
							<textarea id="repContent" class="form-control myRepBox" name="content" style="height: 120px; resize: none;"></textarea>
						</div>
						<div class="row">
							<div class="col text-right">
								<button type="button" class="btn btn-primary myReportSubmit me-1">
									작성완료
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>