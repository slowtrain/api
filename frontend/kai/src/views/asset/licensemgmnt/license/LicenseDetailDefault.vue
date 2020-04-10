<!--
 Class Name  : LicenseDetailDefault.vue
 Description : 라이선스 > 상세 기본 정보
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.16  최영훈  최초 생성
-->
<template>
  <div>
    <b-row>
      <div class="form-group col-md-2">
        <label for="swlicenseId">{{labels.swlicenseId}}</label>
        <input id="swlicenseId" v-model="listGrid.currentData.swlicenseId" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-4">
        <label for="swlicensename">{{labels.name}}<span class="require-mark">*</span></label>
        <div class="search-component sm">
          <input id="swlicensename" v-model="listGrid.currentData.swlicname" class="formdata sm" autocomplete="off" v-bind:maxlength="100" v-on:change="changeValue(listGrid.currentData)"/>
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.nameMsg)" v-text="validMsg.nameMsg"></p>
        </div>
      </div>
      <div class="form-group col-md-2">
        <label for="managerNm">{{labels.manager}}</label>
        <div class="search-component sm">
          <input id="managerNm" v-model="listGrid.currentData.managerNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a class="search-button cursor-pointer" v-on:click="openUserDialog">search</a>
        </div>
      </div>
      <div class="form-group col-md-2">
        <label for="swlictype">{{labels.classstructure}}</label>
        <div class="search-component sm">
          <input id="swlictype" v-model="listGrid.currentData.swlictypeNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a class="search-button cursor-pointer" v-on:click="openClassDialog">search</a>
        </div>
      </div>
      <div class="form-group col-md-2">
        <label for="status">{{labels.status}}</label>
        <el-select v-model="listGrid.currentData.swlicstatus" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
          <el-option v-for="status in comboData.statusSelect" :prop="status.value" :value="status.key" :label="status.value" :key="status.key"></el-option>
        </el-select>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-6">
        <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
        <input id="description" v-model="listGrid.currentData.swlicdescription" class="formdata sm" autocomplete="off" v-bind:maxlength="255" v-on:change="changeValue(listGrid.currentData)"/>
        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
      </div>
      <div class="form-group col-md-2">
        <label for="orgId">{{labels.orgId}}</label>
        <el-select v-model="listGrid.currentData.orgId" :placeholder="labels.selectData" disabled="disabled">
          <el-option v-for="orgId in comboData.orgSelect" :prop="orgId.orgId" :value="orgId.orgId" :key="orgId.orgId">
            {{orgId.orgId}}
          </el-option>
        </el-select>
      </div>
      <div class="form-group col-md-2">
        <label for="companyId">{{labels.company}}</label>
        <div class="search-component sm">
          <input id="companyId" v-model="listGrid.currentData.companyname" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a class="search-button cursor-pointer" v-on:click="openCompanyDialog">search</a>
        </div>
      </div>
      <div class="form-group col-md-2">
        <label for="purchasetype">{{labels.purchasetype}}</label>
        <el-select v-model="listGrid.currentData.purchasetype" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
          <el-option v-for="purchasetype in comboData.purchasetypeSelect" :prop="purchasetype.value" :value="purchasetype.key" :label="purchasetype.value" :key="purchasetype.key"></el-option>
        </el-select>
      </div>
    </b-row>
    <b-row>
      <div class='form-group col-md-2'>
        <label for="application_createId">{{labels.createId}}</label>
        <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
      </div>
      <div class='form-group col-md-2'>
        <label for="application_createDt">{{labels.createDt}}</label>
        <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
      </div>
      <div class='form-group col-md-2'>
        <label for="application_updateId">{{labels.updateId}}</label>
        <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
      </div>
      <div class='form-group col-md-2'>
        <label>{{labels.updateDt}}</label>
        <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-10">
      </div>
    </b-row>
    <!-- User Dialog -->
    <User
      ref="userDialog"
      v-bind:showDialogFlagProp="showUserDialogFlag"
      v-bind:username="listGrid.currentData.managerId"
      v-on:confirm="confirmUser"
      v-on:close="closeUserDialog"
    />
    <!-- Class Dialog -->
    <Class
      ref="ciDialog"
      v-bind:showDialogFlagProp="showClassDialogFlag"
      v-bind:param="{object: 'LICENSE', expand: {data: {classstructureId: 'SOFTWARE'}}}"
      v-on:confirm="confirmClass"
      v-on:close="closeClassDialog"
    />
    <!-- Company Dialog -->
    <Company
      ref="companyDialog"
      v-bind:showDialogFlagProp="showCompanyDialogFlag"
      v-bind:labels="labels"
      v-bind:param="{companytype: companytype}"
      v-on:confirm="confirmCompany"
      v-on:close="closeCompanyDialog"
    />
  </div>
</template>
<script>
import User from '@/views/common/User'
import Class from '@/views/common/Class'
import Company from './LicenseCompanyDialog'
export default {
  props: ['labels', 'comboData', 'detailType', 'validMsg', 'listGrid'],
  components: {
    'User': User,
    'Class': Class,
    'Company': Company
  },
  data () {
    return {
      showUserDialogFlag: false,
      showClassDialogFlag: false,
      showCompanyDialogFlag: false,
      companytype: 'PRVDR'
    }
  },
  methods: {
    openUserDialog: function () {
      this.showUserDialogFlag = true
    },
    confirmUser: function (rslt) {
      this.showUserDialogFlag = false
      this.listGrid.currentData.managerId = rslt.username
      this.listGrid.currentData.managerNm = rslt.firstname
      this.changeValue(this.listGrid.currentData)
    },
    closeUserDialog: function () {
      this.showUserDialogFlag = false
    },
    openClassDialog: function () {
      this.showClassDialogFlag = true
    },
    confirmClass: function (rslt) {
      this.showClassDialogFlag = false
      this.listGrid.currentData.swlictypeNm = rslt.classstructureNm
      this.listGrid.currentData.swlictype = rslt.classstructureId
      this.changeValue(this.listGrid.currentData)
    },
    closeClassDialog: function () {
      this.showClassDialogFlag = false
    },
    openCompanyDialog: function () {
      this.showCompanyDialogFlag = true
    },
    confirmCompany: function (select) {
      this.listGrid.currentData.companyId = select.companyId
      this.listGrid.currentData.companyname = select.companyname
      this.showCompanyDialogFlag = false
      this.changeValue(this.listGrid.currentData)
    },
    closeCompanyDialog: function () {
      this.showCompanyDialogFlag = false
    },
    changeValue: function (data) {
      if (!this.$util.isNull(data.id)) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    }
  },
  created: function () {}
}
</script>
