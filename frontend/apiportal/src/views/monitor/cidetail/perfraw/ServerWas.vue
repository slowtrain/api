<!--
 Class Name  : ServerWas.vue
 Description : 장비별 대시보드 > 서버 Was 성능 컴포넌트
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
              <line-chart ref="AVERAGE_RESPONSE_TIME" :height='chartHeight' v-if='this.relationFlag.AVERAGE_RESPONSE_TIME' :chart-data="this.relationData.AVERAGE_RESPONSE_TIME" :options="this.relationOption.AVERAGE_RESPONSE_TIME" :style='qStyle'/>
          </b-row>
           <b-row>
              <line-chart ref="WAIT_TIME" :height='chartHeight' v-if='this.relationFlag.WAIT_TIME' :chart-data="this.relationData.WAIT_TIME" :options="this.relationOption.WAIT_TIME" :style='qStyle'/>
          </b-row>
           <b-row>
              <line-chart ref="RESPONSE_TIME" :height='chartHeight' v-if='this.relationFlag.RESPONSE_TIME' :chart-data="this.relationData.RESPONSE_TIME" :options="this.relationOption.RESPONSE_TIME" :style='qStyle'/>
          </b-row>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import LineChart from '@/views/monitor/chart/LineChart'

export default {
  name: 'PERFRAW',
  props: ['ciData', 'system', 'wasSc', 'cycle', 'datePref', 'labels'],
  components: {
    LineChart
  },
  data: function () {
    return {
      chartHeight: 400,
      sWasfInterval: {},
      resource: 'APM',
      ancestor: 'SW_MIDDLESW_WAS',
      qStyle: {height: '130%', width: '100%'},
      avgFlag: false,
      relationList: ['AVERAGE_RESPONSE_TIME', 'WAIT_TIME', 'RESPONSE_TIME'],
      relationData: {AVERAGE_RESPONSE_TIME: {}, WAIT_TIME: {}, RESPONSE_TIME: {}},
      relationOption: {AVERAGE_RESPONSE_TIME: {}, WAIT_TIME: {}, RESPONSE_TIME: {}},
      relationFlag: {AVERAGE_RESPONSE_TIME: false, WAIT_TIME: false, RESPONSE_TIME: false},
      relationName: {AVERAGE_RESPONSE_TIME: '', WAIT_TIME: '', RESPONSE_TIME: ''},
      chartColor: [],
      chartColor2: [],
      colorFlag: false
    }
  },
  watch: {
    ciData: function () {
      if (this.$util.isNull(this.ciData.ciId)) {
        return
      }
      clearInterval(this.sWasfInterval)
      if (this.system === 'SERVER') this.initialize()
    },
    wasSc: function () {
      if (this.wasSc) {
        this.relationList.forEach(r => {
          this.relationOption[r].legend.display = true
          this.$refs[r].$data._chart.update()
          this.$refs[r].renderChart(this.relationData[r], this.relationOption[r])
        })
      } else {
        this.relationList.forEach(r => {
          this.relationOption[r].legend.display = false
          this.$refs[r].$data._chart.update()
          this.$refs[r].renderChart(this.relationData[r], this.relationOption[r])
        })
      }
    }
  },
  methods: {
    initialize: function () {
      this.chartColor = []
      this.chartColor2 = []
      this.relationName.AVERAGE_RESPONSE_TIME = this.labels.avgResTime
      this.relationName.WAIT_TIME = this.labels.waitTime
      this.relationName.RESPONSE_TIME = this.labels.responseTime
      this.colorFlag = false
      this.getEms()
      this.sWasfInterval = setInterval(this.getEmsAdd, (this.cycle * 1000))
    },
    getEms: function () {
      this.relationFlag.AVERAGE_RESPONSE_TIME = false
      this.relationFlag.WAIT_TIME = false
      this.relationFlag.RESPONSE_TIME = false
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      let params = {params: {
        ciId: this.ciData.ciId,
        resource: this.resource,
        ancestor: this.ancestor,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/monitor/detail/relationems',
        params
      ).then(res => {
        this.relationChart(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEmsAdd: function () {
      if (this.relationData.AVERAGE_RESPONSE_TIME.labels.length > 286 || this.relationData.WAIT_TIME.labels.length > 286 || this.relationData.RESPONSE_TIME.labels.length > 286) {
        this.getEms()
        return
      }
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      let params = {params: {
        ciId: this.ciData.ciId,
        resource: this.resource,
        ancestor: this.ancestor,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/monitor/detail/relationadd',
        params
      ).then(res => {
        if (res.data.length === 0) return
        this.relationList.forEach(r => {
          if (r === 'AVERAGE_RESPONSE_TIME') this.$util.multipleChartPush(this.relationData.AVERAGE_RESPONSE_TIME, this.relationOption.AVERAGE_RESPONSE_TIME, res.data.filter(row => row.perf_detail === r), this.$refs.AVERAGE_RESPONSE_TIME.$data._chart)
          else if (r === 'WAIT_TIME') this.$util.multipleChartPush(this.relationData.WAIT_TIME, this.relationOption.WAIT_TIME, res.data.filter(row => row.perf_detail === r), this.$refs.WAIT_TIME.$data._chart)
          else this.$util.multipleChartPush(this.relationData.RESPONSE_TIME, this.relationOption.RESPONSE_TIME, res.data.filter(row => row.perf_detail === r), this.$refs.RESPONSE_TIME.$data._chart)
        })
      }).catch(error => {
        console.log(error)
      })
    },
    relationChart: function (data) {
      this.$emit('serverWasData', data)
      if (data.length !== 0) {
        this.relationList.forEach(r => {
          this.relationChartMake(r, data.filter(row => row.perf_detail === r))
        })
      } else {
        clearInterval(this.sWasfInterval)
      }
    },
    relationChartMake: function (type, relationData) {
      if (relationData.length === 0) {
        return
      }
      if (!this.colorFlag) {
        this.$util.getRgba(relationData, this.chartColor, this.chartColor2)
        this.colorFlag = true
      }
      this.chartHeight = 400
      if (relationData.length > 18) {
        this.chartHeight = relationData.length * 22
      }
      this.relationFlag[type] = true
      const day = JSON.parse(relationData[0].json.value)
      this.relationData[type].labels = day.origin
      this.relationData[type].datasets = []
      // this.relationData[type].rt = day.rt
      this.relationData[type].rt = day.rt[day.rt.length - 1]
      relationData.forEach((r, i) => {
        const jsonData = JSON.parse(r.json.value)
        const chartData = {}
        chartData.pointRadius = 0
        chartData.label = jsonData.ciname
        chartData.borderColor = this.chartColor[i]
        chartData.borderWidth = 1.5
        chartData.backgroundColor = this.chartColor2[i]
        chartData.data = jsonData.rscval
        this.relationData[type].datasets.push(chartData)
      })
      this.relationOption[type] = {
        legend: {
          display: false,
          labels: {
            fontSize: relationData.length > 10 ? 8 : 12
          }
        },
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
                return value + ' (ms)'
              }
            },
            scaleLabel: {
              display: true,
              labelString: this.relationName[type]
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
    this.relationData.AVERAGE_RESPONSE_TIME = {}
    this.relationData.WAIT_TIME = {}
    this.relationData.RESPONSE_TIME = {}
    this.relationOption.AVERAGE_RESPONSE_TIME = {}
    this.relationOption.WAIT_TIME = {}
    this.relationOption.RESPONSE_TIME = {}
    this.$nextTick(function () {
      this.relationFlag.AVERAGE_RESPONSE_TIME = false
      this.relationFlag.WAIT_TIME = false
      this.relationFlag.RESPONSE_TIME = false
      clearInterval(this.sWasfInterval)
    })
  }
}
</script>
