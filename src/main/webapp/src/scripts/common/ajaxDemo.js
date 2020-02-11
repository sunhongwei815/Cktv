ajax.get('/login', function(data){
  var data = JSON.parse(data);
  console.log(data);
}, false);// 异步请求

var formData = {
  username: $('#username').val(),
  password: $('#password').val()
};
ajax.post('/login', formData, function(data){
  console.log(data);
}, true);// 此处是同步请求，一般都用异步请求，浏览器不建议使用同步请求。
