
async function a(){
  console.log('a() 호출됨');
  const url = 'http://localhost:9080/api/products'
  const result = await fetch(url);
  console.log(Object.prototype.toString.call(result));
  const value = await result.json();
  console.log(value);
}
function b() {
  console.log('b() 호출됨');
}

console.log('1');
a();
console.log('2');
b();
console.log('3');