<!--
 Class Name  : LicenseSwcat.vue
 Description : 라이선스 > 관련 카탈로그
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
            <a class="title-box">{{labels.licenseSwcat}}</a>
          </div>
        </b-row>
        <div class='row'>
          <div class="col" style="padding-top:18px; padding-left:20px;">
            <div class="search-component sm">
              <input class="form-control formdata sm" style="width: 100%;" v-model="swcatGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder=labels.searchA />
            </div>
          </div>
          <div class="col">
            <div class="float-right mt-3" role="group">
              <b-button variant="primary" class="px-3 sm" v-if="visibleAddSwcat" v-on:click="openLicenseSwcatDialog">{{labels.add}}</b-button>
            </div>
          </div>
        </div>
        <div class='row mt-2'>
          <div class='col'>
            <p class="grid-line"></p>
            <data-tables layout='table, pagination' :table-props='swcatGrid.tableProps' :data='swcatGrid.data' :filters='swcatGrid.filters' :page-size='swcatGrid.pageProps.pageSize' :pagination-props='swcatGrid.pageProps'>
              <el-table-column :prop='swcatGrid.titles[0].prop' :label='labels.swcatId'        sortable='custom' :width='swcatGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='swcatGrid.titles[1].prop' :label='labels.classstructure' sortable='custom' :width='swcatGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='swcatGrid.titles[2].prop' :label='labels.publisher'      sortable='custom' :width='swcatGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='swcatGrid.titles[3].prop' :label='labels.name'           sortable='custom' ></el-table-column>
              <el-table-column :prop='swcatGrid.titles[4].prop' :label='labels.cattype'        sortable='custom' :width='swcatGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='swcatGrid.titles[5].prop' :label='labels.version'        sortable='custom' :width='swcatGrid.titles[5].width'></el-table-column>
              <el-table-column align="right" width="80">
                <template  slot-scope="props">
                  <el-button  v-if="visibleDeleteSwcat" v-on:click="delSwcat(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column align="right" width="80">
                <template  slot-scope="props">
                  <el-button v-on:click="moveSwcat(props.row)" :title="labels.moveswcat" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_search_login.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </div>
      </div>
    </b-row>
     <!-- Swcat Dialog -->
    <LicenseSwcatDialog
      ref="licenseSwcatDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showLicenseSwcatDialogFlag"
      v-bind:param="{swlicenseId: listGrid.currentData.swlicenseId}"
      v-on:add="addSwcat"
      v-on:close="closeLicenseSwcatDialog"
    />
  </div>
</template>
<script>
import LicenseDetailDefault from './LicenseDetailDefault'
import LicenseSwcatDialog from './LicenseSwcatDialog'
export default {
  props: ['labels', 'authorities', 'comboData', 'detailType', 'validMsg', 'listGrid', 'swcatGrid', 'getSwcatList'],
  components: {
    'LicenseDetailDefault': LicenseDetailDefault,
    'LicenseSwcatDialog': LicenseSwcatDialog
  },
  data () {
    return {
      showLicenseSwcatDialogFlag: false
    }
  },
  computed: {
    visibleAddSwcat: function () {
      return this.authorities.LICENSE_SWCAT_ADD && this.detailType === 'UPDATE'
    },
    visibleDeleteSwcat: function () {
      return this.authorities.LICENSE_SWCAT_DELETE
    }
  },
  methods: {
    saveSwcat: function () {
      this.addSwcat(this.swcatGrid.data)
    },
    openLicenseSwcatDialog: function () {
      this.showLicenseSwcatDialogFlag = true
    },
    addSwcat: function (selection) {
      let dataList = []
      selection.forEach(select => {
        let data = {
          orgId: this.listGrid.currentData.orgId,
          swlicenseId: this.listGrid.currentData.swlicenseId,
          swcatId: select.swcatId
        }
        dataList.push(data)
      })
      if (dataList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.$http.post(
        '/episode/api/asset/licensemgmnt/license/swcat'
        , dataList
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.getSwcatList(this.listGrid.currentData.id)
        this.showLicenseSwcatDialogFlag = false
      }).catch(error => {
        console.log(error)
      })
    },
    closeLicenseSwcatDialog: function () {
      this.showLicenseSwcatDialogFlag = false
    },
    delSwcat: function (index, row) {
      if (this.$util.isNull(row.id)) {
        this.swcatGrid.data.splice(index, 1)
      } else {
        this.$http.delete(
          '/episode/api/asset/licensemgmnt/license/swcat/' + row.relId
        ).then(res => {
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.getSwcatList(this.listGrid.currentData.id)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },
    moveSwcat: function (row) {
      this.$http.get(
        '/episode/api/asset/management/swcat/rootdata/?orgId=' + row.orgId + '&hierarchypath=' + row.hierarchypath
      ).then(res => {
        this.$util.move(this, 'SWCAT', res.data[0].id)
      }).catch(error => {
        console.log(error)
      })
    },
    enterSearch: function () {
      this.swcatGrid.filters[0].value = this.swcatGrid.searchParam
    }
  },
  created: function () {}
}
</script>
