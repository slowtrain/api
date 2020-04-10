<!--
 Class Name  : LicenseStatus.vue
 Description : IT Infra 현황 > 대시보드 > 라이선스 현황
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
            {{labels.licenseStatus}}
          </div>
         <b-card-body>
      <b-row>
        <b-col sm="6">
          <div :style='cStyle'>
         <license-doughnut :style='eStyle' v-if='apiFlag' :chart-data="companyData" :flag="companyFlag" :count="companyCount" :chartName="labels.company" :labels="labels" />
          </div>
        </b-col>
        <b-col sm="6">
          <div :style='cStyle'>
          <license-doughnut :style='eStyle' v-if='apiFlag' :chart-data="swlictypeData" :flag="swlictypeFlag" :count="swlictypeCount" :chartName="labels.swlictype" :labels="labels" />
          </div>
        </b-col>
        </b-row>
         </b-card-body>
    </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import LicenseDoughnut from '@/views/asset/chart/LicenseDoughnut'
export default {
  props: ['labels'],
  name: 'ASSETDASHBOARD',
  components: {
    LicenseDoughnut
  },
  data: function () {
    return {
      companyCount: 0,
      companyData: {},
      companyFlag: {},
      swlictypeCount: 0,
      swlictypeData: {},
      swlictypeFlag: {},
      chartColor: ['#ffbc58', '#abdb8d', '4e69a2', '#112358', '#5772ff', '#bb1bc9'],
      apiFlag: false,
      cStyle: {height: '150px', width: '61%', margin: 'auto'},
      eStyle: {height: '100%', width: '100%'}
    }
  },
  methods: {
    initialize: function () {
      this.getLicenseStatus()
    },
    getLicenseStatus: function () {
      this.apiFlag = false
      this.$http.get('/episode/api/asset/dashboard/license'
      ).then(res => {
        this.apiFlag = true
        this.chartMake(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    chartMake: function (data) {
      this.companyChart(data.company)
      this.swlictypeChart(data.swlictype)
    },
    companyChart: function (data) {
      let arr = []
      let label = []
      let companytype = []
      if (data.length !== 0) {
        if (data.length > this.chartColor.length) {
          this.$util.getColor(data.length - this.chartColor.length, this.chartColor)
        }
        data.forEach(r => {
          this.companyCount += r.cnt
          arr.push(r.cnt)
          label.push(r.company)
          companytype.push(r.companytype)
          this.companyFlag = true
        })
      } else {
        arr = [999]
        this.companyFlag = false
      }
      this.companyData = {
        id: 'COMPANY',
        labels: label,
        companytype: companytype,
        datasets: [{
          backgroundColor: this.companyFlag ? this.chartColor : '#f2f3f5',
          data: arr
        }]
      }
    },
    swlictypeChart: function (data) {
      let arr = []
      let label = []
      let swlictype = []
      if (data.length !== 0) {
        if (data.length > this.chartColor.length) {
          this.$util.getColor(data.length - this.chartColor.length, this.chartColor)
        }
        data.forEach(r => {
          this.swlictypeCount += r.cnt
          arr.push(r.cnt)
          label.push(r.swlictypeName)
          swlictype.push(r.swlictype)
          this.swlictypeFlag = true
        })
      } else {
        arr = [999]
        this.swlictypeFlag = false
      }
      this.swlictypeData = {
        id: 'SWLICTYPE',
        labels: label,
        swlictypes: swlictype,
        datasets: [{
          backgroundColor: this.swlictypeFlag ? this.chartColor : '#f2f3f5',
          data: arr
        }]
      }
    }
  },
  created () {
    this.initialize()
  },
  beforeDestroy () {
    this.companyData = {}
    this.swlictypeData = {}
    this.apiFlag = false
    this.$nextTick(function () {
    })
  }
}
</script>
