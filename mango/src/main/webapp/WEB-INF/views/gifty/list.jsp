<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<style type="text/css">
.body-container {
	max-width: 800px;
}

</style>
<script type="text/javascript">
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

var current_page = 2;

function listPage(page) {
	var url = "${pageContext.request.contextPath}/gifty/morelist";
	var query = "pageNo=" + page;
	
	var fn = function(data) {
		
		printGifty2(data);
		// console.log(data);
	};
	ajaxFun(url, "get", query, "json", fn);
}


function printGifty(data) {
	/*
	var start = $("#listGifty h2").length;
	
	if(data.list.length < 6) {
		$(".load-more").remove();
	} else {
		
	}
	*/
	/*
	var dataCount = data.dataCount;
	var pageNo = data.pageNo;
	var total_page = data.total_page;
	
	*/
	/*
	$(".gifty-count").attr("data-pageNo", pageNo);
	$(".gifty-count").attr("data-totalPage", total_page);
	
	$("#listGifty").show();
	// $(".gifty-count").html("게시물" + dataCount + "개");
	
	$(".load-more").hide();
	if(dataCount == 0) {
		$(".morelist").empty();
		return;
	}
	
	if(pageNo < total_page) {
		$(".load-more").show();
	}
	*/
	/*
	var out = "";
	
	for(var idx = 0; idx < data.list.length; idx++) {
		var gnum = data.list[idx].gNum;
		var userId = data.list[idx].userId;
		var userNickName = data.list[idx].userNickName;
		var gSubject = data.list[idx].gSubject;
		var gContent = data.list[idx].gContent;
		var gRegdate = data.list[idx].gRegdate;
		var gExpdate = data.list[idx].gExpdate;
		var gcNum = data.list[idx].gcNum;
		
	}
	
		out += "<div class='col-md-4 project-item mix" + gcNum + "'>";
		out += "	<div class='project-thumb'>";
		out += "    	<img src='images/projects/project_1.jpg' alt=''>";
		out += "		<div class='overlay-b'>";
		out += "		<div class='overlay-inner'>";
		out += "			<a href='images/projects/project_1.jpg' class='fancybox fa fa-expand' title='Project Title Here'></a>";
		out += "    	</div>";
		out += "		</div>";
		out += "	</div>";
		out += "    <div class='box-content project-detail'>"; 
		out += "		<h2><a href='${pageContext.request.contextPath}/gifty/article?page="+current_page+"&gNum="+gnum+"'>"+gSubject+"</a></h2>";
		out += "		<p>" +gContent+ "</p>";
		out += "	</div>";
		out += "</div>";
		
	
	$(".morelist").append(out);
*/
	
}

function printGifty2(data) {
	var dataCount = data.dataCount;
	var pageNo = data.pageNo;
	var total_page = data.total_page;
	
	$("#listGifty").show();
	// $(".gifty-count").html("게시물" + dataCount + "개");
	
	$(".load-more").hide();
	if(dataCount == 0) {
		$(".morelist").empty();
		return;
	}
	
	if(pageNo < total_page) {
		$(".load-more").show();
	}
	
	var out = "";
	
	for(var idx = 0; idx < data.list.length; idx++) {
		var gnum = data.list[idx].gNum;
		var userId = data.list[idx].userId;
		var userNickName = data.list[idx].userNickName;
		var gSubject = data.list[idx].gSubject;
		var gContent = data.list[idx].gContent;
		var gRegdate = data.list[idx].gRegdate;
		var gExpdate = data.list[idx].gExpdate;
		var gcNum = data.list[idx].gcNum;
		
	
	
	out += "<div class='col-lg-4 mb-5'>";
	out += "	<div class='card h-100 shadow border-0'>";
	out += "		<img class='card-img-top' src='' alt='' />";
	out += "		<div class='card-body p-4'>";
	out += "			<a class='text-decoration-none link-dark stretched-link' href='${pageContext.request.contextPath}/gifty/article?page="+current_page+"&gNum="+gnum+"'>";
	out += "			<div class='h5 card-title mb-3'>"+gSubject+"</div></a>";
	out += "		</div>";
	out += "	<div class='card-footer p-4 pt-0 bg-transparent border-top-0'>";
	out += "		<div class='d-flex align-items-end justify-content-between'>";
	out += "			<div class='d-flex align-items-center'>";
	out += "				<img class='rounded-circle me-3' src='' alt='...' />";
	out += "			<div class='small'>";
	out += "				<div class='fw-bold'>"+userNickName+"</div>";
	out += "			<div class='text-muted'>"+gRegdate+"&middot; 만약끌올하면 끌올 몇분전이나 몇회나 표시</div>";
	out += "			</div>";
	out += "			</div>";
	out += "			</div>";
	out += "		</div>";
	out += "	</div>";
	out += "</div>";
	
	}
	
	$(".morelist").append(out);

}

$(function(){
	$(".load-more .more").click(function(){
		
		listPage(2);
		/*
		var pageNo = $(".gifty-count").attr("data-pageNo");
		var total_page = $(".gifty-count").attr("data-totalPage");
		*/
		
		/*
		
		if(pageNo < total_page) {
			pageNo++;
			listPage(pageNo);
		}
		*/
	});
});

