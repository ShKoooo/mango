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

<div class="content-wrapper">
	<div class="body-container">
		<div class="inner-container container">
	        <div class="row justify-content-md-center mt-5">
	            <div class="col-md-8">
	                <div class="border bg-light mt-5 p-4">
	                    <h4 class="text-center fw-bold">${title}</h4>
	                    <hr class="mt-4">
	                       
		                <div class="d-grid p-3">
							<p class="text-center">${message}</p>
		                </div>
	                       
	                    <c:if test="${empty goBack}">
		                    <div class="d-grid">
		                        <button type="button" class="btn btn-lg btn-primary" onclick="location.href='${pageContext.request.contextPath}/';">메인화면 <i class="bi bi-check2"></i> </button>
		                    </div>
	                    </c:if>
	                    <c:if test="${not empty goBack && fin ne 'true'}">
		                    <div class="d-grid">
		                        <button type="button" class="btn btn-lg btn-primary" onclick="location.href='${pageContext.request.contextPath}${goBack}';">
		                        	뒤로가기 <i class="icofont-arrow-left"></i>
		                        </button>
		                    </div>
	                    </c:if>
	                    <c:if test="${not empty goBack && fin eq 'true'}">
		                    <div class="d-grid">
		                        <button type="button" class="btn btn-lg btn-primary" onclick="location.href='${pageContext.request.contextPath}${goBack}';">
		                        	확인 <i class="bi bi-check2"></i>
		                        </button>
		                    </div>
	                    </c:if>
	                </div>
	
	            </div>
	        </div>
		</div>

	        
	</div>
</div>