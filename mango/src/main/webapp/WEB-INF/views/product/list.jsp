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
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">

<script type="text/javascript">

$(function(){
	$("#tab-${group}").addClass("active");
	
    $("button[role='tab']").on("click", function(e){
		var tab = $(this).attr("data-tab");
		var url="${pageContext.request.contextPath}/product/list?group="+tab;	
		location.href=url;
    });
});

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
		}
	});
}

var current_page = 2;
function listPage(page) {
	var url = "${pageContext.request.contextPath}/product/morelist";
	var query = "page=" + page;
	
	var fn = function(data) {
		printGuest(data);
	};
	ajaxFun(url, "get", query, "json", fn);
}

function printGuest(data) {
//	var uid = "${sessionScope.member.userId}";
//	var permission = "${sessionScope.member.membership > 50 ? 'true':'false'}";
	
	var dataCount = data.dataCount;
	var page = data.page;
	var total_page = data.total_page;
	
//	$(".guest-count").attr("data-page", page);
//	$(".guest-count").attr("data-totalPage", total_page);
	
	$(".more-list").show();
//	$(".guest-count").html("총 데이터 " + dataCount + "개"); // 나중에 지우기
	
	$(".more-box").hide();
	if(dataCount == 0) {
		$(".more-list").empty();
		return;
	}
	
	if(page < total_page) {
		$(".more-box").show();
	}
	
	var out = "";
	
	for(var idx = 0; idx < data.list.length; idx++) {
		var pcNum = data.list[idx].pcNum;
		var pNum = data.list[idx].pNum;
		var pSubject = data.list[idx].pSubject;
		var pWishCount = data.list[idx].pWishCount;
		var maAddr1 = data.list[idx].maAddr1 || '주소'; // 바꿀 것
		var articleUrl = data.list[idx].articleUrl;
		
	}
/*	
		out += "<div class='col-md-4 project-item mix " + pcNum + "'>";
		out += "    <div class='project-thumb'>";
		out += "    	<img src='' alt=''>";
		out += "		<div class='overlay-b'>";
		out += "			<div class='overlay-inner'>";
		out += "				<a href='images/projects/project_1.jpg' class='fancybox fa fa-expand' title='Project Title Here'></a>";	
		out += "			</div>";
		out += "		</div>";
		out += "	</div>";
		out += "	<div class='box-content project-detail'>";
		out += "		<h2><a href='" + articleUrl + "&pNum=" + pNum + "'>" + pSubject + "</a></h2>";
		out += "		<p>" + maAddr1 + "</p>";
		out += "		<p>채팅현황(채팅몇건햇는지(유저당1))</p>";
		out += "		<p>" + pWishCount + "</p>";
		out += "	</div>";
		out += "</div>"
	
	 $(".more-list").append(out);
*/		
}

$(function(){
	$(".load-more").click(function(){
		// listPage(2);
	});
});

/*
$(function(){
	$("#selectArea").change(function(){
		if($(this).val()=="0") {
			$("#maLat").val("0");
			$("#maLon").val("0");
			return false;
		}
		
		var opt = $("#selectArea option:selected");
	
		$("#maLat").val(opt.attr("data-maLat"));
		$("#maLon").val(opt.attr("data-maLon"));
	});
});
*/


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
		
		var url = "${pageContext.request.contextPath}/product/list2"
		var query = {maLat: $("#maLat").val(), maLon: $("#maLon").val()};
		
		var fn = function(data){
			printJson2(data);
		};
		ajaxFun(url, "get", query, "json", fn);
	}

});

