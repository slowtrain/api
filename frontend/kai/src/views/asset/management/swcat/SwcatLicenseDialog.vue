<!--
 Class Name  : SwcatLicenseDialog.vue
 Description : 소프트웨어 카탈로그 > 관련 라이선스 추가 Dialog
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.14  최영훈  최초 생성
-->
<template>
  <el-dialog :title="labels.licenseDialogTitle" :visible.sync="showDialogFlag" width=1000px  v-on:close="close">
    <b-row>
      <div class="form-group col-md-3">
        <label for="swlicenseId">{{labels.swlicenseId}}</label>
        <div class="search-component sm">
          <input id="swlicenseId" v-model="data.swlicenseId" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a slot="reference" class="action-button cursor-pointer" v-on:click="moveLicense">search</a>
        </div>
      </div>
      <div class="form-group col-md-3">
        <label for="swlicensename">{{labels.name}}<span class="require-mark">*</span></label>
        <input id="swlicensename" v-model="data.swlicname" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-6">
        <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
        <input id="description" v-model="data.swlicdescription" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-3">
        <label for="swlictype">{{labels.licensetype}}</label>
        <input id="swlictype" v-model="data.swlictypeNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-3">
        <label for="purchasetype">{{labels.purchasetype}}</label>
        <input id="swlicstatus" v-model="data.purchasetype" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-3">
        <label for="status">{{labels.status}}</label>
        <input id="swlicstatus" v-model="data.swlicstatus" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-3">
        <label for="orgId">{{labels.orgId}}</label>
        <input id="orgId" v-model="data.orgId" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-3">
        <label for="managerNm">{{labels.manager}}</label>
        <input id="managerNm" v-model="data.managerNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-3">
        <label for="companyId">{{labels.company}}</label>
        <input id="companyId" v-model="data.companyId" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class='form-group col-md-3'>
        <label>{{labels.updateDt}}</label>
        <input id="updateDt" :value="$util.dateTimeWithTimeZone(data.updateDt)" class="formdata sm" disabled="disabled"/>
      </div>
      <div class='form-group col-md-3'>
        <label>{{labels.updateNm}}</label>
        <input id="updateDt" :value="data.updateNm" class="formdata sm" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="col mt-2">
        <div class='d-flex justify-content-end'>
          <b-button variant="primary" class="px-3 sm" v-on:click="close">{{labels.close}}</b-button>
        </div>
      </div>
    </b-row>
  </el-dialog>
</template>
<script>
export default {
  name: 'LICENSE',
  props: ['showDialogFlagProp', 'param'],
  data () {
    return {
      labels: {
        ciId: ''
      },
      data: {},
      // Global Var
      orgId: '',
      searchType: 'NORMAL',
      searchParam: '',
      showDialogFlag: false
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      this.getRowData(this.param.id)
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.orgId = this.$store.getters.getUser.orgId
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getRowData: function (id) {
      this.data = {}
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license/' + id
      ).then(res => {
        this.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    close: function () {
      this.$emit('close')
    },
    moveLicense: function () {
      this.$util.move(this, 'LICENSE', this.data.id)
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
