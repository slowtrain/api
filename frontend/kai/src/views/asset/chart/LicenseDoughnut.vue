<script>
import { Doughnut, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  extends: Doughnut,
  mixins: [reactiveProp],
  props: ['options', 'chartName', 'count', 'flag', 'labels'],
  mounted () {
    this.renderChart(this.chartData,
      {
        elements: {
          center: {
            text: this.chartName
          }
        },
        legend: {
          display: false
        },
        title: {
          display: true,
          text: this.chartName + ' [' + this.count + ']'
        },
        responsive: true,
        maintainAspectRatio: false,
        onClick: this.moveLicense,
        tooltips: {
          enabled: this.flag
        }
      }
      // this.textCenter(this.chartName)
    )
  },
  methods: {
    moveLicense: function (point, event) {
      if (!this.$util.readAuth(this, 'LICENSE_READ')) {
        this.$util.esmessage(this, 'warn', this.labels.moveMsg)
        return
      }
      if (this.chartData.datasets[0].data[0] !== 999 && event.length !== 0) {
        let chartData = event[0]._chart.data
        if (chartData.id === 'SWLICTYPE') {
          let swlictype = chartData.swlictypes[event[0]._index]
          let swlicname = chartData.labels[event[0]._index]
          let param = {ls: chartData.id, key: ''}
          if (this.$util.isNull(swlictype)) {
            swlictype = 'etc'
            param.key = swlictype
          } else {
            param.key = swlicname
          }
          this.$router.push(
            {
              name: 'LICENSE',
              params: param
            })
        } else {
          let company = chartData.companytype[event[0]._index]
          if (this.$util.isNull(company)) {
            company = 'etc'
          }
          this.$router.push(
            {
              name: 'LICENSE',
              params: {ls: chartData.id, key: company}
            })
        }
      }
    }
  }
}
</script>
