<!--
 Class Name  : CIDetail.vue
 Description : IT 자원 > 상세
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
   <CIDetailDefault
      ref="ciDetailDefault"
      v-if="(activeTab !== 'list')"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:listGrid="listGrid"
    />
    <b-row>
      <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class='col'>
        <!-- Spec -->
        <b-row v-if="false">
          <div class="form-group col-md-2">
            <label>{{labels.displayAll}}</label>
            <el-switch v-model="displayAllFlag" class="formdata sm"></el-switch>
          </div>
        </b-row>
        <CIDetailSpecComputerSystem
          ref="ciedDetailSpecComputerSystem"
          v-if="(activeTab !== 'list') && (currentView === 'CIDetailSpecComputerSystem')"
          v-bind:labels="labels"
          v-bind:comboData="comboData"
          v-bind:detailType="detailType"
          v-bind:listGrid="listGrid"
        />
        <CIDetailSpecNetStorSec
          ref="ciDetailSpecNetStorSec"
          v-if="(activeTab !== 'list') && (currentView === 'CIDetailSpecNetStorSec')"
          v-bind:labels="labels"
          v-bind:comboData="comboData"
          v-bind:detailType="detailType"
          v-bind:listGrid="listGrid"
        />
        <CIDetailSpecApplication
          ref="ciDetailSpecApplication"
          v-if="(activeTab !== 'list') && (currentView === 'CIDetailSpecApplication')"
          v-bind:labels="labels"
          v-bind:comboData="comboData"
          v-bind:detailType="detailType"
          v-bind:listGrid="listGrid"
        />
        <CIDetailSpecSoftware
          ref="ciDetailSpecSoftware"
          v-if="(activeTab !== 'list') && (currentView === 'CIDetailSpecSoftware')"
          v-bind:labels="labels"
          v-bind:comboData="comboData"
          v-bind:detailType="detailType"
          v-bind:listGrid="listGrid"
        />
        <CIDetailSpec
          ref="ciDetailSpec"
          v-if="(activeTab !== 'list') && (currentView === 'CIDetailSpec')"
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
          <b-col v-bind:sm="(listGrid.currentData.classstructureId === 'RPAAPPLICATION') ? 6:12">
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
            <b-row class='mt-2'>
              <div class='col'>
                <p class="grid-line"></p>
                <data-tables v-if="(activeTab !== 'list')" layout='table, pagination' :table-props='specGrid.tableProps' :data='specGrid.data' :filters='specGrid.filters' :page-size='specGrid.pageProps.pageSize' :pagination-props='specGrid.pageProps'>
                  <el-table-column :prop='specGrid.titles[0].prop' :label='labels.spec' sortable='custom'></el-table-column>
                  <el-table-column :prop='specGrid.titles[1].prop' :label='labels.value' sortable='custom'>
                    <template slot-scope="props">
                      <input v-show="props.row.type === 'ALNVALUE'" class="form-control formdata sm" type="text" v-model="props.row.alnvalue" v-on:change="changeValue(props.row)" v-bind:maxlength="50"/>
                      <input v-show="props.row.type === 'NUMVALUE'" class="form-control formdata sm" type="text" v-model="props.row.numvalue" v-on:change="changeValue(props.row)" v-on:keydown="$util.onlyNumber($event)" v-bind:maxlength="50"/>
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
            </b-row>
          </b-col>
          <!--rpa test-->
          <b-col v-show="(listGrid.currentData.classstructureId === 'RPAAPPLICATION')" sm='6'>
            <b-row>
              <div class="col" style="padding-top:25px; padding-left:20px;">
                <a class="title-box">{{labels.rparespmap}}</a>
              </div>
              <div class="col">
                <div class="float-right mt-3" role="group">
                  <b-button variant="primary" class="px-3 sm" v-show="visibleAddRpa" v-on:click="addRparespmap">{{labels.add}}</b-button>
                </div>
              </div>
            </b-row>
            <div class='row mt-2'>
              <div class='col'>
                <p class="grid-line"></p>
                <data-tables v-if="(activeTab !== 'list')" layout='table, pagination' :table-props='rparespmapGrid.tableProps' :data='rparespmapGrid.data' :filters='rparespmapGrid.filters' :page-size='rparespmapGrid.pageProps.pageSize' :pagination-props='rparespmapGrid.pageProps'>
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
                      <el-button v-show="visibleDeleteRpa"  v-on:click="delRparespmap(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
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
              <data-tables v-if="(activeTab !== 'list')" style="width: 100%" layout='table, pagination'
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
import CIDetailDefault from './CIDetailDefault'
import CIDetailSpecComputerSystem from './CIDetailSpecComputerSystem'
import CIDetailSpecNetStorSec from './CIDetailSpecNetStorSec'
import CIDetailSpecApplication from './CIDetailSpecApplication'
import CIDetailSpecSoftware from './CIDetailSpecSoftware'
import CIDetailSpec from './CIDetailSpec'
export default {
  props: ['activeTab', 'labels', 'authorities', 'comboData', 'detailType', 'listGrid', 'specGrid', 'rparespmapGrid'],
  components: {
    'CIDetailDefault': CIDetailDefault,
    'CIDetailSpecComputerSystem': CIDetailSpecComputerSystem,
    'CIDetailSpecNetStorSec': CIDetailSpecNetStorSec,
    'CIDetailSpecApplication': CIDetailSpecApplication,
    'CIDetailSpecSoftware': CIDetailSpecSoftware,
    'CIDetailSpec': CIDetailSpec
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
        selectedItem: [],
        searchParam: ''
      },
      pageMapList: [
        {key: '/HARDWARE/SERVER/', objNm: 'CIDetailSpecComputerSystem'},
        {key: '/HARDWARE/NETWORK/', objNm: 'CIDetailSpecNetStorSec'},
        {key: '/HARDWARE/STORAGE/', objNm: 'CIDetailSpecNetStorSec'},
        {key: '/HARDWARE/SECURE_HW/', objNm: 'CIDetailSpecNetStorSec'},
        {key: '/HARDWARE/BACKUP_HW/', objNm: 'CIDetailSpecNetStorSec'},
        {key: '/GROUP_1/', objNm: 'CIDetailSpecApplication'},
        {key: '/SOFTWARE/', objNm: 'CIDetailSpecSoftware'}
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
        this.currentView = 'CIDetailSpec'
      } else {
        this.makeSpec()
      }
    }
  },
  computed: {
    visibleAddSpec: function () {
      return this.authorities.CI_SPEC_ADD && this.detailType === 'UPDATE'
    },
    visibleDeleteSpec: function () {
      return this.authorities.CI_SPEC_DELETE
    },
    visibleAddRpa: function () {
      return this.authorities.CI_RPA_ADD && this.detailType === 'UPDATE'
    },
    visibleDeleteRpa: function () {
      return this.authorities.CI_RPA_DELETE && this.detailType === 'UPDATE'
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.makeSpec()
    },
    // USER FN ////////////////////////////////////////////
    fltrRelation: function (data, param) {
      let rtnData = data.filter((data) => {
        return data[param.fltrCol] === param.fltrStr
      })
      return rtnData
    },
    showPartTab: function () {
      if (this.$util.isNull(this.listGrid.currentData.hierarchypath)) {
        return
      }
      return this.listGrid.currentData.hierarchypath.indexOf('/SERVER/') > -1
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
        this.currentView = 'CIDetailSpec'
      }
    },
    // TRNS FN ////////////////////////////////////////////
    getSpecData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/cispec'
      ).then(res => {
        this.specGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getSpecSrchListData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/cispecSrch/'
      ).then(res => {
        this.specSrchGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRpareapmapData: function (id) {
      this.$http.get(
        '/episode/api/asset/resource/ci/' + id + '/rparespmap'
      ).then(res => {
        this.rparespmapGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    transSpec: function (dataList) {
      this.$http.post(
        '/episode/api/asset/resource/ci/cispec'
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
          '/episode/api/asset/resource/ci/cispec/' + row.id
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
    changeValue: function (row) {
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
          '/episode/api/asset/resource/ci/rparespmap/' + row.id
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
    enterSearchRelationSw: function () {
      this.relationSwGrid.filters[0].value = this.relationSwGrid.searchParam
    },
    enterSearchItsmSpec: function () {
      this.itsmSpecGrid.filters[0].value = this.itsmSpecGrid.searchParam
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
