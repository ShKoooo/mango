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
	background-image: url("${pageContext.request.contextPath}/resources/images/add_photo.png");
	position: relative;
	z-index: 9999;
	background-repeat : no-repeat;
	background-size : cover;
}

.map { width:700px; height:450px; border: 1px solid #777; }

.my-font-size {
	font-size: 14px;
}

.content-wrapper {
	background-color: #FFFFFF;
}
</style>

<script type="text/javascript">
// 수정필요
function memberOk() {
    var f = document.businessForm;
	var mode = "${mode}";
	var userId = "${sessionScope.member.userId}";
	var nick = f.busNickName.value;
	var tel1 = f.tel1.value;
	var tel2 = f.tel2.value;
	var tel3 = f.tel3.value;
	var email1 = f.email1.value.trim();
	var email2 = f.email2.value.trim();
	// var busTel;
	// var busEmail;
	var busZip = f.busZip.value;
	var busAddr1 = f.busAddr1.value;
	var busAddr2 = f.busAddr2.value;
	
	if(mode==="business" && !nick) {
		alert("닉네임을 입력하세요. ");
		f.busNickName.focus(); return;
	}
	
	if(mode === "business" && !tel1 || !tel2 || !tel3 ) {
        alert("전화번호를 입력하세요. ");
        if (!tel1) {
        	f.tel1.focus(); return;
        }
        if (!tel2) {
        	f.tel2.focus(); return;
        }
        if (!tel3) { 
        	f.tel3.focus(); return;
        }
    }
    if(mode === "business" && !/^\d{2,3}$/.test(tel1) ) {
        alert("숫자만 가능합니다. ");
        f.tel1.focus();
        return;
    }

    if(mode === "business" && !/^\d{3,4}$/.test(tel2) ) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }

    if(mode === "business" && !/^\d{4}$/.test(tel3) ) {
    	alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    if(mode === "business" && !email1 ) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    if(mode === "business" && !email2 ) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }
    
    if(mode === "business" && (!busZip || !busAddr1 || !busAddr2)) {
    	alert("주소를 입력하세요. ");
    	f.addr2.focus(); return;
    }
    
    if(mode === "business" &&f.userNickValid.value === "false") {
		str = "닉네임 중복 검사가 실행되지 않았습니다.";
		$("#userNickName").parents().find(".help-block2").html(str);
		f.userNickName.focus();
		return;
	}
	
	if(mode === "business" &&f.userEmailValid.value === "false") {
		str = "이메일 중복 검사가 실행되지 않았습니다.";
		$("#email1").parents().find(".help-block3").html(str);
		f.email1.focus();
		return;
	}
    
	f.action = "${pageContext.request.contextPath}/member/${mode}";
	
    f.submit();
}

function changeEmail() {
    var f = document.businessForm;
	    
    var str = f.selectEmail.value;
    if(str != "direct") {
        f.email2.value = str; 
        f.email2.readOnly = true;
        f.email1.focus(); 
    }
    else {
        f.email2.value = "";
        f.email2.readOnly = false;
        f.email1.focus();
    }
}

//이메일 중복검사
function userEmailCheck() {
	var email1 = $("#email1").val();
	var email2 = $("#email2").val();
	var userEmail = email1 + "%40" + email2; // @: &#64;
	
	var url = "${pageContext.request.contextPath}/member/busDuplCheck";
	var query = "userParam=" + userEmail;
	query += "&chkWay=busEmail";
	
	console.log(query);
	
	$.ajax({
		type:"POST"
		,url:url
		,data:query
		,dataType:"json"
		,success:function(data) {
			var passed = data.passed;
			
			if(passed === "true") {
				var str = "<span style='color:blue; font-weight: bold;'>" + email1+"&#64;"+email2 + "</span> 이메일은 사용가능 합니다.";
				$(".userEmail-box").find(".help-block3").html(str);
				$("#userEmailValid").val("true");
			} else {
				var str = "<span style='color:red; font-weight: bold;'>" + email1+"&#64;"+email2 + "</span> 이메일은 이미 사용되고 있습니다.";
				$(".userEmail-box").find(".help-block3").html(str);
				$("#email1").val("");
				$("#email2").val("");
				$("#userEmailValid").val("false");
				$("#email1").focus();
			}
		}
	});
}

