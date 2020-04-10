<!--
 Class Name  : E2E.vue
 Description : E2E 현황 > 메인화면
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.23  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="4">
          <el-form label-position='top' label-width='100px'>
            <el-form-item :label="labels.date">
              <div class="search-component">
              <el-date-picker class="form-control mt-1" v-model="seleteDate" type="datetimerange" :clearable='false' style="width: 100%;"> </el-date-picker>
              </div>
            </el-form-item>
          </el-form>
       </b-col>
       <b-col sm="3">
         <el-form label-position='top' label-width='100px'>
           <el-form-item :label="labels.system">
             <div class='search-component'>
             <input class="form-control mt-1" readonly v-model="selectedBizName" style="background-color: white"/>
               <a class="search-button cursor-pointer" style="top: 7px" v-on:click="openRpaDialog">search</a>
             </div>
          </el-form-item>
        </el-form>
      </b-col>
      <b-col sm="3">
        <el-form label-position='top' label-width='100px'>
          <el-form-item :label="labels.location">
            <div class="search-component">
              <div class ="eselect">
              <el-select v-model='rpaList' multiple='multiple' collapse-tags placeholder='SELECT' style="width: 100%;">
                <el-option v-for="rpa in rpaSelect" :label="rpa.value" :value="rpa.key" :key="rpa.key"></el-option>
              </el-select>
              </div>
            </div>
          </el-form-item>
        </el-form>
      </b-col>
      <b-col sm="2">
        <div style='margin-top: 40px;'>
          <b-button  style='backgroundColor: white; color: #606266; font-weight: bolder;' v-on:click='setFilters(true)' class='px-3 sm'>{{this.labels.search}}</b-button>
        </div>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="12">
        <p class="chart-line"></p>
        <E2EChart
          v-bind:ciId="selectedBiz"
          v-bind:rpa="rpaList"
          v-bind:seleteDate="seleteDate"
          v-bind:labels="labels"
          ref="e2echart"
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
            <data-tables-server v-if="listGridFlag" ref="grid" :current-page.sync='listGrid.currentPage' :table-props='listGrid.tableProps' v-on:query-change="getRpaList"  @filtered-data="handleFilteredData" :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" :page-size="listGrid.pageProps.pageSize" :pagination-props="{pageSizes: [5, 10, 15  ] }" >
              <el-table-column :prop='listGrid.titles[0].prop' :label='labels.linebizstartDt' sortable='custom' :width='listGrid.titles[0].width'>
                <template slot-scope="scope">{{dateTime(scope.row.robotDt)}}</template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.ciId'               sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.ciname'             sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.robotip'            sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.namekor'            sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.robotresult'        sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[6].prop' :label='labels.linebizstep'        sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[7].prop' :label='labels.linebizresult'      sortable='custom' :width='listGrid.titles[7].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[8].prop' :label='labels.linebizstartDt'     sortable='custom' :width='listGrid.titles[8].width'>
                <template slot-scope="scope">{{dateTime(scope.row.linebizstartDt)}}</template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[9].prop' :label='labels.linebizendDt'       sortable='custom' :width='listGrid.titles[9].width'>
                <template slot-scope="scope">{{dateTime(scope.row.linebizendDt)}}</template>
              </el-table-column>
            </data-tables-server>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    </b-card>
    <!-- Rpa Dialog -->
    <BizSystem
      ref="bizDialog"
      v-bind:showDialogFlagProp="showRpaDialogFlag"
      v-on:confirm="confirmRpa"
      v-on:close="closeRpaDialog"
    />
  </div>
</template>

<script>
import BizSystem from '@/views/common/BizSystem'
import E2EChart from '@/views/monitor/e2e/E2EChart'

