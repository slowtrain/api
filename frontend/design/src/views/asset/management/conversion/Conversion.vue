<!--
 Class Name  : Conversion.vue
 Description : 변환
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.21  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class="comm-btn-box" role="group">
            <b-button variant="primary" class="px-3 sm" v-show="visibleConverterSave" v-on:click="openConvertMgmntDialog">{{labels.convertermgmnt}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="newConversion">{{labels.new}}</b-button>
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="6">
          <div class="search-component">
            <input class="form-control mt-2" style="width: 80%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
          </div>
        </b-col>
        <b-col sm="6">
          <div class="float-right mt-1 mb-1" role="group">
            <el-button v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0 0 0 0; width:40px; height: 40px;">
              <img src="../../../../assets/img/icons/icon_search_download.png" width="60%" />
            </el-button>
          </div>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="12">
          <p class="grid-line"></p>
          <data-tables-server v-if="listGridFlag" ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps" :current-page.sync='listGrid.currentPage'>
            <el-table-column :prop='listGrid.titles[0].prop' :label='labels.tablename'          sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[1].prop' :label='labels.classstructure'     sortable='custom'></el-table-column>
            <el-table-column :prop='listGrid.titles[2].prop' :label='labels.columnname'         sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[3].prop' :label='labels.converterId'        sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[4].prop' :label='labels.conversionprocess'  sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
            <el-table-column align="right" width="100" >
              <template slot-scope="props">
                <el-button v-on:click="clickGridRow(props.row)" :title="labels.detail" style="padding: 0; width:30px; height: 30px;">
                  <img src="@/assets/img/icons/icon_edit.png" width="60%" />
                </el-button>
               <el-button  v-if="visibleDelete" v-on:click="del(props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                  <img src="@/assets/img/icons/icon_delete.png" width="60%" />
                </el-button>
              </template>
            </el-table-column>
          </data-tables-server>
        </b-col>
      </b-row>
    </b-card>
    <!-- Detail Dialog -->
    <el-dialog :title="labels.detailDialogTitle" :visible.sync="showDialogFlag" width=800px  v-on:close="closeDialog">
      <b-row>
        <div class="form-group col-md-12">
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.duplicateMsg)" v-text="validMsg.duplicateMsg"></p>
        </div>
      </b-row>
      <b-row>
        <div class="form-group col-md-6">
          <label for="tablename">{{labels.tablename}}<span class="require-mark">*</span></label>
          <input id="tablename" v-model="listGrid.currentData.tablename" v-lower v-on:blur="chkDup()" class="formdata sm" autocomplete="off" :disabled="listGrid.currentData.hasOwnProperty('id')" v-bind:maxlength="50"/>
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.tablenameMsg)" v-text="validMsg.tablenameMsg"></p>
        </div>
        <div class="form-group col-md-6">
          <label for="classstructure">{{labels.classstructure}}<span class="require-mark">*</span></label>
          <div class="search-component sm">
            <input id="classstructure" v-model="listGrid.currentData.classstructureNm" v-on:blur="chkDup()" class="formdata sm" autocomplete="off" disabled="disabled"/>
            <a class="search-button cursor-pointer" v-on:click="openClassDialog" :disabled="listGrid.currentData.hasOwnProperty('id')">search</a>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.classstructureMsg)" v-text="validMsg.classstructureMsg"></p>
          </div>
        </div>
      </b-row>
      <b-row>
        <div class="form-group col-md-6">
          <label for="columnname">{{labels.columnname}}<span class="require-mark">*</span></label>
          <input id="columnname" v-model="listGrid.currentData.columnname" v-on:blur="chkDup()" v-lower class="formdata sm" autocomplete="off"  :disabled="listGrid.currentData.hasOwnProperty('id')" v-bind:maxlength="50" />
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.columnnameMsg)" v-text="validMsg.columnnameMsg"></p>
        </div>
        <div class="form-group col-md-6">
          <label for="converterId">{{labels.converterId}}<span class="require-mark">*</span></label>
          <div class="search-component sm">
            <input id="converterId" v-model="listGrid.currentData.converterId" class="formdata sm" autocomplete="off" disabled="disabled" v-bind:maxlength="50"/>
            <a class="search-button cursor-pointer" v-on:click="openConvertDialog">search</a>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.converterIdMsg)" v-text="validMsg.converterIdMsg"></p>
          </div>
        </div>
      </b-row>
      <b-row>
        <div class="form-group col-md-12" v-show="visibleConverterSave">
          <label for="columnname">{{labels.conversionprocess}}</label>
          <el-select v-model="listGrid.currentData.conversionprocess" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
          <el-option v-for="process in processSelect" :prop="process.key" :value="process.key" :label="process.value" :key="process.key">
            {{process.value}}
          </el-option>
        </el-select>
        </div>
      </b-row>
      <b-row>
        <div class='form-group col-md-6'>
          <label for="application_createId">{{labels.createNm}}</label>
          <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
        </div>
        <div class='form-group col-md-6'>
          <label for="application_createDt">{{labels.createDt}}</label>
          <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
        </div>
      </b-row>
      <b-row>
        <div class='form-group col-md-6'>
          <label for="application_updateId">{{labels.updateNm}}</label>
          <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
        </div>
        <div class='form-group col-md-6'>
          <label>{{labels.updateDt}}</label>
          <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
        </div>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="save" v-show="visibleSave">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-on:click="closeDialog">{{labels.close}}</b-button>
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
    <!-- Converter Dialog -->
    <ConversionConverterDialog
      ref="conversionConverterDialog"
      v-bind:showDialogFlagProp="showConverterDialogFlag"
      v-bind:labels="labels"
      v-on:confirm="confirmConverter"
      v-on:close="closeConverterDialog"
    />
    <!-- ConverterMgmnt Dialog -->
    <ConversionConverterMgmntDialog
      ref="conversionConverterMgmntDialog"
      v-bind:showDialogFlagProp="showConverterMgmntDialogFlag"
      v-bind:labels="labels"
      v-bind:authorities="authorities"
      v-on:close="closeConverterMgmntDialog"
    />
  </div>
