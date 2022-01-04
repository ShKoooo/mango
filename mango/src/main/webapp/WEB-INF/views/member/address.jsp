<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

function sendOk() {
	var f = document.addrForm;
	
	f.action = "${pageContext.request.contextPath}/member/address";
	f.submit();
}

</script>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.map { width:700px; height:450px; border: 1px solid #777; }

.my-font-size {
	font-size: 14px;
}

.content-wrapper {
	background-color: #FFFFFF;
}
</style>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">
			<div class="section-header">
				<h3><i class="icofont-google-map"></i> 주소 설정 </h3>
			</div>
			
			<div class="container">
				<form name="addrForm" method="post">
				
					<div class="row mb-3">
						<label class="col-sm-2 col-form-label" for="maZip">우편번호</label>
						<div class="col-sm-5">
				       		<div class="input-group">
				           		<input type="text" name="maZip" id="maZip" class="form-control" placeholder="우편번호" value="${dto.maZip}" readonly="readonly">&nbsp;&nbsp;
			           			<button class="btn btn-light my-font-size" type="button" style="margin-left: 3px;" onclick="daumPostcode();">우편번호 검색</button>
				           	</div>
						</div>
					</div>
					
					<div class="row mb-3">
				        <label class="col-sm-2 col-form-label" for="maAddr1">주소</label>
				        <div class="col-sm-10">
				       		<div>
				           		<input type="text" name="maAddr1" id="maAddr1" class="form-control" placeholder="기본 주소" value="${dto.maAddr1}" readonly="readonly">
				           	</div>
				       		<div style="margin-top: 5px;">
				       			<input type="text" name="maAddr2" id="maAddr2" class="form-control" placeholder="상세 주소" value="${dto.maAddr2}">
							</div>
						</div>
						<input type="hidden" name="area1" id="area1" value = "${dto.area1}">
						<input type="hidden" name="area2" id="area2" value = "${dto.area2}">
						<input type="hidden" name="area3" id="area3" value = "${dto.area3}">
				    </div>
				    
				    <div class="row mb-3">
				    	<div class="col-md-2">&nbsp;</div>
				    	<div class="col-md-4">
						    <button type="button" class="btn btn-light" onclick="mapSearch();">지도에서 찾기</button>&nbsp;&nbsp;
						    <button type="button" class="btn btn-send btn-primary" onclick="sendOk();" style="display: none;">등록하기</button>
				    	</div>
				    	<div class="col-md-2 ms-auto">
				    		<button type="button" class="btn btn-danger justify-content-md-end" onclick="location.href='${pageContext.request.contextPath}/mypage/main'">뒤로가기</button>
				    	</div>
				    </div>
				    
				    <div class="row">
				    	<div class="col-auto me-auto">&nbsp;</div>
				    	<div class="col-auto">
						    <div id="map" class="map row mb-3 mx-auto"></div>
				    	</div>
				    </div>
				    <div id="map2" class="map row mb-3" style="display: none;"></div>
				    
				    <input type="hidden" name="maLat" id = "maLat" value="${dto.maLat}">
				    <input type="hidden" name="maLon" id = "maLon" value="${dto.maLon}">
				    
				    <input type="hidden" name="aLat" id = "aLat" value="${dto.aLat}">
				    <input type="hidden" name="aLon" id = "aLon" value="${dto.aLon}">
				    <input type="hidden" name="bcodeCut" id = "bcodeCut" value="${dto.bcodeCut}">
				    <input type="hidden" name="bcodeSigungu" id = "bcodeSigungu" value="${dto.bcodeSigungu}">
				    
				   		    
				</form>
			</div>
		</div>
	</div>
</div>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d2d510fbc8c7d16727e9019ea9b6de54&libraries=services,clusterer,drawing"></script>

<script type="text/javascript">
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수
                var area1 = '';
                var area2 = '';
                var area3 = '';
                var bcode = '';
                var bcodeCut = '';
                var area123 = '';

                console.log(data);                
                
                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }
                
                // 시도, 시군구, 읍면동
                area1 = data.sido;
                area2 = data.sigungu;
                area3 = data.bname1;
                if (area3 === null || area3 === '') {
                	area3 = data.bname;
                }
                bcode = data.bcode;
                bcodeCut = bcode.substring(0,bcode.length-2);
                console.log(bcode+":"+bcodeCut);

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('maZip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('maAddr1').value = fullAddr;
                document.getElementById('area1').value = area1;
                document.getElementById('area2').value = area2;
                document.getElementById('area3').value = area3;
                document.getElementById('bcodeCut').value = bcodeCut;
                document.getElementById('bcodeSigungu').value = data.sigunguCode;
                
                // area 테이블의 좌표 등록을 위한 위치검색어
                area123 = area1+" "+area2+" "+area3;
                setAreaCoords(area123);
                
                console.log(fullAddr);
                console.log(area1+":"+area2+":"+area3);
                console.log(bcodeCut);

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('maAddr2').focus();
            }
        }).open();
    }
    
    function mapSearch() {
    	// 등록하기. 주소 검색
    	// $("#map").hide(50);
    	$(".btn-send").hide(50);
    	
    	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };  

	    // 지도를 생성합니다    
	    var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	    // 주소-좌표 변환 객체를 생성합니다
	    var geocoder = new kakao.maps.services.Geocoder();
	
	    var addr1 = document.getElementById('maAddr1').value;
	    var addr2 = document.getElementById('maAddr2').value;
	    
	    // 주소로 좌표를 검색합니다
	    geocoder.addressSearch(addr1, function(result, status) {
	
	        // 정상적으로 검색이 완료됐으면 
	         if (status === kakao.maps.services.Status.OK) {
	
	            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
	            // 결과값으로 받은 위치를 마커로 표시합니다
	            var marker = new kakao.maps.Marker({
	                map: map,
	                position: coords
	            });
	
	            // 인포윈도우로 장소에 대한 설명을 표시합니다
	            var infowindow = new kakao.maps.InfoWindow({
	                content: '<div style="width:150px;text-align:center;padding:6px 0;">'+addr2+'</div>'
	            });
	            infowindow.open(map, marker);
	
	            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	            map.setCenter(coords);
	            console.log(coords);
	            
	            document.getElementById('maLat').value = coords.Ma;	// Ma: 위도, La: 경도 (안 헷갈리게 유의할 것)
	            document.getElementById('maLon').value = coords.La;
	            
	            // $("#map").show(50);
	            $(".btn-send").show(50);
	        } 
	    });
	    
    }
    
    function setAreaCoords(area123) {
    	var mapContainer = document.getElementById('map2'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };  

	    // 지도를 생성합니다    
	    var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	    // 주소-좌표 변환 객체를 생성합니다
	    var geocoder = new kakao.maps.services.Geocoder();
	    
	    geocoder.addressSearch(area123, function(result, status) {
	    	
	        // 정상적으로 검색이 완료됐으면 
	         if (status === kakao.maps.services.Status.OK) {
	
	            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
	            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	            map.setCenter(coords);
	            console.log(coords);
	            
	            document.getElementById('aLat').value = coords.Ma;	// Ma: 위도, La: 경도 (안 헷갈리게 유의할 것)
	            document.getElementById('aLon').value = coords.La;
	        } 
	    });
    }
	
</script>