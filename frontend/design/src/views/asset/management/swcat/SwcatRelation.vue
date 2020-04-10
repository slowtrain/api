<!--
 Class Name  : SwcatRelation.vue
 Description : 소프트웨어 카탈로그 > 관련 카탈로그
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.14  최영훈  최초 생성
-->
<template>
  <div>
   <SwcatDetailDefault
      ref="swcatDetailDefault"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:listGrid="listGrid"
      v-bind:validMsg="validMsg"
    />
    <b-row>
      <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row class="mb-1">
      <b-col style="height:60px; padding-top:25px; padding-left:20px;">
        <a class="title-box">{{labels.swcatrelation}}</a>
      </b-col>
      <b-col>
        <div class="float-right mt-3">
          <b-button variant="primary" class="px-3 sm" v-if="visibleDeleteRelation" v-on:click="delSwcatRelation" >{{labels.delete}}</b-button>
          <b-button variant="primary" class="px-3 sm" v-if="visibleAddRelation" v-on:click="openSwcatRelationDialog" >{{labels.add}}</b-button>
        </div>
      </b-col>
    </b-row>
    <b-row v-if="relationTree.rawData.length > 0">
      <b-col sm="3">
        <Tree
          ref="relationTree"
          v-bind:tree="relationTree"
          v-on:clickNode="clickNode"
          style="height:150px !important"
        />
      </b-col>
      <b-col sm="9" v-if="relationTree.rawData.length > 0">
        <b-row>
          <div class="form-group col-md-2">
            <label for="swcatId">{{labels.id}}</label>
            <input id="swcatId" v-model="relationData.swcatId" class="formdata sm" autocomplete="off" disabled="disabled"/>
          </div>
          <div class="form-group col-md-5">
            <label for="swcatname">{{labels.name}}<span class="require-mark">*</span></label>
            <input id="swcatname" v-model="relationData.swcatname" class="formdata sm" autocomplete="off" disabled="disabled"/>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.swcatnameMsg)" v-text="validMsg.swcatnameMsg"></p>
          </div>
          <div class="form-group col-md-2">
            <label for="swcattype">{{labels.type}}</label>
            <el-select v-model="relationData.swcattype" :placeholder="labels.selectData" disabled="disabled">
              <el-option v-for="swcattype in comboData.swcattypeSelect" :prop="swcattype.value" :value="swcattype.value" :key="swcattype.key" :label="swcattype.value">
              </el-option>
            </el-select>
          </div>
          <div class="form-group col-md-3">
            <label>{{labels.publisher}}</label>
            <input v-model="relationData.publishername" class="formdata sm" autocomplete="off" disabled="disabled"/>
          </div>
        </b-row>
        <b-row>
          <div class="form-group col-md-7">
            <label for="swcatdescription">{{labels.description}}</label>
            <input id="swcatdescription" v-model="relationData.swcatdescription" class="formdata sm" autocomplete="off"  disabled="disabled"/>
          </div>
          <div class="form-group col-md-2">
            <label>{{labels.version}}</label>
            <input v-model="relationData.swcatversion" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off"  disabled="disabled"/>
          </div>
          <div class="form-group col-md-2">
            <label>{{labels.isdomestic}}</label>
            <el-switch v-model="relationData.isdomestic" class="formdata sm" disabled="disabled"></el-switch>
          </div>
        </b-row>
      </b-col>
    </b-row>
    <!-- Relation Dialog -->
    <SwcatRelationDialog
      ref="swcatRelationDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showSwcatRelationDialogFlag"
      v-bind:param="{swcatId: listGrid.currentData.swcatId}"
      v-on:add="addSwcatRelation"
      v-on:close="closeSwcatRelationDialog"
    />
  </div>
</template>
<script>
import SwcatDetailDefault from './SwcatDetailDefault'
import Tree from '@/views/common/Tree'
import SwcatRelationDialog from './SwcatRelationDialog'
export default {
  props: ['labels', 'authorities', 'comboData', 'detailType', 'listGrid', 'relationTree', 'getRowData', 'validMsg'],
  components: {
    'SwcatDetailDefault': SwcatDetailDefault,
    'Tree': Tree,
    'SwcatRelationDialog': SwcatRelationDialog
  },
  data () {
    return {
      relationData: {},
      // Global Var
      orgId: '',
      // Dialog Flag
      showSwcatRelationDialogFlag: false
    }
  },
  computed: {
    visibleAddRelation: function (val) {
      return this.detailType === 'UPDATE' && this.authorities.SWCAT_RELATION_ADD
    },
    visibleDeleteRelation: function (val) {
      return this.detailType === 'UPDATE' && this.authorities.SWCAT_RELATION_DELETE
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    // EVENT FN ///////////////////////////////////////////
    clickNode: function (node) {
      this.searchParam = ''
      if (!this.$util.isNull(node)) {
        this.relationData = {}
        this.relationData = node.data
      }
    },
    openSwcatRelationDialog: function () {
      this.showSwcatRelationDialogFlag = true
    },
    addSwcatRelation: function (selection) {
      let dataList = []
      selection.forEach(select => {
        let data = {
          orgId: this.listGrid.currentData.orgId,
          swcatId1: this.listGrid.currentData.swcatId,
          swcatId2: select.swcatId
        }
        dataList.push(data)
      })
      if (dataList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.$http.post(
        '/episode/api/asset/management/swcat/relation'
        , dataList
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.getRowData(this.listGrid.currentData.id)
        this.showSwcatRelationDialogFlag = false
      }).catch(error => {
        console.log(error)
      })
    },
    closeSwcatRelationDialog: function () {
      this.showSwcatRelationDialogFlag = false
    },
    delSwcatRelation: function () {
      this.$http.delete(
        '/episode/api/asset/management/swcat/relation?orgId=' + this.relationData.orgId + '&swcatId=' + this.listGrid.currentData.swcatId + '&hierarchypath=' + this.relationData.hierarchypath
      ).then(res => {
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        this.getRowData(this.listGrid.currentData.id)
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
