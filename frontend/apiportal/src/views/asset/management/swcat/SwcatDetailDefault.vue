<!--
 Class Name  : SwcatDetailDefault.vue
 Description : 소프트웨어 카탈로그 > 상세 기본 정보
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.14  최영훈  최초 생성
-->
<template>
  <div>
    <b-row>
      <div class="form-group col-md-2">
        <label for="swcatId">{{labels.id}}</label>
        <input id="swcatId" v-model="listGrid.currentData.swcatId" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label for="classstructure">{{labels.classstructureId}}<span class="require-mark">*</span></label>
        <div class="search-component sm">
          <input id="classstructure" v-model="listGrid.currentData.classstructureNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a class="search-button cursor-pointer" v-on:click="openClassDialog">search</a>
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.classstructureMsg)" v-text="validMsg.classstructureMsg"></p>
        </div>
      </div>
      <div class="form-group col-md-6">
        <label for="swcatname">{{labels.name}}<span class="require-mark">*</span></label>
        <input id="swcatname" v-model="listGrid.currentData.swcatname" class="formdata sm" autocomplete="off" :disabled="!listGrid.currentData.userdefine" v-bind:maxlength="200" v-on:change="changeValue(listGrid.currentData)"/>
        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.swcatnameMsg)" v-text="validMsg.swcatnameMsg"></p>
      </div>
      <div class="form-group col-md-2">
        <label for="swcattype">{{labels.type}}</label>
        <el-select v-model="listGrid.currentData.swcattype" :placeholder="labels.selectData" :disabled="listGrid.currentData.hasOwnProperty('id')" v-on:change="changeValue(listGrid.currentData)">
          <el-option v-for="swcattype in comboData.swcattypeSelect" :prop="swcattype.value" :value="swcattype.value" :key="swcattype.key" :label="swcattype.value">
          </el-option>
        </el-select>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-6">
        <label for="swcatdescription">{{labels.description}}</label>
        <input id="swcatdescription" v-model="listGrid.currentData.swcatdescription" class="formdata sm" autocomplete="off" v-bind:maxlength="255" v-on:change="changeValue(listGrid.currentData)"/>
      </div>
      <div class="form-group col-md-6">
        <label>{{labels.publisher}}</label>
        <input v-model="listGrid.currentData.publishername" class="formdata sm" autocomplete="off" :disabled="!listGrid.currentData.userdefine" v-bind:maxlength="100" v-on:change="changeValue(listGrid.currentData)"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{labels.datasrc}}</label>
        <input id="datasrc" :value="listGrid.currentData.datasrc" class="formdata sm" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="listGrid.currentData.swcattype === 'PRODUCT'">
        <label>{{labels.isdomestic}}</label>
        <el-switch v-model="listGrid.currentData.isdomestic" class="formdata sm" :disabled="!listGrid.currentData.userdefine" v-on:change="changeValue(listGrid.currentData)"></el-switch>
      </div>
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
    <!-- Class Dialog -->
    <Class
      ref="classDialog"
      v-bind:showDialogFlagProp="showClassDialogFlag"
      v-bind:param="{object: 'SWCAT', expand: {data: {classstructureId: 'SOFTWARE'}}}"
      v-on:confirm="confirmClass"
      v-on:close="closeClassDialog"
    />
  </div>
</template>
<script>
import Class from '@/views/common/Class'
export default {
  props: ['labels', 'comboData', 'detailType', 'listGrid', 'validMsg'],
  components: {
    'Class': Class
  },
  data () {
    return {
      showClassDialogFlag: false
    }
  },
  methods: {
    openClassDialog: function () {
      this.showClassDialogFlag = true
    },
    confirmClass: function (rslt) {
      this.showClassDialogFlag = false
      this.listGrid.currentData.classstructureNm = rslt.classstructureNm
      this.listGrid.currentData.classstructureId = rslt.classstructureId
      this.changeValue(this.listGrid.currentData)
    },
    closeClassDialog: function () {
      this.showClassDialogFlag = false
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
