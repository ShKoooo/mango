<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.acYourpage:hover {
	color: tomato;
}

.myBox {
	padding: 5px 5px;
	border-radius: 5px;
}

.myBox:hover {
	border-color: tomato;
}

.content-wrapper {
	background-color: #FFFFFF;
}
</style>

<script type="text/javascript">

$(function() {
	$("body").on("click",".acYourpage", function() {
		var yourNick = $(this).attr("data-target-nick");
		if (!yourNick) {
			return false;
		}
		
		yourNick = encodeURIComponent(yourNick);
		
		var query = "userNickName="+yourNick;
		
		var url = "${pageContext.request.contextPath}/mypage/yourpage?"+query;
		
		window.open(url,'_blank');
	});
});

$(function() {
	$("body").on("click",".myBtnRefresh", function() {
		var chkSel = $("#chkSel").is(":checked").toString();
		var chkBuy = $("#chkBuy").is(":checked").toString();
		var chkPrd = $("#chkPrd").is(":checked").toString();
		var chkGfc = $("#chkGfc").is(":checked").toString();
		var acStartDate = $("#acStartDate").val().toString();
		var acEndDate = $("#acEndDate").val().toString();
		
		var query = "page="+${page}+"&chkSel="+chkSel+"&chkBuy="+chkBuy+"&chkPrd="+chkPrd+"&chkGfc="+chkGfc;
		if (acStartDate) {
			acStartDate = acStartDate.replaceAll("-","");
			acStartDate = acStartDate.replaceAll("/","");
			acStartDate = acStartDate.replaceAll(".","");
			query += "&acStartDate="+acStartDate;
		}
		if (acEndDate) {
			acEndDate = acEndDate.replaceAll("-","");
			acEndDate = acEndDate.replaceAll("/","");
			acEndDate = acEndDate.replaceAll(".","");			
			query += "&acEndDate="+acEndDate;
		}
		
		if (acStartDate && acEndDate) {
			if (acEndDate < acStartDate) {
				$("#AcMsgPrint").html("<span style='color:tomato;'>종료일은 시작일 이후여야 합니다.</span>");
				$("#acEndDate").focus();
				return false;
			}
		}
		
		var url = "${pageContext.request.contextPath}/mypage/account?"+query;
		console.log(url);
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".myBtnReset", function() {
		$("#chkSel").prop("checked",true);
		$("#chkBuy").prop("checked",true);
		$("#chkPrd").prop("checked",true);
		$("#chkGfc").prop("checked",true);
		$("#acStartDate").val('');
		$("#acEndDate").val('');
	});
});

</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">
			<div class="section-header">
				<h3> 거래내역 </h3>
			</div>
			
			<div class="container">
				<div class="row mb-3">
					<div class="form-check">
						<div class="row md-5">
							<div class="col-auto me-auto">
								<div class="row mb-3 pb-1">
									<div class="col-auto mx-3">
										<input class="form-check-input align-middle" type="checkbox" value="chkSel" id="chkSel"
											${chkSel=="true"?"checked='checked'":""}>
										<label class="form-check-label align-middle" for="chkSel">&nbsp;판매</label>
									</div>
									<div class="col-auto mx-3">
										<input class="form-check-input align-middle" type="checkbox" value="chkBuy" id="chkBuy"
											${chkBuy=="true"?"checked='checked'":""}>
										<label class="form-check-label align-middle" for="chkBuy">&nbsp;구매</label>
									</div>
									<div class="col-auto mx-3">
										<input class="form-check-input align-middle" type="checkbox" value="chkPrd" id="chkPrd"
											${chkPrd=="true"?"checked='checked'":""}>
										<label class="form-check-label align-middle" for="chkPrd">&nbsp;중고물품</label>
									</div>
									<div class="col-auto mx-3">
										<input class="form-check-input align-middle" type="checkbox" value="chkGfc" id="chkGfc"
											${chkGfc=="true"?"checked='checked'":""}>
										<label class="form-check-label align-middle" for="chkGfc">&nbsp;기프티콘</label>
									</div>
								</div>
								<div class="row mb-3">
									<div class="col-auto me-auto pe-3">
										시작일&nbsp;&nbsp;<input type="date" class="acStartDate myBox" id = "acStartDate" value="${acStartDate}">
									</div>
									<div class="col-auto pe-3">
										종료일&nbsp;&nbsp;<input type="date" class="acEndDate myBox" id = "acEndDate" value="${acEndDate}">
									</div>
									<div class="col-auto" id="AcMsgPrint">
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
				</div>
			
				<div class="row mb-3">
					<c:if test="${empty list}">
						<div class="border bg-light mb-3 p-3 text-center">
							거래내역이 없습니다.
						</div>
					</c:if>
					
					<c:if test="${not empty list}">
						<div class="row ps-3">총 거래 : ${dataCount} 건</div>
						<table class="table">
							<thead class="table-light">
								<tr>
									<th class="col-2">종류</th>
									<th class="col-2">거래대상</th>
									<th class="col-3">내용</th>
									<th class="col-1">입금</th>
									<th class="col-1">출금</th>
									<th class="col-2">거래일시</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dto" items="${list}">
									<tr>
										<td class="col-2">
											<c:if test="${dto.pgType=='product'}">
												중고물품
											</c:if>
											<c:if test="${dto.pgType=='giftycon'}">
												기프티콘
											</c:if>
										</td>
										<td class="col-2 acYourpage" data-target-nick="${dto.userNickName}">
											${dto.userNickName}
										</td>
										<td class="col-3">
											<c:if test="${dto.bsType=='sell'}">
												<span title="판매" style="color: blue"><i class="icofont-arrow-right"></i></span>
											</c:if>
											<c:if test="${dto.bsType=='buy'}">
												<span title="구매" style="color: red"><i class="icofont-arrow-left"></i></span>
											</c:if>
											<span title="${dto.fullSubject}">${dto.subject}</span>
										</td>
										<td class="col-1">${dto.income}</td>
										<td class="col-1">${dto.expenses}</td>
										<td class="col-2" title="${dto.fullSoldDate}">${dto.soldDate}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
				
				<div class="row mb-3">
					${paging}
				</div>
			</div>
		</div>
	</div>
</div>