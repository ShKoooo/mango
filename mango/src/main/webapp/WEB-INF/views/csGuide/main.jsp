<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.title {
	text-align: center;
	margin: 30px;
}

.title2 {
	margin-top: 200px;
	margin-bottom: 100px;
}

.content-body {
	padding: 0;
	margin: 0;
	outline: none;
	
	display: block;
}

.detail {
	display: inline-block;
	width: 50%;
	vertical-align: middle;
	margin-left: 100px;
	
}

.detail2 {
	display: inline-block;
	width: 50%;
	vertical-align: middle;
	margin-right: 100px;
	
}

.detail3 {
	width: 105%;
	vertical-align: middle;
}

.two-body {
	display: flex;
	flex-wrap: wrap;
	margin-bottom: 150px;
	
}

.backMain {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/main.png");
	background-repeat : no-repeat;
    background-size : contain;
}

.img-first {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/location.png");
	background-repeat : no-repeat;
    background-size : contain;
    min-height: 254px;
    width: 340px;
    display: inline-block;
    vertical-align: middle;
}

.img-two {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/11.png");
	background-repeat : no-repeat;
    background-size : contain;
    min-height: 254px;
    width: 340px;
    display: inline-block;
    vertical-align: middle;
}

.img-three {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/direct.png");
	background-repeat : no-repeat;
    background-size : contain;
    min-height: 254px;
    width: 340px;
    display: inline-block;
    vertical-align: middle;
}

.img-four {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/manner.png");
	background-repeat : no-repeat;
    background-size : contain;
    min-height: 254px;
    width: 340px;
    display: inline-block;
    vertical-align: middle;
}

.img-01 {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/01.png");
	background-repeat : no-repeat;
    background-size : contain;
    min-height: 254px;
    width: 340px;
    display: inline-block;
    vertical-align: middle;
}

.img-02 {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/02.png");
	background-repeat : no-repeat;
    background-size : contain;
    min-height: 254px;
    width: 340px;
    display: inline-block;
    vertical-align: middle;
}

.img-03 {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/03.png");
	background-repeat : no-repeat;
    background-size : contain;
    min-height: 254px;
    width: 340px;
    display: inline-block;
    vertical-align: middle;
}

.img-04 {
	background-image: url("${pageContext.request.contextPath}/resources/images/csGuide/04.png");
	background-repeat : no-repeat;
    background-size : contain;
    min-height: 254px;
    width: 340px;
    display: inline-block;
    vertical-align: middle;
}
</style>

<div class="container">
	<h1 class="title">당신 근처의 믿을 수 있는 중고거래</h1>
	<p style="font-size: 23px; text-align: center;">망고마켓과 함께 가깝고 따뜻한 거래문화를 만들어요.</p>
	<div class="body-container backMain"></div>
</div>

