webpackJsonp([21],{USnC:function(t,s,e){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var a={name:"MESSAGE",data:function(){return{labels:{ok:""},iconClass:"popup-img "+this.messageType}},props:{messageText:{type:String},messageType:{type:String}},methods:{initialize:function(){this.$util.setLabels(this.$options.name,this.labels)},popupClose:function(){this.$parent.hideMessage()}},created:function(){this.initialize()}},i={render:function(){var t=this,s=t.$createElement,e=t._self._c||s;return e("div",{staticClass:"dimmed-film",staticStyle:{"z-index":"9999"}},[e("div",{staticClass:"tiny-popup-wrap"},[e("span",{class:t.iconClass}),t._v(" "),e("p",{staticClass:"paragraph"},[e("strong",{staticClass:"normal",domProps:{textContent:t._s(t.messageText)}})]),t._v(" "),e("div",{staticClass:"popup-btn-center"},[e("b-button",{staticClass:"px-3 sm",attrs:{variant:"primary"},on:{click:t.popupClose}},[t._v(t._s(t.labels.ok))])],1)])])},staticRenderFns:[]},n=e("VU/8")(a,i,!1,null,null,null);s.default=n.exports}});
//# sourceMappingURL=21.583e831591e5401e92fb.js.map