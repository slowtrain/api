<!--
 Class Name  : HorizonalBarChart.vue
 Description : 대시보드 > 업무별 응답시간 > 가로 바 차트 && 장비별 대시보드 > 가로 바 차트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.19  이정호  최초 생성
-->
<script>
import { HorizontalBar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  extends: HorizontalBar,
  mixins: [reactiveProp],
  props: ['options'],
  mounted () {
    // Overwriting base render method with actual data.
    this.renderChart(
      this.chartData, this.options
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
