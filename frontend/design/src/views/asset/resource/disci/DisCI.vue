<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="2" >
          <div v-show="(activeTab !== 'list')">
            <b-button class="move-btn" v-on:click="movePage('previous')">
              <img src="../../../../assets/img/icons/arrow-left@2x.png"/>
            </b-button>
            <b-button class="move-btn" v-on:click="movePage('next')">
              <img src="../../../../assets/img/icons/arrow-right@2x.png"/>
            </b-button>
          </div>
        </b-col>
        <b-col sm="10">
          <div class="comm-btn-box" role="group" style="height:40px;">
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="12">
          <!--LIST-->
          <el-tabs v-model="activeTab" v-on:tab-click='clickTab'>
            <el-tab-pane  name="list" :label='labels.list'>
              <b-row>
                <b-col sm="12">
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
                            <label for="disciId">{{labels.ciId}}</label>
                            <input id="disciId" class="formdata sm float-right" v-model="listGrid.searchParamDetail.disciId">
                          </div>
                          <div class='form-group col-md-6'>
                            <label for="ciIdD">{{labels.ciid}}</label>
                            <input id="ciIdD" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ciId">
                          </div>
                          <div class='form-group col-md-6'>
                            <label for="disciclass">{{labels.classstructureId}}</label>
                            <input id="disciclass" class="formdata sm float-right" v-model="listGrid.searchParamDetail.disciclass">
                          </div>
                          <div class='form-group col-md-6'>
                            <label for="disciname">{{labels.name}}</label>
                            <input id="disciname" class="formdata sm float-right" v-model="listGrid.searchParamDetail.disciname">
                          </div>
                          <div class='form-group col-md-6'>
                            <label for="datasrc">{{labels.datasrc}}</label>
                            <input id="datasrc" class="formdata sm float-right" v-model="listGrid.searchParamDetail.datasrc">
                          </div>
                          <div class='form-group col-md-6'>
                            <label for="discitype">{{labels.discitype}}</label>
                            <input id="discitype" class="formdata sm float-right" v-model="listGrid.searchParamDetail.discitype">
                          </div>
                          <div class='form-group col-md-6'>
                            <label for="discidescription">{{labels.description}}</label>
                            <input id="discidescription" class="formdata sm float-right" v-model="listGrid.searchParamDetail.discidescription">
                          </div>
                          <div class='form-group col-md-6'>
                            <label for="isdelete">{{labels.deleteWhether}}</label>
                            <el-select v-model="listGrid.searchParamDetail.isdelete" :placeholder="labels.selectData">
                              <el-option v-for="isdelete in comboData.isdeleteSelect" :prop="isdelete.value" :value="isdelete.value" :key="isdelete.key">
                                {{isdelete.value}}
                              </el-option>
                            </el-select>
                          </div>
                          <div class='form-group col-md-6'>
                            <label for="matching">{{labels.matching}}</label>
                            <el-select v-model="listGrid.searchParamDetail.matching" :placeholder="labels.selectData">
                              <el-option v-for="matching in comboData.matchingSelect" :prop="matching.value" :value="matching.value" :key="matching.key">
                                {{matching.value}}
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
                      <data-tables-server v-if="listGridFlag" ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" v-on:row-click="clickGridRow" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps"  :current-page.sync='listGrid.currentPage'>
                        <el-table-column :prop='listGrid.titles[0].prop' :label='labels.datasrc'          sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[1].prop' :label='labels.ciId'             sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[2].prop' :label='labels.discitype'        sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[3].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[4].prop' :label='labels.ciid'             sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[5].prop' :label='labels.name'             sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[6].prop' :label='labels.description'      sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
                      </data-tables-server>
                    </b-col>
                  </b-row>
                </b-col>
              </b-row>
            </el-tab-pane>
            <!--DETAIL-->
            <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
              <DisCIDetail
                ref="disciDetail"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:listGrid="listGrid"
                v-bind:specGrid="specGrid"
              />
            </el-tab-pane>
            <!--CI relation-->
            <el-tab-pane  name="cirelation" :label='labels.cirelation' v-if="(activeTab !== 'list')">
              <DisCIRelation
                ref="disciRelation"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:listGrid="listGrid"
                v-bind:relationGrid="relationGrid"
                v-on:moveCi="clickGridRow"
              />
            </el-tab-pane>
          </el-tabs>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>
