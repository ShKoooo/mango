<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.section-header {
  margin-top: 60px;
  margin-bottom: 60px;
}

.jb {
	width: 30px;
	height: 30px;
	
}

.jb:hover {
  transform: scale( 1.2, 1.2 )
}

.writeBtnTop {
	position: fixed;
	left: 95%;
	top: 90%;
}

</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">

<script type="text/javascript">

// 카테고리별 리스트
$(function(){
	$("#tab-${pcNum}").addClass("active"); // 눌린부분 활성화
	
    $("button[role='tab']").on("click", function(e){
		var tab = $(this).attr("data-tab");
		
		var tab2 = $("#pcateNum").val("tab");
		
		console.log(tab2);
		
		var maLat = $("#maLat").val() || 0;
		var maLon = $("#maLon").val() || 0;
		
		// opt : 지역번호
		var opt = $("#selectArea option:selected").val() || 0;
		
		var url = "${pageContext.request.contextPath}/product/list?pcNum="+tab+"&maLat="+maLat+"&maLon="+maLon+"&opt="+opt;
		
		location.href=url;
    });
});

// 주소선택시 선택된 값을 저장하게 하는 부분 (값을 저장해야 해당 옵션에 대한 카테고리별 리스트를 볼 수 있다.)
$(function(){
	$("#selectArea").val("${opt}").prop("selected", true);
});


// 주소선택 상자 선택 부분
$(function(){
	if($("#selectArea").val()) {
		selectAreaList();
	}
	
	$("#selectArea").change( function() {
		// 주소 바꿨을때 좌측 좌표 영역에 값 셋팅
		if($(this).val()=="0") {
			$("#maLat").val("0");
			$("#maLon").val("0");
			return false;
		}
		selectAreaList();
		
	});
	
	function selectAreaList() {
		var opt = $("#selectArea option:selected");
		
		$("#maLat").val(opt.attr("data-maLat"));
		$("#maLon").val(opt.attr("data-maLon"));
		// 주소 바꿨을때 좌측 좌표 영역에 값 셋팅
		
		/*
		var url = "${pageContext.request.contextPath}/product/list2"
		var query = {maLat: $("#maLat").val(), maLon: $("#maLon").val()};
		
		console.log(query);
		
		var fn = function(data){
			printJson2(data);
			console.log("성공..?");
		};
		ajaxFun(url, "get", query, "json", fn);
		*/
	}
});



// ajax 함수
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status===403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
			
			console.log(jqXHR.responseText);
		}
	});
}


// 더보기
var current_page = 2;

function listPage(page, pcNum) {
	var url = "${pageContext.request.contextPath}/product/morelist";
	var query = "page=" + page + "&pcNum=" + pcNum;
	
	var fn = function(data) {
		printProduct(data);
		//console.log(data);
	};
	ajaxFun(url, "get", query, "json", fn);
}

function printProduct(data) {
	var dataCount = data.dataCount;
	var page = data.page;
	var total_page = data.total_page;
	var pcNum = data.pcNum;
	
//	$(".more-list").show();
	
	$(".load-more").hide();
	if(dataCount == 0) {
		$(".more-list").empty();
		return;
	}
	
	if(page < total_page) {
		$(".load-more").show();
	}
	
	var out = "";
	
	//console.log("data>> ", data);
	
	for(var i=0; i<data.list.length; i++) {
		var pcNum = data.list[i].pcNum;
		var pNum = data.list[i].pNum;
		var pSubject = data.list[i].pSubject;
		var pRegDate = data.list[i].pRegDate;
		var area3 = data.list[i].area3 || '주소';
		var pWishCount = data.list[i].pWishCount;
		var userNickName = data.list[i].userNickName;
		
		out += "<div class='col-lg-4 mb-5'>";
		out += "    <div class='card h-100 shadow border-0'>";
		out += "    	<img class='card-img-top' src='https://dummyimage.com/600x350/ced4da/6c757d' alt='' />";
		out += "		<div class='card-body p-4'>";
		out += "			<a class='text-decoration-none link-dark stretched-link' href='${articleUrl}&pNum="+pNum+"'><div class='h5 card-title mb-3'>"+pSubject+"</div></a>";
		out += "			<p class='card-text mb-0'>"+area3+"</p>";	
		out += "		</div>";
		out += "		<div class='card-footer p-4 pt-0 bg-transparent border-top-0'>";
		out += "			<div class='d-flex align-items-end justify-content-between'>";
		out += "				<div class='d-flex align-items-center'>";
		out += "					<img class='rounded-circle me-3' src='https://dummyimage.com/40x40/ced4da/6c757d' alt='' />";
		out += "					<div class='small'>";
		out += "						<div class='fw-bold'>"+userNickName+"</div>";
		out += "						<div class='text-muted'>"+pRegDate+" &middot; 만약 끌올하면 끌올 몇분전이나 몇회나 표시</div>";
		out += "					</div>";
		out += "				</div>"
		out += "			</div>"
		out += "		</div>"
		out += "	</div>"
		out += "</div>"
	}	
	
	$(".more-list").append(out);
		
}

$(function(){
	$(".load-more .more").click(function(){
		// listPage(2);
		
		var page = ${page};
		var total_page = ${total_page};
		var pcNum = ${pcNum};
		var dataCount = ${dataCount};
		
		if(page < total_page) {
			page++;
			listPage(page, pcNum);
		}
		
	});
});

