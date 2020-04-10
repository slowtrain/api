<!--
 Class Name  : DashboardAsset.vue
 Description : IT Infra 현황 > 대시보드
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.15  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
      <b-row>
        <b-col sm="12">
        <InfraStatus
          v-bind:labels="labels"
          ref="infreStatus"
         />
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="5">
        <LicenseStatus
          v-bind:labels="labels"
          ref="licenseStatus"
         />
        </b-col>
        <b-col sm="7">
        <ScanCI
          v-bind:labels="labels"
          ref="scanCi"
         />
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="12">
        <JobStatus
          v-bind:labels="labels"
          ref="JobStatus"
         />
        </b-col>
      </b-row>
  </div>
</template>

<script>
import InfraStatus from '@/views/asset/InfraStatus'
import LicenseStatus from '@/views/asset/LicenseStatus'
import JobStatus from '@/views/asset/JobStatus'
import ScanCI from '@/views/asset/ScanCI'
export default {
  name: 'ASSETDASHBOARD',
  components: {
    'InfraStatus': InfraStatus,
    'LicenseStatus': LicenseStatus,
    'JobStatus': JobStatus,
    'ScanCI': ScanCI
  },
  data: function () {
    return {
      labels: {
        server: '',
        network: '',
        busi: '',
        app: '',
        software: '',
        company: '',
        swlictype: '',
        infraStatus: '',
        licenseStatus: '',
        jobStatus: '',
        purchasecost: '',
        purchasequantity: '',
        ea: '',
        mgUndecided: '',
        mgConfirm: '',
        swUnlinked: '',
        swLink: '',
        scanCi: '',
        unidentified: '',
        needMsg: ''
      }
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
    }
  },
  created () {
    this.initialize()
  }
}
</script>
