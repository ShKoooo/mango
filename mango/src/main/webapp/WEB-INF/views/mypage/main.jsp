<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.content-wrapper {
	background-color: #FFFFFF;
}
</style>

<script src="https://code.highcharts.com/highcharts.js"></script>

<script type="text/javascript">
/*
	$(function() {
		var url = "${pageContext.request.contextPath}/";
		$.getJSON(url, function(data) {
			
		});
	});
*/

$(function() {
	var values = [];
	var myDeg = Number($('#thermoContainer').attr('data-deg'));
	// var myDeg = 10;
	
	if (myDeg > 100) myDeg = 100;
	if (myDeg < 0) myDeg = 0;
	
	values.push(myDeg);
	/*
		var colorLim = ['#aaff00','#ffaa00'];	// 0도 ~ 100도
		
		var cl1rr = colorLim[0][1]+colorLim[0][2];
		var cl1gg = colorLim[0][3]+colorLim[0][4];
		var cl1bb = colorLim[0][5]+colorLim[0][6];
		var cl2rr = colorLim[1][1]+colorLim[1][2];
		var cl2gg = colorLim[1][3]+colorLim[1][4];
		var cl2bb = colorLim[1][5]+colorLim[1][6];
		
		var rrN = myDeg/100*(Number.parseInt(cl2rr,16)-Number.parseInt(cl1rr,16)) + Number.parseInt(cl1rr,16);
		var ggN = myDeg/100*(Number.parseInt(cl2gg,16)-Number.parseInt(cl1gg,16)) + Number.parseInt(cl1gg,16);
		var bbN = myDeg/100*(Number.parseInt(cl2bb,16)-Number.parseInt(cl1bb,16)) + Number.parseInt(cl1bb,16);
		
		var gg = parseInt(ggN).toString(16);
		var rr = parseInt(rrN).toString(16);
		var bb = parseInt(bbN).toString(16);
		
		if (rr.length < 2) rr = '0'+rr;
		if (gg.length < 2) gg = '0'+gg;
		if (bb.length < 2) bb = '0'+bb;
		
		var rgb = '#'+rr+gg+bb;
	*/
	
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
	// 주소 삭제
	$("body").on("click",".btn-delAddr", function() {
		if(!confirm("주소를 삭제하시겠습니까?")) {
			return false;
		}
		
		var maNum = $(this).closest("tr").attr("data-maNum");
		var url = "${pageContext.request.contextPath}/member/deleteAddr";
		var query = "maNum="+maNum;
		
		$.ajax({
			type:"POST"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				var passed = data.state;

				if (passed==="true") {
					location.href = "${pageContext.request.contextPath}/mypage/main";
					location.reload();
				}
			}
		});
	});
	
	// 비즈니스 프로필 삭제
	$("body").on("click",".btn-delBusn", function() {
		if(!confirm("프로필을 삭제하시겠습니까?")) {
			return false;
		}
		
		var userId = "${sessionScope.member.userId}";
		var url = "${pageContext.request.contextPath}/member/deleteBusiness";
		var query = "userId="+userId;
		
		$.ajax({
			type:"POST"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				var passed = data.state;

				if (passed==="true") {
					location.href = "${pageContext.request.contextPath}/mypage/main";
					location.reload();
				}
			}
		});
	});
});

$(function() {
	$("body").on("click",".btn-dropout", function() {
		if (!confirm("회원을 탈퇴하면 복구가 불가능합니다.\n회원 탈퇴를 진행하시겠습니까?")) {
			return false;
		}
		
		var url = "${pageContext.request.contextPath}/member/pwd?dropout=dropout&mode=dropout";
		
		location.href = url;
	});
});