</template>
<script>
import Class from '@/views/common/Class'
import ConversionConverterDialog from './ConversionConverterDialog'
import ConversionConverterMgmntDialog from './ConversionConverterMgmntDialog'
export default {
  name: 'CONVERSION',
  components: {
    'Class': Class,
    'ConversionConverterDialog': ConversionConverterDialog,
    'ConversionConverterMgmntDialog': ConversionConverterMgmntDialog
  },
  data () {
    return {
      labels: {
        id: '', columnname: ''
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: false,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 10,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['tablename', 'classstructureNm', 'columnname', 'converterId'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'tablename',
          width: '200px'
        }, {
          prop: 'classstructureNm',
          width: '300px'
        }, {
          prop: 'columnname',
          width: '200px'
        }, {
          prop: 'converterId',
          width: '200px'
        }, {
          prop: 'conversionprocess',
          width: '200px'
        }],
        columns: [],
        columnNames: [],
        currentData: {},
        total: 0,
        srchCnd: null
      },
      // Auth
      authorities: {
        CONVERSION_ADD: false,
        CONVERSION_SAVE: false,
        CONVERSION_DELETE: false,
        CONVERSION_CONVERTER_ADD: false,
        CONVERSION_CONVERTER_SAVE: false,
        CONVERSION_CONVERTER_DELETE: false
      },
      // Global Var
      orgId: '',
      lang: '',
      searchParam: '',
      // Valid Msg
      validMsg: {
        tablenameMsg: '',
        columnnameMsg: '',
        classstructureMsg: '',
        converterIdMsg: '',
        duplicateMsg: ''
      },
      // combo
      processSelect: [],
      // Dialog Flag
      showDialogFlag: false,
      showClassDialogFlag: false,
      showConverterDialogFlag: false,
      showConverterMgmntDialogFlag: false,
      listGridFlag: false
    }
  },
  computed: {
    visibleAdd: function () {
      return this.authorities.CONVERSION_ADD
    },
    visibleSave: function () {
      return this.authorities.CONVERSION_SAVE
    },
    visibleDelete: function () {
      return this.authorities.CONVERSION_DELETE
    },
    visibleConverterSave: function () {
      return this.authorities.CONVERSION_CONVERTER_SAVE
    }
  },
  watch: {
    showDialogFlag: function (val) {
      if (val) {
        this.validMsg.tablenameMsg = ''
        this.validMsg.columnnameMsg = ''
        this.validMsg.classstructureMsg = ''
        this.validMsg.converterIdMsg = ''
        this.validMsg.duplicateMsg = ''
      }
    },
    'listGrid.currentData.tablename': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.tablenameMsg = ''
      }
    },
    'listGrid.currentData.classstructureId': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.classstructureMsg = ''
      }
    },
    'listGrid.currentData.columnname': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.columnnameMsg = ''
      }
    },
    'listGrid.currentData.converterId': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.converterIdMsg = ''
      }
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.orgId = this.$store.getters.getUser.orgId
      this.lang = this.$store.getters.getUser.lang
      this.listGridFlag = true
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    isValidationCheck: function () {
      this.validMsg.tablenameMsg = ''
      this.validMsg.columnnameMsg = ''
      this.validMsg.classstructureMsg = ''
      this.validMsg.converterIdMsg = ''
      let tablename = this.listGrid.currentData.tablename
      let columnname = this.listGrid.currentData.columnname
      let classstructureId = this.listGrid.currentData.classstructureId
      let converterId = this.listGrid.currentData.converterId
      let rtnFlag = true
      if (!this.$util.isNull(this.validMsg.duplicateMsg)) {
        rtnFlag = false
      }
      if (this.$util.isNull(tablename)) {
        this.validMsg.tablenameMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(columnname)) {
        this.validMsg.columnnameMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(classstructureId)) {
        this.validMsg.classstructureMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(converterId)) {
        this.validMsg.converterIdMsg = this.labels.inputData
        rtnFlag = false
      }
      return rtnFlag
    },
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'processSelect', query: '/episode/api/asset/management/conversion/processlist', sel: 'S'}
        ]
      }
      this.$util.getComboData(param)
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'descending'
      let searchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        lang: this.lang,
        searchParam: searchParam}
      }
      this.$http.get(
        '/episode/api/asset/management/conversion',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    getRowData: function (id) {
      this.$http.get(
        '/episode/api/asset/management/conversion/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    save: function () {
      if (this.isValidationCheck()) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/asset/management/conversion/' + this.listGrid.currentData.id : '/episode/api/asset/management/conversion'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
            this.showDialogFlag = false
            this.listGrid.filters[0].value = '/'
            this.listGrid.filters[0].value = this.searchParam
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        } else {
          this.showDialogFlag = false
        }
      }
    },
    del: function (row) {
      let $this = this
      this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
        this.$http.delete('/episode/api/asset/management/conversion/' + row.id).then(res => {
          $this.$util.esmessage($this, 'delete', $this.labels.deleteMessage)
          $this.activeTab = 'list'
          $this.listGrid.filters[0].value = '/'
          $this.listGrid.filters[0].value = $this.searchParam
        }).catch(error => {
          $this.$util.esmessage($this, 'warn', $this.labels.failMessage)
          console.log(error)
        })
      })
    },
    // EVENT FN ///////////////////////////////////////////
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.searchParam
    },
    exportFilter: function () {
      let srchCnd = this.listGrid.srchCnd
      let orderBy = 'id'
      let sort = 'descending'
      let searchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
      }
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        lang: this.lang,
        searchParam: searchParam}
      }
      this.$http.get(
        '/episode/api/asset/management/conversion',
        params
      ).then(res => {
        let fileName = 'conversion'
        let fields = [
          {value: 'tablename', label: this.labels.tablename},
          {value: 'classstructureNm', label: this.labels.classstructure},
          {value: 'columnname', label: this.labels.columnname},
          {value: 'converterId', label: this.labels.converterId}
        ]
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    clickGridRow: function (row) {
      this.getRowData(row.id)
      this.showDialogFlag = true
    },
    changeValue: function (data) {
      if (!this.$util.isNull(data.id)) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    },
    newConversion: function () {
      this.showDialogFlag = true
      this.listGrid.currentData = {
        state: 'ADDED',
        tablename: '',
        classstructureId: '',
        columnname: '',
        converterId: ''
      }
    },
    closeDialog: function () {
      this.showDialogFlag = false
    },
    chkDup: function () {
      let tablename = this.listGrid.currentData.tablename
      let columnname = this.listGrid.currentData.columnname
      let classstructureId = this.listGrid.currentData.classstructureId
      if (!this.listGrid.currentData.hasOwnProperty('id') && !this.$util.isNull(tablename) && !this.$util.isNull(classstructureId) && !this.$util.isNull(columnname)) {
        let params = {params: {
          chkDup: true,
          tablename: tablename,
          columnname: columnname,
          classstructureId: classstructureId}
        }
        this.$http.get(
          '/episode/api/asset/management/conversion',
          params
        ).then(res => {
          if (res.data.total > 0) {
            this.validMsg.duplicateMsg = this.labels.duplicatedMessage
          } else {
            this.validMsg.duplicateMsg = ''
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    openClassDialog: function () {
      this.showClassDialogFlag = true
    },
    confirmClass: function (rslt) {
      this.showClassDialogFlag = false
      this.listGrid.currentData.classstructureId = rslt.classstructureId
      this.listGrid.currentData.classstructureNm = rslt.classstructureNm
      this.chkDup()
    },
    closeClassDialog: function () {
      this.showClassDialogFlag = false
    },
    openConvertDialog: function () {
      this.showConverterDialogFlag = true
    },
    confirmConverter: function (select) {
      this.listGrid.currentData.converterId = select.converterId
      this.showConverterDialogFlag = false
      this.changeValue(this.listGrid.currentData)
    },
    closeConverterDialog: function () {
      this.showConverterDialogFlag = false
    },
    openConvertMgmntDialog: function () {
      this.showConverterMgmntDialogFlag = true
    },
    closeConverterMgmntDialog: function () {
      this.showConverterMgmntDialogFlag = false
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.labels = {}
    this.authorities = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
