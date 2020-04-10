<!--
 Class Name  : SeviceApp.vue
 Description : 서비스  > 어플리케이션 장애 다운 시간
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.25  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="4">
          <el-form label-position='top' label-width='100px'>
            <el-form-item :label="labels.date">
              <div class="search-component">
              <el-date-picker class="form-control mt-1" v-model="seleteDate" type="daterange" v-on:blur="blurData" :clearable='false' style="width: 100%;"> </el-date-picker>
              </div>
            </el-form-item>
          </el-form>
       </b-col>
       <b-col sm="3">
         <el-form label-position='top' label-width='100px'>
           <el-form-item :label="labels.system">
             <div class='search-component'>
             <input class="form-control mt-1" readonly style="background-color: white"/>
             <div class="el-select__tags" style="margin-left: 7px;">
                <span>
                    <span v-show='selectedAppName.length > 0' class="el-tag--info" style="padding: 0px 7px 0px 7px">{{selectedAppName[0]}}</span>
                    <span v-show='selectedAppName.length > 1' class="el-tag--info" style="padding: 0px 7px 0px 7px" > {{`+${selectedAppName.length - 1}`}} </span>
                </span>
               </div>
               <a class="search-button cursor-pointer" style="top: 7px" v-on:click="openAppDialog">search</a>
             </div>
          </el-form-item>
        </el-form>
      </b-col>
      <b-col sm="2">
        <div style='margin-top: 40px;'>
          <b-button style='backgroundColor: white; color: #606266; font-weight: bolder;' v-on:click='setFilters()' class='px-3 sm'>{{this.labels.search}}</b-button>
        </div>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="12">
        <p class="chart-line"></p>
        <ServiceAppChart
          v-bind:app="selectedApp"
          v-bind:seleteDate="seleteDate"
          v-bind:labels="labels"
          ref="appchart"
         />
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="12">
        <p class="chart-line"></p>
        <b-row>
          <b-col sm="6">
            <div class="search-component">
              <input class="form-control mt-1" :placeholder='labels.search' v-model="searchParam" v-on:keyup.enter="enterSearch" style="width: 80%;"/>
            </div>
          </b-col>
          <b-col sm="6">
            <div class="float-right mb-1" role="group">
              <el-button :title="labels.excelDownload" v-on:click="exportFilter" style="padding: 0 0 0 0; width:40px; height: 40px;">
                <img src="@/assets/img/icons/icon_search_download.png" width="60%" />
              </el-button>
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col sm="12">
            <p class="grid-line"></p>
            <data-tables-server v-if="listGridFlag" ref="grid" :current-page.sync='listGrid.currentPage' :table-props='listGrid.tableProps' v-on:query-change="getAppList"  @filtered-data="handleFilteredData" :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" :page-size="listGrid.pageProps.pageSize" :pagination-props="{pageSizes: [5, 10, 15  ] }" >
              <el-table-column :prop='listGrid.titles[0].prop' :label='labels.collDt' sortable='custom' :width='listGrid.titles[0].width'>
                <template slot-scope="scope">{{dateTime(scope.row.collDt)}}</template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.ciId'               sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.classstructureId'   sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.ciname'             sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.totalTime'          sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.lossCost'           sortable='custom' :width='listGrid.titles[5].width'>
                <template slot-scope="scope">{{lossCostFormat(scope.row.lossCost)}}</template>
              </el-table-column>
            </data-tables-server>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    </b-card>
    <!-- Rpa Dialog -->
    <AppSystem
      ref="appDialog"
      v-bind:showDialogFlagProp="showAppDialogFlag"
      v-bind:data="appList"
      v-bind:labels="labels"
      v-on:confirm="confirmApp"
      v-on:close="closeAppDialog"
    />
  </div>
</template>

<script>
import AppSystem from '@/views/common/AppSystem'
import ServiceAppChart from '@/views/asset/servicemgmnt/serviceapp/ServiceAppChart'

