<!--
 Class Name  : CI.vue
 Description : IT 자원 > 메인리스트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
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
            <b-button variant="primary" class="px-3 sm" v-show="visibleOpenRelationMap" v-on:click="openRelationmapDialog">{{labels.relationMap}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleOpenDisCIMap" v-on:click="openDiscimapDialog">{{labels.disciMap}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleOpenAttach" v-on:click="openUploadModal(listGrid.currentData)">{{labels.attachFile}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleCopy" v-on:click="copy">{{labels.copy}}</b-button>
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
                          <label for="itsmciid">ITSM CIID</label>
                          <input id="itsmciid" class="formdata sm float-right" v-model="listGrid.searchParamDetail.itsmciid"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="status">{{labels.opstatus}}</label>
                          <el-select v-model="listGrid.searchParamDetail.status" :placeholder="labels.selectData">
                            <el-option v-for="status in comboData.statusSelect" :prop="status.value" :value="status.value" :key="status.key">
                              {{status.value}}
                            </el-option>
                          </el-select>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="ancestor">{{labels.ancestor}}</label>
                          <input id="ancestor" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ancestor"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="manager">{{labels.mgConfirm}}</label>
                          <el-select v-model="listGrid.searchParamDetail.manager" :placeholder="labels.selectData">
                            <el-option v-for="manager in comboData.managerSelect" :prop="manager.value" :value="manager.key" :key="manager.key">
                              {{manager.value}}
                            </el-option>
                          </el-select>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="swlink">{{labels.swlink}}</label>
                          <el-select v-model="listGrid.searchParamDetail.swlink" :placeholder="labels.selectData">
                            <el-option v-for="swlink in comboData.swlinkSelect" :prop="swlink.value" :value="swlink.key" :key="swlink.key">
                              {{swlink.value}}
                            </el-option>
                          </el-select>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="scan">{{labels.scan}}</label>
                          <el-select v-model="listGrid.searchParamDetail.scan" :placeholder="labels.selectData">
                            <el-option v-for="scan in comboData.scanSelect" :prop="scan.value" :value="scan.key" :key="scan.key">
                              {{scan.value}}
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
                      <data-tables-server v-if="showListGridFlag" ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" v-on:row-click="clickGridRow" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps" :current-page.sync='listGrid.currentPage'>
                        <el-table-column :prop='listGrid.titles[0].prop' :label='labels.ciId'             sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[1].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[2].prop' :label='labels.name'             sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[3].prop' :label='labels.description'      sortable='custom' ></el-table-column>
                        <el-table-column :prop='listGrid.titles[4].prop' :label='"ITSM CIID"'             sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[5].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
                      </data-tables-server>
                    </b-col>
                  </b-row>
                </b-col>
              </b-row>
            </el-tab-pane>
            <!--DETAIL-->
            <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
              <CIDetail
                ref="ciDetail"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:specGrid="specGrid"
                v-bind:rparespmapGrid="rparespmapGrid"
                v-bind:relationTabGrid="relationTabGrid"
                v-bind:relationSwGrid="relationSwGrid"
                v-bind:downtimeGrid="downtimeGrid"
                v-bind:itsmData="itsmData"
                v-bind:itsmSpecGrid="itsmSpecGrid"
                v-bind:disciGrid="disciGrid"
              />
            </el-tab-pane>
            <!--CI relation-->
            <el-tab-pane  name="cirelation" :label='labels.cirelation' v-if="(activeTab !== 'list')">
              <CIRelation
                ref="ciRelation"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:relationGrid="relationGrid"
                v-bind:getRelationData="getRelationData"
                v-on:moveCI="clickGridRow"
              />
            </el-tab-pane>
            <!--CI historty-->
            <el-tab-pane  name="cihistory" :label='labels.cihistory' v-if="(activeTab !== 'list')">
              <CIHis
                ref="ciHis"
                v-bind:labels="labels"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:hisGrid="hisGrid"
              />
            </el-tab-pane>
            <!--CI File-->
            <el-tab-pane  name="cifile" :label='labels.cifile' v-if="(activeTab !== 'list')">
              <CIFile
                ref="ciFile"
                v-bind:labels="labels"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
              />
            </el-tab-pane>
          </el-tabs>
        </b-col>
      </b-row>
    </b-card>
    <!-- CIrelationmap Dialog -->
    <CIrelationmap
      ref="cirelationmapDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showRelationmapDialogFlag"
      v-bind:authorities="authorities"
      v-on:close="closeRelationmapDialog"
    />
    <!-- CIDiscimap -->
    <CIDiscimap
      ref="ciDiscimap"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showDiscimapDialogFlag"
      v-bind:authorities="authorities"
      v-on:close="closeDiscimapDialog"
    />
  </div>
</template>
<script>
import Tree from '@/views/common/Tree'
import CIDetail from './CIDetail'
import CIRelation from './CIRelation'
import CIHis from './CIHis'
import CIFile from './CIFile'
import CIrelationmap from './CIrelationmap'
import CIDiscimap from './CIDiscimap'
// const Tree = () => import('@/views/common/Tree')
// const CIDetail = () => import('./CIDetail')
// const CIRelation = () => import('./CIRelation')
// const CIHis = () => import('./CIHis')
// const CIFile = () => import('./CIFile')
// const CIrelationmap = () => import('./CIrelationmap')
// const CIDiscimap = () => import('./CIDiscimap')
export default {
  name: 'CI',
  props: ['type'],
  components: {
    'Tree': Tree,
    'CIDetail': CIDetail,
    'CIRelation': CIRelation,
    'CIHis': CIHis,
    'CIFile': CIFile,
    'CIrelationmap': CIrelationmap,
    'CIDiscimap': CIDiscimap
  },
  data () {
    return {
      labels: {
        id: '', spec: '', name: '', chgtype: '', value: ''
      },
      tree: {
        dataType: 'RAW',
        data: [],
        rawData: [],
        selectData: {},
        isRootDataFlag: true,
        rootData: {text: 'CLASSSTRUCTURE', data: {classstructureId: 'CLASSSTRUCTURE', hierarchypath: '', classicon: 'sheets-48.png'}, children: []},
        rootVal: null,
        parentColNm: 'parent',
        childColNm: 'classstructureId',
        textColNm: 'description',
        iconImgType: 'svg',
        showTreeFlag: false,
        isUseCntFlag: false,
        showNodeIconFn: function (node) {
          return node.data.classiconsvg
        },
        shwoAddIconFn: function (node) {
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
        }, {
          prop: 'jobCi',
          value: ''
        }, {
          prop: 'cf',
          value: ''
        }, {
          prop: 'scanCiType',
          value: ''
        }, {
          prop: 'infraType',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'ciId',
          width: '150px'
        }, {
          prop: 'classstructureNm',
          width: '180px'
        }, {
          prop: 'ciname',
          width: '150px'
        }, {
          prop: 'description',
          width: '200x'
        }, {
          prop: 'extaln18',
          width: '150px'
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
          status: '',
          ancestor: '',
          manager: '',
          swlink: '',
          scan: ''
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
        total: 0
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
          prop: ['relationname', 'sourceci', 'sourceciNm', 'sourceclass', 'targetci', 'targetciNm', 'targetclass', 'status'],
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
          width: '120px'
        }, {
          prop: 'updateDt',
          width: '180px'
        }],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      relationTabGrid: {
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
          prop: ['relationname', 'sourceci', 'sourceclass', 'targetci', 'targetclass'],
          value: ''
        }],
        filteredData: [],
        currentData: {},
        total: 0
      },
      relationSwGrid: {
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
          prop: ['classstructureNm', 'publishername', 'swcatname', 'swcatversion'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'classstructureNm', label: 'swClass', width: '160px' },
          { prop: 'publishername', label: 'publisher', width: '250px' },
          { prop: 'swcatname', label: 'name', width: '270px' },
          { prop: 'swcatversion', label: 'version', width: '160px' },
          { prop: 'startDt', label: 'version', width: '180px' },
          { prop: 'endDt', label: 'version', width: '180px' },
          { prop: 'islicnouse', label: 'version', width: '140px' },
          { prop: 'licnusecause', label: 'version', width: '80px' }
        ],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      downtimeGrid: {
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
          prop: ['originStartDt', 'originEndDt', 'downtime'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'originStartDt', label: '', width: '160px' },
          { prop: 'originEndDt', label: '', width: '300px' },
          { prop: 'downtime', label: '', width: '270px' }
        ],
        currentData: {},
        total: 0
      },
      itsmData: {
        ciTypeNm: '',
        kaiCiid: '',
        ciName: '',
        usestatus: ''
      },
      itsmSpecGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; width: 70%; height:60px'
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
          prop: ['ciAttrName', 'ciAttrValueLast'],
          value: ''
        }],
        filteredData: [],
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
      disciGrid: {
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
          pageSize: 10,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: [],
          value: ''
        }, {
          prop: 'discitype',
          value: ''
        }, {
          prop: 'cf',
          value: ''
        }, {
          prop: 'ciId',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'datasrc',
          width: '130px'
        }, {
          prop: 'disciId',
          width: '300px'
        }, {
          prop: 'disciname',
          width: '300px'
        }, {
          prop: 'disciclass',
          width: '200px'
        }, {
          prop: 'discidescription',
          width: '200x'
        }, {
          prop: 'discoveryDt',
          width: '200x'
        }],
        columns: ['id', 'disciId', 'disciclass', 'discidescription', 'status'],
        columnNames: ['id', 'disciId', 'disciclass', 'discidescription', 'status'],
        currentData: {},
        disciExtNmData: {},
        total: 0
      },
      // Auth
      authorities: {
        CI_ATTACHMENT: false,
        CI_ADD: false,
        CI_SAVE: false,
        CI_DELETE: false,
        CI_SPEC_ADD: false,
        CI_SPEC_SAVE: false,
        CI_SPEC_DELETE: false,
        CI_RPA_ADD: false,
        CI_RPA_SAVE: false,
        CI_RPA_DELETE: false,
        CI_RELATION_ADD: false,
        CI_RELATION_SAVE: false,
        CI_RELATION_DELETE: false,
        CI_RELATION_MAP_ADD: false,
        CI_RELATION_MAP_SAVE: false,
        CI_RELATION_MAP_DELETE: false,
        CI_DISCI_MAP_ADD: false,
        CI_DISCI_MAP_SAVE: false,
        CI_DISCI_MAP_DELETE: false
      },
      // Combo Data
      comboData: {
        orgSelect: [],
        statusSelect: [],
        typeSelect: [],
        relationSelect: [],
        exetoolsSelect: [],
        linebizstepSelect: [],
        relationStatusSelect: [],
        managerSelect: [],
        swlinkSelect: [],
        scanSelect: []
      },
      // Global Var
      orgId: '',
      activeTab: 'list',
      detailType: 'INSERT',
      searchParam: '',
      ciDialogParam: '',
      // Global Flag
      showRelationmapDialogFlag: false,
      showDiscimapDialogFlag: false,
      validRparespmapFlag: false,
      visibleSearchDetailFlag: false,
      showClassDialogFlag: false,
      showListGridFlag: true
    }
  },
  watch: {
    '$route': function () {
      if (!this.$util.isNull(this.$route.params.id)) {
        this.paramInit()
      } else if (!this.$util.isNull(this.$route.params.cl)) {
        this.cl()
      } else if (!this.$util.isNull(this.$route.params.jobCi)) {
        this.jobCi()
      } else if (!this.$util.isNull(this.$route.params.scan)) {
        this.scanCi()
      }
    },
    activeTab: function (val) {
      if (val === 'list') {
        this.listGrid.currentData = {}
        this.relationSwGrid.searchParam = ''
        this.relationSwGrid.filters[0].value = ''
        this.itsmSpecGrid.searchParam = ''
        this.itsmSpecGrid.filters[0].value = ''
        this.relationGrid.searchParam = ''
        this.relationGrid.filters[0].value = ''
        this.getListData(this.listGrid.srchCnd)
      } else if (val === 'detail') {
        if (!this.$util.isNull(this.listGrid.currentData.id)) {
          this.getRelationTabData(this.listGrid.currentData.id)
        }
      } else if (val === 'cihistory') {
        if (this.detailType === 'UPDATE') {
          this.getHisData(this.listGrid.currentData.id)
        }
      }
    }
  },
  computed: {
    visibleAdd: function () {
      return this.authorities.CI_ADD && (this.activeTab === 'list')
    },
    visibleCopy: function () {
      return this.authorities.CI_ADD && (this.activeTab !== 'list') && this.detailType === 'UPDATE'
    },
    visibleSave: function () {
      return this.authorities.CI_SAVE && (this.activeTab !== 'list')
    },
    visibleDelete: function () {
      return this.authorities.CI_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.userdefine
    },
    visibleOpenRelationMap: function () {
      return this.authorities.CI_RELATION_MAP_SAVE
    },
    visibleOpenDisCIMap: function () {
      return this.authorities.CI_DISCI_MAP_SAVE
    },
    visibleOpenAttach: function () {
      return this.authorities.CI_ATTACHMENT && (this.activeTab !== 'list') && this.detailType === 'UPDATE'
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
      this.tree.showTreeFlag = false
      this.$http.get(
        '/episode/api/system/common/classification?orgId=' + this.orgId + '&object=CI'
      ).then(res => {
        this.tree.rawData = res.data
        if (!this.$util.isNull(this.$route.params.cl)) {
          let cl = this.tree.rawData.filter(r => r.classstructureId === this.$route.params.cl)[0]
          this.tree.selectData = cl
        }
        if (!this.$util.isNull(this.$route.params.scan)) {
          let ancestor = this.tree.rawData.filter(r => r.classstructureId === this.$route.params.ancestor)[0]
          this.tree.selectData = ancestor
        }
      }).catch(error => {
        console.log(error)
        this.tree.showTreeFlag = true
      })
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'comboData.orgSelect', query: '/episode/api/system/common/organization'},
          {dataObj: 'comboData.statusSelect', domainId: 'CISTATUS'},
          {dataObj: 'comboData.typeSelect', domainId: 'CITYPE'},
          {dataObj: 'comboData.relationStatusSelect', domainId: 'STATUS'},
          {dataObj: 'comboData.managerSelect', domainId: 'BOOLEANTYPE', sel: 'S'},
          {dataObj: 'comboData.swlinkSelect', domainId: 'BOOLEANTYPE', sel: 'S'},
          {dataObj: 'comboData.scanSelect', domainId: 'SCANSTATUS', sel: 'S'}
        ]
      }
      this.$util.getComboData(param)
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      let treeSearchParam = ''
      let infraType = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        treeSearchParam = srchCnd.filters[1].value
        infraType = srchCnd.filters[5].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        ciId: this.listGrid.searchParamDetail.ciId,
        classstructure: this.listGrid.searchParamDetail.classstructure,
        ciname: this.listGrid.searchParamDetail.ciname,
        description: this.listGrid.searchParamDetail.description,
        itsmciid: this.listGrid.searchParamDetail.itsmciid,
        status: this.listGrid.searchParamDetail.status,
        manager: this.listGrid.searchParamDetail.manager,
        swlink: this.listGrid.searchParamDetail.swlink,
        ancestor: (this.listGrid.searchParamDetail.ancestor).replace(/(\s*)/g, ''),
        scan: this.listGrid.searchParamDetail.scan,
        infraType: infraType}
      }
      this.$http.get(
        '/episode/api/asset/resource/ci',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    getRowData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id
      ).then(res => {
        this.listGrid.currentData = {}
        this.listGrid.currentData = res.data.data
        this.listGrid.ciExtNmData = res.data.ciExtNmData
      }).catch(error => {
        console.log(error)
      })
      this.getSpecData(id)
      this.getRpareapmapData(id)
      this.getRpacodeData(id)
      this.getRelationData(id)
      this.getRelationTabData(id)
      this.getRelationSwData(id)
      this.getDowntimeData(id)
      this.getItsmCIData(id)
      this.getHisData(id)
      this.getDisciData(id)
    },
    getSpecData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/cispec'
      ).then(res => {
        this.specGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRpareapmapData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/rparespmap'
      ).then(res => {
        this.rparespmapGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRpacodeData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/rpacode'
      ).then(res => {
        this.comboData.exetoolsSelect = res.data.exetoolscode
        this.comboData.linebizstepSelect = res.data.linebizstepcode
      }).catch(error => {
        console.log(error)
      })
    },
    getRelationData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/cirelation'
      ).then(res => {
        this.relationGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRelationTabData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/cirelationTab'
      ).then(res => {
        this.relationTabGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRelationSwData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/relationswlist'
      ).then(res => {
        this.relationSwGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getDowntimeData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/downtimelist'
      ).then(res => {
        this.downtimeGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getItsmCIData: function (id) {
      this.itsmData = {id: null}
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/itsmcidata'
      ).then(res => {
        this.itsmData = res.data.itsmCI
        this.itsmSpecGrid.data = res.data.specList
      }).catch(error => {
        console.log(error)
      })
    },
    getHisData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/cihis'
      ).then(res => {
        this.hisGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getDisciData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/discilist'
      ).then(res => {
        this.disciGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    add: function () {
      this.$http.get(
        '/episode/api/asset/resource/ci/ciid?orgId=' + this.orgId
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
        this.relationTabGrid.data = []
        this.relationSwGrid.data = []
        this.itsmData = {}
        this.itsmSpecGrid.data = []
        this.downtimeGrid.data = []
        this.relationGrid.data = []
        this.hisGrid.data = []
      }).catch(error => {
        console.log(error)
      })
    },
    copy: function () {
      this.$http.get(
        '/episode/api/asset/resource/ci/ciid?orgId=' + this.orgId
      ).then(res => {
        this.activeTab = 'detail'
        this.detailType = 'INSERT'
        this.listGrid.currentData.state = 'ADDED'
        delete this.listGrid.currentData.id
        this.listGrid.currentData.ciId = res.data
        this.listGrid.currentData.userdefine = true
        this.listGrid.currentData.isdelete = false
        this.specGrid.data.forEach(data => {
          data.id = ''
          data.ciId = res.data
          data.state = 'ADDED'
        })
        this.rparespmapGrid.data = []
        this.relationTabGrid.data = []
        this.relationSwGrid.data = []
        this.itsmData = {}
        this.itsmSpecGrid.data = []
        this.downtimeGrid.data = []
        this.relationGrid.data = []
        this.hisGrid.data = []
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
    saveRelationSw: function () {
      let dataList = this.relationSwGrid.data.filter((data, index) => {
        return (data.state === 'CHANGED')
      })
      let saveList = []
      dataList.forEach(data => {
        let save = {
          id: data.ciswId,
          islicnouse: data.islicnouse,
          licnusecause: data.licnusecause
        }
        saveList.push(save)
      })
      return saveList
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
      let ciDetailDefault = this.$refs.ciDetail.$refs.ciDetailDefault
      if (ciDetailDefault.isValidationCheck()) {
        let specList = this.saveSpec()
        let rparespmapList = this.saveRparespmap()
        let relationList = this.saveRelation()
        let relationSwList = this.saveRelationSw()
        if (this.validRparespmapFlag) return
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/asset/resource/ci/' + this.listGrid.currentData.id : '/episode/api/asset/resource/ci'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || specList.length > 0 || rparespmapList.length > 0 || relationList.length > 0 || relationSwList.length > 0) {
          this.listGrid.currentData.specList = specList
          this.listGrid.currentData.rparespmapList = rparespmapList
          this.listGrid.currentData.relationList = relationList
          this.listGrid.currentData.relationSwList = relationSwList
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
          this.$http.delete('/episode/api/asset/resource/ci/' + $this.listGrid.currentData.id).then(res => {
            $this.$util.esmessage($this, 'delete', $this.labels.deleteMessage)
            $this.activeTab = 'list'
            $this.listGrid.currentData = {}
            $this.getListData($this.listGrid.srchCnd)
          }).catch(error => {
            $this.$util.esmessage($this, 'warn', $this.labels.failMessage)
            console.log(error)
          })
        })
      }
    },
    // EVENT FN ///////////////////////////////////////////
    openRelationmapDialog: function () {
      this.showRelationmapDialogFlag = true
    },
    closeRelationmapDialog: function () {
      this.showRelationmapDialogFlag = false
    },
    openDiscimapDialog: function () {
      this.showDiscimapDialogFlag = true
    },
    closeDiscimapDialog: function () {
      this.showDiscimapDialogFlag = false
    },
    openUploadModal: function (data) {
      this.$refs.ciFile.openUploadModal(data)
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
              $this.relationSwGrid.data = []
              $this.activeTab = 'list'
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
      let relationSwList = this.saveRelationSw()
      return this.listGrid.currentData.state === 'CHANGED' ||
             specList.length > 0 || rparespmapList.length > 0 ||
             relationList.length > 0 || relationSwList.length > 0
    },
    clickNode: function (node) {
      this.searchParam = ''
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[2].value = ''
      this.listGrid.filters[3].value = ''
      this.listGrid.filters[4].value = ''
      this.listGrid.filters[5].value = ''
      if (!this.$util.isNull(node)) {
        let filterVal = (node.data.classstructureId === 'CLASSSTRUCTURE' ? '' : node.data.classstructureId)
        this.listGrid.filters[1].value = filterVal
      }
    },
    clickAddBtn: function (tree) {
      this.$http.get(
        '/episode/api/asset/resource/ci/ciid?orgId=' + this.orgId
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
          classstructureId: tree.data.classstructureId,
          classstructureNm: tree.data.description,
          hierarchypath: tree.data.hierarchypath + '/',
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
        this.relationGrid.data = []
        this.$http.get(
          '/episode/api/asset/resource/ci/ciextnm?classstructureId=' + tree.data.classstructureId + '&lang=' + this.$store.getters.getUser.lang
        ).then(res => {
          this.listGrid.ciExtNmData = res.data
          this.$refs.ciDetail.makeSpec()
        }).catch(error => {
          console.log(error)
        })
      }).catch(error => {
        console.log(error)
      })
    },
    filterFocus: function () {
      // this.listGrid.filters[0].value = ''
    },
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
      this.listGrid.filters[2].value = ''
      this.listGrid.filters[3].value = ''
      this.listGrid.filters[4].value = ''
      this.listGrid.filters[5].value = ''
    },
    searchDetail: function () {
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[2].value = ''
      this.listGrid.filters[3].value = ''
      this.listGrid.filters[4].value = ''
      this.listGrid.filters[5].value = ''
      this.visibleSearchDetailFlag = false
    },
    searchDetailReset: function () {
      this.listGrid.searchParamDetail.ciId = ''
      this.listGrid.searchParamDetail.classstructure = ''
      this.listGrid.searchParamDetail.classstructureNm = ''
      this.listGrid.searchParamDetail.ciname = ''
      this.listGrid.searchParamDetail.description = ''
      this.listGrid.searchParamDetail.itsmciid = ''
      this.listGrid.searchParamDetail.status = ''
      this.listGrid.searchParamDetail.ancestor = ''
      this.listGrid.searchParamDetail.manager = ''
      this.listGrid.searchParamDetail.swlink = ''
      this.listGrid.searchParamDetail.scan = ''
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[2].value = ''
      this.listGrid.filters[3].value = ''
      this.listGrid.filters[4].value = ''
      this.listGrid.filters[5].value = ''
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
      let infraType = this.listGrid.filters[5].value
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
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        ciId: this.listGrid.searchParamDetail.ciId,
        classstructure: this.listGrid.searchParamDetail.classstructure,
        ciname: this.listGrid.searchParamDetail.ciname,
        description: this.listGrid.searchParamDetail.description,
        itsmciid: this.listGrid.searchParamDetail.itsmciid,
        status: this.listGrid.searchParamDetail.status,
        ancestor: this.listGrid.searchParamDetail.ancestor,
        manager: this.listGrid.searchParamDetail.manager,
        swlink: this.listGrid.searchParamDetail.swlink,
        scan: this.listGrid.searchParamDetail.scan,
        infraType: infraType}
      }
      this.$http.get(
        '/episode/api/asset/resource/ci',
        params
      ).then(res => {
        let fileName = 'ci'
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
    paramInit: function () {
      if (!this.$util.isNull(this.$route.params.id)) {
        this.activeTab = 'detail'
        this.detailType = 'UPDATE'
        this.getRowData(this.$route.params.id)
      }
    },
    jobCi: function () {
      if (!this.$util.isNull(this.$route.params.jobCi)) {
        if (this.$route.params.jobCi === 'mgConfirmData') {
          this.listGrid.searchParamDetail.manager = this.$route.params.confirm
          this.listGrid.searchParamDetail.ancestor = this.$route.params.ancestor
        } else {
          if (this.$route.params.confirm === 'FALSE') {
            this.listGrid.searchParamDetail.manager = this.$route.params.confirm
          }
          this.listGrid.searchParamDetail.swlink = this.$route.params.confirm
          this.listGrid.searchParamDetail.ancestor = this.$route.params.ancestor
        }
        // let filterVal = this.$route.params.jobCi
        // let filterVal2 = this.$route.params.cf
        // this.listGrid.filters[2].value = filterVal
        // this.listGrid.filters[3].value = filterVal2
      }
    },
    scanCi: function () {
      if (!this.$util.isNull(this.$route.params.scan)) {
        let filterVal = this.$route.params.ancestor
        this.listGrid.filters[1].value = filterVal
        // this.listGrid.searchParamDetail.ancestor = filterVal
        this.listGrid.searchParamDetail.scan = this.$route.params.scan
      }
    },
    cl: function () {
      if (!this.$util.isNull(this.$route.params.cl)) {
        let filterVal = this.$route.params.cl
        let filterVal2 = this.$route.params.infraType
        this.listGrid.filters[1].value = filterVal
        this.listGrid.filters[5].value = filterVal2
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
      this.relationSwGrid.searchParam = ''
      this.relationSwGrid.filters[0].value = ''
      this.itsmSpecGrid.searchParam = ''
      this.itsmSpecGrid.filters[0].value = ''
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
      let infraType = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        treeSearchParam = srchCnd.filters[1].value
        infraType = srchCnd.filters[5].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        ciId: this.listGrid.searchParamDetail.ciId,
        classstructure: this.listGrid.searchParamDetail.classstructure,
        ciname: this.listGrid.searchParamDetail.ciname,
        description: this.listGrid.searchParamDetail.description,
        itsmciid: this.listGrid.searchParamDetail.itsmciid,
        status: this.listGrid.searchParamDetail.status,
        ancestor: this.listGrid.searchParamDetail.ancestor,
        manager: this.listGrid.searchParamDetail.manager,
        swlink: this.listGrid.searchParamDetail.swlink,
        scan: this.listGrid.searchParamDetail.scan,
        infraType: infraType}
      }
      this.$http.get(
        '/episode/api/asset/resource/ci',
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
    this.cl()
    this.jobCi()
    this.scanCi()
  },
  beforeDestroy: function () {
    this.listGrid = {}
    this.$nextTick(function(){
      this.showListGridFlag = false
    }) 
  }
}
</script>
