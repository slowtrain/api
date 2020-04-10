<!--
 Class Name  : LicenseDetail.vue
 Description : 라이선스 > 상세
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.16  최영훈  최초 생성
-->
<template>
  <div>
   <LicenseDetailDefault
      ref="licenseDetailDefault"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:listGrid="listGrid"
      v-bind:validMsg="validMsg"
    />
    <b-row>
      <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row  class="mt-2 mb-2">>
      <div class="col" style="padding-left:5px;">
        <a class="title-box">{{labels.purchaseIno}}</a>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label for="poId">{{labels.po}}</label>
        <div class="search-component sm">
          <input id="poId" v-model="listGrid.currentData.poId" class="formdata sm" autocomplete="off" v-bind:maxlength="30" v-on:change="changeValue(listGrid.currentData)"/>
        </div>
      </div>
      <div class="form-group col-md-2">
        <label for="priceunit">{{labels.priceunit}}</label>
        <el-select v-model="listGrid.currentData.priceunit" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
          <el-option v-for="priceunit in comboData.priceunitSelect" :prop="priceunit.key" :value="priceunit.key" :label="priceunit.value" :key="priceunit.key"></el-option>
        </el-select>
      </div>
      <div class="form-group col-md-2">
        <label>{{labels.purchaseprice}}</label>
        <input v-model="purchaseprice" style='text-align:right;' v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
      </div>
      <div class="form-group col-md-2">
        <label>{{labels.purchaseDt}}<span class="require-mark">*</span></label>
        <el-date-picker type="date" default-time="12:00:00" v-model="purchaseDt" class="formdata sm" v-on:change="changeValue(listGrid.currentData)"></el-date-picker>
        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.purchaseDtMsg)" v-text="validMsg.purchaseDtMsg"></p>
      </div>
      <div class="form-group col-md-2">
        <label for="purchaseunit">{{labels.purchaseunit}}<span class="require-mark">*</span></label>
        <el-select v-model="listGrid.currentData.purchaseunit" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
          <el-option v-for="purchaseunit in comboData.purchaseunitSelect" :prop="purchaseunit.key" :value="purchaseunit.key" :label="purchaseunit.value" :key="purchaseunit.key"></el-option>
        </el-select>
        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.purchaseunitMsg)" v-text="validMsg.purchaseunitMsg"></p>
      </div>
      <div class="form-group col-md-2">
        <label>{{labels.purchasenum}}<span class="require-mark">*</span></label>
        <input v-model="listGrid.currentData.purchasenum" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.purchasenumMsg)" v-text="validMsg.purchasenumMsg"></p>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{labels.inyear}}</label>
        <input v-model="listGrid.currentData.inyear" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
      </div>
      <div class="form-group col-md-2">
        <label>{{labels.remainprice}}</label>
        <input v-model="remainprice" style='text-align:right;' v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
      </div>
      <div class="form-group col-md-2">
        <label>{{labels.startDt}}<span class="require-mark">*</span></label>
        <el-date-picker type="date" default-time="12:00:00" v-model="startDt" class="formdata sm" v-on:change="changeValue(listGrid.currentData)"></el-date-picker>
        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.startDtMsg)" v-text="validMsg.startDtMsg"></p>
      </div>
      <div class="form-group col-md-2">
        <label>{{labels.endDt}}</label>
        <el-date-picker type="date" default-time="12:00:00" v-model="endDt" class="formdata sm" v-on:change="changeValue(listGrid.currentData)"></el-date-picker>
      </div>
    </b-row>
  </div>
</template>
<script>
import LicenseDetailDefault from './LicenseDetailDefault'
export default {
  props: ['labels', 'authorities', 'comboData', 'detailType', 'validMsg', 'listGrid', 'swcatData'],
  components: {
    'LicenseDetailDefault': LicenseDetailDefault
  },
  data () {
    return {}
  },
  computed: {
    purchaseprice: {
      get: function () {
        let rtnPrice = null
        let price = this.listGrid.currentData.purchaseprice
        rtnPrice = Number(price).toLocaleString()
        return rtnPrice
      },
      set: function (val) {
        this.listGrid.currentData.purchaseprice = val.replace(/,/gi, '')
      }
    },
    remainprice: {
      get: function () {
        let rtnPrice = null
        let price = this.listGrid.currentData.remainprice
        rtnPrice = Number(price).toLocaleString()
        return rtnPrice
      },
      set: function (val) {
        this.listGrid.currentData.remainprice = val.replace(/,/gi, '')
      }
    },
    purchaseDt: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.purchaseDt)
      },
      set: function (val) {
        this.listGrid.currentData.purchaseDt = val
      }
    },
    startDt: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.startDt)
      },
      set: function (val) {
        if (!this.chkDate(val, this.endDt)) {
          return
        }
        this.listGrid.currentData.startDt = val
      }
    },
    endDt: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.endDt)
      },
      set: function (val) {
        if (!this.chkDate(this.startDt, val)) {
          return
        }
        this.listGrid.currentData.endDt = val
      }
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {},
    // USER FN ////////////////////////////////////////////
    chkDate: function (statrtDt, endDt) {
      if (this.$util.isNull(statrtDt) || this.$util.isNull(endDt)) return true
      if (statrtDt > endDt) {
        this.$util.esmessage(this, 'warn', this.labels.chkDateMsg)
        return false
      }
      return true
    },
    // TRNS FN ////////////////////////////////////////////
    // EVENT FN ///////////////////////////////////////////
    changeValue: function (data) {
      if (!this.$util.isNull(data.id)) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
