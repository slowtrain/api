<!--
 Class Name  : E2EChart.vue
 Description : E2E 현황 > 라인차트 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.23  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-row>
      <b-col sm="12">
              <line-chart v-if='e2eChartFlag' :chart-data="e2eData" :options="e2eOption" :style='qStyle'/>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import LineChart from '@/views/monitor/chart/LineChart'

export default {
  name: 'E2ECHART',
  props: ['ciId', 'rpa', 'seleteDate', 'labels'],
  components: {
    LineChart
  },
  data: function () {
    return {
      qStyle: {height: '250px', width: '100%'},
      e2eChartFlag: false,
      e2eData: {},
      e2eOption: {},
      chartColor: [],
      chartColor2: []
      // chartColor: [
      //   'rgba(54, 162, 235, 0.5)',
      //   'rgba(255, 206, 86, 0.5)',
      //   'rgba(75, 192, 192, 0.5)',
      //   'rgba(153, 102, 255, 0.5)',
      //   'rgba(255, 159, 64, 0.5)'
      // ],
      // chartColor2: [
      //   'rgba(54, 162, 235, 0.1)',
      //   'rgba(255, 206, 86, 0.1)',
      //   'rgba(75, 192, 192, 0.1)',
      //   'rgba(153, 102, 255, 0.1)',
      //   'rgba(255, 159, 64, 0.1)'
      // ]
    }
  },
  watch: {
  },
  methods: {
    initialize: function () {
      if (this.$util.isNull(this.ciId) || this.$util.isNull(this.rpa)) {
        return
      }
      this.getE2EData(this.ciId, this.rpa)
    },
    getE2EData: function (ciId, rpa) {
      this.e2eChartFlag = false
      this.e2eData = {}
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      let params = {params: {
        ciId: ciId,
        rpa: rpa.join(','),
        startDate: startDate,
        endDate: endDate}
      }
      this.$http.get(
        '/episode/api/e2e/rpadata',
        params
      ).then(res => {
        this.chartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    dateTime: function (date) {
      return this.$util.dateTime(date)
    },
    chartMake: function (data) {
      this.e2eChartFlag = true
      if (data.length !== 0 && !this.$util.isNull(data[0])) {
        this.$util.getRgba(data, this.chartColor, this.chartColor2)
        const time = data[0].origin
        const cDate = []
        time.forEach(row => {
          let dateType = row.substring(0, row.indexOf('.'))
          let dateValue = new Date(dateType)
          cDate.push(new Date(dateValue))
        })
        this.e2eData.labels = cDate
        this.e2eData.datasets = []
        data.forEach((r, i) => {
          const jsonData = r.resptime
          const chartData = {}
          chartData.pointRadius = 0
          chartData.label = r.namekor
          chartData.borderColor = this.chartColor[i]
          chartData.borderWidth = 1.5
          chartData.backgroundColor = this.chartColor2[i]
          chartData.data = jsonData
          this.e2eData.datasets.push(chartData)
        })
      }

      this.e2eOption = {
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
              min: new Date(this.seleteDate[0]),
              max: new Date(this.seleteDate[1])
            }
          }],
          yAxes: [{
            ticks: {
              min: 0,
              callback: function (value) {
                return value + ' (s)'
              }
            },
            scaleLabel: {
              display: true,
              labelString: this.labels.rpaRes
            }
          }]
        }
      }
    }
  },
  mounted () {
    this.initialize()
  },
  beforeDestroy () {
    this.e2eData = {}
    this.e2eOption = {}
    this.chartColor = []
    this.chartColor2 = []
    this.$nextTick(function () {
      this.e2eChartFlag = false
    })
  }
}
</script>
