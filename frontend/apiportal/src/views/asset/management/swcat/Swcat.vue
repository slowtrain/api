<!--
 Class Name  : Swcat.vue
 Description : 소프트웨어 카탈로그 > 메인리스트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.14  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="2" >
          <div v-show="(activeTab !== 'list') && detailType === 'UPDATE'">
            <b-button class="move-btn" v-on:click="preMovePage('previous')">
              <img src="../../../../assets/img/icons/arrow-left@2x.png"/>
            </b-button>
            <b-button class="move-btn" v-on:click="preMovePage('next')">
              <img src="../../../../assets/img/icons/arrow-right@2x.png"/>
            </b-button>
          </div>
        </b-col>
        <b-col sm="10">
          <div class="comm-btn-box" role="group">
            <b-button variant="primary" class="px-3 sm" v-show="visibleDelete" v-on:click="del">{{labels.delete}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="add">{{labels.new}}</b-button>
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="12">
          <!--LIST-->
          <el-tabs v-model="activeTab" :before-leave="chgTab">
            <el-tab-pane  name="list" :label='labels.list'>
              <b-row>
                <b-col sm="3">
                  <Tree
                    v-bind:tree="tree"
                    ref="tree"
                    v-on:clickNode="clickNode"
                  />
                </b-col>
                <b-col sm="9">
                  <b-row>
                    <b-col sm="6">
                      <div class="search-component">
                        <input class="form-control mt-1" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
                      </div>
                    </b-col>
                    <div>
                      <el-popover placement="right" width="500" trigger="click" v-model="visibleSearchDetailFlag">
                        <b-row style="padding-left:15px; padding-bottom:10px; font-size:16px; font-weight:bold;">
                          {{labels.searchDetail}}
                        </b-row>
                        <b-row>
                        <div class='form-group col-md-6'>
                          <label for="ciId">{{labels.swcatId}}</label>
                          <input id="ciId" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swcatIdD">
                        </div>
                        <!-- <div class='form-group col-md-6'>
                          <label for="classstructure">{{labels.classstructureId}}</label>
                          <div class="search-component sm">
                            <input id="classstructureId" v-model="listGrid.searchParamDetail.classstructureNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
                            <a class="search-button cursor-pointer" v-on:click="openClassDialog">search</a>
                          </div>
                        </div> -->
                        <div class='form-group col-md-6'>
                          <label for="ciname">{{labels.publisher}}</label>
                          <input id="ciname" class="formdata sm float-right" v-model="listGrid.searchParamDetail.publishernameD"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="description">{{labels.name}}</label>
                          <input id="description" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swcatnameD"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="itsmciid">{{labels.type}}</label>
                          <input id="itsmciid" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swcattypeD"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="swctr">{{labels.swctr}}</label>
                          <el-select v-model="listGrid.searchParamDetail.swctr" :placeholder="labels.selectData">
                            <el-option v-for="swctr in comboData.swCtrSelect" :prop="swctr.value" :value="swctr.value" :key="swctr.key">
                              {{swctr.value}}
                            </el-option>
                          </el-select>
                        </div>
                      </b-row>
                        <b-row>
                          <div class="col mt-2">
                            <div class='d-flex justify-content-end'>
                              <b-button variant="primary" class="px-3 sm" v-on:click="searchDetailReset">{{labels.reset}}</b-button>
                              <b-button variant="primary" class="px-3 sm" v-on:click="searchDetail">{{labels.search}}</b-button>
                            </div>
                          </div>
                        </b-row>
                        <el-button slot="reference" style="margin-top:4px !important" :class='searchDetailBtnClass'></el-button>
                      </el-popover>
                    </div>
                    <b-col>
                      <div class="float-right mb-1" role="group">
                        <el-button v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0 0 0 0; width:40px; height: 40px;">
                          <img src="../../../../assets/img/icons/icon_search_download.png" width="60%" />
                        </el-button>
                      </div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col sm="12">
                      <p class="grid-line"></p>
                      <data-tables-server ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" v-on:row-click="clickGridRow" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps" :current-page.sync='listGrid.currentPage'>
                        <el-table-column :prop='listGrid.titles[0].prop' :label='labels.swcatId'          sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[1].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[2].prop' :label='labels.publisher'        sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[3].prop' :label='labels.name'             sortable='custom'></el-table-column>
                        <el-table-column :prop='listGrid.titles[4].prop' :label='labels.type'             sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                        <!-- <el-table-column :prop='listGrid.titles[5].prop' :label='labels.ispromotetoci'    sortable='custom' :width='listGrid.titles[5].width'></el-table-column> -->
                      </data-tables-server>
                    </b-col>
                  </b-row>
                </b-col>
              </b-row>
            </el-tab-pane>
            <el-tab-pane  name="detail" :label='listGrid.currentData.swcattype === "PRODUCT" ? labels.swcatchild : labels.swcatgroup' v-if="(activeTab !== 'list')">
              <SwcatChild
                ref="swcatChild"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:childTree="childTree"
                v-bind:getRowData="getRowData"
                v-bind:groupGrid="groupGrid"
                v-bind:validMsg="validMsg"
              />
            </el-tab-pane>
            <el-tab-pane  name="relation" :label='labels.swcatrelation' v-if="(activeTab !== 'list')">
              <SwcatRelation
                ref="swcatRelation"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:relationTree="relationTree"
                v-bind:getRowData="getRowData"
                v-bind:validMsg="validMsg"
              />
            </el-tab-pane>
            <el-tab-pane  name="useserver" :label='labels.useserver' v-if="(activeTab !== 'list')">
              <SwcatUseServer
                ref="swcatUseServer"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:useServerGrid="useServerGrid"
                v-bind:validMsg="validMsg"
              />
            </el-tab-pane>
            <el-tab-pane  name="license" :label='labels.license' v-if="(activeTab !== 'list')">
              <SwcatLicense
                ref="swcatLicense"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:licenseGrid="licenseGrid"
                v-bind:validMsg="validMsg"
              />
            </el-tab-pane>
          </el-tabs>
        </b-col>
      </b-row>
    </b-card>
    <!-- Class Dialog -->
    <Class
      ref="classDialog"
      v-bind:showDialogFlagProp="showClassDialogFlag"
      v-on:confirm="confirmClass"
      v-on:close="closeClassDialog"
      v-bind:param="{object: 'SWCAT', expand: {data: {classstructureId: 'SOFTWARE'}}}"
    />
  </div>
</template>
<script>
import Tree from '@/views/common/TreeCom'
import SwcatChild from './SwcatChild'
import SwcatRelation from './SwcatRelation'
import SwcatUseServer from './SwcatUseServer'
import SwcatLicense from './SwcatLicense'
import Class from '@/views/common/Class'
export default {
  name: 'SWCAT',
  props: ['type'],
  components: {
    'Tree': Tree,
    'SwcatChild': SwcatChild,
    'SwcatRelation': SwcatRelation,
    'SwcatUseServer': SwcatUseServer,
    'SwcatLicense': SwcatLicense,
    'Class': Class
  },
  data () {
    return {
      labels: {
        id: '', spec: '', name: '', chgtype: ''
      },
      tree: {
        data: [],
        expandKeys: ['root'],
        selectData: {},
        showTreeFlag: true,
        showIconFlag: true,
        showCntFlag: false,
        showNodeIconFn: function (data) {
          return data.classiconsvg
        },
        shwoAddIconFn: function (data) {
          return false
        }
      },
      childTree: {
        dataType: 'RAW',
        data: [],
        rawData: [],
        selectData: {},
        rootVal: null,
        parentColNm: 'parentId',
        childColNm: 'swcatId',
        textColNm: 'swcatfullname',
        style: 'height: 120px',
        iconImgType: 'class',
        showTreeFlag: false,
        isUseCntFlag: false,
        showNodeIconFn: function (node) {
          return false
        },
        shwoAddIconFn: function (node) {
          return node.data.userdefine && node.data.swcattype !== 'RELEASE'
        },
        expand: null
      },
      relationTree: {
        dataType: 'RAW',
        data: [],
        rawData: [],
        selectData: {},
        rootVal: null,
        parentColNm: 'parentId',
        childColNm: 'swcatId',
        textColNm: 'swcatfullname',
        style: 'height: 240px',
        iconImgType: 'class',
        showTreeFlag: false,
        isUseCntFlag: false,
        showNodeIconFn: function (node) {
          return false
        },
        shwoAddIconFn: function (node) {
          return false
        }
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: false,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 10,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['classstructureId', 'isdomestic', 'publishername', 'swcatname', 'swcatdescription'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }, {
          prop: 'jobSwcat',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'swcatId',
          width: '120px'
        }, {
          prop: 'classstructureNm',
          width: '180px'
        }, {
          prop: 'publishername',
          width: '200px'
        }, {
          prop: 'swcatname',
          width: '300x'
        }, {
          prop: 'swcattype',
          width: '100x'
        }, {
          prop: 'ispromotetociNm',
          width: '130px'
        }],
        columns: [],
        columnNames: [],
        currentData: {},
        total: 0,
        srchCnd: null,
        searchParamDetail: {
          swcatIdD: '',
          classstructureD: '',
          classstructureNm: '',
          swcatnameD: '',
          publishernameD: '',
          swcattypeD: '',
          swctr: ''
        }
      },
      groupGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['classstructure_id', 'isdomestic', 'publishername', 'swcatname', 'swcatdescription'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'swcatId',
          width: '200px'
        }, {
          prop: 'classstructureNm',
          width: '180px'
        }, {
          prop: 'publishername',
          width: '200px'
        }, {
          prop: 'swcatname',
          width: '300x'
        }, {
          prop: 'swcattype',
          width: '100x'
        }, {
          prop: 'swcatversion',
          width: '100x'
        }],
        columns: [],
        columnNames: [],
        currentData: {},
        total: 0
      },
      useServerGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['ciId', 'classstructureId'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'ciId',
          width: '150px'
        }, {
          prop: 'classstructureNm',
          width: '150px'
        }, {
          prop: 'ciname',
          width: '150px'
        }, {
          prop: 'description',
          width: '150px'
        }, {
          prop: 'extaln17',
          width: '150px'
        }, {
          prop: 'managerNm',
          width: '150px'
        }],
        currentData: {},
        total: 0
      },
      licenseGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['swlicenseId', 'swlicensename'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'swlicenseId',
          width: '140px'
        }, {
          prop: 'swlicname',
          width: '140px'
        }, {
          prop: 'swlictypeNm',
          width: '120px'
        }, {
          prop: 'purchasetypeNm',
          width: '120px'
        }, {
          prop: 'swlicstatus',
          width: '120px'
        }, {
          prop: 'startDt',
          width: '200px'
        }, {
          prop: 'endDt',
          width: '200px'
        }],
        currentData: {},
        total: 0
      },
      // Auth
      authorities: {
        SWCAT_ATTACHMENT: false,
        SWCAT_ADD: false,
        SWCAT_SAVE: false,
        SWCAT_DELETE: false,
        SWCAT_CHILD_ADD: false,
        SWCAT_CHILD_SAVE: false,
        SWCAT_CHILD_DELETE: false,
        SWCAT_GROUP_ADD: false,
        SWCAT_GROUP_SAVE: false,
        SWCAT_GROUP_DELETE: false,
        SWCAT_RELATION_ADD: false,
        SWCAT_RELATION_SAVE: false,
        SWCAT_RELATION_DELETE: false
      },
      // Valid Msg
      validMsg: {
        classstructureMsg: '',
        swcatnameMsg: '',
        swcatversionMsg: ''
      },
      // Combo Data
      comboData: {
        orgSelect: [],
        swcattypeSelect: [],
        swCtrSelect: []
      },
      // Global Var
      orgId: '',
      lang: '',
      activeTab: 'list',
      detailType: 'INSERT',
      searchParam: '',
      visibleSearchDetailFlag: false,
      showClassDialogFlag: false
    }
  },
  watch: {
    '$route': function () {
      if (!this.$util.isNull(this.$route.params.id)) {
        this.paramInit()
      } else if (!this.$util.isNull(this.$route.params.swctr)) {
        this.jobSwcat()
      }
    },
    activeTab: function (val) {
      if (val === 'list') {
        for (let key in this.validMsg) {
          this.validMsg[key] = ''
        }
        this.getListData(this.listGrid.srchCnd)
      }
    },
    'listGrid.currentData.classstructureId': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.classstructureMsg = ''
      }
    },
    'listGrid.currentData.swcatname': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.swcatnameMsg = ''
      }
    }
  },
  computed: {
    visibleAdd: function () {
      return this.authorities.SWCAT_ADD && (this.activeTab === 'list')
    },
    visibleSave: function () {
      return this.authorities.SWCAT_SAVE && (this.activeTab !== 'list')
    },
    visibleDelete: function () {
      return this.authorities.SWCAT_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.userdefine
    },
    searchDetailBtnClass: function () {
      let flag = false
      for (let key in this.listGrid.searchParamDetail) {
        if (!this.$util.isNull(this.listGrid.searchParamDetail[key])) {
          flag = true
        }
      }
      return flag ? 'filter-button-on' : 'filter-button'
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'comboData.swCtrSelect', domainId: 'BOOLEANTYPE'}
        ]
      }
      this.$util.getComboData(param)
      this.orgId = this.$store.getters.getUser.orgId
      this.lang = this.$store.getters.getUser.lang
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    chgTab: function (activeTab, oldTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
        if (this.isChkChg()) {
          rtnFlag = false
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.listGrid.currentData = {}
              let swcatChild = $this.$refs.swcatChild
              swcatChild.childData = {}
              $this.activeTab = 'list'
            })
        }
      }
      return rtnFlag
    },
    isChkChg: function () {
      let swcatChild = this.$refs.swcatChild
      return this.listGrid.currentData.state === 'CHANGED' || swcatChild.childData.state === 'ADDED' || swcatChild.childData.state === 'CHANGED'
    },
    isValidationCheck: function () {
      let rtnFlag = true
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      let classstructure = this.listGrid.currentData.classstructureId
      let swcatname = this.listGrid.currentData.swcatname
      if (this.$util.isNull(classstructure)) {
        this.validMsg.classstructureMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(swcatname)) {
        this.validMsg.swcatnameMsg = this.labels.inputData
        rtnFlag = false
      }
      return rtnFlag
    },
    isValidationCheckChild: function () {
      let rtnFlag = true
      let swcatChild = this.$refs.swcatChild
      if (Object.keys(swcatChild.childData).length === 0) return true
      this.validMsg.swcatversionMsg = ''
      let swcatversion = swcatChild.childData.swcatversion
      let swcatversionOrg = swcatChild.childData.swcatversionOrg
      if (this.$util.isNull(swcatversion) && (swcatChild.childData.swcattype !== 'PRODUCT')) {
        this.validMsg.swcatversionMsg = this.labels.inputData
        rtnFlag = false
      }
      if ((swcatversion === swcatversionOrg) && (swcatChild.childData.swcattype === 'RELEASE')) {
        this.validMsg.swcatversionMsg = this.labels.inputData
        rtnFlag = false
      }
      return rtnFlag
    },
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=SWCAT'
      ).then(res => {
        this.tree.data = res.data
        this.tree.expandKeys = [res.data[0].children[0].id]
      }).catch(error => {
        console.log(error)
      })
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'comboData.orgSelect', query: '/episode/api/system/common/organization'},
          {dataObj: 'comboData.swcattypeSelect', domainId: 'SWCATTYPE'}
        ]
      }
      this.$util.getComboData(param)
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = null
      let sort = 'ascending'
      let searchParam = ''
      let treeSearchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        treeSearchParam = srchCnd.filters[1].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        lang: this.lang,
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        swcattype: 'PRODUCT,GROUP',
        swcatIdD: this.listGrid.searchParamDetail.swcatIdD,
        classstructureD: this.listGrid.searchParamDetail.classstructureD,
        swcatnameD: this.listGrid.searchParamDetail.swcatnameD,
        publishernameD: this.listGrid.searchParamDetail.publishernameD,
        swcattypeD: this.listGrid.searchParamDetail.swcattypeD,
        swctr: this.listGrid.searchParamDetail.swctr}
      }
      this.$http.get(
        '/episode/api/asset/management/swcat',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    getRowData: function (id) {
      this.listGrid.currentData = {}
      this.childTree.rawData = []
      this.$http.get(
        '/episode/api/asset/management/swcat/' + id
      ).then(res => {
        this.listGrid.currentData = res.data[0]
        if (res.data.length > 1) {
          this.childTree.expand = {data: {swcatId: res.data[1].swcatId}}
        } else {
          this.childTree.expand = {data: {swcatId: res.data[0].swcatId}}
        }
        this.childTree.rawData = res.data
      }).catch(error => {
        console.log(error)
      })
      this.getRelationData(id)
      this.getUseServerData(id)
      this.getLicenseData(id)
      this.getGroupData(id)
    },
    getRelationData: function (id) {
      this.relationTree.rawData = []
      this.$http.get(
        '/episode/api/asset/management/swcat/' + id + '/relationlist'
      ).then(res => {
        if (res.data.length > 1) {
          this.relationTree.expnd = {data: {swcatId: res.data[0].swcatId}}
          let swcatRelation = this.$refs.swcatRelation
          swcatRelation.relationData = res.data[0]
        }
        this.relationTree.rawData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getUseServerData: function (id) {
      this.relationTree.showTreeFlag = false
      this.$http.get(
        '/episode/api/asset/management/swcat/' + id + '/useserverlist'
      ).then(res => {
        this.useServerGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getLicenseData: function (id) {
      this.relationTree.showTreeFlag = false
      this.$http.get(
        '/episode/api/asset/management/swcat/' + id + '/licenselist'
      ).then(res => {
        this.licenseGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getGroupData: function (id) {
      this.relationTree.rawData = []
      this.$http.get(
        '/episode/api/asset/management/swcat/' + id + '/grouplist'
      ).then(res => {
        this.groupGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    add: function () {
      this.$http.get(
        '/episode/api/asset/management/swcat/swcatId?orgId=' + this.orgId
      ).then(res => {
        this.activeTab = 'detail'
        this.detailType = 'INSERT'
        this.listGrid.currentData = {
          orgId: this.orgId,
          swcatId: res.data,
          parentId: null,
          classstructureId: '',
          swcatname: '',
          swcattype: 'PRODUCT',
          swcatdescription: '',
          swcatversion: '',
          publishername: '',
          isdomestic: false,
          userdefine: true,
          ispromotetoci: false,
          hierarchypath: '/' + res.data,
          datasrc: 'EPISODE'
        }
        this.childTree.rawData = []
        this.groupGrid.data = []
        this.relationTree.rawData = []
        this.useServerGrid.data = []
        this.licenseGrid.data = []
      }).catch(error => {
        console.log(error)
      })
    },
    saveChild: function () {
      let swcatChild = this.$refs.swcatChild
      return swcatChild.childData.state === 'ADDED' || swcatChild.childData.state === 'CHANGED' ? swcatChild.childData : {}
    },
    save: function () {
      if (this.isValidationCheck()) {
        if (!this.isValidationCheckChild()) return
        let child = this.saveChild()
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/asset/management/swcat/' + this.listGrid.currentData.id : '/episode/api/asset/management/swcat'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || Object.keys(child).length !== 0) {
          this.listGrid.currentData.child = child
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
            this.detailType = 'UPDATE'
            this.getRowData(res.data.id)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      }
    },
    del: function () {
      if (this.$util.isNull(this.listGrid.currentData.id)) {
        this.activeTab = 'list'
      } else if (this.childTree.rawData.length > 1) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else if (this.relationTree.rawData.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.relationDataExists)
      } else if (this.groupGrid.data.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.groupDataExists)
      } else {
        let $this = this
        this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
          this.$http.delete('/episode/api/asset/management/swcat/' + $this.listGrid.currentData.id).then(res => {
            $this.$util.esmessage($this, 'delete', $this.labels.deleteMessage)
            $this.activeTab = 'list'
            $this.getListData($this.listGrid.srchCnd)
          }).catch(error => {
            $this.$util.esmessage($this, 'warn', $this.labels.failMessage)
            console.log(error)
          })
        })
      }
    },
    // EVENT FN ///////////////////////////////////////////
    clickTab: function (tab) {
      this.activeTab = tab.name
    },
    clickNode: function (node) {
      this.searchParam = ''
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[2].value = ''
      if (!this.$util.isNull(node)) {
        let filterVal = (node.data.classstructureId === 'CLASSSTRUCTURE' ? '' : node.data.classstructureId)
        this.listGrid.filters[1].value = filterVal
      }
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.searchParam
      this.listGrid.filters[2].value = ''
    },
    searchDetail: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[2].value = ''
      this.visibleSearchDetailFlag = false
    },
    searchDetailReset: function () {
      this.listGrid.currentPage = 1
      this.listGrid.searchParamDetail.swcatIdD = ''
      this.listGrid.searchParamDetail.classstructureD = ''
      this.listGrid.searchParamDetail.classstructureNm = ''
      this.listGrid.searchParamDetail.swcatnameD = ''
      this.listGrid.searchParamDetail.publishernameD = ''
      this.listGrid.searchParamDetail.swcattypeD = ''
      this.listGrid.searchParamDetail.swctr = ''
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[2].value = ''
      this.visibleSearchDetailFlag = false
    },
    openClassDialog: function () {
      this.showClassDialogFlag = true
    },
    confirmClass: function (rslt) {
      this.visibleSearchDetailFlag = true
      this.showClassDialogFlag = false
      this.listGrid.searchParamDetail.classstructureD = rslt.classstructureId
      this.listGrid.searchParamDetail.classstructureNm = rslt.classstructureNm
    },
    closeClassDialog: function () {
      this.visibleSearchDetailFlag = true
      this.showClassDialogFlag = false
    },
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      let treeSearchParam = this.listGrid.filters[1].value
      let srchCnd = this.listGrid.srchCnd
      let orderBy = null
      let sort = 'ascending'
      if (srchCnd != null && srchCnd !== undefined && srchCnd !== 'undefined') {
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
      }
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        lang: this.lang,
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        swcattype: 'PRODUCT,GROUP',
        swcatIdD: this.listGrid.searchParamDetail.swcatIdD,
        classstructureD: this.listGrid.searchParamDetail.classstructureD,
        swcatnameD: this.listGrid.searchParamDetail.swcatnameD,
        publishernameD: this.listGrid.searchParamDetail.publishernameD,
        swcattypeD: this.listGrid.searchParamDetail.swcattypeD,
        swctr: this.listGrid.searchParamDetail.swctr}
      }
      this.$http.get(
        '/episode/api/asset/management/swcat',
        params
      ).then(res => {
        let fileName = 'swcat'
        let fields = [
          {value: 'swcatId', label: this.labels.swcatId},
          {value: 'classstructureNm', label: this.labels.classstructureId},
          {value: 'publishername', label: this.labels.publisher},
          {value: 'swcatname', label: this.labels.name}
          // {value: 'isdomesticNm', label: this.labels.isdomestic}
        ]
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    clickGridRow: function (row) {
      this.activeTab = 'detail'
      this.detailType = 'UPDATE'
      this.listGrid.rowIndex = this.listGrid.data.indexOf(row)
      this.getRowData(row.id)
    },
    paramInit: function () {
      if (!this.$util.isNull(this.$route.params.id)) {
        this.activeTab = 'detail'
        this.detailType = 'UPDATE'
        this.getRowData(this.$route.params.id)
      }
    },
    preMovePage: function (moveStr) {
      if (this.isChkChg()) {
        let $this = this
        this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
          function () {
            $this.save()
          },
          function () {
            $this.movePage(moveStr)
          }
        )
        return false
      } else {
        this.movePage(moveStr)
      }
    },
    movePage: function (moveStr) {
      let srchCnd = this.listGrid.srchCnd
      let oldPage = srchCnd.page
      let oldRowIndex = this.listGrid.rowIndex
      if (moveStr === 'previous') {
        this.listGrid.rowIndex = this.listGrid.rowIndex - 1
      } else {
        this.listGrid.rowIndex = this.listGrid.rowIndex + 1
      }
      if (this.listGrid.rowIndex < 0) {
        this.listGrid.rowIndex = srchCnd.pageSize - 1
        srchCnd.page = srchCnd.page - 1
      } else if (srchCnd.pageSize - 1 < this.listGrid.rowIndex) {
        this.listGrid.rowIndex = 0
        srchCnd.page = srchCnd.page + 1
      }
      if (srchCnd.page === 0 || oldPage === srchCnd.page || Math.ceil((this.listGrid.total / srchCnd.pageSize)) < srchCnd.page) {
        if (srchCnd.page === 0 || (this.listGrid.data.length - 1 < this.listGrid.rowIndex)) {
          this.listGrid.rowIndex = oldRowIndex
        }
        srchCnd.page = oldPage
        this.getRowData(this.listGrid.data[this.listGrid.rowIndex].id)
        return
      }
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = null
      let sort = 'ascending'
      let searchParam = ''
      let treeSearchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        treeSearchParam = srchCnd.filters[1].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        lang: this.lang,
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        swcattype: 'PRODUCT,GROUP',
        swcatIdD: this.listGrid.searchParamDetail.swcatIdD,
        classstructureD: this.listGrid.searchParamDetail.classstructureD,
        swcatnameD: this.listGrid.searchParamDetail.swcatnameD,
        publishernameD: this.listGrid.searchParamDetail.publishernameD,
        swcattypeD: this.listGrid.searchParamDetail.swcattypeD,
        swctr: this.listGrid.searchParamDetail.swctr}
      }
      this.$http.get(
        '/episode/api/asset/management/swcat',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
        this.listGrid.currentPage = srchCnd.page
        this.getRowData(this.listGrid.data[this.listGrid.rowIndex].id)
      }).catch(error => {
        console.log(error)
      })
    },
    jobSwcat: function () {
      if (!this.$util.isNull(this.$route.params.swctr)) {
        this.listGrid.searchParamDetail.swcattypeD = this.$route.params.swcattypeD
        this.listGrid.searchParamDetail.swctr = this.$route.params.swctr
      }
    }
  },
  created: function () {
    this.initialize()
    this.paramInit()
    this.jobSwcat()
  }
}
</script>
