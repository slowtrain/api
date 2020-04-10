<!--
 Class Name  : ServerNetIO.vue
 Description : 장비별 대시보드 > 서버 네트워크IO 성능 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.02  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-row>
      <b-col>
           <b-row>
              <line-chart ref="netIn" v-if='this.netInFlag' :chart-data="netInData" :options="netInOption" :style='qStyle'/>
          </b-row>
           <b-row>
              <line-chart ref="netOut" v-if='this.netOutFlag' :chart-data="netOutData" :options="netOutOption" :style='qStyle'/>
          </b-row>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import LineChart from '@/views/monitor/chart/LineChart'

export default {
  name: 'PERFRAW',
  props: ['ciData', 'system', 'cycle', 'datePref', 'labels'],
  components: {
    LineChart
  },
  data: function () {
    return {
      qStyle: {height: '200px', width: '100%'},
      netInType: 'NETIO_BPS_IN',
      netOutType: 'NETIO_BPS_OUT',
      netInFlag: false,
      netInData: {},
      netInOption: {},
      netOutFlag: false,
      netOutData: {},
      netOutOption: {},
      chartColor: [],
      chartColor2: [],
      sNetIoInterval: {}
    }
  },
  watch: {
    ciData: function () {
      if (this.$util.isNull(this.ciData.ciId)) {
        return
      }
      clearInterval(this.sNetIoInterval)
      if (this.system === 'SERVER') this.initialize()
    }
  },
  methods: {
    initialize: function () {
      this.chartColor = []
      this.chartColor2 = []
      this.getEms()
      this.sNetIoInterval = setInterval(this.getEmsAdd, (this.cycle * 1000))
    },
    getEms: function () {
      this.netInFlag = false
      this.netOutFlag = false
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      this.$http.get(
        '/episode/api/monitor/detail/multiplems', {params: {ciId: this.ciData.ciId, rscType: this.netInType, cTime: cTime}}
      ).then(res => {
        this.netInChartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/monitor/detail/multiplems', {params: {ciId: this.ciData.ciId, rscType: this.netOutType, cTime: cTime}}
      ).then(res => {
        this.netOutChartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEmsAdd: function () {
      if (this.netInData.labels.length > 286 || this.netOutData.labels.length > 286) {
        this.getEms()
        return
      }
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      this.$http.get(
        '/episode/api/monitor/detail/multiplemsadd', {params: {ciId: this.ciData.ciId, rscType: this.netInType, cTime: cTime}}
      ).then(res => {
        if (res.data.length === 0) return
        this.$util.multipleChartPush(this.netInData, this.netInOption, res.data, this.$refs.netIn.$data._chart)
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/monitor/detail/multiplemsadd', {params: {ciId: this.ciData.ciId, rscType: this.netOutType, cTime: cTime}}
      ).then(res => {
        this.$util.multipleChartPush(this.netOutData, this.netOutOption, res.data, this.$refs.netOut.$data._chart)
      }).catch(error => {
        console.log(error)
      })
    },
    netInChartMake: function (data) {
      this.netInFlag = true
      if (data.length !== 0) {
        this.$util.getRgba(data, this.chartColor, this.chartColor2)
        const day = JSON.parse(data[0].json.value)
        this.netInData = {
          labels: day.origin,
          rt: day.rt[day.rt.length - 1],
          datasets: []
        }
        data.forEach((r, i) => {
          const jsonData = JSON.parse(r.json.value)
          const chartData = {}
          chartData.pointRadius = 0
          chartData.label = jsonData.rsc_key_name
          chartData.borderColor = this.chartColor[i]
          chartData.borderWidth = 1.5
          chartData.backgroundColor = this.chartColor2[i]
          chartData.data = jsonData.rscval
          this.netInData.datasets.push(chartData)
        })
      } else {
        clearInterval(this.sNetIoInterval)
        this.netInData = {
          datasets: []
        }
      }
      this.netInOption = {
        responsive: true,
        maintainAspectRatio: false,
        hover: {
          mode: 'label',
          intersect: true
        },
        tooltips: {
          intersect: false,
          mode: 'index'
        },
        scales: {
          showLines: false,
          xAxes: [{
            type: 'time',
            time: {
              parser: 'YYYY-MM-DD HH:mm:ss',
              unit: 'hour',
              unitStepSize: 2,
              // round: 'hour',
              // tooltipFormat: "h:mm:ss a",
              displayFormats: {
                hour: 'HH:mm'
              },
              min: new Date(new Date().setHours(new Date().getHours() - 22)),
              max: new Date(new Date().setHours(new Date().getHours() + 2))
            }
          }],
          yAxes: [{
            ticks: {
              min: 0,
              callback: function (value) {
                return value + ' (bps)'
              }
            },
            scaleLabel: {
              display: true,
              labelString: this.labels.netIn
              // fontColor: "green"
            }
          }]
        }
      }
    },
    netOutChartMake: function (data) {
      this.netOutFlag = true
      if (data.length !== 0) {
        this.$util.getRgba(data, this.chartColor, this.chartColor2)
        const day = JSON.parse(data[0].json.value)
        this.netOutData = {
          labels: day.origin,
          rt: day.rt[day.rt.length - 1],
          datasets: []
        }
        data.forEach((r, i) => {
          const jsonData = JSON.parse(r.json.value)
          const chartData = {}
          chartData.pointRadius = 0
          chartData.label = jsonData.rsc_key_name
          chartData.borderColor = this.chartColor[i]
          chartData.borderWidth = 1.5
          chartData.backgroundColor = this.chartColor2[i]
          chartData.data = jsonData.rscval
          this.netOutData.datasets.push(chartData)
        })
      } else {
        clearInterval(this.sNetIoInterval)
        this.netOutData = {
          datasets: []
        }
      }
      this.netOutOption = {
        responsive: true,
        maintainAspectRatio: false,
        hover: {
          mode: 'label',
          intersect: true
        },
        tooltips: {
          intersect: false,
          mode: 'index'
        },
        scales: {
          showLines: false,
          xAxes: [{
            type: 'time',
            time: {
              parser: 'YYYY-MM-DD HH:mm:ss',
              unit: 'hour',
              unitStepSize: 2,
              // round: 'hour',
              // tooltipFormat: "h:mm:ss a",
              displayFormats: {
                hour: 'HH:mm'
              },
              min: new Date(new Date().setHours(new Date().getHours() - 22)),
              max: new Date(new Date().setHours(new Date().getHours() + 2))
            }
          }],
          yAxes: [{
            ticks: {
              min: 0,
              callback: function (value) {
                return value + ' (bps)'
              }
            },
            scaleLabel: {
              display: true,
              labelString: this.labels.netOut
              // fontColor: "green"
            }
          }]
        }
      }
    }
  },
  mounted () {
    // this.initialize()
  },
  beforeDestroy () {
    this.chartColor = []
    this.chartColor2 = []
    this.netOutData = {}
    this.netInData = {}
    this.netInOption = {}
    this.netOutOption = {}
    this.$nextTick(function () {
      this.netOutFlag = false
      this.netInFlag = false
      clearInterval(this.sNetIoInterval)
    })
  }
}
</script>
