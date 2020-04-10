<!--
 Class Name  : CIswList.vue
 Description : 소프트웨어 목록
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.29  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <b-row>
            <div class='col-md-12'>
              <div style="height:40px;">
              </div>
            </div>
          </b-row>
          <p class="grid-line"></p>
          <b-row>
            <b-col sm="6">
              <div class="search-component">
                <input class="form-control mt-3" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
              </div>
            </b-col>
            <div>
              <el-popover placement="right" width="500" trigger="click" v-model="visibleSearchDetailFlag">
                <b-row style="padding-left:15px; padding-bottom:10px; font-size:16px; font-weight:bold;">
                  {{labels.searchDetail}}
                </b-row>
                <b-row>
                  <div class='form-group col-md-6'>
                    <label for="swClass">{{labels.swClass}}</label>
                    <input id="swClass" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swClass">
                  </div>
                  <div class='form-group col-md-6'>
                    <label for="publishername">{{labels.publisher}}</label>
                    <input id="publishername" class="formdata sm float-right" v-model="listGrid.searchParamDetail.publishername"/>
                  </div>
                </b-row>
                <b-row>
                  <div class='form-group col-md-6'>
                    <label for="swcatname">{{labels.name}}</label>
                    <input id="swcatname" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swcatname"/>
                  </div>
                  <div class='form-group col-md-6'>
                    <label for="swcatversion">{{labels.version}}</label>
                    <input id="swcatversion" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swcatversion"/>
                  </div>
                  <div class='form-group col-md-6'>
                    <label for="ciName">{{labels.ciId}}</label>
                    <input id="ciName" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ciName"/>
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
                <el-button slot="reference" :class='searchDetailBtnClass' style="margin-top:16px !important"></el-button>
              </el-popover>
            </div>
            <b-col>
              <div class="float-right mb-1" role="group">
                <el-button v-on:click="exportFilter" :title="labels.exportFilter" style="margin-top:10px; padding: 0 0 0 0; width:40px; height: 40px;">
                  <img src="../../../../assets/img/icons/icon_search_download.png" width="60%" />
                </el-button>
              </div>
            </b-col>
          </b-row>
          <b-row>
            <div class='col-md-12'>
              <p class="grid-line"></p>
              <data-tables-server v-if="listGridFlag" ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" @query-change="getListData" :page-size='listGrid.pageProps.pageSize' :pagination-props="{pageSizes: [5, 10, 15] }" :current-page.sync='listGrid.currentPage' >
                <el-table-column :prop='listGrid.titles[0].prop' :label='labels.swClass'          sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[1].prop' :label='labels.publisher'        sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[2].prop' :label='labels.name'             sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[3].prop' :label='labels.version'          sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[4].prop' :label='labels.ciClass'          sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[5].prop' :label='labels.ciId'             sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[6].prop' :label='labels.osName'           sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[7].prop' :label='labels.startDt'          sortable='custom' :width='listGrid.titles[7].width'>
                  <template slot-scope="scope">
                      {{$util.dateTime(scope.row.startDt)}}
                    </template>
                </el-table-column>
              </data-tables-server>
            </div>
          </b-row>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
export default {
  name: 'CISW',
  data () {
    return {
      labels: {
        ciClass: ''
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        currentPage: 1,
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 20, 30]
        },
        data: [],
        filters: [
          { prop: ['searchParam'], value: '' }
        ],
        filteredData: [],
        titles: [
          { prop: 'swClass', label: 'swClass', width: '160px' },
          { prop: 'publishername', label: 'publisher', width: '200px' },
          { prop: 'swcatname', label: 'name', width: '270px' },
          { prop: 'swcatversion', label: 'version', width: '160px' },
          { prop: 'ciClass', label: 'ciClass', width: '160px' },
          { prop: 'ciName', label: 'ciId', width: '200px' },
          { prop: 'osName', label: 'osName', width: '350px' },
          { prop: 'startDt', label: 'startDt', width: '200px' }
        ],
        columns: ['ciClass', 'ciName', 'osName', 'swClass', 'swcatname', 'publishername', 'swcatversion', 'locationId', 'startDt'],
        columnNames: ['1', '1', '1', '1', '1', '1', '1', '1', '1'],
        searchParamDetail: {
          swClass: '',
          publishername: '',
          swcatname: '',
          swcatversion: '',
          ciName: ''
        }
      },
      total: 0,
      searchParam: '',
      srchCnd: null,
      listGridFlag: false,
      visibleSearchDetailFlag: false
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
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels, this.listGrid.titles)
      this.listGridFlag = true
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 5
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      if (srchCnd != null && srchCnd !== undefined && srchCnd !== 'undefined') {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        searchParam = srchCnd.filters[0].value
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        swClass: this.listGrid.searchParamDetail.swClass,
        publishername: this.listGrid.searchParamDetail.publishername,
        swcatname: this.listGrid.searchParamDetail.swcatname,
        swcatversion: this.listGrid.searchParamDetail.swcatversion,
        ciName: this.listGrid.searchParamDetail.ciName}
      }
      this.$http.get(
        '/episode/api/asset/resource/ciswlist',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
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
      this.listGrid.searchParamDetail.swClass = ''
      this.listGrid.searchParamDetail.publishername = ''
      this.listGrid.searchParamDetail.swcatname = ''
      this.listGrid.searchParamDetail.swcatversion = ''
      this.listGrid.searchParamDetail.ciName = ''
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
    },
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
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
        searchParam: searchParam,
        swClass: this.listGrid.searchParamDetail.swClass,
        publishername: this.listGrid.searchParamDetail.publishername,
        swcatname: this.listGrid.searchParamDetail.swcatname,
        swcatversion: this.listGrid.searchParamDetail.swcatversion,
        ciName: this.listGrid.searchParamDetail.ciName}
      }
      this.$http.get(
        '/episode/api/asset/resource/ciswlist',
        params
      ).then(res => {
        let fileName = 'ciswlist'
        let fields = [
          {value: 'swClass', label: this.labels.swClass},
          {value: 'publishername', label: this.labels.publisher},
          {value: 'swcatname', label: this.labels.name},
          {value: 'swcatversion', label: this.labels.version},
          {value: 'ciClass', label: this.labels.ciClass},
          {value: 'ciName', label: this.labels.ciId},
          {value: 'osName', label: this.labels.osName},
          {value: 'startDt', label: this.labels.startDt}
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