<div class="content-body">
	<section style="width: 980px; margin: 0 auto;">
		<h1>망고마켓은 신뢰할 수 있어요</h1>
		
		<section style="margin-top: 50px;">
			<div class="img-first"></div>
			<div class="detail">
				<h3>동네 인증한 사람만 거래 해요</h3>
				<p>
					망고마켓에서 거래하려면 동네인증이 필요해요. 동네인증은 설정한 동네에 있어야만 할 수 있어요. GPS를 이용하여 우리 동네를 인증한 진짜 이웃들과 거래하세요. 거래하려는 상대방의 인증 횟수를 보면 얼마나 자주 이 동네에서 사용했는지 알 수 있어요.
		        </p>
			</div>
		</section>
		
		<section style="margin-top: 50px;">
			<div class="detail2">
				<h3>1:1 망고채팅으로 대화해요</h3>
				<p>      
          			망고마켓 내의 채팅을 통해 거래하는 게 가장 안전해요. 개인정보 공유 없이도 쉽고 편하게 거래할 수 있어요. 1:1 채팅으로 약속을 잡고 만나서 거래하세요. 채팅 내의 거래약속 알림을 설정하면 약속 시간 전에 알림을 받을 수 있어요.
		        </p>
			</div>
			<div class="img-two"></div>
		</section>
		
		<section style="margin-top: 50px;">
			<div class="img-three"></div>
			<div class="detail">
				<h3>당신 근처에서 만나서 거래해요</h3>
				<p>
					중고거래 사기의 대부분은 택배거래에서 발생한다는 사실, 알고 계셨나요? 망고마켓은 택배거래보다 직거래를 권장해요. 만나서 거래할 때는 누구나 찾기 쉽고 안전한 공공장소가 좋아요. 망고마켓에서 가까운 이웃과 따뜻하게 거래하세요.
		        </p>
			</div>
		</section>
		
		<section style="margin-top: 50px;">
			<div class="detail2">
				<h3>매너온도로 따뜻함을 확인해요</h3>
				<p>      
          			거래하기 전 상대방의 매너온도를 확인하세요. 36.5도에서 시작하는 매너온도는 망고마켓 내의 다양한 활동을 종합하여 정해져요. 따뜻한 거래를 많이 한 이웃은 매너온도도 높답니다. 매너온도가 낮은 사용자를 발견한다면, 프로필의 가입일, 인증횟수, 재거래희망률, 후기 등을 꼼꼼히 확인해보세요.
		        </p>
			</div>
			<div class="img-four"></div>
		</section>
		
		<h1 class="title2">지금 이 순간에도 당신을 위해 노력하고 있어요!</h1>
		
		<div class="two-body">
			<section style="max-width: 330px; margin-right: 200px;">
				<div class="img-01"></div>
				<div class="detail3">
					<h3 style="display: block;">사기꾼은 실시간으로 제재해요</h3>
					<p style="display: block;">
				            망고마켓은 사용자 보호를 위해 다양한 사기 사례를 분석하고 있어요. 사기 등의 행위가 발견되는 즉시 해당 사용자의 서비스 이용을 제한해요. 사기를 저지른 사용자가 탈퇴하거나 재가입해도 서비스를 이용할 수 없으니 안심하세요.
		          	</p>
				</div>
			</section>
			
			<section style="max-width: 330px;">
				<div class="img-02"></div>
				<div class="detail3">
					<h3 style="display: block;">채팅 메시지로 미리 알려줘요</h3>
					<p style="display: block;">
				            망고마켓 채팅을 이용하면 다양한 안내 및 경고 메시지들을 받을 수 있어요. 상대방의 사기 이력이나 유사한 사기 사례를 주의하라고 알려주기도 하고, 사기가 진행되지 않도록 대화 도중 채팅을 막기도 해요. 지금 이 순간에도 사기 사례를 분석하여 고도화하고 있어요.
		          	</p>
				</div>
			</section>
			
			<section style="max-width: 330px; margin-right: 200px;">
				<div class="img-03"></div>
				<div class="detail3">
					<h3 style="display: block;">게시글을 분석해요</h3>
					<p style="display: block;">
				            머신러닝 기술을 이용해 게시글을 분석해요. 판매금지품목이나 전문판매업자의 판매 게시글, 허위 게시글, 광고 게시글, 중복 게시글 등은 노출되지 않아요. 신뢰할 수 있는 우리 동네 마켓을 만들기 위해 노력하고 있어요.
		          	</p>
				</div>
			</section>
			
			<section style="max-width: 330px;">
				<div class="img-04"></div>
				<div class="detail3">
					<h3 style="display: block;">수사기관과 함께 해요</h3>
					<p style="display: block;">
				            거래 사기 이력이 있는 사용자는 서비스를 사용할 수 없어요. 사기 등 거래 관련 문제는 빠르게 해결될 수 있도록 망고마켓 팀도 함께 노력해요. 신고를 권장하고, 수사기관의 요청에 적극 협조하고 있어요. 서울지방경찰청과 사기예방캠페인도 함께 진행했어요.
		          	</p>
				</div>
			</section>
		</div>
		
	</section>
</div>