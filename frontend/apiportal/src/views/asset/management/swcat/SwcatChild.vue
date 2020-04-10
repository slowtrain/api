<!--
 Class Name  : SwcatChild.vue
 Description : 소프트웨어 카탈로그 > 하위 카탈로그
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
    <!-- child swcat-->
    <b-row class="mb-1" v-if="listGrid.currentData.swcattype === 'PRODUCT'">
      <b-col style="height:60px; padding-top:25px; padding-left:20px;">
        <a class="title-box">{{labels.swcatchild}}</a>
      </b-col>
      <b-col>
        <div class="float-right mt-3">
          <b-button variant="primary" class="px-3 sm" v-if="visibleDeleteChild" v-on:click="delChild" >{{labels.delete}}</b-button>
        </div>
      </b-col>
    </b-row>
    <b-row v-if="childTree.rawData.length > 0 && listGrid.currentData.swcattype === 'PRODUCT'">
      <b-col sm="3">
        <Tree
          ref="childTree"
          v-bind:tree="childTree"
          v-on:clickNode="clickNode"
          v-on:clickAddBtn="addChild"
          style="height:150px !important"
        />
      </b-col>
      <b-col sm="9" v-if="!$util.isNull(childData.swcatId)">
        <b-row>
          <div class="form-group col-md-3">
            <label for="swcattype">{{labels.type}}</label>
            <input id="swcattype" v-model="childData.swcattype" class="formdata sm" autocomplete="off" disabled="disabled"/>
          </div>
          <div class="form-group col-md-2">
            <label for="swcatId">{{labels.id}}</label>
            <input id="swcatId" v-model="childData.swcatId" class="formdata sm" autocomplete="off" disabled="disabled"/>
          </div>
          <div class="form-group col-md-7">
            <label for="swcatname">{{labels.name}}</label>
            <input id="swcatname" v-model="childData.swcatname" class="formdata sm" autocomplete="off" disabled="disabled"/>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.swcatnameMsg)" v-text="validMsg.swcatnameMsg"></p>
          </div>
        </b-row>
        <b-row>
          <div class="form-group col-md-9">
            <label for="swcatdescription">{{labels.description}}</label>
            <input id="swcatdescription" v-model="childData.swcatdescription" class="formdata sm" autocomplete="off"  :disabled="!listGrid.currentData.userdefine" v-on:change="changeValue(childData)"/>
          </div>
          <div class="form-group col-md-3">
            <label>{{labels.version}}<span class="require-mark">*</span></label>
            <input v-show="childData.swcattype !== 'RELEASE'" v-on:keydown="$util.onlyNumber($event)" v-model="swcatversion" class="formdata sm" autocomplete="off" :disabled="(!listGrid.currentData.userdefine) || (childData.swcattype === 'PRODUCT') || childData.cnt > 0"  v-on:change="changeValue(childData)"/>
            <input v-show="childData.swcattype === 'RELEASE'" v-model="swcatversion" class="formdata sm" autocomplete="off" :disabled="(!listGrid.currentData.userdefine) || (childData.swcattype === 'PRODUCT') || childData.cnt > 0"  v-on:change="changeValue(childData)"/>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.swcatversionMsg)" v-text="validMsg.swcatversionMsg"></p>
          </div>
        </b-row>
      </b-col>
    </b-row>
    <!-- group swcat -->
    <b-row class="mb-1" v-if="listGrid.currentData.swcattype === 'GROUP'">
      <b-col style="height:60px; padding-top:25px; padding-left:20px;">
        <a class="title-box">{{labels.swcatgroup}}</a>
      </b-col>
      <b-col>
        <div class="float-right mt-3">
          <b-button variant="primary" class="px-3 sm" v-if="visibleAddGroup" v-on:click="openSwcatGroupDialog">{{labels.add}}</b-button>
        </div>
      </b-col>
    </b-row>
    <b-row>
      <div class='col'>
        <p class="grid-line"></p>
        <data-tables v-if="listGrid.currentData.swcattype === 'GROUP'" layout='table, pagination' :table-props='groupGrid.tableProps' :data='groupGrid.data' :filters='groupGrid.filters' :page-size='groupGrid.pageProps.pageSize' :pagination-props='groupGrid.pageProps'>
          <el-table-column :prop='groupGrid.titles[0].prop' :label='labels.swcatId'           sortable='custom' :width='groupGrid.titles[0].width'></el-table-column>
          <el-table-column :prop='groupGrid.titles[1].prop' :label='labels.classstructureId'  sortable='custom' :width='groupGrid.titles[1].width'></el-table-column>
          <el-table-column :prop='groupGrid.titles[2].prop' :label='labels.publisher'         sortable='custom' :width='groupGrid.titles[2].width'></el-table-column>
          <el-table-column :prop='groupGrid.titles[3].prop' :label='labels.name'              sortable='custom' ></el-table-column>
          <el-table-column :prop='groupGrid.titles[4].prop' :label='labels.type'              sortable='custom' :width='groupGrid.titles[4].width'></el-table-column>
          <el-table-column :prop='groupGrid.titles[5].prop' :label='labels.version'           sortable='custom' :width='groupGrid.titles[5].width'></el-table-column>
          <el-table-column align="right" width="80">
            <template  slot-scope="props">
              <el-button  v-if="visibleDeleteGroup" v-on:click="delGroup(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                <img src="@/assets/img/icons/icon_delete.png" width="60%" />
              </el-button>
            </template>
          </el-table-column>
        </data-tables>
      </div>
    </b-row>
    <!-- Relation Dialog -->
    <SwcatGroupDialog
      ref="swcatGroupDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showSwcatGroupDialogFlag"
      v-bind:param="{swcatId: listGrid.currentData.swcatId}"
      v-on:add="addSwcatGroup"
      v-on:close="closeSwcatGroupDialog"
    />
  </div>
