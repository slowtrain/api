<!--
 Class Name  : LicenseRelation.vue
 Description : 라이선스 > 관련 라이선스
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
      <div class="col mt-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class="col-md-12">
        <b-row>
          <div class="col" style="padding-top:25px; padding-left:20px;">
            <a class="title-box">{{labels.licenseRelation}}</a>
          </div>
        </b-row>
        <div class='row'>
          <div class="col" style="padding-top:18px; padding-left:20px;">
            <div class="search-component sm">
              <input class="form-control formdata sm" style="width: 100%;" v-model="relationGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder=labels.searchA />
            </div>
          </div>
          <div class="col">
            <div class="float-right mt-3" role="group">
              <b-button variant="primary" class="px-3 sm" v-if="visibleAddRelation" v-on:click="openLicenseRelationDialog">{{labels.add}}</b-button>
            </div>
          </div>
        </div>
        <div class='row mt-2'>
          <div class='col'>
            <p class="grid-line"></p>
            <data-tables layout='table, pagination' :table-props='relationGrid.tableProps' :data='relationGrid.data' :filters='relationGrid.filters' :page-size='relationGrid.pageProps.pageSize' :pagination-props='relationGrid.pageProps'>
              <el-table-column :prop='relationGrid.titles[0].prop' :label='labels.swlicenseId'    sortable='custom' :width='relationGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[1].prop' :label='labels.classstructure' sortable='custom' :width='relationGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[2].prop' :label='labels.name'           sortable='custom'></el-table-column>
              <el-table-column :prop='relationGrid.titles[3].prop' :label='labels.purchasetype'   sortable='custom' :width='relationGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[4].prop' :label='labels.purchaseunit'   sortable='custom' :width='relationGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[5].prop' :label='labels.purchasenum'    sortable='custom' :width='relationGrid.titles[5].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[6].prop' :label='labels.startDt'        sortable='custom' :width='relationGrid.titles[6].width'>
                <template slot-scope="props">
                  {{$util.date(props.row.startDt)}}
                </template>
              </el-table-column>
              <el-table-column :prop='relationGrid.titles[7].prop' :label='labels.endDt'        sortable='custom' :width='relationGrid.titles[7].width'>
                <template slot-scope="props">
                  {{$util.date(props.row.endDt)}}
                </template>
              </el-table-column>
              <el-table-column align="right" width="80">
                <template  slot-scope="props">
                  <el-button  v-if="visibleDeleteRelation" v-on:click="delRelation(props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column align="right" width="80">
                <template  slot-scope="props">
                  <el-button v-on:click="moveLicense(props.row)" :title="labels.movelic" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_search_login.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </div>
      </div>
    </b-row>
    <!-- Relation Dialog -->
    <LicenseRelationDialog
      ref="licenseRelationDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showRelationDialogFlag"
      v-bind:param="{swlicenseId: listGrid.currentData.swlicenseId}"
      v-on:add="addLicense"
      v-on:close="closeLicenseRelationDialog"
    />
  </div>
</template>
<script>
import LicenseDetailDefault from './LicenseDetailDefault'
import LicenseRelationDialog from './LicenseRelationDialog'
export default {
  props: ['labels', 'authorities', 'comboData', 'detailType', 'validMsg', 'listGrid', 'relationGrid', 'getRelationList'],
  components: {
    'LicenseDetailDefault': LicenseDetailDefault,
    'LicenseRelationDialog': LicenseRelationDialog
  },
  data () {
    return {
      showRelationDialogFlag: false
    }
  },
  computed: {
    visibleAddRelation: function () {
      return this.authorities.LICENSE_RELATION_ADD && this.detailType === 'UPDATE'
    },
    visibleDeleteRelation: function () {
      return this.authorities.LICENSE_RELATION_DELETE
    }
  },
  methods: {
    openLicenseRelationDialog: function () {
      this.showRelationDialogFlag = true
    },
    addLicense: function (selection) {
      let dataList = []
      selection.forEach(select => {
        let data = {
          orgId: this.listGrid.currentData.orgId,
          swlicenseId1: this.listGrid.currentData.swlicenseId,
          swlicenseId2: select.swlicenseId
        }
        dataList.push(data)
      })
      if (dataList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.$http.post(
        '/episode/api/asset/licensemgmnt/license/relation'
        , dataList
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.getRelationList(this.listGrid.currentData.id)
        this.showRelationDialogFlag = false
      }).catch(error => {
        console.log(error)
      })
    },
    closeLicenseRelationDialog: function () {
      this.showRelationDialogFlag = false
    },
    delRelation: function (row) {
      this.$http.delete(
        '/episode/api/asset/licensemgmnt/license/relation/' + row.relId
      ).then(res => {
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        this.getRelationList(this.listGrid.currentData.id)
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    },
    moveLicense: function (row) {
      let param = {id: row.id}
      this.$emit('moveLicense', param)
    },
    enterSearch: function () {
      this.relationGrid.filters[0].value = this.relationGrid.searchParam
    }
  },
  created: function () {}
}
</script>
