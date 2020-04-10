<!--
 Class Name  : BarChart.vue
 Description : 대시보드 > 이벤트 현황 > 바 차트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.19  이정호  최초 생성
-->
<script>
import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  extends: Bar,
  mixins: [reactiveProp],
  props: ['options', 'labels'],
  // watch: {
  //   chartData () {
  //     this.$data._chart.update()
  //   }
  // },
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
      if (!this.$util.readAuth(this, 'EVENT_READ')) {
        this.$util.esmessage(this, 'warn', this.labels.moveMsg)
        return
      }
      let seleteDate = null
      const month = new Date(new Date().getFullYear(), new Date().getMonth(), 1)
      if (event[0]._index === 1) {
        this.chartData.id === 'DELAY' ? seleteDate = [new Date(new Date().getFullYear(), new Date().getMonth(), 1), new Date(new Date().setDate(new Date().getDate() - 1))]
          : seleteDate = [new Date(new Date().setDate(new Date().getDate() - 1)), new Date(new Date().setDate(new Date().getDate() - 1))]
      } else if (event[0]._index === 2) {
        this.chartData.id === 'DELAY' ? seleteDate = [new Date(new Date().setDate(new Date().getDate() - 1)), new Date(new Date().setDate(new Date().getDate() - 1))]
        // this.chartData.id === 'DELAY' ? seleteDate = [new Date(new Date().getFullYear(), new Date().getMonth() , 1), new Date(new Date().setDate(new Date().getDate() - 1))]
          : seleteDate = [new Date(), new Date()]
      } else {
        seleteDate = [new Date(new Date().getFullYear(), new Date().getMonth() - 1, 1), new Date(month.setDate(month.getDate() - 1))]
      }
      this.$router.push(
        {
          name: 'EVENT',
          params: {ciType: this.chartData.id === 'DELAY' ? '' : this.chartData.id, date: seleteDate}
        })
    }
  }
}
</script>
