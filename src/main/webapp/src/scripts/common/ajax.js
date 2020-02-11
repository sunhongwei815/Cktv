;(function(){

  'use strict';

  var ajax = {};

  ajax.x = function () {
    var xhr, versions;
    if (typeof XMLHttpRequest !== undefined) {
      xhr = new XMLHttpRequest();
    } else {
      versions = [
        'MSXML2.XmlHttp.5.0',
        'MSXML2.XmlHttp.4.0',
        'MSXML2.XmlHttp.3.0',
        'MSXML2.XmlHttp.2.0',
        'Microsoft.XmlHttp'
      ];
      for (var i = 0; i < versions.length; i++) {
        try {
          xhr = new ActiveXObject(versions[i]);
          break;
        } catch (e) {}
      }
    }
    return xhr;
  };

  ajax.send = function (url, method, callback, data, sync) {
    var xhr = ajax.x();
    xhr.open(method, url, sync);
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        callback(xhr.responseText);
      }
    }
    if (method === 'POST') {
      xhr.setRequestHeader('Content-Type', 'application/json');
    }
    data = typeof(data) === 'string' ? data : JSON.stringify(data);
    xhr.send(data);
  };

  ajax.get = function(url, callback, sync) {
    var async = sync ? false : true;
    ajax.send(url, 'GET', callback, null, async);
  };

  ajax.post = function(url, data, callback, sync) {
    var async = sync ? false : true;

    ajax.send(url, 'POST', function (response) {
      var getData;
      if (typeof (response)=='string'){
        getData = JSON.parse(response);
      }else if (typeof (response) == 'object'){
        getData = eval(response);
      }
      if (getData.success === false&&getData.msg == '您还没有登录,请登录'){
        location.href = '/Cktv/pages/user-login/login.html'
      }else {
        callback(getData);
      }
    }, data, async);
  };

  this.ajax = ajax;

}.call(this));
