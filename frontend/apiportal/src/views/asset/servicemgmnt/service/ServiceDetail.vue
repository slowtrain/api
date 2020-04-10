<!--
 Class Name  : ServiceDetail.vue
 Description : 서비스 > 상세
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.04  최영훈  최초 생성
-->
<template>
  <div>
   <ServiceDetailDefault
      ref="serviceDetailDefault"
      v-if="(activeTab !== 'list')"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:classType="classType"
      v-bind:listGrid="listGrid"
    />
    <b-row>
      <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class='col'>
        <!-- Spec -->
        <b-row v-show="false">
          <div class="form-group col-md-2">
            <label>{{labels.displayAll}}</label>
            <el-switch v-model="displayAllFlag" class="formdata sm"></el-switch>
          </div>
        </b-row>
        <ServiceDetailSpec
          ref="serviceDetailSpec"
          v-show="currentView === 'ServiceDetailSpec'"
          v-bind:is="currentView"
          v-bind:labels="labels"
          v-bind:comboData="comboData"
          v-bind:detailType="detailType"
          v-bind:listGrid="listGrid"
          v-bind:displayAllFlag="displayAllFlag"
        />
        <ServiceDetailSpecBusinessSystem
          ref="serviceDetailSpecBusinessSystem"
          v-show="currentView === 'ServiceDetailSpecBusinessSystem'"
          v-bind:is="currentView"
          v-bind:labels="labels"
          v-bind:comboData="comboData"
          v-bind:detailType="detailType"
          v-bind:listGrid="listGrid"
          v-bind:displayAllFlag="displayAllFlag"
        />
        <ServiceDetailSpecGroup
          ref="serviceDetailSpecGroup"
          v-show="currentView === 'ServiceDetailSpecGroup'"
          v-bind:is="currentView"
          v-bind:labels="labels"
          v-bind:comboData="comboData"
          v-bind:detailType="detailType"
          v-bind:listGrid="listGrid"
          v-bind:displayAllFlag="displayAllFlag"
        />
        <b-row>
          <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
        </b-row>
        <b-row>
          <!-- Spec List -->
          <b-col v-bind:sm="((listGrid.currentData.classstructureId === 'GROUP_1') && (listGrid.currentData.extbool5)) ? 6:12">
            <b-row>
              <div class="col" style="padding-top:25px; padding-left:20px;">
                <a class="title-box">{{labels.addSpec}}</a>
              </div>
              <div class="col">
                <div class="float-right mt-3" role="group">
                  <b-button variant="primary" class="px-3 sm" v-show="visibleAddSpec" v-on:click="openSpecSrchDialog" >{{labels.add}}</b-button>
                </div>
              </div>
            </b-row>
            <div class='row mt-2'>
              <div class='col'>
                <p class="grid-line"></p>
                <data-tables layout='table, pagination' :table-props='specGrid.tableProps' :data='specGrid.data' :filters='specGrid.filters' :page-size='specGrid.pageProps.pageSize' :pagination-props='specGrid.pageProps'>
                  <el-table-column :prop='specGrid.titles[0].prop' :label='labels.spec' sortable='custom'></el-table-column>
                  <el-table-column :prop='specGrid.titles[1].prop' :label='labels.value' sortable='custom'>
                    <template slot-scope="props">
                      <input v-show="props.row.type === 'ALNVALUE'" class="form-control formdata sm" type="text" v-model="props.row.alnvalue" v-on:change="changeRow(props.row)" v-bind:maxlength="50"/>
                      <input v-show="props.row.type === 'NUMVALUE'" class="form-control formdata sm" type="text" v-model="props.row.numvalue" v-on:change="changeRow(props.row)" v-on:keydown="$util.onlyNumber($event)" v-bind:maxlength="50"/>
                    </template>
                  </el-table-column>
                  <el-table-column align="right" width="80">
                    <template  slot-scope="props">
                      <el-button v-show="visibleDeleteSpec" v-on:click="delSpec(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                        <img src="../../../../assets/img/icons/icon_delete.png" width="60%" />
                      </el-button>
                    </template>
                  </el-table-column>
                </data-tables>
              </div>
            </div>
          </b-col>
          <!--rpa test-->
          <b-col v-show="(listGrid.currentData.classstructureId === 'GROUP_1') && (listGrid.currentData.extbool5)" sm='6'>
            <b-row>
              <div class="col" style="padding-top:25px; padding-left:20px;">
                <a class="title-box">{{labels.rparespmap}}</a>
              </div>
              <div class="col">
                <div class="float-right mt-3" role="group">
                  <b-button variant="primary" class="px-3 sm" v-show="visibleAddRpa" v-on:click="addRparespmap" >{{labels.add}}</b-button>
                </div>
              </div>
            </b-row>
            <div class='row mt-2'>
              <div class='col'>
                <p class="grid-line"></p>
                <data-tables layout='table, pagination' :table-props='rparespmapGrid.tableProps' :data='rparespmapGrid.data' :filters='rparespmapGrid.filters' :page-size='rparespmapGrid.pageProps.pageSize' :pagination-props='rparespmapGrid.pageProps'>
                  <el-table-column :prop='rparespmapGrid.titles[0].prop' :label='labels.exetools' sortable='custom'>
                    <template slot-scope="props">
                      <el-select v-model="props.row.exetools" :placeholder="labels.selectData" v-on:change="validRparespmap(props.$index, props.row)" v-bind:disabled="!$util.isNull(props.row.id)">
                        <el-option v-for="exetool in comboData.exetoolsSelect" :prop="exetool.code" :value="exetool.code" :key="exetool.code">
                          {{exetool.code}}
                        </el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column :prop='rparespmapGrid.titles[1].prop' :label='labels.linebizstep' sortable='custom'>
                    <template slot-scope="props">
                      <el-select v-model="props.row.linebizstep" :placeholder="labels.selectData" v-on:change="validRparespmap(props.$index, props.row)" v-bind:disabled="!$util.isNull(props.row.id)">
                        <el-option v-for="linebizstep in comboData.linebizstepSelect" :prop="linebizstep.code" :value="linebizstep.code" :key="linebizstep.code">
                          {{linebizstep.code}}
                        </el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column align="right" width="80">
                    <template  slot-scope="props">
                      <el-button v-show="visibleDeleteRpa" v-on:click="delRparespmap(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                        <img src="../../../../assets/img/icons/icon_delete.png" width="60%" />
                      </el-button>
                    </template>
                  </el-table-column>
                </data-tables>
              </div>
            </div>
          </b-col>
        </b-row>
      </div>
    </b-row>
    <!-- SpecDialog-->
    <el-dialog :title="labels.spec" :visible.sync="showSpecSrchDialogFlag" width=800px>
      <b-row>
        <b-col>
          <b-row>
            <b-col sm="12">
              <div class="search-component">
                <input class="form-control mt-1" style="width: 100%;" v-model="specSrchGrid.searchParam" v-on:keyup.enter="enterSearchSpec" v-bind:placeholder=labels.search />
              </div>
            </b-col>
          </b-row>
          <b-row>
            <b-col sm="12">
              <p class="grid-line"></p>
              <data-tables style="width: 100%" layout='table, pagination'
                :table-props='specSrchGrid.tableProps'
                :page-size='specSrchGrid.pageProps.pageSize'
                :data='specSrchGrid.data'
                :filters='specSrchGrid.filters'
                :pagination-props='specSrchGrid.pageProps'
                @selection-change="selectSpecSrchGridRow"
                :current-page.sync='specSrchGrid.currentPage'>
                <el-table-column type="selection" width="50"></el-table-column>
                <el-table-column :prop='specSrchGrid.titles[1].prop' :label='labels.spec'        :width='specSrchGrid.titles[1].width' sortable='custom' ></el-table-column>
                <el-table-column :prop='specSrchGrid.titles[2].prop' :label='labels.description' sortable='custom' ></el-table-column>
                <el-table-column :prop='specSrchGrid.titles[3].prop' :label='labels.measureunit' :width='specSrchGrid.titles[3].width' sortable='custom' ></el-table-column>
                <el-table-column :prop='specSrchGrid.titles[4].prop' :label='labels.spectype'    :width='specSrchGrid.titles[4].width' sortable='custom' ></el-table-column>
              </data-tables>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="addSpec" >{{labels.add}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-on:click="closeSpecSrchDialog">{{labels.close}}</b-button>
          </div>
        </div>
    </b-row>
    </el-dialog>
  </div>
</template>
<script>
import ServiceDetailDefault from './ServiceDetailDefault'
import ServiceDetailSpec from './ServiceDetailSpec'
import ServiceDetailSpecBusinessSystem from './ServiceDetailSpecBusinessSystem'
import ServiceDetailSpecGroup from './ServiceDetailSpecGroup'
export default {
  props: ['labels', 'activeTab', 'authorities', 'comboData', 'detailType', 'classType', 'listGrid', 'specGrid', 'rparespmapGrid', 'relationTabGrid', 'getRpareapmapData'],
  components: {
    'ServiceDetailDefault': ServiceDetailDefault,
    'ServiceDetailSpec': ServiceDetailSpec,
    'ServiceDetailSpecBusinessSystem': ServiceDetailSpecBusinessSystem,
    'ServiceDetailSpecGroup': ServiceDetailSpecGroup
  },
  data () {
    return {
      specSrchGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 20, 30]
        },
        data: [],
        filters: [{
          prop: ['classspecId', 'description'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'chk'
        }, {
          prop: 'classspecId',
          width: '150px'
        }, {
          prop: 'description',
          width: '150px'
        }, {
          prop: 'measureunit',
          width: '110px'
        }, {
          prop: 'type',
          width: '150px'
        }],
        currentData: {},
        total: 0,
        selectedItem: []
      },
      pageMapList: [
        {key: '/BUSINESSSYSTEM/', objNm: 'ServiceDetailSpecBusinessSystem'},
        {key: '/GROUP_1/', objNm: 'ServiceDetailSpecGroup'}
      ],
      currentView: null,
      activeTabDetail: 'spec',
      showSpecSrchDialogFlag: false,
      displayAllFlag: false
    }
  },
  watch: {
    'listGrid.currentData.classstructureId': function (val) {
      this.makeSpec()
    },
    displayAllFlag: function (val) {
      if (val) {
        this.currentView = 'ServiceDetailSpec'
      } else {
        this.makeSpec()
      }
    }
  },
  computed: {
    visibleAddSpec: function () {
      return this.authorities.SERVICE_SPEC_ADD && this.detailType === 'UPDATE'
    },
    visibleDeleteSpec: function () {
      return this.authorities.SERVICE_SPEC_DELETE
    },
    visibleAddRpa: function () {
      return this.authorities.SERVICE_RPA_ADD && this.detailType === 'UPDATE'
    },
    visibleDeleteRpa: function () {
      return this.authorities.SERVICE_RPA_DELETE && this.detailType === 'UPDATE'
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {},
    // USER FN ////////////////////////////////////////////
    fltrRelation: function (data, param) {
      let rtnData = data.filter((data) => {
        return data[param.fltrCol] === param.fltrStr
      })
      return rtnData
    },
    makeSpec: function () {
      let hierarchypath = this.listGrid.currentData.hierarchypath
      if (this.$util.isNull(hierarchypath)) {
        return
      }
      let fltrObj = this.pageMapList.filter(data => {
        return hierarchypath.indexOf(data.key) > -1
      })
      if (fltrObj.length > 0 && !this.displayAllFlag) {
        this.currentView = fltrObj[0].objNm
      } else {
        this.currentView = 'ServiceDetailSpec'
      }
    },
    // TRNS FN ////////////////////////////////////////////
    getSpecData: function (id) {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/' + id + '/cispec'
      ).then(res => {
        this.specGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getSpecSrchListData: function (id) {
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service/' + id + '/cispecSrch/'
      ).then(res => {
        this.specSrchGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    transSpec: function (dataList) {
      this.$http.post(
        '/episode/api/asset/servicemgmnt/service/cispec'
        , dataList
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.getSpecData(this.listGrid.currentData.id)
        this.showSpecSrchDialogFlag = false
      }).catch(error => {
        console.log(error)
      })
    },
    delSpec: function (index, row) {
      if (this.$util.isNull(row.id)) {
        this.specGrid.data.splice(index, 1)
      } else {
        this.$http.delete(
          '/episode/api/asset/servicemgmnt/service/cispec/' + row.id
        ).then(res => {
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.getSpecData(this.listGrid.currentData.id)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },
    // EVENT FN ///////////////////////////////////////////
    openSpecSrchDialog: function () {
      this.getSpecSrchListData(this.listGrid.currentData.id)
      this.specSrchGrid.filters[0].value = ''
      this.showSpecSrchDialogFlag = true
      this.specSrchGrid.currentPage = 1
    },
    changeRow: function (row) {
      if (!this.$util.isNull(row.id)) {
        row.state = 'CHANGED'
      }
    },
    selectSpecSrchGridRow: function (selectedList) {
      this.specSrchGrid.selectedItem = selectedList
    },
    addSpec: function () {
      let dataList = []
      this.specSrchGrid.selectedItem.forEach(spec => {
        let data = {
          orgId: this.listGrid.currentData.orgId,
          classstructureId: this.listGrid.currentData.classstructureId,
          ciId: this.listGrid.currentData.ciId,
          classspecId: spec.classspecId,
          type: spec.type
        }
        dataList.push(data)
      })
      if (dataList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.transSpec(dataList)
    },
    closeSpecSrchDialog: function () {
      this.showSpecSrchDialogFlag = false
    },
    saveSpec: function () {
      let dataList = this.specGrid.data.filter((data, index) => {
        return (data.state === 'CHANGED' || data.state === 'INSERT')
      })
      this.transSpec(dataList)
    },
    addRparespmap: function () {
      this.rparespmapGrid.data.splice(0, 0, {
        ciId: this.listGrid.currentData.ciId
      })
    },
    delRparespmap: function (idx, row) {
      if (this.$util.isNull(row.id)) {
        this.rparespmapGrid.data.splice(idx, 1)
      } else {
        this.$http.delete(
          '/episode/api/asset/servicemgmnt/service/rparespmap/' + row.id
        ).then(res => {
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.getRpareapmapData(this.listGrid.currentData.id)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },
    validRparespmap: function (idx, row) {
      let dupChkList = this.rparespmapGrid.data.filter((data, i) => {
        if (i !== idx) {
          return data.exetools === row.exetools && data.linebizstep === row.linebizstep
        }
        return false
      })
      if (dupChkList.length > 0) {
        row.exetools = ''
        row.linebizstep = ''
        this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
      }
    },
    enterSearchSpec: function () {
      this.specSrchGrid.filters[0].value = this.specSrchGrid.searchParam
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
