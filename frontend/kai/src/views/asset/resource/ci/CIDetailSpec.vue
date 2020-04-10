<!--
 Class Name  : CIDetailSpec.vue
 Description : IT 자원 > 상세 > 세부정보 (기본)
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln1)">
        <label>{{listGrid.ciExtNmData.extaln1}}</label>
        <input v-model="listGrid.currentData.extaln1" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln2)">
        <label>{{listGrid.ciExtNmData.extaln2}}</label>
        <input v-model="listGrid.currentData.extaln2" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln3)">
        <label>{{listGrid.ciExtNmData.extaln3}}</label>
        <input v-model="listGrid.currentData.extaln3" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln4)">
        <label>{{listGrid.ciExtNmData.extaln4}}</label>
        <input v-model="listGrid.currentData.extaln4" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln5)">
        <label>{{listGrid.ciExtNmData.extaln5}}</label>
        <input v-model="listGrid.currentData.extaln5" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln6)">
        <label>{{listGrid.ciExtNmData.extaln6}}</label>
        <input v-model="listGrid.currentData.extaln6" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln7)">
        <label>{{listGrid.ciExtNmData.extaln7}}</label>
        <input v-model="listGrid.currentData.extaln7" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln8)">
        <label>{{listGrid.ciExtNmData.extaln8}}</label>
        <input v-model="listGrid.currentData.extaln8" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln9)">
        <label>{{listGrid.ciExtNmData.extaln9}}</label>
        <input v-model="listGrid.currentData.extaln9" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln10)">
        <label>{{listGrid.ciExtNmData.extaln10}}</label>
        <input v-model="listGrid.currentData.extaln10" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln11)">
        <label>{{listGrid.ciExtNmData.extaln11}}</label>
        <input v-model="listGrid.currentData.extaln11" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln12)">
        <label>{{listGrid.ciExtNmData.extaln12}}</label>
        <input v-model="listGrid.currentData.extaln12" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln13)">
        <label>{{listGrid.ciExtNmData.extaln13}}</label>
        <input v-model="listGrid.currentData.extaln13" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln14)">
        <label>{{listGrid.ciExtNmData.extaln14}}</label>
        <input v-model="listGrid.currentData.extaln14" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln15)">
        <label>{{listGrid.ciExtNmData.extaln15}}</label>
        <input v-model="listGrid.currentData.extaln15" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln16)">
        <label>{{listGrid.ciExtNmData.extaln16}}</label>
        <input v-model="listGrid.currentData.extaln16" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln17)">
        <label>{{listGrid.ciExtNmData.extaln17}}</label>
        <input v-model="listGrid.currentData.extaln17" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln18)">
        <label>{{listGrid.ciExtNmData.extaln18}}</label>
        <input v-model="listGrid.currentData.extaln18" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
        <!-- <div class="search-component sm">
          <input v-model="listGrid.currentData.extaln18" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200"/>
          <el-popover placement="right" width="120" trigger="click">
            <el-button size="mini" type="text" v-on:click="openItsmCIDialog">{{labels.matchItsmCI}}</el-button>
            <br  v-show="listGrid.currentData.extaln18">
            <el-button size="mini" type="text" v-show="listGrid.currentData.extaln18" v-on:click="delItsmCI()">{{labels.delItsmCI}}</el-button>
            <a slot="reference" class="action-button cursor-pointer">search</a>
          </el-popover>
        </div> -->
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln19)">
        <label>{{listGrid.ciExtNmData.extaln19}}</label>
        <input v-model="listGrid.currentData.extaln19" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln20)">
        <label>{{listGrid.ciExtNmData.extaln20}}</label>
        <input v-model="listGrid.currentData.extaln20" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum1)">
        <label>{{listGrid.ciExtNmData.extnum1}}</label>
        <input v-model="listGrid.currentData.extnum1" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum2)">
        <label>{{listGrid.ciExtNmData.extnum2}}</label>
        <input v-model="listGrid.currentData.extnum2" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum3)">
        <label>{{listGrid.ciExtNmData.extnum3}}</label>
        <input v-model="listGrid.currentData.extnum3" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum4)">
        <label>{{listGrid.ciExtNmData.extnum4}}</label>
        <input v-model="listGrid.currentData.extnum4" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum5)">
        <label>{{listGrid.ciExtNmData.extnum5}}</label>
        <input v-model="listGrid.currentData.extnum5" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate1)">
        <label>{{listGrid.ciExtNmData.extdate1}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate1" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate2)">
        <label>{{listGrid.ciExtNmData.extdate2}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate2" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate3)">
        <label>{{listGrid.ciExtNmData.extdate3}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate3" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate4)">
        <label>{{listGrid.ciExtNmData.extdate4}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate4" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate5)">
        <label>{{listGrid.ciExtNmData.extdate5}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate5" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool1)">
        <label>{{listGrid.ciExtNmData.extbool1}}</label>
        <el-switch v-model="listGrid.currentData.extbool1" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool2)">
        <label>{{listGrid.ciExtNmData.extbool2}}</label>
        <el-switch v-model="listGrid.currentData.extbool2" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool3)">
        <label>{{listGrid.ciExtNmData.extbool3}}</label>
        <el-switch v-model="listGrid.currentData.extbool3" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool4)">
        <label>{{listGrid.ciExtNmData.extbool4}}</label>
        <el-switch v-model="listGrid.currentData.extbool4" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool5)">
        <label>{{listGrid.ciExtNmData.extbool5}}</label>
        <el-switch v-model="listGrid.currentData.extbool5" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
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
  props: ['labels', 'comboData', 'listGrid', 'displayAllFlag'],
  components: {
    'CIItsmCIDialog': CIItsmCIDialog
  },
  data () {
    return {
      showItsmCIDialogFlag: false
    }
  },
  computed: {
    extdate1: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate1)
      },
      set: function (val) {
        this.listGrid.currentData.extdate1 = val
      }
    },
    extdate2: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate2)
      },
      set: function (val) {
        this.listGrid.currentData.extdate2 = val
      }
    },
    extdate3: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate3)
      },
      set: function (val) {
        this.listGrid.currentData.extdate3 = val
      }
    },
    extdate4: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate4)
      },
      set: function (val) {
        this.listGrid.currentData.extdate4 = val
      }
    },
    extdate5: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate5)
      },
      set: function (val) {
        this.listGrid.currentData.extdate5 = val
      }
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
