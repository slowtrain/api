<!--
 Class Name  : InfraStatus.vue
 Description : IT Infra 현황 > 대시보드 > IT Infra 현황
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.15  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-row>
      <b-col sm="12">
     <b-card no-body>
           <div class='card-header head-sm'>
            {{labels.infraStatus }}
          </div>
         <b-card-body>
      <b-row>
          <b-col sm="1">
        </b-col>
        <b-col sm="2">
          <b-row>
            <b-col >
              <div :style='cStyle'>
            <infra-doughnut v-if='apiFlag' :chart-data="ciSystemData.GROUP_1" :flag="ciSystemFlag.GROUP_1" :count="ciSystemCnt.GROUP_1" :chartName="labels.app" :labels="labels" />
              </div>
            </b-col>
          </b-row>
          <b-row>
          </b-row>
        </b-col>
        <b-col sm="2">
          <b-row>
            <b-col>
              <div :style='cStyle'>
            <infra-doughnut v-if='apiFlag' :chart-data="ciSystemData.SOFTWARE" :flag="ciSystemFlag.SOFTWARE" :count="ciSystemCnt.SOFTWARE" :chartName="labels.software" :labels="labels"/>
            </div>
            </b-col>
          </b-row>
        </b-col>
        <b-col sm="2">
          <b-row>
           <b-col>
              <div :style='cStyle'>
            <infra-doughnut v-if='apiFlag' :chart-data="ciSystemData.NETWORK" :flag="ciSystemFlag.NETWORK" :count="ciSystemCnt.NETWORK" :chartName="labels.network" :labels="labels" />
            </div>
           </b-col>
          </b-row>
         </b-col>
        <b-col sm="2">
          <b-row>
            <b-col>
              <div :style='cStyle'>
            <infra-doughnut v-if='apiFlag' :chart-data="ciSystemData.SERVER" :flag="ciSystemFlag.SERVER" :count="ciSystemCnt.SERVER" :chartName="labels.server" :labels="labels" />
            </div>
            </b-col>
          </b-row>
        </b-col>
         <b-col sm="2">
          <b-row>
            <b-col>
              <div :style='cStyle'>
            <infra-doughnut v-if='apiFlag' :chart-data="ciSystemData.BUSINESSSYSTEM" :flag="ciSystemFlag.BUSINESSSYSTEM" :count="ciSystemCnt.BUSINESSSYSTEM" :chartName="labels.busi" :labels="labels" />
            </div>
            </b-col>
          </b-row>
           </b-col>
            <b-col sm="1">
        </b-col>
       </b-row>
         </b-card-body>
    </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import InfraDoughnut from '@/views/asset/chart/InfraDoughnut'
export default {
  props: ['labels'],
  name: 'ASSETDASHBOARD',
  components: {
    InfraDoughnut
  },
  data: function () {
    return {
      chartColor: ['#ffa883', '#fcb154', '#db6a4b', '#ffd731', '#c59e4f', '#bada55'],
      ciSystem: ['GROUP_1', 'SOFTWARE', 'NETWORK', 'SERVER', 'BUSINESSSYSTEM'],
      ciSystemCnt: {GROUP_1: 0, SOFTWARE: 0, NETWORK: 0, SERVER: 0, BUSINESSSYSTEM: 0},
      ciSystemData: {GROUP_1: {}, SOFTWARE: {}, NETWORK: {}, SERVER: {}, BUSINESSSYSTEM: {}},
      ciSystemFlag: {GROUP_1: false, SOFTWARE: false, NETWORK: false, SERVER: false, BUSINESSSYSTEM: false},
      systemCnt: {},
      apiFlag: false,

      cStyle: {height: '75%', width: '75%', margin: 'auto'}
    }
  },
  methods: {
    initialize: function () {
      this.getInfreStatus()
    },
    getInfreStatus: function () {
      let cTime = this.$util.timestampDate(new Date(new Date().setDate(new Date().getDate() - 7)))
      this.apiFlag = false
      this.$http.get('/episode/api/asset/dashboard/itinfra', {params: {'cTime': cTime}}
      ).then(res => {
        this.apiFlag = true
        this.systemCnt = res.data
        this.chartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    cntClass (key) {
      if (key > 0) {
        return '▲ ' + key
      } else if (key === 0) {
        return '- ' + key
      } else {
        return '▼ ' + Math.abs(key)
      }
    },
    chartMake: function (data) {
      this.ciSystem.forEach(key => {
        let ci = data[key]
        let dArray = []
        let dLabel = []
        let ancestor = []
        let ancestorId = []
        if (ci[0].length !== 0) {
          if (ci[0].length > this.chartColor.length) {
            this.$util.getColor(ci[0].length - this.chartColor.length, this.chartColor)
          }
          ci[0].forEach(r => {
            this.ciSystemCnt[key] += r.count
            dArray.push(r.count)
            dLabel.push(r.ancestorName)
            ancestor.push(r.ancestor)
            ancestorId.push(r.id)
            this.ciSystemFlag[key] = true
          })
        } else {
          dArray = [999]
          this.ciSystemFlag[key] = false
        }

        this.ciSystemData[key] = {
          id: key,
          labels: dLabel,
          test: this.cntClass(data[key + '_total']),
          ancestors: ancestor,
          ancestorId: ancestorId,
          datasets: [{
            backgroundColor: this.ciSystemFlag[key] ? this.chartColor : '#f2f3f5',
            data: dArray
          }]
        }
      })
    }
  },
  created () {
    this.initialize()
  },
  beforeDestroy () {
    this.ciSystemData.GROUP_1 = {}
    this.ciSystemData.SOFTWARE = {}
    this.ciSystemData.NETWORK = {}
    this.ciSystemData.SERVER = {}
    this.ciSystemData.BUSINESSSYSTEM = {}
    this.$nextTick(function () {
      this.apiFlag = false
    })
  }
}
</script>