export default {
  name: 'E2E',
  components: {
    'BizSystem': BizSystem,
    'E2EChart': E2EChart
  },
  watch: {
    '$route': function () {
      this.paramInit()
    }
  },
  data: function () {
    return {
      labels: {
        title: '',
        date: '',
        system: '',
        location: '',
        ciId: '',
        ciname: '',
        namekor: '',
        linebizresptime: '',
        robotresult: '',
        linebizstartDt: '',
        linebizendDt: '',
        name: '',
        type: '',
        description: '',
        confirm: '',
        close: '',
        search: '',
        noDataMessage: '',
        noLocationMessage: '',
        noSystemMessage: '',
        noDateMessage: '',
        linebizstep: '',
        robotDt: '',
        robotip: '',
        linebizresult: '',
        rpaRes: '',
        excelDownload: '',
        selectNullMsg: ''
      },
      seleteDate: [new Date(new Date().setHours(new Date().getHours() - 5)), new Date()],
      selectedBiz: '',
      selectedBizName: '',
      rpaSelect: [],
      rpaList: [],
      searchParam: '',
      e2eChartFlag: false,
      selectionFlag: true,
      showRpaDialogFlag: false,
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
          prop: ['robotDt', 'ciId', 'ciname', 'robotip', 'namekor', 'robotresult', 'linebizstep', 'linebizresult', 'linebizstartDt', 'linebizendDt'],
          value: ''
        }, {
          prop: ['selectedBiz'],
          value: ''
        }, {
          prop: ['rpaList'],
          value: []
        }, {
          prop: ['seleteDate'],
          value: ''
        }, {
          prop: ['status'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'robotDt',
          width: '180px'
        }, {
          prop: 'ciId',
          width: '150px'
        }, {
          prop: 'ciname',
          width: '150px'
        }, {
          prop: 'robotip',
          width: '150px'
        }, {
          prop: 'namekor',
          width: '100px'
        }, {
          prop: 'robotresult',
          width: '150px'
        }, {
          prop: 'linebizstep',
          width: '150px'
        }, {
          prop: 'linebizresult',
          width: '150px'
        }, {
          prop: 'linebizstartDt',
          width: '180px'
        }, {
          prop: 'linebizendDt',
          width: '180px'
        }],
        columns: ['robotDt', 'ciId', 'ciname', 'robotip', 'namekor', 'robotresult', 'linebizstep', 'linebizresult', 'linebizstartDt', 'linebizendDt'],
        columnNames: ['robotDt', 'ciId', 'ciname', 'robotip', 'namekor', 'robotresult', 'linebizstep', 'linebizresult', 'linebizstartDt', 'linebizendDt'],
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
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.getRpa()
      this.selectedBiz = ''
      this.selectedBizName = ''
      this.rpaList = []
      this.listGridFlag = true
    },
    getRpa: function () {
      this.$http.get(
        '/episode/api/e2e/rpa'
      ).then(res => {
        res.data.forEach(row => {
          let obj = {}
          obj.key = row.ciId
          obj.value = row.namekor
          this.rpaSelect.push(obj)
        })
      }).catch(error => {
        console.log(error)
      })
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.searchParam
    },
    setFilters: function (type) {
      if (this.$util.isNull(this.seleteDate)) {
        this.$util.esmessage(this, 'warn', this.labels.noDateMessage)
        return
      }
      if (this.selectedBiz === '') {
        this.$util.esmessage(this, 'warn', this.labels.noSystemMessage)
        return null
      }
      if (this.rpaList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noLocationMessage)
        return null
      }
      this.listGrid.currentPage = 1
      this.listGrid.filters[1].value = this.selectedBiz
      this.listGrid.filters[2].value = this.rpaList
      this.listGrid.filters[3].value = this.seleteDate
      if (type) this.$refs.e2echart.initialize()
      this.e2eChartFlag = true
    },
    getRpaList: function (srchCnd) {
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      let page = 1
      let pageSize = 10
      let orderBy = 'robotDt'
      let sort = 'descending'
      let searchParam = ''
      let selectedBiz = ''
      let rpaList = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value.trim()
        selectedBiz = srchCnd.filters[1].value
        rpaList = srchCnd.filters[2].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        startDate: startDate,
        endDate: endDate,
        ciId: selectedBiz,
        rpa: rpaList.join(',')}
      }
      this.$http.get(
        '/episode/api/e2e',
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
    openRpaDialog: function () {
      // this.selectedBiz = ''
      // this.selectedBizName = ''
      this.showRpaDialogFlag = true
    },
    confirmRpa: function (rslt) {
      if (rslt.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
        return
      }
      this.showRpaDialogFlag = false
      this.selectedBiz = rslt.ciId
      this.selectedBizName = rslt.ciname
    },
    closeRpaDialog: function () {
      if (this.$util.isNull(this.selectedBiz)) {
        this.rpaList = []
      }
      this.$refs.bizDialog.destroy()
      this.showRpaDialogFlag = false
    },
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      let selectedBiz = this.listGrid.filters[1].value
      let rpaList = this.listGrid.filters[2].value
      let orderBy = 'robotDt'
      let sort = 'descending'
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        startDate: startDate,
        endDate: endDate,
        ciId: selectedBiz,
        rpa: rpaList.join(',')}
      }
      this.$http.get(
        '/episode/api/e2e?searchParam',
        params
      ).then(res => {
        let fileName = 'e2e'
        let fields = [
          {value: 'robotDt', label: this.labels.robotDt},
          {value: 'ciId', label: this.labels.ciId},
          {value: 'ciname', label: this.labels.ciname},
          {value: 'robotip', label: this.labels.robotip},
          {value: 'namekor', label: this.labels.namekor},
          {value: 'robotresult', label: this.labels.robotresult},
          {value: 'linebizstep', label: this.labels.linebizstep},
          {value: 'linebizresult', label: this.labels.linebizresult},
          {value: 'linebizstartDt', label: this.labels.linebizstartDt},
          {value: 'linebizendDt', label: this.labels.linebizendDt}
        ]
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
    },
    paramInit: function () {
      if (!this.$util.isNull(this.$route.params.rpaData)) {
        let sDateValue = null
        let eDateValue = null
        this.selectedBiz = this.$route.params.rpaData.biz
        this.selectedBizName = this.$route.params.rpaData.ciname
        this.rpaList.push(this.$route.params.rpaData.rpaCi
        )
        let sTime = this.$route.params.rpaData.time
        let eTime = this.$route.params.rpaData.time
        const sDateType = sTime.substring(0, sTime.indexOf('.'))
        sDateValue = new Date(sDateType)
        const eDateType = eTime.substring(0, eTime.indexOf('.'))
        eDateValue = new Date(eDateType)
        this.seleteDate[0] = new Date(sDateValue.setHours(sDateValue.getHours() - 23))
        this.seleteDate[1] = new Date(eDateValue.setHours(eDateValue.getHours() + 1))
        this.setFilters(false)
      } else if (!this.$util.isNull(this.$route.params.bizId)) {
        let sTime = new Date()
        let eTime = new Date()
        let sDateType = new Date(sTime.getFullYear(), sTime.getMonth(), sTime.getDate())
        let sDateValue = new Date(sDateType.setDate(sDateType.getDate() - 1))
        let eDateValue = new Date(eTime.getFullYear(), eTime.getMonth(), eTime.getDate())
        this.selectedBiz = this.$route.params.bizId
        this.selectedBizName = this.$route.params.bizname
        let rpaCiList = this.$route.params.rpaCiList
        rpaCiList.forEach(r => {
          this.rpaList.push(r.ciId)
        })
        this.seleteDate[0] = new Date(sDateValue)
        this.seleteDate[1] = new Date(eDateValue)
        this.setFilters(false)
      }
    }
  },
  created: function () {
    this.initialize()
    this.paramInit()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.seleteDate = []
    this.rpaSelect = []
    this.rpaList = []
    this.labels = {}
    this.$refs.e2echart.e2eData = {}
    this.$refs.e2echart.e2eChartFlag = false
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
