<!--
 Class Name  : CIDetailSpecNetStorSec.vue
 Description : IT 자원 > 상세 > 세부정보 (네트웍 저장장치 시큐리티)
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln1}}</label>
        <input v-model="listGrid.currentData.extaln1" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln2}}</label>
        <input v-model="listGrid.currentData.extaln2" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln3}}</label>
        <input v-model="listGrid.currentData.extaln3" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln4}}</label>
        <input v-model="listGrid.currentData.extaln4" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln5}}</label>
        <input v-model="listGrid.currentData.extaln5" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln6}}</label>
        <input v-model="listGrid.currentData.extaln6" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln7}}</label>
        <input v-model="listGrid.currentData.extaln7" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln8}}</label>
        <input v-model="listGrid.currentData.extaln8" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln9}}</label>
        <input v-model="listGrid.currentData.extaln9" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln10}}</label>
        <input v-model="listGrid.currentData.extaln10" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(listGrid.ciExtNmData.extaln17)">
        <label>{{listGrid.ciExtNmData.extaln17}}</label>
        <input v-model="listGrid.currentData.extaln17" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200"/>
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln18}}</label>
        <div class="search-component sm">
          <input v-model="listGrid.currentData.extaln18" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" disabled="disabled"/>
          <el-popover placement="right" width="120" trigger="click">
            <el-button size="mini" type="text" v-on:click="openItsmCIDialog">{{labels.matchItsmCI}}</el-button>
            <br  v-show="listGrid.currentData.extaln18">
            <el-button size="mini" type="text" v-show="listGrid.currentData.extaln18" v-on:click="delItsmCI()">{{labels.delItsmCI}}</el-button>
            <a slot="reference" class="action-button cursor-pointer">search</a>
          </el-popover>
        </div>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extbool3}}</label>
        <el-switch v-model="listGrid.currentData.extbool3" v-on:change="changeValue(listGrid.currentData)" class="formdata sm"></el-switch>
      </div>
    </b-row>
    <!-- Itsm ci Dialog -->
    <CIItsmCIDialog
      ref="cIItsmCIDialog"
      v-bind:showDialogFlagProp="showItsmCIDialogFlag"
      v-bind:param="{classObj: 'CI' ,ciId: listGrid.currentData.ciId}"
      v-on:add="addItsmCI"
      v-on:close="closeItsmCIDialog"
    />
  </div>
</template>
<script>
import CIItsmCIDialog from './CIItsmCIDialog'
export default {
  name: 'CI',
  props: ['labels', 'comboData', 'listGrid'],
  components: {
    'CIItsmCIDialog': CIItsmCIDialog
  },
  data () {
    return {
      showItsmCIDialogFlag: false
    }
  },
  watch: {
    'listGrid.currentData.extdate1': function (val) {
      this.listGrid.currentData.extdate1 = this.$util.dateTimeCalendar(val)
    }
  },
  methods: {
    init: function () {},
    cmpType: function (key) {
      return key.substr(0, 4)
    },
    openItsmCIDialog: function () {
      this.showItsmCIDialogFlag = true
    },
    addItsmCI: function (select) {
      if (Object.keys(select).length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.listGrid.currentData.extaln18 = select.kaiCiid
      this.showItsmCIDialogFlag = false
      this.changeValue(this.listGrid.currentData)
    },
    delItsmCI: function () {
      this.listGrid.currentData.extaln18 = null
      this.changeValue(this.listGrid.currentData)
    },
    closeItsmCIDialog: function () {
      this.showItsmCIDialogFlag = false
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
