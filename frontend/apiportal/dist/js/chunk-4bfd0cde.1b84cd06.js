(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4bfd0cde"],{"1a3b":function(t,e){var n,i="function"===typeof setImmediate;n=i?function(t){setImmediate(t)}:function(t){setTimeout(t,0)},t.exports=n},"21b7":function(t,e,n){"use strict";var i=n("1a3b");t.exports=function(t,e,n){t&&i((function(){t.apply(n||null,e||[])}))}},"3f31":function(t,e,n){"use strict";(function(e){var i=n("6c1b"),r=n("62cf"),a=e.document,o=l,s=d,c=[];function l(t,e,n,i){return t.addEventListener(e,n,i)}function u(t,e,n){return t.attachEvent("on"+e,v(t,e,n))}function d(t,e,n,i){return t.removeEventListener(e,n,i)}function m(t,e,n){var i=g(t,e,n);if(i)return t.detachEvent("on"+e,i)}function p(t,e,n){var o=-1===r.indexOf(e)?c():s();function s(){var t;return a.createEvent?(t=a.createEvent("Event"),t.initEvent(e,!0,!0)):a.createEventObject&&(t=a.createEventObject()),t}function c(){return new i(e,{detail:n})}t.dispatchEvent?t.dispatchEvent(o):t.fireEvent("on"+e,o)}function f(t,n,i){return function(n){var r=n||e.event;r.target=r.target||r.srcElement,r.preventDefault=r.preventDefault||function(){r.returnValue=!1},r.stopPropagation=r.stopPropagation||function(){r.cancelBubble=!0},r.which=r.which||r.keyCode,i.call(t,r)}}function v(t,e,n){var i=g(t,e,n)||f(t,e,n);return c.push({wrapper:i,element:t,type:e,fn:n}),i}function g(t,e,n){var i=h(t,e,n);if(i){var r=c[i].wrapper;return c.splice(i,1),r}}function h(t,e,n){var i,r;for(i=0;i<c.length;i++)if(r=c[i],r.element===t&&r.type===e&&r.fn===n)return i}e.addEventListener||(o=u,s=m),t.exports={add:o,remove:s,fabricate:p}}).call(this,n("c8ba"))},"612e":function(t,e,n){"use strict";(function(e){var i=n("f3f2"),r=n("3f31"),a=n("9fac"),o=document,s=o.documentElement;function c(t,e){var n,c,m,x,k,L,_,E,R,S,U,D=arguments.length;1===D&&!1===Array.isArray(t)&&(e=t,t=[]);var N,O=null,T=e||{};void 0===T.moves&&(T.moves=v),void 0===T.accepts&&(T.accepts=v),void 0===T.invalid&&(T.invalid=J),void 0===T.containers&&(T.containers=t||[]),void 0===T.isContainer&&(T.isContainer=f),void 0===T.copy&&(T.copy=!1),void 0===T.copySortSource&&(T.copySortSource=!1),void 0===T.revertOnSpill&&(T.revertOnSpill=!1),void 0===T.removeOnSpill&&(T.removeOnSpill=!1),void 0===T.direction&&(T.direction="vertical"),void 0===T.ignoreInputTextSelection&&(T.ignoreInputTextSelection=!0),void 0===T.mirrorContainer&&(T.mirrorContainer=o.body);var B=i({containers:T.containers,start:H,end:z,cancel:Z,remove:W,destroy:V,canMove:$,dragging:!1});return!0===T.removeOnSpill&&B.on("over",rt).on("out",at),P(),B;function I(t){return-1!==B.containers.indexOf(t)||T.isContainer(t)}function P(t){var e=t?"remove":"add";l(s,e,"mousedown",j),l(s,e,"mouseup",G)}function X(t){var e=t?"remove":"add";l(s,e,"mousemove",A)}function F(t){var e=t?"remove":"add";r[e](s,"selectstart",Y),r[e](s,"click",Y)}function V(){P(!0),G({})}function Y(t){N&&t.preventDefault()}function j(t){L=t.clientX,_=t.clientY;var e=1!==u(t)||t.metaKey||t.ctrlKey;if(!e){var n=t.target,i=M(n);i&&(N=i,X(),"mousedown"===t.type&&(y(n)?n.focus():t.preventDefault()))}}function A(t){if(N)if(0!==u(t)){if(void 0===t.clientX||t.clientX!==L||void 0===t.clientY||t.clientY!==_){if(T.ignoreInputTextSelection){var e=w("clientX",t),n=w("clientY",t),i=o.elementFromPoint(e,n);if(y(i))return}var r=N;X(!0),F(),z(),K(r);var s=d(m);x=w("pageX",t)-s.left,k=w("pageY",t)-s.top,a.add(S||m,"gu-transit"),ot(),it(t)}}else G({})}function M(t){if((!B.dragging||!n)&&!I(t)){var e=t;while(b(t)&&!1===I(b(t))){if(T.invalid(t,e))return;if(t=b(t),!t)return}var i=b(t);if(i&&!T.invalid(t,e)){var r=T.moves(t,i,e,C(t));if(r)return{item:t,source:i}}}}function $(t){return!!M(t)}function H(t){var e=M(t);e&&K(e)}function K(t){ut(t.item,t.source)&&(S=t.item.cloneNode(!0),B.emit("cloned",S,t.item,"copy")),c=t.source,m=t.item,E=R=C(t.item),B.dragging=!0,B.emit("drag",m,c)}function J(){return!1}function z(){if(B.dragging){var t=S||m;Q(t,b(t))}}function q(){N=!1,X(!0),F(!0)}function G(t){if(q(),B.dragging){var e=S||m,i=w("clientX",t),r=w("clientY",t),a=p(n,i,r),o=nt(a,i,r);o&&(S&&T.copySortSource||!S||o!==c)?Q(e,o):T.removeOnSpill?W():Z()}}function Q(t,e){var n=b(t);S&&T.copySortSource&&e===c&&n.removeChild(m),et(e)?B.emit("cancel",t,c,c):B.emit("drop",t,e,c,R),tt()}function W(){if(B.dragging){var t=S||m,e=b(t);e&&e.removeChild(t),B.emit(S?"cancel":"remove",t,e,c),tt()}}function Z(t){if(B.dragging){var e=arguments.length>0?t:T.revertOnSpill,n=S||m,i=b(n),r=et(i);!1===r&&e&&(S?i&&i.removeChild(S):c.insertBefore(n,E)),r||e?B.emit("cancel",n,c,c):B.emit("drop",n,i,c,R),tt()}}function tt(){var t=S||m;q(),st(),t&&a.rm(t,"gu-transit"),U&&clearTimeout(U),B.dragging=!1,O&&B.emit("out",t,O,c),B.emit("dragend",t),c=m=S=E=R=U=O=null}function et(t,e){var i;return i=void 0!==e?e:n?R:C(S||m),t===c&&i===E}function nt(t,e,n){var i=t;while(i&&!r())i=b(i);return i;function r(){var r=I(i);if(!1===r)return!1;var a=ct(i,t),o=lt(i,a,e,n),s=et(i,o);return!!s||T.accepts(m,i,c,o)}}function it(t){if(n){t.preventDefault();var e=w("clientX",t),i=w("clientY",t),r=e-x,a=i-k;n.style.left=r+"px",n.style.top=a+"px";var o=S||m,s=p(n,e,i),l=nt(s,e,i),u=null!==l&&l!==O;(u||null===l)&&(y(),O=l,h());var d=b(o);if(l!==c||!S||T.copySortSource){var f,v=ct(l,s);if(null!==v)f=lt(l,v,e,i);else{if(!0!==T.revertOnSpill||S)return void(S&&d&&d.removeChild(o));f=E,l=c}(null===f&&u||f!==o&&f!==C(o))&&(R=f,l.insertBefore(o,f),B.emit("shadow",o,l,c))}else d&&d.removeChild(o)}function g(t){B.emit(t,o,O,c)}function h(){u&&g("over")}function y(){O&&g("out")}}function rt(t){a.rm(t,"gu-hide")}function at(t){B.dragging&&a.add(t,"gu-hide")}function ot(){if(!n){var t=m.getBoundingClientRect();n=m.cloneNode(!0),n.style.width=g(t)+"px",n.style.height=h(t)+"px",a.rm(n,"gu-transit"),a.add(n,"gu-mirror"),T.mirrorContainer.appendChild(n),l(s,"add","mousemove",it),a.add(T.mirrorContainer,"gu-unselectable"),B.emit("cloned",n,m,"mirror")}}function st(){n&&(a.rm(T.mirrorContainer,"gu-unselectable"),l(s,"remove","mousemove",it),b(n).removeChild(n),n=null)}function ct(t,e){var n=e;while(n!==t&&b(n)!==t)n=b(n);return n===s?null:n}function lt(t,e,n,i){var r="horizontal"===T.direction,a=e!==t?s():o();return a;function o(){var e,a,o,s=t.children.length;for(e=0;e<s;e++){if(a=t.children[e],o=a.getBoundingClientRect(),r&&o.left+o.width/2>n)return a;if(!r&&o.top+o.height/2>i)return a}return null}function s(){var t=e.getBoundingClientRect();return c(r?n>t.left+g(t)/2:i>t.top+h(t)/2)}function c(t){return t?C(e):e}}function ut(t,e){return"boolean"===typeof T.copy?T.copy:T.copy(t,e)}}function l(t,n,i,a){var o={mouseup:"touchend",mousedown:"touchstart",mousemove:"touchmove"},s={mouseup:"pointerup",mousedown:"pointerdown",mousemove:"pointermove"},c={mouseup:"MSPointerUp",mousedown:"MSPointerDown",mousemove:"MSPointerMove"};e.navigator.pointerEnabled?r[n](t,s[i],a):e.navigator.msPointerEnabled?r[n](t,c[i],a):(r[n](t,o[i],a),r[n](t,i,a))}function u(t){if(void 0!==t.touches)return t.touches.length;if(void 0!==t.which&&0!==t.which)return t.which;if(void 0!==t.buttons)return t.buttons;var e=t.button;return void 0!==e?1&e?1:2&e?3:4&e?2:0:void 0}function d(t){var e=t.getBoundingClientRect();return{left:e.left+m("scrollLeft","pageXOffset"),top:e.top+m("scrollTop","pageYOffset")}}function m(t,n){return"undefined"!==typeof e[n]?e[n]:s.clientHeight?s[t]:o.body[t]}function p(t,e,n){var i,r=t||{},a=r.className;return r.className+=" gu-hide",i=o.elementFromPoint(e,n),r.className=a,i}function f(){return!1}function v(){return!0}function g(t){return t.width||t.right-t.left}function h(t){return t.height||t.bottom-t.top}function b(t){return t.parentNode===o?null:t.parentNode}function y(t){return"INPUT"===t.tagName||"TEXTAREA"===t.tagName||"SELECT"===t.tagName||x(t)}function x(t){return!!t&&("false"!==t.contentEditable&&("true"===t.contentEditable||x(b(t))))}function C(t){return t.nextElementSibling||e();function e(){var e=t;do{e=e.nextSibling}while(e&&1!==e.nodeType);return e}}function k(t){return t.targetTouches&&t.targetTouches.length?t.targetTouches[0]:t.changedTouches&&t.changedTouches.length?t.changedTouches[0]:t}function w(t,e){var n=k(e),i={pageX:"clientX",pageY:"clientY"};return t in i&&!(t in n)&&i[t]in n&&(t=i[t]),n[t]}t.exports=c}).call(this,n("c8ba"))},"62cf":function(t,e,n){"use strict";(function(e){var n=[],i="",r=/^on/;for(i in e)r.test(i)&&n.push(i.slice(2));t.exports=n}).call(this,n("c8ba"))},"6c1b":function(t,e,n){(function(e){var n=e.CustomEvent;function i(){try{var t=new n("cat",{detail:{foo:"bar"}});return"cat"===t.type&&"bar"===t.detail.foo}catch(e){}return!1}t.exports=i()?n:"function"===typeof document.createEvent?function(t,e){var n=document.createEvent("CustomEvent");return e?n.initCustomEvent(t,e.bubbles,e.cancelable,e.detail):n.initCustomEvent(t,!1,!1,void 0),n}:function(t,e){var n=document.createEventObject();return n.type=t,e?(n.bubbles=Boolean(e.bubbles),n.cancelable=Boolean(e.cancelable),n.detail=e.detail):(n.bubbles=!1,n.cancelable=!1,n.detail=void 0),n}}).call(this,n("c8ba"))},"9fac":function(t,e,n){"use strict";var i={},r="(?:^|\\s)",a="(?:\\s|$)";function o(t){var e=i[t];return e?e.lastIndex=0:i[t]=e=new RegExp(r+t+a,"g"),e}function s(t,e){var n=t.className;n.length?o(e).test(n)||(t.className+=" "+e):t.className=e}function c(t,e){t.className=t.className.replace(o(e)," ").trim()}t.exports={add:s,rm:c}},c3cd:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"app"}},[n("section",{staticClass:"section"},[t._m(0),n("div",{staticClass:"board-wrapper pt-5"},[n("Kanban",{attrs:{stages:t.statuses,blocks:t.blocks}},[t._l(t.statuses,(function(e){return n("div",{key:e,attrs:{slot:e},slot:e},[n("h2",[t._v(t._s(e))])])})),t._l(t.blocks,(function(e){return n("div",{key:e.id,attrs:{slot:e.id},slot:e.id},[n("div",[n("b-progress",{attrs:{value:25,variant:e.progressVariant}}),n("div",{staticClass:"d-flex justify-content-between"},[n("p",{staticClass:"task-date"},[t._v(t._s(e.date))]),n("b-dropdown",{staticClass:"transparent",attrs:{right:""}},[n("template",{slot:"button-content"},[n("i",{staticClass:"ti-more"})]),n("b-dropdown-item",[t._v("Edit")]),n("b-dropdown-item",[t._v("Delete")])],2)],1),n("h4",{staticClass:"task-title"},[t._v(t._s(e.taskName))]),n("div",{staticClass:"image-grouped"},[n("img",{attrs:{src:e.img1URL,alt:"profile image"}}),n("img",{attrs:{src:e.img2URL,alt:"profile image"}}),n("img",{attrs:{src:e.img3URL,alt:"profile image"}})]),n("div",{staticClass:"d-lg-flex justify-content-between"},[n("b-badge",{attrs:{variant:e.progressVariant}},[t._v(t._s(e.priority))]),n("p",{staticClass:"due-date"},[t._v(t._s(e.dueDate))])],1)],1)])})),t._l(t.statuses,(function(e){return n("div",{key:e,attrs:{slot:"footer-"+e},slot:"footer-"+e},[n("span",{attrs:{href:""}},[t._v("+ Add new block")])])}))],2)],1)])])},r=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"d-flex flex-column flex-md-row align-items-center"},[n("h4",{staticClass:"mb-md-0 mb-4"},[t._v("Design Board")]),n("div",{staticClass:"wrapper d-flex align-items-center"},[n("div",{staticClass:"image-grouped ml-md-4"},[n("img",{attrs:{src:"https://via.placeholder.com/36x36",alt:"profile image"}}),n("img",{attrs:{src:"https://via.placeholder.com/36X36",alt:"profile image"}}),n("img",{attrs:{src:"https://via.placeholder.com/36X36",alt:"profile image"}})]),n("button",{staticClass:"btn private-btn btn-fw ml-4",attrs:{type:"button"}},[n("i",{staticClass:"ti-lock"}),t._v("Private ")])]),n("div",{staticClass:"wrapper ml-md-auto d-flex flex-column flex-md-row kanban-toolbar ml-n2 ml-md-0 mt-4 mt-md-0"},[n("div",{staticClass:"d-flex"},[n("button",{staticClass:"btn btn-icons btn-light d-none d-xl-block",attrs:{type:"button"}},[n("i",{staticClass:"ti-zoom-in"})]),n("button",{staticClass:"btn btn-icons btn-light d-none d-xl-block",attrs:{type:"button"}},[n("i",{staticClass:"ti-filter"})]),n("button",{staticClass:"btn btn-icons d-none d-xl-block btn-light",attrs:{type:"button"}},[n("i",{staticClass:"ti-bell"})]),n("button",{staticClass:"btn btn-gradient-primary",attrs:{type:"button"}},[t._v("Boards")])]),n("div",{staticClass:"d-flex mt-4 mt-md-0"},[n("button",{staticClass:"btn btn-gradient-success",attrs:{type:"button"}},[t._v("Create New")])])])])}],a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"drag-container"},[n("ul",{staticClass:"drag-list"},t._l(t.stages,(function(e){var i;return n("li",{key:e,staticClass:"drag-column board-portlet",class:(i={},i["drag-column-"+e]=!0,i)},[n("span",{staticClass:"drag-column-header"},[t._t(e,[n("h2",[t._v(t._s(e))])])],2),n("div",{staticClass:"drag-options"}),n("ul",{ref:"list",refInFor:!0,staticClass:"drag-inner-list portlet-card-list",attrs:{"data-status":e}},t._l(t.getBlocks(e),(function(e){return n("li",{key:e.id,staticClass:"drag-item portlet-card",attrs:{"data-block-id":e.id}},[t._t(e.id,[n("strong",[t._v(t._s(e.status))]),n("div",[t._v(t._s(e.id))])])],2)})),0),n("div",{staticClass:"drag-column-footer add-portlet"},[t._t("footer-"+e)],2)])})),0)])},o=[],s=(n("4de4"),n("612e")),c=n.n(s),l={name:"KanbanBoard",props:{stages:{},blocks:{}},data:function(){return{}},computed:{localBlocks:function(){return this.blocks}},methods:{getBlocks:function(t){return this.localBlocks.filter((function(e){return e.status===t}))}},updated:function(){this.drake.containers=this.$refs.list},mounted:function(){var t=this;this.drake=c()(this.$refs.list).on("drag",(function(t){t.classList.add("is-moving")})).on("drop",(function(e,n){var i=0;for(i=0;i<n.children.length;i+=1)if(n.children[i].classList.contains("is-moving"))break;t.$emit("update-block",e.dataset.blockId,n.dataset.status,i)})).on("dragend",(function(t){t.classList.remove("is-moving"),window.setTimeout((function(){t.classList.add("is-moved"),window.setTimeout((function(){t.classList.remove("is-moved")}),600)}),100)}))}},u=l,d=n("2877"),m=Object(d["a"])(u,a,o,!1,null,null,null),p=m.exports,f={name:"gallery",components:{Kanban:p},data:function(){return{statuses:["Todo","In-progress","On Hold"],blocks:[{id:"task-1",status:"Todo",name:"Rebecca young",taskName:"Server gateway",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"success"},{id:"task-2",status:"In-progress",name:"Jacob march",taskName:"Server gateway",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"info"},{id:"task-3",status:"On Hold",name:"Catherine",taskName:"Software update",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"dark"},{id:"task-4",status:"In-progress",name:"Network maintenance",taskName:"Director",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"danger"},{id:"task-5",name:"Keto Philip",status:"On Hold",taskName:"Attached Preview Icon",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"danger"},{id:"task-6",status:"Todo",name:"Jacob Stephen",taskName:"UI Design Started",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"info"},{id:"task-7",name:"March Creg",status:"Todo",taskName:"New IOS Design",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"success"},{id:"task-8",status:"In progress",name:"Peter Beckham",taskName:"Retail Order",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"secondar"},{id:"task-9",status:"On Hold",name:"John Doe",taskName:"HTML/CSS templates",date:"20 Feb 2019",img1URL:"https://via.placeholder.com/27x27",img2URL:"https://via.placeholder.com/27x27",img3URL:"https://via.placeholder.com/27x27",priority:"important",dueDate:"Due 10 days",progressVariant:"primary"}]}}},v=f,g=Object(d["a"])(v,i,r,!1,null,null,null);e["default"]=g.exports},f13f:function(t,e){t.exports=function(t,e){return Array.prototype.slice.call(t,e)}},f3f2:function(t,e,n){"use strict";var i=n("f13f"),r=n("21b7");t.exports=function(t,e){var n=e||{},a={};return void 0===t&&(t={}),t.on=function(e,n){return a[e]?a[e].push(n):a[e]=[n],t},t.once=function(e,n){return n._once=!0,t.on(e,n),t},t.off=function(e,n){var i=arguments.length;if(1===i)delete a[e];else if(0===i)a={};else{var r=a[e];if(!r)return t;r.splice(r.indexOf(n),1)}return t},t.emit=function(){var e=i(arguments);return t.emitterSnapshot(e.shift()).apply(this,e)},t.emitterSnapshot=function(e){var o=(a[e]||[]).slice(0);return function(){var a=i(arguments),s=this||t;if("error"===e&&!1!==n.throws&&!o.length)throw 1===a.length?a[0]:a;return o.forEach((function(i){n.async?r(i,a,s):i.apply(s,a),i._once&&t.off(e,i)})),t}},t}}}]);