<!--
 Class Name  : Service.vue
 Description : 서비스 > 메인리스트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.04  최영훈  최초 생성
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
            <b-button variant="primary" class="px-3 sm" v-show="visibleOpenAttach" v-on:click="openUploadModal(listGrid.currentData)">{{labels.attachFile}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleCopy"  v-on:click="copy">{{labels.copy}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleDelete"  v-on:click="del">{{labels.delete}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="add">{{labels.new}}</b-button>
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="12">
          <!--LIST-->
          <el-tabs v-if="activeTabFlag.main" v-model="activeTab" :before-leave="chgTab">
            <el-tab-pane name="list" :label='labels.list'>
              <b-row>
                <b-col sm="3">
                  <Tree
                    v-bind:tree="tree"
                    ref="tree"
                    v-on:clickNode="clickNode"
                    v-on:clickAddBtn="clickAddBtn"
                  />
                </b-col>
                <b-col sm="9">
                  <b-row>
                    <b-col sm="6">
                      <div class="search-component">
                        <input class="form-control mt-1" style="width: 100%; height:33px;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
                      </div>
                    </b-col>
                    <div>
                      <el-popover placement="right" width="500" trigger="click" v-model="visibleSearchDetailFlag">
                        <b-row style="padding-left:15px; padding-bottom:10px; font-size:16px; font-weight:bold;">
                          {{labels.searchDetail}}
                        </b-row>
                        <b-row>
                        <div class='form-group col-md-6'>
                          <label for="ciId">{{labels.ciId}}</label>
                          <input id="ciId" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ciId">
                        </div>
                        <!-- <div class='form-group col-md-6'>
                          <label for="classstructure">{{labels.classstructureId}}</label>
                          <div class="search-component sm">
                            <input id="classstructureId" v-model="listGrid.searchParamDetail.classstructureNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
                            <a class="search-button cursor-pointer" v-on:click="openClassDialog">search</a>
                          </div>
                        </div> -->
                        <div class='form-group col-md-6'>
                          <label for="ciname">{{labels.name}}</label>
                          <input id="ciname" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ciname"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="description">{{labels.description}}</label>
                          <input id="description" class="formdata sm float-right" v-model="listGrid.searchParamDetail.description"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="status">{{labels.opstatus}}</label>
                          <el-select v-model="listGrid.searchParamDetail.status" :placeholder="labels.selectData">
                            <el-option v-for="status in comboData.statusSelect" :prop="status.value" :value="status.value" :key="status.key">
                              {{status.value}}
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
                      <data-tables-server ref="grid" v-if="showListGridFlag" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" v-on:row-click="clickGridRow" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps" :current-page.sync='listGrid.currentPage'>
                        <el-table-column :prop='listGrid.titles[0].prop' :label='labels.ciId'             sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[1].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[2].prop' :label='labels.name'             sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[3].prop' :label='labels.description'      sortable='custom' ></el-table-column>
                        <el-table-column :prop='listGrid.titles[4].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                      </data-tables-server>
                    </b-col>
                  </b-row>
                </b-col>
              </b-row>
            </el-tab-pane>
            <!--DETAIL-->
            <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
              <ServiceDetail
                ref="serviceDetail"
                v-if="(activeTab !== 'list')"
                v-bind:activeTab="activeTab"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:classType="classType"
                v-bind:listGrid="listGrid"
                v-bind:specGrid="specGrid"
                v-bind:rparespmapGrid="rparespmapGrid"
                v-bind:getRpareapmapData="getRpareapmapData"
              />
            </el-tab-pane>
            <!--Service relation-->
            <el-tab-pane  name="servicerelation" :label='labels.cirelation' v-if="(activeTab !== 'list')">
              <ServiceRelation
                ref="serviceRelation"
                v-bind:labels="labels"
                v-if="activeTabFlag.relation"
                v-bind:activeTabFlag="activeTabFlag"
                v-bind:activeTab="activeTab"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:classType="classType"
                v-bind:listGrid="listGrid"
                v-bind:relationGrid="relationGrid"
                v-bind:getRelationData="getRelationData"
                v-on:moveService="clickGridRow"
              />
            </el-tab-pane>
            <!--Service historty-->
            <el-tab-pane  name="servicehistory" :label='labels.cihistory' v-if="(activeTab !== 'list')">
              <ServiceHis
                ref="serviceHis"
                v-bind:labels="labels"
                v-if="activeTabFlag.his"
                v-bind:activeTabFlag="activeTabFlag"
                v-bind:activeTab="activeTab"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:classType="classType"
                v-bind:listGrid="listGrid"
                v-bind:hisGrid="hisGrid"
              />
            </el-tab-pane>
            <!--Service File-->
            <el-tab-pane  name="servicefile" :label='labels.cifile' v-if="(activeTab !== 'list')">
              <ServiceFile
                ref="serviceFile"
                v-bind:labels="labels"
                v-bind:activeTabFlag="activeTabFlag"
                v-bind:activeTab="activeTab"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:classType="classType"
                v-bind:listGrid="listGrid"
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
      v-bind:param="{object: 'SERVICE', expand: null}"
    />
  </div>
</template>
<script>
import {eventBus} from '@/eventBus/eventBus'
import Tree from '@/views/common/TreeCom'
import ServiceDetail from './ServiceDetail'
import ServiceRelation from './ServiceRelation'
import ServiceHis from './ServiceHis'
import ServiceFile from './ServiceFile'
import Class from '@/views/common/Class'
// const Tree = () => import('@/views/common/Tree')
// const ServiceDetail = () => import('./ServiceDetail')
// const ServiceRelation = () => import('./ServiceRelation')
// const ServiceHis = () => import('./ServiceHis')
// const ServiceFile = () => import('./ServiceFile')
export default {
  name: 'SERVICE',
  props: ['type'],
  components: {
    'Tree': Tree,
    'ServiceDetail': ServiceDetail,
    'ServiceRelation': ServiceRelation,
    'ServiceHis': ServiceHis,
    'ServiceFile': ServiceFile,
    'Class': Class
  },
  data () {
    return {
      labels: {
        id: '', spec: '', name: '', chgtype: '', item: ''
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
          return true
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
          prop: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'ciId',
          width: '150px'
        }, {
          prop: 'classstructureNm',
          width: '200px'
        }, {
          prop: 'ciname',
          width: '150px'
        }, {
          prop: 'description',
          width: '200x'
        }, {
          prop: 'status',
          width: '150px'
        }],
        columns: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
        columnNames: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
        currentData: {},
        ciExtNmData: {},
        total: 0,
        srchCnd: null,
        searchParamDetail: {
          ciId: '',
          classstructure: '',
          classstructureNm: '',
          ciname: '',
          description: '',
          itsmciid: '',
          status: ''
        }
      },
      specGrid: {
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
          pageSizes: [5, 10]
        },
        data: [],
        filters: [{
          prop: ['classstructureId', 'ciId', 'specId'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'classspecId',
          width: '220px'
        }, {
          prop: 'alnvalue',
          width: '200px'
        }, {
          prop: 'numvalue',
          width: '200x'
        }],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      rparespmapGrid: {
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
          pageSizes: [5, 10]
        },
        data: [],
        filters: [{
          prop: ['exetools', 'linebizstep'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'exetools',
          width: '220px'
        }, {
          prop: 'linebizstep',
          width: '200px'
        }],
        currentData: {},
        total: 0
      },
      relationGrid: {
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
          pageSizes: [5, 10]
        },
        data: [],
        filters: [{
          prop: ['relationname', 'sourceci', 'sourceciNm', 'sourceclass', 'targetci', 'targetciNm', 'targetclass'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'relationname',
          width: '100px'
        }, {
          prop: 'sourceci',
          width: '150px'
        }, {
          prop: 'sourceciNm',
          width: '150px'
        }, {
          prop: 'sourceclass',
          width: '150px'
        }, {
          prop: 'targetci',
          width: '150px'
        }, {
          prop: 'targetciNm',
          width: '150px'
        }, {
          prop: 'targetclass',
          width: '150px'
        }, {
          prop: 'status',
          width: '110px'
        }],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      hisGrid: {
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
          pageSizes: [5, 10]
        },
        data: [],
        filters: [{
          prop: ['chgtype', 'chgitem', 'chgdescription'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'updateNm',
          width: '100px'
        }, {
          prop: 'updateDt',
          width: '200px'
        }, {
          prop: 'chgtype',
          width: '100px'
        }, {
          prop: 'chgdetail',
          width: '300px'
        }, {
          prop: 'chgdescription',
          width: '400px'
        }],
        currentData: {},
        total: 0
      },
      // Auth
      authorities: {
        SERVICE_ATTACHMENT: false,
        SERVICE_ADD: false,
        SERVICE_SAVE: false,
        SERVICE_DELETE: false,
        SERVICE_SPEC_ADD: false,
        SERVICE_SPEC_SAVE: false,
        SERVICE_SPEC_DELETE: false,
        SERVICE_RPA_ADD: false,
        SERVICE_RPA_SAVE: false,
        SERVICE_RPA_DELETE: false,
        SERVICE_RELATION_ADD: false,
        SERVICE_RELATION_SAVE: false,
        SERVICE_RELATION_DELETE: false
      },
      // Combo Data
      comboData: {
        orgSelect: [],
        statusSelect: [],
        typeSelect: [],
        relationSelect: [],
        exetoolsSelect: [],
        linebizstepSelect: [],
        relationStatusSelect: []
      },
      // Global Var
      orgId: '',
      activeTab: 'list',
      detailType: 'INSERT',
      classType: '',
      searchParam: '',
      ciDialogParam: '',
      // Global Flag
      showRelationmapDialogFlag: false,
      showDiscimapDialogFlag: false,
      validRparespmapFlag: false,
      visibleSearchDetailFlag: false,
      showClassDialogFlag: false,
      showListGridFlag: true,
      activeTabFlag: {
        main: true,
        relation: false,
        his: false,
        file: false
      }
    }
  },
  watch: {
    '$route': function () {
      if (!this.$util.isNull(this.$route.params.id)) {
        this.paramInit()
      }
    },
    activeTab: function (val) {
      if (val === 'list') {
        this.relationGrid.searchParam = ''
        this.relationGrid.filters[0].value = ''
        this.getListData(this.listGrid.srchCnd)
      } else if (val === 'servicerelation') {
        this.activeTabFlag.relation = true
      } else if (val === 'servicehistory') {
        this.activeTabFlag.his = true
        if (this.detailType === 'UPDATE') {
          this.getHisData(this.listGrid.currentData.id)
        }
      } else if (val === 'servicefile') {
        this.activeTabFlag.file = true
      }
    }
  },
  computed: {
    visibleAdd: function () {
      return this.authorities.SERVICE_ADD && (this.activeTab === 'list')
    },
    visibleCopy: function () {
      return this.authorities.SERVICE_ADD && (this.activeTab !== 'list') && this.detailType === 'UPDATE'
    },
    visibleSave: function () {
      return this.authorities.SERVICE_SAVE && (this.activeTab !== 'list')
    },
    visibleDelete: function () {
      return this.authorities.SERVICE_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.userdefine
    },
    visibleOpenAttach: function () {
      return this.authorities.SERVICE_ATTACHMENT && (this.activeTab !== 'list') && this.detailType === 'UPDATE'
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
      this.orgId = this.$store.getters.getUser.orgId
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=SERVICE'
      ).then(res => {
        this.tree.data = res.data
      }).catch(error => {
        console.log(error)
      })
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'comboData.orgSelect', query: '/episode/api/system/common/organization'},
          {dataObj: 'comboData.statusSelect', domainId: 'CISTATUS'},
          {dataObj: 'comboData.typeSelect', domainId: 'CITYPE'},
          {dataObj: 'comboData.relationStatusSelect', domainId: 'STATUS'}
        ]
      }
      this.$util.getComboData(param)
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'descending'
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
        object: 'SERVICE',
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        ciId: this.listGrid.searchParamDetail.ciId,
        classstructure: this.listGrid.searchParamDetail.classstructure,
        ciname: this.listGrid.searchParamDetail.ciname,
        description: this.listGrid.searchParamDetail.description,
        itsmciid: this.listGrid.searchParamDetail.itsmciid,
        status: this.listGrid.searchParamDetail.status}
      }
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service',
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
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/' + id
      ).then(res => {
        this.classType = (res.data.data.hierarchypath + '/').substring(1, res.data.data.hierarchypath.indexOf('/', 2))
        this.listGrid.currentData = []
        this.listGrid.currentData = res.data.data
        this.listGrid.ciExtNmData = res.data.ciExtNmData
        if ((res.data.data.classstructureId === 'GROUP_1') && (res.data.data.extbool5)) {
          this.getRpacodeData(res.data.data.id)
          this.getRpareapmapData(res.data.data.id)
        }
      }).catch(error => {
        console.log(error)
      })
      this.getSpecData(id)
      this.getRelationData(id)
      this.getHisData(id)
    },
    getSpecData: function (id) {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/' + id + '/cispec'
      ).then(res => {
        this.specGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRelationData: function (id) {
      this.getRelationDataType(id, 'SERVICE')
      this.getRelationDataType(id, 'SERVICECI')
    },
    getRelationDataType: function (id, type) {
      this.relationGrid.data = []
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/' + id + '/cirelation?type=' + type
      ).then(res => {
        this.relationGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getHisData: function (id) {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/' + id + '/cihis'
      ).then(res => {
        this.hisGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRpareapmapData: function (id) {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/' + id + '/rparespmap'
      ).then(res => {
        this.rparespmapGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRpacodeData: function (id) {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/' + id + '/rpacode'
      ).then(res => {
        this.comboData.exetoolsSelect = res.data.exetoolscode
        this.comboData.linebizstepSelect = res.data.linebizstepcode
      }).catch(error => {
        console.log(error)
      })
    },
    add: function () {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/ciid?orgId=' + this.orgId
      ).then(res => {
        this.activeTab = 'detail'
        this.detailType = 'INSERT'
        this.classType = null
        this.listGrid.currentData = {
          orgId: this.orgId,
          status: 'ACTIVE',
          type: 'OPERATING',
          userdefine: true,
          isdelete: false,
          ciId: res.data,
          ciname: '',
          classstructureId: '',
          description: '',
          locationId: '',
          managerId: '',
          managerNm: '',
          extaln1: '',
          extaln2: '',
          extaln3: '',
          extaln4: '',
          extaln5: '',
          extaln6: '',
          extaln7: '',
          extaln8: '',
          extaln9: '',
          extaln10: '',
          extaln11: '',
          extaln12: '',
          extaln13: '',
          extaln14: '',
          extaln15: '',
          extaln16: '',
          extaln17: '',
          extaln18: '',
          extaln19: '',
          extaln20: '',
          extnum1: '',
          extnum2: '',
          extnum3: '',
          extnum4: '',
          extnum5: '',
          extdate1: '',
          extdate2: '',
          extdate3: '',
          extdate4: '',
          extdate5: '',
          extbool1: false,
          extbool2: false,
          extbool3: false,
          extbool4: false,
          extbool5: false
        }
        this.listGrid.ciExtNmData = []
        this.specGrid.data = []
        this.rparespmapGrid.data = []
        this.relationGrid.data = []
      }).catch(error => {
        console.log(error)
      })
    },
    copy: function () {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/ciid?orgId=' + this.orgId
      ).then(res => {
        this.activeTab = 'detail'
        this.detailType = 'INSERT'
        this.listGrid.currentData.id = null
        this.listGrid.currentData.ciId = res.data
        this.listGrid.currentData.userdefine = true
        this.listGrid.currentData.isdelete = false
        this.specGrid.data.forEach(data => {
          data.id = ''
          data.ciId = res.data
          data.state = 'INSERT'
        })
        this.rparespmapGrid.data = []
        this.relationGrid.data = []
      }).catch(error => {
        console.log(error)
      })
    },
    saveSpec: function () {
      let dataList = this.specGrid.data.filter((data, index) => {
        return (data.state === 'CHANGED' || data.state === 'ADDED')
      })
      return dataList
    },
    saveRelation: function () {
      let dataList = this.relationGrid.data.filter(data => {
        return data.state === 'CHANGED'
      })
      return dataList
    },
    saveRparespmap: function () {
      this.validRparespmapFlag = false
      let validList = this.rparespmapGrid.data.filter((data, i) => {
        return this.$util.isNull(data.exetools) || this.$util.isNull(data.linebizstep)
      })
      if (validList.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.rparespmapValidMessage)
        this.validRparespmapFlag = true
        return []
      }
      let dataList = this.rparespmapGrid.data.filter((data, i) => {
        return this.$util.isNull(data.id)
      })
      return dataList
    },
    save: function () {
      let serviceDetailDefault = this.$refs.serviceDetail.$refs.serviceDetailDefault
      if (serviceDetailDefault.isValidationCheck()) {
        let specList = this.saveSpec()
        let rparespmapList = this.saveRparespmap()
        let relationList = this.saveRelation()
        if (this.validRparespmapFlag) return
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/asset/servicemgmnt/service/' + this.listGrid.currentData.id : '/episode/api/asset/servicemgmnt/service'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || specList.length > 0 || rparespmapList.length > 0 || relationList.length > 0) {
          this.listGrid.currentData.specList = specList
          this.listGrid.currentData.rparespmapList = rparespmapList
          this.listGrid.currentData.relationList = relationList
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
      } else if (this.specGrid.data.length > 0 || this.relationGrid.data.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else {
        let $this = this
        this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
          this.$http.delete('/episode/api/asset/servicemgmnt/service/' + $this.listGrid.currentData.id).then(res => {
            $this.$util.esmessage($this, 'delete', $this.labels.deleteMessage)
            $this.activeTab = 'list'
            $this.listGrid.currentData.classstructureId = null
            $this.getListData($this.listGrid.srchCnd)
          }).catch(error => {
            $this.$util.esmessage($this, 'warn', $this.labels.failMessage)
            console.log(error)
          })
        })
      }
    },
    // EVENT FN ///////////////////////////////////////////
    openUploadModal: function (data) {
      this.$refs.serviceFile.openUploadModal(data)
    },
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
              $this.specGrid.data = []
              $this.rparespmapGrid.data = []
              $this.relationGrid.data = []
              $this.activeTab = 'list'
            })
        }
        if (rtnFlag) {
          this.specGrid.data = []
          this.rparespmapGrid.data = []
          this.relationGrid.data = []
          this.hisGrid.data = []
          this.$nextTick(function () {
            this.activeTabFlag.relation = false
            this.activeTabFlag.his = false
            this.activeTabFlag.file = false
          })
        }
      }
      return rtnFlag
    },
    isChkChg: function () {
      let specList = this.saveSpec()
      let rparespmapList = this.rparespmapGrid.data.filter((data, i) => {
        return this.$util.isNull(data.exetools) || this.$util.isNull(data.linebizstep)
      })
      let relationList = this.saveRelation()
      return this.listGrid.currentData.state === 'CHANGED' ||
             specList.length > 0 || rparespmapList.length > 0 ||
             relationList.length > 0
    },
    clickNode: function (node) {
      this.searchParam = ''
      this.listGrid.filters[0].value = ''
      if (!this.$util.isNull(node)) {
        let filterVal = (node.data.classstructureId === 'CLASSSTRUCTURE' ? '' : node.data.classstructureId)
        this.listGrid.filters[1].value = filterVal
      }
    },
    clickAddBtn: function (tree) {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/ciid?orgId=' + this.orgId
      ).then(res => {
        this.activeTab = 'detail'
        this.detailType = 'INSERT'
        this.listGrid.currentData = {
          orgId: this.orgId,
          status: 'ACTIVE',
          type: 'OPERATING',
          userdefine: true,
          isdelete: false,
          ciId: res.data,
          ciname: '',
          classstructureId: tree.classstructureId,
          classstructureNm: tree.description,
          hierarchypath: tree.hierarchypath + '/',
          description: '',
          locationId: '',
          managerId: '',
          managerNm: '',
          extaln1: '',
          extaln2: '',
          extaln3: '',
          extaln4: '',
          extaln5: '',
          extaln6: '',
          extaln7: '',
          extaln8: '',
          extaln9: '',
          extaln10: '',
          extaln11: '',
          extaln12: '',
          extaln13: '',
          extaln14: '',
          extaln15: '',
          extaln16: '',
          extaln17: '',
          extaln18: '',
          extaln19: '',
          extaln20: '',
          extnum1: '',
          extnum2: '',
          extnum3: '',
          extnum4: '',
          extnum5: '',
          extdate1: '',
          extdate2: '',
          extdate3: '',
          extdate4: '',
          extdate5: '',
          extbool1: false,
          extbool2: false,
          extbool3: false,
          extbool4: false,
          extbool5: false
        }
        this.listGrid.ciExtNmData = []
        this.specGrid.data = []
        this.relationAppGrid.data = []
        this.relationServiceGrid.data = []
        this.relationCIGrid.data = []
        this.$http.get(
          '/episode/api/asset/servicemgmnt/service/ciextnm?classstructureId=' + tree.classstructureId + '&lang=' + this.$store.getters.getUser.lang
        ).then(res => {
          this.listGrid.ciExtNmData = res.data
          this.$refs.serviceDetail.makeSpec()
        }).catch(error => {
          console.log(error)
        })
      }).catch(error => {
        console.log(error)
      })
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.searchParam
    },
    searchDetail: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
    },
    searchDetailReset: function () {
      this.listGrid.currentPage = 1
      this.listGrid.searchParamDetail.ciId = ''
      this.listGrid.searchParamDetail.classstructure = ''
      this.listGrid.searchParamDetail.classstructureNm = ''
      this.listGrid.searchParamDetail.ciname = ''
      this.listGrid.searchParamDetail.description = ''
      this.listGrid.searchParamDetail.status = ''
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
    },
    openClassDialog: function () {
      this.showClassDialogFlag = true
    },
    confirmClass: function (rslt) {
      this.visibleSearchDetailFlag = true
      this.showClassDialogFlag = false
      this.listGrid.searchParamDetail.classstructure = rslt.classstructureId
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
      let orderBy = 'id'
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
        object: 'SERVICE',
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        ciId: this.listGrid.searchParamDetail.ciId,
        classstructure: this.listGrid.searchParamDetail.classstructure,
        ciname: this.listGrid.searchParamDetail.ciname,
        description: this.listGrid.searchParamDetail.description,
        itsmciid: this.listGrid.searchParamDetail.itsmciid,
        status: this.listGrid.searchParamDetail.status}
      }
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service',
        params
      ).then(res => {
        let fileName = 'service'
        let fields = [
          {value: 'ciId', label: this.labels.ciId},
          {value: 'classstructureNm', label: this.labels.classstructureId},
          {value: 'ciname', label: this.labels.name},
          {value: 'description', label: this.labels.description},
          {value: 'status', label: this.labels.status}
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
    openTopology: function () {
      eventBus.$emit('TopologyServiceOpen', this.listGrid.currentData)
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
      this.relationGrid.searchParam = ''
      this.relationGrid.filters[0].value = ''
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
      let orderBy = 'id'
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
        object: 'SERVICE',
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        ciId: this.listGrid.searchParamDetail.ciId,
        classstructure: this.listGrid.searchParamDetail.classstructure,
        ciname: this.listGrid.searchParamDetail.ciname,
        description: this.listGrid.searchParamDetail.description,
        itsmciid: this.listGrid.searchParamDetail.itsmciid,
        status: this.listGrid.searchParamDetail.status}
      }
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
        this.listGrid.currentPage = srchCnd.page
        this.getRowData(this.listGrid.data[this.listGrid.rowIndex].id)
      }).catch(error => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.initialize()
    this.paramInit()
  },
  beforeDestroy: function () {
    this.tree.data = []
    this.listGrid.data = []
    this.specGrid.data = []
    this.rparespmapGrid.data = []
    this.relationGrid.data = []
    this.hisGrid.data = []
    this.$nextTick(function () {
      this.tree.showTreeFlag = false
      this.showListGridFlag = false
      this.activeTabFlag.main = false
      this.activeTabFlag.relation = false
      this.activeTabFlag.his = false
      this.activeTabFlag.file = false
    })
  }
}
</script>
