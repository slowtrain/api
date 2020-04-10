<!--
 Class Name  : CIDiscimap.vue
 Description : IT 자원 > DisCIMapToCI
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
    <el-dialog :title="'DisCI Map To CI'" width=1000px :visible.sync="showDialogFlag" v-on:close="close">
      <b-row>
        <b-col sm="12">
          <b-row>
            <b-col sm="12">
              <div class="comm-btn-box" role="group">
                <b-button variant="primary" class="px-3 sm" v-on:click="openDetailDialog()" v-show="visibleAddDisCIMap">{{labels.new}}</b-button>
              </div>
            </b-col>
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
              <data-tables layout='table, pagination' :table-props='listGrid.tableProps' :data='listGrid.data' :filters='listGrid.filters' :page-size='listGrid.pageProps.pageSize' :pagination-props='listGrid.pageProps' :current-page.sync='listGrid.currentPage' v-on:filtered-data="handleFilteredData">
                <el-table-column :prop='listGrid.titles[0].prop' :label='labels.dataSrc'          sortable='custom' ></el-table-column>
                <el-table-column :prop='listGrid.titles[1].prop' :label='labels.disCIType'        sortable='custom' ></el-table-column>
                <el-table-column :prop='listGrid.titles[2].prop' :label='labels.disCIClass'       sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[3].prop' :label='labels.classstructure' sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[4].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                <el-table-column align="right" width="100" >
                <template slot-scope="props">
                  <el-button v-on:click="clickGridRow(props.row)" :title="labels.detail" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_edit.png" width="60%" />
                  </el-button>
                 <el-button v-on:click="del(props.row)" v-show="visibleDeleteDisCIMap" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_delete.png" width="60%" />
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
    <!-- Detail dialog -->
    <el-dialog :title="labels.detail" :visible.sync="showDetailDialogFlag" width=800px  v-on:close="closeDetailDialog">
      <b-row>
        <div class="form-group col-md-12">
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.duplicateMsg)" v-text="validMsg.duplicateMsg"></p>
        </div>
      </b-row>
      <b-row>
        <div class="form-group col-md-3">
          <label for="openClassDialog">{{labels.dataSrc}}<span class="require-mark">*</span></label>
          <div class="search-component sm">
            <input id="openClassDialog" v-model="listGrid.currentData.dataSrc" class="formdata sm" autocomplete="off" disabled="disabled"/>
            <a class="search-button cursor-pointer" v-on:click="openDiscodeDialog('dataSrc')" v-show="this.$util.isNull(this.listGrid.currentData.id)">search</a>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.dataSrcMsg)" v-text="validMsg.dataSrcMsg"></p>
          </div>
        </div>
        <div class="form-group col-md-3">
          <label for="targetclass">{{labels.disCIType}}<span class="require-mark">*</span></label>
          <div class="search-component sm">
            <input id="targetclass" v-model="listGrid.currentData.disCIType" class="formdata sm" autocomplete="off" disabled="disabled"/>
            <a class="search-button cursor-pointer" v-on:click="openDiscodeDialog('disCIType')" v-show="this.$util.isNull(this.listGrid.currentData.id)">search</a>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.disCITypeMsg)" v-text="validMsg.disCITypeMsg"></p>
          </div>
        </div>
        <div class="form-group col-md-3">
          <label>{{labels.isVirtual}}</label>
          <el-switch v-model="listGrid.currentData.isVirtual" class="formdata sm"  :disabled="!this.$util.isNull(this.listGrid.currentData.id)"></el-switch>
        </div>
        <div class="form-group col-md-3">
          <label for="relation">{{labels.status}}</label>
          <el-select v-model="listGrid.currentData.status" :placeholder="labels.selectData">
            <el-option v-for="status in statusSelect" :prop="status.key" :value="status.value" :key="status.key">
              {{status.value}}
            </el-option>
          </el-select>
        </div>
      </b-row>
      <b-row>
        <div class="form-group col-md-6">
          <label for="targetclass">{{labels.disCIClass}}<span class="require-mark">*</span></label>
          <div class="search-component sm">
            <input id="targetclass" v-model="listGrid.currentData.disCIClass" class="formdata sm" autocomplete="off" disabled="disabled"/>
            <a class="search-button cursor-pointer" v-on:click="openDiscodeDialog('disCIClass')" v-show="this.$util.isNull(this.listGrid.currentData.id)">search</a>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.disCIClassMsg)" v-text="validMsg.disCIClassMsg"></p>
          </div>
        </div>
        <div class="form-group col-md-6">
          <label for="targetclass">{{labels.classstructure}}<span class="require-mark">*</span></label>
          <div class="search-component sm">
            <input id="targetclass" v-model="listGrid.currentData.classstructureNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
            <a class="search-button cursor-pointer" v-on:click="openClassDialog()">search</a>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.classstructureMsg)" v-text="validMsg.classstructureMsg"></p>
          </div>
        </div>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="save" v-show="visibleSaveDisCIMap">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-on:click="closeDetailDialog">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>
    <!-- Discode Dialog -->
    <el-dialog :title="labels.code" :visible.sync="showDiscodeDialogFlag" width=800px v-on:close="closeDiscodeDialog">
      <b-row>
        <b-col>
          <b-row>
            <b-col sm="12">
              <div class="search-component">
                <input class="form-control mt-1" style="width: 100%;" v-model="discodeGrid.searchParam" v-on:keyup.enter="enterSearchDiscode" v-bind:placeholder=labels.search />
              </div>
            </b-col>
          </b-row>
          <b-row>
            <b-col sm="12">
              <p class="grid-line"></p>
              <data-tables style="width: 100%" layout='table, pagination'
                :table-props='discodeGrid.tableProps'
                :page-size='discodeGrid.pageProps.pageSize'
                :data='discodeGrid.data'
                :filters='discodeGrid.filters'
                :pagination-props='discodeGrid.pageProps'
                :current-page.sync='discodeGrid.currentPage'>
                <el-table-column :width='discodeGrid.titles[0].width' >
                  <template slot-scope="props">
                    <el-checkbox class='single' v-model="props.row.chk" v-on:change="selectDiscodeGridRow(props.row)"/>
                  </template>
                </el-table-column>
                <el-table-column :prop='discodeGrid.titles[1].prop' :label='labels.code' sortable='custom' ></el-table-column>
              </data-tables>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="confirmDiscode" >{{labels.confirm}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-on:click="closeDiscodeDialog">{{labels.close}}</b-button>
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
          prop: ['dataSrc', 'disCIType', 'disCIClass', 'classstructureNm'],
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'dataSrc',
          width: '100px'
        }, {
          prop: 'disCIType',
          width: '200px'
        }, {
          prop: 'disCIClass',
          width: '200px'
        }, {
          prop: 'classstructureNm',
          width: '200px'
        }, {
          prop: 'status',
          width: '100px'
        }],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      discodeGrid: {
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
        filters: [{
          prop: ['code'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'chk',
          width: '30px'
        }, {
          prop: 'code',
          width: '150px'
        }],
        currentData: {},
        total: 0,
        type: '',
        searchParam: ''
      },
      selectedItem: {},
      // Gloval var
      orgId: '',
      classtype: '',
      // Gloval Flag
      showDialogFlag: false,
      showDetailDialogFlag: false,
      showDiscodeDialogFlag: false,
      showClassDialogFlag: false,
      // Combo Data
      datasrcSelect: [],
      discitypeSelect: [],
      disciclassSelect: [],
      statusSelect: [],
      // Valid Msg
      validMsg: {
        duplicateMsg: '',
        dataSrcMsg: '',
        disCITypeMsg: '',
        disCIClassMsg: '',
        classstructureMsg: ''
      }
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.listGrid.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.listGrid.currentData = {}
        this.listGrid.currentPage = 1
        this.initialize()
        this.getInitData()
      }
    }
  },
  computed: {
    visibleAddDisCIMap: function () {
      return this.authorities.CI_DISCI_MAP_ADD
    },
    visibleSaveDisCIMap: function () {
      return this.authorities.CI_DISCI_MAP_SAVE
    },
    visibleDeleteDisCIMap: function () {
      return this.authorities.CI_DISCI_MAP_DELETE
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'statusSelect', domainId: 'STATUS'}
        ]
      }
      this.$util.getComboData(param)
    },
    // USER FN ////////////////////////////////////////////
    isValidationCheck: function () {
      this.validMsg.dataSrcMsg = ''
      this.validMsg.disCITypeMsg = ''
      this.validMsg.disCIClassMsg = ''
      this.validMsg.classstructureMsg = ''
      let dataSrc = this.listGrid.currentData.dataSrc
      let disCIType = this.listGrid.currentData.disCIType
      let disCIClass = this.listGrid.currentData.disCIClass
      let classstructureId = this.listGrid.currentData.classstructureId
      if (!this.$util.isNull(this.validMsg.duplicateMsg)) {
        return false
      }
      if (this.$util.isNull(dataSrc)) {
        this.validMsg.dataSrcMsg = this.labels.inputData
        return false
      }
      if (this.$util.isNull(disCIType)) {
        this.validMsg.disCITypeMsg = this.labels.inputData
        return false
      }
      if (this.$util.isNull(disCIClass)) {
        this.validMsg.disCIClassMsg = this.labels.inputData
        return false
      }
      if (this.$util.isNull(classstructureId)) {
        this.validMsg.classstructureMsg = this.labels.inputData
        return false
      }
      return true
    },
    // TRNS FN ////////////////////////////////////////////
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    getInitData: function () {
      this.$http.get(
        '/episode/api/asset/resource/ci/discicodelist'
      ).then(res => {
        this.datasrcSelect = res.data.datasrc
        this.discitypeSelect = res.data.discitype
        this.disciclassSelect = res.data.disciclass
      }).catch(error => {
        console.log(error)
      })
      this.getListData()
    },
    getListData: function () {
      this.$http.get(
        '/episode/api/asset/resource/ci/discimaptocilist'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    save: function () {
      for (let key in this.vailMsg) {
        this.vailMsg[key] = ''
      }
      if (!this.isValidationCheck()) {
        return
      }
      if (this.$util.isNull(this.listGrid.currentData.id)) {
        this.$http.post(
          '/episode/api/asset/resource/ci/discimaptoci',
          this.listGrid.currentData
        ).then(res => {
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.showDetailDialogFlag = false
          this.getListData()
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.$http.put(
          '/episode/api/asset/resource/ci/discimaptoci',
          this.listGrid.currentData
        ).then(res => {
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.showDetailDialogFlag = false
          this.getListData()
        }).catch(error => {
          console.log(error)
        })
      }
    },
    chkDup: function () {
      let dataSrc = this.listGrid.currentData.dataSrc
      let disCIType = this.listGrid.currentData.disCIType
      let disCIClass = this.listGrid.currentData.disCIClass
      let isVirtual = this.listGrid.currentData.isVirtual
      this.$http.get(
        '/episode/api/asset/resource/ci/discimaptocilist?dataSrc=' + dataSrc + '&disCIType=' + disCIType + '&disCIClass=' + disCIClass + '&isVirtual=' + isVirtual
      ).then(res => {
        if (res.data.length > 0) {
          this.validMsg.duplicateMsg = this.labels.duplicatedMessage
        } else {
          this.validMsg.duplicateMsg = ''
        }
      }).catch(error => {
        console.log(error)
      })
    },
    del: function (row) {
      let dataSrc = row.dataSrc
      let disCIType = row.disCIType
      let disCIClass = row.disCIClass
      let isVirtual = row.isVirtual
      this.$http.get(
        '/episode/api/asset/resource/ci/chkExistsDisci?dataSrc=' + dataSrc + '&disCIType=' + disCIType + '&disCIClass=' + disCIClass + '&isVirtual=' + isVirtual
      ).then(res => {
        if (res.data > 0) {
          this.$util.esmessage(this, 'check', this.labels.existsDisciMessage)
        } else {
          this.$http.delete(
            '/episode/api/asset/resource/ci/discimaptoci/' + row.id
          ).then(res => {
            this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
            this.getListData()
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    close: function () {
      this.$emit('close')
    },
    openDetailDialog: function () {
      this.validMsg.duplicateMsg = ''
      this.validMsg.dataSrcMsg = ''
      this.validMsg.disCITypeMsg = ''
      this.validMsg.disCIClassMsg = ''
      this.validMsg.classstructureMsg = ''
      this.showDetailDialogFlag = true
      this.listGrid.currentData = {
        isVirtual: false,
        status: 'ACTIVE'
      }
    },
    closeDetailDialog: function () {
      this.showDetailDialogFlag = false
    },
    clickGridRow: function (row) {
      this.showDetailDialogFlag = true
      this.listGrid.currentData = row
    },
    openDiscodeDialog: function (type) {
      this.discodeGrid.searchParam = ''
      this.discodeGrid.filters[0].value = ''
      this.selectedItem = {}
      let chkedList = this.discodeGrid.data.filter((data) => data.chk === true)
      chkedList.forEach(data => {
        data.chk = false
      })
      this.discodeGrid.currentPage = 1
      this.showDiscodeDialogFlag = true
      this.discodeGrid.type = type
      this.discodeGrid.data = []
      if (type === 'dataSrc') {
        this.discodeGrid.data = this.datasrcSelect
      } else if (type === 'disCIType') {
        this.discodeGrid.data = this.discitypeSelect
      } else if (type === 'disCIClass') {
        this.discodeGrid.data = this.disciclassSelect
      }
    },
    selectDiscodeGridRow: function (row) {
      let chkedList = this.discodeGrid.data.filter((data) => data.chk === true)
      chkedList.forEach(data => {
        if (data.code !== row.code) {
          data.chk = false
        }
      })
      this.selectedItem = row
    },
    confirmDiscode: function () {
      let type = this.discodeGrid.type
      if (type === 'dataSrc') {
        this.listGrid.currentData.dataSrc = this.selectedItem.code
      } else if (type === 'disCIType') {
        this.listGrid.currentData.disCIType = this.selectedItem.code
      } else if (type === 'disCIClass') {
        this.listGrid.currentData.disCIClass = this.selectedItem.code
      }
      this.showDiscodeDialogFlag = false
      this.chkDup()
    },
    closeDiscodeDialog: function () {
      this.showDiscodeDialogFlag = false
    },
    openClassDialog: function (val) {
      this.showClassDialogFlag = true
      this.classtype = val
    },
    confirmClass: function (rslt) {
      this.listGrid.currentData.classstructureId = rslt.classstructureId
      this.listGrid.currentData.classstructureNm = rslt.classstructureNm
      this.showClassDialogFlag = false
      this.chkDup()
    },
    closeClassDialog: function () {
      this.showClassDialogFlag = false
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    enterSearchDiscode: function () {
      this.discodeGrid.currentPage = 1
      this.discodeGrid.filters[0].value = this.discodeGrid.searchParam
    },
    exportFilter: function () {
      let fileName = 'relation map'
      let fields = [
        {value: 'dataSrc', label: this.labels.dataSrc},
        {value: 'disCIType', label: this.labels.disCIType},
        {value: 'disCIClass', label: this.labels.disCIClass},
        {value: 'classstructureNm', label: this.labels.classstructure},
        {value: 'status', label: this.labels.status}
      ]
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    }
  },
  created: function () {}
}
</script>
