<!--
 Class Name  : DashboardRpa.vue
 Description : 모니터 대시보드 > 업무별 응답시간
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.23  이정호  최초 생성
-->
<template>
        <b-row>
      <b-col sm="6" md="9">
        <b-card no-body>
          <div class='card-header head-sm'>
            {{labels.rpaTitle }} ({{labels.second}})
            <div class="card-header-actions" style="font-weight: 600;">
              ＊
            <span class='text-muted mr-3'>{{labels.failMsg}}</span>
            <RpaCanvas v-bind:color="badColor"/>
            <span class='text-muted mr-3'>{{labels.badMsg}}</span>
            <RpaCanvas v-bind:color="delayColor"/>
            <span class='text-muted mr-3'>{{labels.delayMsg}}</span>
            <RpaCanvas v-bind:color="goodColor"/>
            <span class='text-muted mr-3'>{{labels.goodMsg}}</span>
             [ {{dateTime}} ]
            </div>
          </div>
          <b-card-body>
          <bubble-chart v-if="apiFlag" ref="rpaDataList" :style='uStyle' :chart-data="bubbleData" :options="bubbleOption" />
          </b-card-body>
        </b-card>
      </b-col>
      <b-col sm="6" md="3">
        <b-card no-body>
          <div class='card-header head-sm'>
            {{labels.rpaTopTitle }}
            <div class="card-header-actions" style="font-weight: 600;">
             [ {{labels.pdStandard}} ]
            </div>
          </div>
         <b-card-body>
           <horizonal-bar-chart v-if="apiFlag" ref="rpaTopDataList" :style='qStyle' :chart-data="horizontalData" :options="horizontalOption"></horizonal-bar-chart>
         </b-card-body>
        </b-card>
      </b-col>
        </b-row>
</template>

