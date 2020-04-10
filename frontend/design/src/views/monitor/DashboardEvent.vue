<!--
 Class Name  : DashboardEvent.vue
 Description : 모니터 대시보드 > 이벤트 현황
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.23  이정호  최초 생성
-->
<template>
  <b-row>
    <b-col sm="6" md="9">
      <b-card no-body>
        <div class='card-header head-sm'>
          {{labels.eventTitle }}
        </div>
      <b-card-body>
        <b-row>
          <b-col :style='wStyle'>
            <h6 class="chart-title">{{labels.server + '[' + eventCount.SMS + ']'}}</h6>
            <doughnut-chart
              :style="aStyle"
              ref="SMS"
              v-if='apiFlag'
              :chart-data="eventData.SMS"
              :flag="eventFlag.SMS"
            />
          </b-col>
          <b-col :style='wStyle'>
            <h6 class="chart-title">{{labels.network + '[' + eventCount.NMS + ']'}}</h6>
            <doughnut-chart
             :style="aStyle"
             ref="NMS"
             v-if='apiFlag'
             :chart-data="eventData.NMS"
             :flag="eventFlag.NMS"
            />
          </b-col>
          <b-col :style='wStyle'>
            <h6 class="chart-title">{{labels.was + '[' + eventCount.APM + ']'}}</h6>
            <doughnut-chart
             :style="aStyle"
             ref="APM"
             v-if='apiFlag'
             :chart-data="eventData.APM"
             :flag="eventFlag.APM"
            />
          </b-col>
          <b-col :style='wStyle'>
            <h6 class="chart-title">{{labels.db + '[' + eventCount.DBMS + ']'}}</h6>
            <doughnut-chart
             :style="aStyle"
             ref="DBMS"
             v-if='apiFlag'
             :chart-data="eventData.DBMS"
             :flag="eventFlag.DBMS"
            />
          </b-col>
          <b-col :style='wStyle'>
            <h6 class="chart-title">{{labels.delay + '[' + eventCount.DELAY + ']'}}</h6>
            <doughnut-chart
             :style="aStyle"
             ref="DELAY"
             v-if='apiFlag'
             :chart-data="eventData.DELAY"
             :flag="eventFlag.DELAY"
            />
          </b-col>
        </b-row>
        <b-row>
          <b-col :style='wStyle'>
            <bar-chart
              :style='gStyle'
              v-if='apiFlag'
              :chart-data="eventhisData.SMS"
              :labels="labels"
            />
          </b-col>
          <b-col :style='wStyle'>
            <bar-chart
              :style='gStyle'
              v-if='apiFlag'
              :chart-data="eventhisData.NMS"
              :labels="labels"
             />
          </b-col>
          <b-col :style='wStyle'>
            <bar-chart
              :style='gStyle'
              v-if='apiFlag'
              :chart-data="eventhisData.APM"
              :labels="labels"
             />
          </b-col>
          <b-col :style='wStyle'>
            <bar-chart
              :style='gStyle'
              v-if='apiFlag'
              :chart-data="eventhisData.DBMS"
              :labels="labels"
            />
          </b-col>
          <b-col :style='wStyle'>
            <bar-chart
              :style='gStyle'
              v-if='apiFlag'
              :chart-data="eventhisData.DELAY"
              :labels="labels"
            />
          </b-col>
        </b-row>
      </b-card-body>
    </b-card>
  </b-col>
  <b-col sm="6" md="3">
    <b-card no-body>
      <div class='card-header head-sm' style="padding-bottom: 8.5px; padding-top: 8.5px;">
        {{labels.busiTitle}}
      <div class="card-header-actions">
            <el-button size="mini" v-on:click="getEventDataBizChange">
              Reload
            </el-button>
       <!-- <a class="cursor-pointer link" v-on:click='getEventDataBizChange()'>[ Reload ]</a> -->
      </div>
      </div>
      <b-card-body>
        <b-row>
          <b-col >
            <b-row>
              <p style="padding-left: 10px;" />
              <div :style='cStyle'>
                <h6 class="chart-title">{{labels.business + '[' + bizCount + ']'}}</h6>
                <doughnut-chart
                  ref="biz"
                  v-if='bizApiFlag'
                  :chart-data="bizData"
                  :flag="bizFlag"
                 />
              </div>
           </b-row>
           <b-row>
             <biz-bar-chart
              ref="bizBar"
              v-if='bizBarApiFlag'
              :style='dStyle'
              :bizBarType="bizBarType"
              :chart-data="bizBarData"
              :bizEventCi="bizEventCi"
            />
          </b-row>
        </b-col>
      </b-row>
    </b-card-body>
  </b-card>
  </b-col>
  </b-row>
</template>

