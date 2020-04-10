<!--
 Class Name  : JobStatus.vue
 Description : IT Infra 현황 > 대시보드 > 작업 현황
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
          <div class='card-header head-sm'>{{labels.jobStatus}}</div>
            <b-card-body>
              <b-row>
                <b-col sm="5">
                  <b-card class="sm">
                    <b-row>
                      <b-col>
                      {{labels.ci}}
                        <p class="job-line"></p>
                      </b-col>
                    </b-row>
                  <b-row>
                  <b-col sm="4">
                    <JobBox
                      v-bind:data="ciData.mgConfirmData"
                      v-bind:labels="labels"
                    />
                  </b-col>
                  <b-col sm="4">
                   <JobBox
                      v-bind:data="ciData.swLinkData"
                      v-bind:labels="labels"
                    />
                  </b-col>
                 <b-col sm="4">
                    <JobBox
                      v-bind:data="ciData.swcatData"
                      v-bind:labels="labels"
                     />
                 </b-col>
                  </b-row>
                 </b-card>
               </b-col>
               <b-col sm="7">
                  <b-card class="sm">
                    <b-row>
                      <b-col>
                      {{labels.ems}}
                        <p class="job-line"></p>
                      </b-col>
                    </b-row>
                  <b-row>
                  <b-col sm="3">
                    <JobBox
                      v-bind:data="emsData.ComputerSystem"
                      v-bind:labels="labels"
                    />
                  </b-col>
                  <b-col sm="3">
                    <JobBox
                      v-bind:data="emsData.NetDevice"
                      v-bind:labels="labels"
                    />
                  </b-col>
                  <b-col sm="3">
                    <JobBox
                      v-bind:data="emsData.WebApplicationServer"
                      v-bind:labels="labels"
                    />
                  </b-col>
                  <b-col sm="3">
                    <JobBox
                      v-bind:data="emsData.DatabaseServer"
                      v-bind:labels="labels"
                    />
                  </b-col>
                  </b-row>
                 </b-card>
               </b-col>
             </b-row>
           </b-card-body>
         </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import JobBox from '@/views/asset/JobBox'
export default {
  props: ['labels'],
  name: 'ASSETDASHBOARD',
  components: {
    JobBox
  },
  data: function () {
    return {
      emsList: ['ComputerSystem', 'NetDevice', 'WebApplicationServer', 'DatabaseServer'],
      emsData: {
        ComputerSystem: {cf: '', title: '', confirm: 0, undecided: 0, key: ''},
        NetDevice: {cf: '', title: '', confirm: 0, undecided: 0, key: ''},
        WebApplicationServer: {cf: '', title: '', confirm: 0, undecided: 0, key: ''},
        DatabaseServer: {cf: '', title: '', confirm: 0, undecided: 0, key: ''}
      },
      ciList: ['mgConfirmData', 'swLinkData', 'swcatData'],
      ciData: {
        mgConfirmData: {cf: '', title: '', confirm: 0, undecided: 0, key: ''},
        swLinkData: {cf: '', title: '', confirm: 0, undecided: 0, key: ''},
        swcatData: {cf: '', title: '', confirm: 0, undecided: 0, key: ''}
      },
      chartColor: ['#41B883', '#E46651', '#00D8FF', '#DD1B16'],
      emsColor: ['#DD1B16', '#41B883'],
      apiFlag: false,
      cStyle: {height: '85%', width: '85%'},
      dStyle: {height: '80%', width: '80%'}
    }
  },
  methods: {
    initialize: function () {
      this.getJobCI()
      this.getJobEms()
    },
    getJobCI: function () {
      this.apiFlag = false
      this.$http.get('/episode/api/asset/dashboard/jobci'
      ).then(res => {
        this.ciList.forEach(type => {
          this.ciData[type].title = res.data[type].title
          this.ciData[type].confirm = res.data[type].confirm
          this.ciData[type].undecided = res.data[type].undecided
          this.ciData[type].cf = res.data[type].cf
          this.ciData[type].key = type
        })
      }).catch(error => {
        console.log(error)
      })
    },
    getJobEms: function () {
      this.$http.get('/episode/api/asset/dashboard/jobems'
      ).then(res => {
        this.emsList.forEach(type => {
          this.emsData[type].title = res.data[type].title
          this.emsData[type].confirm = res.data[type].confirm
          this.emsData[type].undecided = res.data[type].undecided
          this.emsData[type].cf = res.data[type].cf
          this.emsData[type].key = type
        })
      }).catch(error => {
        console.log(error)
      })
    },
    emsChart: function (data) {
      this.emsList.forEach(r => {
        this.emsChartMake(r, data[r])
      })
    },
    emsChartMake: function (type, emsData) {
      let pArray = []
      let pLabel = []
      emsData.forEach(r => {
        this.emsCount[type] += r.cnt
        pArray.push(r.cnt)
        pLabel.push(r.ems)
      })
      this.emsData[type] = {
        id: type,
        labels: pLabel,
        datasets: [{
          backgroundColor: this.emsColor,
          data: pArray
        }]
      }
      this.emsFlag[type] = true
    }
  },
  created () {
    this.initialize()
  },
  beforeDestroy () {
    this.emsList = []
    this.emsData.ComputerSystem = {}
    this.emsData.NetDevice = {}
    this.emsData.WebApplicationServer = {}
    this.emsData.DatabaseServer = {}
    this.ciList = []
    this.ciData.mgConfirmData = {}
    this.ciData.swLinkData = {}
    this.ciData.swcatData = {}
    this.chartColor = []
    this.emsColor = []
  }
}
</script>
