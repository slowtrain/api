<!--
 Class Name  : License.vue
 Description : 라이선스사용 > 메인리스트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.30  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div  style="height:40px;">
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row class="mt-2">
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
              <label for="classstructure">{{labels.classstructure}}</label>
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
          <data-tables-server v-if="listGridFlag" ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps" :current-page.sync='listGrid.currentPage'>
            <el-table-column :prop='listGrid.titles[0].prop' :label='labels.classstructure' sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[1].prop' :label='labels.publisher'      sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[2].prop' :label='labels.name'           sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[3].prop' :label='labels.licensetype'    sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[4].prop' :label='labels.version'        sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[5].prop' :label='labels.purchaseunit'   sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[6].prop' :label='labels.purchasenum'    sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[7].prop' :label='labels.usenum'         sortable='custom' :width='listGrid.titles[7].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[8].prop' :label='labels.remainnum'      sortable='custom' :width='listGrid.titles[8].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[9].prop' :label='labels.usesever'       sortable='custom' :width='listGrid.titles[9].width'>
              <template  slot-scope="props">
                <a :class="props.row.usesever !== 0 ? 'cursor-pointer link' : ''" v-on:click="openUseServerDialog(props.row)">{{props.row.usesever}}</a>
              </template>
            </el-table-column>
            <el-table-column :prop='listGrid.titles[10].prop' :label='labels.relationlicense' sortable='custom' :width='listGrid.titles[10].width'>
              <template  slot-scope="props">
                <a :class="props.row.relationlicense !== 0 ? 'cursor-pointer link' : ''" v-on:click="openRelationDialog(props.row)">{{props.row.relationlicense}}</a>
              </template>
            </el-table-column>
            <el-table-column :prop='listGrid.titles[11].prop' :label='labels.swcatId'      sortable='custom' :width='listGrid.titles[11].width'></el-table-column>
          </data-tables-server>
        </b-col>
      </b-row>
    </b-card>
    <!-- Use Server Dialog -->
    <LicenseUseServerDialog
      ref="licenseUseServerDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showUseServerDialogFlag"
      v-bind:param="{swcatId: listGrid.currentData.swcatId}"
      v-on:close="closeUseServerDialog"
    />
    <!-- Relation Dialog -->
    <LicenseUseRelationDialog
      ref="licenseUseRelationDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showRelationDialogFlag"
      v-bind:param="{swcatId: listGrid.currentData.swcatId, purchaseunit: listGrid.currentData.purchaseunit}"
      v-on:close="closeRelationDialog"
    />
    <!-- Class Dialog -->
    <Class
      ref="classDialog"
      v-bind:showDialogFlagProp="showClassDialogFlag"
      v-on:confirm="confirmClass"
      v-on:close="closeClassDialog"
      v-bind:param="{object: 'LICENSE', expand: {data: {classstructureId: 'SOFTWARE'}}}"
    />
  </div>