//닉네임 중복검사
function busNickCheck() {
	var userNick=$("#busNickName").val();
	
	var url = "${pageContext.request.contextPath}/member/busDuplCheck";
	var query = "userParam=" + userNick;
	query += "&chkWay=busNickName";
	
	$.ajax({
		type:"POST"
		,url:url
		,data:query
		,dataType:"json"
		,success:function(data) {
			var passed = data.passed;

			if(passed === "true") {
				var str = "<span style='color:blue; font-weight: bold;'>" + userNick + "</span> 닉네임은 사용가능 합니다.";
				$(".busNick-box").find(".help-block2").html(str);
				$("#userNickValid").val("true");
			} else {
				var str = "<span style='color:red; font-weight: bold;'>" + userNick + "</span> 닉네임은 이미 사용되고 있습니다.";
				$(".busNick-box").find(".help-block2").html(str);
				$("#userNickName").val("");
				$("#userNickValid").val("false");
				$("#userNickName").focus();
			}
		}
	});
}

// 수정필요
$(function() {
	var img = "${dto.busImgSaveFileName}";
	if( img ) { // 수정인 경우
		img = "${pageContext.request.contextPath}/uploads/photo/" + img;
		$(".businessForm .img-viewer").empty();
		$(".businessForm .img-viewer").css("background-image", "url("+img+")");
	}
	
	$(".businessForm .img-viewer").click(function(){
		$("form[name=businessForm] input[name=profileImg]").trigger("click"); 
	});
	
	$("form[name=businessForm] input[name=profileImg]").change(function(){
		var file=this.files[0];
		
		if(! file) {
			$(".businessForm .img-viewer").empty();
			if( img ) {
				img = "${pageContext.request.contextPath}/uploads/photo/" + img;
				$(".businessForm .img-viewer").css("background-image", "url("+img+")");
			} else {
				img = "${pageContext.request.contextPath}/resources/images/add_photo.png";
				$(".businessForm .img-viewer").css("background-image", "url("+img+")");
			}
			return false;
		}
		
		if(! file.type.match("image.*")) {
			this.focus();
			return false;
		}
		
		var reader = new FileReader();
		reader.onload = function(e) {
			$(".businessForm .img-viewer").empty();
			$(".businessForm .img-viewer").css("background-image", "url("+e.target.result+")");
		}
		reader.readAsDataURL(file);
	});
});

