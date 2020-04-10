<script>
import Chart from 'chart.js'
import {mixins, generateChart} from 'vue-chartjs'

const { reactiveProp } = mixins

Chart.defaults.LineWithLine = Chart.defaults.line
Chart.controllers.LineWithLine = Chart.controllers.line.extend({
  draw: function (ease) {
    Chart.controllers.line.prototype.draw.call(this, ease)
    if (this.chart.tooltip._active && this.chart.tooltip._active.length) {
      let activePoint = this.chart.tooltip._active[0]
      let ctx = this.chart.ctx
      let x = activePoint.tooltipPosition().x
      let topY = this.chart.scales['y-axis-0'].top
      let bottomY = this.chart.scales['y-axis-0'].bottom
      ctx.save()
      ctx.beginPath()
      ctx.moveTo(x, topY)
      ctx.lineTo(x, bottomY)
      ctx.lineWidth = 1
      ctx.strokeStyle = '#07C'
      ctx.stroke()
      ctx.restore()
    }
  }
})

const CustomLine = generateChart('custom-line', 'LineWithLine')

export default {
  extends: CustomLine,
  mixins: [reactiveProp],
  props: ['options'],
  data () {
    return {
    }
  },
  mounted () {
    this.renderChart(this.chartData, this.options)
  },
  watch: {
    options () {
      this.$data._chart.update()
      this.renderChart(this.chartData, this.options)
    }
  }
}
</script>
