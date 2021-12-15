<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
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
	// var myDeg = Number($('#thermoContainer').attr('data-deg'));
	var myDeg = 100;
	values.push(myDeg);
	var colorLim = ['#0000ff','#ffff00'];	// 0도 ~ 100도
	
	var cl1rr = colorLim[0][1]+colorLim[0][2];
	var cl1gg = colorLim[0][3]+colorLim[0][4];
	var cl1bb = colorLim[0][5]+colorLim[0][6];
	var cl2rr = colorLim[1][1]+colorLim[1][2];
	var cl2gg = colorLim[1][3]+colorLim[1][4];
	var cl2bb = colorLim[1][5]+colorLim[1][6];
	
	var rrN = myDeg/100*(Number.parseInt(cl2rr,16)-Number.parseInt(cl1rr,16)) + Number.parseInt(cl1rr,16);
	var rr = parseInt(rrN).toString(16);
	var ggN = myDeg/100*(Number.parseInt(cl2gg,16)-Number.parseInt(cl1gg,16)) + Number.parseInt(cl1gg,16);
	var gg = parseInt(ggN).toString(16);
	var bbN = myDeg/100*(Number.parseInt(cl2bb,16)-Number.parseInt(cl1bb,16)) + Number.parseInt(cl1bb,16);
	var bb = parseInt(bbN).toString(16);
	
	if (rr.length < 2) rr = '0'+rr;
	if (gg.length < 2) gg = '0'+gg;
	if (bb.length < 2) bb = '0'+bb;
	
	var rgb = '#'+rr+gg+bb;
	console.log(rgb);
	
	Highcharts.chart('thermoContainer', {
	  	chart: {
	    	type: 'bar',
	    	height: 160
	  	},
	  	title: {
	    	text: null
	  	},
	  	xAxis: {
			categories: ['나'],
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
	    	name: '나의 체온 : '+values[0]+' 도',
	    	data: values,
	    	pointWidth: 15,
	    	color: rgb
	  	}]
	});
});
</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 마이 페이지 </h3>
		</div>
		
		<div class="body-main">
			<div class="row mb-3">
				<h4>
					<a href="${pageContext.request.contextPath}/member/pwd">
						<i class="icofont-edit"></i> 회원 정보수정
					</a>
				</h4>
			</div>
			<div class="row mb-3">
				<h4><i class="icofont-thermometer"></i>나의 매너 온도</h4>
				<div id="thermoContainer" data-deg="${mannerDto.mannerDeg}"></div>
			</div>
			<div class="row mb-3">
				<h4><i class="icofont-location-pin"></i>주소 목록</h4>
				<c:if test="${empty addrList}">
					<div>등록한 주소 목록이 없습니다.</div>
				</c:if>
				<c:if test="${not empty addrList}">
					<table class="table table-striped">
						<tr>
							<th>기본주소</th>
							<th>상세주소</th>
						</tr>
						<c:forEach var="addrDto" items="${addrList}">
							<tr>
								<td>${addrDto.maAddr1}</td>
								<td>${addrDto.maAddr2}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<div class="col-3">
					<button class="btn btn-light" type="button" id="addrBtn" onclick="location.href='${pageContext.request.contextPath}/member/address'">
						주소 등록
					</button>
				</div>
			</div>
			<div class="row mb-3">
				<h4>
					<a href="${pageContext.request.contextPath}/member/business">
						<i class="icofont-briefcase-2"></i> 비즈니스 프로필 관리
					</a>
				</h4>
			</div>
			<div class="row mb-3">
				<h4>
					<a href="#">
						<i class="icofont-read-book-alt"></i> 관심유저, 차단유저, 관심키워드 (분리?)
					</a>
				</h4>
			</div>
			<div class="row mb-3">
				<h4>
					<a href="#">
						<i class="icofont-money"></i> 가계부
					</a>
				</h4>
			</div>
			<div class="row mb-3">
				<h4>
					<a href="#">
						<i class="icofont-history"></i> 내 활동
					</a>
				</h4>
			</div>
			<div class="row mb-3">
				<h4>
					<a href="#">
						<i class="icofont-ui-rating"></i> 내 평가
					</a>
				</h4>
			</div>
			<div class="row mb-3">
				<h4>
					<a href="#">
						<i class="icofont-chat"></i> 쪽지
					</a>
				</h4>
			</div>
		</div>
	</div>
</div>