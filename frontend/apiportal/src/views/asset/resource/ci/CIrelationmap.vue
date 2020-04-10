<!--
 Class Name  : CIrelationmap.vue
 Description : IT 자원 > 분류 관계 정의 맵
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
    <el-dialog :title="labels.relationmapDialogTitle" width=850px :visible.sync="showDialogFlag" v-on:close="close">
      <b-row>
        <b-col sm="12">
          <b-row>
            <div class="form-group col-md-3">
              <label for="openClassDialog">{{labels.sourceclass}}<span class="require-mark">*</span></label>
              <div class="search-component sm">
                <input id="openClassDialog" v-model="regData.sourceclassNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
                <a class="search-button cursor-pointer" v-on:click="openClassDialog('source')">search</a>
              </div>
            </div>
            <div class="form-group col-md-3">
              <label for="targetclass">{{labels.targetclass}}<span class="require-mark">*</span></label>
              <div class="search-component sm">
                <input id="targetclass" v-model="regData.targetclassNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
                <a class="search-button cursor-pointer" v-on:click="openClassDialog('target')">search</a>
              </div>
            </div>
            <div class="form-group col-md-3">
              <label for="relation">{{labels.relationname}}<span class="require-mark">*</span></label>
              <el-select v-model="regData.relationname" :placeholder="labels.selectData">
                <el-option v-for="relation in relationSelect" :prop="relation.key" :value="relation.value" :key="relation.key">
                  {{relation.value}}
                </el-option>
              </el-select>
            </div>
            <!-- <div class="form-group col-md-1">
              <label for="description">{{labels.childrenCreate}}</label>
              <el-checkbox v-model="regData.childrenYn" sourceclass />
            </div> -->
            <div class="form-group col-md-2" style="padding-top:20px;">
              <b-button variant="primary" class="px-3 sm" v-on:click="make" v-show="visibleAddRelationMap">{{labels.add}}</b-button>
            </div>
          </b-row>
          <b-row>
            <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
          </b-row>
          <b-row>
            <b-col sm="11">
              <div class="search-component">
                <input class="form-control mt-1" style="width: 100%;" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch"/>
              </div>
            </b-col>
            <b-col>
              <div class="float-right mb-1" role="group">
                <el-button v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0 0 0 0; width:40px; height: 40px;">
                  <img src="../../../../assets/img/icons/icon_search_download.png" width="60%" />
                </el-button>
              </div>
            </b-col>
          </b-row>
          <b-row>
            <b-col sm="12">
              <p class="grid-line"></p>
              <data-tables layout='table, pagination' v-on:filtered-data="handleFilteredData"
                :table-props='listGrid.tableProps' :data='listGrid.data'
                :filters='listGrid.filters' :page-size='listGrid.pageProps.pageSize'
                :pagination-props='listGrid.pageProps' :current-page.sync='listGrid.currentPage'>
                <el-table-column :prop='listGrid.titles[0].prop' :label='labels.sourceclass'      sortable='custom' ></el-table-column>
                <el-table-column :prop='listGrid.titles[1].prop' :label='labels.targetclass'      sortable='custom' ></el-table-column>
                <el-table-column :prop='listGrid.titles[2].prop' :label='labels.relationname'     sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                <el-table-column align="right" width="80">
                  <template  slot-scope="props">
                    <el-button v-show="visibleDeleteRelationMap" v-on:click="delRelationmap(props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                      <img src="../../../../assets/img/icons/icon_delete.png" width="60%" />
                    </el-button>
                  </template>
                </el-table-column>
              </data-tables>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="showDialogFlag = false">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>
    <!-- Class Dialog -->
    <Class
      ref="classDialog"
      v-bind:showDialogFlagProp="showClassDialogFlag"
      v-on:confirm="confirmClass"
      v-on:close="closeClassDialog"
    />
  </div>
