/**
 * Created by gyh on 16-8-26.
 */
window.onload = function(){
    "use strict";

    /*1.通过querySting获得传递过来的数据*/
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    /*var hasVideo = getQueryString('hasVideo');
    var tpl_name = getQueryString('tpl_name');
    var tpl_id = getQueryString('tpl_id');*/

    /*2.通过sessionStorage获得传递过来的数据*/
    var hasVideo = sessionStorage.getItem('hasVideo');
    var tpl_name = sessionStorage.getItem('tpl_name');
    var tpl_id = sessionStorage.getItem('tpl_id');
    // console.log(hasVideo + ',' + tpl_name + ',' + tpl_id);

    
    document.getElementById('nameoftpl').innerHTML += tpl_name;
    document.getElementById('idoftpl').innerHTML += tpl_id;
    if(hasVideo == 1){
        document.getElementsByClassName('video')[0].style.display = 'block';
    }else if(hasVideo == 2){
        document.getElementsByClassName('video')[0].style.display = 'none';
    }



    /*
    * event handler
    */
    var EventUtil = {
        addHandler: function (element, type, handler) {
            if(element.addEventListener){
                element.addEventListener(type,handler,false);
            }else if(element.attachEvent){
                element.attachEvent('on' + type,handler);
            }else{
                element['on' + type] = handler;
            }
        },
        removeHandler: function (element, type, handler) {
            if(element.removeEventListener){
                element.removeEventListener(type,handler,false);
            }else if(element.detachEvent){
                element.detachEvent('on' + type,handler);
            }else{
                element['on' + type] = null;
            }
        },
        getElement: function (event) {
            return event.target || event.srcElement;
        },
        stopBubble: function (event) {
            if(event.stopPropagation){
                event.stopPropagation();
            }else{
                event.cancelBubble = true;
            }
        },
        stopDefault: function (event) {
            if(event.preventDefault){
                event.preventDefault();
            }else{
                window.event.returnValue = false;
            }
        }
    };
    /*
    * the methods of get outer ele
    */
    var getOuterEle = {
        gi: function (id) {
            return document.getElementById(id);
        },
        gc: function (className,parentId) {
            var parent = parentId ? document.getElementById(parentId) : document;
            if(parent.getElementsByClassName){
                return parent.getElementsByClassName(className);//注意这个返回的是一个数组
            }else{
                var eles = [];
                var elements = parent.getElementsByTagName('*');
                for(var i = 0,len = elements.length; i < len; i++){
                    if(elements[i].className == className){
                        eles.push(elements[i]);
                    }
                }
                return eles;
            }
        }
    };
    /*
    * get real dom ele out
    */
    var ele = {
        myIframe: getOuterEle.gi('iframe'),//iframe框
        outerType: getOuterEle.gc('type')[0],//外面要拖动的文字
        outerPhoto: getOuterEle.gc('photo')[0],//外面要拖动的照片
        outerVideo: getOuterEle.gc('video')[0],//外面要拖动的视频
        outerDrag: getOuterEle.gc('drag'),//外面要拖动的元素
        modalType: getOuterEle.gi('layer_type_content'),
        modalPhoto: getOuterEle.gi('layer_photo_content'),
        modalVideo: getOuterEle.gi('layer_video_content')
    };
    /*
     * the methods of get inner iframe ele
     */
    var getInnerEle = {
        gi: function (id) {
            if(!ele.myIframe) return false;
            var ifmChild = ele.myIframe.contentWindow.document.getElementById(id);
            if(!ifmChild) return false;
            return ifmChild;
        },
        gc: function (className,parent) {
            parent = parent ? parent : ele.myIframe.contentWindow.document;
            if(!ele.myIframe) return false;
            var ifmChilds = function () {
                if(parent.getElementsByClassName){
                    return parent.getElementsByClassName(className);
                }else{
                    var eles = [];
                    var elements = parent.getElementsByTagName('*');
                    for(var i = 0,len = elements.length; i < len; i++){
                        if(elements[i].className == className){
                            eles.push(elements[i]);
                        }
                    }
                    return eles;
                }
            };
            if(!ifmChilds().length) return false;
            return ifmChilds();
        },
        gt: function (tagName) {
            if(!ele.myIframe) return false;
            var ifmTags = ele.myIframe.contentWindow.document.getElementsByTagName(tagName);
            if(!ifmTags.length) return false;
            return ifmTags;
        }
    };
    /*
    * the 3 divs static position at the very first
    */
    var divStaticPos = {
        commonLeft: 820 + 'px',
        typeTop: 100 + 'px',
        photoTop: 200 + 'px',
        videoTop: 300 + 'px'
    };
    /*
    * get style of ele
    */
    var getStyle = function (ele) {
        var style = null;
        if(window.getComputedStyle){
            style = window.getComputedStyle(ele,null);
        }else{
            style = ele.currentStyle;
        }
        return style;
    };
    /*
    * get number of string
    */
    var getNum = function (ele) {
        return ele.replace(/[^0-9]/ig,"");
    };
    /*
    * RGB颜色转成#表示的
    */
    String.prototype.colorHex = function (){
        var that = this;
        if(/^(rgb|RGB)/.test(that)){
            var aColor = that.replace(/(?:\(|\)|rgb|RGB)*/g,"").split(",");
            var strHex = "#";
            for(var i=0; i<aColor.length; i++){
                var hex = Number(aColor[i]).toString(16);
                if(hex === "0"){
                    hex += hex;
                }
                strHex += hex;
            }
            if(strHex.length !== 7){
                strHex = that;
            }
            return strHex;
        }else if(reg.test(that)){
            var aNum = that.replace(/#/,"").split("");
            if(aNum.length === 6){
                return that;
            }else if(aNum.length === 3){
                var numHex = "#";
                for(var i=0; i<aNum.length; i+=1){
                    numHex += (aNum[i]+aNum[i]);
                }
                return numHex;
            }
        }else{
            return that;
        }
    };
    /*
    * some calc about iframe ele
    */
    var ifmCalc = {
        ifmL: ele.myIframe.offsetLeft,//0, no px;
        ifmT: ele.myIframe.offsetTop,//50, no px;
        ifmW: ele.myIframe.clientWidth,//1920, no px;
        ifmH: ele.myIframe.clientHeight,//1080, no px;
        getZoom: function(){
            var myZoom = 1;
            if(getStyle(ele.myIframe).zoom){
                myZoom = getStyle(ele.myIframe).zoom;
            }else if(getStyle(ele.myIframe).transform){
                myZoom = getStyle(ele.myIframe).transform;
            }
            return myZoom;
        },//0.4
        ifmR: function () {
            return ifmCalc.ifmL + (ifmCalc.ifmW) * (ifmCalc.getZoom());
        },//768
        ifmB: function () {
            return ifmCalc.ifmT + (ifmCalc.ifmH) * (ifmCalc.getZoom());
        }//482
    };
    /*
    * drag Event
    */
    var lastondblclick;
    var cloneEvent = function (clone) {
        EventUtil.addHandler(clone,'mousedown',function (e) {
            drag.mouseDown(e,this);
        });
        EventUtil.addHandler(clone,'mousemove',function (e) {
            drag.mouseMove(e,this);
        });
        EventUtil.addHandler(clone,'mouseup',function (e) {
            drag.mouseUp1(e,this);
        });
        function ondblclick() {
            var self = this;
            console.log(self);
            if(self.className == 'drag type'){
                $('#layer_type_content').modal();
                var typeObj = modalOperation.typeOp(self);
                modalSave.typeSave(self,typeObj);
            }else if(self.className == 'drag photo'){
                $('#layer_photo_content').modal();
                modalOperation.photoOp();
                modalSave.photoSave(self);
            }else if(self.className == 'drag video'){
                $('#layer_video_content').modal();
                modalOperation.videoOp();
                modalSave.videoSave(self);
            }
        }
        EventUtil.removeHandler(clone,'dblclick',lastondblclick);
        EventUtil.addHandler(clone,'dblclick',ondblclick);
        lastondblclick = ondblclick;
    };
    var deleteEle = function (clone) {
        var onkeydown = function (e) {
            e = e || window.event;
            if(e && e.keyCode == 8){
                EventUtil.stopDefault(e);
                ele.myIframe.contentWindow.document.body.removeChild(clone);
            }
        };
        EventUtil.addHandler(clone,'click',function () {
            if(clone.style.borderStyle == '' || null){
                clone.style.borderStyle = 'dashed';
                EventUtil.addHandler(ele.myIframe.contentWindow.document,'keydown',onkeydown);
            }
        });
        EventUtil.addHandler(clone,'blur',function () {
            if(clone){
                clone.style.borderStyle = '';
                EventUtil.removeHandler(ele.myIframe.contentWindow.document,'keydown',onkeydown)
            }
        })
    };

    var drag = {
        mouseMoveX: 0,
        mouseMoveY: 0,
        isDragging: false,
        moveX: 0,
        moveY: 0,
        isResize: false,
        i: 0,
        mouseDown: function (e,el) {
            var ev = e || window.event;
            drag.mouseMoveX = ev.pageX - el.offsetLeft;
            drag.mouseMoveY = ev.pageY - el.offsetTop;
            drag.isDragging = true;
        },
        mouseMove: function (e, el) {
            var ev = e || window.event;
            if(drag.isDragging && !drag.isResize){
                drag.moveX = ev.pageX - drag.mouseMoveX;
                drag.moveY = ev.pageY - drag.mouseMoveY;
                var righW = getOuterEle.gi('right').clientWidth;
                var righH = getOuterEle.gi('right').clientHeight;
                var divW = el.offsetWidth;
                var divH = el.offsetHeight;
                var maxW,maxH;
                if(el.getAttribute('data-check') == 'out'){
                    maxW = righW - divW;
                    maxH = righH - divH;
                   /* drag.moveX = Math.min(maxW, Math.max(0, drag.moveX));
                    drag.moveY = Math.min(maxH, Math.max(0, drag.moveY));*/
                }else if(el.getAttribute('data-check') == 'in'){
                    maxW = ifmCalc.ifmW * 0.4 - (divW);
                    maxH = ifmCalc.ifmH * 0.4 - (divH);
                    /*drag.moveX = Math.min(maxW, Math.max(0, drag.moveX));
                    drag.moveY = Math.min(maxH, Math.max(0, drag.moveY));*/
                }
                /*if(drag.moveX < 0){
                    drag.moveX = 0;
                }else if(drag.moveX > maxW){
                    drag.moveX = maxW;
                }
                if(drag.moveY < 0){
                    drag.moveY = 0;
                }else if(drag.moveY > maxH){
                    drag.moveY = maxH;
                }*/
                drag.moveX = Math.min(maxW, Math.max(0, drag.moveX));
                drag.moveY = Math.min(maxH, Math.max(0, drag.moveY));

                if(el.className == 'drag video'){
                    el.firstElementChild.style.left = drag.moveX + "px";
                    el.firstElementChild.style.top = drag.moveY + "px";
                }

                el.style.left = drag.moveX + "px";
                el.style.top = drag.moveY + "px";
                var modalZindex = getStyle(getOuterEle.gc('modal')[0]).zIndex;
                el.style.zIndex = modalZindex - 1;
            }
        },
        mouseUp1: function(e,el){
            drag.isDragging = false;
            el.style.zIndex = (drag.i)++;
        },
        mouseUp2: function (e,el) {
            drag.isResize = false;
            var cls = getInnerEle.gc("ui-resizable-ctrl",el);
            console.log(cls);
            for(var i = 0,len = cls.length; i < len; i++){
                cls[i].style.left = "";
                cls[i].style.top = "";
            }
        },
        //judge if the div is in or out the iframe
        isOut: function (e,el) {
            var ev = e || window.event;
            drag.mouseMoveX = ev.pageX - el.offsetLeft;
            drag.mouseMoveY = ev.pageY - el.offsetTop;
            drag.moveX = ev.pageX - drag.mouseMoveX;
            drag.moveY = ev.pageY - drag.mouseMoveY;
            // console.log(drag.moveX + ',' + drag.moveY);
            /*元素没有移动到iframe内*/
           /* if(drag.moveX < ifmCalc.ifmL || drag.moveY < ifmCalc.ifmT || drag.moveX > ifmCalc.ifmR()-el.clientWidth || drag.moveY > ifmCalc.ifmB()-el.clientHeight+ifmCalc.ifmT){
                return false;
            }
            /!*元素移到了iframe框内*!/
            else{
                return true;
            }*/
            return drag.moveX < ifmCalc.ifmL || drag.moveY < ifmCalc.ifmT || drag.moveX > ifmCalc.ifmR()-el.clientWidth || drag.moveY > ifmCalc.ifmB()-el.clientHeight+ifmCalc.ifmT;
        },
        //the div is out the iframe
        outIfm: function (e, el) {
            el.style.zIndex = 1;
            el.style.left = divStaticPos.commonLeft;
            if(el.className == 'drag type'){
                el.style.top = divStaticPos.typeTop;
            }else if(el.className == 'drag photo'){
                el.style.top = divStaticPos.photoTop;
            }else if(el.className == 'drag video'){
                el.style.top = divStaticPos.videoTop;
            }
        },
        //add control ele
        addCtrl: function (el) {
            var l = document.createElement("div");
            var r = document.createElement("div");
            var t = document.createElement("div");
            var b = document.createElement("div");
            var lt = document.createElement("div");
            var rt = document.createElement("div");
            var lb = document.createElement("div");
            var rb = document.createElement("div");
            l.class = l.className = "ui-resizable-l ui-resizable-ctrl";
            r.class = r.className = "ui-resizable-r ui-resizable-ctrl";
            t.class = t.className = "ui-resizable-t ui-resizable-ctrl";
            b.class = b.className = "ui-resizable-b ui-resizable-ctrl";
            lt.class = lt.className = "ui-resizable-lt ui-resizable-ctrl";
            rt.class = rt.className = "ui-resizable-rt ui-resizable-ctrl";
            lb.class = lb.className = "ui-resizable-lb ui-resizable-ctrl";
            rb.class = rb.className = "ui-resizable-rb ui-resizable-ctrl";
            el.appendChild(l);
            el.appendChild(r);
            el.appendChild(t);
            el.appendChild(b);
            el.appendChild(lt);
            el.appendChild(rt);
            el.appendChild(lb);
            el.appendChild(rb);
        },
        //check where is the mouse
        atWhere: function (e,el) {
            e = e || window.event;
            var x = e.pageX;
            var y = e.pageY;
            // console.log('x:' + x + '; y:' + y);
            var x1 = el.offsetLeft;
            var y1 = el.offsetTop;
            // console.log('x1:' + x1 + '; y1:' + y1);
            var x2 = x1 + el.clientWidth;
            var y2 = y1 + el.clientHeight;
            // console.log('x2:' + x2 + '; y2:' + y2);
            var posX = Math.abs(x - x1) > Math.abs(x - x2) ? Math.abs(x - x2) : Math.abs(x - x1);
            var posY = Math.abs(y - y1) > Math.abs(y - y2) ? Math.abs(y - y2) : Math.abs(y - y1);
            // console.log(posX + ',' + posY);
            if(posX > 10 && posY > 10){
                return 'atDiv';
            }
            else if(posX < 10 && posY < 10){
                return 'atCorner';
            }
            /*else if(){
                return 'atL';
            }*/
            else if(posX < 10){
                return 'atR';
            }
            else if(posY < 10) {
                return 'atB'
            }
        },
        //the mouse is at Right Bottom
        atRBMove: function (e, el,ctr) {
            e = e || window.event;
            var x = e.pageX - el.offsetLeft;
            var y = e.pageY - el.offsetTop;
            if(el.className == 'drag video'){
                var $video = el.firstElementChild;
                $video.width = x;
                $video.height = y;
            }
            // ctr.style.left = x - 10 + 'px';
            // ctr.style.top = y - 10 + 'px';
            el.style.width = x + 'px';
            el.style.height = y + 'px';
        },
        //the mouse is at Right
        atRMove: function (e, el,ctr) {
            e = e || window.event;
            var x = e.pageX - el.offsetLeft;
            if(el.className == 'drag video'){
                var $video = el.firstElementChild;
                $video.width = x;
            }
            // ctr.style.left = x - 10 + 'px';
            el.style.width = x + 'px';
        },
        //the mouse is at Bottom
        atBMove: function (e,el,ctr) {
            e = e || window.event;
            var y = e.pageY - el.offsetTop;
            if(el.className == 'drag video'){
                var $video = el.firstElementChild;
                $video.height = y;
            }
            // ctr.style.top = y - 10 + 'px';
            el.style.height = y + 'px';
        },
        bigMouseUp1: function (e,el) {
            EventUtil.addHandler(el,'mouseup',function () {
                drag.mouseUp1(e,el);
            });
            EventUtil.addHandler(getOuterEle.gi('iframe').contentWindow.document,'mouseup',function () {
                drag.mouseUp1(e,el);
            });
            EventUtil.addHandler(document,'mouseup',function () {
                drag.mouseUp1(e,el);
            });
        },
        bigMouseUp2: function (e, el) {
            EventUtil.addHandler(el,'mouseup',function () {
                drag.mouseUp2(e, el);
            });
            EventUtil.addHandler(getOuterEle.gi('iframe').contentWindow.document,'mouseup',function () {
                drag.mouseUp2(e, el);
            });
            EventUtil.addHandler(document,'mouseup',function () {
                drag.mouseUp2(e, el);
            });
        },
        //the div is in the iframe
        inIfm: function (e,el) {
            drag.outIfm(e,el);//没有克隆的元素回到原位
            var clone = el.cloneNode(true);//克隆的元素设置样式
            // clone.style.zIndex = 1;
            clone.style.left = '';
            clone.style.top = '';
            // console.log(clone);
            var script = getInnerEle.gt('script')[0];
            // console.log(script);
            var body = getInnerEle.gt('body')[0];
            // console.log(body);
            body.insertBefore(clone,script);
    
            if(clone.className == 'drag video'){
                clone.style.backgroundColor = '#000';
                var video = clone.firstElementChild;
                video.setAttribute('controls','controls');
                video.setAttribute('autoplay','autoplay');
            }

            // console.log(drag.moveX + ',' + drag.moveY);
            clone.style.left = (drag.moveX) + 'px';
            clone.style.top = (drag.moveY - 80) + 'px';//这个80这样写不好
            clone.removeAttribute('title');
            clone.setAttribute('data-check','in');
            clone.setAttribute('tabindex',drag.i);
            clone.style.zIndex = drag.i;

            cloneEvent(clone);
            deleteEle(clone);

            drag.addCtrl(clone);//添加控制元素

            // console.log(clone);

            var Sys = function (ua) {
                var s = {};
                s.IE = ua.match(/msie ([\d.]+)/) ? true : false;
                s.Firefox = ua.match(/firefox\/([\d.]+)/) ? true : false;
                s.Chrome = ua.match(/chrome\/([\d.]+)/) ? true : false;
                s.IE6 = (s.IE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6)) ? true : false;
                s.IE7 = (s.IE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 7)) ? true : false;
                s.IE8 = (s.IE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 8)) ? true : false;
                return s;
            };
            var Css = function(e,o){
                for(var i in o)
                    e.style[i] = o[i];
            };
            var Bind = function(object, fun) {
                var args = Array.prototype.slice.call(arguments).slice(2);
                return function() {
                    return fun.apply(object, args);
                }
            };
            var BindAsEventListener = function(object, fun) {
                var args = Array.prototype.slice.call(arguments).slice(2);
                return function(event) {
                    return fun.apply(object, [event || window.event].concat(args));
                }
            };
            var Class = function(properties){
                var _class = function(){
                    return (arguments[0] !== null && this.init && typeof(this.init) == 'function') ? this.init.apply(this, arguments) : this;
                };
                _class.prototype = properties;
                return _class;
            };
            var Resize =new Class({
                init : function(obj){
                    this.obj = obj;
                    this.firstchild = null;
                    this.firstchildname = null;
                    this.resizeelm = null;
                    this.fun = null; //记录触发什么事件的索引
                    this.original = []; //记录开始状态的数组
                    this.width = null;//元素的宽 + 元素的left值
                    this.height = null;//元素的高 + 元素的top值
                    this.fR = BindAsEventListener(this,this.resize);
                    this.fS = Bind(this,this.stop);
                },
                set : function(elm,direction){
                    if(!elm)return;
                    this.resizeelm = elm;
                    EventUtil.addHandler(this.resizeelm,'mousedown',BindAsEventListener(this, this.start, this[direction]));
                    return this;
                },
                start : function(e,fun){
                    this.fun = fun;
                    this.firstchild = this.obj.firstElementChild;
                    this.firstchildname = this.obj.firstElementChild.tagName;
                    this.original = [parseInt(getStyle(this.obj).width),parseInt(getStyle(this.obj).height),parseInt(getStyle(this.obj).left),parseInt(getStyle(this.obj).top)];
                    this.width = (this.original[2]||0) + this.original[0];
                    this.height = (this.original[3]||0) + this.original[1];
                    EventUtil.addHandler(ele.myIframe.contentWindow.document,"mousemove",this.fR);
                    EventUtil.addHandler(ele.myIframe.contentWindow.document,'mouseup',this.fS);
                },
                resize : function(e){
                    this.fun(e);
                    Sys(navigator.userAgent.toLowerCase()).IE?(this.resizeelm.onlosecapture=function(){this.fS()}):(this.resizeelm.onblur=function(){this.fS()})
                },
                stop : function(){
                    EventUtil.removeHandler(ele.myIframe.contentWindow.document, "mousemove", this.fR);
                    EventUtil.removeHandler(ele.myIframe.contentWindow.document, "mousemove", this.fS);
                    window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
                },

                up : function(e){
                    if(this.firstchildname === 'VIDEO'){
                        if(this.height > e.clientY){
                            Css(this.firstchild,{top: e.clientY + "px"});
                            this.firstchild.setAttribute('height',(this.height - e.clientY + ''));
                        }else{
                            this.turnDown(e);
                        }
                        // this.height > e.clientY ? Css(this.firstchild,{top: e.clientY + "px", height: this.height - e.clientY + "px"}) : this.turnDown(e);
                    }
                    this.height > e.clientY ? Css(this.obj,{top: e.clientY + "px", height: this.height - e.clientY + "px"}) : this.turnDown(e);
                },
                turnDown : function(e){
                    if(this.firstchildname === 'VIDEO') {
                        // Css(this.firstchild,{top: this.height + 'px', height: e.clientY - this.height + 'px'});
                        Css(this.firstchild,{top: this.height + 'px'});
                        this.firstchild.setAttribute('height',(this.height - e.clientY + ''));
                    }
                    Css(this.obj,{top: this.height + 'px', height: e.clientY - this.height + 'px'});
                },

                down : function(e){
                    if(this.firstchildname === 'VIDEO') {
                        if(e.clientY > this.original[3]){
                            Css(this.firstchild,{top: this.original[3] + 'px'});
                            this.firstchild.setAttribute('height',e.clientY - this.original[3] + '');
                        }else{
                            this.turnUp(e);
                        }
                        // e.clientY > this.original[3] ? Css(this.firstchild,{top: this.original[3] + 'px', height: e.clientY - this.original[3] + 'px'}) : this.turnUp(e);
                    }
                    e.clientY > this.original[3] ? Css(this.obj,{top: this.original[3] + 'px', height: e.clientY - this.original[3] + 'px'}) : this.turnUp(e);
                },
                turnUp : function(e){
                    if(this.firstchildname === 'VIDEO') {
                        Css(this.firstchild,{top: e.clientY + 'px'});
                        this.firstchild.setAttribute('height',this.original[3] - e.clientY + '');
                        // Css(this.firstchild,{top: e.clientY + 'px', height: this.original[3] - e.clientY + 'px'});
                    }
                    Css(this.obj,{top: e.clientY + 'px', height: this.original[3] - e.clientY + 'px'});
                },

                left : function(e){
                    if(this.firstchildname === 'VIDEO'){
                        if(this.width > e.clientX){
                            Css(this.firstchild,{left: e.clientX + 'px'});
                            this.firstchild.setAttribute('width',this.width - e.clientX + '');
                        }else{
                            this.turnRight(e);
                        }
                        // this.width > e.clientX ? Css(this.firstchild,{left: e.clientX + 'px', width: this.width - e.clientX + 'px'}) : this.turnRight(e);
                    }
                    this.width > e.clientX ? Css(this.obj,{left: e.clientX + 'px', width: this.width - e.clientX + 'px'}) : this.turnRight(e);
                },
                turnRight : function(e){
                    if(this.firstchildname === 'VIDEO') {
                        Css(this.firstchild,{left: this.width + 'px'});
                        this.firstchild.setAttribute('width',e.clientX - this.width + 'px');
                        // Css(this.firstchild,{left: this.width + 'px', width: e.clientX - this.width + 'px'});
                    }
                    Css(this.obj,{left: this.width + 'px', width: e.clientX - this.width + 'px'});
                },

                right : function(e){
                    if(this.firstchildname === 'VIDEO'){
                        if(e.clientX > this.original[2]){
                            Css(this.firstchild, {left: this.original[2] + 'px'});
                            this.firstchild.setAttribute('width',e.clientX - this.original[2] + "");
                        }else{
                            this.turnLeft(e);
                        }
                        // e.clientX > this.original[2] ? Css(this.firstchild, {left: this.original[2] + 'px', width: e.clientX - this.original[2] + "px"}) : this.turnLeft(e);
                    }
                    e.clientX > this.original[2] ? Css(this.obj,{left: this.original[2] + 'px', width: e.clientX - this.original[2] + "px"}) : this.turnLeft(e);
                },
                turnLeft : function(e){
                    if(this.firstchildname === 'VIDEO'){
                        Css(this.firstchild,{left: e.clientX + 'px'});
                        this.firstchild.setAttribute('width',this.original[2] - e.clientX + '');
                        // Css(this.firstchild,{left: e.clientX + 'px', width: this.original[2] - e.clientX + 'px'});
                    }
                    Css(this.obj,{left: e.clientX + 'px', width: this.original[2] - e.clientX + 'px'});
                },

                leftUp:function(e){
                    this.left(e);
                    this.up(e);
                },
                leftDown:function(e){
                    this.left(e);
                    this.down(e);
                },
                rightUp:function(e){
                    this.right(e);
                    this.up(e);
                },
                rightDown:function(e){
                    this.right(e);
                    this.down(e);
                }
            });

            new Resize(clone)
                .set(getInnerEle.gc('ui-resizable-t',clone)[0],'up')
                .set(getInnerEle.gc('ui-resizable-b',clone)[0],'down')
                .set(getInnerEle.gc('ui-resizable-l',clone)[0],'left')
                .set(getInnerEle.gc('ui-resizable-r',clone)[0],'right')
                .set(getInnerEle.gc('ui-resizable-lt',clone)[0],'leftUp')
                .set(getInnerEle.gc('ui-resizable-lb',clone)[0],'leftDown')
                .set(getInnerEle.gc('ui-resizable-rb',clone)[0],'rightDown')
                .set(getInnerEle.gc('ui-resizable-rt',clone)[0],'rightUp');
        }
    };

    /*
    * 以下由于使用了Bootstrap，所以有些是jQuery语法。
    */

    /*
    * 模态框操作事件
    */
    var modalOperation = {
        typeOp: function (el) {
            var $size = getOuterEle.gi('font_size');
            var $fColor = getOuterEle.gi('font_color');
            var $bColor = getOuterEle.gi('back_color');
            var $input = getOuterEle.gi('font_input');
            var sizeCss = getNum(getStyle(el).fontSize);
            var fColorCss = (getStyle(el).color).colorHex();
            var bColorCss = (getStyle(el).backgroundColor).colorHex();
            var inputCont = el.innerText;
            var obj = {
                sizeVal: sizeCss,
                fColorVal: fColorCss,
                bColorVal: bColorCss,
                inputVal: inputCont
            };
            EventUtil.addHandler($size,'change',function () {
                var index = this.selectedIndex;
                obj.sizeVal = $size.options[index].value;
            });
            EventUtil.addHandler($fColor,'change',function () {
                obj.fColorVal = this.value;
            });
            EventUtil.addHandler($bColor,'change',function () {
                obj.bColorVal = this.value;
            });
            EventUtil.addHandler($input,'blur',function () {
                obj.inputVal = this.value;
            });
            return obj;
        },
        photoOp: function () {
            var $img = getOuterEle.gi('fileImg');
            var $pre = getOuterEle.gi('preview');
            EventUtil.addHandler($img,'change',function () {
                //preview
                var f = getOuterEle.gi('fileImg').files[0];
                // console.log(f);
                var preSrc = window.URL.createObjectURL(f);
                $pre.src = preSrc;
                //better send ajax when save
            })
        },
        videoOp: function () {
            var url = "/Cktv/video/allVideos/" + 1 + "/" + 10;
            ajax.post(url, null, function (getData) {
                //handlebars渲染视频列表
                console.log(getData);
                var source = getOuterEle.gi('videoListTemplate').innerHTML;
                var tpl = Handlebars.compile(source);
                getOuterEle.gi('video-choose').innerHTML = tpl(getData.videos);
            });
        }
    };

    var lastOnclick;
    /*
    * 模态框保存事件
    */
    var lastonclick1;
    var lastonclick2;
    var lastonclick3;
    var modalSave = {
        typeSave: function (el,obj) {
            var $save = getOuterEle.gi('type_save');
            function onclick1() {
                console.log(el);
                el.style.fontSize = obj.sizeVal + 'px';
                el.style.color = obj.fColorVal;
                el.style.backgroundColor = obj.bColorVal;
                el.firstElementChild.innerText = obj.inputVal;
                $('#layer_type_content').modal('hide');
            }


            EventUtil.removeHandler($save,'click',lastonclick1);
            EventUtil.addHandler($save,'click',onclick1);
            lastonclick1 = onclick1;

        },
        photoSave: function (el) {
            var $save = getOuterEle.gi('photo_save');
            function onclick2(){
                console.log(el);
                var url = "/Cktv/tpl/uploadPicture/" + tpl_id;
                var fileImg = new FormData(getOuterEle.gi('photo_form'));
                $.ajax({
                    url: url,
                    type: "post",
                    cache: false,
                    data: fileImg,
                    processData: false,
                    contentType: false,
                    success: function(getData){
                        console.log(getData);
                        if(getData.success){
                            var imgSrc = getData.picturePath;
                            console.log(el);
                            el.style.cssText += "background-image:url('" + imgSrc + "');background-size:100%,100%;";
                        }else {
                            alert(getData.msg);
                        }
                    },
                    error: function(err){
                        alert(err);
                    }
                });
                $('#layer_photo_content').modal('hide');
            }
            EventUtil.removeHandler($save,'click',lastonclick2);
            EventUtil.addHandler($save,'click',onclick2);
            lastonclick2 = onclick2;
        },
        videoSave: function (el) {
            var $save = getOuterEle.gi('video_save');
            function getVideo() {
                var videoUrl = '';
                var input = document.getElementsByName('video');
                for(var i = 0,len = input.length; i < len; i++){
                    if(input[i].checked == true){
                        videoUrl = input[i].getAttribute('video_url');
                        break;
                    }
                }
                return videoUrl;
            }
            function onclick3() {
                console.log(getVideo());
                el.firstElementChild.setAttribute('src',getVideo());
                $('#layer_video_content').modal('hide');
            }
            EventUtil.removeHandler($save,'click',lastonclick3);
            EventUtil.addHandler($save,'click',onclick3);
            lastonclick3 = onclick3;
        }
    };

    /*event after html onload*/

    /*
     * 1.add src attr of iframe dynamically
     */
    ele.myIframe.src = "/Cktv/pages/userTemplate-manage/iframe.html";

    /*
     * 2.outer ele of drag Event
     */

   for(var i = 0,len = ele.outerDrag.length; i < len; i++){
       EventUtil.addHandler(ele.outerDrag[i],'mousedown',function (e) {
           drag.mouseDown(e,this);
       });
       EventUtil.addHandler(ele.outerDrag[i],'mousemove',function (e) {
           drag.mouseMove(e,this);
       });
       EventUtil.addHandler(ele.outerDrag[i],'mouseup',function (e) {
           drag.mouseUp1(e,this);
           if(drag.isOut(e,this)){
               drag.outIfm(e,this);
           }else {
               drag.inIfm(e,this);
           }
       });
   }
    /*
    * 3.inner ele of drag event
    */

    /*
    * 4.save html event
    * */
    var savehtml = function () {
        var ifm_content = ele.myIframe.contentWindow.document.getElementsByTagName("div");
        // var vidoeel = ele.myIframe.contentWindow.document.getElementsByTagName('video');
        if(ifm_content.length == 0){
            alert("请添加元素...");
        }else{
            ele.myIframe.style.zoom = 1;
            ele.myIframe.contentWindow.document.body.style.zoom = 2.5;
            var html = ele.myIframe.contentWindow.document.documentElement;
            var params = {
                html: html.innerHTML
            };
            var url = "/Cktv/tpl/saveTplHtml/" + tpl_id;
            ajax.post(url,params,function(getData){
                if (getData.success) {
                    location.href = '/Cktv/pages/display-manage/publish-list.html';
                }else {
                    alert(getData.msg);
                }
            });
        }
    };
    EventUtil.removeHandler(getOuterEle.gi('savehtml'),'click',savehtml);
    EventUtil.addHandler(getOuterEle.gi('savehtml'),'click',savehtml);
};