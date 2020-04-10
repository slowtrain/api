<template>
  <el-dialog :title="labels.ciDialogTitle" :visible.sync="showDialogFlag" width=1000px  v-on:close="close">
    <b-row>
      <div class="form-group col-md-3">
        <label for="ciId">{{labels.ciId}}</label>
        <div class="search-component sm">
          <input id="ciId" v-model="data.ciId" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a slot="reference" class="action-button cursor-pointer" v-on:click="moveCI">search</a>
        </div>
      </div>
      <div class="form-group col-md-3">
        <label for="ciname">{{labels.name}}</label>
        <input id="ciname" v-model="data.ciname" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-3">
        <label for="classstructureId">{{labels.classstructureId}}</label>
        <input id="classstructureId" v-model="data.classstructureId" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-3">
        <label for="status">{{labels.opstatus}}</label>
        <input id="status" v-model="data.status" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-12">
        <label for="description">{{labels.description}}</label>
        <input id="description" v-model="data.description" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <b-col class="mt-1 mb-1">
        <p class="grid-line"></p>
      </b-col>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln1)">
        <label>{{ciExtNmData.extaln1}}</label>
        <input v-model="data.extaln1" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln2)">
        <label>{{ciExtNmData.extaln2}}</label>
        <input v-model="data.extaln2" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln3)">
        <label>{{ciExtNmData.extaln3}}</label>
        <input v-model="data.extaln3" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln4)">
        <label>{{ciExtNmData.extaln4}}</label>
        <input v-model="data.extaln4" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln5)">
        <label>{{ciExtNmData.extaln5}}</label>
        <input v-model="data.extaln5" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln6)">
        <label>{{ciExtNmData.extaln6}}</label>
        <input v-model="data.extaln6" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln7)">
        <label>{{ciExtNmData.extaln7}}</label>
        <input v-model="data.extaln7" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln8)">
        <label>{{ciExtNmData.extaln8}}</label>
        <input v-model="data.extaln8" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln9)">
        <label>{{ciExtNmData.extaln9}}</label>
        <input v-model="data.extaln9" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln10)">
        <label>{{ciExtNmData.extaln10}}</label>
        <input v-model="data.extaln10" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln11)">
        <label>{{ciExtNmData.extaln11}}</label>
        <input v-model="data.extaln11" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln12)">
        <label>{{ciExtNmData.extaln12}}</label>
        <input v-model="data.extaln12" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln13)">
        <label>{{ciExtNmData.extaln13}}</label>
        <input v-model="data.extaln13" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln14)">
        <label>{{ciExtNmData.extaln14}}</label>
        <input v-model="data.extaln14" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln15)">
        <label>{{ciExtNmData.extaln15}}</label>
        <input v-model="data.extaln15" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln16)">
        <label>{{ciExtNmData.extaln16}}</label>
        <input v-model="data.extaln16" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln17)">
        <label>{{ciExtNmData.extaln17}}</label>
        <input v-model="data.extaln17" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln18)">
        <label>{{ciExtNmData.extaln18}}</label>
        <input v-model="data.extaln18" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln19)">
        <label>{{ciExtNmData.extaln19}}</label>
        <input v-model="data.extaln19" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extaln20)">
        <label>{{ciExtNmData.extaln20}}</label>
        <input v-model="data.extaln20" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extnum1)">
        <label>{{ciExtNmData.extnum1}}</label>
        <input v-model="data.extnum1" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extnum2)">
        <label>{{ciExtNmData.extnum2}}</label>
        <input v-model="data.extnum2" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extnum3)">
        <label>{{ciExtNmData.extnum3}}</label>
        <input v-model="data.extnum3" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extnum4)">
        <label>{{ciExtNmData.extnum4}}</label>
        <input v-model="data.extnum4" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extnum5)">
        <label>{{ciExtNmData.extnum5}}</label>
        <input v-model="data.extnum5" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extdate1)">
        <label>{{ciExtNmData.extdate1}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate1" class="formdata sm" disabled="disabled"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extdate2)">
        <label>{{ciExtNmData.extdate2}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate2" class="formdata sm" disabled="disabled"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extdate3)">
        <label>{{ciExtNmData.extdate3}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate3" class="formdata sm" disabled="disabled"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extdate4)">
        <label>{{ciExtNmData.extdate4}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate4" class="formdata sm" disabled="disabled"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extdate5)">
        <label>{{ciExtNmData.extdate5}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate5" class="formdata sm" disabled="disabled"></el-date-picker>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extbool1)">
        <label>{{ciExtNmData.extbool1}}</label>
        <el-switch v-model="data.extbool1" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extbool2)">
        <label>{{ciExtNmData.extbool2}}</label>
        <el-switch v-model="data.extbool2" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extbool3)">
        <label>{{ciExtNmData.extbool3}}</label>
        <el-switch v-model="data.extbool3" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extbool4)">
        <label>{{ciExtNmData.extbool4}}</label>
        <el-switch v-model="data.extbool4" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="!$util.isNull(ciExtNmData.extbool5)">
        <label>{{ciExtNmData.extbool5}}</label>
        <el-switch v-model="data.extbool5" class="formdata sm" disabled="disabled"></el-switch>
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
  name: 'CI',
  props: ['showDialogFlagProp', 'param'],
  data () {
    return {
      labels: {
        ciId: ''
      },
      data: {},
      ciExtNmData: {},
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
  computed: {
    extdate1: {
      get: function () {
        return this.$util.dateTimeCalendar(this.ciExtNmData.extdate1)
      },
      set: function (val) {
        this.ciExtNmData.extdate1 = val
      }
    },
    extdate2: {
      get: function () {
        return this.$util.dateTimeCalendar(this.ciExtNmData.extdate2)
      },
      set: function (val) {
        this.ciExtNmData.extdate2 = val
      }
    },
    extdate3: {
      get: function () {
        return this.$util.dateTimeCalendar(this.ciExtNmData.extdate3)
      },
      set: function (val) {
        this.ciExtNmData.extdate3 = val
      }
    },
    extdate4: {
      get: function () {
        return this.$util.dateTimeCalendar(this.ciExtNmData.extdate4)
      },
      set: function (val) {
        this.ciExtNmData.extdate4 = val
      }
    },
    extdate5: {
      get: function () {
        return this.$util.dateTimeCalendar(this.ciExtNmData.extdate5)
      },
      set: function (val) {
        this.ciExtNmData.extdate5 = val
      }
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.orgId = this.$store.getters.getUser.orgId
    },
    getRowData: function (id) {
      this.data = {}
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id
      ).then(res => {
        this.data = res.data.data
        this.ciExtNmData = res.data.ciExtNmData
      }).catch(error => {
        console.log(error)
      })
    },
    close: function () {
      this.$emit('close')
    },
    moveCI: function () {
      this.$util.move(this, 'CI', this.data.id)
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
