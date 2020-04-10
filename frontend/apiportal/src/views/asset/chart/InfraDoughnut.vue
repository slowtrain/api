<script>
import Chart from 'chart.js'
// import {mixins, Doughnut} from 'vue-chartjs'
import {mixins, generateChart} from 'vue-chartjs'
const { reactiveProp } = mixins

Chart.defaults.derivedDoughnut = Chart.defaults.doughnut
// let customDounghnut = Chart.controllers.doughnut.extend({
Chart.controllers.derivedDoughnut = Chart.controllers.doughnut.extend({
  draw: function (ease) {
    Chart.controllers.doughnut.prototype.draw.apply(this, [ease])
    let ctx = this.chart.ctx

    let text = this.chart.data.test

    let centerConfig = this.chart.config.options.elements.center
    let fontStyle = centerConfig.fontStyle || 'Arial'
    let sidePadding = centerConfig.sidePadding || 20
    let sidePaddingCalculated = (sidePadding / 100) * (this.chart.innerRadius * 2)

    let stringWidth = ctx.measureText(text).width
    let elementWidth = (this.chart.innerRadius * 2) - sidePaddingCalculated

    let widthRatio = elementWidth / stringWidth
    let newFontSize = Math.floor(30 * widthRatio)
    let elementHeight = (this.chart.innerRadius * 0.6)

    let fontSizeToUse = Math.min(newFontSize, elementHeight)
    ctx.textAlign = 'center'
    ctx.textBaseline = 'middle'
    let centerX = ((this.chart.chartArea.left + this.chart.chartArea.right) / 2)
    let centerY = ((this.chart.chartArea.top + this.chart.chartArea.bottom) / 2)
    ctx.font = fontSizeToUse + 'px ' + fontStyle
    ctx.fillText(text, centerX, centerY)
  }
})

const CustomLine = generateChart('custom-doughnut', 'derivedDoughnut')
export default {
  extends: CustomLine,
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
        maintainAspectRatio: true,
        onClick: this.moveCI,
        tooltips: {
          enabled: this.flag
        }
      }
      // this.textCenter(this.chartName)
    )
  },
  methods: {
    moveCI: function (point, event) {
      if (!this.$util.readAuth(this, 'CI_READ')) {
        this.$util.esmessage(this, 'warn', this.labels.moveMsg)
        return
      }
      if (this.chartData.datasets[0].data[0] !== 999 && event.length !== 0) {
        let id = event[0]._chart.data.id
        let ancestor = event[0]._chart.data.ancestors[event[0]._index]
        let ancestorId = event[0]._chart.data.ancestorId[event[0]._index]
        let infraType = 'normal'
        if (id === ancestor) {
          infraType = 'selected'
        }
        this.$router.push(
          {
            name: 'CI',
            params: {cl: ancestor, infraType: infraType, ancestorId: ancestorId}
          })
      }
    }
  }
}
</script>
