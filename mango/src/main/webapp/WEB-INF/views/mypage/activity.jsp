<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.myLink:hover {
	color: tomato;
}

.content-wrapper {
	background-color: #FFFFFF;
}
</style>

<script type="text/javascript">
$(function() {
	$("body").on("click",".myLink", function() {
		var myType = $(this).attr("data-myType");
		var num = $(this).attr("data-num");
		var catNum = $(this).attr("data-catNum");
		
		var query = "";
		var url = "${pageContext.request.contextPath}";
		
		if (myType==="product") {
			url += "/product/article";
			query += "pNum="+num+"&pcNum="+catNum+"&page=1";
			
			url += "?"+query;
		} else if (myType==="giftycon") {
			url += "/gifty/article";
			
			query += "gNum="+num+"&page=1";
			url += "?"+query;
		} else {
			query += "vNum="+num+"&page=1";
			
			if (catNum === 1 || catNum === '1') {
				url += "/village/qna/article";
			} else if (catNum === 2 || catNum === '2') {
				url += "/village/eat/article";
			} else if (catNum === 3 || catNum === '3') {
				url += "/village/help/article";
			} else if (catNum === 4 || catNum === '4') {
				url += "/village/with/article";
			} else if (catNum === 5 || catNum === '5') {
				url += "/village/news/article";
			} else if (catNum === 6 || catNum === '6') {
				url += "/village/lost/article";
			} else if (catNum === 7 || catNum === '7') {
				url += "/village/ad/article";
			} else {
				url += "/village/forone/article";
			}
			
			url += "?"+query;
		}
		
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".myBtnRefresh", function() {
		var product = $("#product").is(":checked").toString();
		var giftycon = $("#giftycon").is(":checked").toString();
		var vbbs = $("#vbbs").is(":checked").toString();
		var vbbsReply = $("#vbbsReply").is(":checked").toString();
		
		var query = "page="+${page}+"&product="+product+"&giftycon="+giftycon+"&vbbs="+vbbs+"&vbbsReply="+vbbsReply;
		
		var url="${pageContext.request.contextPath}/mypage/activity?"+query;
		
		location.href = url;
	});
});
</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">
			<div class="section-header">
				<h3> 내 활동 </h3>
			</div>
			
			<div class="container">
				<div class="row mb-3">
					<div class="form-check">
						<div class="row md-5">
							<div class="col-auto me-auto">
								<div class="row">
									<div class="col-auto mx-3">
										<input class="form-check-input align-middle" type="checkbox" value="product" id="product"
											${product=="true"?"checked='checked'":""}>
										<label class="form-check-label align-middle" for="product">&nbsp;중고물품</label>
									</div>
									<div class="col-auto mx-3">
										<input class="form-check-input align-middle" type="checkbox" value="giftycon" id="giftycon"
											${giftycon=="true"?"checked='checked'":""}>
										<label class="form-check-label align-middle" for="giftycon">&nbsp;기프티콘</label>
									</div>
									<div class="col-auto mx-3">
										<input class="form-check-input align-middle" type="checkbox" value="vbbs" id="vbbs"
											${vbbs=="true"?"checked='checked'":""}>
										<label class="form-check-label align-middle" for="vbbs">&nbsp;우리동네</label>
									</div>
									<div class="col-auto mx-3">
										<input class="form-check-input align-middle" type="checkbox" value="vbbsReply" id="vbbsReply"
											${vbbsReply=="true"?"checked='checked'":""}>
										<label class="form-check-label align-middle" for="vbbsReply">&nbsp;댓글</label>
									</div>
								</div>
							</div>
							<div class="col-auto ms-6">
								<button class="btn btn-outline-primary myBtnRefresh">
									<i class="icofont-refresh"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row mb-3">
					<c:if test="${empty list}">
						<div class="border bg-light mb-3 p-3 text-center">
							내 활동이 없습니다.
						</div>
					</c:if>
					<c:if test="${not empty list}">
						<table class="table">
							<thead class="table-light">
								<tr>
									<th class="col-2">종류</th>
									<th class="col-5">제목</th>
									<th class="col-3">작성일시</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dto" items="${list}">
									<tr>
										<td class="col-2">
											<c:if test="${dto.myType=='product'}">중고물품</c:if>
											<c:if test="${dto.myType=='giftycon'}">기프티콘</c:if>
											<c:if test="${dto.myType=='vbbs'}">우리동네</c:if>
											<c:if test="${dto.myType=='vbbsReply'}">댓글</c:if>
										</td>
										<td class="col-5 myLink"
											data-myType="${dto.myType}"
											data-num="${dto.num}"
											data-catNum="${dto.catNum}">
											${dto.subject}
										</td>
										<td class="col-3">${dto.regDate}</td>
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