<script>
import DoughnutChart from '@/views/monitor/chart/DoughnutChart'
import BarChart from '@/views/monitor/chart/BarChart'
import BizBarChart from '@/views/monitor/chart/BizBarChart'
export default {
  props: ['labels', 'cycle', 'delayHour', 'severityData'],
  name: 'MONITORDASHBOARD',
  components: {
    DoughnutChart, BarChart, BizBarChart
  },
  data: function () {
    return {
      eventInterval: {},
      busiInterval: {},
      busiDataInterval: {},
      bChartColor: ['#f86c6b', '#ffc107', '#c8ced3'],
      aStyle: {height: '80%', width: '80%', margin: 'auto'},
      gStyle: {height: '150px', width: '80%', margin: 'auto'},
      cStyle: {height: '44.5%', width: '44.5%', margin: 'auto'},
      dStyle: {height: '150px', width: '100%'},
      wStyle: {width: '10%'},
      chartColor: ['#c59e4f', '#bada55', '#6610f2', '#4dbd74', '#73818f', '#17a2b8'],
      eventList: ['SMS', 'NMS', 'APM', 'DBMS', 'DELAY'],
      eventData: {
        SMS: {}, NMS: {}, APM: {}, DBMS: {}, DELAY: {}
      },
      eventCount: {
        SMS: 0, NMS: 0, APM: 0, DBMS: 0, DELAY: 0
      },
      eventhisData: {
        SMS: {}, NMS: {}, APM: {}, DBMS: {}, DELAY: {}
      },
      eventFlag: {
        SMS: false, NMS: false, APM: false, DBMS: false, DELAY: false
      },
      apiFlag: false,
      bizApiFlag: false,
      bizBarApiFlag: false,
      bizData: {},
      bizCount: 0,
      bizFlag: false,
      bizBarData: {},
      bizBarFlag: false,
      bizBarType: false,
      bizEventCi: '',
      authorities: {
        E2E_READ: false,
        EVENT_READ: false
      }
    }
  },
  methods: {
    initialize: function () {
      // this.eventInterval = setInterval(this.getEventChange, (this.cycle * 1000))
      // this.busiInterval = setInterval(this.getEventBizChange, (this.cycle * 1000))
      // this.busiDataInterval = setInterval(this.getEventDataBizChange, (this.cycle * 1000))
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
    },
    chartMake: function (data) {
      this.eventList.forEach(key => {
        let ci = data[key]
        let dArray = []
        let dLabel = []
        let dColor = []
        let bArray = [ci[1][0].previousmonth, ci[1][0].previousday, ci[1][0].day]

        if (ci[0].length !== 0) {
          ci[0].forEach(r => {
            this.eventCount[key] += r.count
            dArray.push(r.count)
            dLabel.push(r.severity)
            dColor.push(this.severityData[r.severity])
            this.eventFlag[key] = true
          })
        } else {
          dArray = [999]
          this.eventFlag[key] = false
        }

        this.eventData[key] = {
          id: key,
          labels: dLabel,
          datasets: [{
            backgroundColor: this.eventFlag[key] ? dColor : '#f2f3f5',
            data: dArray
          }]
        }
        this.eventhisData[key] = {
          id: key,
          labels: key !== 'DELAY' ? [this.labels.thisWeek, this.labels.previousDay, this.labels.day] : [this.labels.previousMonth, this.labels.month, this.labels.previousDay],
          datasets: [{
            backgroundColor: this.bChartColor,
            data: bArray
          }]
        }
      })
      this.apiFlag = true
    },
    chartUpdate: function (data) {
      this.eventList.forEach(key => {
        let ci = data[key]
        let dArray = []
        let dLabel = []
        let dColor = []
        let bArray = [ci[1][0].previousmonth, ci[1][0].previousday, ci[1][0].day]
        if (ci[0].length !== 0) {
          this.eventCount[key] = 0
          ci[0].forEach(r => {
            this.eventCount[key] += r.count
            dArray.push(r.count)
            dLabel.push(r.severity)
            dColor.push(this.severityData[r.severity])
            this.eventFlag[key] = true
          })
        } else {
          dArray = [999]
          this.eventFlag[key] = false
        }

        this.eventData[key] = {
          id: key,
          labels: dLabel,
          datasets: [{
            backgroundColor: this.eventFlag[key] ? dColor : '#f2f3f5',
            data: dArray
          }]
        }
        this.eventhisData[key] = {
          id: key,
          labels: key !== 'DELAY' ? [this.labels.previousMonth, this.labels.previousDay, this.labels.day] : [this.labels.previousMonth, this.labels.month, this.labels.previousDay],
          datasets: [{
            backgroundColor: this.bChartColor,
            data: bArray
          }]
        }
        this.$refs[key].$data._chart.update()
      })
    },
    getEventSituation: function () {
      let cTime = this.$util.timestampDate(new Date())
      let delay = this.$util.timestampDate(new Date(new Date().setHours(new Date().getHours() - this.delayHour)))
      this.apiFlag = false
      let $this = this
      this.$http.get(
        '/episode/api/monitor/dashboard/event', {params: {'cTime': cTime, 'delay': delay}}
      ).then(res => {
        $this.chartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEventChange: function () {
      let cTime = this.$util.timestampDate(new Date())
      let delay = new Date(new Date().setHours(new Date().getHours() - this.delayHour))
      let $this = this
      this.$http.get(
        '/episode/api/monitor/dashboard/event', {params: {'cTime': cTime, 'delay': delay}}
      ).then(res => {
        $this.chartUpdate(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEventBiz: function () {
      let $this = this
      this.bizApiFlag = false
      this.$http.get(
        '/episode/api/monitor/dashboard/eventbiz'
      ).then(res => {
        $this.bizMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEventBizChange: function () {
      let $this = this
      this.$http.get(
        '/episode/api/monitor/dashboard/eventbiz'
      ).then(res => {
        $this.bizUpdate(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEventDataBiz: function () {
      let $this = this
      this.bizBarType = false
      this.bizBarApiFlag = false
      this.$http.get(
        '/episode/api/monitor/dashboard/bizdata'
      ).then(res => {
        $this.bizBarMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEventDataBizChange: function () {
      let $this = this
      this.bizBarType = false
      this.$http.get(
        '/episode/api/monitor/dashboard/bizdata'
      ).then(res => {
        $this.bizBarUpdate(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEventDataBizCi: function (bizCiId) {
      let $this = this
      this.bizBarType = true
      this.$http.get(
        '/episode/api/monitor/dashboard/bizcidata', {params: {'bizCiId': bizCiId}}
      ).then(res => {
        $this.bizBarMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    bizMake: function (data) {
      let dArray = []
      let dLabel = []
      let ciId = []
      if (data.length !== 0) {
        if (data.length > this.chartColor.length) {
          this.$util.getColor(data.length - this.chartColor.length, this.chartColor)
        }
        this.bizCount = 0
        data.forEach(r => {
          this.bizCount += r.cnt
          dArray.push(r.cnt)
          dLabel.push(r.ciname)
          ciId.push(r.ciId)
          this.bizFlag = true
        })
      } else {
        dArray = [999]
        this.bizFlag = false
      }
      this.bizData = {
        id: 'biz',
        labels: dLabel,
        ciId: ciId,
        datasets: [{
          backgroundColor: this.bizFlag ? this.chartColor : '#f2f3f5',
          data: dArray
        }]
      }
      this.bizApiFlag = true
    },
    bizUpdate: function (data) {
      let dArray = []
      let dLabel = []
      let ciId = []
      if (data.length !== 0) {
        if (data.length > this.chartColor.length) {
          this.$util.getColor(data.length - this.chartColor.length, this.chartColor)
        }
        this.bizCount = 0
        data.forEach(r => {
          this.bizCount += r.cnt
          dArray.push(r.cnt)
          dLabel.push(r.ciname)
          ciId.push(r.ciId)
          this.bizFlag = true
        })
      } else {
        dArray = [999]
        this.bizFlag = false
      }
      this.bizData = {
        id: 'biz',
        labels: dLabel,
        ciId: ciId,
        datasets: [{
          backgroundColor: this.bizFlag ? this.chartColor : '#f2f3f5',
          data: dArray
        }]
      }
      this.$refs.biz.$data._chart.update()
    },
    bizBarMake: function (data) {
      let bArray = []
      let bLabel = []
      let bColor = []
      for (let key in this.severityData) {
        bColor.unshift(this.severityData[key])
      }
      bColor.shift()
      if (data.length !== 0) {
        if (this.bizBarType) {
          this.bizEventCi = data[0].ciId
        }
        data.forEach(r => {
          bArray.unshift(r.cnt)
          bLabel.unshift(r.severity)
        })
        this.bizBarFlag = true
      }
      this.bizBarData = {
        id: 'bizData',
        labels: bLabel,
        datasets: [{
          backgroundColor: bColor,
          data: bArray
        }]
      }
      this.bizBarApiFlag = true
    },
    bizBarUpdate: function (data) {
      let bArray = []
      let bLabel = []
      let bColor = []
      for (let key in this.severityData) {
        bColor.unshift(this.severityData[key])
      }
      bColor.shift()
      if (data.length !== 0) {
        if (this.bizBarType) {
          this.bizEventCi = data[0].ciId
        }
        data.forEach(r => {
          bArray.unshift(r.cnt)
          bLabel.unshift(r.severity)
        })
        this.bizBarFlag = true
      }
      this.bizBarData = {
        id: 'bizData',
        labels: bLabel,
        datasets: [{
          backgroundColor: bColor,
          data: bArray
        }]
      }
      this.$refs.bizBar.$data._chart.update()
    },
    bizEvent: function () {

    }
  },
  mounted () {
    this.getEventSituation()
    this.getEventBiz()
    this.getEventDataBiz()
  },
  created () {
    this.initialize()
  },
  beforeDestroy () {
    this.eventFlag.SMS = false
    this.eventFlag.NMS = false
    this.eventFlag.APM = false
    this.eventFlag.DBMS = false
    this.eventFlag.DELAY = false
    this.apiFlag = false
    this.$nextTick(function () {
      clearInterval(this.eventInterval)
      clearInterval(this.busiInterval)
      clearInterval(this.busiDataInterval)
    })
  }

}
</script>
