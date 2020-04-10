<!--
 Class Name  : BizBarChart.vue
 Description : 대시보드 > 업무 현황 > 바 차트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.19  이정호  최초 생성
-->
<script>
import {eventBus} from '@/eventBus/eventBus'
import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  extends: Bar,
  mixins: [reactiveProp],
  props: ['options', 'bizBarType', 'bizEventCi'],
  watch: {
    chartData () {
      this.$data._chart.update()
    }
  },
  mounted () {
    this.renderChart(
      this.chartData,
      {
        legend: {
          display: false
        },
        title: {
          display: true,
          text: ''
        },
        scales: {
          yAxes: [{
            position: 'left',
            ticks: {
              beginAtZero: true
            }
          }]
        },
        responsive: true,
        maintainAspectRatio: false,
        onClick: this.eventhis
      }
    )
  },
  methods: {
    eventhis: function (point, event) {
      if (this.bizBarType && event.length !== 0) {
        eventBus.$emit('bizEventListOpen', event[0]._view.label, this.bizEventCi, this.$parent.$options.name)
      }
    }
  }
}
</script>