</template>
<script>
import Class from '@/views/common/Class'
export default {
  name: 'CI',
  props: ['labels', 'showDialogFlagProp', 'authorities'],
  components: {
    'Class': Class
  },
  data () {
    return {
      listGrid: {
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
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        dataBak: [],
        filters: [{
          prop: ['sourceclassNm', 'targetclassNm', 'relationname'],
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'sourceclassNm',
          width: '200px'
        }, {
          prop: 'targetclassNm',
          width: '200px'
        }, {
          prop: 'relationname',
          width: '100px'
        }],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      regData: {
        orgId: '',
        childrenYn: false,
        sourceclass: '',
        targetclass: '',
        relationname: '',
        createId: '',
        updateId: ''
      },
      // Gloval var
      orgId: '',
      classtype: '',
      // Gloval Flag
      showDialogFlag: false,
      showClassDialogFlag: false,
      // Combo Data
      relationSelect: []
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.initialize()
        this.listGrid.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.regData.sourceclass = ''
        this.regData.targetclass = ''
        this.regData.relationname = ''
        this.regData.childrenYn = false
        this.getInitData()
      }
    }
  },
  computed: {
    visibleAddRelationMap: function () {
      return this.authorities.CI_RELATION_MAP_ADD
    },
    visibleDeleteRelationMap: function () {
      return this.authorities.CI_RELATION_MAP_DELETE
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
      this.regData.orgId = this.orgId
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'relationSelect', domainId: 'CIRELATION'}
        ]
      }
      this.$util.getComboData(param)
      this.getListData()
    },
    getListData: function () {
      this.$http.get(
        '/episode/api/asset/resource/ci/cirelationmap'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    make: function () {
      let sourceclass = this.regData.sourceclass
      let targetclass = this.regData.targetclass
      let relationname = this.regData.relationname
      if (this.$util.isNull(sourceclass) || this.$util.isNull(targetclass) || this.$util.isNull(relationname)) {
        this.$util.esmessage(this, 'check', this.labels.inputRequire)
        return
      } else if (sourceclass === targetclass) {
        this.$util.esmessage(this, 'check', this.labels.notDuplicate)
        return
      }
      let chekList = this.listGrid.data.filter(data => {
        return (data.sourceclass === this.regData.sourceclass && data.targetclass === this.regData.targetclass && data.relationname === this.regData.relationname) ||
               (data.sourceclass === this.regData.targetclass && data.targetclass === this.regData.sourceclass && data.relationname === this.regData.relationname)
      })
      if (chekList.length > 0) {
        this.$util.esmessage(this, 'check', this.labels.existDuplicate)
        return
      }
      this.$http.post(
        '/episode/api/asset/resource/ci/cirelationmap',
        this.regData
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.regData.sourceclass = ''
        this.regData.targetclass = ''
        this.regData.relationname = ''
        this.getListData()
      }).catch(error => {
        console.log(error)
      })
    },
    delRelationmap: function (row) {
      this.$http.delete('/episode/api/asset/resource/ci/cirelationmap/' + row.id).then(res => {
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        this.getListData()
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    },
    close: function () {
      this.$emit('close')
    },
    openClassDialog: function (val) {
      this.showClassDialogFlag = true
      this.classtype = val
    },
    confirmClass: function (rslt) {
      if (this.classtype === 'source') {
        this.regData.sourceclass = rslt.classstructureId
        this.regData.sourceclassNm = rslt.classstructureNm
      } else {
        this.regData.targetclass = rslt.classstructureId
        this.regData.targetclassNm = rslt.classstructureNm
      }
      this.showClassDialogFlag = false
    },
    closeClassDialog: function () {
      this.showClassDialogFlag = false
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    exportFilter: function () {
      let fileName = 'relation map'
      let fields = [
        {value: 'sourceclassNm', label: this.labels.sourceclass},
        {value: 'targetclassNm', label: this.labels.targetclass},
        {value: 'relationname', label: this.labels.relationname}
      ]
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    }
  },
  created: function () {}
}
</script>
