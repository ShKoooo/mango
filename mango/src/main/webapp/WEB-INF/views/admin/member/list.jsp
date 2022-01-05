<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/icon/bootstrap-icons.css" type="text/css">

<style type="text/css">
.body-container {
	margin-left : 20px;
	max-width: 1200px;
}
</style>

<script type="text/javascript">
$(function() {
	$("body").on("click",".myBtnReset", function() {
		$("#chkBlk").prop("checked",false);
		$("#chkBus").prop("checked",false);
		$("#chkRep").prop("checked",false);
		$("#searchMemberId").val('');
		$("#searchMemberNickName").val('');
	});
});

$(function() {
	$("body").on("click",".myBtnRefresh", function() {
		var page = "${page}";
		if (page < 1) page = 1;
		var chkBlk = $("#chkBlk").is(":checked").toString();
		var chkBus = $("#chkBus").is(":checked").toString();
		var chkRep = $("#chkRep").is(":checked").toString();
		var userId = $("#searchMemberId").val().toString();
		var userNickName = $("#searchMemberNickName").val().toString();
		userNickName = encodeURIComponent(userNickName);
		
		var query = "page="+page+"&chkBlk="+chkBlk+"&chkBus="+chkBus+"&chkRep="+chkRep;
		if (userId) {
			query += "&userId="+userId;
		}
		if (userNickName) {
			query += "&userNickName="+userNickName;
		}
		
		var url = "${pageContext.request.contextPath}/admin/member/list";
		if (query) {
			url += "?"+query;		
		}
		
		console.log(url);
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".goMember", function() {
		var page = "${page}";
		if (page < 1) page = 1;
		var chkBlk = $("#chkBlk").is(":checked").toString();
		var chkBus = $("#chkBus").is(":checked").toString();
		var chkRep = $("#chkRep").is(":checked").toString();
		var userId = $("#searchMemberId").val().toString();
		var userNickName = $("#searchMemberNickName").val().toString();
		userNickName = encodeURIComponent(userNickName);
		
		var memberId = $(this).attr("data-userId");
		
		var query = "memberId="+memberId+"&page="+page+"&chkBlk="+chkBlk+"&chkBus="+chkBus+"&chkRep="+chkRep;
		if (userId) {
			query += "&userId="+userId;
		}
		if (userNickName) {
			query += "&userNickName="+userNickName;
		}
		
		if (!memberId) {
			return false;
		}
		
		var url = "${pageContext.request.contextPath}/admin/member/id";
		if (query) {
			url += "?"+query;
		}
		
		console.log(url);
		location.href = url;
	});
});
</script>


<main>
	<h1>Admin Page</h1>
	
	<div class="body-container">
	    <div class="body-title">
			<h2><i class="icofont-interface"></i> 회원관리 </h2>
	    </div>
	    
	    <div class="body-main">
			<div class="row">
				<div class="form-check">
					<div class="row md-5">
						<div class="col-auto me-auto">
							<div class="row mb-3 pb-1">
								<div class="col-auto mx-3">
									<input class="form-check-input align-middle" type="checkbox" value="chkRep" id="chkRep"
										${chkRep=="true"?"checked='checked'":""}>
									<label class="form-check-label align-middle" for="chkRep">&nbsp;신고계정</label>
								</div>
								<div class="col-auto mx-3">
									<input class="form-check-input align-middle" type="checkbox" value="chkBlk" id="chkBlk"
										${chkBlk=="true"?"checked='checked'":""}>
									<label class="form-check-label align-middle" for="chkBlk">&nbsp;잠금계정</label>
								</div>
								<div class="col-auto mx-3">
									<input class="form-check-input align-middle" type="checkbox" value="chkBus" id="chkBus"
										${chkBus=="true"?"checked='checked'":""}>
									<label class="form-check-label align-middle" for="chkBus">&nbsp;업체계정 보유</label>
								</div>
							</div>
							<div class="row mb-3 pb-1">
								<div class="col-auto mx-3">
									<input type="text" class="form-control" id="searchMemberId" placeholder="아이디" title="검색 아이디 입력" value="${userId}">
								</div>
								<div class="col-auto">
									<input type="text" class="form-control" id="searchMemberNickName" placeholder="닉네임 or 업체" title="검색 닉네임 입력" value="${userNickName}">
								</div>
							</div>
						</div>
						<div class="col-auto ms-6">
							<button class="btn btn-outline-primary myBtnReset" title="검색값 초기화">
								<i class="icofont-eraser"></i>
							</button>&nbsp;&nbsp;
							<button class="btn btn-outline-primary myBtnRefresh" title="검색조건 변경">
								<i class="icofont-refresh"></i>
							</button>
						</div>
					</div>	
				</div>
				
				검색결과: ${dataCount} 명
				<table class="table table-hover">
					<thead class="table-light">
						<tr>
							<th class="col-1">아이디</th>
							<th class="col-2">닉네임</th>
							<th class="col-1">이름</th>
							<th class="col-2">가입일자</th>
							<th class="col-1">신고여부</th>
							<th class="col-1">상태</th>
							<th class="col-1">멤버십</th>
							<th class="col-1">업체 계정</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="dto" items="${list}">
							<tr class="goMember" data-userId="${dto.userId}">
								<td class="col-1">${dto.userId}</td>
								<td class="col-2">${dto.userNickName}</td>
								<td class="col-1">${dto.userName}</td>
								<td class="col-2">${dto.regDate}</td>
								<td class="col-1">
									<c:if test="${dto.cntRep <= 0}">
										<span style="color: blue;">정상</span>
									</c:if>
									<c:if test="${dto.cntRep > 0}">
										<span style="color: tomato; font-weight: 700;">신고됨</span>
									</c:if>
								</td>
								<td class="col-1">
									<c:if test="${dto.memberEnable==1}">
										<span style="color: blue;">정상</span>
									</c:if>
									<c:if test="${dto.memberEnable==0}">
										<span style="color: tomato; font-weight: 700;">잠금</span>
									</c:if>
								</td>
								<td class="col-1">${dto.membershipName}</td>
								<td class="col-1">
									<c:if test="${dto.business=='true'}">
										<span style="font-weight: 700;">${dto.busNickName}</span>
									</c:if>
									<c:if test="${dto.business=='false'}">
										<span style="color: tomato;">없음</span>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${not empty list}">
					${paging}
				</c:if>
				<c:if test="${empty list}">
					회원이 없습니다.
				</c:if>
			</div>
	    </div>
	</div>
	
</main>