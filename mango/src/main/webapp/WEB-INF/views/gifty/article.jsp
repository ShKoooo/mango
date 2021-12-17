<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 기프티콘 자세히 보기 </h3>
		</div>
		
		<div class="content-wrapper">
            <div class="inner-container container">
                <div class="row">
                    <div class="section-header col-md-12">
                        <h2>기프티콘 거래</h2>
                        <span>기프티콘 거래로 아름다운 세상 만들어요~</span>
                    </div> <!-- /.section-header -->
                </div> <!-- /.row -->
                <div class="project-detail row">
                    <div class="project-infos col-md-12">
                        <div class="box-content">
                            <h2 class="project-title">${dto.gSubject}</h2>
                            <span class="project-subtitle">${dto.userNickName}</span>
                            <p class="editor">${dto.gContent}</p>
                            <ul class="project-meta">
                            	 <li><i class="fa fa-calendar-o"></i>${dto.gPrice}</li>
                                <li><i class="fa fa-calendar-o"></i>${dto.gExpdate}</li>
                                <li><i class="fa fa-folder-open"></i><a href="">거래하기</a></li>
                                <li><i class="fa fa-envelope-o"></i><a href="">신고하기</a></li>
                            </ul>
                        </div>
                        
                        <div class="box-content">
                        	<c:choose>
								<c:when test="${sessionScope.member.userId==dto.userId}">
									<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/gifty/update?gNum=${dto.gNum}&page=${page}';">수정</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-light" disabled="disabled">수정</button>
								</c:otherwise>
							</c:choose>
                        </div>
                    </div> 
                </div> 
            </div>
        </div> 
	</div>
</div>