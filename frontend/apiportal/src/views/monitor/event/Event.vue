<!--
 Class Name  : Event.vue
 Description : Eventhis 현황
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.23  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="4">
          <el-form label-position='top' label-width='100px'>
          <el-form-item :label="labels.date">
           <div class="search-component">
             <el-date-picker class="form-control mt-1" v-model="seleteDate" type="daterange"  v-on:blur="dateSearch" style="width: 100%;" :clearable='false'>
             </el-date-picker>
           </div>
          </el-form-item>
          </el-form>
        </b-col>
        <b-col sm="3">
          <el-form label-position='top' label-width='100px'>
          <el-form-item :label="labels.severity">
            <div class="search-component">
            <div class ="eselect">
              <el-select v-model='listGrid.filters[1].value' multiple='multiple' collapse-tags placeholder='SELECT' style="width: 100%;">
                <el-option :style='severity.value'  v-for="severity in severitySelect" :value="severity.key" :key="severity.key">
                  <b-badge :style='badgeStyle(severity.key)' class="m-1 badge-es" pill >{{severity.key}}</b-badge>
                </el-option>
              </el-select>
            </div>
            </div>
          </el-form-item>
          </el-form>
        </b-col>
        <b-col sm="2">
          <el-form label-position='top' label-width='100px'>
          <el-form-item :label="labels.status">
            <div class="search-component">
            <div class ="eselect">
              <el-select   v-model='listGrid.filters[4].value' style="width: 100%;">
                <el-option v-for="status in statusSelect" :label='status.value' :value="status.key" :key="status.key"></el-option>
              </el-select>
            </div>
            </div>
          </el-form-item>
          </el-form>
        </b-col>
        <b-col sm="3">
          <el-form label-position='top' label-width='100px'>
          <el-form-item :label="labels.system">
            <div class="search-component">
            <div class ="eselect">
              <el-select v-model='listGrid.filters[2].value' multiple='multiple' collapse-tags placeholder='SELECT' style="width: 100%;">
                <el-option v-for="system in systemSelect" :value="system.key" :key="system.key"></el-option>
              </el-select>
            </div>
            </div>
          </el-form-item>
          </el-form>
        </b-col>
       </b-row>
       <p class="chart-line"></p>
       <b-row>
         <div class='col-md-4' style="padding-top:10px;padding-right:5px">
           <div class="search-component sm">
             <input class="form-control formdata sm" style="width: 100%;" :placeholder="labels.search" v-model="searchParam" v-on:keyup.enter="enterSearch"  />
           </div>
         </div>
         <div>
           <el-popover placement="right" width="500" trigger="click" v-model="visibleSearchDetailFlag">
             <b-row style="padding-left:15px; padding-bottom:10px; font-size:16px; font-weight:bold;">
               {{labels.searchDetail}}
             </b-row>
             <b-row>
               <div class='form-group col-md-6'>
                 <label for="ciId">{{labels.ciId}}</label>
                 <input id="ciId" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ciId"/>
               </div>
               <div class='form-group col-md-6'>
                 <label for="ciname">{{labels.ciname}}</label>
                 <input id="ciname" class="formdata sm float-right" v-model="listGrid.searchParamDetail.ciname"/>
               </div>
            </b-row>
             <b-row>
               <div class='form-group col-md-6'>
                 <label for="application_url">{{labels.ipAddress}}</label>
                 <input id="application_url" class="formdata sm float-right" v-model="listGrid.searchParamDetail.extaln17"/>
               </div>
               <div class='form-group col-md-6'>
                 <label for="message">{{labels.message}}</label>
                 <input id="message" class="formdata sm float-right" v-model="listGrid.searchParamDetail.message"/>
               </div>
             </b-row>
             <b-row>
               <div class="col mt-2">
                 <div class='d-flex justify-content-end'>
                   <b-button variant="primary" class="px-3 sm" v-on:click="searchDetailReset" >{{labels.reset}}</b-button>
                   <b-button variant="primary" class="px-3 sm" v-on:click="searchDetail">{{labels.search}}</b-button>
                 </div>
               </div>
             </b-row>
             <el-button v-on:click="spInit" slot="reference" :class='searchDetailBtvClass'></el-button>
           </el-popover>
         </div>
         <div class='col'>
           <div class="float-right mt-2" role="group">
             <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="float:right;padding: 0; width:40px; height: 40px;">
               <img src="@/assets/img/icons/icon_search_download.png" width="60%" />
             </el-button>
           </div>
         </div>
       </b-row>
      <b-row>
        <b-col sm="12">
          <p class="grid-line"></p>
          <data-tables-server v-if="listGridFlag" ref="grid" :current-page.sync='listGrid.currentPage' :table-props='listGrid.tableProps' v-on:query-change="getEventhisList"  @filtered-data="handleFilteredData" :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" :page-size="listGrid.pageProps.pageSize" :pagination-props="{pageSizes: [10, 20, 30  ] }" >
            <el-table-column :prop='listGrid.titles[0].prop' :label='labels.originDt'          sortable='custom' :width='listGrid.titles[0].width'>
              <template slot-scope="scope">{{dateTime(scope.row.originDt)}}</template>
            </el-table-column>
            <el-table-column :prop='listGrid.titles[1].prop' :label='labels.severity'          sortable='custom' :width='listGrid.titles[1].width'>
              <template slot-scope="scope"><b-badge :style='badgeStyle(scope.row.severity)' class="m-1 badge-es" pill >{{scope.row.severity}}</b-badge></template>
            </el-table-column>
            <el-table-column :prop='listGrid.titles[2].prop' :label='labels.evtresource'       sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[3].prop' :label='labels.classstructureId'  sortable='custom'></el-table-column>
            <el-table-column :prop='listGrid.titles[4].prop' :label='labels.ciname'            sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[5].prop' :label='labels.ipAddress'         sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[6].prop' :label='labels.message'           sortable='custom' :width='listGrid.titles[6].width'>
              <template  slot-scope="props">
                <a class="cursor-pointer link mr-3 message" v-on:click="openMessageDialog(props.row)">{{props.row.message}}</a>
              </template>
            </el-table-column>
            <el-table-column align="right" width="80">
              <template  slot-scope="props">
                <el-popover placement="left" trigger="click">
                  <el-button size="mini" type="text" v-on:click="topologyOpen(props.row)">{{labels.topologyOpen}}</el-button>
                  <br>
                  <el-button size="mini" type="text" v-on:click="moveMonitor(props.row)">{{labels.moveMonitor}}</el-button>
                  <br>
                  <el-button size="mini" type="text" v-on:click="moveCi(props.row)">{{labels.moveCi}}</el-button>
                <el-button slot="reference" :title="labels.move" style="padding: 0; width:30px; height: 30px;">
                  <img src="@/assets/img/icons/icon_search_login.png" width="60%" />
                </el-button>
                </el-popover>
              </template>
            </el-table-column>
          </data-tables-server>
        </b-col>
      </b-row>

    </b-card>
  </div>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'
