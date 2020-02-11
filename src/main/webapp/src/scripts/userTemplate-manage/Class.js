/* Simple JavaScript Inheritance
 * By John Resig http://ejohn.org/
 * MIT Licensed.
 */
// Inspired by base2 and Prototype
(function(){
    var initializing = false,
        fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

    // The base Class implementation (does nothing)
    //这里为甚么要用this.来声明一个构造函数,如果用var呢?
    this.Class = function(){
        console.log('This is a Constructed function!');
    };

    // Create a new Class that inherits from this class
    //为构造函数添加了一个extend方法
    //这个extend函数为什么不在构造函数里面写?如果在里面写结果是一样的么?
    //参数prop是一个对象,
    Class.extend = function(prop) {
        //谁调用了这个函数,this指的就是谁
        //_super是一个原型对象,调用了extend方法的构造函数的原型对象
        var _super = this.prototype;

        // Instantiate a base class (but only create the instance,
        // don't run the init constructor)
        initializing = true;
        var prototype = new this();//谁调用了这个函数this指的就是谁,实例化了一个调用了extend方法的构造函数,prototype是一个实例
        initializing = false;


        // Copy the properties over onto the new prototype
        for (var name in prop) {//对参数对象里面的属性进行遍历
            // Check if we're overwriting an existing function
            //注意prototype是其它构造函数的实例,检查是否在调用了extend方法的构造函数的实例上重写了该构造函数内部的方法
            //_super是一个原型对象

            //prop[name]传进来的构造函数的属性
            //prototype[name]传进来的构造函数的实例上的属性
            //_super[name]传进来的构造函数的原型上的属性
            prototype[name] = typeof prop[name] == "function" &&
                              typeof _super[name] == "function" && fnTest.test(prop[name]) ?
                (function(name, fn){
                    return function() {
                        var tmp = this._super;//这个this指的是什么?

                        // Add a new ._super() method that is the same method
                        // but on the super-class
                        this._super = _super[name];

                        // The method only need to be bound temporarily, so we
                        // remove it when we're done executing
                        var ret = fn.apply(this, arguments);
                        this._super = tmp;

                        return ret;
                    };
                })(name, prop[name]) :
                prop[name];
        }

        // The dummy class constructor
        //Class构造函数的一个样本
        function Class1() {
            // All construction is actually done in the init method
            if ( !initializing && this.init )
                this.init.apply(this, arguments);
        }

        // Populate our constructed prototype object
        Class1.prototype = prototype;

        // Enforce the constructor to be what we expect
        Class1.prototype.constructor = Class1;

        // And make this class extendable
        Class1.extend = arguments.callee;

        return Class1;
    };
})();

var Person = Class.extend({
    init: function () {
        console.log('Person init');
    },
    _super: function () {
        console.log('Person _super');
    }
});

var Gyh = Person.extend({
    init: function () {
        console.log('Gyh init');
    }
});
new Gyh();