</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">
			<div class="section-header">
				<h3> <span>마이 페이지</span> </h3>
			</div>
			
			<div class="container">
				<div class="row mb-3">
					<div class="col-auto me-auto">
						<h4>
							<a href="${pageContext.request.contextPath}/member/pwd?mode=update">
								<i class="icofont-edit"></i> 회원 정보수정
							</a>
						</h4>
					</div>
					<div class="col-auto text-right">
						<button class="btn btn-danger btn-dropout">
							<i class="icofont-sign-out"></i> 회원탈퇴
						</button>
					</div>
				</div>
				<div class="row mb-3">
					<h4><i class="icofont-thermometer"></i> 나의 매너 온도</h4>
					<div id="thermoContainer" data-deg="${mannerDto.mannerDeg}"></div>
				</div>
				<div class="row mb-3">
					<div class="row mb-3">
						<h4><i class="icofont-location-pin"></i> 주소 목록</h4>
					</div>
					<c:if test="${empty addrList}">
						<div class="border bg-light mb-3 p-3 text-center">등록한 주소 목록이 없습니다.</div>
					</c:if>
					<c:if test="${not empty addrList}">
						<div class="row mb-1 mx-3">
							<table class="table">
								<thead class="table-light">
									<tr>
										<th class="col-8">등록 지역</th>
										<th class="col-1">&nbsp;</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="addrDto" items="${addrList}">
										<tr data-maNum = '${addrDto.maNum}'>
											<td class="col-8">${addrDto.area1}&nbsp;${addrDto.area2}&nbsp;${addrDto.area3}</td>
											<td class="col-1">
												<button type="button" class="btn btn-outline-danger btn-delAddr" name="deleteAddr"><i class="icofont-close"></i></button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					
					<div class="row">
						<div class="col-md-12 text-right">
							<button class="btn btn-primary" type="button" id="addrBtn" onclick="location.href='${pageContext.request.contextPath}/member/address'">
								주소 등록
							</button>
						</div>
					</div>
						
				</div>
				<div class="row mb-3">
					<div class="row mb-3">
						<h4>
							<i class="icofont-briefcase-2"></i> 비즈니스 프로필
						</h4>
					</div>
					
					<c:if test="${empty businessDto}">
						<div class="border bg-light mb-3 p-3 text-center">망고마켓에서 ${sessionScope.member.userNickName}님의 업체를 홍보하세요!</div>
					</c:if>
					<c:if test="${not empty businessDto}">
						<div class="row mb-1 mx-3">
							<table class="table">
								<thead class="table-light">
									<tr>
										<th class="col-3">업체 닉네임</th>
										<th class="col-5">등록 지역</th>
										<th class="col-1">&nbsp;</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="col-3">${businessDto.busNickName}</td>
										<td class="col-5">${businessDto.area1}&nbsp;${businessDto.area2}&nbsp;${businessDto.area3}</td>
										<td class="col-1">
											<button type="button" class="btn btn-outline-danger btn-delBusn" name="deleteBusn"><i class="icofont-close"></i></button>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</c:if>
					
					<div class="row">
						<div class="col-md-12 text-right">
							<c:if test="${empty businessDto}">
								<button class="btn btn-primary" type="button" id="addrBtn" onclick="location.href='${pageContext.request.contextPath}/member/business'">
									업체 등록
								</button>
							</c:if>
							<c:if test="${not empty businessDto}">
								<button class="btn btn-primary" type="button" id="addrBtn" onclick="location.href='${pageContext.request.contextPath}/member/pwd?mode=busnUpdate'">
									프로필 수정
								</button>
							</c:if>
						</div>
					</div>
					
				</div>
				
				<div class="row mb-3">
					<div class="col-md-4">
						<h4>
							<i class="icofont-read-book-alt"></i> 관심 유저
						</h4>
					</div>
					<div class="col-md-4">
						<h4>
							<a href="${pageContext.request.contextPath}/mypage/mypick">
								${countPick}&nbsp;명
							</a>
						</h4>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-4">
						<h4>
							<i class="icofont-not-allowed"></i> 차단 유저
						</h4>
					</div>
					<div class="col-md-4">
						<h4>
							<a href="${pageContext.request.contextPath}/mypage/myblock">
								${countBlock}&nbsp;명
							</a>
						</h4>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-4">
						<h4>
							<i class="icofont-like"></i> 관심 키워드
						</h4>
					</div>
					<div class="col-md-4">
						<h4>
							<a href="${pageContext.request.contextPath}/mypage/mykeyword">
								${countKeyword}&nbsp;개
							</a>
						</h4>
					</div>
				</div>
				
				<div class="row mb-3">
					<h4>
						<a href="${pageContext.request.contextPath}/mypage/account">
							<i class="icofont-money"></i> 가계부
						</a>
					</h4>
				</div>
				
				<div class="row mb-3">
					<h4>
						<a href="${pageContext.request.contextPath}/mypage/activity">
							<i class="icofont-history"></i> 내 활동
						</a>
					</h4>
				</div>
				
				<div class="row mb-3">
					<h4>
						<a href="${pageContext.request.contextPath}/mypage/myrating">
							<i class="icofont-ui-rating"></i> 내 평가
						</a>
					</h4>
				</div>
				
				<div class="row mb-3">
					<h4>
						<a href="${pageContext.request.contextPath}/mypage/note">
							<i class="icofont-chat"></i> 쪽지
						</a>
					</h4>
				</div>
				
			</div>
		</div>
	</div>
</div>