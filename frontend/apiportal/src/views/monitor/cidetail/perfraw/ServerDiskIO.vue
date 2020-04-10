<!--
 Class Name  : ServerDiskIO.vue
 Description : 장비별 대시보드 > 서버 디스크IO 성능 컴포넌트
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
              <line-chart ref="diskIo" :height='chartHeight' v-if='this.diskIoFlag' :chart-data="diskIoData" :options="diskIoOption" :style='qStyle'/>
          </b-row>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import LineChart from '@/views/monitor/chart/LineChart'

export default {
  name: 'PERFRAW',
  props: ['ciData', 'system', 'diskIoSc', 'cycle', 'datePref', 'labels'],
  components: {
    LineChart
  },
  data: function () {
    return {
      chartHeight: 400,
      qStyle: {height: '100%', width: '100%'},
      diskIoType: 'DISKIO_BUSY',
      diskIoFlag: false,
      diskIoData: {},
      diskIoOption: {},
      chartColor: [],
      chartColor2: [],
      sDiskIoInterval: {}
    }
  },
  watch: {
    ciData: function () {
      if (this.$util.isNull(this.ciData.ciId)) {
        return
      }
      clearInterval(this.sDiskIoInterval)
      if (this.system === 'SERVER') this.initialize()
    },
    diskIoSc: function () {
      if (this.diskIoSc) {
        this.diskIoOption.legend.display = true
        this.$refs.diskIo.$data._chart.update()
        this.$refs.diskIo.renderChart(this.diskIoData, this.diskIoOption)
      } else {
        this.diskIoOption.legend.display = false
        this.$refs.diskIo.$data._chart.update()
        this.$refs.diskIo.renderChart(this.diskIoData, this.diskIoOption)
      }
    }
  },
  methods: {
    initialize: function () {
      this.chartColor = []
      this.chartColor2 = []
      this.getEms()
      this.sDiskIoInterval = setInterval(this.getEmsAdd, (this.cycle * 1000))
    },
    getEms: function () {
      this.diskIoFlag = false
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      let params = {params: {
        ciId: this.ciData.ciId,
        rscType: this.diskIoType,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/monitor/detail/multiplems',
        params
      ).then(res => {
        this.diskIoChartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEmsAdd: function () {
      if (this.diskIoData.labels.length > 286) {
        this.getEms()
        return
      }
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      let params = {params: {
        ciId: this.ciData.ciId,
        rscType: this.diskIoType,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/monitor/detail/multiplemsadd',
        params
      ).then(res => {
        if (res.data.length === 0) return
        this.$util.multipleChartPush(this.diskIoData, this.diskIoOption, res.data, this.$refs.diskIo.$data._chart)
      }).catch(error => {
        console.log(error)
      })
    },
    diskIoChartMake: function (data) {
      this.diskIoFlag = true
      if (data.length !== 0) {
        this.$util.getRgba(data, this.chartColor, this.chartColor2)
        this.chartHeight = 400
        if (data.length > 18) {
          this.chartHeight = data.length * 22
        }
        const day = JSON.parse(data[0].json.value)
        this.diskIoData = {
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
          this.diskIoData.datasets.push(chartData)
        })
      } else {
        clearInterval(this.sDiskIoInterval)
        this.diskIoData = {
          datasets: []
        }
      }
      this.diskIoOption = {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false
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
              max: 100,
              stepSize: 20,
              callback: function (value) {
                return value + ' (%)'
              }
            },
            scaleLabel: {
              display: true,
              labelString: this.labels.diskIoUse
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
    this.diskIoData.data = {}
    this.diskIoOption = {}
    this.$nextTick(function () {
      this.diskIoFlag = false
      clearInterval(this.sDiskIoInterval)
    })
  }
}
</script>
