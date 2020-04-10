<!--
 Class Name  : ServiceAppChart.vue
 Description : 서비스  > 어플리케이션 장애 다운 시간 > 라인차트 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.25  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-row>
      <b-col sm="12">
              <line-chart v-if='appChartFlag' :chart-data="appData" :options="appOption" :style='qStyle'/>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import LineChart from '@/views/asset/chart/LineChart'

export default {
  name: 'SERVICEAPPCHART',
  props: ['app', 'seleteDate', 'labels'],
  components: {
    LineChart
  },
  data: function () {
    return {
      qStyle: {height: '250px', width: '100%'},
      appChartFlag: false,
      appData: {},
      appOption: {},
      chartColor: [],
      chartColor2: []
    }
  },
  watch: {
  },
  methods: {
    initialize: function () {
      if (this.$util.isNull(this.app)) {
        return
      }
      this.getE2EData(this.app)
    },
    getE2EData: function (app) {
      this.appChartFlag = false
      this.appData = {}
      let startDate = this.$util.timestampDate(this.seleteDate[0])
      let endDate = this.$util.timestampDate(this.seleteDate[1])
      this.$http.get(
        '/episode/api/asset/servicemgmnt/serviceapp/appdata?app=' + app + '&startDate=' + startDate + '&endDate=' + endDate
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
      this.appChartFlag = true
      if (data.length !== 0 && !this.$util.isNull(data[0])) {
        this.$util.getRgba(data, this.chartColor, this.chartColor2)
        const time = data[0].collDt
        const cDate = []
        time.forEach(row => {
          let dateType = row.substring(0, row.indexOf('.'))
          let dateValue = new Date(dateType)
          cDate.push(new Date(dateValue))
        })
        this.appData.labels = cDate
        this.appData.datasets = []
        data.forEach((r, i) => {
          const jsonData = r.totalTime
          const chartData = {}
          chartData.pointRadius = 0
          chartData.label = r.ciId
          chartData.borderColor = this.chartColor[i]
          chartData.borderWidth = 1.5
          chartData.backgroundColor = this.chartColor2[i]
          chartData.data = jsonData
          this.appData.datasets.push(chartData)
        })
      }

      this.appOption = {
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
            // ticks: {
            //   callback: function (value) {
            //     return new Date(value).toLocaleString(lang, {month: 'long', day: 'numeric'})
            //   }
            // },
            time: {
              unit: 'day',
              min: new Date(this.$util.getDateType(this.seleteDate[0])),
              max: new Date(this.$util.getDateType(this.seleteDate[1]))
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
              labelString: this.labels.totalTime
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
    this.appData = {}
    this.appOption = {}
    this.chartColor = []
    this.chartColor2 = []
    this.$nextTick(function () {
      this.appChartFlag = false
    })
  }
}
</script>
