webpackJsonp([31],{XRLU:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s={props:["labels","showDialogFlagProp","param"],data:function(){return{listGrid:{tableProps:{border:!1,stripe:!0,headerCellStyle:function(){return"font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px"}},currentPage:1,rowIndex:-1,pageProps:{pageSize:5,pageSizes:[5,10,15]},data:[],filters:[{prop:["ciId","classstructureNm","ciname","description"],value:""}],filteredData:[],titles:[{prop:"ciId",width:"150px"},{prop:"classstructureNm",width:"150px"},{prop:"ciname",width:"150px"},{prop:"description",width:"150px"},{prop:"type",width:"150px"},{prop:"managerNm",width:"150px"}],currentData:{},total:0},orgId:"",selectedItem:[],showDialogFlag:!1,searchParam:""}},watch:{showDialogFlagProp:function(t){this.showDialogFlag=t,t&&(this.searchParam="",this.listGrid.filters[0].value="",this.listGrid.currentPage=1,this.getListData())}},methods:{initialize:function(){this.$util.setLabels(this.$options.name,this.labels),this.orgId=this.$store.getters.getUser.orgId},getListData:function(){var t=this;this.$http.get("/episode/api/asset/licensemgmnt/licenseuse/serverlist?orgId="+this.orgId+"&swcatId="+this.param.swcatId).then(function(e){t.listGrid.data=e.data}).catch(function(t){console.log(t)})},enterSearch:function(){this.listGrid.filters[0].value=this.searchParam},close:function(){this.$emit("close")},moveCI:function(t){this.$util.move(this,"CI",t.id)}},created:function(){this.initialize()}},i={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("el-dialog",{attrs:{title:t.labels.useServerDialogTitle,visible:t.showDialogFlag,width:"1000px"},on:{"update:visible":function(e){t.showDialogFlag=e},close:t.close}},[s("b-row",[s("b-col",[s("b-row",[s("b-col",{attrs:{sm:"12"}},[s("div",{staticClass:"search-component"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.searchParam,expression:"searchParam"}],staticClass:"form-control mt-1",staticStyle:{width:"100%"},attrs:{placeholder:t.labels.search},domProps:{value:t.searchParam},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.enterSearch(e)},input:function(e){e.target.composing||(t.searchParam=e.target.value)}}})])])],1),t._v(" "),s("b-row",[s("b-col",{attrs:{sm:"12"}},[s("p",{staticClass:"grid-line"}),t._v(" "),s("data-tables",{staticStyle:{width:"100%"},attrs:{layout:"table, pagination","table-props":t.listGrid.tableProps,"page-size":t.listGrid.pageProps.pageSize,data:t.listGrid.data,filters:t.listGrid.filters,"pagination-props":t.listGrid.pageProps,"current-page":t.listGrid.currentPage},on:{"update:currentPage":function(e){return t.$set(t.listGrid,"currentPage",e)},"update:current-page":function(e){return t.$set(t.listGrid,"currentPage",e)}}},[s("el-table-column",{attrs:{prop:t.listGrid.titles[0].prop,label:t.labels.ciId,sortable:"custom"}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[1].prop,label:t.labels.class,sortable:"custom"}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[2].prop,label:t.labels.name,sortable:"custom"}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[3].prop,label:t.labels.description,sortable:"custom"}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[4].prop,label:t.labels.citype,sortable:"custom"}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[5].prop,label:t.labels.manager,sortable:"custom"}}),t._v(" "),s("el-table-column",{attrs:{align:"right",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[s("el-button",{staticStyle:{padding:"0",width:"30px",height:"30px"},attrs:{title:t.labels.moveci},on:{click:function(a){return t.moveCI(e.row)}}},[s("img",{attrs:{src:a("XNDL"),width:"60%"}})])]}}])})],1)],1)],1)],1)],1),t._v(" "),s("b-row",[s("div",{staticClass:"col mt-2"},[s("div",{staticClass:"d-flex justify-content-end"},[s("b-button",{staticClass:"px-3 sm",attrs:{variant:"primary"},on:{click:t.close}},[t._v(t._s(t.labels.close))])],1)])])],1)},staticRenderFns:[]},l={props:["labels","showDialogFlagProp","param"],data:function(){return{listGrid:{tableProps:{border:!1,stripe:!0,headerCellStyle:function(){return"font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px"}},currentPage:1,rowIndex:-1,pageProps:{pageSize:5,pageSizes:[5,10,15]},data:[],filters:[{prop:["swlicenseId","swlicname","swlictype","purchasetype","swlicstatus"],value:""}],filteredData:[],titles:[{prop:"swlicenseId",width:"140px"},{prop:"swlicname",width:"140px"},{prop:"swlictype",width:"120px"},{prop:"purchasetype",width:"120px"},{prop:"swlicstatus",width:"120px"},{prop:"startDt",width:"200px"},{prop:"endDt",width:"200px"}],currentData:{},total:0},orgId:"",selectedItem:[],showDialogFlag:!1,searchParam:""}},watch:{showDialogFlagProp:function(t){this.showDialogFlag=t,t&&(this.searchParam="",this.listGrid.filters[0].value="",this.listGrid.currentPage=1,this.getListData())}},methods:{initialize:function(){this.$util.setLabels(this.$options.name,this.labels),this.orgId=this.$store.getters.getUser.orgId},getListData:function(){var t=this;this.$http.get("/episode/api/asset/licensemgmnt/license?orgId="+this.orgId+"&searchType=licenseuse&dialogParam="+this.param.swcatId+"&dialogParam2="+this.param.purchaseunit).then(function(e){t.listGrid.data=e.data.data}).catch(function(t){console.log(t)})},enterSearch:function(){this.listGrid.filters[0].value=this.searchParam},close:function(){this.$emit("close")},moveRelation:function(t){this.$util.move(this,"LICENSE",t.id)}},created:function(){this.initialize()}},r={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("el-dialog",{attrs:{title:t.labels.relationLicenseDialogTitle,visible:t.showDialogFlag,width:"1250px"},on:{"update:visible":function(e){t.showDialogFlag=e},close:t.close}},[s("b-row",[s("b-col",[s("b-row",[s("b-col",{attrs:{sm:"12"}},[s("div",{staticClass:"search-component"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.searchParam,expression:"searchParam"}],staticClass:"form-control mt-1",staticStyle:{width:"100%"},attrs:{placeholder:t.labels.search},domProps:{value:t.searchParam},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.enterSearch(e)},input:function(e){e.target.composing||(t.searchParam=e.target.value)}}})])])],1),t._v(" "),s("b-row",[s("b-col",{attrs:{sm:"12"}},[s("p",{staticClass:"grid-line"}),t._v(" "),s("data-tables",{staticStyle:{width:"100%"},attrs:{layout:"table, pagination","table-props":t.listGrid.tableProps,"page-size":t.listGrid.pageProps.pageSize,data:t.listGrid.data,filters:t.listGrid.filters,"pagination-props":t.listGrid.pageProps,"current-page":t.listGrid.currentPage},on:{"update:currentPage":function(e){return t.$set(t.listGrid,"currentPage",e)},"update:current-page":function(e){return t.$set(t.listGrid,"currentPage",e)}}},[s("el-table-column",{attrs:{prop:t.listGrid.titles[0].prop,label:t.labels.swlicenseId,sortable:"custom",width:t.listGrid.titles[0].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[1].prop,label:t.labels.name,sortable:"custom"}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[2].prop,label:t.labels.licensetype,sortable:"custom",width:t.listGrid.titles[2].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[3].prop,label:t.labels.purchasetype,sortable:"custom",width:t.listGrid.titles[3].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[4].prop,label:t.labels.status,sortable:"custom",width:t.listGrid.titles[4].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[5].prop,label:t.labels.startDt,sortable:"custom",width:t.listGrid.titles[5].width},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n                "+t._s(t.$util.dateTimeWithTimeZone(e.row.startDt))+"\n              ")]}}])}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[6].prop,label:t.labels.endDt,sortable:"custom",width:t.listGrid.titles[6].width},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n                "+t._s(t.$util.dateTimeWithTimeZone(e.row.endDt))+"\n              ")]}}])}),t._v(" "),s("el-table-column",{attrs:{align:"right",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[s("el-button",{staticStyle:{padding:"0",width:"30px",height:"30px"},attrs:{title:t.labels.movelic},on:{click:function(a){return t.moveRelation(e.row)}}},[s("img",{attrs:{src:a("XNDL"),width:"60%"}})])]}}])})],1)],1)],1)],1)],1),t._v(" "),s("b-row",[s("div",{staticClass:"col mt-2"},[s("div",{staticClass:"d-flex justify-content-end"},[s("b-button",{staticClass:"px-3 sm",attrs:{variant:"primary"},on:{click:t.close}},[t._v(t._s(t.labels.close))])],1)])])],1)},staticRenderFns:[]},o={name:"LICENSEUSE",components:{LicenseUseServerDialog:a("VU/8")(s,i,!1,null,null,null).exports,LicenseUseRelationDialog:a("VU/8")(l,r,!1,null,null,null).exports,Class:a("lxmB").a},data:function(){return{labels:{classstructure:"",manager:""},listGrid:{tableProps:{border:!1,stripe:!0,"span-method":this.makeSpan,headerCellStyle:function(){return"font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px"}},currentPage:1,rowIndex:-1,pageProps:{pageSize:10,pageSizes:[5,10,15]},data:[],filters:[{prop:[],value:""}],filteredData:[],titles:[{prop:"classstructureNm",width:"250px"},{prop:"publishername",width:"170px"},{prop:"swcatname",width:"300px"},{prop:"swcattype",width:"110px"},{prop:"swcatversion",width:"100px"},{prop:"purchaseunit",width:"150px"},{prop:"purchasenum",width:"110px"},{prop:"usenum",width:"110px"},{prop:"remainnum",width:"110px"},{prop:"usesever",width:"110px"},{prop:"relationlicense",width:"140px"},{prop:"swcatId",width:"140px"}],columns:[],columnNames:[],currentData:{},ciExtNmData:{},total:0,srchCnd:null,searchParamDetail:{swcatIdD:"",classstructureD:"",classstructureNm:"",swcatnameD:"",publishernameD:"",swcattypeD:""}},auth:{showBtnSaveFlag:!1,showBtnDelFlag:!1},comboData:{},orgId:"",activeTab:"list",detailType:"INSERT",searchType:"NORMAL",searchParam:"",validMsg:{},showUseServerDialogFlag:!1,showRelationDialogFlag:!1,visibleSearchDetailFlag:!1,showClassDialogFlag:!1,listGridFlag:!1}},watch:{activeTab:function(t){"list"===t&&this.getListData(this.listGrid.srchCnd)}},computed:{searchDetailBtnClass:function(){var t=!1;for(var e in this.listGrid.searchParamDetail)this.$util.isNull(this.listGrid.searchParamDetail[e])||(t=!0);return t?"filter-button-on":"filter-button"}},methods:{initialize:function(){this.$util.setLabels(this.$options.name,this.labels);for(var t=this.$store.getters.getAuth,e=0;e<t.length;e++)"LICENSE_SAVE"===t[e].authorityId&&(this.auth.showBtnSaveFlag=!0),"LICENSE_DELETE"===t[e].authorityId&&(this.auth.showBtnDelFlag=!0);this.listGridFlag=!0,this.orgId=this.$store.getters.getUser.orgId,this.getInitData()},makeSpan:function(t){if(0===t.columnIndex||1===t.columnIndex||2===t.columnIndex){var e=this.listGrid.data[t.rowIndex],a=this.listGrid.data[t.rowIndex-1],s=1;if(void 0!==a&&e.publishername===a.publishername&&e.swcatname===a.swcatname)return{rowspan:0,colspan:0};for(var i=t.rowIndex;i<this.listGrid.data.length;i++){var l=this.listGrid.data[i];if(e.publishername!==l.publishername||e.swcatname!==l.swcatname)break;s++}return{rowspan:s-1,colspan:1}}},getInitData:function(){},getListData:function(t){var e=this;this.listGrid.srchCnd=t;var a=1,s=10,i=null,l="ascending",r="";this.$util.isNull(t)||(a=t.page,s=t.pageSize,null!=t.sort.prop&&(i=t.sort.prop,l=t.sort.order),r=t.filters[0].value);var o={params:{page:a,pageSize:s,orderBy:i,sort:l,orgId:this.orgId,lang:this.lang,searchParam:r,searchType:this.searchType,swcatIdD:this.listGrid.searchParamDetail.swcatIdD,classstructureD:this.listGrid.searchParamDetail.classstructureD,swcatnameD:this.listGrid.searchParamDetail.swcatnameD,publishernameD:this.listGrid.searchParamDetail.publishernameD,swcattypeD:this.listGrid.searchParamDetail.swcattypeD}};this.$http.get("/episode/api/asset/licensemgmnt/licenseuse",o).then(function(t){e.listGrid.data=t.data.data,e.listGrid.total=t.data.total}).catch(function(t){console.log(t)})},enterSearch:function(){this.listGrid.currentPage=1,this.searchType="NORMAL",this.listGrid.filters[0].value=this.searchParam},searchDetail:function(){this.listGrid.currentPage=1,this.listGrid.filters[0].value="/",this.listGrid.filters[0].value="",this.visibleSearchDetailFlag=!1},searchDetailReset:function(){this.listGrid.currentPage=1,this.listGrid.searchParamDetail.swcatIdD="",this.listGrid.searchParamDetail.classstructureD="",this.listGrid.searchParamDetail.swcatnameD="",this.listGrid.searchParamDetail.publishernameD="",this.listGrid.searchParamDetail.swcattypeD="",this.listGrid.filters[0].value="/",this.listGrid.filters[0].value="",this.visibleSearchDetailFlag=!1},openClassDialog:function(){this.showClassDialogFlag=!0},confirmClass:function(t){this.visibleSearchDetailFlag=!0,this.showClassDialogFlag=!1,this.listGrid.searchParamDetail.classstructureD=t.classstructureId,this.listGrid.searchParamDetail.classstructureNm=t.classstructureNm},closeClassDialog:function(){this.visibleSearchDetailFlag=!0,this.showClassDialogFlag=!1},exportFilter:function(){var t=this,e=this.listGrid.filters[0].value,a=this.listGrid.srchCnd,s=null,i="ascending";null!=a&&void 0!==a&&"undefined"!==a&&null!=a.sort.prop&&(s=a.sort.prop,i=a.sort.order);var l={params:{orderBy:s,sort:i,orgId:this.orgId,lang:this.lang,searchParam:e,searchType:this.searchType,swcatIdD:this.listGrid.searchParamDetail.swcatIdD,classstructureD:this.listGrid.searchParamDetail.classstructureD,swcatnameD:this.listGrid.searchParamDetail.swcatnameD,publishernameD:this.listGrid.searchParamDetail.publishernameD,swcattypeD:this.listGrid.searchParamDetail.swcattypeD}};this.$http.get("/episode/api/asset/licensemgmnt/licenseuse",l).then(function(e){var a=[{value:"classstructureNm",label:t.labels.classstructure},{value:"publishername",label:t.labels.publisher},{value:"swcatname",label:t.labels.name},{value:"swcattype",label:t.labels.licensetype},{value:"swcatversion",label:t.labels.version},{value:"purchaseunit",label:t.labels.purchaseunit},{value:"purchasenum",label:t.labels.purchasenum},{value:"usenum",label:t.labels.usenum},{value:"remainnum",label:t.labels.remainnum},{value:"usesever",label:t.labels.usesever},{value:"relationlicense",label:t.labels.relationlicense},{value:"swcatId",label:t.labels.swcatId}];t.$util.csvExport(e.data.data,a,t.listGrid.columnNames,"licenseuse")}).catch(function(t){console.log(t)})},openUseServerDialog:function(t){this.listGrid.currentData=t,this.showUseServerDialogFlag=!0},closeUseServerDialog:function(){this.showUseServerDialogFlag=!1},openRelationDialog:function(t){this.listGrid.currentData=t,this.showRelationDialogFlag=!0},closeRelationDialog:function(){this.showRelationDialogFlag=!1}},created:function(){this.initialize()},beforeDestroy:function(){this.listGrid.data=[],this.labels={},this.auth={},this.$nextTick(function(){this.listGridFlag=!1})}},n={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"animated fadeIn"},[s("b-card",[s("b-row",[s("b-col",{attrs:{sm:"12"}},[s("div",{staticStyle:{height:"40px"}})])],1),t._v(" "),s("p",{staticClass:"grid-line"}),t._v(" "),s("b-row",{staticClass:"mt-2"},[s("b-col",{attrs:{sm:"6"}},[s("div",{staticClass:"search-component"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.searchParam,expression:"searchParam"}],staticClass:"form-control mt-1",staticStyle:{width:"100%"},attrs:{placeholder:t.labels.search},domProps:{value:t.searchParam},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.enterSearch(e)},input:function(e){e.target.composing||(t.searchParam=e.target.value)}}})])]),t._v(" "),s("div",[s("el-popover",{attrs:{placement:"right",width:"500",trigger:"click"},model:{value:t.visibleSearchDetailFlag,callback:function(e){t.visibleSearchDetailFlag=e},expression:"visibleSearchDetailFlag"}},[s("b-row",{staticStyle:{"padding-left":"15px","padding-bottom":"10px","font-size":"16px","font-weight":"bold"}},[t._v("\n            "+t._s(t.labels.searchDetail)+"\n          ")]),t._v(" "),s("b-row",[s("div",{staticClass:"form-group col-md-6"},[s("label",{attrs:{for:"ciId"}},[t._v(t._s(t.labels.swcatId))]),t._v(" "),s("input",{directives:[{name:"model",rawName:"v-model",value:t.listGrid.searchParamDetail.swcatIdD,expression:"listGrid.searchParamDetail.swcatIdD"}],staticClass:"formdata sm float-right",attrs:{id:"ciId"},domProps:{value:t.listGrid.searchParamDetail.swcatIdD},on:{input:function(e){e.target.composing||t.$set(t.listGrid.searchParamDetail,"swcatIdD",e.target.value)}}})]),t._v(" "),s("div",{staticClass:"form-group col-md-6"},[s("label",{attrs:{for:"ciname"}},[t._v(t._s(t.labels.publisher))]),t._v(" "),s("input",{directives:[{name:"model",rawName:"v-model",value:t.listGrid.searchParamDetail.publishernameD,expression:"listGrid.searchParamDetail.publishernameD"}],staticClass:"formdata sm float-right",attrs:{id:"ciname"},domProps:{value:t.listGrid.searchParamDetail.publishernameD},on:{input:function(e){e.target.composing||t.$set(t.listGrid.searchParamDetail,"publishernameD",e.target.value)}}})]),t._v(" "),s("div",{staticClass:"form-group col-md-6"},[s("label",{attrs:{for:"description"}},[t._v(t._s(t.labels.name))]),t._v(" "),s("input",{directives:[{name:"model",rawName:"v-model",value:t.listGrid.searchParamDetail.swcatnameD,expression:"listGrid.searchParamDetail.swcatnameD"}],staticClass:"formdata sm float-right",attrs:{id:"description"},domProps:{value:t.listGrid.searchParamDetail.swcatnameD},on:{input:function(e){e.target.composing||t.$set(t.listGrid.searchParamDetail,"swcatnameD",e.target.value)}}})]),t._v(" "),s("div",{staticClass:"form-group col-md-6"},[s("label",{attrs:{for:"itsmciid"}},[t._v(t._s(t.labels.type))]),t._v(" "),s("input",{directives:[{name:"model",rawName:"v-model",value:t.listGrid.searchParamDetail.swcattypeD,expression:"listGrid.searchParamDetail.swcattypeD"}],staticClass:"formdata sm float-right",attrs:{id:"itsmciid"},domProps:{value:t.listGrid.searchParamDetail.swcattypeD},on:{input:function(e){e.target.composing||t.$set(t.listGrid.searchParamDetail,"swcattypeD",e.target.value)}}})])]),t._v(" "),s("b-row",[s("div",{staticClass:"col mt-2"},[s("div",{staticClass:"d-flex justify-content-end"},[s("b-button",{staticClass:"px-3 sm",attrs:{variant:"primary"},on:{click:t.searchDetailReset}},[t._v(t._s(t.labels.reset))]),t._v(" "),s("b-button",{staticClass:"px-3 sm",attrs:{variant:"primary"},on:{click:t.searchDetail}},[t._v(t._s(t.labels.search))])],1)])]),t._v(" "),s("el-button",{class:t.searchDetailBtnClass,staticStyle:{"margin-top":"4px !important"},attrs:{slot:"reference"},slot:"reference"})],1)],1),t._v(" "),s("b-col",[s("div",{staticClass:"float-right mb-1",attrs:{role:"group"}},[s("el-button",{staticStyle:{padding:"0 0 0 0",width:"40px",height:"40px"},attrs:{title:t.labels.excelDownload},on:{click:t.exportFilter}},[s("img",{attrs:{src:a("QOY8"),width:"60%"}})])],1)])],1),t._v(" "),s("b-row",[s("b-col",{attrs:{sm:"12"}},[s("p",{staticClass:"grid-line"}),t._v(" "),t.listGridFlag?s("data-tables-server",{ref:"grid",attrs:{"table-props":t.listGrid.tableProps,data:t.listGrid.data,filters:t.listGrid.filters,total:t.listGrid.total,"page-size":t.listGrid.pageProps.pageSize,"pagination-props":t.listGrid.pageProps,"current-page":t.listGrid.currentPage},on:{"query-change":t.getListData,"update:currentPage":function(e){return t.$set(t.listGrid,"currentPage",e)},"update:current-page":function(e){return t.$set(t.listGrid,"currentPage",e)}}},[s("el-table-column",{attrs:{prop:t.listGrid.titles[0].prop,label:t.labels.classstructure,sortable:"custom",width:t.listGrid.titles[0].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[1].prop,label:t.labels.publisher,sortable:"custom",width:t.listGrid.titles[1].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[2].prop,label:t.labels.name,sortable:"custom",width:t.listGrid.titles[2].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[3].prop,label:t.labels.licensetype,sortable:"custom",width:t.listGrid.titles[3].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[4].prop,label:t.labels.version,sortable:"custom",width:t.listGrid.titles[4].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[5].prop,label:t.labels.purchaseunit,sortable:"custom",width:t.listGrid.titles[5].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[6].prop,label:t.labels.purchasenum,sortable:"custom",width:t.listGrid.titles[6].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[7].prop,label:t.labels.usenum,sortable:"custom",width:t.listGrid.titles[7].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[8].prop,label:t.labels.remainnum,sortable:"custom",width:t.listGrid.titles[8].width}}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[9].prop,label:t.labels.usesever,sortable:"custom",width:t.listGrid.titles[9].width},scopedSlots:t._u([{key:"default",fn:function(e){return[s("a",{class:0!==e.row.usesever?"cursor-pointer link":"",on:{click:function(a){return t.openUseServerDialog(e.row)}}},[t._v(t._s(e.row.usesever))])]}}],null,!1,3636607082)}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[10].prop,label:t.labels.relationlicense,sortable:"custom",width:t.listGrid.titles[10].width},scopedSlots:t._u([{key:"default",fn:function(e){return[s("a",{class:0!==e.row.relationlicense?"cursor-pointer link":"",on:{click:function(a){return t.openRelationDialog(e.row)}}},[t._v(t._s(e.row.relationlicense))])]}}],null,!1,3368421066)}),t._v(" "),s("el-table-column",{attrs:{prop:t.listGrid.titles[11].prop,label:t.labels.swcatId,sortable:"custom",width:t.listGrid.titles[11].width}})],1):t._e()],1)],1)],1),t._v(" "),s("LicenseUseServerDialog",{ref:"licenseUseServerDialog",attrs:{labels:t.labels,showDialogFlagProp:t.showUseServerDialogFlag,param:{swcatId:t.listGrid.currentData.swcatId}},on:{close:t.closeUseServerDialog}}),t._v(" "),s("LicenseUseRelationDialog",{ref:"licenseUseRelationDialog",attrs:{labels:t.labels,showDialogFlagProp:t.showRelationDialogFlag,param:{swcatId:t.listGrid.currentData.swcatId,purchaseunit:t.listGrid.currentData.purchaseunit}},on:{close:t.closeRelationDialog}}),t._v(" "),s("Class",{ref:"classDialog",attrs:{showDialogFlagProp:t.showClassDialogFlag,param:{object:"LICENSE",expand:{data:{classstructureId:"SOFTWARE"}}}},on:{confirm:t.confirmClass,close:t.closeClassDialog}})],1)},staticRenderFns:[]},c=a("VU/8")(o,n,!1,null,null,null);e.default=c.exports}});
//# sourceMappingURL=31.6cc8033553d380c21968.js.map