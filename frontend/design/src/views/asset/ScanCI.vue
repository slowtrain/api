<!--
 Class Name  : ScanCI.vue
 Description : IT Infra 현황 > 대시보드 > 스캔 현황
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
            {{labels.scanCi}}
          </div>
         <b-card-body>
           <b-row>
             <b-col>
              <p class="scan-line"></p>
             </b-col>
           </b-row>
      <b-row>
        <b-col sm="4">
           <ScanBox
             v-bind:data="ciData.SERVER"
             v-bind:labels="labels"
           />
         </b-col>
         <b-col sm="4">
          <ScanBox
             v-bind:data="ciData.SW_DBSW"
             v-bind:labels="labels"
           />
         </b-col>
        <b-col sm="4">
           <ScanBox
             v-bind:data="ciData.SW_MIDDLESW"
             v-bind:labels="labels"
            />
        </b-col>
      </b-row>
         </b-card-body>
    </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import ScanBox from '@/views/asset/ScanBox'
export default {
  props: ['labels'],
  name: 'ASSETDASHBOARD',
  components: {
    ScanBox
  },
  data: function () {
    return {
      ciList: ['SERVER', 'SW_DBSW', 'SW_MIDDLESW'],
      ciData: {
        SERVER: {title: '', cnt: 0, key: '', id: ''},
        SW_DBSW: {title: '', cnt: 0, key: '', id: ''},
        SW_MIDDLESW: {title: '', cnt: 0, key: '', id: ''}
      },
      apiFlag: false
    }
  },
  methods: {
    initialize: function () {
      this.getScanCI()
    },
    getScanCI: function () {
      this.apiFlag = false
      this.$http.get('/episode/api/asset/dashboard/scanci'
      ).then(res => {
        this.ciList.forEach(type => {
          this.ciData[type].title = res.data[type].title
          this.ciData[type].cnt = res.data[type].cnt
          this.ciData[type].key = type
          this.ciData[type].id = res.data[type].id
        })
      }).catch(error => {
        console.log(error)
      })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
