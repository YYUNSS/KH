import {Pagination} from '/js/common.js'


const pagination = new Pagination(10, 10);

  //게시물 목록 버튼
const boardId = document.getElementById('boardId').value;
const $listBtn = document.getElementById("listBtn")
$listBtn.addEventListener('click',evt=>{
  location.href = '/boardlist';
  })
  //게시물 삭제 버튼
const $deleteBtn = document.getElementById('deleteBtn');
$deleteBtn.addEventListener('click',evt=>{
  if(!window.confirm('삭제하시겠습니까?'))
  return;
  location.href = `/boardlist/${boardId}/del`;
  })
  //게시물 수정 버튼
  const $modifyBtn = document.getElementById('modifyBtn');
  $modifyBtn.addEventListener('click',evt=>{
    location.href = `/boardlist/${boardId}/edit`;
  });

  //댓글
let $replyList = '';
let $loaddingImg = '';
renderHTML();
function renderHTML(){
  const $div = document.createElement('div');
  $div.innerHTML =
  `<div class="replyFrm-wrap">
    <form id="replyFrm">
      <h3>댓글</h3>
      <textarea name="replyContents" id="replyContents" cols="30" rows="3"></textarea><button id='replyAddBtn'type='submit'>등록</button><button type="reset">초기화</button>
    </form>
  </div>
  <div id='replyList'>
  </div>
  <div id='pagination'></div>
  <img id='loadding' src='/img/loadding.svg'>`;
  const $bidInput = document.createElement('input');
  $bidInput.type = 'hidden';
  $bidInput.id = 'bid';
  $bidInput.value = boardId;
  $div.querySelector("#replyFrm").appendChild($bidInput);
  document.body.appendChild($div);
  
  const $replyAddBtn = $div.querySelector('#replyAddBtn');
  $replyAddBtn.addEventListener('click', evt => {
    evt.preventDefault();
    console.log('등록 요청');
    const replyContents = document.getElementById('replyContents').value;
    const newReply = { // 새로운 변수명으로 수정
      boardId: boardId, // 게시판 ID
      replyContents: replyContents // 댓글 내용
  };
    add(newReply); 
  $replyList = $div.querySelector('#replyList');
  //로딩 이미지
  $loaddingImg = $div.querySelector('#loadding');
  $loaddingImg.style.position = 'absolute';
  $loaddingImg.style.top = '50vh';
  $loaddingImg.style.left = '50vw';
  $loaddingImg.style.transform = 'translate(-50%,-50%)';
  $loaddingImg.style.display = 'none';
  
  findByBoardId(boardId);
  });
//등록
async function add(reply) {
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
      findByBoardId();
    } else {
      new Error('등록 실패!');
    }
  } catch (err) {
    console.error(err.message);
  }
}
  async function findByBoardId(boardId){
    const reqPage = pagination.currentPage;   //요청 페이지
    const recCnt = pagination.recordsPerPage; //페이지당 레코드수
    $loaddingImg.style.display = 'block';

    const url=`http://localhost:9080/reply?boardId=${boardId}&reqPage=${reqPage}&recCnt=${recCnt}`;
    const option = {
      method : 'GET',
      headers: {
      accept: 'application/json'
      }
    }
    try{
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); //응답메세지 바디를 읽어 json포맷 문자열=>js객체
    // 게시글 아이디로 게시글 찾으면
    if (result.header.rtcd == '00') {
      console.log(result.body);
      const str = result.body.map(item=>
        `<div>
          <span>${item.replyContents}</span>
          <span>${item.replyWriterNickname}</span>
          <span>${item.cdate}</span>
        </div>`).join('');

      $productList.innerHTML = str;

      pagination.setTotalRecords(result.totalCnt);
      pagination.displayPagination(findByBoardId);

    // 못찾은 경우  
    }else if (result.header.rtcd == '01') {
      console.log(result.header.rtmsg, result.header.rtdetail);
    } else {
      new Error('조회 실패');
    }
  } catch (err) {
    console.error(err.message);
  }finally{
    $loaddingImg.style.display = 'none';
  }
}
  }
