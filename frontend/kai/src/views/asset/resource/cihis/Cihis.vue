<!--
 Class Name  : Cihis.vue
 Description : IT자원 변경 이력
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.18  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class="comm-btn-box" role="group" style="height:40px;">
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
        <b-row>
          <b-col sm="6">
            <div class="search-component">
              <input class="form-control mt-2" style="width: 100%; height:33px;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder="labels.search" />
            </div>
          </b-col>
          <div>
            <el-popover placement="right" width="500" trigger="click" v-model="visibleSearchDetailFlag">
              <b-row style="padding-left:15px; padding-bottom:10px; font-size:16px; font-weight:bold;">
                {{labels.searchDetail}}
              </b-row>
              <b-row>
              <!-- <div class='form-group col-md-6'>
                <label for="ciId">{{labels.classstructure}}</label>
                <div class="search-component sm">
                  <input id="classstructureId" v-model="listGrid.searchParamDetail.classstructureNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
                  <a class="search-button cursor-pointer" v-on:click="openClassDialog">search</a>
                </div>
              </div> -->
              <div class='form-group col-md-6'>
                <label for="classstructure">{{labels.ciname}}</label>
                <input id="classstructure" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ciId"/>
              </div>
              <div class='form-group col-md-6'>
                <label for="ciname">{{labels.name}}</label>
                <input id="ciname" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ciname"/>
              </div>
              <div class='form-group col-md-6'>
                <label for="description">{{labels.chgtype}}</label>
                <input id="description" class="formdata sm float-right" v-model="listGrid.searchParamDetail.chgtype"/>
              </div>
              <div class='form-group col-md-6'>
                <label for="itsmciid">{{labels.chgitem}}</label>
                <input id="itsmciid" class="formdata sm float-right" v-model="listGrid.searchParamDetail.chgdetail"/>
              </div>
              <div class='form-group col-md-6'>
                <label for="itsmciid">{{labels.chgdescription}}</label>
                <input id="itsmciid" class="formdata sm float-right" v-model="listGrid.searchParamDetail.chgdescription"/>
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
              <el-button slot="reference" style="margin-top:8px !important" :class='searchDetailBtnClass'></el-button>
            </el-popover>
          </div>
          <b-col>
            <div class="float-right mt-1" role="group">
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
              <el-table-column :prop='listGrid.titles[0].prop' :label='labels.classstructure'  sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.ciname'          sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.name'            sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.chgtype'         sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.chgitem'         sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.chgdescription'  sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[6].prop' :label='labels.updateNm'        sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[7].prop' :label='labels.updateDt'        sortable='custom' :width='listGrid.titles[7].width'>
                <template slot-scope="props">
                  {{$util.dateTimeWithTimeZone(props.row.updateDt)}}
                </template>
              </el-table-column>
            </data-tables-server>
          </b-col>
        </b-row>
    </b-card>
    <!-- Class Dialog -->
    <Class
      ref="classDialog"
      v-bind:showDialogFlagProp="showClassDialogFlag"
      v-on:confirm="confirmClass"
      v-on:close="closeClassDialog"
      v-bind:param="{object: 'CI', expand: null}"
    />
  </div>
</template>
<script>
import Class from '@/views/common/Class'
export default {
  name: 'CIHIS',
  components: {
    'Class': Class
  },
  data () {
    return {
      labels: {
        id: '', updateNm: ''
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
          pageSizes: [10, 15, 20]
        },
        data: [],
        filters: [{
          prop: ['chgtype', 'chgitem', 'chgdescription'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'classstructureNm',
          width: '250px'
        }, {
          prop: 'ciId',
          width: '110px'
        }, {
          prop: 'ciname',
          width: '300px'
        }, {
          prop: 'chgtype',
          width: '110px'
        }, {
          prop: 'chgdetail',
          width: '400px'
        }, {
          prop: 'chgdescription',
          width: '665px'
        }, {
          prop: 'updateNm',
          width: '100px'
        }, {
          prop: 'updateDt',
          width: '200px'
        }],
        currentData: {},
        total: 0,
        searchParamDetail: {
          classstructure: '',
          classstructureNm: '',
          ciId: '',
          ciname: '',
          chgtype: '',
          chgdetail: '',
          chgdescription: ''
        }
      },
      // Auth
      auth: {
        showBtnSaveFlag: false,
        showBtnDelFlag: false
      },
      // Global Var
      orgId: '',
      searchParam: '',
      visibleSearchDetailFlag: false,
      showClassDialogFlag: false,
      listGridFlag: false
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
      this.orgId = this.$store.getters.getUser.orgId
      this.listGridFlag = true
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {},
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'update_dt'
      let sort = 'descending'
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
        searchParam: searchParam,
        classstructure: this.listGrid.searchParamDetail.classstructure,
        ciId: this.listGrid.searchParamDetail.ciId,
        ciname: this.listGrid.searchParamDetail.ciname,
        chgtype: this.listGrid.searchParamDetail.chgtype,
        chgdetail: this.listGrid.searchParamDetail.chgdetail,
        chgdescription: this.listGrid.searchParamDetail.chgdescription}
      }
      this.$http.get(
        '/episode/api/asset/resource/cihis',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    filterFocus: function () {
      this.listGrid.filters[0].value = ''
    },
    enterSearch: function () {
      this.searchType = 'NORMAL'
      this.listGrid.filters[0].value = this.searchParam
      this.listGrid.currentPage = 1
    },
    searchDetail: function () {
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
      this.listGrid.currentPage = 1
    },
    searchDetailReset: function () {
      this.listGrid.searchParamDetail.ciId = ''
      this.listGrid.searchParamDetail.classstructure = ''
      this.listGrid.searchParamDetail.classstructureNm = ''
      this.listGrid.searchParamDetail.ciname = ''
      this.listGrid.searchParamDetail.chgtype = ''
      this.listGrid.searchParamDetail.chgdetail = ''
      this.listGrid.searchParamDetail.chgdescription = ''
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
      this.listGrid.currentPage = 1
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
      let srchCnd = this.listGrid.srchCnd
      let orderBy = 'update_dt'
      let sort = 'descending'
      if (srchCnd != null && srchCnd !== undefined && srchCnd !== 'undefined') {
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
      }
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        classstructure: this.listGrid.searchParamDetail.classstructure,
        ciId: this.listGrid.searchParamDetail.ciId,
        ciname: this.listGrid.searchParamDetail.ciname,
        chgtype: this.listGrid.searchParamDetail.chgtype,
        chgdetail: this.listGrid.searchParamDetail.chgdetail,
        chgdescription: this.listGrid.searchParamDetail.chgdescription}
      }
      this.$http.get(
        '/episode/api/asset/resource/cihis',
        params
      ).then(res => {
        let fileName = 'cihis'
        let fields = [
          {value: 'classstructureNm', label: this.labels.classstructure},
          {value: 'ciId', label: this.labels.ciname},
          {value: 'ciname', label: this.labels.name},
          {value: 'updateNm', label: this.labels.updateNm},
          {value: 'updateDt', label: this.labels.updateDt},
          {value: 'chgtype', label: this.labels.chgtype},
          {value: 'chgdetail', label: this.labels.chgitem},
          {value: 'chgdescription', label: this.labels.chgdescription}
        ]
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.labels = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