<script>
import BubbleChart from '@/views/monitor/chart/BubbleChart'
import HorizonalBarChart from '@/views/monitor/chart/HorizonalBarChart'
import RpaCanvas from '@/views/monitor/RpaCanvas'
export default {
  name: 'MONITORDASHBOARD',
  components: {
    BubbleChart, HorizonalBarChart, RpaCanvas
  },
  props: ['labels', 'cycle', 'critical', 'warning'],
  data: function () {
    return {
      failColor: '#081c10',
      badColor: '#f86c6b',
      delayColor: '#ffc107',
      goodColor: '#4dbd74',
      bizDt: '',
      rpaInterval: {},
      rpaTopInterval: {},
      uStyle: {height: '270px', width: '100%'},
      qStyle: {height: '270px', width: '100%'},

      apiFlag: false,
      horizontalData: {},
      horizontalOption: {},
      bubbleData: {},
      bubbleOption: {},
      rpaCiList: [],
      sTime: null,
      eTime: null
    }
  },
  computed: {
    dateTime: function () {
      if (!this.$util.isNull(this.bizDt)) {
        return this.$util.dateTime(this.bizDt)
      } else {
        return ''
      }
    }
  },
  mounted () {
    this.getRpaList()
    this.getRpaTopList()
  },
  methods: {
    initialize: function () {
      this.apiFlag = false
      this.rpaInterval = setInterval(this.getRpaList, (this.cycle * 1000))
      this.rpaTopInterval = setInterval(this.getRpaTopList, (this.cycle * 1000))
    },
    rpaTopChartMake: function (data) {
      let newArr = []
      let labelArr = []
      let bizId = []
      data.forEach(r => {
        bizId.push(r.ciId)
        newArr.push(r.linebizresptime)
        labelArr.push(r.biz)
      })

      this.horizontalData = {
        labels: labelArr,
        datasets: [{backgroundColor: [], data: [], bizId: [], labels: []
        }]
      }

      this.horizontalData.datasets[0].data = newArr
      this.horizontalData.datasets[0].bizId = bizId
      this.horizontalData.datasets[0].labels = labelArr
      let $this = this
      this.horizontalData.datasets[0].backgroundColor =
        function (chart) {
          let size = chart.dataset.data[chart.dataIndex]
          if (size === -1) {
            return '#081c10'
          } else if (size > $this.critical) {
            return '#f86c6b'
          } else if (size > $this.warning && size <= $this.critical) {
            return '#ffc107'
          } else {
            return '#4dbd74'
          }
        }
      this.horizontalOption = {
        legend: {
          display: false
        },
        scales: {
          yAxes: [{
            ticks: {
              mirror: false
            }
          }],
          xAxes: [{
            ticks: {
              min: 0
            },
            gridLines: { display: false }
          }]
        },
        responsive: true,
        onClick: this.e2eTop,
        maintainAspectRatio: false
      }
    },
    getRpaTopList () {
      let cTime = this.$util.timestampDate(new Date())
      this.$http.get(
        '/episode/api/monitor/dashboard/rpaTop', {params: {'cTime': cTime}}
      ).then(res => {
        this.rpaTopChartMake(res.data)
        this.apiFlag = true
      }).catch(error => {
        console.log(error)
      })
    },
    getRpaList: function () {
      this.$http.get(
        '/episode/api/monitor/dashboard/rparesp'
      ).then(res => {
        let bizDt = res.data.bizDt !== null ? res.data.bizDt.bizDt : ''
        this.bizDt = bizDt
        this.rpaCiList = res.data.rpa
        this.rpaChartMake(res.data.rpa, res.data.rpaData, res.data.biz, bizDt)
        this.apiFlag = true
      }).catch(error => {
        console.log(error)
      })
    },
    rpaChartMake: function (rpaList, rpaData, bizList, bizDt) {
      let bizDescription = []

      bizList.forEach(row => {
        bizDescription.push(row.ciname)
      })

      bizDescription.unshift('')
      bizDescription.push('')

      let newArray = []
      let locationArray = []
      locationArray.unshift('')
      rpaList.forEach((row, i) => {
        let rowData = {}
        let arr = []
        locationArray.push(row.namekor)

        arr.unshift({})

        rpaData[row.ciId].forEach((row2, i2) => {
          let data = {}
          data.x = i2 + 2
          data.y = i + 2
          if (row2 !== null) {
            data.r = parseInt(row2.timeSize) / 2
            data.rs = row2.linebizresptime
            data.biz = row2.linebizId
            data.time = row2.robotDt
            data.rpaCi = row2.ciId
            data.ciname = bizDescription[i2 + 1]
          } else {
            data.r = 0
            data.rs = 0
            data.biz = bizList[i2].ciId
            data.time = bizDt
            data.rpaCi = row.ciId
            data.ciname = bizDescription[i2 + 1]
          }

          arr.push(data)
        })

        arr.push({})

        let $this = this
        rowData.backgroundColor =
          function (chart) {
            let size = chart.dataset.data[chart.dataIndex].r
            let bizTime = chart.dataset.data[chart.dataIndex].rs
            if (bizTime === -1) {
              return '#081c10'
            } else if (size > $this.critical / 2) {
              return '#f86c6b'
            } else if (size > $this.warning / 2 && size <= $this.critical / 2) {
              return '#ffc107'
            } else {
              return '#4dbd74'
            }
          }
        rowData.borderColor =
          function (chart) {
            let size = chart.dataset.data[chart.dataIndex].r
            let bizTime = chart.dataset.data[chart.dataIndex].rs
            if (bizTime === -1) {
              return '#081c10'
            } else if (size > $this.critical / 2) {
              return '#f86c6b'
            } else if (size > $this.warning / 2 && size <= $this.critical / 2) {
              return '#ffc107'
            } else {
              return '#4dbd74'
            }
          }
        rowData.pointStyle =
          function (chart) {
            let bizTime = chart.dataset.data[chart.dataIndex].rs
            if (bizTime === -1) {
              return 'star'
            } else {
              return 'circle'
            }
          }
        rowData.data = arr
        newArray.push(rowData)
      })
      locationArray.push('')
      this.bubbleData = {
        datasets: newArray
      }
      let caseArr = []
      let locationLength = locationArray.length
      locationArray.forEach((r, i) => {
        caseArr.push(locationLength - i)
      })
      this.bubbleOption = {
        tooltips: {
          callbacks: {
            label: function (t, d) {
              return locationArray[t.datasetIndex + 1] + ', ' + t.xLabel + ', ' + d.datasets[t.datasetIndex].data[t.index].rs + '초'
            }
          }
        },
        legend: {
          display: false
        },
        title: {
          display: true,
          text: ''
        },
        scales: {
          xAxes: [{
            type: 'category',
            labels: bizDescription !== null ? bizDescription : '',
            ticks: {
              min: 1,
              max: bizDescription.length
              // stepSize: 1
            }
          }],
          yAxes: [{
            // barThickness: 1,
            labels: locationArray !== null ? locationArray : '',
            ticks: {
              min: 1,
              max: locationArray.length,
              stepSize: 1,
              callback: function (value, index, values) {
                if ((caseArr[index]) === value) {
                  return locationArray[caseArr[index + 1]]
                }
                // switch (value) {
                //   case 1: return locationArray[0]
                //   case 2: return locationArray[1]
                //   case 3: return locationArray[2]
                //   case 4: return locationArray[3]
                //   case 5: return locationArray[4]
                //   case 6: return locationArray[5]
                //   case 7: return locationArray[6]
                //   case 8: return locationArray[7]
                //   case 9: return locationArray[8]
                //   case 10: return locationArray[9]
                // }
              }
            }
          }]
        },
        responsive: true,
        onClick: this.e2eMove,
        maintainAspectRatio: false}
    },
    e2eMove: function (point, event) {
      if (!this.$util.readAuth(this, 'E2E_READ')) {
        this.$util.esmessage(this, 'warn', this.labels.moveMsg)
        return
      }
      if (event.length === 0) {
        return
      }
      let rowIdx = event[0]._datasetIndex
      let colIdx = event[0]._index
      this.$router.push(
        {
          name: 'E2E',
          params: {rpaData: this.bubbleData.datasets[rowIdx].data[colIdx]}
        })
    },
    e2eTop: function (point, event) {
      if (!this.$util.readAuth(this, 'E2E_READ')) {
        this.$util.esmessage(this, 'warn', this.labels.moveMsg)
        return
      }
      if (event.length === 0) {
        return
      }
      let rowIdx = event[0]._index
      this.$router.push(
        {
          name: 'E2E',
          params: {bizId: this.horizontalData.datasets[0].bizId[rowIdx], bizname: this.horizontalData.datasets[0].labels[rowIdx], rpaCiList: this.rpaCiList}
        })
    }
  },
  created () {
    this.initialize()
  },
  beforeDestroy () {
    this.apiFlag = false
    this.$nextTick(function () {
      clearInterval(this.rpaInterval)
      clearInterval(this.rpaTopInterval)
    })
  }

}
</script>
