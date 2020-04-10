<!--
 Class Name  : NetPerf.vue
 Description : 장비별 대시보드 > 네트워크 성능 컴포넌트
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
      resType: 'RESPONSETIME_ICMP',
      resFlag: false,
      resData: {},
      resOption: {},
      nPerfInterval: {}
    }
  },
  watch: {
    ciData: function () {
      if (this.$util.isNull(this.ciData.ciId)) {
        return
      }
      clearInterval(this.nPerfInterval)
      if (this.system === 'NETWORK') this.initialize()
    }
  },
  methods: {
    initialize: function () {
      this.getEms()
      this.nPerfInterval = setInterval(this.getEmsAdd, (this.cycle * 1000))
    },
    getEms: function () {
      // this.resFlag = false
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      let params = {params: {
        ciId: this.ciData.ciId,
        rscType: this.resType,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/monitor/detail/emsperf',
        params
      ).then(res => {
        this.resChartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEmsAdd: function () {
      if (this.resData.labels.length > 286) {
        this.getEms()
        return
      }
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      let params = {params: {
        ciId: this.ciData.ciId,
        rscType: this.resType,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/monitor/detail/emsperfadd',
        params
      ).then(res => {
        this.$util.singleChartPush(this.resData, this.resOption, res.data, this.$refs.res.$data._chart)
      }).catch(error => {
        console.log(error)
      })
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
            label: this.labels.day,
            borderColor: 'rgba(50, 115, 220, 0.5)',
            backgroundColor: 'rgba(50, 115, 220, 0.1)',
            data: day.rscval
          },
          {
            pointRadius: 0,
            borderWidth: 1.5,
            label: this.labels.previousDay,
            borderColor: 'rgba(255, 56, 96, 0.5)',
            backgroundColor: 'rgba(255, 56, 96, 0.1)',
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
              labelString: this.labels.icmp
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
    this.resData = {}
    this.resOption = {}
    this.$nextTick(function () {
      this.resFlag = false
      clearInterval(this.nPerfInterval)
    })
  }
}
</script>