export default {
  name: 'EVENT',

  data () {
    return {
      seleteDate: [new Date(new Date().setDate(new Date().getDate() - 3)), new Date()],
      searchParam: '',
      severitySelect: [],
      systemSelect: [],
      statusSelect: [],
      severityColor: {},
      labels: {
        ciId: '',
        ciname: '',
        ipAddress: '',
        deviceName: '',
        deviceIp: '',
        message: '',
        severity: '',
        originDt: '',
        classstructureId: '',
        createDt: '',
        title: '',
        search: '',
        evtresource: '',
        close: '',
        noDateMessage: '',
        status: '',
        date: '',
        system: '',
        move: '',
        moveCi: '',
        moveMonitor: '',
        topologyOpen: '',
        reset: '',
        searchDetail: ''
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
          pageSizes: [10, 20, 30]
        },
        data: [],
        dataBak: [],
        filters: [{
          prop: ['originDt', 'severity', 'evtresource', 'classstructureNm', 'ciname', 'extaln17', 'message', 'classstructureId'],
          value: ''
        }, {
          prop: ['severity'],
          value: []
        }, {
          prop: ['ciType'],
          value: []
        }, {
          prop: ['seleteDate'],
          value: ''
        }, {
          prop: ['status'],
          value: ''
        }, {
          prop: ['ciId'],
          value: ''
        }, {
          prop: ['ciname'],
          value: ''
        }, {
          prop: ['extaln17'],
          value: ''
        }, {
          prop: ['message'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'originDt',
          width: '180px'
        }, {
          prop: 'severity',
          width: '100px'
        }, {
          prop: 'evtresource',
          width: '100px'
        }, {
          prop: 'classstructureNm',
          width: '150px'
        }, {
          prop: 'ciname'
        }, {
          prop: 'extaln17',
          width: '150px'
        }, {
          prop: 'message',
          width: '450px'
        }, {
          prop: 'classstructureId',
          width: '150px'
        }],
        columns: ['originDt', 'severity', 'evtresource', 'classstructureNm', 'ciname', 'extaln17', 'message'],
        columnNames: ['originDt', 'severity', 'evtresource', 'classstructureNm', 'ciname', 'extaln17', 'message'],
        currentData: {},
        total: 0,
        searchParamDetail: {
          ciId: '',
          ciname: '',
          extaln17: '',
          message: ''
        }
      },
      visibleSearchDetailFlag: false,
      listGridFlag: false
    }
  },
  watch: {
    seleteDate: function () {
      if (this.$util.isNull(this.seleteDate)) {
        this.$util.esmessage(this, 'warn', this.labels.noDateMessage)
      }
    },
    severitySelect: function () {
      this.severitySelect.forEach(row => {
        this.severityColor[row.key] = row.value
      })
    },
    '$route': function () {
      if (!this.$util.isNull(this.$route.params.date)) {
        this.paramInit()
      }
    }
  },
  computed: {
    badgeStyle () {
      return (key) => {
        return { backgroundColor: this.severityColor[key], color: 'white' }
      }
    },
    searchDetailBtvClass: function () {
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
      this.$util.setLabels(this.$options.name, this.labels)

      let param = {
        obj: this,
        paramList: [
          {dataObj: 'statusSelect', domainId: 'EVENTSTATUS', sel: 'A'},
          {dataObj: 'systemSelect', domainId: 'EVENTSYSTEM'},
          {dataObj: 'severitySelect', domainId: 'SEVERITYCOLOR'}
        ]
      }
      this.$util.getComboData(param)
      this.listGridFlag = true
    },
    openMessageDialog: function (row) {
      eventBus.$emit('eventDetailOpen', row)
    },
    dateTime: function (date) {
      return this.$util.dateTime(date)
    },
    dateSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[3].value = this.seleteDate
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.searchParam
    },
    searchDetail: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[5].value = this.listGrid.searchParamDetail.ciId
      this.listGrid.filters[6].value = this.listGrid.searchParamDetail.ciname
      this.listGrid.filters[7].value = this.listGrid.searchParamDetail.extaln17
      this.listGrid.filters[8].value = this.listGrid.searchParamDetail.message
      this.visibleSearchDetailFlag = false
    },
    searchDetailReset: function () {
      this.listGrid.currentPage = 1
      this.listGrid.searchParamDetail.ciId = ''
      this.listGrid.searchParamDetail.ciname = ''
      this.listGrid.searchParamDetail.extaln17 = ''
      this.listGrid.searchParamDetail.message = ''
      this.searchDetail()
      this.visibleSearchDetailFlag = false
    },
    getEventhisList: function (srchCnd) {
      if (this.$util.isNull(this.seleteDate)) {
        this.$util.esmessage(this, 'warn', this.labels.noDateMessage)
        return
      }
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      let page = 1
      let pageSize = 10
      let orderBy = 'originDt'
      let sort = 'descending'
      let searchParam = ''
      let ciType = ''
      let severity = ''
      let status = ''
      let searchCiId = ''
      let searchCiname = ''
      let searchExtaln17 = ''
      let searchMessage = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        severity = srchCnd.filters[1].value
        ciType = srchCnd.filters[2].value
        status = srchCnd.filters[4].value
        searchCiId = srchCnd.filters[5].value
        searchCiname = srchCnd.filters[6].value
        searchExtaln17 = srchCnd.filters[7].value
        searchMessage = srchCnd.filters[8].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        startDate: startDate,
        endDate: endDate,
        ciType: ciType.join(','),
        severity: severity.join(','),
        status: status,
        searchCiId: searchCiId,
        searchCiname: searchCiname,
        searchExtaln17: searchExtaln17,
        searchMessage: searchMessage}
      }
      this.$http.get(
        // '/episode/api/event/eventhis?page=' + page + '&pageSize=' + pageSize + '&orderBy=' + orderBy + '&sort=' + sort +
        // '&searchParam=' + searchParam + '&startDate=' + startDate + '&endDate=' + endDate + '&ciType=' + ciType + '&severity=' + severity + '&status=' + status +
        // '&searchCiId=' + searchCiId + '&searchCiname=' + searchCiname + '&searchExtaln17=' + searchExtaln17 + '&searchMessage=' + searchMessage
        '/episode/api/event/eventhis',
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
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      let severity = this.listGrid.filters[1].value
      let ciType = this.listGrid.filters[2].value
      let status = this.listGrid.filters[4].value
      let searchCiId = this.listGrid.filters[5].value
      let searchCiname = this.listGrid.filters[6].value
      let searchExtaln17 = this.listGrid.filters[7].value
      let searchMessage = this.listGrid.filters[8].value
      let params = {params: {
        searchParam: searchParam,
        startDate: startDate,
        endDate: endDate,
        ciType: ciType.join(','),
        severity: severity.join(','),
        status: status,
        searchCiId: searchCiId,
        searchCiname: searchCiname,
        searchExtaln17: searchExtaln17,
        searchMessage: searchMessage}
      }
      this.$http.get(
        '/episode/api/event/eventhis',
        params
      ).then(res => {
        let lang = this.$store.getters.getUser.lang
        let fileName = 'eventhis'
        let fields = [
          {value: 'originDt', label: this.labels.originDt},
          {value: 'severity', label: this.labels.severity},
          {value: 'evtresource', label: this.labels.evtresource},
          {value: 'classstructureId', label: this.labels.classstructureId},
          {value: 'ciname', label: this.labels.ciname},
          {value: 'extaln17', label: this.labels.ipAddress},
          {value: 'message', label: this.labels.message}
        ]
        if (lang === 'ko-kr') {
          fileName = this.labels.title
        }
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    moveMonitor: function (row) {
      this.$util.move(this, 'CIMONITORDETAIL', row.idCi)
    },
    moveCi: function (row) {
      this.$util.move(this, 'CI', row.idCi)
    },
    topologyOpen: function (row) {
      eventBus.$emit('topologyOpen', row)
    },
    paramInit: function () {
      if (!this.$util.isNull(this.$route.params.date)) {
        if (!this.$util.isNull(this.$route.params.ciType)) {
          this.listGrid.filters[1].value = ['Down', 'Critical', 'Major', 'Minor']
          this.listGrid.filters[2].value.push(this.$route.params.ciType)
        }
        this.seleteDate = this.$route.params.date
        if (this.$util.isNull(this.$route.params.ciType)) {
          this.listGrid.filters[4].value = 'OPEN'
        }
      }
    },
    spInit: function () {
      this.searchParam = ''
    }
  },
  created: function () {
    this.initialize()
    this.paramInit()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.seleteDate = []
    this.severitySelect = []
    this.systemSelect = []
    this.statusSelect = []
    this.severityColor = {}
    this.labels = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
