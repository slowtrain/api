<!--
 Class Name  : BubbleChart.vue
 Description : 대시보드 > 업무별 응답시간 > 버블 차트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.19  이정호  최초 생성
-->
<script>
import { Bubble, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  extends: Bubble,
  mixins: [reactiveProp],
  props: ['options'],
  data () {
    return {
    }
  },
  mounted () {
    this.renderChart(
      this.chartData,
      this.options
    )
  },
  watch: {
    options () {
      this.$data._chart.update()
      this.renderChart(this.chartData, this.options)
    }
  }
}
</script>