</template>
<script>
import LicenseUseServerDialog from './LicenseUseServerDialog'
import LicenseUseRelationDialog from './LicenseUseRelationDialog'
import Class from '@/views/common/Class'
export default {
  name: 'LICENSEUSE',
  components: {
    'LicenseUseServerDialog': LicenseUseServerDialog,
    'LicenseUseRelationDialog': LicenseUseRelationDialog,
    'Class': Class
  },
  data () {
    return {
      labels: {
        classstructure: '',
        manager: ''
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: true,
          'span-method': this.makeSpan,
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
        }],
        filteredData: [],
        titles: [{
          prop: 'classstructureNm',
          width: '250px'
        }, {
          prop: 'publishername',
          width: '170px'
        }, {
          prop: 'swcatname',
          width: '300px'
        }, {
          prop: 'swcattype',
          width: '110px'
        }, {
          prop: 'swcatversion',
          width: '100px'
        }, {
          prop: 'purchaseunit',
          width: '150px'
        }, {
          prop: 'purchasenum',
          width: '110px'
        }, {
          prop: 'usenum',
          width: '110px'
        }, {
          prop: 'remainnum',
          width: '110px'
        }, {
          prop: 'usesever',
          width: '110px'
        }, {
          prop: 'relationlicense',
          width: '140px'
        }, {
          prop: 'swcatId',
          width: '140px'
        }],
        columns: [],
        columnNames: [],
        currentData: {},
        ciExtNmData: {},
        total: 0,
        srchCnd: null,
        searchParamDetail: {
          swcatIdD: '',
          classstructureD: '',
          classstructureNm: '',
          swcatnameD: '',
          publishernameD: '',
          swcattypeD: ''
        }
      },
      // Auth
      auth: {
        showBtnSaveFlag: false,
        showBtnDelFlag: false
      },
      // Combo Data
      comboData: {},
      // Global Var
      orgId: '',
      activeTab: 'list',
      detailType: 'INSERT',
      searchType: 'NORMAL',
      searchParam: '',
      // Valid Msg
      validMsg: {},
      // Global Flag
      showUseServerDialogFlag: false,
      showRelationDialogFlag: false,
      visibleSearchDetailFlag: false,
      showClassDialogFlag: false,
      listGridFlag: false
    }
  },
  watch: {
    activeTab: function (val) {
      if (val === 'list') {
        this.getListData(this.listGrid.srchCnd)
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
      for (let i = 0; i < authorities.length; i++) {
        if (authorities[i].authorityId === 'LICENSE_SAVE') {
          this.auth.showBtnSaveFlag = true
        }
        if (authorities[i].authorityId === 'LICENSE_DELETE') {
          this.auth.showBtnDelFlag = true
        }
      }
      this.listGridFlag = true
      this.orgId = this.$store.getters.getUser.orgId
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    makeSpan: function (row) {
      if (row.columnIndex === 0 || row.columnIndex === 1 || row.columnIndex === 2) {
        let curRow = this.listGrid.data[row.rowIndex]
        let preRow = this.listGrid.data[row.rowIndex - 1]
        let rowspan = 1
        if (preRow !== undefined) {
          if (curRow.publishername === preRow.publishername && curRow.swcatname === preRow.swcatname) {
            return {rowspan: 0, colspan: 0}
          }
        }
        for (let i = row.rowIndex; i < this.listGrid.data.length; i++) {
          let data = this.listGrid.data[i]
          if (curRow.publishername === data.publishername && curRow.swcatname === data.swcatname) {
            rowspan++
          } else {
            break
          }
        }
        return {rowspan: rowspan - 1, colspan: 1}
      }
    },
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {},
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = null
      let sort = 'ascending'
      let searchParam = ''
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
        lang: this.lang,
        searchParam: searchParam,
        searchType: this.searchType,
        swcatIdD: this.listGrid.searchParamDetail.swcatIdD,
        classstructureD: this.listGrid.searchParamDetail.classstructureD,
        swcatnameD: this.listGrid.searchParamDetail.swcatnameD,
        publishernameD: this.listGrid.searchParamDetail.publishernameD,
        swcattypeD: this.listGrid.searchParamDetail.swcattypeD}
      }
      this.$http.get(
        '/episode/api/asset/licensemgmnt/licenseuse',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.searchType = 'NORMAL'
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
      this.listGrid.searchParamDetail.swcatIdD = ''
      this.listGrid.searchParamDetail.classstructureD = ''
      this.listGrid.searchParamDetail.swcatnameD = ''
      this.listGrid.searchParamDetail.publishernameD = ''
      this.listGrid.searchParamDetail.swcattypeD = ''
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
      this.listGrid.searchParamDetail.classstructureD = rslt.classstructureId
      this.listGrid.searchParamDetail.classstructureNm = rslt.classstructureNm
    },
    closeClassDialog: function () {
      this.visibleSearchDetailFlag = true
      this.showClassDialogFlag = false
    },
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
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
        searchType: this.searchType,
        swcatIdD: this.listGrid.searchParamDetail.swcatIdD,
        classstructureD: this.listGrid.searchParamDetail.classstructureD,
        swcatnameD: this.listGrid.searchParamDetail.swcatnameD,
        publishernameD: this.listGrid.searchParamDetail.publishernameD,
        swcattypeD: this.listGrid.searchParamDetail.swcattypeD}
      }
      this.$http.get(
        '/episode/api/asset/licensemgmnt/licenseuse',
        params
      ).then(res => {
        let fileName = 'licenseuse'
        let fields = [
          {value: 'classstructureNm', label: this.labels.classstructure},
          {value: 'publishername', label: this.labels.publisher},
          {value: 'swcatname', label: this.labels.name},
          {value: 'swcattype', label: this.labels.licensetype},
          {value: 'swcatversion', label: this.labels.version},
          {value: 'purchaseunit', label: this.labels.purchaseunit},
          {value: 'purchasenum', label: this.labels.purchasenum},
          {value: 'usenum', label: this.labels.usenum},
          {value: 'remainnum', label: this.labels.remainnum},
          {value: 'usesever', label: this.labels.usesever},
          {value: 'relationlicense', label: this.labels.relationlicense},
          {value: 'swcatId', label: this.labels.swcatId}
        ]
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    openUseServerDialog: function (row) {
      this.listGrid.currentData = row
      this.showUseServerDialogFlag = true
    },
    closeUseServerDialog: function () {
      this.showUseServerDialogFlag = false
    },
    openRelationDialog: function (row) {
      this.listGrid.currentData = row
      this.showRelationDialogFlag = true
    },
    closeRelationDialog: function () {
      this.showRelationDialogFlag = false
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.labels = {}
    this.auth = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
