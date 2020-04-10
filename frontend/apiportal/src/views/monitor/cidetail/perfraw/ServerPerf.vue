<!--
 Class Name  : ServerPerf.vue
 Description : 장비별 대시보드 > 서버 성능 컴포넌트
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
              <line-chart ref="cpu" v-if='this.cpuFlag' :chart-data="cpuData" :options="cpuOption" :style='qStyle'/>
          </b-row>
           <b-row>
              <line-chart ref="memory" v-if='this.memoryFlag' :chart-data="memoryData" :options="memoryOption" :style='qStyle'/>
          </b-row>
           <b-row>
              <line-chart ref="res" v-if='this.resFlag' :chart-data="resData" :options="resOption" :style='qStyle'/>
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
      cpuType: 'TOTAL_USERATE',
      memoryType: 'USERATE',
      resType: 'RESPONSETIME_ICMP',
      cpuFlag: false,
      cpuData: {},
      cpuOption: {},
      memoryFlag: false,
      memoryData: {},
      memoryOption: {},
      resFlag: false,
      resData: {},
      resOption: {},
      sPerfInterval: {}
    }
  },
  watch: {
    ciData: function () {
      if (this.$util.isNull(this.ciData.ciId)) {
        return
      }
      clearInterval(this.sPerfInterval)
      if (this.system === 'SERVER') {
        this.initialize()
      }
    }
  },
  methods: {
    initialize: function () {
      this.getEms()
      this.sPerfInterval = setInterval(this.getEmsAdd, (this.cycle * 1000))
    },
    getEms: function () {
      // this.cpuFlag = false
      // this.memoryFlag = false
      // this.resFlag = false
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      this.$http.get(
        '/episode/api/monitor/detail/emsperf', {params: {ciId: this.ciData.ciId, rscType: this.cpuType, cTime: cTime}}
      ).then(res => {
        this.rtCpu = res.data.day.rt[res.data.day.rt.length - 1]
        this.cpuChartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/monitor/detail/emsperf', {params: {ciId: this.ciData.ciId, rscType: this.memoryType, cTime: cTime}}
      ).then(res => {
        this.rtMemory = res.data.day.rt[res.data.day.rt.length - 1]
        this.memoryChartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/monitor/detail/emsperf', {params: {ciId: this.ciData.ciId, rscType: this.resType, cTime: cTime}}
      ).then(res => {
        this.rtRes = res.data.day.rt[res.data.day.rt.length - 1]
        this.resChartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEmsAdd: function () {
      if (this.cpuData.labels.length > 286 || this.memoryData.labels.length > 286 || this.resData.labels.length > 286) {
        this.getEms()
        return
      }
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      this.$http.get(
        '/episode/api/monitor/detail/emsperfadd', {params: {ciId: this.ciData.ciId, rscType: this.cpuType, cTime: cTime}}
      ).then(res => {
        this.$util.singleChartPush(this.cpuData, this.cpuOption, res.data, this.$refs.cpu.$data._chart)
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/monitor/detail/emsperfadd', {params: {ciId: this.ciData.ciId, rscType: this.memoryType, cTime: cTime}}
      ).then(res => {
        this.$util.singleChartPush(this.memoryData, this.memoryOption, res.data, this.$refs.memory.$data._chart)
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/monitor/detail/emsperfadd', {params: {ciId: this.ciData.ciId, rscType: this.resType, cTime: cTime}}
      ).then(res => {
        this.$util.singleChartPush(this.resData, this.resOption, res.data, this.$refs.res.$data._chart)
      }).catch(error => {
        console.log(error)
      })
    },
    upDateChk: function (origin, rt, min) {
      if (!this.$util.isNull(origin)) {
        return true
      } else {
        if (rt === 1) {
          if (min === '4') return true
          else return false
        } else {
          if (min === '9') return true
          else return false
        }
      }
    },
    cpuChartMake: function (data) {
      this.cpuFlag = true
      let day = data.day
      let previousDay = data.previousDay
      this.cpuData = {
        labels: day.origin,
        rt: day.rt[day.rt.length - 1],
        datasets: [
          {
            pointRadius: 0,
            borderWidth: 1.5,
            label: this.labels.day,
            borderColor: 'rgba(63, 110, 198, 0.7)',
            backgroundColor: 'rgba(217, 226, 224, 0.3)',
            data: day.rscval
          },
          {
            pointRadius: 0,
            borderWidth: 1.5,
            label: this.labels.previousDay,
            borderColor: 'rgba(255, 108, 81, 0.7)',
            backgroundColor: 'rgba(255, 226, 220, 0.3)',
            data: previousDay.rscval
          }
        ]
      }

      this.cpuOption = {
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
              // min: new Date(new Date().setDate(new Date().getDate() - 1)),
              // max: new Date()
              min: new Date(new Date().setHours(new Date().getHours() - 22)),
              max: new Date(new Date().setHours(new Date().getHours() + 2))
            }
          }],
          yAxes: [{
            ticks: {
              min: 0,
              max: 100,
              stepSize: 20,
              callback: function (value) {
                return value + ' (%)'
              }
            },
            scaleLabel: {
              display: true,
              labelString: this.labels.cpuUse
              // fontColor: "green"
            }
          }]
        }

      }
    },
    memoryChartMake: function (data) {
      this.memoryFlag = true
      let day = data.day
      let previousDay = data.previousDay

      this.memoryData = {
        labels: day.origin,
        rt: day.rt[day.rt.length - 1],
        datasets: [
          {
            pointRadius: 0,
            borderWidth: 1.5,
            label: '당일',
            borderColor: 'rgba(63, 110, 198, 0.7)',
            backgroundColor: 'rgba(217, 226, 224, 0.3)',
            data: day.rscval
          },
          {
            pointRadius: 0,
            borderWidth: 1.5,
            label: '전일',
            borderColor: 'rgba(255, 108, 81, 0.7)',
            backgroundColor: 'rgba(255, 226, 220, 0.3)',
            data: previousDay.rscval
          }
        ]
      }

      this.memoryOption = {
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
              max: 100,
              stepSize: 20,
              callback: function (value) {
                return value + ' (%)'
              }
            },
            scaleLabel: {
              display: true,
              labelString: '메모리 사용률(%)'
              // fontColor: "green"
            }
          }]
        }

      }
    },
    resChartMake: function (data) {
      this.resFlag = true
      let day = data.day
      let previousDay = data.previousDay
      this.resData = {
        labels: day.origin,
        rt: day.rt[day.rt.length - 1],
        datasets: [
          {
            pointRadius: 0,
            borderWidth: 1.5,
            label: '당일',
            borderColor: 'rgba(63, 110, 198, 0.7)',
            backgroundColor: 'rgba(217, 226, 224, 0.3)',
            data: day.rscval
          },
          {
            pointRadius: 0,
            borderWidth: 1.5,
            label: '전일',
            borderColor: 'rgba(255, 108, 81, 0.7)',
            backgroundColor: 'rgba(255, 226, 220, 0.3)',
            data: previousDay.rscval
          }
        ]
      }

      this.resOption = {
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
              // max: 100,
              // stepSize: 2,
              callback: function (value) {
                return value + ' (ms)'
              }
            },
            scaleLabel: {
              display: true,
              labelString: '응답시간(ICMP)(ms)'
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
    this.cpuOption = {}
    this.memoryOption = {}
    this.resOption = {}
    this.cpuOption = {}
    this.memoryOption = {}
    this.resOption = {}
    this.$nextTick(function () {
      this.cpuFlag = false
      this.memoryFlag = false
      this.resFlag = false
      clearInterval(this.sPerfInterval)
    })
  }
}
</script>
