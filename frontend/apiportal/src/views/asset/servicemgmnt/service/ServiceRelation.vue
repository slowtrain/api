<!--
 Class Name  : ServiceRelation.vue
 Description : 서비스 > 관련 정보
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.04  최영훈  최초 생성
-->
<template>
  <div>
    <ServiceDetailDefault
      ref="serviceDetailDefault"
      v-if="(activeTab !== 'list') && this.activeTabFlag.relation"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:classType="classType"
      v-bind:listGrid="listGrid"
    />
    <b-row>
      <div class="col mt-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class="col-md-12">
        <b-row>
          <div class="col" style="padding-top:25px; padding-left:20px;">
            <a class="title-box">{{labels.relci}}</a>
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
              <b-button variant="primary" class="px-3 sm" v-show="visibleAddRelation" v-on:click="openCIRelationDialog">{{labels.add}}</b-button>
            </div>
          </div>
        </div>
        <div class='row mt-2'>
          <div class='form-group col'>
            <p class="grid-line"></p>
            <data-tables v-if="(activeTab !== 'list') && this.activeTabFlag.relation" layout='table, pagination' :table-props='relationGrid.tableProps' :data='relationGrid.data' :filters='relationGrid.filters' :page-size='relationGrid.pageProps.pageSize' :pagination-props='relationGrid.pageProps' :current-page.sync='relationGrid.currentPage'>
              <el-table-column :prop='relationGrid.titles[0].prop' :label='labels.relationname'     sortable='custom' :width='relationGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[1].prop' :label='labels.sourceci'         sortable='custom' :width='relationGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[2].prop' :label='labels.name'             sortable='custom' ></el-table-column>
              <el-table-column :prop='relationGrid.titles[3].prop' :label='labels.classstructureId' sortable='custom' :width='relationGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[4].prop' :label='labels.targetci'         sortable='custom' :width='relationGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[5].prop' :label='labels.name'             sortable='custom' ></el-table-column>
              <el-table-column :prop='relationGrid.titles[6].prop' :label='labels.classstructureId' sortable='custom' :width='relationGrid.titles[6].width'></el-table-column>
              <el-table-column prop="type" :label="labels.status" >
                <template slot-scope="props">
                  <el-select v-model="props.row.status" :placeholder="labels.selectData" v-on:change="changeRow(props.row)" :disabled="!props.row.userdefine" >
                    <el-option v-for="status in comboData.relationStatusSelect" :prop="status.value" :value="status.value" :key="status.key">
                      {{status.value}}
                    </el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column align="right" width="40">
                <template  slot-scope="props">
                  <el-button v-on:click="moveService(props.row)" :title="labels.move" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_search_login.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column align="right" width="40">
                <template  slot-scope="props">
                  <el-button v-show="visibleDeleteRelation" v-on:click="delRelation(props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </div>
      </div>
    </b-row>
    <!-- CI Dialog -->
    <ServiceRelationDialog
      ref="serviceRelationDialog"
      v-if="(activeTab !== 'list') && this.activeTabFlag.relation"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showServiceRelationDialogFlag"
      v-bind:param="{classstructureId: listGrid.currentData.classstructureId, ciId: listGrid.currentData.ciId, hierarchypath: listGrid.currentData.hierarchypath, object: object}"
      v-on:add="addCI"
      v-on:close="closeCIRelationDialog"
    />
  </div>
</template>
<script>
import ServiceDetailDefault from './ServiceDetailDefault'
import ServiceRelationDialog from './ServiceRelationDialog'
export default {
  props: ['labels', 'activeTab', 'authorities', 'comboData', 'detailType', 'classType', 'listGrid', 'relationGrid', 'getRelationData', 'activeTabFlag'],
  components: {
    'ServiceDetailDefault': ServiceDetailDefault,
    'ServiceRelationDialog': ServiceRelationDialog
  },
  data () {
    return {
      // Global var
      object: '',
      // Global Flag
      showServiceRelationDialogFlag: false
    }
  },
  computed: {
    visibleAddRelation: function () {
      return this.authorities.SERVICE_RELATION_ADD && this.detailType === 'UPDATE'
    },
    visibleDeleteRelation: function () {
      return this.authorities.SERVICE_RELATION_DELETE && this.listGrid.currentData.userdefine && this.detailType === 'UPDATE'
    }
  },
  methods: {
    openCIRelationDialog: function () {
      this.object = (this.classType === 'BUSINESSSYSTEM' ? 'SERVICE' : 'SERVICECI')
      this.showServiceRelationDialogFlag = true
    },
    changeRow: function (row) {
      if (!this.$util.isNull(row.id)) {
        row.state = 'CHANGED'
      }
    },
    addCI: function (selectedCI) {
      let dataList = []
      let chkData = selectedCI.filter((data) => this.$util.isNull(data.classType))
      if (chkData.length > 0) {
        this.$util.esmessage(this, 'check', this.labels.nullClassTypeMessage)
        return
      }
      selectedCI.forEach(select => {
        let data = {
          orgId: select.orgId,
          ciId: this.listGrid.currentData.ciId,
          status: 'ACTIVE',
          relationname: 'INCLUDE',
          userdefine: true
        }
        if (select.classType === 'TARGET') {
          data.sourceci = this.listGrid.currentData.ciId
          data.targetci = select.ciId
        } else {
          data.sourceci = select.ciId
          data.targetci = this.listGrid.currentData.ciId
        }
        dataList.push(data)
      })
      if (dataList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.$http.post(
        '/episode/api/asset/servicemgmnt/service/cirelation'
        , dataList
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.getRelationData(this.listGrid.currentData.id)
        this.showServiceRelationDialogFlag = false
      }).catch(error => {
        console.log(error)
      })
    },
    save: function () {
      let dataList = this.relationGrid.data.filter(data => {
        return data.state === 'CHANGED'
      })
      this.$http.post(
        '/episode/api/asset/servicemgmnt/service/cirelation'
        , dataList
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.getRelationData(this.listGrid.currentData.id)
      }).catch(error => {
        console.log(error)
      })
    },
    closeCIRelationDialog: function () {
      this.showServiceRelationDialogFlag = false
    },
    delRelation: function (row) {
      this.$http.delete(
        '/episode/api/asset/servicemgmnt/service/cirelation?id=' + row.id + '&ciId=' + this.listGrid.currentData.ciId
      ).then(res => {
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        this.getRelationData(this.listGrid.currentData.id)
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    },
    moveService: function (row) {
      let param = {id: ''}
      if (this.listGrid.currentData.id !== row.sourceciId) {
        param.id = row.sourceciId
      } else {
        param.id = row.targetciId
      }
      this.$emit('moveService', param)
    },
    moveCI: function (row) {
      let id = ''
      if (this.listGrid.currentData.id !== row.sourceciId) {
        id = row.sourceciId
      } else {
        id = row.targetciId
      }
      this.$util.move(this, 'CI', id)
    },
    enterSearch: function () {
      this.relationGrid.currentPage = 1
      this.relationGrid.filters[0].value = this.relationGrid.searchParam
    }
  },
  created: function () {}
}
</script>
