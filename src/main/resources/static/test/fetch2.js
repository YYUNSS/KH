{ //동기
  console.log('시작');
  console.log('실행중');
  let sum = 0;
  for (let i = 0; i < 990000000; i++) {
    sum += i;
  }
  console.log(`sum=${sum}`);
  console.log('종료');
}
console.log('----------------------')
// { //비동기 1
//   console.log('시작');
//   console.log('실행중');
//   const result = new Promise((resolve,reject)=>{
//     //비동기처리로직,,보통 시간이 소요되는 작업 ex)서버와의 통신
//     let sum = 0;
//     for (let i = 0; i < 990000000; i++){
//       sum += i;
//     };
//     resolve(sum);
//   });
//   result.then(res=>console.log(`sum=${res}`))
//   console.log('종료');
// }
// console.log('------------------')
{
  //비동기 2
  console.log('시작');
  console.log('실행중');
  const method1 = async ()=>{
    try{
      const result = await new Promise((resolve, reject) => {
        //비동기처리로직,,보통 시간이 소요되는 작업 ex)서버와의 통신
        let sum = 0;
        for (let i = 0; i < 990000000; i++) {
          sum += i;
        }
        if(false) {
          resolve(sum);
        }else{
          reject(new Error('오류발행'));
        };
      });
      console.log(result);
    }catch(err){
      console.log(err);
    }finally{
      console.log('성공유무관계없이 수행됨');
    }  
  }
  method1();
  console.log('종료');
}