export default {
  name: 'SERVICEAPP',
  components: {
    'AppSystem': AppSystem,
    'ServiceAppChart': ServiceAppChart
  },
  data: function () {
    return {
      labels: {
        totalTime: '',
        lossCost: '',
        dialogTitle: '',
        date: '',
        ciId: '',
        ciname: '',
        search: '',
        confirm: '',
        close: '',
        orgId: '',
        title: '',
        excelDownload: '',
        noDataMessage: '',
        appMessage: '',
        noDateMessage: '',
        collDt: '',
        classstructureId: '',
        noAppMessage: '',
        selectNullMsg: ''
      },
      seleteDate: [new Date(new Date().setDate(new Date().getDate() - 7)), new Date()],
      appList: [],
      selectedApp: [],
      selectedAppName: [],
      searchParam: '',
      selectionFlag: true,
      showAppDialogFlag: false,
      listGridFlag: false,
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
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        dataBak: [],
        filters: [{
          prop: ['collDt', 'ciId', 'classstructureNm', 'ciname', 'totalTime', 'lossCost', 'classstructureId'],
          value: ''
        }, {
          prop: ['appList'],
          value: []
        }, {
          prop: ['seleteDate'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'collDt',
          width: '220px'
        }, {
          prop: 'ciId',
          width: '200px'
        }, {
          prop: 'classstructureNm',
          width: '250px'
        }, {
          prop: 'ciname',
          width: '200px'
        }, {
          prop: 'totalTime',
          width: '200px'
        }, {
          prop: 'lossCost',
          width: '200px'
        }, {
          prop: 'classstructureId',
          width: '200px'
        }],
        columns: ['collDt', 'ciId', 'classstructureNm', 'ciname', 'totalTime', 'lossCost'],
        columnNames: ['collDt', 'ciId', 'classstructureNm', 'ciname', 'totalTime', 'lossCost'],
        currentData: {},
        total: 0
      }
    }
  },
  props: {
    confirmResult: {
      type: Boolean
    }
  },
  computed: {
    lossCostFormat () {
      return (key) => {
        return '￦ ' + key.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
      }
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.getApp()
      this.selectedApp = []
      this.selectedAppName = []
      this.listGridFlag = true
    },
    blurData: function () {
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      this.$http.get(
        '/episode/api/asset/servicemgmnt/serviceapp/app?startDate=' + startDate + '&endDate=' + endDate
      ).then(res => {
        this.selectedApp = []
        this.selectedAppName = []
        this.appList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getApp: function () {
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      this.$http.get(
        '/episode/api/asset/servicemgmnt/serviceapp/app?startDate=' + startDate + '&endDate=' + endDate
      ).then(res => {
        this.appList = res.data
        res.data.forEach(r => {
          this.selectedApp.push(r.ciId)
          this.selectedAppName.push(r.ciname)
        })
        if (this.selectedApp.length !== 0) {
          this.setFilters()
        }
      }).catch(error => {
        console.log(error)
      })
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.searchParam
    },
    setFilters: function () {
      if (this.$util.isNull(this.seleteDate)) {
        this.$util.esmessage(this, 'warn', this.labels.noDateMessage)
        return
      }
      if (this.selectedApp.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.appMessage)
        return null
      }
      this.listGrid.filters[1].value = this.selectedApp
      this.listGrid.filters[2].value = this.seleteDate
      this.$refs.appchart.initialize()
    },
    getAppList: function (srchCnd) {
      let orgvariable = this.$store.getters.getOrgvariable
      let avgWageRateData = orgvariable.filter(r => r.key === 'AVG_WAGE_RATE')
      let avgWageRate = ''
      if (avgWageRateData.length !== 0) {
        avgWageRate = parseInt(avgWageRateData[0].value)
      }
      // let avgWageRate = parseInt(orgvariable.filter(r => r.key === 'AVG_WAGE_RATE')[0].value)
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      let page = 1
      let pageSize = 10
      let orderBy = 'collDt'
      let sort = 'descending'
      let searchParam = ''
      let selectedApp = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value.trim()
        selectedApp = srchCnd.filters[1].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        startDate: startDate,
        endDate: endDate,
        app: selectedApp.join(','),
        avgWageRate: avgWageRate}
      }
      this.$http.get(
        '/episode/api/asset/servicemgmnt/serviceapp',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    openAppDialog: function () {
      if (this.appList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noAppMessage)
        return false
      }
      // this.selectedApp = []
      // this.selectedAppName = []
      this.showAppDialogFlag = true
    },
    confirmApp: function (rslt) {
      if (rslt.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
        return
      }
      this.selectedApp = []
      this.selectedAppName = []
      this.showAppDialogFlag = false
      rslt.forEach(r => {
        this.selectedApp.push(r.ciId)
        this.selectedAppName.push(r.ciname)
      })
    },
    closeAppDialog: function () {
      this.showAppDialogFlag = false
    },
    exportFilter: function () {
      let orgvariable = this.$store.getters.getOrgvariable
      let avgWageRate = parseInt(orgvariable.filter(r => r.key === 'AVG_WAGE_RATE')[0].value)
      let searchParam = this.listGrid.filters[0].value
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      let selectedApp = this.listGrid.filters[1].value
      let orderBy = 'collDt'
      let sort = 'descending'
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        startDate: startDate,
        endDate: endDate,
        app: selectedApp.join(','),
        avgWageRate: avgWageRate}
      }
      this.$http.get(
        '/episode/api/asset/servicemgmnt/serviceapp',
        params
      ).then(res => {
        let lang = this.$store.getters.getUser.lang
        let fileName = 'serviceApp'
        let fields = [
          {value: 'collDt', label: this.labels.collDt},
          {value: 'ciId', label: this.labels.ciId},
          {value: 'ciname', label: this.labels.ciname},
          {value: 'classstructureId', label: this.labels.classstructureId},
          {value: 'totalTime', label: this.labels.totalTime},
          {value: 'lossCost', label: this.labels.lossCost}
        ]
        if (lang === 'ko-kr') {
          fileName = this.labels.title
        }
        if (res.data.total !== 0) {
          this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
        } else {
          this.$util.esmessage(this, 'warn', this.labels.noDataMessage)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    dateTime: function (date) {
      return this.$util.dateTime(date)
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.seleteDate = []
    this.appList = []
    this.selectedApp = []
    this.selectedAppName = []
    this.labels = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
