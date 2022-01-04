<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.section-header {
  margin-top: 60px;
  margin-bottom: 60px;
}

.back {
	width : auto;
	left: -1%;
	position: fixed;
	margin:5px;
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

var current_page = "${page}";
var total_page = "${total_page}";

function listPage(page, group) {
	var url = "${pageContext.request.contextPath}/gifty/morelist";
	var query = "page=" + page + "&group=" + group;
	
	var fn = function(data) {
		printGifty(data);
	};
	ajaxFun(url, "get", query, "json", fn);
}

function printGifty(data) {
	var dataCount = data.dataCount;
	current_page = data.page;
	total_page = data.total_page;
	var group = data.group;
	var list = data.list;

	if(dataCount == 0) {
		$(".morelist").empty();
		$(".load-more .more").hide();
		return false;
	}
	
		$(".load-more .more").show();
	if(parseInt(total_page) <= parseInt(current_page)) {
		$(".load-more .more").hide();
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
		var gImgSaveFileName = data.list[idx].gImgSaveFileName;
		var gWishCount = data.list[idx].gWishCount;
	
		out += "<div class='col-lg-4 mb-5'>";
		out += "	<div class='card h-100 shadow border-0'>";
		out += "		<img class='card-img-top' width='308px' height='200px' src="+gImgSaveFileName+" alt='' />";
		out += "		<div class='card-body p-4'>";
		out += "			<a class='text-decoration-none link-dark stretched-link' href='${pageContext.request.contextPath}/gifty/article?page="+current_page+"&gNum="+gnum+"'>";
		out += "			<div class='h5 card-title mb-3'>"+gSubject+"</div></a>";
		out += "		</div>";
		out += "	<div class='card-footer p-4 pt-0 bg-transparent border-top-0'>";
		out += "		<div class='d-flex align-items-end justify-content-between'>";
		out += "			<div class='d-flex align-items-center'>";
		out += "				<c:if test='${not empty dto.userImgSaveFileName}'>";
		out += "				<img class='img-fluid img-thumbnail img-viewer'";
		out += "				src='${pageContext.request.contextPath}/uploads/photo/"+gImgSaveFileName+"'/>";
		out += "				</c:if>";
		out += "				<c:if test='${empty dto.userImgSaveFileName}'>";
		out += "				<img class='img-fluid img-thumbnail img-viewer'>";
		out += "				</c:if>";
		out += "			<div class='small ms-2'>";
		out += "				<div class='fw-bold'>"+userNickName+"</div>";
		out += "				<div class='text-muted'>"+gRegdate+"&middot;<i class='icofont-heart-alt'></i>"+gWishCount+"</div>";
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
		if(current_page == "") return false;
		if(total_page == "") return false;
		
		current_page = parseInt(current_page);
		total_page = parseInt(total_page);
		if(total_page <= current_page) {
			return false;
		}
		
		current_page++;
		var group = "${group}";
		listPage(current_page, group);
	});
});

</script>

<div class="content-wrapper">
      	<div class="inner-container container">
                <div class="row">
                    <div class="section-header col-md-12">
                        <h2>인기매물보기</h2>
                        <span>요즘 떠오르는 핫템은?! pick 6!</span>
                    </div> <!-- /.section-header -->
                    <div>
                    	<a class="btn btn-outline-info back" style="text-decoration: none;" href="${pageContext.request.contextPath}/gifty/list">뒤로가기</a>
                    </div>
                </div> <!-- /.row -->
                <div class="col-6 text-center">
					<form class="row ps-5" name="searchForm" action="${pageContext.request.contextPath}/gifty/list?group=${group}" method="post">
						<div class="col-auto p-1">
							<select name="condition" class="form-select">
								<option value="all" ${condition=="all"?"selected='selected'":""}>제목+내용</option>
								<option value="userNickName" ${condition=="userNickName"?"selected='selected'":""}>작성자</option>
								<option value="gRegdate" ${condition=="gRegdate"?"selected='selected'":""}>등록일</option>
								<option value="gSubject" ${condition=="gSubject"?"selected='selected'":""}>제목</option>
								<option value="gContent" ${condition=="gContent"?"selected='selected'":""}>내용</option>
							</select>
						</div>
						<div class="col-auto p-1">
							<input type="text" name="keyword" value="${keyword}" class="form-control">
							<!-- <input type="hidden" name="rows" value="${rows}"> -->
						</div>
						<div class="col-auto p-1">
							<button type="button" class="btn btn-light" onclick="searchList()"> <i class="bi bi-search"></i> </button>
						</div>
					</form>
				</div>
				
			    <div class="container px-5 mt-4">
				<div class="row gx-5 morelist">
					<!-- list div 반복 영역 -->
					<c:forEach var="dto" items="${list}">
						<div class="col-lg-4 mb-5">
							<div class="card h-100 shadow border-0">
								<img class="card-img-top" width="308px" height="200px"
									src="${dto.gImgSaveFileName}" alt="" />
								<div class="card-body p-4">
									<a class="text-decoration-none link-dark stretched-link"
										href="${articleUrl}&gNum=${dto.gNum}">
										<div class="h5 card-title mb-3">${dto.gSubject}</div></a>
								</div>
								<div class="card-footer p-4 pt-0 bg-transparent border-top-0">
									<div class="d-flex align-items-end justify-content-between">
										<div class="d-flex align-items-center">
											<c:if test="${not empty dto.userImgSaveFileName}">
											<img class="img-fluid img-thumbnail img-viewer"
												src="${pageContext.request.contextPath}/uploads/photo/${dto.userImgSaveFileName}" alt="..." />
											</c:if>
											<c:if test="${empty dto.userImgSaveFileName}">
												<img
													class="img-fluid img-thumbnail img-viewer">
											</c:if>
											<div class="small ms-2">
												<div class="fw-bold">${dto.userNickName}</div>
												<div class="text-muted">${dto.gRegdate}&middot; <i class="icofont-heart-alt" style="color:red;"></i> ${dto.gWishCount}</div>
											</div>
											
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>

                  </div> <!-- /.projects-holder -->
                </div> <!-- /.projects-holder-2 -->
             </div><!-- /.inner-content -->
        <!-- /.content-wrapper -->