</template>
<script>
import SwcatDetailDefault from './SwcatDetailDefault'
import Tree from '@/views/common/Tree'
import SwcatGroupDialog from './SwcatGroupDialog'
export default {
  props: ['labels', 'authorities', 'comboData', 'detailType', 'listGrid', 'childTree', 'getRowData', 'groupGrid', 'validMsg'],
  components: {
    'SwcatDetailDefault': SwcatDetailDefault,
    'Tree': Tree,
    'SwcatGroupDialog': SwcatGroupDialog
  },
  data () {
    return {
      childData: {},
      // Global Var
      orgId: '',
      showSwcatGroupDialogFlag: false
    }
  },
  watch: {
    'childTree.selectData': function (val) {
      this.childData = val
    },
    'childData.swcatversion': function (val) {
      let swcatversionOrg = this.childData.swcatversionOrg
      if (!this.$util.isNull(val) && (this.childData.swcattype !== 'RELEASE')) {
        this.validMsg.swcatversionMsg = ''
      }
      if ((val !== swcatversionOrg) && (this.childData.swcattype === 'RELEASE')) {
        this.validMsg.swcatversionMsg = ''
      }
    }
  },
  computed: {
    visibleAddChild: function () {
      return this.detailType === 'UPDATE' && this.authorities.SWCAT_CHILD_ADD
    },
    visibleDeleteChild: function (val) {
      return this.detailType === 'UPDATE' && this.authorities.SWCAT_CHILD_DELETE && this.listGrid.currentData.userdefine && this.childData.swcattype !== 'PRODUCT' && !this.$util.isNull(this.childData.swcattype) && !this.$util.isNull(this.childData.id)
    },
    visibleAddGroup: function (val) {
      return this.detailType === 'UPDATE' && this.authorities.SWCAT_GROUP_ADD && this.listGrid.currentData.userdefine
    },
    visibleDeleteGroup: function (val) {
      return this.detailType === 'UPDATE' && this.authorities.SWCAT_GROUP_DELETE && this.listGrid.currentData.userdefine
    },
    swcatversion: {
      get: function () {
        let rtn = this.childData.swcatversion
        if (this.$util.isNull(rtn)) return
        if (this.childData.swcatversion.indexOf(this.childData.swcatversionOrg) === -1 && (this.childData.swcattype === 'RELEASE')) {
          rtn = this.childData.swcatversionOrg
        }
        return rtn
      },
      set: function (val) {
        this.childData.swcatversion = val
      }
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
        this.childData = {}
        this.childData = node.data
        if (node.data.swcattype !== 'PRODUCT') {
          let swcatversionOrg = node.parent.data.swcatversion
          if (node.data.swcattype === 'RELEASE') {
            swcatversionOrg = swcatversionOrg + '.'
          }
          this.childData.swcatversionOrg = swcatversionOrg
        }
      }
    },
    addChild: function (node) {
      if (!this.visibleAddChild) return
      this.$http.get(
        '/episode/api/asset/management/swcat/swcatId?orgId=' + this.orgId
      ).then(res => {
        let swcatversion = node.data.swcatversion + ''
        if (node.data.swcattype === 'VERSION') {
          swcatversion = swcatversion + '.'
        }
        this.childData = {
          orgId: this.orgId,
          classstructureId: node.data.classstructureId,
          swcatId: res.data,
          parentId: node.data.swcatId,
          swcatname: node.data.swcatname,
          swcattype: node.data.swcattype === 'PRODUCT' ? 'VERSION' : 'RELEASE',
          swcatdescription: '',
          swcatversion: swcatversion,
          swcatversionOrg: swcatversion,
          publishername: node.data.publishername,
          isdomestic: node.data.isdomestic,
          userdefine: true,
          hierarchypath: node.data.hierarchypath + '/' + res.data,
          state: 'ADDED'
        }
      }).catch(error => {
        console.log(error)
      })
    },
    delChild: function () {
      if (this.childData.cnt > 0) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
        return
      }
      this.$http.delete('/episode/api/asset/management/swcat/' + this.childData.id).then(res => {
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        this.getRowData(this.listGrid.currentData.id)
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    },
    openSwcatGroupDialog: function () {
      this.showSwcatGroupDialogFlag = true
    },
    addSwcatGroup: function (selection) {
      let dataList = []
      selection.forEach(select => {
        let data = {
          swcatgrpId: this.listGrid.currentData.swcatId,
          swcatId: select.swcatId
        }
        dataList.push(data)
      })
      if (dataList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.$http.post(
        '/episode/api/asset/management/swcat/group'
        , dataList
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.getRowData(this.listGrid.currentData.id)
        this.showSwcatGroupDialogFlag = false
      }).catch(error => {
        console.log(error)
      })
    },
    closeSwcatGroupDialog: function () {
      this.showSwcatGroupDialogFlag = false
    },
    delGroup: function (index, row) {
      this.$http.delete(
        '/episode/api/asset/management/swcat/group/' + row.grpId
      ).then(res => {
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        this.getRowData(this.listGrid.currentData.id)
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    },
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