$(function(){
	$("#tab-${group}").addClass("active");
	
    $("button[role='tab']").on("click", function(e){
		var tab = $(this).attr("data-tab");
	
		var url="${pageContext.request.contextPath}/gifty/list?group="+tab;	
		location.href=url;
    });
});

</script>


        <div class="content-wrapper">
            <div class="inner-container container">
                <div class="row">
                    <div class="section-header col-md-12">
                        <h2>기프티콘 거래</h2>
                        <span>안쓰는 기프티콘 팔아요</span>
                    </div> <!-- /.section-header -->
                    <div class="p-1 text-end">
                    	<button type="button" onclick="location.href='${pageContext.request.contextPath}/gifty/write';"  >기프티콘 등록</button>
                    </div>
       
                </div> <!-- /.row -->
               
             <div class="tab-content pt-2" id="nav-tabContent">
                    <!--        
                        <section class="py-5">-->
			    <div class="container px-5">
                <!--
                <ul class="nav nav-tabs" id="myTab" role="tablist">
					<li class="nav-item" role="presentation">
						<button class="nav-link" id="tab-0" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="0" aria-selected="true" data-tab="0">전체</button>
					</li>
					<li class="nav-item" role="presentation">
						<button class="nav-link" id="tab-1" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="1" aria-selected="true" data-tab="1">인기매물</button>
					</li>
				</ul>
				  -->
				 <ul class="nav nav-tabs" id="myTab" role="tablist">
					<c:forEach var="vo" items="${listGcategory}" varStatus="status">
						<li class="nav-item" role="presentation">
							<button class="nav-link" id="tab-${status.count}" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="${status.count}" aria-selected="true" data-tab="${vo.gcNum}">${vo.gcName}</button>
						</li>
					</c:forEach>
					
				</ul>
                 <!-- 
                <div class="projects-holder-3">
                    <div class="filter-categories">
                        <ul class="project-filter">
                            <li class="filter" data-filter="all"><span>전체보기</span></li>
                         	<c:forEach var="vo" items="${listGcategory}">
                            	<li class="filter" data-filter="${vo.gcNum}"><span>${vo.gcName}</span></li>
                         	</c:forEach>
                        </ul>
                    </div>
				  -->
				 <!-- 
				 <div class="d-flex justify-content-center py-3 projects-holder-3">
					 <div class="filter-categories">
						<ul class="project-filter">
							 <li class="filter" data-filter="all"><span>전체보기</span></li>
	                         	<c:forEach var="vo" items="${listGcategory}">
	                            	<li class="filter" data-filter="${vo.gcNum}"><span>${vo.gcName}</span></li>
	                         	</c:forEach>
						</ul>
					</div>
				</div>
				 -->
				 
		<div class="tab-content pt-2" id="nav-tabContent">
			<div class="tab-pane fade show active mt-3" id="nav-content" role="tabpanel" aria-labelledby="nav-tab-content">
				<div class="row gx-5 morelist">
					<!-- list div 반복 영역 -->
					<c:forEach var="dto" items="${list}">
						<div class="col-lg-4 mb-5">
							<div class="card h-100 shadow border-0">
								<img class="card-img-top"
									src="https://dummyimage.com/600x350/ced4da/6c757d" alt="" />
								<div class="card-body p-4">
									<a class="text-decoration-none link-dark stretched-link"
										href="${articleUrl}&gNum=${dto.gNum}">
										<div class="h5 card-title mb-3">${dto.gSubject}</div></a>
								</div>
								<div class="card-footer p-4 pt-0 bg-transparent border-top-0">
									<div class="d-flex align-items-end justify-content-between">
										<div class="d-flex align-items-center">
											<img class="rounded-circle me-3"
												src="https://dummyimage.com/40x40/ced4da/6c757d" alt="..." />
											<div class="small">
												<div class="fw-bold">${dto.userNickName}</div>
												<div class="text-muted">${dto.gRegdate}&middot; 만약
													끌올하면 끌올 몇분전이나 몇회나 표시</div>
											</div>
											
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>


				<div>
                    <a style="color:orange" href="${pageContext.request.contextPath}/gifty/write"><i class="fa fa-plus-circle jb fa-4x" aria-hidden="true"></i></a>
                </div>



					<!-- 
                    <div class="projects-holder" id="listGifty">
                        <div class="row morelist">
                       	<c:forEach var="dto" items="${list}">
                        	<div class="col-md-4 project-item mix ${dto.gcNum}">
                                <div class="project-thumb">
                                    <img src="" alt="">
                                    <div class="overlay-b">
                                        <div class="overlay-inner">
                                            <a href="" class="fancybox fa fa-expand" title="Project Title Here"></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-content project-detail">
                               -->      
                                    <!--  <h2><a href="${pageContext.request.contextPath}/gifty/article?gNum=${dto.gNum}">${dto.gSubject}</a></h2>  -->
                                 <!--    <h2><a href="${articleUrl}&gNum=${dto.gNum}">${dto.gSubject}</a></h2> 
                                    <span>${dto.gContent}</span>
                                </div>
                            </div>
                        </c:forEach>
                        </div>  --> 
                        
                        
                        <div class="load-more">
                            <a href="javascript:void(0)" class="more btn btn-light">Load More</a>
                        </div>  
                    </div> <!-- /.projects-holder -->
                </div> <!-- /.projects-holder-2 -->
            </div> <!-- /.inner-content -->
        </div> <!-- /.content-wrapper -->
	</div>
	</div>
        
       
