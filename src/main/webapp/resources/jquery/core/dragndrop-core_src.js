/*
 * dragndrop
 * version: 1.0.0 (05/13/2009)
 * @ jQuery v1.2.*
 *
 * Licensed under the GPL:
 *   http://gplv3.fsf.org
 *
 * Copyright 2008, 2009 Jericho [ thisnamemeansnothing[at]gmail.com ]
 *  usage:
 *
 */
(function ($) {
    $.extend($.fn, {
        getCss: function (key) {
            var v = parseInt(this.css(key));
            if (isNaN(v))
                return false;
            return v;
        }
    });
    /**
     * 拖拽功能
     * @param opts
     * @returns {*}
     * @constructor
     */
    $.fn.Drags = function (opts) {
        var ps = $.extend({
            zIndex: 20,//控制层叠高度
            opacity: .7,//拖动时的透明度
            handler: null,//拖拽柄, 比如一般的窗口, 标题部分就为拖拽柄, 当点击拖拽柄使会拖拽整个拖拽对象, 默认值为 null, 必须指定, 可为jQuery筛选器对象，如 '.drag' 或 '#drag' , 也可以为jQuery对象，如 $('.drag')
            /**
             * 拖拽发生时的触发事件, 默认值为 function(e) { }, 参数e为拖拽对象参数, 如可使用e.pageX, e.pageY来获取当前对象位置,
             * 使用cookie存储, 同样也可通过 e.data.dragData获取拖拽对象其他参数, 具体内容可查看源码,
             */
            onMove: function () {
            },
            /**
             * 拖拽结束时触发的事件, 默认值为 function(e) { }
             */
            onDrop: function () {
            }
        }, opts);
        var dragndrop = {
            drag: function (e) {
                var dragData = e.data.dragData;
                dragData.target.css({
                    left: dragData.left + e.pageX - dragData.offLeft,
                    top: dragData.top + e.pageY - dragData.offTop
                });
                dragData.handler.css({cursor: 'move'});
                dragData.onMove(e);
            },
            drop: function (e) {
                var dragData = e.data.dragData;
                dragData.target.css(dragData.oldCss); //.css({ 'opacity': '' });
                dragData.handler.css('cursor', dragData.oldCss.cursor);
                dragData.onDrop(e);
                $(document).unbind('mousemove', dragndrop.drag);
                $(document).unbind('mouseup', dragndrop.drop);
            }
        };
        return this.each(function () {
            var me = this;
            var handler = null;
            if (typeof ps.handler == 'undefined' || ps.handler == null)
                handler = $(me);
            else
                handler = (typeof ps.handler == 'string' ? $(ps.handler, this) : ps.handler);
            handler.bind('mousedown', {e: me}, function (s) {
                var target = $(s.data.e);
                var oldCss = {};
                if (target.css('position') != 'absolute') {
                    try {
                        target.position(oldCss);
                    } catch (ex) {

                    }
                    target.css('position', 'absolute');
                }
                oldCss.cursor = target.css('cursor') || 'default';
                oldCss.opacity = target.getCss('opacity') || 1;
                var dragData = {
                    left: oldCss.left || target.getCss('left') || 0,
                    top: oldCss.top || target.getCss('top') || 0,
                    width: target.width() || target.getCss('width'),
                    height: target.height() || target.getCss('height'),
                    offLeft: s.pageX,
                    offTop: s.pageY,
                    oldCss: oldCss,
                    onMove: ps.onMove,
                    onDrop: ps.onDrop,
                    handler: handler,
                    target: target
                };
                target.css('opacity', ps.opacity);
                $(document).bind('mousemove', {dragData: dragData}, dragndrop.drag);
                $(document).bind('mouseup', {dragData: dragData}, dragndrop.drop);
            });
        });
    };

    /**
     * 重置大小功能
     * @param opts
     * @returns {*}
     */
    $.fn.resize = function (opts) {
        var ps = $.extend({
            zIndex: 20,//控制层叠高度
            opacity: .7,//拖动时的透明度
            handler: null,//拖拽柄, 比如一般的窗口, 标题部分就为拖拽柄, 当点击拖拽柄使会拖拽整个拖拽对象, 默认值为 null, 必须指定, 可为jQuery筛选器对象，如 '.drag' 或 '#drag' , 也可以为jQuery对象，如 $('.drag')
            minWidth:100,
            minHeight:100,
            /**
             * 拖拽发生时的触发事件, 默认值为 function(e) { }, 参数e为拖拽对象参数, 如可使用e.pageX, e.pageY来获取当前对象位置,
             * 使用cookie存储, 同样也可通过 e.data.dragData获取拖拽对象其他参数, 具体内容可查看源码,
             */
            onMove: function () {
            },
            /**
             * 拖拽结束时触发的事件, 默认值为 function(e) { }
             */
            onDrop: function () {
            }
        }, opts);
        var dragndrop = {
            drag: function (e) {
                var dragData = e.data.dragData;
                var movedWidth=e.pageX - dragData.offLeft;
                var movedHeight=e.pageY - dragData.offTop;

                var newWidth=dragData.width+movedWidth;
                var newHeight=dragData.height+movedHeight;

                if(newWidth<ps.minWidth){
                    newWidth=ps.minWidth;
                }

                if(newHeight<ps.minHeight){
                    newHeight=ps.minHeight;
                }

                dragData.target.width(newWidth);
                dragData.target.height(newHeight);
                dragData.onMove(e);
            },
            drop: function (e) {
                var dragData = e.data.dragData;
                dragData.target.css(dragData.oldCss);
                dragData.onDrop(e);
                $(document).unbind('mousemove', dragndrop.drag);
                $(document).unbind('mouseup', dragndrop.drop);
            }
        };
        return this.each(function () {
            var me = this;
            var handler = null;
            if (typeof ps.handler == 'undefined' || ps.handler == null)
                handler = $(me);
            else
                handler = (typeof ps.handler == 'string' ? $(ps.handler, this) : ps.handler);

            handler.bind('mousedown', {e: me}, function (s) {
                var target = $(s.data.e);
                var oldCss = {};
                if (target.css('position') != 'absolute') {
                    try {
                        target.position(oldCss);
                    } catch (ex) {

                    }
                    target.css('position', 'absolute');
                }
                oldCss.cursor = target.css('cursor') || 'default';
                oldCss.opacity = target.getCss('opacity') || 1;

                //把初始的样式缓存起来，以在拖动时提高执行性能
                var dragData = {
                    left: oldCss.left || target.getCss('left') || 0,
                    top: oldCss.top || target.getCss('top') || 0,
                    width: target.width() || target.getCss('width'),
                    height: target.height() || target.getCss('height'),
                    offLeft: s.pageX,
                    offTop: s.pageY,
                    oldCss: oldCss,
                    onMove: ps.onMove,
                    onDrop: ps.onDrop,
                    handler: handler,
                    target: target
                };
                target.css('opacity', ps.opacity);
                $(document).bind('mousemove', {dragData: dragData}, dragndrop.drag);
                $(document).bind('mouseup', {dragData: dragData}, dragndrop.drop);
            });
        });
    }
})(jQuery);


