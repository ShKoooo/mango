<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.section-header {
	margin-top : 60px;
	margin-bottom: 60px;
}

.ck.ck-editor {
	max-width: 97%;
}
.ck-editor__editable {
    min-height: 250px;
}
.ck-content .image>figcaption {
	min-height: 25px;
}

.write-form textarea {
	height: 150px;
	resize: none;
}

.write-form .img-viewer {
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

.table-light {
	text-align: center;
}

.img-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, 65px);
	grid-gap: 5px;
}

.img-grid .item {
    object-fit: cover; /* 가로세로 비율은 유지하면서 컨테이너에 꽉 차도록 설정 */
    width: 65px;
    height: 65px;
	cursor: pointer;
}

.img-box {
	max-width: 600px;

	box-sizing: border-box;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}
.img-box img {
	width: 65px; height: 65px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/ckeditor5/ckeditor.js"></script>

<script type="text/javascript">
function sendOk() {
	var f = document.qnaForm;
	var str;
	
	str = f.subject.value.trim();
	if(!str) {
		alert("제목을 입력하세요.");
		f.subject.focus();
		return;
	}
	
	/*
	str = f.content.value.trim();
	if(!str) {
		alert("내용을 입력하세요.");
		f.content.focus();
		return;
	}
	*/
	
	str = window.editor.getData().trim();
    if(! str) {
        alert("내용을 입력하세요. ");
        window.editor.focus();
        return;
    }
	f.content.value = str;;
	
	f.action = "${pageContext.request.contextPath}/village/qna/${mode}";
	f.submit();
	
}

/*
$(function() {
	var sel_files = [];
	
	$("body").on("click", ".write-form .img-add", function(evnet) {
		$("form[name=qnaForm] input[name=selectFile]").trigger("click"); 
	});
	
	$("form[name=qnaForm] input[name=selectFile]").change(function(){
		if(! this.files) {
			var dt = new DataTransfer();
			for(file of sel_files) {
				dt.items.add(file);
			}
			document.qnaForm.selectFile.files = dt.files;
			
			return false;
		}
		
		// 유사 배열 배열로 변환
		const fileArr = Array.from(this.files);
		
		fileArr.forEach((file, index) => {
			sel_files.push(file);
			
			const reader = new FileReader();
			const $img = $("<img>", {class:"item img-item"});
			$img.attr("data-filename", file.name);
			reader.onload = e => {
				$img.attr("src", e.target.result);
			};
			
			reader.readAsDataURL(file);
			
			$(".img-grid").append($img);
		});
		
		var dt = new DataTransfer();
		for(file of sel_files) {
			dt.items.add(file);
		}
		document.qnaForm.selectFile.files = dt.files;
		
	});
	$("body").on("click", ".write-form .img-item", function(event) {
		if(! confirm("선택한 파일을 삭제 하시겠습니까 ?")) {
			return false;
		}
		
		var filename = $(this).attr("data-filename");
		
	    for(var i = 0; i < sel_files.length; i++) {
	    	if(filename === sel_files[i].name){
	    		sel_files.splice(i, 1);
	    		break;
			}
	    }
	
		var dt = new DataTransfer();
		for(file of sel_files) {
			dt.items.add(file);
		}
		document.qnaForm.selectFile.files = dt.files;
		
		$(this).remove();
	});
	
	
}); */

</script>





<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2>동네 질문</h2>
				<span>Village QNA</span>
			</div>
		</div> <!-- row -->
	<div class="projects-holder">
		<div class="col-12">
			<div class="box-content">
				
				<div class="body-main">
				<form name="qnaForm" method="post" enctype="multipart/form-data">
					<table class="table write-form mt-5">
						<tr>
							<td class="table-light col-2" scope="row">제 목</td>
							<td>
								<input type="text" name="subject" class="form-control" value="${dto.subject}">
							</td>
						</tr>
						<tr>
							<td class="table-light col-2" scope="row">작성자명</td>
							<td>
								<p class="form-control-plaintext">${sessionScope.member.userNickName}</p>
							</td> 
						</tr>
						<tr>
							<td class="table-light  col-2" scope="row">내 용</td>
							<td>
								<div class="editor">${dto.content}</div>
								<input type="hidden" name="content">
								<!-- <textarea name="content" id="editor" class="form-control">${dto.content}</textarea> -->
							</td>
						</tr>
						<tr>
							<td class="table-light  col-2" scope="row">지역번호 (임시)</td>
							<td>
								<input type="text" name="areaNum" class="form-control" value="${dto.areaNum}">
							</td>
						</tr>
						<!-- 이미지첨부
						<tr>
							<td class="table-light col-2" scope="row">이미지 첨부</td>
							<td>
								<div class="img-grid"><img class="item img-add rounded" src="${pageContext.request.contextPath}/resources/images/add_photo.png"></div>
								<input type="file" name="selectFile" accept="image/*" multiple="multiple" style="display: none;" class=form-control>
							</td>
						</tr>
						-->
						
					</table>
				
					<table class="table table-borderless">
						<tr>
							<td class="text-center">
								<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}&nbsp;<i class="bi bi-check2"></i></button>
								<button type="reset" class="btn btn-light">다시입력</button>
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/qna/list';">${mode=='update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i></button> 
							</td>
						</tr>
					</table>
				
				</form>
				</div>
				
			</div>
		</div>
	</div>
	</div>

</div>

<script type="text/javascript">
	ClassicEditor
		.create( document.querySelector( '.editor' ), {
			fontFamily: {
	            options: [
	                'default',
	                '맑은 고딕, Malgun Gothic, 돋움, sans-serif',
	                '나눔고딕, NanumGothic, Arial'
	            ]
	        },
	        fontSize: {
	            options: [
	                9, 11, 13, 'default', 17, 19, 21
	            ]
	        },
			toolbar: {
				items: [
					'heading','|',
					'fontFamily','fontSize','bold','italic','fontColor','|',
					'alignment','bulletedList','numberedList','|',
					'imageUpload','insertTable','sourceEditing','blockQuote','mediaEmbed','|',
					'undo','redo','|',
					'link','outdent','indent','|',
				]
			},
			image: {
	            toolbar: [
	                'imageStyle:full',
	                'imageStyle:side',
	                '|',
	                'imageTextAlternative'
	            ],
	
	            // The default value.
	            styles: [
	                'full',
	                'side'
	            ]
	        },
			language: 'ko',
			ckfinder: {
		        uploadUrl: '${pageContext.request.contextPath}/image/upload' // 업로드 url (post로 요청 감)
		    }
		})
		.then( editor => {
			window.editor = editor;
		})
		.catch( err => {
			console.error( err.stack );
		});
</script>