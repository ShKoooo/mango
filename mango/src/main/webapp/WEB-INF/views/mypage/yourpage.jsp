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

<script src="https://code.highcharts.com/highcharts.js"></script>

<script type="text/javascript">
$(function() {
	var values = [];
	var myDeg = Number($('#thermoContainer').attr('data-deg'));
	
	if (myDeg > 100) myDeg = 100;
	if (myDeg < 0) myDeg = 0;
	
	values.push(myDeg);
	
	if (myDeg<10) {
		var rgb = '#0000FF';
	} else if (myDeg>=10 && myDeg<15) {
		var rgb = '#146CF6';
	} else if (myDeg>=15 && myDeg<20) {
		var rgb = '#188AF0';
	} else if (myDeg>=20 && myDeg<25) {
		var rgb = '#00B7D8';
	} else if (myDeg>=25 && myDeg<30) {
		var rgb = '#00D4B0';
	} else if (myDeg>=30 && myDeg<35) {
		var rgb = '#00E54B';
	} else if (myDeg>=35 && myDeg<40) {
		var rgb = '#00F800';
	} else if (myDeg>=40 && myDeg<45) {
		var rgb = '#57E86B';
	} else if (myDeg>=45 && myDeg<50) {
		var rgb = '#78EC6C';
	} else if (myDeg>=50 && myDeg<55) {
		var rgb = '#A9F36A';
	} else if (myDeg>=55 && myDeg<60) {
		var rgb = '#DDF969';
	} else if (myDeg>=60 && myDeg<65) {
		var rgb = '#FEFE69';
	} else if (myDeg>=65 && myDeg<70) {
		var rgb = '#FEF001';
	} else if (myDeg>=70 && myDeg<75) {
		var rgb = '#FFCE03';
	} else if (myDeg>=75 && myDeg<80) {
		var rgb = '#FD9A01';
	} else if (myDeg>=80 && myDeg<85) {
		var rgb = '#FD6104';
	} else if (myDeg>=85 && myDeg<90) {
		var rgb = '#FF2C05';
	} else if (myDeg>=90 && myDeg<95) {
		var rgb = '#F00505';
	} else {
		var rgb = '#FF0000';
	} 
	
	console.log(rgb);
	
	Highcharts.chart('thermoContainer', {
	  	chart: {
	    	type: 'bar',
	    	height: 160,
	    	backgroundColor: '#FFFFFF'
	  	},
	  	title: {
	    	text: null
	  	},
	  	xAxis: {
			categories: [''],
	    	title: {
	        	text: null
    		}
		},
	  	yAxis: {
			min : 0,
			title: {
	      		text: '온도 (deg)',
	      		align: 'middle'
	    	},
	   		max : 100
	  	},
	  	tooltip: {
	    	valueSuffix: ' deg'
	  	},
	  	series: [{
	    	name: '나의 체온 ',
	    	data: values,
	    	pointWidth: 15,
	    	color: rgb
	  	}],
	  	credits: false
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

$(function() {
	$("body").on("click",".myBtnRep", function() {
		var userNick = "${userNick}";
		userNick = encodeURIComponent(userNick);
		
		var query = "userNick="+userNick;
		var url = "${pageContext.request.contextPath}/mypage/report?"+query;
		// console.log(url);
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
						<h3>${userNick} 님 </h3>
					</div>
					<div class="col-auto text-right">
						<button type="button" title="쪽지" class="btn btn-outline-primary me-2" onclick="location.href='${pageContext.request.contextPath}/mypage/notenote?youNick=${userNick}'">
							<i class="icofont-paper-plane"></i>
						</button>
						<button type="button" title="신고" class="btn btn-outline-danger myBtnRep">
							<i class="icofont-ui-block"></i>
						</button>
					</div>
				</div>
			</div>
			
			<div class="container">
				<div class="row mb-6">
					<h4><i class="icofont-thermometer"></i> 매너 온도</h4>
					<div id="thermoContainer" data-deg="${mannerDto.mannerDeg}"></div>
				</div>
				
				<div class="row mb-3">
					<h4><i class="icofont-star"></i> 평가</h4>
				</div>
				<c:if test="${empty ratingList}">
					<div class="border bg-light mb-3 p-3 text-center">
						평가가 없습니다.
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
										${dto.buyerNick}
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