</script>


		<div class="content-wrapper">
            <div class="inner-container container">
                <div class="row">
                    <div class="section-header col-md-12">
                        <h2>중고거래</h2>
                        <span>망설이지 말고 고! 이웃과 거래를 시작해보세요!</span>
                    </div>
                
                </div>
                <c:if test="${! empty sessionScope.member}">
	                <div style="width: 120px; float: right;">
	                
					  	<c:if test="${! empty listMemberAddr || memAddrCount == 2}">
							<select name="areaNum" id="selectArea" class="form-select" id="inputGroupSelect01"> 
						  		<c:forEach var="vo" items="${listMemberAddr}">
						    			<option value="${vo.areaNum}" data-maLat='${vo.maLat}' data-maLon='${vo.maLon}'>${vo.area3}</option>
						    	</c:forEach>
							<!-- 
						    	<c:if test="${empty listMemberAddr || memAddrCount < 2}">
						    		<option value="${pageContext.request.contextPath}/member/address">내 동네 설정하기</option>
						    	</c:if>
						    -->
						  	</select>                
						</c:if>
	                	
	                	<c:if test="${empty listMemberAddr || memAddrCount < 2}">
					    	<a href="${pageContext.request.contextPath}/member/address" style="border: 1px solid medium;">내 동네 설정하기</a>
					    </c:if>
	                </div>
	                <div>
						<input type="hidden" name="maLat" id="maLat" value="${vo.maLat}">
						<input type="hidden" name="maLon" id="maLon" value="${vo.maLon}">
					</div>
                </c:if>
              	
              
              
                <div class="tab-content pt-2" id="nav-tabContent">
			                <div class="container px-5">
			                	<!-- 탭 영역 -->
			                	<!--
				                <ul class="nav nav-tabs" id="myTab" role="tablist" style="clear: both;">
									<li class="nav-item" role="presentation">
										<button class="nav-link" id="tab-0" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="0" aria-selected="true" data-tab="0">전체</button>
									</li>
									<li class="nav-item" role="presentation">
										<button class="nav-link" id="tab-1" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="1" aria-selected="true" data-tab="1">인기매물</button>
									</li>
								</ul>
								-->
								 
			                    <div class="container">
								    <div class="d-flex justify-content-center py-3">
										<ul class="nav nav-tabs" id="myTab" role="tablist">
											
											<li class="nav-item" role="presentation">
												<button class="nav-link" id="tab-0" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="0" aria-selected="true" data-tab="0">ALL</button>
											</li>
											<c:forEach var="vo" items="${listCategory}" varStatus="status">
												<li class="nav-item" role="presentation">
													<button class="nav-link" id="tab-${status.count}" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="${status.count}" aria-selected="true" data-tab="${vo.pcNum}">${vo.pcName}</button>
												</li>
											</c:forEach>
										</ul>
								    </div>
								    								    
								    
				              	<div class="tab-content pt-2" id="nav-tabContent">
									<div class="tab-pane fade show active mt-3" id="nav-content" role="tabpanel" aria-labelledby="nav-tab-content">      
				                    
				                    <div class="row gx-5 more-list">
					                    <!-- list div 반복 영역 -->
					                    <c:forEach var="dto" items="${list}">
					                        <div class="col-lg-4 mb-5">
					                            <div class="card h-100 shadow border-0">
					                                <img class="card-img-top" src="https://dummyimage.com/600x350/ced4da/6c757d" alt="" />
					                                <div class="card-body p-4">
					                                    <a class="text-decoration-none link-dark stretched-link" href="${articleUrl}&pNum=${dto.pNum}"><div class="h5 card-title mb-3">${dto.pSubject}</div></a>
					                                    <p class="card-text mb-0">${dto.area3}</p>
					                                </div>
					                                <div class="card-footer p-4 pt-0 bg-transparent border-top-0">
					                                    <div class="d-flex align-items-end justify-content-between">
					                                        <div class="d-flex align-items-center">
					                                            <img class="rounded-circle me-3" src="https://dummyimage.com/40x40/ced4da/6c757d" alt="..." />
					                                            <div class="small">
					                                                <div class="fw-bold">${dto.userNickName}</div>
					                                                <div class="text-muted">${dto.pRegDate} &middot; 만약 끌올하면 끌올 몇분전이나 몇회나 표시</div>
					                                            </div>
					                                        </div>
					                                    </div>
					                                </div>
					                            </div>
					                        </div>
					                    </c:forEach>
					                    <!-- list div 반복 영역 -->
					                </div>
			                	</div>
			                </div>
                        
                        <!-- 글쓰기 버튼 -->
                        <div class="writeBtnTop">
                    		<a style="color:orange" href="${pageContext.request.contextPath}/product/write"><i class="fa fa-plus-circle jb fa-4x" aria-hidden="true"></i></a>
                    	</div>
                        
                        <!-- 
                        <div class="page-box">
							${dataCount == 0 ? "등록된 매물이 없습니다." : paging}
						</div>
                         -->
                         
						
							
						<div class="more-box load-more">
							<a class="more btn btn-outline-success" style="text-decoration: none;">매물 더보기</a>
						</div>
						
                    </div>
                </div>
                </div>
            </div>
		</div>