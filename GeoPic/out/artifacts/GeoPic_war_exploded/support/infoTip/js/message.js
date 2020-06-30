/****************************************************************
 *																*		
 * 						      代码库							*
 *                        www.dmaku.com							*
 *       		  努力创建完善、持续更新插件以及模板			*
 * 																*
****************************************************************/

/**
 * 消息提示组件
 * 
 * 1.调用
 * 字符串类型参数： $.message('成功');
 * 对象型参数：$.message({});
 * 
 * 2.参数详解
 *  message:' 操作成功',    //提示信息
    time:'2000',           //显示时间（默认：2s）
    type:'success',        //显示类型，包括4种：success.error,info,warning
    showClose:false,       //显示关闭按钮（默认：否）
    autoClose:true,        //是否自动关闭（默认：是）
 * 
 * type:success,error,info,warning
 */

$.extend({
  message: function(options) {
      var defaults={
          message:' 操作成功',
          time:'2000',
          type:'success',
          showClose:false,
          autoClose:true,
          onClose:function(){}
      };
      
      if(typeof options === 'string'){
          defaults.message=options;
      }
      if(typeof options === 'object'){
          defaults=$.extend({},defaults,options);
      }
      //message模版
      var templateClose=defaults.showClose?'<a class="c-message--close">×</a>':'';
      var template='<div class="c-message messageFadeInDown">'+
          '<i class=" c-message--icon c-message--'+defaults.type+'"></i>'+
          templateClose+
          '<div class="c-message--tip">'+defaults.message+'</div>'+
      '</div>';
      var _this=this;
      var $body=$('body');
      var $message=$(template);
      var timer;
      var closeFn,removeFn;
      //关闭
      closeFn=function(){
          $message.addClass('messageFadeOutUp');
          $message.one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend',function(){
              removeFn();
          })
      };
      //移除
      removeFn=function(){
          $message.remove();
          defaults.onClose(defaults);
          clearTimeout(timer);
      };
      //移除所有
      $('.c-message').remove();
      $body.append($message);
      //居中
      $message.css({
          'margin-left':'-'+$message.width()/2+'px'
      })
      //去除动画类
      $message.one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend',function(){
          $message.removeClass('messageFadeInDown');
      });
      //点击关闭
      $body.on('click','.c-message--close',function(e){
          closeFn();
      });
      //自动关闭
      if(defaults.autoClose){
          timer=setTimeout(function(){
              closeFn();
          },defaults.time)
      }
  }
});console.log("\u002f\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u000d\u000a\u0020\u002a\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u002a\u0009\u0009\u000d\u000a\u0020\u002a\u0020\u0009\u0009\u0009\u0009\u0009\u0009\u0020\u0020\u0020\u0020\u0020\u0020\u4ee3\u7801\u5e93\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u002a\u000d\u000a\u0020\u002a\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0077\u0077\u0077\u002e\u0064\u006d\u0061\u006b\u0075\u002e\u0063\u006f\u006d\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u002a\u000d\u000a\u0020\u002a\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0009\u0009\u0020\u0020\u52aa\u529b\u521b\u5efa\u5b8c\u5584\u3001\u6301\u7eed\u66f4\u65b0\u63d2\u4ef6\u4ee5\u53ca\u6a21\u677f\u0009\u0009\u0009\u002a\u000d\u000a\u0020\u002a\u0020\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u0009\u002a\u000d\u000a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002a\u002f");