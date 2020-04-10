<!--
 Class Name  : DoughnutChart.vue
 Description : 대시보드 > 이벤트 현황 > 도넛 차트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.19  이정호  최초 생성
-->
<script>
import {eventBus} from '@/eventBus/eventBus'
import { Doughnut, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  extends: Doughnut,
  mixins: [reactiveProp],
  props: ['options', 'flag'],
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
        responsive: true,
        maintainAspectRatio: true,
        onClick: this.eventhis,
        tooltips: {
          enabled: this.flag
        }
      }
    )
  },
  methods: {
    eventhis: function (point, event) {
      if (this.chartData.datasets[0].data[0] !== 999 && event.length !== 0) {
        if (this.chartData.id === 'biz') {
          eventBus.$emit('bizEventListOpen', '', event[0]._chart.data.ciId[event[0]._index], this.$parent.$options.name)
          this.$parent.getEventDataBizCi(event[0]._chart.data.ciId[event[0]._index])
        } else if (this.chartData.id !== 'DELAY') {
          eventBus.$emit('systemEventListOpen', this.chartData.id, event[0]._view.label, this.$parent.$options.name)
        } else {
          eventBus.$emit('systemEventListOpen', '', event[0]._view.label, this.$parent.$options.name)
        }
      }
    }
  }
}
</script>
