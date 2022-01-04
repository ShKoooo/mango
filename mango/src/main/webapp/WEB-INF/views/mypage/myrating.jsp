<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.img-viewer {
	cursor: pointer;
	border: 1px solid #ccc;
	width: 45px;
	height: 45px;
	border-radius: 45px;
	padding: 0;
	background-image: url("${pageContext.request.contextPath}/resources/images/note-person-icon2.png");
	position: relative;
	z-index: 9999;
	background-repeat : no-repeat;
	background-size : cover;
}

.buyerNick:hover {
	color: tomato;
}

.content-wrapper {
	background-color: #FFFFFF;
}
</style>

<script type="text/javascript">
$(function() {
	$("#typeName").change( function() {
		var typeName = $(this).val();
		
		var query = "typeName="+typeName;
		var url = "${pageContext.request.contextPath}/mypage/myrating?"+query;
		
		location.href=url;
	});
});

$(function() {
	$("body").on("click",".buyerNick", function() {
		var userNick = $(this).attr("data-nick");
		
		var userNickName = encodeURIComponent(userNick);
		
		var query = "userNickName="+userNickName;
		var url = "${pageContext.request.contextPath}/mypage/yourpage?"+query;
		
		window.open(url,'_blank');
	});
});
</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">
			<div class="section-header">
				<div class="row">
					<div class="col-auto me-auto">
						<h3> 내 평가 </h3>
					</div>
					<div class="col-auto">
						<button type="button" title="새로고침" class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/myrating'"><i class="icofont-refresh"></i></button>
						&nbsp;
						<button type="button" title="뒤로가기" class="btn btn-outline-primary" onclick="location.href='${pageContext.request.contextPath}/mypage/main'"><i class="icofont-arrow-left"></i></button>
					</div>
				</div>
			</div>
			<div class="container">
				<c:if test="${empty ratingList}">
					<div class="border bg-light mb-3 p-3 text-center">
						내 평가가 없습니다.
					</div>		
				</c:if>
				<c:if test="${not empty ratingList}">
					<div class="row mb-4">
						<div class="col">
							(평균별점)
							&nbsp;&nbsp;
							${avgPrdStar==""?"":"품질: "}${avgPrdStar}
							&nbsp;&nbsp;
							${avgManStar==""?"":"매너: "}${avgManStar}
						</div>
						<div class="col text-right">
							<select name="typeName" id="typeName">
								<option value="all" ${typeName=="all"?"selected='selected'":""}>전체</option>
								<option value="product" ${typeName=="product"?"selected='selected'":""}>중고상품</option>
								<option value="giftycon" ${typeName=="giftycon"?"selected='selected'":""}>기프티콘</option>
							</select>
						</div>
					</div>
					<div class="row mb-3">
						<c:forEach var="dto" items="${ratingList}">
							<div class="row mb-3">
								<div class="col-md-3">
									<div class="row">
										<c:if test="${not empty dto.userImgSaveFileName}">
											<img src="${pageContext.request.contextPath}/uploads/photo/${dto.userImgSaveFileName}"
												class="img-fluid img-thumbnail img-viewer">
										</c:if>
										<c:if test="${empty dto.userImgSaveFileName}">
											<img
												class="img-fluid img-thumbnail img-viewer">
										</c:if>
									</div>
									<div class="row mt-1">
										<span class="buyerNick" data-nick="${dto.buyerNick}">${dto.buyerNick}</span>
									</div>
								</div>
								<div class="col-md-9">
									<div class="row">
										<div class="col-auto me-auto">
											<c:if test="${dto.pgType == 'product'}">
												(중고상품)
											</c:if>
											<c:if test="${dto.pgType == 'giftycon'}">
												(기프티콘)
											</c:if>
											&nbsp;&nbsp;
											${dto.subject}
										</div>
										<div class="col-auto text-right">
											<c:if test="${dto.pgType == 'product'}">
												품질&nbsp;
												<c:if test="${dto.prdStar>=5}">
													★★★★★
												</c:if>
												<c:if test="${dto.prdStar==4}">
													★★★★
												</c:if>
												<c:if test="${dto.prdStar==3}">
													★★★
												</c:if>
												<c:if test="${dto.prdStar==2}">
													★★
												</c:if>
												<c:if test="${dto.prdStar<=1}">
													★
												</c:if>
												&nbsp;|&nbsp;
											</c:if>
											
											매너&nbsp;
											<c:if test="${dto.mannerStar>=5}">
												★★★★★
											</c:if>
											<c:if test="${dto.mannerStar==4}">
												★★★★
											</c:if>
											<c:if test="${dto.mannerStar==3}">
												★★★
											</c:if>
											<c:if test="${dto.mannerStar==2}">
												★★
											</c:if>
											<c:if test="${dto.mannerStar<=1}">
												★
											</c:if>						
										</div>
									</div>
									<div class="row m-2">
										${dto.reviewContent}
									</div>
								</div>
								<hr>
							</div>
						</c:forEach>
					</div>
					${paging}
				</c:if>
			</div>
		</div>
		
	</div>
</div>