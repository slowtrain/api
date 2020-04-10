<!--
 Class Name  : ServerDb.vue
 Description : 장비별 대시보드 > 서버 DB 성능 컴포넌트
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
              <line-chart ref="DB_SESS_COUNT" :height='chartHeight' v-if='this.relationFlag.DB_SESS_COUNT' :chart-data="this.relationData.DB_SESS_COUNT" :options="this.relationOption.DB_SESS_COUNT" :style='qStyle'/>
          </b-row>
           <b-row>
              <line-chart ref="DB_RESPONSETIME" :height='chartHeight' v-if='this.relationFlag.DB_RESPONSETIME' :chart-data="this.relationData.DB_RESPONSETIME" :options="this.relationOption.DB_RESPONSETIME" :style='qStyle'/>
          </b-row>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import LineChart from '@/views/monitor/chart/LineChart'

export default {
  name: 'PERFRAW',
  props: ['ciData', 'system', 'dbSc', 'cycle', 'datePref', 'labels'],
  components: {
    LineChart
  },
  data: function () {
    return {
      chartHeight: 400,
      resource: 'DBMS',
      ancestor: 'SW_DBSW',
      qStyle: {height: '100%', width: '100%'},
      relationList: ['DB_SESS_COUNT', 'DB_RESPONSETIME'],
      relationData: {DB_SESS_COUNT: {}, DB_RESPONSETIME: {}},
      relationOption: {DB_SESS_COUNT: {}, DB_RESPONSETIME: {}},
      relationFlag: {DB_SESS_COUNT: false, DB_RESPONSETIME: false},
      relationName: {DB_SESS_COUNT: '', DB_RESPONSETIME: ''},
      chartColor: [],
      chartColor2: [],
      colorFlag: false,
      sDbInterval: {}
    }
  },
  watch: {
    ciData: function () {
      if (this.$util.isNull(this.ciData.ciId)) {
        return
      }
      clearInterval(this.sDbInterval)
      if (this.system === 'SERVER') this.initialize()
    },
    dbSc: function () {
      if (this.dbSc) {
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
      this.relationName.DB_SESS_COUNT = this.labels.dbSessCount
      this.relationName.DB_RESPONSETIME = this.labels.dbResTime
      this.colorFlag = false
      this.getEms()
      this.sDbInterval = setInterval(this.getEmsAdd, (this.cycle * 1000))
    },
    getEms: function () {
      this.relationFlag.DB_SESS_COUNT = false
      this.relationFlag.DB_RESPONSETIME = false
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
      if (this.relationData.DB_SESS_COUNT.labels.length > 286 || this.relationData.DB_RESPONSETIME.labels.length > 286) {
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
          if (r === 'DB_SESS_COUNT') this.$util.multipleChartPush(this.relationData.DB_SESS_COUNT, this.relationOption.DB_SESS_COUNT, res.data.filter(row => row.perf_detail === r), this.$refs.DB_SESS_COUNT.$data._chart)
          else this.$util.multipleChartPush(this.relationData.DB_RESPONSETIME, this.relationOption.DB_RESPONSETIME, res.data.filter(row => row.perf_detail === r), this.$refs.DB_RESPONSETIME.$data._chart)
        })
      }).catch(error => {
        console.log(error)
      })
    },
    relationChart: function (data) {
      this.$emit('serverDbData', data)
      if (data.length !== 0) {
        this.relationList.forEach(r => {
          this.relationChartMake(r, data.filter(row => row.perf_detail === r))
        })
      } else {
        clearInterval(this.sDbInterval)
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

      let $this = this
      this.relationOption[type] = {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false,
          labels: {
            fontSize: relationData.length > 10 ? 8 : 12
          }
        },
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
                return $this.relationName[type] === 'Response Time(ms)' ? value + ' (ms)' : value
              }
            },
            scaleLabel: {
              display: true,
              labelString: this.relationName[type]
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
    this.relationOption.DB_SESS_COUNT = {}
    this.relationOption.DB_RESPONSETIME = {}
    this.relationData.DB_SESS_COUNT = {}
    this.relationData.DB_RESPONSETIME = {}
    this.$nextTick(function () {
      this.relationFlag.DB_SESS_COUNT = false
      this.relationFlag.DB_RESPONSETIME = false
      clearInterval(this.sDbInterval)
    })
  }
}
</script>
