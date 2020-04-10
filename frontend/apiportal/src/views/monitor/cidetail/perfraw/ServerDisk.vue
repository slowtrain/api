<!--
 Class Name  : ServerDisk.vue
 Description : 장비별 대시보드 > 서버 디스크 성능 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.02  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-row>
      <b-col>
           <b-row>
           <horizonal-bar-chart ref="disk" v-if='diskFlag' :style='dStyle' :chart-data="diskData" :options="diskOption"></horizonal-bar-chart>
          </b-row>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import HorizonalBarChart from '@/views/monitor/chart/HorizonalBarChart'

export default {
  name: 'PERFRAW',
  props: ['ciData', 'system', 'cycle', 'datePref', 'labels'],
  components: {
    HorizonalBarChart
  },
  data: function () {
    return {
      dStyle: {height: '100%', width: '100%'},
      diskType: 'DISK_USERATE',
      diskFlag: false,
      diskData: {},
      diskOption: {},
      sDiskInterval: {}
    }
  },
  watch: {
    ciData: function () {
      if (this.$util.isNull(this.ciData.ciId)) {
        return
      }
      clearInterval(this.sDiskInterval)
      if (this.system === 'SERVER') this.initialize()
    }
  },
  methods: {
    initialize: function () {
      this.getEms()
      this.sDiskInterval = setInterval(this.getEmsAdd, (this.cycle * 1000))
    },
    getEms: function () {
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      let params = {params: {
        ciId: this.ciData.ciId,
        rscType: this.diskType,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/monitor/detail/emsdisk',
        params
      ).then(res => {
        this.diskChartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    getEmsAdd: function () {
      let cTime = this.$util.perfTimestampDate(new Date(new Date().setMinutes(new Date().getMinutes() - this.datePref)))
      let params = {params: {
        ciId: this.ciData.ciId,
        rscType: this.diskType,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/monitor/detail/emsdisk',
        params
      ).then(res => {
        this.diskData.datasets[0].data.forEach((r, i) => {
          if (r !== res.data[i]) r = res.data[i].rscvalDouble
        })
        this.$refs.disk.$data._chart.update()
      }).catch(error => {
        console.log(error)
      })
    },
    diskChartMake: function (data) {
      this.diskFlag = true
      let newArr = []
      let labelArr = []
      data.forEach(r => {
        newArr.push(r.rscvalDouble)
        labelArr.push(r.rscKeyName)
      })
      this.diskData = {
        labels: labelArr,
        datasets: [
          {
            backgroundColor: '#58A2F7',
            // backgroundColor: 'rgba(21, 12, 119, 0.2)',
            data: newArr
          }
        ]
      }
      this.diskOption = {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        scales: {
          yAxes: [{
            gridLines: { display: true, offsetGridLines: true },
            scaleLabel: {
              display: true,
              labelString: this.labels.diskUse
              // fontColor: "green"
            }
          }],
          xAxes: [{
            ticks: {
              min: 0,
              max: 100,
              stepSize: 20,
              callback: function (value) {
                return value + ' (%)'
              }
            },
            gridLines: { display: false }
          }]
        }
      }
    }
  },
  mounted () {
    // this.initialize()
  },
  beforeDestroy () {
    this.diskOption = {}
    this.diskData = {}
    this.$nextTick(function () {
      this.diskFlag = false
      clearInterval(this.sDiskInterval)
    })
  }
}
</script>