<script>
import DisCIDetail from './DisCIDetail'
import DisCIRelation from './DisCIRelation'
export default {
  name: 'DISCI',
  components: {
    'DisCIDetail': DisCIDetail,
    'DisCIRelation': DisCIRelation
  },
  data () {
    return {
      labels: {
        ciId: ''
      },
      listGrid: {
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
          prop: 'discitype',
          width: '150px'
        }, {
          prop: 'disciclass',
          width: '200px'
        }, {
          prop: 'ciId',
          width: '110px'
        }, {
          prop: 'disciname',
          width: '300px'
        }, {
          prop: 'discidescription',
          width: '300x'
        }],
        columns: ['id', 'disciId', 'disciclass', 'discidescription', 'status'],
        columnNames: ['id', 'disciId', 'disciclass', 'discidescription', 'status'],
        currentData: {},
        disciExtNmData: {},
        total: 0,
        srchCnd: null,
        searchParamDetail: {
          disciId: '',
          ciId: '',
          disciclass: '',
          disciname: '',
          datasrc: '',
          discitype: '',
          discidescription: '',
          isdelete: '',
          matching: ''
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
          prop: 'disclassspecId',
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
          prop: ['relationname', 'sourceci', 'sourceclass', 'targetci', 'targetclass'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'relationname',
          width: '100px'
        }, {
          prop: 'srcdisciId',
          width: '300px'
        }, {
          prop: 'srcdisciNm',
          width: '260px'
        }, {
          prop: 'srcdisciclass',
          width: '150px'
        }, {
          prop: 'tgtdisciId',
          width: '300px'
        }, {
          prop: 'tgtdisciNm',
          width: '260px'
        }, {
          prop: 'tgtdisciclass',
          width: '150px'
        }],
        currentData: {},
        total: 0
      },
      // Auth
      authorities: {
        DISCI_ATTACHMENT: false,
        DISCI_READ: false,
        DISCI_CI_MAP_SAVE: false
      },
      // Combo Data
      comboData: {
        orgSelect: [],
        statusSelect: [],
        typeSelect: [],
        relationSelect: [],
        isdeleteSelect: [],
        matchingSelect: []
      },
      // Global Var
      orgId: '',
      activeTab: 'list',
      searchType: 'NORMAL',
      searchParam: '',
      ciDialogParam: '',
      // Global Flag
      showRelationmapDialogFlag: false,
      visibleSearchDetailFlag: false,
      listGridFlag: false
    }
  },
  watch: {
    '$route': function () {
      if (!this.$util.isNull(this.$route.params.discitype)) {
        this.jobEms()
      }
      if (!this.$util.isNull(this.$route.params.ciId)) {
        this.searchCIID()
      }
    }
  },
  computed: {
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
          {dataObj: 'comboData.isdeleteSelect', domainId: 'BOOLEANTYPE'},
          {dataObj: 'comboData.matchingSelect', domainId: 'BOOLEANTYPE'}
        ]
      }
      this.listGridFlag = true
      this.$util.getComboData(param)
      this.orgId = this.$store.getters.getUser.orgId
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      let ciId = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        ciId = srchCnd.filters[3].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        searchParam: searchParam,
        ciId: ciId,
        disciIdD: this.listGrid.searchParamDetail.disciId,
        ciIdD: this.listGrid.searchParamDetail.ciId,
        disciclass: this.listGrid.searchParamDetail.disciclass,
        disciname: this.listGrid.searchParamDetail.disciname,
        datasrc: this.listGrid.searchParamDetail.datasrc,
        discitype: this.listGrid.searchParamDetail.discitype,
        discidescription: this.listGrid.searchParamDetail.discidescription,
        isdelete: this.listGrid.searchParamDetail.isdelete,
        matching: this.listGrid.searchParamDetail.matching}
      }
      this.$http.get(
        '/episode/api/asset/resource/disci',
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
        '/episode/api/asset/resource/disci/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
      this.getSpecData(id)
      this.getRelationData(id)
    },
    getSpecData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/disci/' + id + '/discispec'
      ).then(res => {
        this.specGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRelationData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/disci/' + id + '/discirelation'
      ).then(res => {
        this.relationGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    clickTab: function (tab) {
      this.activeTab = tab.name
    },
    clickNode: function (node) {
      this.searchParam = ''
      if (!this.$util.isNull(node)) {
        this.searchType = 'TREE'
        let filterVal = (node.data.classstructureId === 'CLASSSTRUCTURE' ? '' : node.data.classstructureId)
        this.listGrid.filters[0].value = filterVal
      }
    },
    filterFocus: function () {
      this.listGrid.filters[0].value = ''
    },
    enterSearch: function () {
      this.searchType = 'NORMAL'
      this.listGrid.filters[0].value = this.searchParam
      this.listGrid.filters[1].value = ''
      this.listGrid.filters[2].value = ''
      this.listGrid.filters[3].value = ''
      this.listGrid.currentPage = 1
    },
    searchDetail: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[1].value = ''
      this.listGrid.filters[2].value = ''
      this.listGrid.filters[3].value = ''
      this.visibleSearchDetailFlag = false
    },
    searchDetailReset: function () {
      this.listGrid.currentPage = 1
      this.listGrid.searchParamDetail.disciId = ''
      this.listGrid.searchParamDetail.ciId = ''
      this.listGrid.searchParamDetail.disciclass = ''
      this.listGrid.searchParamDetail.disciname = ''
      this.listGrid.searchParamDetail.datasrc = ''
      this.listGrid.searchParamDetail.discitype = ''
      this.listGrid.searchParamDetail.discidescription = ''
      this.listGrid.searchParamDetail.isdelete = ''
      this.listGrid.searchParamDetail.matching = ''
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[1].value = ''
      this.listGrid.filters[2].value = ''
      this.listGrid.filters[3].value = ''
      this.visibleSearchDetailFlag = false
    },
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      let srchCnd = this.listGrid.srchCnd
      let orderBy = 'id'
      let sort = 'ascending'
      let ciId = this.listGrid.filters[3].value
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
        ciId: ciId,
        disciIdD: this.listGrid.searchParamDetail.disciId,
        ciIdD: this.listGrid.searchParamDetail.ciId,
        disciclass: this.listGrid.searchParamDetail.disciclass,
        disciname: this.listGrid.searchParamDetail.disciname,
        datasrc: this.listGrid.searchParamDetail.datasrc,
        discitype: this.listGrid.searchParamDetail.discitype,
        discidescription: this.listGrid.searchParamDetail.discidescription,
        isdelete: this.listGrid.searchParamDetail.isdelete,
        matching: this.listGrid.searchParamDetail.matching}
      }
      this.$http.get(
        '/episode/api/asset/resource/disci',
        params
      ).then(res => {
        let fileName = 'disci'
        let fields = [
          {value: 'datasrc', label: this.labels.datasrc},
          {value: 'disciId', label: this.labels.ciId},
          {value: 'discitype', label: this.labels.discitype},
          {value: 'disciclass', label: this.labels.classstructureId},
          {value: 'ciId', label: this.labels.ciid},
          {value: 'disciname', label: this.labels.name},
          {value: 'discidescription', label: this.labels.description}
        ]
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    clickGridRow: function (row, column, event) {
      this.activeTab = 'detail'
      this.listGrid.rowIndex = this.listGrid.data.indexOf(row)
      this.getRowData(row.id)
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
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      let ciId = this.listGrid.filters[3].value
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        searchParam: searchParam,
        ciId: ciId,
        disciIdD: this.listGrid.searchParamDetail.disciId,
        ciIdD: this.listGrid.searchParamDetail.ciId,
        disciclass: this.listGrid.searchParamDetail.disciclass,
        disciname: this.listGrid.searchParamDetail.disciname,
        datasrc: this.listGrid.searchParamDetail.datasrc,
        discitype: this.listGrid.searchParamDetail.discitype,
        discidescription: this.listGrid.searchParamDetail.discidescription,
        isdelete: this.listGrid.searchParamDetail.isdelete,
        matching: this.listGrid.searchParamDetail.matching}
      }
      this.$http.get(
        '/episode/api/asset/resource/disci',
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
    jobEms: function () {
      if (!this.$util.isNull(this.$route.params.discitype)) {
        this.listGrid.searchParamDetail.datasrc = this.$route.params.datasrc
        this.listGrid.searchParamDetail.isdelete = this.$route.params.isdelete
        this.listGrid.searchParamDetail.matching = this.$route.params.matching
        this.listGrid.searchParamDetail.discitype = this.$route.params.discitype
      }
    },
    searchCIID: function () {
      if (!this.$util.isNull(this.$route.params.ciId)) {
        this.listGrid.filters[3].value = this.$route.params.ciId
      }
    }
  },
  created: function () {
    this.initialize()
    this.jobEms()
    this.searchCIID()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.specGrid.data = []
    this.relationGrid.data = []
    this.isdeleteSelect = []
    this.matchingSelect = []
    this.labels = {}
    this.authorities = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
