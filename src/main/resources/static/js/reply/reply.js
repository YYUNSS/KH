import { Pagination } from '/js/common.js'


const pagination = new Pagination(10, 10);

//게시물 목록 버튼
const boardId = document.getElementById('boardId').value;
const $listBtn = document.getElementById("listBtn")
$listBtn.addEventListener('click', evt => {
  location.href = '/boardlist';
})
//게시물 삭제 버튼
const $deleteBtn = document.getElementById('deleteBtn');
$deleteBtn.addEventListener('click', evt => {
  if (!window.confirm('삭제하시겠습니까?'))
    return;
  location.href = `/boardlist/${boardId}/del`;
})
//게시물 수정 버튼
const $modifyBtn = document.getElementById('modifyBtn');
$modifyBtn.addEventListener('click', evt => {
  location.href = `/boardlist/${boardId}/edit`;
});

findByBoardId(boardId);

//댓글
//댓글 등록 버튼
replyAddBtn.addEventListener('click', evt => {
  evt.preventDefault();
  const replyContents = document.getElementById('replyContents').value;
  //댓글 글자수 3자 이상 유효성 검사
  if (replyContents.trim().length < 3) {
    commentErrMsg.textContent = '3글자 이상 입력바랍니다.';
    return;
  } else {
    commentErrMsg.textContent = '';
  }
  const newReply = { // 새로운 변수명으로 수정
    boardId: boardId, // 게시판 ID
    replyContents: replyContents // 댓글 내용
  };
  add(newReply);
});
//등록
async function add(reply) {
  console.log(reply);
  const url = `http://localhost:9080/reply`;
  const payload = reply;
  const option = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      accept: 'application/json',
    },
    body: JSON.stringify(payload)   // js객체=>json포맷 문자열
  };
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); //응답메세지 바디를 읽어 json포맷 문자열=>js객체
    if (result.header.rtcd == '00') {
      console.log(result.body);
      findByBoardId(boardId);
    } else {
      new Error('등록 실패!');
    }
  } catch (err) {
    console.error(err.message);
  }
}

//목록
async function findByBoardId(boardId) {
  const reqPage = pagination.currentPage;   //요청 페이지
  const recCnt = pagination.recordsPerPage; //페이지당 레코드수
  loadding.style.display = 'block';
  const url = `http://localhost:9080/reply?boardId=${boardId}&reqPage=${reqPage}&recCnt=${recCnt}`;
  const option = {
    method: 'GET',
    headers: {
      accept: 'application/json'
    }
  }
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); //응답메세지 바디를 읽어 json포맷 문자열=>js객체
    // 게시글 아이디로 게시글 찾으면
    if (result.header.rtcd == '00') {

      console.log(result.body);
      const str = result.body.map(item =>
        `<div>
          <ul>
            <li class="listrow" >
              <input type="hidden" name="rid" id="rid" value="${item.replyId}">
              <span id="replyContents">${item.replyContents}</span>
              <span id="replyWriterNickname">${item.replyWriterNickname}</span>
              <span id="cdate">${item.cdate}</span>    
              <button class="replyModiBtn">수정</button>
              <button class="replyDelBtn">삭제</button>
            </li>
          </ul>
        </div>`).join('');

      replyList.innerHTML = str;
      pagination.setTotalRecords(result.totalCnt);
      pagination.displayPagination(findByBoardId);

      // 못찾은 경우  
    } else if (result.header.rtcd == '01') {
      console.log(result.header.rtmsg, result.header.rtdetail);
    } else {
      new Error('조회 실패');
    }
  } catch (err) {
    console.error(err.message);
  } finally {
    loadding.style.display = 'none';
  }
  //댓글 삭제 버튼
  const $replyList = document.querySelector('#replyList');
  $replyList.addEventListener('click', evt => {
  if (evt.target.classList.contains('replyDelBtn')) {
    const currLi = evt.target.closest('.listrow'); 
    if (!currLi) return;
    const replyId = currLi.querySelector('#rid').value; // 리스트 요소 내의 숨겨진 input에서 replyId를 가져옵니다.
    del(replyId);


  //댓글 수정
  } else if(evt.target.classList.contains('replyModiBtn')){
    const currLi = evt.target.closest('.listrow'); 
    if (!currLi) return;
    
    const replyContentsSpan = currLi.querySelector('#replyContents');
    const cdateSpan = currLi.querySelector('#cdate');
    const replyContentsValue = replyContentsSpan.textContent;
    // replyContents를 input 태그로 변경
    const input = document.createElement('input');
    input.setAttribute('type', 'text');
    input.setAttribute('value', replyContentsValue);
    //replyContentsSpan을 input으로 교체
    currLi.replaceChild(input, replyContentsSpan);
    //작성일자 안보이게
    currLi.removeChild(cdateSpan);

    // 수정 버튼 -> 저장 대체
    const modifyBtn = currLi.querySelector('.replyModiBtn');
    const saveBtn = document.createElement('button');
    saveBtn.textContent = '저장';
    saveBtn.classList.add('modiSaveBtn');
    modifyBtn.parentNode.replaceChild(saveBtn, modifyBtn);

    // 삭제 - 취소 버튼 대체
    const deleteBtn = currLi.querySelector('.replyDelBtn');
    const cancelBtn = document.createElement('button');
    cancelBtn.textContent = '취소';
    cancelBtn.classList.add('modiCancelBtn');
    deleteBtn.parentNode.replaceChild(cancelBtn, deleteBtn);

    //수정-저장 버튼 누르면(update 실행)
    
    //수정-취소 버튼 누르면(이전으로 되돌리기)
    const replyId = currLi.querySelector('#rid').value; 
    cancelBtn.addEventListener('click',evt=>{
      //input-> 다시 span으로 

      //저장버튼을 수정버튼으로 

      //취소버튼을 삭제버튼으로

      
      // update(replyId);
    });
    

  }
  })


  }

//수정
async function update(rid) {
  console.log('수정');


  //replyContents 추가해야
  const url = `http://localhost:9080/reply/${rid}`;
  const option = {
    method: 'PATCH',
    headers: {
      accept: 'application/json'
    }
  }

}


//삭제
async function del(rid) {
  console.log('삭제');
  const url = `http://localhost:9080/reply/${rid}`;
  const option = {
    method: 'DELETE',
    headers: {
      accept: 'application/json'
    }
  }
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); //응답메세지 바디를 읽어 json포맷 문자열=>js객체
    if (result.header.rtcd == '00') {
      console.log(result.body);
      findByBoardId(boardId);
    } else {
      new Error('삭제 실패!');
    }
  } catch (err) {
    console.error(err.message);
  }
}
//삭제