</script>

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">	
			<div class="section-header">
				<h3>
					<c:if test="${mode=='business' }">
						<i class="icofont-contact-add"></i> 업체 신규 등록
					</c:if>
					<c:if test="${mode=='busnUpdate' }">
						<i class="icofont-id-card"></i> 업체 정보 수정
					</c:if>
				</h3>
			</div>
			
			<div class="alert alert-info" role="alert">
		        <i class="bi bi-person-check-fill"></i> 망고마켓에서 ${sessionScope.member.userNickName}님의 업체를 홍보하세요!
		    </div>
			
			<div class="container">
				<form name="businessForm" class="businessForm" method="post" enctype="multipart/form-data">
				
					<div class="row mb-3">
						<label class="col-sm-2 col-form-label" for="busNickName">업체 닉네임</label>
						<div class="col-sm-10 busNick-box">
							<div class="row">
								<div class="col-5 pe-1">
									<input type="text" name="busNickName" id="busNickName" class="form-control" value="${dto.busNickName}"
										${mode=="busnUpdate"?"readonly='readonly'":""}>
								</div>
								<div class="col-3 ps-1">
									<c:if test="${mode=='business'}">
										<button type="button" class="btn btn-light my-font-size" onclick="busNickCheck();">중복 확인</button>
									</c:if>
								</div>
								<small class="form-control-plaintext help-block2">
									<c:if test="${mode=='business'}">
										욕설을 사용하거나 기타 약관에 위배되는 닉네임은 운영자에 의해 임의 변경될 수 있음을 고지해 드립니다.
									</c:if>
									<c:if test="${mode=='busnUpdate'}">
										업체 닉네임 변경은 고객센터 1:1 문의를 이용해 주시기 바랍니다.
									</c:if>
								</small>
							</div>
						</div>
					</div>
					
					<div class="row mb-3">
						<label class="col-sm-2 col-form-label" for="tel1">연락처</label>
						<div class="col-sm-10 row">
							<div class="col-sm-3 pe-2">
								<input type="text" name="tel1" id="tel1" class="form-control" value="${dto.tel1}" maxlength="3">
							</div>
							<div class="col-sm-1 px-1" style="width: 2%;">
								<p class="form-control-plaintext text-center">-</p>
							</div>
							<div class="col-sm-3 px-1">
								<input type="text" name="tel2" id="tel2" class="form-control" value="${dto.tel2}" maxlength="4">
							</div>
							<div class="col-sm-1 px-1" style="width: 2%;">
								<p class="form-control-plaintext text-center">-</p>
							</div>
							<div class="col-sm-3 ps-1">
								<input type="text" name="tel3" id="tel3" class="form-control" value="${dto.tel3}" maxlength="4">
							</div>
				        </div>
					</div>
					
					<div class="row mb-3">
				    	<label class="col-sm-2 col-form-label" for="selectEmail">이메일</label>
				    	<div class="col-sm-10 row userEmail-box">
							<div class="col-3 pe-0">
								<select name="selectEmail" id="selectEmail" class="form-select" onchange="changeEmail();" ${mode=="busnUpdate" ? "readonly='readonly' ":""}>
									<option value="">선 택</option>
									<option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
									<option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
									<option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
									<option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
									<option value="direct">직접입력</option>
								</select>
							</div>
							
							<div class="col input-group">
								<input type="text" name="email1" id="email1" class="form-control" maxlength="30" value="${dto.email1}" 
									${mode=="busnUpdate" ? "readonly='readonly' ":""}>
							    <span class="input-group-text p-1" style="border: none; background: none;">@</span>
								<input type="text" name="email2" id="email2" class="form-control" maxlength="30" value="${dto.email2}" readonly="readonly">
								<div class="col-3 ps-3">
									<c:if test="${mode=='business'}">
										<button type="button" class="btn btn-light my-font-size" onclick="userEmailCheck();">중복 확인</button>
									</c:if>
								</div>
							</div>
							
							<small class="form-control-plaintext help-block3">
								<c:if test="${mode=='business'}">
									&nbsp;
								</c:if>
								<c:if test="${mode=='busnUpdate'}">
									업체 이메일 변경은 고객센터 1:1 문의를 이용해 주시기 바랍니다.
								</c:if>
							</small>	
				        </div>
				    </div>
				    
				    <div class="row mb-3">
				    	<label class="col-sm-2 col-form-label" for="selectProfileImg">프로필 사진</label>
				    	<div class="col-sm-10 row">
					    	<div class="img-viewer" ></div>
					    	<input type="file" id="selectProfileImg" name="profileImg" accept="image/*" class="form-control" style="display: none;">
				    	</div>
				    </div>
				    
				    <div class="row mb-3">
						<label class="col-sm-2 col-form-label" for="busZip">우편번호</label>
						<div class="col-sm-5">
				       		<div class="input-group">
				           		<input type="text" name="busZip" id="busZip" class="form-control" placeholder="우편번호" value="${dto.busZip}" readonly="readonly">&nbsp;&nbsp;
			           			<button class="btn btn-light my-font-size" type="button" style="margin-left: 3px;" onclick="daumPostcode();">우편번호 검색</button>
				           	</div>
						</div>
					</div>
					
					<div class="row mb-3">
				        <label class="col-sm-2 col-form-label" for="busAddr1">주소</label>
				        <div class="col-sm-10">
				       		<div>
				           		<input type="text" name="busAddr1" id="busAddr1" class="form-control" placeholder="기본 주소" value="${dto.busAddr1}" readonly="readonly">
				           	</div>
				       		<div style="margin-top: 5px;">
				       			<input type="text" name="busAddr2" id="busAddr2" class="form-control" placeholder="상세 주소" value="${dto.busAddr2}">
							</div>
						</div>
						<input type="hidden" name="area1" id="area1" value = "${dto.area1}">
						<input type="hidden" name="area2" id="area2" value = "${dto.area2}">
						<input type="hidden" name="area3" id="area3" value = "${dto.area3}">
				    </div>
				    
				    <div class="row mb-3">
				    	<div class="col-md-2">&nbsp;</div>
				    	<div class="col-md-4">
						    <button type="button" class="btn btn-light my-font-size" onclick="mapSearch();">지도에서 찾기</button>
				    	</div>
				    </div>
				    
				    <div class="row">
				    	<div class="col-auto me-auto">&nbsp;</div>
				    	<div class="col-auto">
						    <div id="map" class="map row mb-3 mx-auto"></div>
				    	</div>
				    </div>
				    <div id="map2" class="map row mb-3" style="display: none;"></div>
				    
				    <input type="hidden" name="bpLat" id = "bpLat" value="${dto.bpLat}">
				    <input type="hidden" name="bpLon" id = "bpLon" value="${dto.bpLon}">
				    <input type="hidden" name="areaNum" id = "areaNum" value="${dto.areaNum}">
				    
				    <input type="hidden" name="aLat" id = "aLat" value="${dto.aLat}">
				    <input type="hidden" name="aLon" id = "aLon" value="${dto.aLon}">
				    <input type="hidden" name="bcodeCut" id = "bcodeCut" value="${dto.bcodeCut}">
				    <input type="hidden" name="bcodeSigungu" id = "bcodeSigungu" value="${dto.bcodeSigungu}">
					
					<div class="row mb-3">
				        <label class="col-sm-2 col-form-label" for="agree">약관 동의</label>
						<div class="col-sm-8" style="padding-top: 5px;">
							<input type="checkbox" id="agree" name="agree"
								class="form-check-input"
								checked="checked"
								style="margin-left: 0;"
								onchange="form.sendButton.disabled = !checked">
							<label class="form-check-label">
								<a href="#" class="text-decoration-none">이용약관</a>에 동의합니다.
							</label>
						</div>
				    </div>
				    
				    <div class="row mb-3">
				        <div class="text-center">
				            <button type="button" name="sendButton" class="btn btn-primary" onclick="memberOk();"> ${mode=="business"?"신규등록":"정보수정"} <i class="bi bi-check2"></i></button>
				            <button type="reset" class="btn btn-light">다시입력</button>
				            <c:if test="${mode=='business'}">
					            <button type="button" class="btn btn-danger" onclick="location.href='${pageContext.request.contextPath}/mypage/main';"> 등록취소 <i class="bi bi-x"></i></button>
				            </c:if>
				            <c:if test="${mode=='busnUpdate'}">
					            <button type="button" class="btn btn-danger" onclick="location.href='${pageContext.request.contextPath}/mypage/main';"> 수정취소 <i class="bi bi-x"></i></button>
				            </c:if>
				            <c:if test="${mode=='business'}">
				            	<input type="hidden" name="userNickValid" id="userNickValid" value="false">
								<input type="hidden" name="userEmailValid" id="userEmailValid" value="false">
				            </c:if>
				            <c:if test="${mode=='busnUpdate'}">
				            	<input type="hidden" name="userNickValid" id="userNickValid" value="true">
								<input type="hidden" name="userEmailValid" id="userEmailValid" value="true">
				            </c:if>
			            </div>
		            </div>
		            
		            <c:if test="${mode=='busnUpdate'}">
						<input type="hidden" name="busImgSaveFileName" value="${dto.busImgSaveFileName}">
					</c:if>
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
                document.getElementById('busZip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('busAddr1').value = fullAddr;
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
                document.getElementById('busAddr2').focus();
            }
        }).open();
    }
    
    function mapSearch() {
    	// 등록하기. 주소 검색
    	// $("#map").hide(50);
    	// $(".btn-send").hide(50);
    	
    	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };  

	    // 지도를 생성합니다    
	    var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	    // 주소-좌표 변환 객체를 생성합니다
	    var geocoder = new kakao.maps.services.Geocoder();
	
	    var addr1 = document.getElementById('busAddr1').value;
	    var addr2 = document.getElementById('busAddr2').value;
	    
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
	            
	            document.getElementById('bpLat').value = coords.Ma;	// Ma: 위도, La: 경도 (안 헷갈리게 유의할 것)
	            document.getElementById('bpLon').value = coords.La;
	            
	            // $("#map").show(50);
	            // $(".btn-send").show(50);
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