function printJson2(data) {
	var out = "";
	
	console.log(data);
	
	for(var i = 0; i<data.length; i++) {
		var pcNum = data[i].pcNum;
		var pNum = data[i].pNum;
		var pSubject = data[i].pSubject;
		var pRegDate = data[i].pRegDate;
		var area3 = data[i].area3 || '주소';
		var pWishCount = data[i].pWishCount;
		var userNickName = data[i].userNickName;
		
		out += "<div class='col-lg-4 mb-5'>";
		out += "    <div class='card h-100 shadow border-0'>";
		out += "    	<img class='card-img-top' src='' alt='' />";
		out += "		<div class='card-body p-4'>";
		out += "			<a class='text-decoration-none link-dark stretched-link' href='${articleUrl}&pNum="+pNum+"'><div class='h5 card-title mb-3'>"+pSubject+"</div></a>";
		out += "			<p class='card-text mb-0'>"+area3+"</p>";	
		out += "		</div>";
		out += "		<div class='card-footer p-4 pt-0 bg-transparent border-top-0'>";
		out += "			<div class='d-flex align-items-end justify-content-between'>";
		out += "				<div class='d-flex align-items-center'>";
		out += "					<img class='rounded-circle me-3' src='' alt='' />";
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
	
	$(".more-list").html(out);
}



$(function(){
	if($(".cate").attr('data-pcNum')) {
		selectCategoryList();
	}
	
	$(".cate").click(function(){
		selectCategoryList();
	});

	
	$("#selectArea").change( function() {
		// 주소 바꿨을때 좌측 좌표 영역에 값 셋팅
		if($(this).val()=="0") {
			$("#maLat").val("0");
			$("#maLon").val("0");
			return false;
		}
		selectAreaList();
		
	});


	function selectCategoryList() {
		// 주소 바꿨을때 좌측 좌표 영역에 값 셋팅
		$(".cate").attr('data-pcNum');
		
		var url = "${pageContext.request.contextPath}/product/list"
		var query = {pcNum: $(".cate").attr('data-pcNum')};
		
		var fn = function(data){
			printJson2(data);
		};
		

		ajaxFun(url, "get", query, "json");
	}

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
						<select name="areaNum" id="selectArea" class="form-select" id="inputGroupSelect01"> <!-- onchange="if(this.value) location.href=(this.value);" -->
					  		<!-- 
					  			<option selected value="0">동네 선택</option>
					  		 -->
					  		
					  		<c:forEach var="vo" items="${listMemberAddr}">
					    			<option value="${vo.areaNum}" data-maLat='${vo.maLat}' data-maLon='${vo.maLon}'>${vo.area3}</option>
					    	</c:forEach>
				
					    	<c:if test="${empty listMemberAddr}">
					    		<option value="${pageContext.request.contextPath}/member/address">내 동네 설정하기</option>
					    	</c:if>
					    	<c:if test="${memAddrCount < 2}"> <!-- 주소 하나만 등록한 사람들이 안나오고있으니 고치자 -->
					    		<option>내 동네 설정하기</option>
					    	</c:if>
					  	</select>                
	                </div>
	                <div>
						<input type="text" name="maLat" id="maLat" value="${vo.maLat}">
						<input type="text" name="maLon" id="maLon" value="${vo.maLon}">
					</div>
                </c:if>
               
 
                  
                <!-- 
                	<c:forEach var="dto" items="${groupList}" varStatus="status">
						<li class="nav-item" role="presentation">
							<button class="nav-link" id="tab-${status.count}" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="${status.count}" aria-selected="true" data-tab="${dto.categoryNum}">${dto.category}</button>
						</li>
					</c:forEach>
                 -->
                
                <!-- 탭 영역 
                <ul class="nav nav-tabs" id="myTab" role="tablist" style="clear: both;">
					<li class="nav-item" role="presentation">
						<button class="nav-link" id="tab-0" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="0" aria-selected="true" data-tab="0">전체</button>
					</li>
					<li class="nav-item" role="presentation">
						<button class="nav-link" id="tab-1" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="1" aria-selected="true" data-tab="1">인기매물</button>
					</li>
				</ul> -->
                
                <div class="tab-content pt-2" id="nav-tabContent">
                    <!--        
                        <section class="py-5">-->
			                <div class="container px-5">
			                	
			                	<!-- 탭 영역 -->
				                <ul class="nav nav-tabs" id="myTab" role="tablist" style="clear: both;">
									<li class="nav-item" role="presentation">
										<button class="nav-link" id="tab-0" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="0" aria-selected="true" data-tab="0">전체</button>
									</li>
									<li class="nav-item" role="presentation">
										<button class="nav-link" id="tab-1" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="1" aria-selected="true" data-tab="1">인기매물</button>
									</li>
								</ul>
								 
			                    <div class="container">
									
									<!-- 카테고리 -->
								    <div class="d-flex justify-content-center py-3">
								      <ul id="categorySelect" class="nav nav-pills">
								      	<li class="nav-item" value="0"><a href="" class="nav-link active cate" aria-current="page" data-pcNum="0">ALL</a></li>
								        <c:forEach var="vo" items="${listCategory}">
								    		<li class="nav-item"><a href="" class="nav-link cate" data-pcNum="${vo.pcNum}">${vo.pcName}</a></li> 
								      	</c:forEach>
								      </ul>
								    </div>
								    
								    
				                    <div class="row gx-5">
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
					                                            <!--  
					                                            <div class="small">
					                                                <div class="fw-bold">${dto.pWishCount}</div>
					                                                <div class="text-muted">쪽지</div>
					                                            </div>
					                                            -->
					                                        </div>
					                                    </div>
					                                </div>
					                            </div>
					                        </div>
					                    </c:forEach>
					                    <!-- list div 반복 영역 -->
					                    
					                    <!--  
					                    <div class="text-end mb-5 mb-xl-0">
					                        <a class="text-decoration-none" href="#!">
					                            More stories
					                            <i class="bi bi-arrow-right"></i>
					                        </a>
					                    </div>
					                    --> 
					                </div>
			                	</div>
			                </div>
			      <!--      </section>  --> 
                        
                        <div>
                    		<a style="color:orange" href="${pageContext.request.contextPath}/product/write"><i class="fa fa-plus-circle jb fa-4x" aria-hidden="true"></i></a>
                    	</div>
                        
                        
						
						
						<c:if test="${dataCount > 6}">	
							<div class="more-box load-more">
								<a class="more load-more">매물 더보기</a>
							</div>
						</c:if>	
						<!-- 
                        <div class="load-more">
                            <a href="javascript:void(0)" class="load-more">매물 더보기</a>
                        </div>
                         -->
                    </div>
                </div>
                </div>
            </div>
		</div>