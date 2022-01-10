<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/icon/bootstrap-icons.css" type="text/css">

<style type="text/css">
.body-container {
	margin-left : 20px;
	max-width: 1000px;
}

.btn-CheckReport:hover {
	font-weight: 600;
	color: tomato;
}
</style>

<script type="text/javascript">

$(function() {
	$("body").on("click",".myBtnTolist", function() {
		var query = "page=${page}"
			+"&chkBlk=${chkBlk}"
			+"&chkBus=${chkBus}"
			+"&chkRep=${chkRep}"
			+"&userId=${userId}"
			+"&userNickName=${userNickName}"
			
		var url = "${pageContext.request.contextPath}/admin/member/list";
		url += "?"+query;
		
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".btn-CheckReport", function() {
		if (!confirm("해당 신고를 처리하셨습니까?\n신고 내역이 목록에서 사라집니다.")) {
			return false;
		}
		
		var repMemNum = $(this).attr("data-repMemNum");
		
		var query = "page=${page}"
			+"&chkBlk=${chkBlk}"
			+"&chkBus=${chkBus}"
			+"&chkRep=${chkRep}"
			+"&userId=${userId}"
			+"&memberId=${memberId}"
			+"&userNickName=${userNickName}";
			
		query += "&repMemNum="+repMemNum;
			
		var url = "${pageContext.request.contextPath}/admin/member/clearReport";
		url += "?"+query;
		
		console.log(url);
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".btn-changeState", function() {
		var page = "${page}";
		if (page<1) page = 1;
		
		var query = "page="+page
			+"&chkBlk=${chkBlk}"
			+"&chkBus=${chkBus}"
			+"&chkRep=${chkRep}"
			+"&userId=${userId}"
			+"&memberId=${memberId}"
			+"&userNickName=${userNickName}";
			
		var url = "${pageContext.request.contextPath}/admin/member/updateState";
		url += "?"+query;
		
		console.log(url);
		location.href = url;
	});
});

</script>

<main>
	<h1>Admin Page</h1>
	
	<div class="body-container">
	    <div class="body-title">
	    	<div class="row ms-2">
	    		<div class="col">
					<h2><i class="icofont-interface"></i> ${memberId} </h2>
	    		</div>
	    		<div class="col text-right me-3">
	    			<button class="btn btn-outline-primary btn-changeState" title="계정관리">
						<i class="icofont-edit"></i>
					</button>&nbsp;&nbsp;
	    			<button class="btn btn-outline-primary myBtnTolist" title="목록으로">
						<i class="icofont-arrow-left"></i>
					</button>
	    		</div>
	    	</div>
	    </div>
	    
	    <div class="body-main">
			<table class="table">
				<thead class="table-light">
					<tr>
						<th colspan="1">필드</th>
						<th colspan="6">값</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="1" class="table-light">아이디</td>
						<td colspan="6">${memberDto.userId}</td>
					</tr>
					<tr>
						<td colspan="1" class="table-light">성명</td>
						<td colspan="6">${memberDto.userName}</td>
					</tr>
					<tr>
						<td class="table-light">닉네임</td>
						<td>${memberDto.userNickName}</td>
					</tr>
					<tr>
						<td colspan="1" class="table-light">계정상태</td>
						<td colspan="6">
							<c:if test="${memberDto.memberEnable<1}">
								<span style="color:tomato; font-weight: 700;">
									잠금 (비밀번호 오류 횟수: ${memberDto.loginFail}&nbsp;회)
								</span>
							</c:if>
							<c:if test="${memberDto.memberEnable>=1}">
								<span style="color:blue; font-weight: 700;">
									정상
								</span>
							</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="1" class="table-light">전화번호</td>
						<td colspan="6">${memberDto.userTel}</td>
					</tr>
					<tr>
						<td colspan="1" class="table-light">이메일</td>
						<td colspan="6">${memberDto.userEmail}</td>
					</tr>
					<tr>
						<td colspan="1" class="table-light">생년월일</td>
						<td colspan="6">${memberDto.birth}</td>
					</tr>
					<tr>
						<td colspan="1" class="table-light">가입일자</td>
						<td colspan="6">${memberDto.regDate}</td>
					</tr>
					<tr>
						<td colspan="1" class="table-light">닉네임 변경일자</td>
						<td colspan="6">${memberDto.nickUpdate_date}</td>
					</tr>
					
					<c:if test="${not empty addrList}">
						<c:forEach var="dto" items="${addrList}" varStatus="status">
							<tr>
								<td colspan="1" class="table-light">주소 ${status.count}</td>
								<td colspan="6">
									${dto.maAddr1}&nbsp; ${dto.maAddr2} <br>
									( ${dto.area1}&nbsp; ${dto.area2}&nbsp; ${dto.area3} )
								</td>
							</tr>
						</c:forEach>
					</c:if>
					
					<c:if test="${not empty memberDto.busNickName}">
						<tr>
							<td colspan="1" class="table-light">업체 계정</td>
							<td colspan="6">${memberDto.busNickName}</td>
						</tr>
						<tr>
							<td colspan="1" class="table-light">업체 이메일</td>
							<td colspan="6">${memberDto.busEmail}</td>
						</tr>
						<tr>
							<td colspan="1" class="table-light">업체 주소</td>
							<td colspan="6">
								${memberDto.busAddr1}&nbsp; ${memberDto.busAddr2} <br>
								( ${memberDto.busArea1}&nbsp; ${memberDto.busArea2}&nbsp; ${memberDto.busArea3} )
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			
			<h4 class="mt-6 ms-2">신고 목록</h4>
			<c:if test="${empty reportList}">
				<div class="row mb-3">
					접수된 신고내역이 없습니다.
				</div>
			</c:if>
			<c:if test="${not empty reportList}">
				<table class="table">
					<tbody>
						<c:forEach var="dto" items="${reportList}" varStatus="status">
							<tr ${status.count == 1?"style='border-top-width: 3px;'":""}>
								<td colspan="1" class="table-light">신고번호</td>
								<td colspan="2">${dto.repMemNum}</td>
								<td colspan="1" class="table-light">사유</td>
								<td colspan="2">${dto.rmReasonName}</td>
								<td colspan="1" class="table-light btn-CheckReport text-center"
									data-repMemNum="${dto.repMemNum}">
									처리완료&nbsp; <i class="icofont-ui-check"></i>
								</td>
							</tr>
							<tr>
								<td colspan="1" class="table-light">신고자</td>
								<td colspan="2">${dto.userId}&nbsp;(${dto.reporter})</td>
								<td colspan="1" class="table-light">신고일시</td>
								<td colspan="3">${dto.repMemRegDate}</td>
							</tr>
							<tr style="border-bottom-width: 3px;">
								<td colspan="1" class="table-light">내용</td>
								<td colspan="6">${dto.repMemContent}
									<c:if test="${dto.repMemContent == null or dto.repMemContent == ''}">
										(상세내역 미작성)
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
	    </div>
	</div>
	
</main>