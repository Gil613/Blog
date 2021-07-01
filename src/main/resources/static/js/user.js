let index = {
	init: function() {
		$("#btn-save").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서 익명함수 사용
			this.save();
		});
		$("#btn-update").on("click", () => { //function(){}, ()=>{} this를 바인딩하기 위해서 익명함수 사용
			this.update();
		});

	},

	save: function() {
		//alert('저장완료');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}

		//console.log(data);

		//ajax 호출시 default가 비동기 호출
		// ajax 통신을 통해 3개의 데이터를 json으로 변경하여 insert요청
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다. 
		$.ajax({
			type: "POST"
			, url: "/auth/joinProc"
			, data: JSON.stringify(data)//http body 데이터
			, contentType: "application/json; charset=utf-8"//body데이터가 어떤 타입인지(MIME)
			, dataType: "json"
		}).done(function(resp) {
			if(resp.status === 500){
				alert("회원가입이 실패하였습니다.");
			}else{
			alert("회원 가입이 완료되었습니다.");
			}
			//console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}

		$.ajax({
			type: "PUT"
			, url: "/user"
			, data: JSON.stringify(data)//http body 데이터
			, contentType: "application/json; charset=utf-8"//body데이터가 어떤 타입인지(MIME)
			, dataType: "json"
		}).done(function(resp) {
			alert("정보 수정을 성공했습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	}

}
index.init();